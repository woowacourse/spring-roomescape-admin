package roomescape.repository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.dto.CreateReservationDto;
import roomescape.entity.Reservation;

@Repository
public class ReservationRepository {
    private JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAll() {
        String sql = "SELECT * FROM reservation";
        return jdbcTemplate.query(sql,
                (resultSet, rowNum) -> new Reservation(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getObject("dateTime", LocalDateTime.class)
                ));
    }

    public Reservation add(CreateReservationDto createReservationDto) {
        SimpleJdbcInsert insertQuery = new SimpleJdbcInsert(jdbcTemplate.getDataSource())
                .withTableName("reservation")
                .usingColumns("name", "dateTime")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", createReservationDto.name());
        parameters.put("dateTime", LocalDateTime.of(
                createReservationDto.date(),
                createReservationDto.time()
        ));
        Long id = insertQuery.executeAndReturnKey(parameters).longValue();

        String selectSql = "SELECT * FROM reservation WHERE id = ?";
        Reservation reservation = jdbcTemplate.queryForObject(selectSql,
                (resultSet, rowNum) -> new Reservation(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getObject("dateTime", LocalDateTime.class)
                ),
                id);
        return reservation;
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
