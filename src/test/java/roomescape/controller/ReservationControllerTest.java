package roomescape.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import roomescape.dto.reservation.ReservationCreateRequest;
import roomescape.dto.reservation.ReservationResponse;
import roomescape.dto.reservationtime.ReservationTimeResponse;
import roomescape.service.ReservationService;

@WebMvcTest(ReservationController.class)
class ReservationControllerTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ReservationService reservationService;
    
    @Test
    @DisplayName("전체 예약을 조회한다.")
    void getAllReservationsTest() throws Exception {
        //given
        List<ReservationResponse> expectedResponses = List.of(
                getReservationResponse(1L, "daon", "2022-02-23", 1L, "12:12"),
                getReservationResponse(2L, "ikjo", "2022-02-05", 2L, "23:22")
        );
        given(reservationService.findAll()).willReturn(expectedResponses);

        //when //then
        mockMvc.perform(get("/reservations"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("daon")))
                .andExpect(jsonPath("$[1].date", is("2022-02-05")))
                .andExpect(jsonPath("$[1].time.startAt", is("23:22")));
    }

    @Test
    @DisplayName("예약을 성공적으로 추가한다.")
    void addReservationTest() throws Exception {
        //given
        String expectedName = "daon";
        String expectedDate = "2024-11-29";
        String expectedStartAt = "00:01";
        ReservationCreateRequest givenRequest = ReservationCreateRequest.of(expectedName, expectedDate, 1L);
        ReservationResponse response
                = getReservationResponse(1L, expectedName, expectedDate, 1L, expectedStartAt);
        given(reservationService.add(givenRequest)).willReturn(response);
        String givenJsonRequest = objectMapper.writeValueAsString(givenRequest);

        //when //then
        mockMvc.perform(post("/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(givenJsonRequest))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(expectedName)))
                .andExpect(jsonPath("$.date", is(expectedDate)))
                .andExpect(jsonPath("$.time.startAt", is(expectedStartAt)));
    }

    @Test
    @DisplayName("예약을 성공적으로 삭제한다.")
    void deleteReservationTest() throws Exception {
        //when //then
        mockMvc.perform(delete("/reservations/{id}", 1))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    private ReservationResponse getReservationResponse(long id,
                                                       String name,
                                                       String date,
                                                       long timeId,
                                                       String startAt) {
        return ReservationResponse.of(
                id,
                name,
                date,
                ReservationTimeResponse.of(timeId, startAt)
        );
    }
}
