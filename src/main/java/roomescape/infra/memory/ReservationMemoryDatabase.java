package roomescape.infra.memory;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import roomescape.infra.ReservationDatabase;
import roomescape.infra.ReservationTimeDatabase;
import roomescape.infra.entity.ReservationEntity;
import roomescape.infra.entity.ReservationTimeEntity;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@ConditionalOnProperty(name = "room-escape.console-view.enabled", havingValue = "true")
public class ReservationMemoryDatabase implements ReservationDatabase {

    private record ReservationData(
            long id,
            String name,
            LocalDate date,
            long timeId
    ) {

        public ReservationEntity toEntity(final ReservationTimeDatabase database) {
            final ReservationTimeEntity entity = database.findById(timeId).get();
            return new ReservationEntity(id, name, date, entity);
        }
    }

    private static final Map<Long, ReservationData> DATA = new HashMap<>();

    private final IdGenerator idGenerator;
    private final ReservationTimeDatabase timeDatabase;

    public ReservationMemoryDatabase(
            @Qualifier("reservationTimeMemoryDatabase") final ReservationTimeDatabase timeDatabase,
            final IdGenerator idGenerator
    ) {
        this.timeDatabase = timeDatabase;
        this.idGenerator = idGenerator;
    }

    @Override
    public List<ReservationEntity> findAll() {
        return DATA.values().stream()
                .map(data -> data.toEntity(timeDatabase))
                .toList();
    }

    @Override
    public Optional<ReservationEntity> findById(final long id) {
        final ReservationData reservationData = DATA.get(id);

        if (reservationData == null) {
            return Optional.empty();
        }
        return Optional.of(reservationData.toEntity(timeDatabase));
    }

    @Override
    public long saveAndGetId(final ReservationEntity entity) {
        final long id = idGenerator.get();
        DATA.put(id, new ReservationData(
                id,
                entity.getName(),
                entity.getDate(),
                entity.getTime().getId()
        ));
        return id;
    }

    @Override
    public void deleteById(final long id) {
        DATA.remove(id);
    }
}
