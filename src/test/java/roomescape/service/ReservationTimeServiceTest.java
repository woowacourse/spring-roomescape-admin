package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;
import roomescape.repository.MemoryReservationTimeRepository;

class ReservationTimeServiceTest {

    @TestFactory
    @DisplayName("예약 시간을 저장, 조회, 삭제한다.")
    Collection<DynamicTest> saveReservationTimeAndFindAndDelete() {
        ReservationTimeService reservationTimeService = new ReservationTimeService(
                new MemoryReservationTimeRepository());

        return List.of(
                dynamicTest("예약 시간을 저장한다.", () -> {
                    ReservationTimeRequest reservationTimeRequest = new ReservationTimeRequest(LocalTime.of(10, 0));
                    ReservationTimeResponse newReservationTime = reservationTimeService.createReservationTime(
                            reservationTimeRequest);

                    assertAll(
                            () -> assertThat(newReservationTime.id()).isSameAs(1L),
                            () -> assertThat(newReservationTime.startAt()).isEqualTo("10:00")
                    );
                }),
                dynamicTest("예약 시간을 모두 조회한다.", () -> {
                    assertThat(reservationTimeService.findReservationTimes()).hasSize(1);
                }),
                dynamicTest("예약 시간을 삭제한다.", () -> {
                    reservationTimeService.deleteReservationTime(1L);

                    assertThat(reservationTimeService.findReservationTimes()).hasSize(0);
                }),
                dynamicTest("이미 삭제된 예약 시간을 삭제하려고 시도하면 예외가 발생한다.", () -> {
                    assertThatIllegalArgumentException()
                            .isThrownBy(() -> reservationTimeService.deleteReservationTime(1L))
                            .withMessage("존재하지 않는 시간입니다.");
                })
        );
    }
}
