package view.nishat.net.BackingBean;

import hub.nishat.net.model.AM.HubModuleImpl;

import hub.nishat.net.model.VO.VO_ExceptionReqImpl;

import java.sql.SQLException;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import java.util.Map;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;

import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.data.RichTable;

import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;


import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Number;
import oracle.jbo.server.ViewObjectImpl;

import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.RowKeySet;

import view.nishat.net.Helper.CommonUtil;
import view.nishat.net.Helper.Constants;
import view.nishat.net.Helper.PolicyHelper;

public class SupervisorResponsibility {
    private RichTable leaveRequestTable;
    private Map<Integer,String> leaves;
    private Map<String,Integer> leavesID;
    private RichTable exceptionReqTable;
    private boolean approved;
    private RichTable lfaRequest;
    private List<String> waiveOffStatusList;
  private RichSelectOneChoice waiveOffStatus;

  public SupervisorResponsibility() {
    
        leaves = new HashMap<Integer,String>();    
        leaves.put(1,"CASUAL");
        leaves.put(2,"ANNUAL");
        leaves.put(3,"HALF_CASUAL");
        leaves.put(4,"HALF_ANNUAL");
        leaves.put(7,"TRAVEL");
        
        leavesID = new HashMap<String,Integer>();    
        leavesID.put("CASUAL",1);
        leavesID.put("ANNUAL",2);
        leavesID.put("HALF_CASUAL",3);
        leavesID.put("HALF_ANNUAL",4);
        leavesID.put("TRAVEL",7);
        
        waiveOffStatusList = new ArrayList<String>();
        waiveOffStatusList.add("Pending");
        waiveOffStatusList.add("Approved");
        waiveOffStatusList.add("Disapproved");
        
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }
    private String commitAction(){
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Commit");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }
        return null;
    }

    public void setLeaveRequestTable(RichTable leaveRequestTable) {
        this.leaveRequestTable = leaveRequestTable;
    }

    public RichTable getLeaveRequestTable() {
        return leaveRequestTable;
    }

    public String approve() {
//        System.out.println("In approve method");
        HubModuleImpl am = ((HubModuleImpl)CommonUtil.getAppModule());
        PolicyHelper ph = new PolicyHelper();
        Row row = getCurrentLeaveTableRowLeaveRequests();
        String leaveType;
        leaveType = row.getAttribute("LeaveType").toString();
        System.out.println("Leave Type = "+leaveType);
        Date startDate = CommonUtil.convertJBODateToJavaDate(((oracle.jbo.domain.Date)row.getAttribute("StartDate")));
        Date endDate = CommonUtil.convertJBODateToJavaDate(((oracle.jbo.domain.Date)row.getAttribute("EndDate")));
        List<Date> leaveDays = CommonUtil.getDatesBetween(startDate,endDate);
        List<java.util.Date> offDays = CommonUtil.getOffDatesBetween(startDate, endDate);
        String empCode = row.getAttribute("EmpCode").toString();
        String userid = row.getAttribute("UserId").toString();
        if (Integer.parseInt(leaveType)==7)
        {
//            System.out.println("Leave Type = "+leaveType);
//            updateLeaveFlaginAttTable(leaveDays,userid,leaveType);
//            //add the size of offDates list into available leaves 
//            System.out.println("Number of leaves allowed = "+offDays.size());
//            row.setAttribute("AprovedFlag", "Y");
//            am.getTransaction().commit();
        }
        else
        {
            for (java.util.Date offDate : offDays)
            {
                leaveDays.remove(offDate);
            }
            System.out.println("In the else");
            updateLeaveFlaginAttTable(leaveDays,userid,leaveType);
            ph.deductLeaves(PolicyHelper.LEAVE_APPROVED,leaves.get(Integer.parseInt(leaveType)).toString(), leaveDays, empCode, userid, am);
            row.setAttribute("AprovedFlag", "Y");
            am.getTransaction().commit();
        }
        ViewObjectImpl vo = am.getVO_LeaveRequests1();
        vo.executeQuery();
        return null;
    }
    public String disapprove() {
        HubModuleImpl am = ((HubModuleImpl)CommonUtil.getAppModule());
        ViewObjectImpl vo = am.getVO_LeaveRequests1();
        
        PolicyHelper ph = new PolicyHelper();
        Row row = getCurrentLeaveTableRowLeaveRequests();
        String leaveType = row.getAttribute("LeaveType").toString();
        Date startDate = CommonUtil.convertJBODateToJavaDate(((oracle.jbo.domain.Date)row.getAttribute("StartDate")));
        Date endDate = CommonUtil.convertJBODateToJavaDate(((oracle.jbo.domain.Date)row.getAttribute("EndDate")));
        List<Date> leaveDays = CommonUtil.getDatesBetween(startDate,endDate);
        String empCode = row.getAttribute("EmpCode").toString();
        String userid = row.getAttribute("UserId").toString();
        CommonUtil.log("PolicyHelper.LEAVE_DISAPPROVED: "+PolicyHelper.LEAVE_DISAPPROVED);
        //ph.deductLeaves(PolicyHelper.LEAVE_DISAPPROVED,leaves.get(Integer.parseInt(leaveType)).toString(), leaveDays, empCode, userid, am);
        row.setAttribute("AprovedFlag", "N");
        am.getTransaction().commit();
        
        CommonUtil.refreshVO("VO_LeaveRequests1Iterator");
        vo.executeQuery();
        
        return null;
    }
  public String approveLFA() {
  //        System.out.println("In approve method");
      HubModuleImpl am = ((HubModuleImpl)CommonUtil.getAppModule());
      PolicyHelper ph = new PolicyHelper();
      Row row= CommonUtil.getSelectedRow("VO_LfaRquests1Iterator");
      if (row!=null)
      {
        String leaveId;
        leaveId = row.getAttribute("LeaveId").toString();
        System.out.println("Leave Type = "+leaveId);
        String lfaId = row.getAttribute("LfaId").toString();
        System.out.println("Lfa Id = "+lfaId);
        String userId = row.getAttribute("UserId").toString();
        System.out.println("User Id = "+userId);
        String empCode = row.getAttribute("EmpCode").toString();
        System.out.println("Emp code = "+empCode);
        Date startDate = CommonUtil.convertJBODateToJavaDate(((oracle.jbo.domain.Date)row.getAttribute("StartDate")));
        Date endDate = CommonUtil.convertJBODateToJavaDate(((oracle.jbo.domain.Date)row.getAttribute("EndDate")));
        List<Date> leaveDays = CommonUtil.getDatesBetween(startDate,endDate);
        List<java.util.Date> offDays = CommonUtil.getOffDatesBetween(startDate, endDate);
        for (java.util.Date offDate : offDays)
        {
          leaveDays.remove(offDate);
        }
        String is_HR = CommonUtil.getSessionValue(Constants.SESSION_IS_HR)==null?"":CommonUtil.getSessionValue(Constants.SESSION_IS_HR).toString();
        
        /**   Working on EMP LEAVES    **/
        ViewObjectImpl empLeavesVO = am.getVO_EmpLeaves1();
        CommonUtil.resetWhereClause(empLeavesVO);
        empLeavesVO.setWhereClause("leave_id = "+leaveId);
        empLeavesVO.executeQuery();
        RowSetIterator rsi = empLeavesVO.createRowSetIterator(null);
        Row currRow = null;
        if (is_HR.equals("Y"))
        {
          if (rsi.getAllRowsInRange().length>0)
          {
            currRow = rsi.getAllRowsInRange()[0];
            currRow.setAttribute("AprovedFlag", "Y");
          }
        }
        
        /**   Working on LFA    **/
        ViewObjectImpl lfaVO = am.getVO_LFA1();
        CommonUtil.resetWhereClause(empLeavesVO);
        lfaVO.setWhereClause("lfa_id = "+lfaId);
        lfaVO.executeQuery();
        rsi = lfaVO.createRowSetIterator(null);
        if (is_HR.equals("Y"))
        {
          if (rsi.getAllRowsInRange().length>0)
          {  
            currRow = rsi.getAllRowsInRange()[0];
            currRow.setAttribute("HrApproved", "Y");
          }
          updateLeaveFlaginAttTable(leaveDays,userId,"2");
          ph.deductLeaves(PolicyHelper.LEAVE_APPROVED,leaves.get(2).toString(), leaveDays, empCode, userId, am);
        }
        else
        {
          if (rsi.getAllRowsInRange().length>0)
          {  
            currRow = rsi.getAllRowsInRange()[0];
            currRow.setAttribute("LineManagerApproved", "Y");
          }
        }
        am.getTransaction().commit();
        ViewObjectImpl vo = am.getVO_LfaRquests1();
        vo.executeQuery();
      }
      return null;
  }
  
  public String disapproveLFA()
  {
    HubModuleImpl am = ((HubModuleImpl)CommonUtil.getAppModule());
    Row row= CommonUtil.getSelectedRow("VO_LfaRquests1Iterator");
    if (row!=null)
    {
      String leaveId = row.getAttribute("LeaveId").toString();
      System.out.println("Leave Type = "+leaveId);
      String lfaId = row.getAttribute("LfaId").toString();
      System.out.println("Lfa Id = "+lfaId);
      String userId = row.getAttribute("UserId").toString();
      System.out.println("User Id = "+userId);
      String is_HR = CommonUtil.getSessionValue(Constants.SESSION_IS_HR)==null?"":CommonUtil.getSessionValue(Constants.SESSION_IS_HR).toString();
      
      /**   Working on EMP LEAVES    **/
      ViewObjectImpl empLeavesVO = am.getVO_EmpLeaves1();
      CommonUtil.resetWhereClause(empLeavesVO);
      empLeavesVO.setWhereClause("leave_id = "+leaveId);
      empLeavesVO.executeQuery();
      RowSetIterator rsi = empLeavesVO.createRowSetIterator(null);
      Row currRow = null;
      if (is_HR.equals("Y"))
      {
        if (rsi.getAllRowsInRange().length>0)
        {
          currRow = rsi.getAllRowsInRange()[0];
          currRow.setAttribute("AprovedFlag", "N");
        }
      }
      /**   Working on LFA    **/
      ViewObjectImpl lfaVO = am.getVO_LFA1();
      CommonUtil.resetWhereClause(lfaVO);
      lfaVO.setWhereClause("lfa_id = "+lfaId);
      lfaVO.executeQuery();
      rsi = lfaVO.createRowSetIterator(null);
      if (is_HR.equals("Y"))
      {
        if (rsi.getAllRowsInRange().length>0)
        {  
          currRow = rsi.getAllRowsInRange()[0];
          currRow.setAttribute("HrApproved", "N");
        }
      }
      else
      {
        if (rsi.getAllRowsInRange().length>0)
        {  
          currRow = rsi.getAllRowsInRange()[0];
          currRow.setAttribute("LineManagerApproved", "N");
        }
      }
      am.getTransaction().commit();
      ViewObjectImpl vo = am.getVO_LfaRquests1();
      vo.executeQuery();
    }
    return null;
  }
  
  public String approveLCF()
  {
    HubModuleImpl am = ((HubModuleImpl)CommonUtil.getAppModule());
    PolicyHelper ph = new PolicyHelper();
    Row row= CommonUtil.getSelectedRow("VO_LcfRequests1Iterator");
    if (row!=null)
    {
      String leaveId;
      leaveId = row.getAttribute("LeaveId").toString();
      System.out.println("Leave Type = "+leaveId);
      String lcfId = row.getAttribute("LcfId").toString();
      System.out.println("Lcf Id = "+lcfId);
      String userId = row.getAttribute("UserId").toString();
      System.out.println("User Id = "+userId);
      String empCode = row.getAttribute("EmpCode").toString();
      System.out.println("Emp code = "+empCode);
      Date startDate = CommonUtil.convertJBODateToJavaDate(((oracle.jbo.domain.Date)row.getAttribute("StartDate")));
      Date endDate = CommonUtil.convertJBODateToJavaDate(((oracle.jbo.domain.Date)row.getAttribute("EndDate")));
      List<Date> leaveDays = CommonUtil.getDatesBetween(startDate,endDate);
      List<java.util.Date> offDays = CommonUtil.getOffDatesBetween(startDate, endDate);
      for (java.util.Date offDate : offDays)
      {
        leaveDays.remove(offDate);
      }
      
      /**   Working on EMP LEAVES    **/
      ViewObjectImpl empLeavesVO = am.getVO_EmpLeaves1();
      CommonUtil.resetWhereClause(empLeavesVO);
      empLeavesVO.setWhereClause("leave_id = "+leaveId);
      empLeavesVO.executeQuery();
      RowSetIterator rsi = empLeavesVO.createRowSetIterator(null);
      Row currRow = null;
      if (rsi.getAllRowsInRange().length>0)
      {
        currRow = rsi.getAllRowsInRange()[0];
        currRow.setAttribute("AprovedFlag", "Y");
      }
      
      /**   Working on LCF    **/
      ViewObjectImpl lcfVO = am.getVO_LCF1();
      CommonUtil.resetWhereClause(lcfVO);
      lcfVO.setWhereClause("lcf_id = "+lcfId);
      lcfVO.executeQuery();
      rsi = lcfVO.createRowSetIterator(null);
      if (rsi.getAllRowsInRange().length>0)
      {  
        currRow = rsi.getAllRowsInRange()[0];
        currRow.setAttribute("HrApproved", "Y");
      }
      updateLeaveFlaginAttTable(leaveDays,userId,"2");
      ph.deductLeaves(PolicyHelper.LEAVE_APPROVED,leaves.get(2).toString(), leaveDays, empCode, userId, am);
    }
    am.getTransaction().commit();
    ViewObjectImpl vo = am.getVO_LcfRequests1();
    vo.executeQuery();
    return null;  
  }
  public String disapproveLCF()
  {
    HubModuleImpl am = ((HubModuleImpl)CommonUtil.getAppModule());
    Row row= CommonUtil.getSelectedRow("VO_LcfRequests1Iterator");
    if (row!=null)
    {
      String leaveId = row.getAttribute("LeaveId").toString();
      System.out.println("Leave Type = "+leaveId);
      String lcfId = row.getAttribute("LcfId").toString();
      System.out.println("Lcf Id = "+lcfId);
      String userId = row.getAttribute("UserId").toString();
      System.out.println("User Id = "+userId);
      
      /**   Working on EMP LEAVES    **/
      ViewObjectImpl empLeavesVO = am.getVO_EmpLeaves1();
      CommonUtil.resetWhereClause(empLeavesVO);
      empLeavesVO.setWhereClause("leave_id = "+leaveId);
      empLeavesVO.executeQuery();
      RowSetIterator rsi = empLeavesVO.createRowSetIterator(null);
      Row currRow = null;
      if (rsi.getAllRowsInRange().length>0)
      {
        currRow = rsi.getAllRowsInRange()[0];
        currRow.setAttribute("AprovedFlag", "N");
      }
     
      /**   Working on LFA    **/
      ViewObjectImpl lcfVO = am.getVO_LCF1();
      CommonUtil.resetWhereClause(lcfVO);
      lcfVO.setWhereClause("lcf_id = "+lcfId);
      lcfVO.executeQuery();
      rsi = lcfVO.createRowSetIterator(null);
      if (rsi.getAllRowsInRange().length>0)
      {  
        currRow = rsi.getAllRowsInRange()[0];
        currRow.setAttribute("HrApproved", "N");
      }
      am.getTransaction().commit();
      ViewObjectImpl vo = am.getVO_LcfRequests1();
      vo.executeQuery();
    }
    return null;
  }
    public Row getCurrentLeaveTableRowLeaveRequests(){
        HubModuleImpl am = ((HubModuleImpl)CommonUtil.getAppModule());
        /**GET THE SELECTED ROW KEY SET OF THE TABLE FROM THE PAGE
         * AND THEN GET THE KEY THAT WE WILL USE TO FIND THE ROW
         * FROM THE VIEW OBJECT*/
        RowKeySet selectedEmps = getLeaveRequestTable().getSelectedRowKeys();
        Iterator selectedEmpIter = selectedEmps.iterator();
        Key key = (Key)((List)selectedEmpIter.next()).get(0);

        /**GET THE SELECTED VIEW OBJECT ROW USING THE KEY*/
        ViewObjectImpl vo = am.getVO_LeaveRequests1();
        vo.executeQuery();
        Row currentRow = vo.getRow(key);
        return currentRow;
    }
    public Row getExceptionTableRow(){
        HubModuleImpl am = ((HubModuleImpl)CommonUtil.getAppModule());
        /**GET THE SELECTED ROW KEY SET OF THE TABLE FROM THE PAGE
         * AND THEN GET THE KEY THAT WE WILL USE TO FIND THE ROW
         * FROM THE VIEW OBJECT*/
        RowKeySet selectedEmps = getExceptionReqTable().getSelectedRowKeys();
        Iterator selectedEmpIter = selectedEmps.iterator();
        Key key = (Key)((List)selectedEmpIter.next()).get(0);

        /**GET THE SELECTED VIEW OBJECT ROW USING THE KEY*/
        ViewObjectImpl vo = am.getVO_ExceptionReq1();
        vo.executeQuery();
        Row currentRow = vo.getRow(key);
        return currentRow;
    }

    public String approveException() {
        Row row = getExceptionTableRow();
        String requestId = row.getAttribute("ExceptionRequestId").toString();
        exceptionAction(requestId, "Y");
        return null;
    }
    public void exceptionAction(String requestID,String flag){
        HubModuleImpl am = (HubModuleImpl)CommonUtil.getAppModule();
        ViewObjectImpl voExceptions = am.getVO_ExceptionReq1();
        CommonUtil.resetWhereClause(voExceptions);
        String whereClause = "EXCEPTION_REQUEST_ID = '"+requestID+"'";
        whereClause += flag.equals("N")?" and (APPROVED = 'P' or APPROVED  = 'Y')":flag.equals("Y")?" and (APPROVED  = 'P' or APPROVED = 'N')":"";
        CommonUtil.log(whereClause);
        voExceptions.setWhereClause(whereClause);
        voExceptions.executeQuery();
        Row exception = voExceptions.createRowSetIterator(null).getAllRowsInRange()[0];
        exception.setAttribute("Approved", flag);
        String irrID = exception.getAttribute("IrregularityId").toString();
        CommonUtil.log("Asim: irr id: "+irrID);
        
        ViewObjectImpl voEvents = am.getVO_AttendanceEvents1();
        CommonUtil.resetWhereClause(voEvents);
        voEvents.setWhereClause("IRREGULARITY_ID = '"+irrID+"'");
        voEvents.executeQuery();
        int pageCount = voEvents.getEstimatedRangePageCount();
        Row irregularity = voEvents.createRowSetIterator(null).getAllRowsInRange()[0];
        irregularity.setAttribute("ExceptionApproved", flag);
        voEvents.executeQuery();
        
        
        
        am.getTransaction().commit();
        CommonUtil.resetWhereClause(voExceptions);
        CommonUtil.refreshVO("VO_ExceptionReq1Iterator");
        voExceptions.executeQuery();
    }

    public String disapproveException() {
        Row row = getExceptionTableRow();
        String requestId = row.getAttribute("ExceptionRequestId").toString();
        exceptionAction(requestId, "N");
        
//        String empCode= row.getAttribute("EmpCode").toString();
//        Date date = CommonUtil.convertJBODateToJavaDate((oracle.jbo.domain.Date)row.getAttribute("AttendanceDate"));
//        List<Date> leaveDays = new ArrayList<Date>();
//        leaveDays.add(date);
//        int  userId = ((Number)row.getAttribute("EmpId")).intValue();
//        
//        int missing_minutes = ((Number)row.getAttribute("MissingMinutes")).intValue();
//        PolicyHelper ph = new PolicyHelper();
//        ph.deductLeaves(4,ph.whatToDeduct(missing_minutes), leaveDays, empCode, String.valueOf(userId), (HubModuleImpl)CommonUtil.getAppModule());
        return null;
    }

    public void setExceptionReqTable(RichTable exceptionReqTable) {
        this.exceptionReqTable = exceptionReqTable;
    }

    public RichTable getExceptionReqTable() {
        return exceptionReqTable;
    }

    private void updateLeaveFlaginAttTable(List<Date> leaveDays,String userid,String leaveType) {
        //System.out.println("inside the update leave flage in attd table method");
        HubModuleImpl am = (HubModuleImpl)CommonUtil.getAppModule();
        ViewObjectImpl voAtt = am.getVO_XXEPortalAttendance1();
        for (Date d:leaveDays) 
        {
            CommonUtil.resetWhereClause(voAtt);
            String where = " to_char(attendance_date,'YYYY-mm-dd') = '"+CommonUtil.convertFromJAVADateToSQLDate(d)+"' and emp_id = '"+userid+"'";
            voAtt.setWhereClause(where);
            voAtt.executeQuery();
            RowSetIterator rsi = voAtt.createRowSetIterator(null);
            Row row = rsi!=null?rsi.getAllRowsInRange().length>0?rsi.getAllRowsInRange()[0]:null:null;
            if (row!=null)
            {
                row.setAttribute("LeaveToday", "Y");
                row.setAttribute("TypeOfLeave", leaveType);
            }
        } 
    }
    
    public boolean getApproved() 
    {
        Row currentRow = getCurrentLeaveTableRowLeaveRequests();  
        if (currentRow.getAttribute("approvedFlag").toString().equals("Y"))
            return true;
        return false;
    }
    public void setApproved(boolean value) 
    {
        approved = value;
    }

  public void statusChangedListener(ValueChangeEvent valueChangeEvent)
  {
    HubModuleImpl am = (HubModuleImpl)CommonUtil.getAppModule();
    ViewObjectImpl voExceptionReq = am.getVO_ExceptionReq1();
    CommonUtil.resetWhereClause(voExceptionReq); 
    CommonUtil.log(waiveOffStatus.getValue()+"");
    int value = 
      waiveOffStatus.getValue()==null?1:Integer.parseInt(valueChangeEvent.getNewValue().toString());
    switch(value)
    {
      case 1:
        CommonUtil.log("setting value of status");
        voExceptionReq.setNamedWhereClauseParam("status", "P");
        waiveOffStatus.setValue("1");
        CommonUtil.log("Pending");
        break;
      case 2:
        voExceptionReq.setNamedWhereClauseParam("status", "Y");
        waiveOffStatus.setValue("2");
        CommonUtil.log("Approved");
        break;
      case 3:
        voExceptionReq.setNamedWhereClauseParam("status", "N");
        waiveOffStatus.setValue("3");
        CommonUtil.log("Disapproved");
        break;
      default:
        voExceptionReq.setNamedWhereClauseParam("status", "P");
        CommonUtil.log("Pending default");
        break;
    }
    voExceptionReq.executeQuery();
  }

  public void setWaiveOffStatusList(List<String> waiveOffStatusList)
  {
    this.waiveOffStatusList = waiveOffStatusList;
  }

  public List<String> getWaiveOffStatusList()
  {
    return waiveOffStatusList;
  }

  public void setWaiveOffStatus(RichSelectOneChoice waiveOffStatus)
  {
    int value = 
      waiveOffStatus.getValue()==null?1:Integer.parseInt(waiveOffStatus.getValue().toString());
    this.waiveOffStatus = waiveOffStatus;
  }

  public RichSelectOneChoice getWaiveOffStatus()
  {
    return waiveOffStatus;
  }
}
