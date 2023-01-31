package korolov.project.model;

public class Operation {

    private final String operationSign;

    private final String operationValue;

    public Operation(String operationSign, String operationValue) {
        this.operationSign = operationSign;
        this.operationValue = operationValue;
    }

    public String getOperationSign() {
        return operationSign;
    }

    public String getOperationValue() {
        return operationValue;
    }
}
