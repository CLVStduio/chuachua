package cn.clvstudio.game.chuachua.interceptor;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


/**
 * @author Darnell
 *
 */
public class GlobalInterceptor extends HandlerInterceptorAdapter {
	
	private static final  Logger LOG = LoggerFactory.getLogger(GlobalInterceptor.class);
    
    @SuppressWarnings("unused")
	private static final String LOGIN_PATH = "/login";

    private static Set<String> WHITE_LIST = new HashSet<String>();

    static {
    	
    }

    @SuppressWarnings("unused")
	private Boolean checkInWhiteList(String uri){
        for (String s : WHITE_LIST) {
            if(uri.startsWith(s)){
                return  true;
            }
        }
        return false;
    }

    @Override
    public boolean preHandle(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, Object handler) throws Exception {

        return true;
    }
    
    @Override
    public void postHandle(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    /**
     * 捕获所有系统异常并打出
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, Object handler, Exception ex) throws Exception {
        try {
            if(ex!=null){
                LOG.error("globalIntercepter throws Exceptions ",ex);
                response.setCharacterEncoding("UTF-8");
    			request.setAttribute("errMsg", ex.getMessage());
            }
        } catch (Exception e) {}
        super.afterCompletion(request, response, handler, ex);
    }

    @Override
    public void afterConcurrentHandlingStarted(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, Object handler) throws Exception {
        super.afterConcurrentHandlingStarted(request, response, handler);
    }
}
