package roomescape.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Component;
import roomescape.domain.Reservation;

@Component
public class ReservationRepository {

    private static AtomicLong id = new AtomicLong(1);
    private static final List<ReservationEntity> reservations = new ArrayList<>();

    private final ReservationEntityMapper mapper;

    public ReservationRepository(ReservationEntityMapper mapper) {
        this.mapper = mapper;
    }

    public List<Reservation> findAll() {
        return reservations.stream()
                .map(mapper::toDomain)
                .toList();
    }

    public Reservation save(Reservation reservation) {
        ReservationEntity noIdEntity = mapper.toEntity(reservation);
        ReservationEntity withIdEntity = noIdEntity.initializeId(id.getAndAdd(1));

        reservations.add(withIdEntity);
        return mapper.toDomain(withIdEntity);
    }
}
