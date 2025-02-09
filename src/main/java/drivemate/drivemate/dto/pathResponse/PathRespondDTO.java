package drivemate.drivemate.dto.pathResponse;
import drivemate.drivemate.domain.Route;
import lombok.Builder;
import lombok.Data;

@Data
public class PathRespondDTO {
    private Integer totalDistance;
    private Integer totalTime;
    private PathRouteDTO route;

    public PathRespondDTO(){}

    @Builder
    public PathRespondDTO(Integer totalDistance, Integer totalTime) {
        this.totalDistance = totalDistance;
        this.totalTime = totalTime;
    }

    public static PathRespondDTO fromRoute(Route route){
        PathRespondDTO pathRespondDTO = PathRespondDTO.builder()
                .totalDistance(route.getTotalDistance())
                .totalTime(route.getTotalTime())
                .build();
        pathRespondDTO.setRoute(PathRouteDTO.fromRoute(route));
        return pathRespondDTO;
    }
}
