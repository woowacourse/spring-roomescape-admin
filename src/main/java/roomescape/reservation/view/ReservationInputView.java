package roomescape.reservation.view;

import roomescape.reservation.domain.dto.ReservationReqDto;

import java.time.LocalDate;
import java.util.Scanner;

public class ReservationInputView {

    private static final Scanner sc = new Scanner(System.in);

    public String readCommandInput() {
        System.out.println("------------------------------");
        System.out.println("기능을 선택해 주세요.\n" +
                "1. 지금까지의 모든 예약 조회\n" +
                "2. 예약 추가\n" +
                "3. 예약 삭제\n" +
                "4. 지금까지의 모든 예약 시간 조회\n" +
                "5. 예약 시간 추가\n" +
                "6. 예약 시간 삭제");
        System.out.println("ex) 5");
        System.out.print("> ");
        return sc.nextLine();
    }

    public ReservationReqDto readReservationReqDto() {
        System.out.println("입력 형식은 다음과 같습니다.");
        System.out.println("{이름}, {날짜(YYYY-mm-dd)}, {시간 id}");
        System.out.println("ex) kali, 2025-11-23, 1");
        System.out.print("> ");
        String input = sc.nextLine();
        String[] split = input.split(", ", -1);
        return new ReservationReqDto(split[0], LocalDate.parse(split[1]), Long.parseLong(split[2]));
    }

    public Long readReservationId() {
        System.out.println("id를 입력해주세요");
        System.out.println("ex) 1");
        System.out.print("> ");
        return Long.parseLong(sc.nextLine());
    }
}
