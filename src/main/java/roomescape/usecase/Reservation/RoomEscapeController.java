package roomescape.usecase.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoomEscapeController {

    private final JdbcTemplate jdbcTemplate;

    public RoomEscapeController(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("reservations")
    public ResponseEntity<List<Reservation>> checkReservation() {
        String sql = "select * from reservation";
        List<Reservation> reservations = jdbcTemplate.query(sql, (resultSet, rowNum) -> {
            return new Reservation(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    LocalDate.parse(resultSet.getString("date")),
                    LocalTime.parse(resultSet.getString("time"))
            );
        });

        return ResponseEntity.ok(reservations);
    }

    @PostMapping("/reservations")
    public ResponseEntity<Void> addReservation(@RequestBody ReservationDto reservationDto) {
        String sql = "insert into reservation(name,date,time) values (?,?,?)";
        jdbcTemplate.update(sql, reservationDto.name(), reservationDto.date(), reservationDto.time());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable long id) {
        String sql = "delete from reservation where id =?";
        jdbcTemplate.update(sql, id);
        return ResponseEntity.ok().build();
    }

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
