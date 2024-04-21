package roomescape.domain;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class H2ReservationRepository implements ReservationRepository {
    private final JdbcTemplate jdbcTemplate;

    public H2ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        String sql = "select id, name, date, time_id from reservation where id = ?";
        try {
            Reservation reservation = jdbcTemplate.queryForObject(sql, (resultSet, rowNum) -> new Reservation(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getDate("date").toLocalDate(),
                    getReservationTime(resultSet.getLong("time_id"))
            ), id);
            return Optional.ofNullable(reservation);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private ReservationTime getReservationTime(long timeId) {
        return jdbcTemplate.queryForObject("select id, start_at from reservation_time where id = ?",
                (resultSet, rowNum) -> new ReservationTime(resultSet.getLong("id"),
                        resultSet.getTime("start_at").toLocalTime()), timeId
        );
    }

    @Override
    public List<Reservation> findAll() {
        String sql = "SELECT \n"
                + "    r.id as reservation_id, \n"
                + "    r.name, \n"
                + "    r.date, \n"
                + "    t.id as time_id, \n"
                + "    t.start_at as time_value \n"
                + "FROM reservation as r \n"
                + "inner join reservation_time as t \n"
                + "on r.time_id = t.id";
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> new Reservation(
                resultSet.getLong("reservation_id"),
                resultSet.getString("name"),
                resultSet.getDate("date").toLocalDate(),
                new ReservationTime(
                        resultSet.getLong("time_id"),
                        resultSet.getTime("time_value").toLocalTime()
                ))
        );
    }

    @Override
    public Reservation save(Reservation reservation) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "insert into reservation (name, date, time_id) values (?, ?, ?)";
        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
                ps.setString(1, reservation.getName());
                ps.setDate(2, java.sql.Date.valueOf(reservation.getDate()));
                ps.setLong(3, reservation.getTime().getId());
                return ps;
            }, keyHolder);

            return new Reservation(keyHolder.getKey().longValue(), reservation.getName(), reservation.getDate(),
                    reservation.getTime());
        } catch (DuplicateKeyException e) {
            throw new IllegalArgumentException("이미 존재하는 예약입니다.");
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "delete from reservation where id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public Long findReservationCountByTimeId(Long timeId) {
        String sql = "select count(*) from reservation where time_id = ?";
        return jdbcTemplate.queryForObject(sql, Long.class, timeId);
    }
}
