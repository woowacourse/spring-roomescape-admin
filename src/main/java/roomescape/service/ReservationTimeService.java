package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationTimeRepository;
import roomescape.repository.dto.ReservationTimeSaveDto;
import roomescape.service.dto.ReservationTimeCreateDto;

@Service
public class ReservationTimeService {
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public ReservationTime save(ReservationTimeCreateDto dto) {
        String time = dto.getStartAt();
        ReservationTimeSaveDto repositoryDto = new ReservationTimeSaveDto(dto.getStartAt());
        return reservationTimeRepository.save(repositoryDto);
    }

    public List<ReservationTime> findAll() {
        return reservationTimeRepository.findAll();
    }

    public void deleteById(long id) {
        reservationTimeRepository.delete(id);
    }
}
