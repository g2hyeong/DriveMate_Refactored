package drivemate.drivemate.dto.routeInfo;

import lombok.Data;

@Data
public class InfoPropertiesDTO {
    private String description;
    private String congestion;
    private String direction;
    private String roadType;
    private Integer distance;
    private Double time;
    private Integer speed;
}
