# Lab 1 evidence checklist

| Evidence | File to add | Status |
| --- | --- | --- |
| Deployed loan approval definition | `captured/lab-1-deployment.png` | `CAPTURE_REQUIRED` |
| Approved instance with variables | `captured/lab-1-approved.png` | `CAPTURE_REQUIRED` |
| Rejected instance | `captured/lab-1-rejected.png` | `CAPTURE_REQUIRED` |
| Manual review task in Tasklist | `captured/lab-1-tasklist-review.png` | `CAPTURE_REQUIRED` |
| Operate variables/activity history | `captured/lab-1-operate-approved.png` | `CAPTURE_REQUIRED` |
| Connector/worker incident before retry | `captured/lab-1-incident.png` | `CAPTURE_REQUIRED` |
| Recovered instance after retry | `captured/lab-1-recovered.png` | `CAPTURE_REQUIRED` |

Suggested recovery: start with `simulateApiFailure=true` or `simulateWorkerFailure=true`, wait for the incident, correct the input/configuration, retry the failed job in Operate, and capture the completed state.
