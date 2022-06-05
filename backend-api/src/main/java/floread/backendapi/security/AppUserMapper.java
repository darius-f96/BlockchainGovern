package floread.backendapi.security;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import floread.backendapi.entities.AppUser;

public class AppUserMapper {
    public static AppUser userToPrincipal(AppUser user) {
        AppUser userp = user;
        Collection<?extends GrantedAuthority> authorities = user.getAuthorities();
        // .stream()
        //     .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRoleType().getRoleCode())).collect(Collectors.toList());

        userp.setUsername(user.getUsername());
        userp.setPassword(user.getPassword());
        userp.setEnabled(user.isEnabled());
        userp.setAuthorities(authorities);
        return userp;
    }
    
}
