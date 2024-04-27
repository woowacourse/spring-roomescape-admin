package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import roomescape.controller.dto.ReservationAddRequest;
import roomescape.controller.dto.ReservationResponse;
import roomescape.dao.ReservationDao;
import roomescape.domain.Reservation;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Sql(value = "classpath:data-reset.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class ReservationServiceTest {
    private final ReservationService service;
    private final ReservationDao reservationDao;

    @Autowired
    public ReservationServiceTest(ReservationService service, ReservationDao reservationDao) {
        this.service = service;
        this.reservationDao = reservationDao;
    }

    @DisplayName("예약을 추가한다.")
    @Test
    void createReservationTest() {
        ReservationAddRequest request = new ReservationAddRequest("페드로", LocalDate.MAX, 1L);
        ReservationResponse response = service.createReservation(request);

        List<Reservation> reservations = reservationDao.findAll();
        Long lastElementId = reservations.get(reservations.size() - 1).getId();
        assertThat(response.id()).isEqualTo(lastElementId);
    }

    @DisplayName("예약 추가 시 예약 시간이 등록되지 않은 경우 예외가 발생한다.")
    @Test
    void createReservationWithInvalidTimeTest() {
        ReservationAddRequest request = new ReservationAddRequest("웨지", LocalDate.MAX, Long.MAX_VALUE);
        assertThatThrownBy(() -> service.createReservation(request)).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 예약 시간입니다.");
    }

    @DisplayName("과거의 시간을 예약하는 경우 예외가 발생한다.")
    @Test
    void createReservationWithPastTimeTest() {
        ReservationAddRequest request = new ReservationAddRequest("리사", LocalDate.MIN, 1L);
        assertThatThrownBy(() -> service.createReservation(request)).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("과거의 시각은 예약할 수 없습니다.");
    }

    @DisplayName("모든 예약을 조회한다.")
    @Test
    void getAllReservationsTest() {
        List<ReservationResponse> reservations = service.findAllReservations();
        assertThat(reservations).hasSize(1);
    }

    @DisplayName("예약을 취소한다.")
    @Test
    void cancelReservationTest() {
        service.cancelReservation(1L);
        assertThat(reservationDao.isExist(1L)).isFalse();
    }
}
