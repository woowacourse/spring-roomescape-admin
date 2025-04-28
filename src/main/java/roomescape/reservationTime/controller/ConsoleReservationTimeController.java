package roomescape.reservationTime.controller;

import roomescape.globalException.CustomException;
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
        outputView.printAllReservationTimesInfo();
        List<ReservationTimeResDto> resDtos = service.findAll();
        outputView.printAllReservationTimes(resDtos);
    }

    public void addReservationTime() {
        outputView.printAddReservationTimeInfo();
        ReservationTimeReqDto reqDto = inputView.readReservation();
        ReservationTimeResDto resDto = service.add(reqDto);
        outputView.printSavedReservationTime(resDto);
    }

    public void deleteReservationTime() {
        outputView.printDeleteReservationTimeInfo();
        List<ReservationTimeResDto> resDtos = service.findAll();
        outputView.printAllReservationTimes(resDtos);
        Long id = inputView.readReservationTimeId();
        try {
            service.delete(id);
            outputView.printDeleteReservationTimeResult();
        } catch (CustomException e) {
            outputView.printDeleteReservationTimeResult();
        }
    }
}
