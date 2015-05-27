package utils.portalInstruments;


import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletURLFactoryUtil;

import javax.portlet.*;

public class URLMaker {

    public static PortletURL getRenderUrl(ActionRequest actionRequest) throws WindowStateException, PortletModeException {
        String portletName = (String) actionRequest.getAttribute(WebKeys.PORTLET_ID);
        ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
        PortletURL portletURL = PortletURLFactoryUtil.create(PortalUtil.getHttpServletRequest(actionRequest), portletName,
                themeDisplay.getLayout().getPlid(), PortletRequest.ACTION_PHASE);
        portletURL.setPortletMode(PortletMode.VIEW);
        portletURL.setWindowState(WindowState.MAXIMIZED);
        return portletURL;
    }
}
