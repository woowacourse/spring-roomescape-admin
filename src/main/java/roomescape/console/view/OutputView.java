package roomescape.console.view;

public class OutputView {
    public void printResponse(ConsoleResponse dispatch) {
        System.out.println(dispatch.getContent());
    }

    public void printErrorMessage(RuntimeException e) {
        System.out.println(e.getMessage());
    }

    public void printCommandGuide() {
        String commandGuideMessage = """
        
        시간 예약 어드민 시스템입니다
        # 시간 관련 명령어
            - 시간 등록: post times [HH:mm]
            - 시간 목록 조회: get times
            - 시간 삭제: delete times [timeId]
            - 시간 예약: reserve [이름] [yyyy-MM-dd] [HH:mm]
        # 예약 관련 명령어
            - 예약 등록: post reservations [이름] [yyyy-MM-dd] [timeId]
            - 예약 목록 조회: get reservations
            - 예약 삭제: delete reservations [reservationId]
        # 종료: end game""";
        System.out.println(commandGuideMessage);
    }
}
