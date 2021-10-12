package by.epum.training.db.controller.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epum.training.db.controller.listener.ContextManager;
import by.epum.training.db.controller.util.Utils;
import by.epum.training.db.entity.Role;
import by.epum.training.db.entity.User;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SecurityFilter implements Filter {
	private static final Logger logger=LogManager.getLogger(ContextManager.class);
	private static final Map<String, Set<Role>> permissions = new HashMap<>();

    static {
        Set<Role> all = new HashSet<>();
        all.addAll(Arrays.asList(Role.values()));
        
        Set<Role> admin = new HashSet<>();
        admin.add(Role.ADMIN);
        Set<Role> passenger = new HashSet<>();
        passenger.add(Role.PASSENGER);
     
        //Set<Role> manager = new HashSet<>();
       
        permissions.put("/", all);
        permissions.put("/jsp/home", all);
        permissions.put("/jsp/login", all);
        permissions.put("/jsp/logout", all);
        permissions.put("/logout", all);
        permissions.put("/jsp/join", all);
        permissions.put("/jsp/sign", all);              
       
        permissions.put("/jsp/user/update", all);
        permissions.put("/jsp/user/list", admin);
        permissions.put("/jsp/user/edit", admin);
        permissions.put("/jsp/user/save", admin);
        permissions.put("/jsp/user/person", passenger);
 
        permissions.put("/jsp/manage", admin);

        permissions.put("/jsp/station/list", admin);
        permissions.put("/jsp/station/edit", admin);
        permissions.put("/jsp/station/save", admin);
        
        permissions.put("/jsp/route/list", admin);
        permissions.put("/jsp/route/edit", admin);
        permissions.put("/jsp/route/save", admin);
        
        permissions.put("/jsp/train/list", admin);
        permissions.put("/jsp/train/edit", admin);
        permissions.put("/jsp/train/save", admin);
        
        permissions.put("/jsp/carriage/list", admin);
        permissions.put("/jsp/carriage/edit", admin);
        permissions.put("/jsp/carriage/save", admin);
        permissions.put("/jsp/carriage/delete", admin);
        permissions.put("/jsp/carriage/choose", passenger);
        
        permissions.put("/jsp/bill/list", admin);
        permissions.put("/jsp/bill/edit", admin);
        permissions.put("/jsp/bill/save", admin);
        
        permissions.put("/jsp/book", all);
        permissions.put("/jsp/book/list", all);
        permissions.put("/jsp/book/save", passenger);
        
        permissions.put("/jsp/pay", passenger);

        permissions.put("/jsp/language", all);
      
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
    		throws IOException, ServletException {

    	HttpServletRequest req = (HttpServletRequest)request;
    	HttpServletResponse resp = (HttpServletResponse)response;   

    	User loginedUser=Utils.getLoginedUser(req.getSession(false));

    	if(isSecurityPage(req)){
    		if(loginedUser==null) {
    			String url=Utils.buildUriWithQueryStringFromRequest(req, resp);
    			int redirectId=Utils.storeRedirectAfterUserLoginUrl(req.getSession(false), url);

    			resp.sendRedirect(req.getContextPath()+"/jsp/login?redirectId="+redirectId);
    			return ;
    		}	

    		//if already logined, check if user has permission
    		boolean hasPermission=hasPermission(req,permissions);

    		if(!hasPermission) {
    			logger.info("user id="+loginedUser.getId()+ "tried to enter protected page");

    			RequestDispatcher rd=req.getServletContext().getRequestDispatcher("/WEB-INF/jsp/login.jsp?message=login.message.access.denied");
    			rd.forward(req, resp);
    			return;	        	
    		}        	
    	}

    	chain.doFilter(req, resp);  	   
    }

    public static boolean isSecurityPage(HttpServletRequest request) {
    	String uri=Utils.getUri(request);

    	Set<Role> roles = permissions.get(uri);

    	if(roles != null && !roles.isEmpty() && roles.size()==Role.values().length) {
    		return false;
    	}

    	return true;
    }


    public static boolean hasPermission(HttpServletRequest request,Map<String, Set<Role>>permissions) {
    	String uri=Utils.getUri(request);

    	User loginedUser=Utils.getLoginedUser(request.getSession(false));
    	Set<Role> roles = permissions.get(uri);

    	if(roles.contains(loginedUser.getRole())) {
    		//have permission
    		return true;
    	}

    	//no permission
    	return false;
    }
}


