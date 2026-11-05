# Setup and deployment

This guide separates the files that can be prepared in advance from the actions that must be performed in the student's Camunda environment.

## Prerequisites

- Camunda Modeler for Camunda 8 BPMN and DMN.
- Java 17 or newer.
- Maven 3.9 or newer.
- A Camunda 8 SaaS cluster or a local/self-managed Camunda 8 development environment.
- Access to Tasklist and Operate for evidence capture.

## Start the worker application

The application lives in `workers/` and exposes both Camunda job workers and the synthetic mock REST API.

```powershell
cd workers
mvn test
mvn spring-boot:run
```

Default mock endpoints:

```text
POST http://localhost:8080/mock/credit-assessments
POST http://localhost:8080/mock/payments
POST http://localhost:8080/mock/schedule/slots
POST http://localhost:8080/mock/schedule/book
```

The mock service returns predictable responses for the sample payloads. Set `simulateApiFailure=true` to produce a controlled HTTP 503 and create a connector incident after retries are exhausted.

## Local self-managed connection

The default profile expects:

```text
Zeebe gRPC: http://localhost:26500
Zeebe REST: http://localhost:8088
```

Override them without editing source:

```powershell
$env:CAMUNDA_CLIENT_MODE = "self-managed"
$env:CAMUNDA_ZEEBE_GRPC_ADDRESS = "http://localhost:26500"
$env:CAMUNDA_ZEEBE_REST_ADDRESS = "http://localhost:8088"
mvn spring-boot:run
```

## SaaS connection

Use the Camunda Spring Boot starter's SaaS profile and supply credentials through the environment or a local untracked configuration file. Do not commit client secrets.

```powershell
$env:CAMUNDA_CLIENT_MODE = "saas"
$env:CAMUNDA_CLIENT_ID = "replace-locally"
$env:CAMUNDA_CLIENT_SECRET = "replace-locally"
$env:CAMUNDA_CLUSTER_ID = "replace-locally"
$env:CAMUNDA_REGION = "replace-locally"
mvn spring-boot:run
```

The exact SaaS property names can change between starter releases; confirm them in the installed Camunda starter documentation before using a real cluster.

## Deploy a lab

1. Open the lab's `.bpmn` and `.dmn` files together in Camunda Modeler.
2. Confirm the connector URL variables point to the running mock endpoints.
3. Deploy the BPMN and DMN resources as one bundle.
4. Start an instance using a sample payload from `docs/variables/`.
5. Use Tasklist to complete any manual review task.
6. Use Operate to inspect variables, activity history, and incidents.
7. For recovery evidence, start a scenario with `simulateApiFailure=true`, wait for the incident, correct the input/configuration, retry the job, and capture both states.

## Version assumptions

The models use Camunda 8 Zeebe extensions, native user tasks, deployment-bound DMN calls, and the `io.camunda:http-json:1` connector task type. The worker project is pinned to the Camunda Spring Boot 3 starter line for Camunda 8.10 and Java 17. If the course cluster is older, open the models in the matching Modeler version and adjust the connector template/version after checking compatibility.
