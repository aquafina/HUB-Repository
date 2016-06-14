package view.nishat.net.BackingBean;

import com.oracle.xmlns.pcbpel.adapter.db.top.readempsalary.HrPayHistory;

import com.oracle.xmlns.pcbpel.adapter.db.top.readempsalary.HrPayHistoryCollection;

import getemppayrollrec.PayHistory;

import hub.nishat.net.model.AM.HubModuleImpl;


import java.io.Console;

import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;

import java.util.HashMap;
import java.util.Iterator;

import java.util.List;

import java.util.Map;

import javax.el.ELContext;

import javax.el.ExpressionFactory;

import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseEvent;

import javax.faces.event.ValueChangeEvent;

import javax.naming.InitialContext;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import javax.xml.bind.JAXBElement;
import javax.xml.datatype.XMLGregorianCalendar;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCDataControl;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputNumberSpinbox;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanRadio;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;
import oracle.adf.view.rich.component.rich.layout.RichPanelBox;
import oracle.adf.view.rich.component.rich.nav.RichCommandButton;

import oracle.adf.view.rich.component.rich.output.RichOutputText;

import oracle.binding.BindingContainer;

import oracle.binding.OperationBinding;

import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowSet;
import oracle.jbo.RowSetIterator;
import oracle.jbo.domain.Date;
import oracle.jbo.server.PayloadDef;
import oracle.jbo.server.ViewObjectImpl;


import org.apache.myfaces.trinidad.event.AttributeChangeEvent;

import org.apache.myfaces.trinidad.event.DisclosureEvent;
import org.apache.myfaces.trinidad.model.RowKeySet;


import utils.system;

import view.nishat.net.CustomDataControl.MonthlyDeductionsDetails;
import view.nishat.net.Helper.CommonUtil;
import view.nishat.net.Helper.Constants;
import view.nishat.net.Helper.PolicyHelper;
import view.nishat.net.PoJo.EmptyAttendance;
import view.nishat.net.PoJo.MonthlyDeductedLeave;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

import oracle.jbo.domain.Number;

import view.nishat.net.CustomDataControl.LeaveReport;


public class Attendance {

    //BINDING COMPONENT'S REFERENCES OF ATTENDANCE.JSPX PAGE
    private RichSelectOneChoice leaveTypeLOV;
    private RichSelectBooleanCheckbox cbIsHalf;

    private RichInputText leaveTypeId;
    private RichInputDate leaveFromDate;
    private RichInputDate leaveToDate;
    private RichCommandButton logExceptionButton;


    private RichTable attTable;
    private RichInputText itPolicyException;
    private RichSelectOneChoice lovLeaveBalanceYear;

    //CLASS LEVEL VARIABLES
    private String dummy = "hello";
    private String leaveType;
    private static final int MAX_CASUAL_LEAVES = 15;
    private static final int MAX_ANNUAL_LEAVES = 15;
    private static final int MAX_SHORT_LEAVES = 10;
    private static final int MAX_LATES = 24;
    private static final int THRESHOLD_CASUAL_LEAVE_COUNT = 3;
    private static final int THRESHOLD_ANNUAL_LEAVE_COUNT = 3;
    private static final String ANNUAL_LEAVE = "ANNUAL";
    private static final String CASUAL_LEAVE = "CASUAL";
    private static final String TRAVEL_LEAVE = "TRAVEL_LEAVE";
    private static final String HALF_CASUAL_LEAVE = "HALF_CASUAL";
    private static final String HALF_ANNUAL_LEAVE = "HALF_ANNUAL";
    private static final String APPROVE_LEAVES = "approve_leave";
    private static final String INTIMATE_SUPERVISOR = "intimate";
    private RichSelectOneChoice monthLov;
    private RichSelectOneChoice yearLOV;
    private RichTable irgTable;
    private Map<String, Integer> leaves;
    private RichPanelBox attendancePanel;
    private HtmlInputText sal_month;
    private HtmlInputText sal_grossSal;
    private RichSelectOneChoice sal_monthLov;
    private RichSelectOneChoice sal_yearLov;
    private RichSelectOneChoice testLov;

    private String var_sal_month;
    private String var_sal_gross;
    private String var_sal_basic;
    private String var_sal_eobi;
    private String var_sal_hrent;
    private String var_sal_netsalary;
    private String var_sal_pfund;
    private String var_sal_util;
    private String var_sal_itax;
    private String var_sal_allowence;
    private String var_sal_total_deduction;
    private RichOutputText curr_month;
    private RichInputText postingMonth;
    private RichInputText curr_Year;
    private RichOutputText totalMissedTimeStr;
    private boolean postingFlag;
    private boolean irregFlag;
    private String postMonth;
    private RichSelectOneRadio startTimeAMPM;
    private RichSelectOneRadio endTimeAMPM;
    private RichInputNumberSpinbox travellingStartTimeHH;
    private RichInputNumberSpinbox travellingStartTimeMM;
    private RichInputNumberSpinbox travellingEndTimeHH;
    private RichInputNumberSpinbox travellingEndTimeMM;
    private boolean startDayLeaveAdded = false;
    private boolean endDayLeaveAdded = false;
    private String emailID = null;
    private String emailSubject = null;
    private String emailText = null;
    private float numberOfCasualLeavesAdded = 0;
  private RichSelectBooleanCheckbox isLFA;
  /*#######################################################*/
    /*#####################THE CONSTRUCTOR###################*/
    /*#######################################################*/
    String leaveIdForLFA = null;
  private RichSelectBooleanCheckbox isLCF;
  private RichInputText comments;


  public Attendance() {
        leaves = new HashMap<String, Integer>();
        leaves.put(CASUAL_LEAVE, 1);
        leaves.put(ANNUAL_LEAVE, 2);
        leaves.put(TRAVEL_LEAVE, 7);
//        postingFlag = checkPosting();
    }

    /*##################################################################
     *                           LEAVE TYPE CHANGED
     *                           ------------------
     *THIS METHOD GETS CALLED WHEN USER CHANGED THE LEAVE TYPE LOV
     *                                                                 */

    public void leaveTypeChanged(ValueChangeEvent valueChangeEvent) {
        
        CommonUtil.setvalueToExpression("#{bindings.LeaveType1.inputValue}",
                                        valueChangeEvent.getNewValue()); //Updating Model Values
        String selectedCode =
            CommonUtil.getValueFrmExpression("#{bindings.LeaveType1.attributeValue}").toString();
        leaveType = selectedCode;

        leaveFromDate.resetValue();
        leaveToDate.resetValue();

    }
    
    public void addTravellingTimeCompensatoryLeave(String userId,java.util.Date startDate,int startHH, int startMM, java.util.Date endDate,int endHH, int endMM) 
    {
        if (CommonUtil.isHoliday(startDate))
        {
          CommonUtil.log("its a holiday");
          
          if ((startHH == 18 && startMM > 0) || (startHH > 18 && startHH <= 20))
          {
            startDayLeaveAdded = true;
            //Add 0.5 to Casual leaves in leave balance table
            CommonUtil.addAvailableLeaveBalance(userId, 1, (double)0.5);
            numberOfCasualLeavesAdded += 0.5;
            CommonUtil.log("Start date half leave added");
          }
          else if ((startHH == 18 && startMM == 0) || startHH < 18)
          {
            //Add 1 to Casual leaves in leave balance table
            CommonUtil.addAvailableLeaveBalance(userId, 1, 1);
            numberOfCasualLeavesAdded += 1;
            CommonUtil.log("Start date full leave added");
          }
          if (startDate.equals(endDate))
            return;
        }
        if (CommonUtil.isHoliday(endDate))
        {
          CommonUtil.log("its a holiday");
          
          if (endHH >= 4 && endHH < 6)
          {
            endDayLeaveAdded = true;
            //Add 0.5 to Casual leaves in leave balance table
            CommonUtil.addAvailableLeaveBalance(userId, 1, (double)0.5);
            numberOfCasualLeavesAdded += 0.5;
            CommonUtil.log("End date half leave added");
          }
          else if (endHH>=6)
          {
            endDayLeaveAdded = true;
            //Add 1 to Casual leaves in leave balance table
            CommonUtil.addAvailableLeaveBalance(userId, 1, 1);
            numberOfCasualLeavesAdded += 1;
            CommonUtil.log("End date full leave added");
          }
      }
  }
    
    /*######################################################################
     *                         TASK APPLY FOR LEAVES
     *                         ----------------
     *THIS METHOD GETS CALLED WHEN USER APPLIES FOR LEAVES.EITHER CASUAL LEAVES
     * OR ANNUAL LEAVES.
     * ######################################################################
     *                                                                      */

    public String applyForLeave() {
        //GETTING THE USER ID. FOR TESTING PURPOSES I AM HARDCOADING THE VALUE
        //String userid = "3721";
        
        String userid =
            CommonUtil.getSessionValue(Constants.SESSION_USERID).toString();
      java.util.Date joiningDate = CommonUtil.getJoiningDate(userid);
        //GET THE LEAVE TYPE THAT USE  HAS SELECTED FROM THE LOV
        String type =
            CommonUtil.getValueFrmExpression("#{bindings.LeaveType.attributeValue}").toString();

        HubModuleImpl am = (HubModuleImpl)CommonUtil.getAppModule();
        ViewObjectImpl voAttendance = am.getVO_Attendance2();
        
        java.util.Date dStart = (java.util.Date)leaveFromDate.getValue();
        java.util.Date dEnd =
            (java.util.Date)leaveToDate.getValue();
        
        int startHH = travellingStartTimeHH.getValue()==null?-1:Integer.parseInt(travellingStartTimeHH.getValue().toString());
        int startMM = travellingStartTimeHH.getValue()==null?-1:Integer.parseInt(travellingStartTimeMM.getValue().toString());
        int endHH = travellingStartTimeHH.getValue()==null?-1:Integer.parseInt(travellingEndTimeHH.getValue().toString());
        int endMM = travellingStartTimeHH.getValue()==null?-1:Integer.parseInt(travellingEndTimeMM.getValue().toString());
        CommonUtil.log("start Time HH = "+travellingStartTimeHH.getValue());
        
        if (dEnd==null || dStart == null)
            CommonUtil.showMessage("Start Date and/or End Date missing", 112);
        else if (dEnd.before(dStart)) 
        {
          CommonUtil.showMessage("Start date should be before End date", 112);
        }
        else
        {
            ViewObjectImpl postingVO = am.getVO_AtdPosting1();
            CommonUtil.resetWhereClause(postingVO);
            Calendar cal = Calendar.getInstance();
            cal.setTime(dStart);
            String startMonth = CommonUtil.getMonthString(cal.get(Calendar.MONTH)+1);
            String where = "POSTING_MONTH = '"+startMonth+"' and POSTING_YEAR = "+cal.get(Calendar.YEAR)+" and USER_ID = "+userid+" and EMP_POSTED_FLAG = 'Y'";
            System.out.println("where Clause = "+where);
            postingVO.setWhereClause(where);
            postingVO.executeQuery();
            RowSetIterator rsi = postingVO.createRowSetIterator(null);
            if (rsi.getAllRowsInRange().length > 0)
                CommonUtil.showMessage("You cannot apply leaves for posted month", 112);
            else if (hasApplied(dStart, dEnd))
                CommonUtil.showMessage("Already applied Leaves", 112);
            else
            {
                //CommonUtil.showMessage("Not applied",112);
                List<java.util.Date> leaveDays =
                    CommonUtil.getDatesBetween(dStart, dEnd);
                
                List<java.util.Date> offDays =
                    CommonUtil.getOffDatesBetween(dStart, dEnd);
                
                for (java.util.Date offDate : offDays) 
                {
                    leaveDays.remove(offDate);
                }
                
                //IF THE LEAVE SELECTED BY THE USER IS CASUAL
                if (type.equals("CASUAL")) {
            //            //CommonUtil.log("Casual Leaves Applied for "+daysBetween);
            //            //CommonUtil.log("IS half flag: " + isHalf);
            //
                    if (leaveDays.size() > THRESHOLD_CASUAL_LEAVE_COUNT) {
                        CommonUtil.log("Attendance.java: Leaves Sent for approval");
                        int leavesRemaining = CommonUtil.getRemainingLeaves(1,userid);
                        if (leavesRemaining < THRESHOLD_CASUAL_LEAVE_COUNT) {
                            CommonUtil.showMessage("You do not have sufficient leaves in your accout to proceed", 112);
                        }
                        else{
                            //CommonUtil.showMessage("You have leave remaining in your account", 112);
                            processLeaves(dStart, dEnd, CommonUtil.getSessionValue(Constants.SESSION_USERID).toString(), CommonUtil.getSessionValue(Constants.SESSION_EMP_CODE).toString(), CASUAL_LEAVE, APPROVE_LEAVES);
                        }
                        //
                    } else {
                        //CommonUtil.log("Attendance.java: Deducting leaves");
                        int leavesRemaining = CommonUtil.getRemainingLeaves(1,userid);
                        if (leavesRemaining < 1) {
                            CommonUtil.showMessage("You do not have sufficient leaves in your accout to proceed", 112);
                        }
                        else{
            //                    CommonUtil.showMessage("You have leave remaining in your account", 112);
                            processLeaves(dStart, dEnd,CommonUtil.getSessionValue(Constants.SESSION_USERID).toString(),
                                          CommonUtil.getSessionValue(Constants.SESSION_EMP_CODE).toString(), CASUAL_LEAVE, INTIMATE_SUPERVISOR);
                        }
                        
                    }
        
                } else if (type.equals("ANNUAL")) {
                    //GET THE TWO DATES (DATE-FROM AND DATE-TO)
                  String isLfaStr = isLFA.isSelected()?"true":"false";
                  CommonUtil.log("is lfa = "+ isLfaStr);
                  CommonUtil.log("current date = "+ CommonUtil.getSessionValue(Constants.SESSION_CURRENT_DATE));
                  int leavesRemaining = CommonUtil.getRemainingLeaves(2, userid);
                  long numberOfLeaves = CommonUtil.getDifferenceDays(dStart, dEnd)+1;
                  long numberOfOffDays = CommonUtil.getOffDatesBetween(dStart, dEnd).size();
                  numberOfLeaves-=numberOfOffDays;
                  if (leavesRemaining < numberOfLeaves) 
                  {
                      CommonUtil.showMessage("You do not have sufficient leaves in your accout to proceed", 112);
                  }
                  else
                  {
                    if (isLFA.isSelected())
                    {
                      if (numberOfLeaves>=5)
                      {
                        try
                        {
                          leaveIdForLFA = hasAppliedforLFA(userid);
                          if (leaveIdForLFA==null)
                          {
                            processLeaves(dStart, dEnd, CommonUtil.getSessionValue(Constants.SESSION_USERID).toString(), 
                                          CommonUtil.getSessionValue(Constants.SESSION_EMP_CODE).toString(),
                                          ANNUAL_LEAVE, APPROVE_LEAVES);
                            leaveIdForLFA = CommonUtil.leaveIdForLFA;
                            applyForLFA(userid,leaveIdForLFA);
                          }
                          else CommonUtil.showMessage("You have already applied for LFA", 112);
                        }
                        catch (ParseException e)
                        {
                        }
                     }
                    else
                    {
                      CommonUtil.showMessage("You have to apply for 5 or more working days for LFA", 112);
                    }
                  }
                  else if (isLCF.isSelected())
                  {
                    CommonUtil.log("lcf is selected");
                    try
                    {
                      leaveIdForLFA = hasAppliedforLFA(userid);
                      if (leaveIdForLFA==null)
                      {
                        processLeaves(dStart, dEnd,CommonUtil.getSessionValue(Constants.SESSION_USERID).toString(), 
                                      CommonUtil.getSessionValue(Constants.SESSION_EMP_CODE).toString(), ANNUAL_LEAVE, APPROVE_LEAVES);
                        leaveIdForLFA = CommonUtil.leaveIdForLFA;
                        applyForLCF(userid,leaveIdForLFA);
                      }
                      else CommonUtil.showMessage("You have already applied for LFA", 112);
                    }
                    catch (ParseException e)
                    {
                    }
                  }
                    else
                    {
                      if (leaveDays.size() > THRESHOLD_ANNUAL_LEAVE_COUNT) {
                          //CommonUtil.showMessage("You have leave remaining in your account", 112);
                              if (CommonUtil.getSessionValue(Constants.SESSION_ACCESS_LEVEL).toString().equals("HOD")) {
                              CommonUtil.log("**************************************************************************");
                                  processLeaves(dStart, dEnd, CommonUtil.getSessionValue(Constants.SESSION_USERID).toString(), 
                                                CommonUtil.getSessionValue(Constants.SESSION_EMP_CODE).toString(), ANNUAL_LEAVE, INTIMATE_SUPERVISOR);   
                              }else{
                          processLeaves(dStart, dEnd, CommonUtil.getSessionValue(Constants.SESSION_USERID).toString(), 
                                        CommonUtil.getSessionValue(Constants.SESSION_EMP_CODE).toString(), ANNUAL_LEAVE, APPROVE_LEAVES);
                              }
                      }
                          else{
                          //CommonUtil.showMessage("You have leave remaining in your account", 112);
                              processLeaves(dStart, dEnd, CommonUtil.getSessionValue(Constants.SESSION_USERID).toString(), 
                                            CommonUtil.getSessionValue(Constants.SESSION_EMP_CODE).toString(), ANNUAL_LEAVE, INTIMATE_SUPERVISOR);
                          }
                    }
                  }
                }
                else if (type.equals("TRAVEL"))
                {
                  if (startHH==-1 || startMM==-1 || endHH==-1 || endMM==-1)
                    CommonUtil.showMessage("Please enter the Travelling Times", 112);
                  else
                  {
                    addTravellingTimeCompensatoryLeave(userid, dStart, startHH, startMM, dEnd, endHH, endMM);
                    processLeaves(dStart, dEnd,CommonUtil.getSessionValue(Constants.SESSION_USERID).toString(), 
                                  CommonUtil.getSessionValue(Constants.SESSION_EMP_CODE).toString(), TRAVEL_LEAVE, INTIMATE_SUPERVISOR);
                    generateEmailForTravellingLeaves(dStart, startHH+":"+startMM, dEnd, endHH+":"+endMM);
                    CommonUtil.showMessage("You have successfully applied for Travelling Leaves!", 111);
                  }
                }
            }
        }
        return null;
    }

  public void applyForLFA(String userId, String leaveIdForLFA)
  {
    HubModuleImpl am = (HubModuleImpl)CommonUtil.getAppModule();
    ViewObjectImpl lfaVO = am.getVO_LFA1();
    
    /**
     * Insert lfa
     */
    Row lfa = lfaVO.createRow();
    lfa.setAttribute("LfaId", am.getLfaSequence());
    lfa.setAttribute("LeaveId", leaveIdForLFA);
    lfa.setAttribute("UserId",userId);
    lfa.setAttribute("LineManagerApproved",null);
    lfa.setAttribute("HrApproved",null);
    lfaVO.insertRow(lfa);
    lfaVO.executeQuery();
    am.getTransaction().commit();
  }
  public void applyForLCF(String userId, String leaveIdForLFA)
  {
    HubModuleImpl am = (HubModuleImpl)CommonUtil.getAppModule();
    ViewObjectImpl lcfVO = am.getVO_LCF1();
    
    /**
     * Insert lfa
     */
    Row lcf = lcfVO.createRow();
    lcf.setAttribute("LcfId", am.getLfaSequence());
    lcf.setAttribute("LeaveId", leaveIdForLFA);
    lcf.setAttribute("UserId",userId);
    //lfa.setAttribute("LineManagerApproved",null);
    lcf.setAttribute("HrApproved",null);
    lcfVO.insertRow(lcf);
    lcfVO.executeQuery();
    am.getTransaction().commit();
  }
  public void generateEmailForTravellingLeaves(java.util.Date startDate, String startTime, java.util.Date endDate,String endTime)
  {
    String emailSubject = "Travelling Leaves Intimation";
    String emailFrom = "jarvis@nishat.net";
    String emailPwd = "N1sh@t.net";
    List<String> emailTo = new ArrayList<String>();
    emailTo.add("hrncg@nishat.net");
    CommonUtil.log("Start date = "+startDate);
    CommonUtil.log("End date = "+endDate);
    Calendar calStart = Calendar.getInstance();
    calStart.setTime(startDate);
    Calendar calEnd = Calendar.getInstance();
    calEnd.setTime(endDate);
    long numberOfLeaves = CommonUtil.getDifferenceDays(startDate, endDate)+1;
    String emailBody = "<html>\n" + 
    "<head>" + 
    "	<style>" + 
    "	tr" + 
    "	{ " + 
    "		background:#deebf6; " + 
    "		padding:8px; " + 
    "	} 	" + 
    "	#footer" + 
    "	{ " + 
    "		background:#ffffff;" + 
    "		color:#777879;" + 
    "	}" + 
    "	</style>" + 
    "</head>" + 
    "<body>" + 
    "	<div id=main_div>" + 
    "		<center>" + 
    "		<table>" + 
    "			<TR>" + 
    "				<TD>" + 
    "					Name:" + 
    "				</TD>" + 
    "				<TD>" + 
    "					<p>"+CommonUtil.getSessionValue(Constants.SESSION_EMP_NAME)+"</p>" + 
    "				</TD>" + 
    "			</TR>" + 
    "			<TR>" + 
    "				<TD>" + 
    "					Email:" + 
    "				</TD> " + 
    "				<TD>" + 
    "					<p>"+CommonUtil.getSessionValue(Constants.SESSION_USERNAME)+"</p>" + 
    "				</TD> " + 
    "			</TR>" + 
    "                   <TR>" + 
    "                           <TD>" + 
    "                                   Department:" + 
    "                           </TD> " + 
    "                           <TD>" + 
    "                                   <p>"+CommonUtil.getSessionValue(Constants.SESSION_EMP_DPT)+"</p>" + 
    "                           </TD> " + 
    "                   </TR>" + 
    "			<TR>" + 
    "				<TD>" + 
    "					Start Date:  " + 
    "				</TD>" + 
    "				<TD>" + 
    "					" + calStart.get(Calendar.DATE)+" "+(CommonUtil.getMonthString(calStart.get(Calendar.MONTH)+1))+" "+calStart.get(Calendar.YEAR)+
    "				</TD> " + 
    "			</TR> " + 
    "                   <TR>" + 
    "                           <TD>" + 
    "                                   Start Time:  " + 
    "                           </TD>" + 
    "                           <TD>" + 
    "                                   " + startTime +
    "                           </TD> " + 
    "                   </TR> " + 
    "			<TR> " + 
    "				<TD>" + 
    "					End Date: " + 
    "				</TD> " + 
    "				<TD>" + 
    "" +                            calEnd.get(Calendar.DATE)+" "+(CommonUtil.getMonthString(calEnd.get(Calendar.MONTH)+1))+" "+calEnd.get(Calendar.YEAR) +   
    "				</TD> " + 
    "			</TR>" + 
    "                   <TR>" + 
    "                           <TD>" + 
    "                                   End Time:  " + 
    "                           </TD>" + 
    "                           <TD>" + 
    "                                   " + endTime+
    "                           </TD> " + 
    "                   </TR> " + 
    "                   <TR>" + 
    "                           <TD>" + 
    "                                   Total Leaves: " + 
    "                           </TD>" + 
    "                           <TD>" + 
    "                                   " + numberOfLeaves+
    "                           </TD> " + 
    "                   </TR> " + 
    "                   <TR>" + 
    "                           <TD>" + 
    "                                   Casual Leaves Added: " + 
    "                           </TD>" + 
    "                           <TD>" + 
    "                                   " + numberOfCasualLeavesAdded+
    "                           </TD> " + 
    "                   </TR> " + 
    "			<TR>" + 
    "				<TD colspan=2 id=footer>" + 
    "					</br></br>This is a system generated email" + 
    "				</TD>" + 
    "			</TR>" + 
    "	</table>" + 
    "</center>" + 
    "</div>" + 
    "</body>" + 
    "</html>";
    numberOfCasualLeavesAdded = 0;
    CommonUtil.sendEmail(emailFrom, emailPwd, emailTo, emailSubject, emailBody);
//    emailTo = "hina@nishat.net";
//    CommonUtil.sendEmail(emailFrom, emailPwd, emailTo, emailSubject, emailBody);
//    emailTo = "hira@nishat.net";
//    CommonUtil.sendEmail(emailFrom, emailPwd, emailTo, emailSubject, emailBody);
  }
  
  public void generateEmailForLeavesLimitExceeded(Map<String,String> leaveLimitExceededMap, String userId)
  {
    String emailSubject = "Leaves Limit Exceeded Intimation";
    String emailFrom = "jarvis@nishat.net";
    String emailPwd = "N1sh@t.net";
    List<String> emailTo = new ArrayList<String>();
    emailTo.add("sarmad@nishat.net");
    emailTo.add("hrncg@nishat.net");
    emailTo.add(CommonUtil.getSessionValue(Constants.SESSION_HOD_EMAIL)==null?"sarmad@nishat.net":CommonUtil.getSessionValue(Constants.SESSION_HOD_EMAIL).toString());
    emailTo.add(CommonUtil.getSessionValue(Constants.SESSION_PARENT_EMAIL)==null?"sarmad@nishat.net":CommonUtil.getSessionValue(Constants.SESSION_PARENT_EMAIL).toString());
    Iterator it = leaveLimitExceededMap.entrySet().iterator();
    String emailBody = "<html>\n" + 
    "<head>" + 
    "	<style>" + 
    "	tr" + 
    "	{ " + 
    "		background:#deebf6; " + 
    "		padding:8px; " + 
    "	} 	" + 
    "	#footer" + 
    "	{ " + 
    "		background:#ffffff;" + 
    "		color:#777879;" + 
    "	}" + 
    "	</style>" + 
    "</head>" + 
    "<body>" + 
    "	<div id=main_div>" + 
    "		<center>" + 
    "		<table>" + 
    "			<TR>" + 
    "				<TD>" + 
    "					Name:" + 
    "				</TD>" + 
    "				<TD>" + 
    "					<p>"+CommonUtil.getSessionValue(Constants.SESSION_EMP_NAME)+"</p>" + 
    "				</TD>" + 
    "			</TR>" + 
    "			<TR>" + 
    "				<TD>" + 
    "					Email:" + 
    "				</TD> " + 
    "				<TD>" + 
    "					<p>"+CommonUtil.getSessionValue(Constants.SESSION_USERNAME)+"</p>" + 
    "				</TD> " + 
    "			</TR>" + 
                       
    "                   <TR>" + 
    "                           <TD>" + 
    "                                   Department:" + 
    "                           </TD> " + 
    "                           <TD>" + 
    "                                   <p>"+CommonUtil.getSessionValue(Constants.SESSION_EMP_DPT)+"</p>" + 
    "                           </TD> " + 
    "                   </TR>";
    while (it.hasNext()) 
    {
      Map.Entry pair = (Map.Entry)it.next();
      emailBody+=
      "                   <TR>" + 
      "                           <TD>" + 
                                    pair.getKey() + 
      "                           </TD> " + 
      "                           <TD>" + 
      "                                   <p>"+pair.getValue()+"</p>" + 
      "                           </TD> " + 
      "                   </TR>";
      CommonUtil.log("leave limit exceeded "+pair.getKey() + " = " + pair.getValue());
      it.remove(); // avoids a ConcurrentModificationException
    }
    emailBody+="			<TR>" + 
    "				<TD colspan=2 id=footer>" + 
    "					</br></br>This is a system generated email" + 
    "				</TD>" + 
    "			</TR>" + 
    "	</table>" + 
    "</center>" + 
    "</div>" + 
    "</body>" + 
    "</html>";
    CommonUtil.sendEmail(emailFrom, emailPwd, emailTo, emailSubject, emailBody);
  }
    /*############# UNUSED METTHOD ##############################################*/

    public void attributeChangeListener(AttributeChangeEvent attributeChangeEvent) {
    }


    public void leaveBalanceYearChanged(ValueChangeEvent valueChangeEvent) {
        CommonUtil.setvalueToExpression("#{bindings.Year.inputValue}",
                                        valueChangeEvent.getNewValue()); //Updating Model Values
        String selectedCode =
            CommonUtil.getValueFrmExpression("#{bindings.Year.attributeValue}").toString();
    }

    /**********************************************************
     *                   TASK POST ATTENDANCE                      *
     *                   ---------------                      *
     * BELOW METHOD GETS CALLED WHEN EMPLOYEE PRESSED THE POST*
     * BUTTON ON ATTENDANCE.JSPX.                             *
     * ********************************************************
     *                                                        *
     *                                                        */
    public String postAttendance() {
        /***********************************************************
         *      EMPLOYEE MONTHLY ATTENDANCE POSTING STARTS         *
         *      ------------------------------------------         *
         * HERE IN THIS METHOD WE ARE POSTING THE ATTENDANCE OF EMP*
         * LOYEE. THE POSTING INCLUDES THE FOLLWOING STEPS:        *
         * 1:-UPDATE THE LEAVE BALANCE                             *
         * 2:-GENERATE NEXT MONTH'S ATTENDANCE                     *
         * 3:-UPDATE THE POSTING FLAG IN THE DATABASE              *
         * BUT IF HE HASN'T LOGGED ONE OR MORE OF HIS DAYS THEN HE *
         * WILL NOT BE ALLOWED TO POST HIS ATTENDANCE.             *
         * *********************************************************
         *                                                         */


        HubModuleImpl am = (HubModuleImpl)CommonUtil.getAppModule();
        int shortLeaveCount = 0;
        int halfHourRelaxation = 0;
        int halfLeaveCount = 0;
        int casualLeaveCount = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        /** FIND THE DEFAULTED DAYS AND SHOW EMPLOYEE A MESSAGE IF HE TRIES
         * TO POST ATTENDANCE WITH THESE DEFAULTED DAYS*/
        ViewObjectImpl voDefaultedDays = am.getVO_Attendance2();
        voDefaultedDays.setWhereClause("((REGEXP_SUBSTR (effective_worked_hours,'[^:]+',1,1)* 60)+ (REGEXP_SUBSTR (effective_worked_hours,'[^:]+',1,2)) IS NULL OR (REGEXP_SUBSTR (effective_worked_hours,'[^:]+',1, 1)* 60)+ (REGEXP_SUBSTR (effective_worked_hours,'[^:]+',1,2)) < 390) and policy_exception_approved_flag is null and leave_approved_flag is null");
        voDefaultedDays.executeQuery();
        List<String> defaultedDays = new ArrayList<String>();
        RowSetIterator rsiDefaultedDays =
            voDefaultedDays.createRowSetIterator(null);
        Row[] defaultedRows = null;
        int pages = rsiDefaultedDays.getEstimatedRangePageCount();
        if (pages > 1) {
            defaultedRows = rsiDefaultedDays.getNextRangeSet();
        } else {
            defaultedRows = rsiDefaultedDays.getAllRowsInRange();
        }
        while (defaultedRows.length > 0) {
            for (int i = 0; i < defaultedRows.length; i++) {
                defaultedDays.add(defaultedRows[i].getAttribute("AttendanceDate").toString());
            }
            defaultedRows = rsiDefaultedDays.getNextRangeSet();
        }
        voDefaultedDays.setWhereClause(null);
        voDefaultedDays.executeQuery();

        /**CHECK FOR FAULTS IN THE ATTENDANCE*/
        if (defaultedDays.size() <= 0) {
            ViewObjectImpl voAttendance = am.getVO_Attendance2();
            voAttendance.executeQuery();
            RowSetIterator rsiAttendance =
                voAttendance.createRowSetIterator(null);

            Row[] rowsAttendance = rsiAttendance.getNextRangeSet();

            /*START OF THE LEAVE CALCULATION LOOP*/
            while (rowsAttendance.length > 0) {
                for (int i = 0; i < rowsAttendance.length; i++) {
                    /************************COUNT LEAVES ON THE BASIS OF EARLY OUT AND LATE IN.*******
                 *                       HERE GOES THE CONDITIONS:
                 *   0 > LATE IN AND EARLY OUT  <30  = RELAXATION
                 *   31 > LATE IN AND EARLY OUT <90  = SHORT LEAVE
                 *        LATE IN AND EARLY OUT >90  = HALF LEAVE**********************************/

                    int lateIn = 0;
                    int earlyOut = 0;
                    if (rowsAttendance[i].getAttribute("LateIn") != null) {
                        lateIn =
                                CommonUtil.convertTimeStringToMin(rowsAttendance[i].getAttribute("LateIn").toString());
                    }
                    if (rowsAttendance[i].getAttribute("EarlyOut") != null) {
                        earlyOut =
                                CommonUtil.convertTimeStringToMin(rowsAttendance[i].getAttribute("EarlyOut").toString());

                    }
                    Object leaveFlag =
                        rowsAttendance[i].getAttribute("LeaveApprovedFlag");
                    Object exceptionFlag =
                        rowsAttendance[i].getAttribute("PolicyExceptionApprovedFlag");
                    /**NOW I THINK ON A SINGLE DAY AN EMPLOYEE CAN UTILIZE EITHER
                 *LATE IN OR EARLY OUT. IF THAT'S THE CASE THEN I AM SUMMING UP
                 *THE TOTAL TIME OF LATE IN AND EARLY OUT AND COMPARE IT TO 30 MIN
                 */

                    int total = lateIn + earlyOut;
                    if (total > 0 && total <= 30) {
                        /**THESE ARE LATE INS. INCREMENT +1 */
                        halfHourRelaxation += 1;
                        System.out.println("incrementing latein earlyout: " +
                                           halfHourRelaxation);
                    } else if (total > 30 && total <= 90) {
                        shortLeaveCount += 1;
                        System.out.println("incrementing short leave: " +
                                           shortLeaveCount);
                    } else if (total > 90 && total <= 240) {
                        if (leaveFlag == null && exceptionFlag == null) {
                            halfLeaveCount += 1;
                            System.out.println("incrementing half leave: " +
                                               halfLeaveCount);
                        }
                    } else if (total > 240) {
                        if (leaveFlag == null && exceptionFlag == null) {
                            casualLeaveCount += 1;
                        }
                    }
                }
                rowsAttendance = rsiAttendance.getNextRangeSet();
            } //END OF THE LEAVE CALCULATION LOOP

            System.out.println("short leaves: " + shortLeaveCount +
                               " half leave: " + halfLeaveCount);
            /*************************HERE GOES THE LEAVE UPDATION TAKS.****************
         * HERE I AM GOING TO INCREASE/UPDATE SHORT LEAVES , HALF LEAVES AND LATE IN
         * AND EARLY OUTS.*/

            ViewObjectImpl voLeaveBalance = am.getVO_EmployeeLeaveBalance1();
            Row shortLeaveRow = null;
            Row relaxationTimeRow = null;
            Row halfLeaveRow = null;
            RowSetIterator rsiLeaves = null;

            /*IF PAGE COUNT IS 1 THEN USE rsiLeaves.getAllRowsInRange();*/
            //int pageCount = rsiLeaves.getEstimatedRangePageCount();

            /*UPDATE LATE INS*/
            voLeaveBalance.setWhereClause("LEAVE_TYPE_ID = 4 and MONTH = 'Apr'");
            voLeaveBalance.executeQuery();
            rsiLeaves = voLeaveBalance.createRowSetIterator(null);
            if (rsiLeaves.getAllRowsInRange().length > 0) {
                relaxationTimeRow = rsiLeaves.getAllRowsInRange()[0];

                if (relaxationTimeRow != null) {
                    relaxationTimeRow.setAttribute("AvailedLeaves",
                                                   Integer.parseInt(relaxationTimeRow.getAttribute("AvailedLeaves").toString()) +
                                                   halfHourRelaxation);
                    System.out.println("updating late in in if " +
                                       halfHourRelaxation);
                }
            } else {
                /*NO LATE INS ARE GIVEN TO EMPLOYEE. HE MAY BE IN HIS PROBATION OF GOD KNOWS
             * WHAT. . .*/
                Row newLeaveRow = voLeaveBalance.createRow();
                newLeaveRow.setAttribute("LeaveBalanceId",
                                         am.getLeaveSequence().intValue());
                newLeaveRow.setAttribute("UserId",
                                         "3562"); //HARD COADING FOR NOW OTHERWISE USE SESSION VALUE
                newLeaveRow.setAttribute("LeaveTypeId",
                                         4); //4 FOR LATE INS AND EARLY OUTS
                newLeaveRow.setAttribute("TotalLeaves", "0");
                newLeaveRow.setAttribute("AvailedLeaves", halfHourRelaxation);
                newLeaveRow.setAttribute("Month", "Apr");
                //CommonUtil.getCurrent(CommonUtil.CURR_MONTH_NAME));
                newLeaveRow.setAttribute("Year",
                                         Calendar.getInstance().get(Calendar.YEAR));
                voLeaveBalance.insertRow(newLeaveRow);
                voLeaveBalance.executeQuery();
                System.out.println("updating late in in else" +
                                   halfHourRelaxation);
            }


            voLeaveBalance.setWhereClause(null);
            voLeaveBalance.executeQuery();

            voLeaveBalance.setWhereClause("LEAVE_TYPE_ID = 3 and MONTH = 'Apr'");
            voLeaveBalance.executeQuery();
            System.out.println("All rows in range: " +
                               voLeaveBalance.getAllRowsInRange().length);

            rsiLeaves = voLeaveBalance.createRowSetIterator(null);

            if (rsiLeaves.getAllRowsInRange().length > 0) {
                shortLeaveRow = rsiLeaves.getAllRowsInRange()[0];
                if (shortLeaveRow != null) {
                    shortLeaveRow.setAttribute("AvailedLeaves",
                                               Integer.parseInt(shortLeaveRow.getAttribute("AvailedLeaves").toString()) +
                                               shortLeaveCount);
                    System.out.println("updating short leaves in if:" +
                                       shortLeaveCount);
                }
            } else {
                /*NO SHORT LEAVE IS GIVEN TO THE EMPLOYEE.
             * EITHER HE IS IN HIS PROBATION PERIOD OR GOD KNOWS WHAT
             * CREATE A ROW AND INCREASE AVAILED LEAVE*/
                Row newLeaveRow = voLeaveBalance.createRow();
                newLeaveRow.setAttribute("LeaveBalanceId",
                                         am.getLeaveSequence().intValue());
                newLeaveRow.setAttribute("UserId",
                                         "3562"); //HARD COADING FOR NOW OTHERWISE USE SESSION VALUE
                newLeaveRow.setAttribute("LeaveTypeId", 3); //3 FOR SHORT LEAVE
                newLeaveRow.setAttribute("TotalLeaves", "0");
                newLeaveRow.setAttribute("AvailedLeaves", shortLeaveCount);
                newLeaveRow.setAttribute("Month", "Apr");
                //CommonUtil.getCurrent(CommonUtil.CURR_MONTH_NAME));
                newLeaveRow.setAttribute("Year",
                                         Calendar.getInstance().get(Calendar.YEAR));
                voLeaveBalance.insertRow(newLeaveRow);
                voLeaveBalance.executeQuery();
                System.out.println("updating short leave in else" +
                                   shortLeaveCount);
            }
            voLeaveBalance.setWhereClause(null);
            voLeaveBalance.executeQuery();


            /*UPDATE HALF LEAVES*/
            voLeaveBalance.setWhereClause("LEAVE_TYPE_ID = 1 and MONTH = 'Apr'");
            voLeaveBalance.executeQuery();
            rsiLeaves = voLeaveBalance.createRowSetIterator(null);
            if (rsiLeaves.getAllRowsInRange().length > 0) {
                halfLeaveRow = rsiLeaves.getAllRowsInRange()[0];
                if (halfLeaveRow != null) {
                    halfLeaveRow.setAttribute("AvailedLeaves",
                                              Float.parseFloat(halfLeaveRow.getAttribute("AvailedLeaves").toString()) +
                                              halfLeaveCount / 2f +
                                              casualLeaveCount);
                    System.out.println("updating half leave in if" +
                                       halfLeaveCount);
                }
            } else {
                /*NO HALF LEAVE IS GIVEN TO USER. HE MAY BE IN PROBATION OR
             * GOD KNOWS WHAT. . .*/

                Row newLeaveRow = voLeaveBalance.createRow();
                newLeaveRow.setAttribute("LeaveBalanceId",
                                         am.getLeaveSequence().intValue());
                newLeaveRow.setAttribute("UserId",
                                         "3562"); //HARD COADING FOR NOW OTHERWISE USE SESSION VALUE
                newLeaveRow.setAttribute("LeaveTypeId", 1); //1 CASUAL LEAVES
                newLeaveRow.setAttribute("TotalLeaves", "0");
                newLeaveRow.setAttribute("AvailedLeaves",
                                         halfLeaveCount / 2f + casualLeaveCount);
                newLeaveRow.setAttribute("Month", "Apr");
                newLeaveRow.setAttribute("Year",
                                         Calendar.getInstance().get(Calendar.YEAR));
                voLeaveBalance.insertRow(newLeaveRow);
                voLeaveBalance.executeQuery();
                System.out.println("updating half leave in else" +
                                   halfLeaveCount);
            }
            voLeaveBalance.setWhereClause(null);
            voLeaveBalance.executeQuery();


            /****** UPDATION OF LEAVES DONE! ********/


            /**************HERE GOES THE GENERATION OF NEXT MONTH TASK*********
         *                                                              PISSED
         * SELECT ATTENDANCE OF 2 MONTHS FROM NOW.
         *                                                                */

            ViewObjectImpl voEmptyAtt = am.getVO_EmptyAttendance1();
            voEmptyAtt.setNamedWhereClauseParam("step_back_days",
                                                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
            voEmptyAtt.setNamedWhereClauseParam("from_month", "3");
            voEmptyAtt.setNamedWhereClauseParam("inlcude_previous_day", "0");
            voEmptyAtt.executeQuery();
            RowSetIterator rsiEmpty = voEmptyAtt.createRowSetIterator(null);
            Row[] rowsEmpty = rsiEmpty.getNextRangeSet();
            List<EmptyAttendance> emptyAttendance =
                new ArrayList<EmptyAttendance>();

            /*SAVE EMPTY ATTENDANCES IN AN ARRAYLIST*/
            while (rowsEmpty.length > 0) {
                for (int i = 0; i < rowsEmpty.length; i++) {
                    EmptyAttendance et = new EmptyAttendance();
                    try {
                        System.out.println(sdf.parse(rowsEmpty[i].getAttribute("AttendanceDate").toString()));
                        et.setAttendance_date(sdf.parse(rowsEmpty[i].getAttribute("AttendanceDate").toString()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    et.setEnd_time(rowsEmpty[i].getAttribute("EndTime").toString());
                    et.setExpected_work_hours(rowsEmpty[i].getAttribute("ExpectedWorkHours").toString());
                    et.setFull_name(rowsEmpty[i].getAttribute("FullName").toString());
                    et.setMax_end_time(rowsEmpty[i].getAttribute("MaxEndTime").toString());
                    et.setMax_start_time(rowsEmpty[i].getAttribute("MaxStartTime").toString());
                    et.setPerson_id(rowsEmpty[i].getAttribute("PersonId").toString());
                    et.setStart_time(rowsEmpty[i].getAttribute("StartTime").toString());
                    emptyAttendance.add(et);
                }
                rowsEmpty = rsiEmpty.getNextRangeSet();
            }

            /*NOW SAVE THE EMPTY ATTENDANCE LIST IN THE DATABASE ATTENDANCE TABLE*/
            for (EmptyAttendance et : emptyAttendance) {
                Row row = voAttendance.createRow();
                System.out.println(et.getAttendance_date().toString());
                row.setAttribute("EmpAtdId",
                                 am.getAttendanceSequence().intValue());
                row.setAttribute("AttendanceDate", et.getAttendance_date());
                row.setAttribute("EndTime", et.getEnd_time());
                row.setAttribute("ExpectedWorkHours",
                                 et.getExpected_work_hours());
                row.setAttribute("EmpName", et.getFull_name());
                row.setAttribute("MaxEndTime", et.getMax_end_time());
                row.setAttribute("MaxStartTime", et.getMax_start_time());
                row.setAttribute("EmpId", et.getPerson_id());
                row.setAttribute("StartTime", et.getStart_time());
                voAttendance.insertRow(row);
            }
            /**NEXT MONTHS EMPTY ATTENDANCE IS DONE***************
        /*

        /**************UPDATE THE POSTING FLAG IN THE DATABASE***************/
            ViewObjectImpl voPosting = am.getVO_AtdPosting1();
            Row rowpost = voPosting.createRow();
            rowpost.setAttribute("AtdPostId",
                                 am.getPostingSequence().intValue());
            rowpost.setAttribute("PostingMonth", "Apr");
            //CommonUtil.getCurrent(CommonUtil.CURR_MONTH_NAME).toString());
            rowpost.setAttribute("UserId",
                                 "3562"); //INSERT THE USER ID WHICH IS IN SESSION. FOR TESTING PURPOSES I AM HARD COADING THE VALUE
            rowpost.setAttribute("EmpPostedFlag", "Y");
            rowpost.setAttribute("HrPostedFlag", null);
            rowpost.setAttribute("PostingYear",
                                 Calendar.getInstance().get(Calendar.YEAR));
            voPosting.insertRow(rowpost);
            voPosting.executeQuery();

            /**COMMIT ALL THE CHANGES NOW**/
            am.getTransaction().commit();
            CommonUtil.refreshVO("VO_LeaveBalanceReport1Iterator");
            CommonUtil.refreshVO("VO_Attendance1Iterator");

        } else {
            StringBuilder dd = new StringBuilder();
            dd.append("<html></body><b><p>Following days need your attention:</p></b>");
            for (int i = 0; i < defaultedDays.size(); i++) {
                dd.append("<p color=red>" + defaultedDays.get(i) + " </p>");
            }
            dd.append("</body></html>");
            CommonUtil.showMessage(dd.toString(), 112);
        }

        /**********************************************************
         *    *     *     END OF EMPLOYEE POSTING            *    *
         *    *     *     ***********************            *    *
         *    *     *                                        *    *
         **********************************************************/

        return null;
    }


    /**#########################################################################################
     *############################## GETTERS AND SETTERS ######################################
     * #######################################################################################*/

    public void setLogExceptionButton(RichCommandButton logExceptionButton) {
        this.logExceptionButton = logExceptionButton;
    }

    public RichCommandButton getLogExceptionButton() {
        return logExceptionButton;
    }

    public void setDummy(String dummy) {
        this.dummy = dummy;
    }

    public String getDummy() {
        return dummy;
    }

    public void setAttTable(RichTable attTable) {
        this.attTable = attTable;
    }

    public RichTable getAttTable() {
        return attTable;
    }

    public void setItPolicyException(RichInputText itPolicyException) {
        this.itPolicyException = itPolicyException;
    }

    public RichInputText getItPolicyException() {
        return itPolicyException;
    }

    public void setLovLeaveBalanceYear(RichSelectOneChoice lovLeaveBalanceYear) {
        this.lovLeaveBalanceYear = lovLeaveBalanceYear;
    }

    public RichSelectOneChoice getLovLeaveBalanceYear() {
        return lovLeaveBalanceYear;
    }

    public void setLeaveTypeLOV(RichSelectOneChoice leaveTypeLOV) {
        this.leaveTypeLOV = leaveTypeLOV;
    }

    public RichSelectOneChoice getLeaveTypeLOV() {
        return leaveTypeLOV;
    }

    public void setCbIsHalf(RichSelectBooleanCheckbox cbIsHalf) {
        this.cbIsHalf = cbIsHalf;
    }

    public RichSelectBooleanCheckbox getCbIsHalf() {
        return cbIsHalf;
    }

    public void setLeaveTypeId(RichInputText leaveTypeId) {
        this.leaveTypeId = leaveTypeId;
    }

    public RichInputText getLeaveTypeId() {
        return leaveTypeId;
    }

    public void setleaveFromDate(RichInputDate leaveFromDate) {
        this.leaveFromDate = leaveFromDate;
    }

    public RichInputDate getleaveFromDate() {
        return leaveFromDate;
    }


    public void setleaveToDate(RichInputDate leaveToDate) {
        this.leaveToDate = leaveToDate;
    }

    public RichInputDate getleaveToDate() {
        return leaveToDate;
    }

    /***THIS METHOD WILL BE CALLED FROM APPLY FOR LEAVES METHOD. IF THE LEAVES THAT
     * USER APPLIED ARE 10 OR MORE THAN 10 THEN AN LFA REQUEST WILL AUTOMATICALLY BE
     * CREATED AND WILL BE SENT TO HOD FOR FURTHER PROCESSING */

    /**
     *
     * @param leaveType
     * @param leaveDays
     * @param whatToDo
     */
    public void processLeaves(java.util.Date dStart, java.util.Date dEnd,
                              String empId, String empCode,
                               String leaveType, String whatToDo) {
        List<java.util.Date> leaveDays =
            CommonUtil.getDatesBetween(dStart, dEnd);
      List<java.util.Date> offDays = null;

        if (leaveType.equals(TRAVEL_LEAVE))
        {
          System.out.println("Leave Type = "+leaveType);
          int result = updateAttendanceLeaveFlag(leaveDays,leaveType, empId);
          if (result==1)
          {
            CommonUtil.insertLeave(leaveType, leaveDays, dStart, dEnd,false, empId, 
                                   empCode);
          //add the size of offDates list into available leaves
          Calendar cal = Calendar.getInstance();
          cal.setTime(dStart);
          cal.add(cal.DAY_OF_MONTH, 1);
          dStart = cal.getTime();
          cal.setTime(dEnd);
          cal.add(cal.DAY_OF_MONTH, -1);
          dEnd = cal.getTime();
            offDays = CommonUtil.getOffDatesBetween(dStart, dEnd);
            System.out.println("Number of leaves allowed = "+offDays.size());
            float addNumberOfLeaves = offDays.size();
            if (!dStart.equals(dEnd))
            {
              numberOfCasualLeavesAdded += addNumberOfLeaves;
              System.out.println("number of added leaves = "+numberOfCasualLeavesAdded);
              CommonUtil.addAvailableLeaveBalance(empId, 1, addNumberOfLeaves);
            }
          }
        }
        else
        {
          offDays = 
                  CommonUtil.getOffDatesBetween(dStart, dEnd);        
          for (java.util.Date offDate : offDays) 
          {
              CommonUtil.log("Off day "+offDate);
  //            CommonUtil.log();
              leaveDays.remove(offDate);
          }
          if (!leaveDays.isEmpty())
          {
              CommonUtil.log("LeaveDays is not empty");
              if (whatToDo.equals(APPROVE_LEAVES)) {
                CommonUtil.insertLeave(leaveType, leaveDays, dStart, dEnd, true, CommonUtil.getSessionValue(Constants.SESSION_USERID).toString(), 
                                       CommonUtil.getSessionValue(Constants.SESSION_EMP_CODE).toString()); //pass true for approval
              } else if (whatToDo.equals(INTIMATE_SUPERVISOR)) {
                  CommonUtil.log("Intimate User for leave :  " + leaveDays);
                  int result = updateAttendanceLeaveFlag(leaveDays, leaveType, empId);
                  CommonUtil.log("Result of update attendance leave flag is: " +
                                 result);
                  if (result == 1) {
                    CommonUtil.insertLeave(leaveType, leaveDays, dStart, dEnd,false, CommonUtil.getSessionValue(Constants.SESSION_USERID).toString(), 
                                           CommonUtil.getSessionValue(Constants.SESSION_EMP_CODE).toString()); //pass false fo just intimatino
                  } else {
                      CommonUtil.doNothing();
                  }
              }
          }
          else CommonUtil.log("LeaveDays is empty");
        }
    }
    
    private boolean hasApplied(java.util.Date startDate, java.util.Date endDate) 
    {
        HubModuleImpl am = (HubModuleImpl)CommonUtil.getAppModule();
        ViewObjectImpl empLeavesVO = am.getVO_EmpLeaves1();
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        int year = cal.get(Calendar.YEAR)%100;
        int month = cal.get(Calendar.MONTH)+1;
        CommonUtil.resetWhereClause(empLeavesVO);
        java.sql.Date startDateSQL = CommonUtil.convertFromJAVADateToSQLDate(startDate);
        java.sql.Date endDateSQL = CommonUtil.convertFromJAVADateToSQLDate(endDate);
        String userid =
            CommonUtil.getSessionValue(Constants.SESSION_USERID).toString();
        String where = "user_id = '"+userid+"' and to_char(start_date,'mm') >= "+month+" and to_char(start_date,'yy') >= "+year+" and " +
            "((((to_date('"+startDateSQL+"','yyyy-mm-dd') between start_date and end_date) " +
            "or to_date('"+endDateSQL+"','yyyy-mm-dd') between start_date and end_date)" +
            "or start_date between to_date('"+startDateSQL+"','yyyy-mm-dd') and to_date('"+endDateSQL+"','yyyy-mm-dd'))" +
            "or end_date between to_date('"+startDateSQL+"','yyyy-mm-dd') and to_date('"+endDateSQL+"','yyyy-mm-dd'))";
        //CommonUtil.log(where);
        empLeavesVO.setWhereClause(where);
        empLeavesVO.executeQuery();
        RowSetIterator rsi = empLeavesVO.createRowSetIterator(null);
        if (rsi.getAllRowsInRange().length > 0) 
        {
          String approvedFlag = rsi.getAllRowsInRange()[0].getAttribute(2)==null?"":rsi.getAllRowsInRange()[0].getAttribute(2).toString();
            if (!approvedFlag.equals("N"))
            {
              CommonUtil.log(approvedFlag+"has already applied");
              return true;
            }
        } 
        return false;
    }
  
    public String hasAppliedforLFA(String userid)
    throws ParseException
  {
      java.sql.Date fiscalStartDate = CommonUtil.convertFromJAVADateToSQLDate(CommonUtil.getFiscalYearStartDate());
      java.sql.Date fiscalEndDate = CommonUtil.convertFromJAVADateToSQLDate(CommonUtil.getFiscalYearEndDate());
      HubModuleImpl am = (HubModuleImpl)CommonUtil.getAppModule();
      ViewObjectImpl empLeavesVO = am.getVO_EmpLeaves1();
      CommonUtil.resetWhereClause(empLeavesVO);
      String where = "user_id = "+userid+" and to_char(start_date,'yyyy-mm-dd') >= '"+fiscalStartDate+"' and to_char(end_date,'yyyy-mm-dd') <= '"+fiscalEndDate+"' and total_leaves >= 5";
      CommonUtil.log("where clause = "+where);
      empLeavesVO.setWhereClause(where);
      empLeavesVO.executeQuery();
      RowSetIterator rsi = empLeavesVO.createRowSetIterator(null);
      if (rsi.getAllRowsInRange().length>0)
      {
        leaveIdForLFA = rsi.getAllRowsInRange()[0].getAttribute(0).toString();
        CommonUtil.log("Leave id = "+leaveIdForLFA);
        ViewObjectImpl lfaVO = am.getVO_LFA1();
        CommonUtil.resetWhereClause(lfaVO);
        lfaVO.setWhereClause("leave_id = "+leaveIdForLFA);
        lfaVO.executeQuery();
        rsi = lfaVO.createRowSetIterator(null);
        if (rsi.getAllRowsInRange().length>0)
        {
          System.out.println("lfa returns value ");
          Row currRow = rsi.getAllRowsInRange()[0];
          String hrApproved = currRow.getAttribute("HrApproved")==null?"":currRow.getAttribute("HrApproved").toString();
          if (hrApproved.equals("Y"))
          {
            leaveIdForLFA = currRow.getAttribute("LeaveId").toString();
            return leaveIdForLFA;
          }
        }
      }
      return null;
    }
    
    private int updateAttendanceLeaveFlag(List<java.util.Date> leaveDays,
                                          String leaveType, String empId) {
        HubModuleImpl am = (HubModuleImpl)CommonUtil.getAppModule();
        ViewObjectImpl attendanceVO = am.getVO_UpdateAttendanceFlag1();
        
            StringBuilder sbLeaveDaysParam = new StringBuilder();
            for (java.util.Date date : leaveDays) {
                sbLeaveDaysParam.append("'" + CommonUtil.getMonDD(date) + "'" +
                                        ",");
            }
            //CommonUtil.log("sbLeaveDaysParam  = "+sbLeaveDaysParam);
            CommonUtil.resetWhereClause(attendanceVO);
            attendanceVO.executeQuery();
            String where =
                "emp_id = '"+ empId +"' and to_char(attendance_date,'MONDD')  in (" + sbLeaveDaysParam.toString().substring(0,
                                                                                                sbLeaveDaysParam.length() -
                                                                                                1) +")";
            CommonUtil.log("Select Rows to update query:" + where);
            attendanceVO.setWhereClause(where);
            CommonUtil.log(where);
            attendanceVO.executeQuery();
            RowSetIterator rsi = attendanceVO.createRowSetIterator(null);
            int pageCount = rsi.getEstimatedRangePageCount();
            CommonUtil.log("Page Count: " + pageCount);
            Row[] rows = null;
            if (pageCount > 1) {
                rows =
rsi.getNextRangeSet(); /**Test this statement by applying for just 1eave*/
            CommonUtil.log("more then 1 row are there to update");
            } else {
                rows = rsi.getAllRowsInRange();
                CommonUtil.log("just 1 row to uipdate: " + rows.length);
            }
            while (rows.length > 0) {
                for (int i = 0; i < rows.length; i++) {
                    String effectiveWorkedHours =
                        rows[i].getAttribute("EffectiveWorkedHours") == null ?
                        "0:00" :
                        rows[i].getAttribute("EffectiveWorkedHours").toString();
                    Object leave_today = rows[i].getAttribute("LeaveToday");
                    String expectedWorkHours =
                        rows[i].getAttribute("ExpectedWorkHours").toString();
                    int minutesMissing =
                        (Integer.parseInt(expectedWorkHours.split(":")[0]) *
                         60 +
                         Integer.parseInt(expectedWorkHours.split(":")[1])) -
                        Integer.parseInt(effectiveWorkedHours.split(":")[0]) *
                        60 +
                        Integer.parseInt(effectiveWorkedHours.split(":")[1]);

                    int missingMinutes = 0;

                    if (minutesMissing > 240 && leave_today == null) {
                        rows[i].setAttribute("LeaveToday", "Y");
                        CommonUtil.log(leaves.get(leaveType) + " - " +
                                       leaveType);
                        rows[i].setAttribute("TypeOfLeave",
                                             leaves.get(leaveType));
                        CommonUtil.log("update flag for row # " + i);
                    }
                    else if (leave_today != null)
                    {
                      CommonUtil.showMessage("Leave already marked on some or all of these day",112);
                      CommonUtil.resetWhereClause(attendanceVO);
                     return 0;
                    }
                    else {
                        CommonUtil.resetWhereClause(attendanceVO);
                        CommonUtil.showMessage("Wrong day to log a leave,you were present on this day.",
                                               111);
                        return 0;
                    }
                }
                rows = rsi.getNextRangeSet();
                //CommonUtil.log("got next range set of attendance");
            }
            attendanceVO.executeQuery();
            am.getTransaction().commit();
            CommonUtil.resetWhereClause(attendanceVO);
            return 1;
            }

    

    public void monthChangedListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        CommonUtil.setvalueToExpression("#{bindings.Month.inputValue}",
                                        valueChangeEvent.getNewValue()); //Updating Model Values
        String selectedMonth =
            CommonUtil.getValueFrmExpression("#{bindings.Month.attributeValue}").toString();
        String selectedYear =
            CommonUtil.getValueFrmExpression("#{bindings.Year.attributeValue}").toString();
        //CommonUtil.log(selectedMonth + " - " + selectedYear);
        CommonUtil.createUserSession("yearmonth",
                                     CommonUtil.getMonthNumber(selectedMonth) +
                                     "-" + selectedYear);
        CommonUtil.log("yearmonth = "+CommonUtil.getSessionValue("yearmonth").toString());
        //MonthlyDeductionsDetails dc =  (MonthlyDeductionsDetails)CommonUtil.getCustomDataControl("MonthlyDeductionDetails")
        //check if this month's attendance has been posted or not
        postingFlag = checkPosting();

        filterAttenedance(selectedMonth, selectedYear);
    }

    public void yearChangedListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        CommonUtil.setvalueToExpression("#{bindings.Year.inputValue}",
                                        valueChangeEvent.getNewValue()); //Updating Model Values
        String selectedYear =
            CommonUtil.getValueFrmExpression("#{bindings.Year.attributeValue}").toString();
        String selectedMonth =
            CommonUtil.getValueFrmExpression("#{bindings.Month.attributeValue}").toString();
        CommonUtil.log("yearmonth = "+selectedMonth+"-"+selectedYear);
        
        //check if this month and year attendance has been posted or not
        postingFlag = checkPosting();
        
        filterAttenedance(selectedMonth, selectedYear);
    }

    public void setMonthLov(RichSelectOneChoice monthLov) {
        this.monthLov = monthLov;
    }

    public RichSelectOneChoice getMonthLov() {
        return monthLov;
    }

    public void setYearLOV(RichSelectOneChoice yearLOV) {
        this.yearLOV = yearLOV;
    }

    public RichSelectOneChoice getYearLOV() {
        return yearLOV;
    }

    private void filterAttenedance(String selectedMonth, String selectedYear) {
        HubModuleImpl am = (HubModuleImpl)CommonUtil.getAppModule();
        ViewObjectImpl voAttendance = am.getVO_Attendance2();
        //CommonUtil.resetWhereClause(voAttendance);
        //CommonUtil.refreshVO("VO_Attendance2Iterator");
//        voAttendance.setNamedWhereClauseParam("bv_month", null);
//        voAttendance.setNamedWhereClauseParam("bv_year", null);
//        voAttendance.executeQuery();

        //        String whereClause =
        //            "to_char(attendance_date,'MONYYYY') = '" + selectedMonth.toUpperCase() +
        //            selectedYear.toUpperCase() + "'";
        //        CommonUtil.log(whereClause);
        //voAttendance.setWhereClause(whereClause);
        //voAttendance.reset();
        //CommonUtil.refreshVO("VO_Attendance2Iterator");

        voAttendance.setNamedWhereClauseParam("bv_month", selectedMonth);
        voAttendance.setNamedWhereClauseParam("bv_year", selectedYear);
        voAttendance.executeQuery();

        //        CommonUtil.refreshVO("VO_Attendance2Iterator");

    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public String saveException() {
        commitAction();
        HubModuleImpl am = (HubModuleImpl)CommonUtil.getAppModule();
        ViewObjectImpl irregularities = am.getVO_AttendanceEvents2();
        Row row = getCurrentExceptionTableRow();
        if (row != null) {
            java.util.Date attendanceDate =
                CommonUtil.convertJBODateToJavaDate((Date)row.getAttribute("AttendanceDate"));
            Object exception = row.getAttribute("ExceptionRemarks");

            String exceptionStr =
                exception == null ? null : exception.toString();
            if (exceptionStr != null) {
                CommonUtil.log(exceptionStr);
                generateExceptionRequest(row);
            }


        } else {
            CommonUtil.doNothing();
        }


        //irregularities.setWhereClause("");
        return null;
    }

    public String undoException() {
        HubModuleImpl am = (HubModuleImpl)CommonUtil.getAppModule();
        Row currRowIrregularity = getCurrentExceptionTableRow();
        if (currRowIrregularity != null) 
        {
            String irrID = currRowIrregularity.getAttribute("IrregularityId").toString();
            currRowIrregularity.setAttribute("ExceptionRemarks",null);
            currRowIrregularity.setAttribute("ExceptionApproved",null);
            System.out.println("irreg id = "+irrID);
            ViewObjectImpl exception = am.getVO_Exceptions1();
            CommonUtil.resetWhereClause(exception);
            exception.setWhereClause("IRREGULARITY_ID = '" + irrID + "'");  
            exception.executeQuery();
            RowSetIterator rsi = exception.createRowSetIterator(null);
            if (rsi.getAllRowsInRange().length>0)
            {
                CommonUtil.log("rows returned are not 0");
                Row currRowException = exception.createRowSetIterator(null).getAllRowsInRange()[0];
                if (currRowException != null)
                {
                    currRowException.setAttribute("ExceptionRemarks", null);
                    currRowException.setAttribute("Approved", null);
                    currRowException.remove();
                    //System.out.println("Exception Remarks = "+currRowException.getAttribute("ExceptionRemarks"));
                }
            }
            am.getTransaction().commit();
        }
        return null;
    }

    private String commitAction() {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding =
            bindings.getOperationBinding("Commit");
        Object result = operationBinding.execute();
        return null;
    }

    public Row getCurrentExceptionTableRow() {
        HubModuleImpl am = ((HubModuleImpl)CommonUtil.getAppModule());
        /**GET THE SELECTED ROW KEY SET OF THE TABLE FROM THE PAGE
         * AND THEN GET THE KEY THAT WE WILL USE TO FIND THE ROW
         * FROM THE VIEW OBJECT*/
        RowKeySet selectedEmps = getIrgTable().getSelectedRowKeys();
        Iterator selectedEmpIter = selectedEmps.iterator();
        Key key = (Key)((List)selectedEmpIter.next()).get(0);
  
        /**GET THE SELECTED VIEW OBJECT ROW USING THE KEY*/
        ViewObjectImpl vo = am.getVO_AttendanceEvents2();
        //vo.executeQuery();
        Row currentRow = vo.getRow(key);
        return currentRow;
    }


    public void setIrgTable(RichTable irgTable) {
        this.irgTable = irgTable;
    }

    public RichTable getIrgTable() {
        return irgTable;
    }

    private void generateExceptionRequest(Row row) {
        HubModuleImpl am = (HubModuleImpl)CommonUtil.getAppModule();
        ViewObjectImpl exception = am.getVO_ExceptionReq1();
        String irrID = row.getAttribute("IrregularityId").toString();
        CommonUtil.resetWhereClause(exception);
        exception.setWhereClause("IRREGULARITY_ID = '" + irrID + "'");
        exception.executeQuery();
        RowSetIterator rsi = exception.createRowSetIterator(null);
        Row exceptionRow = rsi.getAllRowsInRange().length > 0 ? rsi.getAllRowsInRange()[0] : null;
        if (exceptionRow == null)
        {
            Row r = exception.createRow();
            r.setAttribute("AttendanceDate", row.getAttribute("AttendanceDate"));
            r.setAttribute("ExceptionRequestDate", new java.util.Date());
            r.setAttribute("ExceptionRequestId", am.getExceptionReqSequence());
            r.setAttribute("IrregularityId", row.getAttribute("IrregularityId"));
            r.setAttribute("EmpAtdId", row.getAttribute("EmpAtdId"));
            r.setAttribute("EmpId", row.getAttribute("EmpId"));
            r.setAttribute("ExceptionCause", row.getAttribute("Type"));
            r.setAttribute("ExceptionRemarks",
                           row.getAttribute("ExceptionRemarks"));
            r.setAttribute("MissingMinutes", row.getAttribute("MinutesMissing"));
            if (CommonUtil.getSessionValue(Constants.SESSION_ACCESS_LEVEL).toString().equals("HOD")) {
                r.setAttribute("Approved", "Y");
            } else {
                r.setAttribute("Approved", "P");
            }
            exception.insertRow(r);
        }
        CommonUtil.resetWhereClause(exception);
        ViewObjectImpl voEvents = am.getVO_AttendanceEvents1();
        CommonUtil.resetWhereClause(voEvents);
        voEvents.setWhereClause("IRREGULARITY_ID = '" + irrID + "'");
        voEvents.executeQuery();

        Row irregularity =
            voEvents.createRowSetIterator(null).getAllRowsInRange()[0];
        if (CommonUtil.getSessionValue(Constants.SESSION_ACCESS_LEVEL).toString().equals("HOD")) {
            irregularity.setAttribute("ExceptionApproved", "Y");
        } else {
            irregularity.setAttribute("ExceptionApproved", "P");
        }


        am.getTransaction().commit();
    }

    /**TASK Monthly Posting
   * @return
   */
    public String post() {
     
        System.out.println("Selected yearmonth = "+ curr_month.getValue().toString()+"-"+curr_Year.getValue().toString());
        DateFormatSymbols d = new DateFormatSymbols();
        int monthNumber = Integer.parseInt(curr_month.getValue().toString());
        postMonth = d.getShortMonths()[monthNumber - 1];
        
        String monthDate =
            ((Integer.parseInt(curr_month.getValue().toString()) <= 9) ?
             "0" + Integer.parseInt(curr_month.getValue().toString()) :
             "" + Integer.parseInt(curr_month.getValue().toString())) + "-" +
            curr_Year.getValue().toString();


        HubModuleImpl am = (HubModuleImpl)CommonUtil.getAppModule();


        ViewObjectImpl voPosting = (ViewObjectImpl)am.getVO_AtdPosting1();

        CommonUtil.resetWhereClause(voPosting);

        voPosting.setWhereClause(" user_id = " +
                                 CommonUtil.getSessionValue(Constants.SESSION_USERID) +
                                 " and posting_year = "+Integer.parseInt(curr_Year.getValue().toString())+" and posting_month = '" +
                                 postMonth + "'");

        voPosting.executeQuery();

        int count = voPosting.getEstimatedRangePageCount();
        if (count > 0) {

            CommonUtil.showMessage("Already posted", 111);

            return null;
        }

        //GET THE VIEW OBJECTS REFERENCES
        ViewObjectImpl atdIRR = am.getVO_AttendanceIrregularityV1();
        ViewObjectImpl voConsumedLeaves = am.getVO_ConsumedLeaves1();
        ViewObjectImpl voAttendance = am.getVO_Attendance2();

        //RESET QUERIES
        CommonUtil.resetWhereClause(atdIRR);
        CommonUtil.resetWhereClause(voAttendance);

        //CHECK WEITHER EMPLOYEE HAS ANY PENDING REQUESTS FOR PREVIOUS MONTH
        atdIRR.setWhereClause("emp_id = '" +
                              CommonUtil.getSessionValue(Constants.SESSION_USERID).toString() +
                              "' and to_char(attendance_date,'YYYYMON') = '" +
                              curr_Year.getValue().toString() +
                              postMonth.toUpperCase() +
                              "' AND EXCEPTION_APPROVED = 'P'");
        atdIRR.executeQuery();
        RowSetIterator rsatdIRR = atdIRR.createRowSetIterator(null);
        Row irregularities[] =
            rsatdIRR.getEstimatedRangePageCount() == 1 ? rsatdIRR.getAllRowsInRange() :
            rsatdIRR.getNextRangeSet();
        int totalPending = irregularities.length;
        if (totalPending > 0) {
            CommonUtil.showMessage("Your have some unapproved excuses", 112);
        } else {

            CommonUtil.log("Deducting Leaves for the month: " + monthDate);
            PolicyHelper ph = new PolicyHelper();
            List<MonthlyDeductedLeave> leaves =
                ph.getMonthlyLeaves(monthDate, (HubModuleImpl)CommonUtil.getAppModule());
            CommonUtil.log("Total Leaves to be deducted for the month " +
                           monthDate + " are " + leaves.size());

            for (MonthlyDeductedLeave leave : leaves) {
                Row consumedLeave = voConsumedLeaves.createRow();
                consumedLeave.setAttribute("ConsumedLeavesId",
                                           leave.getLeaveID());
                consumedLeave.setAttribute("LeaveType", leave.getLeaveType());
                consumedLeave.setAttribute("Cause", leave.getLeaveCause());
                consumedLeave.setAttribute("UserId", leave.getUserID());
                consumedLeave.setAttribute("LeaveDate", leave.getLeaveDate());
                consumedLeave.setAttribute("JobStatus", leave.getJobStatus());
                voConsumedLeaves.insertRow(consumedLeave);

                CommonUtil.log("Leave Type:" + leave.getLeaveType() +
                               " Leave Date:" + leave.getLeaveCause());

                //                        /** Also log it in attendance table */
            Calendar c2 = Calendar.getInstance();
                c2.setTime(leave.getLeaveDate());
                int day = c2.get(Calendar.DAY_OF_MONTH);
                int month = c2.get(Calendar.MONTH) + 1;
                int year = c2.get(Calendar.YEAR);
                String param =
                    (day <= 9 ? ("0" + day) : day + "") + (month <= 9 ?
                                                           ("0" + month) :
                                                           month) + year + "";
                String where =
                    "to_char(attendance_date,'DDMMYYYY') = '" + param + "'";
                System.out.println("param = "+where);
               voAttendance.setWhereClause(where);
                voAttendance.executeQuery();
                Row row =
                    voAttendance.createRowSetIterator(null).getAllRowsInRange()[0];
              CommonUtil.log(row.getAttribute(0)+"");
                row.setAttribute("TypeOfLeave", leave.getLeaveType());
                row.setAttribute("LeaveToday", "Y");
            }
            //generateNextMonthsAttendance(); because i am doing it manually
            updatePostingFlag();

            CommonUtil.resetWhereClause(atdIRR);
            CommonUtil.resetWhereClause(voAttendance);

            am.getTransaction().commit();
            CommonUtil.resetWhereClause(voAttendance);
        
      CommonUtil.setLeaveReport();
      Map<String,String> leaveLimitExceededMap = CommonUtil.checkIfLeaveLimitExceeded();
      if (leaveLimitExceededMap.size()>0)
      {
        //generateEmailForLeavesLimitExceeded(leaveLimitExceededMap, CommonUtil.getSessionValue(Constants.SESSION_USERID).toString());
        CommonUtil.log("HOD email = "+CommonUtil.getSessionValue(Constants.SESSION_HOD_EMAIL).toString());
        CommonUtil.log("Parent email = "+CommonUtil.getSessionValue(Constants.SESSION_PARENT_EMAIL).toString());
      }
      CommonUtil.redirect("../faces/employee_attendance.jspx");
      }
      return null;
    }

    public String generateNextMonthsAttendance() {
        DateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
//        String postingMonthParam =
//            "05-" + getPostingMonth().getValue().toString() + "-2016";
        String postingDateParam =
            "01-" + "feb" + "-2016";
    java.util.Date postingDate;
    java.sql.Date postingDateSQL = null;
    try
    {
      postingDate = (java.util.Date)sdf.parse(postingDateParam);
      postingDateSQL = CommonUtil.convertFromJAVADateToSQLDate(postingDate);
    }
    catch (ParseException e)
    {
    }
    HubModuleImpl am = (HubModuleImpl)CommonUtil.getAppModule();
        ViewObjectImpl voAttendance = am.getVO_Attendance2();
        ViewObjectImpl voEmptyAtt = am.getVO_EmptyAttendance1();

        voEmptyAtt.setNamedWhereClauseParam("person_id", null);
        voEmptyAtt.setNamedWhereClauseParam("postingDate", null);
        voEmptyAtt.executeQuery();

        voEmptyAtt.setNamedWhereClauseParam("person_id",
                                            "5199");
        voEmptyAtt.setNamedWhereClauseParam("postingDate", postingDateParam);

        System.out.println(postingDateParam);
        
        voEmptyAtt.executeQuery();
        
        System.out.println(voEmptyAtt.getAllRowsInRange().length+" - "+voEmptyAtt.getNextRangeSet().length);
        
        
        String query = voEmptyAtt.getQuery().toString();
        CommonUtil.log("Query\n"+query);
        RowSetIterator rsiEmpty = voEmptyAtt.createRowSetIterator(null);
        
        System.out.println(rsiEmpty.getAllRowsInRange().length+" - "+rsiEmpty.getNextRangeSet().length);
        Row[] rowsEmpty = rsiEmpty.getNextRangeSet();
        
        
        List<EmptyAttendance> emptyAttendance =
            new ArrayList<EmptyAttendance>();
        CommonUtil.log("Empty Attendance\n");
       
        /*SAVE EMPTY ATTENDANCES IN AN ARRAYLIST*/
        while (rowsEmpty.length > 0) {
            for (int i = 0; i < rowsEmpty.length; i++) {
                EmptyAttendance et = new EmptyAttendance();
                try {
                    System.out.println(sdf.parse(rowsEmpty[i].getAttribute("AttendanceDate").toString()));
                    et.setAttendance_date(sdf.parse(rowsEmpty[i].getAttribute("AttendanceDate").toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                et.setEnd_time(rowsEmpty[i].getAttribute("EndTime").toString());
                et.setExpected_work_hours(rowsEmpty[i].getAttribute("ExpectedWorkHours").toString());
                et.setFull_name(rowsEmpty[i].getAttribute("FullName").toString());
                et.setMax_end_time(rowsEmpty[i].getAttribute("MaxEndTime").toString());
                et.setMax_start_time(rowsEmpty[i].getAttribute("MaxStartTime").toString());
                et.setPerson_id(rowsEmpty[i].getAttribute("PersonId").toString());
                et.setStart_time(rowsEmpty[i].getAttribute("StartTime").toString());
                emptyAttendance.add(et);
            }
            rowsEmpty = rsiEmpty.getNextRangeSet();
        }

      for (int i=0;i<emptyAttendance.size();i++)
      {
        CommonUtil.log(emptyAttendance.get(i).getAttendance_date()+"");
        CommonUtil.log("\n");
      }
        /*NOW SAVE THE EMPTY ATTENDANCE LIST IN THE DATABASE ATTENDANCE TABLE*/
        /*for (EmptyAttendance et : emptyAttendance) {
            Row row = voAttendance.createRow();
            System.out.println(et.getAttendance_date().toString());
            row.setAttribute("EmpAtdId",
                             am.getAttendanceSequence().intValue());
            row.setAttribute("AttendanceDate", et.getAttendance_date());
            row.setAttribute("EndTime", et.getEnd_time());
            row.setAttribute("ExpectedWorkHours", et.getExpected_work_hours());
            row.setAttribute("EmpName", et.getFull_name());
            row.setAttribute("MaxEndTime", et.getMax_end_time());
            row.setAttribute("MaxStartTime", et.getMax_start_time());
            row.setAttribute("EmpId", et.getPerson_id());
            row.setAttribute("StartTime", et.getStart_time());
            voAttendance.insertRow(row);
        }*/
        return null;
    }

    private void updatePostingFlag() {
        DateFormatSymbols d = new DateFormatSymbols();
        int monthNumber = Integer.parseInt(curr_month.getValue().toString());
        postMonth = d.getShortMonths()[monthNumber - 1];
        HubModuleImpl am = (HubModuleImpl)CommonUtil.getAppModule();
        ViewObjectImpl voPosting = am.getVO_AtdPosting1();
        String where =
            "user_id = '" + CommonUtil.getSessionValue(Constants.SESSION_USERID) +
            "' and posting_year = '" +
            curr_Year.getValue().toString() +
            "' and posting_month = '" + postMonth + "'";
        CommonUtil.log(where);
        voPosting.setWhereClause(where);
        voPosting.executeQuery();
        int pageCount = voPosting.getEstimatedRangePageCount();

        if (pageCount == 0) {
            Row postingRecord = voPosting.createRow();
            postingRecord.setAttribute("AtdPostId",
                                       am.getPostingSequence().intValue());
            postingRecord.setAttribute("UserId",
                                       CommonUtil.getSessionValue(Constants.SESSION_USERID).toString());
            postingRecord.setAttribute("EmpPostedFlag", "Y");
            postingRecord.setAttribute("HrPostedFlag", null);
            System.out.println("Posting Year = "+curr_Year.getValue().toString() );
            postingRecord.setAttribute("PostingYear",
                                       Integer.parseInt(curr_Year.getValue().toString()));
            postingRecord.setAttribute("PostingMonth", postMonth);
            postingRecord.setAttribute("PostingDate", new java.util.Date());
        } else {
            CommonUtil.showMessage("You already posted attendance of this month",
                                   112);
        }


    }

    public void setAttendancePanel(RichPanelBox attendancePanel) {
        this.attendancePanel = attendancePanel;
    }

    public RichPanelBox getAttendancePanel() {
        return attendancePanel;
    }

    public String cancelLeave() {
        Row row = getCurrentAttendanceTableRow();
        String date = row.getAttribute("AttendanceDate").toString();

        HubModuleImpl am = (HubModuleImpl)CommonUtil.getAppModule();
        ViewObjectImpl voAtt = am.getVO_Attendance2();
        CommonUtil.resetWhereClause(voAtt);
        String where =
            " to_char(attendance_date,'YYYY-mm-dd') = '" + date + "'";

        CommonUtil.log("Attndance.java->cancelLeave: " + where);
        voAtt.setWhereClause(where);
        voAtt.executeQuery();
        Row attRow = voAtt.createRowSetIterator(null).getAllRowsInRange()[0];
        attRow.setAttribute("LeaveCancelled", "Y");
        CommonUtil.resetWhereClause(voAtt);
        voAtt.executeQuery();
        am.getTransaction().commit();
        return null;
    }

    public Row getCurrentAttendanceTableRow() {
        HubModuleImpl am = ((HubModuleImpl)CommonUtil.getAppModule());
        /**GET THE SELECTED ROW KEY SET OF THE TABLE FROM THE PAGE
         * AND THEN GET THE KEY THAT WE WILL USE TO FIND THE ROW
         * FROM THE VIEW OBJECT*/
        RowKeySet selectedEmps = getAttTable().getSelectedRowKeys();

        Iterator selectedEmpIter = selectedEmps.iterator();
        Key key = (Key)((List)selectedEmpIter.next()).get(0);

        /**GET THE SELECTED VIEW OBJECT ROW USING THE KEY*/
        ViewObjectImpl vo = am.getVO_Attendance2();
        vo.executeQuery();
        Row currentRow = vo.getRow(key);
        return currentRow;
    }

    public void setSal_month(HtmlInputText sal_month) {
        this.sal_month = sal_month;
    }

    public HtmlInputText getSal_month() {
        return sal_month;
    }

    public void setSal_grossSal(HtmlInputText sal_grossSal) {
        this.sal_grossSal = sal_grossSal;
    }

    public HtmlInputText getSal_grossSal() {
        return sal_grossSal;
    }

    public String getSal() {
        // Add event code here...
        PayHistory ph = new PayHistory();
        List<HrPayHistory> list;
        String monthCode =
            (sal_yearLov.getValue().toString() + sal_monthLov.getValue().toString())==null?"":sal_yearLov.getValue().toString() + sal_monthLov.getValue().toString();
        String empCode =
            CommonUtil.getSessionValue(Constants.SESSION_EMP_CODE).toString();

        try {
            list = ph.getPayHistory(empCode, monthCode);
            CommonUtil.log("list.Xize()" + list.size() + " empCode: " +
                           empCode + " monthCode: " + monthCode);
            if (list.size() > 0) {

                HrPayHistory hrp = list.get(0);
                CommonUtil.log("has salary " + hrp.getCurGrossSalary());
                //                sal_grossSal.setValue(hrp.getCurGrossSalary());
                //                sal_month.setValue(new DateFormatSymbols().getShortMonths()[(Integer.parseInt(sal_monthLov.getValue().toString())-1)]);
                //setVar_sal_month("What");
                setVar_sal_gross(hrp.getCurGrossSalary().toString());
                setVar_sal_month(new DateFormatSymbols().getShortMonths()[(Integer.parseInt(sal_monthLov.getValue().toString()) -
                                                                           1)]);
                setVar_sal_basic(hrp.getCurGbasic().toString());
                setVar_sal_eobi(hrp.getCurEobi().toString());
                setVar_sal_hrent(hrp.getCurHrent().toString());
                setVar_sal_itax(hrp.getCurHrent().toString());
                setVar_sal_itax(hrp.getCurItax().toString());
                setVar_sal_netsalary(hrp.getCurNetSalary().toString());
                setVar_sal_pfund(hrp.getCurPfund().toString());
                setVar_sal_util(hrp.getCurUtil().toString());
                setVar_sal_allowence(hrp.getCurVAllowances().toString());
                setVar_sal_total_deduction(hrp.getCurVDeductions().toString());
            } else {
                CommonUtil.showMessage("Salary Not Available!", 111);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void salaryBoxListener(DisclosureEvent disclosureEvent) {
        // Add event code here...

        PayHistory ph = new PayHistory();
        List<HrPayHistory> list;

        try {

            int month = (Calendar.getInstance().get(Calendar.MONTH));
            String mC = month <= 9 ? "0" + month : "" + month;
            String monthCode = null;
            if (mC.equals("00")) 
            {
                mC = "12";
                monthCode = (Calendar.getInstance().get(Calendar.YEAR))-1 + mC;
            }
            else monthCode = Calendar.getInstance().get(Calendar.YEAR) + mC;
            CommonUtil.log("monthCode = "+monthCode);
            String empCode =
                CommonUtil.getSessionValue(Constants.SESSION_EMP_CODE).toString();
            list = ph.getPayHistory(empCode, monthCode);
            //list =ph.getPayHistory("001327","201512");

            CommonUtil.log("list.Xize()" + list.size() + " empCode: " +
                           empCode + " monthCode: " + monthCode);

            if (list.size() > 0) {
                HrPayHistory hrp = list.get(0);
                //                sal_grossSal.setValue(hrp.getCurGrossSalary());
                //                sal_month.setValue(new DateFormatSymbols().getShortMonths()[(Calendar.getInstance().get(Calendar.MONTH)-1)]);
                setVar_sal_gross(hrp.getCurGrossSalary().toString());
                month = Calendar.getInstance().get(Calendar.MONTH)==0?11:Calendar.getInstance().get(Calendar.MONTH)-1;
                System.out.println("month = "+month);
                setVar_sal_month(new DateFormatSymbols().getShortMonths()[month]);
                setVar_sal_basic(hrp.getCurGbasic().toString());
                setVar_sal_eobi(hrp.getCurEobi().toString());
                setVar_sal_hrent(hrp.getCurHrent().toString());
                setVar_sal_itax(hrp.getCurHrent().toString());
                setVar_sal_itax(hrp.getCurItax().toString());
                setVar_sal_netsalary(hrp.getCurNetSalary().toString());
                setVar_sal_pfund(hrp.getCurPfund().toString());
                setVar_sal_util(hrp.getCurUtil().toString());
                setVar_sal_allowence(hrp.getCurVAllowances().toString());
                setVar_sal_total_deduction(hrp.getCurVDeductions().toString());


            } else {
                CommonUtil.showMessage("Salary Not Available!", 111);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**It checks if the attendance is posted or not so that it hides irregularities**/
    public boolean checkIrregFlag() 
    {
        HubModuleImpl am = (HubModuleImpl)CommonUtil.getAppModule();
        ViewObjectImpl voPosting = (ViewObjectImpl)am.getVO_AtdPosting1();
        CommonUtil.resetWhereClause(voPosting);
        DateFormatSymbols d = new DateFormatSymbols();
        postMonth = d.getShortMonths()[Integer.parseInt(curr_month.getValue().toString())  - 1];
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MMM/dd HH:mm:ss");
        String cyear = dateFormat.format(Calendar.getInstance().getTime()).split("/")[0];
        String cmonth = dateFormat.format(Calendar.getInstance().getTime()).split("/")[1];
        CommonUtil.log("post month = "+postMonth);
        //If the user is trying to post the attendance for current month return false
        String whereClause = " user_id = " +
                                 CommonUtil.getSessionValue(Constants.SESSION_USERID) +
                                 " and posting_year = "+curr_Year.getValue().toString()+" and posting_month = '" +
                                 postMonth + "'";
        
        System.out.println("Check Posting query where clause "+whereClause);
        voPosting.setWhereClause(" user_id = " +
                                 CommonUtil.getSessionValue(Constants.SESSION_USERID) +
                                 " and posting_year = "+curr_Year.getValue().toString()+" and posting_month = '" +
                                 postMonth + "'");

            voPosting.executeQuery();
            int pageCount = voPosting.getEstimatedRangePageCount();
            if (pageCount == 0) 
            {
                return true;    
            }
            else return false;
    }
    
    /**It checks if the attendance of selected month and Year has been posted or not**/
    public boolean checkPosting() 
    {
        HubModuleImpl am = (HubModuleImpl)CommonUtil.getAppModule();
        ViewObjectImpl voPosting = (ViewObjectImpl)am.getVO_AtdPosting1();
        CommonUtil.resetWhereClause(voPosting);
        DateFormatSymbols d = new DateFormatSymbols();
        postMonth = d.getShortMonths()[Integer.parseInt(curr_month.getValue().toString())  - 1];
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MMM/dd HH:mm:ss");
        String cyear = dateFormat.format(Calendar.getInstance().getTime()).split("/")[0];
        String cmonth = dateFormat.format(Calendar.getInstance().getTime()).split("/")[1];
        CommonUtil.log("post month = "+postMonth);
        //If the user is trying to post the attendance for current month return false
        if (postMonth.equals(cmonth) && curr_Year.getValue().toString().equals(cyear+""))
            return true;
        else
        {
        String whereClause = " user_id = " +
                                 CommonUtil.getSessionValue(Constants.SESSION_USERID) +
                                 " and posting_year = "+curr_Year.getValue().toString()+" and posting_month = '" +
                                 postMonth + "'";
        
        System.out.println("Check Posting query where clause "+whereClause);
        voPosting.setWhereClause(" user_id = " +
                                 CommonUtil.getSessionValue(Constants.SESSION_USERID) +
                                 " and posting_year = "+curr_Year.getValue().toString()+" and posting_month = '" +
                                 postMonth + "'");

            voPosting.executeQuery();    
            
            int pageCount = voPosting.getEstimatedRangePageCount();
            if (pageCount == 0) 
            {
                return false;    
            }
            else return true;
        }
    }
    
    public void setSal_monthLov(RichSelectOneChoice sal_monthLov) {
        this.sal_monthLov = sal_monthLov;
    }

    public RichSelectOneChoice getSal_monthLov() {
        return sal_monthLov;
    }

    public void setSal_yearLov(RichSelectOneChoice sal_yearLov) {
        this.sal_yearLov = sal_yearLov;
    }

    public RichSelectOneChoice getSal_yearLov() {
        return sal_yearLov;
    }

    public void salMonthLovChanged(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        CommonUtil.setvalueToExpression("#{bindings.Month2.inputValue}",
                                        valueChangeEvent.getNewValue()); //Updating Model Values


    }

    public void salYearLovChanged(ValueChangeEvent valueChangeEvent) {
        // Add event fcode here...
        CommonUtil.setvalueToExpression("#{bindings.Year1.inputValue}",
                                        valueChangeEvent.getNewValue()); //Updating Model Values

    }

    public void setTestLov(RichSelectOneChoice testLov) {
        this.testLov = testLov;
    }

    public RichSelectOneChoice getTestLov() {
        return testLov;
    }

    public void setVar_sal_month(String var_sal_month) {
        this.var_sal_month = var_sal_month;
    }

    public String getVar_sal_month() {
        return var_sal_month;
    }

    public void setVar_sal_gross(String var_sal_gross) {
        this.var_sal_gross = var_sal_gross;
    }

    public String getVar_sal_gross() {
        return var_sal_gross;
    }

    public void setVar_sal_basic(String var_sal_basic) {
        this.var_sal_basic = var_sal_basic;
    }

    public String getVar_sal_basic() {
        return var_sal_basic;
    }

    public void setVar_sal_eobi(String var_sal_eobi) {
        this.var_sal_eobi = var_sal_eobi;
    }

    public String getVar_sal_eobi() {
        return var_sal_eobi;
    }

    public void setVar_sal_hrent(String var_sal_hrent) {
        this.var_sal_hrent = var_sal_hrent;
    }

    public String getVar_sal_hrent() {
        return var_sal_hrent;
    }

    public void setVar_sal_netsalary(String var_sal_netsalary) {
        this.var_sal_netsalary = var_sal_netsalary;
    }

    public String getVar_sal_netsalary() {
        return var_sal_netsalary;
    }

    public void setVar_sal_pfund(String var_sal_pfund) {
        this.var_sal_pfund = var_sal_pfund;
    }

    public String getVar_sal_pfund() {
        return var_sal_pfund;
    }

    public void setVar_sal_util(String var_sal_util) {
        this.var_sal_util = var_sal_util;
    }

    public String getVar_sal_util() {
        return var_sal_util;
    }

    public void setVar_sal_itax(String var_sal_itax) {
        this.var_sal_itax = var_sal_itax;
    }

    public String getVar_sal_itax() {
        return var_sal_itax;
    }

    public void setVar_sal_allowence(String var_sal_allowence) {
        this.var_sal_allowence = var_sal_allowence;
    }

    public String getVar_sal_allowence() {
        return var_sal_allowence;
    }

    public void setVar_sal_total_deduction(String var_sal_total_deduction) {
        this.var_sal_total_deduction = var_sal_total_deduction;
    }

    public String getVar_sal_total_deduction() {
        return var_sal_total_deduction;
    }

    public void atdEventTypeChanged(ValueChangeEvent valueChangeEvent) {
        CommonUtil.log("Shit is being called.. .");
        // Add event code here...
        CommonUtil.setvalueToExpression("#{bindings.LeaveType2.inputValue}",
                                        valueChangeEvent.getNewValue()); //Updating Model Values
        String selectedType =
            CommonUtil.getValueFrmExpression("#{bindings.LeaveType2.attributeValue}").toString();
        if (selectedType!=null)
        {

            DCBindingContainer dcBindings =
                (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
            // Get a the row iterator
            DCIteratorBinding iter =
                (DCIteratorBinding)dcBindings.get("VO_AttendanceEvents2Iterator");
            // Get all the rows of a iterator
            Row[] rows = iter.getAllRowsInRange();
            for (int i = 0; i < rows.length; i++) {
                rows[i].setAttribute("LeaveType", selectedType);
            }
            ((HubModuleImpl)CommonUtil.getAppModule()).getTransaction().commit();
        }
    }

    public String openMyHierarchy() {
        // Add event code here...
        ExternalContext ec =
            FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect("../faces/SupervisorView/my_hierarchy.jspx");
            //ec.redirect("http://mis-12:7101/TheHUB/faces/SupervisorView/my_hierarchy.jspx");

        } catch (IOException e) {
        }
        return null;

    }

    public String mail() {
        // Add event code here...
        ExternalContext ec =
            FacesContext.getCurrentInstance().getExternalContext();
        try {
            //ec.redirect("http://fmw.nishat.net:7003/TheHUB/faces/SupervisorView/my_hierarchy.jspx");
            ec.redirect("mailto:usmanriaz@nishat.net");


        } catch (IOException e) {
        }
        return null;
    }

    public void setCurr_month(RichOutputText curr_month) {
        this.curr_month = curr_month;
    }

    public RichOutputText getCurr_month() {
        return curr_month;
    }

    public void setPostingMonth(RichInputText postingMonth) {
        this.postingMonth = postingMonth;
    }

    public RichInputText getPostingMonth() {
        return postingMonth;
    }

    public void setCurr_Year(RichInputText curr_Year) {
        this.curr_Year = curr_Year;
    }

    public RichInputText getCurr_Year() {
        return curr_Year;
    }

    public void setTotalMissedTimeStr(RichOutputText totalMissedTimeStr) {
        this.totalMissedTimeStr = totalMissedTimeStr;
    }

    public RichOutputText getTotalMissedTimeStr() {
        return totalMissedTimeStr;
    }

    public void setPostingFlag(boolean postingFlag) {
        this.postingFlag = postingFlag;
    }

    public boolean isPostingFlag() {
        return checkPosting();
    }
    
    public void setIrregFlag(boolean irregFlag) {
        this.irregFlag = irregFlag;
    }

    public boolean isIrregFlag() {
        return checkIrregFlag();
    }
//    public boolean isValidTravelLeaveRequest(java.util.Date startDate,java.util.Date endDate) 
//    {
//        System.out.println("Start Date = "+startDate);
//        System.out.println("End Date = "+endDate);
//        List<java.util.Date> leaveDays =
//            CommonUtil.getDatesBetween(startDate, endDate);
//        HubModuleImpl appModule = (HubModuleImpl)CommonUtil.getAppModule();
//        ViewObjectImpl voCalendar = appModule.getVO_Calendar1();
//        
////        voCalendar.setWhereClause("where TransactionDate = "+);
//        voCalendar.executeQuery();
//        RowSetIterator rsi= voCalendar.createRowSetIterator(null); 
//        Row currRow = rsi.next();
//        while(currRow!=null)
//        {
//            String description = (String)currRow.getAttribute("Description");
//            System.out.println("Description = "+currRow.getAttribute("Description"));
//            if (description==null) 
//            {
//                java.util.Date currDate = CommonUtil.convertFromSQLDateToJAVADate((java.sql.Date)currRow.getAttribute("TransactionDate"));     
//                System.out.println("currDate = "+ currDate);
//                if (currDate.getDay()!=0) //SUNDAY
//                {
//                    return false;
//                }
//            }
//            else if (description.equals("SATURDAY_ON")) 
//            {
//                return false;
//            }
//            currRow = rsi.next();
//        }
//        voCalendar.setNamedWhereClauseParam("startDate",null);
//        voCalendar.setNamedWhereClauseParam("endDate",null);
//        voCalendar.executeQuery();
//        return true;
//    }


    
    public void setStartTimeAMPM(RichSelectOneRadio startTimeAMPM) {
        this.startTimeAMPM = startTimeAMPM;
    }

    public RichSelectOneRadio getStartTimeAMPM() {
        return startTimeAMPM;
    }

    public void setEndTimeAMPM(RichSelectOneRadio endTimeAMPM) {
        this.endTimeAMPM = endTimeAMPM;
    }

    public RichSelectOneRadio getEndTimeAMPM() {
        return endTimeAMPM;
    }

    public void startTimeAMPMChanged(AttributeChangeEvent attributeChangeEvent) {
        // Add event code here...
    }
    
    public void endTimeAMPMChanged(AttributeChangeEvent attributeChangeEvent) {
        // Add event code here...
        System.out.println("Start time ampm ="+startTimeAMPM.getValue());
    }

    public void setTravellingStartTimeHH(RichInputNumberSpinbox travellingStartTimeHH) {
        this.travellingStartTimeHH = travellingStartTimeHH;
    }

    public RichInputNumberSpinbox getTravellingStartTimeHH() {
        return travellingStartTimeHH;
    }

    public void setTravellingStartTimeMM(RichInputNumberSpinbox travellingStartTimeMM) {
        this.travellingStartTimeMM = travellingStartTimeMM;
    }

    public RichInputNumberSpinbox getTravellingStartTimeMM() {
        return travellingStartTimeMM;
    }

    public void setTravellingEndTimeHH(RichInputNumberSpinbox travellingEndTimeHH) {
        this.travellingEndTimeHH = travellingEndTimeHH;
    }

    public RichInputNumberSpinbox getTravellingEndTimeHH() {
        return travellingEndTimeHH;
    }

    public void setTravellingEndTimeMM(RichInputNumberSpinbox travellingEndTimeMM) {
        this.travellingEndTimeMM = travellingEndTimeMM;
    }

    public RichInputNumberSpinbox getTravellingEndTimeMM() {
        return travellingEndTimeMM;
    }

  public void startDateValueChangedListener(ValueChangeEvent valueChangeEvent)
  {
    // Add event code here...
    System.out.println("start date changed");
    
//    travellingStartTimeHH.setValue(0);
//    travellingStartTimeMM.setValue(0);
  }

  public void endDateValueChangedListener(ValueChangeEvent valueChangeEvent)
  {
    // Add event code here...
    System.out.println("start date changed");
//    travellingEndTimeHH.setValue(0);
//    travellingEndTimeMM.setValue(0);
  }

  public void setIsLFA(RichSelectBooleanCheckbox isLFA)
  {
    this.isLFA = isLFA;
  }

  public RichSelectBooleanCheckbox getIsLFA()
  {
    return isLFA;
  }

  public void setIsLCF(RichSelectBooleanCheckbox isLCF)
  {
    this.isLCF = isLCF;
  }

  public RichSelectBooleanCheckbox getIsLCF()
  {
    return isLCF;
  }

  public void saveComments(ActionEvent actionEvent)
  {
    String comments = getComments()==null?null:getComments().getValue()==null?null:getComments().getValue().toString();
    Row currRow = CommonUtil.getSelectedRow("VO_Attendance2Iterator");
    currRow.setAttribute("Comments", comments); 
    HubModuleImpl am = (HubModuleImpl)CommonUtil.getAppModule();
    am.getTransaction().commit();
  }

  public void setComments(RichInputText comments)
  {
    this.comments = comments;
  }

  public RichInputText getComments()
  {
    return comments;
  }
}
