package roomescape.db;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
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

    public List<Reservation> findAll() {
        return jdbcTemplate.query("""
                        SELECT
                            r.id as reservation_id,
                            r.name,
                            r.date,
                            t.id as time_id,
                            t.start_at as time_value
                        FROM reservation as r
                        inner join reservation_time as t
                        on r.time_id = t.id
                        """,
                (resultSet, rowNum) -> new Reservation(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        LocalDate.parse(resultSet.getString("date")),
                        reservationTimeRepository.findById(resultSet.getLong("time_id"))
                ));
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

    public void deleteById(final long id) {
        jdbcTemplate.update("delete from reservation where id=?", id);
    }
}
