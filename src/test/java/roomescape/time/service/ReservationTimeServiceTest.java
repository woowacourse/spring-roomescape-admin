package roomescape.time.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.time.dao.ReservationTimeDao;
import roomescape.time.dto.ReservationTimeRequestDto;
import roomescape.time.dto.ReservationTimeResponseDto;
import roomescape.time.dto.ReservationTimesResponseDto;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationTimeServiceTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSource dataSource;

    private ReservationTimeService reservationTimeService;

    @BeforeEach
    void init() {
        reservationTimeService = new ReservationTimeService(new ReservationTimeDao(jdbcTemplate, dataSource));
    }

    @DisplayName("예약 시간 삽입 테스트")
    @Test
    void insertTest() {
        // given
        ReservationTimeRequestDto request = new ReservationTimeRequestDto("10:00");

        //when
        ReservationTimeResponseDto response = reservationTimeService.addReservationTime(request);

        //then
        assertThat(response.id()).isEqualTo(1L);
    }

    @DisplayName("예약 시간 전체 조회 테스트")
    @Test
    void findAllTest() {
        // given
        reservationTimeService.addReservationTime(new ReservationTimeRequestDto("10:00"));
        reservationTimeService.addReservationTime(new ReservationTimeRequestDto("10:00"));
        reservationTimeService.addReservationTime(new ReservationTimeRequestDto("10:00"));

        //when
        ReservationTimesResponseDto response = reservationTimeService.findAllReservationTime();
        int findSize = response.reservationTimes().size();

        //then
        assertThat(findSize).isEqualTo(3);
    }

    @DisplayName("예약 시간 삭제 테스트")
    @Test
    void deleteTest() {
        // given
        ReservationTimeResponseDto inserted = reservationTimeService.addReservationTime(new ReservationTimeRequestDto("10:00"));

        //when & then
        Assertions.assertThatCode(() -> reservationTimeService.deleteReservationTimeById(inserted.id()));
    }

    @DisplayName("예약 시간 삭제 실패 테스트")
    @Test
    void deleteFailTest() {
        // given
        Long noneExistId = -1L;

        //when & then
        Assertions.assertThatThrownBy(() -> reservationTimeService.deleteReservationTimeById(noneExistId))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
