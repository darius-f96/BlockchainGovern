package floread.backendapi.security;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import floread.backendapi.services.AppUserService;

public class BChainAuthenticationProvider implements AuthenticationProvider {

  @Autowired
  AppUserService appUserService;
  
    @Override
    public Authentication authenticate(Authentication authentication) 
      throws AuthenticationException {
 
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();
        if (appUserService.loginUser(name, password)){
          return new UsernamePasswordAuthenticationToken(
              name, password, new ArrayList<>());
        }
        else throw new IllegalAccessError();
          
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
