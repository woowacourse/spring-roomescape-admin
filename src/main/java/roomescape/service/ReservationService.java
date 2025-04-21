package roomescape.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationCreationRequest;
import roomescape.exception.BadRequestException;
import roomescape.exception.NotFoundException;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationService(ReservationRepository reservationRepository,
            ReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation getById(long id) {
        return loadReservationById(id);
    }

    public long saveReservation(ReservationCreationRequest request) {
        ReservationTime reservationTime = loadReservationTimeById(request.getTimeId());
        validatePastDateAndTime(request.getDate(), reservationTime.getStartAt());
        validateAlreadyReserve(request.getDate(), reservationTime.getId());

        Reservation reservation = Reservation.createWithoutId(
                request.getName(), request.getDate(), reservationTime);
        return reservationRepository.add(reservation);
    }

    public void deleteReservation(long id) {
        loadReservationById(id);
        reservationRepository.deleteById(id);
    }

    private Reservation loadReservationById(long reservationId) {
        Optional<Reservation> reservation = reservationRepository.findById(reservationId);
        return reservation
                .orElseThrow(() -> new NotFoundException("[ERROR] ID에 해당하는 예약이 존재하지 않습니다."));
    }

    private ReservationTime loadReservationTimeById(long reservationTimeId) {
        Optional<ReservationTime> reservationTime = reservationTimeRepository.findById(reservationTimeId);
        return reservationTime
                .orElseThrow(() -> new NotFoundException("[ERROR] ID에 해당하는 예약시간이 존재하지 않습니다."));
    }

    private void validatePastDateAndTime(LocalDate date, LocalTime time) {
        LocalDateTime dateTime = LocalDateTime.of(date, time);
        LocalDateTime now = LocalDateTime.now();
        if (dateTime.isBefore(now)) {
            throw new BadRequestException("[ERROR] 이미 과거의 날짜와 시간입니다.");
        }
    }

    private void validateAlreadyReserve(LocalDate reservationDate, long reservationTimeId) {
        Optional<Reservation> reservation =
                reservationRepository.findByDateAndTime(reservationDate, reservationTimeId);
        if (reservation.isPresent()) {
            throw new BadRequestException("[ERROR] 이미 예약이 완료된 날짜와 시간입니다.");
        }
    }
}
