package mikufan.cx.vocadb_pv_downloader.entity.downloaders.root;

import mikufan.cx.vocadb_pv_downloader.entity.DownloadStatus;
import mikufan.cx.vocadb_pv_downloader.entity.downloaders.root.annotations.Mixinable;

import java.nio.file.Path;

/**
 * Define base behaviors of a downloader who break down the
 * {@link BasePvDownloader#download(String, Path, String)} method by
 * following 3 methods that subclasses are required to implement:
 *
 * <li>{@link BaseRequestDownloadValidatePvDownloader#buildRequest(java.lang.String, java.nio.file.Path, java.lang.String)}</li>
 * to warp all info needed for downloading the PV into a {@link Req request POJO}
 * <li>{@link BaseRequestDownloadValidatePvDownloader#computeResponse(Req)}</li>
 * which is where the real downloading job done, and return a {@link Rsp response POJO}
 * <li>{@link BaseRequestDownloadValidatePvDownloader#validateStatus(String, Path, String, Rsp, Exception)}</li>
 * check and tell if the download success
 *
 * <h3>Implementation Notice:</h3>
 * It's not a require to be thread-safe (but we recommend to do so). <br/>
 * However all 3 methods should be <b>stateless</b>! <br/>
 *
 * @param <Req> the type of the request POJO
 * @param <Rsp> the type of the response POJO
 * @author CX无敌
 */
public interface BaseRequestDownloadValidatePvDownloader<Req, Rsp> extends BasePvDownloader {

  /**
   * Implement the skeleton code of how download is done by these 3 abstract steps:
   *
   * <li>{@link BaseRequestDownloadValidatePvDownloader#buildRequest(java.lang.String, java.nio.file.Path, java.lang.String)}</li>
   * to warp all info needed for downloading the PV into a {@link Req request POJO}
   * <li>{@link BaseRequestDownloadValidatePvDownloader#computeResponse(Req)}</li>
   * which is where the real downloading job done, and return a {@link Rsp response POJO}
   * <li>{@link BaseRequestDownloadValidatePvDownloader#validateStatus(String, Path, String, Rsp, Exception)}</li>
   * check and tell if the download success
   *
   * @param url the url of where to watch the PV on website
   * @param dir in which directory is the pv file saved
   * @param fileName the file of the pv
   * @return {@link DownloadStatus} indicating success of not, if fail, a reason or a description is set
   * @throws InterruptedException if ctrl+c happens
   */
  @Override
  default DownloadStatus download(String url, Path dir, String fileName) throws InterruptedException {
    //interface can't have private variables :(
    var log = org.slf4j.LoggerFactory.getLogger(BaseRequestDownloadValidatePvDownloader.class);
    Req request = buildRequest(url, dir, fileName);

    Rsp response = null;
    Exception expThrown = null;
    try {
      response = computeResponse(request);
    } catch (InterruptedException e){
      // if the user send an interrupt signal then we better throw the same exception and stop the program NOW
      throw e;
    } catch (Exception e) {
      //some other things stop it from executing the request
      log.error("Damn!! An Exception occurs during downloading {} from {}", fileName, url, e);

      if (e.getCause() instanceof InterruptedException) {
        log.error("It's an InterruptedException wrapped inside", e.getCause());
        throw (InterruptedException) e.getCause();
      } else {
        expThrown = e;
      }
    }
    // otherwise, the download progress is finished, could be abnormally
    // so we need to check
    return validateStatus(url, dir, fileName, response, expThrown);
  }

  /**
   * build the request object that store all information need to download the pv
   * @param url pv url
   * @param dir where the pv file is saved
   * @param fileName the file of the pv
   * @return the request obj
   */
  @Mixinable(
      effect = "multiple mixin should stack up all the information from multiple req pojo to one pojo",
      orderEffect = "different order should define which information stack up first"
  )
  Req buildRequest(String url, Path dir, String fileName);

  /**
   * Once the request object is built, compute the response. <br/>
   * This is a real downloading code implementation,
   *
   * @param request the request
   * @return the respond in form of a response obj, should be non-null
   * @throws Exception if exception happened
   */
  Rsp computeResponse(Req request) throws Exception;

  /**
   * Then we exam the response object and check if we really successfully complete the download.
   * Implementation should handle the case where the an exception is thrown from
   * {@link BaseRequestDownloadValidatePvDownloader#computeResponse(Req)}
   *
   * @param url pv url
   * @param dir where the pv file is saved
   * @param fileName the file of the pv
   * @param response the response computed from {@link BaseRequestDownloadValidatePvDownloader#computeResponse(Req)}.
   *                 It could be null, especially when the computation abort abnormally.
   * @param exp the exception thrown from {@link BaseRequestDownloadValidatePvDownloader#computeResponse(Req)} if exist.
   * @return the status of this download process
   */
  @Mixinable(
      effect = "multiple mixin means the validation can be success or unknown if only all mixin success, and fails if any of mixin fails",
      orderEffect = "first mix first validation"
  )
  DownloadStatus validateStatus(String url, Path dir, String fileName, Rsp response, Exception exp);



}
