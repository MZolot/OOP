package nsu.oop.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data

public class Mapping extends GroovyConfigurable{
    String id;
    String path;
}
