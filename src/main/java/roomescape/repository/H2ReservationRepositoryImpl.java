package roomescape.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

import javax.sql.DataSource;
import java.util.List;

@Primary
@Repository
public class H2ReservationRepositoryImpl implements ReservationRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public H2ReservationRepositoryImpl(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public List<Reservation> findAll() {
        String sql = "select * from reservation";

        return jdbcTemplate.query(sql, (resultSet, rowNum) -> new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getDate("date").toLocalDate(),
                resultSet.getTime("time").toLocalTime()
        ));
    }

    @Override
    public Reservation save(Reservation reservation) {
        SqlParameterSource source = new BeanPropertySqlParameterSource(reservation);
        long reservationId = jdbcInsert.executeAndReturnKey(source).longValue();

        return new Reservation(
                reservationId,
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );
    }

    @Override
    public void deleteById(long id) {
        String sql = "delete from reservation where id = ?";

        jdbcTemplate.update(sql, id);
    }
}
