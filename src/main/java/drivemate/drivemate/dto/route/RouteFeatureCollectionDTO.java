package drivemate.drivemate.dto.route;

import lombok.Data;

import java.util.List;

@Data
public class RouteFeatureCollectionDTO {
    private String type;
    private List<RouteFeatureDTO> features;
}
