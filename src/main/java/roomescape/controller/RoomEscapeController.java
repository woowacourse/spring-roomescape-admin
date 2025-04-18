package roomescape.controller;

import java.util.List;
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

    private final ReservationRepository repository;

    public RoomEscapeController(ReservationRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public String redirectReservationPage() {
        return "redirect:/admin/reservation";
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "admin/index";
    }

    @GetMapping("/admin/reservation")
    public String reservationAdminPage() {
        return "admin/reservation-legacy";
    }

    @ResponseBody
    @GetMapping("/reservations")
    public List<ReservationResponse> getAllReservations() {
        List<Reservation> allReservations = repository.findAll();
        return ReservationResponse.reservationsToDtos(allReservations);
    }

    @ResponseBody
    @PostMapping("/reservations")
    public ReservationResponse addReservation(@RequestBody ReservationRequest request) {
        Long id = repository.add(request.dtoToReservationWithoutId());
        return ReservationResponse.reservationToDto(repository.findById(id));
    }

    @ResponseBody
    @DeleteMapping("/reservations/{id}")
    public void deleteReservation(@PathVariable("id") Long id) {
        repository.deleteById(id);
    }
}
