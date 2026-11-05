package com.workflowautomation;

import io.camunda.client.annotation.Deployment;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Deployment(
    resources = {
      "classpath:models/lab-1-loan-approval/loan-approval.bpmn",
      "classpath:models/lab-1-loan-approval/loan-approval.dmn",
      "classpath:models/lab-2-order-processing/order-processing.bpmn",
      "classpath:models/lab-2-order-processing/order-priority.dmn",
      "classpath:models/lab-3-hospital-triage/hospital-appointment-triage.bpmn",
      "classpath:models/lab-3-hospital-triage/patient-triage.dmn"
    })
public class WorkflowAutomationApplication {

  public static void main(String[] args) {
    SpringApplication.run(WorkflowAutomationApplication.class, args);
  }
}
