package eflowing.example.cloud.service.resource.config;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class MyUrlVoter implements AccessDecisionVoter<Object> {
    private List<String> ignoreUrls;

    public MyUrlVoter(List<String> ignoreUrls) {
        this.ignoreUrls = ignoreUrls;
    }

    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    public boolean supports(Class<?> clazz) {
        return true;
    }

    public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {
        if (authentication == null) {
            return -1;
        } else {
            if (!(object instanceof FilterInvocation)) return 0;

            FilterInvocation fi = (FilterInvocation) object;
            // 匹配是否在忽略列表里
            Iterator<String> var6 = ignoreUrls.iterator();
            while (var6.hasNext()) {
                if (new AntPathRequestMatcher(var6.next()).matches(fi.getHttpRequest())) {
                    return 1;
                }
            }

            // 匹配是否在权限列表里
            Collection<? extends GrantedAuthority> authorities = this.extractAuthorities(authentication);
            Iterator var8 = authorities.iterator();
            while (var8.hasNext()) {
                GrantedAuthority authority = (GrantedAuthority) var8.next();
                if (new AntPathRequestMatcher(authority.getAuthority()).matches(fi.getHttpRequest()))
                    return 1;
            }

            return -1;
        }
    }

    Collection<? extends GrantedAuthority> extractAuthorities(Authentication authentication) {
        return authentication.getAuthorities();
    }

}
