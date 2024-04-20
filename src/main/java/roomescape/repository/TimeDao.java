package roomescape.repository;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.dto.Time;
import roomescape.dto.TimeRequest;

@Repository
public class TimeDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Time save(final TimeRequest timeRequest) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
                    PreparedStatement ps = connection.prepareStatement(
                            "insert into reservation_time (start_at) values (?)",
                            new String[]{"id"}
                    );
                    ps.setString(1, timeRequest.startAt().toString());
                    return ps;
                }, keyHolder
        );

        long id = keyHolder.getKey().longValue();
        return new Time(
                id,
                timeRequest.startAt()
        );
    }

    public List<Time> getAll() {
        return jdbcTemplate.query(
                "select id, start_at from reservation_time",
                (resultSet, rowNum) -> Time.of(
                        resultSet.getLong("id"),
                        resultSet.getString("start_at")
                )
        );
    }

    public void delete(final long id) {
        jdbcTemplate.update("delete from reservation where id = ?", Long.valueOf(id));
    }
}
