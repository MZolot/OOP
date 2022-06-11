package reports;

import lombok.Getter;
import model.Student;
import model.Task;

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
            handlers.GradleHandler gradleHandler = new handlers.GradleHandler();
            boolean buildRes = gradleHandler.buildTask(repositoriesPath.concat(student.getNickname()).concat(folderName));
            boolean testRes = gradleHandler.testTask(repositoriesPath.concat(student.getNickname()).concat(folderName));
            int score = 0;
            if (buildRes && testRes) {
                score = task.getMaxScore();
            }
            results.add(new StudentReport(student.getFullName(), buildRes, testRes, score));
        }
    }
}
