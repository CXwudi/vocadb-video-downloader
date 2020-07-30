package mikufan.cx.vocadb_pv_task_producer.main;

import mikufan.cx.common_vocaloid_entity.pv.service.PvServiceStr;
import mikufan.cx.common_vocaloid_entity.vocadb.models.PVContract;
import mikufan.cx.common_vocaloid_entity.vocadb.models.SongForApiContract;
import mikufan.cx.project_vd_common_util.pv_service.SupportedPvServices;
import org.eclipse.collections.api.factory.Lists;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class SongInfoValidatorTest {

  private final SongInfoValidator validator = new SongInfoValidator();

  //TODO: complete it
  @Test
  void shouldFailIfNoPv() {
    var song = SongForApiContract.builder().id(1234).name("Fake Vocaloid Song").build();
    var res = validator.validate(song);
    assertTrue(res.isPresent());
    assertTrue(res.get().contains("no"));
  }

  @Test
  void shouldFailIfPvUnavailable() {
    var song = SongForApiContract.builder().id(1234).name("Fake Vocaloid Song")
        .pvs(Lists.mutable.of(
            PVContract.builder().name("Deleted PV").disabled(true).build()
        )).build();
    var res = validator.validate(song);
    assertTrue(res.isPresent());
    assertTrue(res.get().contains("not accessible"));
  }

  @Test
  void shouldFailIfPvUnsupported() {
    var serviceName = PvServiceStr.SOUNDCLOUD;
    var song = SongForApiContract.builder().id(1234).name("Fake Vocaloid Song")
        .pvs(Lists.mutable.of(
            PVContract.builder().name("a PV from " + serviceName).disabled(false).service(serviceName).build()
        )).build();
    var res = validator.validate(song);
    assertTrue(res.isPresent());
    assertTrue(res.get().contains("not able to download"));
  }

  @Test
  void passingScenario(){
    SupportedPvServices.getSupportedPvServices().forEach(serviceName -> {
      var song = SongForApiContract.builder().id(1234).name("Fake Vocaloid Song")
          .pvs(Lists.mutable.of(
              PVContract.builder().name("a PV from " + serviceName).disabled(false).service(serviceName).build()
          )).build();
      var res = validator.validate(song);
      assertTrue(res.isEmpty());
    });

  }
}