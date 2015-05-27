package actions.implementations;

import actions.Action;
import org.springframework.stereotype.Component;

import javax.portlet.*;
import java.io.IOException;

@Component(value="error")
public class ErrorHandler implements Action {
    @Override
    public void execute(ActionRequest actionRequest, ActionResponse actionResponse, PortletContext portletContext) throws IOException, PortletException {

    }

    @Override
    public void render(RenderRequest renderRequest, RenderResponse renderResponse, PortletContext portletContext) throws IOException, PortletException {
        portletContext.getRequestDispatcher("/jsp/error.jsp").forward(renderRequest,renderResponse);
    }
}
