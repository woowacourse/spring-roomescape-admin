package roomescape.domain.reservation.controller;

import java.util.List;
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
import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservation.repository.ReservationRepository;
import roomescape.domain.reservation.dto.ReservationRequestDTO;
import roomescape.domain.reservation.dto.ReservationResponseDTO;
import roomescape.domain.time.ReservationTime;
import roomescape.domain.user.User;
import roomescape.domain.time.repository.ReservationTimeRepository;

@Controller
public class ReservationController {
    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationController(ReservationRepository reservationRepository,
                                 ReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/reservations")
    public ReservationResponseDTO create(@RequestBody ReservationRequestDTO requestDTO) {
        ReservationTime time = reservationTimeRepository.findById(requestDTO.getTimeId());
        Reservation reservation = new Reservation(null, new User(null, requestDTO.getName()), requestDTO.getDate(), time);
        Long id = reservationRepository.save(reservation);
        return ReservationResponseDTO.from(
                new Reservation(id, reservation.getUser(), reservation.getDate(), reservation.getTime()));
    }

    @ResponseBody
    @GetMapping("/reservations")
    public List<ReservationResponseDTO> read() {
        return reservationRepository.findAll().stream()
                .map(ReservationResponseDTO::from)
                .collect(Collectors.toList());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/reservations/{id}")
    public void delete(@PathVariable Long id) {
        reservationRepository.delete(id);
    }
}