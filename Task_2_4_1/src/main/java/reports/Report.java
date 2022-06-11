package reports;

import model.*;
import handlers.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class Report {

    private File file;
    private String repositories;
    private final Gson gson;
    private final Writer writer;
    private final Reader reader;

    public Report (String filePath, String repositoriesPath) throws IOException {
        file = new File(filePath);
        repositories = repositoriesPath;
        if (!file.exists()) {
            file.createNewFile();
        }
        gson = new GsonBuilder().setPrettyPrinting().create();
        writer = new FileWriter(file, false);
        reader = new FileReader(file);
    }

    public void writeNewReport() throws IOException, NoSuchFieldException, InvocationTargetException,
            InstantiationException, IllegalAccessException, NoSuchMethodException, GitAPIException {

        Tasks tasks = new Tasks();
        tasks.configure("Task_2_4_1/src/main/java/configs/tasks_config.groovy", true);

        Group groupConfig = new Group();
        groupConfig.configure("Task_2_4_1/src/main/java/configs/group_config.groovy", true);
        List<Student> students = groupConfig.getStudents();

        RepositoryHandler repH = new RepositoryHandler(repositories);
        repH.cloneRepository(students);

        List<TaskReport> taskReports = new ArrayList<>();

        for (Task task : tasks.getTasks()) {
            if (!task.isGiven()) {
                continue;
            }
            taskReports.add(new TaskReport(task, students, repositories));
        }

        gson.toJson(taskReports, writer);
        writer.flush();
        writer.close();
    }
}
