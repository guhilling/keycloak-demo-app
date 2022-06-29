package dasniko.keycloak;

import io.quarkus.qute.TemplateGlobal;
import org.eclipse.microprofile.config.ConfigProvider;

/**
 * @author Niko Köbler, https://www.n-k.de, @dasniko
 */
@TemplateGlobal
public class Globals {
	static String clientId() {
		return ConfigProvider.getConfig().getValue("quarkus.oidc.client-id", String.class);
	}
}
