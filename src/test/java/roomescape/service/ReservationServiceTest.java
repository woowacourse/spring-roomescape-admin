package roomescape.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.domain.Reservation;
import roomescape.repository.ReservationDao;
import roomescape.repository.ReservationDaoJdbcImplementation;
import roomescape.repository.ReservationEntityMapper;
import roomescape.repository.ReservationTimeDao;
import roomescape.repository.ReservationTimeDaoJdbcImplementation;
import roomescape.repository.ReservationTimeEntityMapper;

@JdbcTest
@Import({ReservationEntityMapper.class, ReservationTimeEntityMapper.class})
class ReservationServiceTest {

    private static final String TESTER_NAME = "브라운";
    private static final String TEST_DATE = "2023-08-06";
    private static final String TEST_TIME_VALUE = "15:40";
    private static final String INSERT_RESERVATION_TIME_QUERY = "INSERT INTO reservation_time(start_at) VALUES (?)";
    private static final String SELECT_SPECIFIC_RESERVATION_TIME_QUERY = "SELECT id FROM reservation_time WHERE start_at = ?";

    private final ReservationEntityMapper mapper;
    private final ReservationTimeEntityMapper timeMapper;
    private final JdbcTemplate jdbcTemplate;

    private ReservationDao repository;
    private ReservationTimeDao timeRepository;
    private ReservationService service;
    private Long testTimeId;

    @Autowired
    ReservationServiceTest(ReservationEntityMapper mapper, ReservationTimeEntityMapper timeMapper,
                           JdbcTemplate jdbcTemplate) {
        this.mapper = mapper;
        this.timeMapper = timeMapper;
        this.jdbcTemplate = jdbcTemplate;
    }

    @BeforeEach
    void setUp() {
        repository = new ReservationDaoJdbcImplementation(jdbcTemplate, mapper);
        timeRepository = new ReservationTimeDaoJdbcImplementation(jdbcTemplate, timeMapper);
        service = new ReservationService(repository, timeRepository);

        jdbcTemplate.update(INSERT_RESERVATION_TIME_QUERY, TEST_TIME_VALUE);
        testTimeId = jdbcTemplate.queryForObject(SELECT_SPECIFIC_RESERVATION_TIME_QUERY, Long.class, TEST_TIME_VALUE);
    }

    @Test
    @DisplayName("저장을 잘 한다")
    void enrollReservation_success() {
        //when
        Assertions.assertDoesNotThrow(
                () -> service.enrollReservation(TESTER_NAME, TEST_DATE, testTimeId)
        );
    }

    @Test
    @DisplayName("저장을 하고, ID와 저정된 값을 반환한다")
    void enrollReservation_and_return_value() {
        //when
        Reservation result = service.enrollReservation(TESTER_NAME, TEST_DATE, testTimeId);

        //then
        Assertions.assertNotNull(
                result.id()
        );
    }

    @Test
    @DisplayName("찾기 기능을 수행할 때에 오류가 발생하지 않는다")
    void find_AllReservations_success() {
        service.enrollReservation(TESTER_NAME, TEST_DATE, testTimeId);

        Assertions.assertDoesNotThrow(
                () -> service.findAllReservations()
        );
    }

    @Test
    @DisplayName("찾기 기능을 수행할 때에 저장소가 비어 있어도 오류가 발생하지 않는다")
    void find_AllReservations_success_when_empty() {
        Assertions.assertDoesNotThrow(
                () -> service.findAllReservations()
        );
    }

    @Test
    @DisplayName("삭제를 레포지토리 계층에 전달하고, 오류가 발생하지 않으면 오류를 일으키지 않는다")
    void delete_SpecificReservationById_success() {
        Reservation saved = service.enrollReservation(TESTER_NAME, TEST_DATE, testTimeId);
        Assertions.assertDoesNotThrow(
                () -> service.deleteSpecificReservationById(saved.id())
        );
    }

    @Test
    @DisplayName("없는 대상 관련한 오류가 레포지토리에서 발생하면 오류가 전파된다")
    void delete_SpecificReservationById_exception_propagation() {
        long notExistReservationId = 100000L;
        assertThatThrownBy(
                () -> service.deleteSpecificReservationById(notExistReservationId)
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("삭제 대상이 존재하지 않습니다");
    }
}
