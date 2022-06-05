package floread.backendapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import floread.backendapi.entities.AppUser;
import floread.backendapi.requestmodel.RegistrationRequest;
import lombok.Getter;

@Getter
@Service
public class RegistrationService {
    
    @Autowired
    AppUserService appUserService;

    private final EmailValidator emailValidator = new EmailValidator();


    public String register(RegistrationRequest request){
        boolean isValidEmail = emailValidator.test(request.getEmail());
        if (!isValidEmail){
            throw new IllegalStateException("Email is not valid");
        }

        return appUserService.signUpUser(new AppUser(
            request.getUsername(),
            request.getEmail(),
            request.getPassword()
        ));
    }
}
