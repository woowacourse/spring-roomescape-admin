package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import roomescape.dao.ReservationDao;
import roomescape.dao.ReservationTimeDao;
import roomescape.dto.ReservationResponse;

class ReservationServiceTest {

    private final ReservationDao fakeReservationDao = new FakeReservationDao();
    private final ReservationTimeDao fakeReservationTimeDao = new FakeReservationTimeDao();
    private final ReservationService reservationService = new ReservationService(fakeReservationDao,
            fakeReservationTimeDao);

    @BeforeEach
    void setUp() {
        reservationService.deleteAll();
        fakeReservationTimeDao.save(Fixtures.FIRST_TIME);
        fakeReservationTimeDao.save(Fixtures.SECOND_TIME);
        fakeReservationTimeDao.save(Fixtures.THIRD_TIME);
        fakeReservationDao.save(Fixtures.FIRST_RESERVATION);
        fakeReservationDao.save(Fixtures.SECOND_RESERVATION);
    }

    @Test
    void findAll() {
        List<ReservationResponse> reservationResponses = reservationService.findAll();

        assertThat(reservationResponses).isEqualTo(List.of(Fixtures.FIRST_RESPONSE, Fixtures.SECOND_RESPONSE));
    }

    @Test
    void save() {
        reservationService.save(Fixtures.THIRD_REQUEST);

        assertThat(reservationService.findAll()).isEqualTo(List.of(Fixtures.FIRST_RESPONSE, Fixtures.SECOND_RESPONSE, Fixtures.THIRD_RESPONSE));
    }

    @Test
    void deleteById() {
        reservationService.deleteById(1);

        assertThat(reservationService.findAll()).isEqualTo(List.of(Fixtures.SECOND_RESPONSE));
    }

    @Test
    void invalidDeleteById() {
        assertThatThrownBy(() -> reservationService.deleteById(3))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 id는 존재하지 않습니다.");
    }

    @Test
    void deleteAll() {
        reservationService.deleteAll();

        assertThat(reservationService.findAll()).isEqualTo(List.of());
    }
}
