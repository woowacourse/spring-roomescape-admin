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

@JdbcTest
@Import(ReservationEntityMapper.class)
class ReservationServiceTest {

    private static final String TESTER_NAME = "브라운";
    private static final String TEST_DATE = "2023-08-06";
    private static final String TEST_TIME = "15:40";

    private final ReservationEntityMapper mapper;
    private final JdbcTemplate jdbcTemplate;

    private ReservationDao repository;
    private ReservationService service;

    @Autowired
    ReservationServiceTest(ReservationEntityMapper mapper, JdbcTemplate jdbcTemplate) {
        this.mapper = mapper;
        this.jdbcTemplate = jdbcTemplate;
    }

    @BeforeEach
    void setUp() {
        repository = new ReservationDaoJdbcImplementation(jdbcTemplate, mapper);
        service = new ReservationService(repository);
    }

    @Test
    @DisplayName("저장을 잘 한다")
    void add_success() {
        //when
        Assertions.assertDoesNotThrow(
                () -> service.add(TESTER_NAME, TEST_DATE, TEST_TIME)
        );
    }

    @Test
    @DisplayName("저장을 하고, ID와 저정된 값을 반환한다")
    void add_and_return_value() {
        //when
        Reservation result = service.add(TESTER_NAME, TEST_DATE, TEST_TIME);

        //then
        Assertions.assertNotNull(
                result.id()
        );
    }

    @Test
    @DisplayName("찾기 기능을 수행할 때에 오류가 발생하지 않는다")
    void find_success() {
        service.add(TESTER_NAME, TEST_DATE, TEST_TIME);

        Assertions.assertDoesNotThrow(
                () -> service.find()
        );
    }

    @Test
    @DisplayName("찾기 기능을 수행할 때에 저장소가 비어 있어도 오류가 발생하지 않는다")
    void find_success_when_empty() {
        Assertions.assertDoesNotThrow(
                () -> service.find()
        );
    }

    @Test
    @DisplayName("삭제를 레포지토리 계층에 전달하고, 오류가 발생하지 않으면 오류를 일으키지 않는다")
    void delete_success() {
        Reservation saved = service.add(TESTER_NAME, TEST_DATE, TEST_TIME);
        Assertions.assertDoesNotThrow(
                () -> service.delete(saved.id())
        );
    }

    @Test
    @DisplayName("없는 대상 관련한 오류가 레포지토리에서 발생하면 오류가 전파된다")
    void delete_exception_propagation() {
        long notExistReservationId = 100000L;
        assertThatThrownBy(
                () -> service.delete(notExistReservationId)
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("삭제 대상이 존재하지 않습니다");
    }
}
