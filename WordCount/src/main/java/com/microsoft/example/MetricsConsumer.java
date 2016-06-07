package com.microsoft.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.json.JSONObject;

import backtype.storm.metric.api.IMetricsConsumer;
import backtype.storm.task.IErrorReporter;
import backtype.storm.task.TopologyContext;

import java.util.Map;
import java.util.Collection;

public class MetricsConsumer implements IMetricsConsumer {
	public static final Logger LOG = LoggerFactory.getLogger("METRICS_LOG");

    @Override
    public void prepare(Map stormConf, Object registrationArgument, TopologyContext context, IErrorReporter errorReporter) { }

    @Override
    public void handleDataPoints(TaskInfo taskInfo, Collection<DataPoint> dataPoints) {
        JSONObject json = new JSONObject();
        json.put("TASK_ID", taskInfo.srcTaskId);
        json.put("SRC_COMPONENT_ID", taskInfo.srcComponentId);
        json.put("WORKER_ADDRESS", taskInfo.srcWorkerHost + ":" + taskInfo.srcWorkerPort);
        json.put("INDEX", "METRIC");

        for (DataPoint p : dataPoints) {
            json.put(p.name, p.value);
            LOG.info(json.toString());
        }
    }

    @Override
    public void cleanup() { }
}