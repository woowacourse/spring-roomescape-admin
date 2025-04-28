package roomescape.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.dto.create.ReservationCreate;
import roomescape.dto.read.ReservationRead;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class ReservationDao {

    private final JdbcTemplate jdbcTemplate;

    public ReservationDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public long save(final ReservationCreate reservationCreate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String query = "INSERT INTO reservation(name, date, time_id) VALUES (?, ?, ?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(query, new String[]{"id"});
            ps.setString(1, reservationCreate.name());
            ps.setString(2, reservationCreate.date());
            ps.setLong(3, reservationCreate.timeId());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public List<ReservationRead> getAll() {
        String query = "SELECT r.id as reservation_id, r.name, r.date, t.id as time_id, t.start_at as time_value " +
                "FROM reservation r " +
                "INNER JOIN reservation_time t on r.time_id = t.id";
        return jdbcTemplate.query(query, reservationRowMapper());
    }

    public int delete(final Long id) {
        String query = "DELETE FROM reservation WHERE id = ?";
        int count = jdbcTemplate.update(query, id);
        return count;
    }

    public void deleteAll() {
        String query = "DELETE FROM reservation";
        jdbcTemplate.update(query);
    }

    public boolean isReservationExist(final String date, final Long timeId) {
        String query = "SELECT count(*) FROM reservation WHERE date = ? AND time_id = ?";
        int count = jdbcTemplate.queryForObject(query, Integer.class, date, timeId);
        return count != 0;
    }

    private RowMapper<ReservationRead> reservationRowMapper() {
        return (resultSet, rowNum) -> new ReservationRead(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("date"),
                resultSet.getLong("time_id"),
                resultSet.getString("time_value")
        );
    }
}
