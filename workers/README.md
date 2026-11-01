# Worker application

This Spring Boot application contains the external job workers and synthetic REST endpoints used by the three Camunda 8 models.

## Run

```powershell
mvn test
mvn spring-boot:run
```

The application uses the current Camunda Java client package names from the Camunda Spring Boot 3 starter line. It defaults to a self-managed cluster on `localhost:26500`/`localhost:8088`; set the documented environment variables for SaaS or another cluster.

## Worker types

| Job type | Purpose |
| --- | --- |
| `lab1.validate-applicant` | Validate synthetic applicant fields and calculate affordability ratio |
| `lab2.validate-order` | Validate synthetic order fields |
| `lab2.check-inventory-and-total` | Check in-memory stock and calculate total |
| `lab2.release-to-fulfilment` | Mark an approved order as released |
| `lab3.validate-registration` | Validate synthetic patient request fields |
| `lab3.reserve-slot` | Reserve the first synthetic available slot |

Set `simulateWorkerFailure=true` in an input payload to create a deliberate worker failure. Remove the flag before retrying the job in Operate.
