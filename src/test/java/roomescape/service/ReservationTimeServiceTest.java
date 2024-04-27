package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.dao.ReservationTimeDao;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;

class ReservationTimeServiceTest {

    private final ReservationTimeDao fakeReservationTimeDao = new FakeReservationTimeDao();
    private final ReservationTimeService reservationTimeService = new ReservationTimeService(fakeReservationTimeDao);


    @BeforeEach
    void setUp() {
        reservationTimeService.deleteAll();
        fakeReservationTimeDao.save(Fixtures.FIRST_TIME);
        fakeReservationTimeDao.save(Fixtures.SECOND_TIME);
    }

    @DisplayName("전체 예약 시간을 반환한다.")
    @Test
    void findAll() {
        List<ReservationTimeResponse> reservationTimeResponses = reservationTimeService.findAll();

        assertThat(reservationTimeResponses).isEqualTo(List.of(Fixtures.FIRST_TIME_RESPONSE, Fixtures.SECOND_TIME_RESPONSE));
    }

    @DisplayName("예약 시간을 저장한다.")
    @Test
    void save() {
        reservationTimeService.save(Fixtures.THIRD_TIME_REQUEST);

        assertThat(reservationTimeService.findAll()).isEqualTo(List.of(Fixtures.FIRST_TIME_RESPONSE, Fixtures.SECOND_TIME_RESPONSE, Fixtures.THIRD_TIME_RESPONSE));
    }

    @DisplayName("시간이 null 값인 예약 시간을 저장할 경우 예외를 발생시킨다.")
    @Test
    void startAtIsNull() {
        ReservationTimeRequest reservationTimeRequest = new ReservationTimeRequest(null);

        assertThatThrownBy(() -> reservationTimeService.save(reservationTimeRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("시간이 입력되지 않았습니다. 시간을 입력해주세요.");
    }

    @DisplayName("해당 id의 예약 시간을 삭제한다.")
    @Test
    void deleteById() {
        reservationTimeService.deleteById(1);

        assertThat(reservationTimeService.findAll()).isEqualTo(List.of(Fixtures.SECOND_TIME_RESPONSE));
    }

    @DisplayName("해당 id의 예약 시간이 존재하지 않을 경우 예외를 발생시킨다.")
    @Test
    void invalidDeleteById() {
        assertThatThrownBy(() -> reservationTimeService.deleteById(3))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 id는 존재하지 않습니다. id = %d".formatted(3));
    }

    @DisplayName("전체 예약 시간을 삭제한다.")
    @Test
    void deleteAll() {
        reservationTimeService.deleteAll();

        assertThat(reservationTimeService.findAll()).isEqualTo(List.of());
    }
}
