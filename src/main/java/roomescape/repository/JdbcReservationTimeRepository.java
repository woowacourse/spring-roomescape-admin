package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.time.format.DateTimeFormatter;

@Repository
public class JdbcReservationTimeRepository implements ReservationTimeRepository {
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    private final JdbcTemplate template;

    public JdbcReservationTimeRepository(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public ReservationTime createReservationTime(ReservationTime reservationTime) {
        String sql = "INSERT INTO reservation_time(start_at) VALUES (?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        template.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, timeFormatter.format(reservationTime.getStartAt()));
            return ps;
        }, keyHolder);

        long key = keyHolder.getKey().longValue();
        return new ReservationTime(key, timeFormatter.format(reservationTime.getStartAt())); // QUESTION: 이럴 때 그냥 객체 새로 만들어서 보내면 되는 건지 아니면 만들어진걸 조회해서 보내야 하는 건지
    }

    private RowMapper<ReservationTime> reservationTimeRowMapper() {
        return ((rs, rowNum) -> {
            ReservationTime reservationTime = new ReservationTime(
                    rs.getLong("id"),
                    rs.getString("start_at"));
            return reservationTime;
        });
    }
}
