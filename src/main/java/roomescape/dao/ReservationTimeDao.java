package roomescape.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class ReservationTimeDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Long insert(String startAt) {
        String insertSql = "INSERT INTO reservation_time(start_at) VALUES ?";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    insertSql,
                    new String[]{"id"});
            ps.setString(1, startAt);
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public List<ReservationTime> findAll() {
        String findAllSql = "SELECT id, start_at FROM reservation_time";
        return jdbcTemplate.query(findAllSql,
                (resultSet, numRow) -> new ReservationTime(
                        resultSet.getLong("id"),
                        resultSet.getString("start_at")
                ));
    }

    public void deleteById(Long id) {
        String deleteSql = "DELETE FROM reservation_time WHERE id = ?";
        jdbcTemplate.update(deleteSql, id);
    }
}
