package res.restau.resres.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor 
@NoArgsConstructor

public class RegisterRequest {
    private String username;
    private String password;
    private String role;  // "ADMIN" ou "CLIENT"

    // getters/setters
}

