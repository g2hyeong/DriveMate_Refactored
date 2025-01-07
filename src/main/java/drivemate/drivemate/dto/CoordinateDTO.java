package drivemate.drivemate.dto;

import lombok.Data;

@Data
public class CoordinateDTO {
    private Double lat;
    private Double lng;

    public CoordinateDTO(Double lat, Double lng){
        this.lat = lat;
        this.lng = lng;
    }
}
