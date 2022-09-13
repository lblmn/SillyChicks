package com.bing.monkey.haagendzs.service.impl;

import com.bing.monkey.common.util.PageUtil;
import com.bing.monkey.common.util.ToolkitUtil;
import com.bing.monkey.haagendzs.entity.HaaSignHistory;
import com.bing.monkey.haagendzs.entity.SignDateRange;
import com.bing.monkey.haagendzs.entity.query.HaaSignHistoryQuery;
import com.bing.monkey.haagendzs.exceptions.HaaSignHistoryException;
import com.bing.monkey.haagendzs.exceptions.enums.HaaSignHistoryExceptionEnum;
import com.bing.monkey.haagendzs.repositories.HaaSignHistoryRepo;
import com.bing.monkey.haagendzs.service.HaaSignHistoryService;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 */
@Service
@Log4j2
public class HaaSignHistoryServiceImpl implements HaaSignHistoryService {

    @Resource
    private HaaSignHistoryRepo haaSignHistoryRepo;

    @Override
    public Page<HaaSignHistory> list(HaaSignHistoryQuery haaSignHistoryQuery) {
        Page<HaaSignHistory> signHistories;
        Pageable pageable;
        Specification<HaaSignHistory> haaSignHistorySpecification;
        if (null != haaSignHistoryQuery) {
            HaaSignHistory haaSignHistory = haaSignHistoryQuery.getHaaSignHistory();
            pageable = PageUtil.toPageable(haaSignHistoryQuery.getPager(), "signDate", "desc");
            haaSignHistorySpecification = (root, criteriaQuery, criteriaBuilder) -> {
                Predicate predicate = criteriaBuilder.conjunction();
                if (!ObjectUtils.isEmpty(haaSignHistory)) {
                    if (!StringUtils.isEmpty(haaSignHistory.getName())) {
                        assert predicate != null;
                        predicate.getExpressions().add(criteriaBuilder.equal(root.get("name").as(String.class), haaSignHistory.getName()));
                    }
                    if (!StringUtils.isEmpty(haaSignHistory.getSignDate())) {
                        LocalDate queryMonth = LocalDate.parse(haaSignHistory.getSignDate());
                        LocalDate start = queryMonth.with(TemporalAdjusters.firstDayOfMonth());
                        LocalDate end = queryMonth.with(TemporalAdjusters.lastDayOfMonth());
                        predicate.getExpressions().add(criteriaBuilder.greaterThanOrEqualTo(root.get("signDate").as(String.class), String.valueOf(start)));
                        predicate.getExpressions().add(criteriaBuilder.lessThanOrEqualTo(root.get("signDate").as(String.class), String.valueOf(end)));
                    }
                }
                return predicate;
            };
            assert pageable != null;
            signHistories = haaSignHistoryRepo.findAll(haaSignHistorySpecification, pageable);
        } else {
            signHistories = haaSignHistoryRepo.findAll(PageUtil.newPageable("signDate", "desc"));
        }
        return signHistories;
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

    @Override
    public List<String> listUser() {
        List<String> haaSignHistories = haaSignHistoryRepo.findAllGroupByName();
        log.info(haaSignHistories);
        return haaSignHistories;
    }

    @Override
    public List<String> listYear() {
        Set<String> years = new HashSet<>();
        List<String> allSignYears = haaSignHistoryRepo.findAllGroupBySignDate();
        allSignYears.forEach(signDate -> {
            String year = signDate.split("-")[0];
            years.add(year);
        });
        List<String> signDates = new ArrayList<>(years);
        log.info(years);
        return signDates;
    }

    @Override
    public List<String> listMonth() {
        Set<String> months = new HashSet<>();
        List<String> allSignMonths = haaSignHistoryRepo.findAllGroupBySignDate();
        allSignMonths.forEach(signDate -> {
            String month = signDate.split("-")[1];
            months.add(month);
        });
        List<String> signMonths = new ArrayList<>(months);
//        log.info(months);
        signMonths = signMonths.stream().sorted().collect(Collectors.toList());
        return signMonths;
    }

    @Override
    public SignDateRange listTimeRange() {
        SignDateRange signDateRange = new SignDateRange();
        List<String> allSignDates = haaSignHistoryRepo.findAllGroupBySignDate();
        List<String> sortedSignDates = allSignDates.stream().sorted().collect(Collectors.toList());
        signDateRange.setStartSingDate(sortedSignDates.get(0));
        signDateRange.setEndSingDate(sortedSignDates.get(sortedSignDates.size() - 1));
        return signDateRange;
    }

}
