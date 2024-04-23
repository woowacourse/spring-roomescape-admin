package roomescape.service;

import java.time.LocalTime;
import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.data.dto.request.ReservationRequest;
import roomescape.data.dto.response.ReservationResponse;
import roomescape.data.vo.Reservation;
import roomescape.data.vo.Reservation.Builder;
import roomescape.data.vo.ReservationTime;
import roomescape.repository.ReservationRepository;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(final ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> getAllReservationQuery() {
        return this.reservationRepository.getAll();
    }

    public long createReservationCommand(final ReservationRequest request) {
        final var reservation = new Builder()
                .name(request.getName())
                .time(new ReservationTime(request.getTimeId(), LocalTime.now()))
                .date(request.getDate())
                .build();

        return this.reservationRepository.add(reservation);
    }

    public void deleteReservationCommand(final long id) {
        reservationRepository.remove(id);
    }

    public ReservationResponse getReservationQuery(final long id) {
        final var reservation = reservationRepository.get(id);
        return new ReservationResponse.Builder()
                .date(reservation.getDate())
                .name(reservation.getName())
                .time(reservation.getTime())
                .id(reservation.getId())
                .build();
    }

}
