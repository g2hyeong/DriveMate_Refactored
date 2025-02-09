package drivemate.drivemate.dto.userRequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class LoginDTO {
    @JsonProperty("username")
    private String userName;

    @JsonProperty("pw")
    private String password;

    public LoginDTO(String userName, String password){
        this.userName = userName;
        this.password = password;
    }
}
