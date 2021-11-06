package web.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import web.model.Role;
import web.model.User;
import web.services.UserService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;
    public UUserDetailsService() {}




    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userService.getUser(s);
        System.out.println(user.getUsername());
        System.out.println(user.getRoleList());
        System.out.println(user.getPassword());
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(),
                true, true, true, true,
                getAuthorities(user.getRoleList())
        );
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
            List<GrantedAuthority> authorities = new ArrayList<>();
            for (Role role: roles) {
                authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
            }
            return authorities;
        }
}
