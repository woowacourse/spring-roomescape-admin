package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.domain.ReservationTime;
import roomescape.dto.CreateReservationTimeDto;
import roomescape.dto.ReservationTimeDto;
import roomescape.repository.ReservationTimeRepository;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReservationTimeDto> findAllReservationTime() {
        return reservationTimeRepository.findAll()
                .stream()
                .map(ReservationTimeDto::from)
                .toList();
    }

    public ReservationTimeDto createReservationTime(CreateReservationTimeDto createReservationTimeDto) {
        ReservationTime savedReservationTime = reservationTimeRepository.add(
                createReservationTimeDto.toReservationTime());
        return ReservationTimeDto.from(savedReservationTime);
    }

    public void deleteReservationTime(Long id) {
        reservationTimeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 시간입니다."));
        reservationTimeRepository.deleteById(id);
    }
}
