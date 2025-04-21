package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static roomescape.test.utility.ReservationTimeTestUtility.checkDeleteReservationTime;
import static roomescape.test.utility.ReservationTimeTestUtility.checkReservationTimeFieldWithoutId;
import static roomescape.test.utility.ReservationTimeTestUtility.checkReservationTimeId;

import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeCreationRequest;
import roomescape.exception.BadRequestException;
import roomescape.exception.NotFoundException;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;
import roomescape.test.fake.FakeReservationRepository;
import roomescape.test.fake.FakeReservationTimeRepository;
import roomescape.test.fixture.ReservationFixture;
import roomescape.test.fixture.ReservationTimeFixture;

class ReservationTimeServiceTest {

    private final ReservationRepository reservationRepository = new FakeReservationRepository();
    private final ReservationTimeRepository reservationTimeRepository = new FakeReservationTimeRepository();
    private final ReservationTimeService reservationTimeService =
            new ReservationTimeService(reservationRepository, reservationTimeRepository);

    @DisplayName("등록된 모든 예약 가능 시간을 조회활 수 있다")
    @Test
    void canGetReservationTimes() {
        reservationTimeRepository.add(ReservationTime.createWithoutId(LocalTime.of(10, 0)));
        reservationTimeRepository.add(ReservationTime.createWithoutId(LocalTime.of(11, 0)));
        reservationTimeRepository.add(ReservationTime.createWithoutId(LocalTime.of(12, 0)));

        List<ReservationTime> reservationTimes = reservationTimeService.getAll();

        assertThat(reservationTimes).hasSize(3);
    }

    @DisplayName("예약 가능 시간을 추가할 수 있다")
    @Test
    void canCreateReservationTime() {
        ReservationTime expected = ReservationTime.createWithoutId(LocalTime.of(10, 0));
        ReservationTimeCreationRequest request = new ReservationTimeCreationRequest(expected.getStartAt());

        long id = reservationTimeService.saveReservationTime(request);

        ReservationTime savedReservationTime = reservationTimeRepository.findAll().getFirst();
        assertAll(
                () -> checkReservationTimeId(id, 1L),
                () -> checkReservationTimeId(savedReservationTime.getId(), 1L),
                () -> checkReservationTimeFieldWithoutId(savedReservationTime, expected)
        );
    }

    @DisplayName("이미 추가한 시간의 경우 추가할 수 없다")
    @Test
    void canCreateSameReservationTime() {
        LocalTime sameStartAt = LocalTime.of(10, 0);
        reservationTimeRepository.add(ReservationTime.createWithoutId(sameStartAt));
        ReservationTimeCreationRequest request = new ReservationTimeCreationRequest(sameStartAt);

        assertThatThrownBy(() -> reservationTimeService.saveReservationTime(request))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("[Error] 이미 추가가 완료된 예약 가능 시간입니다.");
    }

    @DisplayName("ID를 통해 예약 가능 시간을 삭제할 수 있다")
    @Test
    void canDeleteReservationTime() {
        reservationTimeRepository.add(ReservationTime.createWithoutId(LocalTime.of(10, 0)));
        reservationTimeRepository.add(ReservationTime.createWithoutId(LocalTime.of(11, 0)));
        reservationTimeRepository.add(ReservationTime.createWithoutId(LocalTime.of(12, 0)));
        Long deletedId = reservationTimeRepository.findAll().getFirst().getId();

        reservationTimeService.deleteReservationTime(deletedId);

        checkDeleteReservationTime(reservationTimeRepository.findAll(), deletedId);
    }

    @DisplayName("존재하지 않는 예약 가능 시간을 삭제하려고 할 경우 예외 응답을 보낸다")
    @Test
    void canNotDeleteWithInvalidId() {
        long noneExistentReservationId = 1L;
        assertThatThrownBy(() -> reservationTimeService.deleteReservationTime(noneExistentReservationId))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("[ERROR] ID에 해당하는 예약 시간이 존재하지 않습니다.");
    }

    @DisplayName("이미 해당 시간에 예약이 존재하는 경우 예약을 제거할 수 없습니다.")
    @Test
    void canNotDeleteBecauseReservations() {
        long savedId = reservationTimeRepository.add(ReservationTimeFixture.createReservationTime(LocalTime.of(10, 0)));
        ReservationTime savedTime = reservationTimeRepository.findById(savedId).get();
        reservationRepository.add(ReservationFixture.createReservation("reservation1", savedTime));

        assertThatThrownBy(() -> reservationTimeService.deleteReservationTime(savedId))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("[ERROR] 이미 해당 시간에 대한 예약 데이터들이 존재합니다.");
    }
}