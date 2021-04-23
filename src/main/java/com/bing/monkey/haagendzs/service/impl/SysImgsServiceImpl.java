package com.bing.monkey.haagendzs.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.bing.monkey.common.util.JPAUtils;
import com.bing.monkey.common.util.PageUtil;
import com.bing.monkey.common.util.ToolkitUtil;
import com.bing.monkey.haagendzs.entity.SysImgs;
import com.bing.monkey.haagendzs.entity.query.SysImgsQuery;
import com.bing.monkey.haagendzs.exceptions.SysImgsException;
import com.bing.monkey.haagendzs.exceptions.enums.SysImgsExceptionEnum;
import com.bing.monkey.haagendzs.repositories.SysImgsRepo;
import com.bing.monkey.haagendzs.service.SysImgsService;
import lombok.extern.log4j.Log4j2;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 *
 */
@Service
@Log4j2
public class SysImgsServiceImpl implements SysImgsService {

    @Resource
    private SysImgsRepo sysImgsRepo;

    @Resource
    private JPAUtils jpaUtils;

    @Override
    public Page<SysImgs> list(SysImgsQuery sysImgsQuery) {
        SysImgs sysImgs = sysImgsQuery.getSysImgs();
        Pageable pageable = PageUtil.toPageable(sysImgsQuery.getPager());
//        Specification<SysImgs> sysImgsSpecification = (Specification<SysImgs>) (root, criteriaQuery, criteriaBuilder) -> {
//            Predicate predicate = null;
//            if (!StringUtils.isEmpty(sysImgs.getMemo())) {
        //                predicate = criteriaBuilder.like(root.get("memo").as(String.class), "%" + sysImgs.getMemo() + "%");
//            predicate = criteriaBuilder.equal(root.get("memo").as(String.class), sysImgs.getMemo());
//            }
//            return predicate;
//        };
//        return sysImgsRepo.findAll(sysImgsSpecification, pageable);
        return sysImgsRepo.findAll(pageable);
    }

    @Override
    public SysImgs add(SysImgs target) {
        SysImgs _return;
        if (StringUtils.isEmpty(target.getId())) {
            _return = sysImgsRepo.save(target);
        } else {
            if (sysImgsRepo.existsById(target.getId())) {
                SysImgs source = sysImgsRepo.getOne(target.getId());
                ToolkitUtil.copyNonNullProperties(target, source);
                _return = sysImgsRepo.save(source);
            } else {
                throw new SysImgsException(SysImgsExceptionEnum.NO_OBJECT_AS_THIS);
            }
        }
        return _return;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public boolean delete(String[] ids) {
        if (ids.length == 0) {
            throw new SysImgsException(SysImgsExceptionEnum.EMPTY_PARAMS);
        } else {
            for (String id :
                    ids) {
                if (sysImgsRepo.existsById(Integer.valueOf(id))) {
                    sysImgsRepo.deleteById(Integer.valueOf(id));
                } else {
                    throw new SysImgsException(SysImgsExceptionEnum.NO_OBJECT_AS_THIS);
                }
            }
        }
        return true;
    }

    @Override
    public void requestAndSaveImg() {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("https://api.blogbig.cn/random/api.php?return=json")
                .method("GET", null)
                .build();
        try {
            Response response = client.newCall(request).execute();
            JSONObject info = JSONObject.parseObject(Objects.requireNonNull(response.body()).string());
            String acgurl = info.getString("acgurl");
            List<SysImgs> allByAcgurl = sysImgsRepo.findAllByAcgurl(acgurl);
            if (allByAcgurl.size() == 0) {
                SysImgs sysImgs = new SysImgs();
                sysImgs.setAcgurl(acgurl);
                sysImgs.setCode(info.getString("code"));
                sysImgs.setHeight(info.getString("height"));
                sysImgs.setWidth(info.getString("width"));
                sysImgs.setSize(info.getString("size"));
                add(sysImgs);
            } else {
                log.info("已经存在该acgurl记录");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public SysImgs getRandomImg() {
        List<SysImgs> sysImgs = jpaUtils.runNativeSql("select * from sys_imgs order by rand() limit 1;", SysImgs.class);
        return sysImgs.get(0);
    }

}
