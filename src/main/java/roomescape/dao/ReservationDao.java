package roomescape.dao;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.dto.request.ReservationCreateRequest;
import roomescape.dto.response.ReservationResponse;

@Repository
public class ReservationDao {
    private JdbcTemplate jdbcTemplate;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");


    @Autowired
    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ReservationResponse> findAll() {
        String sql = "select * from reservation";
        return this.jdbcTemplate.query(sql,
                (resultSet, rowNum) -> {
                    String dateString = resultSet.getString("date");
                    String timeString = resultSet.getString("time");
                    return new ReservationResponse(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            LocalDate.parse(dateString, dateFormatter),
                            LocalTime.parse(timeString, timeFormatter)
                    );
                });
    }

    public int create(@Valid @RequestBody ReservationCreateRequest reservationCreateRequest) {
        String sql = "insert into reservation (name, date, time) values (?, ?, ?)";
        return this.jdbcTemplate.update(sql, reservationCreateRequest.name(), reservationCreateRequest.date(), reservationCreateRequest.time());
    }

    public int delete(@PathVariable Long id) {
        String sql = "delete from reservations where id = ?";
        return this.jdbcTemplate.update(sql, id);
    }

}
