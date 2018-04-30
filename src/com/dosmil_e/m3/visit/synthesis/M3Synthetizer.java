package com.dosmil_e.m3.visit.synthesis;

import com.dosmil_e.modelbase.support.*;
import com.dosmil_e.modelbase.flattrx.*;


import com.dosmil_e.m3.core.pub.*;
import com.dosmil_e.m3.core.ifc.*;
import com.dosmil_e.m3.traversal.ifc.*;
import com.dosmil_e.m3.visit.*;
import com.dosmil_e.m3.replication.ifc.*;
import com.dosmil_e.m3.projection.ifc.*;

import com.dosmil_e.m3.withm3.ifc.MMElementWithM3Ifc;
import com.dosmil_e.modelbase.ifc.EAIMMElementIfc;

import java.util.Vector;
import java.util.Hashtable;

public class M3Synthetizer implements M3SynthetizerIfc   {

  public static int   sDictInitialSize = 13;
  public static float sDictGrowFactor = (float) 0.5;


  protected java.util.Hashtable        vOverridenPortsIdentitySet;

  protected M3ProjectionConfigIfc      vM3SynthesisConfig;

  protected M3RelationshipPub          vM3RelTargetToTransfomations;
  protected M3RelationshipPub          vM3RelTransformationToSources;
  protected M3RelationshipPub          vM3RelTransformationToSubTransformations;
  protected M3RelationshipPub          vM3RelProjectedToProjections;
  protected M3RelationshipPub          vM3RelProjectionToOriginals;
  protected M3TypePub                  vM3TypeProjection;
  protected M3TypePub                  vM3TypeExclusion;
  protected M3AttributePub             vM3AttributeExcludedInRelationshipNamed;


  protected MMElementWithM3Ifc         vSourceMMElement;
  protected MMElementWithM3Ifc         vTargetMMElement;

  protected M3TraversalConfigCacheIfc  vTraversalConfigCache;


/****************************************************************************
 *  How to

    Synthetize
      Source :  Comp1


(0)     Get the type of Comp1
          aTypeComp1 = Comp1.getType ( == Component)

        Lookup in aSynthesisConfig the RootTypeNodeCfg for type aTypeComp1
          aRootTypeNodeCfg = aSynthesisConfig.getRootTypeNodeConfig( aTypeComp1);

        If there is NOT a aRootTypeNodeCfg for aTypeComp1 in aSynthesisConfig
          return nothing

        Get the NodeConfig of aRootTypeNodeCfg
          aNodeConfig  = aRootTypeNodeCfg.getNodeConfig

        If there is NOT a aNodeConfig
          return nothing

        Get the NodeMgr of aNodeConfig
          aNodeMgr = aNodeConfig.getNodeMgr

        If there is NOT a aNodeMgr
          return nothing

        If the aNodeMgr is NOT a ReplicationNodeMgr
          return nothing

        aReplicationNodeMgr = (ReplicationNodeMgr) aNodeMgr

        Get the replicationType of aReplicationNodeMgr
          aReplicationType = aReplicationNodeMgr.getReplicationType

        If aReplicationType is null
          return nothing

        Create a new instance of aReplicationType
          aNewOfReplicationType = aReplicationType.NEW

        Get the BranchConfigs of aNodeConfig
          someBranchConfigs  = aNodeConfig.getBranchConfigs

        For each BranchConfig is someBranchConfigs
          Let aBranchConfig = someBranchConfigs[ h]

          Get the relationship of aBranchConfig
            aRelationship = aBranchConfig.getRelationship()

          Get the BranchMgr of aBranchConfig
            aBranchMgr = aBranchConfig.getBranchMgr()

          If aBranchMgr is NOT null THEN

            If the aBranchMgr is a ReplicationBranchMgr THEN

              aReplicationBranchMgr = (ReplicationBranchMgr) aBranchMgr

              Get the replicationRelationship of aReplicationNodeMgr
                aReplicationRelationship = aReplicationBranchMgr.getReplicationRelationship

                If aReplicationRelationship is NOT null THEN

                  Invoke (1) with Comp1,  aRelationship, aNewOfReplicationType, aReplicationRelationship



------------------------------------------------------------

    Synthetize
      Source :  Comp1
      SourceRelationship: ownedPorts
      Target :  CompPrj1
      TargetRelationship: ownedPorts

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
        For all the synthesis of XXComp:
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

(C0)    Replicate Owned and Synthetized

(C)     Replicate Owned :
        For all the ownedPorts of XXComp :
          aPortOfXXComp = XXComp.ownedPorts[i]

          If aPortOfXXComp is NOT inside the OverridenPortsIdentitySet
            AND aPortOfXXComp is not excluded by the ExclusionStack (see D)
              Create a new XXPortPrj

              Create a new Projection XXProjection
              Set XXProjection source to aPortOfXXComp
              Add the Projection to XXPortPrj

              Add the XXPortPrj to ownedPorts of CompPrj1

(C2)    Replicate Synthetized :
        For all the synthesis of XXComp:
          aSynthOntoXXComp = XXComp.synthesis[j]

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

            Recurse into (C0) : Let XXComp = aSourceCompOfSynthOntoXXComp

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
 *  Constructors of the M3Synthetizer
 ****************************************************************************/

  public M3Synthetizer() {
    super();
  }


  public M3Synthetizer( EAIMMCtxtIfc theCtxt) {
    super();
  }




/****************************************************************************
 *  How to

    Synthetize
      Source :  Comp1


(0)     Get the type of Comp1
          aTypeComp1 = Comp1.getType ( == Component)

        Lookup in aSynthesisConfig the RootTypeNodeCfg for type aTypeComp1
          aRootTypeNodeCfg = aSynthesisConfig.getRootTypeNodeConfig( aTypeComp1);

        If there is NOT a aRootTypeNodeCfg for aTypeComp1 in aSynthesisConfig
          return nothing

        Get the NodeConfig of aRootTypeNodeCfg
          aNodeConfig  = aRootTypeNodeCfg.getNodeConfig

        If there is NOT a aNodeConfig
          return nothing

        Get the NodeMgr of aNodeConfig
          aNodeMgr = aNodeConfig.getNodeMgr

        If there is NOT a aNodeMgr
          return nothing

        If the aNodeMgr is NOT a ReplicationNodeMgr
          return nothing

        aReplicationNodeMgr = (ReplicationNodeMgr) aNodeMgr

        Get the replicationType of aReplicationNodeMgr
          aReplicationType = aReplicationNodeMgr.getReplicationType

        If aReplicationType is null
          return nothing

        Create a new instance of aReplicationType
          aNewOfReplicationType = aReplicationType.NEW

        Get the BranchConfigs of aNodeConfig
          someBranchConfigs  = aNodeConfig.getBranchConfigs

        For each BranchConfig is someBranchConfigs
          Let aBranchConfig = someBranchConfigs[ h]

          Get the relationship of aBranchConfig
            aRelationship = aBranchConfig.getRelationship()

          If aRelationship is NOT null THEN

            Get the BranchMgr of aBranchConfig
              aBranchMgr = aBranchConfig.getBranchMgr()

            If aBranchMgr is NOT null THEN

              If the aBranchMgr is a ReplicationBranchMgr THEN

                aReplicationBranchMgr = (ReplicationBranchMgr) aBranchMgr

                Get the replicationRelationship of aReplicationNodeMgr
                  aReplicationRelationship = aReplicationBranchMgr.getReplicationRelationship

                  If aReplicationRelationship is NOT null THEN

                    Invoke (1) with Comp1,  aRelationship, aNewOfReplicationType, aReplicationRelationship

***************************************************************************/

  public MMElementWithM3Ifc synthetize(
    EAIMMCtxtIfc              theCtxt,
    M3ProjectionConfigIfc     theSynthesisConfig,
    MMElementWithM3Ifc        theSourceMMElement) throws EAIException {

    if( theCtxt == null)                { return null;}
    if( theSourceMMElement == null)     { return null;}
    if( theSynthesisConfig == null)     { return null;}

    if( !initFromSynthesisConfig ( theCtxt, theSynthesisConfig)) { return null;}

    vSourceMMElement           = theSourceMMElement;

    M3RootTypeNodeConfigIfc aRootTypeNodeCfg = getRootTypeNodeConfigFor( theCtxt, vSourceMMElement);
    if( aRootTypeNodeCfg == null)         { return null;}

    M3NodeConfigIfc aNodeCfg = aRootTypeNodeCfg.getNodeConfig( theCtxt);
    if( aNodeCfg == null)                 { return null;}

    M3NodeMgrIfc aNodeMgr = aNodeCfg.getMetaNodeMgr( theCtxt);
    if( aNodeMgr == null)                 { return null;}

    M3ReplicateNodeMgrIfc aReplicatedNodeMgr = null;
    try { aReplicatedNodeMgr = (M3ReplicateNodeMgrIfc) aNodeMgr;} catch( ClassCastException anEx) {}
    if( aReplicatedNodeMgr == null)       { return null;}

    M3TypeIfc aReplicatedType = aReplicatedNodeMgr.getReplicatedMetaType( theCtxt);
    if( aReplicatedType == null)      { return null;}

    M3TypePub aReplicatedTypePub = null;
    try { aReplicatedTypePub = (M3TypePub) aReplicatedType;} catch( ClassCastException anEx) {}
    if( aReplicatedTypePub == null) { return null;}

    String aNewNameString = new String("");
    EAIMMNameIfc aName = vSourceMMElement.getName();
    if( aName != null) {
      String aNameString = aName.getString();
      if( aNameString != null) {
        aNewNameString = aNameString;
      }
    }
    EAIMMNameIfc aNewName = new EAIMMName( aNewNameString);

    EAIMMElementIfc aNewProjectedElement = null;
    try { aNewProjectedElement = aReplicatedTypePub.createElement( theCtxt, aNewName);} catch( EAIException anEx) {}
    if( aNewProjectedElement == null)        { return null;}

    MMElementWithM3Ifc aNewProjectedElementWM3 = null;
    try { aNewProjectedElementWM3 = (MMElementWithM3Ifc) aNewProjectedElement;} catch( ClassCastException anEx) {}
    if( aNewProjectedElementWM3 == null)     { return null;}


    EAIMMNameIfc aNewProjectionName = new EAIMMName( aNewNameString + "Projection");

    EAIMMElementIfc aNewProjectionElement = null;
    try { aNewProjectionElement = vM3TypeProjection.createElement( theCtxt, aNewProjectionName);} catch( EAIException anEx) {}
    if( aNewProjectionElement != null) {

      vM3RelProjectedToProjections.addRelatedElement( theCtxt, aNewProjectedElement,  aNewProjectionElement);
      vM3RelProjectionToOriginals.addRelatedElement(  theCtxt, aNewProjectionElement, vSourceMMElement );
    }

    vTargetMMElement = aNewProjectedElementWM3;

    M3BranchConfigIfc[] someBranchCfgs = aNodeCfg.getBranchConfigs( theCtxt);
    if( someBranchCfgs != null)      {

      int aNumBranchCfgs = someBranchCfgs.length;
      if( aNumBranchCfgs < 1)         { return null;}

      for( int anIndex = 0; anIndex < aNumBranchCfgs; anIndex++) {
        M3BranchConfigIfc aBranchCfg = someBranchCfgs[ anIndex];
        if( aBranchCfg != null) {

          EAIMMElementIfc[] someRelatedElements =  null;

          M3RelationshipIfc aM3Relationship = aBranchCfg.getMetaRelationship( theCtxt);
          if( aM3Relationship != null) {

            M3RelationshipPub aM3RelationshipPub = null;
            try { aM3RelationshipPub = (M3RelationshipPub) aM3Relationship;} catch( ClassCastException anEx) {}
            if( aM3RelationshipPub != null) {

              M3BranchMgrIfc aBranchMgr = aBranchCfg.getMetaBranchMgr( theCtxt);
              if( aBranchMgr != null)      {

                M3ReplicateBranchMgrIfc aReplicationBranchMgr = null;
                try { aReplicationBranchMgr = (M3ReplicateBranchMgrIfc) aBranchMgr;} catch( ClassCastException anEx) {}
                if( aReplicationBranchMgr!= null)      {

                  M3RelationshipIfc aM3ReplicatedRelationship = aReplicationBranchMgr.getReplicatedMetaRelationship( theCtxt);
                  if( aM3ReplicatedRelationship != null) {

                    M3RelationshipPub aM3ReplicatedRelationshipPub = null;
                    try { aM3ReplicatedRelationshipPub = (M3RelationshipPub) aM3ReplicatedRelationship;} catch( ClassCastException anEx) {}
                    if( aM3ReplicatedRelationshipPub != null) {

                      synthetize( theCtxt, vSourceMMElement, aM3RelationshipPub, aBranchCfg, vTargetMMElement, aM3ReplicatedRelationshipPub);
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
    return vTargetMMElement;
  }


  protected boolean initFromSynthesisConfig(
    EAIMMCtxtIfc            theCtxt,
    M3ProjectionConfigIfc   theSynthesisConfig) throws EAIException {

    if( theSynthesisConfig == null)               { return false;}

    vM3SynthesisConfig = theSynthesisConfig;

    vM3RelTargetToTransfomations              = null;
    vM3RelTransformationToSources             = null;
    vM3RelTransformationToSubTransformations  = null;
    vM3TypeProjection                         = null;
    vM3TypeExclusion                          = null;
    vM3AttributeExcludedInRelationshipNamed   = null;
    vM3RelProjectedToProjections              = null;
    vM3RelProjectionToOriginals               = null;



    M3RelationshipIfc aM3RelTargetToTransfomations = vM3SynthesisConfig.getTargetToTransfomations( theCtxt);
    if( aM3RelTargetToTransfomations != null)     {
      try { vM3RelTargetToTransfomations = (M3RelationshipPub) aM3RelTargetToTransfomations;} catch( ClassCastException anEx) {}
    }

    M3RelationshipIfc aM3RelTransformationToSources = vM3SynthesisConfig.getTransformationToSources( theCtxt);
    if( aM3RelTransformationToSources != null)    {
      try { vM3RelTransformationToSources = (M3RelationshipPub) aM3RelTransformationToSources;} catch( ClassCastException anEx) {}
    }

    M3RelationshipIfc aM3RelTransformationToSubTransformations = vM3SynthesisConfig.getTransformationToSubTransformations( theCtxt);
    if( aM3RelTransformationToSubTransformations != null)    {
      try { vM3RelTransformationToSubTransformations = (M3RelationshipPub) aM3RelTransformationToSubTransformations;} catch( ClassCastException anEx) {}
    }

    M3TypeIfc aM3TypeProjection = vM3SynthesisConfig.getProjectionType( theCtxt);
    if( aM3TypeProjection != null)  {
      try { vM3TypeProjection = (M3TypePub) aM3TypeProjection;} catch( ClassCastException anEx) {}
    }

    M3TypeIfc aM3TypeExclusion =  vM3SynthesisConfig.getExclusionType( theCtxt);
    if( aM3TypeExclusion != null)  {
      try { vM3TypeExclusion = (M3TypePub) aM3TypeExclusion;} catch( ClassCastException anEx) {}

      M3AttributeIfc aM3AttributeExcludedInRelationshipNamed = vM3SynthesisConfig.getExcludedInRelationshipNamed( theCtxt);
      if( aM3AttributeExcludedInRelationshipNamed != null) {
        try { vM3AttributeExcludedInRelationshipNamed = (M3AttributePub) aM3AttributeExcludedInRelationshipNamed;} catch( ClassCastException anEx) {}
      }
    }

    M3RelationshipIfc aM3RelProjectedToProjections = vM3SynthesisConfig.getProjectedToProjections( theCtxt);
    if( aM3RelProjectedToProjections != null)     {
      try { vM3RelProjectedToProjections = (M3RelationshipPub) aM3RelProjectedToProjections;} catch( ClassCastException anEx) {}
    }

    M3RelationshipIfc aM3RelProjectionToOriginals = vM3SynthesisConfig.getProjectionToOriginals( theCtxt);
    if( aM3RelProjectionToOriginals != null)    {
      try { vM3RelProjectionToOriginals = (M3RelationshipPub) aM3RelProjectionToOriginals;} catch( ClassCastException anEx) {}
    }



    if( vM3RelTargetToTransfomations == null)     { return false;}
    if( vM3RelTransformationToSources == null)    { return false;}
    if( vM3RelTransformationToSubTransformations == null)      { return false;}
    if( vM3TypeProjection == null)                { return false;}
    if( vM3RelProjectedToProjections == null)     { return false;}
    if( vM3RelProjectionToOriginals == null)      { return false;}

    return true;
  }



/****************************************************************************

    Synthetize
      Source :  Comp1
      SourceRelationship: ownedPorts
      Target :  CompPrj1
      TargetRelationship: ownedPorts

(1)    Collect all the overriden Ports, recursively :
       collectAllOverriden

(2)     Replicate into CompPrj1.ownedPorts

****************************************************************************/

  protected MMElementWithM3Ifc synthetize(
    EAIMMCtxtIfc            theCtxt,
    MMElementWithM3Ifc      theSourceMMElement,
    M3RelationshipPub       theM3Relationship,
    M3BranchConfigIfc       theBranchConfig,
    MMElementWithM3Ifc      theReplicatedMMElement,
    M3RelationshipPub       theM3ReplicatedRelationship) throws EAIException {

    collectAllOverriden( theCtxt, theSourceMMElement, theM3Relationship);

    replicate( theCtxt, theSourceMMElement, theM3Relationship, theBranchConfig,
      theReplicatedMMElement, theM3ReplicatedRelationship);

    return theReplicatedMMElement;
  }






/****************************************************************************
    collectAllOverriden

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
      Invoke (A)

****************************************************************************/

  protected Hashtable collectAllOverriden(
    EAIMMCtxtIfc          theCtxt,
    MMElementWithM3Ifc    theSourceMMElement,
    M3RelationshipPub     theM3Relationship) throws EAIException {

    if( theCtxt == null)                          { return null;}
    if( theSourceMMElement == null)               { return null;}
    if( theM3Relationship == null)                { return null;}

    if( vM3SynthesisConfig == null)                 { return null;}


    vOverridenPortsIdentitySet = new Hashtable( sDictInitialSize, sDictGrowFactor);


    EAIMMElementIfc[] someRelatedElements =  null;

    someRelatedElements = theM3Relationship.getRelatedElements( theCtxt, theSourceMMElement);

    if( someRelatedElements == null)  {
      return vOverridenPortsIdentitySet;
    }


    int aNumRelated = someRelatedElements.length;
    for( int anIndex = 0; anIndex < aNumRelated; anIndex++) {
      EAIMMElementIfc aRelated = someRelatedElements[ anIndex];
      if( aRelated != null) {

        MMElementWithM3Ifc aRelatedWithM3 = null;
        try { aRelatedWithM3 = (MMElementWithM3Ifc) aRelated;} catch( ClassCastException anEx) {}
        if( aRelatedWithM3 != null) {
          collectRecursivelyOverriden( theCtxt, aRelatedWithM3);
        }
      }
    }
    collectOverriden( theCtxt, theSourceMMElement, theM3Relationship);

    return vOverridenPortsIdentitySet;
  }




/****************************************************************************

(A)   Collect all the overriden Ports from Ports of Components Recursively synthetized onto XXComp :
        For all the synthesis of XXComp:
          aSynthOntoXXComp = XXComp.synthesis[j]

          For all the Components source of  aSynthOntoXXComp
            aSourceCompOfSynthOntoXXComp = aSynthOntoXXComp.sources[j]

            For all the ownedPorts of aSourceCompOfSynthOntoXXComp :
              aPortOfSourceCompOfSynthOntoXXComp = aSourceCompOfSynthOntoXXComp.ownedPorts[i]

              Recursively  add to the OverridenPortsIdentitySet all the synthesis source Pors of aPortOfSourceCompOfSynthOntoXXComp:
                Invoke (B) with aSourcePortOfSynthOfPortOfComp1

            Recurse into (A) : Let XXComp = aSourceCompOfSynthOntoXXComp

****************************************************************************/

  protected void collectOverriden(
    EAIMMCtxtIfc          theCtxt,
    MMElementWithM3Ifc    theSourceMMElement,
    M3RelationshipPub     theM3Relationship) throws EAIException {

    if( theSourceMMElement == null)               { return;}
    if( theM3Relationship == null)                { return;}

    EAIMMElementIfc[] someSynthesis =  vM3RelTargetToTransfomations.getRelatedElements( theCtxt, theSourceMMElement);
    if( someSynthesis == null) { return;}

    int aNumSynthesis = someSynthesis.length;
    for( int anIndex = 0; anIndex < aNumSynthesis; anIndex++) {

      EAIMMElementIfc aSynthesis = someSynthesis[ anIndex];
      if( aSynthesis != null) {

        EAIMMElementIfc[] someSources =  vM3RelTransformationToSources.getRelatedElements( theCtxt, aSynthesis);
        if( someSources != null) {

          int aNumSources = someSources.length;
          for( int otherIndex = 0; otherIndex < aNumSources; otherIndex++) {
            EAIMMElementIfc aSource = someSources[ otherIndex];
            if( aSource != null) {

              EAIMMElementIfc[] someRelatedElements = theM3Relationship.getRelatedElements( theCtxt, theSourceMMElement);
              if( someRelatedElements != null) {

                int aNumRelated = someRelatedElements.length;
                for( int anotherIndex = 0; anotherIndex < aNumRelated; anotherIndex++) {
                  EAIMMElementIfc aRelated = someRelatedElements[ anotherIndex];
                  if( aRelated != null) {

                    MMElementWithM3Ifc aRelatedWithM3 = null;
                    try { aRelatedWithM3 = (MMElementWithM3Ifc) aRelated;} catch( ClassCastException anEx) {}
                    if( aRelatedWithM3 != null) {
                      collectRecursivelyOverriden( theCtxt, aRelatedWithM3);
                    }
                  }
                }
              }

              MMElementWithM3Ifc aSourceWithM3 = null;
              try { aSourceWithM3 = (MMElementWithM3Ifc) aSource;} catch( ClassCastException anEx) {}
              if( aSourceWithM3 != null) {
                collectOverriden( theCtxt, aSourceWithM3, theM3Relationship);
              }
            }
          }
        }
      }
    }
  }







/****************************************************************************

(B)   Recursively  add to the OverridenPortsIdentitySet all the synthesis source Pors of XXPort:

      For all the synthesis of XXPort
        aSynthOfPortXX = XXPort.synthesis[j]

        For all the souces Ports of aSynthOfPortXX :
          aSourcePortOfSynthOfPortXX = aSynthOfPortXX.sources[k]

          If aSourcePortOfSynthOfPortXX is NOT inside the OverridenPortsIdentitySet
            Add aSourcePortOfSynthOfPortXX to the OverridenPortsIdentitySet

            Recursively  add to the OverridenPortsIdentitySet all the synthesis source Pors of aSourcePortOfSynthOfPortXX:
              Recurse into (B) with aSourcePortOfSynthOfPortXX

****************************************************************************/

  protected void collectRecursivelyOverriden(
    EAIMMCtxtIfc          theCtxt,
    MMElementWithM3Ifc    theSourceMMElement) throws EAIException {

    if( theSourceMMElement == null)               { return;}

    if( vOverridenPortsIdentitySet == null)       { return;}

    EAIMMElementIfc[] someSynthesis =  vM3RelTargetToTransfomations.getRelatedElements( theCtxt, theSourceMMElement);
    if( someSynthesis == null) { return;}

    int aNumSynthesis = someSynthesis.length;
    for( int anIndex = 0; anIndex < aNumSynthesis; anIndex++) {

      EAIMMElementIfc aSynthesis = someSynthesis[ anIndex];
      if( aSynthesis != null) {

        EAIMMElementIfc[] someSources =  vM3RelTransformationToSources.getRelatedElements( theCtxt, aSynthesis);
        if( someSources != null) {

          int aNumSources = someSources.length;
          for( int otherIndex = 0; otherIndex < aNumSources; otherIndex++) {
            EAIMMElementIfc aSource = someSources[ otherIndex];
            if( aSource != null) {

              if( !vOverridenPortsIdentitySet.containsKey( aSource)) {
                vOverridenPortsIdentitySet.put( aSource, aSource);
              }
            }
          }
        }
      }
    }
  }




  protected M3RootTypeNodeConfigIfc getRootTypeNodeConfigFor(
    EAIMMCtxtIfc                                        theCtxt,
    MMElementWithM3Ifc       theSourceMMElement) throws EAIException {

    if( theCtxt == null)              { return null;}
    if( theSourceMMElement == null)   { return null;}

    if( vTraversalConfigCache == null)   {
      if( vM3SynthesisConfig == null)  { return null;}
      vTraversalConfigCache = new M3TraversalConfigCache( vM3SynthesisConfig);
    }

    return vTraversalConfigCache.getRootTypeNodeConfigFor(  theCtxt, theSourceMMElement);
  }



  protected M3RelatedTypeNodeConfigIfc getRelatedTypeNodeConfigFor(
    EAIMMCtxtIfc                                        theCtxt,
    M3BranchConfigIfc     theBranchCfg,
    MMElementWithM3Ifc       theRelatedMMElement) throws EAIException {

    if( theCtxt == null)              { return null;}
    if( theBranchCfg == null)         { return null;}
    if( theRelatedMMElement == null)  { return null;}

    if( vTraversalConfigCache == null)   {
      if( vM3SynthesisConfig == null)  { return null;}
      vTraversalConfigCache = new M3TraversalConfigCache( vM3SynthesisConfig);
    }

    return vTraversalConfigCache.getRelatedTypeNodeConfigFor(  theCtxt, theBranchCfg, theRelatedMMElement);
  }





/****************************************************************************

(2)   Replicate into CompPrj1.ownedPorts
        the ownedPorts of the Comp1 and the
        the ownedPorts of the Components recursively synthetized into Comp1
        Except the Ports in the OverridenPortsIdentitySet
        Except the excluded Ports

        Initialize an ExclusionFramesStack as a stack of ExclusionFrames each containing an ExcludedPortIdentitySet
        Replicate the owned Ports
        Replicate the synthetized Ports

          Let XXComp be Comp1
          Invoke (C)
          Invoke (C2)

****************************************************************************/

  protected MMElementWithM3Ifc replicate(
    EAIMMCtxtIfc              theCtxt,
    MMElementWithM3Ifc        theSourceMMElement,
    M3RelationshipPub         theM3Relationship,
    M3BranchConfigIfc         theBranchConfig,
    MMElementWithM3Ifc        theReplicatedMMElement,
    M3RelationshipPub         theM3ReplicatedRelationship) throws EAIException {

    M3ExclusionsFrameIfc anExclusionsFrame = new M3ExclusionsFrame( null);

    return replicate( theCtxt, theSourceMMElement, theM3Relationship, theBranchConfig,
      theReplicatedMMElement, theM3ReplicatedRelationship,
      anExclusionsFrame);

  }



  protected MMElementWithM3Ifc replicate(
    EAIMMCtxtIfc            theCtxt,
    MMElementWithM3Ifc      theSourceMMElement,
    M3RelationshipPub       theM3Relationship,
    M3BranchConfigIfc       theBranchConfig,
    MMElementWithM3Ifc      theReplicatedMMElement,
    M3RelationshipPub       theM3ReplicatedRelationship,
    M3ExclusionsFrameIfc    theExclusionsFrame) throws EAIException {

    if( replicateOwned( theCtxt, theSourceMMElement, theM3Relationship, theBranchConfig,
      theReplicatedMMElement, theM3ReplicatedRelationship,
      theExclusionsFrame) == null) { return null;}

    return replicateSynthetized( theCtxt, theSourceMMElement, theM3Relationship, theBranchConfig,
      theReplicatedMMElement, theM3ReplicatedRelationship,
      theExclusionsFrame);

  }





/****************************************************************************
(C)     ReplicateOwned

        For all the ownedPorts of XXComp :
          aPortOfXXComp = XXComp.ownedPorts[i]

          If aPortOfXXComp is NOT inside the OverridenPortsIdentitySet
            AND aPortOfXXComp is not excluded by the ExclusionStack (see D)
              Create a new XXPortPrj

              Create a new Projection XXProjection
              Set XXProjection source to aPortOfXXComp
              Add the Projection to XXPortPrj

              Add the XXPortPrj to ownedPorts of CompPrj1
****************************************************************************/

  protected MMElementWithM3Ifc replicateOwned(
    EAIMMCtxtIfc              theCtxt,
    MMElementWithM3Ifc        theSourceMMElement,
    M3RelationshipPub         theM3Relationship,
    M3BranchConfigIfc         theBranchConfig,
    MMElementWithM3Ifc        theReplicatedMMElement,
    M3RelationshipPub         theM3ReplicatedRelationship,
    M3ExclusionsFrameIfc      theExclusionsFrame) throws EAIException {

    if( theCtxt == null)                      { return null;}
    if( theBranchConfig == null)              { return null;}

    if( vOverridenPortsIdentitySet == null)   { return null;}

    EAIMMElementIfc[] someRelatedElements =  null;

    someRelatedElements = theM3Relationship.getRelatedElements( theCtxt, theSourceMMElement);

    if( someRelatedElements == null)  {
      return theReplicatedMMElement;
    }

    int aNumRelated = someRelatedElements.length;
    for( int anIndex = 0; anIndex < aNumRelated; anIndex++) {
      EAIMMElementIfc aRelated = someRelatedElements[ anIndex];
      if( aRelated != null) {

        MMElementWithM3Ifc aRelatedWM3 = null;
        try { aRelatedWM3 = (MMElementWithM3Ifc) aRelated;} catch( ClassCastException anEx) {}
        if( aRelatedWM3 != null)  {

          if( !vOverridenPortsIdentitySet.containsKey( aRelated)) {
            if( theExclusionsFrame == null || theExclusionsFrame.isAllowedToSynthetize( theCtxt, aRelatedWM3)) {

              M3RelatedTypeNodeConfigIfc aRelatedtTypeNodeCfg =
                getRelatedTypeNodeConfigFor( theCtxt, theBranchConfig, aRelatedWM3);
              if( aRelatedtTypeNodeCfg != null) {

                M3NodeConfigIfc aNodeCfg = aRelatedtTypeNodeCfg.getNodeConfig( theCtxt);
                if( aNodeCfg != null) {

                  M3NodeMgrIfc aNodeMgr = aNodeCfg.getMetaNodeMgr( theCtxt);
                  if( aNodeMgr != null)  {

                    M3ReplicateNodeMgrIfc aReplicatedNodeMgr = null;
                    try { aReplicatedNodeMgr = (M3ReplicateNodeMgrIfc) aNodeMgr;} catch( ClassCastException anEx) {}
                    if( aReplicatedNodeMgr != null) {

                      M3TypeIfc aReplicatedType = aReplicatedNodeMgr.getReplicatedMetaType( theCtxt);
                      if( aReplicatedType != null) {

                        M3TypePub aReplicatedTypePub = null;
                        try { aReplicatedTypePub = (M3TypePub) aReplicatedType;} catch( ClassCastException anEx) {}
                        if( aReplicatedTypePub != null) {

                          String aNewNameString = new String("");
                          EAIMMNameIfc aName = vSourceMMElement.getName();
                          if( aName != null) {
                            String aNameString = aName.getString();
                            if( aNameString != null) {
                              aNewNameString = aNameString;
                            }
                          }
                          EAIMMNameIfc aNewName = new EAIMMName( aNewNameString);

                          EAIMMElementIfc aNewProjectedElement = null;
                          try { aNewProjectedElement = aReplicatedTypePub.createElement( theCtxt, aNewName);} catch( EAIException anEx) {}
                          if( aNewProjectedElement != null) {

                            EAIMMNameIfc aNewProjectionName = new EAIMMName( aNewNameString + "Projection");

                            EAIMMElementIfc aNewProjectionElement = null;
                            try { aNewProjectionElement = vM3TypeProjection.createElement( theCtxt, aNewProjectionName);} catch( EAIException anEx) {}
                            if( aNewProjectionElement != null) {

                              vM3RelProjectedToProjections.addRelatedElement(  theCtxt, aNewProjectedElement,  aNewProjectionElement);
                              vM3RelProjectionToOriginals.addRelatedElement( theCtxt, aNewProjectionElement, aRelated );

                              theM3ReplicatedRelationship.addRelatedElement(  theCtxt, theReplicatedMMElement,  aNewProjectedElement);
                            }
                          }
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
    return theReplicatedMMElement;
  }




/****************************************************************************
(C2)     Replicate Synthetized :

        For all the synthesis of XXComp:
          aSynthOntoXXComp = XXComp.synthesis[j]

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

            Recurse into (C0) : Let XXComp = aSourceCompOfSynthOntoXXComp

          If an ExclusionFrame was pushed
            pop topmost ExclusionFrame
****************************************************************************/

  protected MMElementWithM3Ifc replicateSynthetized(
    EAIMMCtxtIfc            theCtxt,
    MMElementWithM3Ifc      theSourceMMElement,
    M3RelationshipPub       theM3Relationship,
    M3BranchConfigIfc       theBranchConfig,
    MMElementWithM3Ifc      theReplicatedMMElement,
    M3RelationshipPub       theM3ReplicatedRelationship,
    M3ExclusionsFrameIfc    theExclusionsFrame) throws EAIException {


    EAIMMNameIfc aRelationshipName = theM3Relationship.getName();
    if( aRelationshipName == null)                { return null;}

    String aRelationshipNameString = aRelationshipName.getString();
    if( aRelationshipNameString == null)          { return null;}
    if( aRelationshipNameString.length() < 1)     { return null;}

    EAIMMElementIfc[] someSynthesis =  vM3RelTargetToTransfomations.getRelatedElements( theCtxt, theSourceMMElement);
    if( someSynthesis == null) { return null;}

    int aNumSynthesis = someSynthesis.length;
    for( int anIndex = 0; anIndex < aNumSynthesis; anIndex++) {

      EAIMMElementIfc aSynthesis = someSynthesis[ anIndex];
      if( aSynthesis != null) {

        M3ExclusionsFrameIfc anExclusionsFrame = theExclusionsFrame;

        EAIMMElementIfc[] someSubTransformations = vM3RelTransformationToSubTransformations.getRelatedElements( theCtxt, aSynthesis);
        if( someSubTransformations!= null) {

          int aNumSubTransformations = someSubTransformations.length;
          for( int otherIndex = 0; otherIndex < aNumSubTransformations; otherIndex++) {

            EAIMMElementIfc aSubTransformation = someSubTransformations[ anIndex];
            if( aSubTransformation != null) {

              MMElementWithM3Ifc aSubTransformationWM3 = null;
              try { aSubTransformationWM3 = (MMElementWithM3Ifc) aSubTransformation;} catch( ClassCastException anEx) {}
              if( aSubTransformationWM3 != null) {

                M3TypeIfc aSubTransformationM3Type = aSubTransformationWM3.getM3Type( theCtxt);
                if( aSubTransformationM3Type != null) {
                  if( vM3TypeExclusion == null || aSubTransformationM3Type == vM3TypeExclusion) {
                    if( vM3AttributeExcludedInRelationshipNamed != null) {
                      Object aExcludedInRelationshipNamedObject =  vM3AttributeExcludedInRelationshipNamed.getAttributeValue( theCtxt, aSubTransformation);
                      if( aExcludedInRelationshipNamedObject != null) {

                        String aExcludedInRelationshipName = null;
                        try { aExcludedInRelationshipName = (String) aExcludedInRelationshipNamedObject;} catch( ClassCastException anEx) {}
                        if( aExcludedInRelationshipName != null && aExcludedInRelationshipName.equals( aRelationshipNameString)) {

                          anExclusionsFrame = new M3ExclusionsFrame( theExclusionsFrame);

                          EAIMMElementIfc[] someSubTransformationSources =
                            vM3RelTransformationToSources.getRelatedElements( theCtxt, aSubTransformation);
                          if( someSubTransformationSources != null) {

                            int aNumSubTransformationSources = someSubTransformationSources.length;
                            for( int anotherIndex = 0; anotherIndex < aNumSubTransformationSources; anotherIndex++) {

                             EAIMMElementIfc aSubTransformationSource = someSubTransformationSources[ anotherIndex];
                              if( aSubTransformationSource != null) {

                                MMElementWithM3Ifc aSubTransformationSourceWM3 = null;
                                try { aSubTransformationSourceWM3 = (MMElementWithM3Ifc) aSubTransformationSource;} catch( ClassCastException anEx) {}
                                if( aSubTransformationSourceWM3 != null) {

                                  anExclusionsFrame.forbidToSynthetize( theCtxt, aSubTransformationSourceWM3);
                                }
                              }
                            }
                          }
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }

        EAIMMElementIfc[] someSources =  vM3RelTransformationToSources.getRelatedElements( theCtxt, aSynthesis);
        if( someSources != null) {

          int aNumSources = someSources.length;
          for( int otherIndex = 0; otherIndex < aNumSources; otherIndex++) {
            EAIMMElementIfc aSource = someSources[ otherIndex];
            if( aSource != null) {

              MMElementWithM3Ifc aSourceWM3 = null;
              try { aSourceWM3 = (MMElementWithM3Ifc) aSource;} catch( ClassCastException anEx) {}
              if( aSourceWM3 != null) {

                replicate( theCtxt, aSourceWM3, theM3Relationship, theBranchConfig,
                  theReplicatedMMElement, theM3ReplicatedRelationship,
                  anExclusionsFrame);
              }
            }
          }
        }
      }
    }
    return theReplicatedMMElement;
   }



}





