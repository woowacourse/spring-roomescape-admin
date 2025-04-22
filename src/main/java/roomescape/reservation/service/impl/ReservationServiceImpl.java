package roomescape.reservation.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.reservation.dto.ReservationRequestDto;
import roomescape.reservation.dto.ReservationResponseDto;
import roomescape.reservation.entity.Reservation;
import roomescape.reservation.entity.ReservationTime;
import roomescape.common.exception.EntityNotFoundException;
import roomescape.reservation.repository.ReservationRepository;
import roomescape.reservation.repository.ReservationTimeRepository;
import roomescape.reservation.service.ReservationService;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository,
                                  ReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @Override
    public List<ReservationResponseDto> getAll() {
        List<Reservation> reservations = reservationRepository.findAll();

        return reservations.stream()
                .map(ReservationResponseDto::toDto)
                .toList();
    }

    @Override
    public ReservationResponseDto save(ReservationRequestDto requestDto) {
        Long timeId = requestDto.timeId();
        ReservationTime reservationTime = reservationTimeRepository.findById(timeId)
                .orElseThrow(() -> new EntityNotFoundException("reservationsTime not found id =" + timeId));

        Reservation reservation = Reservation.withoutId(requestDto.name(), requestDto.date(), reservationTime);

        Reservation saved = reservationRepository.save(reservation);

        return ReservationResponseDto.toDto(saved);
    }

    @Override
    public void delete(Long id){
        reservationRepository.deleteById(id);
    }
}
