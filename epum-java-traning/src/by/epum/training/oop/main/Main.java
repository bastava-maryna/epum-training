package by.epum.training.oop.main;

import java.util.Scanner;

import by.epum.training.oop.command.CommandName;
import by.epum.training.oop.controller.Controller;
import by.epum.training.oop.service.exception.ServiceException;
import by.epum.training.oop.service.exception.ServiceValidatorException;


public class Main {

	public static void main(String[] args) throws ServiceException {
	
		Controller controller=new Controller();
	
		Scanner input = new Scanner(System.in);
		int choice=0;       
		do {
			showMenu();
			
			try {
		        choice=enterIntValue(input, "\nEnter the option: ");  
		        
		        String request;
		        Long id;
		        int year;
		        int month;
		        String lastName;
		        
		        switch (choice){
		        	case 1: 
		        		//"1-GET_BY_ID id=10060"
		                id=enterLongValue(input, "Enter taxPayer id:");
		                request=CommandName.values()[choice-1]+" id="+id; //request="GET_BY_ID"+" id="+id;   //what is better: use CommandName here or simple string
		                
		                execute(controller,input,request);
		                break;
		               
		        	case 2:
		        		//"2-FIND_BY_LAST_NAME lastName=Ivanova"
		                lastName=enterStringValue(input, "Enter taxPayer last name:");
		                request=CommandName.values()[choice-1]+" lastName="+lastName; //request="FIND_BY_LAST_NAME"+" last name="+lastName;  //what is better: use CommandName here or simple string
		                
		                execute(controller,input,request);
		                break;
		             
		            case 3: case 4 : case 5: case 6:
		            	//"3-CALCULATE_ANNUAL_TAX id=10060 year=2020"  
		            	//"4-GET_INCOMES id=10060 year=2020"   
		            	//"5-DISPLAY_ANNUAL_TAX id=10060 year=2020"
		            	//"6-SORT_ANNUAL_TAX_BY_TAX id=10060 year=2020"
		            	id=enterLongValue(input, "Enter taxPayer id:");
		            	year=enterIntValue(input, "Enter year:");
		            	request=CommandName.values()[choice-1]+" id="+id+" year="+year;
		            	
		            	execute(controller,input,request);
		                break;
	                
		            case 7:
		        		//"7-GET_TAX_DATA year=2020"
		                year=enterIntValue(input, "Enter year:");
		                request=CommandName.values()[choice-1]+" year="+year;
		                
		                execute(controller,input,request);
		                break;
		               
		            case 8: case 9 : 
		            	//"8-GET_MONTH_INCOMES id=10060 year=2020 month=5"  
		            	//"9-DISPLAY_Period_TAX id=10060 year=2020 month=5" 
		            	id=enterLongValue(input, "Enter taxPayer id:");
		            	year=enterIntValue(input, "Enter year:");
		            	month=enterIntValue(input, "Enter month:");
		            	request=CommandName.values()[choice-1]+" id="+id+" year="+year+" month="+month;
		            	
		            	execute(controller,input,request);
		                break;
		            
		            case 10:
		            	//"10-QUIT"
		            	request=CommandName.values()[choice-1].toString();
		            	System.out.println("Your request:\n"+request);
		        		System.out.println("Your responce:\n"+controller.doAction(request));
		                break;

		            default:
		                System.out.println("Selection out of range. Try again");
		       }
		   }catch (ServiceValidatorException e) {
		                System.out.println("Validation failed:");  //what to do
		   }catch (ServiceException e) {
                System.out.println("Something happens:");  //what to do
		   }
	   }while (choice!=CommandName.QUIT.ordinal()+1);
	}

	public static void execute(Controller controller, Scanner sc, String request) throws ServiceException {
		String responce;
		System.out.println("Your request:\n"+request);
		responce=controller.doAction(request);
		System.out.println("Your responce:\n"+responce);
		prompt(sc,"Do you want to continue (C) or exit (\\E)");
	}

	public static void prompt(Scanner sc, String message) {
		String value="";
		System.out.println(message);	

		while(!sc.hasNext("[C,E,c,e]")) {
			System.out.println("Your input is out of range . Try again:");
			sc.next();
		}
		value=sc.next();
		
		if(value.equals("E")|| value.equals("e")) {
			System.out.println("Good by!");
			System.exit(0);
		}
	}
	
	public static int enterIntValue(Scanner sc, String message) {
		int value=0;
		System.out.println(message);
	
		do {
			while(!sc.hasNextInt()) {
				System.out.println("Your input is not a integer number. Try again:");
				sc.next();
			}
			value=sc.nextInt();
		
			if(value<=0) {
				System.out.println("You enter negative number or 0. Try again:");
			}
		}
		while (value<=0);
	
		return value;	
	}

	public static Long enterLongValue(Scanner sc, String message) {
		Long value=0L;
		System.out.println(message);
	
		do {
			while(!sc.hasNextLong()) {
				System.out.println("You enter value which cannot be an id. Try again:");
				sc.next();
			}
			value=sc.nextLong();
		
			if(value<=0 || value>999999999999L) {
				System.out.println("The value should be bigger than 0 and less than 1000000000000.\nYou enter "+value+". Try again:");
			}
		
			if(value<=0) {
				System.out.println("You enter negative number or 0. Try again:");
			}
		}
		while (value<=0 || value>999999999999L);
	
		return value;	
	}

	public static String enterStringValue(Scanner sc, String message) {
		String value="";
		System.out.println(message);
	
		while(!sc.hasNext("([A-Z][a-z]*[-\\s]?){1,}|([А-Я][а-яё]*[-\\s]?){1,}")) {
			System.out.println("Your input is not looks like a last name . Try again:");
			sc.next();
		}
		value=sc.next();
	
		return value;	
	}

	public static void showMenu() {
		System.out.println("\nEnter command to perform from these choices");
		System.out.println("-------------------------");
		System.out.println("1-GET_BY_ID ");
		System.out.println("2-FIND_BY_LAST_NAME ");
		System.out.println("3-CALCULATE_ANNUAL_TAX ");
		System.out.println("4-GET_INCOMES ");
		System.out.println("5-DISPLAY_ANNUAL_TAX ");
		System.out.println("6-SORT_ANNUAL_TAX_BY_TAX ");
		System.out.println("7-GET_TAX_DATA ");
		System.out.println("8-GET_MONTH_INCOMES ");
		System.out.println("9-DISPLAY_PERIOD_TAX ");
		System.out.println("10-QUIT");
	}
}