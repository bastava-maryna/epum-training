package by.epum.training.string.servise.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import by.epum.training.string.entity.Abzac;
import by.epum.training.string.entity.Mark;
import by.epum.training.string.entity.Paragraph;
import by.epum.training.string.entity.Part;
import by.epum.training.string.entity.Sentence;
import by.epum.training.string.entity.Unit;
import by.epum.training.string.entity.Word;
import by.epum.training.string.servise.ServiceProvider;


public class BookParser {
	private static final String COMPONENT_REGEX="\\* \\* \\*\\s*";
	private static final String MAIN_PART_REGEX="(\\r\\n){6,}";
	private static final String PARAGRAPH_REGEX="(\\r\\n){3,}";
	private static final String ABZAC_REGEX="((?<!\\:)\\r\\n){2,}";
	private static final String SENTENCE_REGEX="[(А-ЯЁёа-яA-Za-z)\\:\\ a-z\\d©®+@°\\,\\«\\»\\–\\„\\“\\-\\;]*[\\.\\…\\?\\!]? ?\n*";
	private static final String WORD_REGEX="([А-ЯЁёа-яA-za-z]*-[А-ЯЁёа-яA-za-z]*)|([_A-Za-z\\d\\-\\.]*@[_A-Za-z\\d\\-\\.]+\\.[A-Za-z]{2,})? |(\\+\\d{3}\\(\\d{2}\\)\\d{3}\\-\\d{2}\\-\\d{2})? |([А-ЯЁёа-яA-za-z\\d]*) ?([,–\\.\\…«»®°©!„\\(\\)“\\?:\\-])? ?\n*";
	
	private static final Logger LOGGER=Logger.getLogger(BookParser.class);
	
	public String[] parseToComponents(String text) { 
		return text.split(COMPONENT_REGEX);
	}
	
	public Unit parseToSimplePart(String part) {
		List<Unit> units=null;
		
		units=parseToParagraphs(part);
		
		Unit unit=new Part(units);
		return unit;
	}
	
	public Unit parseMainToParts(String text) {
		String []pars=text.split(MAIN_PART_REGEX);
		
		List<Unit> parts=new LinkedList<>();
		String unitTitle;
		String unitName;
		
		//for study purpose parse a few parts		
		//for(int i=0;i<3;i++) {
			//List<Unit> units=parseToParagraphs(pars[i]);
		for(String s:pars) {	
			List<Unit> units=parseToParagraphs(s);
			
			unitTitle=units.get(0).getAllUnits().get(0).getUnitContent();
			unitName=units.get(0).getAllUnits().get(1).getUnitContent();
			
			Unit unit=new Part((unitTitle+unitName),units);
			parts.add(unit);		
		}
		Unit unit=new Part(parts);	
	
		return unit;
	}
	
	public List<Unit> parseToParagraphs(String part) {
		ServiceProvider sp=ServiceProvider.getInstance();
		
		String[]pars=part.split(PARAGRAPH_REGEX);
		List<Unit>paragraphs=new LinkedList<>();

		for(int i=0;i<pars.length;i++) {
			LOGGER.info("\nOriginal Paragraph "+(i+1)+":\n"+pars[i]+"\n\nStart Paragraph "+(i+1));
		
			Unit paragraph=new Paragraph(parseToAbzacs(pars[i]));
			paragraphs.add(paragraph);
			
			LOGGER.info("\nEnd Paragraph "+(i+1)+"   paragraph_size="+paragraph.getAllUnits().size()+":\n"+sp.getBookService().getUnitContent(paragraph));	
		}
	
		return paragraphs;
	}
	
	
	public static List<Unit> parseToAbzacs(String part) {
		ServiceProvider sp=ServiceProvider.getInstance();
		
		String[]pars=part.split(ABZAC_REGEX);
		List<Unit>abzacs=new LinkedList<>();
		
		for(int i=0;i<pars.length;i++) {
			LOGGER.info("\nOriginal Abzac "+(i+1)+" :\n"+pars[i]+"\n\nStart Abzac "+(i+1));
			
			Unit abzac=new Abzac(parseToSentences(pars[i]));
			abzacs.add(abzac);
			
			LOGGER.info("\nEnd Abzac "+(i+1)+"   abzac_size="+abzac.getAllUnits().size()+":\n"+sp.getBookService().getUnitContent(abzac));
		}
		
		return abzacs;
	}
	
	public static List<Unit> parseToSentences(String part)  {
		Pattern p=Pattern.compile(SENTENCE_REGEX);
		Matcher m=p.matcher(part);
		
		List<String> s=new ArrayList<>();
		int ind=0;
		
		//create dirty sentences
		while(m.find()) {
			if(!m.group().isBlank() ) {
				s.add(m.group());
			}
		}
		
		//to fix M. Sokolova and : [a-z] at the end 
		String regex1="(.*[А-ЯA-Z]\\.|.*\\:$)(?! [А-ЯA-Z])";
		Pattern p1=Pattern.compile(regex1);
				
		//to fix )[a-z] or -[a-z] or [a-z] or » at the start
		String regex2="(^\\)|(^\\–)\\ [а-я]|(^[а-я])|(^\\»))";
		Pattern p2=Pattern.compile(regex2);
		
		int size=s.size();
		int removes=0;
		
		//make some joins
		if(size>1) {
			for(int i=0;i<size-removes;) {
				String cur=s.get(i);
				
				Matcher m2=p2.matcher(cur);
				if(i!=0 && m2.find()) {
					s.set(i-1, String.join("",s.get(i-1),cur));
					s.remove(i);
					removes++;
				}else if(cur!=null && i!=size-removes-1) {//?==
					Matcher m1=p1.matcher(cur);
					String next=s.get(i+1);
					if(m1.find()&& next!=null) {
						s.set(i, String.join("",cur,next));
						s.remove(i+1);
						
						removes++;
					}else {	
						i++;
					}
				}else {
					i++;
				}
			}					
		}
		
		List<Unit> sentences=new LinkedList<>();
		
		for(String ss:s) {
			ind++;
			LOGGER.info("\nOriginal sentence "+ind+":\n"+ss+"\n");
			
			Unit sentence=parseToWords(ss);
			ServiceProvider sp=ServiceProvider.getInstance();
			
			sentences.add(sentence);
			
			LOGGER.info("\nSentence "+ind+"   sent_size="+sentence.getAllUnits().size()+":\n"+sp.getBookService().getUnitContent(sentence));
			LOGGER.info("\n"+sp.getBookService().getView(sentence));		
		}
	
		return sentences;	
	}
	
	public static Unit parseToWords(String part) {
		Pattern p=Pattern.compile(WORD_REGEX);
		Matcher m=p.matcher(part);
		
		Unit sentence=new Sentence();

		while(m.find()) {
			for(int i=1;i<=m.groupCount();i++) {
				
				if(m.group(i)!=null && !m.group(i).equals("")) {
					String temp=m.group(i);
					Unit unit=null;
					
					if(temp.length()==1 && !Character.isLetterOrDigit(temp.charAt(0))) {
						unit=new Mark(temp.charAt(0));
					}else {
						unit=new Word(temp);
					}
						
					 sentence.addUnit(unit);
				}
			}
		}
		
		return sentence;
	}
}
