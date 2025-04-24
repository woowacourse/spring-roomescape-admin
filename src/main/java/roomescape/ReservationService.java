package roomescape;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dto.ReservationRequestDto;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationService(ReservationRepository reservationRepository,
                              ReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.getAllReservations();
    }

    public Reservation addReservation(ReservationRequestDto reservationRequestDto) {
        ReservationTime reservationTime = reservationTimeRepository.getReservationTimeById(
                reservationRequestDto.timeId());
        return reservationRepository.addReservation(reservationRequestDto, reservationTime);
    }

    public Integer deleteReservation(long id) {
        return reservationRepository.deleteReservation(id);
    }

    public ReservationTime addTime(String startAt) {
        return reservationTimeRepository.addTime(startAt);
    }

    public List<ReservationTime> getAllTime() {
        return reservationTimeRepository.getAllTime();
    }

    public Integer deleteTime(Long id) {
        return reservationTimeRepository.deleteTime(id);
    }
}
