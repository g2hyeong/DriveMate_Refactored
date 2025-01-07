package drivemate.drivemate.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("LineString")
public class SemiRouteLineString extends SemiRoute{
    private Integer lineIndex;
    private String name;
    private String description;
    private Integer distance;
    private Double time;
    private Integer roadType;
    private Integer facilityType;
}
