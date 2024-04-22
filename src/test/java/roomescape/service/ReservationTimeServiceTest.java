package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import roomescape.controller.dto.ReservationTimeCreateRequest;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationTimeMemoryRepository;

class ReservationTimeServiceTest {

    private final ReservationTimeMemoryRepository reservationTimeRepository = new ReservationTimeMemoryRepository();
    private final ReservationTimeService reservationTimeService = new ReservationTimeService(reservationTimeRepository);

    @AfterEach
    void cleanUp() {
        reservationTimeRepository.cleanUp();
    }

    @DisplayName("예약 시간 전체 조회 테스트")
    @Nested
    class FindReservationTimesTest {
        @DisplayName("저장된 모든 예약시간을 조회할 수 있다.")
        @Test
        void findReservationTimesTest() {
            Long createdReservationTimeId1 = reservationTimeRepository.createReservationTime(
                    new ReservationTimeCreateRequest("10:00"));
            Long createdReservationTimeId2 = reservationTimeRepository.createReservationTime(
                    new ReservationTimeCreateRequest("11:00"));
            Long createdReservationTimeId3 = reservationTimeRepository.createReservationTime(
                    new ReservationTimeCreateRequest("12:00"));

            List<ReservationTime> reservationTimes = reservationTimeService.findReservationTimes();
            Assertions.assertAll(
                    () -> assertThat(reservationTimes).hasSize(3),
                    () -> assertThat(reservationTimes).containsExactly(
                            new ReservationTime(createdReservationTimeId1, LocalTime.of(10, 0)),
                            new ReservationTime(createdReservationTimeId2, LocalTime.of(11, 0)),
                            new ReservationTime(createdReservationTimeId3, LocalTime.of(12, 0))
                    )
            );
        }
    }

    @DisplayName("예약 시간 단건 조회 테스트")
    @Nested
    class FindReservationTimeTest {
        @DisplayName("id를 이용해 존재하는 예약 시간을 조회할 수 있다.")
        @Test
        void findReservationTimeTest() {
            Long createdReservationTimeId = reservationTimeRepository.createReservationTime(
                    new ReservationTimeCreateRequest("10:00"));

            Optional<ReservationTime> reservationTimeById = reservationTimeService.findReservationTimeById(
                    createdReservationTimeId);

            Assertions.assertAll(
                    () -> assertThat(reservationTimeById.isPresent()).isTrue(),
                    () -> assertThat(reservationTimeById.get()).isEqualTo(
                            new ReservationTime(createdReservationTimeId, LocalTime.of(10, 0)))
            );
        }
    }

    @DisplayName("예약 시간 생성 테스트")
    @Nested
    class CreateReservationTimeTest {
        @DisplayName("예약 시간을 정상적으로 생성할 수 있다")
        @Test
        void createReservationTimeTest() {
            Long createdReservationTimeId = reservationTimeService.createReservationTime(
                    new ReservationTimeCreateRequest("10:00"));

            Optional<ReservationTime> reservationTimeById = reservationTimeRepository.findReservationTimeById(
                    createdReservationTimeId);

            Assertions.assertAll(
                    () -> assertThat(reservationTimeById.isPresent()).isTrue(),
                    () -> assertThat(reservationTimeById.get()).isEqualTo(
                            new ReservationTime(createdReservationTimeId, LocalTime.of(10, 0)))
            );
        }
    }

    @DisplayName("예약 시간 삭제 테스트")
    @Nested
    class DeleteReservationTimeTest {
        @DisplayName("id를 이용해 예약 시간을 삭제할 수 있다.")
        @Test
        void deleteReservationTimeTest() {
            Long createdReservationTimeId = reservationTimeRepository.createReservationTime(
                    new ReservationTimeCreateRequest("10:00"));

            assertThat(reservationTimeRepository.findReservationTimes()).hasSize(1);

            reservationTimeService.deleteReservationTimeById(createdReservationTimeId);

            assertThat(reservationTimeRepository.findReservationTimes()).hasSize(0);
        }
    }
}
