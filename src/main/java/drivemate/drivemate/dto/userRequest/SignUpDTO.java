package drivemate.drivemate.dto.userRequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class SignUpDTO {
    @JsonProperty("nickname")
    private String userNickname;

    @JsonProperty("username")
    private String userName;

    @JsonProperty("pw")
    private String userPW;

    @JsonProperty("confirmPw")
    private String confirmUserPW;
}
