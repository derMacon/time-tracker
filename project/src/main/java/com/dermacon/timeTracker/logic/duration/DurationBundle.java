package com.dermacon.timeTracker.logic.duration;

import java.time.Duration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class DurationBundle implements DurationTask {

    private List<DurationTask> nodes = new LinkedList<>();

    @Override
    public int getChildrenCount() {
        return nodes.size();
    }


    @Override
    public DurationTask get(int i) {
        return nodes.get(i);
    }

    @Override
    public Duration getTotal() {
        Duration out = Duration.ZERO;
        for (DurationTask task : nodes) {
            out = out.plus(task.getTotal());
        }
        return out;
    }

    @Override
    public Duration getToday() {
        Duration out = Duration.ZERO;
        for (DurationTask task : nodes) {
            out = out.plus(task.getToday());
        }
        return out;
    }

    @Override
    public DurationTask add(DurationTask task) {
        this.nodes.add(task);
        return this; // todo maybe take this out?
    }

    @Override
    public Iterator<DurationTask> iterator() {
        return this.nodes.iterator();
    }
}
