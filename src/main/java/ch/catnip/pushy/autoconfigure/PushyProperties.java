package ch.catnip.pushy.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "pushy")
public class PushyProperties {

    private Boolean useProductionHost = true;

    @NonNull
    private String signingKey;

    @NonNull
    private String keyId;

    @NonNull
    private String teamId;

    public Boolean getUseProductionHost() {
        return useProductionHost;
    }

    public void setUseProductionHost(Boolean useProductionHost) {
        this.useProductionHost = useProductionHost;
    }

    public String getSigningKey() {
        return signingKey;
    }

    public void setSigningKey(String signingKey) {
        this.signingKey = signingKey;
    }

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }
}
