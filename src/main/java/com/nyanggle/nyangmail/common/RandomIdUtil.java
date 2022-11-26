package com.nyanggle.nyangmail.common;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;

public class RandomIdUtil implements UserIdGenerator, SnackCartGenerator, FishBreadGenerator {

    private static final int max = 256 * 256;
    private static final AtomicInteger intVal = new AtomicInteger(0);

    private static String generate() {
        final long uuid = Instant.now().toEpochMilli() * max +
                intVal.accumulateAndGet(1, (index, inc) -> (index + inc) % max);
        return Long.toHexString(uuid);
    }
    public String next() {
        return generate();
    }

    @Override
    public String userId() {
        return "U" + next();
    }

    @Override
    public String snackCartId() {
        return "S" + next();
    }

    @Override
    public String fishBreadId() {
        return "F" + next();
    }
}
