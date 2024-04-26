package roomescape;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public class ReservationDao {

    private  JdbcTemplate jdbcTemplate;

    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Reservation> reservationRowMapper=(resultSet,rowNum)-> new Reservation(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            LocalDate.parse(resultSet.getString("date")),
            LocalTime.parse(resultSet.getString("time"))
    );

    public List<Reservation> findAll(){
        String sql="select * from reservation";
        return jdbcTemplate.query(sql,reservationRowMapper);
    }
}
