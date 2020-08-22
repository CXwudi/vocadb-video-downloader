package mikufan.cx.project_vd_common_util.jackson.poc;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * three key points to make no annotation jackson works are
 * <p>
 * 1. getters
 * 2. an all arg constructors with either {@link java.beans.ConstructorProperties}
 * or an all arg constructors without {@link java.beans.ConstructorProperties} but the project is compiled with {@code -parameters}
 * 3. name of the fields, getters and constructor parameters must be consistent.
 * </p>
 *
 * so if we are using lombok, simply adding {@link AllArgsConstructor} and {@link Getter}
 * and then add a {@code lombok.anyConstructor.addConstructorProperties=true}
 * to lombok.config file in project root directory will make everything work
 */
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class DummyIndependent {

  private final int day;

  private final String formatted;

  @JsonProperty("isEmpty") // jackson strip out is/get from getter, so make sure add an "is" back to boolean
  private final boolean isEmpty;

  private final boolean stupid;

  private final int month;

  private final int year;
}
