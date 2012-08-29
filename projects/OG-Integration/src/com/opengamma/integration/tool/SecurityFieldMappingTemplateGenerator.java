/**
 * Copyright (C) 2009 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.integration.tool;

import java.io.FileWriter;

import org.joda.beans.MetaProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.com.bytecode.opencsv.CSVWriter;

import com.opengamma.component.tool.AbstractTool;
import com.opengamma.integration.tool.config.ConfigImportExportTool;
import com.opengamma.master.security.ManageableSecurity;
import com.opengamma.master.security.SecurityMaster;
import com.opengamma.master.security.SecurityMetaDataRequest;
import com.opengamma.master.security.SecurityMetaDataResult;
import com.opengamma.master.security.SecuritySearchRequest;
import com.opengamma.master.security.SecuritySearchResult;
import com.opengamma.util.generate.scripts.Scriptable;

/**
 * Tool to generate a template for doing field mapping tasks
 */
@Scriptable
public class SecurityFieldMappingTemplateGenerator extends AbstractTool {
  private static final Logger s_logger = LoggerFactory.getLogger(SecurityFieldMappingTemplateGenerator.class);
  @Override
  protected void doRun() throws Exception {
    CSVWriter csvWriter = new CSVWriter(new FileWriter(getCommandLine().getArgs()[0]));
    SecurityMaster securityMaster = getToolContext().getSecurityMaster();
    SecurityMetaDataRequest metaRequest = new SecurityMetaDataRequest();
    SecurityMetaDataResult metaData = securityMaster.metaData(metaRequest);
    for (String securityType : metaData.getSecurityTypes()) {
      s_logger.info("Processing security type " + securityType);
      SecuritySearchRequest searchRequest = new SecuritySearchRequest();
      searchRequest.setName("*");
      searchRequest.setSecurityType(securityType);
      SecuritySearchResult search = securityMaster.search(searchRequest);
      s_logger.info("Search returned " + search.getDocuments().size() + " securities");
      dumpSecurityStructure(csvWriter, securityType, search.getFirstSecurity());
    }
    csvWriter.close();
  }

  private void dumpSecurityStructure(CSVWriter csvWriter, String securityType, ManageableSecurity firstSecurity) {
    if (firstSecurity == null) {
      s_logger.error("null security passed to dumpSecurityStructure");
    }
    s_logger.info("Processing security " + firstSecurity);
    csvWriter.writeNext(new String[] {securityType });
    csvWriter.writeNext(new String[] {firstSecurity.metaBean().beanName() });
    csvWriter.writeNext(new String[] {"Type", "Name", "Example"});
    Iterable<MetaProperty<?>> metaPropertyIterable = firstSecurity.metaBean().metaPropertyIterable();
    for (MetaProperty<?> metaProperty : metaPropertyIterable) {
      s_logger.info("Field" + metaProperty.name());
      String strValue;
      try {
        strValue = metaProperty.getString(firstSecurity);
      } catch (IllegalStateException ise) {
        strValue = metaProperty.get(firstSecurity).toString();
      }
      csvWriter.writeNext(new String[] {metaProperty.propertyType().getSimpleName(), metaProperty.name(), strValue });
    }
    csvWriter.writeNext(new String[] {});
  }
  
  /**
   * Main method to run the tool.
   */
  public static void main(String[] args) {  // CSIGNORE
    new SecurityFieldMappingTemplateGenerator().initAndRun(args);
    System.exit(0);
  }
}
