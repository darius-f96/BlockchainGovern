package floread.backendapi.requestmodel;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class CanUseUsernameRequest {
    private String username;

    public String getUsername(){
        return this.username;
    }
}
