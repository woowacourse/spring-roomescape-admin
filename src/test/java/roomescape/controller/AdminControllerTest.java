package roomescape.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AdminControllerTest {
    @Test
    @DisplayName("관리자 메인 페이지 경로를 정해진 경로로 매핑한다.")
    void mainPage() {
        AdminController adminController = new AdminController();
        String mainPage = adminController.mainPage();
        Assertions.assertThat(mainPage)
                .isEqualTo("admin/index");
    }
}
