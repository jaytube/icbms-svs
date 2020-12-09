package com.icbms.web.controller;

import com.icbms.core.util.ShiroUtils;
import com.icbms.core.service.sys.UserService;
import com.icbms.core.service.projectinfo.ProjectInfoService;
import com.icbms.core.util.UserUtils;
import com.icbms.repository.domain.projectinfo.ProjectInfoEntity;
import com.icbms.repository.domain.sys.UserEntity;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

public class BaseController {

    private static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    @Autowired
    private UserService userService;
    @Autowired
    private ProjectInfoService projectInfoService;

    /**
     * 获取当前登陆用户
     *
     * @return
     */
    public UserEntity getUser() {
        return UserUtils.getCurrentUser();
    }

    /**
     * 获取当前登陆用户Id
     *
     * @return
     */
    public String getUserId() {
        UserEntity user = getUser();
        if (null != user && null != user.getId()) {
            return user.getId();
        }
        return "";
    }

    public ProjectInfoEntity getCurrentProject() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        ProjectInfoEntity currentProject = (ProjectInfoEntity) request.getSession().getAttribute("currentProject");
        if(currentProject == null){
            setCurrProjectSession();
            currentProject = (ProjectInfoEntity) request.getSession().getAttribute("currentProject");
        }
        return currentProject;
    }

    public String getCurrentProjectId() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        ProjectInfoEntity currentProject = (ProjectInfoEntity) request.getSession().getAttribute("currentProject");
        if (null != currentProject) {
            return currentProject.getId();
        } else {
            setCurrProjectSession();
            currentProject = (ProjectInfoEntity) request.getSession().getAttribute("currentProject");
            return currentProject.getId();
        }
    }

    private void setCurrProjectSession(){
        UserEntity user = userService.queryObject(ShiroUtils.getUserId());
        String projectIds = user.getProjectIds();
        List<ProjectInfoEntity> projectList = new ArrayList<ProjectInfoEntity>();
        if (StringUtils.isNotBlank(projectIds)) {
            for (String projectId : projectIds.split(",")) {
                ProjectInfoEntity project = projectInfoService.queryObject(projectId);
                projectList.add(project);
            }
        }
        ProjectInfoEntity currentProject = null;
        if ( projectList.size() > 0) {
            currentProject = projectList.get(0);
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                    .getRequest();
            request.getSession().setAttribute("currentProject", currentProject);
        }
    }
}
