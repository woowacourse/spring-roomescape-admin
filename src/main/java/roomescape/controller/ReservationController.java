package roomescape.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.request.ReservationRequest;
import roomescape.dto.response.ReservationResponse;
import roomescape.service.ReservationCommandService;
import roomescape.service.ReservationQueryService;

@RestController
public class ReservationController {

    private final ReservationCommandService reservationCommandService;
    private final ReservationQueryService reservationQueryService;

    public ReservationController(ReservationCommandService reservationCommandService,
                                 ReservationQueryService reservationQueryService) {
        this.reservationCommandService = reservationCommandService;
        this.reservationQueryService = reservationQueryService;
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponse>> getAllReservations() {
        return ResponseEntity.status(HttpStatus.OK).body(reservationQueryService.getAllReservations());
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> createReservation(@RequestBody ReservationRequest request) {
        ReservationResponse reservationResponse = reservationCommandService.create(request.name(), request.date(), request.timeId());
        return ResponseEntity.status(HttpStatus.OK).body(reservationResponse);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationCommandService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

//    @PostMapping("/times")
//    public ResponseEntity<ReservationTimeResponse> createReservationTime(@RequestBody ReservationTimeRequest request) {
//        SqlParameterSource params = new BeanPropertySqlParameterSource(request);
//        SimpleJdbcInsert insertExecutor = new SimpleJdbcInsert(jdbcTemplate)
//                .withTableName("reservation_time")
//                .usingGeneratedKeyColumns("id");
//
//        Number newId = insertExecutor.executeAndReturnKey(params);
//
//        ReservationTime time = ReservationTime.create(
//                newId.longValue(),
//                request.startAt()
//        );
//
//        return ResponseEntity.ok(ReservationTimeResponse.from(time));
//    }
//
//    @GetMapping("/times")
//    public ResponseEntity<List<ReservationTimeResponse>> getAllTimes() {
//        String sql = "SELECT id, start_at FROM reservation_time";
//
//        List<ReservationTime> reservationTimes = jdbcTemplate.query(sql, reservationTimeRowMapper);
//
//        List<ReservationTimeResponse> responses = reservationTimes.stream()
//                .map(ReservationTimeResponse::from)
//                .toList();
//
//        return ResponseEntity.ok(responses);
//    }
//
//    @DeleteMapping("/times/{id}")
//    public ResponseEntity<Void> deleteReservationTime(@PathVariable Long id) {
//        String sql = "DELETE FROM reservation_time WHERE id = ?";
//        jdbcTemplate.update(sql, id);
//
//        return ResponseEntity.status(HttpStatus.OK).build();
//    }
//
//    private final RowMapper<Reservation> reservationRowMapper = (rs, rowNum) -> {
//        ReservationTime reservationTime = ReservationTime.create(
//                rs.getLong("time_id"),
//                rs.getObject("time_value", LocalTime.class)
//        );
//
//        return Reservation.create(
//                rs.getLong("reservation_id"),
//                rs.getString("name"),
//                rs.getObject("date", LocalDate.class),
//                reservationTime
//        );
//    };
//
//    private final RowMapper<ReservationTime> reservationTimeRowMapper = (rs, rowNum) ->
//            ReservationTime.create(
//                    rs.getLong("id"),
//                    rs.getObject("start_at", LocalTime.class)
//            );
}
