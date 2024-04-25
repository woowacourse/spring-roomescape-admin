package roomescape.reservationtime;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    private ReservationTimeService(final ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReservationTime> getReservationTimes() {
        return reservationTimeRepository.findAll();
    }

    public ReservationTime saveReservationTime(final ReservationTimeRequest reservationTimeRequest) {
        Long id = reservationTimeRepository.save(reservationTimeRequest.getStartAt());
        return new ReservationTime(id, reservationTimeRequest.getStartAt());
    }

    public void deleteReservationTime(Long id) {
        reservationTimeRepository.deleteById(id);
    }
}
