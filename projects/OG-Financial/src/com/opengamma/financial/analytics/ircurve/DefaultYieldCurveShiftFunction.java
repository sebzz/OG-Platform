/**
 * Copyright (C) 2011 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */
package com.opengamma.financial.analytics.ircurve;

import java.util.Set;

import com.opengamma.engine.ComputationTarget;
import com.opengamma.engine.ComputationTargetType;
import com.opengamma.engine.function.FunctionCompilationContext;
import com.opengamma.engine.value.ValueProperties;
import com.opengamma.engine.value.ValuePropertyNames;
import com.opengamma.engine.value.ValueRequirement;
import com.opengamma.engine.value.ValueRequirementNames;
import com.opengamma.financial.property.StaticDefaultPropertyFunction;
import com.opengamma.util.money.Currency;

/**
 * Function to inject default yield curve shifts into the dependency graph.
 * Shifts are taken from the default properties in the following order:
 * <ul>
 *  <li><em>curve name</em>_<em>currency</em>_YIELD_CURVE_SHIFT
 *  <li><em>curve_name</em>_YIELD_CURVE_SHIFT
 *  <li><em>currency</em>_YIELD_CURVE_SHIFT
 *  <li>YIELD_CURVE_SHIFT
 * </ul>
 * This should allow either specific curves to be adjusted or more global changes.
 */
public class DefaultYieldCurveShiftFunction extends StaticDefaultPropertyFunction {

  /**
   * Property to shift all yield curves.
   */
  protected static final String YIELD_CURVE_SHIFT = "YIELD_CURVE_" + YieldCurveShiftFunction.SHIFT;

  public DefaultYieldCurveShiftFunction() {
    super(ComputationTargetType.PRIMITIVE, YieldCurveShiftFunction.SHIFT, false, ValueRequirementNames.YIELD_CURVE);
  }

  @Override
  public boolean canApplyTo(final FunctionCompilationContext context, final ComputationTarget target) {
    if (target.getUniqueId() == null) {
      return false;
    }
    return Currency.OBJECT_SCHEME.equals(target.getUniqueId().getScheme());
  }

  @Override
  protected Set<String> getDefaultValue(final FunctionCompilationContext context, final ComputationTarget target, final ValueRequirement desiredValue) {
    final String currency = target.getUniqueId().getValue();
    final ValueProperties defaults = context.getViewCalculationConfiguration().getDefaultProperties();
    final Set<String> names = desiredValue.getConstraints().getValues(ValuePropertyNames.CURVE);
    Set<String> values;
    if ((names != null) && (names.size() == 1)) {
      final String name = names.iterator().next();
      values = defaults.getValues(name + "_" + currency + "_" + YIELD_CURVE_SHIFT);
      if (values != null) {
        return values;
      }
      values = defaults.getValues(name + "_" + YIELD_CURVE_SHIFT);
      if (values != null) {
        return values;
      }
    }
    values = defaults.getValues(currency + "_" + YIELD_CURVE_SHIFT);
    if (values != null) {
      return values;
    }
    return defaults.getValues(YIELD_CURVE_SHIFT);
  }

}
