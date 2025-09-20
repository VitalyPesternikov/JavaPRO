package homework4_7.service;

import homework4_7.repository.UserRepository;
import homework4_7.model.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(String username) {
        userRepository.save(new UserEntity(username));
    }

    public void updateUser(UserEntity userEntity) {
        userRepository.findById(userEntity.getId()).ifPresentOrElse(
                userEntityOptional -> userRepository.save(userEntity),
                () -> {
                    throw new NoSuchElementException("Не найдена запись User c id = " + userEntity.getId());
                }
        );
    }

    public void deleteUser(Long id) {
        userRepository.findById(id).ifPresentOrElse(
                userEntityOptional -> userRepository.deleteById(id),
                () -> {
                    throw new NoSuchElementException("Не найдена запись User c id = " + id);
                }
        );
    }

    public UserEntity getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Не найдена запись User c id = " + id));
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }
}
