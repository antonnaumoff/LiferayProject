package actions.implementations;

import actions.Action;
import org.springframework.stereotype.Component;

import javax.portlet.*;
import java.io.IOException;

@Component("createDepartment")
public class DepartmentCreationFormHandler implements Action {
    @Override
    public void execute(ActionRequest actionRequest, ActionResponse actionResponse, PortletContext portletContext) throws IOException, PortletException {
    actionResponse.setRenderParameters(actionRequest.getParameterMap());

    }

    @Override
    public void render(RenderRequest renderRequest, RenderResponse renderResponse, PortletContext portletContext) throws IOException, PortletException {
        portletContext.getRequestDispatcher("/jsp/departmentForm.jsp").forward(renderRequest, renderResponse);

    }
}
