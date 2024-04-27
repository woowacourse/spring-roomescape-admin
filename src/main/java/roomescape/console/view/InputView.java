package roomescape.console.view;

import java.util.Scanner;

public class InputView {
    private static final Scanner sc = new Scanner(System.in);

    public ConsoleRequest getRequest() {
        String input = sc.nextLine();
        try {
            String[] inputs = input.split(" ");
            return new ConsoleRequest(inputs);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("명령어 길이가 짧습니다. 입력한 명령어: '" + input + "'");
        }
    }
}
