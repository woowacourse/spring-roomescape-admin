package roomescape.dao;

import java.sql.PreparedStatement;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.dto.ReservationCreateRequestDto;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;

@Repository
public class ReservationDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ReservationDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
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
        String sql = "INSERT INTO reservation(name, date, time_id) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    sql, new String[]{"id"});
            ps.setString(1, reservationCreateRequestDto.name());
            ps.setString(2, String.valueOf(reservationCreateRequestDto.date()));
            ps.setString(3, String.valueOf(reservationCreateRequestDto.timeId()));
            return ps;
        }, keyHolder);
        return new Reservation(keyHolder.getKey().longValue(), reservationCreateRequestDto.name(),
                reservationCreateRequestDto.date(),
                new ReservationTime(reservationCreateRequestDto.timeId(),
                        jdbcTemplate.queryForObject("SELECT start_at FROM reservation_time WHERE id = ?",
                                String.class, reservationCreateRequestDto.timeId())));
    }

    public void delete(Long id) {
        jdbcTemplate.update("delete from reservation where id = ?", id);
    }
}
