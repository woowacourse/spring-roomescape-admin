package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;
import roomescape.dao.ReservationRepository;
import roomescape.dao.ReservationTimeRepository;
import roomescape.domain.FakeReservationRepository;
import roomescape.domain.FakeReservationTimeRepository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationRequest;

class ReservationServiceTest {

    private final ReservationRepository reservationRepository = new FakeReservationRepository();
    private final ReservationTimeRepository reservationTimeRepository = new FakeReservationTimeRepository();

    @Test
    void 예약_생성_테스트() {
        ReservationService reservationService = new ReservationService(reservationRepository,
                reservationTimeRepository);
        ReservationTime time = new ReservationTime(1L, LocalTime.of(15, 30));
        ReservationRequest request = new ReservationRequest(
                "브라운",
                LocalDate.of(2023, 8, 5),
                1L
        );

        reservationTimeRepository.save(time);
        Reservation savedReservation = reservationService.createReservation(request);

        assertThat(savedReservation).extracting(
                Reservation::getId,
                Reservation::getName,
                Reservation::getDate,
                reservation -> reservation.getTime().getStartAt()
        ).containsExactly(
                1L,
                "브라운",
                LocalDate.of(2023, 8, 5),
                time.getStartAt()
        );
    }

    @Test
    void 아이디에_해당하는_예약_조회_테스트() {
        ReservationService reservationService = new ReservationService(reservationRepository,
                reservationTimeRepository);

        ReservationTime time = new ReservationTime(1L, LocalTime.of(15, 30));
        ReservationRequest request = new ReservationRequest(
                "브라운",
                LocalDate.of(2023, 8, 5),
                1L
        );

        reservationTimeRepository.save(time);
        reservationService.createReservation(request);
        Long findId = 1L;

        assertThat(reservationService.findReservation(findId)).extracting(
                Reservation::getId,
                Reservation::getName,
                Reservation::getDate,
                reservation -> reservation.getTime().getStartAt()
        ).containsExactly(
                1L,
                "브라운",
                LocalDate.of(2023, 8, 5),
                time.getStartAt()
        );
    }

    @Test
    void 모든_예약_조회_테스트() {
        ReservationService reservationService = new ReservationService(reservationRepository,
                reservationTimeRepository);

        ReservationTime timeBrown = new ReservationTime(1L, LocalTime.of(15, 30));
        ReservationRequest requestBrown = new ReservationRequest(
                "브라운",
                LocalDate.of(2023, 8, 5),
                1L
        );

        ReservationTime timePobi = new ReservationTime(2L, LocalTime.of(15, 30));
        ReservationRequest requestPobi = new ReservationRequest(
                "포비",
                LocalDate.of(2023, 9, 5),
                2L
        );

        reservationTimeRepository.save(timeBrown);
        reservationTimeRepository.save(timePobi);
        reservationService.createReservation(requestBrown);
        reservationService.createReservation(requestPobi);

        assertThat(reservationService.findAllReservations()).hasSize(2);
    }

    @Test
    void 아이디에_해당하는_예약_삭제_테스트() {
        ReservationService reservationService = new ReservationService(reservationRepository,
                reservationTimeRepository);

        ReservationTime time = new ReservationTime(1L, LocalTime.of(15, 30));
        ReservationRequest request = new ReservationRequest(
                "브라운",
                LocalDate.of(2023, 8, 5),
                1L
        );

        reservationTimeRepository.save(time);
        reservationService.createReservation(request);
        Long deleteId = 1L;

        reservationService.deleteReservation(deleteId);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> reservationService.findReservation(deleteId))
                .withMessageContaining("존재하지 않는 예약 시간입니다.");
    }

}
