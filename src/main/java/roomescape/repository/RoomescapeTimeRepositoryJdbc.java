package roomescape.repository;

import java.sql.PreparedStatement;
import java.time.LocalTime;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;

@Repository
public class RoomescapeTimeRepositoryJdbc implements RoomescapeTimeRepository {

    private JdbcTemplate template;

    public RoomescapeTimeRepositoryJdbc(final JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public List<ReservationTime> findAll() {
        String sql = "select * from reservation_time";
        return template.query(sql, reservationTimeRowMapper());
    }

    @Override
    public ReservationTime saveReservationTime(final ReservationTime reservationTime) {
        String sql = "insert into reservation_time (start_at) values (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservationTime.getStartAt().toString());
            return ps;
        }, keyHolder);

        long id = keyHolder.getKey().longValue();
        return reservationTime.toEntity(id);
    }

    @Override
    public int deleteById(final long id) {
        String sql = "delete from reservation_time where id = ?";
        return template.update(sql, id);
    }

    @Override
    public void clear() {
        String sql = "delete from reservation_time";
        String resetAutoIncrementSql = "ALTER TABLE reservation_time ALTER COLUMN id RESTART WITH 1";
        template.update(sql);
        template.update(resetAutoIncrementSql);
    }

    private RowMapper<ReservationTime> reservationTimeRowMapper() {
        return (rs, rowNum) -> {
            ReservationTime reservationTime = new ReservationTime(
                    LocalTime.parse(rs.getString("start_at"))
            );
            return reservationTime;
        };
    }
}
