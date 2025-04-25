package roomescape.time;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.time.dto.ReservationTimeRequest;
import roomescape.time.dto.ReservationTimeResponse;

public class ReservationReservationTimeServiceTest {

    private ReservationTimeService reservationTimeService;
    private FakeReservationTimeDao fakeTimeDao;

    @BeforeEach
    void setUp() {
        fakeTimeDao = new FakeReservationTimeDao();
        reservationTimeService = new ReservationTimeService(fakeTimeDao);
    }

    @DisplayName("TimeRequest를 저장하고, 저장된 TimeResponse를 반환한다.")
    @Test
    void createTime() {
        // given
        final ReservationTimeRequest request = new ReservationTimeRequest(LocalTime.of(12, 40));

        // when
        final ReservationTimeResponse actual = reservationTimeService.createTime(request);

        // then
        assertSoftly(s -> {
            s.assertThat(actual.id()).isEqualTo(1L);
            s.assertThat(actual.startAt()).isEqualTo(LocalTime.of(12, 40));
        });
    }

    @DisplayName("저장된 모든 TimeResponse를 반환한다.")
    @Test
    void findAllTime() {
        // given
        fakeTimeDao.saveTime(new ReservationTime(null, LocalTime.of(12, 40)));

        // when
        final List<ReservationTimeResponse> actual = reservationTimeService.findAllTime();

        // then
        assertThat(actual)
                .hasSize(1)
                .contains(new ReservationTimeResponse(1L, LocalTime.of(12, 40)));
    }

    @DisplayName("id에 해당하는 time을 제거한다")
    @Test
    void deleteTimeById() {
        // given
        fakeTimeDao.saveTime(new ReservationTime(null, LocalTime.of(12, 40)));

        // when
        reservationTimeService.deleteTimeById(1L);
        final boolean actual = fakeTimeDao.isInvokeDeleteId(1L);

        // then
        assertThat(actual).isTrue();
    }
}
