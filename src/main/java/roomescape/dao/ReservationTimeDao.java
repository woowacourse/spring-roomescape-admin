package roomescape.dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;
import roomescape.domain.ReservationTimeCommand;

@Repository
public class ReservationTimeDao {
    private static final String FAILED_ID_GENERATE = "ID 생성에 실패하였습니다.";

    private static final RowMapper<ReservationTime> MAPPER = (rs, rowNumber) -> new ReservationTime(
            rs.getLong("id"),
            rs.getString("start_at")
    );

    private final JdbcTemplate jdbcTemplate;

    public ReservationTimeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public long insertReservationTime(ReservationTimeCommand reservationTimeCommand) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO reservation_time (start_at) VALUES (?)";

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(sql, new String[] { "id" });
            statement.setString(1, reservationTimeCommand.startAt());
            return statement;
        }, keyHolder);
        
        Number key = keyHolder.getKey();
        
        if(key == null) {
            throw new RuntimeException(FAILED_ID_GENERATE);
        }

        return key.longValue();
    }

    public Optional<ReservationTime> getReservationTime(long id) {
        String sql = "SELECT id, start_at FROM reservation_time WHERE id = ?";
        return jdbcTemplate.query(sql, MAPPER, id)
                .stream()
                .findFirst();
    }

    public List<ReservationTime> getAllReservationTime() {
        String sql = "SELECT id, start_at FROM reservation_time";
        return jdbcTemplate.query(sql, MAPPER);
    }

    public int deleteReservation(long id) {
        String sql = "DELETE FROM reservation_time WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
