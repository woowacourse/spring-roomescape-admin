package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.domain.ReservationDao;
import roomescape.domain.ReservationTimeDao;
import roomescape.dto.ReservationRequest;
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

    @DisplayName("전체 예약을 반환한다.")
    @Test
    void findAll() {
        List<ReservationResponse> reservationResponses = reservationService.findAll();

        assertThat(reservationResponses).isEqualTo(List.of(Fixtures.FIRST_RESPONSE, Fixtures.SECOND_RESPONSE));
    }

    @DisplayName("예약을 저장한다.")
    @Test
    void save() {
        reservationService.save(Fixtures.THIRD_REQUEST);

        assertThat(reservationService.findAll()).isEqualTo(List.of(Fixtures.FIRST_RESPONSE, Fixtures.SECOND_RESPONSE, Fixtures.THIRD_RESPONSE));
    }

    @DisplayName("이름이 null 값인 예약을 저장할 경우 예외를 발생시킨다.")
    @Test
    void nameIsNull() {
        ReservationRequest reservationRequest = new ReservationRequest(null, LocalDate.of(2024, 1, 1), 1L);

        assertThatThrownBy(() -> reservationService.save(reservationRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이름이 입력되지 않았습니다. 이름을 입력해주세요.");
    }

    @DisplayName("이름이 공백 문자로만 이루어진 예약을 저장할 경우 예외를 발생시킨다.")
    @Test
    void nameIsBlank() {
        ReservationRequest reservationRequest = new ReservationRequest(" ", LocalDate.of(2024, 1, 1), 1L);

        assertThatThrownBy(() -> reservationService.save(reservationRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이름이 입력되지 않았습니다. 이름을 입력해주세요.");
    }

    @DisplayName("날짜가 null 값인 예약을 저장할 경우 예외를 발생시킨다.")
    @Test
    void dateIsNull() {
        ReservationRequest reservationRequest = new ReservationRequest("first", null, 1L);

        assertThatThrownBy(() -> reservationService.save(reservationRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("날짜가 입력되지 않았습니다. 날짜를 입력해주세요.");
    }

    @DisplayName("해당 id의 예약을 삭제한다.")
    @Test
    void deleteById() {
        reservationService.deleteById(1);

        assertThat(reservationService.findAll()).isEqualTo(List.of(Fixtures.SECOND_RESPONSE));
    }

    @DisplayName("해당 id의 예약이 존재하지 않을 경우 예외를 발생시킨다.")
    @Test
    void invalidDeleteById() {
        assertThatThrownBy(() -> reservationService.deleteById(3))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 id는 존재하지 않습니다. id = %d", 3);
    }

    @DisplayName("전체 예약을 삭제한다.")
    @Test
    void deleteAll() {
        reservationService.deleteAll();

        assertThat(reservationService.findAll()).isEqualTo(List.of());
    }
}
