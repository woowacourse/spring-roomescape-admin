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
import roomescape.controller.ReservationControllerTest.TestConfig;
import roomescape.controller.dto.ReservationRequest;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.service.ReservationService;

@WebMvcTest(ReservationController.class)
@Import(TestConfig.class)
class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public ReservationService reservationService() {
            return new TestReservationServiceImpl();
        }
    }

    @Test
    @DisplayName("예약 등록을 잘 한다")
    void enroll_reservation_success() throws Exception {
        ReservationRequest reservationRequest = new ReservationRequest("브라운", "2026-05-01", 1L);

        mockMvc.perform(
                        post("/reservations")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(reservationRequest))
                ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("브라운"))
                .andExpect(jsonPath("$.date").value("2026-05-01"))
                .andExpect(jsonPath("$.time.id").value(1))
                .andExpect(jsonPath("$.time.startAt").value("12:00"));
    }

    @Test
    @DisplayName("예약 조회를 잘 한다")
    void find_reservation_success() throws Exception {
        mockMvc.perform(get("/reservations")).andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].name").value("브라운"))
                .andExpect(jsonPath("$[1].name").value("리사"))
                .andExpect(jsonPath("$[2].name").value("네오"));
    }

    @Test
    @DisplayName("삭제를 잘 한다")
    void delete_reservation_success() throws Exception {
        mockMvc.perform(delete("/reservations/1")).andExpect(status().isNoContent());
    }


    private static class TestReservationServiceImpl implements ReservationService {

        private static final Integer RANDOM_RANGE = 100_000;

        @Override
        public Reservation save(String name, String date, Long timeId) {
            return new Reservation(
                    (long) (Math.random() * RANDOM_RANGE),
                    name,
                    date,
                    new ReservationTime(timeId, "12:00")
            );
        }

        @Override
        public List<Reservation> findAll() {
            List<String> threeNames = List.of("브라운", "리사", "네오");
            return threeNames.stream()
                    .map(this::createReservationWithRandomId)
                    .toList();
        }

        @Override
        public void deleteById(Long targetId) {
        }

        private Reservation createReservationWithRandomId(String name) {
            return new Reservation(
                    (long) (Math.random() * RANDOM_RANGE),
                    name,
                    "2026-05-01",
                    new ReservationTime(1L, "12:00")
            );
        }
    }
}
