package com.org.kap.controller;

import java.io.File;
import java.io.StringWriter;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.org.kap.dto.CourseEntry;
import com.org.kap.dto.ExamPaper;
import com.org.kap.dto.ExamSchedule;

import com.org.kap.dto.SearchExamCode;
import com.org.kap.dto.SetQuestion;

import com.org.kap.dto.TabQuestionMaster;

import com.org.kap.service.RegistrationService;

@Controller
public class RegistrationController {

	@Autowired
	private RegistrationService registrationService;

		
}
