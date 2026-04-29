package roomescape.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.dto.ReservationRequestDto;
import roomescape.entity.Reservation;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;

@Repository
public class UpdatingDao {

    private final JdbcTemplate jdbcTemplate;

    public UpdatingDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insert(Reservation reservation) {
        String sql = "insert into reservation (name, date, time) values (?, ?, ?)";
        jdbcTemplate.update(sql,
                reservation.getName(),
                Date.valueOf(reservation.getDate()),
                Time.valueOf(reservation.getTime()));
    }

    public int delete(Long id) {
        String sql = "delete from reservation where id = ?";
        return jdbcTemplate.update(sql, id);
    }

    public Long insertWithKeyHolder(ReservationRequestDto requestDto) {
        String sql = "insert into reservation (name, date, time) values (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, requestDto.getName());
            ps.setDate(2, Date.valueOf(requestDto.getDate()));
            ps.setTime(3, Time.valueOf(requestDto.getTime()));
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }
}
