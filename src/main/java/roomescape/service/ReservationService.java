package roomescape.service;

import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;
import roomescape.Repository.ReservationRepository;
import roomescape.Repository.ReservationTimeRepository;
import roomescape.controller.dto.response.ReservationResponse;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservationtime.ReservationTime;
import roomescape.service.dto.ReservationCreation;

@Service
public class ReservationService {

    private static final String ERROR_SIGN = "[ERROR] ";
    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationService(ReservationRepository reservationRepository,
                              ReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public ReservationResponse create(final ReservationCreation creation) {
        final ReservationTime time = reservationTimeRepository.findBy(creation.timeId());
        final Reservation reservation = Reservation.from(creation, time);

        Long id = reservationRepository.insert(reservation);
        Reservation insertedReservation = reservationRepository.findBy(id);

        return ReservationResponse.from(insertedReservation);
    }

    public List<ReservationResponse> readAll() {
        List<Reservation> reservations = reservationRepository.findAll();
        return reservations.stream()
                .map(ReservationResponse::from)
                .toList();
    }

    public void deleteBy(final Long id) {
        if (!reservationRepository.deleteBy(id)) {
            throw new NoSuchElementException(ERROR_SIGN + "해당 ID의 예약을 찾을 수 없습니다. id:" + id);
        }
    }
}
