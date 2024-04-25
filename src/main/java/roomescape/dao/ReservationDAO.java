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
import roomescape.dto.ReservationCreateRequestDto;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;

@Repository
public class ReservationDAO {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    @Autowired
    public ReservationDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    public List<Reservation> findAllReservations() {
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

    public Reservation insert(ReservationCreateRequestDto reservationCreateRequestDto) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(reservationCreateRequestDto);
        long id = jdbcInsert.executeAndReturnKey(params).longValue();
        return new Reservation(id, reservationCreateRequestDto.name(),
                reservationCreateRequestDto.date(),
                new ReservationTime(reservationCreateRequestDto.timeId(),
                        jdbcTemplate.queryForObject("SELECT start_at FROM reservation_time WHERE id = ?",
                                String.class, reservationCreateRequestDto.timeId())));
    }

    public void delete(Long id) {
        jdbcTemplate.update("delete from reservation where id = ?", id);
    }
}
