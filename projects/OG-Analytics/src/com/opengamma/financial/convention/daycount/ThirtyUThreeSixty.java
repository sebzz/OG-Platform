/**
 * Copyright (C) 2009 - 2010 by OpenGamma Inc.
 *
 * Please see distribution for license.
 */
package com.opengamma.financial.convention.daycount;

import javax.time.calendar.CalendricalMatchers;
import javax.time.calendar.ZonedDateTime;

/**
 * The '30U/360' day count.
 */
public class ThirtyUThreeSixty extends ThirtyThreeSixtyTypeDayCount {

  @Override
  public double getDayCountFraction(final ZonedDateTime firstDate, final ZonedDateTime secondDate) {
    return getDayCountFraction(firstDate, secondDate, false);
  }

  public double getDayCountFraction(final ZonedDateTime firstDate, final ZonedDateTime secondDate, final boolean isEOMConvention) {
    testDates(firstDate, secondDate);
    double d1 = firstDate.getDayOfMonth();
    double d2 = secondDate.getDayOfMonth();
    final double m1 = firstDate.getMonthOfYear().getValue();
    final double m2 = secondDate.getMonthOfYear().getValue();
    final double y1 = firstDate.getYear();
    final double y2 = secondDate.getYear();
    if (isEOMConvention && m1 == 2 && isLastDayOfFebruary(firstDate) && isLastDayOfFebruary(secondDate)) {
      d2 = 30;
      d1 = 30;
    } else {
      if (d1 > 29 && d2 == 31) {
        d2 = 30;
      }
      if (d1 == 31) {
        d1 = 30;
      }
    }
    return getYears(d1, d2, m1, m2, y1, y2);
  }

  public double getAccruedInterest(final ZonedDateTime previousCouponDate, final ZonedDateTime date, final double coupon, final boolean isEOMConvention) {
    return coupon * getDayCountFraction(previousCouponDate, date, isEOMConvention);
  }

  @Override
  public String getConventionName() {
    return "30U/360";
  }

  /**
   * Checks if the date, which must be in February, is the last day.
   * 
   * @param date  the date to check, not null
   * @return whether it is the last day
   */
  private static boolean isLastDayOfFebruary(final ZonedDateTime date) {
    return date.matches(CalendricalMatchers.lastDayOfMonth());
  }

}
