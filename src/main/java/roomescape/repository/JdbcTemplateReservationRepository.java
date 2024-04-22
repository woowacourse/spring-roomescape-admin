package roomescape.repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Time;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.reservation.Reservation;

@Repository
public class JdbcTemplateReservationRepository implements ReservationRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) -> new Reservation(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            resultSet.getDate("date").toLocalDate(),
            resultSet.getTime("time").toLocalTime()
    );

    @Override
    public Reservation save(Reservation reservationRequest) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, reservationRequest.getName());
            ps.setDate(2, Date.valueOf(reservationRequest.getDate()));
            ps.setTime(3, Time.valueOf(reservationRequest.getTime()));
            return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();
        return new Reservation(id, reservationRequest);
    }

    @Override
    public List<Reservation> findAll() {
        return jdbcTemplate.query("SELECT id, name, date, time FROM reservation", reservationRowMapper);
    }

    @Override
    public void deleteById(Long id) {
        int rowCount = jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id);
        if (rowCount == 0) {
            throw new IllegalArgumentException("존재하지 않는 예약입니다.");
        }
    }
}
