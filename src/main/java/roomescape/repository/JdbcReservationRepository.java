package roomescape.repository;

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
import roomescape.domain.ReservationTime;

@Repository
public class JdbcReservationRepository implements ReservationRepository {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Reservation> rowMapper = (resultSet, rowNum) -> {
        ReservationTime reservationTime = new ReservationTime(
                resultSet.getLong("time_id"),
                resultSet.getObject("start_at", LocalTime.class)
        );

        return new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getObject("date", LocalDate.class),
                reservationTime
        );
    };

    public JdbcReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Reservation> findAll() {
        String sql = "select r.id, r.name, r.date, rt.id as time_id, rt.start_at "
                + "from reservation as r "
                + "inner join reservation_time as rt "
                + "on r.time_id = rt.id";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public Reservation save(Reservation reservation) {
        String insertSql = "insert into reservation (name, date, time_id) values (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement pstmt = connection.prepareStatement(insertSql, new String[]{"id"});
            pstmt.setString(1, reservation.getName());
            pstmt.setObject(2, reservation.getDate());
            pstmt.setLong(3, reservation.getTime().getId());
            return pstmt;
        }, keyHolder);

        return new Reservation(keyHolder.getKey().longValue(), reservation.getName(), reservation.getDate(),
                reservation.getTime());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "delete from reservation where id = ?";
        jdbcTemplate.update(sql, id);
    }
}
