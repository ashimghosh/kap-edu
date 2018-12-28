package com.org.kap.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.stereotype.Controller;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.org.kap.dto.AnsherSheet;
import com.org.kap.dto.Answer;
import com.org.kap.dto.ExamQuestions;
@Controller
public class GenerateXml {
	private static 	org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(GenerateXml.class);
	 
	
	static List<ExamQuestions> questions=new ArrayList<ExamQuestions>() ;
	static List<AnsherSheet> ansherSheet=new ArrayList<AnsherSheet>() ;
	public synchronized String generatexml(String[] allquestion,String[] otion1,String[] otion2,String[] otion3,String[] otion4,String[] rightansw) {
		String xml=null;
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = docFactory.newDocumentBuilder();
			Document document = documentBuilder.newDocument();
	         Element rootElement = document.createElement("QusestonsList");
	         document.appendChild(rootElement);
	         for(int i=0;i<allquestion.length;i++) {
	         Element questions = document.createElement("Questions");
	         rootElement.appendChild(questions);
	         Element slno = document.createElement("Sl_no");
	         slno.appendChild(document.createTextNode(i+1+""));
	         questions.appendChild(slno);

	         Element question = document.createElement("Question_name");
	         question.appendChild(document.createTextNode(new String(allquestion[i].getBytes("ISO-8859-1"), "UTF-8")));
	         questions.appendChild(question);
	         
	         Element opt1 = document.createElement("Option1");
	         opt1.appendChild(document.createTextNode(new String(otion1[i].getBytes("ISO-8859-1"), "UTF-8")));
	         questions.appendChild(opt1);
	         
	         
	         Element opt2 = document.createElement("Option2");
	         opt2.appendChild(document.createTextNode(new String(otion2[i].getBytes("ISO-8859-1"), "UTF-8")));
	         questions.appendChild(opt2);
	         
	         Element opt3 = document.createElement("Option3");
	         opt3.appendChild(document.createTextNode(new String(otion3[i].getBytes("ISO-8859-1"), "UTF-8")));
	         questions.appendChild(opt3);
	         
	         Element opt4 = document.createElement("Option4");
	         opt4.appendChild(document.createTextNode(new String(otion4[i].getBytes("ISO-8859-1"), "UTF-8")));
	         questions.appendChild(opt4);
	         
	         Element rightans = document.createElement("Answer");
	         rightans.appendChild(document.createTextNode(rightansw[i]));
	         questions.appendChild(rightans);
	         }
	         TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(document);
				
				StringWriter stringWriter = new StringWriter();
				transformer.transform(source, new StreamResult(stringWriter)); 
				xml = stringWriter.toString();
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
		
		
		return xml;
		
	}
	
public  List<ExamQuestions> parsexml(String xml) throws ParserConfigurationException, IOException, SAXException, org.xml.sax.SAXException{
		DocumentBuilderFactory dbFactory1 = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder1 = dbFactory1.newDocumentBuilder();
		Document doc1 = dBuilder1.parse(new ByteArrayInputStream(xml.getBytes()));
		doc1.getDocumentElement().normalize();
		logger.info("Root element :" + doc1.getDocumentElement().getNodeName());
		NodeList nList1 = doc1.getElementsByTagName("Questions");
		for (int temp = 0; temp < nList1.getLength(); temp++) {
			Node nNode1 = nList1.item(temp);
			logger.info("\nCurrent Element :" + nNode1.getNodeName());
				if (nNode1.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode1;
				logger.info(eElement.getElementsByTagName("Sl_no").item(0).getTextContent().toString());
				logger.info(eElement.getElementsByTagName("Question_name").item(0).getTextContent().toString());
				logger.info(eElement.getElementsByTagName("Option1").item(0).getTextContent().toString());
				logger.info(eElement.getElementsByTagName("Option2").item(0).getTextContent().toString());
				logger.info(eElement.getElementsByTagName("Option3").item(0).getTextContent().toString());
				logger.info(eElement.getElementsByTagName("Option4").item(0).getTextContent().toString());
				logger.info(eElement.getElementsByTagName("Answer").item(0).getTextContent().toString());
				ExamQuestions exam=new ExamQuestions();	
				exam.setSlno(eElement.getElementsByTagName("Sl_no").item(0).getTextContent().toString());
				exam.setQuestion(eElement.getElementsByTagName("Question_name").item(0).getTextContent().toString());
				exam.setOption1(eElement.getElementsByTagName("Option1").item(0).getTextContent().toString());
				exam.setOption2(eElement.getElementsByTagName("Option2").item(0).getTextContent().toString());
				exam.setOption3(eElement.getElementsByTagName("Option3").item(0).getTextContent().toString());
				exam.setOption4(eElement.getElementsByTagName("Option4").item(0).getTextContent().toString());
				exam.setRightans(eElement.getElementsByTagName("Answer").item(0).getTextContent().toString());
				questions.add(exam);
			}
		}
		return questions;
		 
		
		
	}
public String generatEexamCode(String cobminationId) {
String examcode=getToken(6,HmacSHA256(cobminationId));
return examcode;
	
}

public synchronized String generatexmlforStudentanswer(ArrayList<String> allquestion,ArrayList<String> otion1,ArrayList<String> otion2,ArrayList<String> otion3,ArrayList<String> otion4,ArrayList<String> rightansw,ArrayList<String> studentans) {
	String xml=null;
	try {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = docFactory.newDocumentBuilder();
		Document document = documentBuilder.newDocument();
         Element rootElement = document.createElement("QusestonsList");
         document.appendChild(rootElement);
         for(int i=0;i<allquestion.size();i++) {
         Element questions = document.createElement("Questions");
         rootElement.appendChild(questions);
         Element slno = document.createElement("Sl_no");
         slno.appendChild(document.createTextNode(i+1+""));
         questions.appendChild(slno);

         Element question = document.createElement("Question_name");
         question.appendChild(document.createTextNode(new String(allquestion.get(i).getBytes("ISO-8859-1"), "UTF-8")));
         questions.appendChild(question);
         
         Element opt1 = document.createElement("Option1");
         opt1.appendChild(document.createTextNode(new String(otion1.get(i).getBytes("ISO-8859-1"), "UTF-8")));
         questions.appendChild(opt1);
         
         
         Element opt2 = document.createElement("Option2");
         opt2.appendChild(document.createTextNode(new String(otion2.get(i).getBytes("ISO-8859-1"), "UTF-8")));
         questions.appendChild(opt2);
         
         Element opt3 = document.createElement("Option3");
         opt3.appendChild(document.createTextNode(new String(otion3.get(i).getBytes("ISO-8859-1"), "UTF-8")));
         questions.appendChild(opt3);
         
         Element opt4 = document.createElement("Option4");
         opt4.appendChild(document.createTextNode(new String(otion4.get(i).getBytes("ISO-8859-1"), "UTF-8")));
         questions.appendChild(opt4);
         
         Element rightans = document.createElement("Answer");
         rightans.appendChild(document.createTextNode(rightansw.get(i)));
         questions.appendChild(rightans);
         
         
         Element student = document.createElement("Student_Answer");
         String answer=studentans.get(i);
         if(answer==null){
        	 answer=""; 
         }
         student.appendChild(document.createTextNode(answer));
         questions.appendChild(student);
         }
         TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(document);
			
			StringWriter stringWriter = new StringWriter();
			transformer.transform(source, new StreamResult(stringWriter)); 
			xml = stringWriter.toString();
      } catch (Exception e) {
         e.printStackTrace();
      }
	
	
	return xml;
	
}
public static String getToken(int length,String str) {
	 Random random = new Random();
	 String CHARS = str;
   StringBuilder token = new StringBuilder(length);
   for (int i = 0; i < length; i++) {
       token.append(CHARS.charAt(random.nextInt(CHARS.length())));
   }
   return token.toString();
}
public static String char2hex(byte x)

{
	char arr[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
			'B', 'C', 'D', 'E', 'F' };

	char c[] = { arr[(x & 0xF0) >> 4], arr[x & 0x0F] };
	return (new String(c));
}

public static String HmacSHA256(String message) {

	try {
		String secret="KAP";
		Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
		SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(),
				"HmacSHA256");
		sha256_HMAC.init(secret_key);
		byte raw[] = (message.getBytes());
		StringBuffer ls_sb = new StringBuffer();
		for (int i = 0; i < raw.length; i++)
			ls_sb.append(char2hex(raw[i]));
		return ls_sb.toString(); // step 6
	} catch (Exception e) {
		e.printStackTrace();
		return null;
	}
}
public  List<AnsherSheet> parsexmlAnswersheet(String xml) throws ParserConfigurationException, IOException, SAXException, org.xml.sax.SAXException{
	DocumentBuilderFactory dbFactory1 = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder1 = dbFactory1.newDocumentBuilder();
	Document doc1 = dBuilder1.parse(new ByteArrayInputStream(xml.getBytes()));
	doc1.getDocumentElement().normalize();
	logger.info("Root element :" + doc1.getDocumentElement().getNodeName());
	NodeList nList1 = doc1.getElementsByTagName("Questions");
	ansherSheet.clear();
	for (int temp = 0; temp < nList1.getLength(); temp++) {
		Node nNode1 = nList1.item(temp);
		logger.info("\nCurrent Element :" + nNode1.getNodeName());
			if (nNode1.getNodeType() == Node.ELEMENT_NODE) {
			Element eElement = (Element) nNode1;
			logger.info(eElement.getElementsByTagName("Sl_no").item(0).getTextContent().toString());
			logger.info(eElement.getElementsByTagName("Question_name").item(0).getTextContent().toString());
			logger.info(eElement.getElementsByTagName("Option1").item(0).getTextContent().toString());
			logger.info(eElement.getElementsByTagName("Option2").item(0).getTextContent().toString());
			logger.info(eElement.getElementsByTagName("Option3").item(0).getTextContent().toString());
			logger.info(eElement.getElementsByTagName("Option4").item(0).getTextContent().toString());
			logger.info(eElement.getElementsByTagName("Answer").item(0).getTextContent().toString());
			logger.info(eElement.getElementsByTagName("Student_Answer").item(0).getTextContent().toString());
			AnsherSheet exam=new AnsherSheet();	
			exam.setSlno(eElement.getElementsByTagName("Sl_no").item(0).getTextContent().toString());
			exam.setQuestion(eElement.getElementsByTagName("Question_name").item(0).getTextContent().toString());
			exam.setOption1(eElement.getElementsByTagName("Option1").item(0).getTextContent().toString());
			exam.setOption2(eElement.getElementsByTagName("Option2").item(0).getTextContent().toString());
			exam.setOption3(eElement.getElementsByTagName("Option3").item(0).getTextContent().toString());
			exam.setOption4(eElement.getElementsByTagName("Option4").item(0).getTextContent().toString());
			exam.setRightans(eElement.getElementsByTagName("Answer").item(0).getTextContent().toString());
			exam.setStudentoption(eElement.getElementsByTagName("Student_Answer").item(0).getTextContent().toString());
			ansherSheet.add(exam);
		}
	}
	return ansherSheet;
	 
	
	
}

}
