package roomescape.domain.reservations.application;

import java.util.List;
import roomescape.domain.reservations.entity.ReservationTime;
import roomescape.domain.reservations.infrastructure.ReservationTimeJdbcTemplateRepository;
import roomescape.domain.reservations.presentation.dto.ReservationTimeRequest;
import roomescape.domain.reservations.presentation.dto.ReservationTimeResponse;

public class ReservationTimeService {

    private final ReservationTimeJdbcTemplateRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeJdbcTemplateRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public ReservationTimeResponse saveTime(ReservationTimeRequest request) {
        ReservationTime reservationTime = ReservationTime.of(
                null,
                request.startAt()
        );
        ReservationTime savedReservationTime = reservationTimeRepository.save(reservationTime);
        return ReservationTimeResponse.from(savedReservationTime);
    }

    public List<ReservationTime> getTimes() {
        return reservationTimeRepository.findAll();
    }

    public void deleteTime(Long id) {
        reservationTimeRepository.deleteById(id);
    }
}
