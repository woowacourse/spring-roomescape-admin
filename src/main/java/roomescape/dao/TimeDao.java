package roomescape.dao;

import java.sql.PreparedStatement;
import java.time.LocalTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Time;

@Repository
public class TimeDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Time> findAll() {
        String sql = "select * from reservation_time";
        return this.jdbcTemplate.query(sql,
                (resultSet, rowNum) -> {
                    String timeString = resultSet.getString("start_at");

                    return new Time(
                            resultSet.getLong("id"),
                            LocalTime.parse(timeString)
                    );
                });
    }

    public Long create(Time time) {
        String sql = "insert into reservation_time (start_at) values (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        this.jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                    sql,
                    new String[]{"id"}
            );
            LocalTime startAt = time.getStartAt();
            ps.setString(1, startAt.toString());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public int delete(Long id) {
        String sql = "delete from reservation_time where id = ?";
        return this.jdbcTemplate.update(sql, id);
    }
}
