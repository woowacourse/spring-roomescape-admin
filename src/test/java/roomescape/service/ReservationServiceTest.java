package roomescape.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import roomescape.fake.FakeReservationDao;
import roomescape.fake.FakeReservationTimeDao;
import roomescape.dao.ReservationDao;
import roomescape.dao.ReservationTimeDao;
import roomescape.dto.ReservationRequest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ReservationServiceTest {

    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        ReservationDao reservationDao = new FakeReservationDao();
        ReservationTimeDao reservationTimeDao = new FakeReservationTimeDao();
        reservationService = new ReservationService(reservationDao, reservationTimeDao);
    }

    @Test
    void 모든_예약을_조회한다() {
        assertThat(reservationService.getReservations()).hasSize(2);
    }

    @Test
    void 예약을_추가하면_추가한_예약을_반환한다() {
        ReservationRequest reservationRequest = new ReservationRequest("듀이", LocalDate.now(), 1L);
        assertThat(reservationService.createReservation(reservationRequest)).isNotNull();
    }

    @Test
    void 특정_예약을_삭제하면_true를_반환한다() {
        assertThat(reservationService.deleteReservationById(1L)).isTrue();
    }

    @Test
    void 특정_예약을_삭제했을때_예약이_없으면_false를_반환한다() {
        assertThat(reservationService.deleteReservationById(4L)).isFalse();
    }
}