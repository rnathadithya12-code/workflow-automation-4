# Lab 2 E-commerce order processing and fulfilment

## Objective

Validate a synthetic order, calculate its total, check inventory, classify priority with DMN, process a mock payment, and route the order to fulfilment, rejection, or manual verification.

## Process flow

`Receive order -> Validate order -> Check stock and calculate total -> Stock gateway -> Pay via REST -> Payment gateway -> Evaluate priority -> Fulfil or review`

The worker calculates `orderTotal`, `stockAvailable`, and `stockStatus`. The HTTP JSON connector calls the mock payment service. A high-priority order reaches Tasklist for manual verification before fulfilment.

## Educational rules

The DMN uses a `FIRST` hit policy so the high-priority rules are evaluated before the standard fallback.

- High priority for an express order worth at least 500.
- High priority for any order worth at least 300.
- High priority for a VIP customer requesting express delivery.
- Standard priority for standard delivery or any remaining combination.

The payment token is a mock token, never a real card credential.

## Required execution evidence

Capture a successful fulfilment, insufficient-stock route, declined payment, high-value Tasklist review, and a payment/inventory incident followed by retry. Use `docs/evidence/lab-2-capture.md`.

## Limitations

Inventory is an in-memory educational lookup. There is no actual warehouse reservation, shipment provider, or payment processor.
