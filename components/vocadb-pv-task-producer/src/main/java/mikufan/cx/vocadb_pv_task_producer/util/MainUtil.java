package mikufan.cx.vocadb_pv_task_producer.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mikufan.cx.project_vd_common_util.exception.ThrowableConsumer;
import mikufan.cx.project_vd_common_util.exception.ThrowableFunction;
import mikufan.cx.vocadb_pv_task_producer.util.exception.VocaDbPvTaskException;
import mikufan.cx.vocadb_pv_task_producer.util.exception.VocaDbPvTaskRCI;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.eclipse.collections.api.block.procedure.Procedure;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * util for {@link mikufan.cx.vocadb_pv_task_producer.Main} class
 *
 * @author CX无敌
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MainUtil {


  /**
   * Warp a piece of code under the existence of a http client
   * @param userAgent user agent that the http client used
   * @param withFunction the function to be called with the input of the created http client
   * @return whatever withFunction returns
   * @throws VocaDbPvTaskException whatever {@link VocaDbPvTaskException} that withFunction thrown or this function thrown
   */
  protected static <T> T withHttpClient(String userAgent, ThrowableFunction<CloseableHttpClient, T> withFunction) throws VocaDbPvTaskException {
    try (var httpClient = HttpClients.custom()
        .setUserAgent(userAgent)
        .build()){
      log.debug("universal http client created with user-agent {}", userAgent);
      return withFunction.toFunction().apply(httpClient);
    } catch (IOException e){
      throw new VocaDbPvTaskException(VocaDbPvTaskRCI.MIKU_TASK_301, "Fail to handle HttpClient closable resource", e);
    }
  }

  /**
   * a small helper function to convert {@link ThrowableConsumer} to eclipse collection's {@link Procedure}
   */
  protected static <T> Procedure<T> makeThrowableProcedure(ThrowableConsumer<T> consumer) {
    return t -> consumer.toConsumer().accept(t);
  }


  protected static void createDir(Path dir) throws VocaDbPvTaskException {
    if (Files.notExists(dir)) {
      try {
        Files.createDirectories(dir);
      } catch (IOException e) {
        throw new VocaDbPvTaskException(VocaDbPvTaskRCI.MIKU_TASK_310, "Failed to create error directory in " + dir, e);
      }
    }
  }
}
