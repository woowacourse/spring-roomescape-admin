package roomescape.reservation.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.reservation.dto.ReservationRequestDto;

import java.sql.Date;
import java.sql.PreparedStatement;

@Repository
public class UpdatingDao {

    private final JdbcTemplate jdbcTemplate;

    public UpdatingDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long insert(ReservationRequestDto requestDto) {
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

    public void delete(Long id) {
        jdbcTemplate.update("delete from reservation where id = ?", id);
    }
}
