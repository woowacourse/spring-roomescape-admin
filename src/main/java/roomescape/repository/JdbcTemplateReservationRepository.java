package roomescape.repository;

import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.Name;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationRepository;
import roomescape.domain.ReservationTime;

@Repository
@Transactional
class JdbcTemplateReservationRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleInsert;

    public JdbcTemplateReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleInsert = new SimpleJdbcInsert(jdbcTemplate.getDataSource())
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Reservation save(Reservation reservation) {
        Map<String, Object> saveParam = Map.of(
                "name", reservation.getName().value(),
                "date", reservation.getDate(),
                "time_id", reservation.getTime().getId()
        );
        Long savedId = simpleInsert
                .executeAndReturnKey(saveParam)
                .longValue();

        return new Reservation(
                savedId,
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );
    }

    @Override
    public void deleteBy(long id) {
        String sql = "delete from reservation where id = ?";

        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<Reservation> findAll() {
        String sql = """
                    select 
                        r.id as reservation_id,
                        r.name as reservation_name,
                        r.date as reservation_date,
                        t.id as time_id,
                        t.start_at as time_value
                    from reservation as r
                    inner join reservation_time as t
                    on r.time_id = t.id
                """;

        return jdbcTemplate.query(sql, getReservationRowMapper());
    }

    private RowMapper<Reservation> getReservationRowMapper() {
        return (rs, rowNum) ->
                new Reservation(
                        rs.getLong("reservation_id"),
                        new Name(rs.getString("reservation_name")),
                        rs.getDate("reservation_date").toLocalDate(),
                        new ReservationTime(
                                rs.getLong("time_id"),
                                rs.getTime("time_value").toLocalTime()
                        )
                );
    }
}
