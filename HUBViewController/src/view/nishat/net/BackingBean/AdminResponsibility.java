package view.nishat.net.BackingBean;

import hub.nishat.net.model.AM.HubModuleImpl;

import java.util.Date;

import java.util.List;

import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.component.rich.input.RichInputDate;

import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;

import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

import oracle.jbo.Row;
import oracle.jbo.RowSet;
import oracle.jbo.RowSetIterator;
import oracle.jbo.server.ViewObjectImpl;

import view.nishat.net.Helper.CommonUtil;

public class AdminResponsibility
{
  private RichInputDate fromDate;
  private RichInputDate toDate;
  private RichInputListOfValues userId;
  private RichInputText userIdAddLeave;
  private RichInputDate leaveDate;
  private RichSelectOneChoice leaveType;

  public AdminResponsibility()
  {
    super();
  }

  public void setFromDate(RichInputDate fromDate)
  {
    this.fromDate = fromDate;
  }

  public RichInputDate getFromDate()
  {
    return fromDate;
  }

  public void setToDate(RichInputDate toDate)
  {
    this.toDate = toDate;
  }

  public RichInputDate getToDate()
  {
    return toDate;
  }

  public void removeLeavesActionListener(ActionEvent actionEvent)
  {
    Date startDate = fromDate.getValue()==null?null:(java.util.Date)fromDate.getValue();
    Date endDate = toDate.getValue()==null?null:(java.util.Date)toDate.getValue();
    Row currRowEmployeeLov = CommonUtil.getSelectedRow("EmployeeLov1Iterator");
    CommonUtil.log(currRowEmployeeLov.getAttribute(0).toString());
    String userId = currRowEmployeeLov.getAttribute(0).toString();
    if (startDate==null || endDate==null)
    {
      CommonUtil.showMessage("You have to enter at both From and To Dates", 112);
    }
    else
    {
      /** FIND LEAVES FROM CONSUMED LEAVES TABLE B/W START DATE AND END DATE AND REMOVE THEM **/
      HubModuleImpl am = (HubModuleImpl) CommonUtil.getAppModule();
      ViewObjectImpl voConsumedLeaves = am.getVO_ConsumedLeaves1();
      //CommonUtil.resetWhereClause(voConsumedLeaves);
      String whereClause = " user_id = '"+userId+"' and leave_date between to_date('"+CommonUtil.convertFromJAVADateToSQLDate(startDate)+"','yyyy/mm/dd') and to_date('"+CommonUtil.convertFromJAVADateToSQLDate(endDate)+"','yyyy/mm/dd')";
      CommonUtil.log(whereClause);
      voConsumedLeaves.setWhereClause(whereClause);
      voConsumedLeaves.executeQuery();
      RowSetIterator rsi = voConsumedLeaves.createRowSetIterator(null);
      if (rsi.getAllRowsInRange().length>0)
      {
        Row currRowConsumedLeaves = rsi.next();
        while(currRowConsumedLeaves!=null)
        {
          CommonUtil.log(rsi.getCurrentRow().getAttribute("ConsumedLeavesId").toString());
          currRowConsumedLeaves.remove();
          currRowConsumedLeaves = rsi.next();
        }
//        voConsumedLeaves.getDBTransaction().commit();
      }
      /** REMOVE THE LEAVE_TODAY AND LEAVE_TYPE FLAGS FROM ATTENDANCE TABLE **/
      ViewObjectImpl voAttendance = am.getVO_UpdateAttendanceFlag1();
      whereClause = " emp_id = '"+userId+"' and attendance_date between to_date('"+CommonUtil.convertFromJAVADateToSQLDate(startDate)+"','yyyy/mm/dd') and to_date('"+CommonUtil.convertFromJAVADateToSQLDate(endDate)+"','yyyy/mm/dd')";
      CommonUtil.log(whereClause);
      CommonUtil.resetWhereClause(voAttendance);
      voAttendance.setWhereClause(whereClause);
      voAttendance.executeQuery();
      rsi = voAttendance.createRowSetIterator(null);
      CommonUtil.log("length of atd = "+rsi.getAllRowsInRange().length);
      if (rsi.getAllRowsInRange().length>0)
      {
        Row currRowAttendance = rsi.next();
        while (currRowAttendance!=null)
        {
          currRowAttendance.setAttribute("LeaveToday", null);
          currRowAttendance.setAttribute("TypeOfLeave", null);
          currRowAttendance = rsi.next();
        }
      }
      am.getTransaction().commit();
    }
  }

  public void setUserId(RichInputListOfValues userId)
  {
    this.userId = userId;
  }

  public RichInputListOfValues getUserId()
  {
    return userId;
  }
  
  public void saveLeaveActionListener(ActionEvent actionEvent)
    {
      HubModuleImpl am = (HubModuleImpl)CommonUtil.getAppModule();
      ViewObjectImpl voAttendance = am.getVO_UpdateAttendanceFlag1();
      CommonUtil.log("user id = "+userIdAddLeave.getValue());
      CommonUtil.log("leave date = "+leaveDate.getValue());
      CommonUtil.log("leave type = "+ leaveType.getValue());
      CommonUtil.resetWhereClause(voAttendance);
      String whereClause = "emp_id = '"+userIdAddLeave.getValue()+"' and attendance_date = to_date('"+leaveDate.getValue()+"','yyyy/mm/dd')";
      voAttendance.setWhereClause(whereClause);
      CommonUtil.log("whereclause = "+whereClause);
      voAttendance.executeQuery();
      RowSetIterator rsi = voAttendance.createRowSetIterator(null);
      if (rsi.getAllRowsInRange().length>0)
      {
        Row currRow = rsi.getAllRowsInRange()[0];
        CommonUtil.log("empname = "+currRow.getAttribute("EmpName"));
        currRow.setAttribute("LeaveToday","Y");
        currRow.setAttribute("TypeOfLeave",leaveType.getValue());
      }
      am.getTransaction().commit();
      //ViewObjectImpl 
    }

  public void setUserIdAddLeave(RichInputText userIdAddLeave)
  {
    this.userIdAddLeave = userIdAddLeave;
  }

  public RichInputText getUserIdAddLeave()
  {
    return userIdAddLeave;
  }

  public void setLeaveDate(RichInputDate leaveDate)
  {
    this.leaveDate = leaveDate;
  }

  public RichInputDate getLeaveDate()
  {
    return leaveDate;
  }

  public void setLeaveType(RichSelectOneChoice leaveType)
  {
    this.leaveType = leaveType;
  }

  public RichSelectOneChoice getLeaveType()
  {
    return leaveType;
  }
}
