package controller.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DAOException;
import dao.IContactsDAO;

/**
 * Command, that can be executed from the front controller
 * @author Yalchyk Ilya
 */
public interface ICommand {
	
	/**
	 * Executes command, which can take data from request, use DAO,
	 * send data in response (including redirect), return url for forwarding.
	 * @param request servlet request
	 * @param response servlet response
	 * @param dao data access object
	 * @return url for forwarding or null, if forwarding is not necessary
	 */
	public String execute(HttpServletRequest request, HttpServletResponse response, IContactsDAO dao)
			throws DAOException;
}
