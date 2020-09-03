package mikufan.cx.vocadb_pv_downloader.entity;

/**
 * Different type of status
 * @author CX无敌
 */
public enum DownloadStatusEnum {
  SUCCESS,
  FAIL_INITIAL, FAIL_DOWNLOAD, //hard failure
  UNKNOWN_STATUS; //soft failure, don't know if the download success or not
}
