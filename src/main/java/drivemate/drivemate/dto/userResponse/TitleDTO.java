package drivemate.drivemate.dto.userResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class TitleDTO {
    @JsonProperty("name")
    private String name;

    @JsonProperty("dateObtained")
    private String dateObtained;
}
