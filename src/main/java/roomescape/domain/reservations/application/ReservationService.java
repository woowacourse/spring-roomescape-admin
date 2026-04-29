package roomescape.domain.reservations.application;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.reservations.entity.Reservation;
import roomescape.domain.reservations.entity.ReservationTime;
import roomescape.domain.reservations.infrastructure.ReservationJdbcTemplateRepository;
import roomescape.domain.reservations.infrastructure.ReservationTimeJdbcTemplateRepository;
import roomescape.domain.reservations.presentation.dto.ReservationRequest;
import roomescape.domain.reservations.presentation.dto.ReservationResponse;

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

    @Transactional
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
        return ReservationResponse.from(savedReservation);
    }

    public List<Reservation> getReservations() {
        return reservationRepository.findAll();
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }
}
