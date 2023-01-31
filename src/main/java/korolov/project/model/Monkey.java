package korolov.project.model;

import java.util.Queue;

public class Monkey {

    private final Queue<Long> items;
    private final Operation operation;
    private final long divisibleBy;
    private final int idToThrowIfTrue;
    private final int idToThrowIfFalse;
    private long inspectedItemsCounter = 0;

    public Monkey(Queue<Long> items, Operation operation, long divisibleBy, int idToThrowIfTrue, int idToThrowIfFalse) {
        this.items = items;
        this.operation = operation;
        this.divisibleBy = divisibleBy;
        this.idToThrowIfTrue = idToThrowIfTrue;
        this.idToThrowIfFalse = idToThrowIfFalse;
    }

    public void addItem(Long item) {
        this.items.add(item);
    }

    public Long pollItem() {
        return this.items.poll();
    }

    public void increaseCounter() {
        inspectedItemsCounter = inspectedItemsCounter + 1;
    }

    public long getInspectedItemsCounter() {
        return inspectedItemsCounter;
    }

    public Operation getOperation() {
        return operation;
    }

    public long getDivisibleBy() {
        return divisibleBy;
    }

    public int getIdToThrowIfTrue() {
        return idToThrowIfTrue;
    }

    public int getIdToThrowIfFalse() {
        return idToThrowIfFalse;
    }

    public boolean hasItems() {
        return !items.isEmpty();
    }
}
