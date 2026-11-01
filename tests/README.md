# Test scenario matrix

The table below is the assignment-facing test plan. The Java unit tests validate the deterministic educational rules and worker calculations. Camunda deployment, Tasklist, Operate, incident, and retry scenarios must be executed in the student's environment and recorded using the evidence templates.

| Lab | Scenario | Input cue | Expected route | Test type |
| --- | --- | --- | --- | --- |
| 1 | Approval | score 760, DTI 0.24, ratio 4.6 | Approved | Rule unit test + Camunda capture |
| 1 | Eligibility failure | score 520 | Rejected | Rule unit test + Camunda capture |
| 1 | Manual review | score 680, DTI 0.40, ratio 7.0 | Manual Review | Rule unit test + Tasklist capture |
| 1 | API failure | `simulateApiFailure=true` | Incident, then retry | Camunda environment |
| 1 | Worker failure | `simulateWorkerFailure=true` | Incident, then retry | Camunda environment |
| 2 | Successful order | stock available, approved token | Fulfilment | Rule unit test + Camunda capture |
| 2 | Insufficient stock | quantity above synthetic stock | Out of stock | Rule unit test + Camunda capture |
| 2 | Payment declined | `tok_demo_declined` | Payment declined | Mock API + Camunda capture |
| 2 | High-value order | total at least 500 | Manual verification | Rule unit test + Tasklist capture |
| 2 | Payment failure | `tok_demo_failure` | Incident, then retry | Camunda environment |
| 3 | Routine booking | no urgent flag, slot available | Book slot | Rule unit test + Camunda capture |
| 3 | Priority review | symptoms 72 hours | Staff review | Rule unit test + Tasklist capture |
| 3 | Urgent assessment | chest pain or breathing difficulty | Immediate staff assessment | Rule unit test + Tasklist capture |
| 3 | No slot | preference `none` | Staff offers alternative | Mock API + Camunda capture |
| 3 | Scheduling failure | `simulateApiFailure=true` | Incident, then retry | Camunda environment |
