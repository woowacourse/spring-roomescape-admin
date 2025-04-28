package roomescape.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.dto.create.ReservationTimeCreate;
import roomescape.dto.read.ReservationTimeRead;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class ReservationTimeDao {

    private final JdbcTemplate jdbcTemplate;

    public ReservationTimeDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public long save(final ReservationTimeCreate reservationTimeCreate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String query = "INSERT INTO reservation_time(start_at) VALUES (?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(query, new String[]{"id"});
            ps.setString(1, reservationTimeCreate.startAt());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public List<ReservationTimeRead> getAll() {
        String query = "SELECT * FROM reservation_time";
        return jdbcTemplate.query(query, timeRowMapper());
    }

    public ReservationTimeRead findById(final Long timeId) {
        String query = "SELECT * FROM reservation_time WHERE id = ?";
        return jdbcTemplate.queryForObject(query, timeRowMapper(), timeId);
    }

    private RowMapper<ReservationTimeRead> timeRowMapper() {
        return (resultSet, rowNum) -> {
            Long id = resultSet.getLong("id");
            String time = resultSet.getString("start_at");
            return new ReservationTimeRead(id, time);
        };
    }

    public int delete(final Long id) {
        String query = "DELETE FROM reservation_time WHERE id = ?";
        int count = jdbcTemplate.update(query, id);
        return count;
    }

    public void deleteAll() {
        String query = "DELETE FROM reservation_time";
        jdbcTemplate.update(query);
    }
}
