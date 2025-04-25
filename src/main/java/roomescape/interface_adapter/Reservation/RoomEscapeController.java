package roomescape.interface_adapter.Reservation;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.usecase.Reservation.AddReservationUseCase;
import roomescape.usecase.Reservation.DeleteReservationUseCase;
import roomescape.usecase.Reservation.GetReservationUseCase;
import roomescape.usecase.Reservation.ReservationInput;
import roomescape.usecase.Reservation.ReservationOutput;

@RestController
public class RoomEscapeController {

    private final JdbcTemplate jdbcTemplate;
    private final GetReservationUseCase getReservationUseCase;
    private final AddReservationUseCase addReservationUseCase;
    private final DeleteReservationUseCase deleteReservationUsecase;

    public RoomEscapeController(final JdbcTemplate jdbcTemplate,
                                final GetReservationUseCase getReservationUseCase,
                                final AddReservationUseCase addReservationUseCase,
                                final DeleteReservationUseCase deleteReservationUsecase
    ) {
        this.jdbcTemplate = jdbcTemplate;
        this.getReservationUseCase = getReservationUseCase;

        this.addReservationUseCase = addReservationUseCase;
        this.deleteReservationUsecase = deleteReservationUsecase;
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationOutput> addReservation(@RequestBody ReservationRequestDto reservationDto) {
        ReservationInput reservationInput = new ReservationInput(reservationDto.date(), reservationDto.name(),
                reservationDto.timeId());
        ReservationOutput reservationOutput = addReservationUseCase.addReservation(reservationInput);

        return ResponseEntity.ok(reservationOutput);
    }

    @GetMapping("reservations")
    public ResponseEntity<List<ReservationOutput>> checkReservation() {
        List<ReservationOutput> reservationOutput = getReservationUseCase.getReservationOutput();
        return ResponseEntity.ok(reservationOutput);
    }


    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable long id) {
        deleteReservationUsecase.deleteReservation(id);
        return ResponseEntity.ok().build();
    }

    //    @GetMapping("reservations")
//    public ResponseEntity<List<Reservation>> checkReservation() {
//        String sql = "select * from reservation";
//        List<Reservation> reservations = jdbcTemplate.query(sql, (resultSet, rowNum) -> {
//            return new Reservation(
//                    resultSet.getLong("id"),
//                    resultSet.getString("name"),
//                    LocalDate.parse(resultSet.getString("date")),
//                    LocalTime.parse(resultSet.getString("time"))
//            );
//        });
//
//        return ResponseEntity.ok(reservations);
//    }

//    @PostMapping("/reservations")
//    public ResponseEntity<Void> addReservation(@RequestBody ReservationRequestDto reservationDto) {
//        String sql = "insert into reservation(name,date,time) values (?,?,?)";
//        jdbcTemplate.update(sql, reservationDto.name(), reservationDto.date(), reservationDto.time());
//        return ResponseEntity.ok().build();
//    }

//
//    @DeleteMapping("/reservations/{id}")
//    public ResponseEntity<Void> deleteReservation(@PathVariable long id) {
//        String sql = "delete from reservation where id =?";
//        jdbcTemplate.update(sql, id);
//        return ResponseEntity.ok().build();
//    }

//    @PostMapping("reservations")
//    public ResponseEntity<Reservation> addReservation(@RequestBody ReservationDto request) {
//        try {
//            long id = reservations.nextId();
//            Reservation reservation = request.toReservation(id);
//            reservations.add(reservation);
//            return ResponseEntity.ok(reservation);
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().build();
//        }
//    }
//
//    @DeleteMapping("reservations/{id}")
//    public ResponseEntity<Void> deleteReservation(@PathVariable("id") Long id) {
//        boolean isRemoved = reservations.removeById(id);
//        if (isRemoved) {
//            return ResponseEntity.ok().build();
//        }
//        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//    }
}
