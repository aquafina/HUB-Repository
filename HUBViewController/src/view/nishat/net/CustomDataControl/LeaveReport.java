package view.nishat.net.CustomDataControl;

import hub.nishat.net.model.AM.HubModuleImpl;

import java.text.ParseException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import view.nishat.net.BackingBean.LoginPage;
import view.nishat.net.Helper.CommonUtil;
import view.nishat.net.Helper.Constants;
import view.nishat.net.Helper.PolicyHelper;
import view.nishat.net.PoJo.MonthlyDeductedLeave;

public class LeaveReport {
    private List<LeaveBalanceRec> recordList;
    float annualLeave = 0;
    float casualLeave = 0;
    float shortLeave = 0;
    float lates = 0;

    public LeaveReport() {
      CommonUtil.log("Constructor called");
        recordList = new ArrayList<LeaveBalanceRec>();
        //getMonthlyLeaves();
        CommonUtil.log(annualLeave+" - "+casualLeave+"  - "+shortLeave+" - "+lates);
        
                                      /** ANNUAL LEAVES **/
        LeaveBalanceRec annualRec = new LeaveBalanceRec();
        annualRec.setLeaveType("Annual");
//        double total_available = CommonUtil.round(Float.parseFloat(CommonUtil.getSessionValue(Constants.SESSION_ANNUAL_AVAILABLE).toString()) - Float.parseFloat(CommonUtil.getSessionValue(Constants.SESSION_ANNUAL_AVAILED).toString()),2);
//        float availableAnnual = Float.parseFloat(CommonUtil.getSessionValue(Constants.SESSION_ANNUAL_AVAILABLE).toString());
        float availableAnnual = CommonUtil.getSessionValue(Constants.SESSION_ANNUAL_AVAILABLE)==null?0:Float.parseFloat(CommonUtil.getSessionValue(Constants.SESSION_ANNUAL_AVAILABLE).toString());
        float availedAnnualDB = CommonUtil.getSessionValue(Constants.SESSION_ANNUAL_AVAILED)==null?0:Float.parseFloat(CommonUtil.getSessionValue(Constants.SESSION_ANNUAL_AVAILED).toString());
        float unpaidAnnual = CommonUtil.getSessionValue(Constants.SESSION_ANNUAL_UNPAID)==null?0:Float.parseFloat(CommonUtil.getSessionValue(Constants.SESSION_ANNUAL_UNPAID).toString());;
        float remainingAnnual = 0;
            try
            {
              remainingAnnual = CommonUtil.getLeavesTillDate(availableAnnual,CommonUtil.getSessionValue(Constants.SESSION_USERID)==null?null:CommonUtil.getSessionValue(Constants.SESSION_USERID).toString());
              CommonUtil.log("available annual = "+availableAnnual);
              remainingAnnual = (float)CommonUtil.round(availableAnnual - (availedAnnualDB+annualLeave),2);
              CommonUtil.log("Calculated total annual remaining = "+ availableAnnual);
            }
            catch (ParseException e)
            {
            }
        
        annualRec.setLeaveAvailable(availableAnnual);
        annualRec.setLeaveAvailed(availedAnnualDB+annualLeave);
        CommonUtil.log(availableAnnual+" - "+availableAnnual+" = "+(availableAnnual-availedAnnualDB));
        //annualRec.setUnpaid((availableAnnual-availedAnnualDB)>0?0:(availableAnnual-availedAnnualDB));
        annualRec.setUnpaid(unpaidAnnual);
        annualRec.setRemaining(remainingAnnual);
        
        
                                        /** CASUAL LEAVES **/
        LeaveBalanceRec casualRec = new LeaveBalanceRec();
        casualRec.setLeaveType("Casual");
        float availableCasual = CommonUtil.getSessionValue(Constants.SESSION_CASUAL_AVAILABLE)==null?0:Float.parseFloat(CommonUtil.getSessionValue(Constants.SESSION_CASUAL_AVAILABLE).toString());
        float availedCasualDB = CommonUtil.getSessionValue(Constants.SESSION_CASUAL_AVAILED)==null?0:Float.parseFloat(CommonUtil.getSessionValue(Constants.SESSION_CASUAL_AVAILED).toString());
        float unpaidCasual = CommonUtil.getSessionValue(Constants.SESSION_CASUAL_UNPAID)==null?0:Float.parseFloat(CommonUtil.getSessionValue(Constants.SESSION_CASUAL_UNPAID).toString());
      float remainingCasual = 0;
      
          try
          {
            remainingCasual = CommonUtil.getLeavesTillDate(availableCasual,CommonUtil.getSessionValue(Constants.SESSION_USERID).toString());
            remainingCasual = (float)CommonUtil.round(availableCasual - (availedCasualDB+casualLeave),2);
            CommonUtil.log("Calculated total casual available = "+ availableCasual);
          }
          catch (ParseException e)
          {
          }
        casualRec.setLeaveAvailable(availableCasual);
        casualRec.setLeaveAvailed(availedCasualDB+casualLeave);
        casualRec.setUnpaid(unpaidCasual);
        casualRec.setRemaining(remainingCasual);
        
                                            /** SHORT LEAVES **/
        LeaveBalanceRec shortRec = new LeaveBalanceRec();
        shortRec.setLeaveType("Short");
        float availableShort = CommonUtil.getSessionValue(Constants.SESSION_SHORT_AVAILABLE)==null?0:Float.parseFloat(CommonUtil.getSessionValue(Constants.SESSION_SHORT_AVAILABLE).toString());
        float availedShortDB = CommonUtil.getSessionValue(Constants.SESSION_SHORT_AVAILED)==null?0:Float.parseFloat(CommonUtil.getSessionValue(Constants.SESSION_SHORT_AVAILED).toString());
        float unpaidShort = CommonUtil.getSessionValue(Constants.SESSION_SHORT_UNPAID)==null?0:Float.parseFloat(CommonUtil.getSessionValue(Constants.SESSION_SHORT_UNPAID).toString());
      float remainingShort = 0;
      
          try
          {
            remainingShort = CommonUtil.getLeavesTillDate(availableShort,CommonUtil.getSessionValue(Constants.SESSION_USERID).toString());
            remainingShort = (float)CommonUtil.round(availableShort - (availedShortDB+shortLeave),2);
            CommonUtil.log("Calculated total short available = "+ availableShort);
            unpaidShort = (availedShortDB - availableShort)>0?(availedShortDB - availableShort):0;
          }
          catch (ParseException e)
          {
          }
        
        shortRec.setLeaveAvailable(availableShort);
        shortRec.setLeaveAvailed(availedShortDB+shortLeave);
        shortRec.setUnpaid(unpaidShort);
      shortRec.setRemaining(remainingShort);
        
                                            /** LATES **/
        LeaveBalanceRec lateRec = new LeaveBalanceRec();
        lateRec.setLeaveType("30 Min Lates");
        float availableLates = CommonUtil.getSessionValue(Constants.SESSION_LATES_AVAILABLE)==null?0:Float.parseFloat(CommonUtil.getSessionValue(Constants.SESSION_LATES_AVAILABLE).toString());
        float availedLatesDB = CommonUtil.getSessionValue(Constants.SESSION_LATES_AVAILED)==null?0:Float.parseFloat(CommonUtil.getSessionValue(Constants.SESSION_LATES_AVAILED).toString());
        float unpaidLates = CommonUtil.getSessionValue(Constants.SESSION_LATES_UNPAID)==null?0:Float.parseFloat(CommonUtil.getSessionValue(Constants.SESSION_LATES_UNPAID).toString());
      float remainingLates = 0;
      
      try
      {
        remainingLates = CommonUtil.getLeavesTillDate(availableLates,CommonUtil.getSessionValue(Constants.SESSION_USERID).toString());
        remainingLates = (float)CommonUtil.round(availableLates - (availedLatesDB+lates),2);
        CommonUtil.log("Calculated total short available = "+ availableLates);
      }
      catch (ParseException e)
      {
      }
        lateRec.setLeaveAvailable(availableLates);
        lateRec.setLeaveAvailed(availedLatesDB+lates);
        lateRec.setUnpaid(unpaidLates);
        lateRec.setRemaining(remainingLates);
        recordList.add(casualRec);
        recordList.add(annualRec);
        recordList.add(shortRec);
        recordList.add(lateRec);
    }

    private void getMonthlyLeaves() {
        PolicyHelper ph = new PolicyHelper();
        List<MonthlyDeductedLeave> leaves =
            ph.getMonthlyLeaves(CommonUtil.getMM_YYYY(new Date()),
                                (HubModuleImpl)CommonUtil.getAppModule());
        
        for (MonthlyDeductedLeave leave : leaves) {
            switch (leave.getLeaveType()) {
            case 1:
                casualLeave += 1;
                break;
            case 2:
                annualLeave += 1;
                break;
            case 3:
                casualLeave += .5;
                break;
            case 4:
                annualLeave += .5;
                break;
            case 5:
                shortLeave += 1;
                break;
            case 6:
                lates += 1;
                break;
            }
        }
        

    }

    public void setRecordList(List<LeaveBalanceRec> recordList) {
        this.recordList = recordList;
    }

    public List<LeaveBalanceRec> getRecordList() {
        return recordList;
    }
}
