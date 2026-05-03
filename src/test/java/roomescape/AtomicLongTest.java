package roomescape;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class AtomicLongTest {

    static final int THREAD_COUNT = 10;

    static final int INCREMENT_COUNT = 100_000;

    @Test
    @DisplayName("AtomicLong은 동시에 접근해도 각자의 연산을 보장하고, Long은 그렇지 않다.")
    void atomicLong_should_be_thread_safe() throws InterruptedException {
        long[] normalCount = {0}; // 람다에서 사용하려고 배열로 감쌈
        AtomicLong atomicCount = new AtomicLong(0);
        Thread[] threads = new Thread[THREAD_COUNT];

        for (int i = 0; i < THREAD_COUNT; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < INCREMENT_COUNT; j++) {
                    normalCount[0]++;
                    atomicCount.incrementAndGet();
                }
            });
        }

        for (Thread thread : threads) thread.start();
        for (Thread thread : threads) thread.join();

        long expected = (long) THREAD_COUNT * INCREMENT_COUNT;

        assertEquals(expected, atomicCount.get());
        assertNotEquals(expected, normalCount[0]);
    }
}
