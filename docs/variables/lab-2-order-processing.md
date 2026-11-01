# Lab 2 process variables

| Variable | Type | Purpose | Example |
| --- | --- | --- | --- |
| `orderReference` | string | Synthetic order key | `ORD-2001` |
| `customerReference` | string | Synthetic customer key | `CUS-2001` |
| `productId` | string | Educational product SKU | `SKU-RED-001` |
| `quantity` | number | Requested units | `3` |
| `unitPrice` | number | Optional price override | `79.90` |
| `customerCategory` | string | DMN input | `Regular` or `VIP` |
| `deliveryPreference` | string | DMN input | `Express` or `Standard` |
| `paymentToken` | string | Mock token only | `tok_demo_approved` |
| `paymentApiUrl` | string | HTTP JSON connector URL | `http://localhost:8080/mock/payments` |
| `simulateApiFailure` | boolean | Controlled payment-failure switch | `false` |
| `simulateWorkerFailure` | boolean | Controlled worker-failure switch | `false` |
| `orderValidationStatus` | string | Validation result | `VALID` |
| `availableQuantity` | number | In-memory inventory result | `12` |
| `stockAvailable` | boolean | Inventory gateway input | `true` |
| `stockStatus` | string | Human-readable stock result | `AVAILABLE` |
| `orderTotal` | number | Calculated total | `239.70` |
| `paymentResponse` | object | Connector response mapping | `{paymentStatus: "APPROVED"}` |
| `orderPriority` | object | DMN output context | `{priority: "Standard"}` |
| `fulfilmentStatus` | string | Worker output | `RELEASED` |

## Successful sample payload

```json
{
  "orderReference": "ORD-2001",
  "customerReference": "CUS-2001",
  "productId": "SKU-RED-001",
  "quantity": 3,
  "customerCategory": "Regular",
  "deliveryPreference": "Express",
  "paymentToken": "tok_demo_approved",
  "paymentApiUrl": "http://localhost:8080/mock/payments",
  "simulateApiFailure": false,
  "simulateWorkerFailure": false
}
```

Use `SKU-BLUE-002` with quantity `5` for insufficient stock, `tok_demo_declined` for a declined payment, and an order total of at least `500` for manual verification.
