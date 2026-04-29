package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static roomescape.repository.rowmapper.RowMapperUtils.RESERVATION_ROW_MAPPER;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

@JdbcTest
class ReservationRepositoryTest {

    private static final long DEFAULT_ID = 1;
    private static final long NOT_EXIST_ID = 999;
    private static final String DEFAULT_NAME = "name";
    private static final String DEFAULT_DATE = "2025-01-01";
    private static final String DEFAULT_START_AT = "00:00";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private ReservationRepository reservationRepository;

    @BeforeEach
    void setUp() {
        reservationRepository = new ReservationRepository(jdbcTemplate);
    }

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
            List<Reservation> foundReservations = jdbcTemplate.query(selectSql, RESERVATION_ROW_MAPPER);

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
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
        Number id = simpleJdbcInsert.executeAndReturnKey(Map.of(
                "start_at", startAt
        ));

        return ReservationTime.retrieve(
                id.longValue(),
                startAt
        );
    }
}
