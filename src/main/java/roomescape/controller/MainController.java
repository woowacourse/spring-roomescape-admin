package roomescape.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class MainController {

    @GetMapping
    public String readMainPage() {
        return "admin/index";
    }
}
