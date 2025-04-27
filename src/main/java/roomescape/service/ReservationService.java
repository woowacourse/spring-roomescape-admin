package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationDate;
import roomescape.domain.ReservationTime;
import roomescape.domain.exception.ReservationException;
import roomescape.domain.exception.ReservationTimeException;
import roomescape.persist.repository.ReservationRepository;
import roomescape.persist.repository.ReservationTimeRepository;
import roomescape.presentation.dto.ReservationRequestDto;
import roomescape.presentation.dto.ReservationResponseDto;
import roomescape.presentation.dto.ReservationTimeResponseDto;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationService(ReservationRepository reservationRepository,
                              ReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @Transactional(readOnly = true)
    public List<ReservationResponseDto> getAllReservations() {
        return reservationRepository.findAll().stream()
                .map(reservation -> new ReservationResponseDto(
                        reservation.getId(),
                        reservation.getName(),
                        reservation.getDate().getStartDate(),
                        new ReservationTimeResponseDto(
                                reservation.getTime().getId(),
                                reservation.getTime().getStartTime()
                        )
                ))
                .toList();
    }

    @Transactional
    public ReservationResponseDto makeReservation(ReservationRequestDto reservationRequestDto) {
        ReservationDate reservationDate = new ReservationDate(reservationRequestDto.date());
        ReservationTime reservationTime = getReservationTime(reservationRequestDto);
        validateReservationDateTimeAvailability(reservationDate, reservationTime);
        Reservation reservation = reservationRepository.add(new Reservation(
                reservationRequestDto.name(),
                reservationDate,
                reservationTime
        ));
        return new ReservationResponseDto(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate().getStartDate(),
                new ReservationTimeResponseDto(
                        reservationTime.getId(),
                        reservationTime.getStartTime()
                )
        );
    }

    private ReservationTime getReservationTime(ReservationRequestDto reservationRequestDto) {
        return reservationTimeRepository.findById(reservationRequestDto.timeId())
                .orElseThrow(() -> new ReservationTimeException("예약 가능한 시간이 존재하지 않습니다."));
    }

    private void validateReservationDateTimeAvailability(ReservationDate reservationDate, ReservationTime reservationTime) {
        if (reservationRepository.isReservationDateTimeTaken(reservationDate, reservationTime)) {
            throw new ReservationException("해당 날짜와 시간에 이미 예약이 존재합니다.");
        }
    }

    @Transactional
    public void cancelReservation(long id) {
        reservationRepository.removeById(id);
    }
}
