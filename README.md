# Marketplace

A marketplace built with Kotlin and domain-driven design. Currently, the project contains only the framework-agnostic domain module.

## Modules

- `domain` contains the Offer aggregate, value objects, and invariants. Offers start as `DRAFT` and can be published once to become `ACTIVE`.

## Requirements and tests

Use JDK 21. Run the test suite with `./gradlew test` (or `.\gradlew.bat test` on Windows) from the repository root.
