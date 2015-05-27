package actions;


import javax.portlet.*;
import java.io.IOException;

public interface Action {

    public void execute(ActionRequest actionRequest, ActionResponse actionResponse, PortletContext portletContext) throws IOException, PortletException;

    public void render(RenderRequest renderRequest, RenderResponse renderResponse, PortletContext portletContext) throws IOException, PortletException;

}
