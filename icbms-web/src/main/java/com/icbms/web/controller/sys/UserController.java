package com.icbms.web.controller.sys;

import com.icbms.common.constant.Constant;
import com.icbms.common.util.PageUtils;
import com.icbms.common.util.Query;
import com.icbms.common.util.Result;
import com.icbms.core.util.ShiroUtils;
import com.icbms.core.annotation.DataAuth;
import com.icbms.core.annotation.SysLog;
import com.icbms.core.service.sys.NoticeService;
import com.icbms.core.service.sys.UserRoleService;
import com.icbms.core.service.sys.UserService;
import com.icbms.core.service.activity.ActModelerService;
import com.icbms.core.service.projectinfo.ProjectInfoService;
import com.icbms.repository.domain.projectinfo.ProjectInfoEntity;
import com.icbms.repository.domain.sys.UserEntity;
import com.icbms.web.controller.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 系统用户表
 *
 * @author admin
 * @email rui.sun.java@gmail.com
 * @date 2017-05-03 09:41:38
 */
@CrossOrigin
@RestController
@RequestMapping("sys/user")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private ActModelerService actModelerService;

    @Autowired
    private ProjectInfoService projectInfoService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:user:list")
    @SysLog("查看系统用户列表")
    @DataAuth(tableAlias = "s")
    public Result list(@RequestParam Map<String, Object> params) {
        // 查询列表数据
        Query query = new Query(params);
        List<UserEntity> userList = userService.queryList(query);
        int total = userService.queryTotal(query);
        PageUtils pageUtil = new PageUtils(userList, total, query.getLimit(), query.getPage());
        return Result.ok().put("page", pageUtil);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:user:info")
    @SysLog("查看系统用户信息")
    public Result info(@PathVariable("id") String id) {
        UserEntity user = userService.queryObject(id);
        if (user != null) {
            user.setPassWord("");
            user.setRoleIdList(userRoleService.queryRoleIdList(user.getId()));
        }
        return Result.ok().put("user", user);
    }

    /**
     *
     * 主页用户信息
     */
    @RequestMapping("/info")
    public Result info() {
        UserEntity user = userService.queryObject(ShiroUtils.getUserId());
        String projectIds = user.getProjectIds();
        List<ProjectInfoEntity> projectList = new ArrayList<ProjectInfoEntity>();
        if (StringUtils.isNotBlank(projectIds)) {
            for (String projectId : projectIds.split(",")) {
                ProjectInfoEntity project = projectInfoService.queryObject(projectId);
                projectList.add(project);
            }
        }
        ProjectInfoEntity currentProject = this.getCurrentProject();
        if (null == currentProject && projectList.size() > 0) {
            currentProject = projectList.get(0);
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                    .getRequest();
            request.getSession().setAttribute("currentProject", currentProject);
        }
        return Result.ok().put("user", user).put("projectList", projectList).put("currentProject", currentProject);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:user:update")
    @SysLog("新增系统用户")
    public Result save(@RequestBody UserEntity user) {
        userService.save(user);
        return Result.ok();
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/update")
    @RequiresPermissions("sys:user:update")
    @SysLog("修改系统用户")
    public Result update(@RequestBody UserEntity user) {
        user.setPassWord(null);
        userService.update(user);
        return Result.ok();
    }

    /**
     * 修改密码
     */
    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    @SysLog("用户修改密码")
    public Result updatePassword(UserEntity user) {
        int i = userService.updatePassword(user);
        if (i < 1) {
            return Result.error("更改密码失败");
        }
        return Result.ok("更改密码成功");
    }

    /**
     * 切换项目
     */
    @RequestMapping(value = "/setCurrentProject", method = RequestMethod.POST)
    public Result setCurrentProject(String currentProjectId) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        ProjectInfoEntity currentProject = projectInfoService.queryObject(currentProjectId);
        request.getSession().setAttribute("currentProject", currentProject);
        return Result.ok("项目切换成功");
    }

    /**
     * 禁用、
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:user:delete")
    @SysLog("禁用系统用户")
    public Result delete(@RequestBody String[] ids) {
        userService.updateBatchStatus(ids, Constant.ABLE_STATUS.NO.getValue());
        return Result.ok();
    }

    /**
     * 启用、
     */
    @RequestMapping("/enabled")
    @RequiresPermissions("sys:user:enabled")
    @SysLog("启用系统用户")
    public Result enabled(@RequestBody String[] ids) {
        userService.updateBatchStatus(ids, Constant.ABLE_STATUS.YES.getValue());
        return Result.ok();
    }

    /**
     * 重置密码
     */
    @RequestMapping("/reset")
    @RequiresPermissions("sys:user:reset")
    @SysLog("重置密码")
    public Result reset(@RequestBody String[] ids) {
        userService.resetPassWord(ids);
        return Result.ok("重置密码成功,密码为:" + Constant.DEF_PASSWORD);
    }
}
