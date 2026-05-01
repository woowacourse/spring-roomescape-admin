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
    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationService(ReservationRepository reservationRepository,
                              ReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReservationDto> findAll() {
        List<Reservation> reservations = reservationRepository.findAll();

        List<ReservationDto> response = reservations.stream()
                .map(reservation -> new ReservationDto(reservation.getId(), reservation.getName(),
                        reservation.getDate(), reservation.getTime().getId()))
                .collect(Collectors.toList());
        return response;
    }

    public ReservationDto save(ReservationCreateDto dto) {
        ReservationTime find = reservationTimeRepository.findById(dto.getTimeId())
                .orElseThrow(() -> new IllegalArgumentException("조회된 타임 슬롯이 없습니다."));

        ReservationSaveDto repositoryDto = new ReservationSaveDto(dto.getName(), dto.getDate(), dto.getTimeId());
        Reservation save = reservationRepository.save(repositoryDto, find);

        return new ReservationDto(save.getId(), save.getName(), save.getDate(), save.getTime().getId());
    }

    public void deleteById(long id) {
        reservationRepository.deleteById(id);
    }
}
