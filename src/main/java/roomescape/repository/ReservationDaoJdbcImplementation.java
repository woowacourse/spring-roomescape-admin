package roomescape.repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

@Repository
public class ReservationDaoJdbcImplementation implements ReservationDao {

    private static final String SELECT_QUERY = "SELECT id, name, date, time FROM reservation;";
    private static final String INSERT_QUERY = "INSERT INTO reservation(name, date, time) VALUES (?, ?, ?)";
    private static final String DELETE_SPECIFIC_QUERY = "DELETE FROM reservation WHERE id = ?;";

    private final JdbcTemplate jdbcTemplate;
    private final ReservationEntityMapper mapper;

    public ReservationDaoJdbcImplementation(JdbcTemplate jdbcTemplate, ReservationEntityMapper mapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.mapper = mapper;
    }

    @Override
    public List<Reservation> findAll() {
        return jdbcTemplate.query(
                SELECT_QUERY,
                (resultSet, rowNum) -> {
                    ReservationEntity entity = new ReservationEntity(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            resultSet.getString("date"),
                            resultSet.getString("time")
                    );

                    return mapper.toDomain(entity);
                }
        );
    }

    @Override
    public Reservation save(Reservation reservation) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        ReservationEntity entity = mapper.toEntity(reservation);

        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, entity.name());
                    ps.setString(2, entity.date());
                    ps.setString(3, entity.time());
                    return ps;
                },
                keyHolder
        );

        Long id = Objects.requireNonNull(keyHolder.getKey()).longValue();

        return mapper.toDomain(entity.initializeId(id));
    }

    @Override
    public void delete(Long targetId) {
        int rows = jdbcTemplate.update(DELETE_SPECIFIC_QUERY, targetId);
        if (rows == 0) {
            throw new IllegalArgumentException("삭제 대상이 존재하지 않습니다");
        }
    }
}
