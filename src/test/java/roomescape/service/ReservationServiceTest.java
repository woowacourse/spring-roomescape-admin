package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.repository.MemoryReservationRepository;
import roomescape.repository.MemoryReservationTimeRepository;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;

class ReservationServiceTest {

    @TestFactory
    @DisplayName("예약을 저장, 조회, 삭제한다.")
    Collection<DynamicTest> saveReservationAndFindAndDelete() {
        ReservationRepository reservationRepository = new MemoryReservationRepository();
        ReservationTimeRepository reservationTimeRepository = new MemoryReservationTimeRepository();
        ReservationService reservationService = new ReservationService(
                reservationRepository,
                reservationTimeRepository);
        reservationTimeRepository.save(new ReservationTime(LocalTime.of(10, 0)));

        return List.of(
                dynamicTest("예약을 저장한다.", () -> {
                    ReservationRequest reservationRequest = new ReservationRequest("리브", LocalDate.of(2024, 4, 27), 1L);
                    ReservationResponse reservationResponse = reservationService.createReservation(reservationRequest);

                    Assertions.assertAll(
                            () -> assertThat(reservationResponse.id()).isSameAs(1L),
                            () -> assertThat(reservationResponse.name()).isEqualTo("리브"),
                            () -> assertThat(reservationResponse.date()).isEqualTo(LocalDate.of(2024, 4, 27)),
                            () -> assertThat(reservationResponse.time().id()).isEqualTo(1L),
                            () -> assertThat(reservationResponse.time().startAt()).isEqualTo("10:00")
                    );
                }),
                dynamicTest("저장된 예약을 모두 조회한다.", () -> {
                    assertThat(reservationService.findReservations()).hasSize(1);
                }),
                dynamicTest("예약을 삭제한다.", () -> {
                    reservationService.deleteReservation(1L);

                    assertThat(reservationService.findReservations()).hasSize(0);
                }),
                dynamicTest("이미 삭제된 예약을 삭제하려고 시도하면 예외가 발생한다.", () -> {
                    assertThatIllegalArgumentException()
                            .isThrownBy(() -> reservationService.deleteReservation(1L))
                            .withMessage("존재하지 않는 예약입니다.");
                })
        );
    }
}
