package roomescape.console.view;

import java.util.List;
import java.util.Scanner;
import org.springframework.stereotype.Component;

@Component
public class InputView {

    private static final Scanner SCANNER = new Scanner(System.in);

    public AdminMenu inputAdminMenu() {
        return AdminMenu.from(Integer.parseInt(SCANNER.nextLine()));
    }

    public List<String> inputCommandLine() {
        String input = SCANNER.nextLine();
        return List.of(input.split(" "));
    }
}
