package ch.catnip.pushy;

import ch.catnip.pushy.autoconfigure.PushyProperties;
import com.turo.pushy.apns.ApnsClient;
import com.turo.pushy.apns.ApnsClientBuilder;
import com.turo.pushy.apns.ApnsPushNotification;
import com.turo.pushy.apns.PushNotificationResponse;
import com.turo.pushy.apns.auth.ApnsSigningKey;
import com.turo.pushy.apns.util.concurrent.PushNotificationFuture;
import io.netty.util.concurrent.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import javax.annotation.PreDestroy;
import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class PushyApnsClient {

    private static final Logger log = LoggerFactory.getLogger(PushyApnsClient.class);

    private final ApnsClient apnsClient;

    public PushyApnsClient(final PushyProperties pushyProperties) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        final String host = pushyProperties.getUseProductionHost() ? ApnsClientBuilder.PRODUCTION_APNS_HOST : ApnsClientBuilder.DEVELOPMENT_APNS_HOST;

        final File signingKey = ResourceUtils.getFile(pushyProperties.getSigningKey());
        final ApnsSigningKey apnsSigningKey = ApnsSigningKey.loadFromPkcs8File(signingKey, pushyProperties.getTeamId(), pushyProperties.getKeyId());

        apnsClient = new ApnsClientBuilder().setApnsServer(host).setSigningKey(apnsSigningKey).build();

        log.debug("Initialized with host: {} keyId: {} teamId: {}", host, pushyProperties.getKeyId(), pushyProperties.getTeamId());
    }

    @PreDestroy
    public void destroy() throws InterruptedException {
        if (apnsClient != null) {
            final Future<Void> disconnectFuture = apnsClient.close();
            disconnectFuture.await();
            log.debug("Disconnected");
        }
    }

    public <T extends ApnsPushNotification> PushNotificationFuture<T, PushNotificationResponse<T>> sendNotification(T notification) {
        return apnsClient.sendNotification(notification);
    }
}
