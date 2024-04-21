package roomescape.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationResponse;
import roomescape.dto.ReservationSaveRequest;
import roomescape.mapper.ReservationMapper;
import roomescape.repository.ReservationDao;
import roomescape.repository.TimeDao;

import java.net.URI;
import java.util.List;

@Controller
public class ReservationController {

    private final ReservationMapper reservationMapper = new ReservationMapper();

    @Autowired
    private final ReservationDao reservationDao;

    @Autowired
    private final TimeDao timeDao;

    public ReservationController(ReservationDao reservationDao, TimeDao timeDao) {
        this.reservationDao = reservationDao;
        this.timeDao = timeDao;
    }

    @GetMapping("/reservation")
    public String getAdminReservations() {
        return "/admin/reservation";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponse>> getReservations() {
        List<Reservation> reservations = reservationDao.findAll();
        List<ReservationResponse> responses = reservations.stream()
                .map(reservationMapper::mapToResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> createReservation(@RequestBody ReservationSaveRequest request) {
        ReservationTime time = timeDao.findById(request.timeId());
        Reservation reservation = reservationMapper.mapToReservation(request, time);
        long saveId = reservationDao.save(reservation);

        ReservationResponse response = reservationMapper.mapToResponse(saveId, reservation);
        URI location = URI.create("/reservations/" + saveId);
        return ResponseEntity.created(location).body(response);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationDao.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
