package roomescape;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.model.Reservation;
import roomescape.repository.ReservationRepository;

import java.util.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AdminEndpointTest {

    @Autowired
    private ReservationRepository reservationRepository;

    @DisplayName("관리자 메인 페이지 응답")
    @Test
    void adminPageLoadTest() {
        HttpRestTestTemplate.assertGetOk("/admin");
    }

    @DisplayName("예약 페이지 응답")
    @Test
    void reservationPageLoadTest() {
        List<Reservation> reservations = reservationRepository.findAll();
        int reservationSize = reservations.size();

        HttpRestTestTemplate.assertGetOk("/admin/reservation");
        HttpRestTestTemplate.assertGetOk("/reservations", "size()", reservationSize);
    }

    @DisplayName("예약 추가 삭제 시나리오")
    @TestFactory
    Collection<DynamicTest> reservationAddAndRemoveScenarioTest() {
        List<Reservation> reservations = reservationRepository.findAll();
        int reservationSize = reservations.size();
        int lastIndex = Math.toIntExact(reservations.get(reservationSize - 1)
                .getId());

        return Arrays.asList(
                DynamicTest.dynamicTest("예약 추가", () -> {
                    Map<String, String> params = new HashMap<>();
                    params.put("name", "브라운");
                    params.put("date", "2023-08-05");
                    params.put("time", "15:40");

                    HttpRestTestTemplate.assertPostOk(params, "/reservations", "id", lastIndex + 1);
                    HttpRestTestTemplate.assertGetOk("/reservations", "size()", reservationSize + 1);
                }),
                DynamicTest.dynamicTest("예약 삭제", () -> {
                    HttpRestTestTemplate.assertDeleteOk("/reservations/" + (lastIndex + 1));
                    HttpRestTestTemplate.assertGetOk("/reservations", "size()", reservationSize);
                })
        );
    }
}
