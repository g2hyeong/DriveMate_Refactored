package drivemate.drivemate.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RouteSetRequestDTO {
    @JsonProperty("start_location")
    private CoordinateDTO start;

    @JsonProperty("end_location")
    private CoordinateDTO end;

    public RouteSetRequestDTO(CoordinateDTO start, CoordinateDTO end){
        this.start = start;
        this.end = end;
    }
}
