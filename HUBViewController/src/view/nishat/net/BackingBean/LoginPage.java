package view.nishat.net.BackingBean;

import hub.nishat.net.model.AM.HubModuleImpl;

import java.io.IOException;

import java.sql.Date;
import java.sql.SQLException;

import java.text.DateFormatSymbols;

import java.util.Calendar;

import java.util.List;

import javax.faces.application.FacesMessage;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.dms.http.HttpRequest;

import oracle.javatools.parser.java.v2.common.CommonUtilities;

import oracle.jbo.Row;
import oracle.jbo.RowSet;
import oracle.jbo.RowSetIterator;
import oracle.jbo.server.ViewObjectImpl;

import view.nishat.net.Helper.AuthHandler;
import view.nishat.net.Helper.CommonUtil;
import view.nishat.net.Helper.Constants;
import view.nishat.net.Helper.PolicyHelper;
import view.nishat.net.PoJo.MonthlyDeductedLeave;

public class LoginPage {
    private RichInputText username;
    private RichInputText password;

    public LoginPage() {
        System.out.println("Login Bean Created");
    }

    public void setUsername(RichInputText username) {
        this.username = username;
    }

    public RichInputText getUsername() {
        return username;
    }

    public void setPassword(RichInputText password) {
        this.password = password;
    }

    public RichInputText getPassword() {
        return password;
    }

    public String proceedLogin() {
        //get the username and password that user entered
        String _username = username.getValue().toString();
        String _password = password.getValue().toString();

        AuthHandler authHandler = new AuthHandler();
        int result_code = -1;
        try {
            result_code = authHandler.authenticate(_username, _password);
        } catch (ClassNotFoundException e) {
            CommonUtil.showMessage("Take Screenshot and email it to I.C.T - " +
                                   e.getMessage() + " LoginPage.java line 52",
                                   112);
        }

        switch (result_code) {
        case 0:
            CommonUtil.showMessage("Invalid Username or password", 112);
            break;
        case 1:
//            setHierarchyData();
            CommonUtil.setLeaveReport();
            calculateAvailableLeaves();
            DateFormatSymbols d = new DateFormatSymbols();
            String month = d.getShortMonths()[Calendar.getInstance().get(Calendar.MONTH)];
            CommonUtil.createUserSession("yearmonth", CommonUtil.getMonthNumber(month)+"-"+Calendar.getInstance().get(Calendar.YEAR));
            CommonUtil.createUserSession(Constants.SESSION_CURRENT_DATE, Calendar.getInstance().get(Calendar.DATE) +"-"+CommonUtil.getMonthNumber(month)+"-"+Calendar.getInstance().get(Calendar.YEAR));
            CommonUtil.log(CommonUtil.getSessionValue(Constants.SESSION_USERID).toString());
            if (CommonUtil.getSessionValue(Constants.SESSION_USERID).toString().equals("84")) {
                CommonUtil.redirect("CEOReports/employee_report.jspx");
            }else{
                CommonUtil.redirect("employee_attendance.jspx");
            }
            break;
        case 2:
            CommonUtil.redirect("confirm_password_page.jspx");
            break;
        }
        return null;
    }

    public String proceedLogout() {
        CommonUtil.destroySession();
        CommonUtil.redirect("/TheHUB/faces/login_page.jspx");
        return null;
    }
    
    public int calculateAvailableLeaves() 
    {
        int availableLeaves = 0;
        HubModuleImpl am = (HubModuleImpl)CommonUtil.getAppModule();
        ViewObjectImpl voUsers = am.getVO_XxEPortalUsers1();
        String userId = CommonUtil.getSessionValue(Constants.SESSION_USERID).toString();
        voUsers.setWhereClause("person_id = "+userId);
        System.out.println("person_id = "+userId);
        voUsers.executeQuery();
        RowSetIterator rsi =  voUsers.createRowSetIterator(null);
        Row userRow = null;
        userRow = rsi.next();
        if (userRow != null)
        {
            java.util.Date joiningDate = CommonUtil.convertJBODateToJavaDate((oracle.jbo.domain.Date)userRow.getAttribute("EffectiveStartDate"));
            CommonUtil.log("Joining Date = "+joiningDate);
        }
        return availableLeaves;
    }
  public void setHierarchyData()
  {
    HubModuleImpl am = (HubModuleImpl)CommonUtil.getAppModule();
    ViewObjectImpl empSummaryVO = am.getVO_EmpHierarchySummary1();
    CommonUtil.resetWhereClause(empSummaryVO);
    String where = "person_id = "+CommonUtil.getSessionValue(Constants.SESSION_USERID);
    CommonUtil.log("where clause "+where);
    empSummaryVO.setWhereClause("person_id = "+CommonUtil.getSessionValue(Constants.SESSION_USERID));
    RowSetIterator rsi = empSummaryVO.createRowSetIterator(null);
    if (rsi.getAllRowsInRange().length>0)
    {
      Row row = rsi.getAllRowsInRange()[0];
      CommonUtil.createUserSession(Constants.SESSION_EMP_DPT, row.getAttribute("HierarchyName").toString());
      CommonUtil.createUserSession(Constants.SESSION_EMP_NAME, row.getAttribute("FullNameChild").toString());
      CommonUtil.createUserSession(Constants.SESSION_HOD_EMAIL, row.getAttribute("Hodemail").toString());
      CommonUtil.log("dept name = "+CommonUtil.getSessionValue(Constants.SESSION_EMP_DPT));
      CommonUtil.log("emp name = "+CommonUtil.getSessionValue(Constants.SESSION_EMP_NAME));
      CommonUtil.log("HOD email = "+CommonUtil.getSessionValue(Constants.SESSION_HOD_EMAIL));
    }
  }
}
