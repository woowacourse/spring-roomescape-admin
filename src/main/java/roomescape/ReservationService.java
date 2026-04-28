package roomescape;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<ReservationDto> findAll() {
        return reservationRepository.findAll()
                .stream()
                .map(ReservationDto::from)
                .toList();
    }

    public ReservationDto findById(long id) {
        return ReservationDto.from(reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("id에 해당하는 resrvation이 없습니다. id: " + id)));
    }

    public ReservationDto save(Reservation reservation) {
        return ReservationDto.from(reservationRepository.save(reservation));
    }

    public void delete(long id) {
        reservationRepository.delete(id);
    }
}
