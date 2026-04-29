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
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationTimeDao;
import roomescape.repository.ReservationTimeDaoJdbcImplementation;
import roomescape.repository.ReservationTimeEntityMapper;

@JdbcTest
@Import(ReservationTimeEntityMapper.class)
class ReservationTimeServiceTest {

    private static final String TEST_TIME = "15:40";

    private final ReservationTimeEntityMapper mapper;
    private final JdbcTemplate jdbcTemplate;

    private ReservationTimeDao repository;
    private ReservationTimeService service;

    @Autowired
    ReservationTimeServiceTest(ReservationTimeEntityMapper mapper, JdbcTemplate jdbcTemplate) {
        this.mapper = mapper;
        this.jdbcTemplate = jdbcTemplate;
    }

    @BeforeEach
    void setUp() {
        repository = new ReservationTimeDaoJdbcImplementation(jdbcTemplate, mapper);
        service = new ReservationTimeService(repository);
    }

    @Test
    @DisplayName("저장을 잘 한다")
    void enrollReservationTimes_success() {
        //when
        Assertions.assertDoesNotThrow(
                () -> service.enrollReservationTimes(TEST_TIME)
        );
    }

    @Test
    @DisplayName("저장을 하고, ID와 저정된 값을 반환한다")
    void enrollReservationTimes_and_return_value() {
        //when
        ReservationTime result = service.enrollReservationTimes(TEST_TIME);

        //then
        Assertions.assertNotNull(
                result.id()
        );
    }

    @Test
    @DisplayName("찾기 기능을 수행할 때에 오류가 발생하지 않는다")
    void find_AllReservationTimes_success() {
        service.enrollReservationTimes(TEST_TIME);

        Assertions.assertDoesNotThrow(
                () -> service.findAllReservationTimes()
        );
    }

    @Test
    @DisplayName("찾기 기능을 수행할 때에 저장소가 비어 있어도 오류가 발생하지 않는다")
    void find_AllReservationTimes_success_when_empty() {
        Assertions.assertDoesNotThrow(
                () -> service.findAllReservationTimes()
        );
    }

    @Test
    @DisplayName("삭제를 레포지토리 계층에 전달하고, 오류가 발생하지 않으면 오류를 일으키지 않는다")
    void delete_SpecificReservationTime_success() {
        ReservationTime saved = service.enrollReservationTimes(TEST_TIME);
        Assertions.assertDoesNotThrow(
                () -> service.deleteSpecificReservationTime(saved.id())
        );
    }

    @Test
    @DisplayName("없는 대상 관련한 오류가 레포지토리에서 발생하면 오류가 전파된다")
    void delete_SpecificReservationTime_exception_propagation() {
        long notExistReservationId = 100000L;
        assertThatThrownBy(
                () -> service.deleteSpecificReservationTime(notExistReservationId)
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("삭제 대상이 존재하지 않습니다");
    }
}
