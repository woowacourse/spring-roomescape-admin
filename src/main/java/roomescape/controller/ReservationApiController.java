package roomescape.controller;

import jakarta.servlet.http.HttpServletResponse;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

@RestController
@RequestMapping("reservations")
public class ReservationApiController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping
    public List<ReservationResponse> getReservations() {
        String sql = "select * from reservation";
        List<Reservation> reservations = jdbcTemplate.query(
                sql,
                (resultSet, row) -> {
                    return new Reservation(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            resultSet.getObject("datetime", LocalDateTime.class)
                    );
                }
        );

        return reservations.stream()
                .map(ReservationResponse::new)
                .toList();
    }

    @PostMapping
    public ReservationResponse createReservation(@RequestBody ReservationRequest request) {
        LocalDate date = LocalDate.parse(request.date());
        LocalTime time = LocalTime.parse(request.time());
        LocalDateTime dateTime = LocalDateTime.of(date, time);

        String sql = "insert into reservation (name, datetime) values(?, ?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement prepared = connection.prepareStatement(sql, new String[]{"id"});
            prepared.setString(1, request.name());
            prepared.setObject(2, dateTime);
            return prepared;
        }, keyHolder);

        long id = keyHolder.getKey().longValue();

        Reservation created = request.toReservation(id);

        return new ReservationResponse(created);
    }

    @DeleteMapping("{id}")
    public void deleteReservation(@PathVariable Long id, HttpServletResponse response) {
        String sql = "delete from reservation where id = ?";
        int count = jdbcTemplate.update(sql, id);

        if (count == 0) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
