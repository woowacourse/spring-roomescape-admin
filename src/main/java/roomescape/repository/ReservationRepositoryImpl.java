package roomescape.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservation.ReservationRepository;
import roomescape.domain.time.Time;

import javax.sql.DataSource;
import java.util.List;

@Repository
@Primary
public class ReservationRepositoryImpl implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public ReservationRepositoryImpl(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public List<Reservation> findAll() {
        String sql = "SELECT * FROM reservation r JOIN reservation_time rt ON r.time_id = rt.id";
        RowMapper<Reservation> rowMapper = (resultSet, rowNum) -> {
            Time time = new Time(
                    resultSet.getLong("reservation_time.id"),
                    resultSet.getTime("reservation_time.start_at").toLocalTime());

            Reservation reservation = new Reservation(
                    resultSet.getLong("reservation.id"),
                    resultSet.getString("reservation.name"),
                    resultSet.getDate("reservation.date").toLocalDate(),
                    time
            );
            return reservation;
        };

        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public Reservation create(Reservation requestReservation) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", requestReservation.getName())
                .addValue("date", requestReservation.getDate())
                .addValue("time_id", requestReservation.getTime().getId());
        Long id = jdbcInsert.executeAndReturnKey(params).longValue();

        return new Reservation(
                id, requestReservation.getName(),
                requestReservation.getDate(), requestReservation.getTime());
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
