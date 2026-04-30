package roomescape.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

@Repository
@RequiredArgsConstructor
public class ReservationDao {

    private final JdbcTemplate jdbcTemplate;

    private static final RowMapper<Reservation> ROW_MAPPER = (resultSet, rowNum) -> {
        ReservationTime time = ReservationTime.builder()
                .id(resultSet.getLong("time_id"))
                .startAt(resultSet.getObject("time_start_at", LocalTime.class))
                .build();

        return Reservation.builder()
                .id(resultSet.getLong("reservation_id"))
                .name(resultSet.getString("reservation_name"))
                .date(resultSet.getObject("reservation_date", LocalDate.class))
                .time(time)
                .build();
    };

    public List<Reservation> findAll() {
        String sql = """
                SELECT r.id       AS reservation_id,
                       r.name     AS reservation_name,
                       r.date     AS reservation_date,
                       t.id       AS time_id,
                       t.start_at AS time_start_at
                FROM reservation r
                    INNER JOIN reservation_time t ON r.time_id = t.id
                """;
        return jdbcTemplate.query(sql, ROW_MAPPER);
    }

    public Reservation save(Reservation reservation) {
        String sql = "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setDate(2, Date.valueOf(reservation.getDate()));
            ps.setLong(3, reservation.getTime().getId());
            return ps;
        }, keyHolder);

        Long generatedId = keyHolder.getKey().longValue();
        reservation.assignId(generatedId);
        return reservation;
    }

    public int deleteById(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
