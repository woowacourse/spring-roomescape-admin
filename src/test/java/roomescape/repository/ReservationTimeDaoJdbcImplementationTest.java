package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.domain.ReservationTime;

@JdbcTest
@Import(ReservationTimeEntityMapper.class)
class ReservationTimeDaoJdbcImplementationTest {

    private static final long id = 9999L;
    private static final String TEST_TIME = "15:00";

    private final JdbcTemplate jdbcTemplate;
    private final ReservationTimeEntityMapper mapper;

    private ReservationTimeDaoJdbcImplementation reservationTimeDao;

    @Autowired
    ReservationTimeDaoJdbcImplementationTest(JdbcTemplate jdbcTemplate, ReservationTimeEntityMapper mapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.mapper = mapper;
    }

    @BeforeEach
    void setUp() {
        reservationTimeDao = new ReservationTimeDaoJdbcImplementation(jdbcTemplate, mapper);
    }

    @ParameterizedTest
    @ValueSource(strings = {"20:00", "00:00", "23:59", "99:99"})
    @DisplayName("ReservationTime 객체를 받아 잘 저장 후 새로운 객체를 반환한다")
    void save_success(String timeValue) {
        //given
        ReservationTime testTime = new ReservationTime(id, timeValue);

        //when
        ReservationTime result = reservationTimeDao.save(testTime);

        //then
        Assertions.assertNotNull(result.id());
    }

    @Test
    @DisplayName("전체 조회를 잘 한다")
    void findAll_success() {
        ReservationTime testTime = new ReservationTime(1L, TEST_TIME);
        ReservationTime testTime2 = new ReservationTime(2L, TEST_TIME);
        ReservationTime testTime3 = new ReservationTime(3L, TEST_TIME);

        reservationTimeDao.save(testTime);
        reservationTimeDao.save(testTime2);
        reservationTimeDao.save(testTime3);

        List<ReservationTime> result = reservationTimeDao.findAll();

        Assertions.assertEquals(3, result.size());
    }

    @Test
    @DisplayName("조회 대상이 없으면 빈 리스트를 반환한다.")
    void findAll_return_empty_list_when_Empty() {
        List<ReservationTime> result = reservationTimeDao.findAll();
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("특정 대상을 잘 지운다")
    void delete_success() {
        ReservationTime testTime = new ReservationTime(1L, TEST_TIME);
        ReservationTime saved = reservationTimeDao.save(testTime);

        Long deleteTargetId = saved.id();
        reservationTimeDao.delete(deleteTargetId);

        //then
        List<ReservationTime> leftReservationTimes = reservationTimeDao.findAll();
        Optional<ReservationTime> deletedTargetFindResult = leftReservationTimes.stream()
                .filter(entity -> entity.id().equals(deleteTargetId))
                .findAny();
        Assertions.assertTrue(deletedTargetFindResult.isEmpty());
    }

    @Test
    @DisplayName("삭제 대상이 없으면 오류가 발생한다")
    void delete_throw_exception_when_target_not_exist() {
        Long targetId = 99999L;
        assertThatThrownBy(
                () -> reservationTimeDao.delete(targetId)
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("삭제 대상이 존재하지 않습니다");
    }
}
