package roomescape.time.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcTemplateTimesRepository implements TimesRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcTemplateTimesRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public TimeEntity saveTime(TimeEntity entity) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection ->
                        createPreparedStatement(entity, connection),
                keyHolder
        );

        long id = keyHolder.getKey().longValue();
        return entity.updateId(id);
    }

    private PreparedStatement createPreparedStatement(
            TimeEntity entity,
            Connection connection
    ) throws SQLException {
        String sql = "INSERT INTO reservation_time(start_at) VALUES (?)";

        PreparedStatement preparedStatement = connection.prepareStatement(
                sql,
                Statement.RETURN_GENERATED_KEYS
        );

        preparedStatement.setTime(1, entity.startAt());
        return preparedStatement;
    }

    @Override
    public List<TimeEntity> getTimes() {
        String sql = "SELECT * from reservation_time";
        return jdbcTemplate.query(
                sql,
                (rs, rowNum) ->
                        new TimeEntity(
                                rs.getLong("id"),
                                rs.getTime("start_at")
                        )
        );
    }

    @Override
    public void deleteTimeById(Long id) {
        String sql = "DELETE FROM reservation_time WHERE id = ?";
        int affectedRows = jdbcTemplate.update(sql, id);

        if (affectedRows == 0) {
            throw new IllegalArgumentException("해당 시간은 존재하지 않습니다.");
        }
    }
}
