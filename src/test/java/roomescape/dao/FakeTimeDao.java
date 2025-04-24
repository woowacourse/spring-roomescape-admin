package roomescape.dao;

import roomescape.entity.ReservationTimeEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class FakeTimeDao implements ReservationTimeDao {
    private final List<ReservationTimeEntity> entities = new ArrayList<>();

    @Override
    public ReservationTimeEntity save(ReservationTimeEntity entity) {
        entities.add(entity);
        return entity;
    }

    @Override
    public List<ReservationTimeEntity> findAll() {
        return Collections.unmodifiableList(entities);
    }

    @Override
    public int deleteById(Long id) {
        if (entities.removeIf(entity -> entity.id().equals(id))) {
            return 1;
        }
        return 0;
    }

    @Override
    public Optional<ReservationTimeEntity> findById(Long id) {
        return entities.stream()
                .filter(entity -> entity.id().equals(id))
                .findFirst();
    }
}
