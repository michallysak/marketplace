# Marketplace

A marketplace built with Kotlin and domain-driven design, with a Spring Boot publish API.

## Modules

- `domain` contains the Offer aggregate, value objects, and invariants. Offers start as `DRAFT` and can be published once to become `ACTIVE`.
- `application` is the runnable Spring Boot module. See its [documentation](application/README.md) for an overview and startup instructions.

The module dependency is `application -> domain`.

## Requirements and tests

Use JDK 21. Run the test suite with `./gradlew test` (or `.\gradlew.bat test` on Windows) from the repository root.
