package roomescape.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import roomescape.controller.ReservationTimeControllerTest.TestConfig;
import roomescape.controller.dto.ReservationTimeRequest;
import roomescape.domain.ReservationTime;
import roomescape.service.ReservationTimeService;

@WebMvcTest(ReservationTimeController.class)
@Import(TestConfig.class)
class ReservationTimeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public ReservationTimeService reservationService() {
            return new TestReservationTimeServiceImpl();
        }
    }

    @Test
    @DisplayName("시간 등록을 잘 한다")
    void enroll_time_success() throws Exception {
        ReservationTimeRequest reservationTimeRequest = new ReservationTimeRequest("12:00");

        mockMvc.perform(
                        post("/times")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(reservationTimeRequest))
                ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.startAt").value("12:00"));
    }

    @Test
    @DisplayName("시간 조회를 잘 한다")
    void find_time_success() throws Exception {
        mockMvc.perform(get("/times")).andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].startAt").value("12:00"))
                .andExpect(jsonPath("$[1].startAt").value("12:01"))
                .andExpect(jsonPath("$[2].startAt").value("12:02"));
    }

    @Test
    @DisplayName("삭제를 잘 한다")
    void delete_time_success() throws Exception {
        mockMvc.perform(delete("/times/1")).andExpect(status().isNoContent());
    }


    private static class TestReservationTimeServiceImpl implements ReservationTimeService {

        private static final Integer RANDOM_RANGE = 100_000;

        @Override
        public ReservationTime save(String startAt) {
            return createReservationWithRandomId(startAt);
        }

        @Override
        public List<ReservationTime> findAll() {
            List<String> threeStartAts = List.of("12:00", "12:01", "12:02");
            return threeStartAts.stream()
                    .map(this::createReservationWithRandomId)
                    .toList();
        }

        @Override
        public void deleteById(Long targetId) {
        }

        private ReservationTime createReservationWithRandomId(String startAt) {
            return new ReservationTime(
                    (long) (Math.random() * RANDOM_RANGE),
                    startAt
            );
        }
    }
}
