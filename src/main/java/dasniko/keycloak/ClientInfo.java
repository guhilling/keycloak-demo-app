package dasniko.keycloak;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.quarkus.oidc.client.OidcClient;
import io.quarkus.oidc.client.Tokens;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.jose4j.base64url.Base64;

import java.io.IOException;
import java.util.Map;

@Path("/client")
public class ClientInfo {

	@Inject
	Template client;
	@Inject
	OidcClient oidcClient;
	@Inject
	ObjectMapper objectMapper;
	@Inject

	volatile Tokens tokens;

	@PostConstruct
	void init() {
		tokens = oidcClient.getTokens().await().indefinitely();
	}

	@GET
	@Produces(MediaType.TEXT_HTML)
	public TemplateInstance tokenInfo() throws IOException {
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		TypeReference<Map<String, Object>> typeRef = new TypeReference<>() {
		};
		String accessToken = tokens.getAccessToken();
		String[] parts = accessToken.split("\\.");
		String header = new String(Base64.decode(parts[0]));
		String payload = new String(Base64.decode(parts[1]));
		return client
			.data("header", objectMapper.writeValueAsString(objectMapper.readValue(header, typeRef)))
			.data("payload", objectMapper.writeValueAsString(objectMapper.readValue(payload, typeRef)))
			.data("accessToken", accessToken);
	}

}
