/**
 * Copyright (C) 2009 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.master.exchange;

import java.util.Map;

import javax.time.calendar.LocalDate;
import javax.time.calendar.LocalTime;

import org.joda.beans.BeanBuilder;
import org.joda.beans.BeanDefinition;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectBean;
import org.joda.beans.impl.direct.DirectBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import com.opengamma.util.PublicSPI;

/**
 * Detailed information about an exchange, including when it opens and closes.
 * <p>
 * Some of this information is relatively unstructured, consisting mainly of strings.
 * Over time, it is expected that it will be interpreted more fully.
 */
@PublicSPI
@BeanDefinition
public class ManageableExchangeDetail extends DirectBean {

  /**
   * The exchange product group, such as Certificates, Derivatives, Debt Market.
   */
  @PropertyDefinition
  private String _productGroup;
  /**
   * The exchange product, such as Transferable, Treasury Bond, Options on Wheat.
   */
  @PropertyDefinition
  private String _productName;
  /**
   * The exchange product type, such as Derivative, Cash, OTC/Block.
   */
  @PropertyDefinition
  private String _productType;
  /**
   * The exchange product code as identified by the exchange.
   */
  @PropertyDefinition
  private String _productCode;
  /**
   * The start date of the period of the trading hours.
   */
  @PropertyDefinition
  private LocalDate _calendarStart;
  /**
   * The inclusive end date of the period of the trading hours.
   */
  @PropertyDefinition
  private LocalDate _calendarEnd;
  /**
   * The start day of week, which may be descriptive such as Last trading day.
   */
  @PropertyDefinition
  private String _dayStart;
  /**
   * The day range type, blank (one day only), through or to.
   */
  @PropertyDefinition
  private String _dayRangeType;
  /**
   * The inclusive end day of week.
   */
  @PropertyDefinition
  private String _dayEnd;
  /**
   * The phase name of trading.
   */
  @PropertyDefinition
  private String _phaseName;
  /**
   * The phase type of trading, such as Block registration, Pre-opening, Trading, Closing, Electronic Trading, Night trading.
   */
  @PropertyDefinition
  private String _phaseType;
  /**
   * The time the phase starts.
   */
  @PropertyDefinition
  private LocalTime _phaseStart;
  /**
   * The time the phase ends.
   */
  @PropertyDefinition
  private LocalTime _phaseEnd;
  /**
   * The earliest start time.
   */
  @PropertyDefinition
  private LocalTime _randomStartMin;
  /**
   * The latest start time.
   */
  @PropertyDefinition
  private LocalTime _randomStartMax;
  /**
   * The earliest end time.
   */
  @PropertyDefinition
  private LocalTime _randomEndMin;
  /**
   * The latest end time.
   */
  @PropertyDefinition
  private LocalTime _randomEndMax;
  /**
   * The date when the information was last checked.
   */
  @PropertyDefinition
  private LocalDate _lastConfirmed;
  /**
   * The free text notes.
   */
  @PropertyDefinition
  private String _notes;

  /**
   * Creates an instance.
   */
  public ManageableExchangeDetail() {
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code ManageableExchangeDetail}.
   * @return the meta-bean, not null
   */
  public static ManageableExchangeDetail.Meta meta() {
    return ManageableExchangeDetail.Meta.INSTANCE;
  }
  static {
    JodaBeanUtils.registerMetaBean(ManageableExchangeDetail.Meta.INSTANCE);
  }

  @Override
  public ManageableExchangeDetail.Meta metaBean() {
    return ManageableExchangeDetail.Meta.INSTANCE;
  }

  @Override
  protected Object propertyGet(String propertyName, boolean quiet) {
    switch (propertyName.hashCode()) {
      case 992343760:  // productGroup
        return getProductGroup();
      case -1491817446:  // productName
        return getProductName();
      case -1491615543:  // productType
        return getProductType();
      case -1492131972:  // productCode
        return getProductCode();
      case 1952067524:  // calendarStart
        return getCalendarStart();
      case 404251837:  // calendarEnd
        return getCalendarEnd();
      case 1920217638:  // dayStart
        return getDayStart();
      case 761807323:  // dayRangeType
        return getDayRangeType();
      case -1338796129:  // dayEnd
        return getDayEnd();
      case -426196314:  // phaseName
        return getPhaseName();
      case -425994411:  // phaseType
        return getPhaseType();
      case -322011225:  // phaseStart
        return getPhaseStart();
      case -1676324512:  // phaseEnd
        return getPhaseEnd();
      case -882488205:  // randomStartMin
        return getRandomStartMin();
      case -882488443:  // randomStartMax
        return getRandomStartMax();
      case -961403366:  // randomEndMin
        return getRandomEndMin();
      case -961403604:  // randomEndMax
        return getRandomEndMax();
      case 1696487785:  // lastConfirmed
        return getLastConfirmed();
      case 105008833:  // notes
        return getNotes();
    }
    return super.propertyGet(propertyName, quiet);
  }

  @Override
  protected void propertySet(String propertyName, Object newValue, boolean quiet) {
    switch (propertyName.hashCode()) {
      case 992343760:  // productGroup
        setProductGroup((String) newValue);
        return;
      case -1491817446:  // productName
        setProductName((String) newValue);
        return;
      case -1491615543:  // productType
        setProductType((String) newValue);
        return;
      case -1492131972:  // productCode
        setProductCode((String) newValue);
        return;
      case 1952067524:  // calendarStart
        setCalendarStart((LocalDate) newValue);
        return;
      case 404251837:  // calendarEnd
        setCalendarEnd((LocalDate) newValue);
        return;
      case 1920217638:  // dayStart
        setDayStart((String) newValue);
        return;
      case 761807323:  // dayRangeType
        setDayRangeType((String) newValue);
        return;
      case -1338796129:  // dayEnd
        setDayEnd((String) newValue);
        return;
      case -426196314:  // phaseName
        setPhaseName((String) newValue);
        return;
      case -425994411:  // phaseType
        setPhaseType((String) newValue);
        return;
      case -322011225:  // phaseStart
        setPhaseStart((LocalTime) newValue);
        return;
      case -1676324512:  // phaseEnd
        setPhaseEnd((LocalTime) newValue);
        return;
      case -882488205:  // randomStartMin
        setRandomStartMin((LocalTime) newValue);
        return;
      case -882488443:  // randomStartMax
        setRandomStartMax((LocalTime) newValue);
        return;
      case -961403366:  // randomEndMin
        setRandomEndMin((LocalTime) newValue);
        return;
      case -961403604:  // randomEndMax
        setRandomEndMax((LocalTime) newValue);
        return;
      case 1696487785:  // lastConfirmed
        setLastConfirmed((LocalDate) newValue);
        return;
      case 105008833:  // notes
        setNotes((String) newValue);
        return;
    }
    super.propertySet(propertyName, newValue, quiet);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      ManageableExchangeDetail other = (ManageableExchangeDetail) obj;
      return JodaBeanUtils.equal(getProductGroup(), other.getProductGroup()) &&
          JodaBeanUtils.equal(getProductName(), other.getProductName()) &&
          JodaBeanUtils.equal(getProductType(), other.getProductType()) &&
          JodaBeanUtils.equal(getProductCode(), other.getProductCode()) &&
          JodaBeanUtils.equal(getCalendarStart(), other.getCalendarStart()) &&
          JodaBeanUtils.equal(getCalendarEnd(), other.getCalendarEnd()) &&
          JodaBeanUtils.equal(getDayStart(), other.getDayStart()) &&
          JodaBeanUtils.equal(getDayRangeType(), other.getDayRangeType()) &&
          JodaBeanUtils.equal(getDayEnd(), other.getDayEnd()) &&
          JodaBeanUtils.equal(getPhaseName(), other.getPhaseName()) &&
          JodaBeanUtils.equal(getPhaseType(), other.getPhaseType()) &&
          JodaBeanUtils.equal(getPhaseStart(), other.getPhaseStart()) &&
          JodaBeanUtils.equal(getPhaseEnd(), other.getPhaseEnd()) &&
          JodaBeanUtils.equal(getRandomStartMin(), other.getRandomStartMin()) &&
          JodaBeanUtils.equal(getRandomStartMax(), other.getRandomStartMax()) &&
          JodaBeanUtils.equal(getRandomEndMin(), other.getRandomEndMin()) &&
          JodaBeanUtils.equal(getRandomEndMax(), other.getRandomEndMax()) &&
          JodaBeanUtils.equal(getLastConfirmed(), other.getLastConfirmed()) &&
          JodaBeanUtils.equal(getNotes(), other.getNotes());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash += hash * 31 + JodaBeanUtils.hashCode(getProductGroup());
    hash += hash * 31 + JodaBeanUtils.hashCode(getProductName());
    hash += hash * 31 + JodaBeanUtils.hashCode(getProductType());
    hash += hash * 31 + JodaBeanUtils.hashCode(getProductCode());
    hash += hash * 31 + JodaBeanUtils.hashCode(getCalendarStart());
    hash += hash * 31 + JodaBeanUtils.hashCode(getCalendarEnd());
    hash += hash * 31 + JodaBeanUtils.hashCode(getDayStart());
    hash += hash * 31 + JodaBeanUtils.hashCode(getDayRangeType());
    hash += hash * 31 + JodaBeanUtils.hashCode(getDayEnd());
    hash += hash * 31 + JodaBeanUtils.hashCode(getPhaseName());
    hash += hash * 31 + JodaBeanUtils.hashCode(getPhaseType());
    hash += hash * 31 + JodaBeanUtils.hashCode(getPhaseStart());
    hash += hash * 31 + JodaBeanUtils.hashCode(getPhaseEnd());
    hash += hash * 31 + JodaBeanUtils.hashCode(getRandomStartMin());
    hash += hash * 31 + JodaBeanUtils.hashCode(getRandomStartMax());
    hash += hash * 31 + JodaBeanUtils.hashCode(getRandomEndMin());
    hash += hash * 31 + JodaBeanUtils.hashCode(getRandomEndMax());
    hash += hash * 31 + JodaBeanUtils.hashCode(getLastConfirmed());
    hash += hash * 31 + JodaBeanUtils.hashCode(getNotes());
    return hash;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the exchange product group, such as Certificates, Derivatives, Debt Market.
   * @return the value of the property
   */
  public String getProductGroup() {
    return _productGroup;
  }

  /**
   * Sets the exchange product group, such as Certificates, Derivatives, Debt Market.
   * @param productGroup  the new value of the property
   */
  public void setProductGroup(String productGroup) {
    this._productGroup = productGroup;
  }

  /**
   * Gets the the {@code productGroup} property.
   * @return the property, not null
   */
  public final Property<String> productGroup() {
    return metaBean().productGroup().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the exchange product, such as Transferable, Treasury Bond, Options on Wheat.
   * @return the value of the property
   */
  public String getProductName() {
    return _productName;
  }

  /**
   * Sets the exchange product, such as Transferable, Treasury Bond, Options on Wheat.
   * @param productName  the new value of the property
   */
  public void setProductName(String productName) {
    this._productName = productName;
  }

  /**
   * Gets the the {@code productName} property.
   * @return the property, not null
   */
  public final Property<String> productName() {
    return metaBean().productName().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the exchange product type, such as Derivative, Cash, OTC/Block.
   * @return the value of the property
   */
  public String getProductType() {
    return _productType;
  }

  /**
   * Sets the exchange product type, such as Derivative, Cash, OTC/Block.
   * @param productType  the new value of the property
   */
  public void setProductType(String productType) {
    this._productType = productType;
  }

  /**
   * Gets the the {@code productType} property.
   * @return the property, not null
   */
  public final Property<String> productType() {
    return metaBean().productType().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the exchange product code as identified by the exchange.
   * @return the value of the property
   */
  public String getProductCode() {
    return _productCode;
  }

  /**
   * Sets the exchange product code as identified by the exchange.
   * @param productCode  the new value of the property
   */
  public void setProductCode(String productCode) {
    this._productCode = productCode;
  }

  /**
   * Gets the the {@code productCode} property.
   * @return the property, not null
   */
  public final Property<String> productCode() {
    return metaBean().productCode().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the start date of the period of the trading hours.
   * @return the value of the property
   */
  public LocalDate getCalendarStart() {
    return _calendarStart;
  }

  /**
   * Sets the start date of the period of the trading hours.
   * @param calendarStart  the new value of the property
   */
  public void setCalendarStart(LocalDate calendarStart) {
    this._calendarStart = calendarStart;
  }

  /**
   * Gets the the {@code calendarStart} property.
   * @return the property, not null
   */
  public final Property<LocalDate> calendarStart() {
    return metaBean().calendarStart().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the inclusive end date of the period of the trading hours.
   * @return the value of the property
   */
  public LocalDate getCalendarEnd() {
    return _calendarEnd;
  }

  /**
   * Sets the inclusive end date of the period of the trading hours.
   * @param calendarEnd  the new value of the property
   */
  public void setCalendarEnd(LocalDate calendarEnd) {
    this._calendarEnd = calendarEnd;
  }

  /**
   * Gets the the {@code calendarEnd} property.
   * @return the property, not null
   */
  public final Property<LocalDate> calendarEnd() {
    return metaBean().calendarEnd().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the start day of week, which may be descriptive such as Last trading day.
   * @return the value of the property
   */
  public String getDayStart() {
    return _dayStart;
  }

  /**
   * Sets the start day of week, which may be descriptive such as Last trading day.
   * @param dayStart  the new value of the property
   */
  public void setDayStart(String dayStart) {
    this._dayStart = dayStart;
  }

  /**
   * Gets the the {@code dayStart} property.
   * @return the property, not null
   */
  public final Property<String> dayStart() {
    return metaBean().dayStart().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the day range type, blank (one day only), through or to.
   * @return the value of the property
   */
  public String getDayRangeType() {
    return _dayRangeType;
  }

  /**
   * Sets the day range type, blank (one day only), through or to.
   * @param dayRangeType  the new value of the property
   */
  public void setDayRangeType(String dayRangeType) {
    this._dayRangeType = dayRangeType;
  }

  /**
   * Gets the the {@code dayRangeType} property.
   * @return the property, not null
   */
  public final Property<String> dayRangeType() {
    return metaBean().dayRangeType().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the inclusive end day of week.
   * @return the value of the property
   */
  public String getDayEnd() {
    return _dayEnd;
  }

  /**
   * Sets the inclusive end day of week.
   * @param dayEnd  the new value of the property
   */
  public void setDayEnd(String dayEnd) {
    this._dayEnd = dayEnd;
  }

  /**
   * Gets the the {@code dayEnd} property.
   * @return the property, not null
   */
  public final Property<String> dayEnd() {
    return metaBean().dayEnd().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the phase name of trading.
   * @return the value of the property
   */
  public String getPhaseName() {
    return _phaseName;
  }

  /**
   * Sets the phase name of trading.
   * @param phaseName  the new value of the property
   */
  public void setPhaseName(String phaseName) {
    this._phaseName = phaseName;
  }

  /**
   * Gets the the {@code phaseName} property.
   * @return the property, not null
   */
  public final Property<String> phaseName() {
    return metaBean().phaseName().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the phase type of trading, such as Block registration, Pre-opening, Trading, Closing, Electronic Trading, Night trading.
   * @return the value of the property
   */
  public String getPhaseType() {
    return _phaseType;
  }

  /**
   * Sets the phase type of trading, such as Block registration, Pre-opening, Trading, Closing, Electronic Trading, Night trading.
   * @param phaseType  the new value of the property
   */
  public void setPhaseType(String phaseType) {
    this._phaseType = phaseType;
  }

  /**
   * Gets the the {@code phaseType} property.
   * @return the property, not null
   */
  public final Property<String> phaseType() {
    return metaBean().phaseType().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the time the phase starts.
   * @return the value of the property
   */
  public LocalTime getPhaseStart() {
    return _phaseStart;
  }

  /**
   * Sets the time the phase starts.
   * @param phaseStart  the new value of the property
   */
  public void setPhaseStart(LocalTime phaseStart) {
    this._phaseStart = phaseStart;
  }

  /**
   * Gets the the {@code phaseStart} property.
   * @return the property, not null
   */
  public final Property<LocalTime> phaseStart() {
    return metaBean().phaseStart().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the time the phase ends.
   * @return the value of the property
   */
  public LocalTime getPhaseEnd() {
    return _phaseEnd;
  }

  /**
   * Sets the time the phase ends.
   * @param phaseEnd  the new value of the property
   */
  public void setPhaseEnd(LocalTime phaseEnd) {
    this._phaseEnd = phaseEnd;
  }

  /**
   * Gets the the {@code phaseEnd} property.
   * @return the property, not null
   */
  public final Property<LocalTime> phaseEnd() {
    return metaBean().phaseEnd().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the earliest start time.
   * @return the value of the property
   */
  public LocalTime getRandomStartMin() {
    return _randomStartMin;
  }

  /**
   * Sets the earliest start time.
   * @param randomStartMin  the new value of the property
   */
  public void setRandomStartMin(LocalTime randomStartMin) {
    this._randomStartMin = randomStartMin;
  }

  /**
   * Gets the the {@code randomStartMin} property.
   * @return the property, not null
   */
  public final Property<LocalTime> randomStartMin() {
    return metaBean().randomStartMin().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the latest start time.
   * @return the value of the property
   */
  public LocalTime getRandomStartMax() {
    return _randomStartMax;
  }

  /**
   * Sets the latest start time.
   * @param randomStartMax  the new value of the property
   */
  public void setRandomStartMax(LocalTime randomStartMax) {
    this._randomStartMax = randomStartMax;
  }

  /**
   * Gets the the {@code randomStartMax} property.
   * @return the property, not null
   */
  public final Property<LocalTime> randomStartMax() {
    return metaBean().randomStartMax().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the earliest end time.
   * @return the value of the property
   */
  public LocalTime getRandomEndMin() {
    return _randomEndMin;
  }

  /**
   * Sets the earliest end time.
   * @param randomEndMin  the new value of the property
   */
  public void setRandomEndMin(LocalTime randomEndMin) {
    this._randomEndMin = randomEndMin;
  }

  /**
   * Gets the the {@code randomEndMin} property.
   * @return the property, not null
   */
  public final Property<LocalTime> randomEndMin() {
    return metaBean().randomEndMin().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the latest end time.
   * @return the value of the property
   */
  public LocalTime getRandomEndMax() {
    return _randomEndMax;
  }

  /**
   * Sets the latest end time.
   * @param randomEndMax  the new value of the property
   */
  public void setRandomEndMax(LocalTime randomEndMax) {
    this._randomEndMax = randomEndMax;
  }

  /**
   * Gets the the {@code randomEndMax} property.
   * @return the property, not null
   */
  public final Property<LocalTime> randomEndMax() {
    return metaBean().randomEndMax().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the date when the information was last checked.
   * @return the value of the property
   */
  public LocalDate getLastConfirmed() {
    return _lastConfirmed;
  }

  /**
   * Sets the date when the information was last checked.
   * @param lastConfirmed  the new value of the property
   */
  public void setLastConfirmed(LocalDate lastConfirmed) {
    this._lastConfirmed = lastConfirmed;
  }

  /**
   * Gets the the {@code lastConfirmed} property.
   * @return the property, not null
   */
  public final Property<LocalDate> lastConfirmed() {
    return metaBean().lastConfirmed().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the free text notes.
   * @return the value of the property
   */
  public String getNotes() {
    return _notes;
  }

  /**
   * Sets the free text notes.
   * @param notes  the new value of the property
   */
  public void setNotes(String notes) {
    this._notes = notes;
  }

  /**
   * Gets the the {@code notes} property.
   * @return the property, not null
   */
  public final Property<String> notes() {
    return metaBean().notes().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code ManageableExchangeDetail}.
   */
  public static class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code productGroup} property.
     */
    private final MetaProperty<String> _productGroup = DirectMetaProperty.ofReadWrite(
        this, "productGroup", ManageableExchangeDetail.class, String.class);
    /**
     * The meta-property for the {@code productName} property.
     */
    private final MetaProperty<String> _productName = DirectMetaProperty.ofReadWrite(
        this, "productName", ManageableExchangeDetail.class, String.class);
    /**
     * The meta-property for the {@code productType} property.
     */
    private final MetaProperty<String> _productType = DirectMetaProperty.ofReadWrite(
        this, "productType", ManageableExchangeDetail.class, String.class);
    /**
     * The meta-property for the {@code productCode} property.
     */
    private final MetaProperty<String> _productCode = DirectMetaProperty.ofReadWrite(
        this, "productCode", ManageableExchangeDetail.class, String.class);
    /**
     * The meta-property for the {@code calendarStart} property.
     */
    private final MetaProperty<LocalDate> _calendarStart = DirectMetaProperty.ofReadWrite(
        this, "calendarStart", ManageableExchangeDetail.class, LocalDate.class);
    /**
     * The meta-property for the {@code calendarEnd} property.
     */
    private final MetaProperty<LocalDate> _calendarEnd = DirectMetaProperty.ofReadWrite(
        this, "calendarEnd", ManageableExchangeDetail.class, LocalDate.class);
    /**
     * The meta-property for the {@code dayStart} property.
     */
    private final MetaProperty<String> _dayStart = DirectMetaProperty.ofReadWrite(
        this, "dayStart", ManageableExchangeDetail.class, String.class);
    /**
     * The meta-property for the {@code dayRangeType} property.
     */
    private final MetaProperty<String> _dayRangeType = DirectMetaProperty.ofReadWrite(
        this, "dayRangeType", ManageableExchangeDetail.class, String.class);
    /**
     * The meta-property for the {@code dayEnd} property.
     */
    private final MetaProperty<String> _dayEnd = DirectMetaProperty.ofReadWrite(
        this, "dayEnd", ManageableExchangeDetail.class, String.class);
    /**
     * The meta-property for the {@code phaseName} property.
     */
    private final MetaProperty<String> _phaseName = DirectMetaProperty.ofReadWrite(
        this, "phaseName", ManageableExchangeDetail.class, String.class);
    /**
     * The meta-property for the {@code phaseType} property.
     */
    private final MetaProperty<String> _phaseType = DirectMetaProperty.ofReadWrite(
        this, "phaseType", ManageableExchangeDetail.class, String.class);
    /**
     * The meta-property for the {@code phaseStart} property.
     */
    private final MetaProperty<LocalTime> _phaseStart = DirectMetaProperty.ofReadWrite(
        this, "phaseStart", ManageableExchangeDetail.class, LocalTime.class);
    /**
     * The meta-property for the {@code phaseEnd} property.
     */
    private final MetaProperty<LocalTime> _phaseEnd = DirectMetaProperty.ofReadWrite(
        this, "phaseEnd", ManageableExchangeDetail.class, LocalTime.class);
    /**
     * The meta-property for the {@code randomStartMin} property.
     */
    private final MetaProperty<LocalTime> _randomStartMin = DirectMetaProperty.ofReadWrite(
        this, "randomStartMin", ManageableExchangeDetail.class, LocalTime.class);
    /**
     * The meta-property for the {@code randomStartMax} property.
     */
    private final MetaProperty<LocalTime> _randomStartMax = DirectMetaProperty.ofReadWrite(
        this, "randomStartMax", ManageableExchangeDetail.class, LocalTime.class);
    /**
     * The meta-property for the {@code randomEndMin} property.
     */
    private final MetaProperty<LocalTime> _randomEndMin = DirectMetaProperty.ofReadWrite(
        this, "randomEndMin", ManageableExchangeDetail.class, LocalTime.class);
    /**
     * The meta-property for the {@code randomEndMax} property.
     */
    private final MetaProperty<LocalTime> _randomEndMax = DirectMetaProperty.ofReadWrite(
        this, "randomEndMax", ManageableExchangeDetail.class, LocalTime.class);
    /**
     * The meta-property for the {@code lastConfirmed} property.
     */
    private final MetaProperty<LocalDate> _lastConfirmed = DirectMetaProperty.ofReadWrite(
        this, "lastConfirmed", ManageableExchangeDetail.class, LocalDate.class);
    /**
     * The meta-property for the {@code notes} property.
     */
    private final MetaProperty<String> _notes = DirectMetaProperty.ofReadWrite(
        this, "notes", ManageableExchangeDetail.class, String.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> _metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "productGroup",
        "productName",
        "productType",
        "productCode",
        "calendarStart",
        "calendarEnd",
        "dayStart",
        "dayRangeType",
        "dayEnd",
        "phaseName",
        "phaseType",
        "phaseStart",
        "phaseEnd",
        "randomStartMin",
        "randomStartMax",
        "randomEndMin",
        "randomEndMax",
        "lastConfirmed",
        "notes");

    /**
     * Restricted constructor.
     */
    protected Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case 992343760:  // productGroup
          return _productGroup;
        case -1491817446:  // productName
          return _productName;
        case -1491615543:  // productType
          return _productType;
        case -1492131972:  // productCode
          return _productCode;
        case 1952067524:  // calendarStart
          return _calendarStart;
        case 404251837:  // calendarEnd
          return _calendarEnd;
        case 1920217638:  // dayStart
          return _dayStart;
        case 761807323:  // dayRangeType
          return _dayRangeType;
        case -1338796129:  // dayEnd
          return _dayEnd;
        case -426196314:  // phaseName
          return _phaseName;
        case -425994411:  // phaseType
          return _phaseType;
        case -322011225:  // phaseStart
          return _phaseStart;
        case -1676324512:  // phaseEnd
          return _phaseEnd;
        case -882488205:  // randomStartMin
          return _randomStartMin;
        case -882488443:  // randomStartMax
          return _randomStartMax;
        case -961403366:  // randomEndMin
          return _randomEndMin;
        case -961403604:  // randomEndMax
          return _randomEndMax;
        case 1696487785:  // lastConfirmed
          return _lastConfirmed;
        case 105008833:  // notes
          return _notes;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends ManageableExchangeDetail> builder() {
      return new DirectBeanBuilder<ManageableExchangeDetail>(new ManageableExchangeDetail());
    }

    @Override
    public Class<? extends ManageableExchangeDetail> beanType() {
      return ManageableExchangeDetail.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return _metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code productGroup} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<String> productGroup() {
      return _productGroup;
    }

    /**
     * The meta-property for the {@code productName} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<String> productName() {
      return _productName;
    }

    /**
     * The meta-property for the {@code productType} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<String> productType() {
      return _productType;
    }

    /**
     * The meta-property for the {@code productCode} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<String> productCode() {
      return _productCode;
    }

    /**
     * The meta-property for the {@code calendarStart} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<LocalDate> calendarStart() {
      return _calendarStart;
    }

    /**
     * The meta-property for the {@code calendarEnd} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<LocalDate> calendarEnd() {
      return _calendarEnd;
    }

    /**
     * The meta-property for the {@code dayStart} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<String> dayStart() {
      return _dayStart;
    }

    /**
     * The meta-property for the {@code dayRangeType} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<String> dayRangeType() {
      return _dayRangeType;
    }

    /**
     * The meta-property for the {@code dayEnd} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<String> dayEnd() {
      return _dayEnd;
    }

    /**
     * The meta-property for the {@code phaseName} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<String> phaseName() {
      return _phaseName;
    }

    /**
     * The meta-property for the {@code phaseType} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<String> phaseType() {
      return _phaseType;
    }

    /**
     * The meta-property for the {@code phaseStart} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<LocalTime> phaseStart() {
      return _phaseStart;
    }

    /**
     * The meta-property for the {@code phaseEnd} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<LocalTime> phaseEnd() {
      return _phaseEnd;
    }

    /**
     * The meta-property for the {@code randomStartMin} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<LocalTime> randomStartMin() {
      return _randomStartMin;
    }

    /**
     * The meta-property for the {@code randomStartMax} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<LocalTime> randomStartMax() {
      return _randomStartMax;
    }

    /**
     * The meta-property for the {@code randomEndMin} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<LocalTime> randomEndMin() {
      return _randomEndMin;
    }

    /**
     * The meta-property for the {@code randomEndMax} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<LocalTime> randomEndMax() {
      return _randomEndMax;
    }

    /**
     * The meta-property for the {@code lastConfirmed} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<LocalDate> lastConfirmed() {
      return _lastConfirmed;
    }

    /**
     * The meta-property for the {@code notes} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<String> notes() {
      return _notes;
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
