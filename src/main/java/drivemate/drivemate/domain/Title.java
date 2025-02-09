package drivemate.drivemate.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class Title {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "Title_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String name;

    private String obtainedTime = "";

    private boolean isObtained = false;

    public Title(){}

    @Builder
    public Title(String name, String obtainedTime, boolean isObtained) {
        this.name = name;
        this.obtainedTime = obtainedTime;
        this.isObtained = isObtained;
    }

    public void setObtained(String obtainedTime, boolean isObtained){
        this.obtainedTime = obtainedTime;
        this.isObtained = true;
    }
    void setUser(User user){
        this.user = user;
        user.getTitleList().add(this);
    }
}
