// Automatically created - do not modify
///CLOVER:OFF
// CSOFF: Generated File
package com.opengamma.engine.view.calcnode.msg;
public class Failure extends com.opengamma.engine.view.calcnode.msg.RemoteCalcNodeMessage implements java.io.Serializable {
  public void accept (RemoteCalcNodeMessageVisitor visitor) { visitor.visitFailureMessage (this); }
  private static final long serialVersionUID = 1032075112779165725l;
  private com.opengamma.engine.view.calcnode.CalculationJobSpecification _job;
  public static final String JOB_KEY = "job";
  private String _errorMessage;
  public static final String ERROR_MESSAGE_KEY = "errorMessage";
  private String _computeNodeId;
  public static final String COMPUTE_NODE_ID_KEY = "computeNodeId";
  private com.opengamma.engine.view.calcnode.msg.Ready _ready;
  public static final String READY_KEY = "ready";
  public Failure (com.opengamma.engine.view.calcnode.CalculationJobSpecification job, String errorMessage, String computeNodeId) {
    if (job == null) throw new NullPointerException ("'job' cannot be null");
    else {
      _job = job;
    }
    if (errorMessage == null) throw new NullPointerException ("errorMessage' cannot be null");
    _errorMessage = errorMessage;
    if (computeNodeId == null) throw new NullPointerException ("computeNodeId' cannot be null");
    _computeNodeId = computeNodeId;
  }
  protected Failure (final org.fudgemsg.mapping.FudgeDeserializationContext fudgeContext, final org.fudgemsg.FudgeMsg fudgeMsg) {
    super (fudgeContext, fudgeMsg);
    org.fudgemsg.FudgeField fudgeField;
    fudgeField = fudgeMsg.getByName (JOB_KEY);
    if (fudgeField == null) throw new IllegalArgumentException ("Fudge message is not a Failure - field 'job' is not present");
    try {
      _job = fudgeContext.fieldValueToObject (com.opengamma.engine.view.calcnode.CalculationJobSpecification.class, fudgeField);
    }
    catch (IllegalArgumentException e) {
      throw new IllegalArgumentException ("Fudge message is not a Failure - field 'job' is not CalculationJobSpecification message", e);
    }
    fudgeField = fudgeMsg.getByName (ERROR_MESSAGE_KEY);
    if (fudgeField == null) throw new IllegalArgumentException ("Fudge message is not a Failure - field 'errorMessage' is not present");
    try {
      _errorMessage = fudgeField.getValue ().toString ();
    }
    catch (IllegalArgumentException e) {
      throw new IllegalArgumentException ("Fudge message is not a Failure - field 'errorMessage' is not string", e);
    }
    fudgeField = fudgeMsg.getByName (COMPUTE_NODE_ID_KEY);
    if (fudgeField == null) throw new IllegalArgumentException ("Fudge message is not a Failure - field 'computeNodeId' is not present");
    try {
      _computeNodeId = fudgeField.getValue ().toString ();
    }
    catch (IllegalArgumentException e) {
      throw new IllegalArgumentException ("Fudge message is not a Failure - field 'computeNodeId' is not string", e);
    }
    fudgeField = fudgeMsg.getByName (READY_KEY);
    if (fudgeField != null)  {
      try {
        final com.opengamma.engine.view.calcnode.msg.Ready fudge1;
        fudge1 = com.opengamma.engine.view.calcnode.msg.Ready.fromFudgeMsg (fudgeContext, fudgeMsg.getFieldValue (org.fudgemsg.FudgeMsg.class, fudgeField));
        setReady (fudge1);
      }
      catch (IllegalArgumentException e) {
        throw new IllegalArgumentException ("Fudge message is not a Failure - field 'ready' is not Ready message", e);
      }
    }
  }
  public Failure (com.opengamma.engine.view.calcnode.CalculationJobSpecification job, String errorMessage, String computeNodeId, com.opengamma.engine.view.calcnode.msg.Ready ready) {
    if (job == null) throw new NullPointerException ("'job' cannot be null");
    else {
      _job = job;
    }
    if (errorMessage == null) throw new NullPointerException ("errorMessage' cannot be null");
    _errorMessage = errorMessage;
    if (computeNodeId == null) throw new NullPointerException ("computeNodeId' cannot be null");
    _computeNodeId = computeNodeId;
    if (ready == null) _ready = null;
    else {
      _ready = (com.opengamma.engine.view.calcnode.msg.Ready)ready.clone ();
    }
  }
  protected Failure (final Failure source) {
    super (source);
    if (source == null) throw new NullPointerException ("'source' must not be null");
    if (source._job == null) _job = null;
    else {
      _job = source._job;
    }
    _errorMessage = source._errorMessage;
    _computeNodeId = source._computeNodeId;
    if (source._ready == null) _ready = null;
    else {
      _ready = (com.opengamma.engine.view.calcnode.msg.Ready)source._ready.clone ();
    }
  }
  public Failure clone () {
    return new Failure (this);
  }
  public org.fudgemsg.FudgeMsg toFudgeMsg (final org.fudgemsg.mapping.FudgeSerializationContext fudgeContext) {
    if (fudgeContext == null) throw new NullPointerException ("fudgeContext must not be null");
    final org.fudgemsg.MutableFudgeMsg msg = fudgeContext.newMessage ();
    toFudgeMsg (fudgeContext, msg);
    return msg;
  }
  public void toFudgeMsg (final org.fudgemsg.mapping.FudgeSerializationContext fudgeContext, final org.fudgemsg.MutableFudgeMsg msg) {
    super.toFudgeMsg (fudgeContext, msg);
    if (_job != null)  {
      fudgeContext.addToMessageWithClassHeaders (msg, JOB_KEY, null, _job, com.opengamma.engine.view.calcnode.CalculationJobSpecification.class);
    }
    if (_errorMessage != null)  {
      msg.add (ERROR_MESSAGE_KEY, null, _errorMessage);
    }
    if (_computeNodeId != null)  {
      msg.add (COMPUTE_NODE_ID_KEY, null, _computeNodeId);
    }
    if (_ready != null)  {
      final org.fudgemsg.MutableFudgeMsg fudge1 = org.fudgemsg.mapping.FudgeSerializationContext.addClassHeader (fudgeContext.newMessage (), _ready.getClass (), com.opengamma.engine.view.calcnode.msg.Ready.class);
      _ready.toFudgeMsg (fudgeContext, fudge1);
      msg.add (READY_KEY, null, fudge1);
    }
  }
  public static Failure fromFudgeMsg (final org.fudgemsg.mapping.FudgeDeserializationContext fudgeContext, final org.fudgemsg.FudgeMsg fudgeMsg) {
    final java.util.List<org.fudgemsg.FudgeField> types = fudgeMsg.getAllByOrdinal (0);
    for (org.fudgemsg.FudgeField field : types) {
      final String className = (String)field.getValue ();
      if ("com.opengamma.engine.view.calcnode.msg.Failure".equals (className)) break;
      try {
        return (com.opengamma.engine.view.calcnode.msg.Failure)Class.forName (className).getDeclaredMethod ("fromFudgeMsg", org.fudgemsg.mapping.FudgeDeserializationContext.class, org.fudgemsg.FudgeMsg.class).invoke (null, fudgeContext, fudgeMsg);
      }
      catch (Throwable t) {
        // no-action
      }
    }
    return new Failure (fudgeContext, fudgeMsg);
  }
  public com.opengamma.engine.view.calcnode.CalculationJobSpecification getJob () {
    return _job;
  }
  public void setJob (com.opengamma.engine.view.calcnode.CalculationJobSpecification job) {
    if (job == null) throw new NullPointerException ("'job' cannot be null");
    else {
      _job = job;
    }
  }
  public String getErrorMessage () {
    return _errorMessage;
  }
  public void setErrorMessage (String errorMessage) {
    if (errorMessage == null) throw new NullPointerException ("errorMessage' cannot be null");
    _errorMessage = errorMessage;
  }
  public String getComputeNodeId () {
    return _computeNodeId;
  }
  public void setComputeNodeId (String computeNodeId) {
    if (computeNodeId == null) throw new NullPointerException ("computeNodeId' cannot be null");
    _computeNodeId = computeNodeId;
  }
  public com.opengamma.engine.view.calcnode.msg.Ready getReady () {
    return _ready;
  }
  public void setReady (com.opengamma.engine.view.calcnode.msg.Ready ready) {
    if (ready == null) _ready = null;
    else {
      _ready = (com.opengamma.engine.view.calcnode.msg.Ready)ready.clone ();
    }
  }
  public String toString () {
    return org.apache.commons.lang.builder.ToStringBuilder.reflectionToString(this, org.apache.commons.lang.builder.ToStringStyle.SHORT_PREFIX_STYLE);
  }
}
///CLOVER:ON
// CSON: Generated File
