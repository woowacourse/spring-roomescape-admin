package roomescape.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;

import java.sql.PreparedStatement;
import java.util.List;

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
    public List<ReservationTime> findAll() {
        return List.of();
    }

    @Override
    public void delete(Long id) {

    }
}
