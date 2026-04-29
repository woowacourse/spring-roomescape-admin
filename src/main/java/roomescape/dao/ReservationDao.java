package roomescape.dao;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationCommand;
import roomescape.domain.ReservationTime;

@Repository
public class ReservationDao {
    private static final String FAILED_ID_GENERATE = "ID 생성에 실패하였습니다.";

    private static final RowMapper<Reservation> MAPPER = (rs, rowNumber) -> new Reservation(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getString("date"),
            new ReservationTime(
                    rs.getLong("timeId"),
                    rs.getString("startAt")
            )
    );

    private final JdbcTemplate jdbcTemplate;

    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> getAllReservation() {
        String sql = "SELECT r.id as id, r.name as name, r.date as date, t.id as timeId, t.start_at as startAt FROM reservation AS r JOIN reservation_time AS t ON reservation.time_id = reservation_time.id";
        return jdbcTemplate.query(sql, MAPPER);
    }

    public long insertReservation(ReservationCommand reservationCommand) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)";

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(sql, new String[] { "id" });
            statement.setString(1, reservationCommand.name());
            statement.setString(2, reservationCommand.date());
            statement.setLong(3, reservationCommand.timeId());
            return statement;
        }, keyHolder);

        Number key = keyHolder.getKey();

        if(key == null) {
            throw new RuntimeException(FAILED_ID_GENERATE);
        }
        return key.longValue();
    }

    public int deleteReservation(long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
