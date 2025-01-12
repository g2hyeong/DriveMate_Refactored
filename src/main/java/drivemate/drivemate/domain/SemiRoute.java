package drivemate.drivemate.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import drivemate.drivemate.dto.routeJSON.RouteFeatureDTO;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
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
    @JsonIgnore
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

    public SemiRoute(Integer numIndex, String sectionName) {
        this.numIndex = numIndex;
        this.sectionName = sectionName;
    }

    public SemiRoute() {
    }

    /**
     *  메서드
     */

    public static SemiRoute fromDTO(RouteFeatureDTO dto){
        if ("LineString".equals(dto.getGeometry().getType())) {
            return SemiRouteLineString.fromDTO(dto);
        } else if ("Point".equals(dto.getGeometry().getType())) {
            return SemiRoutePoint.fromDTO(dto);
        }
        throw new IllegalArgumentException("Unknown SemiRoute type: " + dto.getGeometry().getType());
    }

    public void setRoute(Route route) {
        this.route = route;
        route.addSemiRoute(this);
    }

    public void addCoordinate(Coordinate coordinate){
        this.coordinateList.add(coordinate);
    }

}
