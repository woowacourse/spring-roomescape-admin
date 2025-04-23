package roomescape.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.dto.ReservationTimeRequestDto;
import roomescape.dto.ReservationTimeResponseDto;
import roomescape.entity.ReservationTime;
import roomescape.exceptions.EntityNotFoundException;
import roomescape.repository.ReservationTimeRepository;

public class ReservationTimeServiceTest {

    private final ReservationTimeService reservationService = new ReservationTimeService(
            new ReservationTimeTestRepository());

    @Test
    @DisplayName("조회된 엔티티를 DTO로 매핑해 반환한다.")
    void test_readReservationTime() {
        //given & when
        List<ReservationTimeResponseDto> actual = reservationService.readReservationTime();
        //then
        assertThat(actual.size()).isEqualTo(1);
        assertThat(actual.getFirst().startAt()).isEqualTo(LocalTime.MIN);
    }

    @Test
    @DisplayName("저장한 엔티티를 DTO로 반환한다.")
    void test_postReservationTime() {
        //given
        ReservationTimeRequestDto requestDto = new ReservationTimeRequestDto(LocalTime.MIN);
        //when
        ReservationTimeResponseDto actual = reservationService.postReservationTime(requestDto);
        //then
        assertThat(actual.startAt()).isEqualTo(LocalTime.MIN);
    }

    @Test
    @DisplayName("저장소에 없는 값을 삭제하려할 경우, 예외가 발생한다.")
    void test_deleteReservationTime() {
        assertThatThrownBy(() -> reservationService.deleteReservationTime(999L))
                .isInstanceOf(EntityNotFoundException.class);
    }

    private static class ReservationTimeTestRepository implements ReservationTimeRepository {

        @Override
        public List<ReservationTime> findAll() {
            return List.of(
                    new ReservationTime(1L, LocalTime.MIN));
        }

        @Override
        public ReservationTime save(ReservationTime reservation) {
            return new ReservationTime(1L, reservation.startAt());
        }

        @Override
        public void deleteById(long id) {
            throw new EntityNotFoundException("");
        }
    }
}
