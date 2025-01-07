package drivemate.drivemate.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class SemiRoute {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "SemiRoute_id")
    private Long id;

    /**
     *  엔티티 간 연관관계 매핑
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id")
    private Route route;

    @OneToMany(mappedBy = "semiRoute", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Coordinate> coordinateList = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    private SemiRouteInfo semiRouteInfo;

    /**
     *  엔티티 속성
     */
    private Integer numIndex;
    private String sectionName;
}
