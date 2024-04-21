package roomescape.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

@Repository
public class ReservationSqlRepository implements ReservationRepository {
    private static final KeyHolder keyHolder = new GeneratedKeyHolder();
    private final RowMapper<Reservation> mapper = (resultset, rowNum) ->
            new Reservation(
                    resultset.getLong("id"),
                    resultset.getString("name"),
                    resultset.getString("date"),
                    resultset.getString("time")
            );
    private final JdbcTemplate jdbcTemplate;

    public ReservationSqlRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAll() {
        return jdbcTemplate.query("select * from reservation", mapper);
    }

    public long create(Reservation reservation) {
        try {
            jdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement("insert into reservation (name, date, time) values (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, reservation.getName());
                ps.setString(2, reservation.getDate());
                ps.setString(3, reservation.getTime());
                return ps;
            }, keyHolder);
            return Objects.requireNonNull(keyHolder.getKey())
                          .longValue();
        } catch (DataAccessException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public boolean deleteById(long reservationId) {
        try {
            jdbcTemplate.update("delete from reservation where id=?",
                    reservationId
            );
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }
}
