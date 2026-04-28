package roomescape.repository;

import org.springframework.stereotype.Component;
import roomescape.domain.Reservation;

@Component
public class ReservationEntityMapper {
    public ReservationEntity toEntity(Reservation domain) {
        return new ReservationEntity(domain.id(), domain.name(), domain.date(), domain.time());
    }

    public Reservation toDomain(ReservationEntity entity) {
        return new Reservation(
                entity.id(),
                entity.name(),
                entity.date(),
                entity.time()
        );
    }
}
