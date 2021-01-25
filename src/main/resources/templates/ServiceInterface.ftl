package ${packagePath}.service;

import ${packagePath}.entity.${modelClass}Query;
import ${packagePath}.entity.${modelClass};
import org.springframework.data.domain.Page;

/**
*
*
*/
public interface ${modelClass}Service {

    Page<${modelClass}> list(${modelClass}Query ${modelName}Query);

    ${modelClass} add(${modelClass} ${modelName});

    boolean delete(String[] ids);
}
