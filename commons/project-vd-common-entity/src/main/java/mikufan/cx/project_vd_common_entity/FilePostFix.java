package mikufan.cx.project_vd_common_entity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FilePostFix {
  private final static String SAPERATOR = "-";
  public static final String SONG_INFO = SAPERATOR + "songInfo";
  public static final String SONG_INFO_ERR = SONG_INFO + SAPERATOR + "err";
  public static final String VIDEO = SAPERATOR + "pv";
  public static final String AUDIO = SAPERATOR + "audio";
  public static final String THUMBNAIL = SAPERATOR + "thumbnail";
  public static final String RESOURCES_LABEL = SAPERATOR + "resources";
}
