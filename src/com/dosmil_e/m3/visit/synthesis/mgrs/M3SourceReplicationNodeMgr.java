package com.dosmil_e.m3.visit.synthesis.mgrs;

import com.dosmil_e.modelbase.support.*;
import com.dosmil_e.modelbase.flattrx.*;

public class M3SourceReplicationNodeMgr extends com.dosmil_e.m3.replication.cust.M3ReplicateNodeMgrCust{

  public M3SourceReplicationNodeMgr() {
    super();
  }


  public M3SourceReplicationNodeMgr( EAIMMCtxtIfc theCtxt) {
    super( theCtxt);
  }

  public M3SourceReplicationNodeMgr( EAIMMCtxtIfc theCtxt, EAIMMNameIfc theName) {
    super( theCtxt, theName);
  }


/* Override START */

/****************************************************************************
 *  Implementation of visiting operations of M3NodeMgr
 ****************************************************************************/


  public boolean beginVisit(
    EAIMMCtxtIfc                                        theCtxt,
    com.dosmil_e.m3.visit.M3TraversalCtxtIfc            theTraversalCtxt,
    com.dosmil_e.m3.visit.M3TraversalVisitorIfc         theVisitor,
    com.dosmil_e.m3.traversal.ifc.M3TraversalConfigIfc  theTraversalCfg,
    com.dosmil_e.m3.traversal.ifc.M3NodeConfigIfc       theNodeCfg,
    com.dosmil_e.m3.core.ifc.M3TypeIfc                  theType,
    com.dosmil_e.m3.withm3.ifc.MMElementWithM3Ifc       theSourceMMElement) throws EAIException {

    if( theNodeCfg == null)         { return false;}
    if( theTraversalCtxt == null)   { return false;}

    com.dosmil_e.m3.withm3.ifc.MMElementWithM3Ifc aNewElement = replicateSource( theCtxt,
      theTraversalCtxt, theVisitor, theTraversalCfg, theNodeCfg, theType, theSourceMMElement);
    if( aNewElement == null)        { return false;}

    boolean aIsTerminal = theNodeCfg.getIsTerminal( theCtxt);
    if( aIsTerminal) { return false;}

    theTraversalCtxt.push();

    return true;
  }

  public static String gSynthetizedPostfix = "Synth";

  protected com.dosmil_e.m3.withm3.ifc.MMElementWithM3Ifc replicateSource(
    EAIMMCtxtIfc                                        theCtxt,
    com.dosmil_e.m3.visit.M3TraversalCtxtIfc            theTraversalCtxt,
    com.dosmil_e.m3.visit.M3TraversalVisitorIfc         theVisitor,
    com.dosmil_e.m3.traversal.ifc.M3TraversalConfigIfc  theTraversalCfg,
    com.dosmil_e.m3.traversal.ifc.M3NodeConfigIfc       theNodeCfg,
    com.dosmil_e.m3.core.ifc.M3TypeIfc                  theType,
    com.dosmil_e.m3.withm3.ifc.MMElementWithM3Ifc       theSourceMMElement) throws EAIException {

    if( theTraversalCtxt == null)       { return null;}
    if( theSourceMMElement == null)     { return null;}


    com.dosmil_e.m3.visit.synthesis.M3SynthesisCtxtIfc aM3SynthesisCtxt = null;
    try { aM3SynthesisCtxt = (com.dosmil_e.m3.visit.synthesis.M3SynthesisCtxtIfc) theTraversalCtxt;} catch( ClassCastException anEx) {}
    if( aM3SynthesisCtxt == null) { return null;}

    com.dosmil_e.m3.withm3.ifc.MMElementWithM3Ifc aReplicatedSourceElement = null;

    com.dosmil_e.m3.withm3.ifc.MMElementWithM3Ifc[] someReplicatedSourceElement =
      aM3SynthesisCtxt.getReplicatedBySource( theCtxt, theSourceMMElement);
    if( someReplicatedSourceElement == null) {

      com.dosmil_e.m3.core.ifc.M3TypeIfc aReplicatedType = getReplicatedMetaType( theCtxt);
      if( aReplicatedType == null)        { return null;}

      com.dosmil_e.m3.core.pub.M3TypePub aReplicatedTypePub = null;
      try { aReplicatedTypePub = (com.dosmil_e.m3.core.pub.M3TypePub) aReplicatedType;} catch( ClassCastException anEx) {}
      if( aReplicatedTypePub == null)     { return null;}

      String aNewNameString = new String("");
      EAIMMNameIfc aName = theSourceMMElement.getName();
      if( aName != null) {
        String aNameString = aName.getString();
        if( aNameString != null) {
          aNewNameString = aNameString + gSynthetizedPostfix;
        }
      }
      EAIMMNameIfc aNewName = new EAIMMName( aNewNameString);

      com.dosmil_e.modelbase.ifc.EAIMMElementIfc aNewReplicatedSourceElement = null;
      try { aNewReplicatedSourceElement = aReplicatedTypePub.createElement( theCtxt, aNewName);} catch( EAIException anEx) {}
      if( aNewReplicatedSourceElement == null)          { return null;}

      try { aReplicatedSourceElement = (com.dosmil_e.m3.withm3.ifc.MMElementWithM3Ifc) aNewReplicatedSourceElement;} catch( ClassCastException anEx) {}
      if( aReplicatedSourceElement == null)       { return null;}

      aM3SynthesisCtxt.registerReplicated( theCtxt, theSourceMMElement, aReplicatedSourceElement);
    }

    if( someReplicatedSourceElement.length < 1) { return null;}
    aReplicatedSourceElement = someReplicatedSourceElement[ 0];

    return aReplicatedSourceElement;
  }



  public void endVisit(
    EAIMMCtxtIfc                                        theCtxt,
    com.dosmil_e.m3.visit.M3TraversalCtxtIfc            theTraversalCtxt,
    com.dosmil_e.m3.visit.M3TraversalVisitorIfc         theVisitor,
    com.dosmil_e.m3.traversal.ifc.M3TraversalConfigIfc  theTraversalCfg,
    com.dosmil_e.m3.traversal.ifc.M3NodeConfigIfc       theNodeCfg,
    com.dosmil_e.m3.core.ifc.M3TypeIfc                  theType,
    com.dosmil_e.m3.withm3.ifc.MMElementWithM3Ifc       theSourceMMElement) throws EAIException {

    if( theNodeCfg == null)           { return;}
    if( theTraversalCtxt == null)     { return;}

    boolean aIsTerminal = theNodeCfg.getIsTerminal( theCtxt);
    if( aIsTerminal)                  { return;}

    theTraversalCtxt.pop();
  }



  public boolean beginVisit(
    EAIMMCtxtIfc                                        theCtxt,
    com.dosmil_e.m3.visit.M3TraversalCtxtIfc            theTraversalCtxt,
    com.dosmil_e.m3.visit.M3TraversalVisitorIfc         theVisitor,
    com.dosmil_e.m3.traversal.ifc.M3TraversalConfigIfc  theTraversalCfg,
    com.dosmil_e.m3.traversal.ifc.M3BranchConfigIfc     theBranchCfg,
    com.dosmil_e.m3.traversal.ifc.M3BranchMgrIfc        theBranchMgr,
    com.dosmil_e.m3.core.ifc.M3RelationshipIfc          theRelationship,
    com.dosmil_e.m3.traversal.ifc.M3NodeConfigIfc       theNodeCfg,
    com.dosmil_e.m3.core.ifc.M3TypeIfc                  theType,
    com.dosmil_e.m3.withm3.ifc.MMElementWithM3Ifc       theSourceMMElement,
    com.dosmil_e.m3.withm3.ifc.MMElementWithM3Ifc       theRelatedMMElement) throws EAIException {

    if( theNodeCfg == null)         { return false;}
    if( theTraversalCtxt == null)   { return false;}

    com.dosmil_e.m3.withm3.ifc.MMElementWithM3Ifc aNewElement = replicateSource( theCtxt,
      theTraversalCtxt, theVisitor,
      theTraversalCfg, theBranchCfg, theBranchMgr, theRelationship, theNodeCfg, theType,
      theSourceMMElement, theRelatedMMElement);
    if( aNewElement == null)        { return false;}

    boolean aIsTerminal = theNodeCfg.getIsTerminal( theCtxt);
    if( aIsTerminal) { return false;}

    theTraversalCtxt.push();

    return true;
  }




  protected com.dosmil_e.m3.withm3.ifc.MMElementWithM3Ifc replicateSource(
    EAIMMCtxtIfc                                        theCtxt,
    com.dosmil_e.m3.visit.M3TraversalCtxtIfc            theTraversalCtxt,
    com.dosmil_e.m3.visit.M3TraversalVisitorIfc         theVisitor,
    com.dosmil_e.m3.traversal.ifc.M3TraversalConfigIfc  theTraversalCfg,
    com.dosmil_e.m3.traversal.ifc.M3BranchConfigIfc     theBranchCfg,
    com.dosmil_e.m3.traversal.ifc.M3BranchMgrIfc        theBranchMgr,
    com.dosmil_e.m3.core.ifc.M3RelationshipIfc          theRelationship,
    com.dosmil_e.m3.traversal.ifc.M3NodeConfigIfc       theNodeCfg,
    com.dosmil_e.m3.core.ifc.M3TypeIfc                  theType,
    com.dosmil_e.m3.withm3.ifc.MMElementWithM3Ifc       theSourceMMElement,
    com.dosmil_e.m3.withm3.ifc.MMElementWithM3Ifc       theRelatedMMElement) throws EAIException {

    if( theTraversalCtxt == null)       { return null;}
    if( theSourceMMElement == null)     { return null;}

    com.dosmil_e.m3.visit.synthesis.M3SynthesisCtxtIfc aM3SynthesisCtxt = null;
    try { aM3SynthesisCtxt = (com.dosmil_e.m3.visit.synthesis.M3SynthesisCtxtIfc) theTraversalCtxt;} catch( ClassCastException anEx) {}
    if( aM3SynthesisCtxt == null) { return null;}

    com.dosmil_e.m3.withm3.ifc.MMElementWithM3Ifc[] someReplicatedSourceElement =
      aM3SynthesisCtxt.getReplicatedBySource( theCtxt, theSourceMMElement);
    if( someReplicatedSourceElement == null)     { return null;}

    if( someReplicatedSourceElement.length < 1) { return null;}
    com.dosmil_e.m3.withm3.ifc.MMElementWithM3Ifc aReplicatedSourceElement = someReplicatedSourceElement[ 0];

    com.dosmil_e.m3.replication.ifc.M3ReplicateBranchMgrIfc aReplicateBranchMgr = null;
    try { aReplicateBranchMgr = (com.dosmil_e.m3.replication.ifc.M3ReplicateBranchMgrIfc) theBranchMgr;} catch( ClassCastException anEx) {}
    if( aReplicateBranchMgr == null)     { return null;}

    com.dosmil_e.m3.core.ifc.M3RelationshipIfc aReplicateRelationship =
      aReplicateBranchMgr.getReplicatedMetaRelationship( theCtxt);
    if( aReplicateRelationship == null)    { return null;}

    com.dosmil_e.m3.core.pub.M3RelationshipPub aReplicateRelationshipPub = null;
    try { aReplicateRelationshipPub = (com.dosmil_e.m3.core.pub.M3RelationshipPub) aReplicateRelationship;} catch( ClassCastException anEx) {}
    if( aReplicateRelationshipPub == null) { return null;}

    com.dosmil_e.m3.withm3.ifc.MMElementWithM3Ifc aReplicatedRelatedElement = null;
    com.dosmil_e.m3.withm3.ifc.MMElementWithM3Ifc[] someReplicatedRelatedElements =
      aM3SynthesisCtxt.getReplicatedBySource( theCtxt, theRelatedMMElement);
    if( someReplicatedRelatedElements == null || someReplicatedRelatedElements.length < 1 || someReplicatedRelatedElements[0] == null) {

      com.dosmil_e.m3.core.ifc.M3TypeIfc aReplicatedType = getReplicatedMetaType( theCtxt);
      if( aReplicatedType == null)        { return null;}

      com.dosmil_e.m3.core.pub.M3TypePub aReplicatedTypePub = null;
      try { aReplicatedTypePub = (com.dosmil_e.m3.core.pub.M3TypePub) aReplicatedType;} catch( ClassCastException anEx) {}
      if( aReplicatedTypePub == null)     { return null;}

      String aNewNameString = new String("");
      EAIMMNameIfc aName = theSourceMMElement.getName();
      if( aName != null) {
        String aNameString = aName.getString();
        if( aNameString != null) {
          aNewNameString = aNameString + gSynthetizedPostfix;
        }
      }
      EAIMMNameIfc aNewName = new EAIMMName( aNewNameString);

      com.dosmil_e.modelbase.ifc.EAIMMElementIfc aNewReplicatedRelatedElement = null;
      try { aNewReplicatedRelatedElement = aReplicatedTypePub.createElement( theCtxt, aNewName);} catch( EAIException anEx) {}
      if( aNewReplicatedRelatedElement == null)          { return null;}

      try { aReplicatedRelatedElement = (com.dosmil_e.m3.withm3.ifc.MMElementWithM3Ifc) aNewReplicatedRelatedElement;} catch( ClassCastException anEx) {}
      if( aReplicatedRelatedElement == null)       { return null;}

      aM3SynthesisCtxt.registerReplicated( theCtxt, theRelatedMMElement, aReplicatedRelatedElement);
    }


    if( aReplicateRelationshipPub.isM3Many( theCtxt)) {
//      aReplicateRelationshipPub.addReplicatedRelatedElement( theCtxt, aReplicatedSourceElement, aReplicatedRelatedElement);
      aReplicateRelationshipPub.addRelatedElement( theCtxt, aReplicatedSourceElement, aReplicatedRelatedElement);
    }
    else {
//      aReplicateRelationshipPub.setReplicatedRelatedElement( theCtxt, aReplicatedSourceElement, aReplicatedRelatedElement);
      aReplicateRelationshipPub.setRelatedElement( theCtxt, aReplicatedSourceElement, aReplicatedRelatedElement);
    }

    return aReplicatedRelatedElement;
  }






  public void endVisit(
    EAIMMCtxtIfc                                        theCtxt,
    com.dosmil_e.m3.visit.M3TraversalCtxtIfc            theTraversalCtxt,
    com.dosmil_e.m3.visit.M3TraversalVisitorIfc         theVisitor,
    com.dosmil_e.m3.traversal.ifc.M3TraversalConfigIfc  theTraversalCfg,
    com.dosmil_e.m3.traversal.ifc.M3BranchConfigIfc     theBranchCfg,
    com.dosmil_e.m3.traversal.ifc.M3BranchMgrIfc        theBranchMgr,
    com.dosmil_e.m3.core.ifc.M3RelationshipIfc          theRelationship,
    com.dosmil_e.m3.traversal.ifc.M3NodeConfigIfc       theNodeCfg,
    com.dosmil_e.m3.core.ifc.M3TypeIfc                  theType,
    com.dosmil_e.m3.withm3.ifc.MMElementWithM3Ifc       theSourceMMElement,
    com.dosmil_e.m3.withm3.ifc.MMElementWithM3Ifc       theRelatedMMElement) throws EAIException {

    if( theNodeCfg == null)         { return;}
    if( theTraversalCtxt == null)   { return;}

    boolean aIsTerminal = theNodeCfg.getIsTerminal( theCtxt);
    if( aIsTerminal) { return;}

    theTraversalCtxt.pop();
  }

/* Override END */


/****************************************************************************
 *  Serialization support
 ****************************************************************************/

  private static final long serialVersionUID = -1234123456300000007L;



}
