package com.opengamma.integration.copiernew.configuration;

import com.opengamma.OpenGammaRuntimeException;
import com.opengamma.id.ExternalIdSearch;
import com.opengamma.id.UniqueId;
import com.opengamma.id.UniqueIdentifiable;
import com.opengamma.id.VersionCorrection;
import com.opengamma.integration.copiernew.Writeable;
import com.opengamma.master.config.ConfigMaster;
import com.opengamma.master.config.ConfigDocument;
import com.opengamma.master.config.ConfigMaster;
import com.opengamma.master.config.ConfigSearchRequest;
import com.opengamma.master.config.ConfigSearchResult;
import com.opengamma.master.config.ConfigSearchSortOrder;
import com.opengamma.util.ArgumentChecker;
import com.opengamma.util.beancompare.BeanCompare;
import com.opengamma.util.beancompare.BeanDifference;
import com.opengamma.util.tuple.ObjectsPair;
import org.joda.beans.impl.direct.DirectBean;

import javax.time.calendar.ZonedDateTime;
import java.io.IOException;
import java.util.List;

public class ConfigMasterWriter<T> implements Writeable<ObjectsPair<String, T>> {

  ConfigMaster _configMaster;
  private BeanCompare _beanCompare;

  public ConfigMasterWriter(ConfigMaster configMaster) {
    ArgumentChecker.notNull(configMaster, "configMaster");
    _configMaster = configMaster;
    _beanCompare = new BeanCompare();
  }

  @Override
  public void addOrUpdate(ObjectsPair<String, T> pair) {
    ArgumentChecker.notNull(pair, "config, name pair");

    ConfigSearchRequest searchReq = new ConfigSearchRequest<T>();
    String name = pair.getFirst();
    T config = pair.getSecond();

    searchReq.setName(name);
    searchReq.setType(config.getClass());


    searchReq.setVersionCorrection(VersionCorrection.ofVersionAsOf(ZonedDateTime.now())); // valid now
    searchReq.setSortOrder(ConfigSearchSortOrder.VERSION_FROM_INSTANT_DESC);
    ConfigSearchResult<T> searchResult = _configMaster.search(searchReq);
    Object foundConfig = searchResult.getFirstValue();
    if (foundConfig != null) {
      List<BeanDifference<?>> differences;
      try {
        differences = _beanCompare.compare((DirectBean) foundConfig, (DirectBean) config);
      } catch (Exception e) {
        throw new OpenGammaRuntimeException("Error comparing configs (" + config + ")", e);
      }
      if (differences.size() == 1 && differences.get(0).getProperty().propertyType() == UniqueId.class) {
        // It's already there, don't update or add it
      } else {
        ConfigDocument<T> updateDoc = new ConfigDocument<T>(config.getClass());
        updateDoc.setValue(config);
        updateDoc.setUniqueId(searchResult.getFirstDocument().getUniqueId());
        ConfigDocument result = _configMaster.update(updateDoc);
      }
    } else {
      // Not found, so add it
      ConfigDocument<T> addDoc = new ConfigDocument<T>(config.getClass());
      addDoc.setValue(config);
      ConfigDocument result = _configMaster.add(addDoc);
    }
  }

  @Override
  public void addOrUpdate(Iterable<ObjectsPair<String, T>> data) {
    for (ObjectsPair<String, T> datum : data) {
      addOrUpdate(datum);
    }
  }

  @Override
  public void flush() throws IOException {
    // No action
  }
}
