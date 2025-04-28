package roomescape.reservation.controller;

import roomescape.globalException.CustomException;
import roomescape.reservation.domain.dto.ReservationReqDto;
import roomescape.reservation.domain.dto.ReservationResDto;
import roomescape.reservation.service.ReservationService;
import roomescape.reservationTime.domain.dto.ReservationTimeResDto;
import roomescape.reservationTime.service.ReservationTimeService;
import roomescape.reservation.view.ReservationInputView;
import roomescape.reservation.view.ReservationOutputView;
import roomescape.reservationTime.view.ReservationTimeOutputView;

import java.util.List;

public class ConsoleReservationController {

    private final ReservationInputView reservationInputView;
    private final ReservationOutputView reservationOutputView;
    private final ReservationTimeOutputView reservationTimeOutputView;
    private final ReservationService service;
    private final ReservationTimeService reservationTimeService;

    public ConsoleReservationController(ReservationInputView reservationInputView, ReservationOutputView reservationOutputView, ReservationTimeOutputView reservationTimeOutputView, ReservationService service, ReservationTimeService reservationTimeService) {
        this.reservationInputView = reservationInputView;
        this.reservationOutputView = reservationOutputView;
        this.reservationTimeOutputView = reservationTimeOutputView;
        this.service = service;
        this.reservationTimeService = reservationTimeService;
    }

    void readAllReservation() {
        reservationOutputView.printAllReservationsInfo();
        List<ReservationResDto> reservationResDtos = service.readAll();
        reservationOutputView.printAllReservations(reservationResDtos);
    }

    void addReservation() {
        reservationOutputView.printAddReservationInfo();
        List<ReservationTimeResDto> resDtos = reservationTimeService.findAll();
        reservationTimeOutputView.printAllReservationTimes(resDtos);
        ReservationReqDto reqDto = reservationInputView.readReservationReqDto();
        ReservationResDto resDto = service.add(reqDto);
        reservationOutputView.printSavedReservation(resDto);
    }

    void deleteReservation() {
        reservationOutputView.printDeleteReservationInfo();
        List<ReservationResDto> resDtos = service.readAll();
        reservationOutputView.printAllReservations(resDtos);
        Long id = reservationInputView.readReservationId();
        try {
            service.delete(id);
            reservationOutputView.printDeleteReservationResult();
        } catch (CustomException e) {
            reservationOutputView.printDeleteReservationResult(e.getMessage());
        }

    }
}
