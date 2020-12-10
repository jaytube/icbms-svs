package com.icbms.web.controller.report;

import com.icbms.common.excel.JxlsExcelView;
import com.icbms.common.util.PageUtils;
import com.icbms.common.util.Query;
import com.icbms.common.util.Result;
import com.icbms.core.annotation.SysLog;
import com.icbms.core.service.report.ElectricReportService;
import com.icbms.repository.domain.report.ElectricReportEntity;
import com.icbms.web.controller.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/report/electric")
public class ElectricReportController extends BaseController {
    @Autowired
    private ElectricReportService electricReportService;

    /**
     * 列表
     */
    @ResponseBody
    @RequestMapping("/list")
    @RequiresPermissions("report:electric:list")
    @SysLog("用电查询")
    public Result list(@RequestParam Map<String, Object> params) {
        params.put("projectId", this.getCurrentProjectId());
        // 查询列表数据
        Query query = new Query(params);
        List<ElectricReportEntity> list = electricReportService.queryList(query);
        int total = electricReportService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(list, total, query.getLimit(), query.getPage());
        return Result.ok().put("page", pageUtil);
    }

    /**
     * 导出全量表
     */
    @RequestMapping("/exportAll")
    @SysLog("用电报表导出")
    public ModelAndView export(@RequestParam(required = false) String beginTime,
                               @RequestParam(required = false) String endTime, HttpServletRequest request) {
        Map<String, Object> params = new HashMap<>();
        if (StringUtils.isNotBlank(beginTime)) {
            params.put("beginTime", beginTime);
        }
        if (StringUtils.isNotBlank(endTime)) {
            params.put("endTime", endTime);
        }

        // 查询列表数据
        params.put("projectId", this.getCurrentProjectId());
        List<ElectricReportEntity> list = electricReportService.queryList(params);

        Map<String, Object> model = new HashMap<>();
        model.put("electricList", list);
        return new ModelAndView(new JxlsExcelView("electricAll.xlsx", "电量统计.xlsx"), model);
    }
}
