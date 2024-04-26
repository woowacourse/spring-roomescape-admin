package roomescape.support.extension;

import static org.mockito.Mockito.when;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import roomescape.support.annotation.FixedClock;

public class MockClockExtension implements BeforeEachCallback {
    @Override
    public void beforeEach(ExtensionContext context) {
        Clock clockBean = SpringExtension.getApplicationContext(context).getBean(Clock.class);
        FixedClock fixedClockAnnotation = context.getRequiredTestClass().getDeclaredAnnotation(FixedClock.class);
        when(clockBean.instant()).thenReturn(fixedClock(fixedClockAnnotation.date()).instant());
    }

    private static Clock fixedClock(String date) {
        return Clock.fixed(Instant.parse(date + "T00:00:00Z"), ZoneId.of("UTC"));
    }
}
