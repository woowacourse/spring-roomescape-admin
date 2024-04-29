package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertAll;
import static roomescape.TestConstant.TEST_START_AT;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;
import roomescape.service.dto.request.ReservationTimeCreateRequest;
import roomescape.service.dto.response.ReservationTimeResponse;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationTimeServiceTest {

    @Autowired
    private ReservationTimeService reservationTimeService;
    @Autowired
    private ReservationTimeDao reservationTimeDao;

    @DisplayName("예약시간을 생성한다.")
    @Test
    void createReservationTime() {
        // given
        ReservationTimeCreateRequest reservationTimeCreateRequest = new ReservationTimeCreateRequest(TEST_START_AT);

        // when
        ReservationTimeResponse reservationTimeResponse = reservationTimeService.createReservationTime(reservationTimeCreateRequest);

        // then
        assertAll(
                () -> assertThat(reservationTimeResponse.id()).isEqualTo(1L),
                () -> assertThat(reservationTimeResponse.startAt()).isEqualTo(TEST_START_AT)
        );
    }

    @DisplayName("저장되어 있는 예약시간을 전부 가져온다.")
    @Test
    void getReservationTimes() {
        // given
        ReservationTime savedReservationTime = reservationTimeDao.save(new ReservationTime(TEST_START_AT));

        // when
        List<ReservationTimeResponse> reservationTimeResponses = reservationTimeService.getReservationTimes();

        // then
        ReservationTimeResponse reservationTimeResponse = reservationTimeResponses.get(0);
        assertAll(
                () -> assertThat(reservationTimeResponses).hasSize(1),
                () -> assertThat(reservationTimeResponse.id()).isEqualTo(savedReservationTime.getId()),
                () -> assertThat(reservationTimeResponse.startAt()).isEqualTo(TEST_START_AT)
        );
    }

    @DisplayName("예약시간을 삭제한다.")
    @Test
    void delete() {
        // given
        ReservationTime savedReservationTime = reservationTimeDao.save(new ReservationTime(TEST_START_AT));

        // when & then
        assertThatCode(() -> reservationTimeService.delete(savedReservationTime.getId()))
                .doesNotThrowAnyException();
    }
}
