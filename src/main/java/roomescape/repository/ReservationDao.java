package roomescape.repository;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;

@Repository
public class ReservationDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public ReservationDao(JdbcTemplate jdbcTemplate, DataSource source) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(source)
            .withTableName("reservation")
            .usingGeneratedKeyColumns("id");
    }

    public List<Reservation> findAll() {
        String sql = """
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
        return jdbcTemplate.query(sql, mappingReservation());
    }

    private RowMapper<Reservation> mappingReservation() {
        return (resultSet, rowNum) -> new Reservation(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            resultSet.getString("date"),
            new ReservationTime(
                resultSet.getLong("time_id"),
                resultSet.getString("time_value")
            )
        );
    }

    public Reservation save(Reservation reservation) {
        SqlParameterSource params = new MapSqlParameterSource()
            .addValue("name", reservation.getName())
            .addValue("date", reservation.getDate())
            .addValue("time_id", reservation.getTime().getId());
        long id = jdbcInsert.executeAndReturnKey(params)
            .longValue();
        return new Reservation(
            id,
            reservation.getName(),
            reservation.getDate(),
            reservation.getTime()
        );
    }

    public boolean deleteById(Long id) {
        String sql = "delete from reservation where id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }
}
