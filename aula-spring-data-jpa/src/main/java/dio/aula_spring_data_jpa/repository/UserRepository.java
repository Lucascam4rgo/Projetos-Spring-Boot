package dio.aula_spring_data_jpa.repository;

import dio.aula_spring_data_jpa.model.User_BD;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User_BD, Integer> {

    //Query Method
    List<User_BD> findBynomeContaining(String nome);

    //Query Method
    User_BD findByUsername(String username);

    //Query Override
    @Query("SELECT u FROM User_BD u WHERE u.nome LIKE %:nome%")
    List<User_BD> filtrarPorNome(@Param("nome") String nome);
}
