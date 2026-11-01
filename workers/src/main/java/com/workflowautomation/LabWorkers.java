package com.workflowautomation;

import io.camunda.client.annotation.JobWorker;
import io.camunda.client.api.response.ActivatedJob;
import io.camunda.client.api.worker.JobClient;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class LabWorkers {

  @JobWorker(type = "lab1.validate-applicant", autoComplete = false, fetchAllVariables = true)
  public void validateApplicant(JobClient client, ActivatedJob job) {
    Map<String, Object> input = job.getVariablesAsMap();
    failIfRequested(input, "lab1.validate-applicant");
    double requestedAmount = number(input.get("requestedAmount"));
    double monthlyIncome = number(input.get("monthlyIncome"));
    boolean valid = text(input.get("applicantReference")) != null && requestedAmount > 0 && monthlyIncome > 0;
    boolean identityValidated = valid && !Boolean.FALSE.equals(input.get("identityValidated"));
    Map<String, Object> output = new HashMap<>();
    output.put("identityValidated", identityValidated);
    output.put("applicantValidationStatus", valid ? "VALID" : "INVALID");
    output.put("affordabilityRatio", monthlyIncome == 0 ? 0 : round(requestedAmount / monthlyIncome));
    complete(client, job, output);
  }

  @JobWorker(type = "lab2.validate-order", autoComplete = false, fetchAllVariables = true)
  public void validateOrder(JobClient client, ActivatedJob job) {
    Map<String, Object> input = job.getVariablesAsMap();
    failIfRequested(input, "lab2.validate-order");
    boolean valid = text(input.get("orderReference")) != null
        && text(input.get("productId")) != null
        && number(input.get("quantity")) > 0
        && text(input.get("paymentToken")) != null;
    complete(client, job, Map.of("orderValidationStatus", valid ? "VALID" : "INVALID"));
  }

  @JobWorker(type = "lab2.check-inventory-and-total", autoComplete = false, fetchAllVariables = true)
  public void checkInventoryAndTotal(JobClient client, ActivatedJob job) {
    Map<String, Object> input = job.getVariablesAsMap();
    failIfRequested(input, "lab2.check-inventory-and-total");
    String productId = text(input.get("productId"));
    int quantity = (int) number(input.get("quantity"));
    int availableQuantity = WorkflowRules.availableQuantity(productId);
    BigDecimal total = WorkflowRules.unitPrice(productId, input.get("unitPrice"))
        .multiply(BigDecimal.valueOf(quantity)).setScale(2, RoundingMode.HALF_UP);
    Map<String, Object> output = new HashMap<>();
    output.put("availableQuantity", availableQuantity);
    output.put("stockAvailable", quantity > 0 && quantity <= availableQuantity);
    output.put("stockStatus", quantity > availableQuantity ? "INSUFFICIENT" : "AVAILABLE");
    output.put("orderTotal", total);
    complete(client, job, output);
  }

  @JobWorker(type = "lab2.release-to-fulfilment", autoComplete = false, fetchAllVariables = true)
  public void releaseToFulfilment(JobClient client, ActivatedJob job) {
    Map<String, Object> output = new HashMap<>();
    output.put("fulfilmentStatus", "RELEASED");
    output.put("fulfilmentReference", "FULFIL-DEMO-" + job.getProcessInstanceKey());
    output.put("fulfilmentTimestamp", Instant.now().toString());
    complete(client, job, output);
  }

  @JobWorker(type = "lab3.validate-registration", autoComplete = false, fetchAllVariables = true)
  public void validateRegistration(JobClient client, ActivatedJob job) {
    Map<String, Object> input = job.getVariablesAsMap();
    failIfRequested(input, "lab3.validate-registration");
    boolean valid = text(input.get("patientReference")) != null
        && text(input.get("appointmentPreference")) != null
        && text(input.get("symptomsSummary")) != null;
    complete(client, job, Map.of("registrationStatus", valid ? "VALID" : "INVALID"));
  }

  @JobWorker(type = "lab3.reserve-slot", autoComplete = false, fetchAllVariables = true)
  public void reserveSlot(JobClient client, ActivatedJob job) {
    Map<String, Object> input = job.getVariablesAsMap();
    failIfRequested(input, "lab3.reserve-slot");
    Object scheduleResponse = input.get("scheduleResponse");
    Object rawSlots = scheduleResponse instanceof Map<?, ?> response
        ? response.get("availableSlots")
        : input.get("availableSlots");
    String selectedSlot = rawSlots instanceof List<?> slots && !slots.isEmpty() ? String.valueOf(slots.get(0)) : "demo-slot-1";
    complete(client, job, Map.of("bookingStatus", "BOOKED", "bookedSlot", selectedSlot));
  }

  private void failIfRequested(Map<String, Object> input, String workerType) {
    if (Boolean.TRUE.equals(input.get("simulateWorkerFailure"))) {
      throw new IllegalStateException("Synthetic failure requested for " + workerType + "; correct the input before retrying.");
    }
  }

  private void complete(JobClient client, ActivatedJob job, Map<String, Object> variables) {
    client.newCompleteCommand(job.getKey()).variables(variables).send().join();
  }

  private static String text(Object value) {
    if (value == null || String.valueOf(value).isBlank()) {
      return null;
    }
    return String.valueOf(value);
  }

  private static double number(Object value) {
    return value instanceof Number number ? number.doubleValue() : 0;
  }

  private static double round(double value) {
    return BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP).doubleValue();
  }
}
