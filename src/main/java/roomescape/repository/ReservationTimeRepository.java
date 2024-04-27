package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;

import java.sql.PreparedStatement;
import java.sql.Time;
import java.util.List;
import java.util.Optional;

@Repository
public class ReservationTimeRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<ReservationTime> reservationTimeRowMapper = (resultSet, rowNum) -> new ReservationTime(
            resultSet.getLong("id"),
            resultSet.getTime("start_at").toLocalTime()
    );


    public ReservationTimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ReservationTime save(ReservationTime time) {
        String sql = "INSERT INTO reservation_time (start_at) values (?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                    sql,
                    new String[]{"id"});
            ps.setTime(1, Time.valueOf(time.getStartAt()));
            return ps;
        }, keyHolder);
        return new ReservationTime(keyHolder.getKey().longValue(), time.getStartAt());
    }

    public Optional<ReservationTime> findById(Long id) {
        String sql = "SELECT id, start_at FROM reservation_time where id = ?";
        List<ReservationTime> reservationTimes = jdbcTemplate.query(sql, reservationTimeRowMapper, id);
        return reservationTimes.isEmpty() ? Optional.empty() : Optional.of(reservationTimes.get(0));
    }

    public List<ReservationTime> findAll() {
        String sql = "SELECT id, start_at FROM reservation_time";
        return jdbcTemplate.query(sql, reservationTimeRowMapper);
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM reservation_time WHERE id = ?";
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            return ps;
        });
    }
}
