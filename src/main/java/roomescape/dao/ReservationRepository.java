package roomescape.dao;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.dto.ReservationCreateRequest;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;

@Repository
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    @Autowired
    public ReservationRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    public List<Reservation> findAll() {
        return jdbcTemplate.query(
                "SELECT "
                        + "r.id as reservation_id, "
                        + "r.name, "
                        + "r.date, "
                        + "t.id as time_id, "
                        + "t.start_at as time_value "
                        + "FROM reservation as r "
                        + "inner join reservation_time as t "
                        + "on r.time_id = t.id",
                rowMapper
                );
    }

    private final RowMapper<Reservation> rowMapper = (resultSet, rowNum) -> new Reservation(
            resultSet.getLong("reservation_id"),
            resultSet.getString("name"),
            resultSet.getString("date"),
            new ReservationTime(resultSet.getLong("time_id"),
                    resultSet.getString("time_value")));

    public Reservation insert(ReservationCreateRequest reservationCreateRequest) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(reservationCreateRequest);
        long id = jdbcInsert.executeAndReturnKey(params).longValue();
        return new Reservation(id, reservationCreateRequest.name(),
                reservationCreateRequest.date(),
                new ReservationTime(reservationCreateRequest.timeId(),
                        jdbcTemplate.queryForObject("SELECT start_at FROM reservation_time WHERE id = ?",
                                String.class, reservationCreateRequest.timeId())));
    }

    public void delete(Long id) {
        jdbcTemplate.update("delete from reservation where id = ?", id);
    }
}
