package roomescape.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.model.EntityId;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;

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
        String sql = "SELECT * FROM reservation INNER JOIN reservation_time ON reservation.time_id = reservation_time.id";
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> {
            ReservationTime time = new ReservationTime(
                    new EntityId(resultSet.getLong("time_id")),
                    resultSet.getObject("start_at", LocalTime.class));
            return new Reservation(
                    new EntityId(resultSet.getLong("id")),
                    resultSet.getString("name"),
                    resultSet.getObject("date", LocalDate.class),
                    time);
        });
    }

    @Override
    public boolean existByDateAndTimeId(LocalDate date, Long timeId) {
        String sql = "SELECT EXISTS(SELECT 1 FROM reservation WHERE date = ? AND time_id = ?)";
        return jdbcTemplate.queryForObject(sql, Boolean.class, date, timeId);
    }

    @Override
    public Reservation insert(Reservation reservation) {
        String sql ="INSERT INTO reservation(name, date, time_id) VALUES(?, ?, ?)";
        jdbcTemplate.update((Connection con) -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setString(1, reservation.getName());
            preparedStatement.setObject(2, reservation.getDate());
            preparedStatement.setObject(3, reservation.getTime().getId());
            return preparedStatement;
        }, keyHolder);
        return new Reservation(
                new EntityId(keyHolder.getKeyAs(Long.class)),
                reservation.getName(), reservation.getDate(), reservation.getTime());
    }

    @Override
    public int deleteById(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
