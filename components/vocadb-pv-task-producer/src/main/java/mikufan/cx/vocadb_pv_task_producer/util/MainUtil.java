package mikufan.cx.vocadb_pv_task_producer.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mikufan.cx.project_vd_common_util.exception.ThrowableFunction;
import mikufan.cx.vocadb_pv_task_producer.util.exception.VocaDbPvTaskException;
import mikufan.cx.vocadb_pv_task_producer.util.exception.VocaDbPvTaskRCI;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;

import java.io.IOException;

/**
 * util for {@link mikufan.cx.vocadb_pv_task_producer.Main} class
 *
 * @author CX无敌
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MainUtil {


  /**
   * Warp a piece of code under the existence of a http client
   * @param userAgent user agent that the http client used
   * @param withFunction the function to be called with the input of the created http client
   * @return whatever withFunction returns
   * @throws VocaDbPvTaskException whatever {@link VocaDbPvTaskException} that withFunction thrown or this function thrown
   */
  public static <T> T withHttpClient(String userAgent, ThrowableFunction<CloseableHttpClient, T> withFunction) throws VocaDbPvTaskException {
    try (var httpClient = HttpClients.custom()
        .setUserAgent(userAgent)
        .build()){
      log.debug("universal http client created with user-agent {}", userAgent);
      return withFunction.toFunction().apply(httpClient);
    } catch (IOException e){
      throw new VocaDbPvTaskException(VocaDbPvTaskRCI.MIKU_TASK_301, "Fail to handle HttpClient closable resource", e);
    }
  }
}
