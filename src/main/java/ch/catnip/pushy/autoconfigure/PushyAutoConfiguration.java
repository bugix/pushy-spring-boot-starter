package ch.catnip.pushy.autoconfigure;

import ch.catnip.pushy.PushyApnsClient;
import com.turo.pushy.apns.ApnsClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Configuration
@ConditionalOnClass(ApnsClient.class)
@EnableConfigurationProperties(PushyProperties.class)
public class PushyAutoConfiguration {

    @Bean
    public PushyApnsClient pushyApnsClient(PushyProperties pushyProperties) throws NoSuchAlgorithmException, InvalidKeyException, IOException {
        return new PushyApnsClient(pushyProperties);
    }

}
