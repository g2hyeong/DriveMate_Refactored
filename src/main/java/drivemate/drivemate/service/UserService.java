package drivemate.drivemate.service;

import drivemate.drivemate.domain.User;
import drivemate.drivemate.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private User currentUser;
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
    public User getCurrentUser() {
        return currentUser;
    }

    public User createUser(String userName, String userPW, String userNickname){
        return User.createUser(userName, userPW, userNickname);
    }

    public User findUserByUserName(String username) { return userRepository.findByUserName(username).get(0);}

    @Transactional
    public Long saveUser(User user){
        validateDuplicateUser(user);
        userRepository.save(user);
        return user.getId();
    }

    @Transactional
    public Long updateUser(User user){
        userRepository.save(user);
        return user.getId();
    }

    private void validateDuplicateUser(User user) {
        List<User> findUsers = userRepository.findByUserName(user.getUserName());
        if(!findUsers.isEmpty())
            throw new IllegalStateException("이미 존재하는 회원입니다.");
    }

    public boolean logIn(String userName, String userPW){
        List<User> findUser = userRepository.findByUserName(userName);
        if(!findUser.isEmpty()){
            if (findUser.get(0).getUserPW().equals(userPW)){
                currentUser = findUser.get(0);
                return true;
            }
        }
        return false;
    }
}
