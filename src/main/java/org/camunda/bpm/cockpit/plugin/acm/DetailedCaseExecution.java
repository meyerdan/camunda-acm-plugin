package org.camunda.bpm.cockpit.plugin.acm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.camunda.bpm.engine.impl.cmmn.entity.runtime.CaseExecutionEntity;
import org.camunda.bpm.engine.runtime.CaseExecution;
import org.camunda.bpm.model.cmmn.impl.instance.CaseImpl;
import org.camunda.bpm.model.cmmn.impl.instance.CaseTaskImpl;
import org.camunda.bpm.model.cmmn.impl.instance.EventListenerImpl;
import org.camunda.bpm.model.cmmn.impl.instance.HumanTaskImpl;
import org.camunda.bpm.model.cmmn.impl.instance.MilestoneImpl;
import org.camunda.bpm.model.cmmn.impl.instance.PlanItemDefinitionImpl;
import org.camunda.bpm.model.cmmn.impl.instance.ProcessTaskImpl;
import org.camunda.bpm.model.cmmn.impl.instance.StageImpl;
import org.camunda.bpm.model.xml.instance.ModelElementInstance;

@XmlRootElement
public class DetailedCaseExecution implements Serializable {

  public static enum Type {
    HUMANTASK, CASETASK, PROCESSTASK, STAGE, CASE, MILESTONE, EVENTLISTENER;
  }

  /**
   * Factory method creating a detailed execution from case execution and case
   * execution entity.
   * 
   * @param executionEntity
   *          case execution entity.
   * @param execution
   *          case execution.
   * @return
   */
  public static DetailedCaseExecution fromExecutionEntity(final CaseExecutionEntity executionEntity, final CaseExecution execution) {
    return new DetailedCaseExecution(executionEntity.getId(), executionEntity.getActivityId(), executionEntity.getCaseDefinitionId(),
        executionEntity.getCaseInstanceId(), execution.isEnabled(), execution.isActive(), execution.isDisabled());
  }

  /**
   * Factory method constructing a list of detailed case executions from list of
   * case executions.
   * 
   * @param executions
   *          list of executions.
   * @return list of detailed executions.
   */
  public static List<DetailedCaseExecution> fromExecutions(final List<CaseExecution> executions) {
    final List<DetailedCaseExecution> tasks = new ArrayList<DetailedCaseExecution>();
    for (final CaseExecution ce : executions) {
      if (ce instanceof CaseExecutionEntity) {
        final CaseExecutionEntity cee = (CaseExecutionEntity) ce;
        if (cee.isCaseInstanceExecution()) {
          // skip the case model itself
          continue;
        }
        tasks.add(fromExecutionEntity(cee, ce));
      }
    }
    return tasks;
  }

  private static final long serialVersionUID = 1L;

  private String id;
  private String activityId;
  private String caseDefinitionId;
  private String caseInstanceId;
  private boolean enabled;
  private boolean disabled;

  private boolean active;
  private Type type;
  private String name;
  private String description;

  public DetailedCaseExecution(final String id, final String activity, final String caseDefinitionId, final String caseInstanceId, final boolean enabled,
      final boolean active, final boolean disabled) {
    this.id = id;
    this.activityId = activity;
    this.caseDefinitionId = caseDefinitionId;
    this.caseInstanceId = caseInstanceId;
    this.enabled = enabled;
    this.active = active;
    this.disabled = disabled;
  }

  public DetailedCaseExecution() {
  }

  public void enrichType(final ModelElementInstance element) {
    // unknown type, just to make sure the polymorph dispatch works.
  }

  public void enrichType(final HumanTaskImpl element) {
    setType(DetailedCaseExecution.Type.HUMANTASK);
  }

  public void enrichType(final CaseTaskImpl element) {
    setType(DetailedCaseExecution.Type.CASETASK);
  }

  public void enrichType(final CaseImpl element) {
    setType(DetailedCaseExecution.Type.CASE);
  }

  public void enrichType(final StageImpl element) {
    setType(DetailedCaseExecution.Type.STAGE);
  }

  public void enrichType(final ProcessTaskImpl element) {
    setType(DetailedCaseExecution.Type.PROCESSTASK);
  }

  public void enrichType(final MilestoneImpl element) {
    setType(DetailedCaseExecution.Type.MILESTONE);
  }

  public void enrichType(final EventListenerImpl element) {
    setType(DetailedCaseExecution.Type.EVENTLISTENER);
  }

  public void enrichLabels(final ModelElementInstance element) {
    // unknown type, just to make sure the polymorph dispatch works.
  }

  public void enrichLabels(final PlanItemDefinitionImpl element) {
    setName(element.getName());
    setDescription(element.getDescription());
  }

  public String getId() {
    return id;
  }

  public void setId(final String id) {
    this.id = id;
  }

  public String getActivityId() {
    return activityId;
  }

  public void setActivityId(final String activityId) {
    this.activityId = activityId;
  }

  public String getCaseDefinitionId() {
    return caseDefinitionId;
  }

  public void setCaseDefinitionId(final String caseDefinitionId) {
    this.caseDefinitionId = caseDefinitionId;
  }

  public String getCaseInstanceId() {
    return caseInstanceId;
  }

  public void setCaseInstanceId(final String caseInstanceId) {
    this.caseInstanceId = caseInstanceId;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(final boolean enabled) {
    this.enabled = enabled;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(final boolean active) {
    this.active = active;
  }

  public void setType(final Type type) {
    this.type = type;
  }

  public Type getType() {
    return type;
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(final String description) {
    this.description = description;
  }

  public boolean isDisabled() {
    return disabled;
  }

  public void setDisabled(final boolean disabled) {
    this.disabled = disabled;
  }

}