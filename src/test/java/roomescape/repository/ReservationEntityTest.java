package roomescape.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationEntityTest {

    @Test
    @DisplayName("ID를 설정하면 기존과는 다른 인스턴스를 반환한다")
    void initializeId_return_another_instance() {
        Long testId = 1L;
        ReservationTimeEntity timeEntity = new ReservationTimeEntity(
                9999L,
                "18:00"
        );
        ReservationEntity noIdEntity = new ReservationEntity(
                null,
                "라티",
                "2026-08-06",
                timeEntity
        );
        int prevHashCode = noIdEntity.hashCode();

        ReservationEntity withIdEntity = noIdEntity.initializeId(testId);
        int afterHashCode = withIdEntity.hashCode();

        Assertions.assertNotEquals(prevHashCode, afterHashCode);
    }
}
