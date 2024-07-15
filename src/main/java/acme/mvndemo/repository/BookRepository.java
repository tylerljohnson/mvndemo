package acme.mvndemo.repository;

import acme.mvndemo.entity.*;
import org.springframework.data.jpa.repository.*;

public interface BookRepository extends JpaRepository<Book, Long> {
}
