package roomescape.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import roomescape.business.ReservationService;
import roomescape.business.ReservationTimeService;

@WebMvcTest
public abstract class ControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    protected ReservationService reservationService;


    @MockBean
    protected ReservationTimeService reservationTimeService;

    @Autowired
    protected ObjectMapper objectMapper;
}
