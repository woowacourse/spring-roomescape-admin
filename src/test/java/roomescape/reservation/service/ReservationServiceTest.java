package roomescape.reservation.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.globalException.CustomException;
import roomescape.reservation.repository.ReservationRepositoryImpl;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.domain.dto.ReservationReqDto;
import roomescape.reservation.fixture.ReservationFixture;
import roomescape.reservationTime.domain.ReservationTime;
import roomescape.reservationTime.fixture.ReservationTimeFixture;
import roomescape.reservationTime.repository.ReservationTimeRepositoryImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ReservationServiceTest {

    @Autowired
    private ReservationService service;
    @Autowired
    private ReservationRepositoryImpl reservationRepository;
    @Autowired
    private ReservationTimeRepositoryImpl reservationTimeRepository;

    @Nested
    @DisplayName("예약 추가하기 기능")
    class add {

        @DisplayName("이미 같은 시간에 예약이 존재한다면 예외 처리한다.")
        @Test
        void add_failure_byDuplicateDateTime() {
            // given

            String dummyName1 = "kali";
            int dummyFuturePlusDay1 = 1;
            LocalDateTime dummyFuture = LocalDateTime.now().plusDays(dummyFuturePlusDay1);
            LocalDate dummyDate = dummyFuture.toLocalDate();
            LocalTime dummyTime = LocalTime.of(11, 33);

            ReservationTime reservationTime1 = ReservationTimeFixture.create(dummyTime);
            ReservationTime savedReservationTime1 = reservationTimeRepository.add(reservationTime1);
            Reservation reservation1 = Reservation.of(dummyName1, dummyDate, savedReservationTime1);

            String dummyName2 = "pobi";
            int dummyFuturePlusDay2 = 2;
            LocalDateTime dummyFuture2 = LocalDateTime.now().plusDays(dummyFuturePlusDay2);
            LocalDate dummyDate2 = dummyFuture2.toLocalDate();
            LocalTime dummyTime2 = LocalTime.of(22, 44);

            ReservationTime reservationTime2 = ReservationTimeFixture.create(dummyTime2);
            ReservationTime savedReservationTime2 = reservationTimeRepository.add(reservationTime2);
            Reservation reservation2 = Reservation.of(dummyName2, dummyDate2, savedReservationTime2);

            reservationRepository.add(reservation1);
            reservationRepository.add(reservation2);

            // when & then
            String dummyName3 = "jason";
            LocalDate duplicateDate = dummyDate;
            Long duplicateReservationTimeId = savedReservationTime1.getId();
            ReservationReqDto reqDto = ReservationFixture.createReqDto(dummyName3, duplicateDate, duplicateReservationTimeId);

            Assertions.assertThatThrownBy(
                    () -> service.add(reqDto)
            ).isInstanceOf(CustomException.class);
        }

        @DisplayName("시간이 같아도 날짜가 다르다면 예약이 가능하다.")
        @Test
        void add_success_withDifferenceDateAndSameTime() {
            // given
            LocalTime duplicateTime = LocalTime.of(11, 22);
            ReservationTime reservationTime = ReservationTimeFixture.create(duplicateTime);
            Long reservationTimeId = reservationTimeRepository.insertWithKeyHolder(reservationTime);
            ReservationTime savedReservationTime = reservationTimeRepository.findById(reservationTimeId);

            String dummyName1 = "kali";
            int dummyFuturePlusDay1 = 1;
            LocalDateTime dummyFuture = LocalDateTime.now().plusDays(dummyFuturePlusDay1);
            LocalDate dummyDate = dummyFuture.toLocalDate();

            Reservation reservation1 = ReservationFixture.create(dummyName1, dummyDate, savedReservationTime);

            String dummyName2 = "pobi";
            int dummyFuturePlusDay2 = 2;
            LocalDateTime dummyFuture2 = LocalDateTime.now().plusDays(dummyFuturePlusDay2);
            LocalDate dummyDate2 = dummyFuture2.toLocalDate();

            Reservation reservation2 = ReservationFixture.create(dummyName2, dummyDate2, savedReservationTime);

            reservationRepository.add(reservation1);
            reservationRepository.add(reservation2);

            // when & then
            String dummyName3 = "jason";
            int dummyFuturePlusDay3 = 3;
            LocalDateTime dummyFuture3 = LocalDateTime.now().plusDays(dummyFuturePlusDay3);
            LocalDate date = dummyFuture3.toLocalDate();
            ReservationReqDto reqDto = ReservationFixture.createReqDto(dummyName3, date, reservationTimeId);

            Assertions.assertThatCode(
                    () -> service.add(reqDto)
            ).doesNotThrowAnyException();
        }
    }
}
