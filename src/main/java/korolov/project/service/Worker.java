package korolov.project.service;

import korolov.project.model.Monkey;
import korolov.project.service.parsers.StringToMonkeyParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Worker {

    private final String path = "input.txt";

    private final long rounds = 20;

    private final StringToMonkeyParser monkeyParser = new StringToMonkeyParser();

    List<Monkey> monkeys = new ArrayList<>();

    public void work() {

        Scanner scanner = null;

        try {
            File file = new File(path);
            scanner = new Scanner(file);

            List<String> textList = new ArrayList<>();

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.isBlank()) {
                    monkeys.add(monkeyParser.parse(textList));
                    textList.clear();
                } else {
                    textList.add(line);
                }
            }
            monkeys.add(monkeyParser.parse(textList)); // add last monkey
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("An error while reading file occurred.");
            fileNotFoundException.printStackTrace();
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }

        processMonkeys();

        for (int i = 0; i < monkeys.size(); i++) {
            System.out.println("Monkey " + i + ": " + monkeys.get(i).getInspectedItemsCounter());
        }

        monkeys.sort(Comparator.comparing(Monkey::getInspectedItemsCounter).reversed());
        if (monkeys.size() > 2) {
            System.out.println("Monkey business: " +
                    (monkeys.get(0).getInspectedItemsCounter() * monkeys.get(1).getInspectedItemsCounter()));
        }
    }

    private void processMonkeys() {

        for (long i = 0; i < rounds; i++) {
            for (Monkey monkey : monkeys) {
                processOneMonkey(monkey);
            }
        }
    }

    private void processOneMonkey(Monkey monkey) {

        // no items to process
        while (monkey.hasItems()) {
            // apply operation
            long newWorryLevel = applyOperation(monkey);

            // relief that the monkey's inspection didn't damage the item
            newWorryLevel = (long) (newWorryLevel / 3d);

            // do test
            if ((newWorryLevel % monkey.getDivisibleBy()) == 0) {
                monkeys.get(monkey.getIdToThrowIfTrue()).addItem(newWorryLevel);
            } else {
                monkeys.get(monkey.getIdToThrowIfFalse()).addItem(newWorryLevel);
            }

            monkey.increaseCounter();
        }
    }

    private Long applyOperation(Monkey monkey) {

        var oldWorryLevel = monkey.pollItem();

        // find operation value
        Long operationValue;
        if (monkey.getOperation().getOperationValue().equals("old")) {
            operationValue = oldWorryLevel;
        } else {
            operationValue = Long.valueOf(monkey.getOperation().getOperationValue());
        }

        // apply operation
        switch (monkey.getOperation().getOperationSign()) {
            case "+" -> {
                return oldWorryLevel + operationValue;
            }
            case "*" -> {
                return oldWorryLevel * operationValue;
            }
            default ->
                    throw new IllegalStateException("Wrong operation sign: " + monkey.getOperation().getOperationSign());
        }
    }
}
