package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static roomescape.util.Fixture.GAMJA_RESERVATION;
import static roomescape.util.Fixture.GAMJA_RESERVATION_RESPONSE;
import static roomescape.util.Fixture.JOJO_RESERVATION;
import static roomescape.util.Fixture.JOJO_RESERVATION_REQUEST;
import static roomescape.util.Fixture.JOJO_RESERVATION_RESPONSE;
import static roomescape.util.Fixture.JOJO_RESERVATION_TIME;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import roomescape.dto.ReservationResponse;
import roomescape.repository.ReservationDao;
import roomescape.repository.ReservationTimeDao;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    private ReservationDao reservationDao;
    @Mock
    private ReservationTimeDao reservationTimeDao;

    @InjectMocks
    private ReservationService reservationService;

    @DisplayName("예약 목록 조회")
    @Test
    void findAllReservation() {
        BDDMockito.given(reservationDao.findAll())
            .willReturn(List.of(JOJO_RESERVATION, GAMJA_RESERVATION));

        List<ReservationResponse> reservations = reservationService.findAll();

        assertThat(reservations).containsExactly(JOJO_RESERVATION_RESPONSE, GAMJA_RESERVATION_RESPONSE);
    }

    @DisplayName("예약 추가")
    @Test
    void createReservation() {
        BDDMockito.given(reservationTimeDao.findById(anyLong()))
            .willReturn(JOJO_RESERVATION_TIME);
        BDDMockito.given(reservationDao.save(any()))
            .willReturn(1L);

        ReservationResponse reservationResponse = reservationService.create(JOJO_RESERVATION_REQUEST);

        assertThat(reservationResponse).isEqualTo(JOJO_RESERVATION_RESPONSE);
    }

    @DisplayName("예약 삭제")
    @Test
    void deleteReservation() {
        BDDMockito.given(reservationDao.deleteById(anyLong()))
            .willReturn(true);

        assertThatCode(() -> reservationService.delete(1L))
            .doesNotThrowAnyException();
    }
}
