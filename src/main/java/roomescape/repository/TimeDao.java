package roomescape.repository;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequest;

@Repository
public class TimeDao {
    private final JdbcTemplate jdbcTemplate;

    public TimeDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ReservationTime save(final ReservationTimeRequest reservationTimeRequest) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
                    PreparedStatement ps = connection.prepareStatement(
                            "insert into reservation_time (start_at) values (?)",
                            new String[]{"id"}
                    );
                    ps.setString(1, reservationTimeRequest.startAt().toString());
                    return ps;
                }, keyHolder
        );

        try {
            long id = keyHolder.getKey().longValue();
            return new ReservationTime(
                    id,
                    reservationTimeRequest.startAt()
            );
        } catch (NullPointerException exception) {
            throw new RuntimeException("[ERROR] 예약 시간 추가 요청이 정상적으로 이루어지지 않았습니다.");
        }
    }

    public List<ReservationTime> getAll() {
        return jdbcTemplate.query(
                "select id, start_at from reservation_time",
                (resultSet, rowNum) -> ReservationTime.of(
                        resultSet.getLong("id"),
                        resultSet.getString("start_at")
                )
        );
    }

    public void delete(final long id) {
        jdbcTemplate.update("delete from reservation where id = ?", Long.valueOf(id));
    }
}
