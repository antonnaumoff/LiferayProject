package actions.implementations;

import actions.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.DataService;
import utils.exceptions.DataBaseException;
import utils.portalInstruments.URLMaker;

import javax.portlet.*;
import java.io.IOException;

@Component(value = "deleteDepartment")
public class DepartmentDeletingHandler implements Action {

    @Autowired
    private DataService dataService;

    @Override
    public void execute(ActionRequest actionRequest, ActionResponse actionResponse, PortletContext portletContext) throws IOException, PortletException {

        int id = Integer.parseInt(actionRequest.getParameter("id_dep"));

            try {
                dataService.deleteDepartment(id);
            } catch (DataBaseException e) {
                actionRequest.setAttribute("message", "Some problem with database, please, try later");
                actionResponse.setRenderParameter("action", "error");
            }
        PortletURL redirectURL = URLMaker.getRenderUrl(actionRequest);
        redirectURL.setParameter("action", "departmentList");
        actionResponse.sendRedirect(redirectURL.toString());

    }

    @Override
    public void render(RenderRequest renderRequest, RenderResponse renderResponse, PortletContext portletContext) throws IOException, PortletException {

    }
}
