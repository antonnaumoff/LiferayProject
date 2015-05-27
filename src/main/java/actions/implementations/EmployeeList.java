package actions.implementations;

import actions.Action;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.DataService;
import utils.exceptions.DataBaseException;

import javax.portlet.*;
import java.io.IOException;
import java.util.List;

@Component(value = "listEmployees")
public class EmployeeList implements Action {

    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private DataService dataService;

    @Override
    public void execute(ActionRequest actionRequest, ActionResponse actionResponse, PortletContext portletContext) throws IOException, PortletException {
        int id_dep = Integer.parseInt(actionRequest.getParameter("id_dep"));
        actionRequest.setAttribute("id_dep", id_dep);
        logger.info("Execute listEmployees with parameter id_dep: "+id_dep);
        actionResponse.setRenderParameter("action", "listEmployees");
    }

    @Override
    public void render(RenderRequest renderRequest, RenderResponse renderResponse, PortletContext portletContext) throws IOException, PortletException {
        List emp = null;
        int id_dep = (Integer) renderRequest.getAttribute("id_dep");
        try {
            emp = dataService.getEmloyeeListById(id_dep);
        } catch (DataBaseException e) {
            renderRequest.setAttribute("message", "Some problem with database, please, try later");
            logger.error("Exception while database operation "+e.getMessage());
            portletContext.getRequestDispatcher("/jsp/error.jsp").forward(renderRequest, renderResponse);

        } catch (Exception e) {
            logger.error("Exception while database operation "+e.getMessage());
        }
        renderRequest.setAttribute("id_dep", id_dep);
        renderRequest.setAttribute("list", emp);
        logger.info("Render listEmployees with parameter id_dep: "+id_dep+" and number of Employees: "+ emp.size());
        portletContext.getRequestDispatcher("/jsp/employeeList.jsp").forward(renderRequest, renderResponse);
    }
}
