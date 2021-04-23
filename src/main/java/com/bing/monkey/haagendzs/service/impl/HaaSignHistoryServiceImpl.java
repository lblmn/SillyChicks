package com.bing.monkey.haagendzs.service.impl;

import com.bing.monkey.common.util.PageUtil;
import com.bing.monkey.common.util.ToolkitUtil;
import com.bing.monkey.haagendzs.entity.HaaSignHistory;
import com.bing.monkey.haagendzs.entity.query.HaaSignHistoryQuery;
import com.bing.monkey.haagendzs.exceptions.HaaSignHistoryException;
import com.bing.monkey.haagendzs.exceptions.enums.HaaSignHistoryExceptionEnum;
import com.bing.monkey.haagendzs.repositories.HaaSignHistoryRepo;
import com.bing.monkey.haagendzs.service.HaaSignHistoryService;
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
public class HaaSignHistoryServiceImpl implements HaaSignHistoryService {

    @Resource
    private HaaSignHistoryRepo haaSignHistoryRepo;

    @Override
    public Page<HaaSignHistory> list(HaaSignHistoryQuery haaSignHistoryQuery) {
        HaaSignHistory haaSignHistory = haaSignHistoryQuery.getHaaSignHistory();
        Pageable pageable = PageUtil.toPageable(haaSignHistoryQuery.getPager());
        Specification<HaaSignHistory> haaSignHistorySpecification = (Specification<HaaSignHistory>) (root, criteriaQuery, criteriaBuilder) -> {
            Predicate predicate = null;
            if (null != haaSignHistory) {
                if (!StringUtils.isEmpty(haaSignHistory.getName())) {
                    //                predicate = criteriaBuilder.like(root.get("memo").as(String.class), "%" + haaSignHistory.getMemo() + "%");
                    predicate = criteriaBuilder.equal(root.get("name").as(String.class), haaSignHistory.getName());
                }
                return predicate;
            } else {
                return null;
            }
        };
        return haaSignHistoryRepo.findAll(haaSignHistorySpecification, pageable);
    }

    @Override
    public HaaSignHistory add(HaaSignHistory target) {
        HaaSignHistory _return;
        if (StringUtils.isEmpty(target.getId())) {
            _return = haaSignHistoryRepo.save(target);
        } else {
            if (haaSignHistoryRepo.existsById(target.getId())) {
                HaaSignHistory source = haaSignHistoryRepo.getOne(target.getId());
                ToolkitUtil.copyNonNullProperties(target, source);
                _return = haaSignHistoryRepo.save(source);
            } else {
                throw new HaaSignHistoryException(HaaSignHistoryExceptionEnum.NO_OBJECT_AS_THIS);
            }
        }
        return _return;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public boolean delete(String[] ids) {
        if (ids.length == 0) {
            throw new HaaSignHistoryException(HaaSignHistoryExceptionEnum.EMPTY_PARAMS);
        } else {
            for (String id :
                    ids) {
                if (haaSignHistoryRepo.existsById(Integer.valueOf(id))) {
                    haaSignHistoryRepo.deleteById(Integer.valueOf(id));
                } else {
                    throw new HaaSignHistoryException(HaaSignHistoryExceptionEnum.NO_OBJECT_AS_THIS);
                }
            }
        }
        return true;
    }

}
