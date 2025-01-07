package drivemate.drivemate.domain;

import jakarta.persistence.*;

@Entity
public class Title {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "Title_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users users;

    private String name;

    private String obtainedTime = "";

    private boolean isObtained = false;
}
