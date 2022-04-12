package kg.peaksoft.ebookb4.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
@Getter @Setter
@PropertySource("classpath:application.properties")
public class JwtConfig {
    @Value("${application.jwt.secret-key}")
    private String secretKey;

    @Value("${application.jwt.token-prefix}")
    private String tokenPrefix;

    @Value("${application.jwt.expiration-date-after-days}")
    private Long expirationDateAfterDays;

    public String getAuthorizationHeader() {
        return HttpHeaders.AUTHORIZATION;
    }
}
