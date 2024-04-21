package roomescape.repository.reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

@Repository
public class JdbcReservationRepository implements ReservationRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcReservationRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public List<Reservation> findAllReservations() {
        String sql = """
                SELECT 
                r.id as reservation_id, 
                r.name, 
                r.date, 
                t.id as time_id, 
                t.start_at as time_value 
                FROM reservation as r 
                inner join reservation_time as t on r.time_id = t.id;
                """;
        return jdbcTemplate.query(sql, reservationRowMapper());
    }

    @Override
    public Reservation insertReservation(Reservation reservation) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("name", reservation.getName())
                .addValue("date", reservation.getDate())
                .addValue("time_id", reservation.getTime().getId());
        Long savedId = jdbcInsert.executeAndReturnKey(parameterSource).longValue();
        return findReservationById(savedId);
    }

    @Override
    public void deleteReservationById(Long id) {
        String sql = "DELETE FROM reservation where id = :id";
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id);
        jdbcTemplate.update(sql, parameterSource);
    }

    private Reservation findReservationById(Long savedId) {
        String sql = """
                SELECT 
                r.id as reservation_id, 
                r.name, 
                r.date, 
                t.id as time_id, 
                t.start_at as time_value 
                FROM reservation as r 
                inner join reservation_time as t on r.time_id = t.id 
                WHERE r.id = :savedId;
                """;
        SqlParameterSource paramMap = new MapSqlParameterSource().addValue("savedId", savedId);
        return jdbcTemplate.query(sql, paramMap, reservationRowMapper()).get(0);
    }

    private RowMapper<Reservation> reservationRowMapper() {
        return (resultSet, rowNum) -> new Reservation(
                resultSet.getLong("reservation_id"),
                resultSet.getString("name"),
                LocalDate.parse(resultSet.getString("date")),
                new ReservationTime(
                        resultSet.getLong("time_id"),
                        LocalTime.parse(resultSet.getString("time_value"))
                ));
    }
}
