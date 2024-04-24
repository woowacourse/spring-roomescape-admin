package roomescape.time.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import roomescape.time.domain.Time;
import roomescape.time.dto.TimeResponse;
import roomescape.time.dto.TimeSaveRequest;
import roomescape.time.service.TimeService;

@DisplayName("시간 API 컨트롤러")
@WebMvcTest(TimeApiController.class)
class TimeApiControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TimeService timeService;

    @DisplayName("모든 시간 조회 성공 시 200 응답을 받는다.")
    @Test
    public void findAllTest() throws Exception {
        // given
        Time time1 = new Time(1L, LocalTime.parse("10:00"));
        Time time2 = new Time(2L, LocalTime.parse("10:00"));
        List<Time> times = List.of(time1, time2);

        // when
        doReturn(times).when(timeService).findAll();

        // then
        mockMvc.perform(get("/times")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(times.size())));
    }

    @DisplayName("시간 저장 테스트")
    @Nested
    class saveTest {
        @Autowired
        private ObjectMapper objectMapper;

        @DisplayName("시간 정보를 저장 성공 시 201 응답을 받는다.")
        @Test
        public void createSuccessTest() throws Exception {
            // given
            TimeSaveRequest timeSaveRequest = new TimeSaveRequest(LocalTime.parse("10:00"));
            TimeResponse time = new TimeResponse(1L, timeSaveRequest.getStartAt());

            // when
            doReturn(time).when(timeService)
                    .save(any(TimeSaveRequest.class));

            // then
            mockMvc.perform(post("/times")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(timeSaveRequest)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").value(time.getId()))
                    .andExpect(jsonPath("$.startAt").value(time.getStartAt().toString()));
        }

        @DisplayName("시간 저장 실패 시 400 응답을 받는다.")
        @Test
        public void createExceptionTest() throws Exception {
            // given
            TimeSaveRequest timeSaveRequest = new TimeSaveRequest(null);

            // when
            doThrow(new DataAccessException("데이터 접근 예외") {}).when(timeService)
                    .save(any(TimeSaveRequest.class));

            // then
            mockMvc.perform(post("/times")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(timeSaveRequest)))
                    .andExpect(status().isBadRequest());
        }
    }

    @DisplayName("시간 삭제 테스트")
    @Nested
    class deleteTest {

        @DisplayName("시간 삭제 성공시 204 응답을 받는다.")
        @Test
        public void deleteByIdSuccessTest() throws Exception {
            // given && when
            doThrow(IllegalArgumentException.class).when(timeService)
                    .deleteById(2L);

            // then
            mockMvc.perform(delete("/times/{id}", 1L)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNoContent());
        }

        @DisplayName("예약 정보 삭제 실패시 400 응답을 받는다.")
        @Test
        public void deleteByIdExceptionTest() throws Exception {
            // given
            Long id = 1L;

            // when
            doThrow(IllegalArgumentException.class).when(timeService)
                    .deleteById(id);

            // then
            mockMvc.perform(delete("/times/{id}", id)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }
    }
}
