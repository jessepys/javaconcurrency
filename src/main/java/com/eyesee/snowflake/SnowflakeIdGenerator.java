package com.eyesee.snowflake;

import java.util.concurrent.atomic.AtomicReference;

import java.util.Objects;

/**
 * The twitter snowflake id generator.
 * <pre>
 *     The format:
 *     1 + 41 time millis + 5 data center size + 5 worker size + 12 sequence.
 *
 *     The total length is: 64
 *
 * </pre>
 *
 * @Auther jesse
 */
public class SnowflakeIdGenerator {
    private static final long WORKER_ID_BITS = 5L;
    private static final long DATA_CENTER_BITS = 5L;
    //The max worker id is 31
    private static final long MAX_WORKER_ID = -1L ^ (-1L << WORKER_ID_BITS);
    //the max data center id is 31
    private static final long MAX_DATA_CENTER_ID = -1L ^ (-1L << DATA_CENTER_BITS);
    private static final long SEQUENCE_BITS = 12L;
    private static final long WORKER_ID_SHIFT = SEQUENCE_BITS;
    //the data center shift left:12 + 5
    private static final long DATA_CENTER_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;
    //the timestamp shift:12 + 5 + 5
    private static final long TIMESTAMP_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS + DATA_CENTER_BITS;
    private static final long SEQUENCE_MASK = -1L ^ (-1L << SEQUENCE_BITS);
    private long workerId;
    private long dataCenterId;

    private AtomicReference<TimestampSequence> timestampSequenceReference = new AtomicReference<>(new TimestampSequence());

    public TimestampSequence generateNextTimestampSequence() {
        long timestamp = timeMillis();
        long newSequenceValue = 0l;
        TimestampSequence oldTimestampSequence = timestampSequenceReference.get();
        TimestampSequence newTimestampSequence = new TimestampSequence();
        if (timestamp == oldTimestampSequence.getTimestamp()) {
            newSequenceValue = (oldTimestampSequence.getSequence() + 1) & SEQUENCE_MASK;
            if (newSequenceValue != 0) {
                newTimestampSequence.setSequence(newSequenceValue);
                newTimestampSequence.setTimestamp(timestamp);
            } else {
                return generateNextTimestampSequence();
            }
        } else {
            newTimestampSequence.setTimestamp(timestamp);
            newTimestampSequence.setSequence(newSequenceValue);
        }

        if(!timestampSequenceReference.compareAndSet(oldTimestampSequence, newTimestampSequence)) {
            return generateNextTimestampSequence();
        }

        return newTimestampSequence;
    }

    public class TimestampSequence {
        //the timstamp when generate the sequence
        private long timestamp = -1l;
        //the sequence(0~4095 Math.pow(2, 12))
        private long sequence = 0l;

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }

        public long getSequence() {
            return sequence;
        }

        public void setSequence(long sequence) {
            this.sequence = sequence;
        }

        @Override
        public int hashCode() {
            return Long.hashCode(timestamp) * 31 + Long.hashCode(sequence);
        }

        @Override
        public boolean equals(Object obj) {
            if (Objects.isNull(obj)) {
                return false;
            }

            if (obj instanceof TimestampSequence) {
                TimestampSequence timestampSequence = (TimestampSequence) obj;
                return (this.timestamp == timestampSequence.getTimestamp()) && (this.sequence == timestampSequence.getSequence());
            }

            return false;
        }
    }

    /**
     *
     * @param workerId  (0~31)
     * @param dataCenterId  (0~31)
     */
    public SnowflakeIdGenerator(long workerId, long dataCenterId) {
        if (workerId > MAX_WORKER_ID || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", MAX_WORKER_ID));
        }
        if (dataCenterId > MAX_DATA_CENTER_ID || dataCenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", MAX_DATA_CENTER_ID));
        }
        this.workerId = workerId;
        this.dataCenterId = dataCenterId;
    }

    public long nextId() {
        TimestampSequence timestampSequence = generateNextTimestampSequence();
        return (timestampSequence.getTimestamp() << TIMESTAMP_SHIFT) //
                | (dataCenterId << DATA_CENTER_SHIFT) //
                | (workerId << WORKER_ID_SHIFT) //
                | timestampSequence.getSequence();

    }

    protected long timeMillis() {
        return System.currentTimeMillis();
    }


    public static void main(String[] args) {
        SnowflakeIdGenerator idWorker = new SnowflakeIdGenerator(0, 0);

        Runnable runnable = () -> {
            Long id = idWorker.nextId();
            System.out.println(id);
        };
        for (int i = 0; i < 1000; i++) {
            new Thread(runnable).start();
        }
    }
}
