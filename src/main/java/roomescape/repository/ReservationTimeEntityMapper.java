package roomescape.repository;

import org.springframework.stereotype.Component;
import roomescape.domain.ReservationTime;

@Component
public class ReservationTimeEntityMapper {
    public ReservationTimeEntity toReservationTimeEntity(ReservationTime reservationTime) {
        return new ReservationTimeEntity(
                reservationTime.id(),
                reservationTime.startAt()
        );
    }

    public ReservationTime toReservationTime(ReservationTimeEntity entity) {
        validateIdIsNotNull(entity);
        return new ReservationTime(
                entity.id(),
                entity.startAt()
        );
    }

    private void validateIdIsNotNull(ReservationTimeEntity entity) {
        if (entity.id() == null) {
            throw new IllegalStateException("ReservationTimeEntity의 id 가 존재하지 않습니다");
        }
    }
}
