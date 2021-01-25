package com.bing.haagendzs.services.impl;

import com.bing.haagendzs.exceptions.HaatokenException;
import com.bing.haagendzs.exceptions.enums.HaatokenExceptionEnum;
import com.bing.haagendzs.models.Haatoken;
import com.bing.haagendzs.models.query.HaatokenQuery;
import com.bing.haagendzs.repositories.HaatokenRepo;
import com.bing.haagendzs.services.StudentHaagendazstokenService;
import com.bing.haagendzs.utils.PageUtil;
import com.bing.haagendzs.utils.ToolkitUtil;
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
 */
@Service
public class StudentHaagendazstokenServiceImpl implements StudentHaagendazstokenService {

    @Resource
    private HaatokenRepo haatokenRepo;

    @Override
    public Page<Haatoken> list(HaatokenQuery haatokenQuery) {
        Haatoken haatoken = haatokenQuery.getHaatoken();
        Pageable pageable = PageUtil.toPageable(haatokenQuery.getPager());
        Specification<Haatoken> studentHaagendazstokenSpecification = (Specification<Haatoken>) (root, criteriaQuery, criteriaBuilder) -> {
            Predicate predicate = null;
            if (!StringUtils.isEmpty(haatoken.getId())) {
                //                predicate = criteriaBuilder.like(root.get("memo").as(String.class), "%" + studentHaagendazstoken.getMemo() + "%");
                predicate = criteriaBuilder.equal(root.get("id").as(String.class), haatoken.getId());
            }
            return predicate;
        };
        return haatokenRepo.findAll(studentHaagendazstokenSpecification, pageable);
    }

    @Override
    public Haatoken add(Haatoken target) {
        Haatoken _return = null;
        if (StringUtils.isEmpty(target.getId())) {
            _return = haatokenRepo.save(target);
        } else {
            if (haatokenRepo.existsById(target.getId())) {
                Haatoken source = haatokenRepo.getOne(target.getId());
                ToolkitUtil.copyNonNullProperties(target, source);
                _return = haatokenRepo.save(source);
            } else {
                throw new HaatokenException(HaatokenExceptionEnum.NO_OBJECT_AS_THIS);
            }
        }
        return _return;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public boolean delete(String[] ids) {
        if (ids.length == 0) {
            throw new HaatokenException(HaatokenExceptionEnum.EMPTY_PARAMS);
        } else {
            for (String id :
                    ids) {
                if (haatokenRepo.existsById(Integer.valueOf(id))) {
                    haatokenRepo.deleteById(Integer.valueOf(id));
                } else {
                    throw new HaatokenException(HaatokenExceptionEnum.NO_OBJECT_AS_THIS);
                }
            }
        }
        return true;
    }

}
