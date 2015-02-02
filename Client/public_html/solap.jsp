<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>

<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
            <title>SOLAP+</title>
            <script language="JavaScript" src="oraclemaps.js"
                    type="text/javascript"></script>
            <script language="JavaScript" src="solapplus.js"
                    type="text/javascript"></script>
            <script language="JavaScript" src="persist.js"
                    type="text/javascript"></script>
                    
            <!-- edit slice/spatial/filter -->
            <script type="text/javascript">
            
                // create a new client-side persistent data store
                var records = new Persist.Store('MapState');
                
                //Used to clean state map when we load a new or previous saved session
                function cleanRecords() {
                    themebasedfoi = new Array();
                    records.set('zoom', "");
                    records.set('x', "");
                    records.set('y', "");
                    records.set('labelST', "false");
                    records.set('layers', "");
                    records.set('legend', "false");
                    
                    records.set('labelST1', "false");
                    records.set('labelST2', "false");
                }

                function showIt() {
                    if (document.getElementById('hiddenData:editSlice').value == 'true') {
                        document.getElementById('modalSlice:addSlice').component.show();
                    }
                    if (document.getElementById('hiddenData:editSpatialSlice').value == 'true') {
                        document.getElementById('modalSpatialSlice:addSpatialSlice').component.show();
                    }
                    if (document.getElementById('hiddenData:editFilter').value == 'true') {
                        document.getElementById('modalFilter:addFilter').component.show();
                    }
                    if (document.getElementById('hiddenData:error').value == 'true') {
                        document.getElementById('errorPanel').component.show();
                    }
                    
                }
            </script>
            
            <style type="text/css">
                .class1 A:link {text-decoration: none; color: red}
                .class1 A:visited {text-decoration: none}
                .class1 A:active {text-decoration: none}
                .class1 A:hover {text-decoration: none}
                .modalText {
                    font-family: Arial, Helvetica, sans-serif;
                    font-size: 11px;
                }
               

            </style>

        </head>
        <body onload="loadMainMap();showIt();alignNumbers()">
            <rich:dragIndicator id="indicator"/>
             
            <!-- toolbar -->
            <h:form style="margin-bottom:0;">
                <rich:toolBar>
                    <rich:dropDownMenu>
                        <f:facet name="label">
                            <h:outputText value="Model/Session"/>
                        </f:facet>
                        <rich:menuItem submitMode="none">
                            <h:outputLink value="#" id="load">
                                Load
                                <rich:componentControl for="loadPanel"
                                                       attachTo="load"
                                                       operation="show"
                                                       event="onclick"/>
                            </h:outputLink>
                        </rich:menuItem>
                        <rich:menuItem submitMode="none">
                            <h:outputLink value="#" id="save">
                                Save
                                <rich:componentControl for="savePanel"
                                                       attachTo="save"
                                                       operation="show"
                                                       event="onclick"/>
                            </h:outputLink>
                        </rich:menuItem>
                    </rich:dropDownMenu>
                    <rich:dropDownMenu>
                        <f:facet name="label">
                            <h:outputText value="View"/>
                        </f:facet>
                        <rich:menuItem submitMode="none">
                            <h:commandLink action="#{PanelBean.switchControlPanel}">
                                <h:outputText value="Control Panel"></h:outputText>
                            </h:commandLink>
                        </rich:menuItem>
                        <rich:menuItem submitMode="none">
                            <h:commandLink action="#{PanelBean.switchDataModelPanel}">
                                <h:outputText value="SOLAP Model"></h:outputText>
                            </h:commandLink>
                        </rich:menuItem>
                    </rich:dropDownMenu>
                    <rich:dropDownMenu>
                        <f:facet name="label">
                            <h:outputText value="Preferences"/>
                        </f:facet>
                        <rich:menuItem submitMode="none">
                            <h:outputLink value="#" id="managerStyles"> 
                                Styles Manager
                                <rich:componentControl for="managerStylesPanel"
                                                       attachTo="managerStyles"
                                                       operation="show"
                                                       event="onclick"/>
                            </h:outputLink>
                        </rich:menuItem>
                    </rich:dropDownMenu>
                    <rich:dropDownMenu rendered="false">
                        <f:facet name="label">
                            <h:commandLink>
                                <h:outputText value="Info"></h:outputText>
                            </h:commandLink>
                        </f:facet>
                    </rich:dropDownMenu>
                </rich:toolBar>
            </h:form>
             
               
             
            <!-- options / map panels -->
            <table id="mainDisplay" >
                <tr valign="top">
                    <!-- left panel -->
                    <rich:column  
                                 rendered="#{PanelBean.displayControlPanel}"
                                  id="controlPanelCol" styleClass="altura" >
                                  
                        <rich:panel header="Control Panel" style="width:210px;height:710px;overflow-y:scroll;">
                            <!-- map control -->
                            <h:form id="mapControlForm">
                               
                                <rich:simpleTogglePanel switchType="ajax" label="Map Control" width="100%" id="mapControlPanel">
                            
                              <!--  <table style="font:11px arial;width:120px;">
                                    <tr>
                                        <td style="width:90px;">Zoom Mode</td>
                                        <td><h:selectBooleanCheckbox id="marqueeZoomCheckbox" onchange="switchMarqueeZoom(this)"/></td>
                                    </tr>
                                </table>
                                -->
                                
                                <table style="font:11px arial;width:120px;">
                                    <tr>
                                        <td style="width:90px;">Legend</td>
                                        <td><h:selectBooleanCheckbox id="legendCheckbox" onchange="switchLegend(this)" /></td>
                                    </tr>
                                </table>
                                <rich:separator height="2"/>
                                <table style="font:11px arial;width:120px;">
                                    <a4j:repeat value="#{SessionBean.layers}" var="layer1">
                                        <tr>
                                            <td style="width:90px;"><h:outputText value="#{layer1.title}" /></td>
                                            <td>
                                                <h:selectBooleanCheckbox value="#{layer1.activate}" >
                                                    <a4j:support ajaxSingle="true" event="onchange" action="#{layer1.changeActivate}" oncomplete="setLayers('#{layer1.name}', #{layer1.id});" />
                                                </h:selectBooleanCheckbox>
                                                
                                            </td>
                                           
                                        </tr>
                                    </a4j:repeat>
                                </table>
                                
                                <rich:separator rendered="#{!empty SessionBean.dimensions}" height="2"/>
                                
                                <table style="font:11px arial;width:120px;">
                                    <a4j:repeat value="#{SessionBean.layersLabel}" var="layer2">
                                        <tr>
                                            <td style="width:90px;"><h:outputText value="#{layer2.title}" /></td>
                                            <td>
                                            <h:selectBooleanCheckbox value="#{layer2.activate}">
                                                 <a4j:support ajaxSingle="true" event="onchange" action="#{layer2.changeActivate}" oncomplete="setLayersLabel('#{layer2.name}', #{layer2.id}, #{layer2.columnRef});" />
                                            </h:selectBooleanCheckbox>
                                            </td>
                                        </tr>
                                    </a4j:repeat>
                                        <tr>
                                            <td style="width:90px;"><h:outputText value="Label Groups" rendered="#{SessionBean.clustering && MainBean.numberAssocAttr == 1}"/></td>
                                            <td>
                                            <h:selectBooleanCheckbox id="labelGroups" rendered="#{SessionBean.clustering && MainBean.numberAssocAttr == 1}"
                                                                     value="#{SessionBean.labelGroupsChecked}">
                                                 <a4j:support ajaxSingle="true" event="onchange" oncomplete="labelGroups();" />
                                            </h:selectBooleanCheckbox>
                                            </td>
                                        </tr>
                                        
                                        <tr>
                                            <td style="width:90px;"><h:outputText value="#{SessionBean.labelGroup1}" rendered="#{SessionBean.clustering && MainBean.numberAssocAttr == 2}"/></td>
                                            <td>
                                            <h:selectBooleanCheckbox id="labelGroups1" rendered="#{SessionBean.clustering && MainBean.numberAssocAttr == 2}"
                                                                   value="#{SessionBean.labelGroups1Checked}">
                                                 <a4j:support ajaxSingle="true" event="onchange" oncomplete="labelGroups1();" />
                                            </h:selectBooleanCheckbox>
                                            </td>
                                        </tr>
                                        
                                        <tr>
                                            <td style="width:90px;"><h:outputText value="#{SessionBean.labelGroup2}" rendered="#{SessionBean.clustering && MainBean.numberAssocAttr == 2}"/></td>
                                            <td>
                                            <h:selectBooleanCheckbox id="labelGroups2" rendered="#{SessionBean.clustering && MainBean.numberAssocAttr == 2}"
                                                                   value="#{SessionBean.labelGroups2Checked}">
                                                 <a4j:support ajaxSingle="true" event="onchange" oncomplete="labelGroups2();" />
                                            </h:selectBooleanCheckbox>
                                            </td>
                                        </tr>
                                        
                                        <tr>
                                            <td style="width:90px;"><h:outputText value="Label Intersected Polygons" rendered="#{SessionBean.newSpatialObjects}"/></td>
                                            <td>
                                            <h:selectBooleanCheckbox id="newSpatialObjects" rendered="#{SessionBean.newSpatialObjects}" value="#{SessionBean.newSpatialObjects}">
                                                 <a4j:support ajaxSingle="true" event="onchange" reRender="newSpatialObjects" oncomplete="labelIntersection();"/>
                                            </h:selectBooleanCheckbox>
                                            </td>
                                        </tr>
                                </table>
                                
                            </rich:simpleTogglePanel>
                            
                            </h:form>
                            
                            <h:form id="sumarizationControlForm">
                                    <rich:simpleTogglePanel switchType="ajax" label="Summarization Control" width="100%" id="sumarizationControlPanel">
                                        <p>Drag an attribute or a measure here</p>
                                        <rich:separator height="2"/>
                                        <rich:dropSupport id="dropSumarization" acceptedTypes="attribute, measure_noop, measure" dropListener="#{SessionBean.processDropSumarizationTable}" oncomplete="document.getElementById('sumarizationControlForm').submit();" />
                                        <h:dataTable id="dataAttSumarization" value="#{SessionBean.attributesSumarizationTable}" var="att" binding="#{SessionBean.dataAST}">
                                            <h:column>
                                                <table style="font:11px arial;width:150px;">
                                                    <tr>
                                                        <td style="width:130px;" >
                                                            <h:outputText value="#{att.attName}" />
                                                        </td>
                                                        <td>
                                                            <a4j:commandLink action="#{SessionBean.removeFromAST}" reRender="generalizationModalPanel,dataAttSumarization,generalizationModalPanel2,sumarizationControlPanel">
                                                                X
                                                            </a4j:commandLink>
                                                        </td>
                                                    </tr>
                                                </table>
                                            </h:column>
                                        </h:dataTable>
                                        <h:dataTable id="dataMeaSumarization" value="#{SessionBean.measuresSumarizationTable}" var="mea" binding="#{SessionBean.dataMST}">
                                            <h:column>
                                                <table style="font:11px arial;width:150px;">
                                                    <tr>
                                                        <td style="width:130px;" >
                                                            <h:outputText value="#{mea.nameToShow}" />
                                                        </td>
                                                        <td>
                                                            <a4j:commandLink action="#{SessionBean.removeFromMST}" reRender="generalizationModalPanel,generalizationModalPanel2,dataMeaSumarization">
                                                                X
                                                            </a4j:commandLink>
                                                        </td>
                                                    </tr>
                                                </table>
                                            </h:column>
                                        </h:dataTable>
                                        <table style="width:150px;">
                                            <tr>
                                                <td align="left">
                                                    <h:outputLink value="#" id="sumarizationOptions">
                                                        Options
                                                        <rich:componentControl for="sumarizationOptionsModalPanel" attachTo="sumarizationOptions" operation="show" event="onclick"/>
                                                    </h:outputLink>
                                                <td>
                                                <td align="center" width="80">
                                                    <h:outputLink value="#" id="ft" rendered="#{SessionBean.diferentDimensions}" >
                                                        Fact Tables
                                                        <rich:componentControl for="factModalPanel" attachTo="ft" operation="show" event="onclick"/>
                                                    </h:outputLink>
                                                <td>
                                                <td align="right">
                                                    <h:outputLink value="#" id="generalizationLink">
                                                        Generalize
                                                        <rich:componentControl for="generalizationModalPanel" attachTo="generalizationLink" operation="show" event="onclick"/>
                                                    </h:outputLink>
                                                <td>
                                            </tr>
                                        </table>
                                    </rich:simpleTogglePanel>
                                </h:form>
                            
                            <h:form id="clustControlForm">
                                <rich:simpleTogglePanel switchType="ajax"
                                                        label="Clustering Control"
                                                        width="100%"
                                                        id="clustControlPanel">
                                    <h:outputText styleClass="modalText"
                                                  value="Mode:"/>
                                    <br/>
                                    <h:selectOneRadio value="#{SessionBean.infoClustering.modeClustering}"
                                                      layout="pageDirection"
                                                      style="font:11px arial;width:120px;">
                                        <f:selectItem itemLabel="No Clustering"
                                                      itemValue="NO"/>
                                        <f:selectItem itemLabel="Auto-Clustering"
                                                      itemValue="AUTO"/>
                                        <f:selectItem itemLabel="Clustering"
                                                      itemValue="MANUAL"/>
                                        <a4j:support event="onchange"
                                                     reRender="mapCol,styleXML"
                                                     action="#{MainBean.execute}"
                                                     oncomplete="window.document.forms[0].submit();"/>
                                    </h:selectOneRadio>
                                    <table style="font:11px arial;width:75px;">
                                        <tr>
                                            <td align="left">
                                                <h:outputText styleClass="modalText"
                                                              value="Restricted (1st dimension)"/>
                                                 
                                                <rich:comboBox id="pickSpatialHierarchy1"
                                                               value="#{SessionBean.infoClustering.hierarchyChosen1}">
                                                    <f:selectItem itemLabel="None"
                                                                  itemValue="None"/>
                                                    <f:selectItems id="spatialHierarchies1"
                                                                   value="#{SessionBean.infoClustering.hierarchies1}"/>
                                                    <a4j:support event="onselect"
                                                                 reRender="pickSpatialHierarchy1,mapCol,styleXML"
                                                                 action="#{MainBean.execute}"
                                                                 oncomplete="window.document.forms[0].submit();"/>
                                                </rich:comboBox>
                                                 
                                                <rich:spacer height="25"
                                                             rendered="#{MainBean.numberAssocAttr == 2}"/>
                                                 
                                                <h:outputText styleClass="modalText"
                                                              value="Restricted (2nd dimension)"
                                                              rendered="#{MainBean.numberAssocAttr == 2}"/>
                                                 
                                                <rich:comboBox id="pickSpatialHierarchy2"
                                                               value="#{SessionBean.infoClustering.hierarchyChosen2}"
                                                               rendered="#{MainBean.numberAssocAttr == 2}">
                                                    <f:selectItem itemLabel="None"
                                                                  itemValue="None"/>
                                                    <f:selectItems id="spatialHierarchies2"
                                                                   value="#{SessionBean.infoClustering.hierarchies2}"/>
                                                    <a4j:support event="onselect"
                                                                 reRender="pickSpatialHierarchy2,mapCol,styleXML"
                                                                 action="#{MainBean.execute}"
                                                                 oncomplete="window.document.forms[0].submit();"/>
                                                </rich:comboBox>
                                            </td>
                                        </tr>
                                         
                                        <tr>
                                            <td align="center">
                                                <br/>
                                                 
                                                <h:outputText styleClass="modalText"
                                                              value="Groups"/>
                                                 
                                                <br/>
                                                                                                       
                                                <rich:inputNumberSlider value="#{SessionBean.infoClustering.groupsParameter}"
                                                                        minValue="-2"
                                                                        maxValue="2"
                                                                        step="1"
                                                                        showInput="false"
                                                                        width="100"
                                                                        enableManualInput="false"
                                                                        showBoundaryValues="true"
                                                                        showToolTip="true">
                                                    <a4j:support event="onchange"
                                                                 reRender="mapCol,styleXML"
                                                                 action="#{MainBean.execute}"
                                                                 oncomplete="window.document.forms[0].submit();"/>
                                                </rich:inputNumberSlider>
                                                 
                                                <br/>
                                                 
                                                <rich:separator height="2"/>
                                            </td>
                                        </tr>
                                         
                                        <tr>
                                            <td align="left">
                                                <h:outputText value="Level Applied (1st dimension):"/>
                                                 
                                                <h:inputText value="#{SessionBean.infoClustering.currentRestrictedLevel1}"/>
                                                 
                                                <h:outputText value="Level Applied (2nd dimension):"
                                                              rendered="#{MainBean.numberAssocAttr == 2}"/>
                                                 
                                                <rich:spacer height="25"
                                                             rendered="#{MainBean.numberAssocAttr == 2}"/>
                                                 
                                                <h:inputText value="#{SessionBean.infoClustering.currentRestrictedLevel2}"
                                                             rendered="#{MainBean.numberAssocAttr == 2}"/>
                                            </td>
                                        </tr>
                                    </table>
                                </rich:simpleTogglePanel>
                            </h:form>
                            <!-- slices -->
                            <!-- modal panel for slice add -->
                            <h:form id="modalSlice">
                                <rich:modalPanel id="addSlice" width="450"
                                                 height="500">
                                    <f:facet name="header">
                                        <h:outputText value="Add Alphanumeric Slice"></h:outputText>
                                    </f:facet>
                                    <f:facet name="controls">
                                        <h:panelGroup>
                                            <a4j:commandLink id="closeLink"
                                                           value="Close"/>
                                            <rich:componentControl for="addSlice"
                                                                   attachTo="closeLink"
                                                                   operation="hide"
                                                                   event="onclick">
                                            </rich:componentControl>
                                        </h:panelGroup>
                                    </f:facet>
                                    <table>
                                        <tr>
                                            <td width="40%" rowspan="2"
                                                valign="top">
                                                <h:outputText styleClass="modalText"
                                                              value="Attribute: #{SessionBean.tempName}"/>
                                            </td>
                                            <td width="10%">&nbsp;</td>
                                            <td width="50%">
                                                <h:outputText styleClass="modalText"
                                                              value="Name: "/>
                                                 
                                                <h:inputText value="#{SessionBean.tempFilterName}"/><br/><br/><br/>
                                                 
                                                <h:outputText styleClass="modalText"
                                                              value="Description:"/>
                                                 
                                                <br/><br/><h:inputTextarea value="#{SessionBean.tempFilterDesc}"
                                                                           cols="30"
                                                                           rows="4"/>
                                            </td>
                                        </tr>
                                    </table>
                                    <br/>
                                    <br/>
                                    <h:outputText value="Select considered values:"/>
                                    <br/>
                                    <br/>
                                    <rich:pickList value="#{SessionBean.tempSelectedValues}"
                                                   copyAllControlLabel="Add all"
                                                   copyControlLabel="Add">
                                        <f:selectItems value="#{SessionBean.tempDistincts}"/>
                                    </rich:pickList>
                                    <br/>
                                    Add a slider:
                                    <h:selectBooleanCheckbox value="#{SessionBean.tempSlider}"/>
                                    <br/>
                                    <br/>
                                    <div align="center">
                                        <table>
                                            <tr>
                                                <td width="100" align="left">
                                                    <a4j:commandButton id="deleteSlice3"
                                                                     value="Delete"
                                                                     onclick="document.getElementById('sliceDelete').component.show()"/>
                                                </td>
                                                <td width="100" align="center">
                                                    <a4j:commandButton id="cancelSlice3"
                                                                     value="Cancel"
                                                                     action="#{SessionBean.cancelSlice}"
                                                                     onclick="document.getElementById('modalSlice:addSlice').component.hide()"/>
                                                </td>
                                                <td width="100" align="right">
                                                    <h:commandButton id="execSlice3"
                                                                     value="Save"
                                                                     action="#{SessionBean.addSlice}"
                                                                     onclick="document.getElementById('modalSlice:addSlice').component.hide()"/>
                                                </td>
                                            </tr>
                                        </table>
                                    </div>
                                </rich:modalPanel>
                            </h:form>
                            <h:form style="margin-bottom:0;" id="sliceForm">
                                <rich:simpleTogglePanel switchType="ajax"
                                                        label="Slices"
                                                        width="100%"
                                                        id="slicePanel">
                                    <rich:dropSupport id="dropSlice"
                                                      acceptedTypes="attribute"
                                                      dropListener="#{SessionBean.processDropSlice}"
                                                      oncomplete="document.getElementById('modalSlice:addSlice').component.show()"
                                                      reRender="addSlice">
                                    </rich:dropSupport>
                                    
                                    <p>Drag an attribute here to add a slice</p>
                                    <rich:separator height="2"/>
                                    <!-- active slices -->
                                    <rich:dataList value="#{SessionBean.ISlices}"
                                                   var="slice">
                                        <h:commandLink action="#{SessionBean.setEditSlice}">
                                            <f:param value="#{slice.filterName}"
                                                     name="activeSlice"/>
                                            <h:outputText value="#{slice.filterName}"/>
                                            <br/>
                                            
                                        </h:commandLink>
                                        
                                        <rich:toolTip value="#{slice.filterDescription}"/>
                                        
                                    </rich:dataList>
                                    
                                    <rich:dataList value="#{SessionBean.ISliders}"
                                                   var="slider"
                                                   rendered="#{!empty(SessionBean.ISliders)}" >
                                        <h:commandLink action="#{SessionBean.setEditSlice}">
                                            <f:param value="#{slider.filterName}"
                                                     name="activeSlice"/>
                                            <h:outputText value="#{slider.filterName} (slider)"/>
                                            <br/>
                                        </h:commandLink>
                                        
                                        <rich:inputNumberSlider value="#{slider.sliderValue}"
                                                                maxValue="#{slider.sliderOptions}"
                                                                showInput="false"
                                                                width="80"
                                                                enableManualInput="false"
                                                                showBoundaryValues="false"
                                                                showToolTip="true">
                                            <a4j:support event="onchange"
                                                         reRender="sliceval,mapCol"
                                                         action="#{MainBean.execute}"
                                                         oncomplete="window.document.forms[0].submit();"/>
                                        </rich:inputNumberSlider>
                                        <br/>
                                        <h:outputText id="sliceval" value="#{slider.sliderTextValue}"/>
                                    </rich:dataList>
                                    <!-- -->
                                </rich:simpleTogglePanel>
                            </h:form>
                            <!-- spatial slices -->
                            <!-- modal panel for spatial slice add -->
                            <h:form id="modalSpatialSlice">
                                <rich:modalPanel id="addSpatialSlice"
                                                 width="400" height="450">
                                    <f:facet name="header">
                                        <h:panelGroup>
                                            <h:outputText value="Add Spatial Slice"></h:outputText>
                                        </h:panelGroup>
                                    </f:facet>
                                    <f:facet name="controls">
                                        <h:panelGroup>
                                            <a4j:commandLink id="closeLink2"
                                                           value="Close"/>
                                            <rich:componentControl for="addSpatialSlice"
                                                                   attachTo="closeLink2"
                                                                   operation="hide"
                                                                   event="onclick"/>
                                        </h:panelGroup>
                                    </f:facet>
                                    <table>
                                        <tr>
                                            <td width="40%" rowspan="2"
                                                valign="top">
                                                <h:outputText styleClass="modalText"
                                                              value="Attribute: #{SessionBean.tempName}"/><br/><br/>
                                            </td>
                                            <td width="10%">&nbsp;</td>
                                            <td width="50%">
                                                <h:outputText styleClass="modalText"
                                                              value="Name: "/>
                                                 
                                                <h:inputText value="#{SessionBean.tempFilterName}"/><br/><br/><br/>
                                                 
                                                <h:outputText styleClass="modalText"
                                                              value="Description: "/>
                                                 
                                                <br/><br/><h:inputTextarea value="#{SessionBean.tempFilterDesc}"
                                                                           cols="30"
                                                                           rows="4"/>
                                            </td>
                                        </tr>
                                    </table>
                                    <br/>
                                    <h:outputText value="Operator:"/>
                                    <rich:comboBox defaultLabel=""
                                                   value="#{SessionBean.tempOperator}">
                                        <f:selectItem itemValue="INSIDE"/>
                                        <f:selectItem itemValue="WITHIN_DISTANCE"/>
                                        <f:selectItem itemValue="NEIGHBOURS"/>
                                    </rich:comboBox>
                                    <br/>
                                    <br/>
                                    <table width="80%">
                                        <tr>
                                            <td width="40%">
                                                <h:outputText styleClass="modalText"
                                                              value="Value: "/><br/>
                                                 
                                                <h:inputText value="#{SessionBean.tempValue}"/>
                                            </td>
                                            <td width="40%">
                                                <h:outputText styleClass="modalText"
                                                              value="Unit:"/><br/>
                                                 
                                                <rich:comboBox defaultLabel=""
                                                               value="#{SessionBean.tempUnit}">
                                                    <f:selectItem itemValue="METER"/>
                                                    <f:selectItem itemValue="KILOMETER"/>
                                                    <f:selectItem itemValue="MILE"/>
                                                </rich:comboBox>
                                            </td>
                                        </tr>
                                    </table>
                                    <br/>
                                    <br/>
                                    <h:outputText styleClass="modalText"
                                                  value="Spatial object:"/>
                                    <rich:comboBox value="#{SessionBean.tempLayerName}">
                                        <f:selectItems value="#{SessionBean.layerNames}"/>
                                    </rich:comboBox>
                                    <br/>
                                    <br/>
                                    <div align="center">
                                        <table>
                                            <tr>
                                                <td width="100" align="left">
                                                    <h:commandButton id="commandButton1"
                                                                     value="Delete"
                                                                     onclick="document.getElementById('spatialSliceDelete').component.show()"/>
                                                </td>
                                                <td width="100" align="center">
                                                    <a4j:commandButton id="commandButton2"
                                                                     value="Cancel"
                                                                     action="#{SessionBean.cancelSpatialSlice}"
                                                                     onclick="document.getElementById('modalSpatialSlice:addSpatialSlice').component.hide()"/>
                                                </td>
                                                <td width="100" align="right">
                                                    <h:commandButton id="commandButton3"
                                                                     value="Save"
                                                                     action="#{SessionBean.addSpatialSlice}"
                                                                     onclick="document.getElementById('modalSpatialSlice:addSpatialSlice').component.hide()"/>
                                                </td>
                                            </tr>
                                        </table>
                                    </div>
                                </rich:modalPanel>
                            </h:form>
                            <h:form style="margin-bottom:0;"
                                    id="spatialSliceForm">
                                <rich:simpleTogglePanel switchType="ajax"
                                                        label="Spatial Slices"
                                                        width="100%"
                                                        id="spatialSlicePanel">
                                    <rich:panel>
                                        <rich:dropSupport id="dropSpatialSlice"
                                                          acceptedTypes="attribute"
                                                          dropListener="#{SessionBean.processDropSpatialSlice}"
                                                          reRender="addSpatialSlice">
                                            <p>Drag a spatial attribute here to
                                               add a spatial slice</p>
                                        </rich:dropSupport>
                                    </rich:panel>
                                    <rich:panel>
                                        <rich:dropSupport id="dropSpatialSlice2"
                                                          acceptedTypes="layer"
                                                          dropListener="#{SessionBean.processDropSpatialSlice}"
                                                          oncomplete="document.getElementById('modalSpatialSlice:addSpatialSlice').component.show()"
                                                          reRender="addSpatialSlice">
                                            <p>Drag a layer or spatial object
                                               here</p>
                                        </rich:dropSupport>
                                    </rich:panel>
                                    <rich:separator height="2"/>
                                    <rich:dataList value="#{SessionBean.ISpatialSlices}"
                                                   var="spatialSlice">
                                        <h:commandLink action="#{SessionBean.setEditSpatialSlice}">
                                            <f:param value="#{spatialSlice.filterName}"
                                                     name="activeSpatialSlice"/>
                                            <h:outputText value="#{spatialSlice.filterName}"/>
                                            <br/>
                                        </h:commandLink>
                                        
                                        <rich:toolTip value="#{spatialSlice.filterDescription}" />
                                    </rich:dataList>
                                </rich:simpleTogglePanel>
                            </h:form>
                            <!-- measure filters -->
                            <!-- modal panel for filter -->
                            <h:form id="modalFilter">
                                <rich:modalPanel id="addFilter" width="450"
                                                 height="470">
                                    <f:facet name="header">
                                        <h:outputText value="Add Measure Filter"></h:outputText>
                                    </f:facet>
                                    <f:facet name="controls">
                                        <h:panelGroup>
                                            <a4j:commandLink id="closeLink"
                                                           value="Close"/>
                                            <rich:componentControl for="addFilter"
                                                                   attachTo="closeLink"
                                                                   operation="hide"
                                                                   event="onclick"/>
                                        </h:panelGroup>
                                    </f:facet>
                                    <table>
                                        <tr>
                                            <td colspan="2">
                                                <table>
                                                    <tr>
                                                        <td width="40%"
                                                            rowspan="2"
                                                            valign="top">
                                                            <h:outputText styleClass="modalText"
                                                                          value="Measure: #{SessionBean.tempName}"/><br/><br/>
                                                        </td>
                                                        <td width="10%">&nbsp;</td>
                                                        <td width="50%">
                                                            <h:outputText styleClass="modalText"
                                                                          value="Name: "/>
                                                             
                                                            <h:inputText value="#{SessionBean.tempFilterName}"/><br/><br/><br/>
                                                             
                                                            <h:outputText styleClass="modalText"
                                                                          value="Description: "/>
                                                             
                                                            <br/><br/><h:inputTextarea value="#{SessionBean.tempFilterDesc}"
                                                                                       cols="30"
                                                                                       rows="4"/>
                                                        </td>
                                                    </tr>
                                                </table>
                                                <br/>
                                            </td>
                                        </tr>
                                         
                                        <tr valign="bottom">
                                            <td width="50%" align="center"
                                                valign="bottom">
                                                <div align="left">
                                                    <h:outputText styleClass="modalText"
                                                                  value="Conditions: "/>
                                                </div>
                                                <br/><br/>
                                                 
                                                <rich:comboBox defaultLabel=""
                                                               value="#{SessionBean.tempOperator1}">
                                                    <f:selectItem itemLabel="Greater than"
                                                                  itemValue="GREATER"/>
                                                    <f:selectItem itemLabel="Lower than"
                                                                  itemValue="LESS"/>
                                                    <f:selectItem itemLabel="Greater or equal to"
                                                                  itemValue="GREATER_OR_EQUAL"/>
                                                    <f:selectItem itemLabel="Lower or equal to"
                                                                  itemValue="LESS_OR_EQUAL"/>
                                                    <f:selectItem itemLabel="Equal to"
                                                                  itemValue="EQUAL"/>
                                                </rich:comboBox>
                                            </td>
                                            <td width="50%" align="center"
                                                valign="bottom">
                                                <h:inputText value="#{SessionBean.tempValue1}"/>
                                            </td>
                                        </tr>
                                         
                                        <tr valign="bottom">
                                            <td width="50%" align="center"
                                                valign="bottom">
                                                <br/><h:outputText styleClass="modalText"
                                                                   value="and"/><br/><br/>
                                                 
                                                <rich:comboBox defaultLabel=""
                                                               value="#{SessionBean.tempOperator2}">
                                                    <f:selectItem itemLabel="Greater than"
                                                                  itemValue="GREATER"/>
                                                    <f:selectItem itemLabel="Lower than"
                                                                  itemValue="LESS"/>
                                                    <f:selectItem itemLabel="Greater or equal to"
                                                                  itemValue="GREATER_OR_EQUAL"/>
                                                    <f:selectItem itemLabel="Lower or equal to"
                                                                  itemValue="LESS_OR_EQUAL"/>
                                                    <f:selectItem itemLabel="Equal to"
                                                                  itemValue="EQUAL"/>
                                                </rich:comboBox>
                                            </td>
                                            <td width="50%" align="center"
                                                valign="bottom">
                                                <h:inputText value="#{SessionBean.tempValue2}"/>
                                            </td>
                                        </tr>
                                    </table>
                                    <br/>
                                    <br/>
                                    <div align="center">
                                        <table>
                                            <tr>
                                                <td width="100" align="left">
                                                    <h:commandButton id="deleteSlice3"
                                                                     value="Delete"
                                                                     onclick="document.getElementById('filterDelete').component.show()"/>
                                                </td>
                                                <td width="100" align="center">
                                                    <a4j:commandButton id="cancelSlice3"
                                                                     value="Cancel"
                                                                     action="#{SessionBean.cancelFilter}"
                                                                     onclick="document.getElementById('modalFilter:addFilter').component.hide()"/>
                                                </td>
                                                <td width="100" align="right">
                                                    <h:commandButton id="execSlice3"
                                                                     value="Save"
                                                                     action="#{SessionBean.addFieldFilter}"
                                                                     onclick="document.getElementById('modalFilter:addFilter').component.hide()"/>
                                                </td>
                                            </tr>
                                        </table>
                                    </div>
                                </rich:modalPanel>
                            </h:form>
                            <h:form id="filterForm">
                                <rich:simpleTogglePanel switchType="ajax"
                                                        label="Measure Filters"
                                                        width="100%"
                                                        id="measureFiltersPanel">
                                    <rich:dropSupport id="dropFilter"
                                                      acceptedTypes="measure_noop"
                                                      dropListener="#{SessionBean.processDropMeasureFilter}"
                                                      oncomplete="document.getElementById('modalFilter:addFilter').component.show()"
                                                      reRender="addFilter"></rich:dropSupport>
                                    <p>Drag a measure here to add a filter</p>
                                    <rich:separator height="2"/>
                                    <rich:dataList value="#{SessionBean.IFilters}"
                                                   var="filter">
                                        <h:commandLink action="#{SessionBean.setEditFilter}">
                                            <f:param value="#{filter.filterName}"
                                                     name="activeFilter"/>
                                            <h:outputText value="#{filter.filterName}"/>
                                            <br/>
                                        </h:commandLink>
                                        
                                         <rich:toolTip value="#{filter.filterDescription}"/>
                                         
                                    </rich:dataList>
                                </rich:simpleTogglePanel>
                            </h:form>
                        </rich:panel>
                    </rich:column>
                    
                   
                    <!-- middle panel -->
                    <rich:column style="width:80%; height:100%;" id="mapCol">
                        
                        <rich:simpleTogglePanel id="mapPanel" width="100%"
                                                switchType="server"
                                                height="#{PanelBean.mapHeight}"
                                                label="Map"
                                                action="#{PanelBean.switchMap}"
                                                reRender="supportTablePanel,detailTablePanel" >
                            <div id="map_div"
                                 style="left:0px; top:0px; width:100%; height:100%; border:2px solid"></div>
                        </rich:simpleTogglePanel>

                        
                        <h:form style="margin-bottom:0;" id="supportTableForm">

                            <rich:dropSupport id="dropSupportTable"
                                              acceptedTypes="attribute,level,measure"
                                              dropListener="#{SessionBean.processDropSupportTable}"
                                              reRender="mapPanel,supportTablePanel,detailTablePanel,setOrder,remove"
                                              oncomplete="window.document.forms[0].submit();">
                            </rich:dropSupport>
                            <rich:simpleTogglePanel switchType="server"
                                                    label="Support Table"
                                                    width="100%"
                                                    height="#{PanelBean.supportTableHeight}"
                                                    id="supportTablePanel"
                                                    action="#{PanelBean.switchSupportTable}"
                                                    reRender="mapPanel,detailTablePanel,supportTablePanel">

                                <rich:contextMenu event="oncontextmenu"
                                                  attachTo="supportTablePanel"
                                                  submitMode="none">
                                    <rich:menuItem value="Set order..."
                                                   onclick="document.getElementById('setOrder').component.show()"
                                                   id="zin"></rich:menuItem>
                                    <rich:menuItem value="Remove..."
                                                   onclick="document.getElementById('remove').component.show()"
                                                   id="zout"></rich:menuItem>
                                </rich:contextMenu>
                                
                                <rich:dataTable id="supportTable" width="400"
                                                
                                                value="#{MainBean.data}"
                                                var="mainData"
                                                reRender="highlighted"
                                                styleClass="class1">

                                    <f:facet name="header">
                                        <rich:columnGroup id="headersST">
                                          
                                            <rich:column colspan="#{MainBean.numberOfInLineAttributes}"
                                                         rowspan="2">
                                                <rich:spacer/>
                                            </rich:column>

                                            <rich:columns value="#{MainBean.headerList2}"
                                                          var="columnsH2"
                                                          index="ind"
                                                          colspan="#{MainBean.colspanHeader2}"
                                                          rendered="#{MainBean.colspanHeader2 > 0}">
                                                <h:outputText value="#{columnsH2}"/>
                                            </rich:columns>
                                            <rich:column breakBefore="true"
                                                         rendered="false"></rich:column>

                                            <rich:columns value="#{MainBean.headerList}"
                                                          var="columns"
                                                          index="ind"
                                                          colspan="#{MainBean.colspanHeader1}"
                                                          rendered="#{MainBean.colspanHeader1 > 0}">
                                                <h:outputText value="#{columns}"/>
                                            </rich:columns>

                                            <rich:column breakBefore="true"
                                                         rendered="false"></rich:column>
                                            <rich:columns value="#{MainBean.measureList}"
                                                          var="columns2">
                                                <h:outputText value="#{columns2}"/>
                                            </rich:columns>
                                        </rich:columnGroup>
                                    </f:facet>
                                    

                                    <rich:columns value="#{MainBean.stubIndex}"
                                                  index="ind" rendered="false"/>

                                    <rich:columns value="#{MainBean.data[ind]}"
                                                  var="singleCellData"
                                                  index="indcell">
                                        <a4j:commandLink action="#{MainBean.drawDetail}" oncomplete="unHighlight(); highlight(); alignNumbers();" reRender="detailTablePanel, supportTable, highlighted">
                                            <f:param value="#{mainData[0]}"
                                                     name="rowdata0"/>
                                            <f:param value="#{mainData[1]}"
                                                     name="rowdata1"/>
                                            <h:outputText style="color:black"
                                                          value="#{mainData[indcell]}"/>
                                        </a4j:commandLink>
                                    </rich:columns>
                                </rich:dataTable>
                            </rich:simpleTogglePanel>
                        </h:form>

                        <h:form style="margin-bottom:0;">
                            <rich:dropSupport id="dropDetailTable"
                                              acceptedTypes="attribute,level,measure"
                                              dropListener="#{SessionBean.processDropDetailTable}"
                                              reRender="detailTablePanel"></rich:dropSupport>
                            <rich:simpleTogglePanel opened="true"
                                                    switchType="server"
                                                    label="Detail Table"
                                                    width="100%"
                                                    height="#{PanelBean.detailTableHeight}"
                                                    id="detailTablePanel"
                                                    action="#{PanelBean.switchDetailTable}"
                                                    reRender="supportTablePanel,mapPanel">
                                <rich:dataTable id="detailTable" width="400"
                                                rendered="#{!empty MainBean.detailData}"
                                                value="#{MainBean.detailData}"
                                                var="detailData">

                                    <f:facet name="header">
                                        <rich:columnGroup>
                                            <rich:columns value="#{MainBean.detailHeaders}"
                                                          var="detailHeader">
                                                <h:outputText value="#{detailHeader}"/>
                                            </rich:columns>
                                        </rich:columnGroup>
                                    </f:facet>

                                    <rich:columns value="#{MainBean.stubIndex}"
                                                  index="ind2"
                                                  rendered="false"/>

                                    <rich:columns value="#{MainBean.detailData[ind2]}"
                                                  var="singleCellData2"
                                                  index="indcell2">
                                        <h:outputText value="#{detailData[indcell2]}"/>
                                    </rich:columns>
                                </rich:dataTable>
                            </rich:simpleTogglePanel>
                        </h:form>
                    </rich:column>
                    
                    <!-- right panel -->
                    <rich:column 
                                 rendered="#{PanelBean.displayDataModelPanel}"
                                 id="controlPanelCol2">
                        <rich:panel header="SOLAP Model"
                                    style="height:710px;overflow-y:scroll;">
                            <!-- dimensions tree -->
                            <rich:simpleTogglePanel switchType="ajax"
                                                    label="Dimensions"
                                                    id="dimensionsPanel">
                                <h:form style="margin-bottom:0;margin-top:0;">
                                    <rich:tree switchType="ajax" id="mainTree">
                                        <rich:treeNodesAdaptor id="dimension"
                                                               nodes="#{SessionBean.dimensions}"
                                                               var="dimension">
                                            <rich:treeNode>
                                                <h:outputText value="#{dimension.name}"
                                                              style="font:11px arial;"/>
                                            </rich:treeNode>
                                            <rich:treeNodesAdaptor id="level"
                                                                   var="level"
                                                                   nodes="#{dimension.levels}">
                                                <rich:treeNode>
                                                    <rich:dragSupport dragIndicator=":indicator"
                                                                      dragType="level"
                                                                      dragValue="#{level}">
                                                        <rich:dndParam name="label"
                                                                       value="#{level.name}"/>
                                                    </rich:dragSupport>
                                                    <h:outputText value="#{level.name}"
                                                                  style="font:11px arial;"/>
                                                </rich:treeNode>
                                                <rich:treeNodesAdaptor id="attribute"
                                                                       var="attribute"
                                                                       nodes="#{level.attributes}">
                                                    <rich:treeNode>
                                                        <rich:dragSupport dragIndicator=":indicator"
                                                                          dragType="attribute"
                                                                          dragValue="#{attribute}">
                                                            <rich:dndParam name="label"
                                                                           value="#{attribute.name}"/>
                                                        </rich:dragSupport>
                                                        <h:outputText value="#{attribute.name}"
                                                                      style="font:11px arial;"/>
                                                    </rich:treeNode>
                                                </rich:treeNodesAdaptor>
                                            </rich:treeNodesAdaptor>
                                        </rich:treeNodesAdaptor>
                                    </rich:tree>
                                </h:form>
                            </rich:simpleTogglePanel>
                            <!-- measures tree -->
                            <rich:simpleTogglePanel switchType="ajax"
                                                    label="Measures"
                                                    id="measuresPanel">
                                <h:form style="margin-bottom:0;margin-top:0;">
                                    <rich:tree switchType="ajax"
                                               id="measuresTree">
                                        <rich:treeNodesAdaptor id="measure"
                                                               nodes="#{SessionBean.measures}"
                                                               var="measure">
                                            <rich:treeNode>
                                                <rich:dragSupport dragIndicator=":indicator"
                                                                  dragType="measure_noop"
                                                                  dragValue="#{measure}">
                                                    <rich:dndParam name="label"
                                                                   value="#{measure.name}"/>
                                                    <h:outputText value="#{measure.name}"
                                                                  style="font:11px arial;"/>
                                                </rich:dragSupport>
                                            </rich:treeNode>
                                            <rich:treeNodesAdaptor id="aggregationOperators"
                                                                   var="aggOp"
                                                                   nodes="#{measure.aggregationOperators}">
                                                <rich:treeNode>
                                                    <rich:dragSupport dragIndicator=":indicator"
                                                                      dragType="measure"
                                                                      dragValue="#{measure}">
                                                        <f:param name="operator"
                                                                 value="#{aggOp}"/>
                                                        <rich:dndParam name="label"
                                                                       value="#{measure.name} (#{aggOp})"/>
                                                    </rich:dragSupport>
                                                    <h:outputText value="#{aggOp}"
                                                                  style="font:11px arial;"/>
                                                </rich:treeNode>
                                            </rich:treeNodesAdaptor>
                                        </rich:treeNodesAdaptor>
                                    </rich:tree>
                                </h:form>
                            </rich:simpleTogglePanel>
                            <!-- layers tree -->
                            <rich:simpleTogglePanel switchType="ajax"
                                                    label="Layers"
                                                    id="layersPanel"
                                                    opened="true">
                                <h:form style="margin-bottom:0;margin-top:0;">
                                    <rich:tree switchType="ajax"
                                               id="layersTree">
                                        <rich:treeNodesAdaptor id="layer"
                                                               nodes="#{SessionBean.layers}"
                                                               var="layer">
                                            <rich:treeNode>
                                                <rich:dragSupport dragIndicator=":indicator"
                                                                  dragType="layer"
                                                                  dragValue="#{layer}">
                                                    <rich:dndParam name="label"
                                                                   value="#{layer.title}"/>
                                                </rich:dragSupport>
                                                <h:outputText value="#{layer.title}"
                                                              style="font:11px arial;"/>
                                            </rich:treeNode>
                                        </rich:treeNodesAdaptor>
                                    </rich:tree>
                                </h:form>
                            </rich:simpleTogglePanel>
                            <!-- spatial objects tree -->
                            <rich:simpleTogglePanel switchType="ajax"
                                                    label="Spatial Objects"
                                                    id="spatialObjectsPanel"
                                                    opened="true">
                                <h:form style="margin-bottom:0;margin-top:0;">
                                    <rich:tree switchType="ajax"
                                               id="spatialObjectsTree">
                                        <rich:treeNodesAdaptor id="spatialObject"
                                                               nodes="#{SessionBean.layersObject}"
                                                               var="layer">
                                            <rich:treeNode>
                                                <rich:dragSupport dragIndicator=":indicator"
                                                                  dragType="layer"
                                                                  dragValue="#{layer}">
                                                    <rich:dndParam name="label"
                                                                   value="#{layer.title}"/>
                                                </rich:dragSupport>
                                                <h:outputText value="#{layer.title}"
                                                              style="font:11px arial;"/>
                                            </rich:treeNode>
                                        </rich:treeNodesAdaptor>
                                    </rich:tree>
                                </h:form>
                            </rich:simpleTogglePanel>
                        </rich:panel>
                    </rich:column>
                </tr>
            </table>
             
            <!-- hidden data (JSF/JavaScript data exchange) -->
            <h:form id="hiddenData" style="margin-bottom:0;">
                <h:inputHidden id="sqlQuery" value="#{MainBean.sqlQuery}"/>
                <h:inputHidden id="geometryType"
                               value="#{MainBean.geometryType}"/>
                <h:inputHidden id="rendering"
                               value="#{MainBean.managerStyles.renderingElement}"/>
                <h:inputHidden id="hiddenXML"
                               value="#{MainBean.hiddenElement}"/>
                <h:inputHidden id="styleXML"
                               value="#{MainBean.managerStyles.xmlMapViewerStyle}"/>
                <h:inputHidden id="legendXML"
                               value="#{MainBean.managerStyles.xmlMapViewerLegend}"/>
                <h:inputHidden id="nRows" value="#{MainBean.numberOfRows}"/>
                <h:inputHidden id="nBars" value="#{MainBean.numberOfBars}"/>
                <h:inputHidden id="nAttribs"
                               value="#{MainBean.numberOfAttribs}"/>
                <h:inputHidden id="nAssocAttribs"
                               value="#{MainBean.numberAssocAttr}"/>
                <h:inputHidden id="highlighted"
                               value="#{MainBean.highlighted}"/>
                <h:inputHidden id="editSlice" value="#{SessionBean.editSlice}"/>
                <h:inputHidden id="editSpatialSlice"
                               value="#{SessionBean.editSpatialSlice}"/>
                <h:inputHidden id="editFilter"
                               value="#{SessionBean.editFilter}"/>
                <h:inputHidden id="assoc1" value="#{MainBean.assoc1}"/>
                <h:inputHidden id="assoc2" value="#{MainBean.assoc2}"/>
                <!-- Informations about map -->
                <h:inputHidden id="datasource"
                               value="#{SessionBean.mapInfo.datasource}"/>
                <h:inputHidden id="host" value="#{SessionBean.mapInfo.host}"/>
                <h:inputHidden id="namebasemap"
                               value="#{SessionBean.mapInfo.nameBaseMap}"/>
                <h:inputHidden id="port" value="#{SessionBean.mapInfo.port}"/>
                <h:inputHidden id="name" value="#{SessionBean.mapInfo.name}"/>
                <h:inputHidden id="srid" value="#{SessionBean.mapInfo.SRID}"/>

                <h:inputHidden id="clusteringFlag"
                               value="#{SessionBean.clustering}"/>
                <h:inputHidden id="columnRefST"
                               value="#{SessionBean.columnRefInST}"/>
                <h:inputHidden id="legendField"
                               value="#{MainBean.legendField}"/>
                
                <!-- to know if we are in the case of intersection of polygons -->
                <h:inputHidden id="newSpatialObjects"
                               value="#{SessionBean.newSpatialObjects}"/>
                               
                <h:inputHidden id="error" value="#{MainBean.errorHandler.error}"/>
                
                <!-- summarization info -->
                <h:inputHidden id="summarization" value="#{MainBean.summarization}"/>
                <h:inputHidden id="groupsColorsDescription" value="#{MainBean.groupsColors}"/>
                
            </h:form>
             
            <!-- To update values of zoom and center point of map -->
            <h:form id="hiddenButtons" style="margin-bottom:0;">
                <h:commandButton id="zoomChange"
                                 onclick="document.getElementById('hiddenButtons:zoomNivel').value = map.getZoom();"
                                 style="background-color:transparent; border-color:transparent; border-style:hidden; outline-style:hidden;">
                    <a4j:support ajaxSingle="true"
                                 reRender=""/>
                </h:commandButton>
                
                <h:inputHidden id="zoomNivel" value="#{MapBean.zoom}"/>
            </h:form>
             
            <!-- detail table highlighting -->
            <script type="text/javascript">
                function highlight() {
                    var row = document.getElementById("hiddenData:highlighted").value;
                    if (row != null) {
                        row = parseInt(row);
                        row += 3;
                        var nCols = parseInt(document.getElementById("hiddenData:nBars").value) + parseInt(document.getElementById("hiddenData:nAttribs").value) - 1;
                        for (var i = 0; i < nCols + 1; i++) {
                            var cell = document.getElementById("supportTableForm:supportTable").rows[row].cells[i].id;
                            document.getElementById(cell).style.backgroundColor="#87CEEB";
                        }
                    }
                }
                
                highlight();
                
                function unHighlight(row) {
                    if (row != null) {
                        row = parseInt(row);
                        row += 3;
                        var nCols = parseInt(document.getElementById("hiddenData:nBars").value) + parseInt(document.getElementById("hiddenData:nAttribs").value) - 1;
                        for (var i = 0; i < nCols + 1; i++) {
                            var cell = document.getElementById("supportTableForm:supportTable").rows[row].cells[i].id;
                            document.getElementById(cell).style.backgroundColor="#FFFFFF";
                        }
                    }
                }
                                            
            </script>
            <!-- modal panel for table order -->
            <rich:modalPanel id="setOrder" width="470" height="285">
                <h:form id="modalSetOrder">
                    <f:facet name="header">
                        <h:panelGroup>
                            <h:outputText value="Order Measures/Attributes"></h:outputText>
                        </h:panelGroup>
                    </f:facet>
                    <f:facet name="controls">
                        <h:panelGroup></h:panelGroup>
                    </f:facet>
                    <table>
                        <tr>
                            <td width="50%">
                                Measures: 
                                <br/><br/>
                                 
                                <rich:orderingList id="list1" listWidth="150"
                                                   value="#{SessionBean.IMeasuresST}"
                                                   converter="solapMeasureConverter"
                                                   var="item">
                                    <h:column>
                                        <h:outputText value="#{item.name} (#{item.operator})"/>
                                    </h:column>
                                </rich:orderingList>
                            </td>
                            <td width="50%">
                                Attributes: 
                                <br/><br/>
                                 
                                <rich:orderingList id="list2" listWidth="150"
                                                   value="#{SessionBean.IAttributesST}"
                                                   converter="solapConverter"
                                                   var="item">
                                    <h:column>
                                        <h:outputText value="#{item.name}"/>
                                    </h:column>
                                </rich:orderingList>
                            </td>
                        </tr>
                    </table>
                    <br/>
                    <br/>
                    <table width="100%">
                        <tr>
                            <td>
                                <div align="center">
                                    <h:commandButton value="Submit"
                                                     action="#{SessionBean.show}"></h:commandButton>
                                </div>
                            </td>
                        </tr>
                    </table>
                </h:form>
            </rich:modalPanel>
             
            <!-- modal panel for removal -->
            <rich:modalPanel id="remove" width="480" height="220">
                <h:form id="modalRemove">
                    <f:facet name="header">
                        <h:panelGroup>
                            <h:outputText value="Remove Measures/Attributes"></h:outputText>
                        </h:panelGroup>
                    </f:facet>
                    <f:facet name="controls">
                        <h:panelGroup></h:panelGroup>
                    </f:facet>
                    <table>
                        <tr>
                            <td width="33%" align="center">
                                Measures: 
                                <br/><br/>
                                 
                                <h:selectManyListbox id="aMeasures"
                                                     value="#{SessionBean.tempSelectedMValues}"
                                                     size="7"
                                                     style="width:150px">
                                    <f:selectItems value="#{SessionBean.tempAvailableM}"/>
                                </h:selectManyListbox>
                            </td>
                            <td width="34%" align="center">
                                Attributes: 
                                <br/><br/>
                                 
                                <h:selectManyListbox id="aAttributes"
                                                     value="#{SessionBean.tempSelectedAValues}"
                                                     size="7"
                                                     style="width:150px">
                                    <f:selectItems value="#{SessionBean.tempAvailableA}"/>
                                </h:selectManyListbox>
                            </td>
                            <td width="33%" align="center">
                                Levels: 
                                <br/><br/>
                                 
                                <h:selectManyListbox id="aLevels"
                                                     value="#{SessionBean.tempSelectedLValues}"
                                                     size="7"
                                                     style="width:150px">
                                    <f:selectItems value="#{SessionBean.tempAvailableL}"/>
                                </h:selectManyListbox>
                            </td>
                        </tr>
                    </table>
                    <br/>
                    <br/>
                    <table width="100%">
                        <tr>
                            <td>
                                <div align="center">
                                    <a4j:commandButton value="Cancel" onclick="document.getElementById('remove').component.hide()"/>
                                </div>
                            </td>
                            <td>
                                <div align="center">
                                    <a4j:commandButton value="Remove"
                                                     action="#{SessionBean.removeElements}"
                                                     reRender="mapPanel,supportTable,detailTablePanel, setOrder, remove, hiddenData"
                                                     oncomplete="document.getElementById('remove').component.hide(); loadMainMap(); alignNumbers();"/>
                                </div>
                            </td>
                        </tr>
                    </table>
                </h:form>
            </rich:modalPanel>
             
            <!-- modal for slice delete confirmation -->
             
            <rich:modalPanel id="sliceDelete" width="285" height="80">
                <h:form id="confirmSliceDeletion">
                    <f:facet name="header">
                        <h:panelGroup>
                            <h:outputText value="Delete slice?"></h:outputText>
                        </h:panelGroup>
                    </f:facet>
                    <f:facet name="controls">
                        <h:panelGroup></h:panelGroup>
                    </f:facet>
                    Are you sure you want to delete the selected slice?
                    <br/>
                    <br/>
                    <table width="100%">
                        <tr>
                            <td>
                                <div align="center">
                                    <a4j:commandButton value="No"/>
                                </div>
                            </td>
                            <td>
                                <div align="center">
                                    <h:commandButton value="Yes"
                                                     action="#{SessionBean.removeSlice}"
                                                     onclick="document.getElementById('modalSlice:addSlice').component.hide()"/>
                                </div>
                            </td>
                        </tr>
                    </table>
                </h:form>
            </rich:modalPanel>
             
            <!-- modal for spatial slice delete confirmation -->
             
            <rich:modalPanel id="spatialSliceDelete" width="285" height="80">
                <h:form id="confirmSpatialSliceDeletion">
                    <f:facet name="header">
                        <h:panelGroup>
                            <h:outputText value="Delete spatial slice?"></h:outputText>
                        </h:panelGroup>
                    </f:facet>
                    <f:facet name="controls">
                        <h:panelGroup></h:panelGroup>
                    </f:facet>
                    Are you sure you want to delete the selected spatial slice?
                    <br/>
                    <br/>
                    <table width="100%">
                        <tr>
                            <td>
                                <div align="center">
                                    <h:commandButton value="No"/>
                                </div>
                            </td>
                            <td>
                                <div align="center">
                                    <h:commandButton value="Yes"
                                                     action="#{SessionBean.removeSpatialSlice}"
                                                     onclick="document.getElementById('modalSlice:addSpatialSlice').component.hide()"/>
                                </div>
                            </td>
                        </tr>
                    </table>
                </h:form>
            </rich:modalPanel>
             
            <!-- modal for filter delete confirmation -->
             
            <rich:modalPanel id="filterDelete" width="285" height="80">
                <h:form id="confirmFilterDeletion">
                    Are you sure you want to delete the selected filter?
                    <br/>
                    <br/>
                    <table width="100%">
                        <tr>
                            <td>
                                <div align="center">
                                    <h:commandButton value="No"/>
                                </div>
                            </td>
                            <td>
                                <div align="center">
                                    <h:commandButton value="Yes"
                                                     action="#{SessionBean.removeFilter}"
                                                     onclick="document.getElementById('modalFilter:addFilter').component.hide()"/>
                                </div>
                            </td>
                        </tr>
                    </table>
                </h:form>
            </rich:modalPanel>
             
            <!-- modal panel for load_cube -->
             
            <h:form id="formLoad">
                <rich:modalPanel id="panel" width="350" height="100">
                    <f:facet name="header">
                        <h:panelGroup>
                            <h:outputText value="Load Cube"></h:outputText>
                        </h:panelGroup>
                    </f:facet>
                    <f:facet name="controls">
                        <h:panelGroup>
                            <h:commandLink id="cancelLoad" value="Close"/>
                            <rich:componentControl for="panel"
                                                   attachTo="closeLink"
                                                   operation="hide"
                                                   event="onclick"/>
                        </h:panelGroup>
                    </f:facet>
                    <h:commandLink value="Emissões"
                                   action="#{SessionBean.initializeCube}"/>
                </rich:modalPanel>
            </h:form>
             
            <!-- modal panel for load session -->
             
            <rich:modalPanel id="loadPanel" width="450" height="350">
                <f:facet name="header">
                    <h:panelGroup>
                        <h:outputText value="Load Model/Session"></h:outputText>
                    </h:panelGroup>
                </f:facet>
                <h:form id="modalLoad">
                    <table>
                        <tr>
                            <td width="40%" rowspan="2" valign="top">
                                <h:outputText styleClass="modalText"
                                              value="Models:"/>
                                 
                                <br/><br/>
                                 
                                <h:selectOneListbox id="pickModel" size="7"
                                                    value="#{SessionBean.selectedCube}">
                                    <f:selectItems value="#{SessionBean.cubesList}"/>
                                    <a4j:support event="onclick"
                                                 reRender="description_m,pickSession"
                                                 ajaxSingle="true"/>
                                </h:selectOneListbox>
                            </td>
                            <td width="60%">
                                <h:outputText styleClass="modalText"
                                              value="Description:"/>
                                 
                                <br/><br/><h:inputTextarea id="description_m"
                                                           value="#{SessionBean.tempDescription}"
                                                           rows="4" cols="40"/>
                            </td>
                        </tr>
                         
                        <tr>
                            <td width="60%" valign="middle">
                                <br/><h:outputText styleClass="modalText"
                                                   value="Select session:"/><br/><br/>
                                 
                                <h:selectOneListbox id="pickSession"
                                                    value="#{SessionBean.selectedSession}"
                                                    size="5">
                                    <f:selectItem itemLabel="<NEW>"/>
                                    <f:selectItems value="#{SessionBean.sessionList}"/>
                                </h:selectOneListbox>
                            </td>
                        </tr>
                    </table>
                    <br/>
                    <br/>
                    <table width="100%">
                        <tr>
                            <td>
                                <div align="center">
                                    <a4j:commandButton value="Cancel"
                                                     onclick="document.getElementById('loadPanel').component.hide()"/>
                                </div>
                            </td>
                            <td>
                                <div align="center">
                                    <h:commandButton value="OK"
                                                     action="#{SessionBean.initializeCube}">
                                        <a4j:support event="onclick"
                                                     action="#{ManagerBean.reset}"
                                                     oncomplete="cleanRecords();"/>
                                    </h:commandButton>
                                </div>
                            </td>
                        </tr>
                    </table>
                </h:form>
            </rich:modalPanel>
             
            <!-- modal panel for save session -->
             
            <rich:modalPanel id="savePanel" width="400" height="300">
                <f:facet name="header">
                    <h:panelGroup>
                        <h:outputText value="Save Session"></h:outputText>
                    </h:panelGroup>
                </f:facet>
                <h:form>
                    <table>
                        <tr>
                            <td width="40%" valign="top">
                                <h:outputText styleClass="modalText"
                                              value="Saved sessions for this model:"/>
                                 
                                <br/><br/>
                                 
                                <h:selectOneListbox value="#{SessionBean.tempSaveName}"
                                                    id="pickName" size="5">
                                    <f:selectItems value="#{SessionBean.sessionsForThisModel}"/>
                                    <a4j:support ajaxSingle="true"
                                                 event="onclick"
                                                 reRender="saveName"/>
                                </h:selectOneListbox>
                            </td>
                            <td width="20%">&nbsp;</td>
                            <td width="40%" valign="middle">
                                <h:outputText styleClass="modalText"
                                              value="Description:"/>
                                 
                                <br/><br/><h:inputTextarea value="Enter a description"
                                                           cols="30" rows="4"/><br/><br/><br/>
                                 
                                <h:outputText styleClass="modalText"
                                              value="Filename:"/>
                                 
                                <br/><br/><h:inputText id="saveName"
                                                       value="#{SessionBean.tempSaveName}"
                                                       size="35"/>
                            </td>
                        </tr>
                    </table>
                    <br/>
                    <br/>
                    <table width="100%">
                        <tr>
                            <td>
                                <div align="center">
                                    <a4j:commandButton value="Cancel"
                                                     onclick="document.getElementById('savePanel').component.hide()"/>
                                </div>
                            </td>
                            <td>
                                <div align="center">
                                    <h:commandButton value="Save"
                                                     action="#{ManagerBean.save}"></h:commandButton>
                                </div>
                            </td>
                        </tr>
                    </table>
                </h:form>
            </rich:modalPanel>
             
            <!--modal panel for manager styles -->
             
            <rich:modalPanel id="managerStylesPanel" width="600" height="220">
                <f:facet name="header">
                    <h:panelGroup>
                        <h:outputText value="Styles Manager"></h:outputText>
                    </h:panelGroup>
                </f:facet>
                <h:form id="modalStyles">
                    <table width="100%">
                        <tr>
                            <td width="40%" rowspan="2" valign="top">
                                <h:outputText styleClass="modalText"
                                              value="Select the desired type of style:"/>
                                 
                                <br/><br/>
                                 
                                <h:selectOneListbox value="#{MainBean.managerStyles.typeChosen}"
                                                    id="pickType" size="5">
                                    <f:selectItems value="#{MainBean.managerStyles.tempApplicableTypeOfStyles}"/>
                                    <a4j:support ajaxSingle="true"
                                                 event="onclick"
                                                 reRender="pickStyle"/>
                                </h:selectOneListbox>
                            </td>
                        </tr><tr>
                            <td width="40%" rowspan="2" valign="top">
                                <h:outputText styleClass="modalText"
                                              value="Select one instance of type style:"/>
                                 
                                <br/><br/>
                                 
                                <rich:comboBox id="pickStyle"
                                               value="#{MainBean.managerStyles.styleChosen}">
                                    <f:selectItems value="#{MainBean.managerStyles.showApplicableStyles}"/>
                                </rich:comboBox>
                            </td>
                        </tr>
                    </table>
                    <br/>
                    <br/>
                    <table width="100%">
                        <tr>
                            <td>
                                <div align="center">
                                    <a4j:commandButton value="Cancel"
                                                     onclick="document.getElementById('managerStylesPanel').component.hide()"/>
                                </div>
                            </td>
                            <td>
                                <div align="center">
                                    <h:commandButton value="Submit"
                                                     action="#{MainBean.managerStyles.changeStyle}"/>
                                </div>
                            </td>
                        </tr>
                    </table>
                </h:form>
                
            </rich:modalPanel>
            
            <rich:modalPanel id="errorPanel" width="300" height="90">
                <f:facet name="header">
                        <h:outputText value="Error"></h:outputText>
                </f:facet>
                
                <h:form id="errorForm">
                    <table width="100%">
                        <tr>
                            <td align="center">
                                <h:outputText id="errorMessage" styleClass="modalText"  value="#{MainBean.errorHandler.message}"></h:outputText>
                            </td>
                        </tr>
                    </table>
                    <br />
                    <table width="100%">
                        <tr>
                            <td>
                                <div align="center">
                                    <a4j:commandButton value="OK"  onclick="document.getElementById('errorPanel').component.hide()">
                                    </a4j:commandButton>
                                </div>
                            </td>
                        </tr>
                    </table>
                </h:form>
            </rich:modalPanel>
            
            <!-- Sumarization Painels-->
            
            <rich:modalPanel id="sumarizationOptionsModalPanel" width="450" height="400">
                <f:facet name="header">
                    <h:outputText value="Summarization Options"/>
                </f:facet>
                <h:form id="SumarizationModalForm">
                    <h:outputText value="Generalization Options" style="font:14px arial;" />
                    <br/>
                    <br/>
                    <table width="100%" id="generalization_options_table">
                        <tr>
                            <td>
                                <h:selectOneRadio value="#{SessionBean.infoSumarization.generalizationType}" layout="pageDirection" style="font:11px arial;">
                                    <f:selectItem itemLabel="Spatial dominant" itemValue="SPATIAL"/>
                                    <f:selectItem itemLabel="Non-spatial dominant" itemValue="NON_SPATIAL"/>
                                    <a4j:support event="onchange" reRender="generalization_options_groups_title, generalization_options_groups_param, generalization_options_groups_note, generalizationModalForm, SumarizationModalForm" oncomplete="document.getElementById('SumarizationModalForm').sumit(); "/>
                                </h:selectOneRadio>
                            </td>
                            <td align="right">
                                <h:outputText id="generalization_options_groups_title" rendered="#{SessionBean.infoSumarization.nonSpatial}" style="font:10px arial;" value="Groups Parameter"/>                       
                                <rich:inputNumberSlider id="generalization_options_groups_param" rendered="#{SessionBean.infoSumarization.nonSpatial}" value="#{SessionBean.infoSumarization.groupsParameter}" minValue="-2" maxValue="2" step="1" showInput="false" width="100" enableManualInput="false" showBoundaryValues="true" showToolTip="true"></rich:inputNumberSlider>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <h:outputText id="generalization_options_groups_note" rendered="#{SessionBean.infoSumarization.nonSpatial}" style="font:8px arial;" value="*Note: Groups parameter value is used only in case of clustering of points."/>
                            </td> 
                        </tr>
                    </table>
                    <br/>                      
                    <br/>
                    <h:outputText value="Hierarchies Options" style="font:14px arial;" />
                    <br/>
                    <br/>
                    <table width="100%">
                        <tr style="height:30;">
                            <td style="width:80%; font:11px arial;">
                                Refinement of hierarchies
                            </td>
                            <td>
                                <rich:comboBox id="refinementHierarchies" value="#{SessionBean.infoSumarization.refinementHierarchies}">
                                    <f:selectItem itemLabel="Yes" itemValue="Yes"/>
                                    <f:selectItem itemLabel="No" itemValue="No"/>
                                </rich:comboBox>
                            </td>
                        </tr>
                        <tr style="height:30;">
                            <td style="width:80%; font:11px arial;">
                                Use concepts from
                            </td>
                            <td>
                                <rich:comboBox id="useConcepts" value="#{SessionBean.infoSumarization.useConceptsFrom}">
                                    <f:selectItem itemLabel="All" itemValue="All"/>
                                    <f:selectItem itemLabel="Query" itemValue="Query"/>
                                </rich:comboBox>
                            </td>
                        </tr>
                        <tr style="height:30;">
                            <td style="width:80%; font:11px arial;">
                                Define labels for created concepts
                            </td>
                            <td>
                                <rich:comboBox id="defineLabels" value="#{SessionBean.infoSumarization.defineLabels}">
                                    <f:selectItem itemLabel="Yes" itemValue="Yes"/>
                                    <f:selectItem itemLabel="No" itemValue="No"/>
                                </rich:comboBox>
                            </td>
                        </tr>
                        <tr style="height:30;">
                            <td style="width:80%; font:11px arial;">
                                Use only distinct elements
                            </td>
                            <td>
                                <rich:comboBox id="distinct" value="#{SessionBean.infoSumarization.distinct}">
                                    <f:selectItem itemLabel="Yes" itemValue="Yes"/>
                                    <f:selectItem itemLabel="No" itemValue="No"/>
                                </rich:comboBox>
                            </td>
                        </tr>
                        <tr style="height:30;">
                            <td style="width:80%; font:11px arial;">
                                Characterization by values of the measures (if they exist)
                            </td>
                            <td>
                                <rich:comboBox id="characterizationMeasures" value="#{SessionBean.infoSumarization.charaterizationByMeasures}">
                                    <f:selectItem itemLabel="Yes" itemValue="Yes"/>
                                    <f:selectItem itemLabel="No" itemValue="No"/>
                                </rich:comboBox>
                            </td>
                        </tr>
                    </table>
                    <br/>
                    <br/>
                    <table width="100%">
                        <tr>
                            <td>
                                <div align="left">
                                    <a4j:commandButton value="Cancel" action="#{SessionBean.infoSumarization.oldConfigs}" onclick="document.getElementById('sumarizationOptionsModalPanel').component.hide()" reRender="sumarizationOptionsModalPanel"/>
                                </div>
                            </td>
                            <td>
                                <div align="right">
                                    <a4j:commandButton value="Save" action="#{SessionBean.infoSumarization.saveConfigs}" onclick="document.getElementById('sumarizationOptionsModalPanel').component.hide()" reRender="sumarizationOptionsModalPanel, generalizationModalPanel2"/>
                                </div>
                            </td>
                        </tr>
                    </table>
                </h:form>
            </rich:modalPanel>
            
            <rich:modalPanel id="generalizationModalPanel" width="450" height="350">
                <f:facet name="header">
                    <h:outputText value="Set Generalization Parameters"/>
                </f:facet>
                <h:form id="generalizationModalForm">
                    <table width="100%">
                        <a4j:repeat value="#{SessionBean.attributesSumarizationTable}" var="h">
                            <tr>
                                <td style="font:14px arial;">
                                    <h:outputText value="#{h.attName}"/>
                                    <h:outputText value=" (There is no hierarchy) " rendered="#{!h.haveHierarchy}" style="font:11px arial;"/>
                                    <h:selectOneRadio value="#{h.selectedHierarchy}" style="font:11px arial;" layout="pageDirection" rendered="#{h.haveHierarchy && ((!h.spatial && SessionBean.infoSumarization.nonSpatial) || !SessionBean.infoSumarization.nonSpatial)}">
                                        <f:selectItems value="#{h.items}"/>
                                    </h:selectOneRadio>
                                </td>
                                
                            </tr>
                            <tr/>
                        </a4j:repeat>
                        <a4j:repeat value="#{SessionBean.measuresSumarizationTable}" var="m">
                            <tr>
                                <td style="font:14px arial;">
                                    <h:outputText value="#{m.nameToShow}"/>
                                    <br/>
                                    <h:selectBooleanCheckbox disabled="#{m.noOperator}" value="#{m.checked}"/>
                                    <h:outputText value="Create hierarchy" style="font:11px arial;"/>
                                    <br/>
                                </td>
                            </tr>
                        </a4j:repeat>
                    </table>
                    <br/>
                    <br/>
                    <br/>
                    <br/>
                    <table width="100%">
                        <tr>
                            <td>
                                <div align="left">
                                    <a4j:commandButton value="Cancel" onclick="document.getElementById('generalizationModalPanel').component.hide()" reRender="generalizationModalPanel"/>
                                </div>
                            </td>
                            <td>
                                <div align="right">
                                    <a4j:commandButton value="Next" action="#{SessionBean.printChosenHierarchies}" onclick="document.getElementById('generalizationModalPanel').component.hide(); document.getElementById('generalizationModalPanel2').component.show();" reRender="generalizationModalPanel, hierarchySlider, generalizationModalForm2"/>
                                </div>
                            </td>
                        </tr>
                    </table>
                </h:form>
            </rich:modalPanel>
            
            <rich:modalPanel id="generalizationModalPanel2" width="450" height="350">
                <f:facet name="header">
                    <h:outputText value="Set Generalization Parameters"/>
                </f:facet>
                <h:form id="generalizationModalForm2">
                    <table width="100%">
                        <tr>
                            <td style="font:11px arial;">
                                <h:outputText value="Choose generalization levels:"/>
                            </td>
                        </tr>
                        <a4j:repeat value="#{SessionBean.attributesSumarizationTable}" var="h">
                            <tr>
                                <td style="font:14px arial;">
                                    <h:outputText value="#{h.attName}" rendered="#{h.haveHierarchy && ((!h.spatial && SessionBean.infoSumarization.nonSpatial) || !SessionBean.infoSumarization.nonSpatial)}"/>
                                </td>
                                <td align="right">
                                    <rich:inputNumberSlider rendered="#{h.haveHierarchy && ((!h.spatial && SessionBean.infoSumarization.nonSpatial) || !SessionBean.infoSumarization.nonSpatial)}" id="hierarchySlider" value="#{h.selectedAttributeHierarchy.selectedValue}" maxValue="#{h.selectedAttributeHierarchy.sliderOptions}" showInput="false" width="80" enableManualInput="false" showBoundaryValues="false" showToolTip="true">
                                        <a4j:support event="onchange" reRender="levelValID"/>
                                    </rich:inputNumberSlider>
                                    <h:outputText rendered="#{h.haveHierarchy && ((!h.spatial && SessionBean.infoSumarization.nonSpatial) || !SessionBean.infoSumarization.nonSpatial)}" id="levelValID" value="#{h.selectedAttributeHierarchy.selectedAttributeName}" style="font:11px arial;"/>
                                </td>
                            </tr>
                        </a4j:repeat>
                    </table>
                    <br/>
                    <table width="100%">
                        <tr>
                            <td style="font:11px arial;">
                                <h:outputText value="Choose generalization threshold:" rendered="#{!SessionBean.noMeasures}" />
                            </td>
                        </tr>
                        <a4j:repeat value="#{SessionBean.measuresSumarizationTable}" var="m">
                            <tr>
                                <td style="font:14px arial;">
                                    <h:outputText value="#{m.nameToShow}"/>
                                </td>
                                <td align="right">
                                    <h:inputText rendered="#{m.checked}" value="#{m.threshold}" label="Insert the threshold number"/>
                                </td>
                            </tr>
                        </a4j:repeat>
                    </table>
                    <br/>
                    <br/>
                    <br/>
                    <br/>
                    <table width="100%">
                        <tr>
                            <td>
                                <div align="left">
                                    <a4j:commandButton value="Cancel" onclick="document.getElementById('generalizationModalPanel2').component.hide()" reRender="generalizationModalPanel2"/>
                                </div>
                            </td>
                            <td>
                                <div align="right">
                                    <!--<a4j:commandButton value="Finish" action="#{SessionBean.startGeneralization}" onclick="document.getElementById('generalizationModalPanel2').component.hide()" oncomplete="loadMainMap();" reRender="generalizationModalPanel2, supportTablePanel, mapPanel"/>-->
                                    <h:commandButton value="Finish" action="#{SessionBean.startGeneralization}" onclick="document.getElementById('generalizationModalPanel2').component.hide()"> 
                                        <a4j:support action="#{SessionBean.startGeneralization}" />
                                    </h:commandButton>
                                </div>
                            </td>
                        </tr>
                    </table>
                </h:form>
            </rich:modalPanel>
            
            <rich:modalPanel id="factModalPanel" width="450" height="350">
                <f:facet name="header">
                    <h:outputText value="Choose fact tables"/>
                </f:facet>
                <h:form id="factTableForm">
                    <h:outputText value="Select the fact tables to use:"/>
                    <br/>
                    <br/>
                    <rich:pickList value="#{SessionBean.factTablesSelectedValues}" copyAllControlLabel="Add all" copyControlLabel="Add" >
                        <f:selectItems value="#{SessionBean.factTableItems}"/>
                    </rich:pickList>
                    <br/>
                    <br/>
                    <br/>
                    <br/>
                    <br/>
                    <br/>
                    <br/>
                    <table width="100%">
                        <tr>
                            <td>
                                <div align="left">
                                    <a4j:commandButton value="Cancel" onclick="document.getElementById('factModalPanel').component.hide()" reRender="factModalPanel"/>
                                </div>
                            </td>
                            <td>
                                <div align="right">
                                    <a4j:commandButton value="Ok" action="#{SessionBean.addFactTables}" onclick="document.getElementById('factModalPanel').component.hide()" reRender="factModalPanel"/>
                                </div>
                            </td>
                        </tr>
                    </table>
                </h:form>
            </rich:modalPanel>
            
        </body>
    </html>
</f:view>