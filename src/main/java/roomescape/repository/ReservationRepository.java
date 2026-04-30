package roomescape.repository;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.controller.dto.ReservationCreateRequestDto;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

@Repository
public class ReservationRepository {
    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAll() {
        String sql = "select r.id as reservation_id, r.name, r.date, rt.id as time_id, rt.start_at from reservation r inner join reservation_time rt on r.time_id = rt.id";
        return jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> new Reservation(
                        resultSet.getLong("reservation_id"),
                        resultSet.getString("name"),
                        resultSet.getString("date"),
                        new ReservationTime(resultSet.getLong("time_id"), resultSet.getString("start_at"))));
    }

    public Reservation save(ReservationCreateRequestDto dto, ReservationTime find) {
        String sql = "insert into reservation(name, date, time_id) values (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement pstmt = connection.prepareStatement(sql, new String[]{"id"});

            pstmt.setString(1, dto.getName());
            pstmt.setString(2, dto.getDate());
            pstmt.setLong(3, find.getId());
            return pstmt;
        }, keyHolder);
        return new Reservation(keyHolder.getKey().longValue(), dto.getName(), dto.getDate(), find);
    }

    public void deleteById(Long id) {
        String sql = "delete from reservation where id = ?";
        jdbcTemplate.update(sql, id);
    }
}
