package com.bing.monkey.haagendzs.service.impl;

import com.bing.monkey.common.util.PageUtil;
import com.bing.monkey.common.util.ToolkitUtil;
import com.bing.monkey.haagendzs.entity.HaaOrgData;
import com.bing.monkey.haagendzs.entity.query.HaaOrgDataQuery;
import com.bing.monkey.haagendzs.exceptions.HaaOrgDataException;
import com.bing.monkey.haagendzs.exceptions.enums.HaaOrgDataExceptionEnum;
import com.bing.monkey.haagendzs.repositories.HaaOrgDataRepo;
import com.bing.monkey.haagendzs.service.HaaOrgDataService;
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
public class HaaOrgDataServiceImpl implements HaaOrgDataService {


    @Resource
    private HaaOrgDataRepo haaOrgDataRepo;

    @Override
    public Page<HaaOrgData> list(HaaOrgDataQuery haaOrgDataQuery) {
        Page<HaaOrgData> orgData;
        Specification<HaaOrgData> haaOrgDataSpecification;
        if (null != haaOrgDataQuery) {
            HaaOrgData haaOrgData = haaOrgDataQuery.getHaaOrgData();
            haaOrgDataSpecification = (root, criteriaQuery, criteriaBuilder) -> {
                Predicate predicate = null;
                if (null != haaOrgData) {
                    if (!StringUtils.isEmpty(haaOrgData.getMemo())) {
                        //                predicate = criteriaBuilder.like(root.get("memo").as(String.class), "%" + haaOrgData.getMemo() + "%");
                        predicate = criteriaBuilder.equal(root.get("memo").as(String.class), haaOrgData.getMemo());
                    }
                    return predicate;
                } else {
                    return null;
                }
            };
            Pageable pageable = PageUtil.toPageable(haaOrgDataQuery.getPager());
            orgData = haaOrgDataRepo.findAll(haaOrgDataSpecification, pageable);
        } else {
            orgData = haaOrgDataRepo.findAll(PageUtil.newPageable("id","asc"));
        }
        return orgData;
    }

    @Override
    public HaaOrgData getOne(HaaOrgDataQuery haaOrgDataQuery) {
        return null;
    }

    @Override
    public HaaOrgData add(HaaOrgData target) {
        HaaOrgData _return;
        if (StringUtils.isEmpty(target.getId())) {
            _return = haaOrgDataRepo.save(target);
        } else {
            if (haaOrgDataRepo.existsById(target.getId())) {
                HaaOrgData source = haaOrgDataRepo.getOne(target.getId());
                ToolkitUtil.copyNonNullProperties(target, source);
                _return = haaOrgDataRepo.save(source);
            } else {
                throw new HaaOrgDataException(HaaOrgDataExceptionEnum.NO_OBJECT_AS_THIS);
            }
        }
        return _return;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public boolean delete(String[] ids) {
        if (ids.length == 0) {
            throw new HaaOrgDataException(HaaOrgDataExceptionEnum.EMPTY_PARAMS);
        } else {
            for (String id :
                    ids) {
                if (haaOrgDataRepo.existsById(Integer.valueOf(id))) {
                    haaOrgDataRepo.deleteById(Integer.valueOf(id));
                } else {
                    throw new HaaOrgDataException(HaaOrgDataExceptionEnum.NO_OBJECT_AS_THIS);
                }
            }
        }
        return true;
    }

}
