package roomescape.time.repository;

import java.sql.PreparedStatement;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.time.domain.Time;

@Repository
public class H2TimeRepository implements TimeRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public H2TimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Time> findAll() {
        String sql = "select id, start_at from reservation_time";
        return jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> new Time(
                        resultSet.getLong("id"),
                        LocalTime.parse(resultSet.getString("start_at"))
                )
        );
    }

    @Override
    public Optional<Time> findById(Long id) {
        String sql = "select id, start_at from reservation_time where id = ?";
        return Optional.ofNullable(
                jdbcTemplate.queryForObject(
                        sql,
                        (resultSet, rowNum) -> new Time(
                                resultSet.getLong("id"),
                                LocalTime.parse(resultSet.getString("start_at"))
                        ),
                        id
                ));
    }

    @Override
    public Time save(Time time) {
        String sql = "insert into reservation_time (start_at) values (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    sql,
                    new String[]{"id"}
            );
            ps.setString(1, time.getStartAt().toString());
            return ps;
        }, keyHolder);

        long id = keyHolder.getKey().longValue();

        return new Time(id, time.getStartAt());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "delete from reservation_time where id = ?";
        jdbcTemplate.update(sql, id);
    }
}
