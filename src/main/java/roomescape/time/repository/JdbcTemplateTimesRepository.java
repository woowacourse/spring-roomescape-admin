package roomescape.time.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
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
}
