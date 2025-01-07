package drivemate.drivemate.domain;

import jakarta.persistence.*;

@Entity
public class SurveySemi {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    /**
     *  엔티티 간 연관관계 매핑
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driveReport_id")
    private DriveReport driveReport;


    /**
     *  엔티티 속성
     */
    private Boolean laneSwitch;
    private Boolean laneConfusion;
    private Boolean tension;
    private Boolean trafficLaws;
    private Boolean situationDecision;
    private Boolean trafficCongestion;
    private Boolean roadType;
}
