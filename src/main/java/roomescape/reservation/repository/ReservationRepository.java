package roomescape.reservation.repository;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.ReservationSaveRequest;
import roomescape.time.domain.Time;

@Repository
public class ReservationRepository {
    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Reservation save(final ReservationSaveRequest reservationSaveRequest, final Time time) {
        String name = reservationSaveRequest.getName();
        LocalDate date = reservationSaveRequest.getDate();

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into reservation(name, date, time_id) values (?, ? ,?)", new String[]{"id"});
            ps.setString(1, name);
            ps.setString(2, date.toString());
            ps.setLong(3, time.getId());
            return ps;
        }, keyHolder);
        long reservationId = Objects.requireNonNull(keyHolder.getKey()).longValue();

        return new Reservation(reservationId, name, date, time);
    }

    public Optional<Reservation> findById(final Long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("""
                    SELECT
                    r.id,
                    r.name,
                    r.date,
                    t.id as time_id,
                    t.start_at
                    from reservation as r
                    inner join reservation_time as t
                    on r.time_id = t.id
                    where r.id = ?
                    """, createReservationRowMapper(), id));
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    public List<Reservation> findAll() {
        return jdbcTemplate.query("""
                SELECT
                r.id,
                r.name,
                r.date,
                t.id as time_id,
                t.start_at
                from reservation as r
                inner join reservation_time as t
                on r.time_id = t.id
                """, createReservationRowMapper());
    }

    private RowMapper<Reservation> createReservationRowMapper() {
        return (resultSet, rowNum) ->
                new Reservation(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getDate("date").toLocalDate(),
                        new Time(
                                resultSet.getLong("time_id"),
                                resultSet.getTime("start_at").toLocalTime()
                        )
                );
    }

    public void deleteById(final Long id) {
        jdbcTemplate.update("delete from reservation where id = ?", id);
    }
}
