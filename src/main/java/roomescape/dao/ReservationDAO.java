package roomescape.dao;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import roomescape.dto.ReservationCreateRequestDto;
import roomescape.model.Reservation;

@Component
public class ReservationDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ReservationDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Reservation> findAllReservations() {
        return jdbcTemplate.query(
                "SELECT * FROM reservation",
                (resultSet, rowNum) -> new Reservation(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        LocalDate.parse(resultSet.getString("date")),
                        LocalTime.parse(resultSet.getString("time"))
                ));
    }

    public Reservation insert(ReservationCreateRequestDto reservationCreateRequestDto) {
        String sql = "INSERT INTO reservation(name, date, time) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    sql, new String[]{"id"});
            ps.setString(1, reservationCreateRequestDto.name());
            ps.setString(2, String.valueOf(reservationCreateRequestDto.date()));
            ps.setString(3, String.valueOf(reservationCreateRequestDto.time()));
            return ps;
        }, keyHolder);
        return new Reservation(keyHolder.getKey().longValue(), reservationCreateRequestDto.name(),
                reservationCreateRequestDto.date(), reservationCreateRequestDto.time());
    }

}
