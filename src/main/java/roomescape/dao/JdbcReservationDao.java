package roomescape.dao;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.entity.Reservation;
import roomescape.entity.ReservationTime;

@Repository
public class JdbcReservationDao implements ReservationDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private final RowMapper<Reservation> rowMapper = (rs, rowNum) -> new Reservation(
            rs.getLong("reservation_id"),
            rs.getString("name"),
            rs.getDate("date").toLocalDate(),
            new ReservationTime(rs.getLong("time_id"), rs.getTime("time_value").toLocalTime()));

    public JdbcReservationDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    public List<Reservation> findAll() {
        String sql = "SELECT "
                + "r.id as reservation_id, "
                + "r.name, "
                + "r.date, "
                + "t.id as time_id, "
                + "t.start_at as time_value "
                + "FROM reservation as r "
                + "inner join reservation_time as t "
                + "on r.time_id = t.id";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Reservation findById(long id) {
        String sql = "SELECT "
                + "r.id as reservation_id, "
                + "r.name, "
                + "r.date, "
                + "t.id as time_id, "
                + "t.start_at as time_value "
                + "FROM reservation as r "
                + "inner join reservation_time as t "
                + "on r.time_id = t.id "
                + "WHERE t.id = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public long save(Reservation reservation) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("name", reservation.getName())
                .addValue("date", reservation.getDate())
                .addValue("time_id", reservation.getTime().getId());
        return jdbcInsert.executeAndReturnKey(parameterSource).longValue();
    }

    public int deleteById(long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
