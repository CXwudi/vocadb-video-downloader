package mikufan.cx.vocadb_pv_downloader.services.downloaders.root;

import mikufan.cx.vocadb_pv_downloader.entity.DownloadStatus;
import mikufan.cx.vocadb_pv_downloader.util.exception.VocaDbPvDlException;
import mikufan.cx.vocadb_pv_downloader.util.exception.VocaDbPvDlRCI;

import java.nio.file.Path;

/**
 * Define base behaviors of a downloader who implements
 * {@link BasePvDownloader#download(String, Path, String)} by
 * these 3 steps that subclasses are required to implement:
 *
 * <li>{@link BaseRequestDownloadValidatePvDownloader#buildRequest(java.lang.String, java.nio.file.Path, java.lang.String)}</li>
 * to warp all info needed for downloading the PV into a request POJO
 * <li>{@link BaseRequestDownloadValidatePvDownloader#computeResponse(Req)}</li>
 * which is where the real downloading job done, and return a response POJO
 * <li>{@link BaseRequestDownloadValidatePvDownloader#validateStatus(String, Path, String, Rsp, Throwable)}</li>
 * check and make sure download success
 *
 * <h3>Implementation Notice:</h3>
 * It's not a require to be thread-safe (but we recommend to do so). <br/>
 * However all 3 methods should be <b>stateless</b>! <br/>
 * Subclasses may not purely use youtube-dl as a downloading engine,
 * they could, for example, use IDM to download the real PV url extracted from youtube-dl. <br/>
 * @author CX无敌
 */
public interface BaseRequestDownloadValidatePvDownloader<Req, Rsp> extends BasePvDownloader {

  /**
   * Implement the skeleton code of how download is done by these 3 abstract steps:
   *
   * <li>{@link BaseRequestDownloadValidatePvDownloader#buildRequest(java.lang.String, java.nio.file.Path, java.lang.String)}</li>
   * to warp all info needed for downloading the PV into a request POJO
   * <li>{@link BaseRequestDownloadValidatePvDownloader#computeResponse(Req)}</li>
   * which is where the real downloading job done, and return a response POJO
   * <li>{@link BaseRequestDownloadValidatePvDownloader#validateStatus(String, Path, String, Rsp, Throwable)}</li>
   * check and make sure download success
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
    Throwable throwable = null;
    try {
      response = computeResponse(request);
    } catch (InterruptedException e){
      // if the user send an interrupt signal then we better throw the same exception and stop the program NOW
      log.error("Ohh, an InterruptedException", e);
      throw e;
    } catch (Throwable e) {
      //some other things stop it from executing the request
      log.error("Damn!! An Exception occurs", e);

      if (e.getCause() instanceof InterruptedException) {
        throw (InterruptedException) e.getCause();
      } else {
        throwable = new VocaDbPvDlException(VocaDbPvDlRCI.MIKU_DL_301,
            "An exception occurs during downloading " + fileName + " from " + url + ", see \"Cause by:\"", e);
      }
    }
    // otherwise, the download progress is finished
    // but we need to check if the file is downloaded correctly
    return validateStatus(url, dir, fileName, response, throwable);
  }

  /**
   * build the request object that store all information need to download the pv
   * @param url pv url
   * @param dir where the pv file is saved
   * @param fileName the file of the pv
   * @return the request obj
   */
  Req buildRequest(String url, Path dir, String fileName);

  /**
   * Once the request object is built, compute the response. <br/>
   * This is a real downloading code implementation,
   *
   * @param request the request
   * @return the respond in form of a response obj, should be non-null
   * @throws Throwable if exception happened
   */
  Rsp computeResponse(Req request) throws Throwable;

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
   * @param throwable the exception thrown from {@link BaseRequestDownloadValidatePvDownloader#computeResponse(Req)} if exist.
   * @return the status of this download process
   */
  DownloadStatus validateStatus(String url, Path dir, String fileName, Rsp response, Throwable throwable);



}
