package roomescape.view;

import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservation.ReservationTime;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.StringJoiner;

public class OutputView {
    private static final String RESERVATION_MESSAGE_FORMAT = "%3d %5s %9s %9s";
    private static final String RESERVATION_TIME_MESSAGE_FORMAT = "%3d %9s";
    private static final String LINE_SEPERATOR = System.lineSeparator();

    public void printNoTimeMessage() {
        System.out.println("등록한 시간이 없습니다.");
    }

    public void printNoReservationMessage() {
        System.out.println("예약된 방탈출이 없습니다.");
    }

    public void printAllOptions() {
        System.out.println(resolveAllOptionsMessage());
    }

    public void printTimeOptions() {
        System.out.println(resolveTimeOptionsMessage());
    }

    public void printReservationOptions() {
        System.out.println(resolveReservationOptionsMessage());
    }

    public void printAllReservation(List<Reservation> reservations) {
        System.out.println(resolveAllReservationMessage(reservations));
    }

    public void printAllReservationTimes(List<ReservationTime> reservationTimes) {
        System.out.println(resolveAllTimeMessage(reservationTimes));
    }

    private String resolveAllReservationMessage(List<Reservation> reservations) {
        StringJoiner joiner = new StringJoiner(LINE_SEPERATOR);
        for (Reservation reservation : reservations) {
            joiner.add(resolveReservationMessage(reservation));
        }
        return joiner.toString();
    }

    private String resolveReservationMessage(Reservation reservation) {
        Long id = reservation.getId();
        String name = reservation.getName().getName();
        LocalDate date = reservation.getDate().getDate();
        LocalTime time = reservation.getTime().getTime();
        return String.format(RESERVATION_MESSAGE_FORMAT, id, name, date, time);
    }

    private String resolveAllTimeMessage(List<ReservationTime> timeResponses) {
        StringJoiner joiner = new StringJoiner(LINE_SEPERATOR);
        for (ReservationTime time : timeResponses) {
            joiner.add(resolveTimeMessage(time));
        }
        return joiner.toString();
    }

    private String resolveTimeMessage(ReservationTime reservationTime) {
        return String.format(RESERVATION_TIME_MESSAGE_FORMAT, reservationTime.getId(), reservationTime.getTime());
    }

    private String resolveAllOptionsMessage() {
        StringJoiner sj = new StringJoiner(LINE_SEPERATOR);
        sj.add("[INFO] 방탈출 예약 및 시간 관리 어드민입니다.");
        sj.add("원하는 옵션을 선택해주세요. ex) 1");
        sj.add("--");
        sj.add("1. 시간 관리");
        sj.add("2. 예약 관리");
        sj.add("3. 종료");
        return sj.toString();
    }

    private String resolveReservationOptionsMessage() {
        StringJoiner sj = new StringJoiner(LINE_SEPERATOR);
        sj.add("[INFO] 방탈출 예약 관리 어드민입니다.");
        sj.add("원하는 옵션을 선택해주세요. ex) 1");
        sj.add("--");
        sj.add("1. 예약 추가 : 지정한 예약을 추가합니다");
        sj.add("2. 예약 삭제 : 지정한 예약을 삭제합니다");
        sj.add("3. 예약 조회 : 저장되어 있는 예약을 모두 조회합니다");
        sj.add("4. 종료");
        return sj.toString();
    }

    private String resolveTimeOptionsMessage() {
        StringJoiner sj = new StringJoiner(LINE_SEPERATOR);
        sj.add("[INFO] 방탈출 시간 관리 어드민입니다.");
        sj.add("원하는 옵션을 선택해주세요. ex) 1");
        sj.add("--");
        sj.add("1. 시간 추가 : 지정한 시간을 추가합니다");
        sj.add("2. 시간 삭제 : 지정한 시간을 삭제합니다");
        sj.add("3. 시간 조회 : 저장되어 있는 시간을 모두 조회합니다");
        sj.add("4. 종료");
        return sj.toString();
    }
}
