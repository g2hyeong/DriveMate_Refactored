package drivemate.drivemate.dto.pathResponse;
import drivemate.drivemate.domain.Route;
import drivemate.drivemate.domain.SemiRoute;
import drivemate.drivemate.domain.SemiRouteLineString;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PathRouteDTO {
    private List<PathSegmentDTO> segments = new ArrayList<>();

    @Builder
    public PathRouteDTO() {
    }

    public void addSegments(PathSegmentDTO pathSegmentDTO){
        this.segments.add(pathSegmentDTO);
    }

    public static PathRouteDTO fromRoute(Route route) {
        PathRouteDTO pathRouteDTO = PathRouteDTO.builder().build();
        for (SemiRoute semiRoute : route.getSemiRouteList()){
            if (semiRoute.getClass() == SemiRouteLineString.class) {
                PathSegmentDTO pathSegmentDTO = PathSegmentDTO.fromSemiRoute(semiRoute);
                pathRouteDTO.addSegments(pathSegmentDTO);
            }
        }
        return pathRouteDTO;
    }
}
