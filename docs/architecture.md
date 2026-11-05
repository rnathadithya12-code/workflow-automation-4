# Architecture and design choices

## One small process solution

The three labs share one Spring Boot application so the submission is easy to run and inspect. Each BPMN model has a distinct process ID and each DMN table has a distinct decision ID. Worker job types are prefixed with `lab1`, `lab2`, or `lab3` to make subscriptions visible in logs and Operate.

## Where work happens

- BPMN controls orchestration, routing, retries, and human-task boundaries.
- DMN contains the educational decision rules and returns a small result context.
- Java workers validate inputs, calculate derived values, reserve synthetic slots, and deliberately expose recovery hooks.
- HTTP JSON connector tasks call the local mock REST endpoints for credit, payment, and scheduling.
- Tasklist is used only for explicit manual review or staff assessment.
- Operate is the evidence surface for variables, activity history, retries, and incidents.

## Failure and recovery pattern

The REST connector and worker service tasks use three retries in the BPMN. A controlled failure flag causes the mock service or worker to fail without inventing a successful response. Once the cause is corrected, the job can be retried from Operate. The evidence folder describes exactly what must be captured.

## Design simplifications

The processes intentionally avoid parallel branches, loops, complex subprocesses, and external identity/payment/clinical systems. This keeps the diagrams readable and makes each required path visible in a single left-to-right flow.
