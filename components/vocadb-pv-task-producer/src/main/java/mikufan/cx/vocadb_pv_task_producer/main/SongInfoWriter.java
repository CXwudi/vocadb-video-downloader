package mikufan.cx.vocadb_pv_task_producer.main;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mikufan.cx.common_vocaloid_entity.vocadb.models.SongForApiContract;
import mikufan.cx.project_vd_common_entity.FilePostFix;
import mikufan.cx.project_vd_common_entity.failure.FailedSong;
import mikufan.cx.project_vd_common_util.io.JacksonPojoTransformer;
import mikufan.cx.project_vd_common_util.string.FileNameNormalizer;
import mikufan.cx.vocadb_pv_task_producer.util.exception.VocaDbPvTaskException;
import mikufan.cx.vocadb_pv_task_producer.util.exception.VocaDbPvTaskRCI;

import java.io.IOException;
import java.nio.file.Path;

/**
 * writing song info back to destination dir
 * warning: this class doesn't contain test cases
 * @author CX无敌
 */
@Slf4j @RequiredArgsConstructor
public class SongInfoWriter {
  private final JacksonPojoTransformer<SongForApiContract> songWriter = JacksonPojoTransformer.createWithDefaultMapper(SongForApiContract.class);
  private final JacksonPojoTransformer<FailedSong> failureWriter = JacksonPojoTransformer.createWithDefaultMapper(FailedSong.class);

  public boolean writeSongInfo(SongForApiContract song, Path outputDir) throws VocaDbPvTaskException {
    var fileName = FileNameNormalizer.removeIllegalChars(song.getName()) + FilePostFix.SONG_INFO + ".json";
    var outputFile = outputDir.resolve(fileName);
    try {
      return songWriter.write(song, outputFile);
    } catch (IOException e) {
      throw new VocaDbPvTaskException(VocaDbPvTaskRCI.MIKU_TASK_308, "fail to write " + fileName + " to output dir " + outputDir, e);
    }
  }

  public boolean writeError(FailedSong failedSong, Path errDir) throws VocaDbPvTaskException {
    var fileName = FileNameNormalizer.removeIllegalChars(failedSong.getFailedObj().getName()) + FilePostFix.SONG_INFO_ERR + ".json";
    var outputFile = errDir.resolve(fileName);
    try {
      return failureWriter.write(failedSong, outputFile);
    } catch (IOException e) {
      throw new VocaDbPvTaskException(VocaDbPvTaskRCI.MIKU_TASK_308, "fail to write " + fileName + " to error dir " + errDir, e);
    }
  }
}
