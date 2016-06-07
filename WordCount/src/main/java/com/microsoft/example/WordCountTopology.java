package com.microsoft.example;

import java.util.List;
import java.util.Map;

import backtype.storm.Config;
import backtype.storm.StormSubmitter;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
import backtype.storm.generated.KillOptions;
import backtype.storm.generated.Nimbus.Client;
import backtype.storm.generated.TopologySummary;
import backtype.storm.utils.NimbusClient;
import backtype.storm.utils.Utils;

import com.microsoft.example.MetricsConsumer;

public class WordCountTopology {
  public static void main(String[] args) throws Exception {
    TopologyBuilder builder = new TopologyBuilder();

    builder.setSpout("spout", new RandomSentenceSpout(), 5);

    builder.setBolt("split", new SplitSentence(), 8).shuffleGrouping("spout");

    builder.setBolt("count", new WordCount(), 12).fieldsGrouping("split", new Fields("word"));

    Config conf = new Config();
    conf.setDebug(false);
    conf.registerMetricsConsumer(MetricsConsumer.class, 1);
    conf.setNumWorkers(4);

    String topologyName = "word-count-topology";

    try {
      Map config = Utils.readStormConfig();
      Client client = NimbusClient.getConfiguredClient(config).getClient();
      KillOptions killOpts = new KillOptions();
      killOpts.set_wait_secs(0);
      client.killTopologyWithOpts(topologyName, killOpts);
    } catch (Exception e) {

    }

    StormSubmitter.submitTopology(topologyName, conf, builder.createTopology());
  }
}