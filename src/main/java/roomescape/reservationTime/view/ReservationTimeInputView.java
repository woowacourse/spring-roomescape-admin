package roomescape.reservationTime.view;

import roomescape.reservationTime.domain.dto.ReservationTimeReqDto;

import java.time.LocalTime;
import java.util.Scanner;

public class ReservationTimeInputView {

    private static final Scanner sc = new Scanner(System.in);

    public ReservationTimeReqDto readReservation() {
        System.out.println("입력 형식은 다음과 같습니다.");
        System.out.println("HH:mm");
        System.out.println("ex) 11:22");
        System.out.print("> ");
        String input = sc.nextLine();
        return new ReservationTimeReqDto(LocalTime.parse(input));
    }

    public Long readReservationTimeId() {
        System.out.println("id를 입력해주세요");
        System.out.println("ex) 1");
        System.out.print("> ");
        return Long.parseLong(sc.nextLine());
    }
}
