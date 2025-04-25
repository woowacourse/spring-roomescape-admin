package roomescape.adapter.ReservationTime;

import java.sql.PreparedStatement;
import java.time.LocalTime;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;
import roomescape.usecase.ReservationTime.ReservationTimeOutput;
import roomescape.usecase.ReservationTime.ReservationTimeRepository;

@Repository
public class ReservationTimeRepositoryImpl implements ReservationTimeRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationTimeRepositoryImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public ReservationTimeOutput addReservationTime(ReservationTime reservationTime) {
        String sql = "insert into reservation_time(start_at) values(?) ";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update((connection) -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, String.valueOf(reservationTime.getStart_at()));

            return ps;
        }, keyHolder);
        long id = keyHolder.getKey().longValue();
        return new ReservationTimeOutput(id, reservationTime.getStart_at());
    }

    @Override
    public List<ReservationTimeOutput> getAllReservationTimes() {
        String sql = "select * from reservation_time";
        return jdbcTemplate.query(sql, (resultSet, rowNUm) -> {
            return new ReservationTimeOutput(
                    resultSet.getLong("id"),
                    LocalTime.parse(resultSet.getString("start_at"))
            );
        });
    }

    @Override
    public ReservationTime getReservationTime(final Long timeId) {
        String sql = "select * from reservation_time where id = ?";
        return jdbcTemplate.queryForObject(sql, (resultSet, rowNUm) -> {
            return new ReservationTime(
                    resultSet.getLong("id"),
                    LocalTime.parse(resultSet.getString("start_at"))
            );
        }, timeId);

    }

    @Override
    public void deleteReservationTime(final long id) {
        String sql = "delete from reservation_time where id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public boolean existsById(final long id) {
        String sql = "SELECT COUNT(*) FROM reservation_time WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

//
//    @DeleteMapping("/times/{id}")
//    public ResponseEntity<Void> deleteReservationTime(@PathVariable long id) {
//        String sql = "delete from reservation_time where id = ?";
//        jdbcTemplate.update(sql, id);
//        return ResponseEntity.ok().build();
//    }
}
