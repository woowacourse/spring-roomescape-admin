package roomescape.console.view;

import roomescape.dto.response.TimesResponse;

import java.util.List;

public class OutputView {

    public void printTime(TimesResponse timesResponse) {
        System.out.println("추가된 시간");
        printTimeInfo(timesResponse);
    }

    public void printTimes(List<TimesResponse> allTimes) {
        System.out.println("추가된 시간");
        for (TimesResponse allTime : allTimes) {
            printTimeInfo(allTime);
        }
    }

    private void printTimeInfo(TimesResponse timesResponse) {
        System.out.println("ID : " + timesResponse.id());
        System.out.println("시간 : " + timesResponse.startAt());
        System.out.println();
    }
}
