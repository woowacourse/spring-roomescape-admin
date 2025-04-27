package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dto.ReservationRequestDto;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;
import roomescape.repository.JdbcReservationRepository;

@Service
public class ReservationService {
    private final JdbcReservationRepository jdbcReservationRepository;
    private final ReservationTimeService reservationTimeService;

    public ReservationService(JdbcReservationRepository jdbcReservationRepository,
                              ReservationTimeService reservationTimeService) {
        this.jdbcReservationRepository = jdbcReservationRepository;
        this.reservationTimeService = reservationTimeService;
    }

    public List<Reservation> getAllReservations() {
        return jdbcReservationRepository.getAllReservations();
    }

    public Reservation addReservation(ReservationRequestDto reservationRequestDto) {
        ReservationTime reservationTime = reservationTimeService.getReservationTimeById(
                reservationRequestDto.time_id());
        return jdbcReservationRepository.addReservation(reservationRequestDto, reservationTime);
    }

    public Integer deleteReservation(long id) {
        return jdbcReservationRepository.deleteReservation(id);
    }

}
