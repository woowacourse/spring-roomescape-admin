package roomescape.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import roomescape.domain.Member;
import roomescape.domain.Role;
import roomescape.exception.BadRequestException;
import roomescape.exception.ErrorCode;
import roomescape.repository.MemberRepository;
import roomescape.service.dto.LoginRequest;
import roomescape.service.dto.LoginResponse;
import roomescape.service.util.TokenProvider;

@Slf4j
@RequiredArgsConstructor
@Service
public class LoginService {

    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;

    public LoginResponse login(LoginRequest loginRequest) {
        Member member = memberRepository.findByEmail(loginRequest.email())
                .orElseThrow(() -> new BadRequestException(ErrorCode.WRONG_EMAIL));
        if (member.isNotCorrectPassword(loginRequest.password())) {
            throw new BadRequestException(ErrorCode.WRONG_PASSWORD);
        }
        String token = tokenProvider.generateToken(member.getId(), member.getRole().toString());
        return new LoginResponse(token);
    }

    public Member findMemberByToken(String token) {
        Long userId = tokenProvider.parseMemberId(token);

        return memberRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException(ErrorCode.INVALID_TOKEN));
    }

    public boolean isAdminToken(String token) {
        String roleName = tokenProvider.parseRole(token);

        Role role = Role.match(roleName);
        return role.isAdmin();
    }
}
