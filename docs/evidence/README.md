# Evidence capture guide

This folder keeps the submission honest. Design previews are committed under `docs/diagrams/`; live execution evidence must be captured by the student from the Camunda environment used for the demonstration.

## Capture rules

1. Do not use a placeholder, generated, or edited screenshot as execution evidence.
2. Keep the Camunda URL, process name, instance key, and capture date visible where practical.
3. Crop only irrelevant desktop chrome; do not crop away the state, variables, or incident details.
4. Use synthetic data only.
5. Copy the screenshot into `docs/evidence/captured/` and update the relevant lab checklist with the real filename.

## Minimum capture set

- Deployed BPMN/DMN definition or deployment confirmation.
- One successful instance.
- One rejected/exceptional route.
- One Tasklist user task showing the review/assessment queue.
- One Operate process view showing variables or activity history.
- One incident before retry and one successful state after retry.

The templates use `CAPTURE_REQUIRED` markers so an incomplete evidence set is obvious during review.
