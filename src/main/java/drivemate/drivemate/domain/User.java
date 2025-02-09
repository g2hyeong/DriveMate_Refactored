package drivemate.drivemate.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    /**
     *  엔티티 간 연관관계 매핑
     */
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<DriveReport> driveReportList = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
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

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "user_weak_points", joinColumns = @JoinColumn(name = "user_id"))
    @MapKeyColumn(name = "weak_point")
    @Column(name = "value")
    private Map<String, Integer> weakPoints = new HashMap<>(); // 약점 이름과 점수 매핑

    public User(){}

    @Builder
    public User(String userName, String userPW, String userNickname, String mainTitle, Integer level, Integer experience, Double avgSightDegree) {
        this.userName = userName;
        this.userPW = userPW;
        this.userNickname = userNickname;
        this.mainTitle = mainTitle;
        this.level = level;
        this.experience = experience;
        this.avgSightDegree = avgSightDegree;
    }

    public static User createUser(String userName, String userPW, String userNickname) {
        return User.builder()
                .userName(userName)
                .userPW(userPW)
                .userNickname(userNickname)
                .build();
    }

    /**
     * 메서드
     */

    public void setInitialWeakPoint(){
        String[] weakPoint = {"trafficCongestion", "laneStaying", "weather", "sightDegree", "sideMirror", "trafficLaws", "switchLight", "tension"};
        for (String name : weakPoint){
            this.weakPoints.put(name, 0);
        }
    }

    public List<String> getTop3WeakPoints() {
        // weakPoints를 value 기준으로 내림차순 정렬
        return this.weakPoints.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(3) // 상위 3개만 추출
                .map(Map.Entry::getKey) // key 값만 가져옴
                .collect(Collectors.toList()); // List로 반환
    }

    public void setInitialTitle(){
        String[] titleName = {"병아리 운전자", "도로 위 무법자", "평정심", "날씨의 아이", "눈이 두개지요", "사팔뜨기", "준법 시민", "드리블의 귀재", "그대 참치마요"};
        for (String name : titleName) {
            Title.builder()
                    .name(name)
                    .isObtained(false)
                    .obtainedTime("")
                    .build().setUser(this);
        }
    }

    public List<String> updateTitle(){
        String[] weakPoint = {"trafficCongestion", "laneStaying", "weather", "sightDegree", "sideMirror", "trafficLaws", "switchLight", "tension"};
        List<String> obtainedTitleList = new ArrayList<>();
        for (int i=0; i<weakPoint.length; i++) {
            if (this.weakPoints.get(weakPoint[i]) >= 1 && !titleList.get(i+1).isObtained()) {
                titleList.get(i+1).setObtained("", true);
                obtainedTitleList.add(weakPoint[i]);
            }
        }
        return obtainedTitleList;
    }
    public void updateNicknameAndMainTitle(String newNickname, String newMainTitle) {
        this.userNickname = newNickname;
        this.mainTitle = newMainTitle;
    }
    public void updateExperienceByRoute(){
        this.experience += 50;
    }

    public void updateExperienceByCheck(){
        this.experience += 25;
    }

    public void expToLevel(){
        this.level = this.level + this.experience / 100;
        this.experience = this.experience % 100;
    }
}
