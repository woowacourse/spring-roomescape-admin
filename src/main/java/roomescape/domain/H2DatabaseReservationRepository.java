package roomescape.domain;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class H2DatabaseReservationRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public H2DatabaseReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        String sql = "select id, name, date, time from reservation where id = ?";
        try {
            Reservation reservation = jdbcTemplate.queryForObject(sql, (resultSet, rowNum) -> new Reservation(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getDate("date").toLocalDate(),
                    resultSet.getTime("time").toLocalTime()
            ), id);
            return Optional.ofNullable(reservation);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Reservation> findAll() {
        String sql = "select id, name, date, time from reservation";
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getDate("date").toLocalDate(),
                resultSet.getTime("time").toLocalTime()
        ));
    }

    @Override
    public Reservation save(Reservation reservation) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "insert into reservation (name, date, time) values (?, ?, ?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setDate(2, java.sql.Date.valueOf(reservation.getDate()));
            ps.setTime(3, java.sql.Time.valueOf(reservation.getTime()));
            return ps;
        }, keyHolder);

        return new Reservation(keyHolder.getKey().longValue(), reservation.getName(), reservation.getDate(),
                reservation.getTime());
    }

    @Override
    public void deleteById(Long id) {
        Optional<Reservation> findReservation = findById(id);
        if (findReservation.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 예약입니다.");
        }
        String sql = "delete from reservation where id = ?";
        jdbcTemplate.update(sql, id);
    }
}
