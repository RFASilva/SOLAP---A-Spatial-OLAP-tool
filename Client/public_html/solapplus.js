function loadMainMap() {
    // Initialize
    initVariables();
    
    if(datSource != "") {
        initMap();
        
        themebasedfoi = new Array();
        MVMapView.enableCodingHints(false);
        
         // Display the map
        if (isActive()) {
            //Update the some variables. This variables are responsible for choose
            //the appropriate column to show the label of objects
            updateLegendVariables();
            
            showPolygonGroupPoints();
            //alert("sadfghjkjhgfd");
            //Generate the thematic map including the legend
            generateThematicMap();
            
            //Activate previous chosen layers
            activatePreviousLayers();
            
            //Assign some listeners do mapview
            activateListeners();
            
            //Activate the legend of thematic map if its is already chosen
            putLegendInMap();
        
        }
        mapview.display();
    }
}

//****** initialization functions ******
function addMapElements() {

    // Set up initial map center and zoom level
    center = MVSdoGeometry.createPoint(parseFloat(document.getElementById("hiddenButtons:centerx").value), parseFloat(document.getElementById("hiddenButtons:centery").value), 8307);
    mapZoom = document.getElementById("hiddenButtons:zoomNivel").value;
    
    // Add a zoom panel
    mapview.setHomeMap(center, mapZoom);
    // Add decorations
    mapview.addNavigationPanel('WEST');
    mapview.addScaleBar();
}

function updateX() {
    records.get('x', function(ok, val) {
        if (ok) {
            if(val == ""){
                records.set('x', document.getElementById("hiddenButtons:centerx").value);
                cx = document.getElementById("hiddenButtons:centerx").value;
            }
            else {
                cx = val;
            }
        }
    });
}

function updateY() {
    records.get('y', function(ok, val) {
        if (ok) {
            if(val == "") {
                records.set('y', document.getElementById("hiddenButtons:centery").value);
                cy = document.getElementById("hiddenButtons:centery").value;
            }
            else {
                cy = val;
            }
        }
    });
}

function updateZoom() {
    records.get('zoom', function(ok, val) {
    if (ok) {
        if(val == "") {
            records.set('zoom', document.getElementById("hiddenButtons:zoomNivel").value);
            zoomLevel = document.getElementById("hiddenButtons:zoomNivel").value;
        }
        else {
            zoomLevel = val;
        }
    }
    });
}

function initVariables() {
    updateX();
    updateY();
    
    datSource = document.getElementById("hiddenData:datasource").value;
    prt = document.getElementById("hiddenData:port").value;
    nam = document.getElementById("hiddenData:name").value;
    srid1 = document.getElementById("hiddenData:srid").value;
    
    updateZoom();
    namebasemap1 = document.getElementById("hiddenData:namebasemap").value;
}

function initMap() {
    //Set up connection
    baseURL = "http://localhost:" + prt + "/" + nam;
    mapview = new MVMapView(document.getElementById("map_div"), baseURL);
    
    // Add a base map
    mapview.addMapTileLayer(new MVMapTileLayer( (datSource + "." + namebasemap1) ));
    
    // Set up initial map center and zoom level
    center = MVSdoGeometry.createPoint(parseFloat(cx), parseFloat(cy), 8307);
    
    mapZoom = zoomLevel;
    mapview.setCenter(center);
    mapview.setZoomLevel(mapZoom);

    // Set double-click behavior
    mapview.setDoubleClickAction("zoomin");
    
    //add map elements (zoom, scale bar, overview map, ...)
    addMapElements();
}

//Theme related functions 
function generateTheme(sqlQuery, index, rendering, flagLabel) {
    var SqlSelect = sqlQuery;
    
    SqlSelect = cleanUpQuery(SqlSelect);
    var hiddenInfo = document.getElementById("hiddenData:hiddenXML").value;
    var legendField = "";

    if(index == 0) { 
        if(flagLabel == "intersection") {
            legendField = "name";
        }
        else if(flagLabel == "normal" || flagLabel == "nolegend") {
            legendField = document.getElementById("hiddenData:legendField").value;
        }
        else if (flagLabel == "groups") {
            legendField = "label";
        }
        else if(flagLabel == "withoutGroups") {
            legendField = "labelname";
        }
        else if(flagLabel == "all") {
            legendField = "LABELALL";
        }
    }
    else if(index == 1) {
        if(flagLabel == "normal" || flagLabel == "nolegend") {
            SqlSelect = "select temp_id as idin, object1 as glocal, labelNames1 from twospatial";
            legendField = "labelNames1";
        }
        else if (flagLabel == "groups") {
            SqlSelect = "select temp_id as idin, object1 as glocal, labelGroups1 from twospatial";
            legendField = "labelGroups1";
        }
        else if(flagLabel == "withoutGroups") {
            SqlSelect = "select temp_id as idin, object1 as glocal, labelNames1 from twospatial";
            legendField = "labelNames1";
        }
        else if(flagLabel == "all") {
            SqlSelect = "select temp_id as idin, object1 as glocal, labelall1 from twospatial";
            legendField = "labelAll1";
        }
    }
    else if(index == 2) {
        if(flagLabel == "normal" || flagLabel == "nolegend") {
            SqlSelect = "select temp_id as idin, object2 as glocal, labelNames2 from twospatial";
            legendField = "labelNames2";
        }
        else if (flagLabel == "groups") {
            SqlSelect = "select temp_id as idin, object2 as glocal, labelGroups2 from twospatial";
            legendField = "labelGroups2";
        }
        else if(flagLabel == "withoutGroups") {
            SqlSelect = "select temp_id as idin, object2 as glocal, labelNames2 from twospatial";
            legendField = "labelNames2";
        }
        else if(flagLabel == "all") {
            SqlSelect = "select temp_id as idin, object2 as glocal, labelall2 from twospatial";
            legendField = "labelAll2";
        }
    }
    
    if(rendering == "") {
        jdbcTheme =
            "<themes>"+
                " <theme name='selected_objects"+index+"' >" +
                    " <jdbc_query key_column='idin' spatial_column='glocal' asis='true' jdbc_srid='8307' " +
                        " render_style='STUB' datasource='solap' label_column='" + legendField + "' label_style='MDSYS:T.ROAD NAME' >" +
                        SqlSelect + " "+ hiddenInfo +
                    " <\/jdbc_query>" +
                " <\/theme>"+
            "<\/themes>";
        //alert(jdbcTheme);    
        return new MVThemeBasedFOI('SELECTED_OBJECTS'+index+'',jdbcTheme);
    }
    
    else if(rendering != "") {
        jdbcTheme =
            "<themes>"+
                " <theme name='selected_objects"+index+"' >" +
                    " <jdbc_query key_column='idin' spatial_column='glocal' asis='true' jdbc_srid='8307' " +
                        " datasource='solap' label_column='" + legendField + "' label_style='MDSYS:T.ROAD NAME'>" +
                        SqlSelect + " " + hiddenInfo +
                    " <\/jdbc_query>" +
                    rendering +
                " <\/theme>"+
            "<\/themes>";
            
        return new MVThemeBasedFOI('SELECTED_OBJECTS'+index+'',jdbcTheme);
    }
}

function cleanUpQuery(SqlSelect) {
    //replace < and >
    SqlSelect = SqlSelect.replace(/</g, "&lt;");
    SqlSelect = SqlSelect.replace(/>/g, "&gt;");
    SqlSelect = SqlSelect.replace("$ROWS$", document.getElementById("hiddenData:nRows").value);
    
    return SqlSelect;
}

function isActive() {
    if (document.getElementById("hiddenData:nRows").value != "0")
        return true;
    else
        return false;
}

//This function is called by solap.jsp when a layer is chosen (except label layers)
function setLayers(name, id) {
    setLayersAutomatically(name, id, "", "normalLayer");
}

function setLayersAutomatically(name, id, columnRef, flag) {
     if (themebasedfoi[id] == null) {
        
        if(flag == "normalLayer") {
            themebasedfoi[id] = new MVThemeBasedFOI('themebasedfoi'+id, name);
            mapview.removeThemeBasedFOI(themebasedfoi[id]);
            mapview.addThemeBasedFOI(themebasedfoi[id]);
            themebasedfoi[id].enableLabels(true);
            themebasedfoi[id].enableAutoWholeImage(true, 1, 1);
            
        }
        
        var contains = containsLayer(name, id);
        if(!contains) {
            storeLayer(name, id, columnRef);
        }
    }
    else {
        mapview.removeThemeBasedFOI(themebasedfoi[id]);
        removeLayer(name, id);
        themebasedfoi[id] = null;
    }
}

function changeFirstTheme() {
    initVariables();
    initMap();

    showPolygonGroupPoints();
    
    var xmlDefinition = document.getElementById("hiddenData:styleXML").value;
    var xmlStylesArray = xmlDefinition.split("newStyle");
    
    var queries = document.getElementById("hiddenData:sqlQuery").value;
    var queriesArray = queries.split("%%");
    var rendering = document.getElementById("hiddenData:rendering").value;

    var j = 0;
    flagLabel = determineFlag();
    selectedObjectsNew0 = generateTheme(queriesArray[0], 0, rendering, flagLabel);
    selectedObjectsNew0.deleteAllStyles();

    var xmlDefinitionArray = xmlStylesArray[j].split("%%");
    var size = xmlDefinitionArray.length;
    var i=0;
    for(i = 0; i < size-1; i+=2) {
        var style = new MVXMLStyle(xmlDefinitionArray[i+1], xmlDefinitionArray[i]);
        selectedObjectsNew0.addStyle(style);
    }
    
    if(rendering == "") selectedObjectsNew0.setRenderingStyle(xmlDefinitionArray[size-1]);
    
    if(flagLabel != "nolegend") selectedObjectsNew0.enableLabels(true);
    else selectedObjectsNew0.enableLabels(false);
    
    selectedObjectsNew0.enableAutoWholeImage(true, 130, 130);
    selectedObjectsNew0.attachEventListener(MVEvent.MOUSE_CLICK, highlightLine);
    
 
    mapview.addThemeBasedFOI(selectedObjectsNew0);
    selectedObjects0 = selectedObjectsNew0;
    
    addLayersAgain();
    putLegendInMap();
    
    //Assign some listeners do mapview
    activateListeners();
}

function changeSecondTheme() {
    initVariables();
    initMap();
    
    showPolygonGroupPoints();
    
    var xmlDefinition = document.getElementById("hiddenData:styleXML").value;
    var xmlStylesArray = xmlDefinition.split("newStyle");
    
    var queries = document.getElementById("hiddenData:sqlQuery").value;
    var queriesArray = queries.split("%%");
    var rendering = document.getElementById("hiddenData:rendering").value;
    
    var j = 1;
    
    flagLabel = determineFlag2("labelST1", "labelGroups1" );
    
    var newSelectedObjects1 = generateTheme(queriesArray[1], 1, rendering, flagLabel);
    mapview.removeThemeBasedFOI(newSelectedObjects1);
    newSelectedObjects1.deleteAllStyles();
    xmlDefinitionArray = xmlStylesArray[j].split("%%");
    size = xmlDefinitionArray.length;
    
    var i=0;
    for(i = 0; i < size; i+=2) {
        style = new MVXMLStyle(xmlDefinitionArray[i+1], xmlDefinitionArray[i]);
        newSelectedObjects1.addStyle(style);
    }
    
    if(flagLabel != "nolegend") newSelectedObjects1.enableLabels(true);
    else newSelectedObjects1.enableLabels(false);
            
    newSelectedObjects1.setRenderingStyle(xmlDefinitionArray[size-1]);
    
    mapview.addThemeBasedFOI(selectedObjects0);
    mapview.addThemeBasedFOI(newSelectedObjects1);
    mapview.addThemeBasedFOI(selectedObjects2);
    selectedObjects1 = newSelectedObjects1;
    
    addLayersAgain();
    putLegendInMap();
    
    //Assign some listeners do mapview
    activateListeners();
}

function changeThirdTheme() {
    initVariables();
    initMap();
    
    showPolygonGroupPoints();
    
    var xmlDefinition = document.getElementById("hiddenData:styleXML").value;
    var xmlStylesArray = xmlDefinition.split("newStyle");
    
    var queries = document.getElementById("hiddenData:sqlQuery").value;
    var queriesArray = queries.split("%%");
    var rendering = document.getElementById("hiddenData:rendering").value;
    
    var j = 2;
    flagLabel = determineFlag2("labelST2", "labelGroups2" );
    
    var newSelectedObjects2 = generateTheme(queriesArray[j], j, rendering, flagLabel);

    newSelectedObjects2.deleteAllStyles();
    xmlDefinitionArray = xmlStylesArray[j].split("%%");
    size = xmlDefinitionArray.length;
    
    var i=0;
    for(i = 0; i < size; i+=2) {
        style = new MVXMLStyle(xmlDefinitionArray[i+1], xmlDefinitionArray[i]);
        newSelectedObjects2.addStyle(style);
    }
    
    if(flagLabel != "nolegend") newSelectedObjects2.enableLabels(true);
    else newSelectedObjects2.enableLabels(false);

    newSelectedObjects2.setBringToTopOnMouseOver(true);
    newSelectedObjects2.setRenderingStyle(xmlDefinitionArray[size-1]);
    
    mapview.addThemeBasedFOI(selectedObjects0);
    mapview.addThemeBasedFOI(selectedObjects1);
    mapview.addThemeBasedFOI(newSelectedObjects2);
    selectedObjects2 = newSelectedObjects2;
    
    addLayersAgain();
    putLegendInMap();
    
    //Assign some listeners do mapview
    activateListeners();
}

function determineFlag2(variableST, idCheckBox) {
    var groups = document.getElementById("mapControlForm:" + idCheckBox);

    records.get(variableST, function(ok, val) {
        if (ok) {
            labelST = val;
        }
    });
    
    var labelGroups = false;
    if(groups != null) labelGroups = groups.checked;
    else {
        if(labelST == "true") return "normal";
    }
    
    flag = "nolegend";
        
    if(labelGroups) {
        flag = "groups";
        if(labelST == "true") flag = "all";
    }
    else if (labelST == "true") flag = "withoutGroups";
    
    return flag;
}

function determineFlag() {
    var newSpatialObjects = document.getElementById("mapControlForm:newSpatialObjects");
    var groups = document.getElementById("mapControlForm:labelGroups");

    //In case of intersection polygons
    if(newSpatialObjects != null) {
        if(newSpatialObjects.checked) return "intersection";
        else return "nolegend";
    }

    labelST = "false";
    records.get('labelST', function(ok, val) {
        if (ok) {
            labelST = val;
        }
    });
    
    var labelGroups = false;
    if(groups != null) labelGroups = groups.checked;
    else {
        if(labelST == "true") return "normal";
    }
    
    flag = "nolegend";
        
    if(labelGroups) {
        flag = "groups";
        if(labelST == "true") flag = "all";
    }
    else if (labelST == "true") flag = "withoutGroups";
    
    return flag;
}

//Generate the thematic map and the legend
function generateThematicMap() {
    var xmlDefinition = document.getElementById("hiddenData:styleXML").value;
    var xmlStylesArray = xmlDefinition.split("newStyle");
    //alert(xmlDefinition);
    var queries = document.getElementById("hiddenData:sqlQuery").value;
    //alert(queries);
    var queriesArray = queries.split("%%");
    var rendering = document.getElementById("hiddenData:rendering").value;
    
    //alert("queries: " + queriesArray.length-1);
    
    var j = 0;
    for(j = 0; j < queriesArray.length-1; j++) {
        //generate dynamic theme
        if(j==0) {
            flagLabel = determineFlag();
            
            selectedObjects0 = generateTheme(queriesArray[0], 0, rendering, flagLabel);
            selectedObjects0.deleteAllStyles();
            
            var xmlDefinitionArray = xmlStylesArray[j].split("%%");
            var size = xmlDefinitionArray.length;
            var i=0;
            for(i = 0; i < size-1; i+=2) {
                //alert(xmlDefinitionArray[i+1]);
                //alert(xmlDefinitionArray[i]);
                var style = new MVXMLStyle(xmlDefinitionArray[i+1], xmlDefinitionArray[i]);
                selectedObjects0.addStyle(style);
            }
            //alert(xmlDefinitionArray[size-1]);
            if(rendering == "") selectedObjects0.setRenderingStyle(xmlDefinitionArray[size-1]);
            
            selectedObjects0.enableAutoWholeImage(true, 130, 130);
            selectedObjects0.attachEventListener(MVEvent.MOUSE_CLICK, highlightLine);
            
            if(flagLabel != "nolegend") selectedObjects0.enableLabels(true);
            else selectedObjects0.enableLabels(false);
            
            mapview.removeThemeBasedFOI(selectedObjects0);
            mapview.addThemeBasedFOI(selectedObjects0);
        }
        
        //To generate the styles for extremes of arcs
        if(j==1) {
            flagLabel2 = determineFlag2("labelST1", "labelGroups1" );
            
            selectedObjects1 = generateTheme(queriesArray[1], 1, rendering, flagLabel2);
            mapview.removeThemeBasedFOI(selectedObjects1);
            selectedObjects1.deleteAllStyles();
            xmlDefinitionArray = xmlStylesArray[j].split("%%");
            size = xmlDefinitionArray.length;
            i=0;
            for(i = 0; i < size; i+=2) {
                style = new MVXMLStyle(xmlDefinitionArray[i+1], xmlDefinitionArray[i]);
                selectedObjects1.addStyle(style);
            }
                        
            if(flagLabel2 != "nolegend") selectedObjects1.enableLabels(true);
            else selectedObjects1.enableLabels(false);
            
            selectedObjects1.setBringToTopOnMouseOver(true);
            selectedObjects1.attachEventListener(MVEvent.MOUSE_CLICK, highlightLine);
        
            selectedObjects1.setRenderingStyle(xmlDefinitionArray[size-1]);
            mapview.addThemeBasedFOI(selectedObjects1);
        }
        
        if(j==2) {
            flagLabel2 = determineFlag2("labelST2", "labelGroups2" );
            
            selectedObjects2 = generateTheme(queriesArray[2], 2, rendering, flagLabel2);
            mapview.removeThemeBasedFOI(selectedObjects2);
            selectedObjects2.deleteAllStyles();
            xmlDefinitionArray = xmlStylesArray[j].split("%%");
            size = xmlDefinitionArray.length;
            i=0;
            
            for(i = 0; i < size; i+=2) {
                style = new MVXMLStyle(xmlDefinitionArray[i+1], xmlDefinitionArray[i]);
                selectedObjects2.addStyle(style);
            }
            
            if(flagLabel2 != "nolegend") selectedObjects2.enableLabels(true);
            else selectedObjects2.enableLabels(false);
            
            selectedObjects2.setBringToTopOnMouseOver(true);
            selectedObjects2.attachEventListener(MVEvent.MOUSE_CLICK, highlightLine);
        
            selectedObjects2.setRenderingStyle(xmlDefinitionArray[size-1]);
            mapview.removeThemeBasedFOI(selectedObjects2);
            mapview.addThemeBasedFOI(selectedObjects2);
        }
        
    }
        
    //generate legend
    xmlReq = encodeURIComponent(document.getElementById("hiddenData:legendXML").value);
    html = "<table><tr><td><img src="+baseURL+"/omserver?xml_request="+xmlReq + "><\/td><\/tr><\/table>";
    legend = new MVMapDecoration(html,null,0.04,280,0);
}

function setLayersLabel(name, id, columnRef) {
    var columnRefs = document.getElementById("hiddenData:columnRefST").value.split(",");
    
    if(columnRefs.length == 2) setLayersLabelTwoSpatial(name, id, columnRef, columnRefs);
    else setLayersLabelOneSpatial(name, id, columnRef);
}

function setLayersLabelTwoSpatial(name, id, columnRef, columnRefs) {

    if(columnRefs[0] == columnRef) {
        updateLayersLabelST('labelST1', id, name, columnRef, 1);
    }
    else if(columnRefs[1] == columnRef) {
        updateLayersLabelST('labelST2', id, name, columnRef, 2);
    }
    else {
        setLayersAutomatically(name, id, columnRef, "normalLayer");
    }
    
    mapview.display();
}

function setLayersLabelOneSpatial(name, id, columnRef) {
    if(document.getElementById("hiddenData:columnRefST").value == columnRef) {
        updateLayersLabelST('labelST', id, name, columnRef, 0);
    }
    else setLayersAutomatically(name, id, columnRef, "normalLayer");
    
    mapview.display();
}

function updateLayersLabelST(variable, id, name, columnRef, numberTheme) {
    records.get(variable, function(ok, val) {
        if (ok) {
            
            if(val == "false") {
                records.set(variable, "true");

                if (numberTheme == 0) changeFirstTheme();
                else if (numberTheme == 1) changeSecondTheme();
                else if (numberTheme == 2) changeThirdTheme();
                
                var contains = containsLayer(name, id);
                if(!contains) storeLayer(name, id, columnRef);
            }
            else {
                records.set(variable, "false");
                
                if (numberTheme == 0) changeFirstTheme();
                else if (numberTheme == 1) changeSecondTheme();
                else if (numberTheme == 2) changeThirdTheme();
                
                removeLayer(name, id);
            }
        }
    });     
}


function labelGroups() {
    changeFirstTheme();
    mapview.display();
}

function labelGroups1() {
    changeSecondTheme();
    mapview.display();
}

function labelGroups2() {
    changeThirdTheme();
    mapview.display();
}

function labelIntersection() {
    changeFirstTheme();
    mapview.display();
}

function storeLayer(name, id, columnRef) {
    records.get('layers', function(ok, val) {
        if (ok) {
            newVal = val + "%%" + name + "," + id;
            if(columnRef != "") {
                newVal += "," + columnRef;
            }
            else newVal += ",null";
            
            records.set('layers', newVal);
        }
    });
}

function removeLayer(name, id) {
    records.get('layers', function(ok, val) {
        if (ok) {
            var layers = val.split("%%");
            var newVal = "";
            var i = 0;
            for(i = 1; i < layers.length; i++) {
                definition = layers[i].split(",");
                var thisname = definition[0];
                var thisid = definition[1];
                var thiscolumnRef = definition[2];
                
                if((thisname == name) && (thisid == id)) {
                    newVal += "";
                }
                else {
                    newVal += "%%" + thisname + "," + thisid + "," + thiscolumnRef;
                }
            }
            records.set('layers', newVal);
        }
    });
}

function containsLayer(name, id) {
    var result = false;
    records.get('layers', function(ok, val) {
        if (ok) {
            var layers = val.split("%%");
            var i = 0;
            for(i = 1; i < layers.length; i++) {
                definition = layers[i].split(",");
                var thisname = definition[0];
                var thisid = definition[1];
                
                if((thisname == name) && (thisid == id)) {
                    result = true;
                }
            }
        }
    });
    
    return result;
}

function updateLegendVariables() {

records.set('labelST', "false");
records.set('labelST1', "false");
records.set('labelST2', "false");

records.get('layers', function(ok, val) {
        if (ok) {
            layers = val.split("%%");
            var i = 0;
            var isLabelST = false;
            var isLabelST1 = false;
            var isLabelST2 = false;
            
            for(i = 1; i < layers.length; i++) {
                definition = layers[i].split(",");
                var name = definition[0];
                var id = definition[1];
                var columnRef = definition[2];
                
                var columnRefs = document.getElementById("hiddenData:columnRefST").value.split(",");
                if(columnRefs.length == 2) {
                    if(columnRefs[0] == columnRef) {
                        isLabelST1 = true;
                    }
                    if(columnRefs[1] == columnRef) {
                        isLabelST2 = true;
                    }
                }
                else {
                    if(document.getElementById("hiddenData:columnRefST").value == columnRef)
                        isLabelST = true;
                }
            }
            
            if(isLabelST) records.set('labelST', "true");
            if(isLabelST1) records.set('labelST1', "true");
            if(isLabelST2) records.set('labelST2', "trsue");
            
        }
    });
}

function activatePreviousLayers() {
    records.get('layers', function(ok, val) {
        if (ok) {
            layers = val.split("%%");
            var i = 0;
            for(i = 1; i < layers.length; i++) {
                definition = layers[i].split(",");
                var name = definition[0];
                var id = definition[1];
                var columnRef = definition[2];
                
                var columnRefs = document.getElementById("hiddenData:columnRefST").value.split(",");

                if(columnRefs.length == 2) {
                    if(columnRefs[0] != columnRef && columnRefs[1] != columnRef) {
                        setLayersAutomatically(name, id, columnRef, "normalLayer");
                    }
                    
                }
                else {
                    if(document.getElementById("hiddenData:columnRefST").value != columnRef) {
                        setLayersAutomatically(name, id, columnRef, "normalLayer");
                    }
                }
            }
        }
    });
}

//This function is necessary because is important the previous layers not stay hide for the spatial objects's support table
function addLayersAgain() {
    records.get('layers', function(ok, val) {
        if (ok) {
            layers = val.split("%%");
            var i = 0;
            for(i = 1; i < layers.length; i++) {
                definition = layers[i].split(",");
                var name = definition[0];
                var id = definition[1];
                var columnRef = definition[2];

                var columnRefs = document.getElementById("hiddenData:columnRefST").value.split(",");
                if(columnRefs.length == 2) {
                    if(columnRefs[0] != columnRef && columnRefs[1] != columnRef) {
                        mapview.removeThemeBasedFOI(themebasedfoi[id]);

                        themebasedfoi[id] = new MVThemeBasedFOI('themebasedfoi'+id, name);
                        themebasedfoi[id].setVisible(true);
                        themebasedfoi[id].setBringToTopOnMouseOver(true);
                        mapview.addThemeBasedFOI(themebasedfoi[id]);
                    }
                }
                else {
                    if(document.getElementById("hiddenData:columnRefST").value != columnRef) {
                        mapview.removeThemeBasedFOI(themebasedfoi[id]);
                        
                        themebasedfoi[id] = new MVThemeBasedFOI('themebasedfoi'+id, name);
                        themebasedfoi[id].setVisible(true);
                        themebasedfoi[id].setBringToTopOnMouseOver(true);
                        mapview.addThemeBasedFOI(themebasedfoi[id]);
                    }
                }
            }
        }
    });
}


function switchLegend(checkbox) {
    if(checkbox.checked) {
        mapview.removeMapDecoration(legend);
        mapview.addMapDecoration(legend);
        records.set('legend', "true");
    }
    else {
        mapview.removeMapDecoration(legend);
        records.set('legend', "false");
    }
}

function putLegendInMap() {
        records.get('legend', function(ok, val) {
        if (ok) {
           if(val == "true") {
                if(document.getElementById("mapControlForm:legendCheckbox") != null) {
                    document.getElementById("mapControlForm:legendCheckbox").checked = true;
                }
                
                //generate legend
                xmlReq = encodeURIComponent(document.getElementById("hiddenData:legendXML").value);
                html = "<table><tr><td><img src="+baseURL+"/omserver?xml_request="+xmlReq + "><\/td><\/tr><\/table>";
                legend = new MVMapDecoration(html,null,0.04,280,0);
                
                mapview.addMapDecoration(legend);
                records.set('legend', "true");       
           }
           else {
                mapview.removeMapDecoration(legend);
                if(document.getElementById("mapControlForm:legendCheckbox") != null) {
                    document.getElementById("mapControlForm:legendCheckbox").checked = false;
                }
           }
        }
    });
}

// Other visual tweaks
function alignNumbers() {
    var nAttribs = parseInt(document.getElementById("hiddenData:nAttribs").value);
    var nBars = parseInt(document.getElementById("hiddenData:nBars").value);
    var nRows = parseInt(document.getElementById("hiddenData:nRows").value);
    
    for (var i = 0; i < nRows; i++) {
        for (var j = 0; j < nBars; j++) {
            var cell = document.getElementById("supportTableForm:supportTable").rows[3+i].cells[nAttribs-1+j].id;
            document.getElementById(cell).style.textAlign = "right";
        }
    }
    
    if (nBars != 0) {
        var cell2 = document.getElementById("supportTableForm:supportTable").rows[3].cells[1].id;
        if (cell2 != null)
            document.getElementById(cell2).style.color = "000000";
    }
    
}

function setValue (id, value) {
    document.getElementById(id).value = value;
}

//Implementation of feature (click a element in map and
//trigger the highlighting of line in support table
function highlightLine(geometry, foi, mouseEvent) {
    //alert("FOI: " + foi.attrs);
    var row = document.getElementById("hiddenData:highlighted").value;
    if (row != null && row != "")
        unHighlight(row);
    //alert("FOI: " + foi.attrs);
    var nRows = parseInt(document.getElementById("hiddenData:nRows").value);
    //alert("FOI: " + foi.attrs);
    var nAssocAttribs = parseInt(document.getElementById("hiddenData:nAssocAttribs").value);
    
    
    if(nAssocAttribs == 1) {
        for (var i = 3; i < nRows+3; i++) {
            cell = document.getElementById("supportTableForm:supportTable").rows[i].cells[nAssocAttribs].id;
            
            if (document.getElementById(cell).innerHTML.indexOf(foi.attrs) != -1) {
                document.getElementById("hiddenData:highlighted").value = i-3;
            }
        }
    }
    highlight(); 
}

//Functions responsible for position of the map
function updateRecenter() {
    center = mapview.getCenter();     
    records.set('x', center.getPointX());
    records.set('y', center.getPointY());
}

function bChangeZoom(beforeLevel, afterLevel) {
    var formElement = document.getElementById("hiddenButtons:zoomNivel");  
    formElement.value = afterLevel;
    afterzoom = afterLevel;
    records.set('zoom', afterLevel);
    
    document.getElementById("hiddenButtons:zoomChange").click();
}

function changeLevelZoom(beforeLevel, afterLevel) {
    records.set('zoom', afterLevel);
}

function activateListeners() {
    //Listeners to maintain the state of map
    mapview.attachEventListener(MVEvent.RECENTER, updateRecenter);
    clust = document.getElementById("hiddenData:clusteringFlag").value;

    if(clust == "true") {
        mapview.attachEventListener(MVEvent.BEFORE_ZOOM_LEVEL_CHANGE, bChangeZoom);
    }
    else {
        mapview.attachEventListener(MVEvent.ZOOM_LEVEL_CHANGE, changeLevelZoom);
    }
}

function showPolygonGroupPoints() {
    var nBars = document.getElementById("hiddenData:nBars").value;
    var clust = document.getElementById("hiddenData:clusteringFlag").value;
    var geometryType = document.getElementById("hiddenData:geometryType").value;
    var geometryTypeArray = geometryType.split("%%");
    
    var summarization = document.getElementById("hiddenData:summarization").value;
    if(summarization == "true" && !(geometryTypeArray[0]=="polygon")){
        var groupsColors = document.getElementById("hiddenData:groupsColorsDescription").value;
        addCharacterizedPolygonRepresentation("1", groupsColors);
        //addPolygonRepresentation("1", "black");
    }

    if(clust == "true" && nBars > 0 && !(geometryTypeArray[0]=="polygon")) {
        addPolygonRepresentation("1", "black");
        var numberAssoc = document.getElementById("hiddenData:nAssocAttribs").value;
        if(numberAssoc == 2) addPolygonRepresentation("2", "red");           
    }
}

function addPolygonRepresentation(index, color) {
    var groupTheme = "<themes>"+
                    " <theme name='polygonGroupPoints" + index + "' >" +
                        " <jdbc_query key_column='idin' asis='true' spatial_column='polygon"+index+"' jdbc_srid='8307' render_style='STUB' datasource='solap'>" +
                            "SELECT temp_id"+index+" as idin, polygon"+index+" FROM polygon_point_group" +
                        " <\/jdbc_query>" +
                    " <\/theme>"+
                "<\/themes>";
                
    var groupLayer = new MVThemeBasedFOI('polygonGroupPoints'+index,groupTheme);
    
    var xmldef = "<g class=\"color\" style=\"stroke:#000000;fill:"+color+";fill-opacity:175\"/>";                 
    var style = new MVXMLStyle("my_style"+index, xmldef);

    groupLayer.addStyle(style);
    groupLayer.setRenderingStyle("my_style"+index);
    mapview.addThemeBasedFOI(groupLayer);
}

function addCharacterizedPolygonRepresentation(index, groupsColors) {
    var separator = "%%";
    var groupTheme = "<themes>"+
                    "<theme name='polygonGroupPoints" + index + "' >" +
                        " <jdbc_query key_column='idin' asis='true' spatial_column='polygon"+index+"' jdbc_srid='8307' render_style='STUB' datasource='solap'>" +
                            "SELECT temp_id"+index+" as idin, polygon"+index+", characteristic"+index+ " FROM polygon_point_group" +
                        " <\/jdbc_query>\n";
    
    var xmldef="";
    //alert(groupsColors);
    var groupsColorsArray = groupsColors.split("%%");
    var size = groupsColorsArray.length;
    var i=0;
    for(i = 0; i < size; i+=2) {
        xmldef += "<g class=\"color\" style=\"stroke:#000000;fill:"+groupsColorsArray[i+1]+";fill-opacity:175\"/>";
        xmldef += separator;
        xmldef += Math.random()+"";
        xmldef += separator;
    }
    
    var markers = xmldef.split(separator);
    xmldef += "<AdvancedStyle>\n";
    xmldef += "<BucketStyle>\n";
    xmldef += "<Buckets>\n";
    var count = 0;
    for(i = 0; i < size; i+=2) {
        xmldef += "<CollectionBucket seq=\"" + count + "\" label=\""+ groupsColorsArray[i]+"\"" + 
        " style=\""+markers[i+1]+"\">"+groupsColorsArray[i]+"<\/CollectionBucket>\n";
        
        count++;
    }
    xmldef+="<\/Buckets>\n";
    xmldef+="<\/BucketStyle>\n";
    xmldef+="<\/AdvancedStyle>\n";   
    xmldef += separator;
    xmldef += Math.random()+"";
    
    //alert(xmldef);
    var xmldefArray = xmldef.split("%%");
    var xmldefArraySize = xmldefArray.length;
    var renderingStyle = "<rendering><style name=\"" + xmldefArray[xmldefArraySize-1] + "\" value_columns=\"characteristic1\"/>\n<\/rendering>\n";
    
    groupTheme += renderingStyle +
                " <\/theme>"+
            "<\/themes>";
    
    //alert(groupTheme);
    var groupLayer = new MVThemeBasedFOI('polygonGroupPoints'+index,groupTheme);
    
    for(i = 0; i < xmldefArraySize; i+=2) {
        //alert(xmldefArray[i+1] + "\n" + xmldefArray[i]);
        var style = new MVXMLStyle(xmldefArray[i+1], xmldefArray[i]);
        groupLayer.addStyle(style);
    }
    mapview.removeThemeBasedFOI(groupLayer);
    mapview.addThemeBasedFOI(groupLayer);
}

