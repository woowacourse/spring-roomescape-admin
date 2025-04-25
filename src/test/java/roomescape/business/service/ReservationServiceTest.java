package roomescape.business.service;

import static org.assertj.core.api.Assertions.assertThat;

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
}
