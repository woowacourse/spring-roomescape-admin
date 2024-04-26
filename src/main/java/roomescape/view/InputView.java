package roomescape.view;


import java.util.InputMismatchException;
import java.util.Scanner;
import org.springframework.stereotype.Component;

@Component
public class InputView {
    private static final Scanner scanner = new Scanner(System.in);
    OutputView outputView;

    public InputView(final OutputView outputView) {
        this.outputView = outputView;
    }

    public int read() {
        try {
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            throw new IllegalArgumentException("올바른 명령어가 아닙니다.");
        }
    }

    public String readTime() {
        outputView.print("예약할 시간을 입력해주세요. 형식(HH:mm)");
        return scanner.next();
    }

    public Long readTimeId() {
        try {
            outputView.print("삭제할 예약 시간 ID를 입력해주세요.");
            return Long.parseLong(scanner.next());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("올바른 시간 ID가 아닙니다.");
        }
    }

}
