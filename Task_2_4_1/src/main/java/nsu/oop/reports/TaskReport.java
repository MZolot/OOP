package nsu.oop.reports;

import lombok.Getter;
import nsu.oop.model.Student;
import nsu.oop.model.Task;

import java.util.ArrayList;
import java.util.List;

@Getter
public class TaskReport {
    private final String id;
    private final String name;
    private final List<StudentReport> results;

    record StudentReport (String name, boolean buildResult, boolean testResult, int score) {}

    public TaskReport (Task task, List<Student> students, String repositoriesPath) {
        id = task.getId();
        name = task.getName();
        String folderName = "/Task_".concat(id);

        results = new ArrayList<>();
        for (Student student : students) {
            nsu.oop.handlers.GradleHandler gradleHandler = new nsu.oop.handlers.GradleHandler();
            boolean buildRes = gradleHandler.buildTask(repositoriesPath.concat(student.getNickname()).concat(folderName));
            boolean testRes = gradleHandler.testTask(repositoriesPath.concat(student.getNickname()).concat(folderName));
            results.add(new StudentReport(student.getFullName(), buildRes, testRes,
                    calculateScore(buildRes, testRes, task.getMaxScore())));
        }
    }

    private int calculateScore(boolean buildResult, boolean testResult, int maxScore) {
        if (buildResult && testResult) {
            return maxScore;
        } else if (buildResult || testResult) {
            return maxScore/2;
        }
        else {
            return 0;
        }
    }
}
