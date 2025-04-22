package roomescape.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.dto.ReservationResponseDto;
import roomescape.model.Reservation;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public class ReservationDao {

    private final JdbcTemplate jdbcTemplate;

    public ReservationDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ReservationResponseDto> findAllReservations() {
        String sql = "select * from reservation";
        RowMapper<ReservationResponseDto> rowMapper = (rs, rowNum) -> {
            String date = rs.getString("date");
            String time = rs.getString("time");

            ReservationResponseDto dto = new ReservationResponseDto(
                    rs.getLong("id"),
                    rs.getString("name"),
                    LocalDate.parse(date),
                    LocalTime.parse(time)
            );
            return dto;
        };

        return jdbcTemplate.query(sql, rowMapper);
    }

    public Long saveAndReturnId(final Reservation reservation) {
        String sql = "insert into reservation (name, date, time) values (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    sql, new String[]{"id"});
            ps.setString(1, reservation.name());
            ps.setString(2, reservation.date().toString());
            ps.setString(3, reservation.time().toString());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public void delete(final Long id) {
        String sql = "delete from reservation where id = ?";
        jdbcTemplate.update(sql, id);
    }
}
