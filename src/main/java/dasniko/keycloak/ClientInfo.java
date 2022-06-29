package dasniko.keycloak;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.quarkus.oidc.client.OidcClient;
import io.quarkus.oidc.client.Tokens;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import org.jose4j.base64url.Base64;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
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
