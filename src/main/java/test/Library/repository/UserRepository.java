package test.Library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import test.Library.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
