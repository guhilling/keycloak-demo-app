package dasniko.keycloak;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.quarkus.oidc.IdToken;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.security.Authenticated;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.security.Principal;
import java.util.Map;

@Path("/user")
@Authenticated
public class UserInfo {

	@Inject
	Template user;
	@Inject
	@ConfigProperty(name = "quarkus.oidc.client-id")
	String clientId;

	@Inject
	Principal principal;
	@Inject
	JsonWebToken accessToken;
	@Inject
	@IdToken
	JsonWebToken idToken;
	@Inject
	io.quarkus.oidc.UserInfo userInfo;

	@Inject
	ObjectMapper objectMapper;

	@Inject
	@RestClient
	KeycloakClient keycloakClient;

	@GET
	@Produces(MediaType.TEXT_HTML)
	public TemplateInstance userInfo() throws IOException {
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		TypeReference<Map<String, Object>> typeRef = new TypeReference<>() {
		};
		return user
			.data("clientId", clientId)
			.data("principal", principal.getName())
			.data("accessToken", objectMapper.writeValueAsString(accessToken))
			.data("rawAccessToken", accessToken.getRawToken())
			.data("idToken", objectMapper.writeValueAsString(idToken))
			.data("rawIdToken", idToken.getRawToken())
			.data("userInfo", objectMapper.writeValueAsString(objectMapper.readValue(userInfo.getUserInfoString(), typeRef)));
	}

	@GET
	@Path("broker")
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> getIdpTokens(@QueryParam("alias") String alias) {
		try {
			return keycloakClient.getIdpToken(alias);
		} catch (WebApplicationException e) {
			return Map.of("message", e.getMessage());
		}
	}

}
