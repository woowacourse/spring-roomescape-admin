package roomescape.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
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
            Long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            LocalDate date = resultSet.getObject("date", LocalDate.class);
            Long timeId = resultSet.getLong("time_id");
            LocalTime startAt = resultSet.getObject("start_at", LocalTime.class);
            ReservationTime time = new ReservationTime(startAt);
            Reservation reservation = new Reservation(name, date, ReservationTime.toEntity(time, timeId));
            return Reservation.toEntity(reservation, id);
        });
    }

    @Override
    public boolean existByDateAndTimeId(LocalDate date, Long timeId) {
        String sql = "SELECT COUNT(*) FROM reservation WHERE date = ? AND time_id = ?";
        return jdbcTemplate.queryForObject(sql, Long.class, date, timeId) > 0L;
    }

    @Override
    public Reservation insertAndGet(Reservation reservation) {
        String sql ="INSERT INTO reservation(name, date, time_id) VALUES(?, ?, ?)";
        jdbcTemplate.update((Connection con) -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setString(1, reservation.getName());
            preparedStatement.setObject(2, reservation.getDate());
            preparedStatement.setObject(3, reservation.getTime().getId());
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
