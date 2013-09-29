package controller;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Gets filename from the request,
 * search it in the path from DD
 * and returns its binary data
 * @author Yalchyk Ilya
 */
@WebServlet("/uploads/*")
public class UploadHandleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadHandleServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String path = request.getServletContext().getInitParameter("upload_path");
		response.setHeader("Content-Disposition", String.format("inline; filename=\"%s\"", name));
		ServletOutputStream out = response.getOutputStream();
		try {			
			DataInputStream in = new DataInputStream(new FileInputStream(new File(path + name)));		  
			IOUtils.copy(in, out);				
		} catch (IOException e) {
			logger.info("user has requested unavailable file {}", path + name);
		} catch (Exception e) {
			logger.error("Exception has been occured", e);
		} finally {
			out.flush();
			out.close();
		}		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
