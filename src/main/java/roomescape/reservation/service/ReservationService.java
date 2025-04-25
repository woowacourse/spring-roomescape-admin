package roomescape.reservation.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import roomescape.reservation.database.ReservationRepository;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.domain.dto.ReservationReqDto;
import roomescape.reservation.domain.dto.ReservationResDto;
import roomescape.globalException.CustomException;
import roomescape.reservationTime.domain.ReservationTime;
import roomescape.reservationTime.domain.dto.ReservationTimeResDto;
import roomescape.reservationTime.repository.ReservationTimeRepository;
import roomescape.reservationTime.service.ReservationTimeService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final ReservationTimeService reservationTimeService;
    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationService(ReservationTimeService reservationTimeService, ReservationRepository reservationRepository, ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeService = reservationTimeService;
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
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
        ReservationTime reservationTime = reservationTimeRepository.findById(dto.timeId());
        return Reservation.of(
                dto.name(),
                dto.date(),
                reservationTime);
    }

    private ReservationResDto convertReservationResDto(Reservation reservation) {
        ReservationTimeResDto reservationTimeResDto = reservationTimeService.convertToReservationTimeResDto(reservation.getReservationTime());
        return new ReservationResDto(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservationTimeResDto
        );
    }
}
