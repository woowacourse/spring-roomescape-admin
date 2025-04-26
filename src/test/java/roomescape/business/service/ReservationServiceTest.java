package roomescape.business.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.data.entity.TimeEntity;
import roomescape.fake.FakeReservationDao;
import roomescape.fake.FakeTimeDao;
import roomescape.presentation.dto.ReservationRequest;
import roomescape.presentation.dto.ReservationResponse;

public class ReservationServiceTest {

    private static final LocalDate FORMATTED_MAX_LOCAL_DATE = LocalDate.of(9999, 12, 31);
    private static final LocalTime FORMATTED_MAX_LOCAL_TIME = LocalTime.of(23, 59);

    private ReservationService reservationService;

    private final FakeTimeDao timeDaoFixture = new FakeTimeDao(new ArrayList<>(List.of(
            new TimeEntity(1L, FORMATTED_MAX_LOCAL_TIME.toString())
    )));
    private final TimeService timeServiceFixture = new TimeService(timeDaoFixture);

    @BeforeEach
    void setUp() {
        reservationService = new ReservationService(
                timeServiceFixture,
                new FakeReservationDao(timeDaoFixture.getTimes())
        );
    }

    @DisplayName("방탈출 예약을 저장한다.")
    @Test
    void create() {
        // given
        final ReservationRequest reservationRequest = new ReservationRequest(
                "hotteok", FORMATTED_MAX_LOCAL_DATE, 1L
        );
        final ReservationResponse expected = new ReservationResponse(
                1L, "hotteok", FORMATTED_MAX_LOCAL_DATE, FORMATTED_MAX_LOCAL_TIME
        );

        // when & then
        assertThat(reservationService.create(reservationRequest))
                .isEqualTo(expected);
    }

    @DisplayName("저장하려는 예약에 해당하는 방탈출 시간이 없다면 예외가 발생한다.")
    @Test
    void createOrThrowIfTimeIdNotExists() {
        // given
        final ReservationRequest reservationRequest = new ReservationRequest(
                "hotteok", FORMATTED_MAX_LOCAL_DATE, 2L
        );

        // when & then
        assertThatThrownBy(() -> reservationService.create(reservationRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당하는 id가 없습니다.");
    }

    @DisplayName("저장하려는 예약이 현재 날짜/시간보다 과거라면 예외가 발생한다.")
    @Test
    void createOrThrowIfFuture() {
        // given
        final ReservationRequest reservationRequest = new ReservationRequest(
                "hotteok", LocalDate.of(0, 1, 1), 1L
        );

        // when & then
        assertThatThrownBy(() -> reservationService.create(reservationRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("예약 날짜 및 시간이 현재보다 과거일 수 없습니다.");
    }

    @DisplayName("모든 방탈출 예약을 조회한다.")
    @Test
    void findAll() {
        // given
        reservationService.create(new ReservationRequest(
                "hotteok", FORMATTED_MAX_LOCAL_DATE, 1L
        ));
        reservationService.create(new ReservationRequest(
                "saba", FORMATTED_MAX_LOCAL_DATE.minusDays(1), 1L
        ));

        // when & then
        assertThat(reservationService.findAll())
                .containsExactly(
                        new ReservationResponse(1L, "hotteok", FORMATTED_MAX_LOCAL_DATE, FORMATTED_MAX_LOCAL_TIME),
                        new ReservationResponse(2L, "saba", FORMATTED_MAX_LOCAL_DATE.minusDays(1),
                                FORMATTED_MAX_LOCAL_TIME)
                );
    }

    @DisplayName("방탈출 예약을 삭제한다.")
    @Test
    void remove() {
        // given
        reservationService.create(new ReservationRequest(
                "hotteok", FORMATTED_MAX_LOCAL_DATE, 1L
        ));

        // when
        reservationService.remove(1L);

        // then
        assertThat(reservationService.findAll()).isEmpty();
    }

    @DisplayName("석재하려는 예약이 존재하지 않으면 예외가 발생한다.")
    @Test
    void removeOrThrowIfIdNotExists() {
        // given & when & then
        assertThatThrownBy(() -> reservationService.remove(1L))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
