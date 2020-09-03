package mikufan.cx.vocadb_pv_downloader.entity;

import lombok.Value;

/**
 * Entity for indicating the result of downloading
 * @author CX无敌
 */
@Value
public class DownloadStatus {
  DownloadStatusEnum status;
  String description;
}
