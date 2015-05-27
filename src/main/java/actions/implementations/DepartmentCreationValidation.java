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

@Component(value = "validateCreation")
public class DepartmentCreationValidation implements Action {

    @Autowired
    private DataService dataService;

    @Override
    public void execute(ActionRequest actionRequest, ActionResponse actionResponse, PortletContext portletContext) throws IOException, PortletException {
        Department dep = new Department();
        String title = actionRequest.getParameter("title").trim();
        dep.setTitle(title);
        Map<String, String> messages = OvalValidator.validate(dep);
        if (messages.isEmpty()) {
            try {
                dataService.createDepartment(title);
            } catch (DataBaseException e) {
                actionRequest.setAttribute("message", "Some problem with database, please, try later");
                actionResponse.setRenderParameter("action", "error");
            }
            PortletURL redirectURL = URLMaker.getRenderUrl(actionRequest);
            redirectURL.setParameter("action", "departmentList");
            actionResponse.sendRedirect(redirectURL.toString());

//            ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute("LIFERAY_SHARED_THEME_DISPLAY");
//            String url = themeDisplay.getURLCurrent();
//            actionResponse.sendRedirect(url.substring(0, url.indexOf("action") + "action".length() + 1) + "departmentList");


        } else {
            actionRequest.setAttribute("message", messages.get("title"));
            dep.setId(0);
            actionRequest.setAttribute("department", dep);
            actionResponse.setRenderParameter("action", "createDepartment");
        }
    }

    @Override
    public void render(RenderRequest renderRequest, RenderResponse renderResponse, PortletContext portletContext) throws IOException, PortletException {
    }
}

