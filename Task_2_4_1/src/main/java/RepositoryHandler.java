import model.Student;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;
import java.util.Collection;

public class RepositoryHandler {

    private final String path;

    public RepositoryHandler(String repositoriesPath) {
        path = repositoriesPath;
    }

    private void deleteDirectory(File directory) {
        File[] allContents = directory.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        directory.delete();
    }

    public void addRepository(Student student) throws GitAPIException {
        File directory = new File(path.concat(student.getNickname()));
        if (directory.exists()) {
            deleteDirectory(directory);
        }
        directory.mkdirs();
        System.out.println("Cloning " + student.getNickname() + "'s repository...");
        Git.cloneRepository()
                .setURI(student.getRepositoryURL())
                .setDirectory(directory)
                .call();
    }

    public void addRepository(Collection<Student> students) throws GitAPIException {
        for (Student student : students) {
            addRepository(student);
        }
    }
}
