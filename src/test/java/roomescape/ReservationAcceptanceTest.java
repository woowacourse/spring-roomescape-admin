package roomescape;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestExecutionListeners;
import roomescape.testExecutionListener.TestDatabaseInitializer;
import roomescape.testExecutionListener.TestPortInitializer;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Retention(RetentionPolicy.RUNTIME)
@TestExecutionListeners(listeners = {TestPortInitializer.class,
        TestDatabaseInitializer.class}, mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
public @interface ReservationAcceptanceTest {
}
