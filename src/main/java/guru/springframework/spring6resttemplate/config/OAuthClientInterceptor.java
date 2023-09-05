package guru.springframework.spring6resttemplate.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.registration.ClientRegistration;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import static java.util.Objects.isNull;

public class OAuthClientInterceptor implements ClientHttpRequestInterceptor {
    private final OAuth2AuthorizedClientManager manager;
    private final Authentication principal;
    private final ClientRegistration clientRegistration;

    public OAuthClientInterceptor(OAuth2AuthorizedClientManager manager, Authentication principal, ClientRegistration clientRegistration) {
        this.manager = manager;
        this.principal = principal;
        this.clientRegistration = clientRegistration;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        OAuth2AuthorizeRequest oAuth2AuthorizeRequest = OAuth2AuthorizeRequest
                .withClientRegistrationId(clientRegistration.getClientId())
                .principal(createPrincipal())
                .build();

        OAuth2AuthorizedClient client = manager.authorize(oAuth2AuthorizeRequest);

        if (isNull(client)) {
            throw new IllegalStateException("Missing credentials");
        }
        request.getHeaders()
                    .add(HttpHeaders.AUTHORIZATION, "Bearer " + client.getAccessToken());

        return execution.execute(request, body);
    }

    private Authentication createPrincipal() {
        return new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return Collections.emptySet();
            }

            @Override
            public Object getCredentials() {
                return null;
            }

            @Override
            public Object getDetails() {
                return null;
            }

            @Override
            public Object getPrincipal() {
                return null;
            }

            @Override
            public boolean isAuthenticated() {
                return false;
            }

            @Override
            public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

            }

            @Override
            public String getName() {
                return null;
            }
        };
    }
}
