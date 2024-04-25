package roomescape.repository.reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.repository.reservationtime.ReservationTimeRepository;

@Repository
@Primary
public class ReservationH2Repository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private final ReservationTimeRepository timeRepository;

    public ReservationH2Repository(JdbcTemplate jdbcTemplate, DataSource source,
                                   ReservationTimeRepository timeRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(source)
                .withTableName("RESERVATION")
                .usingGeneratedKeyColumns("id");
        this.timeRepository = timeRepository;
    }

    @Override
    public Reservation save(Reservation reservation) {
        Long timeId = reservation.time().id();
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", reservation.name())
                .addValue("date", reservation.date(DateTimeFormatter.ISO_DATE))
                .addValue("time_id", timeId);
        Long id = jdbcInsert.executeAndReturnKey(params).longValue();
        ReservationTime reservationTime = timeRepository.findById(timeId);

        return Reservation.of(id, reservation, reservationTime);
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM RESERVATION WHERE id = ?", id);
    }

    @Override
    public List<Reservation> findAll() {
        return jdbcTemplate.query(
                "SELECT \n"
                        + "    r.id as reservation_id, \n"
                        + "    r.name, \n"
                        + "    r.date, \n"
                        + "    t.id as time_id, \n"
                        + "    t.start_at as time_value \n"
                        + "FROM reservation as r \n"
                        + "inner join reservation_time as t \n"
                        + "on r.time_id = t.id\n",
                (resultSet, rowNum) -> {
                    ReservationTime reservationTime = new ReservationTime(
                            resultSet.getLong("time_id"),
                            LocalTime.parse(resultSet.getString("time_value"))
                    );
                    return new Reservation(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            LocalDate.parse(resultSet.getString("date")),
                            reservationTime
                    );
                }
        );
    }
}
