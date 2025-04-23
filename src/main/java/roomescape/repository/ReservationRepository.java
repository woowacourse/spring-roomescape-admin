package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.controller.dto.ReservationCreateRequest;
import roomescape.entity.Reservation;
import roomescape.entity.ReservationTime;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Reservation> reservationRowMapper = (row, rowNum) ->
            new Reservation(row.getLong("id"),
                    row.getString("name"),
                    row.getDate("date").toLocalDate(),
                    new ReservationTime(row.getLong("time_id"), row.getTime("time_value").toLocalTime())
            );

    public Long add(final Reservation reservation) {
        String sql = "INSERT INTO RESERVATION (NAME, DATE, TIME_ID) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setString(2, String.valueOf(reservation.getDate()));
            ps.setLong(3, reservation.getTime().getId());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public Reservation findById(final Long id) {
        String sql = "SELECT r.id as reservation_id, r.name, r.date, t.id as time_id, t.start_at as time_value " +
                "FROM reservation as r inner join reservation_time as t on r.time_id = t.id " +
                "WHERE r.id = ?";
        return jdbcTemplate.queryForObject(sql, reservationRowMapper, id);
    }

    public void deleteById(final Long id) {
        String sql = "DELETE FROM RESERVATION WHERE ID = ?";
        int updatedRow = jdbcTemplate.update(sql, id);
        if (updatedRow == 0) {
            throw new IllegalArgumentException("해당 하는 예약이 없습니다.");
        }
    }

    public List<Reservation> findAll() {
        String sql = "SELECT r.id as reservation_id, r.name, r.date, t.id as time_id, t.start_at as time_value " +
                "FROM reservation as r inner join reservation_time as t on r.time_id = t.id";
        return jdbcTemplate.query(sql, reservationRowMapper);
    }
}
