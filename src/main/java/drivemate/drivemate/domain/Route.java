package drivemate.drivemate.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "route_id")
    private Long id;

    /**
     *  엔티티 간 연관관계 매핑
     */
    @OneToMany(mappedBy = "route", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<SemiRoute> semiRouteList = new ArrayList<>();

    /**
     *  엔티티 속성
     */
    private String startLocation;
    private String endLocation;
    private String startLat;
    private String startLon;
    private String endLat;
    private String endLon;
    private Integer totalDistance;
    private Integer totalTime;
}
