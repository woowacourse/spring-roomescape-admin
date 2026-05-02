package roomescape.dao;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Name;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

@Repository
public class ReservationDao {
    private final JdbcTemplate jdbcTemplate;

    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) -> {
        Reservation reservation = new Reservation(
                resultSet.getLong("reservation_id"),
                Name.parse(resultSet.getString("name")),
                LocalDate.parse(resultSet.getString("date")),
                new ReservationTime(
                        resultSet.getLong("time_id"),
                        LocalTime.parse(resultSet.getString("time_value"))
                )
        );
        return reservation;
    };

    public List<Reservation> findAll() {
        String sql = """
                select 
                    r.id as reservation_id,
                    r.name as name,
                    r.date as date,
                    t.id as time_id,
                    t.start_at as time_value
                from reservations as r
                inner join reservation_times as t
                    on r.time_id = t.id
                """;
        return jdbcTemplate.query(sql, reservationRowMapper);
    }

    public Reservation insertReservation(Reservation reservation) {
        String createSql = "insert into reservations (name, date, time_id) values (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(createSql, new String[]{"id"});
            ps.setString(1, reservation.getName().toString());
            ps.setString(2, reservation.getDate().toString());
            ps.setLong(3, reservation.getTime().getId());
            return ps;
        }, keyHolder);

        Long generatedId = Objects.requireNonNull(keyHolder.getKey()).longValue();

        return new Reservation(
                generatedId,
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );
    }

    public void deleteById(Long id) {
        String sql = "delete from reservations where id = ?";

        jdbcTemplate.update(sql, id);
    }
}
