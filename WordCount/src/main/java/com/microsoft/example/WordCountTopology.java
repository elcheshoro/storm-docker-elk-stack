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

public class WordCountTopology {
  //Entry point for the topology
  public static void main(String[] args) throws Exception {
    //Used to build the topology
    TopologyBuilder builder = new TopologyBuilder();
    //Add the spout, with a name of 'spout'
    //and parallelism hint of 5 executors
    builder.setSpout("spout", new RandomSentenceSpout(), 5);
    //Add the SplitSentence bolt, with a name of 'split'
    //and parallelism hint of 8 executors
    //shufflegrouping subscribes to the spout, and equally distributes
    //tuples (sentences) across instances of the SplitSentence bolt
    builder.setBolt("split", new SplitSentence(), 8).shuffleGrouping("spout");
    //Add the counter, with a name of 'count'
    //and parallelism hint of 12 executors
    //fieldsgrouping subscribes to the split bolt, and
    //ensures that the same word is sent to the same instance (group by field 'word')
    builder.setBolt("count", new WordCount(), 12).fieldsGrouping("split", new Fields("word"));

    //new configuration
    Config conf = new Config();
    conf.setDebug(false);

    //If there are arguments, we are running on a cluster

    //Otherwise, we are running locally
    //Cap the maximum number of executors that can be spawned
    //for a component to 3
    String topologyName = "word-count-topology";

    try {
      Map config = Utils.readStormConfig();
      Client client = NimbusClient.getConfiguredClient(config).getClient();
      KillOptions killOpts = new KillOptions();
      killOpts.set_wait_secs(0);
      client.killTopologyWithOpts(topologyName, killOpts);
    } catch (Exception e) {

    }

    conf.setNumWorkers(4);
    
    //submit the topology
    StormSubmitter.submitTopology(topologyName, conf, builder.createTopology());
  }
}