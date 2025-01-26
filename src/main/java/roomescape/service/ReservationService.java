package roomescape.service;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.Member;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationSlot;
import roomescape.domain.ReservationStatus;
import roomescape.domain.ReservationTime;
import roomescape.domain.Theme;
import roomescape.exception.BadRequestException;
import roomescape.exception.DuplicatedSlotException;
import roomescape.exception.ErrorCode;
import roomescape.exception.ForbiddenException;
import roomescape.exception.NotFoundException;
import roomescape.repository.MemberRepository;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationSlotRepository;
import roomescape.repository.ReservationTimeRepository;
import roomescape.repository.ThemeRepository;
import roomescape.service.dto.ReservationAdminRequest;
import roomescape.service.dto.ReservationMineResponse;
import roomescape.service.dto.ReservationRequest;
import roomescape.service.dto.ReservationResponse;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationSlotRepository reservationSlotRepository;
    private final ReservationTimeRepository timeRepository;
    private final ThemeRepository themeRepository;
    private final MemberRepository memberRepository;

    // 관리자 예약 생성은 실패하더라도 예약 대기를 추가로 신청하지 않는다.
    @Transactional
    public ReservationResponse createReservationByAdmin(ReservationAdminRequest request) {
        Member member = memberRepository.findById(request.memberId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_MEMBER, request.memberId()));
        return createReservedReservation(new ReservationRequest(request), member);
    }

    //TODO: createWaiting 메서드를 따로 만들고, facade에서 createReservation 중 createSlot에 실패하면 catch해서 createWaiting 시도! (복구로직)
    //TODO: Facade를 만들지 않고, 그냥 여기서 try catch 해도 되는거 아닌가?
    @Transactional
    public ReservationResponse createReservedReservation(ReservationRequest request, Member member) {
        ReservationSlot slot = createSlot(request);

        Reservation reservation = new Reservation(member, slot, ReservationStatus.RESERVED);
        Reservation savedReservation = reservationRepository.save(reservation);
        return new ReservationResponse(savedReservation);
    }

    private ReservationSlot createSlot(ReservationRequest request) {
        try {
            ReservationTime time = timeRepository.findById(request.timeId())
                    .orElseThrow(() -> new BadRequestException(ErrorCode.NOT_FOUND_TIME));
            Theme theme = themeRepository.findById(request.themeId())
                    .orElseThrow(() -> new BadRequestException(ErrorCode.NOT_FOUND_THEME));
            ReservationSlot slot = new ReservationSlot(request.date(), time, theme);
            validateIsBeforeNow(slot);
            validateDuplicatedSlot(slot);
            return reservationSlotRepository.save(slot);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicatedSlotException(ErrorCode.DUPLICATED_RESERVATION, e);
        }
    }

    // TODO: WaitingResponse DTO를 따로 분리하거나, ReservationResponse에 status를 추가할 지 고려
    @Transactional
    public ReservationResponse createWaitingReservation(ReservationRequest request, Member member) {
        ReservationSlot slot = reservationSlotRepository.findByDateAndTimeIdAndThemeId(
                        request.date(), request.timeId(), request.themeId())
                .orElseThrow(() -> new BadRequestException(ErrorCode.WAITING_WITHOUT_RESERVATION));
        validateDuplicatedWaiting(slot, member);

        Reservation reservation = new Reservation(member, slot, ReservationStatus.WAITING);
        Reservation savedReservation = reservationRepository.save(reservation);
        return new ReservationResponse(savedReservation);
    }

    private void validateDuplicatedWaiting(ReservationSlot slot, Member member) {
        if (reservationRepository.existsByMemberIdAndSlotId(member.getId(), slot.getId())) {
            throw new BadRequestException(ErrorCode.DUPLICATED_WAITING);
        }
    }

    private void validateIsBeforeNow(ReservationSlot slot) {
        if (slot.isBefore(LocalDateTime.now())) {
            throw new BadRequestException(ErrorCode.PAST_TIME_RESERVATION_NOT_ALLOWED);
        }
    }

    // try 문에서 save를 호출하기 전에 복잡한 로직이 많은 경우에 의미가 있을 것 같다.
    // exists를 위한 쿼리 날리고 바로 save 쿼리를 날리면 굳이 쿼리 하나가 더 추가되는 느낌이다.
    // 이런 경우엔 그냥 save 쿼리 날려서 실패해버리는게 더 낫지않을까?
    private void validateDuplicatedSlot(ReservationSlot slot) {
        Long themeId = slot.getTheme().getId();
        Long timeId = slot.getTime().getId();
        if (reservationSlotRepository.existsByDateAndThemeIdAndTimeId(slot.getDate(), themeId, timeId)) {
            throw new DuplicatedSlotException(ErrorCode.DUPLICATED_RESERVATION);
        }
    }

    public List<ReservationResponse> findAll() {
        return reservationRepository.findAll().stream()
                .map(ReservationResponse::new)
                .toList();
    }

    public List<ReservationMineResponse> findOfMember(Member member) {
        return reservationRepository.findReservationWithRankByMemberId(member.getId()).stream()
                .map(ReservationMineResponse::new)
                .toList();
    }

    // TODO: "예약"상태의 예약을 취소하면, "대기" 1번을 "예약"으로 승격한다.
    @Transactional
    public void remove(Long id, Member member) {
        reservationRepository.findById(id).ifPresent(r -> {
            if (r.cancelBy(member)) {
                return;
            }
            throw new ForbiddenException(ErrorCode.FORBIDDEN_RESERVATION_CANCEL);
        });
    }
}
