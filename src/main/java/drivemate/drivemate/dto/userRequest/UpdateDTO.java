package drivemate.drivemate.dto.userRequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class UpdateDTO {
    public String nickname;
    public String mainTitle;
}
