package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.dto.ReservationTimeDto;

import java.util.List;

@Repository
public class DbReservationTimeRepository implements ReservationTimeRepository{

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public DbReservationTimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public ReservationTimeDto add(ReservationTimeDto reservationTimeDto) {
        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(reservationTimeDto);
        Long id = simpleJdbcInsert.executeAndReturnKey(sqlParameterSource).longValue();
        return new ReservationTimeDto(id, reservationTimeDto.startAt());
    }

    @Override
    public ReservationTimeDto findById(Long id) {
        String SQL = "SELECT * FROM reservation_time WHERE id = ?";
        return jdbcTemplate.queryForObject(SQL, ReservationTimeDtoRowMapper(), id);
    }

    @Override
    public List<ReservationTimeDto> findAll() {
        String SQL = "SELECT * FROM reservation_time";
        return jdbcTemplate.query(SQL, ReservationTimeDtoRowMapper());
    }

    private static RowMapper<ReservationTimeDto> ReservationTimeDtoRowMapper() {
        return (rs, rowNum) -> new ReservationTimeDto(
                    rs.getLong("id"),
                    rs.getString("start_at")
        );
    }

    @Override
    public int remove(Long id) {
        String SQL = "DELETE FROM reservation_time WHERE id = ?";
        return jdbcTemplate.update(SQL, id);
    }
}
