package drivemate.drivemate.dto.userResponse;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserRespondDTO {
    private String nickname;
    private String username;
    private String mainTitle;
    private Integer level;
    private Integer experience;
    private Integer nextLevelExperience;
    private List<String> weakPoint;
    private List<TitleDTO> titles = new ArrayList<>();

    public void addTitleDTO(TitleDTO titleDTO){
        this.titles.add(titleDTO);
    }
}
