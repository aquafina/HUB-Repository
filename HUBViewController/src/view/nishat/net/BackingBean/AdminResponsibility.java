package view.nishat.net.BackingBean;

import hub.nishat.net.model.AM.HubModuleImpl;

import java.util.Date;

import java.util.List;

import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.component.rich.input.RichInputDate;

import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;

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
        Row currRowConsumedLeaves = null;
        do
        {
          currRowConsumedLeaves = rsi.next();
          CommonUtil.log(rsi.getCurrentRow().getAttribute("ConsumedLeavesId").toString());
          rsi.getCurrentRow().remove();
        }
        while(rsi.next()!=null);
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
        Row currRowAttendance = null;
        do
        {
          currRowAttendance = rsi.next();
          rsi.getCurrentRow().setAttribute("LeaveToday", null);
          rsi.getCurrentRow().setAttribute("TypeOfLeave", null);
        }
        while(rsi.next()!=null);
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
  
  public void addConsumedLeave()
  {
    HubModuleImpl am = (HubModuleImpl)CommonUtil.getAppModule();
    //ViewObjectImpl 
  }
}
