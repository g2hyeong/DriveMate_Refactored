package drivemate.drivemate.dto.pathResponse;
import drivemate.drivemate.domain.Coordinate;
import drivemate.drivemate.domain.SemiRoute;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PathSegmentDTO {
    private Integer segmentIndex;
    private Integer distance;
    private Double time;
    private String roadName;
    private String traffic;
    private String roadType;
    private PathPointDTO startPoint;
    private PathPointDTO endPoint;
    private List<PathPointDTO> path = new ArrayList<>();

    @Builder
    public PathSegmentDTO(Integer segmentIndex, Integer distance, Double time, String roadName, String traffic, String roadType) {
        this.segmentIndex = segmentIndex;
        this.distance = distance;
        this.time = time;
        this.roadName = roadName;
        this.traffic = traffic;
        this.roadType = roadType;
    }

    public void addPath(PathPointDTO pathPointDTO){
        this.path.add(pathPointDTO);
    }

    public static PathSegmentDTO fromSemiRoute(SemiRoute semiRoute) {
        PathSegmentDTO pathSegmentDTO = PathSegmentDTO.builder()
                .segmentIndex(semiRoute.getNumIndex())
                .time(semiRoute.getSemiRouteInfo().getTime())
                .distance(semiRoute.getSemiRouteInfo().getDistance())
                .roadType(semiRoute.getSemiRouteInfo().getRoadType())
                .traffic(semiRoute.getSemiRouteInfo().getCongestion())
                .build();
        for (Coordinate coordinate : semiRoute.getCoordinateList()){
            pathSegmentDTO.addPath(PathPointDTO.fromCoordinate(coordinate));
        }
        return pathSegmentDTO;
    }
}
