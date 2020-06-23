package mikufan.cx.vocadb_pv_task_producer.config.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SystemConfigTest {
  @Test
  void testGet(){
    assertEquals(50, SystemConfig.INSTANCE.getMaxResult());
  }

}