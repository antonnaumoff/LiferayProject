package actions.implementations;

import actions.Action;
import models.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.DataService;
import utils.exceptions.DataBaseException;
import utils.parsers.RequestParser;
import utils.portalInstruments.URLMaker;
import utils.validators.OvalValidator;

import javax.portlet.*;
import java.io.IOException;
import java.util.Map;

@Component(value = "validateEmpCreation")
public class EmployeeCreationValidation implements Action {

    @Autowired
    private DataService dataService;

    @Override
    public void execute(ActionRequest actionRequest, ActionResponse actionResponse, PortletContext portletContext) throws IOException, PortletException {
        Employee emp = createEmployeeFromView(actionRequest);
        Map<String, String> messages = OvalValidator.validate(emp);
        if (messages.isEmpty()) {
            try {
                dataService.createEmployee(emp);

            } catch (DataBaseException e) {
                actionRequest.setAttribute("message", "Some problem with database, please, try later");
                actionResponse.setRenderParameter("action", "error");
            }

            PortletURL redirectURL = URLMaker.getRenderUrl(actionRequest);
            redirectURL.setParameter("id_dep", String.valueOf(emp.getDep_id()));
            redirectURL.setParameter("action", "listEmployees");
            actionResponse.sendRedirect(redirectURL.toString());


        } else {
            emp.setId(0);
            actionRequest.setAttribute("id_dep", emp.getDep_id());
            actionRequest.setAttribute("messages", messages);
            actionRequest.setAttribute("emp", emp);
            actionResponse.setRenderParameter("action", "createEmployee");
        }
    }

    @Override
    public void render(RenderRequest renderRequest, RenderResponse renderResponse, PortletContext portletContext) throws IOException, PortletException {

    }

    private Employee createEmployeeFromView(ActionRequest actionRequest) {
        Employee emp = new Employee();
        emp.setJob_title(actionRequest.getParameter("job_title"));
        emp.setFirst_name(actionRequest.getParameter("first_name"));
        emp.setSecond_name(actionRequest.getParameter("second_name"));
        int salary = RequestParser.parseIntWithDefaultValue(actionRequest, "salary");
        emp.setSalary(salary);
        emp.setDate(RequestParser.parseDateDeafultValue(actionRequest, "date"));
        emp.setDep_id(Integer.parseInt(actionRequest.getParameter("id_dep")));
        emp.setId(0);
        return emp;
    }
}
