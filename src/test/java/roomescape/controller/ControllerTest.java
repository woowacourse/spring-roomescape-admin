package roomescape.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import roomescape.service.ReservationService;

@WebMvcTest
public abstract class ControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    protected ReservationService reservationService;
}
