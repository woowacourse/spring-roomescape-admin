package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dto.ReservationRequestDto;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;
import roomescape.repository.ReservationRepository;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationTimeService reservationTimeService;

    public ReservationService(ReservationRepository reservationRepository,
                              ReservationTimeService reservationTimeService) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeService = reservationTimeService;
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.getAllReservations();
    }

    public Reservation addReservation(ReservationRequestDto reservationRequestDto) {
        ReservationTime reservationTime = reservationTimeService.getReservationTimeById(
                reservationRequestDto.time_id());
        return reservationRepository.addReservation(reservationRequestDto, reservationTime);
    }

    public Integer deleteReservation(long id) {
        return reservationRepository.deleteReservation(id);
    }

}
