/**
 * Copyright (C) 2009 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.bbg.livedata;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.fudgemsg.FudgeContext;
import org.fudgemsg.mapping.FudgeSerializer;
import org.fudgemsg.wire.FudgeMsgWriter;

import com.opengamma.OpenGammaRuntimeException;
import com.opengamma.bbg.PerSecurityReferenceDataResult;
import com.opengamma.bbg.ReferenceDataProvider;
import com.opengamma.bbg.ReferenceDataResult;
import com.opengamma.util.ArgumentChecker;

/**
 * Decorates a reference data provider, storing the data to a flat file.
 * <p>
 * The request and response data is written to the file incrementally.
 * The intention is for this decorator to be used for a period while certain reference data
 * requests are made, such as when a view starts being processed. Subsequently the data file
 * can replace the 'real' source of reference data for the same requests.
 * The file is stored using Fudge.
 * See {@link LoggedReferenceDataProvider} for a reference data provider that reads the file.
 */
public class LoggingReferenceDataProvider implements ReferenceDataProvider {

  /**
   * The underlying reference data provider.
   */
  private final ReferenceDataProvider _underlying;
  /**
   * The store of data already written to file.
   */
  private final ConcurrentMap<String, Set<String>> _alreadyLogged = new ConcurrentHashMap<String, Set<String>>();
  /**
   * The Fudge context.
   */
  private final FudgeContext _fudgeContext;
  /**
   * The Fudge message writer.
   */
  private final FudgeMsgWriter _fudgeMsgWriter;

  /**
   * Creates an instance.
   * 
   * @param underlying  the underlying reference data provider, not null
   * @param fudgeContext  the Fudge context, not null
   * @param outputFile  the file to write to, not null
   */
  public LoggingReferenceDataProvider(ReferenceDataProvider underlying, FudgeContext fudgeContext, File outputFile) {
    ArgumentChecker.notNull(underlying, "underlying");
    ArgumentChecker.notNull(fudgeContext, "fudgeContext");
    ArgumentChecker.notNull(outputFile, "outputFile");
    _underlying = underlying;
    _fudgeContext = fudgeContext;
    try {
      FileOutputStream fos = new FileOutputStream(outputFile);
      BufferedOutputStream bos = new BufferedOutputStream(fos, 4096);
      _fudgeMsgWriter = fudgeContext.createMessageWriter(bos);
    } catch (FileNotFoundException ex) {
      throw new OpenGammaRuntimeException("Cannot open " + outputFile + " for writing");
    }
  }

  //-------------------------------------------------------------------------
  @Override
  public ReferenceDataResult getFields(Set<String> securityKeys, Set<String> fields) {   
    ReferenceDataResult result = _underlying.getFields(securityKeys, fields);
    processResult(result, fields);
    return result;
  }

  private void processResult(ReferenceDataResult result, Set<String> fields) {
    for (String securityKey : result.getSecurities()) {
      Set<String> freshFieldsLogged = new HashSet<String>();
      Set<String> fieldsLogged = _alreadyLogged.putIfAbsent(securityKey, freshFieldsLogged);
      if (fieldsLogged == null) {
        fieldsLogged = freshFieldsLogged;
      }
      
      PerSecurityReferenceDataResult securityResult = result.getResult(securityKey);
      synchronized (fieldsLogged) {
        for (String field : fields) {
          if (fieldsLogged.contains(field) == false) {
            Object value = securityResult.getFieldData().getValue(field);
            log(securityKey, field, value);
          }
        }
      }
    }
  }

  private void log(String securityKey, String field, Object value) {
    LoggedReferenceData loggedReferenceData = new LoggedReferenceData(securityKey, field, value);
    _fudgeMsgWriter.writeMessage(loggedReferenceData.toFudgeMsg(new FudgeSerializer(_fudgeContext)));
    _fudgeMsgWriter.flush();
  }

}
