package roomescape.repository;

import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface CommonRepository<T> {
    List<T> findAll();

    T findById(long id);

    T save(T t);

    void delete(long id);

    boolean isExists(long id);
}
