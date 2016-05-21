package view.nishat.net.BackingBean;

import hub.nishat.net.model.AM.HubModuleImpl;

import java.util.List;

import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputNumberSpinbox;
import oracle.adf.view.rich.component.rich.output.RichOutputText;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.server.ViewObjectImpl;

import view.nishat.net.Helper.CommonUtil;

public class HRTravellingLeaves
{
  private RichOutputText empIdSelected;
  private RichInputDate startDateSelected;
  private RichInputDate endDateSelected;
  private List<java.util.Date> travellingDates;
  private static final String TRAVEL_LEAVE = "TRAVEL_LEAVE";
  private static final String INTIMATE_SUPERVISOR = "intimate";
  private RichInputNumberSpinbox startTimeHH;
  private RichInputNumberSpinbox startTimeMM;
  private RichInputNumberSpinbox endTimeHH;
  private RichInputNumberSpinbox endTimeMM;
  private RichInputNumberSpinbox numberOfFullLeaves;
  private RichInputNumberSpinbox numberOfHalfLeaves;

  public HRTravellingLeaves()
  {
    super();
  }

  public void setEmpIdSelected(RichOutputText empIdSelected)
  {
    this.empIdSelected = empIdSelected;
  }

  public RichOutputText getEmpIdSelected()
  {
    return empIdSelected;
  }

  public void setStartDateSelected(RichInputDate startDateSelected)
  {
    this.startDateSelected = startDateSelected;
  }

  public RichInputDate getStartDateSelected()
  {
    return startDateSelected;
  }

  public void setEndDateSelected(RichInputDate endDateSelected)
  {
    this.endDateSelected = endDateSelected;
  }

  public RichInputDate getEndDateSelected()
  {
    return endDateSelected;
  }

  public void addTravellingLeaves(ActionEvent actionEvent)
  {
    CommonUtil.log("empId = "+empIdSelected.getValue().toString());
    String empId = empIdSelected.getValue().toString();
    CommonUtil.log("start date = "+startDateSelected.getValue());
    CommonUtil.log("end date = "+endDateSelected.getValue());
    java.util.Date startDate = startDateSelected == null?null:(java.util.Date)startDateSelected.getValue();
    java.util.Date endDate = endDateSelected == null?null:(java.util.Date)endDateSelected.getValue();
    int startHH = startTimeHH.getValue()==null?0:Integer.parseInt(startTimeHH.getValue().toString());
    int startMM = startTimeMM.getValue()==null?0:Integer.parseInt(startTimeMM.getValue().toString());
    int endHH = endTimeHH.getValue()==null?0:Integer.parseInt(endTimeHH.getValue().toString());
    int endMM = endTimeMM.getValue()==null?0:Integer.parseInt(endTimeMM.getValue().toString());
    if (startDate==null && endDate==null)
    {
    float fullLeaves = numberOfFullLeaves.getValue()==null?0:Float.parseFloat(numberOfFullLeaves.getValue().toString());
    float halfLeaves = numberOfHalfLeaves.getValue()==null?0:(Float.parseFloat(numberOfHalfLeaves.getValue().toString()));
      float addTotalLeaves = fullLeaves+halfLeaves;
      CommonUtil.log("number of casual leaves added = "+addTotalLeaves);
      CommonUtil.addAvailableLeaveBalance(empId, 1, addTotalLeaves);
    }
    else
    {
      Attendance atdObject = new Attendance();
      atdObject.addTravellingTimeCompensatoryLeave(empId, startDate, startHH, startMM, endDate, endHH, endMM);
      String empCode = CommonUtil.getEmpCode(empId);
      CommonUtil.log("empCode = "+empCode);
      atdObject.processLeaves(startDate, endDate, empId, empCode, TRAVEL_LEAVE, INTIMATE_SUPERVISOR);
      HubModuleImpl am = (HubModuleImpl)CommonUtil.getAppModule();
      java.sql.Date startDateSQL = CommonUtil.convertFromJAVADateToSQLDate(startDate);
      java.sql.Date endDateSQL = CommonUtil.convertFromJAVADateToSQLDate(endDate);
      CommonUtil.removeIrregularites(am,empIdSelected.getValue().toString(), startDateSQL, endDateSQL);
      CommonUtil.removeConsumedLeaves(am, empIdSelected.getValue().toString(), startDateSQL, endDateSQL);
      am.getTransaction().commit();
    }
  }

  public void setStartTimeHH(RichInputNumberSpinbox startTimeHH)
  {
    this.startTimeHH = startTimeHH;
  }

  public RichInputNumberSpinbox getStartTimeHH()
  {
    return startTimeHH;
  }

  public void setStartTimeMM(RichInputNumberSpinbox startTimeMM)
  {
    this.startTimeMM = startTimeMM;
  }

  public RichInputNumberSpinbox getStartTimeMM()
  {
    return startTimeMM;
  }

  public void setEndTimeHH(RichInputNumberSpinbox endTimeHH)
  {
    this.endTimeHH = endTimeHH;
  }

  public RichInputNumberSpinbox getEndTimeHH()
  {
    return endTimeHH;
  }

  public void setEndTimeMM(RichInputNumberSpinbox endTimeMM)
  {
    this.endTimeMM = endTimeMM;
  }

  public RichInputNumberSpinbox getEndTimeMM()
  {
    return endTimeMM;
  }

  public void setNumberOfFullLeaves(RichInputNumberSpinbox numberOfFullLeaves)
  {
    this.numberOfFullLeaves = numberOfFullLeaves;
  }

  public RichInputNumberSpinbox getNumberOfFullLeaves()
  {
    return numberOfFullLeaves;
  }

  public void setNumberOfHalfLeaves(RichInputNumberSpinbox numberOfHalfLeaves)
  {
    this.numberOfHalfLeaves = numberOfHalfLeaves;
  }

  public RichInputNumberSpinbox getNumberOfHalfLeaves()
  {
    return numberOfHalfLeaves;
  }
}
