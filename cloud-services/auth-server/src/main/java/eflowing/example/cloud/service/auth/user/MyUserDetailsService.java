package eflowing.example.cloud.service.auth.user;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class MyUserDetailsService implements UserDetailsService {
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private Map<String, UserDetails> consoleUsers = new HashMap<>();
    private Map<String, UserDetails> appUsers = new HashMap<>();

    public MyUserDetailsService() {
        consoleUsers.put("user", User.withUsername("user")
                .password(passwordEncoder.encode("123"))
//                .authorities(Arrays.asList(new SimpleGrantedAuthority("/ws/**"))).build())
                .authorities(Arrays.asList(new SimpleGrantedAuthority("name"))).build())
        ;
        appUsers.put("user", User.withUsername("user")
                .password(passwordEncoder.encode("456"))
                .authorities(Arrays.asList(new SimpleGrantedAuthority("name")
                        , new SimpleGrantedAuthority("hello"))).build())
//                .authorities(Arrays.asList(new SimpleGrantedAuthority("/ws/hello"))).build())
        ;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String[] paras = username.split("\\|");
        if (paras.length == 2) {
            return getUserDetails(paras[0], paras[1]);
        } else {
            throw new UsernameNotFoundException(String.format("未找到用户！用户名参数错误，username=%s", username));
        }
    }

    private UserDetails getUserDetails(String userType, String username) {
        UserDetails tuser;
        UserDetails user;
        switch (userType) {
            case "console":
                tuser = consoleUsers.get(username);
                user = User.withUsername(tuser.getUsername()).password(tuser.getPassword()).authorities(tuser.getAuthorities()).build();
                return user;
            case "app":
                tuser = appUsers.get(username);
                user = User.withUsername(tuser.getUsername()).password(tuser.getPassword()).authorities(tuser.getAuthorities()).build();
                return user;
            default:
                throw new UsernameNotFoundException(String.format("用户类型错误！user_type=%s", userType));
        }
    }
}
