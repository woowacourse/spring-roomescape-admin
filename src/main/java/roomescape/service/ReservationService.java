package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.controller.dto.ReservationCreateRequestDto;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
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

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public Reservation save(ReservationCreateRequestDto reservationCreateRequestDto) {
        ReservationTime find = reservationTimeRepository.findById(reservationCreateRequestDto.getTimeId())
                .orElseThrow(() -> new IllegalArgumentException("조회된 타임 슬롯이 없습니다."));

        return reservationRepository.save(reservationCreateRequestDto, find);
    }

    public void deleteById(Long id) {
        reservationRepository.deleteById(id);
    }
}
