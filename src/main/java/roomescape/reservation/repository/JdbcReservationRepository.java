package roomescape.reservation.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.reservation.dto.ReservationRequestDto;
import roomescape.reservation.entity.Reservation;
import roomescape.time.entity.ReservationTime;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class JdbcReservationRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) -> {
        ReservationTime time = new ReservationTime(
                resultSet.getLong("time_id"),
                resultSet.getString("start_at")
        );

        return new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getDate("date").toLocalDate(),
                time
        );
    };

    public JdbcReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long save(ReservationRequestDto requestDto) {
        String sql = "insert into reservation (name, date, time_id) values (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, requestDto.getName());
            ps.setDate(2, Date.valueOf(requestDto.getDate()));
            ps.setLong(3, requestDto.getTimeId());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("delete from reservation where id = ?", id);
    }

    @Override
    public Reservation findById(Long id) {
        String sql = "select r.id, r.name, r.date, r.time_id, t.start_at " +
                "from reservation r " +
                "inner join reservation_time t on r.time_id = t.id " +
                "where r.id = ?";
        return jdbcTemplate.queryForObject(sql, reservationRowMapper, id);
    }

    @Override
    public List<Reservation> findAll() {
        String sql = "select r.id, r.name, r.date, r.time_id, t.start_at " +
                "from reservation r " +
                "inner join reservation_time t on r.time_id = t.id";
        return jdbcTemplate.query(sql, reservationRowMapper);
    }
}
