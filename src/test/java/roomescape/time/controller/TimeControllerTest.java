package roomescape.time.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import roomescape.time.controller.TimeController;

@DisplayName("시간 view 컨트롤러")
@WebMvcTest(TimeController.class)
class TimeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void timeViewTest() throws Exception {
        mockMvc.perform(get("/time"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/time"));
    }
}
