package roomescape.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationDto;

@Repository
public class ReservationDao {

    private JdbcTemplate jdbcTemplate;

    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAll() {
        String sql = """
                select  
                    r.id as reservation_id,  
                    r.name, 
                    r.date, 
                    t.id as time_id, 
                    t.start_at as time_value 
                from reservation as r 
                inner join reservation_time as t 
                on r.time_id = t.id
                """;
        return jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> {
                    ReservationTime reservationTime = new ReservationTime(
                            resultSet.getLong("time_id"),
                            resultSet.getTime("time_value")
                                    .toLocalTime());
                    Reservation reservation = new Reservation.Builder()
                            .id(resultSet.getLong("id"))
                            .name(resultSet.getString("name"))
                            .date(resultSet.getDate("date")
                                    .toLocalDate())
                            .time(reservationTime)
                            .build();
                    return reservation;
                });
    }

    public Long save(ReservationDto reservationDto) {
        String name = reservationDto.name();
        LocalDate date = reservationDto.date();
        Long timeId = reservationDto.timeId();
        String sql = "insert into reservation (name, date, time_id) values (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    sql,
                    new String[]{"id"});
            ps.setString(1, name);
            ps.setDate(2, Date.valueOf(date));
            ps.setLong(3, timeId);
            return ps;
        }, keyHolder);

        return keyHolder.getKey()
                .longValue();
    }

    public void delete(Long id) {
        String sql = "delete from reservation where id = ?";
        jdbcTemplate.update(sql, id);
    }
}
