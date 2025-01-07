package drivemate.drivemate.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class DriveReport {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "DriveReport_id")
    private Long id;


    /**
     *  엔티티 간 연관관계 매핑
     */

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private Users users;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id")
    private Route route;

    @OneToOne(fetch = FetchType.LAZY)
    private SurveyTotal surveyTotal;

    @OneToMany(mappedBy = "driveReport", fetch = FetchType.LAZY)
    private List<SurveySemi> surveySemiList = new ArrayList<>();


    /**
     *  엔티티 속성
     */
    private String startLocation;
    private String endLocation;
    private String startTime;
    private String endTime;
}
