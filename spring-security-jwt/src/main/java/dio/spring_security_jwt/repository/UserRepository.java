package dio.spring_security_jwt.repository;

import dio.spring_security_jwt.model.User_DB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User_DB, Integer> {

    @Query ("SELECT e FROM User_DB e JOIN FETCH e.roles WHERE e.username= (:username)")
    User_DB findByUsername(@Param("username") String username);

    boolean existsByUsername(String username);

}
