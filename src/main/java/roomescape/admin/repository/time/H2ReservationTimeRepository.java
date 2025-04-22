package roomescape.admin.repository.time;

import java.sql.PreparedStatement;
import java.sql.Time;
import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.admin.domain.ReservationTime;
import roomescape.exception.DataNotFoundException;

@Repository
@RequiredArgsConstructor
public class H2ReservationTimeRepository implements ReservationTimeRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Long save(final ReservationTime reservationTime) {
        String sql = "insert into reservation_times (start_at) values (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setTime(1, Time.valueOf(reservationTime.getStartAt()));
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    @Override
    public ReservationTime getOneById(final Long id) {
        String sql = "select * from reservation_times where id = ?";
        List<ReservationTime> reservationTimes = jdbcTemplate.query(sql, (rs, rowNum) ->
                        new ReservationTime(rs.getLong("id"),
                                rs.getTime("start_at").toLocalTime()),
                id
        );

        if (reservationTimes.size() != 1) {
            throw new DataNotFoundException("해당 예약 시간 데이터가 존재하지 않습니다. id = " + id);
        }
        return reservationTimes.getFirst();
    }

    @Override
    public ReservationTime getOneByStartAt(LocalTime startAt) {
        String sql = "select * from reservation_times where start_at = ?";
        List<ReservationTime> reservationTimes = jdbcTemplate.query(sql, (rs, rowNum) ->
                        new ReservationTime(rs.getLong("id"),
                                rs.getTime("start_at").toLocalTime()),
                startAt
        );

        if (reservationTimes.size() != 1) {
            throw new DataNotFoundException("해당 예약 시간 데이터가 존재하지 않습니다. startAt = " + startAt);
        }
        return reservationTimes.getFirst();
    }

    @Override
    public List<ReservationTime> findAll() {
        String sql = "select * from reservation_times";
        return jdbcTemplate.query(sql, (resultSet, rowCount) ->
                new ReservationTime(resultSet.getLong("id"),
                        resultSet.getTime("start_at").toLocalTime())
        );
    }

    @Override
    public void delete(final ReservationTime reservationTime) {
        String sql = "delete from reservation_times where id = ?";
        jdbcTemplate.update(sql, reservationTime.getId());
    }
}
