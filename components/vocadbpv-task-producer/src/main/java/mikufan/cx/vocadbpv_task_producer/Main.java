package mikufan.cx.vocadbpv_task_producer;

import lombok.extern.slf4j.Slf4j;
import mikufan.cx.vocadbpv_task_producer.config.parser.ArgParser;

@Slf4j
public class Main {
  public static void main(String[] args) {
    var appConfig = ArgParser.parse(args);
  }
}
