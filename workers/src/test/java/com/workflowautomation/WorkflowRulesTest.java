package com.workflowautomation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class WorkflowRulesTest {

  @Test
  void approvesStrongLoan() {
    assertEquals("Approved", WorkflowRules.loanDecision(true, 760, 0.24, 4.6).decision());
  }

  @Test
  void rejectsLowScoreLoan() {
    assertEquals("Rejected", WorkflowRules.loanDecision(true, 520, 0.20, 3.0).decision());
  }

  @Test
  void sendsBorderlineLoanToManualReview() {
    assertEquals("Manual Review", WorkflowRules.loanDecision(true, 680, 0.40, 7.0).decision());
  }

  @Test
  void rejectsLoanWithFailedIdentity() {
    assertEquals("Rejected", WorkflowRules.loanDecision(false, 800, 0.20, 3.0).decision());
  }

  @Test
  void marksExpressHighValueOrderAsHighPriority() {
    assertEquals("High", WorkflowRules.orderPriority(650, "Regular", "Express").priority());
  }

  @Test
  void keepsSmallStandardOrderOnStandardRoute() {
    assertEquals("Standard", WorkflowRules.orderPriority(120, "Regular", "Standard").priority());
  }

  @Test
  void routesUrgentFlagsToImmediateAssessment() {
    assertEquals("Urgent Assessment", WorkflowRules.triageDecision(true, false, 2, "adult").triageCategory());
  }

  @Test
  void routesLongDurationToPriorityReview() {
    assertEquals("Priority Review", WorkflowRules.triageDecision(false, false, 72, "adult").triageCategory());
  }

  @Test
  void routesOrdinaryRequestToRoutine() {
    assertEquals("Routine", WorkflowRules.triageDecision(false, false, 6, "adult").triageCategory());
  }

  @Test
  void detectsInsufficientInventory() {
    assertEquals(4, WorkflowRules.availableQuantity("SKU-BLUE-002"));
  }
}
