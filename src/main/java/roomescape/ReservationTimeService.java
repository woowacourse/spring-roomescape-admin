package roomescape;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ReservationTimeService {
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public ReservationTime save(ReservationTimeCreateDto reservationTimeCreateDto) {
        String time = reservationTimeCreateDto.getStartAt();
        return reservationTimeRepository.save(time);
    }

    public List<ReservationTime> findAll() {
        return reservationTimeRepository.findAll();
    }

    public void deleteById(long id) {
        reservationTimeRepository.delete(id);
    }
}
