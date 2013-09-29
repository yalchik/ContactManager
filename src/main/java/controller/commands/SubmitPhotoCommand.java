package controller.commands;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import dao.IContactsDAO;
import model.Contact;

/**
 * Parses multipart form data, saves received photo in the upload directory.
 * Sets photo to the session contact.
 * Redirect to loading page.
 * @author Yalchyk Ilya
 */
public class SubmitPhotoCommand extends AbstractCommand {

	/**
	 * @see ICommand#execute(HttpServletRequest, HttpServletResponse, IContactsDAO)
	 */
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response, IContactsDAO dao) {
		
		Contact contact = (Contact) request.getSession().getAttribute("contact");
		if (contact != null) {
		
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			if (isMultipart) {			
				DiskFileItemFactory factory = new DiskFileItemFactory();
				File repository = (File) request.getServletContext().getAttribute("javax.servlet.context.tempdir");			
				factory.setRepository(repository);
				ServletFileUpload upload = new ServletFileUpload(factory);
	
				// Parse the request
				try {
					List<FileItem> items = upload.parseRequest(request);
					for (FileItem item : items) {						
						if (!item.isFormField()) {
							logger.debug("File recieved. Name: {}, size: {}", item.getName(), item.getSize());
							try {
								String path = request.getServletContext().getInitParameter("upload_path");
								File file = File.createTempFile("upload-", item.getName(), new File(path));
								item.write(file);		
					            contact.setPhotoPath("uploads/picture?name=" + file.getName());
					            request.getSession().setAttribute("contact", contact);
							} catch (IOException e) {
								logger.error("Can't create temp file. ", e);
							} catch (Exception e) {
								logger.error("Can't write into created file. {}", e);
							}
							try {
								response.sendRedirect("/Contact/loading.html");
							} catch (IOException e) {
								logger.error("Redirect failed", e);
							}
						}
						else {
							logger.warn("FormField recieved. Name = {}; Value = {}", item.getFieldName(), item.getString());
						}
					}
				} catch (FileUploadException e) {
					logger.error("Can't upload file.", e);
				}
			}
			else {
				logger.warn("No files has been uploaded");
			}
		}
		else {
			logger.error("Empty session");
		}
		return null;
	}

}
