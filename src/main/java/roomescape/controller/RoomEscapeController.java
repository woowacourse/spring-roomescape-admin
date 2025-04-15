package roomescape.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
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
import roomescape.repository.ReservationRepository;

@Controller
public class RoomEscapeController {

    private final ReservationRepository repository = new ReservationRepository();

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
        List<Reservation> allReservations = repository.findAll();
        return allReservations.stream().map(ReservationResponse::entityToDto).toList();
    }

    @ResponseBody
    @PostMapping("/reservations")
    public ReservationResponse addReservation(@RequestBody ReservationRequest request) {
        LocalDate date = LocalDate.parse(request.date());
        LocalTime time = LocalTime.parse(request.time());

        LocalDateTime reservationTime = LocalDateTime.of(date, time);
        Reservation newReservation = Reservation.createReservationWithoutID(request.name(), reservationTime);

        Long id = repository.add(newReservation);

        return ReservationResponse.entityToDto(repository.findById(id));
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("id") Long id){
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
