package roomescape.service;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.Member;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.domain.Theme;
import roomescape.exception.BadRequestException;
import roomescape.repository.MemberRepository;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;
import roomescape.repository.ThemeRepository;
import roomescape.service.dto.ReservationAdminRequest;
import roomescape.service.dto.ReservationRequest;
import roomescape.service.dto.ReservationResponse;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository timeRepository;
    private final ThemeRepository themeRepository;
    private final MemberRepository memberRepository;

    //TODO: 중복 예약 검증 방법이 이게 최선인지 고민해보기
    @Transactional
    public ReservationResponse create(ReservationRequest reservationRequest, Member member) {
        ReservationTime time = timeRepository.findById(reservationRequest.timeId()).orElseThrow();
        Theme theme = themeRepository.findById(reservationRequest.themeId()).orElseThrow();
        Reservation reservation = new Reservation(reservationRequest.date(), member, time, theme);
        validateIsBeforeNow(reservation);
        validateDuplicated(reservation);
        try {
            Reservation savedReservation = reservationRepository.save(reservation);
            return new ReservationResponse(savedReservation);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException("같은 시간에 이미 예약이 존재합니다.", e);
        }
    }

    @Transactional
    public ReservationResponse create(ReservationAdminRequest adminRequest, Member admin) {
        validateAdmin(admin);
        ReservationTime time = timeRepository.findById(adminRequest.timeId())
                .orElseThrow(() -> new BadRequestException("예약 시간이 존재하지 않습니다. id = " + adminRequest.timeId()));
        Theme theme = themeRepository.findById(adminRequest.themeId())
                .orElseThrow(() -> new BadRequestException("테마가 존재하지 않습니다. id = " + adminRequest.themeId()));
        Member member = memberRepository.findById(adminRequest.memberId())
                .orElseThrow(() -> new BadRequestException("회원이 존재하지 않습니다. id = " + adminRequest.memberId()));
        Reservation reservation = new Reservation(adminRequest.date(), member, time, theme);
        validateIsBeforeNow(reservation);
        validateDuplicated(reservation);
        try {
            Reservation savedReservation = reservationRepository.save(reservation);
            return new ReservationResponse(savedReservation);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException("같은 시간에 이미 예약이 존재합니다.", e);
        }
    }

    private void validateAdmin(Member admin) {
        if (!admin.isAdmin()) {
            throw new BadRequestException("관리자만 접근 가능한 요청입니다.");
        }
    }

    private void validateIsBeforeNow(Reservation reservation) {
        if (reservation.isBefore(LocalDateTime.now())) {
            throw new BadRequestException("현재 시각보다 이전의 예약은 불가능합니다.");
        }
    }

    // try 문에서 save를 호출하기 전에 복잡한 로직이 많은 경우에 의미가 있을 것 같다.
    // exists를 위한 쿼리 날리고 바로 save 쿼리를 날리면 굳이 쿼리 하나가 더 추가되는 느낌이다.
    // 이런 경우엔 그냥 save 쿼리 날려서 실패해버리는게 더 낫지않을까?
    private void validateDuplicated(Reservation reservation) {
        Long themeId = reservation.getTheme().getId();
        Long timeId = reservation.getTime().getId();
        if (reservationRepository.existsByDateAndThemeIdAndTimeId(reservation.getDate(), themeId, timeId)) {
            throw new BadRequestException("같은 시간에 이미 예약이 존재합니다.");
        }
    }

    public List<ReservationResponse> findAll() {
        return reservationRepository.findAll().stream()
                .map(ReservationResponse::new)
                .toList();
    }

    @Transactional
    public void remove(Long id, Member member) {
        reservationRepository.findById(id).ifPresent(r -> {
            if (r.hasCancelPermission(member)) {
                reservationRepository.deleteById(id);
                return;
            }
            throw new BadRequestException("예약을 취소할 권한이 없습니다.");
        });
    }
}
