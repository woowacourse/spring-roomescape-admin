package roomescape.dao;

import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import roomescape.domain.Reservation;

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

    public List<Reservation> findAll() {
        String sql = "select * from reservation";
        return this.jdbcTemplate.query(sql,
                (resultSet, rowNum) -> {
                    String dateString = resultSet.getString("date");
                    String timeString = resultSet.getString("startAt");
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

                    return new Reservation(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            LocalDateTime.parse(dateString + " " + timeString, formatter)
                    );
                });
    }

    public Long create(Reservation reservation) {
        String sql = "insert into reservation (name, date, time) values (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        this.jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                    sql,
                    new String[]{"id"}
            );
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getDateTime().toLocalDate().toString());
            ps.setString(3, reservation.getDateTime().toLocalTime().toString());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public Long createWithMap(Reservation reservation) {
        Map<String, Object> parameters = new HashMap<>();

        parameters.put("name", reservation.getName());
        parameters.put("date", reservation.getDateTime().toLocalDate().toString());
        parameters.put("time", reservation.getDateTime().toLocalTime().toString());

        Number number = simpleJdbcInsert.executeAndReturnKey(parameters);
        return number.longValue();
    }

    public int delete(Long id) {
        String sql = "delete from reservation where id = ?";
        return this.jdbcTemplate.update(sql, id);
    }
}
