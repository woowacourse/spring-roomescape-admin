package roomescape.view;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;
import roomescape.controller.request.ReservationRequest;
import roomescape.controller.request.ReservationTimeRequest;

public class InputView {

    private static final Scanner in = new Scanner(System.in);
    private static final String ASK_INPUT_NUMBER_MESSAGE = "\n이용하실 기능의 번호를 입력해주세요 : ";
    private static final String ASK_INPUT_NAME = "예약자 이름을 입력해주세요 : ";
    private static final String ASK_INPUT_DATE = "날짜를 입력해주세요. (ex. 2024-04-14) : ";
    private static final String ASK_INPUT_TIME_ID = "원하는 시간에 맞는 순서를 입력해주세요 : ";
    private static final String ASK_INPUT_RESRVATION_TIME = "추가할 예약시간을 입력해주세요 (ex. 11:00:00) : ";
    private static final String ASK_DELETE_RESERVATION_ID = "삭제하고 싶은 방탈출 예약번호를 입력해주세요 : ";
    private static final String ASK_DELETE_RESERVATION_TIME_ID = "삭제하고 싶은 예약시간의 순서를 입력해주세요 : ";

    public int askInputNumber() {
        System.out.print(ASK_INPUT_NUMBER_MESSAGE);
        return in.nextInt();
    }


    public ReservationRequest askInputReservation() {
        System.out.print(ASK_INPUT_TIME_ID);
        long timeId = in.nextLong();
        System.out.print(ASK_INPUT_NAME);
        String name = in.next();
        System.out.print(ASK_INPUT_DATE);
        LocalDate date = LocalDate.parse(in.next());
        return new ReservationRequest(date, name, timeId);
    }

    public ReservationTimeRequest askInputReservationTime() {
        System.out.print(ASK_INPUT_RESRVATION_TIME);
        LocalTime time = LocalTime.parse(in.next());
        return new ReservationTimeRequest(time);
    }

    public long askInputReservationId() {
        System.out.print(ASK_DELETE_RESERVATION_ID);
        return in.nextLong();
    }

    public long askInputReservationTimeId() {
        System.out.print(ASK_DELETE_RESERVATION_TIME_ID);
        return in.nextLong();
    }
}
