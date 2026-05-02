package roomescape.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationTimeRepository;
import roomescape.repository.dto.ReservationTimeSaveDto;
import roomescape.service.dto.ReservationTimeCreateDto;
import roomescape.service.dto.ReservationTimeDto;

@Service
public class ReservationTimeService {
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public ReservationTimeDto save(ReservationTimeCreateDto dto) {
        ReservationTimeSaveDto repositoryDto = ReservationTimeSaveDto.toDto(dto);

        ReservationTime save = reservationTimeRepository.save(repositoryDto);

        return ReservationTimeDto.toDto(save);
    }

    public List<ReservationTimeDto> findAll() {
        List<ReservationTime> reservationTimes = reservationTimeRepository.findAll();

        return reservationTimes.stream()
                .map(ReservationTimeDto::toDto)
                .collect(Collectors.toList());
    }

    public void deleteById(long id) {
        reservationTimeRepository.delete(id);
    }
}
