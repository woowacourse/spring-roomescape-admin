package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static roomescape.util.Fixture.GAMJA_RESERVATION_TIME_AFTER_SAVE;
import static roomescape.util.Fixture.GAMJA_RESERVATION_TIME_RESPONSE;
import static roomescape.util.Fixture.JOJO_RESERVATION_TIME_AFTER_SAVE;
import static roomescape.util.Fixture.JOJO_RESERVATION_TIME_REQUEST;
import static roomescape.util.Fixture.JOJO_RESERVATION_TIME_RESPONSE;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import roomescape.dto.ReservationTimeResponse;
import roomescape.repository.ReservationTimeDao;

@ExtendWith(MockitoExtension.class)
class ReservationTimeServiceTest {

    @Mock
    private ReservationTimeDao reservationTimeDao;

    @InjectMocks
    private ReservationTimeService reservationTimeService;

    @DisplayName("시간 목록 조회")
    @Test
    void findAllReservationTime() {
        BDDMockito.given(reservationTimeDao.findAll())
            .willReturn(
                List.of(JOJO_RESERVATION_TIME_AFTER_SAVE, GAMJA_RESERVATION_TIME_AFTER_SAVE)
            );

        List<ReservationTimeResponse> reservationTimes = reservationTimeService.findAll();

        assertThat(reservationTimes).containsExactly(
            JOJO_RESERVATION_TIME_RESPONSE,
            GAMJA_RESERVATION_TIME_RESPONSE
        );
    }

    @DisplayName("시간 추가")
    @Test
    void createReservationTime() {
        BDDMockito.given(reservationTimeDao.save(any()))
            .willReturn(JOJO_RESERVATION_TIME_AFTER_SAVE);

        ReservationTimeResponse reservationTimeResponse = reservationTimeService.create(
            JOJO_RESERVATION_TIME_REQUEST);

        assertThat(reservationTimeResponse).isEqualTo(JOJO_RESERVATION_TIME_RESPONSE);
    }

    @DisplayName("시간 삭제")
    @Test
    void deleteReservationTime() {
        BDDMockito.given(reservationTimeDao.deleteById(anyLong()))
            .willReturn(true);

        assertThatCode(() -> reservationTimeService.delete(1L))
            .doesNotThrowAnyException();
    }
}
