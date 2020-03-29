package eflowing.example.cloud.service.resource.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

public class MyRemoteTokenService implements ResourceServerTokenServices {
    protected final Log logger = LogFactory.getLog(this.getClass());
    private RestOperations restTemplate = new RestTemplate();
    private String clientId;
    private String clientSecret;
    private String tokenName = "token";
    private AccessTokenConverter tokenConverter = new DefaultAccessTokenConverter();

    private LoadBalancerClient loadBalancerClient;

    private String authServer;
    private String checkTokenUri;

    public MyRemoteTokenService(LoadBalancerClient balancerClient, String authServer) {
        this(balancerClient,authServer, "oauth/check_token");
    }

    public MyRemoteTokenService(LoadBalancerClient balancerClient, String authServer, String checkTokenUri) {
        this.loadBalancerClient=balancerClient;
        this.authServer = authServer;
        this.checkTokenUri = checkTokenUri;

        ((RestTemplate) this.restTemplate).setErrorHandler(new DefaultResponseErrorHandler() {
            public void handleError(ClientHttpResponse response) throws IOException {
                if (response.getRawStatusCode() != 400) {
                    super.handleError(response);
                }

            }
        });
    }

    private String getCheckTokenEndpointUrl() {
        ServiceInstance serviceInstance = loadBalancerClient.choose(this.authServer);
        return String.format("http://%s:%s/%s", serviceInstance.getHost(), serviceInstance.getPort(), this.checkTokenUri);
    }

    @Override
    public OAuth2Authentication loadAuthentication(String accessToken) throws
            AuthenticationException, InvalidTokenException {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap();
        formData.add(this.tokenName, accessToken);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", this.getAuthorizationHeader(this.clientId, this.clientSecret));
        Map<String, Object> map = this.postForMap(this.getCheckTokenEndpointUrl(), formData, headers);
        if (map.containsKey("error")) {
            this.logger.debug("check_token returned error: " + map.get("error"));
            throw new InvalidTokenException(accessToken);
        } else if (!Boolean.TRUE.equals(map.get("active"))) {
            this.logger.debug("check_token returned active attribute: " + map.get("active"));
            throw new InvalidTokenException(accessToken);
        } else {
            return this.tokenConverter.extractAuthentication(map);
        }
    }

    public void setRestTemplate(RestOperations restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public void setAccessTokenConverter(AccessTokenConverter accessTokenConverter) {
        this.tokenConverter = accessTokenConverter;
    }

    public void setTokenName(String tokenName) {
        this.tokenName = tokenName;
    }

    public OAuth2AccessToken readAccessToken(String accessToken) {
        throw new UnsupportedOperationException("Not supported: read access token");
    }

    private String getAuthorizationHeader(String clientId, String clientSecret) {
        if (clientId == null || clientSecret == null) {
            this.logger.warn("Null Client ID or Client Secret detected. Endpoint that requires authentication will reject request with 401 error.");
        }

        String creds = String.format("%s:%s", clientId, clientSecret);

        try {
            return "Basic " + new String(Base64.encode(creds.getBytes("UTF-8")));
        } catch (UnsupportedEncodingException var5) {
            throw new IllegalStateException("Could not convert String");
        }
    }

    private Map<String, Object> postForMap(String path, MultiValueMap<String, String> formData, HttpHeaders headers) {
        if (headers.getContentType() == null) {
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        }

        Map map = (Map)this.restTemplate.exchange(path, HttpMethod.POST, new HttpEntity(formData, headers), Map.class, new Object[0]).getBody();
        return map;
    }
}
