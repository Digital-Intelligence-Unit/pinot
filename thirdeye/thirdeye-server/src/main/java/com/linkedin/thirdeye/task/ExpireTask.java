package com.linkedin.thirdeye.task;

import java.io.File;
import java.io.PrintWriter;
import java.util.Collection;

import org.joda.time.DateTime;

import com.google.common.collect.ImmutableMultimap;
import com.linkedin.thirdeye.impl.storage.DataUpdateManager;

import io.dropwizard.servlets.tasks.Task;

public class ExpireTask extends Task {

  private final DataUpdateManager dataUpdateManager;

  public ExpireTask(DataUpdateManager dataUpdateManager)
  {
    super("expire");
    this.dataUpdateManager = dataUpdateManager;
  }

  @Override
  public void execute(ImmutableMultimap<String, String> params, PrintWriter printWriter) throws Exception {

    Collection<String> collectionParam = params.get("collection");
    Collection<String> scheduleParam = params.get("schedule");
    Collection<String> minTimeParam = params.get("minTime");
    Collection<String> maxTimeParam = params.get("maxTime");

    if (collectionParam == null || collectionParam.isEmpty() || scheduleParam == null || scheduleParam.isEmpty() ||
        minTimeParam == null || minTimeParam.isEmpty() || maxTimeParam == null || maxTimeParam.isEmpty())
    {
      throw new IllegalArgumentException("Must provide collection, schedule, minTime and maxTime");
    }
    String collection = collectionParam.iterator().next();
    String schedule = scheduleParam.iterator().next();
    DateTime minTime = DateTime.parse(minTimeParam.iterator().next());
    DateTime maxTime = DateTime.parse(maxTimeParam.iterator().next());

    dataUpdateManager.deleteData(collection, schedule, minTime, maxTime);

  }

}
