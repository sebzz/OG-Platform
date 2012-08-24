/**
 * Copyright (C) 2011 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */

package com.opengamma.integration.copiernew.sheet;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.opengamma.util.ArgumentChecker;

/**
 * A class to facilitate importing portfolio data from a JDBC query result
 */
public class JdbcRawSheetReader extends RawSheetReader {

  private DataSource _dataSource;
  private JdbcTemplate _jdbcTemplate;
  private ResultSet _resultSet;
  private String[] _row;
  private Iterator<Map<String, String>> _iterator;
  private static final Logger s_logger = LoggerFactory.getLogger(JdbcRawSheetReader.class);
  
  public JdbcRawSheetReader(DataSource dataSource, String query) {
    ArgumentChecker.notNull(dataSource, "dataSource");
    ArgumentChecker.notEmpty(query, "query");
    init(dataSource, query, null);
  }
  
  public JdbcRawSheetReader(DataSource dataSource, String query, PreparedStatementSetter preparedStatementSetter) {
    ArgumentChecker.notNull(dataSource, "dataSource");
    ArgumentChecker.notEmpty(query, "query");
    ArgumentChecker.notNull(preparedStatementSetter, "prepared statement setter");
    init(dataSource, query, preparedStatementSetter);
  }
  
  protected void init(DataSource dataSource, String query, PreparedStatementSetter preparedStatementSetter) {

    _jdbcTemplate = new JdbcTemplate(dataSource);
     
    ResultSetExtractor<List<Map<String, String>>> extractor = new ResultSetExtractor<List<Map<String, String>>>() {
      @Override
      public List<Map<String, String>> extractData(ResultSet rs) throws SQLException, DataAccessException {
        String[] columns = new String[rs.getMetaData().getColumnCount()];
        for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
          columns[i] = rs.getMetaData().getColumnName(i + 1);
        }
        setColumns(columns);
        List<Map<String, String>> entries = new ArrayList<Map<String, String>>();
        while (rs.next()) {
          s_logger.info("Read a row");
          String[] rawRow = new String[rs.getMetaData().getColumnCount()];
          for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
            rawRow[i] = rs.getString(i + 1);
          }
          Map<String, String> result = new HashMap<String, String>();
          // Map read-in row onto expected columns
          for (int i = 0; i < getColumns().length; i++) {
            if (i >= rawRow.length) {
              break;
            }
            if (rawRow[i] != null && rawRow[i].trim().length() > 0) {
              result.put(getColumns()[i], rawRow[i]);
            }
          }
          entries.add(result);
        }
        return entries;
      }
    };

    List<Map<String, String>> results;
    if (preparedStatementSetter != null) {
      results = getJDBCTemplate().query(query, preparedStatementSetter, extractor);
    } else {
      results = getJDBCTemplate().query(query, extractor);
    }
    _iterator = results.iterator();
  }
  
  private JdbcTemplate getJDBCTemplate() {
    return _jdbcTemplate;
  }

  @Override
  public void close() {
  }

  @Override
  public Iterator<Map<String, String>> iterator() {
    return _iterator;
  }
}
