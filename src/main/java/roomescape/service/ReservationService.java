package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationDate;
import roomescape.domain.ReservationTime;
import roomescape.persist.repository.ReservationRepository;
import roomescape.persist.repository.ReservationTimeRepository;
import roomescape.presentation.dto.ReservationRequestDto;
import roomescape.presentation.dto.ReservationResponseDto;
import roomescape.presentation.dto.ReservationTimeResponseDto;

@Service
public final class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationService(ReservationRepository reservationRepository,
                              ReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReservationResponseDto> getAllReservations() {
        return reservationRepository.findAll().stream()
                .map(reservation -> new ReservationResponseDto(
                        reservation.getId(),
                        reservation.getName(),
                        reservation.getDate().getStartDate(),
                        new ReservationTimeResponseDto(reservation.getTime().getId(),
                                reservation.getTime().getStartTime())))
                .toList();
    }

    public ReservationResponseDto makeReservation(ReservationRequestDto reservationRequestDto) {
        ReservationDate reservationDate = new ReservationDate(reservationRequestDto.date());
        ReservationTime reservationTime = reservationTimeRepository.findById(reservationRequestDto.timeId());
        Reservation reservation = reservationRepository.add(new Reservation(
                reservationRequestDto.name(),
                reservationDate,
                reservationTime
        ));
        return new ReservationResponseDto(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate().getStartDate(),
                new ReservationTimeResponseDto(reservationTime.getId(),
                        reservationTime.getStartTime()));
    }

    public void cancelReservation(long id) {
        reservationRepository.removeById(id);
    }
}
