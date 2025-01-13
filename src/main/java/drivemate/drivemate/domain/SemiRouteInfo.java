package drivemate.drivemate.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import drivemate.drivemate.dto.routeInfo.InfoFeatureCollectionDTO;
import drivemate.drivemate.dto.routeInfo.InfoPropertiesDTO;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class SemiRouteInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "semiRouteInfo_id")
    private Long id;

    /**
     *  엔티티 간 연관관계 매핑
     */

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name="semiRoute_id")
    private SemiRoute semiRoute;

    /**
     *  엔티티 속성
     */
    private String description;
    private String congestion;
    private String direction;
    private String roadType;
    private Integer distance;
    private Double time;
    private Integer speed;


    public void setSemiRoute(SemiRoute semiRoute){
        this.semiRoute = semiRoute;
        semiRoute.setSemiRouteInfo(this);
    }

    public SemiRouteInfo(){
    }

    @Builder
    public SemiRouteInfo(String description, String congestion, String direction, String roadType, Integer distance, Double time, Integer speed) {
        this.description = description;
        this.congestion = congestion;
        this.direction = direction;
        this.roadType = roadType;
        this.distance = distance;
        this.time = time;
        this.speed = speed;
    }

    public static SemiRouteInfo fromDTO(InfoFeatureCollectionDTO dto){
        InfoPropertiesDTO pDto = dto.getFeatures().get(0).getProperties();
        return SemiRouteInfo.builder().congestion(pDto.getCongestion())
                .description(pDto.getDescription())
                .time(pDto.getTime())
                .distance(pDto.getDistance())
                .direction(pDto.getDirection())
                .roadType(pDto.getRoadType())
                .speed(pDto.getSpeed())
                .build();
    }
}
