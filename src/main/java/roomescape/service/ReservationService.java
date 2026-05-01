package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;
import roomescape.repository.dto.ReservationSaveDto;
import roomescape.service.dto.ReservationCreateDto;

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

    public Reservation save(ReservationCreateDto dto) {
        ReservationTime find = reservationTimeRepository.findById(dto.getTimeId())
                .orElseThrow(() -> new IllegalArgumentException("조회된 타임 슬롯이 없습니다."));

        ReservationSaveDto repositoryDto = new ReservationSaveDto(dto.getName(), dto.getDate(), dto.getTimeId());

        return reservationRepository.save(repositoryDto, find);
    }

    public void deleteById(long id) {
        reservationRepository.deleteById(id);
    }
}
