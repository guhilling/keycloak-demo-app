# keycloak-demo-app

Displays some information about the authenticated user and the configured client (service account has to be enabled).

Available as Docker image: [dasniko/keycloak-demo-app](https://hub.docker.com/r/dasniko/keycloak-demo-app)

Example `docker-compose.yml`:

```
version: '3'
services:
  app:
    image: dasniko/keycloak-demo-app:latest
    # command: ["wait-for-it.sh", "keycloak:8080", "--", "./application", "-Dquarkus.http.host=0.0.0.0"]
    environment:
      - QUARKUS_OIDC_AUTH_SERVER_URL=http://keycloak:8080/realms/demo
      - QUARKUS_OIDC_CLIENT_ID=quarkus-app
      - QUARKUS_OIDC_CREDENTIALS_SECRET=some-super-secret-value
    ports:
      - "8080:8080"
```

You can also start it together with a Keycloak server in one compose file, but then you have to wait for Keycloak to start, before this app can be started.
In this case, just use the outcommented command in above example.

There is an example client config [here](./src/test/resources/quarkus-app.json).
