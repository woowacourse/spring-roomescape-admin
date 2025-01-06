package roomescape.service.dto;

import roomescape.domain.Member;

public record MemberResponse(long id, String name) {

    public MemberResponse(Member member) {
        this(member.getId(), member.getName());
    }
}
