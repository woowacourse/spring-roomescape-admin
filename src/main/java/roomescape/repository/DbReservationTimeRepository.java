package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.dto.ReservationRepositoryTimeDto;
import roomescape.repository.entity.ReservationTimeEntity;

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
    public ReservationRepositoryTimeDto add(ReservationRepositoryTimeDto reservationRepositoryTimeDto) {
        ReservationTimeEntity reservationTimeEntity = reservationRepositoryTimeDto.toEntity();
        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(reservationTimeEntity);
        Long id = simpleJdbcInsert.executeAndReturnKey(sqlParameterSource).longValue();
        return new ReservationRepositoryTimeDto(id, reservationRepositoryTimeDto.startAt());
    }

    @Override
    public ReservationRepositoryTimeDto findById(Long id) {
        String SQL = "SELECT * FROM reservation_time WHERE id = ?";
        return jdbcTemplate.queryForObject(SQL, ReservationTimeDtoRowMapper(), id);
    }

    @Override
    public List<ReservationRepositoryTimeDto> findAll() {
        String SQL = "SELECT * FROM reservation_time";
        return jdbcTemplate.query(SQL, ReservationTimeDtoRowMapper());
    }

    private static RowMapper<ReservationRepositoryTimeDto> ReservationTimeDtoRowMapper() {
        return (rs, rowNum) -> new ReservationRepositoryTimeDto(
                    rs.getLong("id"),
                    rs.getString("start_at")
        );
    }

    @Override
    public boolean remove(Long id) {
        String SQL = "DELETE FROM reservation_time WHERE id = ?";
        if (jdbcTemplate.update(SQL, id) > 0) {
            return true;
        }
        return false;
    }
}
