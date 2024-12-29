package roomescape.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import roomescape.domain.Member;
import roomescape.exception.BadRequestException;
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
                .orElseThrow(() -> new BadRequestException("이메일을 다시 확인해주세요."));
        if (member.isNotCorrectPassword(loginRequest.password())) {
            throw new BadRequestException("비밀번호가 일치하지 않습니다.");
        }
        String token = tokenProvider.generateToken(member.getId());
        return new LoginResponse(token);
    }

    public Member findMemberByToken(String token) {
        Long userId = tokenProvider.parseUserId(token);

        return memberRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException("로그인을 위한 토큰이 유효하지 않습니다."));
    }
}
