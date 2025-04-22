package roomescape.repository;

import java.util.List;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import roomescape.entity.Reservation;
import roomescape.exceptions.EntityNotFoundException;

@Primary
@Repository
public class ReservationH2Repository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationH2Repository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Reservation> findAll() {
        String sql = "select * from reservation";
        return jdbcTemplate.query(sql, getReservationRowMapper());
    }

    @Override
    public void save(Reservation reservation) {
        String sql = "insert into reservation (name, date, time) values (?, ?, ?)";
        jdbcTemplate.update(sql, reservation.name(), reservation.date(), reservation.time());
    }

    @Override
    public void deleteById(long id) {
        String sql = "delete from reservation where id=?";
        int result = jdbcTemplate.update(sql, id);
        if (result == 0) {
            throw new EntityNotFoundException("예약 데이터를 찾을 수 없습니다:" + id);
        }
    }

    private RowMapper<Reservation> getReservationRowMapper() {
        return (resultSet, rowNum) -> new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getDate("date").toLocalDate(),
                resultSet.getTime("time").toLocalTime()
        );
    }
}
