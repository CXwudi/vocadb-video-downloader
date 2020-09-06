package mikufan.cx.vocadb_pv_downloader.entity;

/**
 * Different type of status
 * @author CX无敌
 */
public enum DownloadStatusEnum {
  SUCCESS,
  /**
   * something wrong even before the download process
   */
  FAIL_INITIAL,
  /**
   * hard failure, something wrong during the download process
   */
  FAIL_DOWNLOAD,
  /**
   * soft failure, don't know if the download success or not
   */
  UNKNOWN_STATUS
}
