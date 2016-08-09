package view.nishat.net.Helper;

import hub.nishat.net.model.AM.HubModuleImpl;

import java.io.IOException;

import java.math.BigDecimal;
import java.math.BigInteger;

import java.math.RoundingMode;

import java.security.SecureRandom;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import java.util.HashMap;
import java.util.List;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCDataControl;
import oracle.adf.model.binding.DCIteratorBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Number;
import oracle.jbo.server.ViewObjectImpl;

public class CommonUtil {

    public static final String CURR_MONTH_NAME = "month_name";
    public static final String CURR_MONTH_DAYS = "month_days";
    public static final String PREVIOUS_MONTH_NAME = "p_month_name";
    public static boolean IS_RAMADAN = true;
    
    private static String connectionString =
        "jdbc:oracle:thin:@192.168.0.2:1521:oranctex";
    private static String user = "cyber_ncl";
    private static String password = "panther";
    
    private static String connectionString_EBS =
        "jdbc:oracle:thin:@192.168.0.31:1522:prod";
    private static String user_EBS = "xx_e_portal";
    private static String password_EBS = "mskiz145";
  private static final String ANNUAL_LEAVE = "ANNUAL";
  private static final String CASUAL_LEAVE = "CASUAL";
  private static final String TRAVEL_LEAVE = "TRAVEL_LEAVE";
  private static final String SHORT_LEAVE = "SHORT_LEAVE";
  private static final String LATE_LEAVE = "LATE_LEAVE";
  private static Map<String, Integer> leaves;
    public static String leaveIdForLFA = null;
  public static long ramadanExpectedWorkTime = 390;
  public static long normalExpectedWorkTime = 480;
    public static String limitSubtractTime(String time1, String time2,
                                           String totalHours) {
        String difference = "";
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");
            java.util.Date d1 = formatter.parse(time1);
            java.util.Date d2 = formatter.parse(time2);
            long timeDiff = d2.getTime() - d1.getTime();
            long diffMinutes = timeDiff / (60 * 1000) % 60;
            long diffHours = timeDiff / (60 * 60 * 1000) % 24;
            long totalMin = (diffHours * 60) + diffMinutes;


            if (totalMin <= 0) {
                diffHours = 0;
                diffMinutes = 0;
                difference =
                        "0" + "#" + (diffMinutes <= 9 ? ("0" + diffMinutes) :
                                     (diffMinutes + ""));
            }
          if (!IS_RAMADAN)
          {
            if (totalMin >= normalExpectedWorkTime) { //480 for 8 hours
                diffHours = 8;
                diffMinutes = 00;
                difference =
                        diffHours + "#" + (diffMinutes <= 9 ? ("0" + diffMinutes) :
                                            (diffMinutes + ""));
            } else {
                difference =
                        diffHours + "#" + (diffMinutes <= 9 ? ("0" + diffMinutes) :
                                           (diffMinutes + ""));
            }
          }
          else
          {
            if (totalMin >= ramadanExpectedWorkTime) { //390 for 6 hours 30 mins
                diffHours = 6;
                diffMinutes = 30;
                difference =
                        diffHours + "#" + (diffMinutes <= 9 ? ("0" + diffMinutes) :
                                            (diffMinutes + ""));
            } else {
                difference =
                        diffHours + "#" + (diffMinutes <= 9 ? ("0" + diffMinutes) :
                                           (diffMinutes + ""));
            }
          }

        } catch (Exception e) {
            difference = "";
            e.printStackTrace();
        }
        return difference;
    }

    public static void log(String chars) {
        System.out.println("###>>>>    " + chars + "    <<<<###");
    }

    public static Date convertJBODateToJavaDate(oracle.jbo.domain.Date jboDate) {
        Calendar c = Calendar.getInstance();
        java.sql.Date sd = jboDate.dateValue();
        Date jd = new Date(sd.getTime());
        return jd;
    }

    public static String getMonthNumber(String month) {
        if (month.toUpperCase().trim().equals("JAN")) {
            return "01";
        } else if (month.toUpperCase().trim().equals("FEB")) {
            return "02";
        } else if (month.toUpperCase().trim().equals("MAR")) {
            return "03";
        } else if (month.toUpperCase().trim().equals("APR")) {
            return "04";
        } else if (month.toUpperCase().trim().equals("MAY")) {
            return "05";
        } else if (month.toUpperCase().trim().equals("JUN")) {
            return "06";
        } else if (month.toUpperCase().trim().equals("JUL")) {
            return "07";
        } else if (month.toUpperCase().trim().equals("AUG")) {
            return "08";
        } else if (month.toUpperCase().trim().equals("SEP")) {
            return "09";
        } else if (month.toUpperCase().trim().equals("OCT")) {
            return "10";
        } else if (month.toUpperCase().trim().equals("NOV")) {
            return "11";
        } else if (month.toUpperCase().trim().equals("DEC")) {
            return "12";
        }else{
            return "f";
        }
    }
    public static String getMonthString(int monthNumber) 
    {
        CommonUtil.log("month number = "+monthNumber);
        switch(monthNumber) 
        {
        case 1:
            return "Jan";
        case 2:
            return "Feb";
        case 3:
            return "Mar";
        case 4:
            return "Apr";
        case 5:
            return "May";
        case 6:
            return "Jun";
        case 7:
            return "Jul";
        case 8:
            return "Aug";
        case 9:
            return "Sep";
        case 10:
            return "Oct";
        case 11:
            return "Nov";
        case 12:
            return "Dec";
        default: 
            return null;
        }
    }
    public static String addHourToAmPMTime(String time, String hours) {
        Date date = null;
        SimpleDateFormat formatter = new SimpleDateFormat("h:mm");
        try {

            date = formatter.parse(time);

        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.HOUR, Integer.parseInt(hours.split(":")[0]));
        c.add(Calendar.MINUTE, Integer.parseInt(hours.split(":")[1]));
        return c.get(Calendar.HOUR) + ":" + c.get(Calendar.MINUTE);
    }

    public static String getMonDD(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.get(Calendar.DAY_OF_MONTH);
        String d =
            DateFormatSymbols.getInstance().getShortMonths()[c.get(Calendar.MONTH)].toUpperCase() +
            (c.get(Calendar.DAY_OF_MONTH) <= 9 ? "0" : "") +
            c.get(Calendar.DAY_OF_MONTH);
        return d;
    }

    public static String ddmonyyyy(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_MONTH) + "-" +
            new DateFormatSymbols().getShortMonths()[c.get(Calendar.MONTH)] +
            "-" + c.get(Calendar.YEAR);
    }

    public static void doNothing() {
    }

    public static void doSomething(String whatToDo) {
        log(whatToDo);
    }

    public static String getValueFrmExpression(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp =
            elFactory.createValueExpression(elContext, data, Object.class);
        String Message = null;
        Object obj = valueExp.getValue(elContext);
        if (obj != null) {
            Message = obj.toString();
        }
        return Message;
    }

    public static void resetWhereClause(ViewObjectImpl vo) {
        vo.setWhereClause(null);
        vo.executeQuery();
    }

    public static List<Date> getDatesBetween(Date fromDate, Date toDate) {
        List<Date> dates = new ArrayList<Date>();
        if (fromDate==null || toDate==null)
          return dates;
        else
        {
          Calendar startDate = Calendar.getInstance();
          startDate.setTime(fromDate);
          Calendar endDate = Calendar.getInstance();
          endDate.setTime(toDate);
          endDate.add(Calendar.DATE,
                      1); //INCREASING THE END DATE BY ONE TO GET FROM-DATE INCLUDED
          while (startDate.getTime().before(endDate.getTime())) {
              dates.add(startDate.getTime());
              startDate.add(Calendar.DATE, 1);
          }
          return dates;
        }
    }

    public static void setvalueToExpression(String el, Object val) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELContext elContext = facesContext.getELContext();
        ExpressionFactory expressionFactory =
            facesContext.getApplication().getExpressionFactory();
        ValueExpression exp =
            expressionFactory.createValueExpression(elContext, el,
                                                    Object.class);
        exp.setValue(elContext, val);
    }

    public static java.sql.Date convertFromJAVADateToSQLDate(java.util.Date javaDate) {
        java.sql.Date sqlDate = null;
        if (javaDate != null) {
            sqlDate = new java.sql.Date(javaDate.getTime());
        }
        return sqlDate;
    }

    public static java.sql.Date convertFromSQLDateToJAVADate(java.sql.Date sqlDate) {
        java.sql.Date javaDate = null;
        if (sqlDate != null) {
            javaDate = new java.sql.Date(sqlDate.getTime());
        }
        return javaDate;
    }
    
  public static java.util.Date
    convertFromStringToUtilDate(String datestring) {
    java.text.SimpleDateFormat sdf =
      new java.text.SimpleDateFormat("dd-MM-yyyy");
    java.util.Date date = null;
    try {
      date = sdf.parse(datestring);
    } catch (Exception exc) {
      System.out.println("Date error");
    }
    return date;
  }
  public static java.util.Date
    convertFromJboToUtilDate(oracle.jbo.domain.Date jbo) {
    //return jbo.dateValue();
    java.sql.Date sqldate = jbo.dateValue();
    java.util.Date date = new Date(sqldate.getTime());
    return date;
  }
  
  public static java.util.Date
    convertStringToUtilDate(String datestring) {
    java.text.SimpleDateFormat sdf =
      new java.text.SimpleDateFormat("dd.MM.yyyy");
    java.util.Date date = null;
    try {
      date = sdf.parse(datestring);
    } catch (Exception exc) {
      System.out.println("Date error");
    }
    return date;
  }
  
    public static String getYYYYMON(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.YEAR) +
            new DateFormatSymbols().getShortMonths()[c.get(Calendar.MONTH)].toUpperCase();
    }


    public static String subtractTime(String time1, String time2) {

        String difference = "";

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("hh:mm a");
            java.util.Date d1 = formatter.parse(time1);
            java.util.Date d2 = formatter.parse(time2);
            long timeDiff = d2.getTime() - d1.getTime();
            long diffMinutes = timeDiff / (60 * 1000) % 60;
            long diffHours = timeDiff / (60 * 60 * 1000) % 24;
            long totalMin = (diffHours * 60) + diffMinutes;
            //            if (totalMin<=0) {
            //                diffHours = 0;
            //                diffMinutes = 0;
            //            }
            //            if (totalMin>=480) {
            //                diffHours = 8;
            //                diffMinutes = 0;
            //            }
            difference = diffHours + "#" + diffMinutes;

        } catch (Exception e) {
            difference = "";
            e.printStackTrace();
        }
        return difference;
    }

    public static void redirect(String pageName) {
        FacesContext fctx = FacesContext.getCurrentInstance();
        ExternalContext ec = fctx.getExternalContext();
        HttpServletRequest request = (HttpServletRequest)ec.getRequest();
        HttpServletResponse response = (HttpServletResponse)ec.getResponse();

        try {
            response.sendRedirect(pageName);
        } catch (IOException e) {
            showMessage("Send the screen shot to I.C.T - " + e.getMessage(),
                        112);
        } finally {
            fctx.responseComplete();
        }
    }

    public static void showMessage(String message, int code) {

        FacesMessage.Severity s = null;
        if (code == 112) {
            s = FacesMessage.SEVERITY_ERROR;
        } else if (code == 111) {
            s = FacesMessage.SEVERITY_INFO;
        }

        FacesMessage msg = new FacesMessage(s, message, "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public static String randomString() {
        String random = new BigInteger(130, new SecureRandom()).toString(32);
        return (random.length() > 6 ? random.substring(0, 6) : random);
    }

    public static void createUserSession(String key, String value) {
        FacesContext fctx = FacesContext.getCurrentInstance();
        ExternalContext ec = fctx.getExternalContext();
        HttpSession userSession = (HttpSession)ec.getSession(true);
        userSession.setAttribute(key, value);
    }

    public static Object getSessionValue(String key) {
        FacesContext fctx = FacesContext.getCurrentInstance();
        ExternalContext ec = fctx.getExternalContext();
        HttpSession userSession = (HttpSession)ec.getSession(true);
        return userSession.getAttribute(key);
    }

    public static void destroySession() {
        FacesContext fctx = FacesContext.getCurrentInstance();
        ExternalContext ec = fctx.getExternalContext();
        HttpSession userSession = (HttpSession)ec.getSession(true);
        userSession.invalidate();
    }
    
    public static boolean isSessionValid()
    {
      if (CommonUtil.getSessionValue(Constants.SESSION_USERID)==null)
        return false;
      else return true;
    }
    public static FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }

    private static BindingContext getBindingContext() {
        return BindingContext.getCurrent();
    }

    private static DCBindingContainer getBindingContainer() {
        return (DCBindingContainer)getBindingContext().getCurrent().getCurrentBindingsEntry();
    }

    public static DCDataControl getDataControl(String dataControl) {
        return getBindingContext().findDataControl(dataControl);
    }

    public static Object getAppModule() {
        return getDataControl("HubModuleDataControl").getDataProvider();
    }
  
    public static Object getCustomDataControl(String dc){
        return getDataControl(dc).getDataProvider();
    }

    public static Object getCEOModule() {
        return getDataControl("CEOAppModuleDataControl").getDataProvider();
    }

    public static void refreshVO(String voName) {
        DCBindingContainer dcBindings =
            (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding iterBind = (DCIteratorBinding)dcBindings.get(voName);
        iterBind.executeQuery();
        iterBind.refresh(DCIteratorBinding.RANGESIZE_UNLIMITED);
//        iterBind.refresh(31);
    }

    public static DCIteratorBinding getIterator(String iteratorName) {
        return (DCIteratorBinding)getBindingContainer().get(iteratorName);
    }

    public static int convertTimeStringToMin(String effectiveWorkedHours) {
        int min =
            (Integer.parseInt(effectiveWorkedHours.split(":")[0]) * 60) + (Integer.parseInt(effectiveWorkedHours.split(":")[1]));
        return min;
    }


    public static Object getCurrent(String key) {
        Object result = null;
        if (key.equals(CURR_MONTH_DAYS)) {
            result = Calendar.getInstance().getActualMaximum(Calendar.YEAR);
        }
        if (key.equals(CURR_MONTH_NAME)) {
            DateFormatSymbols dfs = new DateFormatSymbols();
            String[] months = dfs.getShortMonths();
            result = months[Calendar.getInstance().get(Calendar.MONTH)];
        } else if (key.equals(PREVIOUS_MONTH_NAME)) {
            DateFormatSymbols dfs = new DateFormatSymbols();
            String[] months = dfs.getShortMonths();
            result = months[Calendar.getInstance().get(Calendar.MONTH) - 1];
        }
        return result;
    }

    private static void loadOracleDrivers() throws ClassNotFoundException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
    }

    public static Connection connectHRDB() throws Exception {
        loadOracleDrivers();
        return DriverManager.getConnection(connectionString, user, password);
    }

    public static Connection connectEBSDB() throws Exception {
        loadOracleDrivers();
        return DriverManager.getConnection(connectionString_EBS, user_EBS, password_EBS);
    }
    public static String getMONYYYY(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return new DateFormatSymbols().getShortMonths()[c.get(Calendar.MONTH)].toUpperCase() +
            c.get(Calendar.YEAR);
    }

    public static String getMM_YYYY(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return ((c.get(Calendar.MONTH) + 1) <= 9 ?
                "0" + (c.get(Calendar.MONTH) + 1) :
                (c.get(Calendar.MONTH) + 1)) + "-" + c.get(Calendar.YEAR);
    }

    public static int getTotalDaysOfPreviousMonth() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        int days = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        return days;
    }

    public static int getTotalDaysOfCurrentMonth() {
        Calendar c = Calendar.getInstance();
        int days = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        return days;
    }

    /**
     * @param value
     * @param places
     * @return
     */
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
    
    /*This method is used by approve method in the "TRAVEL" check to get the dates which 
     * were off*/
    public static List<java.util.Date> getOffDatesBetween(Date startDate, Date endDate) 
    {
      List<Date> leavesList = new ArrayList<Date>();
      if (startDate==null || endDate==null) 
        return leavesList;
      else
      {
        Calendar start = Calendar.getInstance();
        start.setTime(startDate);
        Calendar end = Calendar.getInstance();
        end.setTime(endDate);
        end.add(Calendar.DAY_OF_MONTH,1);
        for (Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date = start.getTime())
        {
            HubModuleImpl appModule = (HubModuleImpl)CommonUtil.getAppModule();
            ViewObjectImpl voCalendar = appModule.getVO_Calendar1();
            java.sql.Date sqlDate = CommonUtil.convertFromJAVADateToSQLDate(date);
            voCalendar.setWhereClause(" to_char(transaction_date,'YYYY-mm-dd') = '"+ sqlDate + "' and description not like 'SATURDAY_ON' or to_char(transaction_date,'YYYY-mm-dd') = '" + sqlDate + "' and  day_of_week like 'SUN'");
            voCalendar.executeQuery();
            RowSetIterator rsi= voCalendar.createRowSetIterator(null); 
            Row currRow = rsi.next();
            if (currRow!=null)
            {
                leavesList.add(date);
            }
        }
        return leavesList;
      }
    }
    public static boolean isHoliday(Date date) 
    {
        HubModuleImpl appModule = (HubModuleImpl)CommonUtil.getAppModule();
        ViewObjectImpl voCalendar = appModule.getVO_Calendar1();
        java.sql.Date sqlDate = CommonUtil.convertFromJAVADateToSQLDate(date);
        voCalendar.setWhereClause(" to_char(transaction_date,'YYYY-mm-dd') = '"+ sqlDate + "' and description not like 'SATURDAY_ON' or to_char(transaction_date,'YYYY-mm-dd') = '" + sqlDate + "' and  day_of_week like 'SUN'");
        voCalendar.executeQuery();
        RowSetIterator rsi= voCalendar.createRowSetIterator(null); 
        Row currRow = rsi.next();
        if (currRow!=null)
        {
            System.out.println("currRow is not null");
            return true;
        }    
        else return false;
    }
    public static boolean isLeapYear(int year) 
    {
        if ((year % 4 == 0) && year % 100 != 0)
        {
            return true;
        }
        else if ((year % 4 == 0) && (year % 100 == 0) && (year % 400 == 0))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public static Date getFiscalYearEndDate() throws ParseException {
        Calendar cal = Calendar.getInstance();
        int currMonth = cal.get(Calendar.MONTH);
        int currYear = cal.get(Calendar.YEAR);
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        Date endDate = null;
        if (currMonth>=7)
            endDate = formatter.parse("6/30/"+(currYear+1));
        else endDate = formatter.parse("6/30/"+currYear);
        return endDate;
    }
    public static Date getFiscalYearStartDate() throws ParseException {
        Calendar cal = Calendar.getInstance();
        int currMonth = cal.get(Calendar.MONTH);
        int currYear = cal.get(Calendar.YEAR);
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        Date startDate = null;
        if (currMonth<7)
            startDate = formatter.parse("7/1/"+(currYear-1));
        else startDate = formatter.parse("7/1/"+currYear);
        return startDate;
    }
    
    public static void addAvailableLeaveBalance(String userId, int leaveType, double numberOfLeaves) 
    {
        HubModuleImpl am = (HubModuleImpl)CommonUtil.getAppModule();
        ViewObjectImpl empLeaveBalanceVO;
        empLeaveBalanceVO = am.getVO_LeaveBalance1();
        resetWhereClause(empLeaveBalanceVO);
        String where = "user_id = '"+userId+"' and leave_type_id = "+leaveType;
        System.out.println(where);
        empLeaveBalanceVO.setWhereClause(where);
        empLeaveBalanceVO.executeQuery();
        RowSetIterator rsi = empLeaveBalanceVO.createRowSetIterator(null);
        if (rsi.getAllRowsInRange().length>0) 
        {
            Row row = rsi.getAllRowsInRange()[0];
            row.setAttribute("TotalLeaves", Double.parseDouble(row.getAttribute("TotalLeaves").toString())+numberOfLeaves);
            am.getTransaction().commit();
            System.out.println("number of leaves = "+row.getAttribute("TotalLeaves"));
        }
    }
    
    public static long getDifferenceDays(Date d1, Date d2) 
    {
        long diff = d2.getTime() - d1.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }
    
    public static Date getJoiningDate(String userId)
    {
      HubModuleImpl am = (HubModuleImpl) getAppModule();
      ViewObjectImpl perAllPeopleVO = am.getVO_PERALLPEOPLE1();
      perAllPeopleVO.setWhereClause("person_id = '"+userId+"'");
      perAllPeopleVO.executeQuery();
      RowSetIterator rsi = perAllPeopleVO.createRowSetIterator(null);
      if (rsi.getAllRowsInRange().length>0)
      {
        Row row = rsi.getAllRowsInRange()[0];
        System.out.println("joining date = "+row.getAttribute("JoiningDate"));
      Date joiningDate=null;
      if (row.getAttribute("JoiningDate")!=null)
        joiningDate = convertFromStringToUtilDate(row.getAttribute("JoiningDate").toString());
      System.out.println("joining date = "+ joiningDate);
        return joiningDate;
      }
      return null;
    }
    public static int getNumberOfMonthsBetween(Date startDate, Date endDate)
    {
      Calendar startCalendar = Calendar.getInstance();
      startCalendar.setTime(startDate);
      Calendar endCalendar = Calendar.getInstance();
      endCalendar.setTime(endDate);
      int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
      int diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
      return diffMonth;
    }
    public static float getLeavesTillDate(float maxLeaves, String userId)
    throws ParseException
  {
      if (userId!=null)
      {
        java.util.Date joiningDate = getJoiningDate(userId);
      CommonUtil.log("joining date = "+joiningDate);
      if (joiningDate == null)
      {
        return maxLeaves;
      }
      else
      {
        java.util.Date fiscalYearStartDate = getFiscalYearStartDate();
        Calendar calendar = Calendar.getInstance();
        java.util.Date currDate = calendar.getTime();
        long days = getDifferenceDays(fiscalYearStartDate, joiningDate);
        float totalLeavesTillDate = 0;
        float monthsTillDate = 0;
        CommonUtil.log("number of days = "+days);
        if (days<=0)
        {
          monthsTillDate = getNumberOfMonthsBetween(fiscalYearStartDate, currDate);
          totalLeavesTillDate = maxLeaves * (monthsTillDate/12);
          CommonUtil.log("totalLeavesTillDate = "+totalLeavesTillDate);
        }
        else
        {
          monthsTillDate = getNumberOfMonthsBetween(joiningDate, currDate);
          System.out.print("monnths till date = "+monthsTillDate);
          float monthsTillFiscalEnd = getNumberOfMonthsBetween(joiningDate, getFiscalYearEndDate());
          totalLeavesTillDate = maxLeaves * (monthsTillDate/monthsTillFiscalEnd);
          CommonUtil.log("totalLeavesTillDate = "+totalLeavesTillDate);
        }
        CommonUtil.log("totalLeavesTillDate = "+totalLeavesTillDate);
        return totalLeavesTillDate;
      }
    }
      return 0;
  }
    public static String getEmpCode(String empId)
    {
      HubModuleImpl am = (HubModuleImpl) CommonUtil.getAppModule();
      ViewObjectImpl usersVO = am.getVO_XxEPortalUsers1();
      CommonUtil.resetWhereClause(usersVO);
      usersVO.setWhereClause("person_id = "+empId);
      usersVO.executeQuery();
      RowSetIterator rsi = usersVO.createRowSetIterator(null);
      if (rsi.getAllRowsInRange().length>0)
        return (String) rsi.getAllRowsInRange()[0].getAttribute("EmpCode");
      return null;
    }
  public static void sendEmail(final String username, final String password, List<String> to, String subject, String body)
     {
         Properties props = new Properties();
 /*props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "nmail.nishat.net");
        props.put("mail.smtp.port", "587");*/
    props.put("mail.smtp.host", "nmail.nishat.net");
//    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.port", "25");
    props.put("mail.smtp.auth", "true");

         Session session = Session.getInstance(props,
           new javax.mail.Authenticator() {
             protected PasswordAuthentication getPasswordAuthentication() {
                 return new PasswordAuthentication(username, password);
             }
           });

         try {

             Message message = new MimeMessage(session);
             message.setFrom(new InternetAddress(username));
             message.setSubject(subject);
             message.setContent(body, "text/html; charset=utf-8");
             for (String emailTo : to)
             {
                message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(emailTo));
                Transport.send(message);
                System.out.println("Email sent successfully to "+emailTo);
             }

         } catch (MessagingException e) {
             throw new RuntimeException(e);
         }
  }
  public static int getRemainingLeaves(int leave_type, String user_id) {
      HubModuleImpl appModule = (HubModuleImpl)CommonUtil.getAppModule();
      ViewObjectImpl voRemainingLeaves = appModule.getVO_RemainingLeaves1();
      voRemainingLeaves.setNamedWhereClauseParam("user_id", user_id);
      voRemainingLeaves.setNamedWhereClauseParam("leave_type", leave_type);
      voRemainingLeaves.executeQuery();
      RowSetIterator rsiRemainingLeaves =
          voRemainingLeaves.createRowSetIterator(null);
      return Integer.parseInt(rsiRemainingLeaves.getAllRowsInRange()[0].getAttribute("LeavesRemaining").toString());
  }
  
  public static Row getSelectedRow(String iterator)
  {
    DCBindingContainer bindings =
               (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
           DCIteratorBinding dcItteratorBindings =
               bindings.findIteratorBinding(iterator);
    
           // Get an object representing the table and what may be selected within it
           ViewObject voTableData = dcItteratorBindings.getViewObject();
    
           // Get selected row
           Row rowSelected = voTableData.getCurrentRow();
           return rowSelected;
  }
  
  public static void insertLeave(String leaveType, List<java.util.Date> leaveDays,
                          java.util.Date start, java.util.Date end,
                          boolean approvalRequired, String userId,
                          String empCode)
  {
    HubModuleImpl am = (HubModuleImpl) CommonUtil.getAppModule();
    ViewObjectImpl leavesVO = am.getVO_EmpLeaves1();
    ViewObjectImpl consumedLeavesVO = am.getVO_ConsumedLeaves1();
    PolicyHelper ph = new PolicyHelper();

    /**
       * Insert leaves
       */
    Number leaveId = am.getLeaveSequence();
    leaveIdForLFA = leaveId.toString();
    Row leave = leavesVO.createRow();
    leave.setAttribute("LeaveId", leaveId);
    leave.setAttribute("LeaveType", ph.getLeaves().get(leaveType));
    leave.setAttribute("AprovedFlag", "");
    leave.setAttribute("ApprovalRequired", approvalRequired? "Y": "");
    leave.setAttribute("StartDate",
                       CommonUtil.convertFromJAVADateToSQLDate(start));
    leave.setAttribute("EndDate",
                       CommonUtil.convertFromJAVADateToSQLDate(end));
    leave.setAttribute("TotalLeaves", leaveDays.size());
    leave.setAttribute("UserId", userId);
    leavesVO.insertRow(leave);
    leavesVO.executeQuery();
    am.getTransaction().commit();
    //CommonUtil.log("Everything Cool here. . . ");


    /**
       * If approval is not required then deduct the leave balance
       */
    if (!approvalRequired && !leaveType.equals(TRAVEL_LEAVE))
    {
      //CommonUtil.log("Attendance.java: Approval is not required. Deduct leaves");
      //deduction here
      try
      {
        ph.deductLeaves(PolicyHelper.NO_APPROVAL_REQUIRED, leaveType,
                        leaveDays, empCode, userId, am);
      }
      catch (Exception ex)
      {
        ex.printStackTrace();
      }
    }
  }
  public static int updateAttendanceLeaveFlag(List<java.util.Date> leaveDays,
                                        String leaveType) {
            leaves = new HashMap<String, Integer>();
            leaves.put(CASUAL_LEAVE, 1);
            leaves.put(ANNUAL_LEAVE, 2);
            leaves.put(TRAVEL_LEAVE, 7);
      HubModuleImpl am = (HubModuleImpl)CommonUtil.getAppModule();
      ViewObjectImpl attendanceVO = am.getVO_Attendance2();
          StringBuilder sbLeaveDaysParam = new StringBuilder();
          for (java.util.Date date : leaveDays) {
              sbLeaveDaysParam.append("'" + CommonUtil.getMonDD(date) + "'" +
                                      ",");
          }
          //CommonUtil.log("sbLeaveDaysParam  = "+sbLeaveDaysParam);
          CommonUtil.resetWhereClause(attendanceVO);
          attendanceVO.executeQuery();
          String where =
              "to_char(attendance_date,'MONDD')  in(" + sbLeaveDaysParam.toString().substring(0,
                                                                                              sbLeaveDaysParam.length() -
                                                                                              1) +
              ")";
          CommonUtil.log("Select Rows to update query:" + where);
          attendanceVO.setWhereClause("to_char(attendance_date,'MONDD')  in(" +
                                      sbLeaveDaysParam.toString().substring(0,
                                                                            sbLeaveDaysParam.length() -
                                                                            1) +
                                      ")");

          String query =
              "to_char(attendance_date,'MONDD')  in(" + sbLeaveDaysParam.toString().substring(0,
                                                                                              sbLeaveDaysParam.length() -
                                                                                              1) +")";
          CommonUtil.log(query);
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
          public static void refreshPage() 
          {
            FacesContext fctx = FacesContext.getCurrentInstance();
            String page = fctx.getViewRoot().getViewId();
            ViewHandler ViewH = fctx.getApplication().getViewHandler();
            UIViewRoot UIV = ViewH.createView(fctx, page);
            UIV.setViewId(page);
            fctx.setViewRoot(UIV);
          }
          
  public static void setLeaveReport() 
  {
      HubModuleImpl am = (HubModuleImpl)CommonUtil.getAppModule();
      ViewObjectImpl voConsumedLeaves = am.getVO_ConsumedLeaves1();
      CommonUtil.resetWhereClause(voConsumedLeaves);
      Calendar calendar = Calendar.getInstance();
      int currMonth = calendar.get(Calendar.MONTH)+1;
      int currYear = calendar.get(Calendar.YEAR);
      String startDate = "1/7/"+(currYear-1);
      String endDate = "1/7/" + currYear;
      String where = null;
      String isContractual = CommonUtil.getSessionValue(Constants.SESSION_IS_CONTRACTUAL)==null?"":CommonUtil.getSessionValue(Constants.SESSION_IS_CONTRACTUAL).toString();
      if (isContractual.equals("Y"))
      {
        ViewObjectImpl voPerAllPeople = am.getVO_PERALLPEOPLE1();
        where = "person_id = '" + CommonUtil.getSessionValue(Constants.SESSION_USERID).toString() + "' and sysdate between effective_start_date and effective_end_date";
        voPerAllPeople.setWhereClause(where);
        voPerAllPeople.executeQuery();
        where = null;
        RowSetIterator rsi = voPerAllPeople.createRowSetIterator(null);
        Row currRow = rsi.getAllRowsInRange().length>0?rsi.getAllRowsInRange()[0]:null;
        if (currRow!=null)
        {
          String contractStartDateStr = currRow.getAttribute("JoiningDate").toString();
          java.util.Date contractStartDate = CommonUtil.convertFromStringToUtilDate(contractStartDateStr);
          Calendar cal = Calendar.getInstance();
          cal.setTime(contractStartDate);
          cal.add(Calendar.YEAR,1);
          java.util.Date contractEndDate = cal.getTime();
          where = "user_id = '" + CommonUtil.getSessionValue(Constants.SESSION_USERID).toString() + "' and " +
                "leave_date between to_date('"+CommonUtil.convertFromJAVADateToSQLDate(contractStartDate)+"','yyyy/mm/dd') and " +
                "to_date('"+CommonUtil.convertFromJAVADateToSQLDate(contractEndDate)+"','yyyy/mm/dd')";
        }
      }
      else
      {
        if (currMonth<7)
        {
            where =
                "user_id = '" + CommonUtil.getSessionValue(Constants.SESSION_USERID).toString() + "' and " +
                "leave_date between to_date('"+startDate+"','dd/mm/yyyy') and " +
                "to_date('"+endDate+"','dd/mm/yyyy')";
        }
        else 
        {
            where =
                "user_id = '" + CommonUtil.getSessionValue(Constants.SESSION_USERID).toString() +
               "' and leave_date >= to_date('"+endDate+"','dd/mm/yyyy')";
        }
      }
      CommonUtil.log("availed leaves query "+where);
      voConsumedLeaves.setWhereClause(where);
      voConsumedLeaves.executeQuery();

      int pageCount = voConsumedLeaves.getEstimatedRangePageCount();
      Row[] rows =
          pageCount == 1 ? voConsumedLeaves.getAllRowsInRange() :
          voConsumedLeaves.getNextRangeSet();
      
      float casualAvailed = 0;
      float annualAvailed = 0;
      float shortAvailed = 0;
      float latesAvailed = 0;
      
      float casualAvailable = 0;
      float annualAvailable = 0;
      float shortAvailable = 0;
      float latesAvailable = 0;
      
      float casualUnpaid = 0;
      float annualUnpaid = 0;
      float shortUnpaid = 0;
      float latesUnpaid = 0;
      
      while (rows.length > 0) {
          for (int i = 0; i < rows.length; i++) {
              //if (rows[i].getAttribute("EffectiveWorkedHours") == null) {
                  int leaveType =
                      Integer.parseInt(rows[i].getAttribute("LeaveType").toString());
                  switch (leaveType) {
                  case 1:
                      casualAvailed += 1;
                      break;
                  case 2:
                      annualAvailed += 1;
                      break;
                  case 3:
                      casualAvailed += .5;
                      break;
                  case 4:
                      annualAvailed += .5;
                      break;
                  case 5:
                      shortAvailed += 1;
                      break;
                  case 6:
                      latesAvailed += 1;
                      break;
                  }
              }
          //}
          rows = voConsumedLeaves.getNextRangeSet();
      }
      CommonUtil.log("Login Page: "+casualAvailed+ " - "+annualAvailed);
      CommonUtil.resetWhereClause(voConsumedLeaves);


      ViewObjectImpl voLeaveBalance = am.getVO_LeaveBalance1();
      CommonUtil.resetWhereClause(voLeaveBalance);

      String whereClause =
          "user_id = '" + CommonUtil.getSessionValue(Constants.SESSION_USERID).toString()+"' and year = 2017";
      
      CommonUtil.log(whereClause);
      voLeaveBalance.setWhereClause(whereClause);
      voLeaveBalance.executeQuery();
      int pageCount1 = voLeaveBalance.getEstimatedRangePageCount();
      RowSetIterator rsi = voLeaveBalance.createRowSetIterator(null);
      Row[] rowsLeaveBalance =
          pageCount1 == 1 ? rsi.getAllRowsInRange() :
          rsi.getNextRangeSet();
      CommonUtil.log("Leave balance rec: " + pageCount1);
      while (rowsLeaveBalance.length > 0) {
          for (int i = 0; i < rowsLeaveBalance.length; i++) {
              int leaveTypeID =
                  Integer.parseInt(rowsLeaveBalance[i].getAttribute("LeaveTypeId").toString());
              switch (leaveTypeID) {
              case 1:
                  casualAvailable =
                          Float.parseFloat(rowsLeaveBalance[i].getAttribute("TotalLeaves").toString());
                  break;
              case 2:
                  annualAvailable =
                          Float.parseFloat(rowsLeaveBalance[i].getAttribute("TotalLeaves").toString());
                  break;
              case 5:
                  shortAvailable =
                          Float.parseFloat(rowsLeaveBalance[i].getAttribute("TotalLeaves").toString());
                  break;
              case 6:
                  latesAvailable =
                          Float.parseFloat(rowsLeaveBalance[i].getAttribute("TotalLeaves").toString());
                  break;
              }
          }
          rowsLeaveBalance = rsi.getNextRangeSet();
      }
      
      casualUnpaid = (casualAvailed - casualAvailable)>0?(casualAvailed - casualAvailable):0;
      annualUnpaid = (annualAvailed - annualAvailable)>0?(annualAvailed - annualAvailable):0;
      shortUnpaid = (shortAvailed - shortAvailable)>0?(shortAvailed - shortAvailable):0;
      latesUnpaid = (latesAvailed - latesAvailable)>0?(latesAvailed - latesAvailable):0;
      
      CommonUtil.resetWhereClause(voLeaveBalance);
      CommonUtil.createUserSession(Constants.SESSION_CASUAL_AVAILED,
                                   String.valueOf(casualAvailed));
      CommonUtil.createUserSession(Constants.SESSION_ANNUAL_AVAILED,
                                   String.valueOf(annualAvailed));
      CommonUtil.createUserSession(Constants.SESSION_SHORT_AVAILED,
                                   String.valueOf(shortAvailed));
      CommonUtil.createUserSession(Constants.SESSION_LATES_AVAILED,
                                   String.valueOf(latesAvailed));

      CommonUtil.createUserSession(Constants.SESSION_CASUAL_AVAILABLE,
                                   String.valueOf(casualAvailable));
      CommonUtil.createUserSession(Constants.SESSION_ANNUAL_AVAILABLE,
                                   String.valueOf(annualAvailable));
      CommonUtil.createUserSession(Constants.SESSION_SHORT_AVAILABLE,
                                   String.valueOf(shortAvailable));
      CommonUtil.createUserSession(Constants.SESSION_LATES_AVAILABLE,
                                   String.valueOf(latesAvailable));
      
    CommonUtil.createUserSession(Constants.SESSION_CASUAL_UNPAID,
                                 String.valueOf(casualUnpaid));
    CommonUtil.createUserSession(Constants.SESSION_ANNUAL_UNPAID,
                                 String.valueOf(annualUnpaid));
    CommonUtil.createUserSession(Constants.SESSION_SHORT_UNPAID,
                                 String.valueOf(shortUnpaid));
    CommonUtil.createUserSession(Constants.SESSION_LATES_UNPAID,
                                 String.valueOf(latesUnpaid));  
  }
  
  public static Map<String,String> checkIfLeaveLimitExceeded()
  {
    Map<String,String> leaveTypeExceededList = new HashMap<String,String>();
    if (Float.parseFloat(getSessionValue(Constants.SESSION_CASUAL_UNPAID).toString())>0)
    {
      leaveTypeExceededList.put(CASUAL_LEAVE,getSessionValue(Constants.SESSION_CASUAL_UNPAID).toString());
    }
    if (Float.parseFloat(getSessionValue(Constants.SESSION_ANNUAL_UNPAID).toString())>0)
    {
      leaveTypeExceededList.put(ANNUAL_LEAVE,getSessionValue(Constants.SESSION_ANNUAL_UNPAID).toString());
    }
    if (Float.parseFloat(getSessionValue(Constants.SESSION_SHORT_UNPAID).toString())>0)
    {
      leaveTypeExceededList.put(SHORT_LEAVE,getSessionValue(Constants.SESSION_SHORT_UNPAID).toString());
    }
    if (Float.parseFloat(getSessionValue(Constants.SESSION_LATES_UNPAID).toString())>0)
    {
      leaveTypeExceededList.put(LATE_LEAVE,getSessionValue(Constants.SESSION_LATES_UNPAID).toString());
    }
    return leaveTypeExceededList;
  }
  
  public static void removeIrregularites(HubModuleImpl am, String empId, java.sql.Date startDateSQL, java.sql.Date endDateSQL)
  {
    ViewObjectImpl irregularitiesVO = am.getVO_Irregularities1();
    CommonUtil.resetWhereClause(irregularitiesVO);
    String whereClause = "emp_id = "+empId+" and attendance_date between to_date('"+startDateSQL+"','yyyy-mm-dd') and to_date('"+endDateSQL+"','yyyy-mm-dd')";
    CommonUtil.log("where clause = "+ whereClause);
    irregularitiesVO.setWhereClause(whereClause);
    irregularitiesVO.executeQuery();
    RowSetIterator rsi = irregularitiesVO.createRowSetIterator(null);
    while (rsi.next()!=null)
    {
      Row currRow = rsi.getCurrentRow();
      CommonUtil.log("irrID = "+currRow.getAttribute(0));
      rsi.removeCurrentRow();
    }
  }
  
  public static void removeConsumedLeaves(HubModuleImpl am, String empId, java.sql.Date startDateSQL, java.sql.Date endDateSQL)
  {
    ViewObjectImpl consumedLeavesVO = am.getVO_ConsumedLeavesHR1();
    CommonUtil.resetWhereClause(consumedLeavesVO);
    String whereClause = "user_id = "+empId+" and leave_date between to_date('"+startDateSQL+"','yyyy-mm-dd') and to_date('"+endDateSQL+"','yyyy-mm-dd')";
    CommonUtil.log("where clause "+whereClause);
    consumedLeavesVO.setWhereClause(whereClause);
    consumedLeavesVO.executeQuery();    
    RowSetIterator rsi = consumedLeavesVO.createRowSetIterator(null);
    while (rsi.next()!=null)
    {
      Row currRow = rsi.getCurrentRow();
      CommonUtil.log("consumedId = "+currRow.getAttribute(1));
      rsi.removeCurrentRow();
    }
  }
  
}
