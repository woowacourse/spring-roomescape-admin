package roomescape.reservation.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import roomescape.globalException.CustomException;
import roomescape.reservation.ReservationHelper;
import roomescape.reservation.domain.Reservation;
import roomescape.reservationTime.domain.ReservationTime;

import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

class MemoryReservationRepositoryTest {

    private static final ReservationTime DEFAULT_DUMMY_TIME = new ReservationTime(LocalTime.of(11, 22));

    private final MemoryReservationRepository repository = new MemoryReservationRepository();
    private final ReservationHelper helper = new ReservationHelper();
    private Long reservationId;

    @BeforeEach
    void setUp() {
        Reservation reservation = helper.createReservation("kali", 1, DEFAULT_DUMMY_TIME);
        Reservation savedReservation = repository.add(reservation);
        reservationId = savedReservation.getId();
    }

    @Nested
    @DisplayName("저장되어 있는 예약 불러오는 기능")
    class findAll {

        @DisplayName("데이터가 있을 때 모든 예약을 불러온다")
        @Test
        void findAll_success_whenDataExists() {
            // given
            // when
            List<Reservation> reservations = repository.findAll();

            // then
            assertSoftly(s -> {
                        s.assertThat(reservations).hasSize(1);
                        s.assertThat(reservations)
                                .extracting(Reservation::getReservationTime)
                                .contains(DEFAULT_DUMMY_TIME);
                        reservations.forEach(resDto ->
                                s.assertThat(resDto.getId()).isNotNull());
                    }
            );
        }

        @DisplayName("데이터가 없더라도 예외 없이 빈 리스트를 반환한다")
        @Test
        void findAll_success_whenNoData() {
            // given
            deleteAll();

            // when
            List<Reservation> reservations = repository.findAll();

            // then
            Assertions.assertThat(reservations).hasSize(0);
        }

    }

    @Nested
    @DisplayName("id에 따라 예약 반환 기능")
    class findByIdOrThrow {

        @DisplayName("존재하는 id로 요청 시 예약이 반환된다")
        @Test
        void findByIdOrThrow_success_withValidId() {
            // given
            // when
            Reservation reservation = repository.findByIdOrThrow(reservationId);

            // then
            assertSoftly(s -> {
                        s.assertThat(reservation).isNotNull();
                        s.assertThat(reservation.getReservationTime()).isEqualTo(DEFAULT_DUMMY_TIME);
                    }
            );
        }

        @DisplayName("존재하지 않는 id로 요청 시 예외 처리된다")
        @Test
        void findByIdOrThrow_failure_byNonExistenceId() {
            // given
            // when
            // then
            Assertions.assertThatThrownBy(
                    () -> repository.findByIdOrThrow(Long.MAX_VALUE)
            ).isInstanceOf(CustomException.class);
        }
    }


    @Nested
    @DisplayName("예약 추가 기능")
    class add {

        @DisplayName("유효한 입력일 시 예약이 추가된다")
        @Test
        void add_success_whenValidInput() {
            // given
            ReservationTime time = new ReservationTime(LocalTime.of(10, 5));
            Reservation reservation = helper.createReservation("pobi", 2, time);

            // when
            repository.add(reservation);

            // then
            List<Reservation> reservations = repository.findAll();
            Assertions.assertThat(reservations)
                    .extracting(Reservation::getReservationTime)
                    .contains(time);
        }
    }

    @Nested
    @DisplayName("예약 삭제 기능")
    class delete {

        @DisplayName("요청 id에 따른 예약 객체를 삭제한다")
        @Test
        void delete_success_withId() {
            // given
            repository.delete(reservationId);

            // when
            List<Reservation> reservations = repository.findAll();

            // then
            Assertions.assertThat(reservations).hasSize(0);
        }
    }

    private void deleteAll() {
        repository.delete(reservationId);
    }
}

