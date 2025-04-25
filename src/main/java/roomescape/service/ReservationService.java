package roomescape.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.controller.dto.ReservationRegister;
import roomescape.controller.dto.ReservationResponse;
import roomescape.controller.dto.ReservationTimeRegister;
import roomescape.controller.dto.ReservationTimeResponse;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;


    @Autowired
    public ReservationService(final ReservationRepository reservationRepository, final ReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public ReservationResponse saveReservation(final ReservationRegister reservationRegister) {
        final ReservationTime reservationTime = reservationTimeRepository.findById(reservationRegister.timeId())
                .orElseThrow(() -> new IllegalArgumentException("예약 시간이 존재하지 않습니다."));
        final Reservation reservation = Reservation.of(reservationRegister.name(), reservationRegister.date(),
                reservationTime);
        reservation.setId(reservationRepository.save(reservation));
        return ReservationResponse.toDto(reservation);
    }

    public List<ReservationResponse> getAllReservation() {
        final List<Reservation> reservations = reservationRepository.findAll();
        return reservations.stream().map(ReservationResponse::toDto).toList();
    }

    public ReservationTimeResponse saveReservationTime(final ReservationTimeRegister reservationTimeRegister) {
        final ReservationTime reservationTime = ReservationTime.of(reservationTimeRegister.startAt());
        reservationTimeRepository.save(reservationTime);
        return ReservationTimeResponse.toDto(reservationTime);
    }

    public List<ReservationTimeResponse> getAllReservationTime() {
        final List<ReservationTime> reservationTimes = reservationTimeRepository.findAll();
        return reservationTimes.stream()
                .map(ReservationTimeResponse::toDto)
                .toList();
    }

    public void deleteReservationById(final long id) {
        final int result = reservationRepository.deleteById(id);
        validateDelete(result);
    }

    public void deleteReservationTimeById(final long id) {
        final int result = reservationTimeRepository.deleteById(id);
        validateDelete(result);
    }

    private void validateDelete(final int result) {
        if (result == 0) {
            throw new IllegalArgumentException("삭제 실패");
        }
    }
}
