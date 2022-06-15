package nsu.oop.model;

import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
public class Settings extends GroovyConfigurable {
    private String repositoriesPath;
}
