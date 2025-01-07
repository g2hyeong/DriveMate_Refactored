package drivemate.drivemate.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    /**
     *  엔티티 간 연관관계 매핑
     */
    @OneToMany(mappedBy = "users", fetch = FetchType.LAZY)
    private List<DriveReport> driveReportList = new ArrayList<>();

    @OneToMany(mappedBy = "users", fetch = FetchType.LAZY)
    private List<Title> titleList = new ArrayList<>();

    /**
     *  엔티티 속성
     */
    private String userName;
    private String userPW;
    private String userNickname;
    private String mainTitle = ""; // Title 아닌 String으로 Title의 name만 받아온다
    private Integer level = 1;
    private Integer experience = 0;
    private Double avgSightDegree = 0.0;
}
