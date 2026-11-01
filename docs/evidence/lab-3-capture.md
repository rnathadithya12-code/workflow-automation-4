# Lab 3 evidence checklist

| Evidence | File to add | Status |
| --- | --- | --- |
| Deployed appointment/triage definition | `captured/lab-3-deployment.png` | `CAPTURE_REQUIRED` |
| Routine booking with slot | `captured/lab-3-routine-booking.png` | `CAPTURE_REQUIRED` |
| Priority review task in Tasklist | `captured/lab-3-priority-review.png` | `CAPTURE_REQUIRED` |
| Urgent assessment task in Tasklist | `captured/lab-3-urgent-assessment.png` | `CAPTURE_REQUIRED` |
| No-slot route | `captured/lab-3-no-slot.png` | `CAPTURE_REQUIRED` |
| Scheduling incident | `captured/lab-3-incident.png` | `CAPTURE_REQUIRED` |
| Recovered instance after retry | `captured/lab-3-recovered.png` | `CAPTURE_REQUIRED` |

Suggested recovery: start with `simulateApiFailure=true`, inspect the scheduling connector incident, make the mock service available again, retry, and capture the resulting booking or review route.
