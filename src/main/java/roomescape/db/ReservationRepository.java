package roomescape.db;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationRequest;


@Repository
public class ReservationRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    ReservationTimeRepository reservationTimeRepository;

    public ReservationRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Long createReservation(ReservationRequest reservationRequest) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into reservation (name,date,time_id) values (?,?,?)",
                    new String[]{"id"});
            ps.setString(1, reservationRequest.name());
            ps.setObject(2, reservationRequest.date());
            ps.setLong(3, reservationRequest.timeId());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public Reservation findById(final Long id) {
        return jdbcTemplate.queryForObject("select id, name, date, time_id from reservation where id=?",
                (resultSet, rowNum) -> new Reservation(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        LocalDate.parse(resultSet.getString("date")),
                        reservationTimeRepository.findById(resultSet.getLong("time_id"))
                ), id);
    }

    public List<Reservation> getReservations() {
        return jdbcTemplate.query("SELECT \n"
                        + "    r.id as reservation_id, \n"
                        + "    r.name, \n"
                        + "    r.date, \n"
                        + "    t.id as time_id, \n"
                        + "    t.start_at as time_value \n"
                        + "FROM reservation as r \n"
                        + "inner join reservation_time as t \n"
                        + "on r.time_id = t.id\n",
                (resultSet, rowNum) -> new Reservation(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        LocalDate.parse(resultSet.getString("date")),
                        reservationTimeRepository.findById(resultSet.getLong("time_id"))
                ));
    }

    public void deleteById(final long id) {
        jdbcTemplate.update("delete from reservation where id=?", id);
    }
}
