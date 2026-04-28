package roomescape.time.controller;


import java.sql.Date;
import java.sql.PreparedStatement;
import java.time.LocalTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.reservation.dto.ReservationResponseDto;
import roomescape.time.dto.TimeRequestDto;
import roomescape.time.dto.TimeResponseDto;
import roomescape.time.entity.Time;

@RestController
@RequestMapping("/times")
public class TimeController {

    private static final String INSERT_TIME = "INSERT INTO reservation_time(start_at) VALUES (?)";
    private static final String SELECT_ALL = "SELECT * FROM reservation_time";
    private static final String DELETE = "DELETE FROM reservation_time WHERE id = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public TimeController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @GetMapping
    public ResponseEntity<List<TimeResponseDto>> readAll() {
        List<TimeResponseDto> timeResponse = jdbcTemplate.query(SELECT_ALL, timeRowMapper());

        return ResponseEntity.ok(timeResponse);
    }

    public RowMapper<TimeResponseDto> timeRowMapper() {
        return (resultSet, rowNum) -> new TimeResponseDto(
                resultSet.getLong("id"),
                resultSet.getString("start_at")
        );
    }

    @PostMapping
    public ResponseEntity<TimeResponseDto> add(@RequestBody TimeRequestDto timeRequestDto) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(INSERT_TIME, new String[]{"id"});
            ps.setString(1, timeRequestDto.startAt());
            return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();

        TimeResponseDto timeResponseDto = new TimeResponseDto(id, timeRequestDto.startAt());

        return ResponseEntity.ok(timeResponseDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        jdbcTemplate.update(DELETE, id);
    }
}
