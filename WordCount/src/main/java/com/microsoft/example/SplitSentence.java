package com.microsoft.example;

import java.text.BreakIterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class SplitSentence extends BaseBasicBolt {
  private static final Logger LOG = LoggerFactory.getLogger(SplitSentence.class);

  @Override
  public void execute(Tuple tuple, BasicOutputCollector collector) {
    String sentence = tuple.getString(0);

    BreakIterator boundary=BreakIterator.getWordInstance();
    boundary.setText(sentence);

    int start=boundary.first();

    for (int end=boundary.next(); end != BreakIterator.DONE; start=end, end=boundary.next()) {
      String word=sentence.substring(start,end);
      word=word.replaceAll("\\s+","");

      if (!word.equals("")) {
        collector.emit(new Values(word));
        WordCountLogger.EVENT("EMITTING-WORD", word);
      }
    }
  }

  @Override
  public void declareOutputFields(OutputFieldsDeclarer declarer) {
    declarer.declare(new Fields("word"));
  }
}