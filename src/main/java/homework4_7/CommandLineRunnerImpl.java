package homework4_7;

import homework4_7.model.UserEntity;
import homework4_7.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final UserService userService;

    @Override
    public void run(String... args) {
        userService.createUser("First");
        userService.createUser("Second");
        userService.createUser("Number 3");
        System.out.println(userService.getAllUsers());

        userService.updateUser(new UserEntity(3L, "Third"));
        System.out.println(userService.getAllUsers());

        userService.deleteUser(2L);
        System.out.println(userService.getAllUsers());

        System.out.println(userService.getUserById(1L));
    }
}
