package roomescape.dao;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.dto.request.ReservationCreateRequest;
import roomescape.dto.response.ReservationResponse;

@Repository
public class ReservationDao {

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public ReservationDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
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
                            LocalDate.parse(dateString),
                            LocalTime.parse(timeString)
                    );
                });
    }

    public Long create(ReservationCreateRequest reservationCreateRequest) {
        String sql = "insert into reservation (name, date, time) values (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        this.jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                    sql,
                    new String[]{"id"}
            );
            ps.setString(1, reservationCreateRequest.name());
            ps.setString(2, reservationCreateRequest.date().toString());
            ps.setString(3, reservationCreateRequest.time().toString());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public Long createWithMap(ReservationCreateRequest reservationCreateRequest) {
        Map<String, Object> parameters = new HashMap<>();

        parameters.put("name", reservationCreateRequest.name());
        parameters.put("date", reservationCreateRequest.date());
        parameters.put("time", reservationCreateRequest.time());

        Number number = simpleJdbcInsert.executeAndReturnKey(parameters);
        return number.longValue();
    }

    public int delete(Long id) {
        String sql = "delete from reservation where id = ?";
        return this.jdbcTemplate.update(sql, id);
    }
}
