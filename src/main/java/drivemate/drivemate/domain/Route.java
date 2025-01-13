package drivemate.drivemate.domain;

import drivemate.drivemate.dto.route.RouteFeatureCollectionDTO;
import drivemate.drivemate.dto.route.RouteFeatureDTO;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
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
    private Integer totalDistance;
    private Integer totalTime;

    /**
     * 메서드
     */

    public Route() {
    }
    // Setter 대신 Builder 사용
    @Builder
    public Route(Integer totalDistance, Integer totalTime) {
        this.totalDistance = totalDistance;
        this.totalTime = totalTime;
    }

    public void addSemiRoute(SemiRoute semiRoute){
        this.semiRouteList.add(semiRoute);
    }

    public static Route fromDTO(RouteFeatureCollectionDTO dto){
        Route route = Route.builder()
                .totalDistance(dto.getFeatures().get(0).getProperties().getTotalDistance())
                .totalTime(dto.getFeatures().get(0).getProperties().getTotalTime())
                .build();
        for (RouteFeatureDTO routeFeatureDTO : dto.getFeatures()){
            SemiRoute semiRoute = SemiRoute.fromDTO(routeFeatureDTO);
            semiRoute.setRoute(route);
        }

        return route;
    }
}
