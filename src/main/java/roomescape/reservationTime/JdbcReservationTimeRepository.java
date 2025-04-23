package roomescape.reservationTime;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class JdbcReservationTimeRepository implements ReservationTimeRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcReservationTimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public ReservationTime saveReservationTime(ReservationTime wantToSaveReservationTime) {
        String query = "INSERT INTO reservation_time (start_at) values (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    query, new String[]{"id"});
            preparedStatement.setTime(1, java.sql.Time.valueOf(wantToSaveReservationTime.getStartAt()));
            return preparedStatement;
        }, keyHolder);

        return ReservationTime.toEntity(keyHolder.getKey().longValue(), wantToSaveReservationTime);
    }

    @Override
    public void deleteReservationTime(Long wantToDeleteId) {
        String query = "DELETE FROM reservation_time WHERE ID = ?";
        jdbcTemplate.update(query, wantToDeleteId);
    }

    @Override
    public List<ReservationTime> findAllReservationTimes() {
        String query = "SELECT id, start_at FROM reservation_time";

        return jdbcTemplate.query(
                query,
                (result, rowNum) -> {
                    ReservationTime reservationTime = new ReservationTime(
                            result.getLong("id"),
                            result.getTime("start_at").toLocalTime()
                    );
                    return reservationTime;
                }
        );
    }
}
