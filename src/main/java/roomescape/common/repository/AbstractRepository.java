package roomescape.common.repository;

import java.util.List;
import java.util.Optional;
import roomescape.common.domain.Cacheable;

public interface AbstractRepository<T> {
    List<T> getAll();

    T put(T item);

    void deleteById(long id);

    Optional<T> findById(long id);

    Long getCachedId(Cacheable domain);

    void cacheId(Cacheable domain, Long id);
}
