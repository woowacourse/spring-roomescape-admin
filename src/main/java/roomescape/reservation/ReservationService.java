package roomescape.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.reservationtime.ReservationTime;
import roomescape.reservationtime.ReservationTimeRepository;

import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ReservationTimeRepository reservationTimeRepository;

    public Reservation saveReservation(final ReservationDto reservationDto) {
        Long id = reservationRepository.save(reservationDto.getName(), reservationDto.getDate(), reservationDto.getTimeId());
        ReservationTime reservationTime = reservationTimeRepository.findById(reservationDto.getTimeId());
        return new Reservation(id, reservationDto.getName(), reservationDto.getDate(), reservationTime);
    }

    public List<Reservation> getReservationTimes() {
        return reservationRepository.findAll();
    }

    public void deleteReservationTime(Long id) {
        reservationTimeRepository.deleteById(id);
    }
}
