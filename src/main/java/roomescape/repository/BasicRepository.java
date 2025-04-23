package roomescape.repository;

import java.util.List;

public interface BasicRepository<T> {

    List<T> findAll();

    T add(T t);

    void removeById(Long id);
}
