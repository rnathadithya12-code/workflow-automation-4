package com.workflowautomation;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/mock")
public class MockApiController {

  @PostMapping("/credit-assessments")
  public Map<String, Object> creditAssessment(@RequestBody Map<String, Object> request) {
    failIfRequested(request, "credit-assessments");
    double score = number(request.getOrDefault("creditScore", 700));
    double debtToIncome = number(request.getOrDefault("debtToIncome", 0.38));
    Map<String, Object> response = new LinkedHashMap<>();
    response.put("status", "ASSESSED");
    response.put("creditScore", score);
    response.put("debtToIncome", debtToIncome);
    response.put("providerReference", "CREDIT-DEMO-" + request.getOrDefault("applicantReference", "UNKNOWN"));
    return response;
  }

  @PostMapping("/payments")
  public Map<String, Object> payment(@RequestBody Map<String, Object> request) {
    failIfRequested(request, "payments");
    String token = String.valueOf(request.getOrDefault("paymentToken", ""));
    if ("tok_demo_failure".equals(token)) {
      throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Synthetic payment provider failure");
    }
    String status = "tok_demo_declined".equals(token) ? "DECLINED" : "APPROVED";
    return Map.of(
        "status", status,
        "paymentReference", "PAY-DEMO-" + request.getOrDefault("orderReference", "UNKNOWN"));
  }

  @PostMapping("/schedule/slots")
  public Map<String, Object> scheduleSlots(@RequestBody Map<String, Object> request) {
    failIfRequested(request, "schedule/slots");
    String preference = String.valueOf(request.getOrDefault("appointmentPreference", "weekday-morning"));
    if ("none".equalsIgnoreCase(preference)) {
      return Map.of(
          "slotsAvailable", false,
          "availableSlots", List.of(),
          "schedulingReference", "SCHEDULE-DEMO-NONE");
    }
    return Map.of(
        "slotsAvailable", true,
        "availableSlots", List.of("2026-11-05T09:00:00+05:30", "2026-11-05T11:30:00+05:30"),
        "schedulingReference", "SCHEDULE-DEMO-" + request.getOrDefault("patientReference", "UNKNOWN"));
  }

  @PostMapping("/schedule/book")
  public Map<String, Object> scheduleBook(@RequestBody Map<String, Object> request) {
    failIfRequested(request, "schedule/book");
    return Map.of("bookingStatus", "BOOKED", "bookingReference", "BOOK-DEMO-" + request.getOrDefault("patientReference", "UNKNOWN"));
  }

  private void failIfRequested(Map<String, Object> request, String service) {
    if (Boolean.TRUE.equals(request.get("simulateApiFailure"))) {
      throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Synthetic " + service + " outage");
    }
  }

  private double number(Object value) {
    return value instanceof Number number ? number.doubleValue() : Double.parseDouble(String.valueOf(value));
  }
}
