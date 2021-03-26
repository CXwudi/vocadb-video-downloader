package mikufan.cx.vocadb_pv_task_producer.services;

import lombok.SneakyThrows;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public class WithHttpClientSupport {
  protected static final String testedUserAgent = "Mozilla/5.0 (compatible; CXwudi's VSongListsFetcher for Testing propose/Alpha 1.0; +https://github.com/CXwudi)";
  protected static CloseableHttpClient testedHttpClient;

  @BeforeAll
  static void createClient(){
    testedHttpClient = HttpClients.custom().setUserAgent(testedUserAgent).build();
  }

  @AfterAll
  @SneakyThrows
  static void closeClient(){
    testedHttpClient.close();
  }
}
