/**
 * Copyright (C) 2012 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.web.server.push.analytics;

import java.util.HashMap;
import java.util.Map;

import com.opengamma.DataNotFoundException;
import com.opengamma.engine.view.compilation.CompiledViewDefinition;
import com.opengamma.util.ArgumentChecker;

/**
 *
 */
/* package */ class MainAnalyticsGrid extends AnalyticsGrid {

  private final AnalyticsView.GridType _gridType;
  private final Map<String, AnalyticsGrid> _depGraphs = new HashMap<String, AnalyticsGrid>();

  /* package */ MainAnalyticsGrid(AnalyticsView.GridType gridType,
                                  AnalyticsGridStructure gridStructure) {
    super(gridStructure);
    ArgumentChecker.notNull(gridType, "gridType");
    _gridType = gridType;
  }

  // TODO does this actually need the grid type parameter? could hard code it as one or other and it probably wouldn't matter
  /* package */ static MainAnalyticsGrid empty(AnalyticsView.GridType gridType) {
    return new MainAnalyticsGrid(gridType, AnalyticsGridStructure.empty());
  }

  /* package */ static MainAnalyticsGrid portfolio(CompiledViewDefinition compiledViewDef) {
    return new MainAnalyticsGrid(AnalyticsView.GridType.PORTFORLIO, AnalyticsGridStructure.portoflio(compiledViewDef));
  }

  /* package */ static MainAnalyticsGrid primitives(CompiledViewDefinition compiledViewDef) {
    return new MainAnalyticsGrid(AnalyticsView.GridType.PRIMITIVES, AnalyticsGridStructure.primitives(compiledViewDef));
  }

  // -------- dependency graph grids --------

  private AnalyticsGrid getDependencyGraph(String graphId) {
    AnalyticsGrid grid = _depGraphs.get(graphId);
    if (grid == null) {
      throw new DataNotFoundException("No dependency graph found with ID " + graphId + " for " + _gridType + " grid");
    }
    return grid;
  }

  // TODO a better way to specify which cell we want - target spec? stable row ID generated on the server?
  /* package */ String openDependencyGraph(String graphId, int row, int col) {
    if (_depGraphs.containsKey(graphId)) {
      throw new IllegalArgumentException("Dependency graph ID " + graphId + " is already in use");
    }
    _depGraphs.put(graphId, AnalyticsGrid.empty());
    return graphId;
  }

  /* package */ void closeDependencyGraph(String graphId) {
    AnalyticsGrid grid = _depGraphs.remove(graphId);
    if (grid == null) {
      throw new DataNotFoundException("No dependency graph found with ID " + graphId + " for " + _gridType + " grid");
    }
  }

  /* package */ AnalyticsGridStructure getGridStructure(String graphId) {
    return getDependencyGraph(graphId)._gridStructure;
  }

  /* package */ String createViewport(String graphId,
                                      String viewportId,
                                      ViewportSpecification viewportSpecification,
                                      AnalyticsHistory history) {
    return getDependencyGraph(graphId).createViewport(viewportId, viewportSpecification, history);
  }

  /* package */ void updateViewport(String graphId, String viewportId, ViewportSpecification viewportSpec) {
    getDependencyGraph(graphId).updateViewport(viewportId, viewportSpec, null);
  }

  /* package */ void deleteViewport(String graphId, String viewportId) {
    getDependencyGraph(graphId).deleteViewport(viewportId);
  }

  /* package */ ViewportResults getData(String graphId, String viewportId) {
    return getDependencyGraph(graphId).getData(viewportId);
  }
}