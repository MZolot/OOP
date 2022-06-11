package handlers;

import model.Student;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;
import java.util.Collection;

public class RepositoryHandler {

    private final String path;
    private final String username;
    private final String password;

    public RepositoryHandler(String repositoriesPath, String username, String password) {
        path = repositoriesPath;
        this.username = username;
        this.password = password;
    }

    public RepositoryHandler(String repositoriesPath) {
        path = repositoriesPath;
        this.username = "";
        this.password = "";
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

    public void cloneRepository(Student student) throws GitAPIException {
        File directory = new File(path.concat(student.getNickname()));
        if (directory.exists()) {
            deleteDirectory(directory);
        }
        directory.mkdirs();
        System.out.println("Cloning " + student.getNickname() + "'s repository...");
        try {
            Git.cloneRepository()
                    .setURI(student.getRepositoryURL())
                    .setDirectory(directory)
                    .setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, password))
                    .call();
        } catch (TransportException e) {
            System.out.println("Can't access " + student.getNickname() + "'s repository");
            return;
        }
        System.out.println(student.getNickname() + "'s repository cloned successfully");
    }

    public void cloneRepository(Collection<Student> students) throws GitAPIException {
        for (Student student : students) {
            cloneRepository(student);
        }
    }
}
