package mikufan.cx.vocadb_pv_downloader.entity;

/**
 * Different type of status
 * @author CX无敌
 */
public enum DownloadStatusEnum {
  /**
   * Yeah, we made it. <br/>
   * But during the validation, this means validation hasn't completed yet. Subclasses will continue the validation
   */
  SUCCESS,
  /**
   * soft failure, not sure download success or not.
   */
  UNKNOWN,
  /**
   * hard failure, something wrong before or during the download process
   */
  FAIL_DOWNLOAD;


  public static boolean isFailed(DownloadStatusEnum statusEnum){
    return statusEnum == FAIL_DOWNLOAD;
  }

  public static boolean isSuccess(DownloadStatusEnum statusEnum){
    return statusEnum == SUCCESS;
  }

  public static boolean isUnknown(DownloadStatusEnum statusEnum){
    return statusEnum == UNKNOWN;
  }
}
