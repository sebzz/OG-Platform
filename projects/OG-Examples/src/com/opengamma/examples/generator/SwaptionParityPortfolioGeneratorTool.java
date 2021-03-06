/**
 * Copyright (C) 2012 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */
package com.opengamma.examples.generator;

import java.math.BigDecimal;

import javax.time.calendar.ZonedDateTime;

import com.opengamma.core.id.ExternalSchemes;
import com.opengamma.core.position.Counterparty;
import com.opengamma.core.position.PortfolioNode;
import com.opengamma.core.position.Position;
import com.opengamma.core.position.impl.SimplePortfolioNode;
import com.opengamma.financial.convention.businessday.BusinessDayConvention;
import com.opengamma.financial.convention.businessday.BusinessDayConventionFactory;
import com.opengamma.financial.convention.daycount.DayCount;
import com.opengamma.financial.convention.daycount.DayCountFactory;
import com.opengamma.financial.convention.frequency.Frequency;
import com.opengamma.financial.convention.frequency.PeriodFrequency;
import com.opengamma.financial.generator.AbstractPortfolioGeneratorTool;
import com.opengamma.financial.generator.PortfolioNodeGenerator;
import com.opengamma.financial.generator.SecurityGenerator;
import com.opengamma.financial.generator.SimplePositionGenerator;
import com.opengamma.financial.generator.StaticNameGenerator;
import com.opengamma.financial.generator.TreePortfolioNodeGenerator;
import com.opengamma.financial.security.option.SwaptionSecurity;
import com.opengamma.financial.security.swap.FixedInterestRateLeg;
import com.opengamma.financial.security.swap.FloatingInterestRateLeg;
import com.opengamma.financial.security.swap.FloatingRateType;
import com.opengamma.financial.security.swap.InterestRateNotional;
import com.opengamma.financial.security.swap.SwapSecurity;
import com.opengamma.id.ExternalId;
import com.opengamma.master.position.ManageableTrade;
import com.opengamma.master.security.ManageableSecurity;
import com.opengamma.master.security.SecurityDocument;
import com.opengamma.master.security.SecurityMaster;
import com.opengamma.util.money.Currency;
import com.opengamma.util.time.DateUtils;
import com.opengamma.util.time.Expiry;

/**
 * 
 */
public class SwaptionParityPortfolioGeneratorTool extends AbstractPortfolioGeneratorTool {
  private static final Currency CURRENCY = Currency.USD;
  private static final DayCount ACT_360 = DayCountFactory.INSTANCE.getDayCount("Actual/360");
  private static final DayCount ACT_365 = DayCountFactory.INSTANCE.getDayCount("Actual/365");
  private static final DayCount THIRTYU_360 = DayCountFactory.INSTANCE.getDayCount("30U/360");
  private static final Frequency QUARTERLY = PeriodFrequency.QUARTERLY;
  private static final Frequency SEMI_ANNUAL = PeriodFrequency.SEMI_ANNUAL;
  private static final BusinessDayConvention MODIFIED_FOLLOWING = BusinessDayConventionFactory.INSTANCE.getBusinessDayConvention("Modified Following");
  private static final ExternalId REGION = ExternalSchemes.financialRegionId("US+GB");
  private static final ExternalId LIBOR_3M = ExternalSchemes.syntheticSecurityId("USDLIBORP3M");
  private static final String COUNTERPARTY = "Cpty";

  @Override
  public PortfolioNodeGenerator createPortfolioNodeGenerator(final int size) {
    final SecurityMaster securityMaster = getToolContext().getSecurityMaster();
    final MySecurityGenerator<ManageableSecurity> firstGenerator = getSwapParityGenerator();
    final MySecurityGenerator<ManageableSecurity> secondGenerator = getSwaptionLongShortGenerator(securityMaster);
    final MySecurityGenerator<ManageableSecurity> thirdGenerator = getSwaptionConventionGenerator(securityMaster);
    final MySecurityGenerator<ManageableSecurity> fourthGenerator = getSwaptionParityGenerator(securityMaster);
    configure(firstGenerator);
    configure(secondGenerator);
    configure(thirdGenerator);
    configure(fourthGenerator);
    final TreePortfolioNodeGenerator rootNode = new TreePortfolioNodeGenerator(new StaticNameGenerator("Swap / Swaption Portfolio"));
    rootNode.addChildNode(firstGenerator);
    rootNode.addChildNode(secondGenerator);
    rootNode.addChildNode(thirdGenerator);
    rootNode.addChildNode(fourthGenerator);
    return rootNode;
  }

  private MySecurityGenerator<ManageableSecurity> getSwapParityGenerator() {
    final ZonedDateTime tradeDate = DateUtils.getUTCDate(2012, 6, 5);
    final ZonedDateTime effectiveDate = DateUtils.getUTCDate(2013, 9, 5);
    final ZonedDateTime maturity = DateUtils.getUTCDate(2023, 9, 5);
    final InterestRateNotional notional = new InterestRateNotional(CURRENCY, 10000000);
    final FloatingInterestRateLeg receiveLeg1 = new FloatingInterestRateLeg(ACT_360, QUARTERLY, REGION, MODIFIED_FOLLOWING, notional, true, LIBOR_3M, FloatingRateType.IBOR);
    final FixedInterestRateLeg payLeg1 = new FixedInterestRateLeg(THIRTYU_360, SEMI_ANNUAL, REGION, MODIFIED_FOLLOWING, notional, true, 0.02);
    final SwapSecurity swap1 = new SwapSecurity(tradeDate, effectiveDate, maturity, COUNTERPARTY, payLeg1, receiveLeg1);
    swap1.setName("Pay Fixed @ 2% v USD 3m Libor");
    final SwapSecurity swap2 = new SwapSecurity(tradeDate, effectiveDate, maturity, COUNTERPARTY, receiveLeg1, payLeg1);
    swap2.setName("Receive Fixed @ 2% v USD 3m Libor");
    final SwapSecurity[] swapParity = new SwapSecurity[] {swap1, swap2};
    final ZonedDateTime[] tradeDates = new ZonedDateTime[] {tradeDate, tradeDate};
    return new MySecurityGenerator<ManageableSecurity>(swapParity, tradeDates, "Swap payer / receiver parity");
  }

  private MySecurityGenerator<ManageableSecurity> getSwaptionLongShortGenerator(final SecurityMaster securityMaster) {
    final ZonedDateTime tradeDate = DateUtils.getUTCDate(2012, 6, 1);
    final ZonedDateTime effectiveDate = DateUtils.getUTCDate(2013, 6, 5);
    final ZonedDateTime maturityDate = DateUtils.getUTCDate(2023, 6, 5);
    final Expiry expiry = new Expiry(DateUtils.getUTCDate(2013, 6, 3));
    final InterestRateNotional notional = new InterestRateNotional(CURRENCY, 10000000);
    final FloatingInterestRateLeg payLeg = new FloatingInterestRateLeg(ACT_360, QUARTERLY, REGION, MODIFIED_FOLLOWING, notional, true, LIBOR_3M, FloatingRateType.IBOR);
    final FixedInterestRateLeg receiveLeg = new FixedInterestRateLeg(THIRTYU_360, SEMI_ANNUAL, REGION, MODIFIED_FOLLOWING, notional, true, 0.02);
    final SwapSecurity underlyingSwap = new SwapSecurity(tradeDate, effectiveDate, maturityDate, COUNTERPARTY, payLeg, receiveLeg);
    underlyingSwap.setName("Receive fixed @ 2% v USD 3m Libor");
    final SecurityDocument toAddDoc = new SecurityDocument();
    toAddDoc.setSecurity(underlyingSwap);
    securityMaster.add(toAddDoc);
    final ZonedDateTime[] tradeDates = new ZonedDateTime[] {tradeDate, tradeDate};
    final ExternalId underlyingId = getSecurityPersister().storeSecurity(underlyingSwap).iterator().next();
    final SwaptionSecurity swaption1 = new SwaptionSecurity(true, underlyingId, true, expiry, false, CURRENCY);
    swaption1.setName("Long payer 1Yx10Y @ 2%");
    final SwaptionSecurity swaption2 = new SwaptionSecurity(true, underlyingId, false, expiry, false, CURRENCY);
    swaption2.setName("Short payer 1Yx10Y @ 2%");
    final SwaptionSecurity[] swaptionParity = new SwaptionSecurity[] {swaption1, swaption2};
    return new MySecurityGenerator<ManageableSecurity>(swaptionParity, tradeDates, "Swaption long / short parity");
  }

  private MySecurityGenerator<ManageableSecurity> getSwaptionConventionGenerator(final SecurityMaster securityMaster) {
    final ZonedDateTime tradeDate = DateUtils.getUTCDate(2012, 6, 1);
    final ZonedDateTime effectiveDate = DateUtils.getUTCDate(2013, 6, 5);
    final ZonedDateTime maturityDate = DateUtils.getUTCDate(2023, 6, 5);
    final Expiry expiry = new Expiry(DateUtils.getUTCDate(2013, 6, 3));
    final InterestRateNotional notional = new InterestRateNotional(CURRENCY, 10000000);
    final FloatingInterestRateLeg payLeg = new FloatingInterestRateLeg(ACT_360, QUARTERLY, REGION, MODIFIED_FOLLOWING, notional, true, LIBOR_3M, FloatingRateType.IBOR);
    final FixedInterestRateLeg receiveLeg1 = new FixedInterestRateLeg(ACT_360, SEMI_ANNUAL, REGION, MODIFIED_FOLLOWING, notional, true, 0.018);
    final FixedInterestRateLeg receiveLeg2 = new FixedInterestRateLeg(ACT_365, SEMI_ANNUAL, REGION, MODIFIED_FOLLOWING, notional, true, 0.01825);
    final SwapSecurity underlyingSwap1 = new SwapSecurity(tradeDate, effectiveDate, maturityDate, COUNTERPARTY, payLeg, receiveLeg1);
    underlyingSwap1.setName("Receive fixed @ 1.8% v USD 3m Libor");
    final SwapSecurity underlyingSwap2 = new SwapSecurity(tradeDate, effectiveDate, maturityDate, COUNTERPARTY, payLeg, receiveLeg2);
    underlyingSwap2.setName("Receive fixed @ 1.825% v USD 3m Libor");
    SecurityDocument toAddDoc = new SecurityDocument();
    toAddDoc.setSecurity(underlyingSwap1);
    securityMaster.add(toAddDoc);
    toAddDoc = new SecurityDocument();
    toAddDoc.setSecurity(underlyingSwap2);
    securityMaster.add(toAddDoc);
    final ExternalId underlyingId1 = getSecurityPersister().storeSecurity(underlyingSwap1).iterator().next();
    final ExternalId underlyingId2 = getSecurityPersister().storeSecurity(underlyingSwap2).iterator().next();
    final ZonedDateTime[] tradeDates = new ZonedDateTime[] {tradeDate, tradeDate};
    final SwaptionSecurity swaption1 = new SwaptionSecurity(false, underlyingId1, true, expiry, false, CURRENCY);
    swaption1.setName("Long receiver 1Yx10Y @ 1.8% - ACT/360");
    final SwaptionSecurity swaption2 = new SwaptionSecurity(false, underlyingId2, false, expiry, false, CURRENCY);
    swaption2.setName("Short receiver 1Yx10Y @ 1.825% - ACT/365");
    final SwaptionSecurity[] swaptionParity = new SwaptionSecurity[] {swaption1, swaption2};
    return new MySecurityGenerator<ManageableSecurity>(swaptionParity, tradeDates, "Swaption convention parity");
  }

  private MySecurityGenerator<ManageableSecurity> getSwaptionParityGenerator(final SecurityMaster securityMaster) {
    final ZonedDateTime tradeDate = DateUtils.getUTCDate(2012, 6, 1);
    final ZonedDateTime effectiveDate = DateUtils.getUTCDate(2013, 6, 5);
    final ZonedDateTime maturityDate = DateUtils.getUTCDate(2023, 6, 5);
    final Expiry expiry = new Expiry(DateUtils.getUTCDate(2013, 6, 3));
    final InterestRateNotional notional = new InterestRateNotional(CURRENCY, 10000000);
    final FloatingInterestRateLeg payLeg = new FloatingInterestRateLeg(ACT_360, QUARTERLY, REGION, MODIFIED_FOLLOWING, notional, true, LIBOR_3M, FloatingRateType.IBOR);
    final FixedInterestRateLeg receiveLeg = new FixedInterestRateLeg(THIRTYU_360, SEMI_ANNUAL, REGION, MODIFIED_FOLLOWING, notional, true, 0.02);
    final SwapSecurity underlyingSwap1 = new SwapSecurity(tradeDate, effectiveDate, maturityDate, COUNTERPARTY, payLeg, receiveLeg);
    underlyingSwap1.setName("Receive fixed @ 2% v USD 3m Libor");
    final SwapSecurity underlyingSwap2 = new SwapSecurity(tradeDate, effectiveDate, maturityDate, COUNTERPARTY, receiveLeg, payLeg);
    underlyingSwap2.setName("Pay fixed @ 2% v USD 3m Libor");
    SecurityDocument toAddDoc = new SecurityDocument();
    toAddDoc.setSecurity(underlyingSwap1);
    securityMaster.add(toAddDoc);
    toAddDoc = new SecurityDocument();
    toAddDoc.setSecurity(underlyingSwap2);
    securityMaster.add(toAddDoc);
    final ExternalId underlyingId1 = getSecurityPersister().storeSecurity(underlyingSwap1).iterator().next();
    final ExternalId underlyingId2 = getSecurityPersister().storeSecurity(underlyingSwap2).iterator().next();
    final ZonedDateTime[] tradeDates = new ZonedDateTime[] {tradeDate, tradeDate, tradeDate};
    final SwaptionSecurity swaption1 = new SwaptionSecurity(true, underlyingId1, true, expiry, false, CURRENCY);
    swaption1.setName("Long payer 1Yx10Y @ 2%");
    final SwaptionSecurity swaption2 = new SwaptionSecurity(true, underlyingId2, false, expiry, false, CURRENCY);
    swaption2.setName("Short receiver 1Yx10Y @ 2%");
    final ManageableSecurity[] swaptionParity = new ManageableSecurity[] {underlyingSwap2, swaption1, swaption2};
    return new MySecurityGenerator<ManageableSecurity>(swaptionParity, tradeDates, "Swaption payer / receiver parity");
  }

  private class MySecurityGenerator<T extends ManageableSecurity> extends SecurityGenerator<T> implements PortfolioNodeGenerator {
    private final ManageableSecurity[] _securities;
    private final ZonedDateTime[] _tradeDates;
    private final String _name;

    public MySecurityGenerator(final ManageableSecurity[] securities, final ZonedDateTime[] tradeDates, final String name) {
      _securities = securities;
      _tradeDates = tradeDates;
      _name = name;
    }

    @Override
    public PortfolioNode createPortfolioNode() {
      final SimplePortfolioNode node = new SimplePortfolioNode(_name);
      for (int i = 0; i < _securities.length; i++) {
        final ManageableTrade trade = new ManageableTrade(BigDecimal.ONE, getSecurityPersister().storeSecurity(_securities[i]), _tradeDates[i].toLocalDate(),
            _tradeDates[i].toOffsetTime(), ExternalId.of(Counterparty.DEFAULT_SCHEME, COUNTERPARTY));
        trade.setPremium(0.);
        trade.setPremiumCurrency(CURRENCY);
        final Position position = SimplePositionGenerator.createPositionFromTrade(trade);
        node.addPosition(position);
      }
      return node;
    }

    @Override
    public T createSecurity() {
      return null;
    }
  }
}
