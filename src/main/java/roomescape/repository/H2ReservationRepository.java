package roomescape.repository;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.model.Reservation;

@Repository
public class H2ReservationRepository implements ReservationRepository {
    private final JdbcTemplate jdbcTemplate;

    public H2ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) -> {
        Reservation reservation = new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                LocalDate.parse(resultSet.getString("date")),
                LocalTime.parse(resultSet.getString("time"))
        );
        return reservation;
    };

    public Reservation findById(long id) {
        String sql = "select id, name, date, time from reservation where id = ?";
        return jdbcTemplate.queryForObject(sql, reservationRowMapper, id);
    }

    @Override
    public List<Reservation> findAll() {
        String sql = "select id, name, date, time from reservation";
        return jdbcTemplate.query(sql, reservationRowMapper);
    }

    @Override
    public Reservation add(final Reservation reservation) {
        String sql = "insert into reservation (name, date, time) values (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement pstmt = connection.prepareStatement(sql, new String[]{"id"});
            pstmt.setString(1, reservation.getName());
            pstmt.setString(2, reservation.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            pstmt.setString(3, reservation.getTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            return pstmt;
        }, keyHolder);

        return findById(keyHolder.getKey().longValue());
    }

    @Override
    public void removeById(final Long id) {
        String sql = "delete from reservation where id = ?";
        jdbcTemplate.update(sql, id);
    }
}
