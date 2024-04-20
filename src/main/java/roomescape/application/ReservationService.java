package roomescape.application;


import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;
import roomescape.application.request.CreateReservationRequest;
import roomescape.domain.Name;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

@Service
public class ReservationService {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleInsert;

    public ReservationService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleInsert = new SimpleJdbcInsert(jdbcTemplate.getDataSource())
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    public Reservation reserve(CreateReservationRequest request) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(request);
        Number key = simpleInsert.executeAndReturnKey(parameterSource);

        return new Reservation(
                key.longValue(),
                new Name(request.name()),
                request.date(),
                resolveTime(request.timeId())
        );
    }

    private ReservationTime resolveTime(Long timeId) {
        String sql = "select * from reservation_time where id = ?";
        ReservationTime reservationTime = jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new ReservationTime(
                rs.getLong("id"),
                rs.getTime("start_at").toLocalTime()
        ), timeId);

        return reservationTime;
    }

    public void deleteBy(Long id) {
        String sql = "delete from reservation where id = ?";
        jdbcTemplate.update(sql, id);
    }

    public List<Reservation> findReservations() {
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

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new Reservation(
                        rs.getLong("reservation_id"),
                        new Name(rs.getString("reservation_name")),
                        rs.getDate("reservation_date").toLocalDate(),
                        new ReservationTime(
                                rs.getLong("time_id"),
                                rs.getTime("time_value").toLocalTime()
                        )
                )
        );
    }
}
