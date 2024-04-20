package roomescape.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.service.dto.ReservationCreationDto;

@Repository
public class ReservationJdbcRepository implements ReservationRepository {

    private static final RowMapper<Reservation> ROW_MAPPER = (resultSet, rowNum) ->
            new Reservation(
                    Long.valueOf(resultSet.getString("id")),
                    resultSet.getString("name"),
                    resultSet.getString("date"),
                    new ReservationTime(
                            resultSet.getLong("time_id"),
                            resultSet.getString("start_at")
                    )
            );

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public ReservationJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingColumns("name", "date", "time_id")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Reservation addReservation(ReservationCreationDto reservationCreationDto) {
        Map<String, ? extends Serializable> parameters = Map.of(
                "name", reservationCreationDto.getName(),
                "date", reservationCreationDto.getDate(),
                "time_id", reservationCreationDto.getTimeId()
        );
        Number key = jdbcInsert.executeAndReturnKey(parameters);
        return reservationCreationDto.toEntity(key.longValue());
    }

    @Override
    public List<Reservation> findAll() {
        String sql = """
                select reservation.id as id, name, date, time_id, start_at \s
                from reservation left join reservation_time \s
                on time_id = reservation_time.id
                """;
        return jdbcTemplate.query(sql, ROW_MAPPER);
    }

    @Override
    public void deleteById(Long id) {
        String sql = "delete from reservation where id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void deleteAll() {
        String sql = "delete from reservation";
        jdbcTemplate.update(sql);
    }
}
