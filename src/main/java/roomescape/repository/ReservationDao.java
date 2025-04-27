package roomescape.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;

@Repository
public class ReservationDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertActor;

    public ReservationDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertActor = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    public long save(final Reservation reservation) {
        final Map<String, Object> reservationParameters = new HashMap<>(3);
        reservationParameters.put("name", reservation.getName());
        reservationParameters.put("date", reservation.getDate());
        reservationParameters.put("time_id", reservation.getTime().getId());
        final Number number = insertActor.executeAndReturnKey(reservationParameters);
        return getGenerateId(number);
    }

    public List<Reservation> findAll() {
        final String sql = """
                SELECT 
                    r.id as reservation_id, 
                    r.name, 
                    r.date, 
                    t.id as time_id, 
                    t.start_at as time_value 
                FROM reservation as r 
                INNER JOIN reservation_time as t 
                ON r.time_id = t.id
                """;
        final RowMapper<Reservation> rowMapper = getRowMapper();
        return jdbcTemplate.query(sql, rowMapper);
    }

    public void deleteById(final Long id) {
        final String sql = "delete from reservation where id = ?";
        jdbcTemplate.update(sql, id);
    }

    private RowMapper<Reservation> getRowMapper() {
        return (resultSet, rowNum) ->
                Reservation.from(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getDate("date").toLocalDate(),
                        ReservationTime.from(resultSet.getLong("time_id"),
                                resultSet.getTime("time_value").toLocalTime()));
    }

    private long getGenerateId(final Number number) {
        return number.longValue();
    }
}
