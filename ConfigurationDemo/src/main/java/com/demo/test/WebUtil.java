/*
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.security.spec.KeySpec;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.jboss.logging.Logger;
import org.jboss.seam.security.Identity;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.primefaces.component.autocomplete.AutoComplete;
import org.primefaces.component.panel.Panel;

import com.ids.jpms.ApplicationEnums.AdultType;
import com.ids.jpms.ApplicationEnums.ApplicableModules;
import com.ids.jpms.ApplicationEnums.BroadcastMessageSubModule;
import com.ids.jpms.ApplicationEnums.Classification;
import com.ids.jpms.ApplicationEnums.DesignationType;
import com.ids.jpms.ApplicationEnums.DynamicRateOperator;
import com.ids.jpms.ApplicationEnums.GuestClassification;
import com.ids.jpms.ApplicationEnums.NegativeFormat;
import com.ids.jpms.ApplicationEnums.NumberFormat;
import com.ids.jpms.ApplicationEnums.ReasonModuleTasks;
import com.ids.jpms.ApplicationEnums.RevenueCodeApplicableModules;
import com.ids.jpms.ApplicationEnums.TaxModules;
import com.ids.jpms.ApplicationEnums.TaxOn;
import com.ids.jpms.ApplicationEnums.TaxStructureCalculationType;
import com.ids.jpms.ApplicationEnums.TimeFormat;
import com.ids.jpms.authorization.CurrentUserManager;
import com.ids.jpms.authorization.annotations.Authenticated;
import com.ids.jpms.common.Utility.CommonUtility;
import com.ids.jpms.entity.BookingGuestDetail;
import com.ids.jpms.entity.Contacts;
import com.ids.jpms.entity.Country;
import com.ids.jpms.entity.Currency;
import com.ids.jpms.entity.FunctionRight.FunctionAction;
import com.ids.jpms.entity.GuestProfile;
import com.ids.jpms.entity.Property;
import com.ids.jpms.entity.RevenueCode;
import com.ids.jpms.entity.RevenueCodeRoundOff;
import com.ids.jpms.entity.Role;
import com.ids.jpms.entity.Room;
import com.ids.jpms.entity.RoomFeature;
import com.ids.jpms.entity.RoomType;
import com.ids.jpms.entity.RoomTypeApplicableDate;
import com.ids.jpms.entity.Section;
import com.ids.jpms.entity.SwitchConfiguration;
import com.ids.jpms.entity.TaxStructure;
import com.ids.jpms.entity.TaxStructureDetails;
import com.ids.jpms.entity.User;
import com.ids.jpms.entity.UserGroup;
import com.ids.jpms.message.MessageFactory;
import com.ids.jpms.usermanagement.service.PropertyService;
import com.ids.qb.authSample.OAuthUtils;
import com.intuit.ipp.core.Context;
import com.intuit.ipp.core.ServiceType;
import com.intuit.ipp.security.IAuthorizer;
import com.intuit.ipp.security.OAuthAuthorizer;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.Config;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.basic.DefaultOAuthProvider;

*//**
 * This class contains all commonly used Utility methods.
 * 
 * @author JavaTeam
 * @version 1.0
 *//*

@Named("webUtil")
@SessionScoped
public class WebUtil implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	@Authenticated
	User user;

	@Inject
	PropertyService propertyService;

	@Inject
	CurrentUserManager currentUserManager;

	private static final DecimalFormat decimalFormat = new DecimalFormat("#.00");

	private static final DateFormatSymbols dateFormatSymbols = new DateFormatSymbols();

	private List<Property> applicablePropertyList;

	@Inject
	Identity identity;

	private static User currentAccount;

	*//**
	 * This method returns the UserDateFormat. This method reads the User and returns corresponding userDateFormat in the form of String. If the userDateFormat is not defined then it will return "dd-MM-yyyy"
	 * 
	 * @return useinputStringrDateFormat
	 *//*
	public String getUserDateFormat() {

		String dateFormat = user.getDateFormat().getSymbol();
		if (dateFormat == null || "".equalsIgnoreCase(dateFormat)) {
			dateFormat = "dd/MM/yy"; // check for default format
		}

		return dateFormat;
	}

	public static String getTimezoneConvertedDate(final Date date,

			final String datePattern, final String timeZone) {

		final DateFormat formatter = new SimpleDateFormat(

				datePattern != null ? datePattern : "dd-MM-yy HH:mm:ss");

		final Calendar cal = Calendar.getInstance();

		cal.setTime(date);

		cal.add(Calendar.MILLISECOND, TimeZone.getTimeZone(timeZone)

				.getRawOffset());

		final Date date2 = cal.getTime();

		return formatter.format(date2);

	}

	public String getTimezoneConvertedDate(final Date date,

			final String datePattern, final long propertyId) {

		Property property = null;

		try {

			property = propertyService.getPropertyById(propertyId);

		} catch (final Exception e) {

			e.printStackTrace();

		}

		return getTimezoneConvertedDate(date, datePattern, property

				.getContactList().get(0).getCountryId().getGmtTimeDiffrence());
	}

	public DataService establishConnectionBetweenIMagineAndQB(String companyId) throws Exception {
		
		 * HttpSession session= (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false); String propertyCode=currentUserManager.getSelectedProperty().getPropertyCode();
		 
		DataService service = null;// (DataService) session.getAttribute("QBService");
		if (service == null) {
			service = getPersistedDataService();
			if (service == null)
				throw new Exception("Data Service For Quick Book is null!");

		}
		return service;

		
		 * session.setAttribute(propertyCode+"QBService", service); session.setAttribute(propertyCode+"isQBConnected", true); } return service ; //if(service==null){
		 * 
		 * // } /*else return service;
		 
		
		 * Properties prop = new Properties(); prop.put("oauth_consumer_key", "qyprd6owtiwWw8A0UK5Go2g1t9oXiM"); prop.put("oauth_consumer_secret", "o89ZnAHIpw9DvPVVAn9w3n8KX16ZsPXOtfOPerdI"); prop.put("oauth_callbackURL", "http://172.16.11.151:8080/OuthSample/AccessToken");
		 * 
		 * If you want to switch to production, please make sure that you change the qbo_url in app. properties file inside OauthSample folder to quickbooks.api.intuit.com from sandbox-quickbooks.api.intuit.com. Also, make sure that you configure the sample app to use prod tokens instead of development tokens.
		 * 
		 * prop.put("qbo_url", "https://sandbox-quickbooks.api.intuit.com/v3/company/"); //prop.put("access_Token", "qyprdDn92DbWVIP0NG0cYdXNxSZz3zWvm8OqGjpZn5KPoc8e"); prop.put("access_Token", "qyprdG9KOAakNMxnRq7QJYsmhTEaGXq4oKKGa1bcYji72Ahm");
		 * 
		 * //prop.put("access_Token_Secret", "uJd9Sjr9SPkxYssrWC4Ni6fzda2Qzb7k8Y2ouvao"); prop.put("access_Token_Secret", "KABRK2KdVwyUBQt4iUt9cew1c5dLzZco3kCg707Z");
		 * 
		 * prop.put("app_Token", "0e588d91b066fb4c2abb5a1b4bc82dedde89");
		 * 
		 * 
		 * String companyId1 = "1386028405"; companyId1 ="1416833620";
		 
		// return service;//fetchConnectedDataService(companyId, prop);

	}

	public DataService getDataService() {
		DataService dataService = null;
		try {
			Config.setProperty(Config.BASE_URL_QBO, "https://quickbooks.api.intuit.com/v3/company");
			Config.setProperty(Config.TIMEOUT_CONNECTION, "1000000");
			Config.setProperty(Config.TIMEOUT_REQUEST, "1000000");
			Config.setProperty(Config.SERIALIZATION_REQUEST_FORMAT, "xml");
			Config.setProperty(Config.SERIALIZATION_RESPONSE_FORMAT, "xml");
			Config.setProperty(Config.COMPRESSION_REQUEST_FORMAT, "gzip");
			Config.setProperty(Config.COMPRESSION_RESPONSE_FORMAT, "gzip");
			OAuthUtils utils = new OAuthUtils();
			Properties prop = utils.readProperties();
			String oauth_consumer_key = prop.getProperty("oauth_consumer_key");
			String oauth_consumer_secret = prop.getProperty("oauth_consumer_secret");
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			String accessTokenSecret = (String) session.getAttribute("accessTokenSecret");
			String accessToken = (String) session.getAttribute("accessToken");
			String realmID = (String) session.getAttribute("realmId");
			IAuthorizer authorizer = new OAuthAuthorizer(oauth_consumer_key, oauth_consumer_secret, accessToken, accessTokenSecret);
			Context context = new Context(authorizer, ServiceType.QBO, realmID);
			dataService = new DataService(context);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataService;
	}

	public DataService getPersistedDataService() {
		DataService dataService = null;
		try {
			Config.setProperty(Config.BASE_URL_QBO, "https://quickbooks.api.intuit.com/v3/company");
			Config.setProperty(Config.TIMEOUT_CONNECTION, "1000000");
			Config.setProperty(Config.TIMEOUT_REQUEST, "1000000");
			Config.setProperty(Config.SERIALIZATION_REQUEST_FORMAT, "xml");
			Config.setProperty(Config.SERIALIZATION_RESPONSE_FORMAT, "xml");
			Config.setProperty(Config.COMPRESSION_REQUEST_FORMAT, "gzip");
			Config.setProperty(Config.COMPRESSION_RESPONSE_FORMAT, "gzip");
			OAuthUtils utils = new OAuthUtils();
			Properties prop = utils.readProperties();
			if (prop != null) {
				String oauth_consumer_key = prop.getProperty("oauth_consumer_key");
				String oauth_consumer_secret = prop.getProperty("oauth_consumer_secret");
				// String connectionString=propertyMB.getProperty().getConnectionString();
				String connectionString = currentUserManager.getSelectedProperty().getConnectionString();// propertyMB.getProperty().getConnectionString();
				if (connectionString != null && !connectionString.isEmpty()) {
					String[] connectionDetail = connectionString.split(",");
					if (connectionDetail.length == 3) {
						String accessToken = connectionDetail[0];
						String accessTokenSecret = connectionDetail[1];
						String realmID = connectionDetail[2];
						IAuthorizer authorizer = new OAuthAuthorizer(oauth_consumer_key, oauth_consumer_secret, accessToken, accessTokenSecret);
						Context context = new Context(authorizer, ServiceType.QBO, realmID);
						dataService = new DataService(context);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataService;
	}

	public DataService fetchConnectedDataService(String companyId1, Properties prop) {

		OAuthConsumer oauthconsumer;

		// Initialize the Provider class with the request token, access token
		// and authorize URLs
		OAuthProvider provider = new DefaultOAuthProvider(FormNames.QB_REQUEST_TOKEN_URL, FormNames.QB_ACCESS_TOKEN_URL, FormNames.QB_AUTHORIZE_URL);

		try {

			// Read the consumer key and secret from the Properties file to
			// create the OauthConsumer object
			oauthconsumer = new DefaultOAuthConsumer(prop.getProperty("oauth_consumer_key"), prop.getProperty("oauth_consumer_secret"));
			String accessToken = prop.getProperty("access_Token");
			String accessTokenSecret = prop.getProperty("access_Token_Secret");
			Config.setProperty(Config.BASE_URL_QBO, "https://sandbox-quickbooks.api.intuit.com/v3/company");
			Config.setProperty(Config.TIMEOUT_CONNECTION, "1000000");
			Config.setProperty(Config.TIMEOUT_REQUEST, "1000000");
			Config.setProperty(Config.SERIALIZATION_REQUEST_FORMAT, "xml");
			Config.setProperty(Config.SERIALIZATION_RESPONSE_FORMAT, "xml");
			Config.setProperty(Config.COMPRESSION_REQUEST_FORMAT, "gzip");
			Config.setProperty(Config.COMPRESSION_REQUEST_FORMAT, "gzip");
			OAuthAuthorizer oauth = new OAuthAuthorizer(prop.getProperty("oauth_consumer_key"), prop.getProperty("oauth_consumer_secret"), accessToken, accessTokenSecret);
			String appToken = prop.getProperty("app_Token");
			String companyID = companyId1;
			Context context = new Context(oauth, appToken, ServiceType.QBO, companyID);
			DataService service = new DataService(context);

			return service;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public static Date getTimezoneConvertDate(final Date date,

			final String datePattern, final String timeZone) {

		final int offset = TimeZone.getDefault().getRawOffset()

				- TimeZone.getTimeZone(timeZone).getRawOffset();

		final DateFormat formatter = new SimpleDateFormat(

				datePattern != null ? datePattern : "dd-MM-yy HH:mm:ss");

		final DateFormat formatter1 = new SimpleDateFormat(

				datePattern != null ? datePattern : "dd-MM-yy HH:mm:ss");

		formatter.setTimeZone(TimeZone.getTimeZone("UTC"));

		final Calendar cal = Calendar.getInstance();

		Date date1 = null;

		try {

			final String formatedDate = formatter.format(date);

			date1 = formatter1.parse(formatedDate);

		} catch (final ParseException e) {
			e.printStackTrace();
		}

		cal.setTime(date1);

		cal.add(Calendar.MILLISECOND, offset);

		final Date date2 = cal.getTime();

		return date2;

	}

	// by Sachin displaying date and time in format defined in User Table

	public String getUserDateAndTimeFormat() {

		String retString = null;

		String dateFormat = user.getDateFormat().getSymbol();

		String timeFormat = user.getTimeFormat().getSymbol();

		if (dateFormat == null || "".equalsIgnoreCase(dateFormat)) {

			dateFormat = "dd-MMM-yyyy  "; // check for default format

		}

		if (timeFormat == null || "".equalsIgnoreCase(timeFormat)) {

			timeFormat = "HH:mm";

		}

		if (timeFormat.equals(TimeFormat.hhmm.getSymbol())) {

			timeFormat = timeFormat + " a ";

		}

		retString = dateFormat + " " + timeFormat;

		return retString;

	}

	private static Logger log = Logger.getLogger(WebUtil.class);

	public static void clearForm(final String formName) {

		clearSubmittedValues(formName);

		clearValuesAndErrorStyle(formName);

	}

	public static String printStackTrace(final Throwable exception) {

		final StringWriter stringWriter = new StringWriter();

		exception.printStackTrace(new PrintWriter(stringWriter, true));

		return stringWriter.toString();

	}

	public static void clearSubmittedValues(final UIComponent uiComponent) {

		if (uiComponent == null) {

			return;

		}

		final Iterator<UIComponent> children = uiComponent

				.getFacetsAndChildren();

		while (children.hasNext()) {

			clearSubmittedValues(children.next());

		}

		if (uiComponent instanceof UIInput) {

			((UIInput) uiComponent).setSubmittedValue(null);

			((UIInput) uiComponent).setValue(null);

			((UIInput) uiComponent).setLocalValueSet(false);

			((UIInput) uiComponent).resetValue();

		}

	}

	public static void clearAutoCompleteSubmittedValues(

			final UIComponent uiComponent) {

		if (uiComponent == null) {

			return;

		}

		final Iterator<UIComponent> children = uiComponent

				.getFacetsAndChildren();

		while (children.hasNext()) {

			clearSubmittedValues(children.next());

		}

		if (uiComponent instanceof AutoComplete) {

			((UIInput) uiComponent).setSubmittedValue(null);

			((UIInput) uiComponent).setValue(null);

			((UIInput) uiComponent).setLocalValueSet(false);

			((UIInput) uiComponent).resetValue();

		}

	}

	public static void checkValues(final UIComponent uiComponent) {

		if (uiComponent == null) {

			return;

		}

		final Iterator<UIComponent> children = uiComponent

				.getFacetsAndChildren();

		while (children.hasNext()) {

			checkValues(children.next());

		}

		
		 * 
		 * 
		 * 
		 * if (uiComponent instanceof UIInput)
		 * 
		 * 
		 * 
		 * { uiComponent.getAttributes().put("styleClass", ""); }
		 

	}

	public static void clearValuesAndErrorStyle(final String formName) {

		// log.info("Clear Form Values and Error Style ...");

		final FacesContext context = FacesContext.getCurrentInstance();

		final UIViewRoot view = context.getViewRoot();

		final UIComponent uiComponent = view.findComponent(formName);

		if (uiComponent != null) {

			final Iterator<UIComponent> children = uiComponent

					.getFacetsAndChildren();

			UIComponent currentComponent;

			while (children.hasNext()) {

				currentComponent = children.next();

				// log.info(currentComponent.getId());

				currentComponent.clearInitialState();

			}

		}

	}

	public static void clearSubmittedValues(final String formName) {

		final FacesContext context = FacesContext.getCurrentInstance();

		final UIViewRoot view = context.getViewRoot();

		final UIComponent uiComponent = view.findComponent(formName);

		clearSubmittedValues(uiComponent);

	}

	public static void clearErrorStyle(final String formName) {

		final FacesContext context = FacesContext.getCurrentInstance();

		final UIViewRoot view = context.getViewRoot();

		final UIComponent uiComponent = view.findComponent(formName);

		checkValues(uiComponent);

	}

	*//**
	 * 
	 * To Identity The Exception Type
	 * 
	 * 
	 * 
	 * @param exceptionMessage
	 * 
	 *            Exception Message
	 * 
	 * @param functionAction
	 * 
	 *            Function Actions
	 * 
	 * @param classType
	 * 
	 *            Class
	 * 
	 * 
	 *//*

	public static void getExceptionType(final Exception exception, final FunctionAction functionAction, final Class<?> classType) {
		log.info("Exception Message - " + exception.getMessage());
		Writer exceptionMessage = new StringWriter();
		PrintWriter printWriter = new PrintWriter(exceptionMessage);
		exception.printStackTrace(printWriter);

		String exceptionType = "M7";
		String fieldId = null;
		if (functionAction.equals(FunctionAction.Delete)) {// Delete
			if (exceptionMessage.toString().contains("CONSTRAINT") && exceptionMessage.toString().contains("FOREIGN KEY") && exceptionMessage.toString().contains("REFERENCES")) {
				exceptionType = "M6"; // Transactions Exists! Can't Delete!
			}
		} else if (functionAction.equals(FunctionAction.Add)) {// Save
			if (exceptionMessage.toString().contains("Duplicate entry")) {
				exceptionType = "M8"; // Duplicate Entries Found!
				log.info("Duplicate entry found...");
				for (int i = 1; i < classType.getDeclaredFields().length; i++) {
					final String id = "key " + i;
					final String fieldName = classType.getDeclaredFields()[i].getName();
					final String tableName = "key '" + convertFieldToTableName(fieldName) + "'";
					if (exceptionMessage.toString().contains(id) || exceptionMessage.toString().contains(tableName)) {
						fieldId = classType.getSimpleName().toLowerCase() + "Form:" + fieldName + "Id";
						final FacesContext context = FacesContext.getCurrentInstance();
						final UIViewRoot view = context.getViewRoot();
						final UIComponent uiComponentForm = view.findComponent(classType.getSimpleName().toLowerCase() + "Form");
						checkValues(uiComponentForm);
						UIComponent uiComponent = view.findComponent(fieldId);
						if (uiComponent == null) {
							fieldId = classType.getSimpleName().toLowerCase() + "Form:" + "tabView:" + fieldName + "Id";
							uiComponent = view.findComponent(fieldId);
						}
						break;
					}
				}
			}
		}
		MessageFactory.setServerMessage(fieldId, exceptionType);
		// MessageFactory.setGrowlMessage(FacesMessage.SEVERITY_INFO, fieldId,
		// exceptionType);
		log.info("Exception Type Code - " + exceptionType);
	}

	public static void getExceptionType(final Exception exception, final FunctionAction functionAction, final Class<?> classType, final Class<?> tabClass) {
		log.info("Exception Message - " + exception.getMessage());
		Writer exceptionMessage = new StringWriter();
		PrintWriter printWriter = new PrintWriter(exceptionMessage);
		exception.printStackTrace(printWriter);

		String exceptionType = "M7";
		String fieldId = null;
		UIComponent uiComponentForm = null;
		if (functionAction.equals(FunctionAction.Delete)) {// Delete
			if (exceptionMessage.toString().contains("CONSTRAINT") && exceptionMessage.toString().contains("FOREIGN KEY") && exceptionMessage.toString().contains("REFERENCES")) {
				exceptionType = "M6"; // Transactions Exists! Can't Delete!
			}
		} else if (functionAction.equals(FunctionAction.Add)) {// Save
			if (exceptionMessage.toString().contains("Duplicate entry")) {
				exceptionType = "M8"; // Duplicate Entries Found!
				log.info("Duplicate entry found...");
				for (int i = 1; i < classType.getDeclaredFields().length; i++) {
					final String id = "key " + i;
					final String fieldName = classType.getDeclaredFields()[i].getName();
					final String tableName = "key '" + convertFieldToTableName(fieldName) + "'";
					if (exceptionMessage.toString().contains(id) || exceptionMessage.toString().contains(tableName)) {
						fieldId = tabClass.getSimpleName().toLowerCase() + "Form:" + fieldName + "Id";
						final FacesContext context = FacesContext.getCurrentInstance();
						final UIViewRoot view = context.getViewRoot();
						uiComponentForm = view.findComponent(tabClass.getSimpleName().toLowerCase() + "Form");
						checkValues(uiComponentForm);
						UIComponent uiComponent = view.findComponent(fieldId);
						if (uiComponent == null) {
							fieldId = tabClass.getSimpleName().toLowerCase() + "Form:" + "tabView:" + fieldName + "Id";
							uiComponent = view.findComponent(fieldId);
						}
						break;
					}
				}
			}
		}
		MessageFactory.setServerMessage(fieldId, exceptionType);
		// MessageFactory.setGrowlMessage(FacesMessage.SEVERITY_INFO, fieldId,
		// exceptionType);
		log.info("Exception Type Code - " + exceptionType);
	}

	// ------------- Exception --------------------

	public static void getExceptionType(final Exception exception, final FunctionAction functionAction, final Class<?> classType, final String formName) {
		log.info("Exception Message - " + exception.getMessage());
		Writer exceptionMessage = new StringWriter();
		PrintWriter printWriter = new PrintWriter(exceptionMessage);
		exception.printStackTrace(printWriter);

		String exceptionType = "M7";
		String fieldId = null;
		if (functionAction.equals(FunctionAction.Delete)) {// Delete
			if (exceptionMessage.toString().contains("CONSTRAINT") && exceptionMessage.toString().contains("FOREIGN KEY") && exceptionMessage.toString().contains("REFERENCES")) {
				exceptionType = "M6"; // Transactions Exists! Can't Delete!
			}
		} else if (functionAction.equals(FunctionAction.Add)) {// Save
			if (exceptionMessage.toString().contains("Duplicate entry")) {
				exceptionType = "M8"; // Duplicate Entries Found!
				for (int i = 1; i < classType.getDeclaredFields().length; i++) {
					final String id = "key " + i;
					final String fieldName = classType.getDeclaredFields()[i].getName();
					final String tableName = "key '" + convertFieldToTableName(fieldName) + "'";
					if (exceptionMessage.toString().contains(id) || exceptionMessage.toString().contains(tableName)) {
						fieldId = formName + ":" + fieldName + "Id";
						final FacesContext context = FacesContext.getCurrentInstance();
						final UIViewRoot view = context.getViewRoot();
						final UIComponent uiComponentForm = view.findComponent(formName);
						checkValues(uiComponentForm);
						UIComponent uiComponent = view.findComponent(fieldId);
						if (uiComponent == null) {
							fieldId = formName + ":tabView:" + fieldName + "Id";
							uiComponent = view.findComponent(fieldId);
						}
						if (uiComponent != null) {
							uiComponent.getAttributes().put("styleClass", "ui-state-error");
						}
						break;
					}
				}
			}
		}
		MessageFactory.setServerMessage(fieldId, exceptionType);
		// MessageFactory.setGrowlMessage(FacesMessage.SEVERITY_INFO, fieldId,
		// exceptionType);
		log.info("Exception Type Code - " + exceptionType);
	}

	*//**
	 * 
	 * Exception Capturing for Dialogs
	 * 
	 * 
	 * 
	 * @param Exception
	 * 
	 *            Message
	 * 
	 * @param Function
	 * 
	 *            Action
	 * 
	 * @param Class
	 * 
	 *            Type
	 *//*

	public static void getExceptionTypeForDialog(final String exceptionMessage,

			final FunctionAction functionAction, final Class<?> classType) {

		log.info("Exception Message - " + exceptionMessage);

		String exceptionType = "M7";

		String fieldId = null;

		if (functionAction.equals(FunctionAction.Delete)) {// Delete

			if (exceptionMessage.contains("CONSTRAINT")

					&& exceptionMessage.contains("FOREIGN KEY")

					&& exceptionMessage.contains("REFERENCES")) {

				exceptionType = "M6"; // Transactions Exists! Can't Delete!

			}

		} else if (functionAction.equals(FunctionAction.Add)) {// Save

			if (exceptionMessage.contains("Duplicate entry")) {

				exceptionType = "M8"; // Duplicate Entries Found!

				log.info("Duplicate entry found...");

				for (int i = 1; i < classType.getDeclaredFields().length; i++) {

					final String id = "key " + i;

					final String fieldName = classType.getDeclaredFields()[i]

							.getName();

					final String tableName = "key '"

							+ convertFieldToTableName(fieldName) + "'";

					if (exceptionMessage.contains(id)

							|| exceptionMessage.contains(tableName)) {

						fieldId = classType.getSimpleName().toLowerCase()

								+ "Form:" + fieldName + "Id";

						final FacesContext context = FacesContext

								.getCurrentInstance();

						final UIViewRoot view = context.getViewRoot();

						final UIComponent uiComponentForm = view

								.findComponent(classType.getSimpleName()

										.toLowerCase() + "Form");

						checkValues(uiComponentForm);

						UIComponent uiComponent = view.findComponent(fieldId);

						if (uiComponent == null) {

							fieldId = classType.getSimpleName().toLowerCase()

									+ "Form:" + "modalDialog:" + fieldName

									+ "Id";

							uiComponent = view.findComponent(fieldId);

						}

						log.info("On Form:Field  - " + fieldId);

						// uiComponent.getAttributes().put("styleClass",

						// "ui-state-error");

						break;

					}

				}

			}

		}

		MessageFactory.setServerMessage(fieldId, exceptionType);
		// MessageFactory.setGrowlMessage(FacesMessage.SEVERITY_INFO, fieldId,
		// exceptionType);
		log.info("Exception Type Code - " + exceptionType);

	}

	private static String convertFieldToTableName(final String fieldName) {

		String tableName = "";

		for (int i = 0; i < fieldName.length(); i++) {

			if (Character.isUpperCase(fieldName.charAt(i))) {

				tableName += "_" + Character.toLowerCase(fieldName.charAt(i));

			} else {

				tableName += fieldName.charAt(i);

			}

		}

		return tableName;

	}

	*//**
	 * 
	 * To Reset Objects Instead of Creating New One
	 * 
	 * 
	 * 
	 * @author : Santosh J.
	 * 
	 * @param object
	 * 
	 *            Object to be reset
	 *//*

	public static void resetObject(final Object object) {

		Class className;

		Class<?> dataType;

		String fieldName;

		Field field;

		for (int index = 1; index < object.getClass().getDeclaredFields().length; index++) {

			className = object.getClass();

			dataType = object.getClass().getDeclaredFields()[index].getType();

			fieldName = object.getClass().getDeclaredFields()[index].getName();

			// get the reflected object

			try {

				field = className.getDeclaredField(fieldName);

				// set accessible true

				field.setAccessible(true);

				if (dataType.isPrimitive()) {

					if (dataType.equals(byte.class)) {

						field.set(object, 0);

					} else if (dataType.equals(short.class)) {

						field.set(object, 0);

					} else if (dataType.equals(int.class)) {

						field.set(object, 0);

					} else if (dataType.equals(long.class)) {

						field.set(object, 0L);

					} else if (dataType.equals(float.class)) {

						field.set(object, 0.0f);

					} else if (dataType.equals(double.class)) {

						field.set(object, 0.0d);

					} else if (dataType.equals(char.class)) {

						field.set(object, '\u0000');

					} else if (dataType.equals(boolean.class)) {

						field.set(object, false);

					}

				} else {

					field.set(object, null);

				}

			} catch (final Exception e) {

			}

		}

	}

	*//**
	 * 
	 * To convert a date from one format to another
	 * 
	 * 
	 * 
	 * @author : Santosh J.
	 * 
	 * @param date
	 * 
	 *            date to be format
	 * 
	 * @param fromFormat
	 * 
	 *            from format
	 * 
	 * @param toFormat
	 * 
	 *            to format
	 * 
	 * @return : convertedDateFormat Converted Date Format
	 * 
	 *//*

	public static String convertDateFormat(final String date,

			final String fromFormat, final String toFormat) {

		final String strippedValue = date;

		final Date d = convertToDate(strippedValue, fromFormat);

		if (d == null) {
			return "";
		}
		return convertDateFormat(d, toFormat);

	}

	public static String convertDateFormat(final Date date, final String toFormat) {
		if (date != null && !"".equals(toFormat)) {
			final SimpleDateFormat sdf = new SimpleDateFormat(toFormat);
			return sdf.format(date);
		} else {
			return "";
		}
	}

	*//**
	 * 
	 * To convert a string into date
	 * 
	 * 
	 * 
	 * @author : Santosh J.
	 * 
	 * @param date
	 * 
	 *            date to be format
	 * 
	 * @param format
	 * 
	 *            format
	 * 
	 * @return d converted date as Date object
	 * 
	 *//*

	public static Date convertToDate(final String date, final String format) {

		final SimpleDateFormat sdf = new SimpleDateFormat(format);

		sdf.setLenient(false);

		Date d = null;

		try {

			d = sdf.parse(date);

		} catch (final Exception e) {

			d = new Date();

		}

		return d;

	}

	public static Long getNextDate(final String curDate, final int nofDays) {

		Timestamp timestamp = new Timestamp(0L);

		final Calendar newCalendar = new GregorianCalendar();

		timestamp = convertDateToTimeStamp(curDate);

		newCalendar.setTimeInMillis(timestamp.getTime());

		newCalendar.add(Calendar.DATE, nofDays);

		return convertDateToLong(convertCalendarToDate(newCalendar));

	}

	public static Long getNextMonth(final String curDate, final int nofDays) {

		Timestamp timestamp = new Timestamp(0L);

		final Calendar newCalendar = new GregorianCalendar();

		timestamp = convertDateToTimeStamp(curDate);

		newCalendar.setTimeInMillis(timestamp.getTime());

		newCalendar.add(Calendar.MONTH, nofDays);

		return convertDateToLong(convertCalendarToDate(newCalendar));

	}

	public static Timestamp convertDateToTimeStamp(final String argDate) {

		final Date date = convertToDate(argDate, "yyyyMMdd");

		Timestamp timestamp = new Timestamp(0L);

		timestamp = new Timestamp(date.getTime());

		return timestamp;

	}

	public static Timestamp convertDateToTimeStamp(final Date argDate) {

		final Date date = argDate;

		Timestamp timestamp = new Timestamp(0L);

		timestamp = new Timestamp(date.getTime());

		return timestamp;

	}

	private static Date convertCalendarToDate(final Calendar calendar) {

		final String dates = calendar.get(Calendar.YEAR) * 10000

				+ (calendar.get(Calendar.MONTH) + 1) * 100

				+ calendar.get(Calendar.DATE) + "";

		final Date date = convertToDate(dates, "yyyyMMdd");

		return date;

	}

	public static Long convertDateToLong(final Date date) {

		Long convert = 0L;

		try {

			convert = convertDateToString(date);

		} catch (final Exception e) {

		}

		return convert;

	}

	public static Long convertDateToString(final Date date) {

		Long returnValue = 0L;

		if (date == null) {

			return returnValue;

		} else {

			final SimpleDateFormat sDFormat = new SimpleDateFormat("yyyyMMdd");

			final StringBuilder stringFormat = new StringBuilder(

					sDFormat.format(date));

			returnValue = Long.parseLong(stringFormat.toString());

			return returnValue;

		}

	}

	public String convertDateToStringFormat(final Date date) {

		final DateFormat df = new SimpleDateFormat(getUserDateFormat());

		final String reportDate = df.format(date);

		return reportDate;

	}

	*//**
	 * 
	 * Used to Convert Valid String to Date Format
	 * 
	 * 
	 * 
	 * @author RAHMAN
	 * 
	 * @param String
	 * 
	 *            dateStr
	 * 
	 * @return reportDate reportDate
	 * 
	 * @throws ParseException
	 * 
	 *             throws ParseException
	 *//*

	public Date convertStringToDateFormat(final String dateStr) {

		final DateFormat df = new SimpleDateFormat(getUserDateFormat());

		Date reportDate = null;

		try {

			reportDate = df.parse(dateStr);

			// log.info("conversion succeeded");

		} catch (ParseException e) {

			// log.info("convertion failed");

			e.printStackTrace();

		}

		return reportDate;

	}

	*//**
	 * 
	 *//*

	public Date convertStringToDateTimeFormat(final String dateStr) {
		DateFormat df = null;
		if (dateStr.length() == 5 && dateStr.split(":").length == 2) {
			df = new SimpleDateFormat(getUserTimeFormat());
		} else {
			df = new SimpleDateFormat(getUserDateAndTimeFormat());
		}
		Date reportDate = null;

		try {

			reportDate = df.parse(dateStr);

			// log.info("conversion succeeded");

		} catch (ParseException e) {

			// log.info("convertion failed");

			// e.printStackTrace();

		}

		return reportDate;

	}

	*//**
	 * 
	 * Used to Convert Time of Particular Date to 00:00:00 example - Date
	 * 
	 * 00:00:00
	 * 
	 * 
	 * 
	 * @param Date
	 * 
	 * @return Date
	 *//*

	public static Date roundDateDown(final Date date) {

		final Calendar calendar = Calendar.getInstance();

		calendar.setTime(date);

		calendar.set(Calendar.HOUR_OF_DAY, 0);

		calendar.set(Calendar.MINUTE, 0);

		calendar.set(Calendar.SECOND, 0);

		calendar.set(Calendar.MILLISECOND, 0);

		return calendar.getTime();

	}

	*//**
	 * 
	 * Used to Convert Time of Particular Date to 23:59:59 example - Date
	 * 
	 * 23:59:59
	 * 
	 * 
	 * 
	 * @param Date
	 * 
	 * @return Date
	 *//*

	public static Date roundDateUp(final Date date) {
		if (date != null) {
			final Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			calendar.set(Calendar.MILLISECOND, 999);
			return calendar.getTime();
		} else
			return null;
	}

	// get week day

	public static String getWeekDay(final Date date) throws ParseException {

		// String input_date= date;

		final String format1 = new SimpleDateFormat("EEEE").format(date);

		return format1;

	}

	// displaying date in format defined in User Table
	public String formateDate(final Date date) {
		if (date != null) {
			final SimpleDateFormat dateFormat = new SimpleDateFormat(getUserDateFormat());
			return dateFormat.format(date);
		} else {
			return "";
		}
	}

	// by Sachin displaying date and time in format defined in User Table

	public String formateDateAndTime(final Date date) {
		if (date != null) {
			final SimpleDateFormat dateFormat = new SimpleDateFormat(getUserDateAndTimeFormat());
			return dateFormat.format(date);
		} else {
			return "";
		}
	}

	public String getUserDateFormatHour() {

		String dateFormat = "";

		if (user.getDateFormat() != null && user.getDateFormat() != null) {

			dateFormat = user.getDateFormat().getSymbol() + " "

					+ user.getTimeFormat().getSymbol();

		} else if (user.getDateFormat() == null && user.getTimeFormat() == null) {

			dateFormat = "dd-MMM-yyyy HH:mm "; // check for default format

		} else if (user.getDateFormat() != null && user.getTimeFormat() == null) {

			dateFormat = user.getDateFormat().getSymbol() + " HH:mm";

		} else {

			dateFormat = "dd-MMM-yyyy " + user.getTimeFormat();

		}

		
		 * 
		 * if (dateFormat == null || "".equalsIgnoreCase(dateFormat)) {
		 * 
		 * dateFormat = "dd-MMM-yyyy HH:mm "; // check for default format
		 * 
		 * 
		 * 
		 * }
		 return dateFormat;

	}

	public String getUserTimeFormat() {

		String dateFormat = "";

		if (user.getTimeFormat() == null) {

			dateFormat = "HH:mm";

		} else {

			dateFormat = String.valueOf(user.getTimeFormat().getSymbol());

		}

		return dateFormat;

	}

	public String formateDateHour(final Date date) {
		if (date != null) {
			final SimpleDateFormat dateFormat = new SimpleDateFormat(getUserDateFormatHour());
			return dateFormat.format(date);
		} else {
			return "";
		}
	}

	public String formatHour(final Date date) {
		if (date != null) {
			final SimpleDateFormat dateFormat = new SimpleDateFormat(getFormatHour());
			return dateFormat.format(date);
		} else {
			return "";
		}
	}

	public String formatHour() {
		return formatHour(null);
	}

	public String getFormatHour() {
		String dateFormat = user.getTimeFormat().getSymbol();
		if (dateFormat == null || "".equalsIgnoreCase(dateFormat)) {
			dateFormat = "HH:mm "; // check for default format
		}
		return dateFormat;
	}

	public String formatArrivalDate(final Date date) {
		if (date != null) {
			final SimpleDateFormat dateFormat = new SimpleDateFormat(getArrivalDate());
			return dateFormat.format(date);
		} else {
			return "";
		}
	}

	private String getArrivalDate() {
		String dateFormat = user.getTimeFormat().getSymbol();
		if (dateFormat == null || "".equalsIgnoreCase(dateFormat)) {
			dateFormat = "ddmmyyyy"; // check for default format
		}
		return dateFormat;
	}

	public String formateDate1(final Date date) {
		if (date != null) {
			final SimpleDateFormat dateFormat = new SimpleDateFormat(getUserDateFormatDeparture());
			return dateFormat.format(date);
		} else {
			return "";
		}
	}

	private String getUserDateFormatDeparture() {
		String dateFormat = user.getDateFormat().getSymbol();
		if (dateFormat == null || "".equalsIgnoreCase(dateFormat)) {
			dateFormat = "yyyyddmm"; // check for default format
		}
		return dateFormat;
	}

	// Sachin

	// Exchange Rate Calculation

	
	 * 
	 * public static float getExchangeRateCalculation(float bankRate, float
	 * 
	 * percantage) { float exchangeRate; exchangeRate = ((bankRate / 100) *
	 * 
	 * (percantage)) + bankRate; return exchangeRate;
	 * 
	 * 
	 * 
	 * }
	 

	// added by bala

	public static float getExchangeRateCalculation(final float bankRate,

			final float percantage,

			final DynamicRateOperator selectedDynamicOperator) {

		float exchangeRate;

		if (selectedDynamicOperator.equals(DynamicRateOperator.Plus)) {

			exchangeRate = bankRate + bankRate / 100 * percantage;

		} else {

			exchangeRate = bankRate - bankRate / 100 * percantage;

		}

		return exchangeRate;

	}

	// Rajendra

	// Tax Structure Calculation - 'OnWhat' variable has been changed to

	// 'Method'

	public static List<TaxValueSplitStructure> getTaxValueSplitStructureList(

			final TaxStructure taxStructure, final double amount,

			final double discountedAmount) {

		double netTaxValue = 0.0;

		TaxValueSplitStructure taxValueSplitStructure;

		final List<TaxValueSplitStructure> taxValueSplitStructureList = new ArrayList<TaxValueSplitStructure>();

		if (taxStructure == null) {

			// log.info("Tax structure is null");

			MessageFactory.setServerMessage("M7"); // Add message
			// MessageFactory.setGrowlMessage(FacesMessage.SEVERITY_INFO, "M7",
			// "M7");
			return null;

		}

		// log.info("Calculating tax value for -" +

		// taxStructure.getTaxStructureName());

		// log.info("Amount -->" + amount);

		// log.info("Discount Amount --->"+ discountedAmount);

		for (int index = 0; index < taxStructure.getTaxStructureDetailsList()

				.size(); index++) {

			taxValueSplitStructure = new TaxValueSplitStructure();

			// log.info("Tax split for tax -->" +

			// taxStructure.getTaxStructureDetailsList().get(index).getTaxCodeId().getDescription());

			taxValueSplitStructure.setTaxStructureDetails(taxStructure

					.getTaxStructureDetailsList().get(index));

			taxValueSplitStructure.setTaxCalculatedValue(0.0);

			taxValueSplitStructureList.add(taxValueSplitStructure);

		}

		TaxValueSplitStructure currentTaxValueSplit;

		double currentTaxValue = 0;

		for (int index = 0; index < taxValueSplitStructureList.size(); index++) {

			currentTaxValueSplit = new TaxValueSplitStructure();

			currentTaxValueSplit = taxValueSplitStructureList.get(index);

			// log.info("Split ------------>" +index+1);

			// log.info("Calculation type is ---->" +

			// currentTaxValueSplit.getTaxStructureDetails().getCalculationType());

			// log.info("Tax on what ------------>" +

			// currentTaxValueSplit.getTaxStructureDetails().getOnWhat());

			if (currentTaxValueSplit.getTaxStructureDetails()

					.getCalculationType()

					.equals(TaxStructureCalculationType.Percentage)) {

				double taxAmount = 0.0;

				if (currentTaxValueSplit.getTaxStructureDetails().getMethod()

						.equals(TaxOn.OnTax)) {

					taxAmount = getOnTaxAmount(currentTaxValueSplit,

							taxValueSplitStructureList);

				}

				currentTaxValue = getTaxPercentageValue(currentTaxValueSplit

						.getTaxStructureDetails().getValue(), amount,

						discountedAmount, currentTaxValueSplit

								.getTaxStructureDetails().getMethod(),

						taxAmount);

			} else if (currentTaxValueSplit.getTaxStructureDetails()

					.getCalculationType()

					.equals(TaxStructureCalculationType.Amount)) { // Flat

				// has

				// been

				// changed

				// to Amount

				// according

				// to dharma

				// sir

				// suggession

				double taxAmount = 0.0;

				if (currentTaxValueSplit.getTaxStructureDetails().getMethod()

						.equals(TaxOn.OnTax)) {

					taxAmount = getOnTaxAmount(currentTaxValueSplit,

							taxValueSplitStructureList);

				}

				currentTaxValue = getTaxFlatValue(currentTaxValueSplit

						.getTaxStructureDetails().getValue(), amount,

						discountedAmount, currentTaxValueSplit

								.getTaxStructureDetails().getMethod(),

						taxAmount);

			} else if (currentTaxValueSplit.getTaxStructureDetails()

					.getCalculationType()

					.equals(TaxStructureCalculationType.Slab)) {

				if (currentTaxValueSplit.getTaxStructureDetails().getMethod()

						.equals(TaxOn.OnTax)) {

					getOnTaxAmount(currentTaxValueSplit,

							taxValueSplitStructureList);

				}

				// currentTaxValue =

				// getTotalTaxSlabValue(currentTaxValueSplit.getTaxStructureDetails().getTaxSlabCodeId().getTaxSlabList(),

				// amount, discountedAmount,

				// currentTaxValueSplit.getTaxStructureDetails().getMethod(),

				// taxAmount);

			}

			// log.info(" Calculated tax value -->" + currentTaxValue);

			currentTaxValueSplit.setTaxCalculatedValue(currentTaxValue);

			netTaxValue = netTaxValue + currentTaxValue;

		}

		// log.info("Net Tax value -->" + netTaxValue);

		return taxValueSplitStructureList;

	}

	public static double getTaxPercentageValue(final double taxDefinedValue,

			final double amount, final double discount, final TaxOn onWhat,

			final double taxAmount) {

		double tempPercentageValue = 0.0;

		if (onWhat.equals(TaxOn.OnValue)) {

			tempPercentageValue = amount * (taxDefinedValue / 100);

		} else if (onWhat.equals(TaxOn.OnDiscount)) {

			tempPercentageValue = discount * (taxDefinedValue / 100);

		} else if (onWhat.equals(TaxOn.OnTax)) {

			tempPercentageValue = taxAmount * (taxDefinedValue / 100);

		}

		return tempPercentageValue;

	}

	public static double getTaxFlatValue(final double taxDefinedValue,

			final double amount, final double discount, final TaxOn onWhat,

			final double taxAmount) {

		double tempFlatValue = 0.0;

		if (onWhat.equals(TaxOn.OnValue)) {

			tempFlatValue = taxDefinedValue;

		} else if (onWhat.equals(TaxOn.OnDiscount)) {

			tempFlatValue = taxDefinedValue;

		} else if (onWhat.equals(TaxOn.OnTax)) {

			tempFlatValue = taxDefinedValue;

		}

		return tempFlatValue;

	}

	
	 * 
	 * public static double getTotalTaxSlabValue(List<TaxSlab> taxSlabList,
	 * 
	 * double amount, double discount, TaxOn onWhat, double taxAmount) {
	 * 
	 * 
	 * 
	 * double tempSlabValue = 0.0;
	 * 
	 * 
	 * 
	 * for (int index = 0; index < taxSlabList.size(); index++) {
	 * 
	 * 
	 * 
	 * if (amount >= taxSlabList.get(index).getAmountFrom() && amount <=
	 * 
	 * taxSlabList.get(index).getAmountTo()) {
	 * 
	 * 
	 * 
	 * if
	 * 
	 * (taxSlabList.get(index).getCalculationTypeList().equals(CalculationType
	 * 
	 * .Percentage)) { tempSlabValue +=
	 * 
	 * getTaxPercentageValue(taxSlabList.get(index).getValue(), amount,
	 * 
	 * discount, onWhat, taxAmount); break; } else if
	 * 
	 * (taxSlabList.get(index).getCalculationTypeList
	 * 
	 * ().equals(CalculationType.Amount)) { tempSlabValue =
	 * 
	 * getTaxFlatValue(taxSlabList.get(index).getValue(), amount, discount,
	 * 
	 * onWhat, taxAmount); break; } } }
	 * 
	 * 
	 * 
	 * return tempSlabValue; }
	 

	private static double getOnTaxAmount(

			final TaxValueSplitStructure currentTaxValueSplit,

			final List<TaxValueSplitStructure> taxValueSplitStructureList) {

		double taxAmount = 0.0;

		final int taxNumber = currentTaxValueSplit.getTaxStructureDetails()

				.getOnTaxCodeId();

		taxAmount = taxValueSplitStructureList.get(taxNumber - 1)

				.getTaxCalculatedValue();

		// log.info(" Tax number -->" + taxNumber);

		// log.info(" tax Amount -->" + taxAmount);

		return taxAmount;

	}

	public static double getTaxStructureValue(final TaxStructure taxStructure,

			final double amount, final double discount) {

		double netTaxValue = 0.0;

		List<TaxValueSplitStructure> taxValueSplitStructureList = new ArrayList<TaxValueSplitStructure>();

		taxValueSplitStructureList = getTaxValueSplitStructureList(

				taxStructure, amount, discount);

		for (int index = 0; index < taxValueSplitStructureList.size(); index++) {

			netTaxValue += taxValueSplitStructureList.get(index)

					.getTaxCalculatedValue();

		}

		return netTaxValue;

	}

	public static int getDaysBetween(final Date d1, final Date d2) {

		final Calendar c1 = Calendar.getInstance();

		c1.setTime(d1);

		final Calendar c2 = Calendar.getInstance();

		c2.setTime(d2);

		return getDaysBetween(c1, c2);

	}

	public static int getDaysBetween(final Date d1, final Date d2,

			final boolean swapRequired) {

		final Calendar c1 = Calendar.getInstance();

		c1.setTime(d1);

		final Calendar c2 = Calendar.getInstance();

		c2.setTime(d2);

		return getDaysBetween(c1, c2, swapRequired);

	}

	public static StringBuilder getDateInSeason(final Date date) {

		final SimpleDateFormat dateformatMMDDYYYY = new SimpleDateFormat(

				"dd/MM");

		final StringBuilder startConvertedDate = new StringBuilder(

				dateformatMMDDYYYY.format(date));

		return startConvertedDate;

	}

	*//**
	 * 
	 * Calculates the number of days between two calendar days in a manner which
	 * 
	 * is independent of the Calendar type used.
	 * 
	 * 
	 * 
	 * @param d1
	 * 
	 *            The first date.
	 * 
	 * @param d2
	 * 
	 *            The second date.
	 * 
	 * @param swapRequired
	 * 
	 *            swapRequired
	 * 
	 * @return days The number of days between the two dates. Zero is returned
	 * 
	 *         if the dates are the same, one if the dates are adjacent, etc.
	 * 
	 *         The order of the dates does not matter, the value returned is
	 * 
	 *         always greater than or equal to 0.If Calendar types of d1 and d2
	 * 
	 *         are different, the result may not be accurate.
	 *//*

	public static int getDaysBetween(java.util.Calendar d1,

			java.util.Calendar d2, final boolean swapRequired) {

		if (swapRequired && d1.after(d2)) { // swap dates so that d1 is start

			// and d2 is end

			final java.util.Calendar swap = d1;

			d1 = d2;

			d2 = swap;

		}

		int days = d2.get(java.util.Calendar.DAY_OF_YEAR)

				- d1.get(java.util.Calendar.DAY_OF_YEAR);

		final int y2 = d2.get(java.util.Calendar.YEAR);

		if (d1.get(java.util.Calendar.YEAR) != y2) {

			d1 = (java.util.Calendar) d1.clone();

			do {

				days += d1.getActualMaximum(java.util.Calendar.DAY_OF_YEAR);

				d1.add(java.util.Calendar.YEAR, 1);

			} while (d1.get(java.util.Calendar.YEAR) != y2);

		}

		return days;

	}

	public static int getDaysBetween(java.util.Calendar d1,

			java.util.Calendar d2) {

		if (d1.after(d2)) { // swap dates so that d1 is start and d2 is end

			final java.util.Calendar swap = d1;

			d1 = d2;

			d2 = swap;

		}

		int days = d2.get(java.util.Calendar.DAY_OF_YEAR)

				- d1.get(java.util.Calendar.DAY_OF_YEAR);

		final int y2 = d2.get(java.util.Calendar.YEAR);

		if (d1.get(java.util.Calendar.YEAR) != y2) {

			d1 = (java.util.Calendar) d1.clone();

			do {

				days += d1.getActualMaximum(java.util.Calendar.DAY_OF_YEAR);

				d1.add(java.util.Calendar.YEAR, 1);

			} while (d1.get(java.util.Calendar.YEAR) != y2);

		}

		return days;

	}

	*//**
	 * 
	 * To Identity The Browser Type
	 * 
	 * 
	 * 
	 * @param headerInformation
	 * 
	 *            it will take headerInformation in the form of String
	 * 
	 * @return browserName it will return browser name in the String format
	 *//*

	public static String getBrowserName(final String headerInformation) {

		String browserName = "";

		String browserVersion = "";

		final String browser = headerInformation;

		if (browser.contains("MSIE")) {

			final String subsString = browser

					.substring(browser.indexOf("MSIE"));

			final String Info[] = subsString.split(";")[0].split(" ");

			browserName = "Internet Explorer";// Info[0];

			browserVersion = Info[1];

		} else if (browser.contains("Firefox")) {

			final String subsString = browser.substring(browser

					.indexOf("Firefox"));

			final String info[] = subsString.split(" ")[0].split("/");

			browserName = info[0];

			browserVersion = info[1];

		} else if (browser.contains("Chrome")) {

			final String subsString = browser.substring(browser

					.indexOf("Chrome"));

			final String info[] = subsString.split(" ")[0].split("/");

			browserName = info[0];

			browserVersion = info[1];

		} else if (browser.contains("Opera")) {

			final String subsString = browser.substring(browser

					.indexOf("Opera"));

			final String info[] = subsString.split(" ")[0].split("/");

			browserName = info[0];

			browserVersion = info[1];

		} else if (browser.contains("Safari")) {

			final String subsString = browser.substring(browser

					.indexOf("Safari"));

			final String info[] = subsString.split(" ")[0].split("/");

			browserName = info[0];

			browserVersion = info[1];

		}

		return browserName + " " + browserVersion;

	}

	*//**
	 * 
	 * To Identity The Browser Language
	 * 
	 * 
	 * 
	 * @param headerInformation
	 * 
	 *            headerInformation
	 * 
	 * @return browserLanguage browserLanguage
	 *//*

	public static String getBrowserLanguage(String headerInformation) {

		String browserLanguage = "";

		if (headerInformation.trim().length() > 0) {

			if (headerInformation.contains(",")) {

				headerInformation = headerInformation.substring(0,

						headerInformation.indexOf(","));

				if (headerInformation.contains(";")) {

					browserLanguage = headerInformation.substring(0,

							headerInformation.indexOf(";"));

				} else {

					browserLanguage = headerInformation;

				}

			} else {

				browserLanguage = headerInformation;

			}

		} else {

			browserLanguage = "";

		}

		return browserLanguage;

	}

	// ---- For getting Current Year and Next Year -------

	public static String getCurrentYear(final Date date) {

		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy");

		final Date currentDate = date;

		final String currentYear = sdf.format(currentDate);

		return currentYear;

	}

	public static String getNextYear(final Date date) {

		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy");

		String nextYear = sdf.format(date);

		int tempYear = Integer.parseInt(nextYear);

		tempYear = tempYear + 1;

		nextYear = String.valueOf(tempYear);

		return nextYear;

	}

	// --------- Random password alpha numerics ----------------------

	public static String generateRandomString(final int length) {

		final String alphabet = new String(

				"0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz _!@#$"); // 9

		final int n = alphabet.length();

		String result = new String();

		final Random r = new Random();

		for (int i = 0; i < length; i++) {

			result = result + alphabet.charAt(r.nextInt(n));

		}

		return result;

	}

	public static String generateRandomPassWord(final int passWordlength) {

		final Random rand = new Random();

		final char[] values1 = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',

				'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u',

				'v', 'w', 'x', 'y', 'z' };

		final char[] values2 = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',

				'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',

				'V', 'W', 'X', 'Y', 'Z' };

		final char[] values3 = { '@', '&', '$', '#', '_', '!' };

		final char[] values4 = { '1', '2', '3', '4', '5', '6', '7', '8', '9',

				'0' };

		String out = "";

		String out1 = "";

		String out2 = "";

		String out3 = "";

		String out4 = "";

		for (int i = 0; i < passWordlength - 6; i++) {

			final int idx = rand.nextInt(values1.length);

			out1 += values1[idx];

		}

		for (int i = 0; i < passWordlength - 6; i++) {

			final int idx = rand.nextInt(values2.length);

			out2 += values2[idx];

		}

		for (int i = 0; i < passWordlength - (passWordlength - 1); i++) {

			final int idx = rand.nextInt(values3.length);

			out3 += values3[idx];

		}

		for (int i = 0; i < passWordlength - 5; i++) {

			final int idx = rand.nextInt(values4.length);

			out4 += values4[idx];

		}

		out = out1.concat(out2).concat(out3).concat(out4);

		return out;

	}

	// ------------ Password Encryption -------------------------

	private static final String UNICODE_FORMAT = "UTF8";

	public static final String DESEDE_ENCRYPTION_SCHEME = "DESede";

	private static KeySpec ks;

	private static SecretKeyFactory skf;

	private static Cipher cipher;

	private static byte[] arrayBytes;

	private static String myEncryptionKey;

	private static String myEncryptionScheme;

	static SecretKey key;

	static {

		try {

			myEncryptionKey = "ThisIsSpartaThisIsSparta";

			myEncryptionScheme = DESEDE_ENCRYPTION_SCHEME;

			arrayBytes = myEncryptionKey.getBytes(UNICODE_FORMAT);

			ks = new DESedeKeySpec(arrayBytes);

			skf = SecretKeyFactory.getInstance(myEncryptionScheme);

			cipher = Cipher.getInstance(myEncryptionScheme);

			key = skf.generateSecret(ks);

		} catch (final Exception e) {

			e.printStackTrace();

		}

	}

	
	 * public static String encrypt(final String unencryptedString) {
	 * 
	 * String encryptedString = null;
	 * 
	 * try {
	 * 
	 * cipher.init(Cipher.ENCRYPT_MODE, key);
	 * 
	 * final byte[] plainText = unencryptedString.getBytes(UNICODE_FORMAT);
	 * 
	 * final byte[] encryptedText = cipher.doFinal(plainText);
	 * 
	 * encryptedString = new String(Base64.encodeBase64(encryptedText));
	 * 
	 * } catch (final Exception e) {
	 * 
	 * e.printStackTrace();
	 * 
	 * }
	 * 
	 * return encryptedString;
	 * 
	 * }
	 

	
	 * public static String decrypt(final String encryptedString) {
	 * 
	 * String decryptedText = null;
	 * 
	 * try {
	 * 
	 * cipher.init(Cipher.DECRYPT_MODE, key);
	 * 
	 * final byte[] encryptedText = Base64.decodeBase64(encryptedString);
	 * 
	 * final byte[] plainText = cipher.doFinal(encryptedText);
	 * 
	 * decryptedText = new String(plainText);
	 * 
	 * } catch (final Exception e) {
	 * 
	 * e.printStackTrace();
	 * 
	 * }
	 * 
	 * return decryptedText;
	 * 
	 * }
	 

	public static String formateApplicationModules(final List<ApplicableModules> applicableModules) {
		String returnValue = "";
		if (applicableModules != null) {
			for (final ApplicableModules module : applicableModules) {
				returnValue += module.getSymbol() + ", ";
			}
			if (returnValue.length() > 0) {
				returnValue = returnValue.substring(0, returnValue.length() - 2);
			}
		}
		return returnValue;
	}

	*//**
	 * Convert List of 'RevenueCodeApplicableModules-Codes' to list of 'RevenueCodeApplicableModules-Name' Used in RevenueCode Functionality
	 * 
	 * @author Shreyas
	 * @param revenueCodeApplicableModule
	 * @return
	 *//*

	public static String formatRevenueCodeApplicableModules(final List<RevenueCodeApplicableModules> revenueCodeApplicableModule) {
		String returnValue = "";
		if (revenueCodeApplicableModule != null) {
			for (final RevenueCodeApplicableModules tempRevenueCodeApplicableModule : revenueCodeApplicableModule) {
				returnValue += tempRevenueCodeApplicableModule.getSymbol() + ", ";
			}
			if (returnValue.length() > 0) {
				returnValue = returnValue.substring(0, returnValue.length() - 2);
			}
		}
		return returnValue;
	}

	public static String formateApplicationModule(final List<ReasonModuleTasks> reasonModuleTasks) {
		String returnValue = "";
		if (reasonModuleTasks != null) {
			for (final ReasonModuleTasks module : reasonModuleTasks) {
				returnValue += module.getSymbol() + ", ";
			}
			if (returnValue.length() > 0) {
				returnValue = returnValue.substring(0, returnValue.length() - 2);
			}
		}
		return returnValue;
	}

	public static String formateDesignationType(final List<DesignationType> designationTypes) {
		String returnValue = "";
		if (designationTypes != null) {
			for (final DesignationType type : designationTypes) {
				returnValue += type.getSymbol() + ", ";
			}
			if (returnValue.length() > 0) {
				returnValue = returnValue.substring(0, returnValue.length() - 2);
			}
		}
		return returnValue;
	}

	public static String formateSection(final List<Section> sectionList) {
		String returnValue = "";
		if (sectionList != null) {
			for (final Section section : sectionList) {
				returnValue += section.getSectionName() + ", ";
			}
			if (returnValue.length() > 0) {
				returnValue = returnValue.substring(0, returnValue.length() - 2);
			}
		}
		return returnValue;
	}

	public static void copyProperties(final Object fromObj, final Object toObj) {

		final Class<? extends Object> fromClass = fromObj.getClass();

		final Class<? extends Object> toClass = toObj.getClass();

		try {

			final BeanInfo fromBean = Introspector.getBeanInfo(fromClass);

			final BeanInfo toBean = Introspector.getBeanInfo(toClass);

			final PropertyDescriptor[] toPd = toBean.getPropertyDescriptors();

			final List<PropertyDescriptor> fromPd = Arrays.asList(fromBean

					.getPropertyDescriptors());

			for (final PropertyDescriptor propertyDescriptor : toPd) {

				propertyDescriptor.getDisplayName();

				final PropertyDescriptor pd = fromPd.get(fromPd

						.indexOf(propertyDescriptor));

				if (pd.getDisplayName().equals(

						propertyDescriptor.getDisplayName())

						&& !pd.getDisplayName().equals("class")) {

					if (propertyDescriptor.getWriteMethod() != null) {

						propertyDescriptor.getWriteMethod().invoke(toObj,

								pd.getReadMethod().invoke(fromObj, null));

					}

				}

			}

		} catch (final IntrospectionException e) {

			e.printStackTrace();

		} catch (final IllegalArgumentException e) {

			e.printStackTrace();

		} catch (final IllegalAccessException e) {

			e.printStackTrace();

		} catch (final InvocationTargetException e) {

			e.printStackTrace();

		}

	}

	// rp Swamy

	public static String formatContactsAddress(final List<Contacts> contacts) {
		String returnValue = "";
		if (contacts != null) {
			for (final Contacts contact : contacts) {
				returnValue += contact.getAddress1() + ", ";
			}
			if (returnValue.length() > 0) {
				returnValue = returnValue.substring(0, returnValue.length() - 2);
			}
		}
		return returnValue;
	}

	public static String formatContactsEmail(final List<Contacts> contacts) {
		String returnValue = "";
		if (contacts != null) {
			for (final Contacts contact : contacts) {
				returnValue += contact.getOfficialMailId() + ", ";
			}
			if (returnValue.length() > 0) {
				returnValue = returnValue.substring(0, returnValue.length() - 2);
			}
		}
		return returnValue;
	}

	public static String formatContactsURL(final List<Contacts> contacts) {
		String returnValue = "";
		if (contacts != null) {

		}
		for (final Contacts contact : contacts) {
			returnValue += contact.getWeb() + ", ";
		}
		if (returnValue.length() > 0) {
			returnValue = returnValue.substring(0, returnValue.length() - 2);
		}
		return returnValue;
	}

	// Added By Rajendra For Mail Validation

	public static boolean isValidEmail(final String email) {

		if (email != null || !"".equalsIgnoreCase(email)) {

			final String regEx = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\\b";

			final Pattern p = Pattern.compile(regEx);

			final Matcher m = p.matcher(email);

			if (m.find()) {

				// log.info(email + " is a valid email address.");

				return true;

			} else {

				// log.info(email + " is a invalid email address");

				return false;

			}

		} else {

			return false;

		}

	}

	// Added By Rajendra For Laundry Setup Round off currencies displayed in

	// data table

	public static String formateCurrencyRoundOffs(final List<RevenueCodeRoundOff> roundOffList) {
		String returnValue = "";
		if (roundOffList != null) {
			for (final RevenueCodeRoundOff section : roundOffList) {
				if (section.getRoundOffValue() != 0 && section.getRoundOffType().name() != null && section.getRoundOffType().name().length() != 0) {
					returnValue += section.getCurrencyId().getCountryId().getCurrencyName() + ", ";
				}
			}
			if (returnValue.length() > 0) {
				returnValue = returnValue.substring(0, returnValue.length() - 2);
			}
		}
		return returnValue;
	}

	public static Date dateTimeConcatenation(final Date selecteddate,

			final String selectedTime) {

		final Calendar calendar = Calendar.getInstance();

		if (selectedTime != null) {

			final int hour = Integer.parseInt(selectedTime.substring(0, 2));

			final int minute = Integer.parseInt(selectedTime.substring(3, 5));

			calendar.setTime(selecteddate);

			calendar.set(Calendar.HOUR_OF_DAY, hour);

			calendar.set(Calendar.MINUTE, minute);

			calendar.set(Calendar.SECOND, 0);

			calendar.set(Calendar.MILLISECOND, 0);

		}

		return calendar.getTime();

	}

	public static Date dateTimeConcatenation(final Date selecteddate,

			final Date selectedTime) {

		final Calendar calendar = Calendar.getInstance();

		final Calendar timeCal = Calendar.getInstance();

		if (selectedTime != null) {

			timeCal.setTime(selectedTime);

			final int hour = timeCal.get(Calendar.HOUR_OF_DAY);

			final int minute = timeCal.get(Calendar.MINUTE);

			calendar.setTime(selecteddate);

			calendar.set(Calendar.HOUR_OF_DAY, hour);

			calendar.set(Calendar.MINUTE, minute);

			calendar.set(Calendar.SECOND, 0);

			calendar.set(Calendar.MILLISECOND, 0);

		}

		return calendar.getTime();

	}

	// By Sachin to Round offf the values

	public static Double roundoffValues(final double value,

			final long decimalPlaces) {

		final double valueIn = value;

		if (value != 0) {

			if (decimalPlaces == 1) {

				final DecimalFormat twoDForm = new DecimalFormat("#.0");

				return Double.parseDouble(twoDForm.format(valueIn));

			} else if (decimalPlaces == 2) {

				final DecimalFormat twoDForm = new DecimalFormat("#.00");

				return Double.parseDouble(twoDForm.format(valueIn));

			} else if (decimalPlaces == 3) {

				final DecimalFormat twoDForm = new DecimalFormat("#.000");

				return Double.parseDouble(twoDForm.format(valueIn));

			} else if (decimalPlaces == 4) {

				final DecimalFormat twoDForm = new DecimalFormat("#.0000");

				// 5 and 6 Added by Ravi Periyasami

				return Double.parseDouble(twoDForm.format(valueIn));

			} else if (decimalPlaces == 5) {

				final DecimalFormat twoDForm = new DecimalFormat("#.00000");

				return Double.parseDouble(twoDForm.format(valueIn));

			} else if (decimalPlaces == 6) {

				final DecimalFormat twoDForm = new DecimalFormat("#.000000");

				return Double.parseDouble(twoDForm.format(valueIn));

			}

		}

		return valueIn;

	}

	public static String roundoffDoubleValue(final double value,

			final long decimalPlaces) {

		final double valueIn = value;

		String exchangeRate = "" + value;

		if (value != 0) {

			if (decimalPlaces == 1) {

				final DecimalFormat twoDForm = new DecimalFormat("#.0");

				exchangeRate = (twoDForm.format(valueIn));

			} else if (decimalPlaces == 2) {

				final DecimalFormat twoDForm = new DecimalFormat("#.00");

				exchangeRate = (twoDForm.format(valueIn));

			} else if (decimalPlaces == 3) {

				final DecimalFormat twoDForm = new DecimalFormat("#.000");

				exchangeRate = (twoDForm.format(valueIn));

			} else if (decimalPlaces == 4) {

				final DecimalFormat twoDForm = new DecimalFormat("#.0000");

				// 5 and 6 Added by Ravi Periyasami

				exchangeRate = (twoDForm.format(valueIn));

			} else if (decimalPlaces == 5) {

				final DecimalFormat twoDForm = new DecimalFormat("#.00000");

				exchangeRate = (twoDForm.format(valueIn));

			} else if (decimalPlaces == 6) {

				final DecimalFormat twoDForm = new DecimalFormat("#.000000");

				exchangeRate = (twoDForm.format(valueIn));

			}

		}

		return exchangeRate;

	}

	// Sachin

	// Exchange Rate Calculation

	public static double percentageCalculation(final Double bankRate,

			final Double percantage) {

		double exchangeRate;

		exchangeRate = bankRate * (percantage / 100);

		return exchangeRate;

	}

	// Sachin

	// Tax Inclusive

	public static double getTotalTaxForTaxInclusive(

			final TaxStructure taxStructure) {

		double totalTax = 0;

		for (final TaxStructureDetails details : taxStructure

				.getTaxStructureDetailsList()) {

			totalTax = totalTax + details.getValue();

		}

		return totalTax;

	}

	public static double calculatingAmountAfterTax(final double amount,

			final double tax, final double totaTax, final int type) {

		double amountAfterTax = 0;

		if (type == 1) {// if only amount is required then total tax and amount

			// is used for calculation Type 1 for amountafter tax

			amountAfterTax = amount * 100 / (100 + totaTax);

		} else if (type == 2) {// if tax for specific is required then, tax,

			// totaltax, amount is used in here

			amountAfterTax = amount * 100 * tax / (100 * (100 + totaTax));

		}

		return amountAfterTax;

	}

	public static List<TaxValueSplitStructure> getTaxInclusiveStructureList(

			final TaxStructure taxStructure, final double amount) {

		final List<TaxValueSplitStructure> taxValueSplitStructureList = new ArrayList<TaxValueSplitStructure>();

		for (final TaxStructureDetails details : taxStructure

				.getTaxStructureDetailsList()) {

			final TaxValueSplitStructure taxValueSplitStructure = new TaxValueSplitStructure();

			taxValueSplitStructure.setTaxStructureDetails(details);

			if (details.getMethod().equals(TaxOn.OnValue)) {

				taxValueSplitStructure

						.setTaxCalculatedValue(calculatingAmountAfterTax(

								amount, details.getValue(),

								getTotalTaxForTaxInclusive(taxStructure), 2));

				// the above line will return the calculated tax for each of the

				// tax structure detail

			}

			taxValueSplitStructureList.add(taxValueSplitStructure);

		}

		return taxValueSplitStructureList;

	}

	// Added By Rajendra For RoomRack Opposite and Connecting Rooms displayed in

	// data table

	public static String formateRoomNumbers(final List<Room> roomList) {
		String returnValue = "";
		if (roomList != null) {
			for (final Room room : roomList) {
				returnValue += room.getRoomNumber() + ", ";
			}
			if (returnValue.length() > 0) {
				returnValue = returnValue.substring(0, returnValue.length() - 2);
			}
		}
		return returnValue;
	}

	public static String formateRevenue(final List<RevenueCode> revenueList) {
		String returnValue = "";
		if (revenueList != null) {
			for (final RevenueCode revenueCode : revenueList) {
				returnValue += revenueCode.getRevenueName() + ", ";
			}
			if (returnValue.length() > 0) {
				returnValue = returnValue.substring(0, returnValue.length() - 2);
			}
		}
		return returnValue;
	}

	public static Date getDate(final int days) {

		final int MILLIS_IN_DAY = 1000 * 60 * 60 * 24 * days;

		final Date date = new Date();

		final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");

		final String prevDate = dateFormat.format(date.getTime()

				- MILLIS_IN_DAY);

		final DateFormat df = new SimpleDateFormat("dd/MM/yy");

		Date previousDate;

		try {

			previousDate = df.parse(prevDate);

		} catch (final ParseException e) {
			previousDate = new Date();
		}

		return previousDate;

	}

	public static String getYear(final Date date) {

		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy");

		String nextYear = sdf.format(date);

		final int tempYear = Integer.parseInt(nextYear);

		nextYear = String.valueOf(tempYear);

		return nextYear;

	}

	public String formateYear(final Date date) {
		if (date != null) {
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			String nextYear = sdf.format(date);
			final int tempYear = Integer.parseInt(nextYear);
			nextYear = String.valueOf(tempYear);
			return nextYear;
		} else {
			return null;
		}
	}

	public static String formateGuestClassification(final List<GuestClassification> guestClassifications) {
		String returnValue = "";
		if (guestClassifications != null) {
			for (final GuestClassification classification : guestClassifications) {
				returnValue += classification.getName() + ", ";
			}
			if (returnValue.length() > 0) {
				returnValue = returnValue.substring(0, returnValue.length() - 2);
			}
		}
		return returnValue;
	}

	public static Date getNextDate(final Date currentDate,

			final long numberOfDays) {

		Timestamp timestamp = new Timestamp(0L);

		final Calendar newCalendar = new GregorianCalendar();

		timestamp = convertDateToTimeStamp(currentDate);

		newCalendar.setTimeInMillis(timestamp.getTime());

		newCalendar.add(Calendar.DATE, (int) numberOfDays);

		return newCalendar.getTime();

	}

	public static Date getPrevDate(final Date currentDate,

			final long numberOfDays) {

		Timestamp timestamp = new Timestamp(0L);

		final Calendar newCalendar = new GregorianCalendar();

		timestamp = convertDateToTimeStamp(currentDate);

		newCalendar.setTimeInMillis(timestamp.getTime());

		newCalendar.add(Calendar.DATE, (((int) numberOfDays) * -1));

		return newCalendar.getTime();

	}

	public static Date getNextMonth(final Date currentDate,

			final long numberOfDays) {

		Timestamp timestamp = new Timestamp(0L);

		final Calendar newCalendar = new GregorianCalendar();

		timestamp = convertDateToTimeStamp(currentDate);

		newCalendar.setTimeInMillis(timestamp.getTime());

		newCalendar.add(Calendar.MONTH, (int) numberOfDays);

		return newCalendar.getTime();

	}

	public static Date getNextMonthFirstDate(final Date currentDate,

			final long numberOfDays) {

		Timestamp timestamp = new Timestamp(0L);

		final Calendar newCalendar = new GregorianCalendar();

		timestamp = convertDateToTimeStamp(currentDate);

		newCalendar.setTimeInMillis(timestamp.getTime());

		newCalendar.add(Calendar.MONTH, (int) numberOfDays);

		newCalendar.set(Calendar.DATE, 1);

		return newCalendar.getTime();

	}

	public static Calendar getNextCalender(final Date currentDate,

			final long numberOfDays) {

		Timestamp timestamp = new Timestamp(0L);

		final Calendar newCalendar = new GregorianCalendar();

		timestamp = convertDateToTimeStamp(currentDate);

		newCalendar.setTimeInMillis(timestamp.getTime());

		newCalendar.add(Calendar.DATE, (int) numberOfDays);

		return newCalendar;

	}

	public static Date getAccountingDate(final long propertyId) {

		// return abcd.getAccountingDateFromService(propertyId);

		return new Date();

	}

	public static Date convertTimeStampToDate(final Timestamp timestamp) {

		return new Date(timestamp.getTime());

	}

	public static String formateTotalRooms(final List<RoomTypeApplicableDate> reasonModuleTasks) {
		String returnValue = "";
		if (reasonModuleTasks != null) {
			for (final RoomTypeApplicableDate module : reasonModuleTasks) {
				returnValue += module.getTotalRooms() + ", ";
			}
			if (returnValue.length() > 0) {
				returnValue = returnValue.substring(0, returnValue.length() - 2);
			}
		}
		return returnValue;
	}

	public static String formateRoomFeatures(final List<RoomFeature> roomFeatures) {
		String returnValue = "";
		if (roomFeatures != null) {
			for (final RoomFeature features : roomFeatures) {
				returnValue += features.getRoomFeatureDescription() + ", ";
			}
			if (returnValue.length() > 0) {
				returnValue = returnValue.substring(0, returnValue.length() - 2);
			}
		}
		return returnValue;
	}

	// CHECK: NOT VALIDATED CLEAN THIS METHOD - ELANGO

	// to convert current date/time to respective country date/time based on gmt

	// difference

	public static Date getRespectiveTime(final Property property) {

		final Date date = new Date();

		// getting GMT difference of current property's country

		String gmtValue = new String();

		if (CommonUtility.isValidObject(property)) {
			gmtValue = property.getTimeZone().trim();
		}

		// creating one Calendar instance

		final Calendar cal = Calendar.getInstance();

		// setting our date object to calendar object(in our case this is

		// GMT+5:30)

		cal.setTime(date);

		// converting indain time to standard GMT time

		cal.add(Calendar.HOUR, -5);

		cal.add(Calendar.MINUTE, -30);

		final Date gmtTime = cal.getTime();

		// reading GMT difference

		gmtValue = gmtValue.trim();

		final char symobl = gmtValue.charAt(3);

		final String timeValue = gmtValue.substring(4);

		final StringTokenizer st = new StringTokenizer(timeValue, ":");

		String hours = st.nextToken();

		String minutes = st.nextToken();

		if (symobl == '-') {

			hours = '-' + hours;

			minutes = '-' + minutes;

		}

		final int hh = Integer.parseInt(hours);

		final int mm = Integer.parseInt(minutes);

		// converting GMT time to respective country time

		cal.setTime(gmtTime);

		cal.add(Calendar.HOUR, hh);

		cal.add(Calendar.MINUTE, mm);

		final Date requiredTime = cal.getTime();

		return requiredTime;

	}

	public static String formateClassification(final List<Classification> classifications) {
		String returnValue = "";
		if (classifications != null) {
			for (final Classification classification : classifications) {
				returnValue += classification.getName() + ", ";
			}
			if (returnValue.length() > 0) {
				returnValue = returnValue.substring(0, returnValue.length() - 2);
			}
		}
		return returnValue;
	}

	public static String formateRevenueCode(final List<RevenueCode> revenueList) {
		String returnValue = "";
		if (revenueList != null) {
			for (final RevenueCode revenueCode : revenueList) {
				returnValue += revenueCode.getRevenueCode() + ", ";
			}
			if (returnValue.length() > 0) {
				returnValue = returnValue.substring(0, returnValue.length() - 2);
			}
		}
		return returnValue;
	}

	public Date getTimeOnly(final Date date) {

		String timeFormat;

		timeFormat = getFormatHour();

		final SimpleDateFormat formatter = new SimpleDateFormat(timeFormat);

		try {

			return formatter.parse(formatter.format(date));

		} catch (final ParseException e) {
			e.printStackTrace();
		}

		return date;

	}

	public Date getOnlyDate(final Date date) {
		String dateFormat = getUserDateFormat();
		final SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		try {
			return formatter.parse(formatter.format(date));
		} catch (final ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	*//**
	 * This method is used to display connectingRoomList in data table in comma separated format
	 * 
	 * @param connectingRoomList
	 *            connectingRoomList
	 * @return returnValue returnValue
	 *//*
	public static String formateConnectionRoomList(final List<Room> connectingRoomList) {
		String returnValue = "";
		if (connectingRoomList != null) {
			for (final Room roomVar : connectingRoomList) {
				returnValue += roomVar.getRoomNumber() + ", ";
			}
			if (returnValue.length() > 0) {
				returnValue = returnValue.substring(0, returnValue.length() - 2);
			}
		}
		return returnValue;
	}

	public static String formateTaxModules(final TaxModules taxModules) {
		String returnValue = "";
		if (taxModules != null) {
			returnValue += taxModules.getSymbol();
			if (returnValue.length() > 0) {
				returnValue = returnValue.substring(0, returnValue.length() - 2);
			}
		}
		return returnValue;
	}

	 For Room block 

	public static String getPattern(final String str) {

		if (str == "Monday") {

			return "w#1/7/2008~1/8/2008";

		} else if (str == "Tuesday") {

			return "w#1/8/2008~1/9/2008";

		} else if (str == "Wednesday") {

			return "w#1/9/2008~1/10/2008";

		} else if (str == "Thrusday") {

			return "w#1/10/2008~1/11/2008";

		} else if (str == "Friday") {

			return "w#1/11/2008~1/12/2008";

		} else if (str == "Saturday") {

			return "w#1/5/2008~1/6/2008";

		} else if (str == "Sunday") {

			return "w#1/6/2008~1/7/2008";

		}

		return str;

	}

	----------------------------Start----------------------------------------------------

	
	 * 
	 * To read full name (Fisat /Last/ Middle Name) according to user
	 * 
	 * configuration file
	 

	public static String fetchFullNames(final GuestProfile guestProfile) {

		final StringBuffer retValue = new StringBuffer();

		if (guestProfile != null) {

			if (guestProfile.getLastName() != null

					&& guestProfile.getLastName().trim().length() > 0) {

				retValue.append(guestProfile.getLastName().trim());

			}

			if (guestProfile.getMiddleName() != null

					&& guestProfile.getMiddleName().trim().length() > 0) {

				retValue.append(" " + guestProfile.getMiddleName().trim());

			}

			if (guestProfile.getFirstName() != null

					&& guestProfile.getFirstName().trim().length() > 0) {

				retValue.append(" " + guestProfile.getFirstName().trim());

			}

		}

		return retValue.toString();

	}

	public static String getNameConfiguration(final SwitchConfiguration switchConfiguration, final GuestProfile profile) {
		String ret = null;
		if (profile != null) {
			if (switchConfiguration != null && switchConfiguration.getSingleNewValue() != null) {
				ret = fetchNameBaedOn(switchConfiguration.getSingleNewValue(), profile);
			} else if (switchConfiguration != null && switchConfiguration.getSwitchesId() != null && switchConfiguration.getSwitchesId().getDefaultValue() != null) {
				ret = fetchNameBaedOn(switchConfiguration.getSwitchesId().getDefaultValue(), profile);
			}
		}
		return ret;
	}

	*//**
	 * 
	 * @author pramod
	 * @param switchConfiguration
	 * @param user
	 * @return
	 *//*
	public static String getUserNameConfiguration(final SwitchConfiguration switchConfiguration, final User user) {
		String ret = null;
		if (user != null) {
			if (switchConfiguration != null && switchConfiguration.getSingleNewValue() != null) {
				ret = fetchUserNameBaedOn(switchConfiguration.getSingleNewValue(), user);
			} else if (switchConfiguration != null && switchConfiguration.getSwitchesId() != null && switchConfiguration.getSwitchesId().getDefaultValue() != null) {
				ret = fetchUserNameBaedOn(switchConfiguration.getSwitchesId().getDefaultValue(), user);
			}
		}
		return ret;
	}

	public String guestNameBasedOnSwitchConfiguration(

			final SwitchConfiguration switchConfiguration,

			final GuestProfile profile) {

		String ret = null;

		if (switchConfiguration != null

				&& switchConfiguration.getSingleNewValue() != null) {

			ret = fetchNameBaedOn(switchConfiguration.getSingleNewValue(),

					profile);

		} else if (switchConfiguration != null

				&& switchConfiguration.getSwitchesId().getDefaultValue() != null) {

			ret = fetchNameBaedOn(switchConfiguration.getSwitchesId()

					.getDefaultValue(), profile);

		}

		return ret;

	}

	public static String fetchNameBaedOn(final String string,

			final GuestProfile guestProfile) {

		String[] abc;

		final StringBuffer ret = new StringBuffer();

		if (guestProfile != null) {

			abc = giveSplit(string);

			for (int i = 0; i <= 2; i++) {

				if (abc[i].equalsIgnoreCase("First") || abc[i].startsWith("f")

						|| abc[i].startsWith("F")) {

					ret.append(guestProfile.getFirstName() == null

							|| guestProfile.getFirstName() == "" ? ""

									: guestProfile.getFirstName());

					if (guestProfile.getFirstName() != null)
						if (!guestProfile.getFirstName().equals("") && guestProfile.getFirstName().length() > 0) { // added
																													// by
																													// pramod.k
																													// for
																													// space
																													// if
																													// name
																													// is
																													// not
																													// blank
							ret.append(" "); // bug id 25695 fixed
						}

				} else if (abc[i].equalsIgnoreCase("Last")

						|| abc[i].startsWith("l") || abc[i].startsWith("L")) {

					ret.append(guestProfile.getLastName() == null

							|| guestProfile.getLastName() == "" ? ""

									: guestProfile.getLastName());

					if (guestProfile.getLastName() != null)
						if (!guestProfile.getLastName().trim().equals("") && guestProfile.getLastName().length() > 0) {// added
																														// by
																														// pramod.k
																														// for
																														// space
							ret.append(" "); // if name is not blank
						}

				} else if (abc[i].equalsIgnoreCase("Middle")

						|| abc[i].startsWith("m") || abc[i].startsWith("M")) {

					ret.append(guestProfile.getMiddleName() == null

							|| guestProfile.getMiddleName() == "" ? ""

									: guestProfile.getMiddleName());

					if (guestProfile.getMiddleName() != null)
						if (!guestProfile.getMiddleName().trim().equals("") && guestProfile.getMiddleName().length() > 0) {// added
																															// by
																															// pramod.k
																															// for
																															// space
							ret.append(" "); // if name is not blank
						}
				}

			}

		}

		// Commented by Ravi Periyasamy 18-07-2013 because it is duplicating two

		// times

		// ret.append(ret.toString().trim());

		return ret.toString().trim(); // ret.toString();

	}

	public static String fetchUserNameBaedOn(final String string,

			final User user) {

		String[] abc;

		final StringBuffer ret = new StringBuffer();

		if (user != null) {

			abc = giveSplit(string);

			for (int i = 0; i <= 2; i++) {

				if (abc[i].equalsIgnoreCase("First") || abc[i].startsWith("f")

						|| abc[i].startsWith("F")) {

					ret.append(user.getFirstName() == null

							|| user.getFirstName() == "" ? ""

									: user.getFirstName() + " ");

				} else if (abc[i].equalsIgnoreCase("Last")

						|| abc[i].startsWith("l") || abc[i].startsWith("L")) {

					ret.append(user.getLastName() == null

							|| user.getLastName() == "" ? ""

									: user.getLastName() + " ");

				} else if (abc[i].equalsIgnoreCase("Middle")

						|| abc[i].startsWith("m") || abc[i].startsWith("M")) {

					ret.append(user.getMiddleName() == null

							|| user.getMiddleName() == "" ? ""

									: user.getMiddleName() + " ");

				}

			}

		}
		return ret.toString().trim(); // ret.toString();

	}

	private static String[] giveSplit(final String string) {

		String[] abc;

		abc = string.split("/");

		abc[0] = abc[0].trim();

		abc[1] = abc[1].trim();

		abc[2] = abc[2].trim();

		return abc;

	}

	----------------------------------End-----------------------------------------------

	public static String formatesubmodule(final List<BroadcastMessageSubModule> submodule) {
		String returnValue = "";
		if (submodule != null) {
			for (final BroadcastMessageSubModule broadcastsubmodule : submodule) {
				returnValue += broadcastsubmodule.getSymbol() + ", ";
			}
			if (returnValue.length() > 0) {
				returnValue = returnValue.substring(0, returnValue.length() - 2);
			}
		}
		return returnValue;
	}

	
	 * 
	 * @author Jayalakshmi to display the number based on Property Default
	 

	public static String displayNumberFormatForDecimalAndThousand(final double value, final long decimalPlaces, final Property property) {
		return getAmountAsPropertyNumberFormat(value, property, null, decimalPlaces);
		// final double valueIn = value;
		//
		// String retValue = value + "";
		//
		// String strFormat;
		// if (CommonUtility.isValidObject(property)) {
		// if (decimalPlaces == 0) {
		//
		// strFormat = property.getNumberFormat().getSymbol() + "0";
		//
		// } else {
		//
		// strFormat = property.getNumberFormat().getSymbol() + "0.";
		//
		// }
		//
		// for (int i = 1; i <= decimalPlaces; i++) {
		//
		// strFormat += "0";
		//
		// }
		//
		// if (value != 0) {
		//
		// final DecimalFormat decimalFormat = new DecimalFormat(strFormat);
		//
		// retValue = decimalFormat.format(valueIn);
		//
		// retValue = retValue.replace(".", property.getDecimalIndicator());
		//
		// retValue = retValue.replace(",", property.getThousandSeparators());
		//
		// return retValue;
		//
		// } else if (value == 0) { // Added Else Part. To resolve issue with 0
		//
		// // value records are not displaying as per
		//
		// // our standards ~Ankur
		//
		// final DecimalFormat decimalFormat = new DecimalFormat(strFormat);
		//
		// retValue = decimalFormat.format(valueIn);
		//
		// retValue = retValue.replace(".",
		//
		// "0" + property.getDecimalIndicator());
		//
		// retValue = retValue.replace(",", property.getThousandSeparators());
		//
		// // return retValue;
		//
		// }
		// }
		//
		// return retValue;

	}

	// By Ravi Periyasamy to displayNumberFormat values

	public static String displayNumberFormat(final double value,

			final long decimalPlaces) {

		final double valueIn = value;

		String retValue = value + "";

		String strFormat;

		if (decimalPlaces == 0) {

			strFormat = "#0";

		} else {

			strFormat = "#0.";

		}

		for (int i = 1; i <= decimalPlaces; i++) {

			strFormat += "0";

		}

		if (value != 0) {

			final DecimalFormat decimalFormat = new DecimalFormat(strFormat);

			retValue = decimalFormat.format(valueIn);

			return retValue;

		}

		return retValue;

	}

	public static String rateRoundUp(final double rate) {

		if (rate == 0) {

			return "0.00";

		}

		return decimalFormat.format(rate);

	}

	public static String getMonthName(final int iNumber) {

		String monthName = null;

		if (iNumber >= 0 && iNumber <= 11) {

			monthName = dateFormatSymbols.getMonths()[iNumber];

		}

		return monthName;

	}

	public static String getDayName(final int number) {

		return dateFormatSymbols.getWeekdays()[number];

	}

	public String displayOnlyDate(final Date date) {

		if (date != null) {

			final SimpleDateFormat dateFormat = new SimpleDateFormat(

					"MMM-dd-yyyy");

			return dateFormat.format(date);

		} else {

			return "Null";

		}

	}

	*//**
	 * 
	 * Get a diff between two dates diff in days:pass timeunit equal to
	 * 
	 * TimeUnit.DAYS diff in Hours:pass timeunit eq TimeUnit.HOURS diff in
	 * 
	 * Min:pass timeunit eq TimeUnit.MINUTES diff in sec:pass
	 * 
	 * timeunit=TimeUnit.SECONDS
	 * 
	 * 
	 * 
	 * @param date1
	 * 
	 *            date1
	 * 
	 * @param date2
	 * 
	 *            date2
	 * 
	 * @param timeUnit
	 * 
	 *            timeUnit
	 * 
	 * 
	 * 
	 * @return diffInMillies
	 *//*

	public static long convertDateDiff(final Date date1, final Date date2,

			final TimeUnit timeUnit) {

		final long diffInMillies = date2.getTime() - date1.getTime();

		return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);

	}

	public static String formateRole(List<Role> roleList) {
		String returnValue = "";
		if (roleList != null) {
			for (Role role : roleList) {
				returnValue += role.getRoleName() + ", ";
			}
			if (returnValue.length() > 0) {
				returnValue = returnValue.substring(0, returnValue.length() - 2);
			}
		}
		return returnValue;
	}

	public static String formateRoomType(List<RoomType> roomTypeList) {
		String returnValue = "";
		if (roomTypeList != null) {
			for (RoomType roomType : roomTypeList) {
				returnValue += roomType.getDescription() + ", ";
			}
			if (returnValue.length() > 0) {
				returnValue = returnValue.substring(0, returnValue.length() - 2);
			}
		}
		return returnValue;
	}

	public static String formateUserGroup(List<UserGroup> groupList) {
		String returnValue = "";
		if (groupList != null) {
			for (UserGroup group : groupList) {
				returnValue += group.getUserGroupName() + ", ";
			}
			if (returnValue.length() > 0) {
				returnValue = returnValue.substring(0, returnValue.length() - 2);
			}
		}
		return returnValue;
	}

	public static void panelCollapse(final String UIComponetId,

			final boolean collapse) {

		final Panel panel = (Panel) FacesContext.getCurrentInstance()

				.getViewRoot().findComponent(UIComponetId);

		panel.setCollapsed(collapse);

	}

	
	 * 
	 * Ravi Periyasamy for Creadit Card Masking
	 

	public static String setCreditCardMask(final String cardNumber) {

		String tmpStr = new String();

		final String tmpStr1 = new String(

				"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");

		tmpStr = cardNumber;

		if (cardNumber == null) {

			return tmpStr;

		} else if (tmpStr.length() > 8) {

			// log.info("Origin Value :" + cardNumber);

			tmpStr = tmpStr.substring(0, 4)

					+ tmpStr1.substring(0, tmpStr.length() - 8)

					+ tmpStr.substring(tmpStr.length() - 4, tmpStr.length());

			// log.info("Masked Value :" + tmpStr);

		}

		return tmpStr;

	}

	public static String formateRoleForUser(final List<Role> roleList) {
		String returnValue = "";
		if (roleList != null) {
			for (final Role role : roleList) {
				if (role != null) {
					returnValue += role.getRoleName() + ", ";
				}
			}
			if (returnValue.length() > 0) {
				returnValue = returnValue.substring(0, returnValue.length() - 2);
			}
		}
		return returnValue;
	}

	*//**
	 * 
	 * Returns Date Value from Date Object (Without Time)
	 * 
	 * 
	 * 
	 * @author Elango
	 * 
	 * @param date
	 * 
	 *            date
	 * 
	 * @return dateValue dateValue
	 * 
	 * @throws ParseException
	 * 
	 *             throws ParseException
	 *//*

	public static Date getDateFromDateObject(final Date date)

			throws ParseException {

		final DateFormat inputFormatter = new SimpleDateFormat(

				"yyyy/MM/dd HH:mm:ss.SSS");

		final Date dateValue = inputFormatter.parse(date.toString());

		return dateValue;

	}

	public static double getPassitiveValue(final double inputValue) {

		if (inputValue < 0) {

			return inputValue * -1;

		} else {

			return inputValue;

		}

	}

	*//**
	 * 
	 * Returns Next Increment Value in Decimal Format
	 * 
	 * 
	 * 
	 * @author Ankur
	 * 
	 * @param double
	 * 
	 * @return double
	 *//*

	public static double calculateNextFromValueIncrement(double deccimalPlaces) {

		double incrementValue = 0.01;

		try {

			deccimalPlaces = Math.pow(10, deccimalPlaces);

			incrementValue = 1 / deccimalPlaces;

		} catch (Exception e) {

		}

		return incrementValue;

	}

	*//**
	 * 
	 * Returns Decimal Format Pattern for Given Property (For UI Binding)
	 * 
	 * 
	 * 
	 * @author Elango
	 * 
	 * @param Property
	 * 
	 * @return String
	 *//*

	public static String decimalFormatForProperty(Property property) {

		String numberPattern = "9999999999";

		int decimalPlaces = property.getRateDecimal();

		if (decimalPlaces > 0) {

			numberPattern = numberPattern + "."

					+ new String(new char[decimalPlaces]).replace("\0", "9");

		}

		return numberPattern;

	}

	*//**
	 * Converts List of Property Object to List of PropertyNames
	 * 
	 * @author Shreyas
	 * @param property
	 * @return Formatted PropertyList
	 *//*
	public static String formatPropertyList(final List<Property> propertyList) {
		String returnValue = "";
		if (propertyList != null) {
			for (final Property tempProperty : propertyList) {
				returnValue += tempProperty.getPropertyName() + ", ";
			}
			if (returnValue.length() > 0) {
				returnValue = returnValue.substring(0, returnValue.length() - 2);
			}
			return returnValue;
		}
		return returnValue;
	}

	*//**
	 * to get only Date from date
	 * 
	 * @param Date
	 * @return Date
	 *//*
	public static Date getDateOnly(Date date) {
		try {
			// log.info("Reading Date only from Date and time");
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			try {
				if (date != null) {
					return formatter.parse(formatter.format(date));
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return date;
		} catch (Exception e) {
			// log.info("....Some exception raised.");
			return null;
		}
	}

	*//**
	 * to get only time from date
	 * 
	 * @param Date
	 * @return Date
	 *//*
	public static Date getOnlyTime(final Date value) {
		try {
			// log.info("Reading time only from Date and time");
			final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
			Date date = new Date();
			try {
				date = dateFormat.parse(dateFormat.format(value));
			} catch (final ParseException e) {
				e.printStackTrace();
			}
			// log.info(date);
			return date;
		} catch (Exception e) {
			// log.info("....Some exception raised.");
			return null;
		}
	}

	*//**
	 * to get only time from date
	 * 
	 * @param Date
	 * @return Date
	 *//*
	public static String getOnlyTimeString(final Date value) {
		try {
			// log.info("Reading time only from Date and time");
			final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
			Date date = new Date();
			try {
				date = dateFormat.parse(dateFormat.format(value));
			} catch (final ParseException e) {
				e.printStackTrace();
			}

			// log.info(date);
			return dateFormat.format(date);
		} catch (Exception e) {
			// log.info("....Some exception raised.");
			return null;
		}
	}

	// .............AES Algorithm Implementation.....................
	*//**
	 * @description : Encrypt with AES Algorithm
	 * @param :01)text
	 *            to be encrypted 02)encryption key
	 * @author shivranjan
	 *//*
	private static String encryptWithAES(String plainText, String key) throws Exception {
		SecretKeySpec keySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, keySpec);
		byte[] encryptedTextBytes = cipher.doFinal(plainText.getBytes("UTF-8"));
		return new Base64().encodeToString(encryptedTextBytes);
	}

	*//**
	 * @description : decrypt with AES algorithm
	 * @param :01)text
	 *            to be decrypted 02)same key whatever we used at encryption time
	 * @author shivranjan
	 *//*
	
	 * private static String decryptWithAES(String encryptedText, String key) throws Exception { SecretKeySpec keySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES"); Cipher cipher = Cipher.getInstance("AES"); cipher.init(Cipher.DECRYPT_MODE, keySpec); byte[] encryptedTextBytes = Base64.decodeBase64(encryptedText); byte[] decryptedTextBytes = cipher.doFinal(encryptedTextBytes); return new String(decryptedTextBytes); }
	 

	*//**
	 * @description : creation of unique key for every user
	 * @param :key
	 *            of type string
	 * @author shivranjan
	 *//*
	private static String getSalt(String inputString) {
		// Check - Reinitialization of Key Required - ELANGO
		StringBuilder key = new StringBuilder("Ids" + inputString.length() + "_" + inputString + "@Imagine");
		if (key.length() > 16)
			return key.substring(0, 16);
		else if (key.length() < 16) {
			int remChar = 16 - key.length();
			for (int index = 0; index < remChar; index++) {
				key.append(inputString.charAt(0));
			}
			return key.toString();
		} else
			return key.toString();
	}

	*//**
	 * @description : encrypt password with AES algorithm
	 * @param :01)plainText
	 *            to be encrypted 02)User entity
	 * @author shivranjan
	 *//*
	public static String encryptPasswordWithAES(String unencryptedPassword, User currentUser) {
		String encPassword = "";
		try {
			String userName = currentUser.getUserName();
			String key = getSalt(userName);
			encPassword = WebUtil.encryptWithAES(unencryptedPassword, key);
		} catch (Exception e) {
			log.info("-- AES Encryption Exception --");
		}
		return encPassword;
	}

	*//**
	 * @description :decrypt password with AES algorithm
	 * @param :01)encrypted
	 *            password to be encrypted 02)User entity
	 * @author shivranjan
	 *//*
	
	 * private static String decryptPasswordWithAES(String encryptedPassword, User currentUser) { String decPassword = ""; try { String userName = currentUser.getUserName(); String key = getSalt(userName); decPassword = WebUtil.decryptWithAES(encryptedPassword, key); } catch (Exception e) { log.info("-- AES Ddecryption Exception --"); } return decPassword; }
	 

	// .............3DES Algorithm Implementation.....................
	*//**
	 * @description :making 24 characters key
	 * @param :key
	 *            of type string
	 * @author shivranjan
	 *//*
	public static String getSugar(String inputString) {
		// Check - Reinitialization of Key Required - ELANGO
		StringBuilder key = new StringBuilder("Ids" + inputString.length() + "_" + inputString + "Next@ImagineJEE");
		if (key.length() > 24)
			return key.substring(0, 24);
		else if (key.length() < 24) {
			int remainingCharacters = 24 - key.length();
			for (int index = 0; index < remainingCharacters; index++) {
				key.append(inputString.charAt(0));
			}
			return key.toString();
		} else
			return key.toString();
	}

	*//**
	 * @description :encrypt plainText by 3DES algorithm
	 * @param :01)plainText
	 *            to be encrypted 02)encryption key
	 * @author shivranjan
	 *//*
	private static String encryptWith3DES(String plainText, String key) throws Exception {
		SecretKeySpec keySpec = new SecretKeySpec(key.getBytes("UTF-8"), "DESede");
		Cipher cipher = Cipher.getInstance("DESede");
		cipher.init(Cipher.ENCRYPT_MODE, keySpec);
		byte[] encryptedTextBytes = cipher.doFinal(plainText.getBytes("UTF-8"));
		return new Base64().encodeToString(encryptedTextBytes);
	}

	*//**
	 * @description :decrypt encryptedText by 3DES algorithm
	 * @param :01)encryptedText
	 *            to be decrypted 02)decryption key
	 * @author shivranjan
	 *//*
	private static String decryptWith3DES(String encryptedText, String key) throws Exception {
		SecretKeySpec keySpec = new SecretKeySpec(key.getBytes("UTF-8"), "DESede");
		Cipher cipher = Cipher.getInstance("DESede");
		cipher.init(Cipher.DECRYPT_MODE, keySpec);
		byte[] encryptedTextBytes = Base64.decodeBase64(encryptedText);
		byte[] decryptedTextBytes = cipher.doFinal(encryptedTextBytes);
		return new String(decryptedTextBytes);
	}

	public static String encryptPasswordWith3DES(String unencryptedPassword, User currentUser) {
		String encPassword = "";
		try {
			String userName = currentUser.getUserName();
			String key = getSugar(userName);
			encPassword = WebUtil.encryptWith3DES(unencryptedPassword, key);
		} catch (Exception e) {
			log.info("-- 3DES Encryption Exception --");
		}
		return encPassword;
	}

	public static String decryptPasswordWith3DES(String encryptedPassword, User currentUser) {
		String decPassword = "";
		try {
			String userName = currentUser.getUserName();
			String key = getSugar(userName);
			decPassword = WebUtil.decryptWith3DES(encryptedPassword, key);
		} catch (Exception e) {
			log.info("-- 3DES Ddecryption Exception --");
		}
		return decPassword;
	}

	// ............password policy ......................
	*//**
	 * @description :Verify Password according to give policy and returns corresponding integer value if any cond. fails
	 * @param :password
	 *            text
	 * @author shivranjan
	 *//*
	public static int verifyPasswordPolicy(String password) {
		Pattern pattern = Pattern.compile("^(?=(.*[a-zA-Z]){4,})(?=(.*\\d){2,})(?=.*[$@$!%*?&])[A-Za-z\\d$@$!%*?&]{8,15}");
		Matcher matcher = pattern.matcher(password);
		if (matcher.matches())
			return 0;
		else if (password.length() < 8 || password.length() > 15)
			return 1;
		else if (!(verifyOtherCharacters(password)))
			return 5;
		else if (!(verifyAlphaCharacters(password)))
			return 2;
		else if (!(verifyNumericCharacters(password)))
			return 3;
		else if (!(verifySpecialCharacters(password)))
			return 4;
		else
			return -1;
	}

	*//**
	 * @description :verify at least 4 alpha charactes among string characters
	 * @param :password
	 *            text
	 * @author shivranjan
	 *//*
	private static boolean verifyAlphaCharacters(String password) {
		boolean returnValue = false;
		try {
			Pattern pattern = Pattern.compile("^(?=(.*[a-zA-Z]){4,})[A-Za-z\\d@&$#_!]{8,15}");
			Matcher matcher = pattern.matcher(password);
			returnValue = matcher.matches();
		} catch (Exception e) {
			log.info("-- Pattern Syntax Exception --");
		}
		return returnValue;
	}

	*//**
	 * @description :verify at least 2 numeric charactes among string characters
	 * @param :password
	 *            text
	 * @author shivranjan
	 *//*
	private static boolean verifyNumericCharacters(String password) {
		boolean returnValue = false;
		try {
			Pattern pattern = Pattern.compile("^(?=(.*\\d){2,})[A-Za-z\\d@&$#_!]{8,15}");
			Matcher matcher = pattern.matcher(password);
			returnValue = matcher.matches();
		} catch (Exception e) {
			log.info("-- Pattern Syntax Exception --");
		}
		return returnValue;
	}

	*//**
	 * @description :verify at least 1 special charactes from (@,&,$,#,_,!) among string characters
	 * @param :password
	 *            text
	 * @author shivranjan
	 *//*
	private static boolean verifySpecialCharacters(String password) {
		boolean returnValue = false;
		try {
			Pattern pattern = Pattern.compile("^(?=.*[@&$#_!])[A-Za-z\\d@&$#_!]{8,15}");
			Matcher matcher = pattern.matcher(password);
			returnValue = matcher.matches();
		} catch (Exception e) {
			log.info("-- Pattern Syntax Exception --");
		}
		return returnValue;
	}

	*//**
	 * @description :noone character should be used in password leaving alpha,numeric and (@,&,$,#,_,!)
	 * @param :password
	 *            text
	 * @author shivranjan
	 *//*
	private static boolean verifyOtherCharacters(String password) {
		boolean returnValue = false;
		try {
			Pattern pattern = Pattern.compile("^(?=.*[a-zA-Z\\d@&$#_!])[A-Za-z\\d@&$#_!]{8,15}");
			Matcher matcher = pattern.matcher(password);
			returnValue = matcher.matches();
		} catch (Exception e) {
			log.info("-- Pattern Syntax Exception --");
		}
		return returnValue;
	}

	*//**
	 * This will convert the given amount to selected {@link NumberFormat} of the given {@link Property}. If {@link Currency} is not <code>null</code> then it will return the converted amount prefix with currency symbol.<br/>
	 * If {@link Currency} is <code>null</code>, it will return only amount.
	 * <p>
	 * Length of Fractional Part depends on last parameter i.e. decimalLength. If it is zero(0) or less than zero(0) it will not show fractional part.
	 * 
	 * </p>
	 * 
	 * @param amountValue
	 *            type of {@link Double}
	 * @param property
	 *            type of {@link Property}
	 * @param currency
	 *            type of {@link Currency}
	 * @param decimalLength
	 *            type of {@link Long}
	 * @return converted Amount as {@link String}
	 * 
	 * @author Rakesh Kumar "MooN"
	 *//*
	public static String getAmountAsPropertyNumberFormat(final double amountValue, final Property property, final Currency currency, final long decimalLength) {
		if (property == null)
			throw new RuntimeException("Property can't be null.");

		if (property.getNumberFormat() == NumberFormat.NUMBER1) {
			return convertToPropertyAmountFormat0(amountValue, property, currency, decimalLength);
		} else if (property.getNumberFormat() == NumberFormat.NUMBER2) {
			String currencySymbol = "";
			if (currency != null) {
				currencySymbol = currency.getCountryId().getCurrencySymbol();
			}
			String amountString = "" + amountValue;
			String formatString = property.getNumberFormat().getSymbol();
			double valueIn = Math.abs(amountValue);

			// For Decimal Point
			String decimalString = "";
			if (decimalLength > 0) {
				decimalString = ".";
				for (int index = 1; index <= decimalLength; index++) {
					decimalString += "0";
				}
			}

			if (amountValue != 0) {
				if (amountValue < 1000) {
					amountString = new DecimalFormat(formatString + decimalString).format(valueIn);
				} else {
					double hundreds = amountValue % 1000;
					int other = (int) (amountValue / 1000);
					double d = Double.parseDouble(new DecimalFormat("000" + decimalString).format(hundreds));
					if (d >= 1000) {
						other = other + 1;
						hundreds = 0;
					}
					amountString = new DecimalFormat(",##").format(other) + ',' + new DecimalFormat("000" + decimalString).format(hundreds);
				}
				amountString = amountString.replace(".", property.getDecimalIndicator());
				amountString = amountString.replace(",", property.getThousandSeparators());
				if (amountValue < 0) {
					if (property.getNegativeFormat().equals(NegativeFormat.NEGATIVE)) {
						amountString = "-" + amountString;
					} else if (property.getNegativeFormat().equals(NegativeFormat.CR)) {
						amountString = "CR" + amountString;
					} else if (property.getNegativeFormat().equals(NegativeFormat.BRACKETS)) {
						amountString = "( " + amountString + ") ";
					}
				}
			} else if (amountValue == 0) {
				final DecimalFormat decimalFormat = new DecimalFormat(formatString + decimalString);
				amountString = decimalFormat.format(valueIn);
				amountString = amountString.replace(".", "0" + property.getDecimalIndicator());
				amountString = amountString.replace(",", property.getThousandSeparators());
			}
			// To Handle values between less than 1 and greater than -1
			amountString = amountString.split("\\.")[0].length() == 0 ? ("0" + amountString) : (amountString);
			if (amountString.split("\\.")[0].length() == 1) {
				if (!Character.isDigit(amountString.split("\\.")[0].charAt(0))) {
					StringBuilder sb = new StringBuilder(amountString);
					sb = sb.insert(1, "0");
					amountString = sb.toString();

				}
			}
			return currencySymbol + " " + amountString;
		}
		return null;
	}

	public static double truncate(double value, int decimal) {
		if (value == 0)
			return value;
		String s = value + "";
		String expVal = s.split("\\.")[0];
		String decVal = s.split("\\.")[1];
		return Double.parseDouble(expVal + "." + (decVal.length() > decimal ? decVal.substring(0, decimal) : decVal));

	}

	*//**
	 * @description Format Amount as Per Property Settings
	 * @param double
	 * @param Property
	 * @return Currency
	 * @author Elango
	 *//*
	public static String convertToPropertyAmountFormat(final double amountValue, final Property property, final Currency currency, final long decimalLenth) {
		return getAmountAsPropertyNumberFormat(amountValue, property, currency, decimalLenth);
	}

	*//**
	 * @description Format Amount as Per Property Settings
	 * @param double
	 * @param Property
	 * @return Currency
	 * @author Elango
	 *//*
	private static String convertToPropertyAmountFormat0(final double amountValue, final Property property, final Currency currency, final long decimalLenth) {
		String currencySymbol = "";
		if (currency != null) {
			currencySymbol = currency.getCountryId().getCurrencySymbol();
		}
		double valueIn = Math.abs(amountValue);
		String amountString = amountValue + "";
		String formatString;
		if (decimalLenth <= 0) {
			// formatString = property.getNumberFormat().getSymbol() + "0"; //Commented By Rakesh Kumar 'MooN' becoz it will go wrong
			formatString = property.getNumberFormat().getSymbol();
		} else {
			formatString = property.getNumberFormat().getSymbol() + ".";
		}

		for (int index = 1; index <= decimalLenth; index++) {
			formatString += "0";
		}

		if (amountValue != 0) {
			final DecimalFormat decimalFormat = new DecimalFormat(formatString);
			amountString = decimalFormat.format(valueIn);
			amountString = amountString.replace(".", property.getDecimalIndicator());
			amountString = amountString.replace(",", property.getThousandSeparators());
			if (amountValue < 0) {
				if (property.getNegativeFormat().equals(NegativeFormat.NEGATIVE)) {
					amountString = currencySymbol + " " + "-" + amountString;
				} else if (property.getNegativeFormat().equals(NegativeFormat.CR)) {
					amountString = currencySymbol + " " + "CR " + amountString;
				} else if (property.getNegativeFormat().equals(NegativeFormat.BRACKETS)) {
					amountString = currencySymbol + " ( " + amountString + ") ";
				}
			} else {

				amountString = currencySymbol + " " + amountString;
			}
		} else if (amountValue == 0) {
			final DecimalFormat decimalFormat = new DecimalFormat(formatString);
			amountString = decimalFormat.format(valueIn);
			amountString = amountString.replace(".", "0" + property.getDecimalIndicator());
			amountString = amountString.replace(",", property.getThousandSeparators());
			amountString = currencySymbol + " " + amountString;
		}

		// To Handle values between less than 1 and greater than -1
		amountString = amountString.split("\\.")[0].length() == 0 ? ("0" + amountString) : (amountString);
		if (amountString.split("\\.")[0].length() == 1) {
			if (!Character.isDigit(amountString.split("\\.")[0].charAt(0))) {
				StringBuilder sb = new StringBuilder(amountString);
				sb = sb.insert(1, "0");
				amountString = sb.toString();

			}
		}
		return amountString;
	}

	*//**
	 * @description use for Applicable property
	 * @author vishvas
	 *//*
	public List<Property> getPropertyList(String functionName) {

		List<Property> propertyList = new ArrayList<Property>();
		for (Property property : currentUserManager.getPropertyList()) {
			if (currentUserManager.hasPermission(functionName, property.getPropertyId(), "View"))
				propertyList.add(property);
		}
		return propertyList;
	}

	*//**
	 * Time Zone Conversion Based On property
	 * 
	 * @return
	 * @throws ParseException
	 *//*

	public Date convertServerDateToPropertyDate(Property property, Date tempDate) throws ParseException {
		try {
			if (property != null && tempDate != null) {
				// log.info("Time Zone Conversion For - " + property.getPropertyName() + " Date - " + tempDate + " ...");
				TimeZone propertyTimeZone = null;
				if (property.getTimeZone() != null && !property.getTimeZone().trim().isEmpty()) {
					// Validation for Time Zone
					long countryId = Long.parseLong(property.getTimeZone());
					Country country = propertyService.getCountryById(countryId);
					propertyTimeZone = TimeZone.getTimeZone(country.getGmtTimeDiffrence());
				} else if (property.getContactId() != null && property.getContactId().getCountryId() != null) {
					// Validation for Time Zone from Country
					propertyTimeZone = TimeZone.getTimeZone(property.getContactId().getCountryId().getGmtTimeDiffrence());

				} else {
					// If no Country/Time Zone Assume Server Time Zone
					propertyTimeZone = TimeZone.getDefault();
				}
				// Reading Server Time Zone
				TimeZone tzServer = TimeZone.getDefault();
				int serverHours = (int) TimeUnit.MILLISECONDS.toHours(tzServer.getRawOffset());
				int serverMinuts = (int) (TimeUnit.MILLISECONDS.toMinutes(tzServer.getRawOffset()) - TimeUnit.HOURS.toMinutes(serverHours));
				TimeZone propertyTz = propertyTimeZone;
				// Reading Property Time Zone Hour/Min.
				int propertyHours = (int) TimeUnit.MILLISECONDS.toHours(propertyTz.getRawOffset());
				int propertyMinutes = (int) (TimeUnit.MILLISECONDS.toMinutes(propertyTz.getRawOffset()) - TimeUnit.HOURS.toMinutes(propertyHours));
				// Calculating Time Difference
				int hours = propertyHours - serverHours;
				int minutes = propertyMinutes - serverMinuts;
				// Set Time Difference to Input Date
				Calendar cal = Calendar.getInstance();
				cal.setTime(tempDate);
				cal.add(Calendar.HOUR_OF_DAY, hours);
				cal.add(Calendar.MINUTE, minutes);
				// log.info("... " + cal.getTime());
				return cal.getTime();
			}
		} catch (Exception e) {
			// log.info("... Invalid Property or Date");
			return tempDate;
		}
		return tempDate;
	}

	*//**
	 * Time Zone Conversion Based On property
	 * 
	 * @return
	 * @throws ParseException
	 *//*

	public Date convertServerDateToPropertyDate(long propertyId, Date tempDate) throws ParseException {
		// log.info("converting date to property date....");
		try {
			Property property = propertyService.getPropertyById(propertyId);
			if (property != null && tempDate != null) {
				// log.info("Time Zone Conversion For - " + property.getPropertyName() + " Date - " + tempDate + " ...");
				TimeZone propertyTimeZone = null;
				if (property.getTimeZone() != null && !property.getTimeZone().trim().isEmpty()) {
					// Validation for Time Zone
					long countryId = Long.parseLong(property.getTimeZone());
					Country country = propertyService.getCountryById(countryId);
					propertyTimeZone = TimeZone.getTimeZone(country.getGmtTimeDiffrence());
				} else if (property.getContactId() != null && property.getContactId().getCountryId() != null) {
					// Validation for Time Zone from Country
					propertyTimeZone = TimeZone.getTimeZone(property.getContactId().getCountryId().getGmtTimeDiffrence());

				} else {
					// If no Country/Time Zone Assume Server Time Zone
					propertyTimeZone = TimeZone.getDefault();
				}
				// Reading Server Time Zone
				TimeZone tzServer = TimeZone.getDefault();
				int serverHours = (int) TimeUnit.MILLISECONDS.toHours(tzServer.getRawOffset());
				int serverMinuts = (int) (TimeUnit.MILLISECONDS.toMinutes(tzServer.getRawOffset()) - TimeUnit.HOURS.toMinutes(serverHours));
				TimeZone propertyTz = propertyTimeZone;
				// Reading Property Time Zone Hour/Min.
				int propertyHours = (int) TimeUnit.MILLISECONDS.toHours(propertyTz.getRawOffset());
				int propertyMinutes = (int) (TimeUnit.MILLISECONDS.toMinutes(propertyTz.getRawOffset()) - TimeUnit.HOURS.toMinutes(propertyHours));
				// Calculating Time Difference
				int hours = propertyHours - serverHours;
				int minutes = propertyMinutes - serverMinuts;
				// Set Time Difference to Input Date
				Calendar cal = Calendar.getInstance();
				cal.setTime(tempDate);
				cal.add(Calendar.HOUR_OF_DAY, hours);
				cal.add(Calendar.MINUTE, minutes);
				// log.info("... " + cal.getTime());
				return cal.getTime();
			}
		} catch (Exception e) {
			// log.info("... Invalid Property or Date");
			return tempDate;
		}
		return tempDate;
	}

	*//**
	 * Time Zone Conversion Based On property
	 * 
	 * @return
	 * @throws ParseException
	 *//*
	
	 * 
	 * public Date convertServerDateToPropertyDate(long propertyId,Date tempDate) throws ParseException { log.info("converting date to property date...."); try { Property property = propertyService.getPropertyById(propertyId); if(property !=null && tempDate != null){ TimeZone propertyTimeZone = null; if (property.getContactId() != null && property.getContactId().getCountryId() != null) { propertyTimeZone = TimeZone .getTimeZone(property.getContactId().getCountryId().getGmtTimeDiffrence ()); } else if (property.getTimeZone() != null && !property.getTimeZone().trim().isEmpty()) { long countryId = Long.parseLong(property.getTimeZone()); Country country = propertyService.getCountryById(countryId); propertyTimeZone = TimeZone.getTimeZone(country.getGmtTimeDiffrence());
	 * 
	 * } else { propertyTimeZone = TimeZone.getDefault(); }
	 * 
	 * 
	 * TimeZone tzServer = TimeZone.getDefault(); int serverHours = (int)TimeUnit.MILLISECONDS.toHours(tzServer.getRawOffset()); int serverMinuts = (int)(TimeUnit.MILLISECONDS.toMinutes(tzServer.getRawOffset()) - TimeUnit.HOURS.toMinutes(serverHours)); TimeZone propertyTz = propertyTimeZone;
	 * 
	 * int propertyHours = (int)TimeUnit.MILLISECONDS.toHours(propertyTz.getRawOffset()); int propertyMinutes = (int)(TimeUnit.MILLISECONDS.toMinutes(propertyTz.getRawOffset()) - TimeUnit.HOURS.toMinutes(propertyHours)); int hours = propertyHours - serverHours; int minutes = propertyMinutes - serverMinuts;
	 * 
	 * Calendar cal = Calendar.getInstance(); // creates calendar cal.setTime(tempDate); // sets calendar time/date cal.add(Calendar.HOUR_OF_DAY, hours); // adds one hour cal.add(Calendar.MINUTE, minutes); } } catch (Exception e) { log.info("Date And Time Conversion Error...."); return null; } return new Date(); }
	 

	*//**
	 * 
	 *//*

	public Date convertServerDateToPropertyDate(Property property, Date tempDate, Date time) throws ParseException {
		// log.info("Time Conversion......");
		try {
			Date date = new Date();
			date.setDate(tempDate.getDate());
			date.setMonth(tempDate.getMonth());
			date.setYear(tempDate.getYear());
			if (property != null && tempDate != null && time != null) {
				date.setHours(time.getHours());
				date.setMinutes(time.getMinutes());
				date.setSeconds(time.getSeconds());
			}
			return convertServerDateToPropertyDate(property, date);

		} catch (Exception e) {
			// .info("Date And Time Conversion Error....");
			return tempDate;
		}
	}

	*//**
	 * 
	 *//*

	public Date convertServerDateToPropertyDate(long propertyId, Date tempDate, Date time) throws ParseException {
		// log.info("Time Conversion......");
		try {
			Property property = propertyService.getPropertyById(propertyId);
			Date date = new Date();
			date.setDate(tempDate.getDate());
			date.setMonth(tempDate.getMonth());
			date.setYear(tempDate.getYear());
			if (property != null && tempDate != null && time != null) {
				date.setHours(time.getHours());
				date.setMinutes(time.getMinutes());
				date.setSeconds(time.getSeconds());
			}
			return convertServerDateToPropertyDate(property, date);

		} catch (Exception e) {
			// log.info("Date And Time Conversion Error....");
			return tempDate;
		}
	}

	*//**
	 * 
	 * 
	 * @param property
	 * @param tempDate
	 * @param time
	 * @return
	 * @throws Exception
	 *//*
	public Date getCurrentDateTimeBasedOnPropertyTimeZone(final Property property, final String datePattern) throws Exception {
		TimeZone propertyTimeZone = null;
		if (property.getContactId() != null && property.getContactId().getCountryId() != null) {
			propertyTimeZone = TimeZone.getTimeZone(property.getContactId().getCountryId().getGmtTimeDiffrence());
		} else if (property.getTimeZone() != null && !property.getTimeZone().trim().isEmpty()) {
			long countryId = Long.parseLong(property.getTimeZone());
			Country country = propertyService.getCountryById(countryId);
			propertyTimeZone = TimeZone.getTimeZone(country.getGmtTimeDiffrence());

		} else {
			propertyTimeZone = TimeZone.getDefault();
		}

		DateTimeZone defaultDateTimeZone = null;
		DateTime dateTime = new LocalDateTime().toDateTime(DateTimeZone.forTimeZone(TimeZone.getDefault()));
		defaultDateTimeZone = DateTimeZone.forTimeZone(propertyTimeZone);
		SimpleDateFormat propertyFormat = new SimpleDateFormat(datePattern != null ? datePattern : "dd-MM-yy HH:mm:ss");
		String out = propertyFormat.format(dateTime.toDate());
		DateTimeFormatter formatDT = DateTimeFormat.forPattern(datePattern != null ? datePattern : "dd-MM-yy HH:mm:ss");
		DateTime dateDateTime = formatDT.parseDateTime(out).toDateTime(defaultDateTimeZone);
		
		 * Date date = new Date(); date.setDate(dateDateTime.getDayOfMonth()); date.setMonth(dateDateTime.getMonthOfYear()); date.setYear(dateDateTime.getYear()); date.setHours(dateDateTime.getHourOfDay()); date.setMinutes(dateDateTime.getMinuteOfHour()); date.setSeconds(dateDateTime.getSecondOfMinute());
		 
		return dateDateTime.toDate();
	}

	*//**
	 * @description Property to Server Time Zone Conversion Based On property
	 * @param Property
	 *            . Date
	 * @return Date
	 * @throws ParseException
	 *//*
	public Date convertPropertyDateTimeToServerDateTime(final Property property, Date date) {
		try {
			if (property != null && date != null) {
				// log.info("Time Zone Conversion For - " + property.getPropertyName() + " Date - " + date + " ...");
				TimeZone propertyTimeZone = null;
				if (property.getTimeZone() != null && !property.getTimeZone().trim().isEmpty()) {
					// log.info("validating timeZone");
					long countryId = Long.parseLong(property.getTimeZone());
					Country country = propertyService.getCountryById(countryId);
					propertyTimeZone = TimeZone.getTimeZone(country.getGmtTimeDiffrence());
				} else if (property.getContactId() != null && property.getContactId().getCountryId() != null) {
					// log.info("validating timeZone through country");
					propertyTimeZone = TimeZone.getTimeZone(property.getContactId().getCountryId().getGmtTimeDiffrence());

				} else {
					// If no Country/Time Zone Assume Server Time Zone
					propertyTimeZone = TimeZone.getDefault();
				}

				// Reading Server Time Zone
				TimeZone tzDefault = TimeZone.getDefault();
				int defaultHours = (int) TimeUnit.MILLISECONDS.toHours(tzDefault.getRawOffset());
				int defaultMinutes = (int) (TimeUnit.MILLISECONDS.toMinutes(tzDefault.getRawOffset()) - TimeUnit.HOURS.toMinutes(defaultHours));

				// Reading property Time Zone
				TimeZone propertyTz = propertyTimeZone;
				int propertyHours = (int) TimeUnit.MILLISECONDS.toHours(propertyTz.getRawOffset());
				int propertyMinutes = (int) (TimeUnit.MILLISECONDS.toMinutes(propertyTz.getRawOffset()) - TimeUnit.HOURS.toMinutes(propertyHours));
				// calculating TimeZone diffrence between Property and server timeZone
				int hours = defaultHours - propertyHours;
				int minutes = defaultMinutes - propertyMinutes;

				// setting the actual time to the input Date
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				cal.add(Calendar.HOUR_OF_DAY, hours);
				cal.add(Calendar.MINUTE, minutes);
				return cal.getTime();
			}
		} catch (Exception e) {
			// log.info("Invalid property/Date");
			return date;
		}
		return date;
	}

	*//**
	 * Time Zone Conversion Based On property
	 * 
	 * @return
	 * @throws ParseException
	 *//*

	public Date convertPropertyDateTimeToServerDateTime(long propertyId, Date date) {
		try {
			Property property = propertyService.getPropertyById(propertyId);
			if (property != null && date != null) {
				// log.info("Time Zone Conversion For - " + property.getPropertyName() + " Date - " + date + " ...");
				TimeZone propertyTimeZone = null;
				if (property.getTimeZone() != null && !property.getTimeZone().trim().isEmpty()) {
					// log.info("validating timeZone");
					long countryId = Long.parseLong(property.getTimeZone());
					Country country = propertyService.getCountryById(countryId);
					propertyTimeZone = TimeZone.getTimeZone(country.getGmtTimeDiffrence());
				} else if (property.getContactId() != null && property.getContactId().getCountryId() != null) {
					// log.info("validating timeZone through country");
					propertyTimeZone = TimeZone.getTimeZone(property.getContactId().getCountryId().getGmtTimeDiffrence());

				} else {
					// If no Country/Time Zone Assume Server Time Zone
					propertyTimeZone = TimeZone.getDefault();
				}

				// Reading Server Time Zone
				TimeZone tzDefault = TimeZone.getDefault();
				int defaultHours = (int) TimeUnit.MILLISECONDS.toHours(tzDefault.getRawOffset());
				int defaultMinutes = (int) (TimeUnit.MILLISECONDS.toMinutes(tzDefault.getRawOffset()) - TimeUnit.HOURS.toMinutes(defaultHours));

				// Reading proprty Time Zone
				TimeZone propertyTz = propertyTimeZone;
				int propertyHours = (int) TimeUnit.MILLISECONDS.toHours(propertyTz.getRawOffset());
				int propertyMinutes = (int) (TimeUnit.MILLISECONDS.toMinutes(propertyTz.getRawOffset()) - TimeUnit.HOURS.toMinutes(propertyHours));
				// calculating TimeZone diffrence between Property and server timeZone
				int hours = defaultHours - propertyHours;
				int minutes = defaultMinutes - propertyMinutes;

				// setting the actual time to the input Date
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				cal.add(Calendar.HOUR_OF_DAY, hours);
				cal.add(Calendar.MINUTE, minutes);
				return cal.getTime();
			}
		} catch (Exception e) {
			// log.info("Invalid property/Date");
			return date;
		}
		return date;
	}

	*//**
	 * 
	 * @param property
	 * @param datePattern
	 * @return
	 *//*
	public Date getPropertyDateTime(Property property, final String datePattern) {
		try {
			TimeZone propertyTimeZone = null;
			if (property.getContactId() != null && property.getContactId().getCountryId() != null) {
				propertyTimeZone = TimeZone.getTimeZone(property.getContactId().getCountryId().getGmtTimeDiffrence());
			} else if (property.getTimeZone() != null && !property.getTimeZone().trim().isEmpty()) {
				long countryId = Long.parseLong(property.getTimeZone());
				Country country = propertyService.getCountryById(countryId);
				propertyTimeZone = TimeZone.getTimeZone(country.getGmtTimeDiffrence());

			} else {
				propertyTimeZone = TimeZone.getDefault();
			}

			DateTimeZone propertyDateTimeZone = null;
			propertyDateTimeZone = DateTimeZone.forTimeZone(propertyTimeZone);
			DateTime dateTime = new LocalDateTime().toDateTime(DateTimeZone.forTimeZone(TimeZone.getDefault()));
			SimpleDateFormat propertyFormat = new SimpleDateFormat(datePattern != null ? datePattern : "dd-MM-yy HH:mm:ss");
			String out = propertyFormat.format(dateTime.toDate());
			DateTimeFormatter formatDT = DateTimeFormat.forPattern(datePattern != null ? datePattern : "dd-MM-yy HH:mm:ss");
			DateTime dateDateTime = formatDT.parseDateTime(out).toDateTime(propertyDateTimeZone);
			return dateDateTime.toDate();

		} catch (Exception e) {
			return null;
		}
	}

	*//**
	 * 
	 * 
	 *//*

	@SuppressWarnings("deprecation")
	public Date convertPropertyDateTimeToServerDateTime(Property property, Date date, Date time) {
		try {
			// log.info("concatanating date and time ");
			if (date != null && time != null) {
				date.setHours(time.getHours());
				date.setMinutes(time.getMinutes());
				date.setSeconds(time.getSeconds());
			}
			return convertPropertyDateTimeToServerDateTime(property, date);

		} catch (Exception e) {
			// log.info("Date And Time Conversion Error....");
			return date;
		}

	}

	*//**
	 * 
	 * 
	 *//*

	@SuppressWarnings("deprecation")
	public Date convertPropertyDateTimeToServerDateTime(long propertyId, Date date, Date time) {
		try {
			Property property = propertyService.getPropertyById(propertyId);
			// log.info("concatanating date and time ");
			if (date != null && time != null) {
				date.setHours(time.getHours());
				date.setMinutes(time.getMinutes());
				date.setSeconds(time.getSeconds());
			}

			return convertPropertyDateTimeToServerDateTime(property, date);

		} catch (Exception e) {
			// log.info("Date And Time Conversion Error....");
			return date;
		}
	}

	public TimeZone getTimeZoneBasedOnProperty() {
		return TimeZone.getDefault();
	}

	*//**
	 * To Get Time Zone based on Property
	 * 
	 * @return
	 *//*

	public TimeZone getTimeZoneBasedOnProperty(Property property) {
		try {
			if (property != null) {
				if (property.getTimeZone() != null && !property.getTimeZone().trim().isEmpty()) {
					long countryId = Long.parseLong(property.getTimeZone());
					Country country = propertyService.getCountryById(countryId);
					return TimeZone.getTimeZone(country.getGmtTimeDiffrence());
				} else if (property.getContactId() != null && property.getContactId().getCountryId() != null) {
					return TimeZone.getTimeZone(property.getContactId().getCountryId().getGmtTimeDiffrence());
				} else {
					return TimeZone.getDefault();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return TimeZone.getDefault();
		}
		return TimeZone.getDefault();
	}

	*//**
	 * Get user Locale for birt Internationalization
	 * 
	 * @return
	 *//*

	public static String getCurrentUserLanguage() {
		// log.info("getting Current User Language...");
		try {
			if (getCurrentAccount() != null) {
				// log.info(getCurrentAccount().getLocale());
				return "captions_" + getCurrentAccount().getLocale().getSymbol();
			} else {
				// log.info("Error.........Default Language...English");
				return "captions_en";
			}
		} catch (Exception e) {
			// log.info("Error.........Default Language...English");
			return "captions_en";
		}
	}

	*//**
	 * To Get Time Zone based on Property
	 * 
	 * @return
	 *//*

	public TimeZone getTimeZoneBasedOnProperty(long propertyId) {
		try {
			Property property = propertyService.getPropertyById(propertyId);
			if (property != null) {
				if (property.getTimeZone() != null && !property.getTimeZone().trim().isEmpty()) {
					long countryId = Long.parseLong(property.getTimeZone());
					Country country = propertyService.getCountryById(countryId);
					return TimeZone.getTimeZone(country.getGmtTimeDiffrence());
				} else if (property.getContactId() != null && property.getContactId().getCountryId() != null) {
					return TimeZone.getTimeZone(property.getContactId().getCountryId().getGmtTimeDiffrence());
				} else {
					return TimeZone.getDefault();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return TimeZone.getDefault();
		}
		return TimeZone.getDefault();
	}

	*//**
	 * @description Format Amount as Per Property Settings
	 * @param double
	 * @param Property
	 * @return Currency
	 * @author pramod
	 *//*
	public static String convertToPropertyAmountNumberToWordFormat(final double amountValue, final Property property) {
		String result = null;
		if (amountValue >= 100000 && amountValue < 1000000) {
			double tmp = (double) ((amountValue / 100) * 0.001);
			result = convertToPropertyAmountNumberToWordFormatForPrint((amountValue / 100000) % 100, " Hundred Thousand", displayNumberFormatForDecimalAndThousand(tmp, property.getValueDecimal(), property));
		} else if (amountValue >= 1000000 && amountValue < 10000000) {
			double tmp = (double) ((amountValue / 1000) * 0.001);
			result = convertToPropertyAmountNumberToWordFormatForPrint((amountValue / 1000000) % 1000, " Million", displayNumberFormatForDecimalAndThousand(tmp, property.getValueDecimal(), property));
			// result
			// =convertToPropertyAmountNumberToWordFormatForPrint((amountValue /
			// 100000)%1000,
			// "Million",displayNumberFormatForDecimalAndThousand(tmp,
			// property.getValueDecimal(), property));
		} else if (amountValue >= 10000000 && amountValue < 100000000) {
			double tmp = (double) ((amountValue / 1000) * 0.001);
			result = convertToPropertyAmountNumberToWordFormatForPrint((amountValue / 1000000) % 10000, " Million", displayNumberFormatForDecimalAndThousand(tmp, property.getValueDecimal(), property));
		} else if (amountValue >= 100000000 && amountValue < 1000000000) {
			double tmp = (double) ((amountValue / 10000) * 0.0001);
			result = convertToPropertyAmountNumberToWordFormatForPrint((amountValue / 1000000) % 10000, " Hundred Million", displayNumberFormatForDecimalAndThousand(tmp, property.getValueDecimal(), property));
		} else if (amountValue >= 1000000000 && amountValue < 10000000000l) {
			double tmp = (double) ((amountValue / 10000) * 0.00001);
			result = convertToPropertyAmountNumberToWordFormatForPrint((amountValue / 1000000) % 1000, " Billion", displayNumberFormatForDecimalAndThousand(tmp, property.getValueDecimal(), property));
		} else if (amountValue >= 10000000000l && amountValue < 100000000000l) {
			double tmp = (double) ((amountValue / 100000) * 0.0001);
			result = convertToPropertyAmountNumberToWordFormatForPrint((amountValue / 1000000) % 10000, " Billion", displayNumberFormatForDecimalAndThousand(tmp, property.getValueDecimal(), property));
		} else if (amountValue >= 100000000000l && amountValue < 1000000000000l) {
			double tmp = (double) ((amountValue / 10000) * 0.0000001);
			result = convertToPropertyAmountNumberToWordFormatForPrint((amountValue / 1000000) % 10000, " Hundred Billion", displayNumberFormatForDecimalAndThousand(tmp, property.getValueDecimal(), property));
		} else if (amountValue >= 1000000000000l && amountValue < 10000000000000l) {
			double tmp = (double) ((amountValue / 100000) * 0.0000001);
			result = convertToPropertyAmountNumberToWordFormatForPrint((amountValue / 10000000) % 100000, " Trillion", displayNumberFormatForDecimalAndThousand(tmp, property.getValueDecimal(), property));
		} else if (amountValue >= 10000000000000l && amountValue < 100000000000000l) {
			double tmp = (double) ((amountValue / 10000) * 0.00000001);
			result = convertToPropertyAmountNumberToWordFormatForPrint((amountValue / 1000000) % 10000, " Trillion", displayNumberFormatForDecimalAndThousand(tmp, property.getValueDecimal(), property));
		} else if (amountValue >= 100000000000000l && amountValue < 1000000000000000l) {
			double tmp = (double) ((amountValue / 10000) * 0.0000000001);
			result = convertToPropertyAmountNumberToWordFormatForPrint((amountValue / 1000000) % 10000, " Hundred Trillion", displayNumberFormatForDecimalAndThousand(tmp, property.getValueDecimal(), property));
		} else if (amountValue >= 1000000000000000l && amountValue < 10000000000000000l) {
			double tmp = (double) ((amountValue / 1000) * 0.000000000001);
			result = convertToPropertyAmountNumberToWordFormatForPrint((amountValue / 1000000) % 10000, " Quadrillian", displayNumberFormatForDecimalAndThousand(tmp, property.getValueDecimal(), property));
		} else if (amountValue >= 10000000000000000l) {
			double tmp = (double) ((amountValue / 10000) * 0.00000000001);
			result = convertToPropertyAmountNumberToWordFormatForPrint((amountValue / 1000000) % 10000, " Quadrillian", displayNumberFormatForDecimalAndThousand(tmp, property.getValueDecimal(), property));
		}

		else {
			result = displayNumberFormatForDecimalAndThousand(amountValue, property.getValueDecimal(), property);
		}
		return result;
	}

	public static String convertToPropertyAmountNumberToWordFormatForPrint(double amt, String str, String decimal) {
		// String result = "";
		return decimal + str;

		// return result;

	}

	public static User getCurrentAccount() {
		return currentAccount;
	}

	public static void setCurrentAccount(User currentAccount) {
		WebUtil.currentAccount = currentAccount;
	}

	public String loadHomePage() {
		log.info("Loading Home Page");
		return NavigationStrings.HOME_PAGE;
	}

	public static boolean validateEmail(String email) {
		// log.info("validateEmail calling.......");
		boolean temp = false;
		try {
			Pattern pattern = Pattern.compile("^(.+)@(.+)$");
			temp = pattern.matcher(email).matches();
		} catch (Exception e) {
			// log.info("Exception In validateEmail method..");
		}
		return temp;
	}

	*//**
	 * To get the base url of application like:http://172.16.11.152:8080/JPMS
	 * 
	 * @return String
	 *//*

	private String url;

	public String getURL() {
		if (url == null) {
			HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			url = req.getRequestURL().toString();
			url = (url.substring(0, url.length() - req.getRequestURI().length()) + req.getContextPath() + "/");
		}
		return url;
	}

	*//**
	 * @desc : for counting number of adults in a room
	 * @param bgd
	 * @param bgdlist
	 * @return
	 * @author SHIVRANJAN
	 *//*
	public static byte getNumberOfAdultForRoom(BookingGuestDetail bgd, List<BookingGuestDetail> bgdlist) {
		byte adult = 0;
		try {
			for (BookingGuestDetail sbgd : bgdlist) {
				if (sbgd.getBookingReferenceNumber() == bgd.getBookingReferenceNumber()) {
					if (sbgd.getAdultType().equals(AdultType.Adult) || sbgd.getAdultType().equals(AdultType.ExtraAdult)) {
						++adult;
					}
				}
			}
		} catch (Exception e) {
			log.error("Exception In getNumberOfAdultForRoom");
		}
		return adult;
	}

	*//**
	 * @desc : for counting number of childs in a room
	 * @param bgd
	 * @param bgdlist
	 * @return
	 * @author SHIVRANJAN
	 *//*
	public static byte getNumberOfChildInRoom(BookingGuestDetail bgd, List<BookingGuestDetail> bgdlist) {
		byte child = 0;
		try {
			for (BookingGuestDetail sbgd : bgdlist) {
				if (sbgd.getBookingReferenceNumber() == bgd.getBookingReferenceNumber()) {
					if (sbgd.getAdultType().equals(AdultType.Child) || sbgd.getAdultType().equals(AdultType.ExtraChild)) {
						++child;
					}
				}
			}
		} catch (Exception e) {
			log.error("Exception In getNumberOfAdultForRoom");
		}
		return child;
	}

	*//**
	 * @author Satish Keshri This will Take datePattern as String Date in any of these Formats (2016-04-24),(2016/04/24,(2016.04.24)) and validate PassedDate
	 * @param datePattern
	 * @return true/false
	 *//*
	public static boolean validateDatePattern(String datePattern) {
		if (!datePattern.matches("^([0-9]{4})[- /.](0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])$"))
			return false;
		else
			return true;

	}

}*/