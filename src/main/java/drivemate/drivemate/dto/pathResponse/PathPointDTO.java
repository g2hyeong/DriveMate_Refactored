package drivemate.drivemate.dto.pathResponse;
import drivemate.drivemate.domain.Coordinate;
import lombok.Builder;
import lombok.Data;

@Data
public class PathPointDTO {
    private double lat;
    private double lng;

    @Builder
    public PathPointDTO(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public static PathPointDTO fromCoordinate(Coordinate coordinate){
        return PathPointDTO.builder()
                .lat(coordinate.getLatitude())
                .lng(coordinate.getLongitude())
                .build();
    }
}
