package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.dto.ReservationRepositoryDto;
import roomescape.repository.entity.ReservationEntity;

import java.util.List;

@Repository
public class DbReservationRepository implements ReservationRepository{

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public DbReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("RESERVATION")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public ReservationRepositoryDto add(ReservationRepositoryDto reservationRepositoryDto) {
        ReservationEntity reservationEntity = reservationRepositoryDto.toEntity();
        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(reservationEntity);
        Long id = simpleJdbcInsert.executeAndReturnKey(sqlParameterSource).longValue();
        return new ReservationRepositoryDto(id, reservationRepositoryDto.name(), reservationRepositoryDto.date(), reservationRepositoryDto.timeId());
    }

    @Override
    public List<ReservationRepositoryDto> findAll() {
        String SQL = "SELECT r.id, r.name, r.date, t.id AS time_id, t.start_at  " +
                "FROM reservation AS r " +
                "INNER JOIN reservation_time AS t " +
                "ON r.time_id = t.id";

        return jdbcTemplate.query(SQL, ReservationRepositoryDtoRowMapper());
    }

    private static RowMapper<ReservationRepositoryDto> ReservationRepositoryDtoRowMapper() {
        return (rs, rowNum) -> new ReservationRepositoryDto(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("date"),
                rs.getLong("time_id")
        );
    }

    @Override
    public int remove(Long id) {
        String SQL = "DELETE FROM RESERVATION WHERE id = ?";
        return jdbcTemplate.update(SQL, id);
    }
}
