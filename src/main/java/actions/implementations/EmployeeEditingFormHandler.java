package actions.implementations;

import actions.Action;
import models.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.DataService;
import utils.exceptions.DataBaseException;

import javax.portlet.*;
import java.io.IOException;

@Component(value = "editEmployee")
public class EmployeeEditingFormHandler implements Action {

    @Autowired
    private DataService dataService;

    @Override
    public void execute(ActionRequest actionRequest, ActionResponse actionResponse, PortletContext portletContext) throws IOException, PortletException {
        Employee emp = null;
        int id = Integer.parseInt(actionRequest.getParameter("id"));
        try {
            emp = dataService.getEmloyeeById(id);
        } catch (DataBaseException e) {
            actionRequest.setAttribute("message", "Some problem with database, please, try later");
            actionResponse.setRenderParameter("action", "error");
        }
        actionRequest.setAttribute("editor",1);
        actionRequest.setAttribute("id_dep", emp.getDep_id());
        actionRequest.setAttribute("emp", emp);
        actionResponse.setRenderParameter("action", "editEmployee");

    }

    @Override
    public void render(RenderRequest renderRequest, RenderResponse renderResponse, PortletContext portletContext) throws IOException, PortletException {
        portletContext.getRequestDispatcher("/jsp/employeeForm.jsp").forward(renderRequest, renderResponse);
    }
}
