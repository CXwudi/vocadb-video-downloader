package mikufan.cx.project_vd_common_util.string;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor
@Slf4j
public class FileNameNormalizer {

  public static String removeIllegalChars(String fileName){
    return fileName
        .replace("/", "-")
        .replace("\\", "-")

        .replace("? ", " ")
        .replace("* ", " ")
        .replace(": ", " ")

        .replace("?", " ")
        .replace("*", " ")
        .replace(":", " ");
  }
}
