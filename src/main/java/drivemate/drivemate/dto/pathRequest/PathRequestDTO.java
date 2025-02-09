package drivemate.drivemate.dto.pathRequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import drivemate.drivemate.dto.CoordinateDTO;
import lombok.Data;

@Data
public class PathRequestDTO {
    @JsonProperty("start_location")
    private CoordinateDTO start;

    @JsonProperty("end_location")
    private CoordinateDTO end;
}
