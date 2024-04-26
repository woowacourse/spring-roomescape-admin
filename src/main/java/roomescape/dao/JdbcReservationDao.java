package roomescape.dao;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

@Repository
public class JdbcReservationDao implements ReservationDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public JdbcReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public List<Reservation> findAll() {
        return jdbcTemplate.query("""
                        SELECT r.id as reservation_id, r.name, r.date,
                            t.id as time_id, t.start_at as time_value
                        FROM reservation as r
                        inner join reservation_time as t
                        on r.time_id = t.id""",
                (rs, rowNum) -> new Reservation(
                        rs.getLong("reservation_id"),
                        rs.getString("name"),
                        rs.getDate("date").toLocalDate(),
                        new ReservationTime(rs.getLong("time_id"),
                                rs.getTime("time_value"))
                ));
    }

    @Override
    public long save(String name, String date, long timeId) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("name", name)
                .addValue("date", date)
                .addValue("time_id", timeId);
        return simpleJdbcInsert.executeAndReturnKey(sqlParameterSource)
                .longValue();
    }

    @Override
    public boolean deleteById(long id) {
        return jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id) > 0;
    }
}
