package mikufan.cx.project_vd_common_util.io;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FilePostFix {
  private final static String SEPARATOR = "-";
  public static final String SONG_INFO = SEPARATOR + "songInfo";
  public static final String SONG_INFO_ERR = SONG_INFO + SEPARATOR + "err";
  public static final String VIDEO = SEPARATOR + "pv";
  public static final String AUDIO = SEPARATOR + "audio";
  public static final String THUMBNAIL = SEPARATOR + "thumbnail";
  public static final String RESOURCES_LABEL = SEPARATOR + "resources";
}
