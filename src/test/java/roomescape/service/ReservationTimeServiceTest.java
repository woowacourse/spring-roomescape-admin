package roomescape.service;

import java.time.LocalTime;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;
import roomescape.FakeReservationTimeDaoImpl;
import roomescape.domain.ReservationTime;
import roomescape.service.dto.ReservationTimeRegisterDto;
import roomescape.service.dto.ReservationTimeResponseDto;

public class ReservationTimeServiceTest {
    private final FakeReservationTimeDaoImpl fakeReservationTimeRepositoryImpl = new FakeReservationTimeDaoImpl();

    private final ReservationTimeService reservationTimeService = new ReservationTimeService(
            fakeReservationTimeRepositoryImpl);

    @Test
    void 예약_시각_저장_시에_저장된_id를_반환한다() {
        // given
        ReservationTimeRegisterDto reservationTimeRegisterDto = new ReservationTimeRegisterDto(LocalTime.of(23, 30));

        // when
        Long savedId = reservationTimeService.saveReservationTime(reservationTimeRegisterDto);

        // then
        assertThat(savedId).isEqualTo(1L);
    }

    @Test
    void 예약_시각_저장_시에_id로_저장된_객체를_찾는다() {
        // given
        ReservationTime savedReservationTime = new ReservationTime(LocalTime.of(23, 30));
        long savedId = fakeReservationTimeRepositoryImpl.save(savedReservationTime);

        // when
        ReservationTime foundReservationTime = reservationTimeService.findReservationTimeById(savedId);

        // then
        assertThat(foundReservationTime).isEqualTo(savedReservationTime);
    }

    @Test
    void 모든_예약_시각을_조회한다() {
        // given
        fakeReservationTimeRepositoryImpl.save(new ReservationTime(LocalTime.of(23, 30)));
        fakeReservationTimeRepositoryImpl.save(new ReservationTime(LocalTime.of(23, 31)));
        fakeReservationTimeRepositoryImpl.save(new ReservationTime(LocalTime.of(23, 32)));

        // when
        List<ReservationTimeResponseDto> foundReservationTimeDtos = reservationTimeService.findAllReservationTimes();

        // then
        Assertions.assertAll(
                () -> assertThat(foundReservationTimeDtos).hasSize(3),
                () -> assertThat(foundReservationTimeDtos).containsExactlyInAnyOrder(
                        new ReservationTimeResponseDto(1L, LocalTime.of(23, 30)),
                        new ReservationTimeResponseDto(2L, LocalTime.of(23, 31)),
                        new ReservationTimeResponseDto(3L, LocalTime.of(23, 32))
                )
        );
    }

    @Test
    void id로_저장된_객체를_삭제한다() {
        // given
        long savedId = fakeReservationTimeRepositoryImpl.save(new ReservationTime(LocalTime.of(23, 30)));

        // when
        fakeReservationTimeRepositoryImpl.deleteById(savedId);

        // then
        assertThat(fakeReservationTimeRepositoryImpl.findById(savedId)).isEmpty();
    }

    @Test
    void 존재하지_않는_id의_객체를_삭제하고자_하면_예외가_발생한다() {
        // then
        assertThrows(ResponseStatusException.class, () -> {
            reservationTimeService.deleteReservationTimeById(1L);
        });
    }
}
