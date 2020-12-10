package com.icbms.web.controller.device;

import com.icbms.common.util.PageUtils;
import com.icbms.common.util.Query;
import com.icbms.common.util.Result;
import com.icbms.core.annotation.SysLog;
import com.icbms.core.service.deviceinfo.DeviceBoxInfoService;
import com.icbms.core.service.devicelog.DeviceAlarmInfoLogService;
import com.icbms.core.service.kk.KkService;
import com.icbms.core.util.UserUtils;
import com.icbms.repository.domain.deviceinfo.DeviceBoxInfoEntity;
import com.icbms.repository.domain.sys.UserEntity;
import com.icbms.web.controller.BaseController;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * 电箱设备基础表; InnoDB free: 401408 kB
 *
 * @author Raymond
 * @email rui.sun.java@gmail.com
 * @date 2018-03-13 15:57:43
 */
@RestController
@RequestMapping("deviceboxinfo")
public class DeviceBoxInfoController extends BaseController {
    @Autowired
    private DeviceBoxInfoService deviceBoxInfoService;

    @Autowired
    private DeviceAlarmInfoLogService deviceAlarmInfoLogService;

    @Autowired
    private KkService kkService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("deviceboxinfo:list")
    @SysLog("电箱查看")
    public Result list(@RequestParam Map<String, Object> params) {
        params.put("projectId", this.getCurrentProjectId());
        Query query = new Query(params);
        List<DeviceBoxInfoEntity> deviceBoxInfoList = deviceBoxInfoService.queryList(query);
        int total = deviceBoxInfoService.queryTotal(query);
        PageUtils pageUtil = new PageUtils(deviceBoxInfoList, total, query.getLimit(), query.getPage());
        return Result.ok().put("page", pageUtil);
    }

    @RequestMapping("/deviceView")
    @SysLog("设备状态查看")
    public Result deviceView(@RequestParam Map<String, Object> params) {
        params.put("projectId", this.getCurrentProjectId());
        Query query = new Query(params);
        List<DeviceBoxInfoEntity> deviceBoxInfoList = deviceBoxInfoService.queryList(query);
        int total = deviceBoxInfoService.queryTotal(query);
        PageUtils pageUtil = new PageUtils(deviceBoxInfoList, total, query.getLimit(), query.getPage());
        try {
            this.kkService.processDeviceBoxOnline((List<DeviceBoxInfoEntity>) pageUtil.getList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.ok().put("page", pageUtil);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("deviceboxinfo:info")
    public Result info(@PathVariable("id") String id) {
        DeviceBoxInfoEntity deviceBoxInfo = deviceBoxInfoService.queryObject(id);
        return Result.ok().put("deviceBoxInfo", deviceBoxInfo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("deviceboxinfo:save")
    @SysLog("电箱保存")
    public Result save(@RequestBody DeviceBoxInfoEntity deviceBoxInfo) {
        deviceBoxInfoService.save(deviceBoxInfo);
        return Result.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("deviceboxinfo:update")
    @SysLog("电箱修改")
    public Result update(@RequestBody DeviceBoxInfoEntity deviceBoxInfo) {
        deviceBoxInfoService.update(deviceBoxInfo);
        return Result.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("deviceboxinfo:delete")
    @SysLog("电箱删除")
    public Result delete(@RequestBody String[] ids) {
        deviceBoxInfoService.deleteBatch(ids, null);
        return Result.ok();
    }

    /**
     * 根据位置ID选择电箱
     */
    @RequestMapping("/findDeviceBoxsInfoByLId")
    @RequiresPermissions("deviceboxinfo:list")
    public Result findDeviceBoxsInfoByLId(@RequestBody String locationId) {
        List<DeviceBoxInfoEntity> deviceBoxs = deviceBoxInfoService.findDeviceBoxsInfoByLId(this.getCurrentProjectId(),
                locationId);
        return Result.ok().put("deviceBoxs", deviceBoxs);
    }

    @RequestMapping("/findMapDeviceBoxsInfo")
    public Result findDeviceBoxsInfoForMap(String locationId, String showBoxOnline) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        String xyControl = request.getParameter("xyControl");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("projectId", this.getCurrentProjectId());
        params.put("locationId", locationId);
        params.put("page", "1");
        params.put("limit", "99999");
        if ("true".equals(xyControl)) {
            params.put("xyControl", xyControl);
        }
        Query query = new Query(params);
        List<DeviceBoxInfoEntity> deviceBoxInfoList = deviceBoxInfoService.queryList(query);
        if ("true".equals(showBoxOnline)) {
            try {
                this.kkService.processDeviceBoxOnline(deviceBoxInfoList);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return Result.ok().put("deviceBoxs", deviceBoxInfoList);
    }

    @RequestMapping("/setDeboxInfoXy")
    @SysLog("设置电箱拓扑图位置")
    public Result setDeboxInfoXy(String deviceBoxInfoId, String fx, String fy) {
        DeviceBoxInfoEntity deviceBoxInfo = deviceBoxInfoService.queryObject(deviceBoxInfoId);
        deviceBoxInfo.setFx(fx);
        deviceBoxInfo.setFy(fy);
        deviceBoxInfoService.update(deviceBoxInfo);
        return Result.ok().put("deviceBoxInfo", deviceBoxInfo);
    }

    @RequestMapping("/resetXy")
    @SysLog("重置电箱拓扑图位置")
    public Result resetXy(String deviceBoxInfoId, String fx, String fy) {
        DeviceBoxInfoEntity deviceBoxInfo = deviceBoxInfoService.queryObject(deviceBoxInfoId);
        deviceBoxInfo.setFx(null);
        deviceBoxInfo.setFy(null);
        deviceBoxInfoService.xyReset(deviceBoxInfo);
        return Result.ok().put("deviceBoxInfo", deviceBoxInfo);
    }

    @RequestMapping(value = "/batchImpDevices", method = RequestMethod.POST)
    @SysLog("批量导入电箱设备")
    public List<Map<String, String>> batchImpDevices(@RequestParam("file") MultipartFile file) {
        List<Map<String, String>> result = new ArrayList<>();
        try {
            InputStream input = file.getInputStream();
            Workbook wb = new HSSFWorkbook(input);
            Sheet sheet = wb.getSheetAt(0);
            int rowNum = sheet.getLastRowNum() + 1;
            Map<String, String> map = null;
            for (int i = 1; i < rowNum; i++) {
                Row row = sheet.getRow(i);
                // 一级位置
                Cell firstClssLocCell = row.getCell(0);
                firstClssLocCell.setCellType(Cell.CELL_TYPE_STRING);
                String firstLoc = firstClssLocCell.getStringCellValue();
                // 二级位置
                Cell secClssLocCell = row.getCell(1);
                secClssLocCell.setCellType(Cell.CELL_TYPE_STRING);
                String secLoc = secClssLocCell.getStringCellValue();
                // 三级位置
                Cell thirdClssLocCell = row.getCell(2);
                thirdClssLocCell.setCellType(Cell.CELL_TYPE_STRING);
                String thirdLoc = thirdClssLocCell.getStringCellValue();
                // 四级位置
                Cell forthClssLocCell = row.getCell(3);
                forthClssLocCell.setCellType(Cell.CELL_TYPE_STRING);
                String forthLoc = forthClssLocCell.getStringCellValue();
                // 五级位置
                Cell fifthClssLocCell = row.getCell(4);
                fifthClssLocCell.setCellType(Cell.CELL_TYPE_STRING);
                String fifthLoc = fifthClssLocCell.getStringCellValue();
                // 展位号
                Cell standNoCell = row.getCell(5);
                standNoCell.setCellType(Cell.CELL_TYPE_STRING);
                String standNo = standNoCell.getStringCellValue();
                // 电箱MAC
                Cell deviceMacCell = row.getCell(6);
                deviceMacCell.setCellType(Cell.CELL_TYPE_STRING);
                String deviceMac = deviceMacCell.getStringCellValue();

                // 二级电箱网关号
                Cell secBoxGatewayCell = row.getCell(7);
                secBoxGatewayCell.setCellType(Cell.CELL_TYPE_STRING);
                String secBoxGateway = secBoxGatewayCell.getStringCellValue();

                // 电箱容量
                Cell boxCapacityCell = row.getCell(8);
                boxCapacityCell.setCellType(Cell.CELL_TYPE_STRING);
                String boxCapacity = boxCapacityCell.getStringCellValue();

                // 是否受控
                Cell controlFlagCell = row.getCell(9);
                controlFlagCell.setCellType(Cell.CELL_TYPE_STRING);
                String controlFlag = controlFlagCell.getStringCellValue();
                // 备注
                Cell remarkCell = row.getCell(10);
                remarkCell.setCellType(Cell.CELL_TYPE_STRING);
                String remark = remarkCell.getStringCellValue();

                map = new LinkedHashMap<>();
                map.put("firstLoc", firstLoc);
                map.put("secLoc", secLoc);
                map.put("thirdLoc", thirdLoc);
                map.put("forthLoc", forthLoc);
                map.put("fifthLoc", fifthLoc);
                map.put("deviceMac", deviceMac);
                map.put("remark", remark);
                map.put("secBoxGateway", secBoxGateway);
                map.put("standNo", standNo);
                map.put("boxCapacity", boxCapacity);
                map.put("controlFlag", controlFlag);
                result.add(map);
            }
            if (map != null && map.size() > 0) {
                UserEntity currentUser = UserUtils.getCurrentUser();
                deviceBoxInfoService.saveBoxLocBatch(result, super.getCurrentProjectId(), currentUser);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
