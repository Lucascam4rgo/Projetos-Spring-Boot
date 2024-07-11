package dio.spring_security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import dio.spring_security.user.User_DB;

@Repository
public interface UserRepository extends JpaRepository<User_DB, Integer> {

    @Query("SELECT e FROM User_DB e JOIN FETCH e.roles WHERE e.username= (:username)")
    User_DB findByUsername(@Param("username") String username);
}
