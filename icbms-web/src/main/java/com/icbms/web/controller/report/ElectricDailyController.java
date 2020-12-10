package com.icbms.web.controller.report;

import com.icbms.common.util.DateUtils;
import com.icbms.common.util.PageUtils;
import com.icbms.common.util.Query;
import com.icbms.common.util.Result;
import com.icbms.core.annotation.SysLog;
import com.icbms.core.service.report.ElectricReportService;
import com.icbms.repository.domain.report.ElectricDailyEntity;
import com.icbms.web.controller.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author lkpc
 */
@RestController
@RequestMapping("/report/electricdaily")
public class ElectricDailyController extends BaseController {

    @Autowired
    private ElectricReportService electricReportService;

    /**
     * 列表
     */
    @ResponseBody
    @RequestMapping("/list")
    @RequiresPermissions("report:electricdaily:list")
    @SysLog("当日用电统计查询")
    public Result list(@RequestParam Map<String, Object> params) {
        params.put("projectId", this.getCurrentProjectId());
        params.put("statDate", DateUtils.format(new Date()));
        // 查询列表数据
        Query query = new Query(params);
        List<ElectricDailyEntity> list = electricReportService.queryDailyElectricCnt(query);
        int total = electricReportService.queryDailyElectricCntTotal(query);

        PageUtils pageUtil = new PageUtils(list, total, query.getLimit(), query.getPage());
        return Result.ok().put("page", pageUtil);
    }

}
