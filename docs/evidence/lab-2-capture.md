# Lab 2 evidence checklist

| Evidence | File to add | Status |
| --- | --- | --- |
| Deployed order-processing definition | `captured/lab-2-deployment.png` | `CAPTURE_REQUIRED` |
| Successful fulfilment | `captured/lab-2-fulfilled.png` | `CAPTURE_REQUIRED` |
| Insufficient-stock route | `captured/lab-2-out-of-stock.png` | `CAPTURE_REQUIRED` |
| Declined payment route | `captured/lab-2-payment-declined.png` | `CAPTURE_REQUIRED` |
| High-value manual review in Tasklist | `captured/lab-2-tasklist-review.png` | `CAPTURE_REQUIRED` |
| Payment/inventory incident | `captured/lab-2-incident.png` | `CAPTURE_REQUIRED` |
| Recovered instance after retry | `captured/lab-2-recovered.png` | `CAPTURE_REQUIRED` |

Suggested recovery: use `tok_demo_failure` or `simulateApiFailure=true`, inspect the incident, restore the mock-service condition, retry the connector job, and capture the successful outcome.
