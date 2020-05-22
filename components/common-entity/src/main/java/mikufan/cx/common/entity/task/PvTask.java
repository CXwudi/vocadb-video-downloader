package mikufan.cx.common.entity.task;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import mikufan.cx.common.entity.task.pv.AbstractPv;
import mikufan.cx.common.entity.task.pv.FailedPv;
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
public class PvTask<P extends AbstractPv> implements Task<P> {
  //we use <P extends AbstractPv> to allow either storing generic AbstractPv or just one subtype of AbstractPv

  @Getter @NonNull
  @JsonProperty("folderName")
  protected String folderName;

  @JsonProperty("todo")
  protected MutableSortedSet<P> todo = SortedSets.mutable.empty();

  @JsonProperty("done")
  protected MutableSortedSet<P> done = SortedSets.mutable.empty();

  @JsonProperty("fail")
  protected MutableSortedSet<FailedPv> fail = SortedSets.mutable.with(Comparator.comparing(FailedPv::getPv));


  @Override
  @JsonIgnore
  public MutableSortedSet<P> getTodos() {
    return todo;
  }

  @Override
  @JsonIgnore
  public MutableSortedSet<P> getDones() {
    return done;
  }

  @JsonIgnore
  public MutableSortedSet<FailedPv> getErrors() {
    return fail;
  }

  /**
   * add the pv to done list, remove it from other lists if presents
   * @return {@code true} if pv added
   */
  public boolean markDone(P pv){
    todo.remove(pv);
    fail.removeIf(failedPv -> failedPv.getPv().equals(pv));
    return done.add(pv);
  }

  /**
   * add the pv to tudo list, remove it from other lists if presents
   * @return {@code true} if pv added
   */
  public boolean markTodo(P pv){
    done.remove(pv);
    fail.removeIf(failedPv -> failedPv.getPv().equals(pv));
    return todo.add(pv);
  }

  /**
   * add the pv to error list, remove it from other lists if presents
   * @return {@code true} if pv added
   */
  public boolean markError(P pv, String reason){
    done.remove(pv);
    todo.remove(pv);
    return fail.add(new FailedPv(pv, reason));
  }
}
