package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.dao.FakeReservationDao;
import roomescape.dao.FakeReservationTimeDao;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.exception.ReservationTimeNotFoundException;

class ReservationServiceTest {

    private FakeReservationDao reservationDao;
    private FakeReservationTimeDao reservationTimeDao;
    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        reservationDao = new FakeReservationDao();
        reservationTimeDao = new FakeReservationTimeDao();
        reservationService = new ReservationService(reservationDao, reservationTimeDao);
    }

    @Test
    @DisplayName("저장된 예약이 없으면 빈 리스트를 반환한다")
    void findAll_returnsEmptyListWhenNothingSaved() {
        assertThat(reservationService.findAll()).isEmpty();
    }

    @Test
    @DisplayName("저장된 예약을 모두 조회한다")
    void findAll_returnsAllSavedReservations() {
        ReservationTime time = reservationTimeDao.save(new ReservationTime(LocalTime.of(10, 0)));
        reservationDao.save(new Reservation("브라운", LocalDate.of(2026, 5, 4), time));
        reservationDao.save(new Reservation("네오", LocalDate.of(2026, 5, 5), time));

        List<ReservationResponseDto> result = reservationService.findAll();

        assertThat(result).extracting(ReservationResponseDto::name)
                .containsExactly("브라운", "네오");
    }

    @Test
    @DisplayName("예약을 생성하면 저장 후 응답 DTO를 반환한다")
    void create_savesAndReturnsResponse() {
        ReservationTime time = reservationTimeDao.save(new ReservationTime(LocalTime.of(10, 0)));
        ReservationRequestDto request = new ReservationRequestDto("브라운", LocalDate.of(2026, 5, 4), time.getId());

        ReservationResponseDto result = reservationService.create(request);

        assertThat(result.id()).isNotNull();
        assertThat(result.name()).isEqualTo("브라운");
        assertThat(result.date()).isEqualTo(LocalDate.of(2026, 5, 4));
        assertThat(result.time().id()).isEqualTo(time.getId());
        assertThat(reservationDao.findAll()).hasSize(1);
    }

    @Test
    @DisplayName("존재하지 않는 시간 ID로 예약을 생성하면 예외가 발생한다")
    void create_throwsWhenTimeIdNotFound() {
        ReservationRequestDto request = new ReservationRequestDto("브라운", LocalDate.of(2026, 5, 4), 999L);

        assertThatThrownBy(() -> reservationService.create(request))
                .isInstanceOf(ReservationTimeNotFoundException.class);
    }

    @Test
    @DisplayName("존재하는 예약을 삭제하면 true 를 반환한다")
    void delete_returnsTrueWhenReservationExists() {
        ReservationTime time = reservationTimeDao.save(new ReservationTime(LocalTime.of(10, 0)));
        Reservation saved = reservationDao.save(new Reservation("브라운", LocalDate.of(2026, 5, 4), time));

        boolean result = reservationService.delete(saved.getId());

        assertThat(result).isTrue();
        assertThat(reservationDao.findAll()).isEmpty();
    }

    @Test
    @DisplayName("존재하지 않는 예약을 삭제하면 false 를 반환한다")
    void delete_returnsFalseWhenReservationMissing() {
        assertThat(reservationService.delete(999L)).isFalse();
    }
}
