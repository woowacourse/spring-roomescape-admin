package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.dto.ReservationTimeResponseDto;

import java.sql.PreparedStatement;
import java.time.LocalTime;
import java.util.List;

@Repository
public class ReservationTimeJdbcRepository implements ReservationTimeRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationTimeJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long saveAndReturnId(final LocalTime startAt) {
        String sql = "insert into reservation_time (start_at) values (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, startAt.toString());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public List<ReservationTimeResponseDto> findAllReservationTimes() {
        String sql = "select * from reservation_time";
        RowMapper<ReservationTimeResponseDto> rowMapper = ((rs, rowNum) -> {
            String startAt = rs.getString("start_at");
            LocalTime reservationStartAt = LocalTime.parse(startAt);
            ReservationTimeResponseDto responseDto = new ReservationTimeResponseDto(rs.getLong("id"), reservationStartAt);
            return responseDto;
        });
        return jdbcTemplate.query(sql, rowMapper);
    }

    public void deleteById(Long id) {
        String sql = "delete from reservation_time where id = ?";
        int affectedRows = jdbcTemplate.update(sql, id);

        if (affectedRows == 0) {
            throw new IllegalStateException("Reservation time with id " + id + " not found");
        }
    }
}
