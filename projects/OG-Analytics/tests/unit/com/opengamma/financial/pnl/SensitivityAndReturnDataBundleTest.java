/**
 * Copyright (C) 2009 - 2010 by OpenGamma Inc.
 *
 * Please see distribution for license.
 */
package com.opengamma.financial.pnl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.opengamma.financial.greeks.Greek;
import com.opengamma.financial.sensitivity.Sensitivity;
import com.opengamma.financial.sensitivity.ValueGreek;
import com.opengamma.financial.sensitivity.ValueGreekSensitivity;
import com.opengamma.util.timeseries.DoubleTimeSeries;
import com.opengamma.util.timeseries.fast.DateTimeNumericEncoding;
import com.opengamma.util.timeseries.fast.longint.FastArrayLongDoubleTimeSeries;

/**
 * 
 */
public class SensitivityAndReturnDataBundleTest {
  private static final Sensitivity<ValueGreek> S1 = new ValueGreekSensitivity(new ValueGreek(Greek.DELTA), "ValueDelta");
  private static final Sensitivity<ValueGreek> S2 = new ValueGreekSensitivity(new ValueGreek(Greek.GAMMA), "ValueGamma");
  private static final double VALUE_DELTA = 1000;
  private static final double VALUE_GAMMA = -1234;
  private static final DoubleTimeSeries<?> TS1;
  private static final DoubleTimeSeries<?> TS2;
  private static final Map<UnderlyingType, DoubleTimeSeries<?>> M1;
  private static final Map<UnderlyingType, DoubleTimeSeries<?>> M2;
  private static final SensitivityAndReturnDataBundle DATA;

  static {
    final int n = 100;
    final long[] times = new long[n];
    final double[] x1 = new double[n];
    final double[] x2 = new double[n];
    for (int i = 0; i < n; i++) {
      times[i] = i;
      x1[i] = Math.random() - 0.5;
      x2[i] = Math.random() - 0.5;
    }
    TS1 = new FastArrayLongDoubleTimeSeries(DateTimeNumericEncoding.DATE_EPOCH_DAYS, times, x1);
    TS2 = new FastArrayLongDoubleTimeSeries(DateTimeNumericEncoding.DATE_EPOCH_DAYS, times, x2);
    M1 = new HashMap<UnderlyingType, DoubleTimeSeries<?>>();
    M1.put(UnderlyingType.SPOT_PRICE, TS1);
    M2 = new HashMap<UnderlyingType, DoubleTimeSeries<?>>();
    M2.put(UnderlyingType.SPOT_PRICE, TS2);
    DATA = new SensitivityAndReturnDataBundle(S1, VALUE_GAMMA, M1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullSensitivity() {
    new SensitivityAndReturnDataBundle(null, VALUE_DELTA, M1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullReturns() {
    new SensitivityAndReturnDataBundle(S1, VALUE_DELTA, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEmptyReturns() {
    new SensitivityAndReturnDataBundle(S1, VALUE_DELTA, Collections.<UnderlyingType, DoubleTimeSeries<?>> emptyMap());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullKey() {
    final Map<UnderlyingType, DoubleTimeSeries<?>> m = new HashMap<UnderlyingType, DoubleTimeSeries<?>>();
    m.put(null, TS1);
    new SensitivityAndReturnDataBundle(S1, VALUE_GAMMA, m);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullEntry() {
    final Map<UnderlyingType, DoubleTimeSeries<?>> m = new HashMap<UnderlyingType, DoubleTimeSeries<?>>();
    m.put(UnderlyingType.SPOT_PRICE, null);
    new SensitivityAndReturnDataBundle(S1, VALUE_GAMMA, m);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testWrongUnderlyings1() {
    final Sensitivity<ValueGreek> s = new ValueGreekSensitivity(new ValueGreek(Greek.VANNA), "ValueVanna");
    new SensitivityAndReturnDataBundle(s, VALUE_DELTA, M1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testWrongUnderlyings2() {
    final Sensitivity<ValueGreek> s = new ValueGreekSensitivity(new ValueGreek(Greek.VEGA), "ValueVega");
    new SensitivityAndReturnDataBundle(s, VALUE_DELTA, M1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullUnderlyingType() {
    DATA.getReturnTimeSeriesForUnderlying(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testWrongUnderlyingType() {
    DATA.getReturnTimeSeriesForUnderlying(UnderlyingType.BOND_YIELD);
  }

  @Test
  public void testEqualsAndHashCode() {
    SensitivityAndReturnDataBundle other = new SensitivityAndReturnDataBundle(S1, VALUE_GAMMA, M1);
    assertEquals(other, DATA);
    assertEquals(other.hashCode(), DATA.hashCode());
    other = new SensitivityAndReturnDataBundle(S2, VALUE_GAMMA, M1);
    assertFalse(other.equals(DATA));
    other = new SensitivityAndReturnDataBundle(S1, VALUE_DELTA, M1);
    assertFalse(other.equals(DATA));
    other = new SensitivityAndReturnDataBundle(S1, VALUE_GAMMA, M2);
  }

  @Test
  public void testGetters() {
    assertEquals(DATA.getReturnTimeSeriesForUnderlying(UnderlyingType.SPOT_PRICE), TS1);
    assertEquals(DATA.getSensitivity(), S1);
    assertEquals(DATA.getUnderlying(), S1.getUnderlying());
    assertEquals(DATA.getUnderlyingReturnTS(), M1);
    assertEquals(DATA.getUnderlyingTypes(), M1.keySet());
    assertEquals(DATA.getValue(), VALUE_GAMMA, 0);
  }
}
