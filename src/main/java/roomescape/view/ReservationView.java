package roomescape.view;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import roomescape.dto.ReservationResponse;

public class ReservationView {
    private final Scanner scanner = new Scanner(System.in);

    public String readName() {
        System.out.println("[INFO] 방탈출 예약을 진행합니다. 예약자 이름을 입력해주세요.");
        return scanner.nextLine();
    }

    public LocalDate readDate() {
        System.out.println("[INFO] 예약할 날짜를 선택해주세요. (ex. 2023-01-01)");
        return LocalDate.parse(scanner.nextLine());
    }

    public void printSuccessfullyAdded() {
        System.out.println("[INFO] 예약이 추가되었습니다.");
    }

    public int readReservationIdToDelete(final List<ReservationResponse> reservationResponses) {
        System.out.println("[INFO] 삭제할 방탈출 예약 번호를 선택해주세요.");
        printReservations(reservationResponses);
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 예약 번호 입력입니다.");
        }
    }

    public void printSuccessfullyDeleted() {
        System.out.println("[INFO] 예약이 삭제되었습니다.");
    }

    private void printReservations(final List<ReservationResponse> reservations) {
        System.out.println("번호  이름    날짜      시간");
        for (int index = 0; index < reservations.size(); index++) {
            printReservation(index, reservations.get(index));
        }
    }

    private void printReservation(final int index, final ReservationResponse reservationResponse) {
        System.out.printf(
                "%d.  %s  %s  %s%n",
                index,
                reservationResponse.name(),
                reservationResponse.date().toString(),
                reservationResponse.time().startAt().toString()
        );
    }
}
