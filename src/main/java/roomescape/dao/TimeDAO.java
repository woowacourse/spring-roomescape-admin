package roomescape.dao;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import roomescape.model.Time;

@Repository
public class TimeDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TimeDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Time> findAllTimes() {
        return jdbcTemplate.query(
                "SELECT * FROM reservation_time",
                (resultSet, rowNum) -> new Time(
                        resultSet.getLong("id"),
                        resultSet.getString("start_at")
                ));
    }
}
