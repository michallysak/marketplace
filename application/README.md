# Application

Spring Boot application exposing offer publication over REST. It depends on `domain`, which owns the business rules.

The module contains REST controllers, application services, and in-memory persistence. Stored offers are lost on restart.

## Run

Requires JDK 21. From the repository root in Bash:

```bash
./gradlew :application:bootRun
```

## Publish an offer

With the application running, send `POST /offers` from another terminal.

```bash
curl --include \
  --header 'Content-Type: application/json' \
  --data '{"title":"Book","priceAmount":25.00,"priceCurrency":"PLN"}' \
  http://localhost:8080/offers
```

The request creates a new published offer and returns `201 Created` with its generated ID and details. Omit `priceCurrency` to use the default `PLN`.
