package roomescape.dao;

import roomescape.entity.ReservationEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FakeReservationDao implements ReservationDao {
    private final List<ReservationEntity> entities = new ArrayList<>();

    @Override
    public ReservationEntity save(ReservationEntity entity) {
        entities.add(entity);
        return entity;
    }

    @Override
    public int deleteById(Long id) {
        if (entities.removeIf(entity -> entity.id().equals(id))) {
            return 1;
        }
        return 0;
    }

    @Override
    public List<ReservationEntity> findAll() {
        return Collections.unmodifiableList(entities);
    }
}
