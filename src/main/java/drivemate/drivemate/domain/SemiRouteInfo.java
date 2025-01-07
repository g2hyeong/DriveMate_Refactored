package drivemate.drivemate.domain;

import jakarta.persistence.*;

@Entity
public class SemiRouteInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "semiRouteInfo_id")
    private Long id;

    /**
     *  엔티티 간 연관관계 매핑
     */

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="semiRoute_id")
    private SemiRoute semiRoute;

    /**
     *  엔티티 속성
     */
    private Integer infoIndex;
    private String name;
    private String disturbance;
    private String description;
    private String congestion;
    private String direction;
    private String roadType;
    private Integer distance;
    private Double time;
    private Integer speed;
    private String pointDescription;


}
