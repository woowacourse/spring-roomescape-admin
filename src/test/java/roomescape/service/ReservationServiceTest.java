package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertAll;
import static roomescape.TestConstant.TEST_DATE;
import static roomescape.TestConstant.TEST_NAME;
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
import roomescape.service.dto.request.ReservationCreateRequest;
import roomescape.service.dto.response.ReservationResponse;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService;
    @Autowired
    private ReservationTimeDao reservationTimeDao;

    @DisplayName("예약을 생성한다.")
    @Test
    void createReservation() {
        // given
        ReservationTime reservationTime = new ReservationTime(TEST_START_AT);
        ReservationTime savedReservationTime = reservationTimeDao.save(reservationTime);

        ReservationCreateRequest reservationRequest = new ReservationCreateRequest(TEST_NAME, TEST_DATE, savedReservationTime.getId());

        // when
        ReservationResponse reservationResponse = reservationService.createReservation(reservationRequest);

        // then
        assertAll(
                () -> assertThat(reservationResponse.name()).isEqualTo(reservationRequest.name()),
                () -> assertThat(reservationResponse.date()).isEqualTo(reservationRequest.date()),
                () -> assertThat(reservationResponse.id()).isEqualTo(savedReservationTime.getId()),
                () -> assertThat(reservationResponse.time().id()).isEqualTo(reservationRequest.timeId()),
                () -> assertThat(reservationResponse.time().startAt()).isEqualTo(savedReservationTime.getStartAt())
        );
    }

    @DisplayName("저장되어 있는 예약을 전부 가져온다.")
    @Test
    void getReservations() {
        // given
        ReservationTime reservationTime = new ReservationTime(TEST_START_AT);
        ReservationTime savedReservationTime = reservationTimeDao.save(reservationTime);

        ReservationCreateRequest reservationRequest = new ReservationCreateRequest(TEST_NAME, TEST_DATE, savedReservationTime.getId());
        ReservationResponse reservationResponse1 = reservationService.createReservation(reservationRequest);

        // when
        List<ReservationResponse> reservationResponses = reservationService.getReservations();

        //then
        ReservationResponse reservationResponse2 = reservationResponses.get(0);
        assertAll(
                () -> assertThat(reservationResponses).hasSize(1),
                () -> assertThat(reservationResponse2.id()).isEqualTo(reservationResponse1.id()),
                () -> assertThat(reservationResponse2.name()).isEqualTo(reservationResponse1.name()),
                () -> assertThat(reservationResponse2.date()).isEqualTo(reservationResponse1.date()),
                () -> assertThat(reservationResponse2.time().id()).isEqualTo(reservationResponse1.time().id()),
                () -> assertThat(reservationResponse2.time().startAt()).isEqualTo(reservationResponse1.time().startAt())
        );
    }

    @DisplayName("예약을 삭제한다.")
    @Test
    void delete() {
        // given
        ReservationTime reservationTime = new ReservationTime(TEST_START_AT);
        ReservationTime savedReservationTime = reservationTimeDao.save(reservationTime);

        ReservationCreateRequest reservationRequest = new ReservationCreateRequest(TEST_NAME, TEST_DATE, savedReservationTime.getId());
        ReservationResponse reservationResponse = reservationService.createReservation(reservationRequest);

        // when & then
        assertThatCode(() -> reservationService.delete(reservationResponse.id()))
                .doesNotThrowAnyException();
    }
}
