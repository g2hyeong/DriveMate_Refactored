package drivemate.drivemate.domain;

import jakarta.persistence.*;

@Entity
public class Coordinate {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    /**
     *  엔티티 간 연관관계 매핑
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "semiRoute_id")
    private SemiRoute semiRoute;

    /**
     *  엔티티 속성
     */
    private double first;
    private double second;
}
