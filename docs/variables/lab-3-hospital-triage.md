# Lab 3 process variables

| Variable | Type | Purpose | Example |
| --- | --- | --- | --- |
| `patientReference` | string | Synthetic patient key | `PAT-3001` |
| `appointmentPreference` | string | Requested slot preference | `weekday-morning` |
| `symptomsSummary` | string | Educational text summary | `mild cough` |
| `chestPain` | boolean | DMN urgency flag | `false` |
| `breathingDifficulty` | boolean | DMN urgency flag | `false` |
| `symptomDurationHours` | number | DMN input | `6` |
| `ageBand` | string | Educational age band | `adult` |
| `schedulingApiUrl` | string | HTTP JSON connector URL | `http://localhost:8080/mock/schedule/slots` |
| `simulateApiFailure` | boolean | Controlled scheduler-failure switch | `false` |
| `simulateWorkerFailure` | boolean | Controlled worker-failure switch | `false` |
| `registrationStatus` | string | Worker validation result | `VALID` |
| `triageDecision` | object | DMN output context | `{triageCategory: "Routine"}` |
| `scheduleResponse` | object | Connector response mapping | `{slotsAvailable: true}` |
| `slotsAvailable` | boolean | Appointment gateway input | `true` |
| `scheduleResponse.availableSlots` | list of strings | Synthetic slots returned by connector | `["2026-11-05T09:00:00+05:30"]` |
| `bookingStatus` | string | Reservation worker output | `BOOKED` |
| `bookedSlot` | string | Selected synthetic slot | `2026-11-05T09:00:00+05:30` |

## Routine sample payload

```json
{
  "patientReference": "PAT-3001",
  "appointmentPreference": "weekday-morning",
  "symptomsSummary": "mild cough for six hours",
  "chestPain": false,
  "breathingDifficulty": false,
  "symptomDurationHours": 6,
  "ageBand": "adult",
  "schedulingApiUrl": "http://localhost:8080/mock/schedule/slots",
  "simulateApiFailure": false,
  "simulateWorkerFailure": false
}
```

For priority review use `symptomDurationHours: 72`. For urgent assessment set `chestPain: true` or `breathingDifficulty: true`. For the no-slot branch use `appointmentPreference: "none"`.
