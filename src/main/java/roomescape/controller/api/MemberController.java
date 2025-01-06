package roomescape.controller.api;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import roomescape.service.MemberService;
import roomescape.service.dto.MemberResponse;

@RequiredArgsConstructor
@RequestMapping("/members")
@RestController
public class MemberController {

    private final MemberService memberService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<MemberResponse> findAll() {
        return memberService.findAll();
    }
}
