package roomescape.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;
import roomescape.Reservation;

@Component
public class ReservationDaoImpl implements ReservationDao{
    private final JdbcTemplate jdbcTemplate;

    public ReservationDaoImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public long save(final Reservation reservation) {
        final var sql = "INSERT INTO reservation(name, date, time) VALUES(?,?,?)";
        final var keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement pstmt = con.prepareStatement(sql, new String[]{"id"});
            pstmt.setString(1, reservation.getName());
            pstmt.setDate(2, Date.valueOf(reservation.getDate()));
            pstmt.setTime(3, Time.valueOf(reservation.getTime()));
            return pstmt;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    @Override
    public List<Reservation> findAll() {
        final var sql = "SELECT id, name, date, time FROM reservation";
        return jdbcTemplate.query(sql, actorRowMapper());
    }

    @Override
    public void delete(final long id) {
        final var sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private RowMapper<Reservation> actorRowMapper() {
        return (resultSet, rowNum) -> new Reservation.Builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .date(resultSet.getDate("date").toLocalDate())
                .time(resultSet.getTime("time").toLocalTime())
                .build();
    }
}
