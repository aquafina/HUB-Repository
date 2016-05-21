package hub.nishat.net.model.VO.Lov;

import oracle.jbo.RowSet;
import oracle.jbo.domain.Date;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewRowImpl;

import view.nishat.net.Helper.CommonUtil;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Fri May 08 17:08:47 PKT 2015
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class MonthLovRowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        TransactionDate {
            public Object get(MonthLovRowImpl obj) {
                return obj.getTransactionDate();
            }

            public void put(MonthLovRowImpl obj, Object value) {
                obj.setTransactionDate((Date)value);
            }
        }
        ,
        Month {
            public Object get(MonthLovRowImpl obj) {
                return obj.getMonth();
            }

            public void put(MonthLovRowImpl obj, Object value) {
                obj.setMonth((String)value);
            }
        }
        ,
        MonthLov1 {
            public Object get(MonthLovRowImpl obj) {
                return obj.getMonthLov1();
            }

            public void put(MonthLovRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        VO_Months1 {
            public Object get(MonthLovRowImpl obj) {
                return obj.getVO_Months1();
            }

            public void put(MonthLovRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        public abstract Object get(MonthLovRowImpl object);

        public abstract void put(MonthLovRowImpl object, Object value);

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


    public static final int TRANSACTIONDATE = AttributesEnum.TransactionDate.index();
    public static final int MONTH = AttributesEnum.Month.index();
    public static final int MONTHLOV1 = AttributesEnum.MonthLov1.index();
    public static final int VO_MONTHS1 = AttributesEnum.VO_Months1.index();

    /**
     * This is the default constructor (do not remove).
     */
    public MonthLovRowImpl() {
    }


    /**
     * Gets the attribute value for the calculated attribute TransactionDate.
     * @return the TransactionDate
     */
    public Date getTransactionDate() {
        return (Date) getAttributeInternal(TRANSACTIONDATE);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TransactionDate.
     * @param value value to set the  TransactionDate
     */
    public void setTransactionDate(Date value) {
        setAttributeInternal(TRANSACTIONDATE, value);
    }

    /**
     * Gets the attribute value for the calculated attribute Month.
     * @return the Month
     */
    public String getMonth() {
        String month =  (String) getAttributeInternal(MONTH);
        if (month==null || month.trim().length()==0 ){
            //System.out.println(CommonUtil.getCurrent(CommonUtil.CURR_MONTH_NAME).toString()+"<-- month");
            return CommonUtil.getCurrent(CommonUtil.CURR_MONTH_NAME).toString().toUpperCase();
        }
        return month;
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute Month.
     * @param value value to set the  Month
     */
    public void setMonth(String value) {
        setAttributeInternal(MONTH, value);
    }

    /**
     * Gets the view accessor <code>RowSet</code> MonthLov1.
     */
    public RowSet getMonthLov1() {
        return (RowSet)getAttributeInternal(MONTHLOV1);
    }

    /**
     * Gets the view accessor <code>RowSet</code> VO_Months1.
     */
    public RowSet getVO_Months1() {
        return (RowSet)getAttributeInternal(VO_MONTHS1);
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
