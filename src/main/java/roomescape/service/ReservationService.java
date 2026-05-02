package roomescape.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;
import roomescape.repository.dto.ReservationSaveDto;
import roomescape.service.dto.ReservationCreateDto;
import roomescape.service.dto.ReservationDto;

@Service
public class ReservationService {
    public static final String TIME_SLOT_DOES_NOT_EXISTS = "조회된 타임 슬롯이 없습니다.";
    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationService(ReservationRepository reservationRepository,
                              ReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReservationDto> findAll() {
        List<Reservation> reservations = reservationRepository.findAll();

        return reservations.stream()
                .map(ReservationDto::toDto)
                .collect(Collectors.toList());
    }

    public ReservationDto save(ReservationCreateDto dto) {
        ReservationTime reservationTime = reservationTimeRepository.findById(dto.getTimeId())
                .orElseThrow(() -> new IllegalArgumentException(TIME_SLOT_DOES_NOT_EXISTS));

        ReservationSaveDto repositoryDto = ReservationSaveDto.toDto(dto, reservationTime);
        Reservation save = reservationRepository.save(repositoryDto);

        return ReservationDto.toDto(save);
    }

    public void deleteById(long id) {
        reservationRepository.deleteById(id);
    }
}
