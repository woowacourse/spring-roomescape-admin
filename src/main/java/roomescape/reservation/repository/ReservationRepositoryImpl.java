package roomescape.reservation.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.common.exception.EntityNotFoundException;
import roomescape.reservation.entity.Reservation;
import roomescape.reservation.entity.ReservationTime;

@Repository
public class ReservationRepositoryImpl implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Reservation> findAll() {
        String sql = """
                select rs.id as reservation_id, rs.name, rs.date, rst.id as reservation_time_id, rst.start_at
                from reservation rs
                INNER JOIN reservation_time rst ON rs.time_id = rst.id
                """;

        return jdbcTemplate.query(sql,
                (resultSet, rowNum) -> getReservation(resultSet)
        );
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        String sql = """
                select rs.id as reservation_id, rs.name, rs.date, rst.id  as reservation_time_id, rst.start_at
                from reservation rs
                inner join reservation_time rst on rs.time_id = rst.id where rs.id = ?
                """;

        try {
            Reservation reservation = jdbcTemplate.queryForObject(sql,
                    (resultSet, rowNum) -> getReservation(resultSet), id);
            return Optional.ofNullable(reservation);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("entity not found");
        }
    }

    private Reservation getReservation(ResultSet resultSet) throws SQLException {
        return new Reservation(
                resultSet.getLong("reservation_id"),
                resultSet.getString("name"),
                LocalDate.parse(resultSet.getString("date")),
                getReservationTime(resultSet));
    }

    private ReservationTime getReservationTime(ResultSet resultSet) throws SQLException {
        return new ReservationTime(
                resultSet.getLong("reservation_time_id"),
                LocalTime.parse(resultSet.getString("start_at")
                ));
    }

    @Override
    public Reservation save(Reservation reservation) {

        if (reservation.existId()) {
            return update(reservation);
        }

        return create(reservation);
    }

    private Reservation create(Reservation reservation) {
        String sql = "insert into reservation (name, date, time_id) values (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getReservationDate().toString());
            ps.setLong(3, reservation.getReservationTime().getId());
            return ps;
        }, keyHolder);

        long id = keyHolder.getKey().longValue();

        return new Reservation(id, reservation.getName(), reservation.getReservationDate(),
                reservation.getReservationTime());
    }

    /**
     * 현재 구조에서는 reservation의 start_at의 변경에 대해서는 처리하지 못한다.
     * 해당 영역은 reservation 에 대해서만 처리해야 한다고 본다.
     * 우선은 요구 사항에는 update와 관련된 부분이 없으므로 보류한다.
     */
    private Reservation update(Reservation reservation) {
        String updateReservationSql = "update reservation set name = ?, date = ?, time_id = ? where id = ?";
        checkReservationTime(reservation.getReservationTime());

        int update = jdbcTemplate.update(updateReservationSql,
                reservation.getName(),
                reservation.getReservationDate(),
                reservation.getReservationTime().getId(),
                reservation.getId()
        );

        if (update == 0) {
            throw new EntityNotFoundException("Reservation with id " + reservation.getId() + " not found");
        }

        return reservation;
    }

    private void checkReservationTime(ReservationTime reservationTime) {
        if (reservationTime == null) {
            throw new EntityNotFoundException("reservationTime is null");
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "delete from reservation where id = ?";

        int result = jdbcTemplate.update(sql, id);

        if (result != 1) {
            throw new EntityNotFoundException("Reservation with id " + id + " not found");
        }
    }
}
