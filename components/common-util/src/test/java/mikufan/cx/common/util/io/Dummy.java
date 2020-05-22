package mikufan.cx.common.util.io;

import lombok.Value;

@Value
public class Dummy {
  String name;
  int id;

  @Override
  public String toString() {
    return
        "Dummy{" +
            "name = '" + name + '\'' +
            ",id = '" + id + '\'' +
            "}";
  }
}
