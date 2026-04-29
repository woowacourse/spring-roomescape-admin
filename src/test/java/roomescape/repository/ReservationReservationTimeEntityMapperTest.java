package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.domain.ReservationTime;

class ReservationReservationTimeEntityMapperTest {

    ReservationTimeEntityMapper mapper = new ReservationTimeEntityMapper();

    private static final String TEST_TIME = "14:49";

    @Test
    @DisplayName("ReservationTime를 ReservationTimeEntity로 잘 변환한다")
    void toReservationTimeEntity_success() {
        ReservationTime testReservationTime = new ReservationTime(null, TEST_TIME);

        ReservationTimeEntity result = mapper.toReservationTimeEntity(testReservationTime);

        Assertions.assertEquals(TEST_TIME, result.startAt());
    }

    @Test
    @DisplayName("id가 없어도 ReservationTime를 ReservationTimeEntity로 잘 변환한다")
    void toReservationTimeEntity_success_without_id() {
        ReservationTime testReservationTime = ReservationTime.constructWithoutId(TEST_TIME);

        ReservationTimeEntity result = mapper.toReservationTimeEntity(testReservationTime);

        Assertions.assertEquals(TEST_TIME, result.startAt());
        Assertions.assertNull(result.id());
    }

    @Test
    @DisplayName("ReservationTimeEntity를 ReservationTime 객체로 잘 변환한다")
    void toReservation_success() {
        ReservationTimeEntity entity = new ReservationTimeEntity(999L, TEST_TIME);

        ReservationTime result = mapper.toReservationTime(entity);

        Assertions.assertNotNull(result.id());
        Assertions.assertEquals(TEST_TIME, result.startAt());
    }

    @Test
    @DisplayName("ReservationTimeEntity에 ID 가 없으면 ReservationTime으로 변환 시 오류를 반환한다")
    void toReservation_fail_no_id() {
        ReservationTimeEntity entity = new ReservationTimeEntity(null, TEST_TIME);

        assertThatThrownBy(
                () -> mapper.toReservationTime(entity)
        ).isInstanceOf(IllegalStateException.class)
                .hasMessage("ReservationTimeEntity의 id 가 존재하지 않습니다");
    }
}
