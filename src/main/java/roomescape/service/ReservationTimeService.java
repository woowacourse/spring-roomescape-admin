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
        ReservationTimeSaveDto repositoryDto = new ReservationTimeSaveDto(dto.getStartAt());

        ReservationTime save = reservationTimeRepository.save(repositoryDto);
        return new ReservationTimeDto(save.getId(), save.getStartAt().toString());
    }

    public List<ReservationTimeDto> findAll() {
        List<ReservationTime> found = reservationTimeRepository.findAll();
        List<ReservationTimeDto> response = found.stream()
                .map(reservationTime -> new ReservationTimeDto(reservationTime.getId(),
                        reservationTime.getStartAt().toString()))
                .collect(Collectors.toList());
        return response;
    }

    public void deleteById(long id) {
        reservationTimeRepository.delete(id);
    }
}
