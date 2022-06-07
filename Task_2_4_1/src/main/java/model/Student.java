package model;

import groovy.lang.GroovyObjectSupport;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Student extends GroovyConfigurable {
    private String nickname;
    private String fullName;
    private String repositoryURL;
}