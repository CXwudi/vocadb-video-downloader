package mikufan.cx.project_vd_common_util.jackson.poc;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * As {@link DummyIndependent} works even with boolean field, if "-parameters" flag is turned on in javac,
 * but a class with just one field will fail. <br/>
 * A workaround is to add a {@link NoArgsConstructor} with force = true and access = private.
 * Another workaround is to have a lombok.config file in the ROOT folder of the WHOLE project with following content:
 * """
 * lombok.anyConstructor.addConstructorProperties=true
 * """
 * so that lombok can generate {@link java.beans.ConstructorProperties} for a constructor
 */
@Getter
@ToString
@AllArgsConstructor
//@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class SingleFieldDummy {
  private final String str;
}
