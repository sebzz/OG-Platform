/**
 * Copyright (C) 2012 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */
package com.opengamma.financial.analytics.fudgemsg;

import com.opengamma.math.function.Function;
import com.opengamma.util.serialization.InvokedSerializedForm;

/**
 * 
 */
public class TestStatelessFunction {

  public Function<Double, Double> getSurface(final Double a, final Double b) {
    final Function<Double, Double> f = new Function<Double, Double>() {

      @Override
      public Double evaluate(final Double... x) {
        return a * x[0] + b * x[1] * x[1];
      }

      public Object writeReplace() {
        return new InvokedSerializedForm(TestStatelessFunction.class, "getSurface", a, b);
      }
    };
    return f;
  }
}