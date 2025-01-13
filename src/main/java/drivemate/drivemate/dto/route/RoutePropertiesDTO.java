package drivemate.drivemate.dto.route;

import lombok.Data;

@Data
public class RoutePropertiesDTO {
    private Integer index;
    private Integer pointIndex;
    private Integer lineIndex;
    private String name;
    private String description;
    private Integer distance;
    private Integer time;
    private Integer roadType;
    private Integer facilityType;
    private String nextRoadName;
    private Integer turnType;
    private String pointType;

    private Integer totalDistance;
    private Integer totalTime;
}
