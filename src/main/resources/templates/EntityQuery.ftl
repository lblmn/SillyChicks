package ${packagePath}.entity;

import ${packagePath}.entity.${modelClass};
import ${packagePath}.utils.Pager;
import lombok.Data;

@Data
public class ${modelClass}Query {
    private ${modelClass} ${modelName};
    private Pager pager;
}
