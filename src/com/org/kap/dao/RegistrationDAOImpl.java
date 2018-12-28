package com.org.kap.dao;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import com.org.kap.dto.CourseEntry;
import com.org.kap.dto.Course_Histry;
import com.org.kap.dto.Deduct_list;
import com.org.kap.dto.ExamCodeResult;
import com.org.kap.dto.ExamPaper;
import com.org.kap.dto.ExamSchedule;
import com.org.kap.dto.Exam_Result;
import com.org.kap.dto.GetQuestionList;
import com.org.kap.dto.GetRechargeHistory;
import com.org.kap.dto.GetResultHistory;
import com.org.kap.dto.Question_details;
import com.org.kap.dto.SearchExamCode;
import com.org.kap.dto.SetQuestion;
import com.org.kap.dto.Student_Results;
import com.org.kap.dto.TabQuestionMaster;
import com.org.kap.dto.User_Recharge;
import com.org.kap.dto.Userdata;
import com.org.kap.entity.User_Details;

@Repository
public class RegistrationDAOImpl implements RegistrationDAO {
	private static 	org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(RegistrationDAOImpl.class);
	 

	@Autowired
	private DataSource dataSource;
	@Override
	public Userdata authenticateUserDetails(Userdata userdata) {

		JdbcTemplate jtemp = null;
		Userdata returnObj = null;
		final String sql = "SELECT user_id,full_name,mobile_number,user_type from tab_user_details where mobile_number='"+userdata.getPhoneNo()+"'  and password=? and active_status=1";
		logger.info(sql);
		try {
			jtemp = new JdbcTemplate(dataSource);

			returnObj = jtemp.queryForObject(sql,
					new Object[] { userdata.getPassword() },
					new RowMapper<Userdata>() {
						public Userdata mapRow(ResultSet rs, int rowNum) throws SQLException {
							Userdata user = new Userdata();
							user.setTumId(rs.getInt(1));
							user.setUserName(rs.getString(2));
							user.setPhoneNo(rs.getString(3));
							user.setUserlogintype(rs.getInt(4));
							
						return user;
						}

					});

		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnObj;

	}

	

	@Override
	public int submitregistrationdata(User_Details user) {
		logger.info("In DAO =" + user);
		int response=0;

		JdbcTemplate jtemp = null;
		try {
			jtemp = new JdbcTemplate(dataSource);
			final String sql = "insert into tab_user_details (full_name,mobile_number,dob,password,email,user_type,blance_amount,active_status,register_by,register_on,gender,address,pin_code) VALUES(?,?,?,?,?,?,?,?,?,current_timestamp,?,?,?)";
			Object[] objArray = new Object[] {
					user.getFullname(),user.getMobno(),
					user.getDob(), user.getPassword(),
					user.getEmail(),user.getLogintype(),0,1,
					user.getRegistered_by(),user.getGender(),
					user.getAddress(),user.getPin()
					
			};
			response = jtemp.update(sql, objArray);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public int submitSetQuestionData(TabQuestionMaster questionMaster) {
		logger.info("In DAO =" + questionMaster);
		JdbcTemplate jtemp = null;
		int response=0;
		try {
			jtemp = new JdbcTemplate(dataSource);
			final String sql = "Insert into tab_question_master (question_paper_code,questionPaperName,question_answer_xml,inserted_by,inserted_on,course_id,no_of_questions,question_language) values (?,?,?,?,current_timestamp,?,?,?)";
			Object[] objArray = new Object[] { questionMaster.getQuestionPaperCode(), questionMaster.getQuestionPaperName(),
					questionMaster.getQuestionAnswerXml(),questionMaster.getInsertedBy(), questionMaster.getCourseid(),questionMaster.getTqmId(),questionMaster.getLanguage() };
			response = jtemp.update(sql, objArray);
			questionMaster.setTqmId(response);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

	@Override
	public int submitExamScheduleData(ExamSchedule examschedule) {
		logger.info("In DAO =" + examschedule);
		int response=0;
		JdbcTemplate jtemp = null;
		try {
			jtemp = new JdbcTemplate(dataSource);
			final String sql = "Insert into tab_exam_schedule (questionPaperCode,startDate,endDate,total_masks,pass_masks,Duration,inserted_on,question_id,inserted_by,exam_code) values (?,?,?,?,?,?,current_timestamp,?,?,?)";
			Object[] objArray = new Object[] { examschedule.getExamScheduleCode(), examschedule.getStartDate(),
					examschedule.getEndDate(), examschedule.getTotalMasks(), examschedule.getMasks(),
					examschedule.getDuration(),examschedule.getTesId(),examschedule.getInserted_by(),examschedule.getExamcode()};
			response = jtemp.update(sql, objArray);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

	@Override
	public int submitCourseEntryData(CourseEntry courseentry) {
		logger.info("In DAO =" + courseentry);
		int response=0;
		int id=(courseentry.getInsertedBy());
		JdbcTemplate jtemp = null;
		jtemp = new JdbcTemplate(dataSource);
		try {
			
			int	count=jtemp.queryForInt("select count(*) from tab_course_master where inserted_by="+id+" and course_name='"+courseentry.getCourseName()+"'");
			logger.info(count);
			if(count>0) {
				response=0;
			return response;
			
			}
			
			final String sql = "Insert into tab_course_master (course_name,course_des,inserted_by,inserted_on) values (?,?,?,current_timestamp)";
			Object[] objArray = new Object[] { courseentry.getCourseName(), courseentry.getDescription(),
					 id};
			response = jtemp.update(sql, objArray);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public List<CourseEntry> getCourseDetails(int insertedby) {
		List<CourseEntry> list = null;
		JdbcTemplate jtemp = null;
		try {
			final String queryString = "select * from tab_course_master where inserted_by="+insertedby+"";
			jtemp = new JdbcTemplate(dataSource);
			list = jtemp.query(queryString, new RowMapper<CourseEntry>() {
				public CourseEntry mapRow(ResultSet rs, int rowNum) throws SQLException {
					CourseEntry bean = new CourseEntry();
					bean.setTcmId(rs.getInt(1));
					bean.setCourseName(rs.getString(2));
					bean.setDescription(rs.getString(3));
					bean.setInsertedBy(rs.getInt(4));
					bean.setInsertedOn(rs.getTimestamp(5));
					return bean;
				}
			});
		} catch (Exception e) {
			logger.info("Exception in findByLang():" + e);
		}
		return list;

	}

	@Override
	public List<TabQuestionMaster> getTabQuestionMasters(int insertedby) {
		List<TabQuestionMaster> list = null;
		JdbcTemplate jtemp = null;
		try {
			final String queryString = "" + 
					"SELECT question_id, question_paper_code,questionPaperName,no_of_questions FROM tab_question_master where inserted_by="+insertedby+"";
			jtemp = new JdbcTemplate(dataSource);
			list = jtemp.query(queryString, new RowMapper<TabQuestionMaster>() {
				public TabQuestionMaster mapRow(ResultSet rs, int rowNum) throws SQLException {
					TabQuestionMaster bean = new TabQuestionMaster();
					bean.setTqmId(rs.getInt(1));
					bean.setQuestionPaperCode(rs.getString(2));
					bean.setQuestionPaperName(rs.getString(3));
					bean.setCourseid(rs.getInt(4));
					return bean;
				}
			});
		} catch (Exception e) {
			logger.info("Exception in getTabQuestionMasters():" + e);
		}
		return list;

	}

	@Override
	public ExamPaper submitExamPaperData(ExamPaper exampaper) {
		logger.info("In DAO =" + exampaper);

		return exampaper;
	}
	
	@Override
	public SearchExamCode submitSearchExamCodeData(SearchExamCode searchexamcode) {
		logger.info("In DAO =" + searchexamcode);

		return searchexamcode;
	}
	
	
	
	
	@Override
	public List<Question_details> getquestion_answer(int insertedby,String papercode) {
		List<Question_details> list = null;
		JdbcTemplate jtemp = null;
		try {
			final String queryString = "SELECT a1.question_id,a2.exam_code,a1.questionPaperName,a1.question_answer_xml,a1.inserted_by,a1.no_of_questions,a2.endDate,a2.total_masks,a2.pass_masks,a2.Duration " + 
					" FROM  tab_question_master a1, tab_exam_schedule a2 where  a1.question_paper_code=a2.questionPaperCode and a2.exam_code='"+papercode+"'  and a2.startDate<=CURDATE()";
			jtemp = new JdbcTemplate(dataSource);
			list = jtemp.query(queryString, new RowMapper<Question_details>() {
				public Question_details mapRow(ResultSet rs, int rowNum) throws SQLException {
					Question_details bean = new Question_details();
					bean.setQuestion_id(rs.getInt(1));
					bean.setQuestion_paper_code(rs.getString(2));
					bean.setQuestionPaperName(rs.getString(3));
					bean.setQuestion_answer_xml(rs.getString(4));
					bean.setInserted_by(rs.getInt(5));
					bean.setNoofquestion(rs.getInt(6));
					bean.setEnd_date(rs.getString(7));
					bean.setTotal_marks(rs.getInt(8));
					bean.setPass_marks(rs.getInt(9));
					bean.setDuration(rs.getInt(10));
					return bean;
				}
			});
		} catch (Exception e) {
			logger.info("Exception in getTabQuestionMasters():" + e);
		}
		return list;

	}

	@Override
	public int submitresult(Exam_Result exampaper) {
		logger.info("In DAO =" + exampaper);
		int response=0;
		JdbcTemplate jtemp = null;
		jtemp = new JdbcTemplate(dataSource);
		try {
		
			final String sql = "Insert into tab_student_result (examCode,student_id,obtained_marks,full_marks,pass_marks,remarks,completed_date,result_xml) values (?,?,?,?,?,?,current_timestamp,?)";
			Object[] objArray = new Object[] { exampaper.getPapercode(), exampaper.getStudentid(),
					exampaper.getObtainedmarks(), exampaper.getTotalmarks(),exampaper.getPassmarks(),
					exampaper.getRemarks(),exampaper.getResultXml()};
			response = jtemp.update(sql, objArray);
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public List<User_Recharge> getRechargeUserList(int id) {
		List<User_Recharge> list = null;
		JdbcTemplate jtemp = null;
		try {
			String condition="";
			if(id==0){
				condition=" ";
			}
			else{
				condition=" and register_by="+id+"";
			}
			final String queryString = "SELECT user_id,full_name,mobile_number from tab_user_details where user_type in(2,3,4) "+condition;
			jtemp = new JdbcTemplate(dataSource);
			list = jtemp.query(queryString, new RowMapper<User_Recharge>() {
				public User_Recharge mapRow(ResultSet rs, int rowNum) throws SQLException {
					User_Recharge bean = new User_Recharge();
					bean.setUserId(rs.getInt(1));
					bean.setUsername(rs.getString(2));
					bean.setMobile(rs.getString(3));
					return bean;
				}
			});
		} catch (Exception e) {
			logger.info("Exception in getRechargeUserList():" + e);
		}
		return list;
	}

	@Override
	public int sumbmitRechargeUserList(User_Recharge recharge) {
		
		logger.info("In DAO =" + recharge);
		int response=0;
		JdbcTemplate jtemp = null;
		jtemp = new JdbcTemplate(dataSource);
		try {
			
			String sql2="update tab_user_details  set blance_amount=blance_amount+"+recharge.getRechargeAmt()+" where user_id=?";
			final String sql = "Insert into tab_recharge_details (user_id,recharge_amount,recharged_by,recharged_date,rechargetype,rechargemode) values (?,?,?,current_timestamp,?,?)";
			Object[] objArray = new Object[] { recharge.getUserId(), recharge.getRechargeAmt(),
					recharge.getRechargedBy(),recharge.getRechargetype(),recharge.getRechargemode()};
			Object[] objArray1 = new Object[] { recharge.getUserId()};
			response = jtemp.update(sql, objArray);
			response = jtemp.update(sql2, objArray1);
			if(recharge.getUsertype()!=1){
				String sql3="update tab_user_details  set blance_amount=blance_amount-"+recharge.getRechargeAmt()+" where user_id=?";
				final String sql4 = "insert into  tab_amount_deduction_history  (deduct_amount,user_id,purpose,deduction_time,examCode,deduct_userId) values (?,?,?,current_timestamp,?,?)";;
				Object[] objArray3 = new Object[] { recharge.getRechargeAmt(), recharge.getUserId(),
						"Customer Recharge","",recharge.getRechargedBy()};
				Object[] objArray2 = new Object[] {recharge.getRechargedBy()};
				response = jtemp.update(sql4, objArray3);
				response = jtemp.update(sql3, objArray2);	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public List<Student_Results> getstudentResult(int studentid) {
		List<Student_Results> list = null;
		JdbcTemplate jtemp = null;
		try {
			final String queryString = "SELECT examCode,obtained_marks,full_marks,pass_marks,remarks,completed_date from tab_student_result where student_id="+studentid+" order by result_id desc";
			jtemp = new JdbcTemplate(dataSource);
			list = jtemp.query(queryString, new RowMapper<Student_Results>() {
				public Student_Results mapRow(ResultSet rs, int rowNum) throws SQLException {
					Student_Results bean = new Student_Results();
					bean.setPapercode(rs.getString(1));
					bean.setObtainedmarks(rs.getInt(2));
					bean.setTotalmarks(rs.getInt(3));
					bean.setPassmarks(rs.getInt(4));
					bean.setRemarks(rs.getString(5));
					bean.setCompleteddate(rs.getString(6));
					return bean;
				}
			});
		} catch (Exception e) {
			logger.info("Exception in getstudentResult():" + e);
		}
		return list;
	}

	@Override
	public int validateamount(String exampapercode) {
		int response=0;
		JdbcTemplate jtemp = null;
		jtemp = new JdbcTemplate(dataSource);
		try {
			response=jtemp.queryForInt("SELECT blance_amount FROM  tab_user_details  where user_id=(select inserted_by from tab_exam_schedule where exam_code='"+exampapercode+"')");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public int checkfinalsubmition(String exampapercode, int student_id) {
		
		int no_of_attempt=0;
		JdbcTemplate jtemp = null;
		jtemp = new JdbcTemplate(dataSource);
		try {
			
			no_of_attempt=jtemp.queryForInt("SELECT count(*) FROM  tab_student_result where examCode='"+exampapercode+"' and student_id="+student_id+"");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return no_of_attempt;
	}
	
	@Override
	public int get_no_of_attempt(String exampapercode, int student_id) {
		
		Integer no_of_attempt=0;
		JdbcTemplate jtemp = null;
		jtemp = new JdbcTemplate(dataSource);
		try {
			no_of_attempt=jtemp.queryForInt("SELECT count(*) FROM  tab_eaxm_attempt_history where exam_code='"+exampapercode+"' and student_id="+student_id+"");
			if(no_of_attempt==0){
				final String sql = "Insert into tab_eaxm_attempt_history (exam_code,student_id,no_of_attempt,attempt_time) values (?,?,1,current_timestamp)";
				Object[] objArray = new Object[] { exampapercode,
						student_id};
				no_of_attempt = jtemp.update(sql, objArray);	
			}
			else{
			no_of_attempt=jtemp.queryForInt("SELECT no_of_attempt FROM  tab_eaxm_attempt_history where exam_code='"+exampapercode+"' and student_id="+student_id+"");
			if(no_of_attempt<3){
			final String sql = "update  tab_eaxm_attempt_history set no_of_attempt=no_of_attempt+1,attempt_time=current_timestamp where exam_code=? and  student_id=?";
				Object[] objArray = new Object[] { exampapercode,
						student_id};
				no_of_attempt = jtemp.update(sql, objArray);
			}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return no_of_attempt;
	}



	@Override
	public int deduct_amount(int student_id, int amount, int teacher_id) {
		int respoonse=0;
		int teacherId=0;
		JdbcTemplate jtemp = null;
		jtemp = new JdbcTemplate(dataSource);
		try {
		if(teacher_id==0){
			teacherId=jtemp.queryForInt("SELECT register_by from tab_user_details  where user_id="+student_id+"");
		}
		else{
		teacherId=teacher_id;	
		}
		String sql2="update tab_user_details  set blance_amount=blance_amount-"+amount+" where user_id=?";
		Object[] objArray1 = new Object[] {teacherId};
		respoonse = jtemp.update(sql2, objArray1);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return respoonse;
	}
	
	
	@Override
	public int checkdeductionHistory(int student_id,String examCode) {
		int respoonse=0;
		
		JdbcTemplate jtemp = null;
		jtemp = new JdbcTemplate(dataSource);
		try {
		
			respoonse=jtemp.queryForInt("SELECT count(*) from tab_amount_deduction_history where user_id="+student_id+" and examCode='"+examCode+"'");
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return respoonse;
	}

	@Override
	public int insertdeductionhistory(int student_id,int amount,String purpose,String examcode) {
		int respoonse=0;
		
		JdbcTemplate jtemp = null;
		jtemp = new JdbcTemplate(dataSource);
		try {
		int	teacherId=jtemp.queryForInt("SELECT inserted_by  from tab_exam_schedule where  exam_code='"+examcode+"'");
			String sql2="insert into  tab_amount_deduction_history  (deduct_amount,user_id,purpose,deduction_time,examCode,deduct_userId) values (?,?,?,current_timestamp,?,?)";
			Object[] objArray1 = new Object[]{amount,student_id,purpose,examcode,teacherId};
			respoonse = jtemp.update(sql2, objArray1);
		
		
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return respoonse;
	}
	
	
	
	@Override
	public int getbalance(int id) {
		int balance=0;
		
		JdbcTemplate jtemp = null;
		jtemp = new JdbcTemplate(dataSource);
		try {
			if(id!=0){
			balance=jtemp.queryForInt("SELECT blance_amount  from tab_user_details where  user_id="+id+"");
			}
			} catch (Exception e) {
			e.printStackTrace();
		}
		return balance;
	}
	
	@Override
	public int gettotalcoursecount(int id) {
		int count=0;
		
		JdbcTemplate jtemp = null;
		jtemp = new JdbcTemplate(dataSource);
		try {
			if(id!=0){
				count=jtemp.queryForInt("SELECT count(*)  from tab_course_master where  inserted_by="+id+"");
			}
			} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	@Override
	public int gettotalquestioncount(int id) {
		int count=0;
		
		JdbcTemplate jtemp = null;
		jtemp = new JdbcTemplate(dataSource);
		try {
			if(id!=0){
				count=jtemp.queryForInt("SELECT count(*)  from tab_question_master where  inserted_by="+id+"");
			}
			} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	@Override
	public int gettoexamshedulecount(int id) {
		int count=0;
		
		JdbcTemplate jtemp = null;
		jtemp = new JdbcTemplate(dataSource);
		try {
			if(id!=0){
				count=jtemp.queryForInt("SELECT count(*)  from tab_exam_schedule where  inserted_by="+id+"");
			}
			} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	
	
	@Override
	public  List<Course_Histry> getcoursehistory(int id) {
		List<Course_Histry> list = null;
		JdbcTemplate jtemp = null;
		try {
			final String queryString = "SELECT course_id,course_name,course_des,inserted_by FROM  tab_course_master where inserted_by="+id+" order by course_id desc";
			jtemp = new JdbcTemplate(dataSource);
			list = jtemp.query(queryString, new RowMapper<Course_Histry>() {
				public Course_Histry mapRow(ResultSet rs, int rowNum) throws SQLException {
					Course_Histry bean = new Course_Histry();
					bean.setCourseid(rs.getInt(1));
					bean.setCoursename(rs.getString(2));
					bean.setCoursedesc(rs.getString(3));
					bean.setCreatedby(rs.getInt(4));
					return bean;
				}
			});
		} catch (Exception e) {
			logger.info("Exception in getcoursehistory():" + e);
		}
		return list;
	}
	@Override
	public int updateCourseEntryData(CourseEntry courseentry) {
		logger.info("In DAO =" + courseentry);
		int response=0;
		JdbcTemplate jtemp = null;
		jtemp = new JdbcTemplate(dataSource);
		try {
			
			final String sql = "update tab_course_master set course_name=?,course_des=?,inserted_on=current_timestamp where course_id=?";
			Object[] objArray = new Object[] { courseentry.getCourseName(), courseentry.getDescription(),
					courseentry.getCourseId()};
			response = jtemp.update(sql, objArray);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	
	@Override
	public  int deleteCourseEntryData(int courseId) {
		int response=0;
		JdbcTemplate jtemp = null;
		jtemp = new JdbcTemplate(dataSource);
		try {
			final String sql = "delete from  tab_course_master  where course_id=?";
			Object[] objArray = new Object[] {courseId};
			response = jtemp.update(sql, objArray);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	@Override
	public  List<GetQuestionList> getquestionhistory(int id) {
		List<GetQuestionList> list = null;
		JdbcTemplate jtemp = null;
		try {
			final String queryString = "SELECT question_id,question_paper_code,questionPaperName,inserted_by FROM  tab_question_master where inserted_by="+id+" order by question_id desc";
			jtemp = new JdbcTemplate(dataSource);
			list = jtemp.query(queryString, new RowMapper<GetQuestionList>() {
				public GetQuestionList mapRow(ResultSet rs, int rowNum) throws SQLException {
					GetQuestionList bean = new GetQuestionList();
					bean.setQuestionId(rs.getInt(1));
					bean.setPapercodeName(rs.getString(2));
					bean.setPaperName(rs.getString(3));
					bean.setInsertedBy(rs.getInt(4));
					return bean;
				}
			});
		} catch (Exception e) {
			logger.info("Exception in getquestionhistory():" + e);
		}
		return list;
	}
	
	@Override
	public  List<GetQuestionList> getquestion(int id) {
		List<GetQuestionList> list = null;
		JdbcTemplate jtemp = null;
		try {
			final String queryString = "SELECT question_paper_code,questionPaperName,question_answer_xml,question_language FROM  tab_question_master where question_id="+id+"";
			jtemp = new JdbcTemplate(dataSource);
			list = jtemp.query(queryString, new RowMapper<GetQuestionList>() {
				public GetQuestionList mapRow(ResultSet rs, int rowNum) throws SQLException {
					GetQuestionList bean = new GetQuestionList();
					bean.setPapercodeName(rs.getString(1));
					bean.setPaperName(rs.getString(2));
					bean.setQuestionxml(rs.getString(3));
					bean.setLanguage(rs.getString(4));
					return bean;
				}
			});
		} catch (Exception e) {
			logger.info("Exception in getquestion():" + e);
		}
		return list;
	}
	@Override
	public  int updateQuestion(int questionId,String xml,int length) {
		
		int response=0;
		JdbcTemplate jtemp = null;
		jtemp = new JdbcTemplate(dataSource);
		try {
			
			final String sql = "update tab_question_master set question_answer_xml=?,inserted_on=current_timestamp,no_of_questions=? where question_id=?";
			Object[] objArray = new Object[] {xml,length, questionId};
			response = jtemp.update(sql, objArray);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	@Override
	public  int deletequestion(int questionId) {
		int response=0;
		JdbcTemplate jtemp = null;
		jtemp = new JdbcTemplate(dataSource);
		try {
			final String sql = "delete from  tab_question_master  where question_id=?";
			Object[] objArray = new Object[] {questionId};
			response = jtemp.update(sql, objArray);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	
	@Override
	public  List<ExamSchedule> examSchedulehistory(int id) {
		List<ExamSchedule> list = null;
		JdbcTemplate jtemp = null;
		try {
			final String queryString = "SELECT schedule_id,startDate,endDate,total_masks,pass_masks,Duration,exam_code FROM tab_exam_schedule where inserted_by="+id+"";
			jtemp = new JdbcTemplate(dataSource);
			list = jtemp.query(queryString, new RowMapper<ExamSchedule>() {
				public ExamSchedule mapRow(ResultSet rs, int rowNum) throws SQLException {
					ExamSchedule bean = new ExamSchedule();
					bean.setTesId(rs.getInt(1));
					bean.setStartDate(rs.getString(2));
					bean.setEndDate(rs.getString(3));
					bean.setTotalMasks(rs.getString(4));
					bean.setMasks(rs.getString(5));
					bean.setDuration(rs.getString(6));
					bean.setExamcode(rs.getString(7));
					return bean;
				}
			});
		} catch (Exception e) {
			logger.info("Exception in examSchedulehistory():" + e);
		}
		return list;
	}
	@Override
	public  int deleteschedule(int questionId) {
		int response=0;
		JdbcTemplate jtemp = null;
		jtemp = new JdbcTemplate(dataSource);
		try {
			final String sql = "delete from  tab_exam_schedule  where schedule_id=?";
			Object[] objArray = new Object[] {questionId};
			response = jtemp.update(sql, objArray);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	@Override
	public List<GetResultHistory> getResultHistory(int id) {
		List<GetResultHistory> list = null;
		JdbcTemplate jtemp = null;
		try {
			final String queryString = "SELECT examCode,count(student_id),full_marks,pass_marks from tab_student_result where examCode in(SELECT exam_code FROM tab_exam_schedule where inserted_by="+id+") group by examCode";
			//logger.info(queryString);
			jtemp = new JdbcTemplate(dataSource);
			list = jtemp.query(queryString, new RowMapper<GetResultHistory>() {
				public GetResultHistory mapRow(ResultSet rs, int rowNum) throws SQLException {
					GetResultHistory bean = new GetResultHistory();
					bean.setExamcode(rs.getString(1));
					bean.setStudentcount(rs.getInt(2));
					bean.setTotalmarks(rs.getInt(3));
					bean.setPassmarks(rs.getInt(4));
					return bean;
				}
			});
		} catch (Exception e) {
			logger.info("Exception in getResultHistory():" + e);
		}
		return list;
	}
	
	

	@Override
	public List<ExamCodeResult> getExamcodeResul(String examCode) {
		List<ExamCodeResult> list = null;
		JdbcTemplate jtemp = null;
		try {
			final String queryString = "SELECT a1.examCode,a2.full_name,a2.mobile_number,a1.student_id,a1.obtained_marks,a1.full_marks,a1.pass_marks,a1.remarks,a1.completed_date "
					+ "  FROM tab_student_result a1,tab_user_details a2 where a2.user_id =a1.student_id and a1.examCode='"+examCode+"'";
			jtemp = new JdbcTemplate(dataSource);
			list = jtemp.query(queryString, new RowMapper<ExamCodeResult>() {
				public ExamCodeResult mapRow(ResultSet rs, int rowNum) throws SQLException {
					ExamCodeResult bean = new ExamCodeResult();
					bean.setExamCode(rs.getString(1));
					bean.setStudentName(rs.getString(2));
					bean.setMobileNumber(rs.getString(3));
					bean.setStudentId(rs.getString(4));
					bean.setObtainedmarks(rs.getString(5));
					bean.setFullmarks(rs.getString(6));
					bean.setPassmarks(rs.getString(7));
					bean.setRemarks(rs.getString(8));
					bean.setCompletedDate(rs.getString(9));
					return bean;
				}
			});
		} catch (Exception e) {
			logger.info("Exception in getExamcodeResul():" + e);
		}
		return list;
	}
	
	@Override
	public List<GetRechargeHistory> getRechargeHistory(int id) {
		List<GetRechargeHistory> list = null;
		JdbcTemplate jtemp = null;
		try {
			final String queryString = "SELECT recharged_date,rechargetype,recharge_amount FROM  tab_recharge_details where user_id="+id+"";
			//logger.info(queryString);
			jtemp = new JdbcTemplate(dataSource);
			list = jtemp.query(queryString, new RowMapper<GetRechargeHistory>() {
				public GetRechargeHistory mapRow(ResultSet rs, int rowNum) throws SQLException {
					GetRechargeHistory bean = new GetRechargeHistory();
					bean.setRechargedatetime(rs.getString(1));
					bean.setRecharhetype(rs.getString(2));
					bean.setAmount(rs.getString(3));
					return bean;
				}
			});
		} catch (Exception e) {
			logger.info("Exception in GetRechargeHistory():" + e);
		}
		return list;
	}
	
	@Override
	public int gettotalstudent(int id) {
		int count=0;
		
		JdbcTemplate jtemp = null;
		jtemp = new JdbcTemplate(dataSource);
		try {
			if(id==0){
				count=jtemp.queryForInt("SELECT count(*)  from tab_user_details where user_type=5");
			}
			else{
				count=jtemp.queryForInt("SELECT count(*)  from tab_user_details where user_type=5 and  register_by="+id+"");	
			}
			} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	@Override
	public List<User_Details> getStudentList(int id) {
		List<User_Details> list = null;
		JdbcTemplate jtemp = null;
		try {
			String queryString="";
			if(id==0){
				 queryString = "SELECT full_name,mobile_number FROM  tab_user_details where user_type=5";	
			}
			else{
			 queryString = "SELECT full_name,mobile_number FROM  tab_user_details where user_type=5 and register_by="+id+"";	
			}
			
			//logger.info(queryString);
			jtemp = new JdbcTemplate(dataSource);
			list = jtemp.query(queryString, new RowMapper<User_Details>() {
				public User_Details mapRow(ResultSet rs, int rowNum) throws SQLException {
					User_Details bean = new User_Details();
					bean.setFullname(rs.getString(1));
					bean.setMobno(rs.getString(2));
					return bean;
				}
			});
		} catch (Exception e) {
			logger.info("Exception in getStudentList():" + e);
		}
		return list;
	}
	@Override
	public int gettotalteacher(int id) {
		int count=0;
		
		JdbcTemplate jtemp = null;
		jtemp = new JdbcTemplate(dataSource);
		try {
			if(id==0){
				count=jtemp.queryForInt("SELECT count(*)  from tab_user_details where user_type=3 ");
			}
			else{
			count=jtemp.queryForInt("SELECT count(*)  from tab_user_details where user_type=3  and register_by="+id+"");	
			}
			} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	@Override
	public int gettotalparent(int id) {
		int count=0;
		
		JdbcTemplate jtemp = null;
		jtemp = new JdbcTemplate(dataSource);
		try {
			if(id==0){
				count=jtemp.queryForInt("SELECT count(*)  from tab_user_details where user_type=4 ");
			}
			else{
			count=jtemp.queryForInt("SELECT count(*)  from tab_user_details where user_type=4  and register_by="+id+"");	
			}
			} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	@Override
	public int gettotalMe(int id) {
		int count=0;
		
		JdbcTemplate jtemp = null;
		jtemp = new JdbcTemplate(dataSource);
		try {
			if(id==0){
				count=jtemp.queryForInt("SELECT count(*)  from tab_user_details where user_type=2");
			}
			else{
			count=jtemp.queryForInt("SELECT count(*)  from tab_user_details where user_type=2  and register_by="+id+"");	
			}
			} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}



	@Override
	public List<User_Details> getTeacherMeList(int id,int serrchtype) {
		List<User_Details> list = null;
		JdbcTemplate jtemp = null;
		try {
			 String queryString="";
			if(id==0){
				queryString = "SELECT full_name,mobile_number FROM  tab_user_details where user_type="+serrchtype+"";	
			}
			else{
				queryString = "SELECT full_name,mobile_number FROM  tab_user_details where user_type="+serrchtype+" and register_by="+id+"";	
			}
		
			//logger.info(queryString);
			jtemp = new JdbcTemplate(dataSource);
			list = jtemp.query(queryString, new RowMapper<User_Details>() {
				public User_Details mapRow(ResultSet rs, int rowNum) throws SQLException {
					User_Details bean = new User_Details();
					bean.setFullname(rs.getString(1));
					bean.setMobno(rs.getString(2));
					return bean;
				}
			});
		} catch (Exception e) {
			logger.info("Exception in getTeacherMeList():" + e);
		}
		return list;	
	}
	
	@Override
	public int getpaidRechargeCount(int id) {
		int count=0;
		
		JdbcTemplate jtemp = null;
		jtemp = new JdbcTemplate(dataSource);
		try {
			if(id==0){
				count=jtemp.queryForInt("SELECT count(*) FROM  tab_recharge_details where rechargetype='Paid'");
			}
			else{
				count=jtemp.queryForInt("SELECT count(*) FROM  tab_recharge_details where rechargetype='Paid' and recharged_by="+id+"");	
			}
			} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	
	@Override
	public int getCompRechargeCount(int id) {
		int count=0;
		
		JdbcTemplate jtemp = null;
		jtemp = new JdbcTemplate(dataSource);
		try {
			if(id==0){
				count=jtemp.queryForInt("SELECT count(*) FROM  tab_recharge_details where rechargetype='Complementary'");
			}
			else{
				count=jtemp.queryForInt("SELECT count(*) FROM  tab_recharge_details where rechargetype='Complementary' and recharged_by="+id+"");	
			}
			} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	@Override
	public List<User_Recharge> getCompAndPaidRechargeList(int id,String rechargeType) {
		List<User_Recharge> list = null;
		JdbcTemplate jtemp = null;
		try {
			 String queryString="";
			if(id==0){
				queryString = "SELECT a1.recharged_date,a2.full_name,a2.mobile_number,a1.recharge_amount FROM  tab_recharge_details a1,tab_user_details a2  where  a1.user_id=a2.user_id and  a1.rechargetype='"+rechargeType+"'";	
			}
			else{
				queryString = "SELECT a1.recharged_date,a2.full_name,a2.mobile_number,a1.recharge_amount FROM  tab_recharge_details a1,tab_user_details a2  where  a1.user_id=a2.user_id and  a1.rechargetype='"+rechargeType+"' and a1.recharged_by="+id+" order by a1.id desc";	
			}
			jtemp = new JdbcTemplate(dataSource);
			list = jtemp.query(queryString, new RowMapper<User_Recharge>() {
				public User_Recharge mapRow(ResultSet rs, int rowNum) throws SQLException {
					User_Recharge bean = new User_Recharge();
					bean.setRechargemode(rs.getString(1));
					bean.setUsername(rs.getString(2));
					bean.setMobile(rs.getString(3));
					bean.setRechargeAmt(rs.getInt(4));
					return bean;
				}
			});
		} catch (Exception e) {
			logger.info("Exception in getCompAndPaidRechargeList():" + e);
		}
		return list;	
	}
	
	@Override
	public int examcount(int id,int var) {
		int count=0;
		
		JdbcTemplate jtemp = null;
		jtemp = new JdbcTemplate(dataSource);
		try {
			if(var==0){
				count=jtemp.queryForInt("SELECT count(*) from tab_student_result where student_id="+id+" and remarks='Fail'");
			}
			else if(var==1){
				count=jtemp.queryForInt("SELECT count(*) from tab_student_result where student_id="+id+" and remarks='Pass'");	
			}
			else{
				count=jtemp.queryForInt("SELECT count(*)  from tab_student_result where student_id="+id+"");	
			}
			} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	@Override
	public List<User_Details> getProfile(int id) {
		List<User_Details> list = null;
		JdbcTemplate jtemp = null;
		try {
			String  queryString = "SELECT full_name,mobile_number,dob,password,email,gender,address,pin_code FROM   tab_user_details where user_id="+id+"";
			jtemp = new JdbcTemplate(dataSource);
			list = jtemp.query(queryString, new RowMapper<User_Details>() {
				public User_Details mapRow(ResultSet rs, int rowNum) throws SQLException {
					User_Details bean = new User_Details();
					bean.setFullname(rs.getString(1));
					bean.setMobno(rs.getString(2));
					bean.setDob(rs.getString(3));
					bean.setPassword(rs.getString(4));
					bean.setEmail(rs.getString(5));
					bean.setGender(rs.getString(6));
					bean.setAddress(rs.getString(7));
					bean.setPin(rs.getString(8));
					return bean;
				}
			});
		} catch (Exception e) {
			logger.info("Exception in getProfile():" + e);
		}
		return list;
	}
	
	
	@Override
	public int updateprofile(User_Details user) {
		logger.info("In DAO =" + user);
		int response=0;

		JdbcTemplate jtemp = null;
		try {
			jtemp = new JdbcTemplate(dataSource);
			final String sql = "update tab_user_details set full_name=?,mobile_number=?,dob=?,password=?,email=?,register_on=current_timestamp,gender=?,address=?,pin_code=?  where user_id=?";
			Object[] objArray = new Object[] {
					user.getFullname(),user.getMobno(),
					user.getDob(), user.getPassword(),
					user.getEmail(),user.getGender(),
					user.getAddress(),user.getPin(),user.getRegistered_by()
		};
			response = jtemp.update(sql, objArray);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public List<Deduct_list> userlist(int id) {
		List<Deduct_list> list = null;
		JdbcTemplate jtemp = null;
		try {
			String  queryString = "SELECT a2.full_name,a2.mobile_number,a1.deduct_amount,a1.deduction_time,a1.examCode FROM tab_amount_deduction_history a1, tab_user_details a2 where a1.user_id=a2.user_id and a1.deduct_userId="+id+"";
			jtemp = new JdbcTemplate(dataSource);
			list = jtemp.query(queryString, new RowMapper<Deduct_list>() {
				public Deduct_list mapRow(ResultSet rs, int rowNum) throws SQLException {
					Deduct_list bean = new Deduct_list();
					bean.setName(rs.getString(1));
					bean.setMobile(rs.getString(2));
					bean.setAmount(rs.getString(3));
					bean.setTime(rs.getString(4));
					bean.setExamcode(rs.getString(5));
					return bean;
				}
			});
		} catch (Exception e) {
			logger.info("Exception in userlist():" + e);
		}
		return list;
	}
	@Override
	public int validateMobile(String mobile) {
		int count=0;
		
		JdbcTemplate jtemp = null;
		jtemp = new JdbcTemplate(dataSource);
		try {
		count=jtemp.queryForInt("SELECT count(*)  from  tab_user_details where mobile_number='"+mobile+"'");	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}



	@Override
	public String getAnswerSheet(String examcode, int studenyId) {
String xml="";
		
		JdbcTemplate jtemp = null;
		jtemp = new JdbcTemplate(dataSource);
		try {
		String sql="SELECT result_xml FROM  tab_student_result where examCode='"+examcode+"' and student_id="+studenyId+"";
			xml=jtemp.queryForObject(sql,String.class );	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return xml;
	}
	
	@Override
	public int getuserId(String name,String dob,String mobile) {
		int count=0;
		
		JdbcTemplate jtemp = null;
		jtemp = new JdbcTemplate(dataSource);
		try {
		count=jtemp.queryForInt("SELECT user_id  FROM  tab_user_details where full_name='"+name+"'and mobile_number='"+mobile+"'and dob='"+dob+"'");	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	@Override
	public int changePassword(int userId,String Password) {
	
		int response=0;

		JdbcTemplate jtemp = null;
		try {
			jtemp = new JdbcTemplate(dataSource);
			final String sql = "update tab_user_details set password=?  where user_id=?";
			Object[] objArray = new Object[] {
					Password,
					userId};
			response = jtemp.update(sql, objArray);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	
}
