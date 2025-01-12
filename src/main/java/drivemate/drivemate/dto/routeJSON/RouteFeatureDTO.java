package drivemate.drivemate.dto.routeJSON;

import lombok.Data;

@Data
public class RouteFeatureDTO {
    private String type;
    private RouteGeometryDTO geometry;
    private RoutePropertiesDTO properties;
}
