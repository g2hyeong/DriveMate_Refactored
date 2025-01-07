package drivemate.drivemate.domain;
import jakarta.persistence.*;

@Entity
public class SurveyTotal {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="surveyTotal_id")
    private Long id;

    /**
     *  엔티티 간 연관관계 매핑
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driveReport_id")
    private DriveReport driveReport;

    /**
     *  엔티티 속성
     */
    private Integer switchLight;
    private Integer sideMirror;
    private Integer tensionLevel;
    private Integer weather;
    private Integer laneStaying;
    private Integer sightDegree;
    private String memo;
}
