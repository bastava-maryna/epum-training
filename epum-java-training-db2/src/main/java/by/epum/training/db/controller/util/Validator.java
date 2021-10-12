package by.epum.training.db.controller.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
	private final static int NAME_FIELD_MAX_LENGTH = 30;
	private final static int EMAIL_FIELD_MAX_LENGTH = 40;
	private final static int PASSWORD_FIELD_LENGTH = 10;
	private final static int PASSPORT_FIELD_MAX_LENGTH = 30;
	private final static int PASSPORT_FIELD_MIN_LENGTH = 6;
	private final static int LOGIN_FIELD_MAX_LENGTH = 20;

	private static final Pattern NAME_PATTERN=Pattern.compile("^([A-Z][a-z]+)(?:[ |-][A-Z][a-z]+){0,2}$");
	private static final Pattern EMAIL_PATTERN=Pattern.compile("[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+↵\r\n"
			+ ")*@(?:[A-Za-z0-9-]+\\.)+[A-Za-z]{2,6}$");//check if correct
	private static final Pattern PASSWORD_PATTERN=Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!?@#$%^&+=])(?=\\S+$).{10}$");


	public static boolean isNameValid(String name) {
		if(name == null || name.length() > NAME_FIELD_MAX_LENGTH || name.isBlank()) {
			return false;
		}

		Matcher matcher=NAME_PATTERN.matcher(name);

		return matcher.find();	   
	}

	public static boolean isEmailValid(String name) {
		if(name==null || name.isBlank()) {
			return true;
		}

		if( name.length() > EMAIL_FIELD_MAX_LENGTH || name.isBlank()) {
			return false;
		}

		Matcher matcher=EMAIL_PATTERN.matcher(name);
		return matcher.find();	

	}

	public static boolean isPasswordValid(String name) {
		if(name == null || name.length() != PASSWORD_FIELD_LENGTH ||name.isBlank()) {
			return false;
		}
		Matcher matcher=PASSWORD_PATTERN.matcher(name);

		return matcher.find();	   
	}

	public static boolean isPassportValid(String name) {
		if(name == null || name.length() > PASSPORT_FIELD_MAX_LENGTH || name.length() < PASSPORT_FIELD_MIN_LENGTH ||name.isBlank()) {
			return false;
		}

		return true;	   
	}

	public static boolean isLoginValid(String name) {
		if(name == null || name.length() > LOGIN_FIELD_MAX_LENGTH ||name.isBlank()) {
			return false;
		}

		return true;	   
	}

	public static Map<String,String> validateSignUpForm(String lastName,String firstName,String passport,String email,String login, String password, String repeatPassword) {
		Map<String,String> errors=validateSignUpForm(lastName,firstName,passport,email,login, password);

		if(!password.equals(repeatPassword)) {
			errors.put("passwordConfirm","Password confirm doesnt mathch password");
		}

		return errors;
	}

	public static Map<String,String> validateSignUpForm(String lastName,String firstName,String passport,String email,String login, String password) {
		Map<String,String> errors=new HashMap<String,String>();

		if(!isNameValid(lastName)) {
			errors.put("lastName","Last name incorrect.");
		}
		if(!isNameValid(firstName)) {
			errors.put("firstName","First name incorrect.");
		}
		if(!isPassportValid(passport)) {
			errors.put("passport","Passport too long or too short.");
		}
		if(!isEmailValid(email)) {
			errors.put("email","Email incorrect.");
		}
		if(!isLoginValid(login)) {
			errors.put("login","Login too long.");
		}
		if(!isPasswordValid(password)) {
			errors.put("password","Password incorrect.");
		}

		return errors;
	}

	public static Map<String,String> validateStationEditForm(String stationName) {
		Map<String,String> errors=new HashMap<String,String>();

		if(!isNameValid(stationName)) {
			errors.put("stationName","Station name incorrect.");
		}

		return errors;
	}

	public static Map<String,String> validateRouteEditForm(Integer departureId, Integer destinationId) {
		Map<String,String> errors=new HashMap<String,String>();

		if(departureId==null || destinationId==null ) {
			errors.put("nullRoute","Route is null.");
		}
		
		if(departureId!=null && destinationId!=null && departureId.equals( destinationId)) {
			errors.put("routeStations","Departure equals destination.");
		}
		
		return errors;
	}
}

