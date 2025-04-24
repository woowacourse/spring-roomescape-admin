package roomescape.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;
import roomescape.entity.ReservationEntity;
import roomescape.entity.ReservationTimeEntity;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
public class ReservationDaoImpl implements ReservationDao {
    private final JdbcTemplate jdbcTemplate;

    public ReservationDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ReservationEntity save(ReservationEntity newReservation) {
        String query = "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(query, new String[]{"id"});
            preparedStatement.setString(1, newReservation.name());
            preparedStatement.setString(2, newReservation.date().toString());
            preparedStatement.setLong(3, newReservation.getTimeId());
            return preparedStatement;
        }, keyHolder);
        final long id = keyHolder.getKey().longValue();
        return newReservation.changeId(id);
    }

    public int deleteById(final Long id) {
        String query = "DELETE FROM reservation WHERE id = ?";
        return jdbcTemplate.update(query, id);
    }

    public List<ReservationEntity> findAll() {
        String query = """
            SELECT
                r.id as reservation_id,
                r.name,
                r.date,
                t.id as time_id,
                t.start_at as time_value
            FROM reservation as r
            inner join reservation_time as t
            on r.time_id = t.id
            """;
        return jdbcTemplate.query(query, (resultSet, rowNum) -> {
            LocalDate date = resultSet.getObject("date", LocalDate.class);
            final long timeId = resultSet.getLong("time_id");
            LocalTime timeValue = resultSet.getObject("time_value", LocalTime.class);
            ReservationTimeEntity timeEntity = new ReservationTimeEntity(timeId, timeValue);
            return new ReservationEntity(
                    resultSet.getLong("reservation_id"),
                    resultSet.getString("name"),
                    date,
                    timeEntity
            );
        });
    }
}
