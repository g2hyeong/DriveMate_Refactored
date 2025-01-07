package drivemate.drivemate.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Point")
public class SemiRoutePoint extends SemiRoute{
    private Integer pointIndex;
    private String name;
    private String description;
    private String nextRoadName;
    private Integer turnType;
    private String pointType; // S (start) or E (end)
}
