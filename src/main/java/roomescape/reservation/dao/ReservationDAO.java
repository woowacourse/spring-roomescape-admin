package roomescape.reservation.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.reservation.exception.ReservationNotFoundException;
import roomescape.reservation.model.Reservation;
import roomescape.reservation.model.ReservationTime;

import java.sql.PreparedStatement;
import java.time.LocalTime;
import java.util.List;

@Repository
public class ReservationDAO {

    private final JdbcTemplate jdbcTemplate;

    public ReservationDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Reservation insert(Reservation reservation) {
        Long reservationId = insertWithKeyHolder(reservation);
        return new Reservation(reservationId, reservation.getName(), reservation.getDate(), reservation.getTime());
    }

    private Long insertWithKeyHolder(Reservation reservation) {
        String sql = "insert into reservation (name, date, time_id) values (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getDate().toString());
            ps.setLong(3, reservation.getTime().getId());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public Reservation selectBy(Long id) {
        String sql = """
                select r.id, r.name, r.date, rt.id as time_id, rt.start_at as time_value
                from reservation r
                inner join reservation_time rt
                on r.time_id = rt.id
                where r.id = ?
                """;
        try {
            return jdbcTemplate.queryForObject(sql, rowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            throw new ReservationNotFoundException(id, e);
        }
    }

    public List<Reservation> selectAll() {
        String sql = """
                select r.id, r.name, r.date, rt.id as time_id, rt.start_at as time_value
                from reservation r
                inner join reservation_time rt
                on r.time_id = rt.id
                """;

        return jdbcTemplate.query(sql, rowMapperWithJoin());
    }

    public void deleteBy(Long id) {
        String sql = "delete from reservation where id = ?";
        int deletedCount = jdbcTemplate.update(sql, id);
        if (deletedCount == 0) {
            throw new ReservationNotFoundException(id);
        }
    }

    private RowMapper<Reservation> rowMapperWithJoin() {
        return (rs, rowNum) -> {
            long timeId = rs.getLong("time_id");
            LocalTime startAt = rs.getTime("time_value").toLocalTime();
            ReservationTime time = new ReservationTime(timeId, startAt);

            return new Reservation(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getDate("date").toLocalDate(),
                    time
            );
        };
    }

    private RowMapper<Reservation> rowMapper() {
        return (rs, rowNum) -> new Reservation(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getDate("date").toLocalDate(),
                new ReservationTime(rs.getLong("time_id"), rs.getTime("time_value").toLocalTime())
        );
    }
}
