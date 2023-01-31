package korolov.project;

import korolov.project.service.Worker;

public class Application {
    public static void main(String[] args) {
        var worker = new Worker();
        worker.work();
    }
}