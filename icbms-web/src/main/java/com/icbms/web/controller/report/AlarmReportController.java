package com.icbms.web.controller.report;

import com.icbms.common.excel.JxlsExcelView;
import com.icbms.common.util.PageUtils;
import com.icbms.common.util.Query;
import com.icbms.common.util.Result;
import com.icbms.core.annotation.SysLog;
import com.icbms.core.service.report.AlarmReportService;
import com.icbms.core.service.sys.CodeService;
import com.icbms.repository.domain.report.AlarmInfoReportEntity;
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
@RequestMapping("/report/alarm")
public class AlarmReportController extends BaseController {

    @Autowired
    private AlarmReportService alarmReportService;

    @Autowired
    private CodeService codeService;

    /**
     * 列表
     */
    @ResponseBody
    @RequestMapping("/list")
    @RequiresPermissions("report:alarm:list")
    @SysLog("告警报表")
    public Result list(@RequestParam Map<String, Object> params) {
        // 查询列表数据
        params.put("projectId", this.getCurrentProjectId());
        params.put("alarmStatus", "1");
        Query query = new Query(params);

        List<AlarmInfoReportEntity> deviceAlarmInfoLogList = alarmReportService.queryList(query);
        if (null != deviceAlarmInfoLogList && 0 < deviceAlarmInfoLogList.size()) {
            Map<String, String> levelMap = this.codeService.queryChildsMapByMark("alarm_level");

            for (AlarmInfoReportEntity alarm : deviceAlarmInfoLogList) {
                if (levelMap.containsKey(alarm.getAlarmLevel())) {
                    alarm.setAlarmLevelName(levelMap.get(alarm.getAlarmLevel()));
                }
            }
        }
        int total = alarmReportService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(deviceAlarmInfoLogList, total, query.getLimit(), query.getPage());

        return Result.ok().put("page", pageUtil);
    }

    /**
     * 导出全量表
     */
    @RequestMapping("/exportAll")
    @SysLog("告警报表导出")
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
        params.put("alarmStatus", "1");
        // Query query = new Query(params);
        List<AlarmInfoReportEntity> deviceAlarmInfoLogList = alarmReportService.queryList(params);
        if (null != deviceAlarmInfoLogList && 0 < deviceAlarmInfoLogList.size()) {
            Map<String, String> levelMap = this.codeService.queryChildsMapByMark("alarm_level");

            for (AlarmInfoReportEntity alarm : deviceAlarmInfoLogList) {
                if (levelMap.containsKey(alarm.getAlarmLevel())) {
                    alarm.setAlarmLevelName(levelMap.get(alarm.getAlarmLevel()));
                }
            }
        }
        Map<String, Object> model = new HashMap<>();
        model.put("alarmList", deviceAlarmInfoLogList);
        return new ModelAndView(new JxlsExcelView("alarmAll.xlsx", "告警记录.xlsx"), model);
    }
}
