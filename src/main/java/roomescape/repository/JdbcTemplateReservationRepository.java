package roomescape.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class JdbcTemplateReservationRepository implements ReservationRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Reservation> reservationRowMapper = (rs, rowNum) -> {
        Reservation reservation = new Reservation(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("date"),
                rs.getString("time")
        );
        return reservation;
    };

    @Override
    public Reservation insert(final Reservation reservation) {
        String sql = "INSERT INTO reservation (name, date, time) VALUES (?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update((Connection con) -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, reservation.getName());
            preparedStatement.setString(2, reservation.getDate());
            preparedStatement.setString(3, reservation.getTime());

            return preparedStatement;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();
        return new Reservation(id, reservation);
    }

    @Override
    public List<Reservation> findAll() {
        return jdbcTemplate.query("SELECT * FROM reservation", reservationRowMapper);
    }

    @Override
    public int deleteById(final Long id) {
        String sql = "DELETE FROM reservation WHERE id = (?)";
        int updateCount = jdbcTemplate.update(sql, id);

        return updateCount;
    }
}
