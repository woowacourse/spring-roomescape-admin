package roomescape.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AdminControllerTest {

    @Test
    @DisplayName("admin 뷰 경로를 반환한다")
    void should_return_admin_view_path() {
        // given
        AdminController adminController = new AdminController();

        // when
        String result = adminController.admin();

        // then
        String expected = "admin/index";
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("reservation 뷰 경로를 반환한다")
    void should_return_reservation_view_path() {
        // given
        AdminController adminController = new AdminController();

        // when
        String result = adminController.reservation();

        // then
        String expected = "admin/reservation-legacy";
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("time 뷰 경로를 반환한다")
    void should_return_time_view_path() {
        // given
        AdminController adminController = new AdminController();

        // when
        String result = adminController.time();

        // then
        String expected = "admin/time";
        assertThat(result).isEqualTo(expected);
    }
}
