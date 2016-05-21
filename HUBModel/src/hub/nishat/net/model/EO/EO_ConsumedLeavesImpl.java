package hub.nishat.net.model.EO;

import oracle.jbo.AttributeList;
import oracle.jbo.Key;
import oracle.jbo.domain.Date;
import oracle.jbo.domain.Number;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.EntityDefImpl;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.SequenceImpl;

import view.nishat.net.Helper.CommonUtil;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon May 16 09:44:43 PKT 2016
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class EO_ConsumedLeavesImpl
  extends EntityImpl
{
  private static EntityDefImpl mDefinitionObject;

  /**
   * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
   */
  public enum AttributesEnum
  {
    ConsumedLeavesId
    {
      public Object get(EO_ConsumedLeavesImpl obj)
      {
        return obj.getConsumedLeavesId();
      }

      public void put(EO_ConsumedLeavesImpl obj, Object value)
      {
        obj.setConsumedLeavesId((Number)value);
      }
    }
    ,
    LeaveType
    {
      public Object get(EO_ConsumedLeavesImpl obj)
      {
        return obj.getLeaveType();
      }

      public void put(EO_ConsumedLeavesImpl obj, Object value)
      {
        obj.setLeaveType((Number)value);
      }
    }
    ,
    Cause
    {
      public Object get(EO_ConsumedLeavesImpl obj)
      {
        return obj.getCause();
      }

      public void put(EO_ConsumedLeavesImpl obj, Object value)
      {
        obj.setCause((Number)value);
      }
    }
    ,
    UserId
    {
      public Object get(EO_ConsumedLeavesImpl obj)
      {
        return obj.getUserId();
      }

      public void put(EO_ConsumedLeavesImpl obj, Object value)
      {
        obj.setUserId((Number)value);
      }
    }
    ,
    LeaveDate
    {
      public Object get(EO_ConsumedLeavesImpl obj)
      {
        return obj.getLeaveDate();
      }

      public void put(EO_ConsumedLeavesImpl obj, Object value)
      {
        obj.setLeaveDate((Date)value);
      }
    }
    ,
    JobStatus
    {
      public Object get(EO_ConsumedLeavesImpl obj)
      {
        return obj.getJobStatus();
      }

      public void put(EO_ConsumedLeavesImpl obj, Object value)
      {
        obj.setJobStatus((String)value);
      }
    }
    ;
    private static AttributesEnum[] vals = null;
    private static final int firstIndex = 0;

    public abstract Object get(EO_ConsumedLeavesImpl object);

    public abstract void put(EO_ConsumedLeavesImpl object, Object value);

    public int index()
    {
      return AttributesEnum.firstIndex() + ordinal();
    }

    public static final int firstIndex()
    {
      return firstIndex;
    }

    public static int count()
    {
      return AttributesEnum.firstIndex() + AttributesEnum.staticValues().length;
    }

    public static final AttributesEnum[] staticValues()
    {
      if (vals == null)
      {
        vals = AttributesEnum.values();
      }
      return vals;
    }
  }
  public static final int CONSUMEDLEAVESID = AttributesEnum.ConsumedLeavesId.index();
  public static final int LEAVETYPE = AttributesEnum.LeaveType.index();
  public static final int CAUSE = AttributesEnum.Cause.index();
  public static final int USERID = AttributesEnum.UserId.index();
  public static final int LEAVEDATE = AttributesEnum.LeaveDate.index();
  public static final int JOBSTATUS = AttributesEnum.JobStatus.index();

  /**
   * This is the default constructor (do not remove).
   */
  public EO_ConsumedLeavesImpl()
  {
  }

  /**
   * Gets the attribute value for ConsumedLeavesId, using the alias name ConsumedLeavesId.
   * @return the ConsumedLeavesId
   */
  public Number getConsumedLeavesId()
  {
    return (Number)getAttributeInternal(CONSUMEDLEAVESID);
  }

  /**
   * Sets <code>value</code> as the attribute value for ConsumedLeavesId.
   * @param value value to set the ConsumedLeavesId
   */
  public void setConsumedLeavesId(Number value)
  {
    setAttributeInternal(CONSUMEDLEAVESID, value);
  }

  /**
   * Gets the attribute value for LeaveType, using the alias name LeaveType.
   * @return the LeaveType
   */
  public Number getLeaveType()
  {
    return (Number)getAttributeInternal(LEAVETYPE);
  }

  /**
   * Sets <code>value</code> as the attribute value for LeaveType.
   * @param value value to set the LeaveType
   */
  public void setLeaveType(Number value)
  {
    setAttributeInternal(LEAVETYPE, value);
  }

  /**
   * Gets the attribute value for Cause, using the alias name Cause.
   * @return the Cause
   */
  public Number getCause()
  {
    return (Number)getAttributeInternal(CAUSE);
  }

  /**
   * Sets <code>value</code> as the attribute value for Cause.
   * @param value value to set the Cause
   */
  public void setCause(Number value)
  {
    setAttributeInternal(CAUSE, value);
  }

  /**
   * Gets the attribute value for UserId, using the alias name UserId.
   * @return the UserId
   */
  public Number getUserId()
  {
    return (Number)getAttributeInternal(USERID);
  }

  /**
   * Sets <code>value</code> as the attribute value for UserId.
   * @param value value to set the UserId
   */
  public void setUserId(Number value)
  {
    setAttributeInternal(USERID, value);
  }

  /**
   * Gets the attribute value for LeaveDate, using the alias name LeaveDate.
   * @return the LeaveDate
   */
  public Date getLeaveDate()
  {
    return (Date)getAttributeInternal(LEAVEDATE);
  }

  /**
   * Sets <code>value</code> as the attribute value for LeaveDate.
   * @param value value to set the LeaveDate
   */
  public void setLeaveDate(Date value)
  {
    setAttributeInternal(LEAVEDATE, value);
  }

  /**
   * Gets the attribute value for JobStatus, using the alias name JobStatus.
   * @return the JobStatus
   */
  public String getJobStatus()
  {
    return (String)getAttributeInternal(JOBSTATUS);
  }

  /**
   * Sets <code>value</code> as the attribute value for JobStatus.
   * @param value value to set the JobStatus
   */
  public void setJobStatus(String value)
  {
    setAttributeInternal(JOBSTATUS, value);
  }

  /**
   * getAttrInvokeAccessor: generated method. Do not modify.
   * @param index the index identifying the attribute
   * @param attrDef the attribute

   * @return the attribute value
   * @throws Exception
   */
  protected Object getAttrInvokeAccessor(int index,
                                         AttributeDefImpl attrDef)
    throws Exception
  {
    if ((index >= AttributesEnum.firstIndex()) && (index < AttributesEnum.count()))
    {
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
                                       AttributeDefImpl attrDef)
    throws Exception
  {
    if ((index >= AttributesEnum.firstIndex()) && (index < AttributesEnum.count()))
    {
      AttributesEnum.staticValues()[index - AttributesEnum.firstIndex()].put(this, value);
      return;
    }
    super.setAttrInvokeAccessor(index, value, attrDef);
  }

  /**
   * @param consumedLeavesId key constituent

   * @return a Key object based on given key constituents.
   */
  public static Key createPrimaryKey(Number consumedLeavesId)
  {
    return new Key(new Object[]{consumedLeavesId});
  }

  /**
   * @return the definition object for this instance class.
   */
  public static synchronized EntityDefImpl getDefinitionObject()
  {
    if (mDefinitionObject == null)
    {
      mDefinitionObject = EntityDefImpl.findDefObject("hub.nishat.net.model.EO.EO_ConsumedLeaves");
    }
    return mDefinitionObject;
  }
  
  /**
   * Add attribute defaulting logic in this method.
   * @param attributeList list of attribute names/values to initialize the row
   */
  protected void create(AttributeList attributeList) {
      super.create(attributeList);
      this.setConsumedLeavesId(new SequenceImpl("xx_e_portal_consumed_leave_seq",
                                          getDBTransaction()).getSequenceNumber());
      oracle.jbo.domain.Number cause = new oracle.jbo.domain.Number(7);
      this.setCause(cause);
  }
}
