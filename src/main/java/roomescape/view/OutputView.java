package roomescape.view;

import org.springframework.stereotype.Component;

@Component
public class OutputView {
    
    public void printStartMessage() {
        System.out.println("=== 방탈출 예약 콘솔 프로그램 ===");
        System.out.println();
    }
}
