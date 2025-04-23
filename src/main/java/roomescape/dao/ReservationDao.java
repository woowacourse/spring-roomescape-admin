package roomescape.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.dto.ReservationResponseDto;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public class ReservationDao {

    private static final RowMapper<Reservation> rowMapper = (rs, rowNum) -> {
        String date = rs.getString("date");
        Long timeId = rs.getLong("time_id");
        String timeValue = rs.getString("start_at");
        ReservationTime reservationTime = new ReservationTime(timeId, LocalTime.parse(timeValue));

        Reservation reservation = new Reservation(
                rs.getLong("id"),
                rs.getString("name"),
                LocalDate.parse(date),
                reservationTime
        );
        return reservation;
    };

    private final JdbcTemplate jdbcTemplate;

    public ReservationDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ReservationResponseDto> findAllReservations() {
        String sql = """
                SELECT *
                FROM reservation as r
                inner join reservation_time as t
                on r.time_id = t.id
                """;
        List<Reservation> reservations = jdbcTemplate.query(sql, rowMapper);
        return reservations.stream()
                .map(reservation -> ReservationResponseDto.from(reservation))
                .toList();
    }

    public Reservation findById(Long id) {
        String sql = """
                SELECT *
                FROM reservation as r
                inner join reservation_time as t
                on r.time_id = t.id
                where r.id = ?
                """;
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public Long saveAndReturnId(final String name, final LocalDate requestDate, final Long timeId) {
        String sql = """
                insert into reservation (name, date, time_id) values (?, ?, ?)
                """;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    sql, new String[]{"id"});
            ps.setString(1, name);
            ps.setString(2, requestDate.toString());
            ps.setLong(3, timeId);
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public void deleteById(final Long id) {
        String sql = "delete from reservation where id = ?";
        int affectedRows = jdbcTemplate.update(sql, id);

        if (affectedRows == 0) {
            throw new IllegalStateException("Reservation with id " + id + " not found");
        }
    }
}
