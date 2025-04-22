package roomescape.infra.memory;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import roomescape.business.domain.Customer;
import roomescape.business.domain.Reservation;
import roomescape.business.domain.ReservationTime;
import roomescape.dto.request.ReservationCreateRequest;
import roomescape.infra.ReservationDatabase;
import roomescape.infra.ReservationTimeDatabase;

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

        public Reservation toDomain(ReservationTimeDatabase database) {
            final ReservationTime time = database.findById(timeId).get();
            return new Reservation(id, new Customer(name), date, time);
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
    public List<Reservation> findAll() {
        return DATA.values().stream()
                .map(data -> data.toDomain(timeDatabase))
                .toList();
    }

    @Override
    public Optional<Reservation> findById(final long id) {
        final ReservationData reservationData = DATA.get(id);

        if (reservationData == null) {
            return Optional.empty();
        }
        return Optional.of(reservationData.toDomain(timeDatabase));
    }

    @Override
    public long saveAndGetId(final ReservationCreateRequest request) {
        final long id = idGenerator.get();
        DATA.put(id, new ReservationData(
                id,
                request.name(),
                request.date(),
                request.timeId()
        ));
        return id;
    }

    @Override
    public void deleteById(final long id) {
        DATA.remove(id);
    }
}
