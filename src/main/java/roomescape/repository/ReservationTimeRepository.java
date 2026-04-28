package roomescape.repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;

@Repository
public class ReservationTimeRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationTimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ReservationTime create(ReservationTime reservationTime) {
        String createSql = "INSERT INTO reservation_time (start_at) VALUES (?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(createSql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, reservationTime.getStartAt());

            return statement;
        }, keyHolder);

        Number id = keyHolder.getKey();
        validateNotNull(id);

        return reservationTime.with(id.longValue());
    }

    public List<ReservationTime> findAll() {
        String findSql = "SELECT * FROM reservation_time";

        return jdbcTemplate.query(findSql, reservationTimeRowMapper());
    }

    public void delete(long id) {
        String deleteSql = "DELETE FROM reservation_time WHERE id = ?";

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

    private void validateNotNull(Number id) {
        if (id == null) {
            throw new InvalidDataAccessApiUsageException("ID 조회에 실패했습니다.");
        }
    }
}
