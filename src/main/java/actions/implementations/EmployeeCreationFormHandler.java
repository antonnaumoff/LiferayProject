package actions.implementations;

import actions.Action;
import org.springframework.stereotype.Component;

import javax.portlet.*;
import java.io.IOException;

@Component(value = "createEmployee")
public class EmployeeCreationFormHandler implements Action {
    @Override
    public void execute(ActionRequest actionRequest, ActionResponse actionResponse, PortletContext portletContext) throws IOException, PortletException {

        actionRequest.setAttribute("id_dep", actionRequest.getParameter("id_dep"));
        actionResponse.setRenderParameter("action", "createEmployee");
    }

    @Override
    public void render(RenderRequest renderRequest, RenderResponse renderResponse, PortletContext portletContext) throws IOException, PortletException {
        portletContext.getRequestDispatcher("/jsp/employeeForm.jsp").forward(renderRequest, renderResponse);
    }
}
