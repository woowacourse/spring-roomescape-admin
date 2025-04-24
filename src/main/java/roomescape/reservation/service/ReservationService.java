package roomescape.reservation.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import roomescape.reservation.database.ReservationRepository;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.domain.dto.ReservationReqDto;
import roomescape.reservation.domain.dto.ReservationResDto;
import roomescape.globalException.CustomException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<ReservationResDto> readAll() {
        List<Reservation> reservations = reservationRepository.findAll();
        return reservations.stream()
                .map(this::convertReservationResDto)
                .collect(Collectors.toList());
    }

    public ReservationResDto add(ReservationReqDto dto) {
        Reservation reservation = convertReservation(dto);
        validateDuplicateDateTime(reservation);
        Reservation savedReservation = reservationRepository.add(reservation);
        return convertReservationResDto(savedReservation);
    }

    public void delete(Long id) {
        reservationRepository.delete(id);
    }

    private void validateDuplicateDateTime(Reservation inputReservation) {
        List<Reservation> reservations = reservationRepository.findAll();
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
                reservation.getReservationTime().getStartAt()
        );
    }
}
