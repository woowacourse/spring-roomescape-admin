package roomescape.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.model.Reservation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public class JdbcReservationRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;
    private final KeyHolder keyHolder;

    @Autowired
    public JdbcReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.keyHolder = new GeneratedKeyHolder();
    }

    @Override
    public List<Reservation> findAll() {
        String sql = "SELECT * FROM reservation";
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> {
            Long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            LocalDate date = resultSet.getObject("date", LocalDate.class);
            LocalTime time = resultSet.getObject("time", LocalTime.class);
            Reservation reservation = new Reservation(name, date, time);
            return Reservation.toEntity(reservation, id);
        });
    }

    @Override
    public boolean existByDateAndTime(LocalDate date, LocalTime time) {
        String sql = "SELECT COUNT(*) FROM reservation WHERE date = ? AND time = ?";
        return jdbcTemplate.queryForObject(sql, Long.class, date, time) > 0L;
    }

    @Override
    public Reservation insertAndGet(Reservation reservation) {
        String sql ="INSERT INTO reservation(name, date, time) VALUES(?, ?, ?)";
        jdbcTemplate.update((Connection con) -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setString(1, reservation.getName());
            preparedStatement.setObject(2, reservation.getDate());
            preparedStatement.setObject(3, reservation.getTime());
            return preparedStatement;
        }, keyHolder);
        return Reservation.toEntity(reservation, keyHolder.getKeyAs(Long.class));
    }

    @Override
    public int deleteByIdAndCountAffected(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
