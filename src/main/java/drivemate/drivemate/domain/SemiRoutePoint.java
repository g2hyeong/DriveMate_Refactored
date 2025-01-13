package drivemate.drivemate.domain;

import drivemate.drivemate.dto.route.RouteFeatureDTO;
import drivemate.drivemate.dto.route.RouteGeometryDTO;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@DiscriminatorValue("Point")
public class SemiRoutePoint extends SemiRoute{
    private Integer pointIndex;
    private String name;
    private String description;
    private String nextRoadName;
    private Integer turnType;
    private String pointType; // S (start) or E (end)

    @Builder
    public SemiRoutePoint(Integer numIndex, String sectionName, Integer pointIndex, String name, String description, String nextRoadName, Integer turnType, String pointType){
        super(numIndex, sectionName);
        this.pointIndex = pointIndex;
        this.name = name;
        this.description = description;
        this.nextRoadName = nextRoadName;
        this.turnType = turnType;
        this.pointType = pointType;
    }

    public SemiRoutePoint(){
    }

    public static SemiRoutePoint fromDTO(RouteFeatureDTO dto){
        RouteGeometryDTO routeGeometryDTO = dto.getGeometry();

        SemiRoutePoint semiRoutePoint = SemiRoutePoint.builder()
                .numIndex(dto.getProperties().getIndex())
                .sectionName("temp for point")
                .pointIndex(dto.getProperties().getPointIndex())
                .name(dto.getProperties().getName())
                .description(dto.getProperties().getDescription())
                .nextRoadName(dto.getProperties().getNextRoadName())
                .turnType(dto.getProperties().getTurnType())
                .pointType(dto.getProperties().getPointType())
                .build();

        if (routeGeometryDTO.getCoordinates() != null) {
            Coordinate coordinate = Coordinate.builder()
                    .latitude((Double) routeGeometryDTO.getCoordinates().get(1))
                    .longitude((Double) routeGeometryDTO.getCoordinates().get(0))
                    .build();
            coordinate.setSemiRoute(semiRoutePoint);
        }
        return semiRoutePoint;
    }
}
