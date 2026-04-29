package roomescape.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface MyRepository<T> {
    List<T> findAll();

    Optional<T> findById(long id);

    T save(T t);

    void delete(long id);
}
