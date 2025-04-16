package roomescape.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;
import roomescape.repository.ReservationRepository;

@RestController
public class RoomescapeController {

    private final ReservationRepository reservationRepository;

    public RoomescapeController(final ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @GetMapping("/reservations")
    public List<ReservationDto> getReservations() {
        return reservationRepository.findAll().stream()
                .map(ReservationDto::toDto)
                .toList();
    }

    record ReservationDto(
            long id,
            String name,
            LocalDate date,
            @JsonFormat(pattern = "HH:mm")
            LocalTime time
    ) {

        public static ReservationDto toDto(final Reservation reservation) {
            return new ReservationDto(reservation.getId(), reservation.getName(), reservation.getDate(),
                    reservation.getTime());
        }
    }

    @PostMapping("/reservations")
    public ReservationDto registerReservation(@RequestBody final ReservationRegisterDto reservationRegisterDto) {
        Reservation reservation = reservationRegisterDto.toEntity();
        this.reservationRepository.save(reservation);
        return ReservationDto.toDto(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable final long id) {
        try {
            reservationRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    record ReservationRegisterDto(
            LocalDate date,
            String name,
            @JsonFormat(pattern = "HH:mm")
            LocalTime time
    ) {
        public Reservation toEntity() {
            return new Reservation(name, date, time);
        }
    }
}
