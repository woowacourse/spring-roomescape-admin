package roomescape.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationSaveDto;
import roomescape.fixture.TestReservationDao;
import roomescape.fixture.TestReservationTimeDao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

class ReservationServiceTest {

    private final int INSERT_INCREMENT = 1;
    private final LocalTime time = LocalTime.of(10, 0);
    private final LocalDate date = LocalDate.of(2026, 5, 3);
    private final String name = "송송";
    private final ReservationTime reservationTime = new ReservationTime(1L, time);
    private final Reservation reservation = new Reservation(name, date, reservationTime);

    @Nested
    class ReadAll {

        @Test
        void 등록된_예약이_없으면_빈_리스트를_반환한다() {
            // given
            List<Reservation> emptyReservations = List.of();
            ReservationService service = generateReservationService(emptyReservations);

            // when
            List<Reservation> actual = service.readAll();

            // then
            Assertions.assertThat(actual)
                    .isEmpty();
        }

        @Test
        void 등록된_예약이_여러개이면_조회시_등록된_개수만큼_반환한다() {
            // given
            List<Reservation> reservations = List.of(reservation, reservation);
            ReservationService service = generateReservationService(reservations);

            // when
            List<Reservation> actual = service.readAll();

            // then
            Assertions.assertThat(actual)
                    .hasSize(reservations.size());
        }

    }

    @Nested
    class Reserve {

        @Test
        void 등록시_예약_데이터수가_1증가한다() {
            // given
            List<Reservation> emptyReservations = List.of();
            ReservationService service = generateReservationService(
                    emptyReservations,
                    List.of(reservationTime)
            );
            ReservationSaveDto dto = new ReservationSaveDto(name, date, reservationTime.getId());

            // when
            service.reserve(dto);

            // then
            Assertions.assertThat(service.readAll())
                    .hasSize(emptyReservations.size() + INSERT_INCREMENT);
        }

        @Test
        void 등록한_예약과_반환되는_예약의_id를_제외한_모든필드가_일치한다() {
            // given
            ReservationService service = generateReservationService(
                    List.of(),
                    List.of(reservationTime)
            );
            ReservationSaveDto dto = new ReservationSaveDto(name, date, reservationTime.getId());

            // when
            Reservation actual = service.reserve(dto);

            // then
            Assertions.assertThat(actual)
                    .usingRecursiveComparison()
                    .ignoringFields("id")
                    .isEqualTo(reservation);
        }

        @Test
        void 존재하지_않는_timeId로_예약하면_예외가_발생한다() {
            // given
            Long wrongId = Long.MIN_VALUE;
            ReservationService service = generateReservationService(List.of(), List.of());
            ReservationSaveDto saveDto = new ReservationSaveDto(name, date, wrongId);

            // when & then
            Assertions.assertThatThrownBy(() -> service.reserve(saveDto))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("[ERROR] 존재하지 않는 ReservationTime 입니다.");
        }
    }

    @Nested
    class Cancel {

        @Test
        void 존재하는_ID로_취소하면_저장소에서_제거된다() {
            // given
            ReservationService service = generateReservationService(List.of(), List.of(reservationTime));
            ReservationSaveDto saveDto = new ReservationSaveDto(name, date, reservationTime.getId());
            Reservation savedReservation = service.reserve(saveDto);

            // when
            service.cancel(savedReservation.getId());

            // then
            Assertions.assertThat(service.readAll())
                    .isEmpty();
        }

        @Test
        void 존재하지_않는_ID로_취소하면_예외가_발생한다() {
            // given
            Long wrongId = Long.MIN_VALUE;
            ReservationService service = generateReservationService(List.of(), List.of());

            // when & then
            Assertions.assertThatThrownBy(() -> service.cancel(wrongId))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("[ERROR] 존재하지 않는 id 이므로, Reservation을 삭제할 수 없습니다.");
        }

    }

    private ReservationService generateReservationService(List<Reservation> reservations) {
        TestReservationDao testReservationDao = new TestReservationDao(reservations);
        TestReservationTimeDao testReservationTimeDao = new TestReservationTimeDao(List.of());
        return new ReservationService(testReservationDao, testReservationTimeDao);
    }

    private ReservationService generateReservationService(List<Reservation> reservations, List<ReservationTime> times) {
        TestReservationDao testReservationDao = new TestReservationDao(reservations);
        TestReservationTimeDao testReservationTimeDao = new TestReservationTimeDao(times);
        return new ReservationService(testReservationDao, testReservationTimeDao);
    }

}
