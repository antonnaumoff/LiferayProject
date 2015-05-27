package actions.implementations;

import actions.Action;
import models.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.DataService;
import utils.exceptions.DataBaseException;
import utils.portalInstruments.URLMaker;
import utils.validators.OvalValidator;

import javax.portlet.*;
import java.io.IOException;
import java.util.Map;

@Component(value="validateEditing")
public class DepartmentEditingValidation implements Action {

    @Autowired
    private DataService dataService;

    @Override
    public void execute(ActionRequest actionRequest, ActionResponse actionResponse, PortletContext portletContext) throws IOException, PortletException {
        Department dep = new Department();
        int id = Integer.parseInt(actionRequest.getParameter("id_dep"));
        String title = actionRequest.getParameter("title").trim();
        dep.setId(id);
        dep.setTitle(title);
        Map<String, String> messages = OvalValidator.validate(dep);
        if (messages.isEmpty()) {
            try {
                dataService.editDepartment(dep);
            } catch (DataBaseException e) {
                actionRequest.setAttribute("message", "Some problem with database, please, try later");
                actionResponse.setRenderParameter("action", "error");
            }
            PortletURL redirectURL = URLMaker.getRenderUrl(actionRequest);
            redirectURL.setParameter("action", "departmentList");
            actionResponse.sendRedirect(redirectURL.toString());

        } else {
            actionRequest.setAttribute("message", messages.get("title"));
            actionRequest.setAttribute("department", dep);
            actionResponse.setRenderParameter("action", "editDepartment");
        }
    }

    @Override
    public void render(RenderRequest renderRequest, RenderResponse renderResponse, PortletContext portletContext) throws IOException, PortletException {

    }
}
