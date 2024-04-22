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
import roomescape.controller.dto.TimeSlotCreationRequest;
import roomescape.controller.dto.TimeSlotCreationResponse;
import roomescape.domain.TimeSlot;
import roomescape.repository.TimeSlotRepository;
import roomescape.service.dto.TimeSlotDto;

class TimeSlotTest extends AcceptanceTest {

    @Autowired
    private TimeSlotRepository timeSlotRepository;

    @AfterEach
    void tearDown() {
        timeSlotRepository.deleteAll();
    }

    @Test
    @DisplayName("시각을 추가한다.")
    void createTimeTest() {
        TimeSlotCreationRequest request = new TimeSlotCreationRequest("13:00");
        TimeSlotCreationResponse response = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/times")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(TimeSlotCreationResponse.class);

        assertThat(response.startAt()).isEqualTo("13:00");
    }

    @Test
    @DisplayName("등록된 모든 시각을 조회한다.")
    void getAllTimeTest() {
        List.of(
                new TimeSlotDto(1L, "13:00"),
                new TimeSlotDto(2L, "14:00")
        ).forEach(timeSlotRepository::create);

        RestAssured.given().log().all()
                .when().get("/times")
                .then().log().all()
                .body("size()", is(2));
    }

    @Test
    @DisplayName("id를 사용해 시각을 삭제한다.")
    void deleteByIdTest() {
        TimeSlotDto timeSlotDto = new TimeSlotDto(1L, "13:00");
        Long id = timeSlotRepository.create(timeSlotDto).getId();

        RestAssured.given().log().all()
                .when().delete("/times/" + id)
                .then().log().all()
                .statusCode(200);

        List<TimeSlot> actual = timeSlotRepository.findAll();
        assertThat(actual).isEmpty();
    }
}
