package roomescape.web.db;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.general.db.ReservationDao;
import roomescape.general.domain.Reservation;
import roomescape.general.domain.ReservationTime;


@Repository
public class ReservationDaoH2Impl implements ReservationDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public ReservationDaoH2Impl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    public Reservation save(final Reservation reservation) {
        final MapSqlParameterSource source = new MapSqlParameterSource()
                .addValue("name", reservation.getName())
                .addValue("date", reservation.getDate())
                .addValue("time_id", reservation.getReservationTime().getId());
        final long id = jdbcInsert.executeAndReturnKey(source).longValue();
        return new Reservation(id, reservation.getName(), reservation.getDate(), reservation.getReservationTime());
    }

    public boolean isTimeIdExist(final long id) {
        final String sql = "select id from reservation where time_id=? limit 1";
        try {
            jdbcTemplate.queryForObject(sql, Long.class, id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    public List<Reservation> findAll() {
        return jdbcTemplate.query("""
                SELECT
                    r.id as reservation_id,
                    r.name,
                    r.date,
                    t.id as time_id,
                    t.start_at as time_value
                FROM reservation as r
                inner join reservation_time as t
                on r.time_id = t.id
                """, getReservationRowMapper());
    }

    public void deleteById(final long id) {
        jdbcTemplate.update("delete from reservation where id=?", id);
    }

    private RowMapper<Reservation> getReservationRowMapper() {
        return (resultSet, rowNum) -> new Reservation(resultSet.getLong("id"),
                resultSet.getString("name"),
                LocalDate.parse(resultSet.getString("date")),
                new ReservationTime(resultSet.getLong("time_id"),
                        LocalTime.parse(resultSet.getString("start_at"))));
    }
}
