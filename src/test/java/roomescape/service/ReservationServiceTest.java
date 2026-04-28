package roomescape.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.domain.Reservation;
import roomescape.repository.ReservationEntityMapper;
import roomescape.repository.ReservationRepository;

class ReservationServiceTest {

    ReservationEntityMapper mapper = new ReservationEntityMapper();
    ReservationRepository repository = new ReservationRepository(mapper);
    ReservationService reservationService = new ReservationService(repository);

    private static final String TESTER_NAME = "브라운";
    private static final String TEST_DATE = "2023-08-06";
    private static final String TEST_TIME = "15:40";

    @Test
    @DisplayName("저장을 잘 한다")
    void add_success() {
        //when
        Assertions.assertDoesNotThrow(
                () -> reservationService.add(TESTER_NAME, TEST_DATE, TEST_TIME)
        );
    }

    @Test
    @DisplayName("저장을 하고, ID와 저정된 값을 반환한다")
    void add_and_return_value() {
        //when
        Reservation result = reservationService.add(TESTER_NAME, TEST_DATE, TEST_TIME);

        //then
        Assertions.assertNotNull(
                result.id()
        );

    }
}
