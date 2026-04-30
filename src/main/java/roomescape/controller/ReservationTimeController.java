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
import roomescape.domain.ReservationTime;
import roomescape.dto.TimeRequest;
import roomescape.dto.TimeResponse;

@Controller
public class ReservationTimeController {
    private JdbcTemplate jdbcTemplate;

    public ReservationTimeController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping("/times")
    @ResponseBody
    public TimeResponse create(@RequestBody TimeRequest request) {
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
            throw new IllegalStateException("[ERROR] 시간 ID가 생성되지 않았습니다.");
        }

        return new TimeResponse(key.longValue(), request.startAt());
    }

    @GetMapping("/times")
    @ResponseBody
    public List<TimeResponse> findAll() {
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

    public List<TimeResponse> findAllTime() {
        return findAll();
    }

    @DeleteMapping("/times/{id}")
    @ResponseBody
    public void delete(@PathVariable Long id) {
        int deleteCount = jdbcTemplate.update(
                "DELETE FROM reservation_time WHERE id = ?", id
        );

        if (deleteCount == 0) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 시간입니다.");
        }
    }

    public void deleteTime(Long id) {
        delete(id);
    }
}
