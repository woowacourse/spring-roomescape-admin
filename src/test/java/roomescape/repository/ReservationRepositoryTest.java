package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

@SpringBootTest
@Transactional
class ReservationRepositoryTest {

    private static final long DEFAULT_ID = 1;
    private static final long NOT_EXIST_ID = 999;
    private static final String DEFAULT_NAME = "name";
    private static final String DEFAULT_DATE = "2025-01-01";
    private static final String DEFAULT_START_AT = "00:00";

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Nested
    class 예약을_저장한다 {

        @Test
        void 새로운_예약_정보를_저장한다() {
            // given
            ReservationTime time = persistTime(DEFAULT_START_AT);
            Reservation transientReservation = Reservation.create(
                    DEFAULT_NAME,
                    DEFAULT_DATE,
                    time
            );

            // when
            reservationRepository.create(transientReservation);

            // then
            String countSql = "SELECT count(*) FROM reservation";
            Integer reservationCount = jdbcTemplate.queryForObject(countSql, Integer.class);

            assertThat(reservationCount).isEqualTo(1);
        }

        @Test
        void 저장한_예약_정보를_반환한다() {
            // given
            ReservationTime time = persistTime(DEFAULT_START_AT);
            Reservation transientReservation = Reservation.create(
                    DEFAULT_NAME,
                    DEFAULT_DATE,
                    time
            );

            // when
            Reservation persistedReservation = reservationRepository.create(transientReservation);

            // then
            String selectSql = "SELECT r.*, rt.start_at"
                    + " FROM reservation r"
                    + " JOIN reservation_time rt"
                    + " ON r.time_id = rt.id";
            List<Reservation> foundReservations = jdbcTemplate.query(selectSql, reservationRowMapper());

            assertThat(foundReservations).hasSize(1);
            assertThat(foundReservations.getFirst()).isEqualTo(persistedReservation);
        }
    }

    @Test
    void 저장된_모든_예약을_조회한다() {
        // given
        int reservationCount = 5;
        insertReservation(reservationCount);

        // when
        List<Reservation> foundReservations = reservationRepository.findAll();

        // then
        assertThat(foundReservations).hasSize(reservationCount);
    }

    @Nested
    class 예약_정보를_제거한다 {

        @Test
        void ID_기반으로_예약을_제거한다() {
            // given
            ReservationTime time = persistTime(DEFAULT_START_AT);
            String insertSql = "INSERT INTO reservation(id, name, date, time_id)"
                    + " VALUES (?, ?, ?, ?)";

            jdbcTemplate.update(
                    insertSql,
                    DEFAULT_ID,
                    DEFAULT_NAME,
                    DEFAULT_DATE,
                    time.getId()
            );

            // when
            reservationRepository.delete(DEFAULT_ID);

            // then
            String countSql = "SELECT count(*)"
                    + " FROM reservation"
                    + " WHERE id = ?";
            Integer reservationCount = jdbcTemplate.queryForObject(
                    countSql,
                    Integer.class,
                    DEFAULT_ID
            );

            assertThat(reservationCount).isEqualTo(0);
        }

        @Test
        void 존재하지_않는_ID라면_예외를_던진다() {
            assertThatThrownBy(() -> reservationRepository.delete(NOT_EXIST_ID))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("존재하지 않는 예약 id입니다.");
        }
    }

    private void insertReservation(int count) {
        Long timeId = persistTime(DEFAULT_START_AT)
                .getId();

        for (int i = 0; i < count; i++) {
            String insertSql = "INSERT INTO reservation(name, date, time_id)"
                    + " VALUES (?, ?, ?)";

            jdbcTemplate.update(
                    insertSql,
                    DEFAULT_NAME,
                    DEFAULT_DATE,
                    timeId
            );
        }
    }

    private ReservationTime persistTime(String startAt) {
        String insertSql = "INSERT INTO reservation_time(start_at)"
                + " VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, startAt);

            return statement;
        }, keyHolder);

        return ReservationTime.retrieve(
                keyHolder.getKeyAs(Long.class),
                startAt
        );
    }

    private RowMapper<Reservation> reservationRowMapper() {
        return (resultSet, rowNum) -> {
            long timeId = resultSet.getLong("time_id");
            String startAt = resultSet.getString("start_at");

            return Reservation.retrieve(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getString("date"),
                    ReservationTime.retrieve(timeId, startAt)
            );
        };
    }
}
