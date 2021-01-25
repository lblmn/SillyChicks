package ${packagePath}.entity;

import ${packagePath}.entity.${modelClass};
import ${packagePath}.util.questionnaire.Pager;
import lombok.Data;

@Data
public class ${modelClass}Query {
    private ${modelClass} ${modelName};
    private Pager pager;
}
