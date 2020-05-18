package org.mikufan.cx.common.entity.task.pv;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.eclipse.collections.api.factory.Sets;
import org.eclipse.collections.api.set.MutableSet;
import org.mikufan.cx.common.entity.task.Task;

/**
 * a task for manipulating around Pvs
 * @author CX无敌
 */
@NoArgsConstructor
public class PvTask<P extends AbstractPv> implements Task<P> {
  //we use <P extends AbstractPv> to allow either storing generic AbstractPv or just one subtype of AbstractPv

  @Getter
  @JsonProperty("folderName")
  protected String folderName = "";

  @JsonProperty("todo")
  protected MutableSet<P> todo = Sets.mutable.empty();

  @JsonProperty("done")
  protected MutableSet<P> done = Sets.mutable.empty();

  public PvTask (String folderName) {
    this.folderName = folderName;
  }

  @Override
  public MutableSet<P> getTodos() {
    return todo;
  }

  @Override
  public MutableSet<P> getDones() {
    return done;
  }

  /**
   * add the pv to done list, remove it from tudo list if presents
   * @return {@code true} if pv added
   */
  public boolean markDone(P pv){
    todo.remove(pv);
    return done.add(pv);
  }

  /**
   * add the pv to tudo list, remove it from done list if presents
   * @return {@code true} if pv added
   */
  public boolean markTodo(P pv){
    done.remove(pv);
    return todo.add(pv);
  }
}
