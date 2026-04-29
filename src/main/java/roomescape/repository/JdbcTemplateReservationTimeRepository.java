package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;

import java.sql.PreparedStatement;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcTemplateReservationTimeRepository implements ReservationTimeRepository {


    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateReservationTimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public ReservationTime save(ReservationTime reservationTime) {
        String sql = "insert into reservation_time(start_at) values(?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservationTime.getStartAt().toString());
            return ps;
        }, keyHolder);

        reservationTime.bindId(keyHolder.getKey().longValue());
        return reservationTime;
    }

    @Override
    public Optional<ReservationTime> findById(Long id) {
        String sql = "select * from reservation_time where id = ?";
        ReservationTime reservationTime = jdbcTemplate.queryForObject(sql, reservationTimeRowMapper(), id);
        return reservationTime == null ? Optional.empty() : Optional.of(reservationTime);
    }

    @Override
    public List<ReservationTime> findAll() {
        String sql = "select * from reservation_time";
        return jdbcTemplate.query(sql,
                reservationTimeRowMapper()
        );
    }

    private static RowMapper<ReservationTime> reservationTimeRowMapper() {
        return (rs, rowNum) -> new ReservationTime(
                rs.getLong("id"),
                LocalTime.parse(rs.getString("start_at"))
        );
    }

    @Override
    public void delete(Long id) {
        String sql = "delete from reservation_time where id = ?";
        jdbcTemplate.update(sql, id);
    }
}
