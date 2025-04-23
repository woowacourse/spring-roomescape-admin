package roomescape.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.entity.Reservation;
import roomescape.entity.ReservationTime;
import roomescape.exceptions.EntityNotFoundException;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;

public class ReservationServiceTest {

    private final ReservationService reservationService = new ReservationService(
            new ReservationTestRepository(),
            new ReservationTimeTestRepository()
    );

    @Test
    @DisplayName("조회된 엔티티를 DTO로 매핑해 반환한다.")
    void test_readReservation() {
        //given & when
        List<ReservationResponseDto> actual = reservationService.readReservation();
        //then
        assertThat(actual.size()).isEqualTo(1);
        assertThat(actual.getFirst().id()).isEqualTo(1);
    }

    @Test
    @DisplayName("저장한 엔티티를 DTO로 반환한다.")
    void test_postReservation() {
        //given
        long timeId = 1L;
        ReservationRequestDto requestDto = new ReservationRequestDto("브라운", LocalDate.now(), timeId);
        //when
        ReservationResponseDto actual = reservationService.postReservation(requestDto);
        //then
        assertThat(actual.id()).isEqualTo(1);
    }

    @Test
    @DisplayName("저장시 time_id를 찾을 수 없다면, 예외가 발생한다.")
    void test_postReservationWhenCantFindReservationTime() {
        //given
        long timeId = Long.MAX_VALUE;
        ReservationRequestDto requestDto = new ReservationRequestDto("브라운", LocalDate.now(), timeId);
        //when&&then
        assertThatThrownBy(() -> reservationService.postReservation(requestDto))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("저장소에 없는 값을 삭제하려할 경우, 예외가 발생한다.")
    void test_deleteReservation() {
        assertThatThrownBy(() -> reservationService.deleteReservation(Long.MAX_VALUE))
                .isInstanceOf(EntityNotFoundException.class);
    }

    private static class ReservationTestRepository implements ReservationRepository {

        @Override
        public List<Reservation> findAll() {
            return List.of(
                    new Reservation(1L, "브라운", LocalDate.of(2025, 1, 1), new ReservationTime(1L, LocalTime.now())));
        }

        @Override
        public Reservation save(Reservation reservation, long timeId) {
            return new Reservation(1L, reservation.name(), reservation.date(),
                    reservation.time());
        }

        @Override
        public void deleteById(long id) {
            throw new EntityNotFoundException("");
        }
    }

    private static class ReservationTimeTestRepository implements ReservationTimeRepository {

        @Override
        public void existsTimeById(long id) {
            if (id == Long.MAX_VALUE) {
                throw new EntityNotFoundException("");
            }
        }

        @Override
        public List<ReservationTime> findAll() {
            return List.of();
        }

        @Override
        public ReservationTime save(ReservationTime reservationTime) {
            return null;
        }

        @Override
        public void deleteById(long id) {
        }
    }
}
