package floread.backendapi.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import floread.backendapi.dao.AppUserDAO;
import floread.backendapi.entities.AppUser;
import floread.backendapi.requestmodel.CanUseEmailRequest;
import floread.backendapi.requestmodel.CanUseUsernameRequest;
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

    @PostMapping("/signup")
    public Boolean register(@RequestBody RegistrationRequest request){
        try {
            return registrationService.register(request);
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    @PostMapping("/signin")
    public void authenticateUser(@Validated @RequestBody LoginRequest login, HttpServletResponse response) {
        Authentication authentication = authenticationManager
            .authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        try {
            final UserDetails userDetails = appUserService.loadUserByUsername(login.getUsername());

            String jwt = tokenProvider.generateToken(userDetails);
            String refreshJwt = tokenProvider.generateRefreshToken(userDetails);
            response.setHeader("access_token", jwt);
            response.setHeader("refresh_token", refreshJwt);
            response.setStatus(200);
        } catch (Exception e) {
            System.out.println(e);
            response.setStatus(401);
        }
       
    }

    @RequestMapping(value = "/userContext", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> currentUserName(Principal principal) {
        Optional<AppUser> appUser = repository.findByUsername(principal.getName());
        Map<String, Object> response = new HashMap<>();
        if (appUser.isPresent()) {
            response.put("username", appUser.get().getUsername());
            response.put("email", appUser.get().getEmail());
            response.put("appUserId", appUser.get().getAppUserId());
            response.put("persons", appUser.get().getPersons());
            response.put("personWallets", appUser.get().getPersonWallets());
            response.put("companyContractPersons", appUser.get().getCompanyContractPersons());
            response.put("userRoles", appUser.get().getUserRoles());
        }
        if (appUser.isPresent()) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/rfrtkn")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response){
        String reqToken = request.getHeader("Authorization");
        if (reqToken != null && reqToken.startsWith("Bearer ")){
            String token = reqToken.substring(7, reqToken.length());
            if (tokenProvider.validateToken(token)){
                String username = tokenProvider.getUserUsernameFromJWT(token);
                UserDetails userDetails = appUserService.loadUserByUsername(username);
                if (userDetails != null){
                    String newToken = tokenProvider.generateToken(userDetails);
                    response.setHeader("access_token", newToken);
                    response.setHeader("refresh_token", token);
                }
            }
        }
    }

    @PostMapping("/canUseEmail")
    public ResponseEntity<Boolean> canUserEmail(@Validated @RequestBody CanUseEmailRequest request) {
        Optional<AppUser> appUser = repository.findByEmail(request.getEmail());

        if (appUser.isPresent()){
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
    
    @PostMapping("/canUseUsername")
    public ResponseEntity<Boolean> canUseUsername(@Validated @RequestBody CanUseUsernameRequest request) {
        Optional<AppUser> appUser = repository.findByUsername(request.getUsername());

        if (appUser.isPresent()){
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

}