package roomescape.console;

import org.springframework.stereotype.Component;
import java.util.Scanner;

@Component
public class ConsoleInputScanner {

    private final Scanner scanner = new Scanner(System.in);

    public String getInput() {
        return scanner.nextLine();
    }
}
