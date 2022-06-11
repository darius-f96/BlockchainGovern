package floread.backendapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import floread.backendapi.dao.AppUserDAO;
import floread.backendapi.entities.AppUser;
import floread.backendapi.security.AppUserMapper;

@Service
public class AppUserService implements UserDetailsService{

    private final static String USER_NOT_FOUND = "User with username %s not found";
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    AppUserDAO appUserDao;

    @Override
    public UserDetails loadUserByUsername (String username){
        AppUser user = appUserDao.findByUsername(username)
        .orElseThrow( () -> new UsernameNotFoundException(String.format(USER_NOT_FOUND, username)));
        
        return AppUserMapper.userToPrincipal(user);
    }

    public String signUpUser(AppUser appUser){
        boolean userExists = appUserDao.findByEmail(appUser.getEmail()).isPresent();

        if (userExists){
            throw new IllegalStateException ("Email already taken");
        }

        boolean usernameExists = appUserDao.findByUsername(appUser.getUsername()).isPresent();
        if (usernameExists){
            throw new IllegalStateException ("Username already taken");
        }
        String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());
        appUser.setPassword(encodedPassword);

        appUserDao.save(appUser);

        return "it works";
    }
    
}