package roomescape.controller;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationCreationRequest;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;

@RestController
public class ReservationController {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationController(ReservationRepository reservationRepository,
            ReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getReservations() {
        return ResponseEntity.ok().body(reservationRepository.findAll());
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(
            @RequestBody ReservationCreationRequest request
    ) {
        // 입력된 예약 시간인 등록된 예약 시간인지 확인
        Optional<ReservationTime> reservationTime = reservationTimeRepository.findById(request.getTimeId());
        if (reservationTime.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        // 이미 지난 예약 날짜와 시간인지 확인
        if (validatePastDateAndTime(request.getDate(), reservationTime.get().getStartAt())) {
            return ResponseEntity.badRequest().build();
        }

        // 이미 예약한 날짜와 시간인지 확인
        if (reservationRepository.findByDateAndTime(request.getDate(), request.getTimeId()).isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        // 예약 추가
        Reservation reservation = Reservation.createWithoutId(
                request.getName(), request.getDate(), reservationTime.get());
        long id = reservationRepository.add(reservation);

        // 추가된 예약 조회
        Optional<Reservation> addedReservation = reservationRepository.findById(id);
        if (addedReservation.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] ID에 해당하는 예약이 존재하지 않습니다.");
        }

        return ResponseEntity
                .created(URI.create("reservations/" + id))
                .body(addedReservation.get());
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        if (reservationRepository.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        reservationRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    private boolean validatePastDateAndTime(LocalDate date, LocalTime time) {
        LocalDateTime dateTime = LocalDateTime.of(date, time);
        LocalDateTime now = LocalDateTime.now();
        return dateTime.isBefore(now);
    }
}
