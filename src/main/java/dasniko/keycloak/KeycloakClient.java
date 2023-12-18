package dasniko.keycloak;

import io.quarkus.oidc.token.propagation.AccessToken;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.Map;

@AccessToken
@RegisterRestClient(configKey = "keycloak-client")
public interface KeycloakClient {

	@GET
	@Path("/broker/{alias}/token")
	Map<String, Object> getIdpToken(@PathParam("alias") String alias);

}
