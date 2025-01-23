package roomescape.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.domain.Member;
import roomescape.domain.ReservationStatus;
import roomescape.exception.DuplicatedSlotException;
import roomescape.service.dto.ReservationAdminRequest;
import roomescape.service.dto.ReservationMineResponse;
import roomescape.service.dto.ReservationRequest;
import roomescape.service.dto.ReservationResponse;

@RequiredArgsConstructor
@Service
public class ReservationFacadeService {

    private final ReservationService reservationService;

    public ReservationResponse createReservationByAdmin(ReservationAdminRequest request) {
        return reservationService.createReservationByAdmin(request);
    }

    public ReservationResponse createReservation(ReservationRequest request, Member member) {
        if (ReservationStatus.findByViewName(request.status()) == ReservationStatus.RESERVED) {
            return createReservedReservation(request, member);
        }
        return reservationService.createWaitingReservation(request, member);
    }

    private ReservationResponse createReservedReservation(ReservationRequest request, Member member) {
        try {
            return reservationService.createReservedReservation(request, member);
        } catch (DuplicatedSlotException e) {
            return reservationService.createWaitingReservation(request, member);
        }
    }

    public List<ReservationResponse> findAll() {
        return reservationService.findAll();
    }

    public List<ReservationMineResponse> findOfMember(Member member) {
        return reservationService.findOfMember(member);
    }

    public void remove(Long id, Member member) {
        reservationService.remove(id, member);
    }
}
