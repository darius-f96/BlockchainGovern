package floread.backendapi.controller;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import floread.backendapi.dao.AppUserDAO;
import floread.backendapi.entities.AppUser;
import floread.backendapi.requestmodel.LoginRequest;
import floread.backendapi.requestmodel.RegistrationRequest;
import floread.backendapi.responsemodel.JWTResponse;
import floread.backendapi.security.BChainAuthenticationProvider;
import floread.backendapi.security.JWTProvider;
import floread.backendapi.services.AppUserService;
import floread.backendapi.services.RegistrationService;

@RestController
@RequestMapping(value = "/appUser", method = {RequestMethod.POST})
@CrossOrigin
class AppUserController {

    @Autowired
    AppUserDAO repository;

    @Autowired
    RegistrationService registrationService;

    @Autowired
    BChainAuthenticationProvider authenticationManager;
    @Autowired
    JWTProvider tokenProvider;

    @Autowired
    AppUserService appUserService;

    @GetMapping
    public String register(@RequestBody RegistrationRequest request){
        return registrationService.register(request);
    }

    @PostMapping("/signin")
    public ResponseEntity<JWTResponse> authenticateUser(@Validated @RequestBody LoginRequest login) {
        Authentication authentication = authenticationManager
            .authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        final UserDetails userDetails = appUserService.loadUserByUsername(login.getUsername());

        String jwt = tokenProvider.generateToken(userDetails);
        
        return ResponseEntity.ok(new JWTResponse(jwt));
    }

    @GetMapping("login")
    public String getLogin(){
        return "login";
    }

}