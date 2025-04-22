package roomescape.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import roomescape.repository.ReservationFakeRepository;

@WebMvcTest(ReservationController.class)
@Import(ReservationFakeRepository.class)
public class ReservationControllerBindTest {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("잘못된 형식의 요청으로 파라미터 바인딩 실패 시 400 Bad Request")
    @ParameterizedTest
    @ValueSource(
        strings = {
            """
                {"name" : "brown", "date" : "2-0-2-3", "timeId" : "1"}
            """,
            """
                {"name" : "brown", "date" : "2023-01-01", "timeId" : "abc"}
            """
        }
    )
    void badRequestWhenRequestInvalid(String jsonBody) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/reservations")
                .contentType("application/json")
                .content(jsonBody))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
