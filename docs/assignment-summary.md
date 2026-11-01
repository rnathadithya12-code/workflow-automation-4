# Assignment source summary

The supplied practical assignment requires three separate Camunda 8 workflow implementations. Each must include BPMN orchestration, DMN rules where specified, Java/Spring Boot external workers, REST integration using synthetic data, process variables, human review, monitoring, failure/recovery testing, screenshots, and a concise report.

The common checklist also requires genuine evidence of deployment, workflow execution, Tasklist, and Operate. This repository contains model design previews and capture templates only for those environment-specific artifacts; it does not represent them as executed evidence.

## Lab mapping

| Lab | Process ID | Decision ID | Mock REST integration |
| --- | --- | --- | --- |
| Loan approval | `loan-approval` | `loan_approval_decision` | `/mock/credit-assessments` |
| E-commerce order processing | `ecommerce-order-processing` | `order_priority_decision` | `/mock/payments` |
| Hospital appointment and triage | `hospital-appointment-triage` | `patient_triage_decision` | `/mock/schedule/slots` |
