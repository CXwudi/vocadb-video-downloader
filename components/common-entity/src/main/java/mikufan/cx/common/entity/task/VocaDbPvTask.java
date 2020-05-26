package mikufan.cx.common.entity.task;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import mikufan.cx.common.entity.pv.FailedPv;
import mikufan.cx.common.entity.pv.VocaDbPv;
import org.eclipse.collections.api.factory.SortedSets;
import org.eclipse.collections.api.set.sorted.MutableSortedSet;

import java.util.Comparator;

/**
 * a task for manipulating around Pvs
 * @author CX无敌
 */
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor
public class VocaDbPvTask implements Task<VocaDbPv> {
  //we use <P extends AbstractPv> to allow either storing generic AbstractPv or just one subtype of AbstractPv

  @Getter @NonNull
  @JsonProperty("folderName")
  protected String folderName;

  @JsonProperty("todo")
  protected MutableSortedSet<VocaDbPv> todo = SortedSets.mutable.empty();

  @JsonProperty("done")
  protected MutableSortedSet<VocaDbPv> done = SortedSets.mutable.empty();

  @JsonProperty("fail")
  protected MutableSortedSet<FailedPv> fails = SortedSets.mutable.with(Comparator.comparing(FailedPv::getPv));


  @Override
  @JsonIgnore
  public MutableSortedSet<VocaDbPv> getTodo() {
    return todo;
  }

  @Override
  @JsonIgnore
  public MutableSortedSet<VocaDbPv> getDone() {
    return done;
  }

  @JsonIgnore
  public MutableSortedSet<FailedPv> getFails() {
    return fails;
  }

  /**
   * add the vocaDbPv to done list, remove it from other lists if presents
   * @return {@code true} if vocaDbPv added
   */
  public boolean markDone(VocaDbPv vocaDbPv){
    todo.remove(vocaDbPv);
    fails.removeIf(failedPv -> failedPv.getPv().equals(vocaDbPv));
    return done.add(vocaDbPv);
  }

  /**
   * add the vocaDbPv to tudo list, remove it from other lists if presents
   * @return {@code true} if vocaDbPv added
   */
  public boolean markTodo(VocaDbPv vocaDbPv){
    done.remove(vocaDbPv);
    fails.removeIf(failedPv -> failedPv.getPv().equals(vocaDbPv));
    return todo.add(vocaDbPv);
  }

  /**
   * add the vocaDbPv to error list, remove it from other lists if presents
   * @return {@code true} if vocaDbPv added
   */
  public boolean markError(VocaDbPv vocaDbPv, String reason){
    done.remove(vocaDbPv);
    todo.remove(vocaDbPv);
    return fails.add(new FailedPv(vocaDbPv, reason));
  }
}
