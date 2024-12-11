package roomescape.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import roomescape.service.TimeService;
import roomescape.service.dto.TimeResponse;

@WebMvcTest(TimeController.class)
class TimeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TimeService timeService;

    @Test
    @DisplayName("예약을 추가하면 예약 정보를 응답받는다.")
    void createTime() throws Exception {
        // given
        String timeRequest = """
                {
                    "startAt": "10:00"
                }
                """;
        TimeResponse expected = new TimeResponse(1L, LocalTime.of(10, 00));
        when(timeService.create(any())).thenReturn(expected);

        // when & then
        String expectedResponse = """
                {
                    "id": 1,
                    "startAt": "10:00"
                }
                """;
        mockMvc.perform(post("/times")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(timeRequest))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse));
    }

    @Test
    @DisplayName("모든 예약 조회시 형식에 맞게 응답한다.")
    void findAllTimes() throws Exception {
        // given
        List<TimeResponse> expected = List.of(
                new TimeResponse(1L, LocalTime.of(10, 0)),
                new TimeResponse(2L, LocalTime.of(11, 0))
        );
        when(timeService.findAll()).thenReturn(expected);

        // when & then
        String expectedResponse = """
                [
                    {
                        "id": 1,
                        "startAt": "10:00"
                    },
                    {
                        "id": 2,
                        "startAt": "11:00"
                    }
                ]
                """;
        mockMvc.perform(get("/times"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse));
    }

    @Test
    @DisplayName("예약을 취소한다.")
    void cancelTime() throws Exception {
        mockMvc.perform(delete("/times/1"))
                .andExpect(status().isOk());
    }
}
