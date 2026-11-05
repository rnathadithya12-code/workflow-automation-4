package com.workflowautomation;

import io.camunda.client.CamundaClient;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/processes")
public class ProcessController {

  private final CamundaClient camundaClient;

  public ProcessController(CamundaClient camundaClient) {
    this.camundaClient = camundaClient;
  }

  @PostMapping("/{processId}/instances")
  public Map<String, Object> startProcess(
      @PathVariable String processId, @RequestBody Map<String, Object> variables) {
    var instance = camundaClient.newCreateInstanceCommand()
        .bpmnProcessId(processId)
        .latestVersion()
        .variables(variables)
        .send()
        .join();
    Map<String, Object> response = new LinkedHashMap<>();
    response.put("processInstanceKey", instance.getProcessInstanceKey());
    response.put("processDefinitionKey", instance.getProcessDefinitionKey());
    return response;
  }
}
