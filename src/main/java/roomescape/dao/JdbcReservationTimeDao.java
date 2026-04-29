package roomescape.dao;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.dto.ReservationTimeRequestDto;
import roomescape.domain.entity.ReservationTime;

@Primary
@Repository
public class JdbcReservationTimeDao implements ReservationTimeDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcReservationTimeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public ReservationTime create(ReservationTimeRequestDto requestDto) {
        String sql = "INSERT INTO `reservation_time`(`start_at`) VALUES ?";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setString(1, requestDto.startAt());

            return preparedStatement;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();
        return new ReservationTime(id, requestDto.startAt());
    }

    @Override
    public List<ReservationTime> readAll() {
        String sql = "SELECT * FROM `reservation_time`";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Long id = rs.getLong("id");
            String startAt = rs.getString("start_at");
            return new ReservationTime(id, startAt);
        });
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM `reservation_time` WHERE `id` = ?";
        jdbcTemplate.update(sql, id);
    }
}
