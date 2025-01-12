package drivemate.drivemate.dto.routeJSON;

import lombok.Data;

import java.util.List;

@Data
public class RouteGeometryDTO {
    private String type;
    private List<Object> coordinates; // Object를 사용하는 것도 나쁜 방법은 아니지 않을까..
}
