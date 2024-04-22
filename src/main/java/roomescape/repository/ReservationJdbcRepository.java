package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Repository
public class ReservationJdbcRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private final RowMapper<Reservation> rowMapper = (resultSet, rowNum) -> {
        final Reservation reservation = new Reservation(
                resultSet.getLong("reservation_id"),
                resultSet.getString("name"),
                resultSet.getString("date"),
                new ReservationTime(resultSet.getLong("time_id"),
                        LocalTime.parse(resultSet.getString("time_value")))
        );
        return reservation;
    };

    public ReservationJdbcRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Long save(final Reservation reservation) {
        return jdbcInsert.executeAndReturnKey(
                Map.of("name", reservation.getName(),
                        "date", reservation.getDate(),
                        "time_id", reservation.getTime().getId())).longValue();
    }

    @Override
    public List<Reservation> findAll() {
        final String sql = "select r.id as reservation_id, r.name, r.date, \n" +
                "t.id as time_id, t.start_at as time_value \n" +
                "FROM reservation as r \n" +
                "inner join reservation_time as t on r.time_id = t.id";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public void deleteById(final Long id) {
        final String sql = "delete from reservation where id = ?";
        jdbcTemplate.update(sql, Long.valueOf(id));
    }
}
