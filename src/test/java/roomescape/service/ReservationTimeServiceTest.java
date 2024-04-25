package roomescape.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.dao.FakeReservationTimeRepository;
import roomescape.dao.ReservationTimeRepository;
import roomescape.domain.ReservationTime;
import roomescape.exception.InvalidReservationException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class ReservationTimeServiceTest {
    private ReservationTimeRepository reservationTimeRepository;
    private ReservationTimeService reservationTimeService;

    @BeforeEach
    void setUp() {
        reservationTimeRepository = new FakeReservationTimeRepository();
        reservationTimeService = new ReservationTimeService(reservationTimeRepository);
    }

    @AfterEach
    void init() {
        for (final ReservationTime reservationTime : reservationTimeRepository.findAll()) {
            reservationTimeRepository.deleteById(reservationTime.getId());
        }
    }

    @DisplayName("새로운 예약 시간을 저장한다.")
    @Test
    void create() {
        //given
        String startAt = "10:00";
        ReservationTime reservationTime = new ReservationTime(startAt);

        //when
        ReservationTime result = reservationTimeService.create(reservationTime);

        //then
        assertAll(
                () -> assertThat(result.getId()).isNotZero(),
                () -> assertThat(result.getStartAt()).isEqualTo(startAt)
        );
    }

    @DisplayName("모든 예약 시간 내역을 조회한다.")
    @Test
    void findAll() {
        //given
        String startAt = "10:00";
        ReservationTime reservationTime = new ReservationTime(startAt);
        reservationTimeService.create(reservationTime);

        //when
        List<ReservationTime> reservationTimes = reservationTimeService.findAll();

        //then
        assertThat(reservationTimes).hasSize(1);
    }

    @DisplayName("id로 예약 시간을 조회한다.")
    @Test
    void findById() {
        //given
        String startAt = "10:00";
        ReservationTime reservationTime = new ReservationTime(startAt);
        ReservationTime target = reservationTimeService.create(reservationTime);

        //when
        ReservationTime result = reservationTimeService.findById(target.getId());

        //then
        assertAll(
                () -> assertThat(result.getId()).isEqualTo(target.getId()),
                () -> assertThat(result.getStartAt()).isEqualTo(startAt)
        );
    }

    @DisplayName("존재하지 않는 id로 예약 시간 조회를 시도하면 예외를 던진다.")
    @Test
    void findByInvalidId() {
        //given
        long invalidId = 0;

        //then&then
        assertThatThrownBy(() -> reservationTimeService.findById(invalidId))
                .isInstanceOf(InvalidReservationException.class)
                .hasMessage("존재하지 않는 예약 시간입니다. id: " + invalidId);
    }

    @DisplayName("id로 예약 시간을 삭제한다.")
    @Test
    void deleteById() {
        //given
        String startAt = "10:00";
        ReservationTime reservationTime = new ReservationTime(startAt);
        ReservationTime result = reservationTimeService.create(reservationTime);

        //when
        reservationTimeService.deleteById(result.getId());

        //then
        assertThat(reservationTimeService.findAll()).hasSize(0);
    }
}
