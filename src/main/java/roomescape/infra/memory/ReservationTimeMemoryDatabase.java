package roomescape.infra.memory;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import roomescape.business.domain.ReservationTime;
import roomescape.dto.request.ReservationTimeCreateRequest;
import roomescape.infra.ReservationTimeDatabase;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@ConditionalOnProperty(name = "room-escape.console-view.enabled", havingValue = "true")
public class ReservationTimeMemoryDatabase implements ReservationTimeDatabase {

    private record ReservationTimeData(
            long id,
            LocalTime time
    ) {
        public ReservationTime toDomain() {
            return new ReservationTime(id, time);
        }
    }

    private static final Map<Long, ReservationTimeData> DATA = new HashMap<>();

    private final IdGenerator idGenerator;

    public ReservationTimeMemoryDatabase(final IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    @Override
    public List<ReservationTime> findAll() {
        return DATA.values().stream()
                .map(ReservationTimeData::toDomain)
                .toList();
    }

    @Override
    public Optional<ReservationTime> findById(final long id) {
        final ReservationTimeData reservationTimeData = DATA.get(id);

        if (reservationTimeData == null) {
            return Optional.empty();
        }
        return Optional.of(reservationTimeData.toDomain());
    }

    @Override
    public long saveAndGetId(final ReservationTimeCreateRequest request) {
        final long id = idGenerator.get();
        DATA.put(id, new ReservationTimeData(id, request.startAt()));
        return id;
    }

    @Override
    public void deleteById(final long id) {
        DATA.remove(id);
    }
}
