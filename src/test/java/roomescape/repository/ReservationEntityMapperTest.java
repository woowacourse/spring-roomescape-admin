package roomescape.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

class ReservationEntityMapperTest {

    private static final long TEST_RESERVATION_ID = 1L;
    private static final long TEST_TIME_ID = 9999L;
    private static final String TESTER_NAME = "라티";
    private static final String TEST_DATE = "2026-24-28";
    private static final String TEST_TIME = "18:00";


    private static final ReservationTime testReservationTime = new ReservationTime(TEST_TIME_ID, TEST_TIME);
    private static final ReservationTimeEntity testReservationTimeEntity = new ReservationTimeEntity(TEST_TIME_ID,
            TEST_TIME);

    ReservationTimeEntityMapper timeEntityMapper = new ReservationTimeEntityMapper();
    ReservationEntityMapper reservationEntityMapper = new ReservationEntityMapper(timeEntityMapper);

    Reservation testReservation;
    ReservationEntity testReservationEntity;

    @BeforeEach
    void prepareInstanceOfTest() {
        testReservation = new Reservation(TEST_RESERVATION_ID, TESTER_NAME, TEST_DATE, testReservationTime);
        testReservationEntity = new ReservationEntity(TEST_RESERVATION_ID, TESTER_NAME, TEST_DATE,
                testReservationTimeEntity);
    }

    @Test
    @DisplayName("엔티티로 잘 변환한다")
    void toReservation_Entity_success() {
        //when
        ReservationEntity result = reservationEntityMapper.toReservationEntity(testReservation);

        //then
        Assertions.assertEquals(testReservationEntity, result);
    }

    @Test
    @DisplayName("id가 없어도 엔티티로 잘 변환한다")
    void toReservation_Entity_withNoID_success() {
        //when
        ReservationEntity result = reservationEntityMapper.toReservationEntity(testReservation);

        //then
        Assertions.assertEquals(testReservationEntity, result);
    }

    @Test
    @DisplayName("도메인 객체로 잘 변환한다")
    void toReservation_success() {
        //when
        Reservation result = reservationEntityMapper.toReservation(testReservationEntity);

        //then
        Assertions.assertEquals(testReservation, result);
    }
}
