package com.dosmil_e.m3.visit.synthesis;

import com.dosmil_e.modelbase.support.*;
import com.dosmil_e.modelbase.flattrx.*;


import java.util.Vector;
import java.util.Hashtable;

public class M3SynthesisCtxt extends com.dosmil_e.m3.visit.M3TraversalCtxt implements M3SynthesisCtxtIfc   {

  public static int   sDictInitialSize = 13;
  public static float sDictGrowFactor = (float) 0.5;


  protected java.util.Hashtable vReplicatedBySource;
  protected java.util.Hashtable vSourceByReplicated;

  protected java.util.Hashtable vOverriden;

  protected com.dosmil_e.m3.traversal.ifc.M3TraversalConfigIfc vSynthesisReplicationConfig;


/****************************************************************************
 *  How to

    Synthetize
      Target :  CompPrj1
      TargetRelationship: ownedPorts
      Source :  Comp1
      SourceRelationship: ownedPorts

------------------------------------------------------------
(1)    Collect all the overriden Ports, recursively :

      Initialize an OverridenPortsIdentitySet for the collected overriden Ports

      Collect all the overriden Ports from Ports of Comp1 :
        For all the ownedPorts of Comp1 :
          aPortOfComp1 = Comp1.ownedPorts[i]

          For all the synthesis of aPortOfComp1
            aSynthOfPortOfComp1 = aPortOfComp1.synthesis[j]

            For all the souces Ports of aSynthOfPortOfComp1 :
              aSourcePortOfSynthOfPortOfComp1 = aSynthOfPortOfComp1.sources[k]

              If aSourcePortOfSynthOfPortOfComp1 is NOT inside the OverridenPortsIdentitySet
                Add aSourcePortOfSynthOfPortOfComp1 to the OverridenPortsIdentitySet

                Recursively  add to the OverridenPortsIdentitySet all the synthesis source Pors of aSourcePortOfSynthOfPortOfComp1:
                  Invoke (B) with aSourcePortOfSynthOfPortOfComp1

      Let XXComp = Comp1

(A)   Collect all the overriden Ports from Ports of Components Recursively synthetized onto XXComp :
        For all the synthesis of XXComp that are not exclusions:
          aSynthOntoXXComp = XXComp.synthesis[j]
          !aSynthOntoXXComp.isKindOf( Exclusion.type)

          For all the Components source of  aSynthOntoXXComp
            aSourceCompOfSynthOntoXXComp = aSynthOntoXXComp.sources[j]

            For all the ownedPorts of aSourceCompOfSynthOntoXXComp :
              aPortOfSourceCompOfSynthOntoXXComp = aSourceCompOfSynthOntoXXComp.ownedPorts[i]

              Recursively  add to the OverridenPortsIdentitySet all the synthesis source Pors of aPortOfSourceCompOfSynthOntoXXComp:
                Invoke (B) with aSourcePortOfSynthOfPortOfComp1

            Recurse into (A) : Let XXComp = aSourceCompOfSynthOntoXXComp


(B)   Recursively  add to the OverridenPortsIdentitySet all the synthesis source Pors of XXPort:

      For all the synthesis of XXPort
        aSynthOfPortXX = XXPort.synthesis[j]

        For all the souces Ports of aSynthOfPortXX :
          aSourcePortOfSynthOfPortXX = aSynthOfPortXX.sources[k]

          If aSourcePortOfSynthOfPortXX is NOT inside the OverridenPortsIdentitySet
            Add aSourcePortOfSynthOfPortXX to the OverridenPortsIdentitySet

            Recursively  add to the OverridenPortsIdentitySet all the synthesis source Pors of aSourcePortOfSynthOfPortXX:
              Recurse into (B) with aSourcePortOfSynthOfPortXX

------------------------------------------------------------

(2)   Replicate into CompPrj1.ownedPorts
        the ownedPorts of the Comp1 and the
        the ownedPorts of the Components recursively synthetized into Comp1
        Except the Ports in the OverridenPortsIdentitySet
        Except the excluded Ports


        Initialize an ExclusionFramesStack as a stack of ExclusionFrames each containing an ExcludedPortIdentitySet
        Let XXComp be Comp1
        Invoke (C)


(C)     For all the ownedPorts of XXComp :
          aPortOfXXComp = XXComp.ownedPorts[i]

          If aPortOfXXComp is NOT inside the OverridenPortsIdentitySet
            AND aPortOfXXComp is not excluded by the ExclusionStack (see D)
              Create a new XXPortPrj

              Create a new Projection XXProjection
              Set XXProjection source to aPortOfXXComp
              Add the Projection to XXPortPrj

              Add the XXPortPrj to ownedPorts of CompPrj1

        For all the synthesis of XXComp that are not exclusions:
          aSynthOntoXXComp = XXComp.synthesis[j]
          !aSynthOntoXXComp.isKindOf( Exclusion.type)

          For all the subSynthesis of aSynthOntoXXComp
            aSubSyntOfSynthOntoXXCom = aSynthOntoXXComp.subSynthesis[ l]

            If aSubSyntOfSynthOntoXXComp.isKindOf( Exclusion.type)
              AND aSubSyntOfSynthOntoXXComp.excludedRelationship == ownedPorts.rel

              Create a new ExclusionFrame XXExclusionFrame
              Add to the ExcludedPortIdentitySet of the XXExclusionFrame
                all the source Ports of aSubSyntOfSynthOntoXXComp
              Push the XXExclusionFrame into the ExclusionFramesStack

            For all the Components source of aSynthOntoXXComp
              aSourceCompOfSynthOntoXXComp = aSynthOntoXXComp.sources[j]

              Recurse into (C) : Let XXComp = aSourceCompOfSynthOntoXXComp

            If an ExclusionFrame was pushed
              pop topmost ExclusionFrame



(D)   Check if the XXPort is excluded by the ExcludedPortIdentitySet of any of the ExclusionFrames in the ExclusionFramesStack

        If there is a topmost  ExclusionFrames in the ExclusionFramesStack

          Let XXExclusionFrame = topmost ExclusionFrames in the ExclusionFramesStack
          Invoke E

          
(E)   Check if the XXPort is excluded by the ExcludedPortIdentitySet of the XXExclusionFrame
        or any of its parents  in the ExclusionFramesStack

        If  XXPort is included in the ExcludedPortIdentitySet of the  XXExclusionFrame
          Return TRUE

        If XXExclusionFrame does not have a parent ExclusionFrame
          Return FALSE

        Let XXExclusionFrame = XXExclusionFrame.parent
        Return result of invoke E

------------------------------------------------------------




 ****************************************************************************/



 /****************************************************************************
 *  Constructors of the Visitor
 ****************************************************************************/

  public M3SynthesisCtxt() {
    super();
  }


  public M3SynthesisCtxt( EAIMMCtxtIfc theCtxt) {
    super();
  }




  public void registerOverriden(
    EAIMMCtxtIfc                                  theCtxt,
    com.dosmil_e.m3.withm3.ifc.MMElementWithM3Ifc theMMElement) {

    if( vOverriden == null) {
      vOverriden = new Hashtable( sDictInitialSize, sDictGrowFactor);
    }
    else {
      if( vOverriden.containsKey( theMMElement)) { return;}
    }

    vOverriden.put( theMMElement, theMMElement);
  }




  public boolean isOverriden(
    EAIMMCtxtIfc                                  theCtxt,
    com.dosmil_e.m3.withm3.ifc.MMElementWithM3Ifc theMMElement) {

    if( vOverriden == null) { return false;}

    return vOverriden.containsKey( theMMElement);
  }




  public void registerReplicated(
    EAIMMCtxtIfc                                  theCtxt,
    com.dosmil_e.m3.withm3.ifc.MMElementWithM3Ifc theSourceMMElement,
    com.dosmil_e.m3.withm3.ifc.MMElementWithM3Ifc theReplicatedMMElement) {

    Vector someReplicated = null;
    if( vReplicatedBySource == null) {
      vReplicatedBySource = new Hashtable( sDictInitialSize, sDictGrowFactor);
      someReplicated = new Vector();
      vReplicatedBySource.put( theSourceMMElement, someReplicated);
    }
    else {
      Object someReplicatedObject = vReplicatedBySource.get(theSourceMMElement);
      if( someReplicatedObject == null) {
        someReplicated = new java.util.Vector();
        vReplicatedBySource.put( theSourceMMElement, someReplicated);
      }
      else {
        try { someReplicated = (java.util.Vector) someReplicatedObject;} catch( ClassCastException anEx) {}
        if( someReplicated == null) {
          someReplicated = new java.util.Vector();
          vReplicatedBySource.put( theSourceMMElement, someReplicated);
        }
      }
    }
    someReplicated.addElement( theReplicatedMMElement);

    Vector someSources = null;
    if( vSourceByReplicated == null) {
      vSourceByReplicated = new Hashtable( sDictInitialSize, sDictGrowFactor);
      someSources = new Vector();
      vSourceByReplicated.put( theReplicatedMMElement, someSources);
    }
    else {
      Object someSourcesObject = vSourceByReplicated.get(theSourceMMElement);
      if( someSourcesObject == null) {
        someSources = new java.util.Vector();
        vSourceByReplicated.put( theReplicatedMMElement, someSources);
      }
      else {
        try { someSources = (java.util.Vector) someSourcesObject;} catch( ClassCastException anEx) {}
        if( someSources == null) {
          someSources = new java.util.Vector();
          vSourceByReplicated.put( theReplicatedMMElement, someSources);
        }
      }
    }
    someSources.addElement( theSourceMMElement);
  }



  public com.dosmil_e.m3.withm3.ifc.MMElementWithM3Ifc[] getReplicatedBySource(
    EAIMMCtxtIfc                                  theCtxt,
    com.dosmil_e.m3.withm3.ifc.MMElementWithM3Ifc theSourceMMElement) {

    if( vReplicatedBySource == null) { return null;}

    Object someReplicatedObject = vReplicatedBySource.get( theSourceMMElement);
    if( someReplicatedObject == null) { return null;}

    Vector someReplicatedVector = null;
    try { someReplicatedVector = (java.util.Vector) someReplicatedObject;} catch( ClassCastException anEx) {}
    if( someReplicatedVector == null) { return null;}

    int aNumReplicated = someReplicatedVector.size();
    com.dosmil_e.m3.withm3.ifc.MMElementWithM3Ifc[] someReplicated =
      new com.dosmil_e.m3.withm3.ifc.MMElementWithM3Ifc[ aNumReplicated];
    try { someReplicatedVector.toArray( someReplicated); }
    catch( ArrayStoreException        anException) {}

    return someReplicated;
  }




  public com.dosmil_e.m3.withm3.ifc.MMElementWithM3Ifc[] getSourceByReplicated(
    EAIMMCtxtIfc                                  theCtxt,
    com.dosmil_e.m3.withm3.ifc.MMElementWithM3Ifc theReplicatedMMElement) {

    if( vSourceByReplicated == null) { return null;}

    Object someSourceObject = vSourceByReplicated.get( theReplicatedMMElement);
    if( someSourceObject == null) { return null;}

    Vector someSourceVector = null;
    try { someSourceVector = (java.util.Vector) someSourceObject;} catch( ClassCastException anEx) {}
    if( someSourceVector == null) { return null;}

    int aNumSource = someSourceVector.size();
    com.dosmil_e.m3.withm3.ifc.MMElementWithM3Ifc[] someSource =
      new com.dosmil_e.m3.withm3.ifc.MMElementWithM3Ifc[ aNumSource];
    try { someSourceVector.toArray( someSource); }
    catch( ArrayStoreException        anException) {}

    return someSource;
  }


  public com.dosmil_e.m3.traversal.ifc.M3TraversalConfigIfc getSynthesisReplicationConfig(
    EAIMMCtxtIfc                        theCtxt,
    com.dosmil_e.m3.core.ifc.M3ModelIfc theM3Model) {

    return vSynthesisReplicationConfig;
  }



  public void setSynthesisReplicationConfig(
    EAIMMCtxtIfc                                        theCtxt,
    com.dosmil_e.m3.traversal.ifc.M3TraversalConfigIfc  theTraversalConfig) {

    vSynthesisReplicationConfig = theTraversalConfig;
  }



}





