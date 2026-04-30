package roomescape.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.entity.Reservation;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    ReservationTimeRepository reservationTimeRepository;

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
