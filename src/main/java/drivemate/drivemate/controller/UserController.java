package drivemate.drivemate.controller;

import drivemate.drivemate.domain.Title;
import drivemate.drivemate.domain.User;
import drivemate.drivemate.dto.SuccessResponseDTO;
import drivemate.drivemate.dto.userRequest.LoginDTO;
import drivemate.drivemate.dto.userRequest.SignUpDTO;
import drivemate.drivemate.dto.userRequest.UpdateDTO;
import drivemate.drivemate.dto.userResponse.TitleDTO;
import drivemate.drivemate.dto.userResponse.UserRespondDTO;
import drivemate.drivemate.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/login")
    public SuccessResponseDTO logIn(@RequestBody LoginDTO loginDTO){
        SuccessResponseDTO successRespondDTO = new SuccessResponseDTO();
        if (userService.logIn(loginDTO.getUserName(), loginDTO.getPassword())){
            successRespondDTO.setSuccess(true);
            return successRespondDTO;
        }
        else{
            successRespondDTO.setSuccess(false);
            return successRespondDTO;
        }
    }

    @PostMapping("/signup")
    public SuccessResponseDTO signIn(@RequestBody SignUpDTO signUpDTO){
        SuccessResponseDTO successRespondDTO = new SuccessResponseDTO();
        if (signUpDTO.getUserPW().equals(signUpDTO.getConfirmUserPW())) {
            Long id = userService.saveUser(userService.createUser(signUpDTO.getUserName(), signUpDTO.getUserPW(), signUpDTO.getUserNickname()));
            successRespondDTO.setSuccess(id != null);
            return successRespondDTO;
        }
        successRespondDTO.setSuccess(false);
        return successRespondDTO;
    }

    @GetMapping("/info/{username}")
    public UserRespondDTO getUserInfo(@PathVariable String username){
        try {
            User user = userService.findUserByUserName(username);
            UserRespondDTO userRespondDTO = new UserRespondDTO();
            userRespondDTO.setNickname(user.getUserNickname());
            userRespondDTO.setUsername(user.getUserName());
            userRespondDTO.setLevel(user.getLevel());
            userRespondDTO.setExperience(user.getExperience());
            userRespondDTO.setNextLevelExperience(100 - (user.getExperience() % 100));
            userRespondDTO.setMainTitle(user.getMainTitle());
            userRespondDTO.setWeakPoint(user.getTop3WeakPoints());
            for (Title title : user.getTitleList()) {
                if(title.isObtained()){
                    TitleDTO titleDTO = new TitleDTO();
                    titleDTO.setName(title.getName());
                    titleDTO.setDateObtained(title.getObtainedTime());
                    userRespondDTO.addTitleDTO(titleDTO);
                }
            }
            return userRespondDTO;
        } catch (Exception e){
            e.getStackTrace();
            return null;
        }
    }

    @PostMapping("/update/{username}")
    public SuccessResponseDTO updateUser(@PathVariable String username, @RequestBody UpdateDTO userUpdateRequestDTO){
        SuccessResponseDTO successRespondDTO = new SuccessResponseDTO();
        User user = userService.findUserByUserName(username);
        if (user != null) {
            user.updateNicknameAndMainTitle(userUpdateRequestDTO.nickname, userUpdateRequestDTO.mainTitle);
            userService.updateUser(user);
            successRespondDTO.setSuccess(true);
            return successRespondDTO;
        }
        successRespondDTO.setSuccess(false);
        return successRespondDTO;
    }
}
