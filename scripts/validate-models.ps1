$ErrorActionPreference = "Stop"

$root = Split-Path -Parent $PSScriptRoot
$mapping = @{
  "models/lab-1-loan-approval/loan-approval.bpmn" = "models/lab-1-loan-approval/loan-approval.dmn"
  "models/lab-2-order-processing/order-processing.bpmn" = "models/lab-2-order-processing/order-priority.dmn"
  "models/lab-3-hospital-triage/hospital-appointment-triage.bpmn" = "models/lab-3-hospital-triage/patient-triage.dmn"
}

function Read-Xml([string]$relativePath) {
  $path = Join-Path $root $relativePath
  if (-not (Test-Path -LiteralPath $path)) {
    throw "Missing model: $relativePath"
  }
  [xml](Get-Content -LiteralPath $path -Raw)
}

foreach ($bpmnRelative in $mapping.Keys) {
  $bpmn = Read-Xml $bpmnRelative
  $dmnRelative = $mapping[$bpmnRelative]
  $dmn = Read-Xml $dmnRelative
  $process = $bpmn.SelectSingleNode("//*[local-name()='process']")
  $decision = $dmn.SelectSingleNode("//*[local-name()='decision']")
  $calledDecision = $bpmn.SelectSingleNode("//*[local-name()='calledDecision']")

  if ($null -eq $process -or $process.isExecutable -ne "true") {
    throw "$bpmnRelative must contain one executable process"
  }
  if ($null -eq $decision -or $null -eq $calledDecision) {
    throw "$bpmnRelative and $dmnRelative must contain a process and called DMN decision"
  }
  if ($calledDecision.decisionId -ne $decision.id) {
    throw "Decision mismatch: $bpmnRelative calls $($calledDecision.decisionId), but $dmnRelative defines $($decision.id)"
  }

  $nodeIds = @($process.SelectNodes("./*") | Where-Object { $_.id } | ForEach-Object { $_.id })
  foreach ($flow in $process.SelectNodes("./*[local-name()='sequenceFlow']")) {
    if ($flow.sourceRef -notin $nodeIds -or $flow.targetRef -notin $nodeIds) {
      throw "Broken sequence flow $($flow.id) in $bpmnRelative"
    }
  }

  $table = $dmn.SelectSingleNode("//*[local-name()='decisionTable']")
  $inputs = @($table.SelectNodes("./*[local-name()='input']")).Count
  $outputs = @($table.SelectNodes("./*[local-name()='output']")).Count
  foreach ($rule in $table.SelectNodes("./*[local-name()='rule']")) {
    if (@($rule.SelectNodes("./*[local-name()='inputEntry']")).Count -ne $inputs) {
      throw "DMN rule $($rule.id) has the wrong input count in $dmnRelative"
    }
    if (@($rule.SelectNodes("./*[local-name()='outputEntry']")).Count -ne $outputs) {
      throw "DMN rule $($rule.id) has the wrong output count in $dmnRelative"
    }
  }

  Write-Host "OK $bpmnRelative -> $dmnRelative ($($process.id) / $($decision.id))"
}

foreach ($relativePath in $mapping.Keys + $mapping.Values) {
  $source = Join-Path $root $relativePath
  $resource = $relativePath.Replace("models/", "workers/src/main/resources/models/")
  $resourcePath = Join-Path $root $resource
  if (-not (Test-Path -LiteralPath $resourcePath)) {
    throw "Missing worker resource copy: $resource"
  }
  $sourceHash = (Get-FileHash -LiteralPath $source -Algorithm SHA256).Hash
  $resourceHash = (Get-FileHash -LiteralPath $resourcePath -Algorithm SHA256).Hash
  if ($sourceHash -ne $resourceHash) {
    throw "Worker resource copy differs from source model: $relativePath"
  }
}

Write-Host "Model validation passed."
