package roomescape.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Name;
import roomescape.domain.Reservation;

@Repository
public class ReservationDao {
    private final JdbcTemplate jdbcTemplate;

    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Reservation> reservationRowMapper = (resultSet, rownum) -> {
        Reservation reservation = new Reservation(
                resultSet.getLong("id"),
                new Name(resultSet.getString("name")),
                LocalDate.parse(resultSet.getString("date")),
                LocalTime.parse(resultSet.getString("time"))
        );
        return reservation;
    };

    public Reservation save(Reservation reservation) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO reservation(name, `date`, `time`) VALUES (?,?,?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservation.getName().getValue());
            ps.setDate(2, Date.valueOf(reservation.getDate()));
            ps.setTime(3, Time.valueOf(reservation.getTime()));
            return ps;
        }, keyHolder);
        long id = keyHolder.getKey().longValue();
        return new Reservation(id, reservation);
    }

    public List<Reservation> findAll() {
        String sql = "SELECT id, name, `date`, `time` FROM reservation";
        return jdbcTemplate.query(
                sql,
                reservationRowMapper
        );
    }

    public Reservation findById(long id) {
        String sql = "SELECT * FROM reservation WHERE id = ?";
        return jdbcTemplate.query(sql, reservationRowMapper, id)
                .stream()
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약 id 입니다."));
    }

    public void deleteById(long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
