package roomescape.dao;

import java.sql.PreparedStatement;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;

@Repository
public class ReservationTimeDao {
    private final JdbcTemplate jdbcTemplate;

    public ReservationTimeDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ReservationTime createTime(final LocalTime time) {
        String sql = "insert into reservation_time(start_at) values(?)";
        String[] resultColumns = {"id"};
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(sql, resultColumns);
            statement.setString(1, time.format(DateTimeFormatter.ofPattern("HH:mm")));
            return statement;
        }, keyHolder);
        return new ReservationTime(keyHolder.getKey().longValue(), time);
    }

    public List<ReservationTime> findAllTimes() {
        return jdbcTemplate.query("select * from reservation_time", (resultSet, rowNum) -> new ReservationTime(
                resultSet.getLong("id"),
                LocalTime.parse(resultSet.getString("start_at"))
        ));
    }

    public Optional<ReservationTime> findTime(final Long id) {
        String sql = "select * from reservation_time where id = ?";
        List<ReservationTime> reservationTimes = jdbcTemplate.query(sql, (resultSet, rowNum) -> new ReservationTime(
                resultSet.getLong("id"),
                LocalTime.parse(resultSet.getString("start_at"))
        ), id);
        return reservationTimes.stream().findFirst();
    }

    public void deleteTimeById(final Long id) {
        jdbcTemplate.update("delete from reservation_time where id = ?", id);
    }
}
