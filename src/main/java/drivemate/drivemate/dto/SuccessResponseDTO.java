package drivemate.drivemate.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class SuccessResponseDTO {
    @JsonProperty("success")
    private boolean success;

}
