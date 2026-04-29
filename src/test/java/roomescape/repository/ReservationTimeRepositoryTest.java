package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static roomescape.repository.rowmapper.RowMapperUtils.RESERVATION_TIME_ROW_MAPPER;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.domain.ReservationTime;

@JdbcTest
class ReservationTimeRepositoryTest {

    private static final long DEFAULT_ID = 1;
    private static final long NOT_EXIST_ID = 999;
    private static final String DEFAULT_START_AT = "00:00";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private ReservationTimeRepository timeRepository;

    @BeforeEach
    void setUp() {
        timeRepository = new ReservationTimeRepository(jdbcTemplate);
    }

    @Nested
    class 예약_시간을_저장한다 {
        @Test
        void 새로운_시간_정보를_저장한다() {
            // given
            ReservationTime transientTime = ReservationTime.create(DEFAULT_START_AT);

            // when
            timeRepository.create(transientTime);

            // then
            String timeCountSql = "SELECT count(*)"
                    + " FROM reservation_time";
            Integer timeCount = jdbcTemplate.queryForObject(
                    timeCountSql,
                    Integer.class
            );

            assertThat(timeCount).isEqualTo(1);
        }
        
        @Test
        void 저장한_시간_정보를_반환한다() {
            // given
            ReservationTime transientTime = ReservationTime.create(DEFAULT_START_AT);

            // when
            ReservationTime persistedReservationTime = timeRepository.create(transientTime);

            // then
            String selectSql = "SELECT id, start_at"
                    + " FROM reservation_time";
            List<ReservationTime> findReservations = jdbcTemplate.query(selectSql, RESERVATION_TIME_ROW_MAPPER);

            assertThat(findReservations).hasSize(1);
            assertThat(findReservations.getFirst()).isEqualTo(persistedReservationTime);
        }
    }

    @Test
    void 저장된_모든_예약_시간을_조회한다() {
        // given
        int insertCount = 5;
        insertReservationTimeAsAmount(insertCount);

        // when
        List<ReservationTime> reservationTimes = timeRepository.findAll();

        // then
        assertThat(reservationTimes).hasSize(insertCount);
    }

    @Test
    void 예약_시간을_ID_기준으로_조회한다() {
        // given
        insertReservationTime(DEFAULT_ID, DEFAULT_START_AT);
        ReservationTime expected = ReservationTime.retrieve(DEFAULT_ID, DEFAULT_START_AT);

        // when
        ReservationTime actual = timeRepository.findById(DEFAULT_ID);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Nested
    class 예약_시간_정보를_제거한다 {

        @Test
        void ID_기반으로_예약_시간을_제거한다() {
            // given
            insertReservationTime(DEFAULT_ID, DEFAULT_START_AT);

            // when
            timeRepository.delete(DEFAULT_ID);

            // then
            String countSql = "SELECT count(*)"
                    + " FROM reservation_time"
                    + " WHERE id = ?";
            Integer timeCount = jdbcTemplate.queryForObject(
                    countSql,
                    Integer.class,
                    DEFAULT_ID
            );

            assertThat(timeCount).isEqualTo(0);
        }

        @Test
        void 존재하지_않는_ID라면_예외를_던진다() {
            assertThatThrownBy(() -> timeRepository.delete(NOT_EXIST_ID))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("존재하지 않는 시간 id입니다.");
        }
    }

    private void insertReservationTime(
            long id,
            String startAt
    ) {
        String insertSql = "INSERT INTO reservation_time(id, start_at)"
                + " VALUES (?, ?)";
        jdbcTemplate.update(
                insertSql,
                id,
                startAt
        );
    }

    private void insertReservationTimeAsAmount(int count) {
        for (int i = 0; i < count; i++) {
            String insertSql = "INSERT INTO reservation_time(start_at)"
                    + " VALUES (?)";
            jdbcTemplate.update(insertSql, DEFAULT_START_AT);
        }
    }
}
