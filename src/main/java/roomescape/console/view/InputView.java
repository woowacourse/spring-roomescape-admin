package roomescape.console.view;

import java.util.List;
import java.util.Scanner;
import org.springframework.stereotype.Component;

@Component
public class InputView {

    private static final Scanner SCANNER = new Scanner(System.in);

    // 1.사용자 관리 명령어 입력 부분 1 또는 2
    public int inputMenu() {
        return Integer.parseInt(SCANNER.nextLine());
    }

    public List<String> inputCommandLine() {
        String input = SCANNER.nextLine();
        return List.of(input.split(" "));
    }

    public String input() {
        return SCANNER.next();
    }
}
