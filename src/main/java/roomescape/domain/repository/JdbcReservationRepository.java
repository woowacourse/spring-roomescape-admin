package roomescape.domain.repository;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

@Repository
public class JdbcReservationRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Reservation> reservationMapper = (resultSet, rowNum) -> {
        Reservation reservation = new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                LocalDate.parse(resultSet.getString("date")),
                LocalTime.parse(resultSet.getString("time"))
        );
        return reservation;
    };

    public JdbcReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long save(Reservation reservation) {
        String sql = "INSERT INTO reservation(name, date, time) VALUES(?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setString(1, reservation.getName());
            preparedStatement.setString(2, reservation.getDate().toString());
            preparedStatement.setString(3, reservation.getTime().toString());
            return preparedStatement;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    @Override
    public List<Reservation> findAll() {
        String sql = "SELECT * FROM reservation";
        return jdbcTemplate.query(sql, reservationMapper);
    }

    @Override
    public Reservation findById(Long id) {
        String sql = "SELECT * FROM reservation WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, reservationMapper, id);
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
