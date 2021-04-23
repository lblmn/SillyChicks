package com.bing.monkey.haagendzs.entity.process;

import com.bing.monkey.haagendzs.entity.HaaOrgData;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class SignRes extends HaaOrgData implements Serializable {

    int signRes;

    public SignRes(HaaOrgData haaOrgData, int signRes) {
        super(haaOrgData.getId(), haaOrgData.getUnionId(), haaOrgData.getUid(), haaOrgData.getName(), haaOrgData.getMemo(), haaOrgData.getCreateTime(), haaOrgData.getModifyTime());
        this.signRes = signRes;
    }

}
