package com.workflowautomation;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

public final class WorkflowRules {

  private WorkflowRules() {}

  public record LoanDecision(String decision, String reasonCode) {}

  public record OrderPriority(String priority, String reasonCode) {}

  public record TriageDecision(String triageCategory, String reasonCode) {}

  public static LoanDecision loanDecision(
      boolean identityValidated, double creditScore, double debtToIncome, double affordabilityRatio) {
    if (!identityValidated) {
      return new LoanDecision("Rejected", "IDENTITY_FAILED");
    }
    if (creditScore >= 720 && debtToIncome <= 0.35 && affordabilityRatio <= 6) {
      return new LoanDecision("Approved", "STANDARD_ELIGIBILITY");
    }
    if (creditScore >= 650 && debtToIncome <= 0.45 && affordabilityRatio <= 8) {
      return new LoanDecision("Manual Review", "BORDERLINE_ELIGIBILITY");
    }
    return new LoanDecision("Rejected", "POLICY_NOT_MET");
  }

  public static OrderPriority orderPriority(
      double orderTotal, String customerCategory, String deliveryPreference) {
    if (orderTotal >= 500 && "Express".equalsIgnoreCase(deliveryPreference)) {
      return new OrderPriority("High", "EXPRESS_HIGH_VALUE");
    }
    if (orderTotal >= 300) {
      return new OrderPriority("High", "HIGH_ORDER_VALUE");
    }
    if ("VIP".equalsIgnoreCase(customerCategory)
        && "Express".equalsIgnoreCase(deliveryPreference)) {
      return new OrderPriority("High", "VIP_EXPRESS");
    }
    return new OrderPriority("Standard", "STANDARD_ROUTE");
  }

  public static TriageDecision triageDecision(
      boolean chestPain, boolean breathingDifficulty, double symptomDurationHours, String ageBand) {
    if (chestPain) {
      return new TriageDecision("Urgent Assessment", "CHEST_PAIN_FLAG");
    }
    if (breathingDifficulty) {
      return new TriageDecision("Urgent Assessment", "BREATHING_FLAG");
    }
    if (symptomDurationHours >= 72) {
      return new TriageDecision("Priority Review", "LONG_DURATION");
    }
    if (symptomDurationHours >= 24 && "senior".equalsIgnoreCase(ageBand)) {
      return new TriageDecision("Priority Review", "SENIOR_LONG_DURATION");
    }
    return new TriageDecision("Routine", "ROUTINE_ROUTE");
  }

  public static int availableQuantity(String productId) {
    return Map.of("SKU-RED-001", 12, "SKU-BLUE-002", 4, "SKU-GREEN-003", 25)
        .getOrDefault(productId, 0);
  }

  public static BigDecimal unitPrice(String productId, Object requestedPrice) {
    if (requestedPrice instanceof Number number) {
      return BigDecimal.valueOf(number.doubleValue()).setScale(2, RoundingMode.HALF_UP);
    }
    return switch (productId) {
      case "SKU-RED-001" -> new BigDecimal("79.90");
      case "SKU-BLUE-002" -> new BigDecimal("149.50");
      case "SKU-GREEN-003" -> new BigDecimal("29.95");
      default -> BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
    };
  }
}
