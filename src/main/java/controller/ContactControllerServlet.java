package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.commands.ICommand;
import controller.helpers.CommandHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dao.DAOConfigurationException;
import dao.DAOException;
import dao.IContactsDAO;
import dao.MySqlContactsDAO;

/**
 * Front Controller of the application
 * @author Yalchyk Ilya
 */
@WebServlet("*.do")
public class ContactControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public static final String INDEX_PAGE = "/list.jsp";
	public static final String CONTACT_PAGE = "/contact.jsp";
	public static final String PHONE_PAGE = "/phone.jsp";
	public static final String ATTACHMENT_PAGE = "/attachment.jsp";
	public static final String MAIL_PAGE = "/mail.jsp";
	public static final String ERROR_PAGE = "/error.jsp";
	public static final String LOADING_PAGE = "/Contact/loading.html";
	public static final String DEFAULT_PAGE = "/Contact/index.do?count=10&start=0";
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
    public void init() {
    	try {
    		super.init();
    	} catch (ServletException e) {
    		logger.error("Initialization of servlet failed.", e);
    	}
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}
	
	 /** Processes requests for both HTTP  
	   * <code>GET</code> and <code>POST</code> methods.
	   * @param request servlet request
	   * @param response servlet response
	   */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		logger.debug("request: method - {}; URI - {}; query - {}", request.getMethod(), request.getRequestURI(), request.getQueryString());

		IContactsDAO dao = new MySqlContactsDAO();
		try {
			dao.openConnection();
			CommandHelper commandHelper = new CommandHelper();
			ICommand command = commandHelper.getCommand(request);
			if(command != null) {
				logger.debug("Command has been determined: {}", command.getClass().toString());
	            String page = command.execute(request, response, dao);
			    if (page != null) {
			    	logger.debug("dispatch to {}", page);
			    	dispatch(request, response, page);
			    }
	        }
			else {
				logger.error("Command hasn't been determined!");
			}			
		} catch (DAOConfigurationException e) {
			logger.error("DB connection hasn't been opened", e);
			request.setAttribute("message", "database is not available now");
			dispatch(request, response, ERROR_PAGE);
		} catch (DAOException e) {
			logger.error("Database operation hasn't been executed", e);
			request.setAttribute("message", "the last operation hasn't been executed");
			dispatch(request, response, ERROR_PAGE);
		} catch (Exception e) {
			logger.error("Exception has been occured", e);
			request.setAttribute("message", e.getMessage());
			dispatch(request, response, ERROR_PAGE);
		} finally {
			try {
				dao.closeConnection();
			} catch (RuntimeException e) {
				logger.error("DB connection hasn't been closed", e);
			}
		}
	  }
	  
	/**
	 * Forwards request and response to the page
	 * @param request servlet request
	 * @param response servlet response
	 * @param page forwarding page
	 */
	protected void dispatch(HttpServletRequest request, HttpServletResponse response, String page)
			throws  ServletException, IOException {
		
		getServletContext().getRequestDispatcher(page).forward(request, response);
	}

}
