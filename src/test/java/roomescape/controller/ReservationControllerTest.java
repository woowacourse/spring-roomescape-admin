package roomescape.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import roomescape.domain.Reservation;
import roomescape.repository.InMemoryReservationRepositoryImpl;
import roomescape.repository.ReservationRepository;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(InMemoryReservationRepositoryImpl.class)
@WebMvcTest(ReservationController.class)
class ReservationControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("[200] GET /reservations")
    @Test
    void findAll() throws Exception {
        this.mvc.perform(get("/reservations"))
                .andExpect(status().isOk());
    }

    @DisplayName("[201] POST /reservations")
    @Test
    void add() throws Exception {
        String reservation = objectMapper.writeValueAsString(new Reservation(
                "비밥",
                LocalDate.now().plusDays(1),
                LocalTime.of(9, 0)));

        this.mvc.perform(post("/reservations")
                        .content(reservation)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @DisplayName("[400] POST /reservations")
    @Test
    void add_dateOutOfRange() throws Exception {
        String reservation = objectMapper.writeValueAsString(new Reservation(
                "비밥",
                LocalDate.now().minusDays(1),
                LocalTime.of(9, 0)));

        this.mvc.perform(post("/reservations")
                        .content(reservation)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("[400] POST /reservations")
    @Test
    void add_timeBeforeMin() throws Exception {
        String reservation = objectMapper.writeValueAsString(new Reservation(
                "비밥",
                LocalDate.now().plusDays(1),
                LocalTime.of(8, 59)));

        this.mvc.perform(post("/reservations")
                        .content(reservation)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("[400] POST /reservations")
    @Test
    void add_timeAfterMax() throws Exception {
        String reservation = objectMapper.writeValueAsString(new Reservation(
                "비밥",
                LocalDate.now().plusDays(1),
                LocalTime.of(20, 1)));

        this.mvc.perform(post("/reservations")
                        .content(reservation)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("[204] DELETE /reservations/{id}")
    @Test
    void delete_() throws Exception {
        Reservation savedReservation = reservationRepository.save(new Reservation(
                "비밥",
                LocalDate.now().plusDays(1),
                LocalTime.of(9, 0)));

        this.mvc.perform(delete("/reservations/" + savedReservation.getId()))
                .andExpect(status().isNoContent());
    }

    @DisplayName("[204] DELETE /reservations/{id}")
    @Test
    void delete_notExist() throws Exception {
        Reservation savedReservation = reservationRepository.save(new Reservation(
                "비밥",
                LocalDate.now().plusDays(1),
                LocalTime.of(9, 0)));

        this.mvc.perform(delete("/reservations/" + (1 + savedReservation.getId())))
                .andExpect(status().isNoContent());
    }
}
