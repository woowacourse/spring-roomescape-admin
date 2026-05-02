package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.entity.Reservation;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;

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
    public List<ReservationResponseDto> readAll() {
        List<Reservation> reservations = reservationRepository.findAll();
        return reservations.stream()
                .map(reservation -> new ReservationResponseDto(
                        reservation.getId(),
                        reservation.getName(),
                        reservation.getDate(),
                        reservation.getTime())
                )
                .toList();
    }

    @Override
    public ReservationResponseDto reserve(ReservationRequestDto reservationRequestDto) {
        Reservation reservation = reservationRepository.save(new Reservation(
                reservationRequestDto.name(),
                reservationRequestDto.date(),
                reservationTimeRepository.findById(reservationRequestDto.timeId())
        ));
        return new ReservationResponseDto(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );
    }

    @Override
    public void cancel(Long id) {
        reservationRepository.delete(id);
    }
}
