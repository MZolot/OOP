package model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class Group extends GroovyConfigurable {
    private String number;
    private List<Student> students;
}
