package portlet;


import actions.Action;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.portlet.*;
import java.io.IOException;

public class Departments extends GenericPortlet {

    private Logger logger = Logger.getLogger(this.getClass());

    private ApplicationContext applicationContext;

    @Override
    public void init() throws PortletException {
        applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        super.init();
    }

    public void processAction(ActionRequest actionRequest, ActionResponse actionResponse)
            throws IOException, PortletException {

        String action = actionRequest.getParameter("action");
        if (action == null) {
            action = "departmentList";
        }
        logger.info("Action Phase initiated with action parameter: "+action);
        ((Action) applicationContext.getBean(action)).execute(actionRequest, actionResponse, getPortletContext());
    }

    public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
            throws IOException, PortletException {

        String action = renderRequest.getParameter("action");
        if (action == null) {
            action = "departmentList";
        }
        logger.info("Render Phase initiated with render parameter: "+action);
        ((Action) applicationContext.getBean(action)).render(renderRequest, renderResponse, getPortletContext());
    }
}


