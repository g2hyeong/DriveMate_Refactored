package drivemate.drivemate.domain;

import drivemate.drivemate.dto.routeJSON.RouteFeatureDTO;
import drivemate.drivemate.dto.routeJSON.RouteGeometryDTO;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Entity
@Getter
@DiscriminatorValue("LineString")
public class SemiRouteLineString extends SemiRoute{
    private Integer lineIndex;
    private String name;
    private String description;
    private Integer distance;
    private Integer time;
    private Integer roadType;
    private Integer facilityType;

    @Builder
    public SemiRouteLineString(Integer numIndex, String sectionName, Integer lineIndex, String name, String description, Integer distance, Integer time, Integer roadType, Integer facilityType) {
        super(numIndex, sectionName);
        this.lineIndex = lineIndex;
        this.name = name;
        this.description = description;
        this.distance = distance;
        this.time = time;
        this.roadType = roadType;
        this.facilityType = facilityType;
    }

    public SemiRouteLineString() {
    }

    public static SemiRouteLineString fromDTO(RouteFeatureDTO dto){
        RouteGeometryDTO routeGeometryDTO = dto.getGeometry();

        SemiRouteLineString semiRouteLineString = SemiRouteLineString.builder()
                .numIndex(dto.getProperties().getIndex())
                .sectionName("temp for linestring")
                .lineIndex(dto.getProperties().getLineIndex())
                .name(dto.getProperties().getName())
                .description(dto.getProperties().getDescription())
                .distance(dto.getProperties().getDistance())
                .time(dto.getProperties().getTime())
                .roadType(dto.getProperties().getRoadType())
                .facilityType(dto.getProperties().getFacilityType())
                .build();

        if (routeGeometryDTO.getCoordinates() != null) {
            for (Object coord : routeGeometryDTO.getCoordinates()) {
                List<Double> coordinates = (List<Double>) coord;
                Coordinate coordinate = Coordinate.builder()
                        .latitude(coordinates.get(0))
                        .longitude(coordinates.get(1))
                        .build();
                coordinate.setSemiRoute(semiRouteLineString);
            }
        }
        return semiRouteLineString;
    }
}
