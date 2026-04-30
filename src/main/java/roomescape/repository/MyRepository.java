package roomescape.repository;

import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface MyRepository<T> {
    List<T> findAll();

    T findById(long id);

    T save(T t);

    void delete(long id);
}
