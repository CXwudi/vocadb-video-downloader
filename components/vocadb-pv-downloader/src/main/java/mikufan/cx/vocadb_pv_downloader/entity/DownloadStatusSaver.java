package mikufan.cx.vocadb_pv_downloader.entity;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.list.mutable.FastList;

/**
 * Save different {@link DownloadStatus}, and return best status representing among them
 * @author CX无敌
 */
@Slf4j
public class DownloadStatusSaver {
  private final MutableList<DownloadStatus> successList;
  private final MutableList<DownloadStatus> unknownList;
  private final MutableList<DownloadStatus> failList;

  public DownloadStatusSaver(int total) {
    successList = new FastList<>(total);
    unknownList = new FastList<>(total/2);
    failList = new FastList<>(total/2);
  }

  public boolean add(DownloadStatus status){
    var statusEnum = status.getStatus();
    if (DownloadStatusEnum.isFailed(statusEnum)){
      return failList.add(status);
    } else if (DownloadStatusEnum.isUnknown(statusEnum)){
      return unknownList.add(status);
    } else if (DownloadStatusEnum.isSuccess(statusEnum)){
      return successList.add(status);
    } else {
      log.warn("Unknown status {}", status);
      return false;
    }
  }

  public DownloadStatus getBestRepresentedStatus(){
    if (failList.notEmpty()){
      return new DownloadStatus(DownloadStatusEnum.FAIL_DOWNLOAD, aggregateDescriptions(failList));
    } else if (unknownList.notEmpty()){
      return new DownloadStatus(DownloadStatusEnum.UNKNOWN, aggregateDescriptions(unknownList));
    } else {
      return new DownloadStatus(DownloadStatusEnum.SUCCESS, aggregateDescriptions(successList));
    }
  }

  private String aggregateDescriptions(MutableList<DownloadStatus> list){
    return list.makeString("All descriptions: [\"", "\", \"", "\"]");
  }
}
