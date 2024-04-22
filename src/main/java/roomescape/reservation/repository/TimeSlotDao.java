package roomescape.reservation.repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Time;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import roomescape.reservation.domain.TimeSlot;

@Component
public class TimeSlotDao implements TimeSlotRepository {
    private final JdbcTemplate jdbcTemplate;
    private final KeyHolder keyHolder = new GeneratedKeyHolder();

    public TimeSlotDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public TimeSlot save(final TimeSlot timeSlot) {
        String sql = "INSERT INTO reservation_time (start_at) values (?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setTime(1, Time.valueOf(timeSlot.getStartAt()));
            return preparedStatement;
        }, keyHolder);
        return new TimeSlot(keyHolder.getKey().longValue(), timeSlot.getStartAt());
    }
}
