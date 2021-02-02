package ${packagePath}.service.impl;

import ${packagePath}.entity.${modelClass}Query;
import ${packagePath}.entity.${modelClass};
import ${packagePath}.exception.${modelClass}Exception;
import ${packagePath}.exception.enums.${modelClass}ExceptionEnum;
import ${packagePath}.repository.${modelClass}Repo;
import ${packagePath}.service.${modelClass}Service;
import ${packagePath}.utils.PageUtil;
import ${packagePath}.utils.ToolkitUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;

/**
*
*
*/
@Service
public class ${serviceClass}Impl implements ${serviceClass} {

    @Resource
    private ${RepoClass} ${RepoName};

    @Override
    public Page<${modelClass}> list(${modelClass}Query ${modelName}Query) {
        ${modelClass} ${modelName} = ${modelName}Query.get${modelClass}();
        Pageable pageable = PageUtil.toPageable(${modelName}Query.getPager());
        //Specification<${modelClass}> ${modelName}Specification = (Specification<${modelClass}>) (root, criteriaQuery, criteriaBuilder) -> {
            //Predicate predicate = null;
            //if (!StringUtils.isEmpty(${modelName}.getMemo())) {
            //                predicate = criteriaBuilder.like(root.get("memo").as(String.class), "%" + ${modelName}.getMemo() + "%");
            //predicate = criteriaBuilder.equal(root.get("memo").as(String.class), ${modelName}.getMemo());
            //}
            //return predicate;
        //};
        //return ${modelName}Repo.findAll(${modelName}Specification, pageable);
        return ${modelName}Repo.findAll(pageable);
    }

    @Override
    public ${modelClass} add(${modelClass} target) {
        ${modelClass} _return = null;
        if (StringUtils.isEmpty(target.getId())) {
            _return = ${modelName}Repo.save(target);
        } else {
            if (${modelName}Repo.existsById(target.getId())) {
                ${modelClass} source = ${modelName}Repo.getOne(target.getId());
                ToolkitUtil.copyNonNullProperties(target, source);
                _return = ${modelName}Repo.save(source);
            } else {
                throw new ${modelClass}Exception(${modelClass}ExceptionEnum.NO_OBJECT_AS_THIS);
            }
        }
        return _return;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public boolean delete(String[] ids) {
        if (ids.length == 0) {
            throw new ${modelClass}Exception(${modelClass}ExceptionEnum.EMPTY_PARAMS);
        } else {
            for (String id :
            ids) {
                if (${modelName}Repo.existsById(Integer.valueOf(id))) {
                    ${modelName}Repo.deleteById(Integer.valueOf(id));
                } else {
                    throw new ${modelClass}Exception(${modelClass}ExceptionEnum.NO_OBJECT_AS_THIS);
                }
            }
        }
        return true;
    }

}
