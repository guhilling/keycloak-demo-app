package dasniko.keycloak;

import io.quarkus.runtime.annotations.RegisterForReflection;
import org.eclipse.microprofile.jwt.JsonWebToken;

/**
 * @author Niko Köbler, https://www.n-k.de, @dasniko
 */
@RegisterForReflection(targets = {JsonWebToken.class})
public class NativeReflectionConfiguration {
}
