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
        /*
         * 예약 리스트 반환 기능을 고르셨습니다.
         * 아래는 예약 리스트 입니다.
         * (저장되어 있는 예약 리스트 반환) : outputView.printAllReservations(reservationResDtos);
         */
        reservationOutputView.printAllReservationsInfo();
        List<ReservationResDto> reservationResDtos = service.readAll();
        reservationOutputView.printAllReservations(reservationResDtos);
    }

    void addReservation() {
        /*
        * 예약 추가 기능을 고르셨습니다.
        * 아래는 예약 시간 리스트 입니다.
        * (저장되어 있는 예약시간 리스트 반환)
        * 이 중 하나를 골라 예약해주세요.
        * 입력 형식은 다음과 같습니다.
        * 이름, 날짜(YYYY-mm-dd), 시간 id
        */
        reservationOutputView.printAddReservationInfo();
        List<ReservationTimeResDto> resDtos = reservationTimeService.findAll();
        reservationTimeOutputView.printAllReservationTimes(resDtos);
        ReservationReqDto reqDto = reservationInputView.readReservationReqDto();
        ReservationResDto resDto = service.add(reqDto);
        reservationOutputView.printSavedReservation(resDto);
    }

    void deleteReservation() {
        /*
         * 예약 삭제 기능을 고르셨습니다. : outputView.printDeleteReservationInfo();
         * 아래는 예약 리스트 입니다. : outputView.printAllReservations(resDtos);
         * (print: 저장되어 있는 예약 리스트 반환)
         * 이 중 하나를 골라주세요.
         * 입력 형식은 다음과 같습니다.
         * 1
         * (read: id) : Long id = inputView.readReservationId();
         * 예약 삭제가 정상적으로 진행되었습니다. : outputView.printDeleteReservationResult();
         */
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
