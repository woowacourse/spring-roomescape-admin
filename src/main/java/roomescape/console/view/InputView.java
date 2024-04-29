package roomescape.console.view;


import java.util.InputMismatchException;
import java.util.Scanner;
import org.springframework.stereotype.Component;

@Component
public class InputView {
    private static final Scanner scanner = new Scanner(System.in);
    private final OutputView outputView;

    public InputView(final OutputView outputView) {
        this.outputView = outputView;
    }

    public String read() {
        return scanner.nextLine();
    }

    public int readInt() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (InputMismatchException e) {
            throw new IllegalArgumentException("올바른 명령어가 아닙니다.");
        }
    }

    public String readTime() {
        outputView.print("예약할 시간을 입력해주세요. 형식(HH:mm)");
        return scanner.nextLine();
    }

    public Long readTimeId() {
        try {
            outputView.print("삭제할 예약 시간 ID를 입력해주세요.");
            return Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("올바른 시간 ID가 아닙니다.");
        }
    }

    public String readCreateReservation() {
        outputView.print("예약을 생성합니다.");
        outputView.print("형식: {이름 / 날짜(yyyy-mm-dd) / 예약 번호}");
        return scanner.nextLine();
    }

    public long readReservationId() {
        outputView.print("예약을 삭제합니다.");
        outputView.print("형식: 예약 번호");
        return Long.parseLong(scanner.nextLine());
    }
}
