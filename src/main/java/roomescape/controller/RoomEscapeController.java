package roomescape.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.dto.request.ReservationRequest;
import roomescape.dto.response.ReservationResponse;
import roomescape.model.Reservation;

@Controller
public class RoomEscapeController {

    private AtomicLong index = new AtomicLong(3);

    private final List<Reservation> reservations = new ArrayList<>(List.of(new Reservation(1L,"브라운", LocalDateTime.of(2024,4,1,10,0)),
            new Reservation(2L,"솔라",LocalDateTime.of(2024,4,1,11,0)),
            new Reservation(3L,"브리",LocalDateTime.of(2024,4,2,14,0))));

    @GetMapping("/admin")
    public String adminPage() {
        return "admin/index";
    }

    @GetMapping("/admin/reservation")
    public String reservationAdminPage(){
        return "admin/reservation-legacy";
    }

    @ResponseBody
    @GetMapping("/reservations")
    public List<ReservationResponse> reservationsJson(){
        return reservations.stream().map(ReservationResponse::entityToDto).toList();
    }

    @ResponseBody
    @PostMapping("/reservations")
    public ReservationResponse addReservation(@RequestBody ReservationRequest request) {
        LocalDate date = LocalDate.parse(request.date());
        LocalTime time = LocalTime.parse(request.time());

        LocalDateTime reservationTime = LocalDateTime.of(date, time);
        Reservation newReservation = new Reservation(index.incrementAndGet(), request.name(), reservationTime);

        reservations.add(newReservation);

        return ReservationResponse.entityToDto(newReservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("id") Long id){
        Reservation findReservation = reservations.stream().filter(reservation -> reservation.sameId(id))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("해당 ID가 존재하지 않습니다."));
        reservations.remove(findReservation);
        return ResponseEntity.ok().build();
    }
}
