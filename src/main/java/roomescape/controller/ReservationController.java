package roomescape.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import roomescape.domain.Reservation;
import roomescape.domain.User;
import roomescape.dto.ReservationRequestDTO;
import roomescape.dto.ReservationResponseDTO;

@Controller
public class ReservationController {
    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/reservations")
    public ReservationResponseDTO create(@RequestBody ReservationRequestDTO requestDTO) {
        Long id = index.getAndIncrement();
        Reservation newReservation = new Reservation(id, new User(index.getAndIncrement(), requestDTO.getName()), requestDTO.getDate(), requestDTO.getTime());
        reservations.add(newReservation);
        return ReservationResponseDTO.from(newReservation);
    }

    @ResponseBody
    @GetMapping("/reservations")
    public List<ReservationResponseDTO> read() {
        return reservations.stream()
                .map(ReservationResponseDTO::from)
                .collect(Collectors.toList());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/reservations/{id}")
    public void delete(@PathVariable Long id) {
        Reservation reservationToDelete = reservations.stream()
                .filter(reservation -> Objects.equals(reservation.getId(), id))
                .findFirst()
                .orElseThrow(RuntimeException::new);

        reservations.remove(reservationToDelete);
    }
}