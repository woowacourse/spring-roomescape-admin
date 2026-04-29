package roomescape.domain.reservations.application;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.domain.reservations.entity.Reservation;
import roomescape.domain.reservations.entity.ReservationTime;
import roomescape.domain.reservations.infrastructure.ReservationJdbcTemplateRepository;
import roomescape.domain.reservations.infrastructure.ReservationTimeJdbcTemplateRepository;
import roomescape.domain.reservations.presentation.dto.ReservationRequest;
import roomescape.domain.reservations.presentation.dto.ReservationResponse;
import roomescape.domain.reservations.presentation.dto.ReservationTimeRequest;
import roomescape.domain.reservations.presentation.dto.ReservationTimeResponse;

@Service
public class ReservationService {

    private final ReservationJdbcTemplateRepository reservationRepository;
    private final ReservationTimeJdbcTemplateRepository reservationTimeRepository;

    public ReservationService(
            ReservationJdbcTemplateRepository reservationRepository,
            ReservationTimeJdbcTemplateRepository reservationTimeRepository
    ) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public ReservationResponse saveReservation(ReservationRequest request) {
        ReservationTime time = reservationTimeRepository.findById(request.timeId())
                .orElseThrow(IllegalArgumentException::new);
        Reservation reservation = Reservation.of(
                null,
                request.name(),
                request.date(),
                time
        );
        Reservation savedReservation = reservationRepository.save(reservation);
        return ReservationResponse.from(savedReservation, time);
    }

    public List<Reservation> getReservations() {
        return reservationRepository.findAll();
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
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
