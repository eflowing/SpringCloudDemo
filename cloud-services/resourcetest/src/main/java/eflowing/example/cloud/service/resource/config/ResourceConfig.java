package eflowing.example.cloud.service.resource.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceConfig extends ResourceServerConfigurerAdapter {
    private final LoadBalancerClient balancerClient;

    public ResourceConfig(LoadBalancerClient balancerClient) {
        this.balancerClient = balancerClient;
    }

//    @Autowired
//    private ResourceIgnoreUrls ignoreUrls;
//
//    @Bean
//    protected AccessDecisionManager accessDecisionManager() {
//        WebExpressionVoter wev = new WebExpressionVoter();
//        wev.setExpressionHandler(new OAuth2WebSecurityExpressionHandler());
//        List<AccessDecisionVoter<? extends Object>> voters = Arrays.asList(
////                new ClientScopeVoter(),
////                new ScopeVoter(),
//                new AuthenticatedVoter(),
//                wev
////                ,new RoleVoter()
////                new MyUrlVoter(ignoreUrls.getUrls())
//        );
////        return new UnanimousBased(voters);
//        return new AffirmativeBased((voters));
//    }

    @Bean
    protected MyRemoteTokenService tokenService() {
        MyRemoteTokenService rts = new MyRemoteTokenService(balancerClient,"auth-server");
        rts.setClientId("client1");
        rts.setClientSecret("123456");

        return rts;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources
                .stateless(true)
                .tokenServices(tokenService())
        ;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests().antMatchers("/login").permitAll().and()
                .authorizeRequests().anyRequest().authenticated()
//                .and().authorizeRequests().accessDecisionManager(accessDecisionManager())
        ;
    }
}
