package drivemate.drivemate.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class Coordinate {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    /**
     *  엔티티 간 연관관계 매핑
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "semiRoute_id")
    @JsonIgnore
    private SemiRoute semiRoute;

    /**
     *  엔티티 속성
     */
    private Double longitude;
    private Double latitude;

    @Builder
    public Coordinate(Double longitude, Double latitude){
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Coordinate(){
    }

    public void setSemiRoute(SemiRoute semiRoute) {
        this.semiRoute = semiRoute;
        semiRoute.addCoordinate(this);
    }
}
