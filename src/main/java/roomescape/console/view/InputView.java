package roomescape.console.view;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class InputView {

    private static final Scanner SCANNER = new Scanner(System.in);
    private static final String DELIMITER = ",";

    public Menu inputMenu() {
        System.out.println("""
                                
                <사용자 관리 명령어>
                1. 시간 관리 기능
                2. 예약 관리 기능
                3. 종료
                                
                """
        );
        return Menu.from(Integer.parseInt(SCANNER.nextLine()));
    }

    public List<String> inputReservationTimeMenu() {
        System.out.println("""
                                
                <사용자 관리 명령어>
                - 시간등록: POST, hh:mm 
                    예시) POST, 10:00
                - 시간조회: GET
                - 시간삭제: DELETE, 1 
                    예시) DELETE, 1
                    
                """
        );
        return inputCommandLine();
    }

    public List<String> inputReservationMenu() {
        System.out.println("""
                <사용자 관리 명령어>
                - 예약등록: POST, name, yyyy-MM-dd, {id} 
                    예시) POST, 브라운, 2023-08-05, 1
                - 예약조회: GET
                - 예약삭제: DELETE, 1 
                    예시) DELETE, 1
                """
        );
        return inputCommandLine();
    }

    private List<String> inputCommandLine() {
        String input = SCANNER.nextLine();
        String[] tokens = input.split(DELIMITER);

        return Arrays.stream(tokens)
                .map(String::trim)
                .toList();
    }
}
