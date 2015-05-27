package actions.implementations;

import actions.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.DataService;
import utils.exceptions.DataBaseException;
import utils.portalInstruments.URLMaker;

import javax.portlet.*;
import java.io.IOException;

@Component(value="deleteEmployee")
public class EmployeeDeletingHandler implements Action {

    @Autowired
    private DataService dataService;
    @Override
    public void execute(ActionRequest actionRequest, ActionResponse actionResponse, PortletContext portletContext) throws IOException, PortletException {
        int id_dep = Integer.parseInt(actionRequest.getParameter("id_dep"));


        try {
            dataService.deleteById(Integer.parseInt(actionRequest.getParameter("id")));
        } catch (DataBaseException e) {
            actionRequest.setAttribute("message", "Some problem with database, please, try later");
            actionResponse.setRenderParameter("action", "error");
        }
        PortletURL redirectURL = URLMaker.getRenderUrl(actionRequest);
        redirectURL.setParameter("id_dep", String.valueOf(id_dep));
        redirectURL.setParameter("action", "listEmployees");
        actionResponse.sendRedirect(redirectURL.toString());
    }

    @Override
    public void render(RenderRequest renderRequest, RenderResponse renderResponse, PortletContext portletContext) throws IOException, PortletException {

    }
}
