package roomescape;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationService(ReservationRepository reservationRepository,
                              ReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public Reservation save(ReservationCreateDto reservationCreateDto) {
        ReservationTime find = reservationTimeRepository.findById(reservationCreateDto.getTimeId())
                .orElseThrow(() -> new IllegalArgumentException("조회된 타임 슬롯이 없습니다."));

        return reservationRepository.save(reservationCreateDto, find);
    }

    public void deleteById(Long id) {
        reservationRepository.deleteById(id);
    }
}
