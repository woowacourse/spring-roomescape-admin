package roomescape.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationDto;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class ReservationDao {

    private final JdbcTemplate jdbcTemplate;

    public ReservationDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public long save(final Reservation reservation) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String query = "INSERT INTO reservation(name, date, time_id) VALUES (?, ?, ?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(query, new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getDate().toString());
            ps.setLong(3, reservation.getTime().getId());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public List<ReservationDto> getAll() {
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

    private RowMapper<ReservationDto> reservationRowMapper() {
        return (resultSet, rowNum) -> new ReservationDto(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("date"),
                resultSet.getLong("time_id"),
                resultSet.getString("time_value")
        );
    }
}
