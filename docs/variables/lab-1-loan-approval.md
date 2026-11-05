# Lab 1 process variables

| Variable | Type | Purpose | Example |
| --- | --- | --- | --- |
| `applicantReference` | string | Synthetic applicant key | `APP-1001` |
| `requestedAmount` | number | Requested loan amount | `24000` |
| `monthlyIncome` | number | Monthly income used for the classroom ratio | `5200` |
| `identityValidated` | boolean | Input override for the identity rule | `true` |
| `creditApiUrl` | string | HTTP JSON connector URL | `http://localhost:8080/mock/credit-assessments` |
| `simulateApiFailure` | boolean | Controlled connector-failure switch | `false` |
| `simulateWorkerFailure` | boolean | Controlled worker-failure switch | `false` |
| `applicantValidationStatus` | string | Worker validation result | `VALID` |
| `affordabilityRatio` | number | `requestedAmount / monthlyIncome` | `4.62` |
| `creditResponse` | object | Connector response mapping | `{creditScore: 760, debtToIncome: 0.24}` |
| `creditResponse.creditScore` | number | DMN input read from connector response | `760` |
| `creditResponse.debtToIncome` | number | DMN input read from connector response | `0.24` |
| `loanDecision` | object | DMN output context | `{decision: "Approved", reasonCode: "STANDARD_ELIGIBILITY"}` |

## Approved sample payload

```json
{
  "applicantReference": "APP-1001",
  "requestedAmount": 24000,
  "monthlyIncome": 5200,
  "identityValidated": true,
  "creditScore": 760,
  "debtToIncome": 0.24,
  "creditApiUrl": "http://localhost:8080/mock/credit-assessments",
  "simulateApiFailure": false,
  "simulateWorkerFailure": false
}
```

For manual review, use score `680`, debt-to-income `0.40`, and requested amount `36400` with the same monthly income. For rejection, use score `520`.
