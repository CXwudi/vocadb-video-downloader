package mikufan.cx.common.entity.pv.util;

import mikufan.cx.common.entity.common.PvService;
import mikufan.cx.common.entity.pv.VocaDbPv;
import mikufan.cx.common.entity.vocadb.ResponseSongList;
import mikufan.cx.common.util.jackson.ObjMapperUtil;
import org.eclipse.collections.api.list.MutableList;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

public class SamplePvsGenerator {

  /** this generate multiple IdentifiedPv with repeated songIds */
  public static MutableList<VocaDbPv> generateVocadbPvs() {
    var file = new File("src/test/resources/vocadb/songListNeededResponse.json");
    ResponseSongList response = null;
    try {
      response = ObjMapperUtil.createDefaultForReadOnly().readValue(file, ResponseSongList.class);
    } catch (IOException e) {
      fail(e);
    }
    assertNotNull(response);
    var pvs = response.getItems().toList().flatCollect(
        item ->
            item.getSong().getPvs().select(pvItem -> PvService.contain(pvItem.getService()))
                .collect(pvItem -> new VocaDbPv(
                        pvItem.getPvId(),
                        PvService.enumOf(pvItem.getService()),
                        pvItem.getName(),
                        item.getSong().getId()
                    )
                )

    );
    return pvs;
  }
}
