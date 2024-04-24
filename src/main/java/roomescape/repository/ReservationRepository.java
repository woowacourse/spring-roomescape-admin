package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.dto.ReservationDto;
import roomescape.entity.Reservation;
import roomescape.entity.ReservationTime;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class ReservationRepository {
    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Reservation> rowMapper = (resultSet, rowNum) -> new Reservation(
            resultSet.getLong("reservation_id"),
            resultSet.getString("name"),
            resultSet.getDate("date").toLocalDate(),
            new ReservationTime(resultSet.getLong("time_id"),
                    resultSet.getTime("time_value").toLocalTime())
    );

    public long create(ReservationDto reservationDto) {
        String sql = "INSERT INTO reservation (name, date, time_id) VALUES(?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservationDto.name());
            ps.setDate(2, Date.valueOf(reservationDto.date()));
            ps.setLong(3, reservationDto.timeId());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public Reservation read(long reservationId) {
        String sql = "SELECT r.id AS reservation_id, " +
                "r.name, " +
                "r.date, " +
                "t.id AS time_id, " +
                "t.start_at AS time_value " +
                "FROM reservation AS r " +
                "INNER JOIN reservation_time AS t " +
                "ON r.time_id = t.id " +
                "WHERE r.id = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper, reservationId);
    }

    public List<Reservation> readAll() {
        String sql = "SELECT r.id AS reservation_id, " +
                "r.name, " +
                "r.date, " +
                "t.id AS time_id, " +
                "t.start_at AS time_value " +
                "FROM reservation AS r " +
                "INNER JOIN reservation_time AS t " +
                "ON r.time_id = t.id";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public void delete(long reservationId) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, reservationId);
    }
}
