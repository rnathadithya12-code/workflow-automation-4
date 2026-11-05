# Lab 1 Loan approval automation

## Objective

Automate a small loan-approval journey from synthetic applicant validation through credit assessment, DMN classification, and an explicit manual-review path.

## Process flow

`Receive application -> Validate applicant -> Assess credit via REST -> Evaluate loan decision -> Approved / Manual review / Rejected`

Applicant validation is an external worker. Credit assessment is a Camunda HTTP JSON connector call to the mock endpoint. The business rule task evaluates `loan_approval_decision` with deployment binding so the BPMN and DMN resources move together.

## Educational rules

The DMN returns `decision` and `reasonCode`.

- Rejected when identity validation fails.
- Rejected when credit score is below 580.
- Approved when credit score is at least 720, debt-to-income is at most 0.35, and affordability ratio is at most 6.
- Manual Review when credit score is at least 650, debt-to-income is at most 0.45, and affordability ratio is at most 8.
- Other combinations are rejected.

These are classroom rules only. They are not lending advice or a real underwriting policy.

## Required execution evidence

Capture a deployed-definition view, one approved instance, one rejected instance, one manual-review task in Tasklist, Operate variables, and a worker/connector incident followed by a successful retry. Use `docs/evidence/lab-1-capture.md`.

## Limitations

The mock credit service is deterministic and does not access a credit bureau. The model assumes the connected cluster has the HTTP JSON connector available. If the course environment does not provide that connector, replace the connector task with the documented `lab1.assess-credit` worker pattern and keep the same process variables.
