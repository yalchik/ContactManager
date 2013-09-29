package controller.filters;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import static controller.filters.Validator.*;
import static controller.helpers.RequestHelper.*;
import controller.helpers.RequestHelper.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Wrapper for http requests.
 * Provides validation of parameter values
 * @author Yalchyk Ilya
 */
public class ValidatingHttpRequest extends HttpServletRequestWrapper {
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * @see HttpServletRequestWrapper#ValidatingHttpRequest(HttpServletRequest)
	 */
	public ValidatingHttpRequest(HttpServletRequest request) {
        super(request);
	}


	/**
	 * Gets value of the parameter from the request,
	 * and validate it
	 * @param name name of the parameter
	 * @return valid value of the parameter
	 * @see HttpServletRequestWrapper#getParameter(String)
	 */
	@Override
	public String getParameter(String name) {
		HttpServletRequest request = (HttpServletRequest) super.getRequest();
		return validate(name, request.getParameter(name));
	}
	
	/**
	 * Checks value of the parameter in request.
	 * If it's valid, then returns value as is.
	 * If it is not valid, then returns default value.
	 * Default value is considering to be valid.
	 * @param name name of the parameter
	 * @param value value of the parameter
	 * @return valid value of the parameter
	 */
	private String validate(String name, String value) {
		logger.debug("validating {}={}", name, value);

        String validValue;
        if (value != null) {
	        switch (name) {
	        case CommonParameters.ID_PARAMETER:
	        case ListSpecificParameters.COUNT_PARAMETER:
	        case ListSpecificParameters.START_PARAMETER:
	        case PhoneSpecificParameters.PHONE_ID_PARAMETER:
	        case PhoneSpecificParameters.CODE1_PARAMETER:
	        case PhoneSpecificParameters.CODE2_PARAMETER:
	        case PhoneSpecificParameters.PHONE_NUMBER_PARAMETER:
	        case AttachmentSpecificParameters.ATTACHMENT_ID_PARAMETER:
	        	validValue = isNonNegativeNumber(value) ? value : getDefaultValue(name);
	        	break;
	        case ListSpecificParameters.CHECK_CONTACT_PARAMETER:
	        	validValue = isBoolean(value) ? value : getDefaultValue(name);
	        	break;
	        case CommonParameters.ACTION_PARAMETER:
	        	validValue = isEnglishName(value) ? value : getDefaultValue(name);
	        	break;	        	
	        case ContactSpecificParameters.FIRSTNAME_PARAMETER:
	        case ContactSpecificParameters.LASTNAME_PARAMETER:
	        case ContactSpecificParameters.MIDNAME_PARAMETER:
	        case ContactSpecificParameters.CITIZENSHIP_PARAMETER:
	        case ContactSpecificParameters.JOB_PARAMETER:
	        case ContactSpecificParameters.COUNTRY_PARAMETER:
	        case ContactSpecificParameters.CITY_PARAMETER:	        
	        case MailSpecificParameters.SUBJECT_PARAMETER:
	        	validValue = isName(value) ? value : getDefaultValue(name);
	        	break;
	        case ContactSpecificParameters.BIRTHDAY_PARAMETER:
	        	validValue = isDate(value) ? value : getDefaultValue(name);
	        	break;
	        case ContactSpecificParameters.SEX_PARAMETER:
	        	validValue = isSex(value) ? value : getDefaultValue(name);
	        	break;
	        case ContactSpecificParameters.MARITAL_STATUS_PARAMETER:
	        	validValue = isMartialStatus(value) ? value : getDefaultValue(name);
	        	break;
	        case PhoneSpecificParameters.PHONE_TYPE_PARAMETER:
	        	validValue = isPhoneType(value) ? value : getDefaultValue(name);
	        	break;
	        case ContactSpecificParameters.WEB_SITE_PARAMETER:
	        	validValue = isURL(value) ? value : getDefaultValue(name);
	        	break;
	        case ContactSpecificParameters.EMAIL_PARAMETER:
	        	validValue = isEmail(value) ? value : getDefaultValue(name);
	        	break;
	        case ContactSpecificParameters.HOUSE_PARAMETER:
	        case ContactSpecificParameters.APARTMENT_PARAMETER:
	        case ContactSpecificParameters.POSTCODE_PARAMETER:
	        	validValue = isPositiveNumber(value) ? value : getDefaultValue(name);
	        	break;
	        case ContactSpecificParameters.PHOTO_PARAMETER:
	        case AttachmentSpecificParameters.ATTACHMENT_FILE_PARAMETER:
	        	validValue = isUploadPath(value) ? value : getDefaultValue(name);
	        	break;	        
	        case MailSpecificParameters.RECIPIENTS_PARAMETER:
	        	validValue = isEmails(value) ? value : getDefaultValue(name);
	        	break;
	        case CommonParameters.COMPARISON_SIGN:
	        	validValue = isComparisonSign(value) ? value : getDefaultValue(name);
	        	break;
	        case CommonParameters.COMMENT_PARAMETER:
	        case MailSpecificParameters.TEXT_PARAMETER:
	        case ContactSpecificParameters.STREET_PARAMETER:
	        case AttachmentSpecificParameters.ATTACHMENT_NAME_PARAMETER:
	        	validValue = value;
	        	break;
	        default:
	        	logger.error("Parameter '{}' is not supported", name);
	        	validValue = "";
	        }
        }
        else {
        	validValue = getDefaultValue(name);
        }
        logger.debug("return {}={}", name, validValue);
        return validValue;
	}    
}
