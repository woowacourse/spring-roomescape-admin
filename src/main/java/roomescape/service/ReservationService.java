package roomescape.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import roomescape.database.ReservationDatabase;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservation.dto.ReservationReqDto;
import roomescape.domain.reservation.dto.ReservationResDto;
import roomescape.exception.CustomException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final ReservationDatabase reservationDatabase;

    public ReservationService(ReservationDatabase reservationDatabase) {
        this.reservationDatabase = reservationDatabase;
    }

    public List<ReservationResDto> readAll() {
        List<Reservation> reservations = reservationDatabase.findAll();
        return reservations.stream()
                .map(this::convertReservationResDto)
                .collect(Collectors.toList());
    }

    public ReservationResDto add(ReservationReqDto dto) {
        Reservation reservation = convertReservation(dto);
        validateDuplicateDateTime(reservation);
        Reservation savedReservation = reservationDatabase.add(reservation);
        return convertReservationResDto(savedReservation);
    }

    public void delete(Long id) {
        reservationDatabase.delete(id);
    }

    private void validateDuplicateDateTime(Reservation inputReservation) {
        List<Reservation> reservations = reservationDatabase.findAll();
        for (Reservation reservation : reservations) {
            if (inputReservation.isSameDateTime(reservation)) {
                throw new CustomException(HttpStatus.CONFLICT, "이미 예약되어 있는 시간입니다.");
            }
        }
    }

    private Reservation convertReservation(ReservationReqDto dto) {
        return Reservation.of(
                dto.name(),
                dto.date(),
                dto.time());
    }

    private ReservationResDto convertReservationResDto(Reservation reservation) {
        return new ReservationResDto(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );
    }
}
