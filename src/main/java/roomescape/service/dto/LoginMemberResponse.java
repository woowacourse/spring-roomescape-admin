package roomescape.service.dto;

import roomescape.domain.Member;

public record LoginMemberResponse(String name) {

    public LoginMemberResponse(Member member) {
        this(member.getName());
    }
}
