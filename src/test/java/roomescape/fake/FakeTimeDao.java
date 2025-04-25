package roomescape.fake;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import roomescape.business.domain.Time;
import roomescape.data.dao.TimeDao;
import roomescape.data.entity.TimeEntity;

public class FakeTimeDao implements TimeDao {

    private final List<TimeEntity> times;

    private int index = 1;

    public FakeTimeDao() {
        this.times = new ArrayList<>();
        final TimeEntity dummy = new TimeEntity(null, null);
        times.add(dummy);
    }

    public FakeTimeDao(final List<TimeEntity> times) {
        this.times = times;
        index += times.size();
        final TimeEntity dummy = new TimeEntity(null, null);
        times.addFirst(dummy);
    }

    @Override
    public Long save(final Time time) {
        final TimeEntity temp = TimeEntity.from(time);
        final TimeEntity timeEntity = new TimeEntity(
                (long) index,
                temp.startAt()
        );
        times.add(index, timeEntity);

        return (long) index++;
    }

    @Override
    public Optional<Time> find(final Long id) {
        try {
            final TimeEntity timeEntity = times.get(Math.toIntExact(id));
            return Optional.of(timeEntity.toDomain());
        } catch (IndexOutOfBoundsException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Time> findAll() {
        return times.stream()
                .filter(timeEntity -> timeEntity.id() != null)
                .map(TimeEntity::toDomain)
                .toList();
    }

    @Override
    public int remove(final Long id) {
        try {
            times.remove(times.get(Math.toIntExact(id)));
            index--;
            return 1;
        } catch (IndexOutOfBoundsException e) {
            return 0;
        }
    }

    public List<TimeEntity> getTimes() {
        return times;
    }
}
