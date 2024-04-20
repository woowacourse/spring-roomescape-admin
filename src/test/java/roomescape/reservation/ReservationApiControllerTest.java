package roomescape.reservation;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@DisplayName("예약 API 컨트롤러")
@WebMvcTest(ReservationApiController.class)
class ReservationApiControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationService reservationService;

    @DisplayName("모든 예약 정보를 조회 성공 시 200 응답을 받는다.")
    @Test
    public void findAllTest() throws Exception {
        // given
        Reservation reservation1 = new Reservation(1L, "브라운", "2024-08-05", "10:00");
        Reservation reservation2 = new Reservation(2L, "솔라", "2024-08-05", "10:00");
        List<Reservation> reservations = List.of(reservation1, reservation2);

        // when
        doReturn(reservations).when(reservationService).findAll();

        // then
        mockMvc.perform(get("/reservations")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(reservations.size())));
    }

    @DisplayName("예약 정보 저장 테스트")
    @Nested
    class saveTest {
        @Autowired
        private ObjectMapper objectMapper;

        @DisplayName("예약 정보를 저장 성공 시 201 응답을 받고 Location 응답 헤더를 받는다.")
        @Test
        public void createSuccessTest() throws Exception {
            // given
            ReservationRequest reservationRequest = new ReservationRequest("브라운", "2024-08-05", "10:00");
            Long id = 1L;

            // when
            doReturn(id).when(reservationService)
                    .save(any(ReservationRequest.class));

            // then
            mockMvc.perform(post("/reservations")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(reservationRequest)))
                    .andExpect(status().isCreated())
                    .andExpect(header().string("Location", "/reservations/1"))
                    .andExpect(jsonPath("$.id").value(id));
        }

        @DisplayName("예약 정보를 저장 실패 시 400 응답을 받는다.")
        @Test
        public void createExceptionTest() throws Exception {
            // given
            ReservationRequest reservationRequest = new ReservationRequest("브라운", null, null);

            // when
            doThrow(new DataAccessException("데이터 접근 예외") {}).when(reservationService)
                    .save(any(ReservationRequest.class));

            // then
            mockMvc.perform(post("/reservations")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(reservationRequest)))
                    .andExpect(status().isBadRequest());
        }
    }

    @DisplayName("예약 정보 삭제 테스트")
    @Nested
    class deleteTest {

        @DisplayName("예약 정보 삭제 성공시 204 응답을 받는다.")
        @Test
        public void deleteByIdSuccessTest() throws Exception {
            // given && when
            doThrow(IllegalArgumentException.class).when(reservationService)
                    .deleteById(2L);

            // then
            mockMvc.perform(delete("/reservations/{id}", 1L)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNoContent());
        }

        @DisplayName("예약 정보 삭제 실패시 400 응답을 받는다.")
        @Test
        public void deleteByIdExceptionTest() throws Exception {
            // given
            Long id = 1L;

            // when
            doThrow(IllegalArgumentException.class).when(reservationService)
                    .deleteById(id);

            // then
            mockMvc.perform(delete("/reservations/{id}", id)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }
    }
}
