package floread.backendapi.requestmodel;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class CanUseEmailRequest {
    private String email;
    
    public String getEmail(){
        return this.email;
    }
}
