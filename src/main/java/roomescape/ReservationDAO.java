package roomescape;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReservationDAO {
    private JdbcTemplate jdbcTemplate;

    public ReservationDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Reserver> rowMapper = (resultSet, rowNum) -> {
        Reserver reserver = new Reserver(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getDate("date").toLocalDate(),
                resultSet.getTime("time").toLocalTime()
        );
        return reserver;
    };

    public int count() {
        String sql = "SELECT COUNT(*) FROM reservation";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public Reserver findReserverById(Long id) {
        String sql = "SELECT id, name, date, time FROM reservation WHERE id=?";
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public List<Reserver> findAllReserver() {
        String sql = "SELECT id, name, date, time FROM reservation";
        return jdbcTemplate.query(sql, rowMapper);
    }

}
