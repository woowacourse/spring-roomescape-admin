package roomescape.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.domain.Reservation;
import roomescape.repository.ReservationRepository;

@Controller
public class RoomescapeController {

    private final ReservationRepository reservationRepository;

    public RoomescapeController(final ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

//    @GetMapping("/admin")
//    public String roomescape() {
//        return "admin/index";
//    }


    @GetMapping("/admin/reservation")
    public String reservation() {
        return "/admin/reservation-legacy";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<ReservationDto> getReservations() {
//        reservationRepository.findAll();
        List<ReservationDto> reservations = new ArrayList<>();
        final List<ReservationDto> temp = List.of(
                ReservationDto.toDto(new Reservation(1L, "윌슨", LocalDate.now(), LocalTime.of(12, 0))),
                ReservationDto.toDto(new Reservation(2L, "히로", LocalDate.of(2025, 4, 17), LocalTime.of(11, 0))));
        reservations.addAll(temp);
        return reservations;
    }

    record ReservationDto(
            long id,
            String name,
            LocalDate date,
            @JsonFormat(pattern = "HH:mm")
            LocalTime time
    ) {

        public static ReservationDto toDto(final Reservation reservation) {
            return new ReservationDto(reservation.getId(), reservation.getName(), reservation.getDate(), reservation.getTime());
        }
    }
}
