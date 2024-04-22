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
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;

@Repository
public class ReservationTimeRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public ReservationTimeRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Long createReservationTime(ReservationTimeRequest reservationTimeRequest) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into reservation_time (start_at) values (?)",
                    new String[]{"id"});
            ps.setObject(1, reservationTimeRequest.startAt());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public ReservationTime findById(final Long id) {
        return jdbcTemplate.queryForObject("select id, start_at from reservation_time where id=?",
                (resultSet, rowNum) -> new ReservationTime(
                        resultSet.getLong("id"),
                        LocalTime.parse(resultSet.getString("start_at"))
                ), id);
    }

    public List<ReservationTime> getReservationTimes() {
        return jdbcTemplate.query("select id, start_at from reservation_time",
                (resultSet, rowNum) -> new ReservationTime(
                        resultSet.getLong("id"),
                        LocalTime.parse(resultSet.getString("start_at")
                        )));
    }


    public void deleteById(final long id) {
        jdbcTemplate.update("delete from reservation_time where id=?", id);
    }
}
