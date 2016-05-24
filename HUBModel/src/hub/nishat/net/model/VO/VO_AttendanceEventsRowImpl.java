package hub.nishat.net.model.VO;

import oracle.jbo.RowSet;
import oracle.jbo.domain.Date;
import oracle.jbo.domain.Number;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.ViewRowImpl;

import view.nishat.net.Helper.CommonUtil;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon Aug 10 12:51:23 PKT 2015
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class VO_AttendanceEventsRowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        IrregularityId {
            public Object get(VO_AttendanceEventsRowImpl obj) {
                return obj.getIrregularityId();
            }

            public void put(VO_AttendanceEventsRowImpl obj, Object value) {
                obj.setIrregularityId((Number)value);
            }
        }
        ,
        EmpAtdId {
            public Object get(VO_AttendanceEventsRowImpl obj) {
                return obj.getEmpAtdId();
            }

            public void put(VO_AttendanceEventsRowImpl obj, Object value) {
                obj.setEmpAtdId((Number)value);
            }
        }
        ,
        AttendanceDate {
            public Object get(VO_AttendanceEventsRowImpl obj) {
                return obj.getAttendanceDate();
            }

            public void put(VO_AttendanceEventsRowImpl obj, Object value) {
                obj.setAttendanceDate((Date)value);
            }
        }
        ,
        Type {
            public Object get(VO_AttendanceEventsRowImpl obj) {
                return obj.getType();
            }

            public void put(VO_AttendanceEventsRowImpl obj, Object value) {
                obj.setType((String)value);
            }
        }
        ,
        ExceptionRemarks {
            public Object get(VO_AttendanceEventsRowImpl obj) {
                return obj.getExceptionRemarks();
            }

            public void put(VO_AttendanceEventsRowImpl obj, Object value) {
                obj.setExceptionRemarks((String)value);
            }
        }
        ,
        MinutesMissing {
            public Object get(VO_AttendanceEventsRowImpl obj) {
                return obj.getMinutesMissing();
            }

            public void put(VO_AttendanceEventsRowImpl obj, Object value) {
                obj.setMinutesMissing((Number)value);
            }
        }
        ,
        EmpId {
            public Object get(VO_AttendanceEventsRowImpl obj) {
                return obj.getEmpId();
            }

            public void put(VO_AttendanceEventsRowImpl obj, Object value) {
                obj.setEmpId((Number)value);
            }
        }
        ,
        ExceptionApproved {
            public Object get(VO_AttendanceEventsRowImpl obj) {
                return obj.getExceptionApproved();
            }

            public void put(VO_AttendanceEventsRowImpl obj, Object value) {
                obj.setExceptionApproved((String)value);
            }
        }
        ,
        LeaveType {
            public Object get(VO_AttendanceEventsRowImpl obj) {
                return obj.getLeaveType();
            }

            public void put(VO_AttendanceEventsRowImpl obj, Object value) {
                obj.setLeaveType((String)value);
            }
        }
        ,
        MissingTime {
            public Object get(VO_AttendanceEventsRowImpl obj) {
                return obj.getMissingTime();
            }

            public void put(VO_AttendanceEventsRowImpl obj, Object value) {
                obj.setMissingTime((String)value);
            }
        }
        ,
        Leave_typeLov1 {
            public Object get(VO_AttendanceEventsRowImpl obj) {
                return obj.getLeave_typeLov1();
            }

            public void put(VO_AttendanceEventsRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        public abstract Object get(VO_AttendanceEventsRowImpl object);

        public abstract void put(VO_AttendanceEventsRowImpl object,
                                 Object value);

        public int index() {
            return AttributesEnum.firstIndex() + ordinal();
        }

        public static final int firstIndex() {
            return firstIndex;
        }

        public static int count() {
            return AttributesEnum.firstIndex() + AttributesEnum.staticValues().length;
        }

        public static final AttributesEnum[] staticValues() {
            if (vals == null) {
                vals = AttributesEnum.values();
            }
            return vals;
        }
    }

    public static final int IRREGULARITYID = AttributesEnum.IrregularityId.index();
    public static final int EMPATDID = AttributesEnum.EmpAtdId.index();
    public static final int ATTENDANCEDATE = AttributesEnum.AttendanceDate.index();
    public static final int TYPE = AttributesEnum.Type.index();
    public static final int EXCEPTIONREMARKS = AttributesEnum.ExceptionRemarks.index();
    public static final int MINUTESMISSING = AttributesEnum.MinutesMissing.index();
    public static final int EMPID = AttributesEnum.EmpId.index();
    public static final int EXCEPTIONAPPROVED = AttributesEnum.ExceptionApproved.index();
    public static final int LEAVETYPE = AttributesEnum.LeaveType.index();
    public static final int MISSINGTIME = AttributesEnum.MissingTime.index();
    public static final int LEAVE_TYPELOV1 = AttributesEnum.Leave_typeLov1.index();

    /**
     * This is the default constructor (do not remove).
     */
    public VO_AttendanceEventsRowImpl() {
    }

    /**
     * Gets EO_AttendanceEvents entity object.
     * @return the EO_AttendanceEvents
     */
    public EntityImpl getEO_AttendanceEvents() {
        return (EntityImpl)getEntity(0);
    }

    /**
     * Gets the attribute value for IRREGULARITY_ID using the alias name IrregularityId.
     * @return the IRREGULARITY_ID
     */
    public Number getIrregularityId() {
        return (Number) getAttributeInternal(IRREGULARITYID);
    }

    /**
     * Sets <code>value</code> as attribute value for IRREGULARITY_ID using the alias name IrregularityId.
     * @param value value to set the IRREGULARITY_ID
     */
    public void setIrregularityId(Number value) {
        setAttributeInternal(IRREGULARITYID, value);
    }

    /**
     * Gets the attribute value for EMP_ATD_ID using the alias name EmpAtdId.
     * @return the EMP_ATD_ID
     */
    public Number getEmpAtdId() {
        return (Number) getAttributeInternal(EMPATDID);
    }

    /**
     * Sets <code>value</code> as attribute value for EMP_ATD_ID using the alias name EmpAtdId.
     * @param value value to set the EMP_ATD_ID
     */
    public void setEmpAtdId(Number value) {
        setAttributeInternal(EMPATDID, value);
    }

    /**
     * Gets the attribute value for ATTENDANCE_DATE using the alias name AttendanceDate.
     * @return the ATTENDANCE_DATE
     */
    public Date getAttendanceDate() {
        return (Date) getAttributeInternal(ATTENDANCEDATE);
    }

    /**
     * Sets <code>value</code> as attribute value for ATTENDANCE_DATE using the alias name AttendanceDate.
     * @param value value to set the ATTENDANCE_DATE
     */
    public void setAttendanceDate(Date value) {
        setAttributeInternal(ATTENDANCEDATE, value);
    }

    /**
     * Gets the attribute value for TYPE using the alias name Type.
     * @return the TYPE
     */
    public String getType() {
        return (String) getAttributeInternal(TYPE);
    }

    /**
     * Sets <code>value</code> as attribute value for TYPE using the alias name Type.
     * @param value value to set the TYPE
     */
    public void setType(String value) {
        setAttributeInternal(TYPE, value);
    }

    /**
     * Gets the attribute value for EXCEPTION_REMARKS using the alias name ExceptionRemarks.
     * @return the EXCEPTION_REMARKS
     */
    public String getExceptionRemarks() {
        return (String) getAttributeInternal(EXCEPTIONREMARKS);
    }

    /**
     * Sets <code>value</code> as attribute value for EXCEPTION_REMARKS using the alias name ExceptionRemarks.
     * @param value value to set the EXCEPTION_REMARKS
     */
    public void setExceptionRemarks(String value) {
        setAttributeInternal(EXCEPTIONREMARKS, value);
    }

    /**
     * Gets the attribute value for MINUTES_MISSING using the alias name MinutesMissing.
     * @return the MINUTES_MISSING
     */
    public Number getMinutesMissing() {
        return (Number) getAttributeInternal(MINUTESMISSING);
    }

    /**
     * Sets <code>value</code> as attribute value for MINUTES_MISSING using the alias name MinutesMissing.
     * @param value value to set the MINUTES_MISSING
     */
    public void setMinutesMissing(Number value) {
        setAttributeInternal(MINUTESMISSING, value);
    }

    /**
     * Gets the attribute value for EMP_ID using the alias name EmpId.
     * @return the EMP_ID
     */
    public Number getEmpId() {
        return (Number) getAttributeInternal(EMPID);
    }

    /**
     * Sets <code>value</code> as attribute value for EMP_ID using the alias name EmpId.
     * @param value value to set the EMP_ID
     */
    public void setEmpId(Number value) {
        setAttributeInternal(EMPID, value);
    }

    /**
     * Gets the attribute value for EXCEPTION_APPROVED using the alias name ExceptionApproved.
     * @return the EXCEPTION_APPROVED
     */
    public String getExceptionApproved() {
        return (String) getAttributeInternal(EXCEPTIONAPPROVED);
    }

    /**
     * Sets <code>value</code> as attribute value for EXCEPTION_APPROVED using the alias name ExceptionApproved.
     * @param value value to set the EXCEPTION_APPROVED
     */
    public void setExceptionApproved(String value) {
        setAttributeInternal(EXCEPTIONAPPROVED, value);
    }

    /**
     * Gets the attribute value for LEAVE_TYPE using the alias name LeaveType.
     * @return the LEAVE_TYPE
     */
    public String getLeaveType() {
        return (String) getAttributeInternal(LEAVETYPE);
    }

    /**
     * Sets <code>value</code> as attribute value for LEAVE_TYPE using the alias name LeaveType.
     * @param value value to set the LEAVE_TYPE
     */
    public void setLeaveType(String value) {
      CommonUtil.log("leave type = "+value);
      if (value==null)
        setAttributeInternal(LEAVETYPE, "");
      else setAttributeInternal(LEAVETYPE, value);
    }

    /**
     * Gets the attribute value for the calculated attribute MissingTime.
     * @return the MissingTime
     */
    public String getMissingTime() {
        String time = "0:00";
        if (getMinutesMissing() != null) {
            int t = getMinutesMissing().intValue();
                  int hours = t / 60; //since both are ints, you get an int
            int minutes = t % 60;
            time = hours+":"+minutes;
        }
        
        return time;
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute MissingTime.
     * @param value value to set the  MissingTime
     */
    public void setMissingTime(String value) {
        setAttributeInternal(MISSINGTIME, value);
    }

    /**
     * Gets the view accessor <code>RowSet</code> Leave_typeLov1.
     */
    public RowSet getLeave_typeLov1() {
        return (RowSet)getAttributeInternal(LEAVE_TYPELOV1);
    }

    /**
     * getAttrInvokeAccessor: generated method. Do not modify.
     * @param index the index identifying the attribute
     * @param attrDef the attribute

     * @return the attribute value
     * @throws Exception
     */
    protected Object getAttrInvokeAccessor(int index,
                                           AttributeDefImpl attrDef) throws Exception {
        if ((index >= AttributesEnum.firstIndex()) && (index < AttributesEnum.count())) {
            return AttributesEnum.staticValues()[index - AttributesEnum.firstIndex()].get(this);
        }
        return super.getAttrInvokeAccessor(index, attrDef);
    }

    /**
     * setAttrInvokeAccessor: generated method. Do not modify.
     * @param index the index identifying the attribute
     * @param value the value to assign to the attribute
     * @param attrDef the attribute

     * @throws Exception
     */
    protected void setAttrInvokeAccessor(int index, Object value,
                                         AttributeDefImpl attrDef) throws Exception {
        if ((index >= AttributesEnum.firstIndex()) && (index < AttributesEnum.count())) {
            AttributesEnum.staticValues()[index - AttributesEnum.firstIndex()].put(this, value);
            return;
        }
        super.setAttrInvokeAccessor(index, value, attrDef);
    }
}
