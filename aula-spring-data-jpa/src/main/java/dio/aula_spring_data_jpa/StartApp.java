package dio.aula_spring_data_jpa;

import dio.aula_spring_data_jpa.model.User_BD;
import dio.aula_spring_data_jpa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StartApp implements CommandLineRunner {
    @Autowired
    private UserRepository repository;

    @Override
    public void run(String... args) throws Exception {

        List<User_BD> users = repository.findBynomeContaining("Lucas");
        for (User_BD u : users) {
            System.out.println(u);
        }

        User_BD username = repository.findByUsername("mosca");
            System.out.println(username);
    }

    private void insertUser() {
        User_BD user = new User_BD();
        user.setNome("Tulio Maravilha");
        user.setUsername("tulius");
        user.setPassword("wonder1");

        repository.save(user);

        for (User_BD u : repository.findAll()) {
            System.out.println(u);
        }
    }
}

