package nsu.oop.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class Student extends GroovyConfigurable {
    private String nickname;
    private String fullName;
    private String repositoryURL;
    private List<Mapping> folders;
    private List<Mapping> branches;
}