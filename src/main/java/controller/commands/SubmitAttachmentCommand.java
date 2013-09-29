package controller.commands;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Attachment;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import controller.helpers.EncodingHelper;
import controller.helpers.RequestHelper.AttachmentSpecificParameters;
import controller.helpers.RequestHelper.CommonParameters;
import dao.DAOException;
import dao.IContactsDAO;
import static controller.filters.Validator.*;
import static controller.helpers.RequestHelper.*;

/**
 * Parses multipart form data and creates new attachment.
 * Save it in the upload directory.
 * Sets it as request parameter.
 * Delegates attachment handling either to CreateAttachmentCommand or to EditAttachmentCommand
 * @author Yalchyk Ilya
 */
public class SubmitAttachmentCommand extends AbstractCommand {

	/**
	 * @see ICommand#execute(HttpServletRequest, HttpServletResponse, IContactsDAO)
	 */
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response, IContactsDAO dao) throws DAOException {
		
		Attachment attachment = new Attachment();
		ICommand nextCommand = null;
		
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
							attachment.setPath("uploads/attachment?name=" + file.getName());				            
						} catch (IOException e) {
							logger.error("Can't create temp file.", e);
						} catch (Exception e) {
							logger.error("Can't write into created file.", e);
						}
					}
					else {
						logger.debug("FormField recieved. Name = {}; Value = {}", item.getFieldName(), item.getString());
						String field = item.getFieldName();
						String value = EncodingHelper.convertFromUTF8(item.getString());
						switch (field) {
						case CommonParameters.ACTION_PARAMETER:							
							nextCommand = value.equals("create_attachment") ? new CreateAttachmentCommand() : new EditAttachmentCommand();
							break;
						case CommonParameters.ID_PARAMETER:
							value = isNonNegativeNumber(value) ? value : getDefaultValue(field);
							attachment.setContactID(Integer.parseInt(value));
							break;
						case AttachmentSpecificParameters.ATTACHMENT_ID_PARAMETER:
							value = isNonNegativeNumber(value) ? value : getDefaultValue(field);
							attachment.setId(Integer.parseInt(value));
							break;
						case AttachmentSpecificParameters.ATTACHMENT_NAME_PARAMETER:
							value = isName(value) ? value : getDefaultValue(field);
							attachment.setName(value);
							break;
						case CommonParameters.COMMENT_PARAMETER:
							attachment.setComment(value);
							break;
						default:
							logger.warn("Unknown field");
						}
					}
				}

			} catch (FileUploadException e) {
				logger.error("Can't upload file.", e);
			}
		}
		else {
			logger.warn("No files has been uploaded");
		}
		request.setAttribute("attachment", attachment);
		return nextCommand == null ? null : nextCommand.execute(request, response, dao);
	}
	
}
