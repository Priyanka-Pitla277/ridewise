package com.airtribe.ridewise.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Priyanka Pitla
 */
public class IdGenerator {

    private static final String RIDER_PREFIX = "RDR-";
    private static final String DRIVER_PREFIX = "DRV-";
    private static final AtomicInteger sequence = new AtomicInteger(1);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final AtomicLong counter = new AtomicLong(1000);

    //generate rider id
    public static String generateRiderId() {
        long nextValue = counter.incrementAndGet();
        return RIDER_PREFIX + String.format("%06d", nextValue); // Formats to 6 digits
    }

    //generate driver id
    public static String generateDriverId() {
        String datePart = LocalDate.now().format(formatter);
        int seqPart = sequence.getAndIncrement();
        return DRIVER_PREFIX + datePart + "-" + String.format("%05d", seqPart);
    }
}
