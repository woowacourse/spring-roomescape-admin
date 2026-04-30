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
        return jdbcTemplate.query("""
                        SELECT r.id AS reservation_id,
                               r.name,
                               r.date,
                               t.id AS time_id,
                               t.start_at
                        FROM reservation r
                        INNER JOIN reservation_time t
                            ON r.time_id = t.id
                        ORDER BY r.id
                        """,
                (resultSet, rowNum) -> {
                    Reservation reservation = new Reservation(
                            resultSet.getLong("reservation_id"),
                            new Name(resultSet.getString("name")),
                            new ReservationDate(resultSet.getString("date")),
                            new ReservationTime(
                                    resultSet.getLong("time_id"),
                                    resultSet.getString("start_at")
                            )
                    );

                    return ReservationResponse.from(reservation);
                }
        );
    }

    @PostMapping("/reservations")
    @ResponseBody
    public ReservationResponse create(@RequestBody ReservationRequest request) {
        Name name = new Name(request.name());
        ReservationDate date = new ReservationDate(request.date());
        ReservationTime time = findTimeById(request.timeId());

        KeyHolder keyholder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)",
                    new String[]{"id"});

            preparedStatement.setString(1, name.value());
            preparedStatement.setString(2, date.value());
            preparedStatement.setLong(3, time.id());

            return preparedStatement;
        }, keyholder);

        Number key = keyholder.getKey();

        if (key == null) {
            throw new IllegalStateException("[ERROR] 예약 ID가 생성되지 않았습니다.");
        }

        Reservation reservation = new Reservation(key.longValue(), name, date, time);
        return ReservationResponse.from(reservation);
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

    @GetMapping("/times")
    @ResponseBody
    public List<TimeResponse> findAllTime() {
        return jdbcTemplate.query(
                "SELECT id, start_at FROM reservation_time ORDER BY id",
                (resultSet, rowNum) -> {
                    ReservationTime reservationTime = new ReservationTime(
                            resultSet.getLong("id"),
                            resultSet.getString("start_at")
                    );

                    return TimeResponse.from(reservationTime);
                }
        );
    }

    @DeleteMapping("/times/{id}")
    @ResponseBody
    public void deleteTime(@PathVariable Long id) {
        int deleteCount = jdbcTemplate.update(
                "DELETE FROM reservation_time WHERE id = ?", id
        );

        if (deleteCount == 0) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 시간입니다.");
        }
    }

    private ReservationTime findTimeById(Long id) {
        return jdbcTemplate.queryForObject(
                "SELECT id, start_at FROM reservation_time WHERE id = ?",
                (rs, rowNum) -> new ReservationTime(
                        rs.getLong("id"),
                        rs.getString("start_at")
                ),
                id
        );
    }
}
