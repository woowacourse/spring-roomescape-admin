package roomescape.persistence.jdbctemplate;

import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Name;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationRepository;
import roomescape.domain.ReservationTime;

@Repository
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
    public Reservation saveOne(Reservation reservation) {
        ReservationTime time = resolveTime(reservation.getTime());
        Map<String, Object> saveParam = Map.of(
                "name", reservation.getName(),
                "date", reservation.getDate(),
                "time_id", time.getId()
        );
        Long savedId = simpleInsert
                .executeAndReturnKey(saveParam)
                .longValue();

        return new Reservation(
                savedId,
                reservation.getName(),
                reservation.getDate(),
                time
        );
    }

    private ReservationTime resolveTime(ReservationTime time) {
        String sql = "select * from reservation_time where id = ?";
        ReservationTime reservationTime = jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new ReservationTime(
                rs.getLong("id"),
                rs.getTime("start_at").toLocalTime()
        ), time.getId());

        return reservationTime;
    }

    @Override
    public void deleteBy(Long id) {
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
