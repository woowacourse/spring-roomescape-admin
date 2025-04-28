package roomescape.reservationTime.controller;

import roomescape.reservationTime.domain.dto.ReservationTimeReqDto;
import roomescape.reservationTime.domain.dto.ReservationTimeResDto;
import roomescape.reservationTime.service.ReservationTimeService;
import roomescape.reservationTime.view.ReservationTimeInputView;
import roomescape.reservationTime.view.ReservationTimeOutputView;

import java.util.List;

public class ConsoleReservationTimeController {

    private final ReservationTimeInputView inputView;
    private final ReservationTimeOutputView outputView;
    private final ReservationTimeService service;

    public ConsoleReservationTimeController(ReservationTimeInputView inputView, ReservationTimeOutputView outputView, ReservationTimeService service) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.service = service;
    }

    public void readAllReservationTimes() {
        /*
         * 예약 시간 리스트 반환 기능을 고르셨습니다.
         * 아래는 예약 시간 리스트 입니다.
         * (저장되어 있는 예약시간 리스트 반환) : outputView.printAllReservationTimes(resDtos);
         */
        outputView.printAllReservationTimesInfo();
        List<ReservationTimeResDto> resDtos = service.readAll();
        outputView.printAllReservationTimes(resDtos);
    }

    public void addReservationTime() {
        /*
         * 예약 시간 추가 기능을 고르셨습니다.
         * 입력 형식은 다음과 같습니다.
         * HH:mm
         */
        outputView.printAddReservationTimeInfo();
        ReservationTimeReqDto reqDto = inputView.readReservation();
        ReservationTimeResDto resDto = service.add(reqDto);
        outputView.printSavedReservationTime(resDto);
    }

    public void deleteReservationTime() {
        /*
         * 예약 시간 삭제 기능을 고르셨습니다. : outputView.printDeleteReservationTimeInfo();
         * 아래는 예약 시간 리스트 입니다. : outputView.printAllReservationTimes(resDtos);
         * (print: 저장되어 있는 예약시간 리스트 반환)
         * 이 중 하나를 골라주세요.
         * 입력 형식은 다음과 같습니다.
         * 1
         * (read: id) : Long id = inputView.readReservationTimeId();
         * 예약 시간 삭제가 정상적으로 진행되었습니다. : outputView.printDeleteReservationTimeResult();
         */
        outputView.printDeleteReservationTimeInfo();
        List<ReservationTimeResDto> resDtos = service.readAll();
        outputView.printAllReservationTimes(resDtos);
        Long id = inputView.readReservationTimeId();
        service.delete(id); // TODO 2025. 4. 28. 16:06: 예외에 따라 반환 로직이 달라짐 예외 캐치해야 함
        outputView.printDeleteReservationTimeResult();
    }
}
