package roomescape.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;

@Repository
public class JdbcReservationRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public JdbcReservationRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("RESERVATION")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public List<Reservation> findAll() {
        return jdbcTemplate.query("""
                   select r.id as reservation_id,
                          r.name as name,
                          r.date as date,
                          t.id as time_id,
                          t.start_at as start_at
                   from reservation r
                   inner join reservation_time t
                   on r.time_id = t.id
                """, reservationRowMapper());
    }

    @Override
    public Reservation save(Reservation reservation) {
        String name = reservation.getName();
        LocalDate reservationDate = reservation.getReservationDate();
        ReservationTime reservationTime = reservation.getReservationTime();
        String date = reservationDate.toString();
        Long timeId = reservationTime.getId();
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", name)
                .addValue("date", date)
                .addValue("time_id", timeId);
        Long id = simpleJdbcInsert.executeAndReturnKey(params).longValue();

        return reservation.assignId(id);
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("delete from reservation where id = ?", id);
    }

    private RowMapper<Reservation> reservationRowMapper() {
        return (rs, rn) -> {
            Long reservationId = rs.getLong("reservation_id");
            String name = rs.getString("name");
            String date = rs.getString("date");
            String startAt = rs.getString("start_at");
            Long timeId = rs.getLong("time_id");
            LocalDate reservationDate = LocalDate.parse(date);
            LocalTime reservationTime = LocalTime.parse(startAt);
            ReservationTime time = ReservationTime.of(timeId, reservationTime);
            return Reservation.of(reservationId, name, reservationDate, time);
        };
    }
}
