package roomescape.time.dao;

import java.sql.PreparedStatement;
import java.time.LocalTime;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.time.domain.Time;

@Repository
public class TimeDaoImpl implements TimeDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Time insert(Time time) {
        String sql = "insert into reservation_time (start_at) values (?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    sql,
                    new String[]{"id"});
            ps.setObject(1, time.getStartAt());
            return ps;
        }, keyHolder);

        return new Time(keyHolder.getKey().longValue(), time);
    }

    @Override
    public List<Time> findAll() {
        String sql = "select * from reservation_time";

        List<Time> times =  jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> new Time(
                        resultSet.getLong("id"),
                        resultSet.getObject("start_at", LocalTime.class)
                ));

        return times;
    }

    @Override
    public void delete(long id) {
        String sql = "delete from reservation_time where id = ?";
        int result = jdbcTemplate.update(sql, id);
        if (result != 1) {
            throw new NoSuchElementException("데이터베이스에 해당 id가 존재하지 않습니다.");
        }
    }
}
