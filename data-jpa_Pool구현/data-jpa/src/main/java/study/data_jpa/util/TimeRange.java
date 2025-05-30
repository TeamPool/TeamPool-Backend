package study.data_jpa.util;

import java.time.LocalTime;
import java.util.Objects;

public class TimeRange implements Comparable<TimeRange> {
    private LocalTime start;
    private LocalTime end;

    public TimeRange(LocalTime start, LocalTime end) {
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start time must be before end time.");
        }
        this.start = start;
        this.end = end;
    }

    public LocalTime getStart() {
        return start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public boolean overlaps(TimeRange other) {
        return !this.end.isBefore(other.start) && !this.start.isAfter(other.end);
    }

    public boolean isAdjacent(TimeRange other) {
        return this.end.equals(other.start) || this.start.equals(other.end);
    }

    public TimeRange merge(TimeRange other) {
        return new TimeRange(
                this.start.isBefore(other.start) ? this.start : other.start,
                this.end.isAfter(other.end) ? this.end : other.end
        );
    }

    @Override
    public String toString() {
        return start.toString() + " - " + end.toString();
    }

    @Override
    public int compareTo(TimeRange o) {
        return this.start.compareTo(o.start);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TimeRange)) return false;
        TimeRange that = (TimeRange) o;
        return start.equals(that.start) && end.equals(that.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }
}
