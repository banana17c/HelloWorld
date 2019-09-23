package com.web.entry.dubbo.consumers;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;


import com.ztesoft.zsmart.bss.drm.app.gis.model.req.ConsumePosmRequest;
import com.ztesoft.zsmart.bss.drm.app.gis.model.req.PlanDetailActInstAttrRequest;
import com.ztesoft.zsmart.bss.drm.app.gis.model.req.PlanDetailActInstBusiLimitRequest;
import com.ztesoft.zsmart.bss.drm.app.gis.model.req.PlanDetailActInstRequest;
import com.ztesoft.zsmart.bss.drm.app.gis.model.req.PlanDetailActInstSubmitRequest;
import com.ztesoft.zsmart.bss.drm.app.gis.model.req.PlanDetailInstSignInRequest;
import com.ztesoft.zsmart.bss.drm.app.gis.model.req.PlanDetailInstSubmitRequest;
import com.ztesoft.zsmart.bss.drm.app.gis.model.req.PlanInstOperateRequest;
import com.ztesoft.zsmart.bss.drm.app.gis.model.req.PlanInstRequest;
import com.ztesoft.zsmart.bss.drm.app.gis.model.req.RealtimePathRequest;
import com.ztesoft.zsmart.bss.drm.app.gis.model.req.ReportStaffLocationRequest;
import com.ztesoft.zsmart.bss.drm.app.gis.model.rsp.ActIncReasonResponse;
import com.ztesoft.zsmart.bss.drm.app.gis.model.rsp.ConsumePosmResponse;
import com.ztesoft.zsmart.bss.drm.app.gis.model.rsp.PlanDetailActInstAttrResponse;
import com.ztesoft.zsmart.bss.drm.app.gis.model.rsp.PlanDetailActInstBusiLimitResponse;
import com.ztesoft.zsmart.bss.drm.app.gis.model.rsp.PlanDetailActInstResponse;
import com.ztesoft.zsmart.bss.drm.app.gis.model.rsp.PlanDetailActInstSubmitResponse;
import com.ztesoft.zsmart.bss.drm.app.gis.model.rsp.PlanDetailInstRsnListResponse;
import com.ztesoft.zsmart.bss.drm.app.gis.model.rsp.PlanDetailInstSignInResponse;
import com.ztesoft.zsmart.bss.drm.app.gis.model.rsp.PlanDetailInstSubmitResponse;
import com.ztesoft.zsmart.bss.drm.app.gis.model.rsp.PlanInstOperateResponse;
import com.ztesoft.zsmart.bss.drm.app.gis.model.rsp.PlanInstResponse;
import com.ztesoft.zsmart.bss.drm.app.gis.model.rsp.RealtimePathResponse;
import com.ztesoft.zsmart.bss.drm.app.gis.model.rsp.ReportStaffLocationResponse;
import com.ztesoft.zsmart.bss.drm.app.gis.services.DrmSalesPlanDubooService;
import com.ztesoft.zsmart.core.exception.BaseAppException;

/**
 * <Description> personalWeb <br>
 *
 * @author chen.guangwen <br>
 * @CreateDate 2019/8/7 <br>
 */
@Component
public class IwhaleCloudDubboService implements DrmSalesPlanDubooService {

    @Reference
    private DrmSalesPlanDubooService drmSalesPlanDubooService;

    @Override
    public PlanInstResponse qryPlanInstList(PlanInstRequest planInstRequest) {
        return drmSalesPlanDubooService.qryPlanInstList(planInstRequest);
    }

    @Override
    public PlanDetailActInstResponse qryPlanDetailActInst(PlanDetailActInstRequest planDetailActInstRequest) {
        return null;
    }

    @Override
    public PlanDetailActInstAttrResponse qryPlanDetailActInstAttr(PlanDetailActInstAttrRequest planDetailActInstAttrRequest) {
        return null;
    }

    @Override
    public ReportStaffLocationResponse reportStaffLocation(ReportStaffLocationRequest reportStaffLocationRequest) throws BaseAppException {
        return null;
    }

    @Override
    public PlanDetailInstSignInResponse planDetailInstSignIn(PlanDetailInstSignInRequest planDetailInstSignInRequest) {
        return null;
    }

    @Override
    public ActIncReasonResponse qryActIncReason() {
        return drmSalesPlanDubooService.qryActIncReason();
    }

    @Override
    public PlanDetailInstRsnListResponse qryPlanDetailInstRsnList() {
        return null;
    }

    @Override
    public PlanDetailActInstSubmitResponse planDetailActInstSubmit(PlanDetailActInstSubmitRequest planDetailActInstSubmitRequest) throws BaseAppException {
        return null;
    }

    @Override
    public PlanDetailInstSubmitResponse planDetailInstSubmit(PlanDetailInstSubmitRequest planDetailInstSubmitRequest) throws BaseAppException {
        return null;
    }

    @Override
    public PlanInstOperateResponse planInstOperate(PlanInstOperateRequest planInstOperateRequest) throws BaseAppException {
        return null;
    }

    @Override
    public PlanDetailActInstBusiLimitResponse qryPlanDetailActInstBusiLimit(PlanDetailActInstBusiLimitRequest planDetailActInstBusiLimitRequest) {
        return null;
    }

    @Override
    public ConsumePosmResponse consumePosm(ConsumePosmRequest consumePosmRequest) throws BaseAppException {
        return null;
    }

    @Override
    public RealtimePathResponse qryRealtimePath(RealtimePathRequest realtimePathRequest) {
        return null;
    }
}
