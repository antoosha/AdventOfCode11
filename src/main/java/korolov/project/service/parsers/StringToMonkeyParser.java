package korolov.project.service.parsers;

import korolov.project.model.Monkey;
import korolov.project.model.Operation;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

public class StringToMonkeyParser {

    private String itemsStartText = "Starting items:";
    private String operationStartText = "Operation: new = old";
    private String testStartText = "Test: divisible by";
    private String trueCaseStartText = "If true: throw to monkey";
    private String falseCaseStartText = "If false: throw to monkey";

    public Monkey parse(List<String> text) {

        if (text.size() < 6) {
            throw new IllegalStateException("Format of Monkey's configuration is wrong.");
        }

        //find items
        Queue<Long> items = parseItems(text.get(1));

        //find operation
        Operation operation = parseOperation(text.get(2));

        //find test divisibleBy
        Long divisibleBy = parseDivisibleBy(text.get(3));

        //find true case config
        Integer idToThrowIfTrue = parseTrueTestCase(text.get(4));

        //find false case config
        Integer idToThrowIfFalse = parseFalseTestCase(text.get(5));

        return new Monkey(items, operation, divisibleBy, idToThrowIfTrue, idToThrowIfFalse);
    }

    private Queue<Long> parseItems(String itemsText) {

        Queue<Long> items = new ArrayDeque<>();

        if (!itemsText.contains(itemsStartText)) {
            throw new IllegalStateException("Items text has wrong format.");
        }

        //In split is used +3 because 1)skip last character and 2)" " 3)move to first char we need
        String[] itemsStr = itemsText.substring(itemsStartText.length() + 3).split(", ");

        for (String i : itemsStr) {
            items.add(Long.parseLong(i));
        }

        return items;
    }

    private Operation parseOperation(String operationText) {

        if (!operationText.contains(operationStartText)) {
            throw new IllegalStateException("Operation text has wrong format.");
        }

        //In split is used +3 because 1)skip last character and 2)" " 3)move to first char we need
        operationText = operationText.substring(operationStartText.length() + 3);
        String[] splitText = operationText.split(" ");

        if (splitText.length != 2) {
            throw new IllegalStateException("Operation text has wrong format.");
        }

        return new Operation(splitText[0], splitText[1]);
    }

    private Long parseDivisibleBy(String divisibleByText) {

        if (!divisibleByText.contains(testStartText)) {
            throw new IllegalStateException("Test text has wrong format.");
        }

        var divisibleByTextStr = divisibleByText.split(" ");
        return Long.parseLong(divisibleByTextStr[divisibleByTextStr.length - 1]);
    }

    private Integer parseTrueTestCase(String trueTestCase) {

        if (!trueTestCase.contains(trueCaseStartText)) {
            throw new IllegalStateException("True case has wrong format.");
        }

        var trueTestCaseStr = trueTestCase.split(" ");
        return Integer.parseInt(trueTestCaseStr[trueTestCaseStr.length - 1]);
    }

    private Integer parseFalseTestCase(String falseTestCase) {

        if (!falseTestCase.contains(falseCaseStartText)) {
            throw new IllegalStateException("False case has wrong format.");
        }

        var falseTestCaseStr = falseTestCase.split(" ");
        return Integer.parseInt(falseTestCaseStr[falseTestCaseStr.length - 1]);
    }
}
