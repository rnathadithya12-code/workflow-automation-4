# Mock REST API examples

These examples use synthetic values and the local Spring Boot application from `workers/`.

## Loan credit assessment

```http
POST http://localhost:8080/mock/credit-assessments
Content-Type: application/json

{
  "applicantReference": "APP-1001",
  "requestedAmount": 24000,
  "monthlyIncome": 5200,
  "creditScore": 760,
  "debtToIncome": 0.24,
  "simulateApiFailure": false
}
```

```json
{
  "status": "ASSESSED",
  "creditScore": 760,
  "debtToIncome": 0.24,
  "providerReference": "CREDIT-DEMO-1001"
}
```

## Mock payment

```http
POST http://localhost:8080/mock/payments
Content-Type: application/json

{
  "orderReference": "ORD-2001",
  "paymentToken": "tok_demo_approved",
  "amount": 349.90,
  "simulateApiFailure": false
}
```

Use `tok_demo_declined` for a declined result and `tok_demo_failure` or `simulateApiFailure=true` for a controlled service failure.

## Mock scheduling

```http
POST http://localhost:8080/mock/schedule/slots
Content-Type: application/json

{
  "patientReference": "PAT-3001",
  "appointmentPreference": "weekday-morning",
  "simulateApiFailure": false
}
```

Use `appointmentPreference: "none"` to return no available slots. Use `simulateApiFailure=true` to produce a controlled 503 response.
