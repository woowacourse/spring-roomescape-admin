package roomescape.reservation.infrastructure;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.common.jdbc.JdbcUtils;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.domain.ReservationRepository;
import roomescape.reservation.infrastructure.entity.ReservationEntity;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Primary
@Repository
public class H2ReservationRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<ReservationEntity> reservationMapper = (resultSet, rowNum) -> ReservationEntity.of(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            resultSet.getTimestamp("date_time").toLocalDateTime()
    );

    public H2ReservationRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Reservation> findById(final long id) {
        final String sql = "select id, name, date_time from reservation where id = ?";

        return JdbcUtils.queryForOptional(jdbcTemplate, sql, reservationMapper, id)
                .map(ReservationEntity::toDomain);
    }

    @Override
    public List<Reservation> findAll() {
        final String sql = "select id, name, date_time from reservation";

        return jdbcTemplate.query(sql, reservationMapper).stream()
                .map(ReservationEntity::toDomain)
                .toList();
    }

    @Override
    public Reservation save(final Reservation reservation) {
        final String sql = "insert into reservation (name, date_time) values (?, ?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            final PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, reservation.getName());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(reservation.getDatetime()));

            return preparedStatement;
        }, keyHolder);

        final long generatedId = Objects.requireNonNull(keyHolder.getKey()).longValue();

        return Reservation.of(generatedId, reservation.getName(), reservation.getDatetime());
    }

    @Override
    public void deleteById(final long id) {
        final String sql = "delete from reservation where id = ?";
        jdbcTemplate.update(sql, id);
    }
}
