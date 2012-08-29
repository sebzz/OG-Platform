/**
 * Copyright (C) 2009 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.financial.security.capfloor;

import java.util.Map;

import javax.time.calendar.ZonedDateTime;

import org.joda.beans.BeanBuilder;
import org.joda.beans.BeanDefinition;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import com.opengamma.financial.convention.daycount.DayCount;
import com.opengamma.financial.convention.frequency.Frequency;
import com.opengamma.financial.security.FinancialSecurity;
import com.opengamma.financial.security.FinancialSecurityVisitor;
import com.opengamma.id.ExternalId;
import com.opengamma.util.money.Currency;

/**
 * A security for a cap/floor CMS spread.
 */
@BeanDefinition
public class CapFloorCMSSpreadSecurity extends FinancialSecurity {

  /** Serialization version. */
  private static final long serialVersionUID = 1L;

  /**
   * The security type.
   */
  public static final String SECURITY_TYPE = "CAP-FLOOR CMS SPREAD";

  /**
   * The start date.
   */
  @PropertyDefinition(validate = "notNull")
  private ZonedDateTime _startDate;
  /**
   * The maturity date.
   */
  @PropertyDefinition(validate = "notNull")
  private ZonedDateTime _maturityDate;
  /**
   * The notional.
   */
  @PropertyDefinition
  private double _notional;
  /**
   * The long identifier.
   */
  @PropertyDefinition(validate = "notNull")
  private ExternalId _longId;
  /**
   * The short identifier.
   */
  @PropertyDefinition(validate = "notNull")
  private ExternalId _shortId;
  /**
   * The strike.
   */
  @PropertyDefinition
  private double _strike;
  /**
   * The frequency.
   */
  @PropertyDefinition(validate = "notNull")
  private Frequency _frequency;
  /**
   * The currency.
   */
  @PropertyDefinition(validate = "notNull")
  private Currency _currency;
  /**
   * The day count.
   */
  @PropertyDefinition(validate = "notNull")
  private DayCount _dayCount;
  /**
   * The payer flag.
   */
  @PropertyDefinition
  private boolean _payer;
  /**
   * The cap flag.
   */
  @PropertyDefinition
  private boolean _cap;

  CapFloorCMSSpreadSecurity() { //For builder
    super();
  }

  public CapFloorCMSSpreadSecurity(ZonedDateTime startDate, ZonedDateTime maturityDate, double notional, ExternalId longIdentifier,
      ExternalId shortIdentifier, double strike, Frequency frequency, Currency currency, DayCount dayCount, boolean payer, boolean cap) {
    super(SECURITY_TYPE);
    setStartDate(startDate);
    setMaturityDate(maturityDate);
    setNotional(notional);
    setLongId(longIdentifier);
    setShortId(shortIdentifier);
    setStrike(strike);
    setFrequency(frequency);
    setCurrency(currency);
    setDayCount(dayCount);
    setPayer(payer);
    setCap(cap);
  }

  //-------------------------------------------------------------------------
  @Override
  public final <T> T accept(FinancialSecurityVisitor<T> visitor) {
    return visitor.visitCapFloorCMSSpreadSecurity(this);
  }


  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code CapFloorCMSSpreadSecurity}.
   * @return the meta-bean, not null
   */
  public static CapFloorCMSSpreadSecurity.Meta meta() {
    return CapFloorCMSSpreadSecurity.Meta.INSTANCE;
  }
  static {
    JodaBeanUtils.registerMetaBean(CapFloorCMSSpreadSecurity.Meta.INSTANCE);
  }

  @Override
  public CapFloorCMSSpreadSecurity.Meta metaBean() {
    return CapFloorCMSSpreadSecurity.Meta.INSTANCE;
  }

  @Override
  protected Object propertyGet(String propertyName, boolean quiet) {
    switch (propertyName.hashCode()) {
      case -2129778896:  // startDate
        return getStartDate();
      case -414641441:  // maturityDate
        return getMaturityDate();
      case 1585636160:  // notional
        return getNotional();
      case -1097129801:  // longId
        return getLongId();
      case 2067160759:  // shortId
        return getShortId();
      case -891985998:  // strike
        return getStrike();
      case -70023844:  // frequency
        return getFrequency();
      case 575402001:  // currency
        return getCurrency();
      case 1905311443:  // dayCount
        return getDayCount();
      case 106443605:  // payer
        return isPayer();
      case 98258:  // cap
        return isCap();
    }
    return super.propertyGet(propertyName, quiet);
  }

  @Override
  protected void propertySet(String propertyName, Object newValue, boolean quiet) {
    switch (propertyName.hashCode()) {
      case -2129778896:  // startDate
        setStartDate((ZonedDateTime) newValue);
        return;
      case -414641441:  // maturityDate
        setMaturityDate((ZonedDateTime) newValue);
        return;
      case 1585636160:  // notional
        setNotional((Double) newValue);
        return;
      case -1097129801:  // longId
        setLongId((ExternalId) newValue);
        return;
      case 2067160759:  // shortId
        setShortId((ExternalId) newValue);
        return;
      case -891985998:  // strike
        setStrike((Double) newValue);
        return;
      case -70023844:  // frequency
        setFrequency((Frequency) newValue);
        return;
      case 575402001:  // currency
        setCurrency((Currency) newValue);
        return;
      case 1905311443:  // dayCount
        setDayCount((DayCount) newValue);
        return;
      case 106443605:  // payer
        setPayer((Boolean) newValue);
        return;
      case 98258:  // cap
        setCap((Boolean) newValue);
        return;
    }
    super.propertySet(propertyName, newValue, quiet);
  }

  @Override
  protected void validate() {
    JodaBeanUtils.notNull(_startDate, "startDate");
    JodaBeanUtils.notNull(_maturityDate, "maturityDate");
    JodaBeanUtils.notNull(_longId, "longId");
    JodaBeanUtils.notNull(_shortId, "shortId");
    JodaBeanUtils.notNull(_frequency, "frequency");
    JodaBeanUtils.notNull(_currency, "currency");
    JodaBeanUtils.notNull(_dayCount, "dayCount");
    super.validate();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      CapFloorCMSSpreadSecurity other = (CapFloorCMSSpreadSecurity) obj;
      return JodaBeanUtils.equal(getStartDate(), other.getStartDate()) &&
          JodaBeanUtils.equal(getMaturityDate(), other.getMaturityDate()) &&
          JodaBeanUtils.equal(getNotional(), other.getNotional()) &&
          JodaBeanUtils.equal(getLongId(), other.getLongId()) &&
          JodaBeanUtils.equal(getShortId(), other.getShortId()) &&
          JodaBeanUtils.equal(getStrike(), other.getStrike()) &&
          JodaBeanUtils.equal(getFrequency(), other.getFrequency()) &&
          JodaBeanUtils.equal(getCurrency(), other.getCurrency()) &&
          JodaBeanUtils.equal(getDayCount(), other.getDayCount()) &&
          JodaBeanUtils.equal(isPayer(), other.isPayer()) &&
          JodaBeanUtils.equal(isCap(), other.isCap()) &&
          super.equals(obj);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash += hash * 31 + JodaBeanUtils.hashCode(getStartDate());
    hash += hash * 31 + JodaBeanUtils.hashCode(getMaturityDate());
    hash += hash * 31 + JodaBeanUtils.hashCode(getNotional());
    hash += hash * 31 + JodaBeanUtils.hashCode(getLongId());
    hash += hash * 31 + JodaBeanUtils.hashCode(getShortId());
    hash += hash * 31 + JodaBeanUtils.hashCode(getStrike());
    hash += hash * 31 + JodaBeanUtils.hashCode(getFrequency());
    hash += hash * 31 + JodaBeanUtils.hashCode(getCurrency());
    hash += hash * 31 + JodaBeanUtils.hashCode(getDayCount());
    hash += hash * 31 + JodaBeanUtils.hashCode(isPayer());
    hash += hash * 31 + JodaBeanUtils.hashCode(isCap());
    return hash ^ super.hashCode();
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the start date.
   * @return the value of the property, not null
   */
  public ZonedDateTime getStartDate() {
    return _startDate;
  }

  /**
   * Sets the start date.
   * @param startDate  the new value of the property, not null
   */
  public void setStartDate(ZonedDateTime startDate) {
    JodaBeanUtils.notNull(startDate, "startDate");
    this._startDate = startDate;
  }

  /**
   * Gets the the {@code startDate} property.
   * @return the property, not null
   */
  public final Property<ZonedDateTime> startDate() {
    return metaBean().startDate().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the maturity date.
   * @return the value of the property, not null
   */
  public ZonedDateTime getMaturityDate() {
    return _maturityDate;
  }

  /**
   * Sets the maturity date.
   * @param maturityDate  the new value of the property, not null
   */
  public void setMaturityDate(ZonedDateTime maturityDate) {
    JodaBeanUtils.notNull(maturityDate, "maturityDate");
    this._maturityDate = maturityDate;
  }

  /**
   * Gets the the {@code maturityDate} property.
   * @return the property, not null
   */
  public final Property<ZonedDateTime> maturityDate() {
    return metaBean().maturityDate().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the notional.
   * @return the value of the property
   */
  public double getNotional() {
    return _notional;
  }

  /**
   * Sets the notional.
   * @param notional  the new value of the property
   */
  public void setNotional(double notional) {
    this._notional = notional;
  }

  /**
   * Gets the the {@code notional} property.
   * @return the property, not null
   */
  public final Property<Double> notional() {
    return metaBean().notional().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the long identifier.
   * @return the value of the property, not null
   */
  public ExternalId getLongId() {
    return _longId;
  }

  /**
   * Sets the long identifier.
   * @param longId  the new value of the property, not null
   */
  public void setLongId(ExternalId longId) {
    JodaBeanUtils.notNull(longId, "longId");
    this._longId = longId;
  }

  /**
   * Gets the the {@code longId} property.
   * @return the property, not null
   */
  public final Property<ExternalId> longId() {
    return metaBean().longId().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the short identifier.
   * @return the value of the property, not null
   */
  public ExternalId getShortId() {
    return _shortId;
  }

  /**
   * Sets the short identifier.
   * @param shortId  the new value of the property, not null
   */
  public void setShortId(ExternalId shortId) {
    JodaBeanUtils.notNull(shortId, "shortId");
    this._shortId = shortId;
  }

  /**
   * Gets the the {@code shortId} property.
   * @return the property, not null
   */
  public final Property<ExternalId> shortId() {
    return metaBean().shortId().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the strike.
   * @return the value of the property
   */
  public double getStrike() {
    return _strike;
  }

  /**
   * Sets the strike.
   * @param strike  the new value of the property
   */
  public void setStrike(double strike) {
    this._strike = strike;
  }

  /**
   * Gets the the {@code strike} property.
   * @return the property, not null
   */
  public final Property<Double> strike() {
    return metaBean().strike().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the frequency.
   * @return the value of the property, not null
   */
  public Frequency getFrequency() {
    return _frequency;
  }

  /**
   * Sets the frequency.
   * @param frequency  the new value of the property, not null
   */
  public void setFrequency(Frequency frequency) {
    JodaBeanUtils.notNull(frequency, "frequency");
    this._frequency = frequency;
  }

  /**
   * Gets the the {@code frequency} property.
   * @return the property, not null
   */
  public final Property<Frequency> frequency() {
    return metaBean().frequency().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the currency.
   * @return the value of the property, not null
   */
  public Currency getCurrency() {
    return _currency;
  }

  /**
   * Sets the currency.
   * @param currency  the new value of the property, not null
   */
  public void setCurrency(Currency currency) {
    JodaBeanUtils.notNull(currency, "currency");
    this._currency = currency;
  }

  /**
   * Gets the the {@code currency} property.
   * @return the property, not null
   */
  public final Property<Currency> currency() {
    return metaBean().currency().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the day count.
   * @return the value of the property, not null
   */
  public DayCount getDayCount() {
    return _dayCount;
  }

  /**
   * Sets the day count.
   * @param dayCount  the new value of the property, not null
   */
  public void setDayCount(DayCount dayCount) {
    JodaBeanUtils.notNull(dayCount, "dayCount");
    this._dayCount = dayCount;
  }

  /**
   * Gets the the {@code dayCount} property.
   * @return the property, not null
   */
  public final Property<DayCount> dayCount() {
    return metaBean().dayCount().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the payer flag.
   * @return the value of the property
   */
  public boolean isPayer() {
    return _payer;
  }

  /**
   * Sets the payer flag.
   * @param payer  the new value of the property
   */
  public void setPayer(boolean payer) {
    this._payer = payer;
  }

  /**
   * Gets the the {@code payer} property.
   * @return the property, not null
   */
  public final Property<Boolean> payer() {
    return metaBean().payer().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the cap flag.
   * @return the value of the property
   */
  public boolean isCap() {
    return _cap;
  }

  /**
   * Sets the cap flag.
   * @param cap  the new value of the property
   */
  public void setCap(boolean cap) {
    this._cap = cap;
  }

  /**
   * Gets the the {@code cap} property.
   * @return the property, not null
   */
  public final Property<Boolean> cap() {
    return metaBean().cap().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code CapFloorCMSSpreadSecurity}.
   */
  public static class Meta extends FinancialSecurity.Meta {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code startDate} property.
     */
    private final MetaProperty<ZonedDateTime> _startDate = DirectMetaProperty.ofReadWrite(
        this, "startDate", CapFloorCMSSpreadSecurity.class, ZonedDateTime.class);
    /**
     * The meta-property for the {@code maturityDate} property.
     */
    private final MetaProperty<ZonedDateTime> _maturityDate = DirectMetaProperty.ofReadWrite(
        this, "maturityDate", CapFloorCMSSpreadSecurity.class, ZonedDateTime.class);
    /**
     * The meta-property for the {@code notional} property.
     */
    private final MetaProperty<Double> _notional = DirectMetaProperty.ofReadWrite(
        this, "notional", CapFloorCMSSpreadSecurity.class, Double.TYPE);
    /**
     * The meta-property for the {@code longId} property.
     */
    private final MetaProperty<ExternalId> _longId = DirectMetaProperty.ofReadWrite(
        this, "longId", CapFloorCMSSpreadSecurity.class, ExternalId.class);
    /**
     * The meta-property for the {@code shortId} property.
     */
    private final MetaProperty<ExternalId> _shortId = DirectMetaProperty.ofReadWrite(
        this, "shortId", CapFloorCMSSpreadSecurity.class, ExternalId.class);
    /**
     * The meta-property for the {@code strike} property.
     */
    private final MetaProperty<Double> _strike = DirectMetaProperty.ofReadWrite(
        this, "strike", CapFloorCMSSpreadSecurity.class, Double.TYPE);
    /**
     * The meta-property for the {@code frequency} property.
     */
    private final MetaProperty<Frequency> _frequency = DirectMetaProperty.ofReadWrite(
        this, "frequency", CapFloorCMSSpreadSecurity.class, Frequency.class);
    /**
     * The meta-property for the {@code currency} property.
     */
    private final MetaProperty<Currency> _currency = DirectMetaProperty.ofReadWrite(
        this, "currency", CapFloorCMSSpreadSecurity.class, Currency.class);
    /**
     * The meta-property for the {@code dayCount} property.
     */
    private final MetaProperty<DayCount> _dayCount = DirectMetaProperty.ofReadWrite(
        this, "dayCount", CapFloorCMSSpreadSecurity.class, DayCount.class);
    /**
     * The meta-property for the {@code payer} property.
     */
    private final MetaProperty<Boolean> _payer = DirectMetaProperty.ofReadWrite(
        this, "payer", CapFloorCMSSpreadSecurity.class, Boolean.TYPE);
    /**
     * The meta-property for the {@code cap} property.
     */
    private final MetaProperty<Boolean> _cap = DirectMetaProperty.ofReadWrite(
        this, "cap", CapFloorCMSSpreadSecurity.class, Boolean.TYPE);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> _metaPropertyMap$ = new DirectMetaPropertyMap(
      this, (DirectMetaPropertyMap) super.metaPropertyMap(),
        "startDate",
        "maturityDate",
        "notional",
        "longId",
        "shortId",
        "strike",
        "frequency",
        "currency",
        "dayCount",
        "payer",
        "cap");

    /**
     * Restricted constructor.
     */
    protected Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case -2129778896:  // startDate
          return _startDate;
        case -414641441:  // maturityDate
          return _maturityDate;
        case 1585636160:  // notional
          return _notional;
        case -1097129801:  // longId
          return _longId;
        case 2067160759:  // shortId
          return _shortId;
        case -891985998:  // strike
          return _strike;
        case -70023844:  // frequency
          return _frequency;
        case 575402001:  // currency
          return _currency;
        case 1905311443:  // dayCount
          return _dayCount;
        case 106443605:  // payer
          return _payer;
        case 98258:  // cap
          return _cap;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends CapFloorCMSSpreadSecurity> builder() {
      return new DirectBeanBuilder<CapFloorCMSSpreadSecurity>(new CapFloorCMSSpreadSecurity());
    }

    @Override
    public Class<? extends CapFloorCMSSpreadSecurity> beanType() {
      return CapFloorCMSSpreadSecurity.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return _metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code startDate} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<ZonedDateTime> startDate() {
      return _startDate;
    }

    /**
     * The meta-property for the {@code maturityDate} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<ZonedDateTime> maturityDate() {
      return _maturityDate;
    }

    /**
     * The meta-property for the {@code notional} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Double> notional() {
      return _notional;
    }

    /**
     * The meta-property for the {@code longId} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<ExternalId> longId() {
      return _longId;
    }

    /**
     * The meta-property for the {@code shortId} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<ExternalId> shortId() {
      return _shortId;
    }

    /**
     * The meta-property for the {@code strike} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Double> strike() {
      return _strike;
    }

    /**
     * The meta-property for the {@code frequency} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Frequency> frequency() {
      return _frequency;
    }

    /**
     * The meta-property for the {@code currency} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Currency> currency() {
      return _currency;
    }

    /**
     * The meta-property for the {@code dayCount} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<DayCount> dayCount() {
      return _dayCount;
    }

    /**
     * The meta-property for the {@code payer} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Boolean> payer() {
      return _payer;
    }

    /**
     * The meta-property for the {@code cap} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Boolean> cap() {
      return _cap;
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
