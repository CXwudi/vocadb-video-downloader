package mikufan.cx.project_vd_common_entity.failure;

import lombok.NonNull;
import mikufan.cx.common_vocaloid_entity.label.FailedObject;
import mikufan.cx.common_vocaloid_entity.vocadb.models.SongForApiContract;

/**
 * An warpper of {@link SongForApiContract} with a reason describing what's wrong with it
 * @author CX无敌
 */
public class FailedSong extends FailedObject<SongForApiContract> {

  public FailedSong(@NonNull SongForApiContract failedObj, @NonNull String reason) {
    super(failedObj, reason);
  }
}
