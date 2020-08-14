package mikufan.cx.project_vd_common_util.string;

import mikufan.cx.common_vocaloid_entity.vocadb.models.SongForApiContract;

/**
 * @author CX无敌
 */
public class FileNameBuilder {

  /**
   * build a file name for a song base on 【vocals】songName【producers】 format
   * @param song the song
   * @return file name
   */
  public static String build(SongForApiContract song){
    var artists = song.getArtistString().split("feat\\.");
    var vocals = artists[1].trim();
    var producers = artists[0].trim();
    var songName = song.getName();
    return FileNameNormalizer.removeIllegalChars(String.format("【%s】%s【%s】", vocals, songName, producers));
  }
}
