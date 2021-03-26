package mikufan.cx.vocadb_pv_task_producer.config.validator;

import lombok.NonNull;
import mikufan.cx.vocadb_pv_task_producer.config.entity.UserConfig;
import mikufan.cx.vocadb_pv_task_producer.util.exception.VocaDbPvTaskException;
import mikufan.cx.vocadb_pv_task_producer.util.exception.VocaDbPvTaskRCI;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * a class containing external validation for {@link UserConfig}
 * @author CX无敌
 */
@Component @Lazy
public class UserConfigValidator {

  public void validate(@NonNull UserConfig config) throws VocaDbPvTaskException {
    if (StringUtils.isEmpty(config.getUserAgent())){
      throw new VocaDbPvTaskException(VocaDbPvTaskRCI.MIKU_TASK_008, "User-agent can not be empty");
    }
  }
}
