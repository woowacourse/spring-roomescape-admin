package roomescape.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationCreateRequest;
import roomescape.dto.ReservationResponse;
import roomescape.service.ReservationService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class RoomEscapeController {
    private final ReservationService reservationService;

    public RoomEscapeController(ReservationService reservationService){
        this.reservationService = reservationService;
    }

    @GetMapping
    public List<ReservationResponse> findAll(){
        List<Reservation> reservationList = reservationService.findAll();

        List<ReservationResponse> returnDtos = new ArrayList<>();
        for(Reservation reservation : reservationList){
            long id = reservation.getId();
            String name = reservation.getName();
            String date = reservation.getDate();
            String time = reservation.getTime();

            returnDtos.add(new ReservationResponse(id, name, date, time));
        }
        return returnDtos;
    }

    @PostMapping
    public ReservationResponse create(@RequestBody ReservationCreateRequest reservationCreateRequest){
        Reservation reservation = reservationService.create(
                reservationCreateRequest.name(),
                reservationCreateRequest.date(),
                reservationCreateRequest.time()
        );
        return new ReservationResponse(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        reservationService.delete(id);
    }
}
