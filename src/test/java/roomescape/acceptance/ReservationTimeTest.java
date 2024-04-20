package roomescape.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import roomescape.controller.dto.ReservationTimeRequest;
import roomescape.controller.dto.ReservationTimeResponse;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationTimeRepository;
import roomescape.service.dto.ReservationTimeDto;

public class ReservationTimeTest extends AcceptanceTest {

    @Autowired
    private ReservationTimeRepository reservationTimeRepository;

    @AfterEach
    void tearDown() {
        reservationTimeRepository.deleteAll();
    }

    @Test
    @DisplayName("시각을 추가한다.")
    void createTimeTest() {
        ReservationTimeRequest request = new ReservationTimeRequest("13:00");
        ReservationTimeResponse response = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/times")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(ReservationTimeResponse.class);

        assertThat(response.startAt()).isEqualTo("13:00");
    }

    @Test
    @DisplayName("등록된 모든 시각을 조회한다.")
    void getAllTimeTest() {
        List.of(
                new ReservationTimeDto(1L, "13:00"),
                new ReservationTimeDto(2L, "14:00")
        ).forEach(reservationTimeRepository::create);

        RestAssured.given().log().all()
                .when().get("/times")
                .then().log().all()
                .body("size()", is(2));
    }

    @Test
    @DisplayName("id를 사용해 시각을 삭제한다.")
    void deleteByIdTest() {
        ReservationTimeDto reservationTimeDto = new ReservationTimeDto(1L, "13:00");
        Long id = reservationTimeRepository.create(reservationTimeDto).getId();

        RestAssured.given().log().all()
                .when().delete("/times/" + id)
                .then().log().all()
                .statusCode(200);

        List<ReservationTime> actual = reservationTimeRepository.findAll();
        assertThat(actual).isEmpty();
    }
}
