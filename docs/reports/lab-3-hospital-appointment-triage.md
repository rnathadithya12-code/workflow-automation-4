# Lab 3 Hospital appointment and patient triage

## Objective

Register a synthetic patient request, classify urgency with a small DMN table, check mock appointment availability, and route the request to routine booking or staff assessment.

## Process flow

`Register request -> Validate registration -> Evaluate triage -> Routine / Priority review / Urgent assessment`

Routine and reviewed cases call the scheduling REST connector. A worker then reserves the selected synthetic slot. No clinical system is contacted.

## Educational rules

The DMN returns `triageCategory` and `reasonCode`.

- Urgent Assessment when chest pain or breathing difficulty is marked true.
- Priority Review when symptoms have lasted at least 72 hours.
- Priority Review for a senior age band with symptoms lasting at least 24 hours.
- Routine for other inputs.

These rules are deliberately simplified and must not be used for medical decisions. The urgent branch only demonstrates workflow routing to immediate human assessment.

## Required execution evidence

Capture a routine booking, priority review task, urgent assessment task, no-slot branch, and scheduling incident/recovery. Use `docs/evidence/lab-3-capture.md`.

## Limitations

Symptoms are flags in synthetic test data. The mock scheduler does not reserve real appointments, and the triage table is not a clinical protocol.
