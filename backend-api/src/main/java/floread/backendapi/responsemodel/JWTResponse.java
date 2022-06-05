package floread.backendapi.responsemodel;

import java.io.Serializable;

public class JWTResponse implements Serializable{
    
    private static final long serialVersionUID = 1L;
    private final String token;
    public JWTResponse(String token) {
       this.token = token;
    }
    public String getToken() {
       return token;
    }
    
}