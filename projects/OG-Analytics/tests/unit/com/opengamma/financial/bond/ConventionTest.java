/**
 * Copyright (C) 2009 - 2010 by OpenGamma Inc.
 *
 * Please see distribution for license.
 */
package com.opengamma.financial.bond;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import javax.time.calendar.LocalDate;

import org.junit.Test;

import com.opengamma.financial.convention.businessday.BusinessDayConvention;
import com.opengamma.financial.convention.businessday.BusinessDayConventionFactory;
import com.opengamma.financial.convention.calendar.Calendar;
import com.opengamma.financial.convention.calendar.MondayToFridayCalendar;
import com.opengamma.financial.convention.daycount.DayCount;
import com.opengamma.financial.convention.daycount.DayCountFactory;

/**
 * 
 */
public class ConventionTest {
  private static final int SETTLEMENT_DAYS = 2;
  private static final DayCount DAY_COUNT = DayCountFactory.INSTANCE.getDayCount("Actual/360");
  private static final BusinessDayConvention BUSINESS_DAY = BusinessDayConventionFactory.INSTANCE.getBusinessDayConvention("Following");
  private static final Calendar CALENDAR = new MondayToFridayCalendar("A");
  private static final boolean IS_EOM = true;
  private static final String NAME = "CONVENTION";
  private static final Convention CONVENTION = new Convention(SETTLEMENT_DAYS, DAY_COUNT, BUSINESS_DAY, CALENDAR, IS_EOM, NAME);

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeSettlementDays() {
    new Convention(-SETTLEMENT_DAYS, DAY_COUNT, BUSINESS_DAY, CALENDAR, IS_EOM, NAME);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullDayCount() {
    new Convention(SETTLEMENT_DAYS, null, BUSINESS_DAY, CALENDAR, IS_EOM, NAME);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullBusinessDayConvention() {
    new Convention(SETTLEMENT_DAYS, DAY_COUNT, null, CALENDAR, IS_EOM, NAME);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullCalendar() {
    new Convention(SETTLEMENT_DAYS, DAY_COUNT, BUSINESS_DAY, null, IS_EOM, NAME);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullName() {
    new Convention(SETTLEMENT_DAYS, DAY_COUNT, BUSINESS_DAY, CALENDAR, IS_EOM, null);
  }

  @Test
  public void testGetters() {
    assertEquals(CONVENTION.getBusinessDayConvention(), BUSINESS_DAY);
    assertEquals(CONVENTION.getDayCount(), DAY_COUNT);
    assertEquals(CONVENTION.getName(), NAME);
    assertEquals(CONVENTION.getSettlementDays(), SETTLEMENT_DAYS);
    assertEquals(CONVENTION.getWorkingDayCalendar(), CALENDAR);
  }

  @Test
  public void testHashCodeAndEquals() {
    Convention other = new Convention(SETTLEMENT_DAYS, DAY_COUNT, BUSINESS_DAY, CALENDAR, IS_EOM, NAME);
    assertEquals(CONVENTION, other);
    assertEquals(CONVENTION.hashCode(), other.hashCode());
    other = new Convention(SETTLEMENT_DAYS + 1, DAY_COUNT, BUSINESS_DAY, CALENDAR, IS_EOM, NAME);
    assertFalse(CONVENTION.equals(other));
    other = new Convention(SETTLEMENT_DAYS, DayCountFactory.INSTANCE.getDayCount("Actual/365"), BUSINESS_DAY, CALENDAR, IS_EOM, NAME);
    assertFalse(CONVENTION.equals(other));
    other = new Convention(SETTLEMENT_DAYS, DAY_COUNT, BusinessDayConventionFactory.INSTANCE.getBusinessDayConvention("None"), CALENDAR, IS_EOM, NAME);
    assertFalse(CONVENTION.equals(other));
    other = new Convention(SETTLEMENT_DAYS, DAY_COUNT, BUSINESS_DAY, new Calendar() {

      @Override
      public boolean isWorkingDay(final LocalDate date) {
        return false;
      }

      @Override
      public String getConventionName() {
        return null;
      }

    }, IS_EOM, NAME);
    assertFalse(CONVENTION.equals(other));
    other = new Convention(SETTLEMENT_DAYS, DAY_COUNT, BUSINESS_DAY, CALENDAR, IS_EOM, NAME + ")");
    assertFalse(CONVENTION.equals(other));
  }

}
