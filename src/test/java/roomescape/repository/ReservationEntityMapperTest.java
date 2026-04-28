package roomescape.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.domain.Reservation;

class ReservationEntityMapperTest {

    private static final long TEST_ID = 1L;
    private static final String TESTER_NAME = "라티";
    private static final String TEST_DATE = "2026-24-28";
    private static final String TEST_TIME = "18:00";

    ReservationEntityMapper mapper = new ReservationEntityMapper();

    @Test
    @DisplayName("엔티티로 잘 변환한다")
    void toEntity_success() {
        //given
        Reservation testReservation = new Reservation(TEST_ID, TESTER_NAME, TEST_DATE, TEST_TIME);
        ReservationEntity expectResult = new ReservationEntity(TEST_ID, TESTER_NAME, TEST_DATE, TEST_TIME);

        //when
        ReservationEntity result = mapper.toEntity(testReservation);

        //then
        Assertions.assertEquals(expectResult, result);
    }

    @Test
    @DisplayName("id가 없어도 엔티티로 잘 변환한다")
    void toEntity_withNoID_success() {
        //given
        Reservation testReservation = Reservation.constructWithNoId(TESTER_NAME, TEST_DATE, TEST_TIME);
        ReservationEntity expectResult = new ReservationEntity(null, TESTER_NAME, TEST_DATE, TEST_TIME);

        //when
        ReservationEntity result = mapper.toEntity(testReservation);

        //then
        Assertions.assertEquals(expectResult, result);
    }

    @Test
    @DisplayName("도메인 객체로 잘 변환한다")
    void toDomain_success() {
        //given
        ReservationEntity entity = new ReservationEntity(TEST_ID, TESTER_NAME, TEST_DATE, TEST_TIME);
        Reservation expectResult = new Reservation(TEST_ID, TESTER_NAME, TEST_DATE, TEST_TIME);

        //when
        Reservation result = mapper.toDomain(entity);

        //then
        Assertions.assertEquals(expectResult, result);
    }
}
