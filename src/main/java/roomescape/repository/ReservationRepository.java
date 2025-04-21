package roomescape.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.dto.ReservationReadDto;
import roomescape.exception.reservation.ReservationNotFoundException;
import roomescape.model.Reservation;

@Repository
public class ReservationRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    public Long add(Reservation reservation) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", reservation.getName());
        params.put("date", reservation.getDate());
        params.put("time", reservation.getTime());

        Long id = jdbcInsert.executeAndReturnKey(params).longValue();
        return id;
    }

    public int deleteBy(Long id) {
        String sql = "delete from reservation where id = ?";
        int rowNum = jdbcTemplate.update(sql, id);
        if (rowNum == 0) {
            throw new ReservationNotFoundException(id);
        }
        return rowNum;
    }

    public List<ReservationReadDto> findAll() {
        String sql = "select * from reservation";
        List<ReservationReadDto> dtos = jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> {
                    ReservationReadDto dto = new ReservationReadDto(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            resultSet.getDate("date").toLocalDate(),
                            resultSet.getTime("time").toLocalTime()
                    );
                    return dto;
                }
        );
        return dtos;
    }
}
