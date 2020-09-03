package mikufan.cx.vocadb_pv_task_producer.config.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * entity class to store user config read from yaml file that passed from command line
 * @author CX无敌
 */
@Builder @Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserConfig {
//  /**
//   * the list id, download video from which VocaDB fav list
//   */
//  private final int listId;
//
//  /**
//   * give a name for this task, not a critical field but could be useful for human readability
//   */
//  @NonNull private final String taskName;
//
//  /**
//   * the json file, where to store or update the task,
//   */
//  @NonNull private final Path taskJsonFile;
//
//  /**
//   * the json file as well, to store all info about songs in the task.
//   * this is useful for other components in the whole Project-VD later
//   */
//  @NonNull private final Path referenceJsonFile;
//
//  /**
//   * do you prefer youtube over niconico, or vise area, tell me your preference
//   */
//  @NonNull private final ImmutableList<String> pvPerfOrd;

  /**
   * your own user-agent, includes VocaDB username if you can
   */
  @JsonProperty
  @NonNull private String userAgent;
}
