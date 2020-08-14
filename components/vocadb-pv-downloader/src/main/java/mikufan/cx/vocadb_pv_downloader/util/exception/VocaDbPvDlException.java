package mikufan.cx.vocadb_pv_downloader.util.exception;

import mikufan.cx.common_vocaloid_entity.exception.BaseVocalException;

/**
 * @author CX无敌
 */
public class VocaDbPvDlException extends BaseVocalException {

  public VocaDbPvDlException(VocaDbPvDlRCI rci, String message) {
    super(rci, message);
  }

  public VocaDbPvDlException(VocaDbPvDlRCI rci, String message, Throwable cause) {
    super(rci, message, cause);
  }
}
