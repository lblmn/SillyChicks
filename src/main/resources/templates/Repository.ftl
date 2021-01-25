package ${packagePath}.repository;

import ${packagePath}.entity.${modelClass};
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional(rollbackOn = Exception.class)
public interface ${modelClass}Repo extends JpaRepository<${modelClass}, Integer>, JpaSpecificationExecutor<${modelClass}> {
}
