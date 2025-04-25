package roomescape.reservation.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import roomescape.reservation.dto.ReservationRequest;
import roomescape.reservation.dto.ReservationResponse;
import roomescape.reservation.dto.ReservationTimeResponse;
import roomescape.reservation.service.ReservationService;

public class ReservationControllerSliceTest {

    private final MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
    private final ObjectMapper mapper = jackson2HttpMessageConverter.getObjectMapper();

    private ReservationService reservationService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // <-- 핵심!

        reservationService = mock(ReservationService.class);
        ReservationController reservationController = new ReservationController(reservationService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(reservationController)
                .setMessageConverters(jackson2HttpMessageConverter)
                .build();
    }

    @DisplayName("모든 예약 정보를 가져온다.")
    @Test
    void test1() throws Exception {
        // given
        List<String> names = List.of("꾹", "드라고", "히로");
        LocalDate now = LocalDate.now();
        LocalTime time = LocalTime.now();
        Long timeId = 1L;

        AtomicLong id = new AtomicLong(1L);
        ReservationTimeResponse timeResponse = new ReservationTimeResponse(timeId, time);

        List<ReservationResponse> responses = names.stream()
                .map(name -> new ReservationResponse(id.getAndIncrement(), name, now, timeResponse))
                .toList();

        when(reservationService.getAll()).thenReturn(responses);

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/reservations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(names.size()));
    }

    @DisplayName("예약 정보를 추가한다.")
    @Test
    void test2() throws Exception {
        Long reservationId = 1L;
        Long timeId = 1L;
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        String name = "꾹이";

        ReservationRequest request = new ReservationRequest(name, date, timeId);
        ReservationTimeResponse timeResponse = new ReservationTimeResponse(timeId, time);
        ReservationResponse response = new ReservationResponse(reservationId, name, date, timeResponse);

        String requestContent = mapper.writeValueAsString(request);

        when(reservationService.save(request)).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.post("/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(reservationId))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.date").value(date.toString()))
                .andExpect(jsonPath("$.time.id").value(timeId))
                .andExpect(jsonPath("$.time.startAt").value(time.toString()));

    }


//    private String toStartAt(LocalTime time){
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
//
//
//
//    }
}
