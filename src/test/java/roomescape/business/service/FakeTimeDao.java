package roomescape.business.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import roomescape.business.domain.Time;
import roomescape.data.dao.TimeDao;
import roomescape.data.entity.TimeEntity;

public class FakeTimeDao implements TimeDao {

    private final List<TimeEntity> database = new ArrayList<>();

    private int index = 1;

    public FakeTimeDao() {
        final TimeEntity dummy = new TimeEntity(null, null);
        database.add(dummy);
    }

    @Override
    public Long save(final Time time) {
        final TimeEntity temp = TimeEntity.from(time);
        final TimeEntity timeEntity = new TimeEntity(
                (long) index,
                temp.startAt()
        );
        database.add(index, timeEntity);

        return (long) index++;
    }

    @Override
    public Optional<Time> find(final Long id) {
        try {
            final TimeEntity timeEntity = database.get(Math.toIntExact(id));
            return Optional.of(timeEntity.toDomain());
        } catch (IndexOutOfBoundsException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Time> findAll() {
        return database.stream()
                .map(TimeEntity::toDomain)
                .toList();
    }

    @Override
    public int remove(final Long id) {
        try {
            database.remove(database.get(Math.toIntExact(id)));
            index--;
            return 1;
        } catch (IndexOutOfBoundsException e) {
            return 0;
        }
    }
}
