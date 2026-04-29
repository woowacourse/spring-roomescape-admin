package roomescape.dao;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import roomescape.dto.ReservationResponse;

@Component
public class ReservationDao {

    private JdbcTemplate jdbcTemplate;

    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ReservationResponse> getReservations() {
        String sql = "SELECT * FROM reservation";

        List<ReservationResponse> reservations = jdbcTemplate.query(sql,
                (resultSet, rowNum) -> {
                    return new ReservationResponse(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            resultSet.getString("date"),
                            resultSet.getString("time")
                    );
                });

        return reservations;
    }

//    public ReservationResponse createReservation(ReservationRequest request) {
//        String sql = "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)";
//
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//
//        jdbcTemplate.update(connection -> {
//            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//            ps.setString(1, request.name());
//            ps.setString(2, request.date().toString());
//            ps.setString(3, request.time().toString());
//            return ps;
//        }, keyHolder);
//
//        return new ReservationResponse(keyHolder.getKey(), )
//    }
}
