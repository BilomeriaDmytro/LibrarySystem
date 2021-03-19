package test.Library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import test.Library.model.Book;
import test.Library.model.EntityStatus;
import test.Library.model.User;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long>{

    List<Book> getByUserAndStatus(User user, EntityStatus entityStatus);

    List<Book> findByStatus(EntityStatus entityStatus);
}
