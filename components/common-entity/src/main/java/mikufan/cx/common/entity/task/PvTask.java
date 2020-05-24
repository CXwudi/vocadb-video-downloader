package mikufan.cx.common.entity.task;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import mikufan.cx.common.entity.pv.FailedPv;
import mikufan.cx.common.entity.pv.Pv;
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
public class PvTask implements Task<Pv> {
  //we use <P extends AbstractPv> to allow either storing generic AbstractPv or just one subtype of AbstractPv

  @Getter @NonNull
  @JsonProperty("folderName")
  protected String folderName;

  @JsonProperty("todo")
  protected MutableSortedSet<Pv> todo = SortedSets.mutable.empty();

  @JsonProperty("done")
  protected MutableSortedSet<Pv> done = SortedSets.mutable.empty();

  @JsonProperty("fail")
  protected MutableSortedSet<FailedPv> fails = SortedSets.mutable.with(Comparator.comparing(FailedPv::getPv));


  @Override
  @JsonIgnore
  public MutableSortedSet<Pv> getTodo() {
    return todo;
  }

  @Override
  @JsonIgnore
  public MutableSortedSet<Pv> getDone() {
    return done;
  }

  @JsonIgnore
  public MutableSortedSet<FailedPv> getFails() {
    return fails;
  }

  /**
   * add the pv to done list, remove it from other lists if presents
   * @return {@code true} if pv added
   */
  public boolean markDone(Pv pv){
    todo.remove(pv);
    fails.removeIf(failedPv -> failedPv.getPv().equals(pv));
    return done.add(pv);
  }

  /**
   * add the pv to tudo list, remove it from other lists if presents
   * @return {@code true} if pv added
   */
  public boolean markTodo(Pv pv){
    done.remove(pv);
    fails.removeIf(failedPv -> failedPv.getPv().equals(pv));
    return todo.add(pv);
  }

  /**
   * add the pv to error list, remove it from other lists if presents
   * @return {@code true} if pv added
   */
  public boolean markError(Pv pv, String reason){
    done.remove(pv);
    todo.remove(pv);
    return fails.add(new FailedPv(pv, reason));
  }
}
