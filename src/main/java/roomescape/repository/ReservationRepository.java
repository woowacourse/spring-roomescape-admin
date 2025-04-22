package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.ReservationCreateRequest;
import roomescape.entity.Reservation;

import java.sql.PreparedStatement;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

@Repository
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Reservation> reservationRowMapper = (row, rowNum) ->
            new Reservation(row.getLong("id"),
                    row.getString("name"),
                    row.getDate("date").toLocalDate(),
                    row.getTime("time").toLocalTime());

    public Long add(final ReservationCreateRequest request) {
        String sql = "INSERT INTO RESERVATION (NAME, DATE, TIME) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, request.name());
            ps.setDate(2, Date.valueOf(request.date()));
            ps.setTime(3, Time.valueOf(request.time()));
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public void removeById(final Long id) {
        String sql = "DELETE FROM RESERVATION WHERE ID = ?";
        int updatedRow = jdbcTemplate.update(sql, id);
        if (updatedRow == 0) {
            throw new IllegalArgumentException("해당 하는 예약이 없습니다.");
        }
    }

    public List<Reservation> findAll() {
        String sql = "SELECT * FROM RESERVATION";
        return jdbcTemplate.query(sql, reservationRowMapper);
    }
}
