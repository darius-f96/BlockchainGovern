package floread.backendapi.security;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import floread.backendapi.entities.AppUser;

public class AppUserMapper {
    public static AppUser userToPrincipal(AppUser user) {
        AppUser userp = new AppUser();
        List<SimpleGrantedAuthority> authorities = user.getUserRoles().stream()
            .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRoleType().getRoleCode())).collect(Collectors.toList());

        userp.setUsername(user.getUsername());
        userp.setPassword(user.getPassword());
        userp.setEnabled(user.isEnabled());
        userp.setAuthorities(authorities);
        return userp;
    }
    
}
