package roomescape.times;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.dto.ReservationTimeDto;

import java.util.List;

@Repository
public class ReservationTimeRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ReservationTimeDto add(ReservationTimeDto reservationTimeDto) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(reservationTimeDto);
        Long id = simpleJdbcInsert.executeAndReturnKey(sqlParameterSource).longValue();
        return new ReservationTimeDto(id, reservationTimeDto.startAt());
    }

    public ReservationTimeDto findById(Long id) {
        String SQL = "SELECT * FROM reservation_time WHERE id = ?";
        return jdbcTemplate.queryForObject(SQL, (rs, rowNum) -> {
            ReservationTimeDto reservationTimeDto = new ReservationTimeDto(
                    rs.getLong("id"),
                    rs.getString("start_at")
            );
            return reservationTimeDto;
        }, id);
    }

    public List<ReservationTimeDto> findAll() {
        String SQL = "SELECT * FROM reservation_time";
        return jdbcTemplate.query(SQL, (rs, rowNum) -> {
            ReservationTimeDto reservationTimeDto = new ReservationTimeDto(
                    rs.getLong("id"),
                    rs.getString("start_at")
            );
            return reservationTimeDto;
        });
    }

    public void remove(Long id) {
        String SQL = "DELETE FROM reservation_time WHERE id = ?";
        jdbcTemplate.update(SQL, id);
    }
}
