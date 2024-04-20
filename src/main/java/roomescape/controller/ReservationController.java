package roomescape.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.controller.dto.ReservationResponse;
import roomescape.domain.Reservation;
import roomescape.controller.dto.ReservationRequest;
import roomescape.repository.ReservationStore;

@RestController
public class ReservationController {
    private final ReservationStore reservationStore = new ReservationStore();

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> createReservation(@RequestBody ReservationRequest reservationRequest) {
        Reservation newReservation = new Reservation(reservationRequest.name(), reservationRequest.date(), reservationRequest.time());

        reservationStore.save(newReservation);

        return ResponseEntity.ok(new ReservationResponse(1, newReservation.getName(), newReservation.getDate(), newReservation.getTime()));
    }

    @GetMapping("/reservations")
    @ResponseStatus(HttpStatus.OK)
    public List<ReservationResponse> readReservations() {
        List<ReservationResponse> reservationInfo = new ArrayList<>();

        System.out.println("뭐지");
        for(Map.Entry<Long, Reservation> reservationEntry : reservationStore.getAll().entrySet()) {
            Reservation reservation = reservationEntry.getValue();
            reservationInfo.add(new ReservationResponse(
                    reservationEntry.getKey(),
                    reservation.getName(),
                    reservation.getDate(),
                    reservation.getTime())
            );
        }

        for (ReservationResponse responseDto : reservationInfo) {
            System.out.println("테스트: " +  responseDto.getTime());
        }

        return reservationInfo;
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationStore.delete(id);

        return ResponseEntity.ok().build();
    }
}
