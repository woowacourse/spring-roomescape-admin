package roomescape.repository;

import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;
import roomescape.domain.ReservationTimeRepository;

@Repository
public class JdbcReservationTimeRepositoryImpl implements ReservationTimeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public JdbcReservationTimeRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(
                Objects.requireNonNull(jdbcTemplate.getDataSource()))
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public ReservationTime findById(Long id) {
        String sql = "SELECT * FROM reservation_time WHERE id = ?";

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new ReservationTime(
                rs.getLong("id"),
                rs.getTime("start_at").toLocalTime()
        ), id);
    }

    @Override
    public void deleteById(Long id) {
        String sql = "delete from reservation_time where id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<ReservationTime> findAll() {
        String sql = "select * from reservation_time";

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> new ReservationTime(
                        rs.getLong("id"),
                        LocalTime.parse(rs.getString("start_at"))
                ));
    }
}
