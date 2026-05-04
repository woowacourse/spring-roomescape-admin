package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.dao.FakeReservationDao;
import roomescape.dao.FakeReservationTimeDao;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequestDto;
import roomescape.dto.ReservationTimeResponseDto;
import roomescape.exception.ReservationTimeInUseException;

class ReservationTimeServiceTest {

    private FakeReservationDao reservationDao;
    private FakeReservationTimeDao reservationTimeDao;
    private ReservationTimeService reservationTimeService;

    @BeforeEach
    void setUp() {
        reservationDao = new FakeReservationDao();
        reservationTimeDao = new FakeReservationTimeDao();
        reservationTimeService = new ReservationTimeService(reservationTimeDao, reservationDao);
    }

    @Test
    @DisplayName("시간을 생성하면 저장 후 응답 DTO를 반환한다")
    void create_savesAndReturnsResponse() {
        ReservationTimeRequestDto request = new ReservationTimeRequestDto(LocalTime.of(10, 0));

        ReservationTimeResponseDto result = reservationTimeService.create(request);

        assertThat(result.id()).isNotNull();
        assertThat(result.startAt()).isEqualTo(LocalTime.of(10, 0));
        assertThat(reservationTimeDao.findAll()).hasSize(1);
    }

    @Test
    @DisplayName("예약이 없는 시간은 정상적으로 삭제된다")
    void delete_removesTimeWhenNoReservationReferencesIt() {
        ReservationTime saved = reservationTimeDao.save(new ReservationTime(LocalTime.of(10, 0)));

        boolean result = reservationTimeService.delete(saved.getId());

        assertThat(result).isTrue();
        assertThat(reservationTimeDao.findAll()).isEmpty();
    }

    @Test
    @DisplayName("예약이 존재하는 시간을 삭제하면 ReservationTimeInUseException 이 발생한다")
    void delete_throwsWhenReservationReferencesTime() {
        ReservationTime time = reservationTimeDao.save(new ReservationTime(LocalTime.of(10, 0)));
        reservationDao.save(new Reservation("브라운", LocalDate.of(2026, 5, 4), time));

        assertThatThrownBy(() -> reservationTimeService.delete(time.getId()))
                .isInstanceOf(ReservationTimeInUseException.class)
                .hasMessageContaining(String.valueOf(time.getId()));
        assertThat(reservationTimeDao.findAll()).hasSize(1);
    }
}
