package roomescape.controller;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.domain.Name;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationDate;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.dto.TimeRequest;
import roomescape.dto.TimeResponse;

@Controller
public class ReservationController {
    private JdbcTemplate jdbcTemplate;

    public ReservationController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<ReservationResponse> findAll() {
        return jdbcTemplate.query(
                "SELECT id, name, date, time FROM reservation ORDER BY id",
                (resultSet, rowNum) -> {
                    Reservation reservation = new Reservation(
                            resultSet.getLong("id"),
                            new Name(resultSet.getString("name")),
                            new ReservationDate(resultSet.getString("date")),
                            new ReservationTime(resultSet.getString("time"))
                    );

                    return ReservationResponse.from(reservation);
                }
        );
    }

    @PostMapping("/reservations")
    @ResponseBody
    public Long create(@RequestBody ReservationRequest request) {
        KeyHolder keyholder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)",
                    new String[]{"id"});

            preparedStatement.setString(1, request.name());
            preparedStatement.setString(2, request.date());
            preparedStatement.setString(3, request.time());

            return preparedStatement;
        }, keyholder);

        Number key = keyholder.getKey();

        if (key == null) {
            throw new IllegalStateException("[ERROR] 예약 ID가 생성되지 않았습니다.");
        }

        return key.longValue();
    }

    @DeleteMapping("/reservations/{id}")
    @ResponseBody
    public void delete(@PathVariable Long id) {
        jdbcTemplate.update("DELETE FROM RESERVATION WHERE id = ?", id);
    }

    @PostMapping("/times")
    @ResponseBody
    public TimeResponse createTime(@RequestBody TimeRequest request) {
        KeyHolder keyholder = new GeneratedKeyHolder();

        jdbcTemplate.update(
                connection -> {
                    PreparedStatement preparedStatement = connection.prepareStatement(
                            "INSERT INTO reservation_time (start_at) VALUES (?)",
                            new String[]{"id"});

                    preparedStatement.setString(1, request.startAt());

                    return preparedStatement;
                }, keyholder);

        Number key = keyholder.getKey();
        if (key == null) {
            throw new IllegalStateException("[ERROR] 예약 ID가 생성되지 않았습니다.");
        }

        return new TimeResponse(key.longValue(), request.startAt());
    }
}
