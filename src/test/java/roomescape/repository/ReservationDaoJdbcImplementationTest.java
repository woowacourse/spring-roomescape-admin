package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

@JdbcTest
@Import({ReservationEntityMapper.class, ReservationTimeEntityMapper.class})
class ReservationDaoJdbcImplementationTest {

    private static final String TESTER_NAME = "라티";
    private static final String TEST_DATE = "2026-04-28";
    private static final String TEST_TIME_VALUE = "18:00";
    private static final String INSERT_RESERVATION_TIME_QUERY = "INSERT INTO reservation_time(start_at) VALUES (?)";
    private static final String SELECT_SPECIFIC_RESERVATION_TIME_QUERY = "SELECT id FROM reservation_time WHERE start_at = ?";

    private final JdbcTemplate jdbcTemplate;
    private final ReservationEntityMapper mapper;

    private ReservationDaoJdbcImplementation reservationDaoJdbcImplementation;
    private ReservationTime testTime;

    @Autowired
    ReservationDaoJdbcImplementationTest(JdbcTemplate jdbcTemplate, ReservationEntityMapper mapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.mapper = mapper;
    }

    @BeforeEach
    void setUp() {
        reservationDaoJdbcImplementation = new ReservationDaoJdbcImplementation(jdbcTemplate, mapper);
        Long timeId = insertReservationTime(TEST_TIME_VALUE);
        testTime = new ReservationTime(timeId, TEST_TIME_VALUE);
    }

    private Long insertReservationTime(String startAt) {
        jdbcTemplate.update(INSERT_RESERVATION_TIME_QUERY, startAt);
        return jdbcTemplate.queryForObject(SELECT_SPECIFIC_RESERVATION_TIME_QUERY, Long.class, startAt);
    }

    @Test
    @DisplayName("저장을 하고, ID가 있는 Reservation를 반환한다")
    void save_success() {
        //given
        Reservation reservation = Reservation.constructWithNoId(TESTER_NAME, TEST_DATE, testTime);

        //when
        Reservation result = reservationDaoJdbcImplementation.save(reservation);

        //then
        Assertions.assertNotNull(result.id());
    }

    @Test
    @DisplayName("찾기를 하면 모든 결과물을 반환한다")
    void findAll_success() {
        Reservation reservation1 = Reservation.constructWithNoId(TESTER_NAME, TEST_DATE, testTime);
        Reservation reservation2 = Reservation.constructWithNoId(TESTER_NAME, TEST_DATE, testTime);
        Reservation reservation3 = Reservation.constructWithNoId(TESTER_NAME, TEST_DATE, testTime);
        reservationDaoJdbcImplementation.save(reservation1);
        reservationDaoJdbcImplementation.save(reservation2);
        reservationDaoJdbcImplementation.save(reservation3);

        List<Reservation> result = reservationDaoJdbcImplementation.findAll();

        Assertions.assertEquals(3, result.size());
    }

    @Test
    @DisplayName("찾기는 비어 있어도 오류가 발생시키지 않고, 빈 리스트를 반환한다.")
    void findAll_success_when_repository_is_empty() {
        List<Reservation> result = reservationDaoJdbcImplementation.findAll();

        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("삭제 대상이 존재하면 잘 삭제한다")
    void delete_success() {
        Reservation reservation1 = Reservation.constructWithNoId(TESTER_NAME, TEST_DATE, testTime);
        Reservation reservation2 = Reservation.constructWithNoId(TESTER_NAME, TEST_DATE, testTime);
        Reservation reservation3 = Reservation.constructWithNoId(TESTER_NAME, TEST_DATE, testTime);
        Reservation deleteTarget = reservationDaoJdbcImplementation.save(reservation1);
        reservationDaoJdbcImplementation.save(reservation2);
        reservationDaoJdbcImplementation.save(reservation3);

        Long deleteTargetId = deleteTarget.id();
        reservationDaoJdbcImplementation.delete(deleteTargetId);

        Optional<Reservation> deleteTargetFromReservations = findDeleteTargetFromStorage(deleteTargetId);
        Assertions.assertTrue(deleteTargetFromReservations.isEmpty());
    }

    private Optional<Reservation> findDeleteTargetFromStorage(Long deleteTargetId) {
        List<Reservation> leftReservation = reservationDaoJdbcImplementation.findAll();
        return leftReservation.stream()
                .filter(reservation -> reservation.id().equals(deleteTargetId))
                .findAny();
    }

    @Test
    @DisplayName("삭제 대상이 없으면 오류를 발생시킨다")
    void delete_throw_exception_when_target_is_not_exist() {
        assertThatThrownBy(
                () -> reservationDaoJdbcImplementation.delete(1L)
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("삭제 대상이 존재하지 않습니다");
    }
}
