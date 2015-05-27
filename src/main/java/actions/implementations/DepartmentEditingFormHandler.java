package actions.implementations;

import actions.Action;
import models.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.DataService;
import utils.exceptions.DataBaseException;

import javax.portlet.*;
import java.io.IOException;


@Component(value = "editDepartment")
public class DepartmentEditingFormHandler implements Action {

    @Autowired
    private DataService dataService;

    @Override
    public void execute(ActionRequest actionRequest, ActionResponse actionResponse, PortletContext portletContext) throws IOException, PortletException {
     Department dep = null;
        try {
            dep = dataService.getDepartmentById(Integer.parseInt(actionRequest.getParameter("id_dep")));
        } catch (DataBaseException e) {
            actionRequest.setAttribute("message", "Some problem with database, please, try later");
            actionResponse.setRenderParameter("action", "error");
        }
        actionRequest.setAttribute("department", dep);
        actionResponse.setRenderParameter("action", "editDepartment");
    }
    @Override
    public void render(RenderRequest renderRequest, RenderResponse renderResponse, PortletContext portletContext) throws IOException, PortletException {
        portletContext.getRequestDispatcher("/jsp/departmentForm.jsp").forward(renderRequest, renderResponse);
    }
}
