package roomescape.repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.util.List;
import java.util.Objects;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.entity.Reservation;
import roomescape.exceptions.EntityNotFoundException;

@Primary
@Repository
public class ReservationH2Repository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationH2Repository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Reservation> findAll() {
        String sql = "select * from reservation";
        return jdbcTemplate.query(sql, getReservationRowMapper());
    }

    @Override
    public Reservation save(Reservation reservation) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "insert into reservation (name, date, time) values (?, ?, ?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservation.name());
            ps.setDate(2, Date.valueOf(reservation.date()));
            ps.setTime(3, Time.valueOf(reservation.time()));
            return ps;
        }, keyHolder);
        Long id = Objects.requireNonNull(keyHolder.getKey()).longValue();
        return new Reservation(id, reservation.name(), reservation.date(), reservation.time());
    }

    @Override
    public void deleteById(long id) {
        String sql = "delete from reservation where id=?";
        int result = jdbcTemplate.update(sql, id);
        if (result == 0) {
            throw new EntityNotFoundException("예약 데이터를 찾을 수 없습니다:" + id);
        }
    }

    private RowMapper<Reservation> getReservationRowMapper() {
        return (resultSet, rowNum) -> new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getDate("date").toLocalDate(),
                resultSet.getTime("time").toLocalTime()
        );
    }
}
