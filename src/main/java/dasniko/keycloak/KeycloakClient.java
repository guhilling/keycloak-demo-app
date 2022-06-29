package dasniko.keycloak;

import io.quarkus.oidc.token.propagation.AccessToken;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.Map;

@AccessToken
@RegisterRestClient(configKey = "keycloak-client")
public interface KeycloakClient {

	@GET
	@Path("/broker/{alias}/token")
	Map<String, Object> getIdpToken(@PathParam("alias") String alias);

}
