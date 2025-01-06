package roomescape.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.repository.MemberRepository;
import roomescape.service.dto.MemberResponse;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public List<MemberResponse> findAll() {
        return memberRepository.findAll()
                .stream()
                .map(MemberResponse::new)
                .toList();
    }
}
