package roomescape.repository;

import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;

@Repository
public class ReservationTimeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationTimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
    }

    public ReservationTime create(ReservationTime reservationTime) {
        Number id = simpleJdbcInsert.executeAndReturnKey(Map.of(
                "start_at", reservationTime.getStartAt()
        ));

        return reservationTime.with(id.longValue());
    }

    public List<ReservationTime> findAll() {
        String findSql = "SELECT *"
                + " FROM reservation_time";

        return jdbcTemplate.query(findSql, reservationTimeRowMapper());
    }

    public ReservationTime findById(long id) {
        String findSql = "SELECT id, start_at"
                + " FROM reservation_time"
                + " WHERE id = ?";

        return jdbcTemplate.queryForObject(
                findSql,
                reservationTimeRowMapper(),
                id
        );
    }

    public void delete(long id) {
        String deleteSql = "DELETE FROM reservation_time"
                + " WHERE id = ?";

        int deletedRows = jdbcTemplate.update(deleteSql, id);
        if (deletedRows < 1) {
            throw new IllegalArgumentException("존재하지 않는 시간 id입니다.");
        }
    }

    private RowMapper<ReservationTime> reservationTimeRowMapper() {
        return (resultSet, rowNum) -> {
            long id = resultSet.getLong("id");
            String startAt = resultSet.getString("start_at");

            return ReservationTime.retrieve(id, startAt);
        };
    }
}
