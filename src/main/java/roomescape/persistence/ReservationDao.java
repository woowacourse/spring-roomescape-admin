package roomescape.persistence;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Repository
public class ReservationDao {
    private final JdbcTemplate jdbcTemplate;

    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> selectAll() {
        String sql = "select " +
                "r.id as reservation_id, " +
                "r.name, " +
                "r.date, " +
                "t.id as time_id, " +
                "t.start_at as time_value " +
                "from reservation as r " +
                "inner join reservation_time as t " +
                "on r.time_id = t.id";
        return jdbcTemplate.query(sql, this::rowMapper);
    }

    public List<Reservation> selectAllByDateAndTime(LocalDate date, ReservationTime time) {
        String sql = "select " +
                "r.id as reservation_id, " +
                "r.name, " +
                "r.date, " +
                "t.id as time_id, " +
                "t.start_at as time_value " +
                "from reservation as r " +
                "inner join reservation_time as t " +
                "on r.time_id = t.id " +
                "where `date` = ? and t.start_at = ?";
        return jdbcTemplate.query(sql, this::rowMapper, Date.valueOf(date), Time.valueOf(time.getStartAt()));
    }

    public Long insert(Reservation reservation) {
        String sql = "insert into reservation (name, date, time_id) values (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setDate(2, Date.valueOf(reservation.getDate()));
            ps.setLong(3, reservation.getReservationTimeId());
            return ps;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public Reservation selectById(Long id) {
        String sql = "select id as reservation_id, name, date, time_id from reservation where id = ?";
        return jdbcTemplate.queryForObject(sql, this::lazyRowMapper, id);
    }

    public void deleteById(Long id) {
        String sql = "delete from reservation where id = ?";
        jdbcTemplate.update(sql, id);
    }

    private Reservation rowMapper(ResultSet resultSet, int rowNumber) throws SQLException {
        ReservationTime reservationTime = new ReservationTime(
                resultSet.getLong("time_id"),
                resultSet.getTime("time_value").toLocalTime()
        );
        return reservationMapper(resultSet, reservationTime);
    }

    private Reservation lazyRowMapper(ResultSet resultSet, int rowNumber) throws SQLException {
        ReservationTime reservationTime = new ReservationTime(resultSet.getLong("time_id"));
        return reservationMapper(resultSet, reservationTime);
    }

    private Reservation reservationMapper(ResultSet resultSet, ReservationTime reservationTime) throws SQLException {
        return new Reservation(
                resultSet.getLong("reservation_id"),
                resultSet.getString("name"),
                resultSet.getDate("date").toLocalDate(),
                reservationTime
        );
    }
}
