package test.Library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import test.Library.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "select * from books where (user_id is not null) and (status = 'ACTIVE')", nativeQuery = true)
    List<User> getAllUsersWithBooks();

}
