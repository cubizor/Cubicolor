# CLAUDE.md — Cubicolor

Cubicolor is the Cubizor semantic color / theme system (`MessageRole`, color tags, theme JSON) used
by cubiloc and Carbon View. Its conventions are documented as Claude Code **skills and rules that
live outside this repo**.

## Keep tooling in sync — MANDATORY

If you change Cubicolor's `MessageRole` values, color tags, theme JSON format, or version, you
**MUST update the owning rule/skill in the SAME change** so the docs never drift from the code:

- rule `architecture/i18n.md` (theme / `MessageRole` section, in `cubizor/cubizor-rules`)
- skills `cubizor:gui-design` / `cubizor:cubizor-conventions` where colors/themes are referenced

Skills live in the **`cubizor/claude-plugins`** repo; shared rules in **`cubizor/cubizor-rules`**.
Updating Cubicolor and leaving the theme rules stale is the drift this rule prevents.
