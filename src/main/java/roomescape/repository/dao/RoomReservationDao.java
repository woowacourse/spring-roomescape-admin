package roomescape.repository.dao;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.RoomReservation;
import roomescape.repository.entity.RoomReservationEntity;

@Repository
public class RoomReservationDao {

    private static final RowMapper<RoomReservationEntity> reservationRowMapper = (rs, rowNum) ->
            new RoomReservationEntity(
                    rs.getLong("id"),
                    rs.getString("name"),
                    LocalDate.parse(rs.getString("date")),
                    LocalTime.parse(rs.getString("time"), DateTimeFormatter.ofPattern("HH:mm"))
            );
    private final JdbcTemplate jdbcTemplate;

    public RoomReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<RoomReservationEntity> selectAll() {
        String sql = "select * from reservation;";
        return jdbcTemplate.query(sql, reservationRowMapper);
    }

    public Long insert(RoomReservation roomReservation) {
        String sql = "insert into reservation (name, date, time) values (?,?,?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement pstm = connection.prepareStatement(sql, new String[]{"id"});
            pstm.setString(1, roomReservation.getName());
            pstm.setString(2, String.valueOf(roomReservation.getDate()));
            pstm.setString(3, String.valueOf(roomReservation.getTime()));
            return pstm;
        }, keyHolder);

        Number key = keyHolder.getKey();
        if (key == null) {
            throw new IllegalStateException("ID 값이 생성되지 않았습니다.");
        }
        return key.longValue();
    }

    public int deleteById(Long id) {
        String sql = "delete from reservation where id = ?;";
        return jdbcTemplate.update(sql, id);
    }
}
