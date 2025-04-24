package roomescape.repository;

import java.util.List;

public interface BasicRepository<T> {

    T add(T t);

    T findById(Long id);

    List<T> findAll();

    void removeById(Long id);
}
