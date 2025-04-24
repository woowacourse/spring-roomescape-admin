package roomescape.common.repository;

import java.util.List;
import java.util.Optional;

public interface AbstractRepository<T> {
    List<T> getAll();

    T put(T item);

    void deleteById(long id);

    Optional<T> findById(long id);

    Long getCachedId(T item);

    void cacheId(T item, Long id);
}
