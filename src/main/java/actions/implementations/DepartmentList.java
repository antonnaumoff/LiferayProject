package actions.implementations;


import actions.Action;
import models.Department;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.DataService;

import javax.portlet.*;
import java.io.IOException;
import java.util.List;

@Component("departmentList")
public class DepartmentList implements Action {

    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private DataService dataService;

    @Override
    public void execute(ActionRequest actionRequest, ActionResponse actionResponse, PortletContext portletContext) throws IOException, PortletException {

    }

    public void render(RenderRequest renderRequest, RenderResponse renderResponse, PortletContext portletContext)
            throws IOException, PortletException {
        List<Department> list = null;

        try {
            list = dataService.getDepartmentList();
        } catch (Exception e) {
            renderRequest.setAttribute("message", "Some problem with database, please, try later");
            logger.error("Exception while database operation "+e.getMessage());
            portletContext.getRequestDispatcher("/jsp/error.jsp").include(renderRequest, renderResponse);
        }
        if (list != null) {
            renderRequest.setAttribute("department", list);
            logger.info("Successfull departmentlist pick. Number of departments: "+list.size());
            portletContext.getRequestDispatcher("/jsp/departmentList.jsp").include(renderRequest, renderResponse);
        }
    }
}
