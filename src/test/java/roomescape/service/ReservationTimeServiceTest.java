package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import roomescape.dao.ReservationTimeDao;
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

    @Test
    void findAll() {
        List<ReservationTimeResponse> reservationTimeResponses = reservationTimeService.findAll();

        assertThat(reservationTimeResponses).isEqualTo(List.of(Fixtures.FIRST_TIME_RESPONSE, Fixtures.SECOND_TIME_RESPONSE));
    }

    @Test
    void save() {
        reservationTimeService.save(Fixtures.THIRD_TIME_REQUEST);

        assertThat(reservationTimeService.findAll()).isEqualTo(List.of(Fixtures.FIRST_TIME_RESPONSE, Fixtures.SECOND_TIME_RESPONSE, Fixtures.THIRD_TIME_RESPONSE));
    }

    @Test
    void deleteById() {
        reservationTimeService.deleteById(1);

        assertThat(reservationTimeService.findAll()).isEqualTo(List.of(Fixtures.SECOND_TIME_RESPONSE));
    }

    @Test
    void invalidDeleteById() {
        assertThatThrownBy(() -> reservationTimeService.deleteById(3))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 id는 존재하지 않습니다.");
    }

    @Test
    void deleteAll() {
        reservationTimeService.deleteAll();

        assertThat(reservationTimeService.findAll()).isEqualTo(List.of());
    }
}
