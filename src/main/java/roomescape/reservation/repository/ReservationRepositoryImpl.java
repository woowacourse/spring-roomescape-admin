package roomescape.reservation.repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.reservation.domain.Reservation;

@Repository
public class ReservationRepositoryImpl implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationRepositoryImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Reservation> reservationRowMapper =
            (resultSet, rowNum) -> new Reservation(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getDate("date").toLocalDate(),
                    resultSet.getTime("time").toLocalTime()
            );

    @Override
    public List<Reservation> findAll() {
        String sql = "select * from reservation";
        return jdbcTemplate.query(sql, reservationRowMapper);
    }

    @Override
    public Reservation findById(final Long id) {
        String sql = "select * from reservation where id = ?";
        return jdbcTemplate.queryForObject(sql, Reservation.class, id);
    }

    @Override
    public Long save(final Reservation reservation) {
        String sql = "insert into reservation (name, date, time) values (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator preparedStatementCreator = connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservation.getName().getValue());
            ps.setDate(2, Date.valueOf(reservation.getDate().getValue()));
            ps.setTime(3, Time.valueOf(reservation.getTime().getValue()));
            return ps;
        };

        jdbcTemplate.update(preparedStatementCreator, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public void deleteById(final Long id) {
        String sql = "delete from reservation where id = ?";
        jdbcTemplate.update(sql, id);
    }
}
