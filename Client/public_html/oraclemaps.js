//Version: Ver11_B091229
MVMessages=new Array();
MVMessages["MAPVIEWER-05500"]="Oracle Maps client internal error.";
MVMessages["MAPVIEWER-05501"]="Map tile layer not found. Check map tile layer name and/or data source name.";
MVMessages["MAPVIEWER-05502"]="Cannot get map tile layer config information.";
MVMessages["MAPVIEWER-05503"]="FOI ID cannot be null.";
MVMessages["MAPVIEWER-05504"]="MapViewer server base URL is not defined.";
MVMessages["MAPVIEWER-05505"]="Wrong map center point specified. It should be an MVSdoGeometry point.";
MVMessages["MAPVIEWER-05506"]="Invalid zoom level.";
MVMessages["MAPVIEWER-05507"]="Map tile layer name cannot be null. ";
MVMessages["MAPVIEWER-05508"]="FOI Id is already used, please pick a new Id.";
MVMessages["MAPVIEWER-05509"]="Theme-based FOI layer name is used, please pick a new name.";
MVMessages["MAPVIEWER-05510"]="Cannot find theme-based FOI layer.";
MVMessages["MAPVIEWER-05511"]="Error occurred when sending request to MapViewer server.";
MVMessages["MAPVIEWER-05512"]="Not enough points have been drawn.";
MVMessages["MAPVIEWER-05513"]="Not a valid line or polygon type MVSdoGeometry.";
MVMessages["MAPVIEWER-05514"]="Must have a filtering geometry when in high-light only mode.";
MVMessages["MAPVIEWER-05515"]="Oracle Maps client notification.";
MVMessages["MAPVIEWER-05516"]="Synchronized remote call is not supported when using Oracle Maps' non-AJAX remoting.";
MVMessages["MAPVIEWER-05517"]="Request string is too long for Oracle Maps' non-AJAX remoting.";
MVMessages["MAPVIEWER-05518"]="Map tile layer hasn't been successfully added.";
MVMessages["MAPVIEWER-05519"]="One or more parameter is missing or invalid.";
MVMessages["MAPVIEWER-05520"]="Geometry SRID is null.";
MVMessages["MAPVIEWER-05521"]="Call back function cannot be null when browser's builtin XMLHttp/XMLHttpRequest is disabled.";
MVMessages["MAPVIEWER-05522"]="Client side coordinate system conversion not supported.";
MVMessages["MAPVIEWER-05523"]="Cannot process response from MapViewer server.";
MVMessages["MAPVIEWER-05524"]="Theme feature highlighting is disabled for this theme.";
MVMessages["MAPVIEWER-05525"]="Theme has not been added to a MVMapView instance.";
MVMessages["MAPVIEWER-05526"]="Map has not been initialized. Please try again later.";
MVMessages["MAPVIEWER-05527"]="Wrong parameter data type. ";
MVMessages["marqueeZoomHint"]="Click on the rectangle to zoom in!";
MVMessages["panNWInfoTip"]="Pan NorthWest";
MVMessages["panNInfoTip"]="Pan North";
MVMessages["panNEInfoTip"]="Pan NorthEast";
MVMessages["panWInfoTip"]="Pan West";
MVMessages["panEInfoTip"]="Pan East";
MVMessages["panSWInfoTip"]="Pan SouthWest";
MVMessages["panSInfoTip"]="Pan South";
MVMessages["panSEInfoTip"]="Pan SouthEast";
MVMessages["panCenterInfoTip"]="Home";
MVMessages["sliderBarInfoTip"]="Click to change zoom level";
MVMessages["sliderInfoTip"]="Drag to change zoom level";
MVMessages["zoomInInfoTip"]="Zoom in";
MVMessages["zoomOutInfoTip"]="Zoom out";
MVMessages["scaleBarInfoTip"]="Scale at the center of the map";
MVMessages["kilometers"]="km";
MVMessages["miles"]="mi";
MVMessages["meters"]="m";
MVMessages["feet"]="ft";
MVMessages["delete"]="Delete";
MVMessages["addPoint"]="Add Point";
MVMessages["finish"]="Finish";
MVMessages["clear"]="Clear";
MVMessages["totalDistance"]="Total Distance:"
MVMessages["km"]="KM"
MVMessages["m"]="M"
MVMessages["cm"]="CM"
MVMessages["mi"]="mi."
MVMessages["yd"]="yd."
MVMessages["ft"]="ft."
MVMessages["clearButton"]="Clear Button"
MVMessages["redlineTool"]="Redline Tool"
MVMessages["distanceTool"]="Distance Tool"
MVMessages["circleTool"]="Circle Tool"
MVMessages["retangleTool"]="Retangle Tool"
MVMessages["marqueeZoomTool"]="Marquee Zoom Tool"
function MVXMLHttpRequest()
{
this.onreadystatechange=null;
this._f448=0;
this.url=null;
this.status=0;
this.readyState=0;
this._f449=null;
this.responseText=null;
}
MVXMLHttpRequest._f450=new Array();
MVXMLHttpRequest._f451=0;
MVXMLHttpRequest._f452=Math.round(Math.random()*10000);
MVXMLHttpRequest.callBack=function(id,x0,x1)
{
while(MVXMLHttpRequest._f450.length>0)
{
var x2=MVXMLHttpRequest._f450[0];
if(!x2.onreadystatechange)
{
if(x2._f449)
{
document.body.removeChild(x2._f449);
x2._f449=null;
}
MVXMLHttpRequest._f450.shift();
}
else
 break;
}
var x3=0;
for(;x3<MVXMLHttpRequest._f450.length;x3++)
{
if(MVXMLHttpRequest._f450[x3]._f448==id)
{
var x2=MVXMLHttpRequest._f450[x3];
document.body.removeChild(x2._f449);
x2._f449=null;
if(x3==0)
MVXMLHttpRequest._f450.shift();
x2.status=200;
x2.readyState=4;
x2.responseText=x0;
if(x2.onreadystatechange&&!x1)
{
x2.onreadystatechange();
}
x2.onreadystatechange=null;
return ;
}
}
}
MVXMLHttpRequest.prototype.abort=function()
{
MVXMLHttpRequest.callBack(this._f448,null,true);
}
MVXMLHttpRequest.prototype.open=function(x4,x5,x6)
{
if(!x6)
throw "[MVXMLHttpRequest.open] "+MVi18n._f453("MAPVIEWER-05516");
else
 this.url=x5;
}
MVXMLHttpRequest.prototype.send=function(x7)
{
if(!this.url)
return ;
this._f448=MVXMLHttpRequest._f452+"_"+MVXMLHttpRequest._f451++;
var x8=this.url;
if(x8.indexOf("?")>0)
x8+="&";
else
 x8+="?";
x8+="callback_id="+this._f448;
x8+="&"+x7;
if(x8.length>_f11._f276)
{
throw "[MVXMLHttpRequest.send] "+MVi18n._f453("MAPVIEWER-05517");
return ;
}
var x9=document.createElement("script");
x9.src=x8;
x9.type='text/javascript';
x9.charset='utf-8';
this._f449=x9;
MVXMLHttpRequest._f450.push(this);
document.body.appendChild(x9);
if(MVMapView.debug)
MVi18n.alert("MVXMLHttpRequest. URL:"+x8);
}
MVXMLHttpRequest.prototype.setRequestHeader=function(x10)
{
}
function MVUtil(){}
MVUtil._f653=0;
MVUtil._f292=function(x0,x1,x2,x3,x4,x5,x6,x7,x8)
{
var x9=(x7-x1)*x5/(x3-x1)-x0._f140;
var x10=(x4-x8)*x6/(x4-x2)-x0._f141;
var x11=MVSdoGeometry.createPoint(x9,x10);
return x11.sdo_point;
}
MVUtil._f157=function(x12,x13,x14,x15,x16,x17,x18,x19,x20)
{
var x21=(x19-x13)*x17/(x15-x13)-x12._f140;
var x22=(x16-x20)*x18/(x16-x14)-x12._f141;
return [x21,x22];
}
MVUtil.getNumber=function(x23)
{
x23=x23+"";
if(x23.indexOf("px")>0)
x23=x23.substring(0,x23.length-2);
return parseInt(""+x23);
}
MVUtil._f84=function(x24)
{
var x25=x24.style.left;
if(x25.indexOf("px")>0)
x25=x25.substring(0,x25.length-2);
return parseInt(""+x25);
}
MVUtil._f85=function(x26)
{
var x27=x26.style.top;
if(x27.indexOf("px")>0)
x27=x27.substring(0,x27.length-2);
return parseInt(""+x27);
}
MVUtil.getZIndex=function(x28)
{
var x29=x28.style.zIndex;
if(!x29)
return 0;
return parseInt(""+x29);
}
MVUtil._f74=function(x30,x31,x32)
{
if(_f11._f360==2)
{
x30.style.pixelLeft=x31;
x30.style.pixelTop=x32;
}
else
 {
x30.style.left=MVUtil._f32(x31);
x30.style.top=MVUtil._f32(x32);
}
}
MVUtil._f638=function(x33,x34,x35,x36,x37,x38,x39)
{
x33.style.left=MVUtil._f32(x34);
x33.style.top=MVUtil._f32(x35);
x33.style.width=MVUtil._f32(x36);
x33.style.height=MVUtil._f32(x37);
if(x38)
x33.style.zIndex=x38;
if(x39)
x33.style.fontSize=MVUtil._f32(x39);
}
MVUtil._f598=function(x40)
{
x40.style.position="absolute";
x40.unselectable="on";
x40.onselectstart=MVUtil._f654;
x40.style.fontFamily="Arial, sans serif";
x40.style.MozUserSelect="none";
x40.align="left";
}
MVUtil._f654=function()
{
return false;
}
MVUtil._f164=function(x41,x42)
{
try
{
if(x42.indexOf(".")>-1)
{
x42="url(\""+x42+"\"),auto";
}
x41.style.cursor=x42;
}
catch(_exception)
{
if(x42=="pointer")
{
MVUtil._f164(x41,"hand");
}
}
}
MVUtil._f497=function(x43)
{
var x44={"x":0,"y":0};
var x45=x43;
while(x43)
{
x44.x+=x43.offsetLeft;
x44.y+=x43.offsetTop;
x43=x43.offsetParent;
}
x43=x45;
while(x43&&x43.tagName!="BODY")
{
x44.x-=x43.scrollLeft;
x44.y-=x43.scrollTop;
x43=x43.parentNode;
}
return x44;
}
function _f284(x0,x1,x2,x3,x4,x5,x6)
{
var x7=null;
if(x6&&_f11._f69())
{
x7=document.createElement("div");
x7.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(src='"+
x0+"', sizingMethod='scale');";
}
else
 {
x7=document.createElement("img");
x7.src=x0;
}
if(x1&&x2)
{
x7.style.width=MVUtil._f32(x1);
x7.style.height=MVUtil._f32(x2);
x7.width=x1;
x7.height=x2;
}
if(x4||(x3||(x4==0||x3==0)))
{
x7.style.position="absolute";
x7.style.left=MVUtil._f32(x3);
x7.style.top=MVUtil._f32(x4);
}
if(x5||x5==0)
{
x7.style.zIndex=x5;
}
if(_f11._f70=="IF"){
x7.unselectable="on";
x7.onselectstart=MVUtil._f654;}
else{
x7.style.MozUserSelect="none";
}
if(_f11._f70=="IF")
{
x7.galleryImg="no"}
x7.style.border="0";
x7.style.padding="0";
x7.style.margin="0";
x7.oncontextmenu=MVUtil._f654;
return x7;
}
MVUtil._f655=function(x46,x47)
{
if(x47)
{
var x48=null;
if(x47.type!=undefined)
x48=x46._f220(x47);
else
 x48=x47;
var x49=MVSdoGeometry.createPoint(x48.left,x48.top);
return x49.sdo_point;
}
else
 {
var x48=x46._f220(window.event);
var x49=MVSdoGeometry.createPoint(x48.left,x48.top);
return x49.sdo_point;
}
}
MVUtil.getMouseLocation=function(x50,x51)
{
var x52=MVUtil._f655(x50,x51);
var x53=x52.x-x50._f109()/2;
var x54=x53/x50._f76+x50._f95;
x53=x52.y-x50._f110()/2;
var x55=((-1.0)*x53)/x50._f78+x50._f96;
var x56=MVSdoGeometry.createPoint(x54,x55);
return x56;
}
MVUtil._f174=function(x57)
{
x57=(x57)?x57:((window.event)?event:null);
var x58=0;
var x59=0;
if(x57.pageX)
{
x58=x57.pageX;
x59=x57.pageY;
}
else if(x57.clientX)
{
x58=x57.clientX+document.body.scrollLeft-document.body.clientLeft;
x59=x57.clientY+document.body.scrollTop-document.body.clientTop;
if(document.body.parentElement&&(document.body.parentElement.clientLeft
||document.body.parentElement.clientTop))
{
var x60=document.body.parentElement;
x58+=x60.scrollLeft-x60.clientLeft;
x59+=x60.scrollTop-x60.clientTop;
}
}
return MVSdoGeometry.createPoint(x58,x59).sdo_point;
}
MVUtil.keepInCSBoundary=function(x61,x62)
{
var x63=x61.msi._f158-x61.msi._f159;
while(x62<x61.msi._f159)
{
x62=x62+x63;
}
while(x62>x61.msi._f158)
{
x62-=x63;
}
return x62;
}
MVUtil.keepOrdinatesInCSBoundary=function(x64,x65)
{
var x66=new Array();
for(var x67=0;x67<x65.length;)
{
x66.push(MVUtil.keepInCSBoundary(x64,parseFloat(x65[x67++])));
x66.push(x65[x67++]);
}
return x66;
}
MVUtil._f656=0;
MVUtil._f657=function(x68)
{
var x69=document.createElement("div");
x69.style.zIndex=1000;
x69.style.width=200;
x69.style.backgroundColor="#ffffff";
x69.style.position="absolute";
x69.style.left=0;
x69.style.top=((MVUtil._f656++)*20)%550;
x69.innerHTML=x68;
document.body.appendChild(x69);
}
MVUtil.getXMLHttpRequest=function(x70)
{
if(!x70)
return new MVXMLHttpRequest();
else
 {
if(window.ActiveXObject)
{
var x71=null;
try
{
x71=new ActiveXObject("Microsoft.XMLHTTP");
}
catch(e)
{
x71=new ActiveXObject("Msxml2.XMLHTTP");
}
return x71;
}
else
 return new XMLHttpRequest();
}
}
MVUtil._f173=function(x72)
{
if(x72)
{
if(_f11._f70=="IF")
x72.cancelBubble=true;
else if(x72.stopPropagation)
x72.stopPropagation();
if(x72.preventDefault)
x72.preventDefault();
else
 x72.returnValue=false;
}
}
MVUtil._f32=function(x73)
{
return Math.round(x73)+"px";
}
MVUtil._f658=function(x74,x75)
{
var x76=0;
var x77=-1;
while((x77=x74.indexOf(x75,x77+1))!=-1)
{
x76++;
}
return x76;
}
MVUtil._f659=function(x78)
{
var x79=x78.indexOf('<');
if(x79==-1)
return "";
var x80=x78.indexOf(' ',x79+1);
if(x80==-1)
return "";
return '</'+MVUtil._f227(x78.substring(x79+1,x80))+'>';
}
MVUtil._f227=function(x81)
{
return x81.replace(/(^[\s]*)|([\s]*$)/g,"");
}
MVUtil._f660=function(x82,x83)
{
return x82.substring(0,x83.length)==x83;
}
MVUtil._f5=function(x84,x85)
{
return x84.substring(x84.length-x85.length,x84.length)==x85;
}
MVUtil._f661=function(x86,x87)
{
return x87+x86+MVUtil._f659(x87);
}
MVUtil._f49=function(x88,x89,x90)
{
var x91;
x91=x88.indexOf(x89);
if(x91>-1)
{
var x92=x88.substring(0,x91);
var x93=x88.substring(x91+x89.length,x88.length);
x88=x92+x90+MVUtil._f49(x93,x89,x90);
}
return x88;
}
MVUtil._f46=function(x94)
{
if(x94.indexOf(".")>=0)
return x94.substring(0,x94.indexOf("."));
else
 return x94;
}
MVUtil._f45=function(x95)
{
if(x95.indexOf(".")>=0)
return x95.substring(x95.indexOf(".")+1,x95.length);
else
 return x95;
}
MVUtil.objArray=new Array();
MVUtil._f190=new Array();
MVUtil._f662=new Array();
MVUtil._f163=function(x96)
{
if(_f11._f70!="IF")
return;
var x97=MVUtil._f190.length;
if(x96._f663)
{
x97=(x96._f663);
}
else
 x96._f663=-1;
if((x96._f663)==-1&&(MVUtil._f662.length)>0)
x97=MVUtil._f662.pop();
x96._f663=x97;
MVUtil._f190[x97]=x96;
}
MVUtil.MVUnload=function()
{
if(typeof(MVUtil._f190)!="undefined"&&(MVUtil._f190!=null))
{
for(var x98=0;MVUtil._f190&&x98<MVUtil._f190.length;x98++)
{
if(MVUtil._f190[x98])
{
MVUtil._f191(MVUtil._f190[x98]);
MVUtil._f190[x98]=null;
}
}
}
while(MVUtil.objArray.length>0)
{
var x99=MVUtil.objArray.pop();
x99.destroy();
}
}
window.onunload=function()
{
MVUtil.MVUnload();
}
MVUtil._f106=function(x100,x101)
{
if(_f11._f70!="IF")
{
return function()
{
return x101.apply(x100,arguments);
}
}
if(!(MVUtil._f664))
{
MVUtil._f664=new Array();
MVUtil._f665=new Array();
MVUtil._f666=new Array();
}
if(!(MVUtil._f667))
{
MVUtil._f667=new Array();
MVUtil._f668=new Array();
MVUtil._f669=new Array();
}
try
{
var x102=(MVUtil._f664.length);
if(x100._f663)
{
x102=(x100._f663);
}
else
 x100._f663=-1;
if((x100._f663)==-1&&(MVUtil._f666.length)>0)
x102=MVUtil._f666.pop();
MVUtil._f664[x102]=x100;
MVUtil._f664[x102]._f663=x102;
if(!(MVUtil._f665[x102]))
MVUtil._f665[x102]=new Array();
var x103=(MVUtil._f665[x102].length);
MVUtil._f665[x102][x103]=x101;
x100=null;
x101=null;
return function()
{
return MVUtil._f665[x102][x103].apply(MVUtil._f664[x102],arguments);
}
}
catch(e)
{
var x102=(MVUtil._f667.length);
if((MVUtil._f669.length)>0)
x102=MVUtil._f669.pop();
MVUtil._f667[x102]=x100;
MVUtil._f668[x102]=x101;
x100=null;
x101=null;
return function()
{
return MVUtil._f668[x102].apply(MVUtil._f667[x102],arguments);
}
}
}
MVUtil._f306=function(x104)
{
if(_f11._f70=="IF")
{
if(x104._f663)
{
var x105=(x104._f663);
for(var x106=(MVUtil._f665[x105].length);x106>0;--x106)
{
MVUtil._f665[x105][x106-1]=null;
MVUtil._f665[x105].pop();
}
MVUtil._f665[x105]=null;
x104._f663=null;
x104=null;
MVUtil._f664[x105]=null;
MVUtil._f666.push(x105);
}
}
}
MVUtil.gc=function()
{
if(_f11._f70=="IF"&&MVUtil._f667)
{
var x107=0;
if(MVUtil._f670)
x107=(MVUtil._f670);
if((MVUtil._f667.length)<x107)
return;
for(var x108=0;x108<(MVUtil._f667.length);++x108)
{
if((MVUtil._f667[x108])&&(MVUtil._f667[x108].readyState)&&(MVUtil._f667[x108].readyState)==4)
{
MVUtil._f667[x108]=null;
MVUtil._f668[x108]=null;
MVUtil._f669.push(x108);
}
}
}
}
MVUtil._f671=function(x109,x110)
{
if(_f11._f70!="IF")
return;
if(x109=="time")
{
if(MVUtil._f672)
{
clearInterval(MVUtil._f672);
MVUtil._f672=setInterval(MVUtil.gc,x110);
}
else
 MVUtil._f672=setInterval(MVUtil.gc,x110);
}
if(x109=="totalNum")
{
MVUtil._f670=x110;
}
}
MVUtil._f269=function(x111)
{
if(x111!=null)
{
delete x111;
}
}
MVUtil._f146=function(x112)
{
if(_f11._f70=="NS")
{
x112.style.MozUserSelect="none";
}
else if(_f11._f70=="SF")
{
x112.style.KhtmlUserSelect="none";
}
else if(_f11._f70=="IF")
{
x112.unselectable="on";
}
else
 {
return false;
}
return true;
}
MVUtil._f673=function(x113)
{
if(_f11._f70=="NS")
{
x113.style.MozUserSelect="";
}
else if(_f11._f70=="SF")
{
x113.style.KhtmlUserSelect="";
}
else if(_f11._f70=="IF")
{
x113.unselectable="off";
}
else
 {
return false;
}
return true;
}
MVUtil._f9=function(x114)
{
if(!x114)
return null;
var x115=x114.indexOf("://");
if(x115>0)
{
x115+=3;
var x116=x114.indexOf("/",x115);
if(x116>0)
return x114.substring(x115,x116);
else
 return x114.substring(x115);
}
else
 return null;
}
MVUtil._f129=function(x117,x118)
{
if(!x117)
return ;
var x119=x117.childNodes;
if(x119!=null)
{
while(x119.length>0)
{
MVUtil._f191(x119[0],x118);
x117.removeChild(x119[0]);
}
}
}
MVUtil._f191=function(x120,x121)
{
if(!x120)
return ;
var x122=x120.childNodes;
if(x122!=null)
{
while(x122.length>0)
{
MVUtil._f191(x122[0],x121);
x120.removeChild(x122[0]);
}
}
MVUtil._f629(x120,x121);
}
MVUtil._f629=function(x123,x124)
{
if(!x123)
return ;
try
{
if(x123.onload)
x123.onload=null;
if(x123.onmouseover)
x123.onmouseover=null;
if(x123.onmouseout)
x123.onmouseout=null;
if(x123.onmousedown)
x123.onmousedown=null;
if(x123.onmouseup)
x123.onmouseup=null;
if(x123.oncontextmenu)
x123.oncontextmenu=null;
if(x123.ondblclick)
x123.ondblclick=null;
if(x123.onclick)
x123.onclick=null;
if(x123.onkeyup)
x123.onkeyup=null;
if(x123.onkeydown)
x123.onkeydown=null;
if(x123.onkeypressed)
x123.onkeypressed=null;
if(_f11._f70=="IF")
{
if(x123.style&&x123.style.filter)
x123.style.filter=null;
if(x123._f663)
{
MVUtil._f662.push(x123._f663)
MVUtil._f190[x123._f663]=null;
}
}
}catch(error)
{
MVi18n._f6("MVUtil.clearListeners","MAPVIEWER-05500",x123.nodeName+","+x123.nodeType+"\n"+error,x124);
}
}
MVUtil._f92=MVUtil._f191;
MVUtil._f674=function(x125)
{
var x126=document.createElement("table");
var x127=document.createElement("tr");
var x128=document.createElement("td");
x128.innerHTML=x125;
x127.appendChild(x128);
x126.appendChild(x127);
return x126;
}
MVUtil.getEvent=function(x129)
{
return x129?x129:((window.event)?event:null);
}
MVUtil.isIE7=function()
{
if(navigator.userAgent&&navigator.userAgent.indexOf("MSIE 7")>=0)
return true;
else
 return false;
}
MVUtil.getNumber=function(x130)
{
var x131=0;
if(typeof(x130)=="string")
{
if(x130.indexOf("px")>0)
x130=x130.substring(0,x130.length-2);
if(x130.indexOf(".")>0)
x131=parseFloat(x130);
else
 x131=parseInt(x130);
if(isNaN(x131))
x131=0;
return x131;
}
else if(typeof(x130)=="number")
x131=x130;
return x131;
}
MVUtil.trapEvent=function(x132)
{
x132=(x132)?x132:((window.event)?event:null);
if(_f11._f70=="IF")
x132.cancelBubble=true;
else if(x132.stopPropagation)
x132.stopPropagation();
}
MVUtil._f170=function(x133,x134)
{
var x135=(_f11._f69()&&x134)?
document.createElement('div'):document.createElement('img');
if(_f11._f69()&&x134)
{
if(x133)
x135.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(src='"+
x133+"', sizingMethod='image');";
}
else
 {
x135.src=x133;
}
if(_f11._f70=="IF"&&!x134)
{
x135.galleryImg="no"}
return x135;
}
MVUtil.attachEvent=function(x136,x137,x138)
{
if(_f11._f70=="IF")
{
x137="on"+x137;
x136.attachEvent(x137,x138);
}
else
 {
x136.addEventListener(x137,x138,"false");
}
}
MVUtil.detachEvent=function(x139,x140,x141)
{
if(_f11._f70=="IF")
{
x140="on"+x140;
x139.detachEvent(x140,x141);
}
else
 {
x139.removeEventListener(x140,x141,"false");
}
}
MVUtil.mouseLeftKeyPressed=function(x142)
{
if(_f11._f70=="IF")
return x142.button==1;
else
 return x142.which==1;
}
MVUtil.mouseRightKeyPressed=function(x143)
{
if(_f11._f70=="IF")
return x143.button==2;
else
 return x143.which==3;
}
MVUtil.formatNum=function(x144)
{
if(x144>=1000)
{
x144=new Number(x144);
x144=x144.toLocaleString();
if(x144.indexOf(".00")>0)
{
x144=x144.substring(0,x144.indexOf(".00"));
}
}
return x144;
}
MVUtil.getEventSource=function(x145)
{
if(_f11._f70=="IF")
{
return x145.srcElement;
}
else
 {
return x145.target;
}
}
MVUtil.unique_array=function(x146)
{
var x147=x146;
var x148;
for(var x149=0;x149<x146.length;x149++)
{
for(var x150=0;x150<x146.length;x150++)
{
x148=x146[x149];
if((x149+x150+1)<x146.length&&x148==x146[x149+x150+1])x147.splice(x149+x150+1,1);}
}
return x147;
}
MVUtil.runListeners=function(x151,x152,x153)
{
if(x151)
{
for(each in x151[x152])
{
if(x153!=undefined)
(x151[x152][each]).apply(x151[x152][each],x153);
else
 (x151[x152][each]).apply(x151[x152][each]);
}
}
}
MVUtil.detachEventListener=function(x154,x155,x156)
{
var x157=x154[x155];
if(x157)
{
for(var x158=0;x158<x157.length;x158++)
{
if(x156==x157[x158])
{
x157.splice(x158,1);
}
}
}
}
MVUtil.attachEventListener=function(x159,x160,x161)
{
x159[x160]?
x159[x160].push(x161):x159[x160]=[x161];
x159[x160]=MVUtil.unique_array(x159[x160]);
}
MVUtil.getMVIndFOIsControlAndFOI=function(x162)
{
var x163=x162.ifctl;
while(!x163&&x162.parentNode){x162=x162.parentNode;
x163=x162.ifctl;
}
if(x162.parentNode&&x163)
var x164=x163._f262(x162.orgid?x162.orgid:x162.id);
if(x164&&x164.origFOI)
x164=x164.origFOI;
return {ifctl:x163,foi:x164};
}
MVUtil.transOrdinatesOnWarpAroungMap=function(x165,x166)
{
var x167=x165.msi._f158-x165.msi._f159;
var x168=x167/2;
var x169=x165.msi._f159+x168;
var x170=new Array();
if(x166.length<4)
return x166;
var x171=x166[0];
var x172;
x170.push(x171);
x170.push(x166[1]);
for(var x173=2;x173<x166.length;x173++)
{
x172=x166[x173];
if(Math.abs(x172-x171)>x168)
{
if((x172-x171)<0)
x172+=x167;
else
 x172-=x167;
}
x171=x172;
x170.push(x172);
x170.push(x166[++x173]);
}
return x170;
}
MVUtil.transLongitudeOnWarpAroungMap=function(x174,x175,x176)
{
var x177=x174.msi._f158-x174.msi._f159;
var x178=x177/2;
var x179=x174.msi._f159+x178;
while(Math.abs(x176-x175)>x178)
{
if((x176-x175)<0)
{
x176+=x177;
}
else
 {
x176-=x177;
}
}
return x176;
}
MVUtil.crossDateLine=function(x180,x181)
{
var x182=x180.msi._f158-x180.msi._f159;
var x183=x182/2;
var x184=x180.msi._f159+x183;
if(x181.length<4)
return false;
var x185=x181[0];
var x186;
for(var x187=2;x187<x181.length;x187+=2)
{
x186=x181[x187];
if(Math.abs(x186-x185)>x183)
{
return true
}
else if(x185>x180.msi._f159&&x185<x180.msi._f158
&&(x186>x180.msi._f158||x186<x180.msi._f159)){
return true;
}
x185=x186;
}
return false;
}
MVUtil.duplicateCrossDateLineOrds=function(x188,x189,x190)
{
var x191=x188.msi._f158-x188.msi._f159;
var x192=x191/2;
var x193=x188.msi._f159+x192;
var x194=new Array();
var x195=false;
if(x190[0]<x188.msi._f158&&x190[2]>x188.msi._f158)
x195=true;
for(var x196=0;x196<x189.length;x196++)
{
x=x189[x196];
if(x195)
x194.push(parseFloat(x)-x191);
else
 x194.push(parseFloat(x)+x191);
x194.push(x189[++x196]);
}
return x194;
}
MVUtil.cloneObject=function(x197)
{
var x198={};
for(property in x197)x198[property]=x197[property];
return x198;
}
MVUtil.calAreaCodeFromCenter=function(x199,x200)
{
var x201=null;
if(x199.wraparound)
{
var x202=x199.msi._f158-x199.msi._f159;
var x203=x199._f248.centerArea;;
var x204=x199._f222();
var x205=x199._f248.maparea[x203].n;
var x206=x199.msi._f159+x202*x205;
var x207=x199.msi._f158+x202*x205;
var x208=x200-x204;
var x209=0;
while(Math.abs(x208)>x202/2)
{
if(x208<0)
{
x200=x200+x202;
x209--;
}
else
 {
x200=x200-x202;
x209++;
}
x208=x200-x204;
}
if(x200<x206)
x201=x203-1+x209;
else if(x200>x207)
x201=x203+1+x209;
else
 x201=x203+x209;
}
return x201;
}
MVUtil.isNumber=function(x210)
{
if(x210!=null&&typeof x210=="number")
return true;
else
 return false;
}
MVUtil.isNumberArray=function(x211)
{
if(!x211)
return true;
if(typeof x211=="object")
if(x211.length==0||MVUtil.isNumber(x211[0]))
return true;
return false;
}
MVUtil.getImageSize=function(x212,x213)
{
var x214=new Image();
x214.src=x212
x214.onload=function(){
var x215=this.width;
var x216=this.height;
x214.onload=null;
x214=null;
x213(x215,x216);
}
}
MVUtil.canBeCombined=function(x217,x218)
{
if(x217._f507||x218._f507)
return false;
if(x217.getType()!="MVMapTileLayer"||x218.getType()!="MVMapTileLayer"||
!x217.layerControl||!x218.layerControl)
return false;
if(x217._f409!=x218._f409)
return false;
var x219=x217.config;
var x220=x218.config;
if(x219.dataSource!=x219.dataSource)
return false;
if(x219.format!="PNG"||x220.format!="PNG")
return false;
if(x219.coordSys.srid!=x220.coordSys.srid||
x219.coordSys.minX!=x220.coordSys.minX||
x219.coordSys.minY!=x220.coordSys.minY)
return false;
if(x219.zoomLevels.length!=x219.zoomLevels.length)
return false;
for(var x221=0;x221<x219.zoomLevels.length;x221++)
{
if(x219.zoomLevels[x221].tileWidth!=x220.zoomLevels[x221].tileWidth||
x219.zoomLevels[x221].tileHeight!=x220.zoomLevels[x221].tileHeight||
x219.zoomLevels[x221].tileImageWidth!=x220.zoomLevels[x221].tileImageWidth||
x219.zoomLevels[x221].tileImageHeight!=x220.zoomLevels[x221].tileImageHeight)
return false;
}
return true;
}
MVUtil.getImageURL=function(x222)
{
if(x222.indexOf("/")==0||x222.indexOf("http://")==0||
x222.indexOf("https://")==0||x222.indexOf("../")==0||
x222.indexOf("./")==0)
return x222;
else
 return _f11._f142+x222;
}
function _f361()
{
}
_f361._f362=6372795;
_f361._f363=function(x0,x1,x2)
{
var x0=_f361._f364(x0);
var x3=x0.x;
var x4=x0.y;
var x5=x1/_f361._f362;
var x6=Math.asin(Math.sin(x4)*Math.cos(x5)+Math.cos(x4)*Math.sin(x5)*Math.cos(x2));
var x7=x3+Math.atan2(Math.sin(x2)*Math.sin(x5)*Math.cos(x4),Math.cos(x5)-Math.sin(x4)*Math.sin(x6));
return _f361._f365(new MVSdoPointType(x7,x6,0));
}
_f361._f364=function(x8)
{
var x9=x8.x;
var x10=x8.y;
while(x9<-180)x9+=360;
while(x9>180)x9-=360;
x9=x9*Math.PI/180;
x10=x10*Math.PI/180;
return new MVSdoPointType(x9,x10,0);
}
_f361._f365=function(x11)
{
var x12=x11.x;
var x13=x11.y;
x13=x13*180/Math.PI;
x12=x12*180/Math.PI;
return new MVSdoPointType(x12,x13,0);
}
_f361._f366=function(x14,x15)
{
x14=_f361._f364(x14);
x15=_f361._f364(x15);
var x16=x15.x-x14.x;
var x17=x15.y-x14.y;
var x18=Math.sin(x17/2);
var x19=Math.sin(x16/2);
var x20=x18*x18+Math.cos(x14.y)*Math.cos(x15.y)*x19*x19;
var x21=2*Math.atan2(Math.sqrt(x20),Math.sqrt(1-x20));
return x21*_f361._f362;
}
_f361._f367=function(x22)
{
var x23=0;
for(i=0;i<x22.length-2;i+=2)
{
x23+=_f361._f366(new MVSdoPointType(x22[i],x22[i+1]),new MVSdoPointType(x22[i+2],x22[i+3]));
}
return x23;
}
_f361.getSphericalArea=function(x24)
{
var x25=q.length/2;
var x26,x27,x28;
var x29;
var x30,x31,x32;
var x33;
var x34;
var x35=0.;
var x36;
var x37=0.;
for(x31=0,x30=x25-1,x32=1,dP=q+2;x31<x25;++x31,++x30,++x32,dP+=3)
{
x30%=x25;
x32%=x25;
var x38=MVGeoUtil.get3DVector(q[2*x31],q[2*x31+1]);
var x39=MVGeoUtil.get3DVector(q[2*x30],q[2*x30+1]);
var x40=MVGeoUtil.get3DVector(q[2*x32],q[2*x32+1]);
x37+=x38.z;
x26=MVGeoUtil.mdsphcp(x38,x38);
x29=MVGeoUtil.MAGNITUDE(x26);
x27=MVGeoUtil.mdsphcp(x38,x40);
x29*=MVGeoUtil.MAGNITUDE(x27);
x36=MVGeoUtil.DOTPRODUCT(x26,x27)/x29;
if(x36>1.)x36=1.;
if(x36<-1.)x36=-1.;
x33=Math.acos(x36);
x28=MVGeoUtil.mdsphcp(x26,x27);
if(Math.abs(x28.x)<1.e-9*x29&&Math.abs(x28.y)<1.e-9*x29&&
Math.abs(x28.z)<1.e-9*x29)
{
if(x36>0.)
x35-=Math.PI;
}
else
 {
if(MVGeoUtil.DOTPRODUCT(x28,x38)<0.)
x35+=x33-Math.PI;
else
 x35+=Math.PI-x33;
}
}
x37/=x25*1.0;
if(x35<0.)x35+=2.*Math.PI;
else x35-=2.*Math.PI;
}
_f361.mdsphcp=function(x41,x42)
{
var x43={"x":0,"y":0,"z":0};
if((x41.x==x42.x)&&(x41.y==x42.y)&&(x41.z==x42.z))
{
x43.x=x43.y=x43.z=0.0;
}
else
 {
x43.x=x41.y*x42.z-x41.z*x42.y;
x43.y=x41.z*x42.x-x41.x*x42.z;
x43.z=x41.x*x42.y-x41.y*x42.x;
}
return x43;
}
_f361.get3DVector=function(x44,x45)
{
var x46={"x":x44,"y":x45};
var x47=_f361._f364(x46);
x47.z=Math.sin(x47.y);
return x47;
}
_f361.MAGNITUDE=function(x48)
{
return Math.sqrt(x48.x*x48.x+x48.y*x48.y+x48.z*x48.z);
}
_f361.DOTPRODUCT=function(x49,x50)
{
return x49.x*x50.x+x49.y*x50.y+x49.z*x50.z;
}
function _f11(){}
_f11._f360=document.all?(document.getElementById?2:1):(document.layers?3:0);
_f11._f529=navigator.userAgent.toLowerCase();
_f11._f530=null;
_f11._f531=null;
_f11._f514=new Array(30);
_f11._f532=new Array(30);
_f11._f533=new Array(30);
_f11._f295="MVInfoWindowStyle1";
_f11._f534=null;
_f11._f535=new Array(30);
_f11._f456=function()
{
return _f11._f534;
}
_f11._f536=function(x0)
{
if(!_f11._f534)
_f11._f534=x0;
}
_f11._f489=function(x1)
{
_f11._f530=_f11._f529.indexOf(x1)+1;
_f11._f531=x1;
return _f11._f530;
}
if(_f11._f489('safari'))
_f11._f70="SF";
else if(_f11._f489('opera'))
_f11._f70="OS";
else if(_f11._f489('msie'))
_f11._f70="IF";
else if(!_f11._f489('compatible'))
{
_f11._f70="NS";
_f11._f537=_f11._f529.substring(8,11);}
else _f11._f70="unknown";
if(!_f11._f537)
_f11._f537=_f11._f529.substring(_f11._f530+_f11._f531.length,
_f11._f530+_f11._f531.length+3);
_f11._f538=0;
if(_f11._f537)
{
var i=_f11._f537.indexOf(".");
if(i>=0)
_f11._f538=parseInt(_f11._f537.substring(0,i));
else
 _f11._f538=parseInt(_f11._f537);
}
_f11._f69=function()
{
return _f11._f70=="IF"&&_f11._f538<7;
}
_f11._f533["SQ_MM"]=.000001;
_f11._f533["SQ_KILOMETER"]=1000000;
_f11._f533["SQ_CENTIMETER"]=.0001;
_f11._f533["SQ_MILLIMETER"]=.000001;
_f11._f533["SQ_CH"]=404.6856;
_f11._f533["SQ_FT"]=.09290304;
_f11._f533["SQ_IN"]=.00064516;
_f11._f533["SQ_LI"]=.04046856;
_f11._f533["SQ_CHAIN"]=404.6856;
_f11._f533["SQ_FOOT"]=.09290304;
_f11._f533["SQ_INCH"]=.00064516;
_f11._f533["SQ_LINK"]=.04046856;
_f11._f533["SQ_MILE"]=2589988;
_f11._f533["SQ_ROD"]=25.29285;
_f11._f533["SQ_SURVEY_FOOT"]=.09290341;
_f11._f533["SQ_YARD"]=.8361274;
_f11._f533["ACRE"]=4046.856;
_f11._f533["HECTARE"]=10000;
_f11._f533["PERCH"]=25.29285;
_f11._f533["ROOD"]=1011.714;
_f11._f533["M"]=1;
_f11._f533["METER"]=1;
_f11._f533["KM"]=1000;
_f11._f533["KILOMETER"]=1000;
_f11._f533["CM"]=.01;
_f11._f533["CENTIMETER"]=.01;
_f11._f533["MM"]=.001;
_f11._f533["MILLIMETER"]=.001;
_f11._f533["MILE"]=1609.344;
_f11._f533["NAUT_MILE"]=1852;
_f11._f533["SURVEY_FOOT"]=.3048006096012;
_f11._f533["FOOT"]=.3048;
_f11._f533["INCH"]=.0254;
_f11._f533["YARD"]=.9144;
_f11._f533["CHAIN"]=20.1168;
_f11._f533["ROD"]=5.0292;
_f11._f533["LINK"]=.201166195;
_f11._f533["MOD_USFT"]=.304812253;
_f11._f533["CL_FT"]=.304797265;
_f11._f533["IND_FT"]=.304799518;
_f11._f533["LINK_BEN"]=.201167651;
_f11._f533["LINK_SRS"]=.201167651;
_f11._f533["CHN_BEN"]=20.1167825;
_f11._f533["CHN_SRS"]=20.1167651;
_f11._f533["IND_YARD"]=.914398554;
_f11._f533["SRS_YARD"]=.914398415;
_f11._f533["FATHOM"]=1.8288;
_f11._f533["SQ_M"]=1;
_f11._f533["SQ_METER"]=1;
_f11._f533["SQ_KM"]=1000000;
_f11._f533["SQ_CM"]=.0001;
_f11._f459=function(x2)
{
if(x2)
x2=x2.toUpperCase();
else
 return 0;
if(_f11._f533[x2])
return _f11._f533[x2];
else
 return 0;
}
_f11._f539=function(x3,x4)
{
x3=x3.toUpperCase();
_f11._f514[x3]=x4;
if(x4.coordSys.srid)
_f11._f532[x4.coordSys.srid]=x4.coordSys;
}
_f11._f457=function(x5)
{
if(x5&&_f11._f532[x5])
return _f11._f532[x5];
else
 return null;
}
_f11._f541=function(basemap,callBack,_f7)
{
if(basemap._f409==null||basemap._f108==null||MVUtil._f227(basemap._f108)=="")
{
basemap._f108="null";
_f11._f514[basemap._f108]=null;return;
}
basemap._f108=basemap._f108.toUpperCase();
if(_f11._f514[basemap._f108])
{
if(_f11._f514[basemap._f108].transparent)
basemap._f412=true;
if(callBack)
callBack(_f11._f514[basemap._f108]);
return _f11._f514[basemap._f108];
}
var conStr=basemap._f505.indexOf("?")>=0?"&":"?";
var formatStr;
var _f542=basemap._f505+conStr+"xml_request=<?xml+version=\"1.0\"+standalone=\"yes\"?>"
+"<map_cache_admin_request><get_client_config+map_cache_names=\""+basemap._f108
+"\"+format=\"JSON\"/></map_cache_admin_request>";
var request=null;
var localDomain=(MVUtil._f9(basemap._f409)==MVUtil._f9(_f11._f119()));
var xmlHttp=localDomain||MVMapView._f8;
var configLoaded=function()
{
try
{
var resp=xmlHttp?request.responseText:this.responseText;
eval("var result="+resp);
if(result.length==0)
{
MVi18n._f6("MVGlobalVariables.getMapCacheConfig","MAPVIEWER-05501",basemap._f108,_f7);
_f11._f514[basemap._f108]=null;result=null;
return;
}
if(result[0].transparent)
basemap._f412=true;
_f11._f539(basemap._f108,result[0]);
result=null;
}
catch(e)
{
MVi18n._f6("MVGlobalVariables.getMapCacheConfig","MAPVIEWER-05523",
basemap._f108+":"+request.responseText,_f7);
return ;
}
if(callBack)
callBack(_f11._f514[basemap._f108]);
}
try
{
request=MVUtil.getXMLHttpRequest(xmlHttp);
if(!xmlHttp)
request.onreadystatechange=configLoaded;
request.open("GET",_f542,!xmlHttp);
request.send("");
}catch(e)
{
MVi18n._f6("MVGlobalVariables.getMapCacheConfig","MAPVIEWER-05511",e,_f7);
return ;
}
if(xmlHttp)
{
if(request.status==200)
{
configLoaded();
}
else
 {
_f11._f514[basemap._f108]=null;MVi18n._f6("MVGlobalVariables.getMapCacheConfig","MAPVIEWER-05511",
basemap._f108+":"+request.responseText,_f7);
}
}
request=null;
}
_f11._f299=180;
_f11._f300=100;
_f11._f543=14;
_f11._f544=112;
_f11._f545=14;
_f11._f546=14;
_f11._f547=14;
_f11._f548=14;
_f11._f549=14;
_f11._f550=14;
_f11._f551=14;
_f11._f552=14;
_f11._f376=600;
_f11._f377=600;
_f11._f378=30;
_f11._f379=16;
_f11._f380=600;
_f11._f381=1;
_f11._f382=1;
_f11._f383=600;
_f11._f384=1;
_f11._f385=10;
_f11._f487=true;
_f11._f142="/fsmc/images/";
_f11._f553="/fsmc/images/";
_f11._f307=0;
_f11._f348=null;
_f11._f554="bulls-eye.png";
_f11._f555=38;
_f11._f556=38;
_f11._f355=5;
_f11._f354="-redline-point-marker";
_f11._f357="-redline-line-style";
_f11._f358="-redline-area-style";
_f11._f483=0;
_f11._f557=0;
_f11._f276=2000;
_f11._f558=null;
_f11._f10=null;
_f11._f454=function()
{
if(!_f11._f558)
_f11._f558=document.location.protocol+"//"+document.location.host+"/mapviewer/foi";
return _f11._f558;
}
_f11._f119=function()
{
if(!_f11._f10)
_f11._f10=document.location.protocol+"//"+document.location.host+"/mapviewer";
return _f11._f10;
}
_f11._f559="/mapviewer/proxy";
_f11._f560=null;
_f11._f12=function()
{
if(!_f11._f560)
_f11._f560=document.location.protocol+"//"+document.location.host+"/"+_f11._f559;
return _f11._f560;
}
if(document.getElementsByTagName("html")[0].getAttribute("xmlns")=="http://www.w3.org/1999/xhtml")
_f11._f561=true;
else
 _f11._f561=false;
_f11._f405=function(x6,x7,x8)
{
var x9=_f11._f535[x6];
if(!x9)
x9=_f11._f535[x6]=new Array();
x9[x7]=x8;
}
_f11._f458=function(x10,x11)
{
var x12=_f11._f535[x10];
if(x12)
return x12[x11];
else
 return null;
}
_f11._f562=0;
_f11._f563=false;
function _f512(x0)
{
this._f513=x0;
this.mapConfig=_f11._f514[x0];
this._f159=this._f55();
this._f275=this._f56();
this._f158=this._f57();
this._f274=this._f58();
this._f515=this._f57()-this._f55();
this._f516=this._f58()-this._f56();
this.zoomCount=_f11._f514[this._f513].zoomLevels.length;
}
_f512.prototype._f517=function()
{
return this._f513;
}
_f512.prototype._f411=function(x0)
{
return _f11._f514[x0].format;
}
_f512.prototype._f518=function()
{
return this._f515;
}
_f512.prototype._f519=function()
{
return this._f516;
}
_f512.prototype._f55=function()
{
return _f11._f514[this._f513].coordSys.minX;
}
_f512.prototype._f56=function()
{
return _f11._f514[this._f513].coordSys.minY;
}
_f512.prototype._f57=function()
{
return _f11._f514[this._f513].coordSys.maxX;
}
_f512.prototype._f58=function()
{
return _f11._f514[this._f513].coordSys.maxY;
}
_f512.prototype._f430=function()
{
return _f11._f514[this._f513].mapCache;
}
_f512.prototype.getSrid=function()
{
return _f11._f514[this._f513].coordSys.srid;
}
_f512.prototype._f520=function()
{
return _f11._f514[this._f513].coordSys.type;
}
_f512.prototype._f521=function()
{
return _f11._f514[this._f513].coordSys.distConvFactor;
}
_f512.prototype.getMaxZoomLevel=function()
{
return _f11._f514[this._f513].zoomLevels.length;
}
_f512.prototype._f522=function(x1)
{
return _f11._f514[this._f513].zoomLevels[x1].tileWidth;
}
_f512.prototype._f523=function(x2)
{
return _f11._f514[this._f513].zoomLevels[x2].tileHeight;
}
_f512.prototype._f524=function(x3)
{
return _f11._f514[this._f513].zoomLevels[x3].tileImageWidth;
}
_f512.prototype._f525=function(x4)
{
return _f11._f514[this._f513].zoomLevels[x4].tileImageHeight;
}
_f512.prototype._f526=function(x5)
{
return _f11._f514[this._f513].zoomLevels[x5]._f527;
}
_f512.prototype._f528=function(x6,x7,x8,x9)
{
this._f159=x6;
this._f275=x7;
this._f158=x8;
this._f274=x9;
}
function MVEvent(){}
MVEvent.START="start";
MVEvent.FINISH="finish";
MVEvent.DRAG="drag";
MVEvent.NEW_SHAPE_POINT="new_shape_point";
MVEvent.MODIFY="modify";
MVEvent.MOUSE_CLICK="mouse_click";
MVEvent.MOUSE_RIGHT_CLICK="mouse_right_click";
MVEvent.MOUSE_OVER="mouse_over";
MVEvent.MOUSE_OUT="mouse_out";
MVEvent.COLLAPSE="collapse";
MVEvent.AFTER_COLLAPSE="after_collapse";
MVEvent.RESTORE="restore";
MVEvent.AFTER_RESTORE="after_restore";
MVEvent.DRAG_START="drag_start";
MVEvent.DRAG_END="drag_end";
MVEvent.RECENTER="recenter";
MVEvent.ZOOM_LEVEL_CHANGE="zoom_level_change";
MVEvent.BEFORE_ZOOM_LEVEL_CHANGE="before_zoom_level_change";
MVEvent.MOUSE_DOUBLE_CLICK="mouse_double_click";
MVEvent.INITIALIZE="initialize";
MVEvent.MOUSE_MOVE="mouse_move";
MVEvent.CLEAR="clear"
MVEvent.BEFORE_REFRESH="before_refresh";
MVEvent.AFTER_REFRESH="after_refresh";
MVEvent.BUTTON_DOWN="button_down";
MVEvent.BUTTON_UP="button_up";
MVEvent.INIT="init";
MVEvent.MARQUEEZOOM_FINISH="marqueezoom_finish";
function _f368(x0,x1,x2,x3)
{
this._f369=x0.zoomLevels[x3].tileWidth;
this._f370=x0.zoomLevels[x3].tileHeight;this._f371=Math.floor((x1-x0.coordSys.minX)/this._f369);
this._f372=Math.floor((x2-x0.coordSys.minY)/this._f370);
this._f373=x3;
this.minX=this._f371*this._f369+x0.coordSys.minX;
this.minY=this._f372*this._f370+x0.coordSys.minY;}
_f368.prototype._f374=function(x0,x1)
{
this._f371=this._f371+x0;
this._f372=this._f372+x1;
}
function MVMapTileLayer(x0,x1)
{
this._f108=x0;
this._f409="";
this._f505="";
if(x1&&x1!="")
{
this._f409=x1;
this._f505=x1;
if(MVUtil._f5(x1,'/'))
{
this._f409=x1.substring(0,x1.length-1);
this._f505=x1.substring(0,x1.length-1);
}
}
this.parent="";
this._f412=false;
this.visible=true;
this._f410="PNG";
this.minVisibleLevel=null;
this.maxVisibleLevel=null;
this._f437=null;
this.layerControl=null;
this._f506=null;
this._f439=null;
this._f507=true;
}
MVMapTileLayer.prototype.getType=function()
{
return "MVMapTileLayer";
}
MVMapTileLayer.prototype.setCombinable=function(x0)
{
if(x0)
this._f507=false;
else
 this._f507=true;
}
MVMapTileLayer.prototype.setTransparent=function(x1)
{
this._f412=x1;
}
MVMapTileLayer.prototype.setVisible=function(x2)
{
if(this.parent)
{
if(this.layerControl)
{
this.visible=x2;
this.layerControl.setVisible(x2);
}
else if(this.isExtAPITileLayer())
{
this.visible=x2;
if(this.layerDiv)
{
if(x2)
{
this.layerDiv.style.visibility="visible";
var x3=this.parent.getCenter();
this.parent._f508(this,x3.getPointX(),x3.getPointY(),this.parent._f21);
}
else
 this.layerDiv.style.visibility="hidden";
}
}
else if(this._f506)
{
var x4=this._f506.isVisible();
this.visible=x2;
var x5=this._f506.isVisible();
if(x4!=x5)
this._f506.setVisible(x5);
var x6=this._f506.getTransparent();
if(this._f506.layerControl)
this._f506.layerControl._f412=x6;
if(x5)
this._f506.refresh(true);
}
}
else
 this.visible=x2;
}
MVMapTileLayer.prototype.isVisible=function()
{
if(!this._f439)
return this.visible;
else
 {
for(var x7=0;x7<this._f439.length;x7++)
{
if(this._f439[x7].visible)
return true;
}
return false;
}
}
MVMapTileLayer.prototype.setVisibleZoomLevelRange=function(x8,x9)
{
this.minVisibleLevel=x8;
this.maxVisibleLevel=x9;
}
MVMapTileLayer.prototype.refresh=function(x10)
{
if(!x10)
this._f437=Math.round(Math.random()*10000000000);
if(this.parent)
{
if(this.layerControl&&!this._f506)
{
if(this.layerControl.zoomControl)
this.layerControl.zoomControl.removeCloneTiles();
this.layerControl._f432(this.parent._f109(),this.parent._f110(),
this.parent._f95,this.parent._f96);
}
}
}
MVMapTileLayer.prototype._f430=function()
{
if(this._f439)
{
var x11=null;
for(var x12=0;x12<this._f439.length;x12++)
{
if(this._f439[x12].visible
&&(this._f439[x12].minVisibleLevel==null
||this._f439[x12].minVisibleLevel<=this.parent._f21)
&&(this._f439[x12].maxVisibleLevel==null
||this._f439[x12].maxVisibleLevel>=this.parent._f21))
{
if(x11)
x11+=";"+this._f439[x12]._f108;
else
 x11=this._f439[x12]._f108;
}
}
return x11;
}
else
 return this._f108;
}
MVMapTileLayer.prototype.clone=function()
{
var x13=new MVMapTileLayer(this._f108,this._f409);
x13._f412=this._f412;
x13._f410=this._f410;
x13.minVisibleLevel=this.minVisibleLevel;
x13.maxVisibleLevel=this.maxVisibleLevel;
x13._f507=this._f507;
if(this.config)
x13.config=this.config;
return x13;
}
MVMapTileLayer.prototype.destroy=function()
{
if(this.layerControl)
{
this.layerControl.destroy();
this.layerControl=null;
}
else if(this.layerDiv)
{
MVUtil._f191(this.layerDiv,this.parent._f7);
this.layerDiv.parentNode.removeChild(this.layerDiv);
this.layerDiv=null;
if(this._f219)
this._f219();
}
}
MVMapTileLayer.prototype._f435=function()
{
if(this.layerControl)
this.layerControl._f435();
}
MVMapTileLayer.prototype.isExtAPITileLayer=function()
{
return this.getType()=="MVExternalAPIMapTileLayer";
}
MVMapTileLayer.prototype.getTransparent=function()
{
if(this._f439)
{
for(var x14=0;x14<this._f439.length;x14++)
{
if(!this._f439[x14]._f412&&this._f439[x14].isVisble)
{
this._f412=false;
return false;
}
}
this._f412=true;
}
return this._f412;
}
MVBaseMap=MVMapTileLayer;
function MVCustomMapTileLayer(x0,x1)
{
var x2=x0.dataSource?x0.dataSource+"."+x0.mapTileLayer:x0.mapTileLayer;
MVMapView.addMapTileLayerDefinition(x0);
this.layerDefinition=x0;
this._f502=MVMapTileLayer;
this._f502(x2);
this.tileURLProvider=x1;
}
MVCustomMapTileLayer.prototype=new MVMapTileLayer;
MVCustomMapTileLayer.prototype.getType=function()
{
return "MVCustomMapTileLayer";
}
MVCustomMapTileLayer.prototype.clone=function()
{
return new MVCustomMapTileLayer(this.layerDefinition,this.tileURLProvider);
}
function _f406(x0,x1)
{
this.basemap=x1;
this.mapConfig=x1.config;
this.visible=true;
this._f407;
this._f408;
this._f409="";
this.parent=x0;
if(x1._f409)
{
this._f409=x1._f409;
}
else
 {
MVi18n._f6("MVMapTilesControl.constructor","MAPVIEWER-05504",null,x0._f7);
return ;
}
this._f108=x1._f108;
this._f410=this.parent.msi._f411(this._f108);
this._f412=x1._f412;
this._f413;
this._f414;
this._f415;
this._f416;
this._f417;
this._f418;
this.tileWidth;
this.tileHeight;
this._f419=true;
this._f420=[];
this._f421=0;
this._f422=0;
this._f423;
this._f424;
this._f28;
this._f29;
this._f425=null;
this._f426=document.createElement("div");
this._f426.style.position="absolute";
this.zIndex=x1.zIndex;this.parent.div.appendChild(this._f426);
this._f427=x0._f428.src;
this.visible=x1.isVisible();
this.minVisibleLevel=x1.minVisibleLevel;
this.maxVisibleLevel=x1.maxVisibleLevel;
this.zoomControl=new MVZoomControl(x0,this);
this.tileNumInCSBoun=null;
this.coverPixel=null;
}
_f406.prototype._f429=function()
{
return this._f409;
}
_f406.prototype._f430=function()
{
return this._f108;
}
_f406.prototype._f431=function()
{
return this._f426;
}
_f406.prototype.setVisible=function(x0)
{
this.visible=x0;
if(!this.visible)
this._f426.style.visibility="hidden";
else
 {
this._f426.style.visibility="visible";
if(this.parent._f93)
this._f432(this.parent._f109(),this.parent._f110(),this.parent._f95,this.parent._f96);
}
}
_f406.prototype.isVisible=function()
{
return this.visible;
}
_f406.prototype._f433=function(x1,x2)
{
if(this.parent.wraparound)
{
if(x2<0||x2>=(this.mapConfig.coordSys.maxY-this.mapConfig.coordSys.minY)/this._f425._f370)
return this._f427;
else if(x1<0)
{
while(true)
{
x1=parseInt(Math.ceil((this.mapConfig.coordSys.maxX-this.mapConfig.coordSys.minX)/this._f425._f369))+x1;
if(x1>=0)
break;
}
}
else if(x1>=(this.mapConfig.coordSys.maxX-this.mapConfig.coordSys.minX)/this._f425._f369)
{
while(true)
{
x1=x1-parseInt(Math.ceil((this.mapConfig.coordSys.maxX-this.mapConfig.coordSys.minX)/this._f425._f369));
if(x1<(this.mapConfig.coordSys.maxX-this.mapConfig.coordSys.minX)/this._f425._f369)
break;
}
}
}
else if(x1<0||x1>=(this.mapConfig.coordSys.maxX-this.mapConfig.coordSys.minX)/this._f425._f369||
x2<0||x2>=(this.mapConfig.coordSys.maxY-this.mapConfig.coordSys.minY)/this._f425._f370)
return this._f427;
var x3=this._f425._f373;
if(this.basemap.tileURLProvider)
{
var x4=this.basemap.tileURLProvider(
this.mapConfig.coordSys.minX+x1*this._f425._f369,
this.mapConfig.coordSys.minY+x2*this._f425._f370,
this._f425._f369,this._f425._f370,x3);
if(!x4)
return this._f427;
else
 return x4;
}
else
 return String(this._f409+"?request=gettile&format="+this._f410+"&zoomlevel="+x3+"&mapcache="+this.basemap._f430()+"&mx="+x1+"&my="+x2);
}
_f406.prototype._f432=function(x5,x6,x7,x8)
{
if(this.checkVisibility())
return;
this.tileWidth=this.mapConfig.zoomLevels[this.parent._f21].tileImageWidth;
this.tileHeight=this.mapConfig.zoomLevels[this.parent._f21].tileImageHeight;
this._f413=x7;this._f414=x8;
this._f407=Math.ceil(this.tileWidth/2);
this._f408=Math.ceil(this.tileHeight/2);
this._f423=x5;this._f424=x6;
this._f28=x7;this._f29=x8;
this._f417=Math.ceil((x5+this._f407*2)/this.tileWidth);
this._f425=null;
this._f425=new _f368(this.mapConfig,x7,x8,this.parent._f21);
this.tileNumInCSBoun=Math.ceil((this.mapConfig.coordSys.maxX-this.mapConfig.coordSys.minX)/this._f425._f369);
this.parent._f76=this.tileWidth/this._f425._f369;this.parent._f78=this.tileHeight/this._f425._f370;
this.coverPixel=(this._f425._f369*this.tileNumInCSBoun-(this.mapConfig.coordSys.maxX-this.mapConfig.coordSys.minX))*this.parent._f76;
if(this.parent.wraparound)
{
var x9=(this._f417/this.tileNumInCSBoun)*this.coverPixel;
var x10=(this.tileWidth/2);
this._f417=this._f417+Math.ceil(x9/x10)+1;
}
this._f418=Math.ceil((x6+this._f408*2)/this.tileHeight);
this._f421=0;this._f422=0;
this.origParLeft=this.parent._f140;this.origParTop=this.parent._f141;
this._f434();
var x11=Math.floor(this._f417/2);
var x12=Math.floor(this._f418/2);
var x13=this._f423/2.0-(x7-this._f425.minX)*this.parent._f76;
var x14=this._f424/2.0+(x8-this._f425.minY)*this.parent._f78-this.tileHeight;
while(true)
{
var x15=x13-x11*this.tileWidth;
if(x15>0)
{
x11++;
continue;
}
var x16=x14-x12*this.tileHeight;
if(x16>0)
{
x12++;
continue;
}
var x17=x13+(this._f417-x11)*this.tileWidth-this._f423;
if(x17<0)
{
x11--;
continue;
}
var x18=x14+(this._f418-x12)*this.tileHeight-this._f424;
if(x18<0)
{
x12--;
continue;
}
var x19=x17+x15,x20=x18+x16;
if(x19>this.tileWidth)
{
x11++
continue;
}
else if(x19<(-1)*this.tileWidth)
{
x11--;
continue;
}
if(x20>this.tileHeight)
{
x12++;
continue;
}
if(x20<(-1)*this.tileHeight)
{
x12--;
continue;
}
break;
}
this._f415=x13-x11*this.tileWidth-this.parent._f140;
this._f416=x14-x12*this.tileHeight-this.parent._f141;
MVUtil._f74(this._f426,this._f415,this._f416);
this._f170(x11,x12,
this.tileWidth,this.tileHeight,
x11*this.tileWidth,x12*this.tileHeight);
this._f425._f374(-1,1);
var x21=Math.max(x11,this._f417-x11);
var x22=Math.max(x12,this._f418-x12);
for(_f192=1;_f192<=Math.max(x21,x22);_f192++)
{
for(xx=x11-_f192,yy=x12-_f192;
xx<=x11+_f192;xx++)
{
if(!(xx<0||xx>=this._f417||yy<0||yy>=this._f418))
{
this._f170(xx,yy,
this.tileWidth,this.tileHeight,
xx*this.tileWidth,yy*this.tileHeight);
}
this._f425._f374(1,0);
}
this._f425._f374(-(_f192*2+1),-1);
for(xx=x11-_f192,yy=x12-_f192+1;
yy<=x12+_f192-1;yy++)
{
if(!(xx<0||xx>=this._f417||yy<0||yy>=this._f418))
{
this._f170(xx,yy,
this.tileWidth,this.tileHeight,
xx*this.tileWidth,yy*this.tileHeight);
}
this._f425._f374(0,-1);
}
for(xx=x11-_f192,yy=x12+_f192;
xx<=x11+_f192;xx++)
{
if(!(xx<0||xx>=this._f417||yy<0||yy>=this._f418))
{
this._f170(xx,yy,
this.tileWidth,this.tileHeight,
xx*this.tileWidth,yy*this.tileHeight);
}
this._f425._f374(1,0);
}
this._f425._f374(-1,1);
for(xx=x11+_f192,yy=x12+_f192-1;
yy>=x12-_f192+1;yy--)
{
if(!(xx<0||xx>=this._f417||yy<0||yy>=this._f418))
{
this._f170(xx,yy,
this.tileWidth,this.tileHeight,
xx*this.tileWidth,yy*this.tileHeight);
}
this._f425._f374(0,1);
}
this._f425._f374(-(_f192*2+1),1);
}
this._f425._f374(_f192,-(_f192));
this._f425._f374(-x11+Math.floor(this._f417/2),
x12-Math.floor(this._f418/2));
}
_f406.prototype._f435=function()
{
if(this.checkVisibility())
return;
var x23=this.parent._f140+this._f421+this._f415;
var x24=this.parent._f141+this._f422+this._f416;
var x25=0,x26=0;
while(true)
{
var x27=x23-x25*this.tileWidth;
if(x27>0)
{
x25++;
continue;
}
var x28=x24-x26*this.tileHeight;
if(x28>0)
{
x26++;
continue;
}
var x29=x23+(this._f417-x25)*this.tileWidth-this._f423;
if(x29<0)
{
x25--;
continue;
}
var x30=x24+(this._f418-x26)*this.tileHeight-this._f424;
if(x30<0)
{
x26--;
continue;
}
var x31=x29+x27,x32=x30+x28;
if(x31>this.tileWidth)
{
x25++
continue;
}
else if(x31<(-1)*this.tileWidth)
{
x25--;
continue;
}
if(x32>this.tileHeight)
{
x26++;
continue;
}
if(x32<(-1)*this.tileHeight)
{
x26--;
continue;
}
break;
}
if(x25==0&&x26==0)
return ;
var x33=Math.floor(this._f417/2);
var x34=Math.floor(this._f418/2);
var x35=this._f425._f371-x33;
var x36=this._f425._f372+x34;
var x37;
if(x25!=0)
{
for(x37=0;(x25>0?x37<x25:x37>x25);(x25>0?x37++:x37--))
{
var x38;
var x39;
var x40;
if(x25>0)
x39=this._f421-(x37+1)*this.tileWidth;
else
 x39=this._f421+(this._f417-x37)*this.tileWidth;
if(x25>0)
{
x38=this._f420[this._f420.length-1];
this._f420.pop();
for(var x41=0;x41<x38.length;x41++)
{
this._f436(x38[x41],null);
if(x26==0||
(x26>0&&x41<x38.length-x26)||
(x26<0&&x41>=-x26))
{
this._f436(x38[x41],this._f433(x35-x37-1,x36-x41));
}if(this.parent.wraparound)
{
x38[x41].style.zIndex=x35-x37-1;
x40=this._f420[0][x41].style.zIndex;
if(x40%this.tileNumInCSBoun==0)
x38[x41].style.left=MVUtil._f32(parseInt(this._f420[0][x41].style.left)-this.tileWidth+this.coverPixel);
else
 x38[x41].style.left=MVUtil._f32(parseInt(this._f420[0][x41].style.left)-this.tileWidth);
}
else
 x38[x41].style.left=MVUtil._f32(x39);
}
this._f420.unshift(x38);
}
else
 {
x38=this._f420[0];
this._f420.shift();
for(var x41=0;x41<x38.length;x41++)
{
this._f436(x38[x41],null);
if(x26==0||
(x26>0&&x41<x38.length-x26)||
(x26<0&&x41>=-x26))
{
this._f436(x38[x41],this._f433(x35+this._f417-x37,x36-x41));
}
if(this.parent.wraparound)
{
x38[x41].style.zIndex=x35+this._f417-x37;
x40=this._f420[this._f420.length-1][x41].style.zIndex;
if(x40%this.tileNumInCSBoun==(this.tileNumInCSBoun-1)||x40%this.tileNumInCSBoun==-1)
x38[x41].style.left=MVUtil._f32(parseInt(this._f420[this._f420.length-1][x41].style.left)+this.tileWidth-this.coverPixel);
else
 x38[x41].style.left=MVUtil._f32(parseInt(this._f420[this._f420.length-1][x41].style.left)+this.tileWidth);
}
else
 x38[x41].style.left=MVUtil._f32(x39);
}
this._f420.push(x38);
}
}
}
if(x26!=0)
{
var x42;
for(x42=0;(x26>0?x42<x26:x42>x26);(x26>0?x42++:x42--))
{
var x43;
if(x26>0)
x43=this._f422-(x42+1)*this.tileHeight;
else
 x43=this._f422+(this._f418-x42)*this.tileHeight;
for(x37=0;x37<this._f420.length;x37++)
{
var x38=this._f420[x37];
var x44;
if(x26>0)
{
x44=x38[x38.length-1];
this._f436(x44,null);
x38.pop();
x38.unshift(x44);
x44.style.top=MVUtil._f32(x43);
this._f436(x44,this._f433(x35-x25+x37,x36+x42+1));
}
else
 {
x44=x38[0];
this._f436(x44,null);
x38.shift();
x38.push(x44);
x44.style.top=MVUtil._f32(x43);
this._f436(x44,this._f433(x35-x25+x37,x36-this._f418+x42));
}
}
}
}
if(this.parent.wraparound)
this._f421=parseInt(this._f420[0][0].style.left);
else
 this._f421=this._f421-x25*this.tileWidth;
this._f422=this._f422-x26*this.tileHeight;
this._f425._f374(-x25,x26);
}
_f406.prototype._f170=function(x45,x46,x47,x48,x49,x50)
{
var x51=this._f433(this._f425._f371,this._f425._f372);
var x52=this._f420[x45][x46];
if(x52==null)
{
x52=(_f11._f69()&&this._f412)?
document.createElement('div'):document.createElement('img');
this._f426.appendChild(x52);
this._f420[x45][x46]=x52;
}
_f11.c=0;
if(_f11._f70=="IF"&&!this._f412)
{
x52.galleryImg="no"}
else
 {
x52.style.MozUserSelect="none";
}
MVUtil._f146(x52);
x52.style.position="absolute";
if(this.parent.wraparound)
{
x52.style.zIndex=this._f425._f371;
if(this._f425._f371<0)
{
x52.style.left=MVUtil._f32(x49-this.coverPixel*Math.floor(x52.style.zIndex/this.tileNumInCSBoun));
}
else if(this._f425._f371>=(this.mapConfig.coordSys.maxX-this.mapConfig.coordSys.minX)/this._f425._f369)
{
x52.style.left=MVUtil._f32(x49-this.coverPixel*Math.floor(x52.style.zIndex/this.tileNumInCSBoun));
}
else
 {
x52.style.left=MVUtil._f32(x49);
}
}
else
 x52.style.left=MVUtil._f32(x49);
x52.style.top=MVUtil._f32(x50);
if(_f11._f70=="IF")
{
x52.style.width=MVUtil._f32(x47);
x52.style.height=MVUtil._f32(x48);
}
if((this.zoomControl)&&(this.zoomControl.zooming))
x52.style.display='none';
var x53=this;
x52.imgLoadFinished=false;
x52.onload=function()
{
x52.imgLoadFinished=true;
x52.onload=null;
x52.style.display='';
};
this._f436(x52,x51);
return x52;
}
_f406.prototype._f434=function()
{
if(!(this._f426.zooming))
{
this._f426.parentNode.removeChild(this._f426);
MVUtil._f92(this._f426);
}
this._f426=document.createElement("div");
this._f426.style.position="absolute";
this.parent.div.appendChild(this._f426);
this._f426.style.zIndex=this.zIndex;
this._f420=new Array();
for(var x54=0;x54<this._f417;x54++)
{
var x55=new Array();
for(var x56=0;x56<this._f418;x56++)
{
var x57;
if(_f11._f69())
{
x57=MVUtil._f170(null,this._f412);
}
else
 {
x57=MVUtil._f170(this._f427,this._f412);
}
this._f426.appendChild(x57);
x55.push(x57);
}
this._f420.push(x55);
}
}
_f406.prototype.destroy=function()
{
while(this._f420.length>0)
{
var x58=this._f420.pop();
while(x58.length>0)
{
if(_f11._f69()&&this._f412)
{
MVUtil._f92(x58.pop());
}
else
 {
var x59=x58.pop();
if(x59!=null)
{
this._f426.removeChild(x59);
delete x59;
}
}}}
this._f426.parentNode.removeChild(this._f426);
this._f426=null;
this.zIndex=null;
}
_f406.prototype.getXMLForPrint=function()
{
var x60="<map_cache_theme map_cache_name=\""+this._f108+"\" snap_to_cache_scale=\"true\" ";
if(this._f409!="")
{
var x61=this._f409.substring(0,this._f409.lastIndexOf("/"));
if(x61.indexOf(this.parent._f4)<0)
{
x61+="/";
x60+="mapviewer_url=\""+x61+"\"";
}
}
return x60+"/>";
}
_f406.prototype._f436=function(x62,x63)
{
if(x63)
{
x62.imgSrc=x63;
if(this.basemap._f437)
{
x63=x63+"&reload="+this.basemap._f437;
}
}
if(_f11._f69()&&this._f412)
{
if(x63)
x62.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(src='"+
x63+"', sizingMethod='image');";
else
 x62.style.filter="";
}
else
 {
if(x63)
x62.src=x63;
else
 x62.src=this._f427;
}
}
_f406.prototype._f438=function()
{
for(var x64=0;x64<this._f417;x64++)
for(var x65=0;x65<this._f418;x65++)
{
var x66=this._f420[x64][x65];
this._f436(x66,x66.imgSrc);
}
}
_f406.prototype.checkVisibility=function(x67)
{
if(!this.basemap._f439)
{
if(!this.visible)
return true;
if(this.minVisibleLevel!=null&&this.minVisibleLevel>this.parent._f21)
{
if(this._f426)
this._f426.style.visibility="hidden";
return true;
}
if(this.maxVisibleLevel!=null&&this.maxVisibleLevel<this.parent._f21)
{
if(this._f426)
this._f426.style.visibility="hidden";
return true;
}
}
else
 {
for(var x68=0;x68<this.basemap._f439.length;x68++)
{
if(this.basemap._f439[x68].visible&&
(this.basemap._f439[x68].minVisibleLevel==null
||this.basemap._f439[x68].minVisibleLevel<=this.parent._f21)
&&(this.basemap._f439[x68].maxVisibleLevel==null
||this.basemap._f439[x68].maxVisibleLevel>=this.parent._f21))
return false;
}
if(this._f426)
this._f426.style.visibility="hidden";
return true;
}
}
_f406.zIndexCount=1;
function MVArrayParameter(x0,x1,x2)
{
this.array=x0;
this.elementType=x1;
if(x2==undefined||!x2)
{
if(x1==MVArrayParameter.STRING_ARRAY)
x2='MV_STRINGLIST';
else
 x2='MV_NUMBERLIST';
}
this.sqlType=x2;
}
function ArrayParameter(x0,x1,x2)
{
this.array=x0;
this.elementType=x1;
this.sqlType=x2;
}
MVArrayParameter.STRING_ARRAY='sarray';
MVArrayParameter.NUMBER_ARRAY='narray';
function MVThemeBasedFOI(x0,x1,x2)
{
if(!x1)
{
MVi18n._f6("MVThemeBasedFOI.constructor","MAPVIEWER-05519","theme");
return ;
}
this.name=x0;
this.parent=null;
this._f3=x2;
this._f59=-1;
this._f60=100;
this._f185=true;
this.minX=0;
this.minY=0;
this.maxX=0;
this.maxY=0;
this._f98=new Array();
this._f97=null;
this._f134=null;
this._f178=null;
this._f181=null;
this._f182=null;
this._f187=null;
this._f154=null;
this._f43=null;
this._f51=2;
this._f52=2;
this._f160=true;
this._f179=true;
this._f73=true;
this._f44=null;
this._f821=null;
this._f125=null;
this._f302=null;
this._f105=null;
this._f71=-1;
this._f104=-1;
this._f809=false;
this._f810=false;
this._f112=new Array();
this._f111=null;
this._f812=true;
this.visible=true;
this._f172=true;
this.cursorStyle="pointer";
this._f102=false;
this._f103=null;
this._f72=true;
this.renderLabels=false;
this._f183=false;
this._f113=true;
this._f303=true;
this._f304=null;
this._f243=null;
this._f41=false;
if(x1!=null)
{
this._f43=x1;
if(x1.indexOf("</jdbc_query>")>=0)
this._f41=true;
}
this._f122=null;
this._f13=null;
this._f138=0;
this._f139=0;
this._f186=false;
this._f189=0;
this._f168=new Array();
this._f101=null;
this._f130=null;
this.queryParameterArray=null;
this._f67=null;
this.bgColor=null;
this._f114=false;
this._f115=-1;
this._f116=-1;
this._f117=false;
this._f118="N";
this.reuseIENode=false;
this.langCode=null;
}
MVThemeBasedFOI.prototype.setLayoutCustomizer=function(x0)
{
this._f154=x0;
this.reuseIENode=false;
}
MVThemeBasedFOI.prototype.enableHighlight=function(x1)
{
if(!x1)
this.deHighlight();
this._f186=x1;
}
MVThemeBasedFOI.prototype.setHighlightMode=function(x2)
{
switch(x2)
{
case "single":
this._f189=-1;
break;
case "multiple":
this._f189=0;
break;
default:
MVi18n._f6("MVThemeBasedFOI.setHighlightMode","MAPVIEWER-05519","mode",
this.parent?this.parent._f7:null);
}
}
MVThemeBasedFOI.prototype.setHighlightStyle=function(x3)
{
var x4=false;
if(x3!=null)
{
if(typeof(x3)=="object"&&x3.getXMLString!=null)
this._f101=encodeURIComponent(x3.getXMLString());
else if(typeof(x3)=="string")
this._f101=encodeURIComponent(x3);
else
 x4=true;
}
else
 x4=true;
if(x4)
MVi18n._f6("MVThemeBasedFOI.setHighlightStyle","MAPVIEWER-05519","highliteStyle",
this.parent?this.parent._f7:null);
this.reuseIENode=false;
}
MVThemeBasedFOI.prototype.setHighlightMarkerImage=function(x5,x6,x7)
{
this._f130=null;
var x8=true;
if(!x5||typeof(x5)!="string")
x8=false;
if(x8)
this._f130=new _f854(x5,x6,x7);
else
 MVi18n._f6("MVThemeBasedFOI.setHighlightMarkerImage","MAPVIEWER-05519",null,
this.parent?this.parent._f7:null);
this.reuseIENode=false;
}
MVThemeBasedFOI.prototype.highlight=function(x9)
{
if(!this._f186)
{
MVi18n._f6("MVThemeBasedFOI.highlight","MAPVIEWER-05524",null,
this.parent?this.parent._f7:null);
return;
}
var x10=false;
if(x9==null)
{
x10=true;
}
else if(typeof(x9)!="object"||(len=x9.length)==null||x9.length<1)
{
MVi18n._f6("MVThemeBasedFOI.highlight","MAPVIEWER-05519","foiIdArray",
this.parent?this.parent._f7:null);
return;
}
var x11=this._f168;
var x12=this._f125.foiarray;
var x13=x12.length;
for(var x14=0;x14<x13;++x14)
{
var x15=x12[x14];
var x16=x10;
if(x15._f153)
continue;
else {
for(var x17=0;x17<x11.length;x17++)
if(x15==x11[x17])
{
x15._f153=true;
x15=null;
break;
}
}
if(x15!=null&&x15.id!=null)
{
if(!x16)
{
for(var x17=0;x17<x9.length;x17++)
if(x15.id==x9[x17])
{
x16=true;
break;
}
}
if(x15.nodeId!=null&&x16)
{
x11.push(x15);
x15._f153=true;
this._f13.highlightFOI(this._f13,x15);
}
}
}
}
MVThemeBasedFOI.prototype.deHighlight=function(x18)
{
if(!this._f186)
{
MVi18n._f6("MVThemeBasedFOI.deHighlight","MAPVIEWER-05524",null,
this.parent?this.parent._f7:null);
return;
}
var x19=this._f168;
var x20=x19.length;
if(x20<1)
return;
var x21=null;
if(x18!=null&&(typeof(x18)!="object"||(x21=x18.length)==null))
{
MVi18n._f6("MVThemeBasedFOI.deHighlight","MAPVIEWER-05519","foiIdArray",
this.parent?this.parent._f7:null);
return;
}
if(x18!=null&&x21<1)
return;
var x22=false;
if(x18==null){
x22=true;
}
for(var x23=x19.length;x23>=0;x23--)
{
var x24=x19[x23];
var x25=x22;
if(x24!=null&&x24.id!=null)
{
if(!x25)
{
for(var x26=0;x26<x18.length;x26++)
if(x24.id==x18[x26])
{
x25=true;
break;
}
}
if(x25)
{
x19[x23]=null;
x19.pop();
x24._f153=false;
if(x24!=null&&x24.node!=null&&x24.imgurl!=null)
this._f13.deHighlightFOI(this._f13,x24);
}
}
}
}
MVThemeBasedFOI.prototype.getHighlightedFOIData=function()
{
var x27=new Array();
if(this._f168!=null)
{
for(var x28=0;x28<this._f168.length;x28++)
if(this._f168[x28]!=null)
x27.push(this._f168[x28]);
}
return x27;
}
MVThemeBasedFOI.prototype.setQueryParameters=function()
{
if(arguments.length<=0)
return ;
this._f44="&paramnum="+arguments.length;
this.queryParameterArray=new Array(arguments.length);
for(var x29=0;x29<arguments.length;x29++)
{
if(arguments[x29]==null){
this._f44+="&param"+(x29+1)+"="+null;
continue;
}
if(typeof arguments[x29]=="object")
{
this._f44+=this.setObjectQueryParameters(arguments[x29],x29+1);
if(arguments[x29].gtype!=undefined)
this.queryParameterArray[x29]={type:"geometry",value:arguments[x29]};
else
 {
var x30=arguments[x29].elementType==MVArrayParameter.STRING_ARRAY?"String":"double";
this.queryParameterArray[x29]={elemtype:x30,value:arguments[x29].array,type:arguments[x29].sqlType};
}
}
else
 {
this._f44+="&param"+(x29+1)+"="+encodeURIComponent(arguments[x29]);
this.queryParameterArray[x29]={value:arguments[x29]};
}
}
return this._f44;
}
MVThemeBasedFOI.prototype.setObjectQueryParameters=function(x31,x32)
{
if(!x31)
return "";
if(x31.gtype!=undefined)
{
x31=x31.densify();
return "&param"+x32+"="+encodeURI(x31)+"&paramtype"+x32+"=geometry";
}
else
 {
var x33="";
if(x31.array)
{
if(x31.array.substring!=undefined)
x33=x31.array;
else
 {
this.escapeQuote(x31.array);
for(var x34=0;x34<x31.array.length;x34++)
{
if(x34>0)
x33+=",";
x33+="\""+x31.array[x34]+"\"";
}
}
}
return "&param"+x32+"="+encodeURIComponent(x33)+"&paramtype"+x32+"="+x31.elementType+
"&sqltype"+x32+"="+x31.sqlType;
}
}
MVThemeBasedFOI.prototype.getQueryParameters=function()
{
return this._f44;
}
MVThemeBasedFOI.prototype.setMinVisibleZoomLevel=function(x35)
{
this._f59=x35;
}
MVThemeBasedFOI.prototype.setMaxVisibleZoomLevel=function(x36)
{
this._f60=x36;
}
MVThemeBasedFOI.prototype.setBringToTopOnMouseOver=function(x37)
{
this._f185=x37;
}
MVThemeBasedFOI.prototype.setMaxWholeImageLevel=function(x38)
{
this._f71=x38;
this._f114=false;
}
MVThemeBasedFOI.prototype.setMinClickableZoomLevel=function(x39)
{
this._f104=x39;
}
MVThemeBasedFOI.prototype.enableInfoTip=function(x40)
{
this._f160=x40;
}
MVThemeBasedFOI.prototype.enableInfoWindow=function(x41)
{
this._f179=x41;
}
MVThemeBasedFOI.prototype.enableInfoWindowForMouseOver=function(x42)
{
this._f183=x42;
}
MVThemeBasedFOI.prototype.enableInfoWindow=function(x43)
{
this._f179=x43;
}
MVThemeBasedFOI.prototype.setClickable=function(x44)
{
this._f73=x44;
if(this._f13)
{
this._f13._f124();
if(this._f13._f34)
{
if(x44&&this.isVisible())
this._f13._f34.style.visibility="visible";
else if(!x44)
this._f13._f34.style.visibility="hidden";
}
}
}
MVThemeBasedFOI.prototype.addEventListener=function(x45,x46)
{
this.setEventListener(x45,x46);
}
MVThemeBasedFOI.prototype.setEventListener=function(x47,x48)
{
if(x47==MVEvent.BEFORE_REFRESH)
{
this._f97=x48;
}
else if(x47==MVEvent.AFTER_REFRESH)
{
this._f134=x48;
}
else if(x47==MVEvent.MOUSE_CLICK)
{
this._f178=x48;
}
else if(x47==MVEvent.MOUSE_RIGHT_CLICK)
{
this._f181=x48;
}
else if(x47==MVEvent.MOUSE_OVER)
{
this._f182=x48;
}
else if(x47==MVEvent.MOUSE_OUT)
{
this._f187=x48;
}
}
MVThemeBasedFOI.prototype.attachEventListener=function(x49,x50)
{
MVUtil.attachEventListener(this._f98,x49,x50)
}
MVThemeBasedFOI.prototype.detachEventListener=function(x51,x52)
{
MVUtil.detachEventListener(this._f98,x51,x52);
}
MVThemeBasedFOI.prototype.setQueryWindowMultiplier=function(x53)
{
this._f51=x53;
this._f52=x53;
}
MVThemeBasedFOI.prototype.getQueryWindow=function()
{
if(!this.parent)
{
MVi18n._f6("MVThemeBasedFOI.getQueryWindow","MAPVIEWER-05525",this.name,
this.parent?this.parent._f7:null);
return ;
}
var x54=MVSdoGeometry.createRectangle(this.minX,this.minY,this.maxX,this.maxY,this.parent.srid);
return x54;
}
MVThemeBasedFOI.prototype.getLayerMBR=MVThemeBasedFOI.prototype.getQueryWindow;
MVThemeBasedFOI.prototype.getMBR=function()
{
if(this._f122)
return MVSdoGeometry.createRectangle(this._f122[0],this._f122[1],this._f122[2],this._f122[3],this.parent.srid);
else
 return null;
}
MVThemeBasedFOI.prototype.setMarkerImage=function(x55,x56,x57)
{
if(x55)
this._f821=new _f854(x55,x56,x57);
else
 this._f821=null;
this.reuseIENode=false;
}
MVThemeBasedFOI.prototype.getThemeName=function()
{
return this._f43;
}
function _f854(x0,x1,x2)
{
this._f131=x0;
var x3=this;
if((!x1||isNaN(x1))||(!x2||isNaN(x2))){
var x4=function(x5,x6){
if(!x1||isNaN(x1))
x1=x5;
if(!x2||isNaN(x2))
x2=x6;
x3.width=x1;
x3.height=x2;
x3=null;
}
MVUtil.getImageSize(x0,x4);
}else{
this.width=x1;
this.height=x2;
}
}
MVThemeBasedFOI.prototype.getFOIMarker=function()
{
return this._f821;
}
MVThemeBasedFOI.prototype.setVisible=function(x58)
{
this.visible=x58;
if(this.parent&&this.parent._f80)
this.parent._f80._f291();
if(this._f13)
this._f13.setVisible(this.parent,x58);
}
MVThemeBasedFOI.prototype.setInfoWindowStyle=function(x59,x60)
{
this._f302=x59;
this._f243=x60;
}
MVThemeBasedFOI.prototype.isVisible=function()
{
if(this._f13)
{
return this._f13.isVisible();
}
else
 return this.visible;
}
MVThemeBasedFOI.prototype.refresh=function(x61)
{
if(!this.parent)
{
MVi18n._f6("MVThemeBasedFOI.refresh","MAPVIEWER-05525",this.name,
this.parent?this.parent._f7:null);
return ;
}
if(!this.parent._f93){
this.parent._f805(this,"refresh",true);
return;
}
this.parent._f126();
if(this._f13)
{
var x62=this._f13;
var x63=this.parent._f806(x62);
if(x61)
x61=true;
else
 x61=false;
x62._f79(this.parent,x63[0],x63[1],x63[2],x63[3],true,x61,!x61);
}
}
MVThemeBasedFOI.prototype.getFOIData=function()
{
if(this._f125==null||this._f125.foiarray==null)
{
return null;
}
else
 return this._f125.foiarray;
}
MVThemeBasedFOI.prototype.setNSDP=function(x64)
{
this._f105=x64;
}
MVThemeBasedFOI.prototype.setBoundingTheme=function(x65,x66)
{
this._f809=x65;
if(x66)
this._f810=true;
else
 this._f810=false;
}
MVThemeBasedFOI.prototype.zoomToTheme=function()
{
if(!this.parent)
{
MVi18n._f6("MVThemeBasedFOI.zoomToTheme","MAPVIEWER-05525",this.name,
this.parent?this.parent._f7:null);
return ;
}
this.refresh(true);
}
MVThemeBasedFOI.prototype.centerToTheme=function()
{
if(!this.parent)
{
MVi18n._f6("MVThemeBasedFOI.centerToTheme","MAPVIEWER-05525",this.name,
this.parent?this.parent._f7:null);
return ;
}
this.parent._f126();
if(this._f13)
{
var x67=this._f13;
var x68=this.parent._f806(x67);
x67._f79(this.parent,x68[0],x68[1],x68[2],x68[3],true,true,false,false,true);
}
}
MVThemeBasedFOI.prototype.addStyle=function(x69)
{
var x70=this._f112.concat(x69);
this._f112=null;
this._f112=x70;
}
MVThemeBasedFOI.prototype.deleteStyle=function(x71)
{
var x72=-1;
for(var x73=0;x73<this._f112.length;x73++)
{
var x74=this._f112[x73];
if(x74!=null&&x74==x71)
{
x72=x73;
break;
}
}
if(x72<0)
return;
var x75=0;
var x76=new Array(this._f112.length-1);
for(var x73=0;x73<this._f112.length;x73++)
{
if(x73!=x72)
{
x76[x75]=this._f112[x73];
x75++;
}
}
this._f112=null;
this._f112=x76;
}
MVThemeBasedFOI.prototype.deleteAllStyles=function()
{
for(var x77=0;x77<this._f112.length;x77++)
this._f112[x77]=null;
this._f112=new Array();
}
MVThemeBasedFOI.prototype.setRenderingStyle=function(x78)
{
this._f111=x78;
this.reuseIENode=false;
}
MVThemeBasedFOI.prototype.setAutoRefresh=function(x79)
{
this._f812=x79;
}
MVThemeBasedFOI.prototype.enableEventPropagation=function(x80)
{
this._f172=x80;
}
MVThemeBasedFOI.prototype.setMouseCursorStyle=function(x81)
{
this.cursorStyle=x81;
if(this._f13)
{
MVUtil._f164(this._f13.div,x81);
if(this._f125&&this._f125.foiarray&&this._f13._f165)
{
for(var x82=0;x82<this._f125.foiarray.length;x82++)
{
if(this._f125.foiarray[x82]&&this._f125.foiarray[x82].areaNode)
{
var x83=this._f125.foiarray[x82].areaNode;
if(x83 instanceof Array)
{
for(var x84=0;x84<x83.length;x84++)
MVUtil._f164(this._f125.foiarray[x82].areaNode[x84],x81);
}
else
 MVUtil._f164(this._f125.foiarray[x82].areaNode,x81);
}
}
}
}
}
MVThemeBasedFOI.prototype.setHighlightOnly=function(x85)
{
this._f102=x85;
}
MVThemeBasedFOI.prototype.setFilteringGeom=function(x86)
{
this._f103=x86;
}
MVThemeBasedFOI.prototype.getFilteringGeom=function()
{
return this._f103;
}
MVThemeBasedFOI.prototype.enableImageRendering=function(x87)
{
this._f72=x87;
}
MVThemeBasedFOI.prototype.enableLabels=function(x88)
{
this.renderLabels=x88;
}
MVThemeBasedFOI.prototype.enableImageCaching=function(x89)
{
this._f113=x89;
}
MVThemeBasedFOI.prototype.getLayerName=function()
{
return this.name;
}
MVThemeBasedFOI.prototype.setInfoWindowTitle=function(x90)
{
this._f304=x90;
}
MVThemeBasedFOI.prototype.setScreenOffset=function(x91,x92)
{
if(x91==undefined||isNaN(x91))
x91=0;
if(x92==undefined||isNaN(x92))
x92=0;
this._f138=parseInt(x91);
this._f139=parseInt(x92);
}
MVThemeBasedFOI.prototype.setNameAsInfoWindowTitle=function(x93)
{
this._f303=x93;
}
MVThemeBasedFOI.prototype.setImageFormat=function(x94,x95)
{
if(x94)
{
this._f67=x94.toUpperCase();
if(this._f67=="PNG8"&&x95)
this.bgColor=x95;
}
this.reuseIENode=false;
}
MVThemeBasedFOI.prototype.enableAutoWholeImage=function(x96,x97,x98)
{
if(x97!=undefined)
this._f115=x97;
if(x98!=undefined)
this._f116=x98;
this._f114=x96;
}
MVThemeBasedFOI.prototype.abort=function()
{
if(this._f13)
this._f13.abort();
}
MVThemeBasedFOI.prototype.enableMarkerSequence=function(x99,x100)
{
this._f117=x99;
if(x99)
{
if(x100)
this._f118=x100;
}
}
MVThemeBasedFOI.prototype.escapeQuote=function(x101)
{
if(x101&&x101.length>0&&x101.indexOf==undefined)
{
for(var x102=0;x102<x101.length;x102++)
{
if(x101[x102]&&x101[x102].length!=undefined)
x101[x102]=MVUtil._f49(x101[x102],"\"","\\\"");
}
}
}
MVThemeBasedFOI.prototype.getAllStyles=function()
{
return this._f112;
}
MVThemeBasedFOI.prototype.getStyleByName=function(x103)
{
if(x103==null||x103=="")
return;
for(var x104=0;x104<this._f112.length;x104++)
{
var x105=this._f112[x104];
if(x105!=null&&x105.name==x103)
{
return x105;
break;
}
}
}
MVThemeBasedFOI.prototype.isWholeImage=function()
{
if(this._f13)
return this._f13.isWholeImage();
else
 return false;
}
MVThemeBasedFOI.prototype.setLabelLanguageCode=function(x106)
{
this.langCode=x106;
}
MVThemeBasedFOI.prototype.zoomToFeatures=function(x107)
{
if(!x107||!x107.length||!this.parent)
return ;
var x108=null,x109=null,x110=null,x111=null;
for(var x112=0;x112<x107.length;x112++)
{
var x113=x107[x112].rw?x107[x112].rw:x107[x112].width;
var x114=x107[x112].rh?x107[x112].rh:x107[x112].height;
var x115=x107[x112].x;
var x116=x107[x112].y-x114/this.parent.getPixelsPerYUnit();
var x117=x107[x112].x+x113/this.parent.getPixelsPerXUnit();
var x118=x107[x112].y;
if(x108==null)
x108=x115;
else if(x108>x115)
x108=x115;
if(x109==null)
x109=x116;
else if(x109>x116)
x109=x116;
if(x110==null)
x110=x117;
else if(x110<x117)
x110=x117;
if(x111==null)
x111=x118;
else if(x111<x118)
x111=x118;
}
if(x108!=null&&x109!=null&&x110!=null&&x111!=null)
this.parent.zoomToRectangle(MVSdoGeometry.createRectangle(x108,x109,x110,x111));
}
MVThemeBasedFOI.prototype.reDraw=function(x119)
{
if(this._f13&&this._f125)
{
var x120=this;
var x121=function(x122){
if(x122.hidden)
x120._f13.clearFOINode(x122);
else if(x120._f13.parent.wraparound){
x120._f13.clearFOINode(x122);
x120._f13._f133(x122);
}
else{
var x123=new Object();
x123.shown=true;
x123.node=x122.node;
x123.labelNode=x122.labelNode;
x123.areaNode=x122.areaNode;
x120._f13._f133(x122,x123);
if(x123.shown==false){
x120._f13.clearFOINode(x123);
}
}
}
if(x119)
{
x121(x119);
}
else if(this._f125&&this._f125.foiarray)
{
var x124=this._f125.foiarray
for(var x125=0;x125<x124.length;x125++)
{
x121(x124[x125]);
}
}
}
}
function _f0(x0,x1)
{
this.parent=x0;
this._f2=false;
if(!x1._f3)
{
if(this.parent._f4)
{
if(MVUtil._f5(this.parent._f4,'/'))
x1._f3=this.parent._f4+'foi';
else
 x1._f3=this.parent._f4+'/foi';
}
else
 {
MVi18n._f6("MVThemeBasedFOIControl.constructor","MAPVIEWER-05504",null,x0._f7);
return ;
}
}
if(MVMapView._f8&&MVUtil._f9(x1._f3)!=MVUtil._f9(this.parent._f10))
x1._f3=_f11._f12()+"?rtarget="+x1._f3;
x1._f13=this;
this._f1=x1;
this._f14=0;
this._f15=0;
this.width=0;
this.height=0;
this.minX=0;
this.minY=0;
this.maxX=0;
this.maxY=0;
this._f16=0;
this._f17=0;
this._f18=0;
this._f19=0;
this._f20=true;
this._f21=0;
this._f22=true;
this._f23=false;
this._f24=0;
this._f25=0;
this._f26=0;
this._f27=0;
this._f28=0;
this._f29=0;
this._f30=false;
this._f31=1;this.div=document.createElement("div");
this.div.style.position="absolute";
this.div.style.left=MVUtil._f32(0);
this.div.style.top=MVUtil._f32(0);
this._f33=0;
if(!x1.visible)
this.div.style.visibility="hidden";
this.parent.div.appendChild(this.div);
this._f34=null;
this._f35=null;
this._f36=false;
this.themeId=null;
this.abortionSupported=true;
this._f37();
this._f38=true;
this._f39=false;
this.t1=0;
this.t2=0;
this.t3=0
this.mouseL1=MVSdoGeometry.createPoint(0,0);
this.mouseL2=this.mouseL1;
this.labelStyle=null;
}
_f0.prototype._f40=function(x0)
{
if(this._f1._f41)
return this._f42(this._f1._f43);
else if(this._f1._f44==null)
return "<theme name=\""+MVUtil._f45(this._f1._f43)+
"\" render_labels=\""+this._f1.renderLabels+"\" datasource=\""+
MVUtil._f46(this._f1._f43)+"\""+
this._f47(x0)+
this._f48()+"/>";
else
 {
var x1="<theme name=\""+MVUtil._f45(this._f1._f43)+
"\" render_labels=\""+this._f1.renderLabels+"\" datasource=\""+
MVUtil._f46(this._f1._f43)+"\""+
this._f47(x0)+
this._f48()+">\n"+
"  <binding_parameters>\n";
for(var x2=0;x2<this._f1.queryParameterArray.length;x2++)
{
var x3=this._f1.queryParameterArray[x2];
x1+="<parameter ";
if(x3.type==undefined)
x1+="value=\""+x3.value+"\">";
else
 {
x1+="type=\""+x3.type+"\" ";
if(x3.type=="geometry")
{
x1+=">"+x3.value.toGML();
}
else {
x1+="elemtype=\""+x3.elemtype+"\" ";
if(!x3.value)
x1+=">";
else
 {
if(x3.value.substring!=undefined)
{
var x4=x3.value;
if(x3.elemtype=="String")
{
var x5=x3.value.split(",");
finalvalue="";
for(var x6=0;x6<x5.length;x6++)
{
if(x6>0)
finalvalue+=",";
var x7=x5[x6].indexOf("\"");
var x8=x5[x6].lastIndexOf("\"");
finalvalue+=x5[x6].substring(x7+1,x8);
}
x4=finalvalue;
}
x1+="value=\""+MVUtil._f49(x4,"\"","&quot;")+"\">";
}
else
 {
x1+=">";
for(var x6=0;x6<x3.value.length;x6++)
x1+="<elem>"+x3.value[x6]+"</elem>";
}
}
}
}
x1+="</parameter>";
}
x1+="  </binding_parameters>\n";
x1+="</theme>";
return x1;
}
}
_f0.prototype.setSize=function(x9,x10)
{
this.width=x9;
this.height=x10;
}
_f0.prototype._f50=function(x11,x12)
{
{
if(this._f1._f51==1&&this._f1._f52==1)
{
this._f14=this.width;
this._f15=this.height;
}
else
 {
this._f14=x11;
this._f15=x12;
}
}
}
_f0.prototype._f53=function()
{
return this._f30;
}
_f0.prototype._f54=function()
{
return this.request;
}
_f0.prototype._f55=function()
{
return this.minX;
}
_f0.prototype._f56=function()
{
return this.minY;
}
_f0.prototype._f57=function()
{
return this.maxX;
}
_f0.prototype._f58=function()
{
return this.maxY;
}
_f0.prototype.isVisible=function()
{
if(this._f21<this._f1._f59||this._f21>this._f1._f60)
return false;
else
 return !(this.div.style.visibility=='hidden');
}
_f0.prototype._f61=function()
{
return this.div;
}
_f0.prototype.getThemeBasedFOI=function()
{
return this._f1;
}
_f0.prototype._f62=function()
{
return this._f26;
}
_f0.prototype._f63=function()
{
return this._f27;
}
_f0.prototype._f64=function(x13)
{
this._f26=x13;
}
_f0.prototype._f65=function(x14)
{
this._f27=x14;
}
_f0.prototype._f66=function()
{
return this._f1._f67?this._f1._f67:this.parent._f68;
}
_f0.prototype._f69=function()
{
return _f11._f70=="IF"&&this._f66()=="PNG24";
}
_f0.prototype.isWholeImage=function()
{
return this._f1._f71>=this._f21||this._f36;
}
_f0.prototype.setVisible=function(x15,x16)
{
if(x16)
{
if(!this.isVisible())
{
if(this._f23)
{
if((!this._f69()&&this._f1.getFOIData()!=null&&
this._f1.getFOIData()[0].gtype%10!=1)||
this.isWholeImage()||!this._f1._f72)
{
if(this._f1._f73&&this._f34)
this._f34.style.visibility="visible";
}
}
this.div.style.visibility="visible";
if(!this._f22)
{
MVUtil._f74(this.div,Math.ceil((this.minX-this.parent._f75)*this.parent._f76),
-Math.ceil((this.maxY-this.parent._f77)*this.parent._f78));
this._f79(this.parent,this.minX,this.minY,this.maxX,this.maxY,true);
}
}
}
else
 {
if(this._f23)
{
if((!this._f69()&&this._f1.getFOIData()!=null&&
this._f1.getFOIData()[0].gtype%10!=1)||
this.isWholeImage()||!this._f1._f72)
{
if(this._f34)
this._f34.style.visibility="hidden";
if(this.parent._f80._f81.length>0)
{
for(var x17=0;x17<this.parent._f82.childNodes.length;x17++)
{
if(this.parent._f80._f81[0].nid==this.parent._f82.childNodes[x17].id)
{
this.parent._f80.deleteInfoWindow(this.parent._f80._f81[0]);
break;
}
}
}
}
else if(this.parent._f80._f81.length>0)
{
for(var x18=0;x18<this.div.childNodes.length;x18++)
{
if(this.parent._f80._f81[0].nid==this.div.childNodes[x18].id)
{
this.parent._f80.deleteInfoWindow(this.parent._f80._f81[0]);
break;
}
}
}
}
this.div.style.visibility="hidden";
this._f30=false;
this.parent._f83();
}
}
_f0.prototype._f84=function()
{
if(this._f30)
return this._f24;
else
 return MVUtil._f84(this.div);
}
_f0.prototype._f85=function()
{
if(this._f30)
return this._f25;
else
 return MVUtil._f85(this.div);
}
_f0.prototype._f86=function(x19)
{
if(this._f30)
this._f24=x19;
}
_f0.prototype._f87=function(x20)
{
if(this._f30)
this._f25=x20;
}
_f0.prototype._f88=function()
{
return this.width;
}
_f0.prototype._f89=function()
{
return this.height;
}
_f0.prototype._f90=function()
{
this._f91();
while(this.div.childNodes.length>0)
{
var x21=this.div.childNodes[0];
this.div.removeChild(x21);
if(this.parent._f80._f81.length>0)
if(this.parent._f80._f81[0].nid==x21.id)
{
this.parent._f80.deleteInfoWindow(this.parent._f80._f81[0]);
}
MVUtil._f92(x21);
}
}
_f0.prototype._f91=function(x22)
{
this.clearAllStarted=true;
var x23=null;
if(this.div.parentNode)
{
x23=this.div.parentNode;
x23.removeChild(this.div);
}
while(this.div.childNodes.length>0)
{
var x24=this.div.childNodes[0];
this.div.removeChild(x24);
while(x24.childNodes.length>0)
{
var x25=x24.childNodes[0];
x24.removeChild(x25);
MVUtil._f92(x25);
}
MVUtil._f92(x24);
}
this.wholeImage=null;
if(this._f31!=1&&!x22)
{
this.clearTransImageLayer();
}
if(x23)
x23.appendChild(this.div);
this.clearAllStarted=false;
}
_f0.prototype.clearTransImageLayer=function()
{
if(this._f34&&this._f34.parentNode)
{
this._f34.parentNode.removeChild(this._f34);
MVUtil._f92(this._f34);
}
this._f34=null;
this._f35=null;
}
_f0.prototype.getImageParameters=function()
{
var x26="";
var x27=this._f66();
if(x27=="PNG8")
x26+="&format="+x27;
if(this._f1.bgColor)
x26+="&bgcolor="+this._f1.bgColor;
return x26;
}
_f0.prototype._f79=function(x28,x29,x30,x31,x32,x33,
x34,x35,x36,x37)
{
this._f39=false;
this._f36=false;
var x38=this;
var x39=function()
{
if(!x38.themeId&&x38.abortionSupported)
{
setTimeout(x39,50);
return ;
}
if(!x37)
x37=false;
if(!x36&&!x28._f93)
{
var x40=[x28,x29,x30,x31,x32,x33,x34,x35,x37];
var x41=x38._f79;
var x42={obj:x38,func:"refreshFOIs",params:x40};
x28._f94.push(x42);
return ;
}
x38._f30=true;
{
x38._f1.minX=x38.minX=x29;
x38._f1.minY=x38.minY=x30;
x38._f1.maxX=x38.maxX=x31;
x38._f1.maxY=x38.maxY=x32;
}
x38._f24=Math.ceil((x38.minX-x38.parent._f75)*x38.parent._f76);
x38._f25=-Math.ceil((x38.maxY-x38.parent._f77)*x38.parent._f78);
MVUtil._f74(x38.div,x38._f24,x38._f25);
x38.div.style.display='none';
x38._f28=x38.parent._f95;
x38._f29=x38.parent._f96;
if(x38._f1._f97)
{
x38._f1._f97();
}
if(x38._f1)
MVUtil.runListeners(x38._f1._f98,MVEvent.BEFORE_REFRESH);
x38._f20=x33;
x38._f21=x38.parent._f21;
x38._f99();
x38.around=x38.parent.wrapAroundLayer(x38._f88());
if(!x38._f1.visible||
!x34&&(x38._f21<x38._f1._f59||x38._f21>x38._f1._f60))
{
x38._f22=false;
x38._f2=false;
x38._f91();
return;
}
x38._f22=true;
if(x38._f2)
{
x38._f100();
return ;
}
x38.adjustBBox(x34);
if(x38.adjustedRealWidth<=0||x38.adjustedRealHeight<=0)
return;
var x43="request=getfoi&version=1.0"+x38.getImageParameters()+
"&theme="+encodeURIComponent(x38._f1._f43)+"&bbox="+
x38._f16+":"+x38._f17+":"+
x38._f18+":"+x38._f19+
"&width="+x38.adjustedRealWidth+"&height="+x38.adjustedRealHeight;
if(x38.adjustedMinX2)
x43+="&bbox2="+x38.adjustedMinX2+":"+x38._f17+":"+
x38.adjustedMaxX2+":"+x38._f19;
if(x38._f1._f101!=null)
x43+="&hilitestyle="+x38._f1._f101;
if(x38._f1._f102==true){
if(!x38._f1._f103)
{
MVi18n._f6("MVThemeBasedFOIControl.refreshFOIs","MAPVIEWER-05514",null,x38.parent._f7);
return;
}
x38._f1.setQueryParameters(x38._f1._f103);
x43+="&hilite=yes";
}
if(x38._f1.getQueryParameters())
{
x43+=x38._f1.getQueryParameters();
}
if(x38._f1.getFOIMarker()||
!x38._f1._f72)
{
x43+="&renderimg=no";
}
if(x38._f1._f104<=x38._f21)
{
x43+="&clickable=yes";
}
else
 {
x43+="&clickable=no";
}
if(!x38._f69()||
x38._f1._f71>=x38._f21||
!x38._f1._f72)
{
x43+="&area=yes";
}
else
 {
x43+="&area=no";
}
x43+="&dstsrid="+x38.parent.srid;
if(x38._f1._f105!=null)
{
x43+="&nsdp="+encodeURIComponent(x38._f1._f105.getFlatString());
}
if(x38._f1._f71>=x38._f21)
x43+="&wholeimage=yes";
if(x38._f1.renderLabels)
x43+="&renderlabels=yes";
x38.startLoad=MVUtil._f106(this,function()
{
x38._f100();
});
if(x34){
var x44=x38.parent._f107()
if(x44)
x43+="&mapcache="+encodeURI(x44._f108);
else
 x43+="&mapcache="+encodeURI(x38.parent.msi.mapConfig.mapTileLayer);
x43+="&boundingtheme=yes"+
"&mapwinwidth="+x38.parent._f109()+
"&mapwinheight="+x38.parent._f110()+
"&wholeimagelevel="+x38._f1._f71;
if(x37)
x43+="&recenteronly=yes";
}
if(x38._f1._f111!=null)
{
x43+="&trs="+encodeURIComponent(x38._f1._f111);
}
var x45=x38._f1._f112;
if(x45.length>0)
{
x43+="&tempstyles=";
var x46="<styles>";
for(var x47=0;x47<x45.length;x47++)
{
x46+=x45[x47].getXMLString();
}
x46+="</styles>";
x43+=encodeURIComponent(x46);
}
if(!x38._f1._f113||x35)
x43+="&refresh="+Math.round(Math.random()*100000);
if(x38._f1._f113&&x38._f1._f71<x38._f21)
x43+="&cachefoi=yes";
if(x38.abortionSupported)
x43+="&tid="+x38.themeId;
if(!x38._f1._f114)
x43+="&aw=no";
if(x38._f1._f115>0)
x43+="&mw="+x38._f1._f115;
if(x38._f1._f116>0)
x43+="&mu="+x38._f1._f116;
if(x38._f1._f117)
{
x43+="&sq=y";
x43+="&st="+x38._f1._f118;
}
if(x38._f1.langCode)
{
x43+="&lang="+x38._f1.langCode;
}
var x48=(x38._f1._f3.indexOf(_f11._f12())<0&&
MVUtil._f9(x38._f1._f3)==MVUtil._f9(_f11._f119()));
var x49=x48||MVMapView._f8;
if(MVMapView._f120)
x38.t1=new Date();
if(MVMapView.debug)
MVi18n.alert("Sending Theme Based FOI request. URL:"+x38._f1._f3+" Parameters:"+x43);
try
{
x38.parent.setLoadingIconVisible(true);
if(x38.request)
{
x38.request.onreadystatechange=null;
x38.request.abort();
x38.request=null;
}
}
catch(e)
{
}
try
{
x38.request=MVUtil.getXMLHttpRequest(x49);
x38.request.onreadystatechange=x38.startLoad;
x38.request.open("POST",x38._f1._f3,true);
x38.request.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
x38.request.send(x43);
}
catch(e)
{
MVi18n._f6("MVThemeBasedFOIControl.refreshFOIs","MAPVIEWER-05511",e,x38.parent._f7);
x38._f30=false;
x38.parent._f83();
return ;
}
}
x39();
}
_f0.prototype.clearAllParallelly=function(){
this._f91();
}
_f0.prototype.getElementByIdInDiv=function(id){
var x50=[];
var x51=document.getElementById(id);
if(!x51)return x51;
while(x51&&((x51.parentNode!=this.div)||(x51.id!=id))){
x50.push({i:x51.id,e:x51});
x51.id='';
x51=document.getElementById(id);
}
for(j=0,jj=x50.length;j<jj;j++)x50[j].e.id=x50[j].i;
x50=null;
return x51;
}
_f0.prototype._f100=function()
{
if(!this.request)
return ;
if(this.request.readyState==4||this._f2)
{
if(this.request.status==200||this._f2)
{
if(this._f39)
{
this._f30=false;
this.parent._f83();
return ;
}
var oldFois=new Object();
var removeAllNodesFirst=false;
if(this.parent.wraparound&&!this.isWholeImage())
{
removeAllNodesFirst=true;
var data=this._f1.getFOIData();
if(data)
for(var i=0;i<data.length;i++)
{
(data[i]).node=null;
(data[i]).labelNode=null;
}
}
else if(this._f1.getFOIData())
{
for(var i=0;i<this._f1.getFOIData().length;i++)
{
var foi=this._f1.getFOIData()[i];
if(oldFois['_'+foi.nodeId])
{
oldFois=new Object();
removeAllNodesFirst=true;
break;
}
else
 {
oldFois['_'+foi.nodeId]=foi;
oldFois['_'+foi.nodeId].shown=false;
}
}
}
var result=null;
if(MVMapView._f120)
this.t2=new Date();
if(!this._f2)
{
try
{
eval("result="+this.request.responseText);
if(MVMapView._f120)
{
this.t3=new Date();
MVi18n.alert("Time taken to fetch "+this._f1.name+":"+(this.t2-this.t1)+".\n"+
"Time taken to load the FOI data:"+(this.t3-this.t2)+".");
}
}
catch(e)
{
MVi18n._f6("MVThemeBasedFOIControl.foiLoaded","MAPVIEWER-05523",
this.request.responseText,this.parent._f7);
this._f30=false;
this.parent._f83();
return ;
}
this.boundingThemeResult=result;
}
else
 result=this.boundingThemeResult;
if(MVMapView._f121&&this._f38&&this._f1._f72&&
!this._f1._f114&&!this.isWholeImage()&&result.foiarray.length>50)
{
var maxFOINum=0;
if(result.foiarray[0].gtype%10==1)
maxFOINum=750;
else
 maxFOINum=200;
if(result.foiarray.length>maxFOINum)
{
var answer=confirm("You are showing too many FOIs for theme "+this._f1.name+" at the current level.\n"+
"It might take a long time to render these FOIs. You might want to consider rendering\n"+
"these FOIs as a whole image by invoking methods MVThemeBasedFOI.enableAutoWholeImage\n"+
"or MVThemeBasedFOI.setMaxWholeImageLevel.\n\n"+
"Do you want to continue to render the FOIs and ignore this message from now on?\n\n"+
"   Note: You can permanently disable warnings like this by invoking method\n"+
"   MVMapView.enableCodingHints(false).");
if(!answer)
{
this._f30=false;
this.parent._f83();
return ;
}
else
 this._f38=false;
}
}
if(this._f1)
this._f1._f122=result.themeMBR;
else
 return ;
if(result.labelStyle)
this.labelStyle=result.labelStyle;
if(result.isWholeImg)
this._f36=true;
else
 this._f36=false;
if(result.minx!=undefined&&!this._f2)
{
this._f2=true;
var tcx=(result.minx+result.maxx)/2;
var tcy=(result.miny+result.maxy)/2;
var _f21=this._f21;
if(result.adjlevel!=undefined)
_f21=result.adjlevel;
if(_f21<0||
_f21>=0&&this._f21<this._f1._f104&&
_f21>=this._f1._f104)
{
this._f2=false;
this.parent.zoomToRectangle(
MVSdoGeometry.createRectangle(result.minx,result.miny,result.maxx,result.maxy,this.parent.srid));
return ;
}
else
 {
this._f1.minX=this.minX=this._f16=result.minx;
this._f1.minY=this.minY=this._f17=result.miny;
this._f1.maxX=this.maxX=this._f18=result.maxx;
this._f1.maxY=this.maxY=this._f19=result.maxy;
this.parent.reCenterTag=true;
this.parent.setCenterAndZoomLevel(MVSdoGeometry.createPoint(tcx,tcy,this.parent.srid),_f21);
return ;
}
}
this._f123=true;
this._f2=false;
if(!this.div)
return ;
this._f124();
if(MVMapView._f120)
this.t1=new Date();
if(result.foiarray.length>=1)
{
this._f1._f125=result;
this.parent._f126();
if(this.isWholeImage()||!this._f1._f72||
(!this._f69()&&result.foiarray[0].gtype%10!=1))
{
this._f31=2;
if(this._f1._f104<=this._f21)
{
this._f127(result);
}
else{
this.clearTransImageLayer();
}
}
else
 {
this.clearTransImageLayer();
this._f31=1;
}
if(this.wholeImage!=null)
{
if(this.wholeImage instanceof Array)
{
for(var i=0;i<this.wholeImage.length;i++)
{
this.div.removeChild(this.wholeImage[i]);
MVUtil._f92(this.wholeImage[i]);
}
}
else
 {
this.div.removeChild(this.wholeImage);
MVUtil._f92(this.wholeImage);
}
this.wholeImage=null;
}
if(this.isWholeImage()&&this._f1._f72)
{
this._f128(result.foiarray[0].imgurl);
}
if(!this.isWholeImage()||
this._f1._f104<=this._f21&&result.foiarray.length>0)
{
var divParent=this.div.parentNode;
divParent.removeChild(this.div);
if(removeAllNodesFirst&&!this.isWholeImage())
MVUtil._f129(this.div,this.parent._f7);
for(var i=0;i<result.foiarray.length;i++)
{
if(this._f39)
{
this._f30=false;
this.parent._f83();
return ;
}
result.foiarray[i].hiliteImgPref=result.hiliteImgPref;
result.foiarray[i].hiliteMW=result.hiliteMW;
result.foiarray[i].hiliteMH=result.hiliteMH;
result.foiarray[i].attrnames=result.attrnames;
if(this._f31==1&&!this._f69()&&result.foiarray[i].gtype%10!=1)
{
this._f31=3;
this._f127(result);
}
if(this._f31==1&&this._f1._f130)
{
result.foiarray[i].hiliteImgPref=this._f1._f130._f131;
result.foiarray[i].hiliteMW=this._f1._f130.width;
result.foiarray[i].hiliteMH=this._f1._f130.height;
}
var cfoi=result.foiarray[i];
if(cfoi.scid!=undefined)
cfoi.nodeId=cfoi.id+"_scid_"+cfoi.scid;
else
 cfoi.nodeId=cfoi.id;
var ofoi=oldFois['_'+cfoi.nodeId];
var flag=false;
if(ofoi)
{
flag=_f11._f70=="IF"?this._f1.reuseIENode:true;
}
if(this.parent.wraparound)
{
this._f132(cfoi);
}
if(flag){
ofoi.shown=true;
this._f133(cfoi,ofoi);
}
else
 {
this._f133(cfoi);
}
}
divParent.appendChild(this.div);
for(var key in oldFois)
{
foi=oldFois[key];
if(foi.shown==false)
{
this.clearFOINode(foi);
}
}
}
else
 {
for(key in oldFois)
{
foi=oldFois[key];
this.clearFOINode(foi);
}
}
this.makeFOIsVisiable();
this._f23=true;
}
else
 {
this._f1._f125=null;
this._f91();
}
if(MVMapView._f120)
{
this.t2=new Date();
MVi18n.alert("Time taken to render "+this._f1.name+":"+(this.t2-this.t1)+".\n"+
"wholeImage:"+this.isWholeImage()+", clickable:"+(this._f1._f104<=this._f21));
}
this.addTransparentLayer();
this._f30=false;
this.parent._f83();
if(this._f1._f134)
{
this._f1._f134();
}
if(this._f1)
MVUtil.runListeners(this._f1._f98,MVEvent.AFTER_REFRESH);
this.request=null;
this._f1.reuseIENode=true;
}
}
}
_f0.prototype.makeFOIsVisiable=function(){
var x52=true;
for(var x53=0;x53<this.parent._f135.length;x53++)
{
if(this.parent._f135[x53].layerControl&&
!this.parent._f135[x53].layerControl.zoomControl.hasZoomFinished())
{
x52=false;
break;
}
}
if(x52)
{
this.div.style.display='';
}
else
 {
var x54=this;
setTimeout(function(){x54.makeFOIsVisiable.apply(x54)},70);
}
}
_f0.prototype._f127=function(x55)
{
this.clearTransImageLayer();
this._f34=document.createElement("div");
var x56=this.adjustedRealWidth;
var x57=this.adjustedRealHeight;
if(this.around)
{
x56=this._f14;
}
MVUtil._f74(this.parent._f82,0,0);
if(this._f1.visible&&this._f1._f73)
this._f34.style.visibility="visible";
else
 this._f34.style.visibility="hidden";
this._f34.style.position="absolute";
this._f34.id=this._f1.name;
var x58=(this._f16-this.minX)*this.parent._f76;
if(this.around)
{
if(this.maparea.length!=2)
{
var x59=this.minX;
this.htmlMapXoffset=(this._f16-x59)*this.parent._f76;
}
else
 this.htmlMapXoffset=0;
x58=0;
}
else
 this.htmlMapXoffset=0;
var x60=(this.maxY-this._f19)*this.parent._f78;
var x61=(this.parent._f95-this._f28)*this.parent._f76;
var x62=(this.parent._f96-this._f29)*this.parent._f78;
MVUtil._f74(this._f34,this._f24-this.parent._f140-x61+x58,
this._f25-this.parent._f141+x62+x60);
this._f34.style.zIndex=this.div.style.zIndex;
var x63=_f11._f142+"transparent.gif";
if(this._f1._f104>this._f21)
return ;
if(_f11._f70=="IF"&&
(!this._f69()||this.isWholeImage()||
!this._f1._f72))
{
var x64=document.createElement("div");
x64.style.zIndex="127";
var x65=document.createElement("img");
x65.width=MVUtil._f32(x56)
x65.height=MVUtil._f32(x57);
x65.style.width=MVUtil._f32(x56);
x65.style.height=MVUtil._f32(x57);
x65.style.border=0;
x65.src=_f11._f142+"transparent.gif";
x65.setAttribute("usemap","#polygonMap"+this.parent._f145+"_"+this._f33,0);
x64.style.position="absolute";
MVUtil._f74(x64,0,0);
x64.appendChild(x65);
MVUtil._f146(x65);
this._f34.appendChild(x64);
}
else
 {
this._f147=document.createElement("img");
this._f147.src=x63;
this._f147.setAttribute("usemap","#polygonMap"+this.parent._f145+"_"+this._f33);
this._f147.width=x56;
this._f147.height=x57;
this._f147.style.width=MVUtil._f32(x56);
this._f147.style.height=MVUtil._f32(x57);
this._f147.style.border=0;
MVUtil._f146(this._f147);
this._f34.appendChild(this._f147);
}
this._f35=document.createElement("map");
this._f35.setAttribute("name","polygonMap"+this.parent._f145+"_"+this._f33);
this._f35.setAttribute("id","polygonMap"+this.parent._f145+"_"+this._f33);
this._f34.setAttribute("theme",this._f1.name);
if(_f11._f70=="SF")
{
this._f33++;
}
this._f34.appendChild(this._f35);
}
_f0.prototype.addTransparentLayer=function()
{
if(this._f34)
{
this.parent._f82.style.zIndex=Math.max(MVUtil.getZIndex(this.div)+1,MVUtil.getZIndex(this.parent._f82));
this.parent._f82.appendChild(this._f34);
}
}
var _f148=0;
_f0.prototype.setTimeout=function(_f149,_f150)
{
var Ie="tempVar"+_f148;
_f148++;
eval(Ie+" = this;");
var oi=_f149.replace(/\\/g,"\\\\");
oi=oi.replace(/\"/g,'\\"');
return window.setTimeout(Ie+'._setTimeoutDispatcher("'+oi+'");'+Ie+" = null;",_f150);
}
_f0.prototype._setTimeoutDispatcher=function(func)
{
eval(func);
}
_f0.prototype._f151=function(x66)
{
this._f152=0;
this.result=x66;
this.startDisplayGroupFoi();
}
_f0.prototype.startDisplayGroupFoi=function()
{
for(var x67=0;x67<180;x67++)
{
if(this._f152<this.result.foiarray.length)
{
this.result.foiarray[this._f152].attrnames=this.result.attrnames;
this._f133(this.result.foiarray[this._f152]);
this._f152++;
}
else
 {
return;
}
}
this.setTimeout("this.startDisplayGroupFoi()",2);
}
_f0.prototype._f133=function(x68,x69)
{
if(!x68.node&&x68.hidden)
return ;
if(!(this._f1._f72)&&(x69)&&(x69.node))
{
node=x69.node;
x68._f153=x69._f153;
x69.shown=false;
}
if(this.isWholeImage()||x68.imgurl||x68.area||
this._f1.getFOIMarker()||!this._f1._f72)
{
x68.imgurl4Hilite=null;
x68.highlightImageWidth=x68.hiliteMW;
x68.highlightImageHeight=x68.hiliteMH;
x68.pngType4Hilite=true;
x68.normalPngType4Hilite=false;
x68.hilitePngType4Hilite=false;
x68.recalSizePos4Hilite=false;
if(this._f31==1&&this._f1._f130)
x68.imgurl4Hilite=x68.hiliteImgPref;
if(this._f31==1&&this._f1.getFOIMarker())
{
x68.width=this._f1.getFOIMarker().width;
x68.height=this._f1.getFOIMarker().height;
}
if(x68.highlightImageWidth&&x68.highlightImageHeight
&&(x68.width)&&(x68.height)
&&((x68.width!=x68.highlightImageWidth)
||(x68.height!=x68.highlightImageHeight)))
{
x68.recalSizePos4Hilite=true;
}
if(!x68.recalSizePos4Hilite&&x69&&x69.node&&
(x69.highlightImageWidth!=x68.highlightImageWidth
||x69.highlightImageHeight!=x68.highlightImageHeight))
{
x68.recalSizePos4Hilite=true;
}
var x70=this;
var x71=null;
if(!this.isWholeImage()&&this._f1._f72&&!(!x68.imgurl&&x68.scid))
{
if(!(x68.imgurl4Hilite)&&x68.imgurl&&x68.hiliteImgPref)
{
var x72=x68.imgurl.lastIndexOf("/");
if(x72!=-1)
{
x68.imgurl4Hilite=x68.imgurl.substring(0,(x72+1))+x68.hiliteImgPref+x68.imgurl.substr((x72+1));
}
}
if(this._f1.getFOIMarker())
{
x68.imgurl=this._f1.getFOIMarker()._f131;
}
var x73=x68.imgurl.toLowerCase().indexOf('.png')==-1?false:true;
x68.normalPngType4Hilite=x73;
if(x68.imgurl4Hilite)
x68.hilitePngType4Hilite=x68.imgurl4Hilite.toLowerCase().indexOf('.png')==-1?false:true;
x68.pngType4Hilite=(x68.normalPngType4Hilite)?true:(x68.hilitePngType4Hilite);
var x74="IMG";
if((this._f69()&&x68.pngType4Hilite)||
(x70._f1._f154&&x68.gtype%10==1)||x70._f1._f154)
x74="DIV";
if((x69)&&(x69.node))
{
if(x69.node.tagName==x74){
x71=x69.node;
x68._f153=x69._f153;
}
else{
x69.shown=false;
}
}
if(!x71)
{
if(x74=='DIV')
x71=document.createElement('div');
else
 x71=document.createElement('img');
}
x68.node=x71;
x71.className="noprint";
MVUtil._f146(x71);
x71.style.position="absolute";
if(_f11._f70=="IF"&&!x70._f1._f154)
{
x71.style.width=MVUtil._f32(x68.width);
x71.style.height=MVUtil._f32(x68.height);
}
if(x68.nodeId&&x68.nodeId!=null)
x71.id=x68.nodeId;
x71._f155=x68.x;
x71._f156=x68.y;
var x75=(this.parent._f95-this._f28)*this.parent._f76;
var x76=(this.parent._f96-this._f29)*this.parent._f78;
var x77;
if(x68.wr&&x68.wr.length>0)
x77=MVUtil._f157(this.parent,this.minX,this.minY,this.maxX,this.maxY,
this._f14,this._f15,x68.x+x68.wr[0]*(this.parent.msi._f158-this.parent.msi._f159),
x68.y);
else
 x77=MVUtil._f157(this.parent,this.minX,this.minY,this.maxX,this.maxY,
this._f14,this._f15,x68.x,x68.y);
x77[0]-=x75;
x77[1]+=x76;
x77[0]+=this._f1._f138;
x77[1]+=this._f1._f139;
if(_f11._f70=="IF")
{
x77[1]++;}
else
 {
}
if(x68.height%2==0)
x77[1]--;
x68.loc=x77;
if(x68.gtype%10==1)
MVUtil._f74(x71,Math.ceil(x77[0]-x68.width/2.0),Math.ceil(x77[1]-x68.height/2.0));
else
 MVUtil._f74(x71,x77[0],x77[1]);
x71.style.zIndex=160;
}
else
 {
if(!this.isWholeImage()&&this._f1._f72&&
(!x68.imgurl&&x68.scid)&&this.shouldNotUseHTMLMap(x68))
return ;
}
if(x71){
x71.tbf=x70;
x71.foi=x68;
}
var x78="";
if(x68.name&&x68.name!="null")
x78=x68.name;
if((_f11._f70=="IF"||_f11._f70=="NS")
&&x70._f1._f160&&x68.name&&x68.name!="null"&&x71)
{
x71.title=x78;
}
var x79="";
if(x68.name)
x79=MVUtil._f49(x68.name," ","&nbsp;");
if(x71&&this.shouldNotUseHTMLMap(x68))
{
this.div.appendChild(x71);
if(!x70._f1._f154)
{
if(this._f69()&&x68.pngType4Hilite)
{
if(x73)
x71.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(src='"+
x68.imgurl+"', sizingMethod='image');";
else
 x71.innerHTML="<img src=\""+encodeURIComponent(x68.imgurl)+"\"/>";
}
else
 x71.src=x68.imgurl;
if(!x70._f1._f154)
{
x71.setAttribute("id",x68.nodeId);
x71.setAttribute("border",0);
}
if(x68.lox!=null&&x68.loy!=null)
{
var x80;
if((x69)&&(x69.labelNode))
x80=x69.labelNode;
else
 x80=document.createElement("div");
x80.innerHTML=x79;
x80.style.cssText=this.labelStyle;
x80.style.position="absolute";
x80.style.cursor="default";
x80.style.zIndex=x71.style.zIndex;
x68.labelNode=x80;
MVUtil._f74(x80,parseInt(x71.style.left)+x68.lox,parseInt(x71.style.top)+x68.loy);
this.div.appendChild(x80);
}
else if((x69)&&(x69.labelNode))
{
x69.labelNode.parentNode.removeChild(x69.labelNode);
x69.labelNode=null;
}
}
else
 {
x70._f1._f154(x68);
if(x68.xoffset==undefined||isNaN(x68.xoffset))
x68.xoffset=0;
else
 x68.xoffset=parseInt(x68.xoffset);
if(x68.yoffset==undefined||isNaN(x68.yoffset))
x68.yoffset=0;
else
 x68.yoffset=parseInt(x68.yoffset);
this._f161(x68,x69,x71);
}
this._f162(x71,x68);
MVUtil._f163(x71);
}
else {
var x81=x78;
var x82=Math.floor((x68.x-this.minX)*this.parent._f76+0.5);
var x83=Math.floor((this.maxY-x68.y)*this.parent._f78+0.5);
var x84="";
if(this._f1._f104<=this._f21)
{
var x85=null;
if((x69)&&(x69.areaNode))
{
x68._f153=x69._f153;
}
x85=document.createElement("area");
x85.tbf=x70;
x85.foi=x68;
x85.node=x71;
MVUtil._f164(x85,this._f1.cursorStyle);
x68.areaNode=x85;
this._f165=true;
x85.setAttribute("border",0);
if(_f11._f70!="OS")
{
if((_f11._f70=="IF"||_f11._f70=="NS")
&&x70._f1._f160&&x68.name&&x68.name!="null")
{
x85.setAttribute("title",x81);
}
x85.setAttribute("href","javascript:void(0)");
}
if(x68.gtype%10==1)
{
var x77;
if(x68.wr&&x68.wr.length>0)
x77=MVUtil._f157(this.parent,this.minX,this._f17,this.maxX,this._f19,
this._f14,this.adjustedRealHeight,x68.x+x68.wr[0]*(this.parent.msi._f158-this.parent.msi._f159),
x68.y);
else
 x77=MVUtil._f157(this.parent,this._f16,this._f17,
this._f18,this._f19,
this.adjustedRealWidth,this.adjustedRealHeight,
x68.x,x68.y);
var x86=0;
x77[0]+=this.parent._f140+this._f1._f138-x86;
x77[1]+=this.parent._f141+this._f1._f139;
x85.setAttribute("shape","rect");
var x87=parseInt(x68.width);
var x88=parseInt(x68.height);
x87=x87<10?10:x87;
x88=x88<10?10:x88;
x87=x87/2;
x88=x88/2;
var x89=(x77[0]-x87)+","+(x77[1]-x88)+","+
(x77[0]+x87)+","+(x77[1]+x88);
x85.setAttribute("coords",x89);
}
else
 {
var x90="";
if(typeof(x68.area)=='undefined'||x68.area==null||x68.area=='')
x90="-1000,-1000";
else
 {
x90=x68.area;
if(x70._f1._f154&&!this.isWholeImage())
{
x70._f1._f154(x68);
}
if(x68.xoffset==undefined||isNaN(x68.xoffset))
x68.xoffset=0;
else
 x68.xoffset=parseInt(x68.xoffset);
if(x68.yoffset==undefined||isNaN(x68.yoffset))
x68.yoffset=0;
else
 x68.yoffset=parseInt(x68.yoffset);
var x91=this.htmlMapXoffset+x68.xoffset+this._f1._f138;
if(x68.wr&&x68.wr.length>0&&this.maparea.length!=2)
x91+=x68.wr[0]*(this.parent.msi._f158-this.parent.msi._f159)*this.parent._f76;
var x92=x68.yoffset+this._f1._f139;
x90=this._f166(x90,x91,x92);
}
x85.setAttribute("shape","poly");
x85.setAttribute("coords",x90);
}
this._f162(x85,x68);
MVUtil._f163(x85);
this._f35.appendChild(x85);
}
else
 {
if(x68.areaNode)
x68.areaNode=null;
}
if(x71)
{
MVUtil._f74(x71,x77[0],x77[1]);
x71.style.position="absolute";
x71.style.width=MVUtil._f32(x68.width);
x71.style.height=MVUtil._f32(x68.height);
x71.id=x68.nodeId;
if(x70._f1._f154)
this._f161(x68,x69,x71);
else
 x71.src=x68.imgurl;
x71.style.zIndex=125;
this.div.appendChild(x71);
}
}
this._f167(x71,x68);
if(x71!=null&&x71.id!=null)
{
for(var x93=0;x93<x70._f1._f168.length;x93++)
{
if(x68.id==x70._f1._f168[x93].id
&&x70._f1._f168[x93]._f153)
{
var x94=true;
if(x68.attrs&&x70._f1._f168[x93].attrs)
{
var x95=x70._f1._f168[x93].attrs.length;
if(x68.attrs.length==x95)
{
for(var x96=0;x96<x95;x96++)
if(x68.attrs[x96]!=x70._f1._f168[x93].attrs[x96])
{
x94=false;
break;
}
}
else
 x94=false;
}
if(x94)
{
x68._f153=true;
x70._f1._f168[x93]=x68;
this.highlightFOI(x70,x68,x71);
break;
}
}
}
}
}
}
_f0.prototype.destroy=function()
{
this._f90();
if(this.request)
{
this.request.onreadystatechange=function(){};
this.request.abort();
this.request=null;
}
if(this._f1._f125&&this._f1._f125.foiarray)
{
while(this._f1._f125.foiarray.length>0)
{
var x97=this._f1._f125.foiarray.pop();
if(x97.node)
x97.node=null;
if(x97.areaNode)
x97.areaNode=null;
}
}
this._f1._f125=null;
this._f1=null;
if(this.parent)
{
if(this.parent.div&&this.div)
{
try{this.parent.div.removeChild(this.div);}catch(error){}
}
this.div=null;
this.parent._f83();
this.parent=null;
}
this._f35=null;
this._f34=null;
this._f147=null;
}
_f0.prototype.adjustBBox=function(x98)
{
var x99=false;
var x100=this.parent.msi.mapConfig.coordSys;
this.adjustedMinX2=0;
this.adjustedMaxX2=0;
if(this.parent.wraparound&&!x98)
{
if(this.maparea.length>=3)
{
this._f16=x100.minX;
this._f18=x100.maxX;
}
else if(this.maparea.length==2)
{
var x101=x100.maxX-x100.minX;
if(this.parent.maptype!="PROJECTED")
{
this._f16=this.maparea[0].minx;
if(this.maparea[0].minx<this.maparea[1].maxx)
this._f18=this.maparea[0].minx+x101;
else
 this._f18=this.maparea[1].maxx+x101;
}
else
 {
this._f16=this.maparea[0].minx;
this._f18=this.maparea[0].maxx;
if(this.maparea[1].maxx<this.maparea[0].minx)
{
this.adjustedMinX2=this.maparea[1].minx;
this.adjustedMaxX2=this.maparea[1].maxx;
}
else if(this.maparea[1].minx<this.maparea[0].minx)
{
this.adjustedMinX2=this.maparea[1].minx;
this.adjustedMaxX2=this.maparea[0].minx;
}
}
}
else
 {
this._f16=this.maparea[0].minx;
this._f18=this.maparea[0].maxx;
}
x99=true;
}
else
 {
if(!x98&&this.minX<x100.minX)
{
this._f16=x100.minX;
x99=true;
}
else
 this._f16=this.minX;
if(!x98&&this.maxX>x100.maxX)
{
this._f18=x100.maxX;
x99=true;
}
else
 this._f18=this.maxX;
}
if(!x98&&this.minY<x100.minY)
{
this._f17=x100.minY;
x99=true;
}
else
 this._f17=this.minY;
if(!x98&&this.maxY>x100.maxY)
{
this._f19=x100.maxY;
x99=true;
}
else
 this._f19=this.maxY;
if(this.adjustedMinX2)
this.adjustedRealWidth=Math.round(this._f14*
(this._f18-this._f16+this.adjustedMaxX2-this.adjustedMinX2)/(this.maxX-this.minX));
else
 this.adjustedRealWidth=Math.round(this._f14*
(this._f18-this._f16)/(this.maxX-this.minX));
this.adjustedRealHeight=Math.round(this._f15*
(this._f19-this._f17)/(this.maxY-this.minY));
}
_f0.prototype._f42=function(x102)
{
if(!x102)
return x102;
if(x102.indexOf("<themes>")>=0)
x102=x102.substring(x102.indexOf("<themes>")+8,x102.indexOf("</themes>"));
if(this._f1._f111)
{
var x103=x102.indexOf(" render_style");
if(x103>0)
{
var x104="\"";
var x105=x102.indexOf(x104,x103);
var x106=x102.indexOf("'",x103);
if(x105<0||x106<x105)
{
x104="'";
x105=x106;
}
var x107=x102.indexOf(x104,x105+1);
x102=x102.substring(0,x103)+x102.substring(x107+1);
}
else
 x103=x102.indexOf("<jdbc_query")+11;
return x102.substring(0,x103)
+" render_style=\""+this._f1._f111+"\""
+x102.substring(x103);
}
else
 return x102;
}
_f0.prototype._f128=function(x108)
{
var x109=-(this._f19-this.parent._f169)*this.parent._f78;
x109=x109-MVUtil._f85(this.div)+this._f1._f139;
var x110=this;
var x111=function(x112)
{
var x113=null;
if(x110._f69())
x113=MVUtil._f170(x108,true);
else
 {
x113=document.createElement('img');
x113.src=x108;
}
x113.style.width=MVUtil._f32(x110.adjustedRealWidth);
x113.style.height=MVUtil._f32(x110.adjustedRealHeight);
x113.style.position="absolute";
MVUtil._f74(x113,x112,x109);
x110.div.appendChild(x113);
return x113;
}
this._f91(true);
if(!this.parent.wraparound)
{
var x114=(this._f16-this.parent._f171)*this.parent._f76;
x114=x114-MVUtil._f84(this.div)+this._f1._f138;
this.wholeImage=x111(x114);
}
else{
var x115=this.parent.msi.mapConfig.coordSys;
var x116=x115.maxX-x115.minX;
var x117=this.maparea.length;
if(x117>=3)
{
this.wholeImage=new Array();
for(var x118=0;x118<x117;x118++)
{
var x114=(this.maparea[x118].minx+this.maparea[x118].n*x116-(this.maparea[x118].minx-this._f16)-this.parent._f171)*this.parent._f76;
x114=x114-MVUtil._f84(this.div)+this._f1._f138;
var x119=x111(x114);
this.wholeImage.push(x119);
}
}
else if(x117==2)
{
var x114=(this.maparea[0].minx+this.maparea[0].n*x116-this.parent._f171)*this.parent._f76;
x114=x114-MVUtil._f84(this.div)+this._f1._f138;
var x119=x111(x114);
if(this.maparea[0].minx<this.maparea[1].maxx)
{
this.wholeImage=new Array(x119);
var x120=(this.maparea[0].minx+(this.maparea[0].n+1)*x116-this.parent._f171)*this.parent._f76;
x120=x120-MVUtil._f84(this.div)+this._f1._f138;
var x119=x111(x120);
this.wholeImage.push(x119);
}
else
 {
this.wholeImage=x119;
}
}
else
 {
var x114=(this.maparea[0].minx+this.maparea[0].n*x116-this.parent._f171)*this.parent._f76;
x114=x114-MVUtil._f84(this.div)+this._f1._f138;
this.wholeImage=x111(x114);
}
}
}
_f0.prototype._f37=function(x121)
{
var x122="request=getthemeid";
var x123=(this._f1._f3.indexOf(_f11._f12())<0&&
MVUtil._f9(this._f1._f3)==MVUtil._f9(_f11._f119()));
var x124=x123||MVMapView._f8;
var x125=null;
var x126=this;
var x127=function()
{
if(x125.readyState==4&&x125.status==200)
{
x126.themeId=x125.responseText;
if(x126.themeId.lastIndexOf("\n")==x126.themeId.length-1)
x126.themeId=x126.themeId.substr(0,x126.themeId.length-1);
if(x126.themeId&&x126.themeId.indexOf("MAPVIEWER-06011")>=0)
{
x126.abortionSupported=false;
}
if(x121)
x121();
x125=null;
}
}
try
{
x125=MVUtil.getXMLHttpRequest(x124);
x125.onreadystatechange=x127;
x125.open("POST",this._f1._f3,true);
x125.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
x125.send(x122);
}
catch(e)
{
MVi18n._f6("MVThemeBasedFOIControl.getThemeId","MAPVIEWER-05511",e,this.parent._f7);
}
}
_f0.prototype.abort=function()
{
if(!this.abortionSupported)
return ;
var x128="request=abort&tid="+this.themeId;
var x129=(this._f1._f3.indexOf(_f11._f12())<0&&
MVUtil._f9(this._f1._f3)==MVUtil._f9(_f11._f119()));
var x130=x129||MVMapView._f8;
var x131=null;
var x132=this;
this._f39=true;
try
{
x131=MVUtil.getXMLHttpRequest(x130);
x131.open("POST",this._f1._f3,true);
x131.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
x131.send(x128);
x131=null;
}
catch(e)
{
MVi18n._f6("MVThemeBasedFOIControl.abort","MAPVIEWER-05511",e,this.parent._f7);
}
}
_f0.prototype._f124=function()
{
if(this._f1._f104<=this._f21&&this._f1._f73)
MVUtil._f164(this.div,this._f1.cursorStyle);
else
 MVUtil._f164(this.div,"default");
}
_f0.prototype._f161=function(x133,x134,x135)
{
if(x134!=null&&x134.layoutListenerDiv!=null){
if(x134.layoutListenerDiv.parentNode!=null)
{
x134.layoutListenerDiv.parentNode.removeChild(x134.layoutListenerDiv);
}
MVUtil._f92(x134.layoutListenerDiv);
x134.layoutListenerDiv=null;
}
var x136=document.createElement("div");
x136.style.position="absolute";
x136.tag="layout";
var x137=this;
var x138=x133.xoffset+x137._f1._f138;
var x139=x133.yoffset+x137._f1._f139;
x136.style.left=MVUtil._f32(x138);
x136.style.top=MVUtil._f32(x139);
x136.style.padding=MVUtil._f32(0);
x136.innerHTML=""+x133.html+"";
x135.appendChild(x136);
x133.layoutListenerDiv=x136;
}
_f0.prototype.stopPropagation=function(x140)
{
x140=(x140)?x140:((window.event)?event:null);
var x141=MVUtil.getEventSource(x140);
var x142=x141.tbf;
while(!x142&&x141.parentNode){x141=x141.parentNode;
x142=x141.tbf;
}
if(x140&&!x142._f1._f172)
{
if(_f11._f70=="IF")
x140.cancelBubble=true;
else if(x140.stopPropagation)
x140.stopPropagation();
}
}
_f0.prototype._f173=function(x143)
{
x143=(x143)?x143:((window.event)?event:null);
var x144=MVUtil.getEventSource(x143);
var x145=x144.tbf;
while(!x145&&x144.parentNode){x144=x144.parentNode;
x145=x144.tbf;
}
if(!x145||!x145._f1||x143&&!x145._f1._f172)
{
MVUtil._f173(x143);
}
}
_f0.prototype.getMouseDownLocation=function(x146)
{
x146=(x146)?x146:((window.event)?event:null);
var x147=MVUtil.getEventSource(x146);
var x148=x147.tbf;
while(!x148&&x147.parentNode){x147=x147.parentNode;
x148=x147.tbf;
}
if(!x148)
return;
var x149=x147.foi;
x148.mouseL1=MVUtil._f174(x146);
x148._f173(x146);
}
_f0.prototype.tFoiMouseUp=function(x150)
{
x150=(x150)?x150:((window.event)?event:null);
var x151=MVUtil.getEventSource(x150);
var x152=x151.tbf;
while(!x152&&x151.parentNode){x151=x151.parentNode;
x152=x151.tbf;
}
if(!x152)
return;
var x153=x151.foi;
if(!x152._f1._f73||x152.parent._f175)
return ;
x152.mouseL2=MVUtil._f174(x150);
if(x150.button==2)
{
if(!x152.shouldNotUseHTMLMap(x153)&&_f11._f70=='IF')
x152.tFoiRightClick(x150);
return;
}
if(x152.mouseL1.x==x152.mouseL2.x&&x152.mouseL1.y==x152.mouseL2.y)
{
x152.parent._f176();
if(x152.parent.getBrowserType()=="NS")
{
x152.parent._f177=null;
}
var x154=MVUtil.getMouseLocation(x152.parent,x150);
if(x152._f1._f178||x152._f1._f98[MVEvent.MOUSE_CLICK])
{
var x155=MVSdoGeometry.createPoint(x154.sdo_point.x,x154.sdo_point.y,x152.parent.srid);
if(x152._f1._f178)
x152._f1._f178(x155,x153,MVUtil.getEvent(x150));
if(x152._f1)
MVUtil.runListeners(x152._f1._f98,MVEvent.MOUSE_CLICK,[x155,x153,MVUtil.getEvent(x150)]);
}
else
 {
if(x153.attrs!=null)
{
if(x153.attrs.length>=1&&x152._f1._f179)
{
var x156=x153.name==null?"":MVUtil._f49(x153.name," ","&nbsp;");
x152.parent._f80._f180(x153.attrs,x153.attrnames,x153.id,
x156,x154.sdo_point.x,x154.sdo_point.y,null,null,
x152._f1,300);
}
}
}
if(!x152._f1)
{
x152._f173(x150);
return ;
}
var x157=x151;
if(!x152.shouldNotUseHTMLMap(x153))
{
x157=x151.node;
}
x152.mouseUpHighLight(x152,x153,x157);
}
}
_f0.prototype.tFoiRightClick=function(x158)
{
x158=(x158)?x158:((window.event)?event:null);
var x159=MVUtil.getEventSource(x158);
var x160=x159.tbf;
while(!x160&&x159.parentNode){x159=x159.parentNode;
x160=x159.tbf;
}
if(!x160)
return;
var x161=x159.foi;
if(!x160._f1._f73)
return ;
var x162=MVUtil.getMouseLocation(x160.parent,x158);
var x163=MVSdoGeometry.createPoint(x162.sdo_point.x,x162.sdo_point.y,x160.parent.srid);
if(x160._f1._f181)
{
x160._f1._f181(x163,x161,MVUtil.getEvent(x158));
}
if(x160._f1)
MVUtil.runListeners(x160._f1._f98,MVEvent.MOUSE_RIGHT_CLICK,[x163,x161,MVUtil.getEvent(x158)]);
x160._f173(x158);
};
_f0.prototype.tFoiMouseOver=function(x164)
{
x164=(x164)?x164:((window.event)?event:null);
var x165=MVUtil.getEventSource(x164);
var x166=x165.tbf;
var x167=0;
while(!x166&&x165.parentNode){x165=x165.parentNode;
x166=x165.tbf;
x167++;
}
if(!x166)
return;
var x168=x165.foi;
var x169=x165;
if(!x166.shouldNotUseHTMLMap(x168)){
x169=x165.node;
if(!x166._f1._f73)
return ;
}
var x170=MVUtil.getMouseLocation(x166.parent,x164);
var x171=MVSdoGeometry.createPoint(x170.sdo_point.x,x170.sdo_point.y,x166.parent.srid);
if(x166._f1._f182)
{
x166._f1._f182(x171,x168,MVUtil.getEvent(x164));
}
if(x166._f1)
MVUtil.runListeners(x166._f1._f98,MVEvent.MOUSE_OVER,[x171,x168,MVUtil.getEvent(x164)]);
if(x166._f1._f183&&
!(x166.parent._f80._f184()&&!x166.parent._f80.mouseOver))
{
if(x168.attrs!=null)
{
if(x168.attrs.length>=1&&x166._f1._f179)
{
var x172=x168.name==null?"":MVUtil._f49(x168.name," ","&nbsp;");
x166.parent._f80._f180(x168.attrs,x168.attrnames,x168.id,
x172,x170.sdo_point.x,x170.sdo_point.y,null,null,
x166._f1,300,true);
}
}
}
if(x166.shouldNotUseHTMLMap(x168)&&x166._f1._f185)
{
x165.style.zIndex=161;
}
if(!x165.title&&x168.name&&x168.name!="null"&&x166._f1._f160)
{
var x172=x168.name==null?"":MVUtil._f49(x168.name," ","&nbsp;");
x166.parent._f80.showTextTip(x172,x170.sdo_point.x,x170.sdo_point.y);
}
if(x166._f1.enableMouseOverHighlightMode&&x166._f1._f186)
{
if(x169&&x168.imgurl4Hilite)
x166.highlightFOI(x166,x168,x169);
else if(x168.scid)
{
var x168=x166.getMainFOI(x168);
if(x168)
x166.highlightFOI(x166,x168,x168.node);
}
}
};
_f0.prototype.tFoiMouseOut=function(x173)
{
x173=(x173)?x173:((window.event)?event:null);
var x174=MVUtil.getEventSource(x173);
var x175=x174.tbf;
while(!x175&&x174.parentNode){x174=x174.parentNode;
x175=x174.tbf;
}
if(!x175)
return;
var x176=x174.foi;
var x177=x174;
if(!x175.shouldNotUseHTMLMap(x176)){
x177=x174.node;
if(!x175._f1._f73)
return ;
}
var x178=MVUtil.getMouseLocation(x175.parent,x173);
var x179=MVSdoGeometry.createPoint(x178.sdo_point.x,x178.sdo_point.y,x175.parent.srid);
if(x175._f1._f187)
{
x175._f1._f187(x179,x176,MVUtil.getEvent(x173));
}
if(x175._f1)
MVUtil.runListeners(x175._f1._f98,MVEvent.MOUSE_OUT,[x179,x176,MVUtil.getEvent(x173)]);
if(x175._f1._f183&&
!(x175.parent._f80._f184()&&!x175.parent._f80.mouseOver))
{
if(x176.attrs!=null)
{
if(x176.attrs.length>=1&&x175._f1._f179)
{
x175.parent.removeInfoWindow();
}
}
}
if(x175.shouldNotUseHTMLMap(x176))
{
x174.style.zIndex=160;
}
x175.parent._f80._f188();
if(x175._f1.enableMouseOverHighlightMode&&x175._f1._f186)
{
if(x177&&x176.imgurl4Hilite!=null&&!(x176._f153))
x175.deHighlightFOI(x175,x176,x177);
else if(x176.scid)
{
var x176=x175.getMainFOI(x176);
if(x176&&!(x176._f153))
x175.deHighlightFOI(x175,x176,x176.node);
}
}
};
_f0.prototype.shouldNotUseHTMLMap=function(x180)
{
return ((this._f69()||x180.gtype%10==1)&&this._f1._f72&&!this.isWholeImage())
}
_f0.prototype.mouseUpHighLight=function(x181,x182,x183)
{
if(x181._f1._f186&&!x181._f1._f154)
{
if(x182.imgurl4Hilite!=null)
{
var x184=x181._f1._f168.length;
var x185=x181._f1._f189;
var x186=false;
var x187=null;
if(x184>0)
{var x188=x181._f1._f168;
var x189=null;
var x190=null;
for(var x191=0;x191<x188.length;x191++)
{
if(x188[x191]==x182)
{
x186=true;
x187=x191;
break;
}
}
if(x185==-1||x184<=x185){
var x192=x181._f1._f125.foiarray;
for(var x191=0;x191<x188.length;x191++)
{
var x193=x188[x191];
x193._f153=false;
if(x193!=null&&x193.node!=null&&x193.imgurl!=null)
{
x181.deHighlightFOI(x181,x193);
}
}
x181._f1._f168=new Array();
x184=0;
}
else if(x185>0)
{
for(var x191=1;x191<=x185;++x191)
{
x189=x188[x184-x191];
x189._f153=false;if(x189!=null&&x189.node!=null&&x189.imgurl!=null)
{
x181.deHighlightFOI(x181,x189);
}
}
x181._f1._f168=x188.slice(0,(0-x185));
x184-=x185;
}
}
if(!x186)
{
x182._f153=true;x181._f1._f168[x184++]=x182;
x181.highlightFOI(x181,x182);
}
else if(x185==0)
{
if(x187!=null){
x181._f1._f168[x187]._f153=false;
x181._f1._f168.splice(x187,1);
}
x181.deHighlightFOI(x181,x182);
}
}
else if(x182.scid)
{
var x182=x181.getMainFOI(x182);
if(x182)
x181.mouseUpHighLight(x181,x182,x182.node);
}
}
}
_f0.prototype.deHighlightFOI=function(x194,x195)
{
if(!x195.highlight)
return;
var x196=x195.node;
x195.highlight=false;
var x197=function(x198)
{
if(x194.shouldNotUseHTMLMap(x195))
{
if(x195!=null&&x195.imgurl!=null)
{
if(x194._f69()&&x195.pngType4Hilite)
{
if(x195.normalPngType4Hilite)
{
x198.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(src='"+
x195.imgurl+"', sizingMethod='image');";
x198.src=x195.imgurl;
x198.innerHTML="";
}
else
 {
x198.onmouseover=null;
x198.onmouseout=null;
x198.style.filter=null;
x198.src=null;
x198.innerHTML="<img src=\""+encodeURIComponent(x195.imgurl)+"\"/>";
var x199=function(){
x198.onmouseover=x194.tFoiMouseOver;
x198.onmouseout=x194.tFoiMouseOut;
}
window.setTimeout(x199,50);
}
}
else
 x198.src=x195.imgurl;
if(x195.recalSizePos4Hilite&&(x195.gtype%10==1))
{
var x200=Math.ceil(MVUtil._f84(x198)+(x195.highlightImageWidth-x195.width)/2.0);
var x201=Math.ceil(MVUtil._f85(x198)+(x195.highlightImageHeight-x195.height)/2.0);
MVUtil._f74(x198,x200,x201);
x198.style.width=MVUtil._f32(x195.width);
x198.style.height=MVUtil._f32(x195.height);
}
}
}
else
 {
if(x195!=null&&x195.imgurl!=null)
{
if(x194._f69()&&_f190.pngType4Hilite)
{
x198.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(src='"+
x195.imgurl+"', sizingMethod='image');";
}
x198.src=x195.imgurl;
}
}
}
if(x196 instanceof Array)
{
for(var x202=0;x202<x196.length;x202++)
x197(x196[x202]);
}
else
 x197(x196);
}
_f0.prototype.highlightFOI=function(x203,x204)
{
if(x204.highlight)
return;
var x205=x204.node;
x204.highlight=true;
var x206=function(x207)
{
if(x203.shouldNotUseHTMLMap(x204)&&x204.imgurl4Hilite)
{
if(x203._f69()&&x204.pngType4Hilite)
{
if(x204.hilitePngType4Hilite)
{
x207.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(src='"+
x204.imgurl4Hilite+"', sizingMethod='image');";
x207.src=x204.imgurl4Hilite;
x207.innerHTML="";
}
else
 {
x207.onmouseout=null;
x207.onmouseover=null;
x207.style.filter=null;
x207.src=null;
x207.innerHTML="<img src=\""+encodeURIComponent(x204.imgurl4Hilite)+"\"/>";
var x208=function(){
x207.onmouseout=x203.tFoiMouseOut;
x207.onmouseover=x203.tFoiMouseOver;
}
window.setTimeout(x208,10);
}
}
else
 {
x207.src=x204.imgurl4Hilite;
}
if(x204.recalSizePos4Hilite&&(x204.gtype%10==1))
{
var x209=Math.ceil(MVUtil._f84(x207)-(x204.highlightImageWidth-x204.width)/2.0);
var x210=Math.ceil(MVUtil._f85(x207)-(x204.highlightImageHeight-x204.height)/2.0);
MVUtil._f74(x207,x209,x210);
x207.style.width=MVUtil._f32(x204.highlightImageWidth);
x207.style.height=MVUtil._f32(x204.highlightImageHeight);
}
}
else if(x207!=null&&(x204.imgurl4Hilite))
{
if(x203._f69()&&x204.pngType4Hilite)
{
x207.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(src='"+
x204.imgurl4Hilite+"', sizingMethod='image');";
}
x207.src=x204.imgurl4Hilite;
}
}
if(x205 instanceof Array)
{
for(var x211=0;x211<x205.length;x211++)
x206(x205[x211]);
}
else if(x205)
x206(x205);
}
_f0.prototype._f99=function()
{
this.maparea=new Array();
var x212=this.minX;
var x213=this.maxX;
if(this.parent.msi!=null)
{
var x214=this.parent.msi._f159;
var x215=this.parent.msi._f158;
var x216=x215-x214;
var x217=0;
if(x212<x214)
{
x217=Math.ceil((x214-x212)/x216);
x212+=x216*x217;
x213+=x216*x217;
x217=0-x217;
}
else if(x212>=x215)
{
x217=Math.ceil((x212-x215)/x216);
x212-=x216*x217;
if(x212==x215)
{
x217++;
x212=x214;
}
x213-=x216*x217;
}
if(x213<=x215&&x212!=x213){
this.maparea.push({minx:x212,maxx:x213,n:x217});
}
else{
this.maparea.push({minx:x212,maxx:x215,n:x217});
x213-=x216;
x217++;
while(x213>x215)
{
this.maparea.push({minx:x214,maxx:x215,n:x217});
x213-=x216;
x217++;
}
this.maparea.push({minx:x214,maxx:x213,n:x217});
}
}
}
_f0.prototype._f167=function(x218,x219,x220)
{
var x221=this;
if(x219.areaNode)
{
var x222=x219.areaNode;
var x223=x222.getAttribute("coords");
var x224=x222.getAttribute("shape");
if(x219.wr&&x219.wr.length>1)
{
if(!x220)
{
x220=1;
}
if(x220==1)
{
x219.areaNode=new Array(x222);
}
for(var x225=x220;x225<x219.wr.length;x225++)
{
var x226=x222.cloneNode(true);
MVUtil._f164(x226,this._f1.cursorStyle);
var x227=x225*(this.parent.msi._f158-this.parent.msi._f159)*this.parent._f76;
var x228=this._f166(x223,x227,0);
if(_f11._f70=='IF')x226.setAttribute("shape",x224);
x226.setAttribute("coords",x228);
var x229=x226.getAttribute("coords");
if(x218)
x226.node=x218;
this._f162(x226,x219);
MVUtil._f163(x226);
this._f35.appendChild(x226);
x219.areaNode.push(x226);
}
}
}
if(!x218)
return;
if(x219.wr&&x219.wr.length>1)
{
var x230=false;
if(!x220)
{
x220=1;
}
if(x220==1)
{
x219.node=new Array(x218);
if(x219.lox!=null&&x219.loy!=null)
{
x219.labelNode=new Array(x219.labelNode);
}
}
for(var x225=x220;x225<x219.wr.length;x225++)
{
var x231=x218.cloneNode(true);
x231.id=x231.id+"clone"+x225;
var x232=parseInt(x218.style.left);
x231.style.left=MVUtil._f32(x232+x225*(this.parent.msi._f158-this.parent.msi._f159)*this.parent._f76);
this.div.appendChild(x231);
x231.orgid=x218.id;
this._f162(x231,x219);
x219.node.push(x231);
MVUtil._f163(x218);
if(x219.lox!=null&&x219.loy!=null&&!x221._f1._f154)
{
var x233=x219.labelNode[0].cloneNode(true);
var x234=parseInt(x233.style.left);
x233.style.left=MVUtil._f32(x234+x225*(this.parent.msi._f158-this.parent.msi._f159)*this.parent._f76);
x219.labelNode.push(x233);
this.div.appendChild(x233);
}
}
}
}
_f0.prototype._f162=function(x235,x236)
{
var x237=this;
x235.tbf=x237;
x235.foi=x236;
x235.onmouseover=x237.tFoiMouseOver;
x235.onmouseout=x237.tFoiMouseOut;
x235.onmousemove=x237.tFoiMouseMove;
if(this._f1._f104<=this._f21)
{
x235.onmousedown=x237.getMouseDownLocation;
x235.onmouseup=x237.tFoiMouseUp;
if(_f11._f70=='IF'&&!x237.shouldNotUseHTMLMap(x236))
x235.oncontextmenu=function(){return false;};
else
 x235.oncontextmenu=x237.tFoiRightClick;
x235.ondblclick=x237._f173;
x235.onclick=x237.stopPropagation;
x235.onkeyup=x237.stopPropagation;
}
else
 {
x235.onmousedown=null;
x235.onmouseup=null;
if(_f11._f70=='IF'&&!x237.shouldNotUseHTMLMap(x236))
x235.oncontextmenu=function(){return false;};
else
 x235.oncontextmenu=null;
x235.ondblclick=null;
x235.onclick=null;
x235.onkeyup=null;
}
}
_f0.prototype._f132=function(x238)
{
x238.wr=new Array();
var x239=0;
var x240=this.parent.msi._f159;
var x241=this.parent.msi._f158;
var x242=x241-x240;
var x243=this.parent.msi._f159+x242/2;
var x244=x238.x+x238.width;
var x245=x238.x;
var x246=false;
if(x245<=x240||x244>=x241)
{
x238.wr.push(this.maparea[0].n);
if(x245<x243&&x244<x243)
{
x244=x238.x+x242;
x245=x238.x+x238.width;
}
else if(x245>x243&&x244>x243)
{
x244=x238.x;
x245=x238.x+x238.width-x242;
}
for(var x247=1;x247<this.maparea.length;x247++)
{
if(this.maparea[x247].maxx>x245)
x238.wr.push(this.maparea[x247].n);
}
}
else
 {
for(var x247=0;x247<this.maparea.length;x247++)
{
if((x245>this.maparea[x247].minx&&x245<this.maparea[x247].maxx)||(x244>this.maparea[x247].minx&&x244<this.maparea[x247].maxx))
x238.wr.push(this.maparea[x247].n);
}
}
}
_f0.prototype._f166=function(x248,x249,x250)
{
if(x249!=0||x250!=0)
{
var x251="";
var x252=x248.split(" ");
for(var x253=0;x253<x252.length;x253++)
{
var x254=x252[x253].split(",");
for(var x255=0;x255<x254.length;){
x254[x255]=parseInt(x254[x255++])+x249;
x254[x255]=parseInt(x254[x255++])+x250;
}
x251+=x254.toString()+" ";
}
if(x251.length>0)
x251=x251.substring(0,x251.length-1);
return x251;
}
else
 return x248;
}
_f0.prototype._f48=function()
{
if(this._f1._f117)
return " marker_sequence=\""+this._f1._f118+"\"";
else
 return "";
}
_f0.prototype._f47=function(x256)
{
if(x256!=null)
return " render_style=\""+x256+"\"";
else if(this._f1._f111)
return " render_style=\""+this._f1._f111+"\"";
else
 return "";
}
_f0.prototype.getMainFOI=function(x257)
{
for(var x258=0;x258<this._f1._f125.foiarray.length;x258++)
{
if(!this._f1._f125.foiarray[x258].scid&&this._f1._f125.foiarray[x258].id==x257.id){
return this._f1._f125.foiarray[x258];
}
}
return null
}
_f0.prototype.tFoiMouseMove=function(x259)
{
x259=(x259)?x259:((window.event)?event:null);
var x260=MVUtil.getEventSource(x259);
var x261=x260.tbf;
while(!x261&&x260.parentNode){x260=x260.parentNode;
x261=x260.tbf;
}
if(!x261)
return;
var x262=x260.foi;
if(!x261.shouldNotUseHTMLMap(x262)){
node=x260.node;
if(!x261._f1._f73)
return ;
}
var x263=MVUtil.getMouseLocation(x261.parent,x259);
var x264=MVSdoGeometry.createPoint(x263.sdo_point.x,x263.sdo_point.y,x261.parent.srid);
if(x261._f1)
MVUtil.runListeners(x261._f1._f98,MVEvent.MOUSE_MOVE,[x264,x262,MVUtil.getEvent(x259)]);
};
_f0.prototype.clearFOINode=function(x265)
{
var x266=x265.node;
tbf=this;
var x267=function(x268)
{
MVUtil._f191(x268,tbf.parent._f7);
if(x268.parentNode){
x268.parentNode.removeChild(x268);
}
}
if(x266)
{
if(x266 instanceof Array){
for(var x269=0;x269<x266.length;x269++)
x267(x266[x269]);
}
else
 x267(x266);
x265.node=null;
}
x266=x265.labelNode;
if(x266)
{
if(x266 instanceof Array){
for(var x269=0;x269<x266.length;x269++)
x267(x266[x269]);
}
else
 x267(x266);
x265.labelNode=null;
}
x266=x265.areaNode;
if(x266)
{
if(x266 instanceof Array){
for(var x269=0;x269<x266.length;x269++)
x267(x266[x269]);
}
else
 x267(x266);
x265.areaNode=null;
}
}
function _f288(x0,x1,x2)
{
this.minX;
this.minY;
this.maxX;
this.maxY;
this.width=x0._f109();
this.height=x0._f110();
this.parent=x0;
this._f289=null;
this.div=document.createElement("div");
this.div.style.position="absolute";
this.div.style.zIndex="3000";
this.parent._f290.appendChild(this.div);
this._f160=document.createElement("div");
this._f160.style.position="absolute";
this._f160.style.backgroundColor="#FFFFDF";
this._f160.style.border="inset black 1px";
this._f160.style.visibility="hidden";
this._f160.style.zIndex="1200";
this.parent.div.appendChild(this._f160);
MVUtil._f74(this._f160,0,0);
this._f81=new Array(0);
this._f172=false;
this.mouseOver=false;
}
_f288.prototype.enableEventPropagation=function(x0)
{
this._f172=x0;
}
_f288.prototype._f291=function()
{}
_f288.prototype._f188=function()
{
this._f160.style.visibility='hidden';
}
_f288.prototype.refresh=function(x1,x2,x3,x4,x5,x6)
{
MVUtil._f74(this.div,0,0);
this.minX=x1;
this.minY=x2;
this.maxX=x3;
this.maxY=x4;
this.width=x5;
this.height=x6;
var x7=0;
for(;x7<this._f81.length;x7++)
{
var x8=MVUtil._f292(this.parent,this.minX,this.minY,this.maxX,this.maxY,
this.width,this.height,this._f81[x7].winX,this._f81[x7].winY);
this._f81[x7].style.visibility="hidden";
MVUtil._f74(this._f81[x7],x8.x,x8.y);
if(this._f81[x7].winX>=this.parent._f75&&this._f81[x7].winX<=this.parent._f287&&
this._f81[x7].winY>=this.parent._f293&&this._f81[x7].winY<=this.parent._f77)
this._f81[x7].style.visibility="visible";
}
}
_f288.prototype._f61=function()
{
return this.div;
}
_f288.prototype.showTextTip=function(x9,x10,x11)
{
this._f160.innerHTML=x9;
if(_f11._f70=="NS"&&navigator.userAgent.toLowerCase().indexOf("netscape")>0)
{
var x12=MVUtil._f49(x9.toLowerCase(),"&nbsp;"," ").length;
this._f160.style.width=MVUtil._f32(x12*8+8);
}
var x13=MVUtil._f292(this.parent,this.minX,this.minY,this.maxX,this.maxY,
this.width,this.height,x10,x11);
MVUtil._f74(this._f160,x13.x+10,x13.y+5);
this._f160.style.visibility="visible";
}
_f288.prototype._f184=function()
{
return this._f81.length>0;
}
_f288.prototype._f235=function(id,x14,x15,x16,x17,x18,x19,x20,x21,x22,x23,x24)
{
if(MVInfoWindowStyle1._f294)
{
clearTimeout(MVInfoWindowStyle1._f294);
MVInfoWindowStyle1._f294=null;
}
if(x20==null||x20=="")
{
x20=_f11._f295;
}
if(x22)
this.mouseOver=true;
else
 this.mouseOver=false;
this._f188();
var x25=this.parent;
if(x16==null||x16.length==0)
return ;
if(!x17)
x17=0;
else if(x17<0)
x17=0;
else if(x17>x16.length-1)
x17=x16.length-1;
var x26=0;
for(;x26<this._f81.length;x26++)
{
if(id&&this._f81[x26].nid==id){
this.deleteInfoWindow(this._f81[x26]);
return ;
}
}
if(this._f81.length>0)
this.deleteInfoWindow(this._f81[0]);
var x27=document.createElement("div");
var x28=this;
var x29=function(x30)
{
x30=(x30)?x30:((window.event)?event:null);
var x31=!x28._f172;
if(x30&&!x28._f172)
{
if(_f11._f70=="IF")
x30.cancelBubble=true;
else if(x30.stopPropagation)
x30.stopPropagation();
}
}
var x32=function(x33)
{
x25._f297=true;
x29(x33);
}
var x34=function(x35)
{
x25._f297=false;
x29(x35);
}
x27.id="mvinfodiv";
x27.onmousedown=MVUtil._f106(x27,x29);
x27.onmouseup=MVUtil._f106(x27,x29);
x27.onmousemove=MVUtil._f106(x27,x29);
x27.onmouseover=MVUtil._f106(x27,x32);
x27.onmouseout=MVUtil._f106(x27,x34);
x27.ondblclick=MVUtil._f106(x27,x29);
x27.onclick=MVUtil._f106(x27,x29);
x27.onkeyup=MVUtil._f106(x27,x29);
if(_f11._f70=="OS")
x27.onkeypress=MVUtil._f106(x27,x29);
else
 x27.onkeydown=MVUtil._f106(x27,x29);
var x36=MVUtil._f292(this.parent,this.minX,this.minY,this.maxX,this.maxY,
this.width,this.height,x14,x15);
x27.style.position="absolute";
x27.style.visibility="hidden";
this.div.appendChild(x27);
if(x20!="MVInfoWindowStyle1")
{
var x37=document.createElement("div");
if(x18)
x37.style.width=x18;
if(x19)
x37.style.height=x19;
x37.innerHTML=x16[0].getContent();
x27.appendChild(x37);
var x38=document.getElementById("mvinfotable_"+this.parent._f145);
var x39=document.getElementById("mvinfotable1_"+this.parent._f145);
var x40=0,x41=0;
if(x38!=null&&x39!=null)
{
if(x38.offsetWidth<=x39.offsetWidth)
x40=x39.offsetWidth+45;
else
 x40=x38.offsetWidth+45;
x18=x40;
}
if(x40>x18)
x18=x40;
if(x38!=null&&x39!=null)
{
x38.style.width=x18+"px";
if(x38.offsetHeight<=x39.offsetHeight)
x41=x39.offsetHeight+40;
else
 x41=x38.offsetHeight+40;
}
if(x41>x19)
x19=x41;
x27.removeChild(x37);
}
this._f289=null;
this._f289=new _f298(x27,0,0,x16,x17,x18,x19,x20,x25,x22,this,x36,x23);
this._f81.push(x27);
x27.nid=id;
x27.winX=x14;
x27.winY=x15;
MVUtil._f74(x27,x36.x,x36.y);
x27.style.visibility="visible";
x27.style.zIndex="1200";
if(!x22)
{
var x42=0;var x43=0;
if(x20==_f11._f295)
{
var x38=document.getElementById("infowindow3table_"+this.parent._f145);
if(x38!=null)
{
x18=x38.offsetWidth;
x38.style.width=MVUtil._f32(x18);
x19=x38.offsetHeight;
x42=MVUtil._f84(x38);
x43=MVUtil._f85(x38);
}
}
if(x24&&x20=="MVInfoWindowStyle1")
return ;
var x44=x18;
var x45=x19;
var x46=this.parent._f140+x36.x+x42;
var x47=this.parent._f141+x36.y+x43;
var x48=0;
var x49=0;
if(x46<0)
{
if(this.parent._f140+x36.x<x46)
x48=this.parent._f140+x36.x-10;
else
 x48=x46;
}
else if(this.parent._f140+x36.x<0)
x48=this.parent._f140+x36.x-10;
else if(x46+x44>this.width)
{
if(this.parent._f140+x36.x>x46+x44)
x48=this.parent._f140+x36.x-this.width+10;
else
 x48=x46+x44-this.width;
}
else if(this.parent._f140+x36.x>this.width)
x48=this.parent._f140+x36.x-this.width+10;
if(x47<0)
{
if(this.parent._f141+x36.y<x47)
x49=this.parent._f141+x36.y-10;
else
 x49=x47;
}
else if(this.parent._f141+x36.y<0)
x49=this.parent._f141+x36.y-10;
else if(x47+x45>this.height)
{
if(this.parent._f141+x36.y>this.height)
x49=this.parent._f141+x36.y-this.height+10;
else
 x49=x47+x45-this.height;
}
else if(this.parent._f141+x36.y>this.height)
x49=this.parent._f141+x36.y-this.height+10;
if(x21)
this.setTimeout("this.parent.smoothScroll("+x48+","+x49+")",x21);
else
 this.parent.smoothScroll(x48,x49);
}
}
_f288.prototype._f286=function(x50,x51,x52,x53,x54,x55,x56,x57,x58,x59)
{
if(x50.length==0)
return;
if(x55&&x56)
{
this._f235(x50,x51,x52,x53,x54,x55,x56,x57,null,null,x59,true);
}
else
 this._f235(x50,x51,x52,x53,x54,_f11._f299,_f11._f300,x57,null,null,x59,true);
}
_f288.prototype._f301=function(x60,x61,x62,x63,x64,x65,x66)
{
var x67=new Array(4);
if(!x61)
x61="null";
x61=MVUtil._f49(x61," &nbsp;","  ");
var x68=0;
var x69=0;
var x70="";
var x71=12;
if(x61!="null")
{
if(x65)
x67[3]=x61;
else
 {
x68=x61.length;
x69++;
x70="<tr><td>"+this.replaceWhiteSpace(x61)+"</td></tr>";
}
}
x70="<table id='mvinfotable_"+this.parent._f145+"'>"+x70;
if(x62==null||x62.length==0){
x67[0]=x70;
if(x66=="MVInfoWindowStyle1")
{
x67[1]=0;
x67[2]=0;
}
else
 {
x67[1]=x61.length*x71+36;
x67[2]=27+30;
}
return x67;
}
var x72=0;
var x73=0;
x69+=x62.length;
x70+="<tr><td><table id='mvinfotable1_"+this.parent._f145+"'>";
for(var x74=0;x74<x62.length;x74++)
{
var x75="#bbbbbb";
if(x74%2==0)
x75="#dddddd";
if(x63!=null&&x63[x74]!=null&&x63[x74].length>x72)
x72=x63[x74].length;
if(x62[x74]==null)
x62[x74]="";
if(x62[x74].length>x73)
x73=x62[x74].length;
var x76=this.replaceWhiteSpace(x62[x74]);
x70=x70+"<tr bgcolor="+x75+">";
if(x63!=null)
{
var x77="";
if(x63[x74]==null)
x77="&nbsp;";
else
 x77=this.replaceWhiteSpace(x63[x74])+":";
x70=x70+"<td>"+x77+"</td>";
}
x70=x70+"<td align=left>"+x76+"</td></tr>";
}
var x78;
if(x68>(x72+x73))
x78=(x68+1)*x71+40;
else
 x78=(x72+x73+1)*x71+40;
x67[0]=x70+"</table></td></tr></table>";
if(x66=="MVInfoWindowStyle1")
{
x67[1]=0;
x67[2]=0;
}
else
 {
x67[1]=x78;
x67[2]=x69*11+80;
}
return x67;
}
_f288.prototype._f180=function(x79,x80,x81,x82,x83,x84,
x85,x86,x87,x88,
x89)
{
var x90=x87._f43;
var x91=x87._f302?x87._f302:_f11._f295;
var x92=x87._f303;
var x93=x87._f304;
var x94=x87._f243;
if(x81.length==0&&x82.length==0)
return ;
if(x85&&x86)
{
var x95=new MVInfoWindowTab(x93,x79);
var x96=new Array(x95);
this._f235(x81,x83,x84,x95,0,x85,x86,x91,x88,x89,x94,true);
}
else
 {
var x97=this._f301(x81,x82,x79,x80,x90,x91=="MVInfoWindowStyle1"&&x92,x91);
var x95=new MVInfoWindowTab(x93?x93:x97[3],x97[0]);
var x96=new Array(x95);
if(x97[1]>0&&x97[2]>0||x91=="MVInfoWindowStyle1")
{
this._f235(x81,x83,x84,x96,0,x97[1],x97[2],x91,x88,x89,x94,true);
}
else
 this._f235(x81,x83,x84,x96,0,_f11._f299,
_f11._f300,x91,x88,x89,x94,true);
}
}
_f288.prototype._f305=function(x98,x99,x100,x101,x102,x103,x104,x105)
{
var x106=new MVInfoWindowTab(x104,x98);
var x107=new Array(x106);
this._f235("",x99,x100,x107,0,x101,x102,x103,null,null,x105);
}
_f288.prototype.deleteInfoWindow=function(x108)
{
if(MVInfoWindowStyle1._f294)
{
clearTimeout(MVInfoWindowStyle1._f294);
MVInfoWindowStyle1._f294=null;
}
var x109=0;
for(x109=0;x109<this._f81.length;x109++)
{
if(this._f81[x109]==x108){
this._f81.splice(x109,1);
}
}
MVUtil._f306(x108);
MVUtil._f191(x108,this.parent._f7);
this.div.removeChild(x108);
this.parent._f297=false;
}
_f288.prototype.setTimeout=function(_f149,_f150)
{
var Ie="tempVar"+_f264;
_f264++;
eval(Ie+" = this;");
var oi=_f149.replace(/\\/g,"\\\\");
oi=oi.replace(/\"/g,'\\"');
return window.setTimeout(Ie+'._setTimeoutDispatcher("'+oi+'");'+Ie+" = null;",_f150);
}
_f288.prototype._setTimeoutDispatcher=function(func)
{
eval(func);
}
_f288.prototype.scrollInfoLayer=function()
{
for(var x110=0;x110<this._f81.length;x110++)
{
if(this._f81[x110].winX<this.parent._f75||this._f81[x110].winX>this.parent._f287||
this._f81[x110].winY<this.parent._f293||this._f81[x110].winY>this.parent._f77)
this._f81[x110].style.visibility="hidden";
else
 this._f81[x110].style.visibility="visible";
}
}
_f288.prototype.replaceWhiteSpace=function(x111)
{
var x112=x111.indexOf("<");
if(x112>=0&&x111.indexOf(">",x112)>=0)return x111;
x111=x111.replace(" ","&nbsp;");
return MVUtil._f49(x111,"  "," &nbsp;");
}
_f288.prototype.destroy=function()
{
MVUtil._f191(this.div,this.parent._f7);
MVUtil._f191(this._f160,this.parent._f7);
this._f160=null;
this.div=null;
if(this._f81&&this._f81.length>0)
this._f81.pop();
}
_f288.prototype.showTabbedHtmlWindow=function(x113,x114,x115,x116,x117,x118,x119,x120)
{
this._f235(null,x114,x115,x119,x120,x116,x117,null,null,null,x118);
}
function MVFOI(x0,x1,x2,x3,x4,x5)
{
if(!x0||x0.length==0)
{
MVi18n._f6("MVFOI.constructor","MAPVIEWER-05503");
return null;
}
this.parent="";
this.id=MVUtil._f227(x0);
this._f228=x1;
this.gtype=x1.getGType();
this.style=x2;
this._f3=x3;
this._f131="";
this._f229=null;
this.html="";
this._f230="";
this._f231="";
this.area="";
this._f232=null;
this._f233=new Array(4);
this._f160=null;
this.width=0;
this.height=0;
this._f73=true;
this.visible=true;
this._f93=false;
this._f234=false;
this._f235=true;
this._f236=true;
this._f178=null;
this._f181=null;
this._f182=null;
this._f187=null;
this._f98=new Array();
this._f185=true;
this._f237=0;
this._f238=0;
this._f172=true;
this.cursorStyle=null;
this._f239=null;
this._f240=null;
this.node=null;
if(this.gtype%10==1)
{
if(!x4)
this.width=10;
else if(x4>0)
this.width=x4;
if(!x5)
this.height=10;
else if(x5>0)
this.height=x5;
}
this._f241=null;
this._f242=null;
this._f243=null;
this._f244=100;
this._f245=100;
this._f67=null;
this.bgColor=null;
this.isHTMLFOI=null;
this.origgeom=null;
this.wr=null;
this.origFOI=null;
this.cloneFOI=null;
this.areacode=null;
this.createMarker=false;
}
MVFOI.prototype.setHTMLElement=function(x0,x1,x2)
{
this.html=x0;
if(x1)
this._f230=x1;
if(x2)
this._f231=x2;
}
MVFOI.createMarkerFOI=function(id,x3,x4,x5,x6)
{
var x7=new MVFOI(id,x3,"",x4,x5,x6);
x7.createMarker=true;
if((!x5||isNaN(x5))||(!x6||isNaN(x6))){
x7._f246=true;
var x8=function(x9,x10){
if(!x5||isNaN(x5))
x5=x9;
if(!x6||isNaN(x6))
x6=x10;
x7.width=x5;
x7.height=x6;
x7._f246=false;
x7=null;
}
MVUtil.getImageSize(x4,x8);
}
return x7;
}
MVFOI.createHTMLFOI=function(id,x11,x12,x13,x14)
{
var x15=new MVFOI(id,x11,"","",0,0);
x15.setHTMLElement(x12,x13,x14);
x15.isHTMLFOI=true;
return x15;
}
MVFOI.prototype.addEventListener=function(x16,x17)
{
this.setEventListener(x16,x17);
}
MVFOI.prototype.setEventListener=function(x18,x19)
{
if(x18==MVEvent.MOUSE_CLICK)
{
this._f178=x19;
}
else if(x18==MVEvent.MOUSE_RIGHT_CLICK)
{
this._f181=x19;
}
else if(x18==MVEvent.MOUSE_OVER)
{
this._f182=x19;
}
else if(x18==MVEvent.MOUSE_OUT)
{
this._f187=x19;
}
if(this._f93)
{
this._f239._f247(this);
}
}
MVFOI.prototype.attachEventListener=function(x20,x21)
{
MVUtil.attachEventListener(this._f98,x20,x21)
if(this._f93)
{
this._f239._f247(this);
}
}
MVFOI.prototype.detachEventListener=function(x22,x23)
{
MVUtil.detachEventListener(this._f98,x22,x23);
}
MVFOI.prototype.setBringToTopOnMouseOver=function(x24)
{
this._f185=x24;
}
MVFOI.prototype.setTopFlag=function(x25)
{
this._f234=x25;
}
MVFOI.prototype.enableInfoWindow=function(x26)
{
this._f235=x26;
}
MVFOI.prototype.enableInfoTip=function(x27)
{
this._f236=x27;
}
MVFOI.prototype.setFOIOrds=function(x28)
{
this._f232=MVUtil._f227(x28).toUpperCase();
}
MVFOI.prototype.setFOIGtype=function(x29)
{
this.gtype=x29;
}
MVFOI.prototype.setVisible=function(x30)
{
this.visible=x30;
if(this.parent)
this.parent._f248._f249(this,x30);
}
MVFOI.prototype.isVisible=function()
{
return this.visible;
}
MVFOI.prototype.getId=function()
{
return this.id;
}
MVFOI.prototype.setClickable=function(x31)
{
this._f73=x31;
}
MVFOI.prototype.setInfoTip=function(x32)
{
if(x32)
this._f73=true;
this._f160=x32;
}
MVFOI.prototype.setInfoWindow=function(x33,x34,x35,x36,x37,x38)
{
this._f229=x33;
this._f237=x34;
this._f238=x35;
this._f241=x36;
this._f242=x37;
this._f243=x38;
}
MVFOI.prototype.enableEventPropagation=function(x39)
{
this._f172=x39;
}
MVFOI.prototype.setZIndex=function(x40)
{
this._f244=x40;
if(this.node)
this.node.zIndex=x40;
}
MVFOI.prototype.setImageFormat=function(x41,x42)
{
if(x41)
{
this._f67=x41.toUpperCase();
if(this._f67=="PNG8"&&x42)
this.bgColor=x42;
}
}
MVFOI.prototype.getMBR=function()
{
var x43=new Array(4);
if(this.gtype%10==3&&this.elemInfo[2]==4)
{
x43[0]=parseFloat(this._f232[4]);
x43[1]=parseFloat(this._f232[3])-
Math.abs((parseFloat(this._f232[0])-parseFloat(this._f232[4])));
x43[2]=parseFloat(this._f232[0]);
x43[3]=parseFloat(this._f232[3]);
}
else
 {
x43[0]=parseFloat(this._f232[0]);
x43[1]=parseFloat(this._f232[1]);
x43[2]=parseFloat(this._f232[0]);
x43[3]=parseFloat(this._f232[1]);
for(var x44=2;x44<this._f232.length;x44=x44+2)
{
if(parseFloat(this._f232[x44])<x43[0])x43[0]=parseFloat(this._f232[x44]);
if(parseFloat(this._f232[x44])>x43[2])x43[2]=parseFloat(this._f232[x44]);
if(parseFloat(this._f232[x44+1])<x43[1])x43[1]=parseFloat(this._f232[x44+1]);
if(parseFloat(this._f232[x44+1])>x43[3])x43[3]=parseFloat(this._f232[x44+1]);
}
}
return x43;
}
MVFOI.prototype._f250=function(x45){
var x46=x45;
var x47="";
var x48=x46.length;
for(var x49=this.elemInfo.length;x49>0;x49=x49-3)
{
var x50=0;
if(this.elemInfo[x49-2]%10==3&&this.elemInfo[x49-1]==3){
for(var x51=0;x51<this.elemInfo[x49-3]-1;x51++)
x50=x46.indexOf(",",x50)+1;
var x52=x50;var x53=x50;if(x49==this.elemInfo.length){
x52=x46.length;
for(var x54=0;x54<2;x54++)
x53=x46.indexOf(",",x53)+1;
x53--;
}
else
 {
for(var x55=0;x55<4;x55++)
{
x52=x46.indexOf(",",x52)+1;
if(x55==1)
x53=x52;
}
x52--;
x53--;
}
x47=x46.substring(x50,x52);
var x56=new Array(4);
var x57=1;
var x58=1;
for(var x59=0;x59<4;x59++){
x58=x47.indexOf(",",x57);
if(x59==3)x58=x47.length;
if(x59==0)x57--;
x56[x59]=x47.substring(x57,x58);
x57=x58+1;
}
var x60=x56[0]+","+x56[3];
var x61=x56[2]+","+x56[1];
this.elemInfo[x49-1]=1;
if(x49==this.elemInfo.length){
x46=x46+","+x61;
}
else
 {
this.elemInfo[x49-3+3]=this.elemInfo[x49-3+3]*1+4;
x46=x46.substring(0,x52)+","+x61+","+
x46.substring(x52+1,x46.length);
}
x46=x46.substring(0,x53)+","+x60+","+
x46.substring(x53+1,x46.length);
}
}
return x46;
}
MVFOI.prototype._f251=function(x62){
var x63=x62[0]+","+x62[1];
var x64="";
for(var x65=this.elemInfo.length;x65>0;x65=x65-3)
{
var x66=this.elemInfo[x65-3]-1;var x67=this.elemInfo[x65]-1;if(x65==this.elemInfo.length)
{
x67=x62.length;}
var x68=x62[x66]+","+x62[x66+1];
var x69="";
var x70=x67-1;
for(var x71=x66;x71<=x70;x71++)
{
x69+=x62[x71]+",";
}
var x72=false;
if(x62[x66]!=x62[x67-1]||x62[x66+1]!=x62[x67])
{
x69+=x68;
x72=true;
}
if(this.elemInfo.length==3)
{
return x69;
}
if(x65==3){
x64=x69+","+x64;
}
else
 {
if(x72)
x69+=","+x63;
else
 x69+=x63;
if(x65==this.elemInfo.length)
{
x64=x69;
}
else
 {
x64=x69+","+x64;
}
}
}
return x64;
}
MVFOI.prototype._f252=function(x73)
{
var x74=new Array(this._f232.length);
var x75=new Array(this._f233[0],this._f233[1],this._f233[2],this._f233[3]);if(x75[0]<x73._f248.minX)x75[0]=x73._f248.minX;
if(x75[1]<x73._f248.minY)x75[1]=x73._f248.minY;
if(x75[3]>x73._f248.maxY)x75[3]=x73._f248.maxY;
var x76=x75[3]-x75[1];
for(var x77=0;x77<this._f232.length;x77++)
{
if(x77%2==0)
{
x74[x77]=Math.floor((this._f232[x77]-x75[0])*x73._f76+0.5);
}
else
 x74[x77]=Math.floor((x76-(this._f232[x77]-x75[1]))*x73._f78+0.5);
}
x75=null;
return x74;
}
function _f253(x0,x1)
{
var x2=new Array(x1[0],x1[1],x1[2],x1[3]);if(x2[0]<x0._f248.minX)x2[0]=x0._f248.minX;
if(x2[1]<x0._f248.minY)x2[1]=x0._f248.minY;
if(x2[3]>x0._f248.maxY)x2[3]=x0._f248.maxY;
var x3=(x1[2]+x1[0])/2;
var x4=(x1[3]+x1[1])/2
var x5=(x1[2]-x1[0])/2;
var x6=x2[3]-x2[1];
var x7;
var x8="";
var x9=2*Math.PI;
for(var x10=0;x10<90;x10++)
{
if(x10%2==0){
x7=Math.floor((x3+x5*Math.cos((x9*x10)/90)-x2[0])*
x0._f76+0.5);
}
else
 {
x7=Math.floor((x6-(x4+x5*Math.sin((x9*x10)/90)-
x2[1]))*x0._f78+0.5);
}
if(x10==0)x8+=x7;
else
 x8+=","+x7;
}
x2=null;
return x8;
}
MVFOI.prototype._f254=function(x78)
{
if(this.area==null||this.area=="")
{
this.area="";
if(this.gtype%10==3&&this.elemInfo[2]==4)
{
this.area=_f253(x78,this._f233);
}
else if(this.gtype%10==3||this.gtype%10==7)
{
var x79=this._f252(x78);
this.area=this._f251(x79);
}
}
}
MVFOI.prototype.animateToNewLocation=function(x80,x81)
{
var x82=this;
var x83=function(x84)
{
if(x84)
x80=x84;
if(x81==null)
x81=100;
x82.parent._f248.foiToNewLocation(x82,x80,x81);
}
if(!x80.srid)
x80.srid=this.parent.getSrid();
if(x80.srid&&(x80.srid!=this._f228.srid))
x80=this.parent.transformGeom(x80,this._f228.srid,null,x83);
else
 x83();
}
MVFOI.prototype.stopAnimation=function()
{
if(this.move_id!=null){
clearTimeout(this.move_id);
this.move_id=null;
var x85=this;
}
}
MVFOI.prototype.setMouseCursorStyle=function(x86)
{
this.cursorStyle=x86;
if(this._f255)
{
if(this._f255 instanceof Array)
{
for(var x87=0;x87<this._f255.length;x87++)
{
MVUtil._f164(this._f255[x87],x86);
}
}
else
 MVUtil._f164(this._f255,x86);
}
if(this.cloneFOI)
this.cloneFOI.setMouseCursorStyle(x86);
}
MVFOI.prototype.getGeometry=function()
{
if(this.origgeom)
return this.origgeom;
return this._f228;
}
MVFOI.prototype.setRenderingStyle=function(x88,x89,x90)
{
this.style=x88;
if(typeof x89!='undefined')
{
if(x89)
this.width=x89;
else
 this.width=0;
}
if(typeof x90!='undefined')
{
if(x90)
this.height=x90;
else
 this.height=0;
}
}
MVFOI.prototype.setWidth=function(x91)
{
if(!x91)
x91=0;
this.width=x91;
}
MVFOI.prototype.setHeight=function(x92)
{
if(!x92)
x92=0;
this.height=x92;
}
MVFOI.prototype.updateImageURL=function(x93,x94,x95)
{
this._f131=x93;
var x96=0;
var x97=0;
if(x94)
{
x96=this.width-x94;
this.width=x94;
}
if(x95)
{
x97=this.height-x95;
this.height=x95;
}
var x98=function(x99,x100,x101,x102,x103,x104)
{
if(x99)
{
var x105=MVUtil._f84(x99)+x100/2;
var x106=MVUtil._f85(x99)+x101/2;
if(x104)
{
x104.src=x93;
MVUtil._f74(x99,x105,x106);
}
else if(x99.src)
{
x99.src=x93;
MVUtil._f74(x99,x105,x106);
}
else if(x99.style.filter)
{
var x107=MVUtil._f49(x93,"(","%28");
x107=MVUtil._f49(x107,")","%29");
x99.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(src='"+
x107+"', sizingMethod='image');";
MVUtil._f74(x99,x105,x106);
}
else
 {
x102._f256(x103.id);
x102._f257(x103);
}
}
}
if(this.node instanceof Array)
{
for(var x108=0;x108<this.node.length;x108++)
{
var x109
if(this._f240)
x109=this._f240[x108];
x98(this.node[x108],x96,x97,this._f239,this,x109);
}
}
else if(this.node)
x98(this.node,x96,x97,this._f239,this,this._f240);
}
MVFOI.prototype.updateGeometry=function(x110)
{
var x111=this._f228;
this._f228=x110;
if(this._f93)
{
var x112=this;
var x113=function(x114)
{
if(x114)
x112._f228=x114;
if(x112._f228.getGType()==1&&x111.getGType()==1){
var x115;
x112.x=x112._f228.getPointX();
x112.x=MVUtil.keepInCSBoundary(x112.parent,x112.x);
x112.y=x112._f228.getPointY();
x112._f232=x112._f228.getFirstPoint();
x112._f232=MVUtil.keepOrdinatesInCSBoundary(x112._f239.parent,x112._f232);
x112._f228.sdo_ordinates=x112._f232;
if(x112._f232.length>0)
x112._f228.sdo_point.x=x112._f232[0];
var x116=(x112._f239.parent._f95-x112._f239._f28)*x112._f239.parent._f76;
var x117=(x112._f239.parent._f96-x112._f239._f29)*x112._f239.parent._f78;
if(x112._f239.around)
{
var x118=x112.wr;
var x119=0;
var x120=x112._f239.parent.msi._f159;
var x121=x112._f239.parent.msi._f158;
var x122=x121-x120;
x112.wr=new Array();
if(x112.areacode!=null){
var x119=x112._f239.maparea[0].n;
x119+=x112.areacode;
x112.wr.push(x119)
}
else
 for(var x123=0;x123<x112._f239.maparea.length;x123++){
if(x112.x>x112._f239.maparea[x123].minx&&x112.x<x112._f239.maparea[x123].maxx)
{
x112.wr.push(x112._f239.maparea[x123].n);
}
}
var x124=x118==null?0:x118.length;
var x125=x124<x112.wr.length?x124:x112.wr.length;
for(var x123=0;x123<x125;x123++)
{
x115=MVUtil._f157(x112._f239.parent,x112._f239.minX,x112._f239.minY,
x112._f239.maxX,x112._f239.maxY,
x112._f239.width,x112._f239.height,
x112.x+x112.wr[x123]*(x121-x120),x112.y);
x115[0]-=x116;
x115[1]+=x117;
if(x112.node instanceof Array)
MVUtil._f74(x112.node[x123],x115[0]-x112.width/2,x115[1]-x112.height/2);
else if(x112.node)
MVUtil._f74(x112.node,x115[0]-x112.width/2,x115[1]-x112.height/2);
}
if(x124<x112.wr.length)
{
x112._f239._f167(x112.node,x112,x112._f239,x124);
}
else
 {
for(var x123=x124-1;x123>=x112.wr.length;x123--)
{
x112.node[x123].parentNode.removeChild(x112.node[x123]);
MVUtil._f92(x112.node[x123]);
x112.node.pop();
x112._f240.pop();
x112._f255.pop();
}
}
}
else
 {
x115=MVUtil._f157(x112._f239.parent,x112._f239.minX,x112._f239.minY,
x112._f239.maxX,x112._f239.maxY,
x112._f239.width,x112._f239.height,
x112.x,x112.y);
x115[0]-=x116;
x115[1]+=x117;
MVUtil._f74(x112.node,x115[0]-x112.width/2,x115[1]-x112.height/2);
}
}
else
 {
x112._f239._f256(x112.id);
x112.gtype=x112._f228.getGType()
x112._f239._f257(x112);
}
}
if(!this._f228.srid)
this._f228.srid=this.parent.getSrid();
if(this._f228.srid!=this.parent.getSrid())
{
this.parent.transformGeom(this._f228,this.parent.srid,null,x113);
}
else
 x113();
}
}
MVFOI.prototype.reDraw=function()
{
if(this._f93)
{
this._f239._f256(this.id);
this._f239._f257(this);
}
}
MVFOI.prototype._f258=function()
{
if(this._f178||(this._f98[MVEvent.MOUSE_CLICK]!=null&&this._f98[MVEvent.MOUSE_CLICK].length!=0)||
this._f181||(this._f98[MVEvent.MOUSE_RIGHT_CLICK]!=null&&this._f98[MVEvent.MOUSE_RIGHT_CLICK].length!=0)||
this._f182||(this._f98[MVEvent.MOUSE_OVER]!=null&&this._f98[MVEvent.MOUSE_OVER].length!=0)||
this._f187||(this._f98[MVEvent.MOUSE_OUT]!=null&&this._f98[MVEvent.MOUSE_OUT].length!=0)||
(this._f98[MVEvent.MOUSE_MOVE]!=null&&this._f98[MVEvent.MOUSE_MOVE].length!=0)||
this._f229||this._f160||this.cursorStyle)
return true;
else
 return false;
}
MVFOI.prototype.destroy=function()
{
this.node=null;
this._f255=null;
this._f240=null;
}
function _f259(x0)
{
this._f260=[];
this.parent=x0;
var x1=null;
this.minX=0;
this.minY=0;
this.maxX=0;
this.maxY=0;
this._f24=0;
this._f25=0;
this._f53=false;
this.div=document.createElement("div");
this.width=0;
this.height=0;
this.count=0;
this._f28=this.parent._f95;
this._f29=this.parent._f96;
this._f97=null;
this._f98=new Array();
this.div.style.position="absolute";
this.div.style.zIndex=120;
this._f261=document.createElement("div");
this._f261.style.position="absolute";
this._f261.style.zIndex=180;
this.move_id=null;
this.time=20;
if(this.parent.div.appendChild)
{
this.parent.div.appendChild(this.div);
this.parent.div.appendChild(this._f261);
}
else
 {
document.body.appendChild(this.div);
document.body.appendChild(this._f261);
}
this.mouseL1=MVSdoGeometry.createPoint(0,0);
this.mouseL2=this.mouseL1;
this.maparea=null;
this.around=false;
}
_f259.prototype.foiToNewLocation=function(x126,x127,x128)
{
if(x128)
x126.time=x128;
else
 x126.time=this.time;
var x129=new Array();
var x130=new Array();
if(x127.getFirstPoint()){
x129.push(x127.getPointX());
x130.push(x127.getPointY());
}
else
 {
var x131=x127.getDimensions();
for(var x132=0;x132<=x127.getOrdinates().length-x131;x132=x132+x131)
{
x129.push(x127.getOrdinates()[x132]);
x130.push(x127.getOrdinates()[x132+1]);
}
}
var x133=0;
if(x129.length<=x133||x130.length<=x133)
return;
if(x126.move_id)
{
clearTimeout(x126.move_id);
x126.move_id=null;
}
x126.startX=x126.x;
x126.startY=x126.y;
x126.move_xarray=x129;
x126.move_yarray=x130;
x126.move_seq=x133;
x126.move_id=this.setTimeout("this.moveFoi(\""+x126.id+"\")",x126.time);
}
_f259.prototype.moveFoi=function(id)
{
var x134=this._f262(id);
var x135=x134.node;
if(x135 instanceof Array)
x135=x135[0];
var x136=parseInt(x135.style.left);
var x137=parseInt(x135.style.top);
var x138=x134.move_seq;
var x139=x134.move_xarray;
var x140=x134.move_yarray;
var x141=x139[x138];
if(x134.tx&&x134.tx.length>0)
x141+=x134.wr[0]*(this.parent.msi._f158-this.parent.msi._f159);
var x142=0;
x141=MVUtil.transLongitudeOnWarpAroungMap(this.parent,x134.x,x141);
if(this.around)
{
x142=x134.wr[0]*(this.parent.msi._f158-this.parent.msi._f159);
}
var x143=MVUtil._f157(this.parent,this.minX,this.minY,this.maxX,this.maxY,
this.width,this.height,x141+x142,x140[x138]);
var x144=(this.parent._f95-this._f28)*this.parent._f76;
var x145=(this.parent._f96-this._f29)*this.parent._f78;
x143[0]=Math.round(x143[0]-x144-x134.width/2);
x143[1]=Math.round(x143[1]+x145-x134.height/2);
var x146=x143[0]-x136;
var x147=x143[1]-x137;
var x148=Math.sqrt(x146*x146+x147*x147);
var x149=40;
if(x149>x148)
{
x135.style.left=MVUtil._f32(x143[0]);
x135.style.top=MVUtil._f32(x143[1]);
if(x134.node instanceof Array)
{
for(var x150=1;x150<x134.node.length;x150++)
{
x134.node[x150].style.left=MVUtil._f32(parseInt(x134.node[x150].style.left)+x146);
x134.node[x150].style.top=MVUtil._f32(parseInt(x134.node[x150].style.top)+x147);
}
}
clearTimeout(x134.move_id);
x134.move_id=null;
x134._f228.sdo_point.x=x139[x138];
x134._f228.sdo_point.y=x140[x138];
x134.x=x139[x138];
x134.y=x140[x138];
x138++;
if(x139.length<=x138||x140.length<=x138)
{
return;
}
if(x139.length>1)
x134.startX=x139[x138-1];
if(x140.length>1)
x134.startY=x140[x138-1];
x134.move_seq=x138;
x134.move_id=this.setTimeout("this.moveFoi(\""+id+"\")",x134.time);
}
else
 {
var x151=Math.round(x149*x146/x148);
var x152=Math.round(x149*x147/x148);
var x153=parseInt(x135.style.left)+x151;
var x154=parseInt(x135.style.top)+x152;
x135.style.left=MVUtil._f32(x153);
x135.style.top=MVUtil._f32(x154);
if(x134.node instanceof Array)
{
for(var x150=1;x150<x134.node.length;x150++)
{
x134.node[x150].style.left=MVUtil._f32(parseInt(x134.node[x150].style.left)+x151);
x134.node[x150].style.top=MVUtil._f32(parseInt(x134.node[x150].style.top)+x152);
}
}
x134.x=(x141-x134.x)/x146*x151+x134.x;
x134.y=(x141-x134.startY)*(x134.x-x134.startX)/(x141-x134.startX)+x134.startY;
if(this.around)
{
var x155=MVUtil.keepInCSBoundary(this.parent,x134.x);
var x156;
if(x155>x134.x)
x156=-1;
else if(x155<x134.x)
x156=1;
if(x156)
{
x134.x=x155;
if(x134.wr)
for(var x150=0;x150<x134.wr.length;x150++)
x134.wr[x150]+=x156;
}
}
else
 {
var x157=this.parent.msi._f158-this.parent.msi._f159;
if(x134.x<this.parent.msi._f159)
{
x134.x+=x157;
x135.style.left=MVUtil._f32(x153+x157*this.parent._f76);
}
else if(x134.x>this.parent.msi._f158)
{
x134.x-=x157;
x135.style.left=MVUtil._f32(x153-x157*this.parent._f76);
}
}
x134._f228.sdo_point.x=x134.x;
x134._f228.sdo_point.y=x134.y;
x134.move_id=this.setTimeout("this.moveFoi(\""+id+"\")",x134.time);
}
}
_f259.prototype.setTimeout=function(_f149,_f150)
{
var Ie="tempVar"+_f264;
_f264++;
eval(Ie+" = this;");
var oi=_f149.replace(/\\/g,"\\\\");
oi=oi.replace(/\"/g,'\\"');
return window.setTimeout(Ie+'._setTimeoutDispatcher("'+oi+'");'+Ie+" = null;",_f150);
}
_f259.prototype._setTimeoutDispatcher=function(func)
{
eval(func);
}
_f259.prototype.setSize=function(x158,x159)
{
this.width=x158;
this.height=x159;
}
_f259.prototype._f249=function(x160,x161)
{
var x162=x160.id;
for(var x163=0;x163<this._f260.length;x163++)
{
if(this._f260[x163].id==x162)
{
this._f260[x163].visible=x161;
if(!x161&&this.parent._f80._f81.length>0)
if(this.parent._f80._f81[0].nid==x162)
{
this.parent._f80.deleteInfoWindow(this.parent._f80._f81[0]);
}
}
}
var x164=x160.node
if(!x164&&x161&&(!x160.cloneFOI||!x160.cloneFOI.node))
{
if(this.parent._f265)
{
this.parent._f248._f266(x160,true);
}
return ;
}
if(x164 instanceof Array)
{
for(var x165=0;x165<x164.length;x165++)
{
if(x161)
x164[x165].style.visibility="visible";
else
 x164[x165].style.visibility="hidden";
}
}
else
 {
if(x164){
if(x161)
x164.style.visibility="visible";
else
 x164.style.visibility="hidden";
}
if(x160.cloneFOI&&x160.cloneFOI.node)
{
if(x161)
x160.cloneFOI.node.style.visibility="visible";
else
 x160.cloneFOI.node.style.visibility="hidden";
}
}
}
_f259.prototype._f53=function()
{
return this._f53;
}
_f259.prototype._f84=function()
{
if(this._f53)
return this._f24;
else
 return MVUtil._f84(this.div);
}
_f259.prototype._f85=function()
{
if(this._f53)
return this._f25;
else
 return MVUtil._f85(this.div);
}
_f259.prototype._f88=function()
{
return this.width;
}
_f259.prototype._f89=function()
{
return this.height;
}
_f259.prototype._f55=function()
{
return this.minX;
}
_f259.prototype._f56=function()
{
return this.minY;
}
_f259.prototype._f57=function()
{
return this.maxX;
}
_f259.prototype._f58=function()
{
return this.maxY;
}
_f259.prototype._f61=function()
{
return this.div;
}
_f259.prototype.getTopContainer=function()
{
return this._f261;
}
_f259.prototype._f267=function(x166)
{
this._f97=x166;
}
_f259.prototype._f268=function(x167)
{
for(var x168=0;x168<this.div.childNodes.length;x168++)
{
if(x167==(this.div.childNodes[x168]).id)
{
var x169=this.div.childNodes[x168];
this.div.removeChild(x169);
MVUtil._f92(x169);
return;
}
}
for(var x168=0;x168<this._f261.childNodes.length;x168++)
{
if(x167==(this._f261.childNodes[x168]).id)
{
var x169=this._f261.childNodes[x168];
this._f261.removeChild(x169);
MVUtil._f92(x169);
return;
}
}
}
_f259.prototype._f90=function()
{
var x170=this._f260.length;
for(var x171=0;x171<x170;x171++)
{
var x172=this._f260.pop();
MVUtil._f269(x172);
if(this.parent._f80._f81.length>0)
if(this.parent._f80._f81[0].nid==x172.id)
{
this.parent._f80.deleteInfoWindow(this.parent._f80._f81[0]);
}
}
MVUtil._f191(this.div,this.parent._f7);
MVUtil._f191(this._f261,this.parent._f7);
}
_f259.prototype._f256=function(x173)
{
var x174=null;
for(var x175=0;x175<this._f260.length;x175++)
{
if(this._f260[x175].id==x173)
{
x174=this._f260[x175];
if(x174.cloneFOI)
{
this._f256(x174.cloneFOI.id);
x174.cloneFOI.origFOI=null;
x174.cloneFOI._f239=null;
x174.cloneFOI=null;
}
if(this.parent._f80._f81.length>0)
if(this.parent._f80._f81[0].nid==x173)
{
this.parent._f80.deleteInfoWindow(this.parent._f80._f81[0]);
}
this._f260[x175]=this._f260[this._f260.length-1];
MVUtil._f269(this._f260.pop());
break;
}
}
if(x174)
{
if(x174.move_id!=null){
clearTimeout(x174.move_id);
x174.move_id=null;
}
if(x174.node)
{
var x176=x174.node
if(x176 instanceof Array)
{
for(var x177=0;x177<x176.length;x177++)
{
x176[x177].parentNode.removeChild(x176[x177]);
MVUtil._f92(x176[x177]);
}
}
else if(x174.node.parentNode)
{
x176.parentNode.removeChild(x176);
MVUtil._f92(x176);
}
}
x174.destroy();
for(var x178=0;x178<this._f261.childNodes.length;x178++)
{
if(this._f261.childNodes[x178].id==x173)
{
var x179=this._f261.childNodes[x178];
this._f261.removeChild(x179);
MVUtil._f92(x179);
x179=null;
break;
}
}
}
return x174;
}
_f259.prototype._f262=function(x180)
{
for(var x181=0;x181<this._f260.length;x181++)
{
if(x180==this._f260[x181].id)
{
return this._f260[x181];
}
}
return null;
}
_f259.prototype._f257=function(x182)
{
this._f260.push(x182);
if(this.parent._f265&&(x182.visible==true))
{
this._f266(x182,true);
}
}
_f259.prototype._f270=function(x183)
{
var x184=x183._f233[0];
var x185=x183._f233[1];
var x186=x183._f233[2];
var x187=x183._f233[3];
var x188=(this.minX==0?x184:this.minX);
var x189=(this.minY==0?x185:this.minY);
var x190=(this.maxX==0?x186:this.maxX);
var x191=(this.maxY==0?x187:this.maxY);
var x192=x184<x188?x188:x184;
var x193=x185<x189?x189:x185;
var x194=x186<x190?x186:x190;
var x195=x187<x191?x187:x191;
var x196;
if(this.around)
x196=Math.abs(x186-x184);
else
 x196=Math.abs(x194-x192);
var x197=Math.abs(x195-x193);
var x198={"x":x192,"y":x195,"width":x196,"height":x197}
return x198;
}
_f259.prototype._f266=function(x199,x200)
{
if(!x199._f228.srid)
x199._f228.srid=this.parent.getSrid();
var x201=this;
var x202=false;
var x203=function(x204)
{
if(x204)
x199._f228=x204;
if(x199.gtype%10==1)
{
x199._f232=x199._f228.getFirstPoint();
x199._f232=MVUtil.keepOrdinatesInCSBoundary(x201.parent,x199._f232);
x199._f228.sdo_ordinates=x199._f232;
if(x199._f232.length>0)
x199._f228.sdo_point.x=x199._f232[0];
}
else
 {
x199.elemInfo=x199._f228.sdo_elem_info.toString().split(",");
var x205=x199._f228.sdo_ordinates;
if(!x199.origFOI)
{
x205=MVUtil.keepOrdinatesInCSBoundary(x201.parent,x205);
if(x201.parent.wraparound)
{
x199.origgeom=x199._f228.clone();
x199._f228.sdo_ordinates=x205;
var x206=x199._f228.getOrdinatesOfElements();
x205=new Array();
for(var x207=0;x207<x206.length;x207++)
x205=x205.concat(MVUtil.transOrdinatesOnWarpAroungMap(x201.parent,x206[x207]));
x199._f228.sdo_ordinates=x205;
}
else if(!x201.parent.wraparound&&MVUtil.crossDateLine(x201.parent,x205))
{
x199.origgeom=x199._f228.clone();
var x206=x199._f228.getOrdinatesOfElements();
x205=new Array();
for(var x207=0;x207<x206.length;x207++)
x205=x205.concat(MVUtil.transOrdinatesOnWarpAroungMap(x201.parent,x206[x207]));
x199._f228.sdo_ordinates=x205;
x202=true;
}
}
x205=x205.toString();
while(x205.indexOf(" ")!=-1)
{
x205=MVUtil._f49(x205," ","");
}
for(var x208=0;x208<x199.elemInfo.length;x208+=3){
if(x199.elemInfo[x208+2]*1==3){
x205=x199._f250(x205);
break;
}
}
x199._f232=x205.split(",");
}
x199._f233=x199.getMBR();
if(x202&&x199._f232.length>0&&!x199.cloneFOI)
{
var x209=x199._f228.clone();
x209.sdo_ordinates=MVUtil.duplicateCrossDateLineOrds(x201.parent,x199._f232,x199._f233);
var x210=MVUtil.cloneObject(x199);
x210.id="-CL"+x199.id;
x210._f228=x209;
x210.origFOI=x199;
x199.cloneFOI=x210;
x201._f257(x210);
}
x199.x=x199._f233[0];
x199.y=x199._f233[3];
if(x199.gtype%10!=1)
{
x199.width=x199._f233[2]-x199._f233[0];
x199.height=x199._f233[3]-x199._f233[1];
}
x201._f271(x199,x200);
}
if(x199._f228.srid&&(x199._f228.srid!=this.parent.srid))
this.parent.transformGeom(x199._f228,this.parent.srid,null,x203);
else
 x203();
}
_f259.prototype._f271=function(foi,check)
{
var styleStr=null;
if(foi.style)
{
if(foi.style.getXMLString)
styleStr=foi.style.getXMLString();
else
 styleStr=foi.style;
}
if(foi.gtype%10==1)
{
if(styleStr)
foi._f131=foi._f3+"?request=getpoiimage&version=1.0"+
"&poistyle="+encodeURIComponent(styleStr)+
"&width="+foi.width+"&height="+foi.height+
this.getImageParameters(foi);
if(this.around||foi.wr)
{
foi.wr=new Array();
if(foi.areacode==null)
{
var n=0;
var bbminx=this.parent.msi._f159;
var bbmaxx=this.parent.msi._f158;
for(var i=0;i<this.maparea.length;i++){
if(foi.x>this.maparea[i].minx&&foi.x<this.maparea[i].maxx){
foi.wr.push(this.maparea[i].n);
}
}
}
else{
var n=this.maparea[0].n;
n+=foi.areacode;
foi.wr.push(n)
}
}
else
 foi.wr=null;
this._f272(foi);
return;
}
if(_f11._f70=="IF")
styleStr=encodeURIComponent(styleStr);
var _f273=foi._f228.toString();
var url;
var reqParas;
if(this.around||foi.wr)
this._f132(foi);
else
 foi.wr=null;
var reqMinX=this.minX;
var reqMaxX=this.maxX;
if(foi.wr&&foi.wr.length>0)
{
var coverWidth=this.parent.msi._f158-this.parent.msi._f159;
var tOrds=foi._f228.sdo_ordinates;
var crossdl=MVUtil.crossDateLine(this.parent,tOrds);
var middle=this.parent.msi._f159+coverWidth/2;
if(crossdl){
if((this.maparea.length>1||(this.maparea.length==1&&foi._f233[2]<this.maparea[0].minx))
&&foi.x<middle&&foi.x<this.parent.msi._f159){
foi.wr[0]++;
}
else if((this.maparea.length==1&&foi._f233[0]>this.maparea[0].maxx)
&&(foi.x>middle||foi._f233[2]>middle))
{
foi.wr[0]--;
}
if(foi.width>(this.maxX-this.minX)){
reqMinX=this.minX-foi.wr[0]*coverWidth;
reqMaxX=this.maxX-foi.wr[0]*coverWidth;
if(reqMinX>foi.x)
foi.x=reqMinX;
}else{
reqMinX=foi._f233[0];
reqMaxX=foi._f233[2];
}
}else if(!crossdl&&foi.width>(this.maxX-this.minX)){
reqMinX=this.minX-foi.wr[0]*coverWidth;
reqMaxX=this.maxX-foi.wr[0]*coverWidth;
if(reqMinX>foi.x)
foi.x=reqMinX;
}else{
reqMinX=foi._f233[0];
reqMaxX=foi._f233[2];
}
}
else
 {
if(this.maxX>this.parent.msi._f158)
reqMaxX=this.parent.msi._f158;
if(this.minX<this.parent.msi._f159)
reqMinX=this.parent.msi._f159;
if(foi._f233[2]>this.parent.msi._f158)
foi._f233[2]=this.parent.msi._f158;
if(foi._f233[0]<this.parent.msi._f159)
{
foi._f233[0]=this.parent.msi._f159;
foi.x=foi._f233[0];
}
}
var reqMinY=this.minY;
var reqMaxY=this.maxY;
if(this.maxY>this.parent.msi._f274)
reqMaxY=this.parent.msi._f274;
if(this.minY<this.parent.msi._f275)
reqMinY=this.parent.msi._f275;
if(foi._f233[1]<this.parent.msi._f275)
foi._f233[1]=this.parent.msi._f275;
if(foi._f233[3]>this.parent.msi._f274)
{
foi._f233[3]=this.parent.msi._f274;
foi.y=foi._f233[3];
}
reqParas="&version=1.0"+"&figstyle="+encodeURIComponent(styleStr)+
"&ratx="+this.parent._f76+"&raty="+
this.parent._f78+'&bbox='+
reqMinX+':'+reqMinY+':'+reqMaxX+':'+reqMaxY+
this.getImageParameters(foi);
url=foi._f3+"?request=getfigimage"+reqParas+"&figord="+_f273;
if(url.length<_f11._f276&&
(!foi._f258()||this._f69(foi))){
foi._f131=url;
this._f272(foi);
return;
}
try
{
var _f277=this;
var localDomain=(MVUtil._f9(foi._f3)==MVUtil._f9(_f11._f119()));
var xmlHttp=localDomain||MVMapView._f8;
var _f278=MVUtil.getXMLHttpRequest(xmlHttp);
if(MVMapView._f8&&!localDomain)
foi._f3=_f11._f12()+"?rtarget="+foi._f3;
_f278.open("POST",foi._f3,true);
_f278.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
_f278.onreadystatechange=MVUtil._f106(_f278,function()
{
try
{
if(this.readyState!=4){
return;
}
if(this.status==200)
{
if(this.responseText.indexOf("imgurl")>=0)
{
var res=null;
eval("res="+this.responseText);
foi._f131=res.imgurl;
if(res.area&&res.area!="")
foi.area=res.area;
if(check)
{
if(foi._f234)
for(var td=0;td<_f277._f261.childNodes.length;td++)
{
if(_f277._f261.childNodes[td].id==foi.id)
{
_f277._f261.removeChild(_f277._f261.childNodes[td]);
break;
}
}
else
 for(var td=0;td<_f277.div.childNodes.length;td++)
{
if(_f277.div.childNodes[td].id==foi.id)
{
_f277.div.removeChild(_f277.div.childNodes[td]);
break;
}
}
}
_f277._f272(foi);
}
}
}
catch(e)
{
MVi18n._f6("MVIndFOIsControl.postAddAndDisplayIndFOI","MAPVIEWER-05500",e,
_f277.parent?_f277.parent._f7:null);
}
});
_f278.send("request=getfig"+reqParas+"&figord="+_f273);
}
catch(ex)
{
MVi18n._f6("MVIndFOIsControl.postAddAndDisplayIndFOI","MAPVIEWER-05511",ex,
this.parent?this.parent._f7:null);
}
}
_f259.prototype._f279=function()
{
return this._f260;
}
_f259.prototype._f280=function(x211,x212,x213,x214)
{
this._f53=true;
this.parent._f126();
this.minX=x211;
this.minY=x212;
this.maxX=x213;
this.maxY=x214;
this._f28=this.parent._f95;
this._f29=this.parent._f96;
this._f24=Math.ceil((this.minX-this.parent._f75)*this.parent._f76);
this._f25=-Math.ceil((this.maxY-this.parent._f77)*this.parent._f78);
MVUtil._f74(this.div,this._f24,this._f25);
MVUtil._f74(this._f261,this._f24,this._f25);
MVUtil._f191(this.div,this.parent._f7);
MVUtil._f191(this._f261,this.parent._f7);
this._f99();
this.around=this.parent.wrapAroundLayer(this._f88());
if(this._f97)
this._f97;
MVUtil.runListeners(this._f98,"refreshFOI");
MVUtil._f191(this.div,this.parent._f7);
MVUtil._f191(this._f261,this.parent._f7);
for(var x215=0;x215<this._f260.length;x215++)
{
var x216=this._f260[x215];
x216.node=null;
if(x216.move_id!=null){
clearTimeout(x216.move_id);
}
this._f266(x216,true);
}
}
_f259.prototype._f247=function(x217)
{
var x218=this;
if(x217._f73)
{
var x219=function(x220)
{
if(x217._f131&&x217.gtype%10!=1&&!x218._f69(x217))
{
var x221="0,0";
if(x217.area.length>0)
x221=x217.area;
x220.setAttribute("shape","poly");
x220.setAttribute("coords",x221);
if((x217._f178||(x217._f98[MVEvent.MOUSE_CLICK]!=null&&x217._f98[MVEvent.MOUSE_CLICK].length!=0)||((x217._f235&&x217._f229)))
&&_f11._f70!="OS")
{
x220.setAttribute("href","javascript:void(0)");
}
}
if(x217._f178||(x217._f98[MVEvent.MOUSE_CLICK]!=null&&x217._f98[MVEvent.MOUSE_CLICK].length!=0)||(x217._f235&&x217._f229))
{
MVUtil._f164(x217._f255,"pointer");
}
x220.onmousedown=x218.getMouseDownLocation;
x220.onmouseup=x218.IndMouseUp;
x220.oncontextmenu=x218.IndRightClick;
}
if(x217._f255 instanceof Array)
{
for(var x222=0;x222<x217._f255.length;x222++)
x219(x217._f255[x222]);
}
else
 x219(x217._f255)
}
if(x217.cursorStyle)
{
if(x217._f255 instanceof Array)
{
for(var x222=0;x222<x217._f73.length;x222++)
MVUtil._f164(x217._f255[x222],x217.cursorStyle);
}
else
 MVUtil._f164(x217._f255,x217.cursorStyle);
}
}
_f259.prototype._f272=function(x223)
{
x223._f93=true;
x223._f239=this;
var x224;
if(x223.wr&&x223.wr.length>0)
x224=MVUtil._f157(this.parent,this.minX,this.minY,this.maxX,this.maxY,
this.width,this.height,x223.x+x223.wr[0]*(this.parent.msi._f158-this.parent.msi._f159),
x223.y);
else
 x224=MVUtil._f157(this.parent,this.minX,this.minY,this.maxX,this.maxY,
this.width,this.height,x223.x,x223.y);
var x225=(this.parent._f95-this._f28)*this.parent._f76;
var x226=(this.parent._f96-this._f29)*this.parent._f78;
var x227;
var x228=MVUtil._f5(x223._f131.toLowerCase(),'png');
if(!(_f11._f70=='IF'&&(x223.style!=""||x228))&&!x223.html)
{
if(x223.gtype%10==1)
x227=document.createElement('img');
else
 x227=document.createElement('div');
}
else
 x227=document.createElement('div');
if(x223.visible)
x227.style.visibility="visible";
else
 x227.style.visibility="hidden";
x227.style.position="absolute";
var x229=false;
for(var x230=0;x230<this.parent._f135.length;x230++)
{
if(this.parent._f135[x230].layerControl&&this.parent._f135[x230].layerControl.zoomControl&&
!this.parent._f135[x230].layerControl.zoomControl.hasZoomFinished())
{
x229=true;
x227.style.display='none';
break;
}
}
x227.ifctl=this;
var x231=this;
var x232=x223._f172;
x223._f255=x227;
if(x223._f131)
{
x227.className="noprint";
if(!x223.visible)
x227.style.visibility="hidden";
if(x223.id&&x223.id!=null)
x227.id=x223.id;
x227._f155=x223.x;
x227._f156=x223.y;
x227.style.zIndex=x223._f244;
if(x223._f234)
this._f261.appendChild(x227);
else
 this.div.appendChild(x227);
if(x223.gtype%10!=1)
{
this.displayPolygonIndFOI(x227,x224,x225,x226,x223);
}
else
 {
var x233=function()
{
if(x223._f246)
{
setTimeout(x233,200);
return ;
}
x231.displayPointIndFOI(x227,x224,x225,x226,x223);
}
x233();
}
}
else if(x223.gtype%10==1)
{
x227.style.zIndex=x223._f244;
this.displayHTMLIndFOI(x227,x224,x225,x226,x223);
}
x223.node=x227;
this._f167(x227,x223,x231);
if(x223.move_id!=null)
{
var x234=MVUtil._f157(this.parent,this.minX,this.minY,this.maxX,this.maxY,
this.width,this.height,x223.move_xarray[x223.move_seq],x223.move_yarray[x223.move_seq]);
var x235=Math.round(x234[0]-x223.width/2);
var x236=Math.round(x234[1]-x223.height/2);
x223.move_id=this.setTimeout("this.moveFoi(\""+x223.id+"\")",x223.time);
}
}
_f259.prototype._f40=function()
{
var x237="";
for(var x238=0;x238<this._f260.length-1;x238++)
{
for(var x239=1;x239<this._f260.length;x239++)
{
if(this._f260[x238]._f234&&!this._f260[x239]._f234)
{
var x240=this._f260[x238];
this._f260[x238]=this._f260[x239];
this._f260[x239]=x240;
}
}
}
for(var x238=0;x238<this._f260.length;x238++)
{
if((this._f260[x238].style||this._f260[x238].createMarker)
&&this._f260[x238].visible)
{
var x241=this._f260[x238].createMarker?
"indfoi_"+this._f260[x238].id+"_style":
this._f260[x238].style.name==null?
MVUtil._f45(this._f260[x238].style):
this._f260[x238].style.name;
x237+=this._f282(this._f260[x238]._f228,x241);
}
}
return x237;
}
_f259.prototype._f282=function(x242,x243)
{
var x244=x242.toGML();
if(x243.getXMLString)
x243=x243.name;
var x245="<geoFeature render_style=\""+x243+"\"><geometricProperty>"+x244+"</geometricProperty></geoFeature>"
return x245;
}
_f259.prototype.destroy=function()
{
this._f90();
this._f260=null;
this.div=null;
this._f261=null;
}
_f259.prototype._f66=function(x246)
{
return x246._f67?x246._f67:this.parent._f68;
}
_f259.prototype._f69=function(x247)
{
return _f11._f70=="IF"&&this._f66(x247)=="PNG24";
}
_f259.prototype.getImageParameters=function(x248)
{
var x249="";
var x250=this._f66(x248);
if(x250=="PNG8")
x249+="&format="+x250;
if(x248.bgColor)
x249+="&bgcolor="+x248.bgColor;
return x249;
}
_f259.prototype.displayHTMLIndFOI=function(x251,x252,x253,x254,x255)
{
var x256=x251.ifctl;
x251.id=x255.id;
x251.style.width=MVUtil._f32(x255.width);
x251.style.height=MVUtil._f32(x255.height);
x252[0]-=x253;
x252[1]+=x254;
MVUtil._f74(x251,x252[0]-x255.width/2,x252[1]-x255.height/2);
this._f261.appendChild(x251);
x251.onkeyup=x256.IndStopPropagation;
if(_f11._f70=="OS")
x251.onkeypress=x256.IndStopPropagation;
else
 {
x251.onkeydown=x256.IndStopPropagation;
x251.oncontextmenu=x256.IndStopPropagation;
}
if(_f11._f70=="IF")
x251.onselectstart=x256.IndStopPropagation;
if(x255._f160&&x255._f236&&_f11._f70!="OS")
{
x251.title=x255._f160;
}
this.setIndFOIListners(x255,x251);
if(x255.html)
{
var x257=document.createElement("div");
x257.style.position="absolute";
var x258=x255._f230;
var x259=x255._f231;
x257.style.left=MVUtil._f32(x258);
x257.style.top=MVUtil._f32(x259);
x257.style.padding=MVUtil._f32(0);
x255.foiDiv=x257;
x257.innerHTML=x255.html;
x251.appendChild(x257);
}
}
_f259.prototype.displayPolygonIndFOI=function(x260,x261,x262,x263,x264,x265)
{
var x266=this;
var x267=this._f270(x264);
x260.style.width=MVUtil._f32(Math.round(x267.width*this.parent._f76+0.5));
x260.style.height=MVUtil._f32(Math.round(x267.height*this.parent._f78+0.5));
var x268;
var x269;
var x270=x264._f233;
if(x264.wr&&x264.wr.length>0)
{
var x271=this.parent.msi._f158-this.parent.msi._f159;
x270[0]+=x264.wr[0]*x271;
x270[2]+=x264.wr[0]*x271;
}
if((x270[0]>this.maxX||x270[2]<this.minX||
x270[1]>this.maxY||x270[3]<this.minY)&&!this.around)
{
return;
}
if(x264.wr&&x264.wr.length>0)
x268=x261[0];
else if(x261[0]>-this.parent._f140&&x261[0]<-this.parent._f140+this.width)
{
x268=x261[0];
}
else if(x261[0]<-this.parent._f140)
{
x268=-this.parent._f140;
}
else {
return;
}
if(x261[1]>-this.parent._f141&&x261[1]<-this.parent._f141+this.height)
{
x269=x261[1];
}
else if(x261[1]<-this.parent._f141)
{
x269=-this.parent._f141;
}
else {
return;
}
x268=Math.floor(x268+0.5);
x269=Math.floor(x269+0.5);
x268-=x262;
x269+=x263;
MVUtil._f74(x260,x268,x269);
if(this._f69(x264))
{
var x272=MVUtil._f49(x264._f131,"(","%28");
x272=MVUtil._f49(x272,")","%29");
x260.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(src='"+
x272+"', sizingMethod='image');";
if(x264._f160&&x264._f236)
{
x260.setAttribute("title",x264._f160);
}
this.setIndFOIListners(x264,x260);
x260.onkeyup=x266.IndStopPropagation;
if(_f11._f70=="OS")
x260.onkeypress=x266.IndStopPropagation;
else
 x260.onkeydown=x266.IndStopPropagation;
}
else
 {
var x273=_f284(x264._f131,0,0,0,0,10);
x260.appendChild(x273);
x264.node=x260;
if(!x264._f258())
return ;
x264._f254(x266.parent);
var x274=document.createElement("map");var x275=document.createElement("area");
x274.setAttribute("name","f"+x264.id);
x274.setAttribute("id","f"+x264.id);
x275.setAttribute("border",0);
if(x264._f160&&x264._f236)
{
if(_f11._f70!="OS")
x275.setAttribute("title",x264._f160);
}
x264._f255=x275;
var x276="0,0";
if(x264._f73&&x264._f258())
{
if(x264.area.length>0)
x276=x264.area;
if((x264._f178||(x264._f98[MVEvent.MOUSE_CLICK]!=null&&x264._f98[MVEvent.MOUSE_CLICK].length!=0)||((x264._f235&&x264._f229)))
&&_f11._f70!="OS")
x275.setAttribute("href","javascript:void(0)");
}
x275.setAttribute("shape","poly");
x275.setAttribute("coords",x276);
this.setIndFOIListners(x264,x275);
x273.setAttribute("usemap","#f"+x264.id,0);
x260.style.border=0;
x260.appendChild(x274);
x274.appendChild(x275);
}
}
_f259.prototype.displayPointIndFOI=function(x277,x278,x279,x280,x281)
{
var x282=this;
x278[0]-=x279;
x278[1]+=x280;
MVUtil._f74(x277,x278[0]-x281.width/2,x278[1]-x281.height/2);
var x283=MVUtil._f5(x281._f131.toLowerCase(),'png');
var x284=function(x285,x286)
{
var x287=document.createElement("div");
x287.style.position="absolute";
var x288=x285._f230;
var x289=x285._f231;
x287.style.left=MVUtil._f32(x288);
x287.style.top=MVUtil._f32(x289);
x287.style.padding=MVUtil._f32(0);
x285.foiDiv=x287;
x287.innerHTML=x285.html;
x286.appendChild(x287);
}
if((_f11._f70=='IF')&&
(x281.style!=""||x283))
{
if(!x281.width)
x277.style.width=MVUtil._f32(100);
else
 x277.style.width=MVUtil._f32(x281.width);
if(!x281.height)
x277.style.height=MVUtil._f32(100);
else
 x277.style.height=MVUtil._f32(x281.height);
var x290=MVUtil._f49(x281._f131,"(","%28");
x290=MVUtil._f49(x290,")","%29");
if(x281._f160&&x281._f236&&_f11._f70!="OS")
{
x277.setAttribute("title",x281._f160);
}
x277.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(src='"+
x290+"', sizingMethod='image');";
if(x281.html)
{
x284(x281,x277);
}
this.setIndFOIListners(x281,x277);
}
else
 {
if(x281._f160&&x281._f236&&_f11._f70!="OS")
{
x277.setAttribute("title",x281._f160);
}
if(x281.html)
{
var x291=document.createElement("img");
x281._f240=x291;
x291.style.position="relative";
x291.style.left=MVUtil._f32(0);
x291.style.top=MVUtil._f32(0);
x291.setAttribute("id",x281.id);
x291.src=x281._f131;
x291.setAttribute("border",0);
x291.foi=x277.foi;
x291.ifctl=x277.ifctl;
x277.appendChild(x291);
x284(x281,x277);
x282.setIndFOIListners(x281,x281._f240);
}
else
 {
x277.src=x281._f131;
x277.setAttribute("border",0);
x282.setIndFOIListners(x281,x277);
}
}
}
_f259.prototype.IndStopPropagation=function(x292)
{
x292=(x292)?x292:((window.event)?event:null);
var x293=MVUtil.getEventSource(x292);
var x294=MVUtil.getMVIndFOIsControlAndFOI(x293);
var x295=x294.ifctl;
if(!x295)
return;
var x296=x294.foi;
if(x292&&x296&&!x296._f172)
{
if(_f11._f70=="IF")
x292.cancelBubble=true;
else if(x292.stopPropagation)
x292.stopPropagation();
}
}
_f259.prototype.IndStopEvent=function(x297)
{
x297=(x297)?x297:((window.event)?event:null);
var x298=MVUtil.getEventSource(x297);
var x299=MVUtil.getMVIndFOIsControlAndFOI(x298);
var x300=x299.ifctl;
if(!x300)
return;
var x301=x299.foi;
if(x297&&x301&&!x301._f172)
{
MVUtil._f173(x297);
}
}
_f259.prototype.getMouseDownLocation=function(x302)
{
x302=(x302)?x302:((window.event)?event:null);
var x303=MVUtil.getEventSource(x302);
var x304=MVUtil.getMVIndFOIsControlAndFOI(x303);
var x305=x304.ifctl;
if(!x305)
return;
var x306=x304.foi;
x305.mouseL1=MVUtil._f174(x302);
x305.IndStopPropagation(x302);
}
_f259.prototype.IndMouseUp=function(x307)
{
x307=(x307)?x307:((window.event)?event:null);
var x308=MVUtil.getEventSource(x307);
var x309=MVUtil.getMVIndFOIsControlAndFOI(x308);
var x310=x309.ifctl;
if(!x310)
return;
var x311=x309.foi;
x310.mouseL2=MVUtil._f174(x307);
if(x310.mouseL1.x==x310.mouseL2.x&&x310.mouseL1.y==x310.mouseL2.y)
{
if(x307.button==2)
return;
if(!x311._f285&&x310.parent._f175)
return;
var x312=MVUtil.getMouseLocation(x310.parent,x307);
if(x311._f178||(x311._f98[MVEvent.MOUSE_CLICK]!=null&&x311._f98[MVEvent.MOUSE_CLICK].length!=0))
{
x310.parent._f176();
var x313=MVSdoGeometry.createPoint(x312.sdo_point.x,x312.sdo_point.y,x310.parent.srid);
if(x311._f178)
x311._f178(x313,x311,MVUtil.getEvent(x307));
MVUtil.runListeners(x311._f98,MVEvent.MOUSE_CLICK,[x313,x311,MVUtil.getEvent(x307)]);
}
else if(x311._f235&&x311._f229)
{
var x314=new MVInfoWindowTab(x311._f242,x311._f229);
var x315=new Array(x314);
if(x311._f237>0&&x311._f238>0)
x310.parent._f80._f286(x311.id,x312.sdo_point.x,x312.sdo_point.y,
x315,0,x311._f237,x311._f238,x311._f241,x311._f242);
else
 x310.parent._f80._f286(x311.id,x312.sdo_point.x,x312.sdo_point.y,
x315,0,null,null,x311._f241,x311._f242);
return;
}
}
x310.IndStopEvent(x307);
}
_f259.prototype.IndMouseOver=function(x316)
{
x316=(x316)?x316:((window.event)?event:null);
var x317=MVUtil.getEventSource(x316);
var x318=MVUtil.getMVIndFOIsControlAndFOI(x317);
var x319=x318.ifctl;
if(!x319)
return;
var x320=x318.foi;
var x321=MVUtil.getMouseLocation(x319.parent,x316);
var x322=MVSdoGeometry.createPoint(x321.sdo_point.x,x321.sdo_point.y,x319.parent.srid);
if(x320._f182)
{
x320._f182(x322,x320,MVUtil.getEvent(x316));
}
MVUtil.runListeners(x320._f98,MVEvent.MOUSE_OVER,[x322,x320,MVUtil.getEvent(x316)]);
if(x320._f185)
{
var x323=x320.node
if(x323 instanceof Array)
{
x320._f245=new Array();
for(var x324=0;x324<x323.length;x324++)
{
x320._f245.push(x323[x324].style.zIndex);
x323[x324].style.zIndex=1000;
}
}
else
 {
if(x323){
x320._f245=x320.node.style.zIndex;
x320.node.style.zIndex=1000;
}
if(x320.cloneFOI&&x320.cloneFOI.node)
{
x320.cloneFOI.node.style.zIndex=1000;
}
}
}
if(x320._f160&&x320._f236&&_f11._f70=="OS")
x319.parent._f80.showTextTip(x320._f160,x321.sdo_point.x,x321.sdo_point.y);
};
_f259.prototype.IndMouseOut=function(x325)
{
x325=(x325)?x325:((window.event)?event:null);
var x326=MVUtil.getEventSource(x325);
var x327=MVUtil.getMVIndFOIsControlAndFOI(x326);
var x328=x327.ifctl;
if(!x328)
return;
var x329=x327.foi;
var x330=MVUtil.getMouseLocation(x328.parent,x325);
var x331=MVSdoGeometry.createPoint(x330.sdo_point.x,x330.sdo_point.y,x328.parent.srid);
if(x329._f187)
{
x329._f187(x331,x329,MVUtil.getEvent(x325));
}
MVUtil.runListeners(x329._f98,MVEvent.MOUSE_OUT,[x331,x329,MVUtil.getEvent(x325)]);
if(x329._f185)
{
var x332=x329.node
if(x332 instanceof Array)
{
for(var x333=0;x333<x332.length;x333++)
{
x332[x333].style.zIndex=x329._f245[x333];
}
}
else
 {
if(x332)
x332.style.zIndex=x329._f245;
if(x329.cloneFOI&&x329.cloneFOI.node)
{
x329.cloneFOI.node.style.zIndex=x329._f245
}
}
}
if(x329._f160)
x328.parent._f80._f188();
};
_f259.prototype.IndRightClick=function(x334)
{
x334=(x334)?x334:((window.event)?event:null);
var x335=MVUtil.getEventSource(x334);
var x336=MVUtil.getMVIndFOIsControlAndFOI(x335);
var x337=x336.ifctl;
if(!x337)
return;
var x338=x336.foi;
if(x338._f181||(x338._f98[MVEvent.MOUSE_RIGHT_CLICK]!=null&&x338._f98[MVEvent.MOUSE_RIGHT_CLICK].length!=0))
{
var x339=MVUtil.getMouseLocation(x337.parent,x334);
var x340=MVSdoGeometry.createPoint(x339.sdo_point.x,x339.sdo_point.y,x337.parent.srid);
if(x338._f181)
x338._f181(x340,x338,MVUtil.getEvent(x334));
MVUtil.runListeners(x338._f98,MVEvent.MOUSE_RIGHT_CLICK,[x340,x338,MVUtil.getEvent(x334)]);
}
x337.IndStopEvent(x334);
}
_f259.prototype._f99=function()
{
this.maparea=new Array();
this.parent._f126();
var x341=(this._f88()-
this.parent._f109())*0.5/this.parent._f76;
var x342=this.parent._f75-x341;
var x343=this.parent._f287+x341;
if(this.parent.msi!=null)
{
var x344=this.parent.msi._f159;
var x345=this.parent.msi._f158;
var x346=x345-x344;
var x347=0;
if(x342<x344)
{
x347=Math.ceil((x344-x342)/x346);
x342+=x346*x347;
x343+=x346*x347;
x347=0-x347;
}
else if(x342>x345)
{
x347=Math.ceil((x342-x345)/x346);
x342-=x346*x347;
x343-=x346*x347;
}
if(x343<=x345){
this.maparea.push({minx:x342,maxx:x343,n:x347});
}
else{
this.maparea.push({minx:x342,maxx:x345,n:x347});
x343-=x346;
x347++;
while(x343>x345)
{
this.maparea.push({minx:x344,maxx:x345,n:x347});
x343-=x346;
x347++;
}
this.maparea.push({minx:x344,maxx:x343,n:x347});
}
}
var x348=this.maparea.length;
var x349=parseInt(x348/2);
if(x348>0&&x349==x348/2)
{
var x350=this.maparea[0].maxx-this.maparea[0].minx;
var x351=this.maparea[x348-1].maxx-this.maparea[x348-1].minx;
if(x350>x351)
this.centerArea=x348/2-1;
else
 this.centerArea=x348/2
}
else
 this.centerArea=Math.floor(x348/2);
}
_f259.prototype.setIndFOIListners=function(x352,x353)
{
if(x352.origFOI)
x352=x352.origFOI;
x353.onmouseover=this.IndMouseOver;
x353.onmouseout=this.IndMouseOut;
x353.ondblclick=this.IndStopEvent;
x353.onclick=this.IndStopEvent;
x353.onmousemove=this.IndMouseMove;
if(x352._f73&&x352._f258())
{
if((x352._f98[MVEvent.MOUSE_CLICK]!=null&&x352._f98[MVEvent.MOUSE_CLICK].length!=0)||x352._f178||((x352._f235&&x352._f229)))
MVUtil._f164(x353,"pointer")
x353.onmousedown=this.getMouseDownLocation;
x353.onmouseup=this.IndMouseUp;
x353.oncontextmenu=this.IndRightClick;
}
if(x352.cursorStyle)
MVUtil._f164(x353,x352.cursorStyle);
MVUtil._f163(x353);
}
_f259.prototype._f132=function(x354)
{
x354.wr=new Array();
if(x354.areacode!=null)
{
var x355=this.maparea[0].n;
x355+=x354.areacode;
x354.wr.push(x355)
return;
}
var x355=0;
var x356=this.parent.msi._f159;
var x357=this.parent.msi._f158;
var x358=x357-x356;
var x359=this.parent.msi._f159+x358/2;
var x360=x354._f233[2];
var x361=x354._f233[0];
var x362=false;
if(x361<x356||x360>x357)
{
x354.wr.push(this.maparea[0].n);
if(x361<x359&&x360<x359)
{
for(var x363=1;x363<this.maparea.length;x363++)
{
if(this.maparea[x363].maxx>(x361+x358))
x354.wr.push(this.maparea[x363].n);
}
}
else{
for(var x363=1;x363<this.maparea.length;x363++)
{
if(this.maparea[x363].maxx>x361)
x354.wr.push(this.maparea[x363].n);
}
}
}
else
 {
for(var x363=0;x363<this.maparea.length;x363++)
{
if((x361>this.maparea[x363].minx&&x361<this.maparea[x363].maxx)||(x360>this.maparea[x363].minx&&x360<this.maparea[x363].maxx)
||(x361<this.maparea[x363].minx&&x360>this.maparea[x363].maxx))
x354.wr.push(this.maparea[x363].n);
}
}
}
_f259.prototype._f167=function(x364,x365,x366,x367)
{
var x368=parseInt(x364.style.left);
if(x365.wr&&x365.wr.length>1)
{
var x369=false;
if(x365._f240)
{
x369=true;
}
if(!x367)
{
x367=1;
}
if(x367==1)
{
x365.node=new Array(x364);
x365._f255=new Array(x365._f255);
if(x369)
{
x365._f240=new Array(x365._f240);
}
}
for(var x370=x367;x370<x365.wr.length;x370++)
{
var x371=x364.cloneNode(true);
x371.id=x371.id+"clone"+x370;
x371.style.left=MVUtil._f32(x368+x370*(this.parent.msi._f158-this.parent.msi._f159)*this.parent._f76);
if(x365._f234)
this._f261.appendChild(x371);
else
 this.div.appendChild(x371);
x371.ifctl=x366;
x371.orgid=x364.id;
if(x369&&x371.childNodes.length>0)
{
x365._f240.push(x371.childNodes[0]);
}
if(x365.gtype%10==1||this._f69(x365)||!x365.area)
{
this.setIndFOIListners(x365,x371);
x365._f255.push(x371);
}
else if(x371.childNodes.length>1)
{
var x372=x371.childNodes[0];
var x373=x371.childNodes[1];
this.setIndFOIListners(x365,x373);
x372.setAttribute("usemap","#f"+x365.id+"clone"+x370,0);
x373.setAttribute("name","f"+x365.id+"clone"+x370);
x373.setAttribute("id","f"+x365.id+"clone"+x370);
if(x373.childNodes.length>0)
{
x365._f255.push(x373.childNodes[0]);
}
}
x365.node.push(x371);
}
}
}
_f259.prototype.detachEventListener=function(x374,x375)
{
MVUtil.detachEventListener(this._f98,x374,x375);
}
_f259.prototype.attachEventListener=function(x376,x377)
{
MVUtil.attachEventListener(this._f98,x376,x377)
}
_f259.prototype.IndMouseMove=function(x378)
{
x378=(x378)?x378:((window.event)?event:null);
var x379=MVUtil.getEventSource(x378);
var x380=MVUtil.getMVIndFOIsControlAndFOI(x379);
var x381=x380.ifctl;
if(!x381)
return;
var x382=x380.foi;
var x383=MVUtil.getMouseLocation(x381.parent,x378);
var x384=MVSdoGeometry.createPoint(x383.sdo_point.x,x383.sdo_point.y,x381.parent.srid);
MVUtil.runListeners(x382._f98,MVEvent.MOUSE_MOVE,[x384,x382,MVUtil.getEvent(x378)]);
}
function MVRedlineTool(linestyle,areastyle,foiServerURL)
{
var _f307=eval(_f11._f307);
_f11._f307=(++_f307);
this.id=_f307;
this._f308=0;
this._f309=0;
this.parent="";
this._f3=foiServerURL;
this._f310=null;
this._f311=linestyle;
this._f312=areastyle;
this._f313=0;
this._f314=0;
this._f315=0;
this._f316=0;
this.mouseDownX=0;
this.mouseDownY=0;
this.pointdx=0;
this.pointdy=0;
this._f317=new Array();
this.state=0;
this._f153=0;
this._f318=0;
this._f319=0;
this._f320=null;
this._f321=5;
this._f322=5;
this._f323=false;
this._f324=false;
this._f325=null;
var _f326=this;
this._f98=new Array();
this._f327=null;
this._f328=null;
this._f329=new Array();
this._f330=new Array();
this.gtype=3;
this._f331=null;
this._f332=null;
this._f333=null;
this._f334=null;
this._f335=null;
this._f336=false;
this.textStyle="";
this._f337=null;
this._f338=true;
this.editingMode={deletePoint:true,dragPoint:true,deleteLine:true,dragLine:true};
this.pointGenerateOnTopZIndex=101;
this._f339=function(x0,x1,x2)
{
if(!_f326.editingMode)
return ;
var x3=x1.objType4RedLineTool;
switch(x3)
{
case -1:
if(!_f326.editingMode.deletePoint)
return ;
break;
case -2:
if(!_f326.editingMode.deleteLine)
return ;
break;
}
_f326._f331=x1;
var x4=x0.getPointX();
var x5=MVUtil.calAreaCodeFromCenter(_f326.parent,x4);
var x6=new MVMenu(_f326.parent,x0,x5);
_f326._f333=x6.menuFOIId;
switch(x3)
{
case -1:
x6.addMenuItem(MVi18n._f340("delete"),_f326._f341);
break;
case -2:
x0.sdo_ordinates[0]=x4;
x0.sdo_point.x=x4;
_f326._f332=x0;
x6.addMenuItem(MVi18n._f340("delete"),_f326._f342);
x6.addMenuItem(MVi18n._f340("addPoint"),_f326._f343);
break;
}
MVUtil._f173(x2);
}
this._f344=function(x7,x8,x9)
{
if(!_f326.editingMode)
{
MVUtil._f173(x9);
return ;
}
var x10=x8.objType4RedLineTool;
switch(x10)
{
case -1:
if(!_f326.editingMode.dragPoint)
return ;
break;
case -2:
if(!_f326.editingMode.dragLine)
return ;
break;
}
if(_f326._f319!=0&&_f326._f319!=1)
return;
_f326._f319=1;
_f326._f331=x8;
MVUtil._f173(x9);
}
this._f345=function(x11,x12,x13)
{
if(_f326._f319!=1)
return;
_f326._f319=0;
_f326._f331=null;
MVUtil._f173(x13);
}
this._f342=function()
{
var x14=_f326._f331;
if(!x14)
return;
var x15=x14.objType4RedLineTool;
if(x15==-2)
{
_f326.removeEdge(x14);
}
}
this._f341=function()
{
var x16=_f326._f331;
if(!x16)
return;
var x17=x16.objType4RedLineTool;
if(x17==-1)
{
_f326.removeVertex(x16);
}
}
this._f343=function()
{
var x18=_f326._f331;
if(!x18)
return;
var x19=x18.objType4RedLineTool;
if(x19==-2)
{
var x20=_f326._f346(x18);
if(x20[0])
{
var x21=_f326._f332;
var x22=null;
var x23=null;
if(x21)
{
x22=x21.getPointX();
x23=x21.getPointY();
}
if(x22&&x23)
_f326.addVertex(x20[1]+1,x22,x23);
}
}
}
this._f347=function(x24)
{
x24=(x24)?x24:((window.event)?event:null);
if(_f11._f70=="IF")
{
_f326.mouseDownX=x24.clientX+document.body.scrollLeft;
_f326.mouseDownY=x24.clientY+document.body.scrollTop;
}
else
 {
_f326.mouseDownX=x24.pageX;
_f326.mouseDownY=x24.pageY;
}
if(_f326._f319==1)
{
_f11._f348=_f326;
_f326._f319=2;
_f326.parent._f349(x24);
_f326._f313=_f326.parent._f350();
_f326._f314=_f326.parent._f351();
MVUtil._f173(x24);
return;
}
if((_f11._f348)!=_f326||
(x24!=null&&x24.button==2))
return;
MVUtil._f173(x24);
_f326._f153=1;
_f326._f318=1;
if(_f11._f70=="IF")
{
_f326._f315=x24.clientX+document.body.scrollLeft;
_f326._f316=x24.clientY+document.body.scrollTop;
}
else
 {
_f326._f315=x24.pageX;
_f326._f316=x24.pageY;
}
}
this.keepTwoPointsInHalfEarth=function(x25,x26,x27)
{
if(_f326.parent.wraparound)
{
if(_f326.parent._f248.maparea.length==1)
{
x26.areacode=_f326.parent._f248.centerArea;
return;
}
var x28=_f326.parent.msi._f158-_f326.parent.msi._f159;
var x29;
if(x27)
x29=x27.areacode;
else
 x29=_f326.parent._f248.centerArea;
var x30=MVUtil.keepInCSBoundary(_f326.parent,x26.getGeometry().getPointX());
var x31;
if(x27)
x31=MVUtil.keepInCSBoundary(_f326.parent,x27.getGeometry().getPointX());
else
 x31=MVUtil.keepInCSBoundary(_f326.parent,_f326.parent._f222());;
_f326._f317[x25*2]=x30;
x26.areacode=x29;
var x32=x30-x31;
while(Math.abs(x32)>x28/2)
{
if(x32<0)
{
x30=x30+x28;
x26.areacode=x29+1;
}
else
 {
x30=x30-x28;
x26.areacode=x29-1;
}
x32=x30-x31;
}
}
}
this.refreshRedline=function(x33)
{
var x34=function(x35,x36)
{
x35=MVUtil.keepInCSBoundary(_f326.parent,x35);
x36=MVUtil.keepInCSBoundary(_f326.parent,x36);
var x37=x36-x35;
if(x37>0)
x35=x35+(_f326.parent.msi._f158-_f326.parent.msi._f159);
else
 x36=x36+(_f326.parent.msi._f158-_f326.parent.msi._f159);
if(Math.abs(x36-x35)>Math.abs(x37))
return Math.abs(x37);
else
 return Math.abs(x36-x35);
}
if(_f326.parent.wraparound)
{
var x38=_f326._f329;
if(_f326.getStatus()==1)
{
if(x38.length!=0)
{
var x39=0;
var x40=Number.MAX_VALUE;
var x41=MVUtil.keepInCSBoundary(_f326.parent,_f326.parent._f222());
for(var x42=0;x42<x38.length;x42++)
{
var x43=Math.abs(MVUtil.keepInCSBoundary(_f326.parent,x38[x42].getGeometry().getPointX())-x41);
if(x40>x43)
{
x40=x43;
x39=x42;
}
}
_f326.keepTwoPointsInHalfEarth(x39,x38[x39],null);
if(x39>0)
{
for(var x42=x39-1;x42>=0;x42--)
{
_f326.keepTwoPointsInHalfEarth(x42,x38[x42],x38[x42+1]);
}
}
if(x39<x38.length)
{
for(var x42=x39+1;x42<x38.length;x42++)
{
_f326.keepTwoPointsInHalfEarth(x42,x38[x42],x38[x42-1]);
}
}
}
_f326.refreshLines(x33);
}
else if(_f326.getStatus()==2)
{
if(_f326.parent.getFOI("-RL2RL"+_f326.id))
{
var x44=_f326.parent.getFOI("-RL2RL"+_f326.id);
var x45=Number.MAX_VALUE;
var x46=0;
for(var x42=0;x42<x38.length;x42++)
{
var x43=x34(x38[x42].getGeometry().getPointX(),_f326.parent._f222());
if(x45>x43)
{
x45=x43;
x46=x42;
}
}
_f326.keepTwoPointsInHalfEarth(x46,x38[x46],null);
if(x46>0)
{
for(var x42=x46-1;x42>=0;x42--)
{
_f326.keepTwoPointsInHalfEarth(x42,x38[x42],x38[x42+1]);
}
}
if(x46<x38.length)
{
for(var x42=x46+1;x42<x38.length;x42++)
{
_f326.keepTwoPointsInHalfEarth(x42,x38[x42],x38[x42-1]);
}
}
var x47=new Array();
var x48=Number.MAX_VALUE;
for(var x42=0;x42<x38.length;x42++)
{
if(x38[x42].areacode<x48)
x48=x38[x42].areacode;
}
for(var x42=0;x42<x38.length;x42++)
{
if(x38[x42].areacode==x48)
x47.push([x42,x38[x42]]);
}
var x49=0;
var x50=Number.MAX_VALUE;
for(var x42=0;x42<x47.length;x42++)
{
var x51=(x47[x42])[1];
if(MVUtil.keepInCSBoundary(_f326.parent,x51.getGeometry().getPointX())<x50)
{
x50=MVUtil.keepInCSBoundary(_f326.parent,x51.getGeometry().getPointX());
x49=(x47[x42])[0];
}
}
x44.areacode=x38[x49].areacode;
}
}
}
}
this.refreshLines=function(x52)
{
var x53=_f326._f330;
var x54=_f326._f329;
if(x53.length!=0)
{
for(var x55=0;x55<x53.length;x55++)
{
x53[x55].areacode=(x54[x55].areacode<x54[x55+1].areacode)?x54[x55].areacode:x54[x55+1].areacode;
if(x52)
x53[x55].updateGeometry(MVSdoGeometry.createLineString(_f326._f317.slice(x55*2,(x55*2+4)),(_f326.parent.srid)));
}
}
}
this._f352=function(x56)
{
if(_f326._f319==3)
{
_f326.parent._f353();
}
_f326._f319=0;
x56=(x56)?x56:((window.event)?event:null);
if((_f11._f348)!=_f326||_f326._f318!=1||
(x56!=null&&x56.button==2))
{
if(_f326._f334)
{
_f326._f334();
_f326._f334=null;
}
return;
}
_f326._f318=0;
if(_f326._f153==1&&_f326.state==1)
{
MVUtil._f173(x56);
_f326.parent._f177=null;
_f326._f153=0;
_f326._f313=_f326.parent._f350();
_f326._f314=_f326.parent._f351();
if(_f326._f314>_f326.parent.msi._f274)
_f326._f314=_f326.parent.msi._f274;
else if(_f326._f314<_f326.parent.msi._f275)
_f326._f314=_f326.parent.msi._f275;
if(_f326.gtype==1)
{
while(_f326._f329.length>0)
{
var x57=_f326._f329.pop();
_f326.parent.removeFOI(x57);
}
while(_f326._f330.length>0)
{
var x57=_f326._f330.pop();
_f326.parent.removeFOI(x57);
}
_f326._f329=new Array();
_f326._f330=new Array();
_f326.parent.removeFOI("-RL2RL"+_f326.id);
_f326.parent.removeFOI("-RL10RL"+_f326.id);
_f326._f317=new Array();
}
if(_f326._f317!=null&&_f326._f317.length>=2
&&_f326._f313==_f326._f317[_f326._f317.length-2]
&&_f326._f314==_f326._f317[_f326._f317.length-1])
return;
var x58=null;
var x59="-RL8RL"+_f326.id+"_"+(++_f326._f308);
if(_f326._f320)
{
x58=new MVFOI(x59,
MVSdoGeometry.createPoint(_f326._f313,_f326._f314,_f326.parent.srid),"",
_f326._f320,_f326._f321,_f326._f322);
}
else
 {
if(!_f326._f310)
{
_f326._f310=new MVStyleMarker(_f11._f354,"vector");
_f326._f310.setStrokeColor("000000");
_f326._f310.setStrokeWidth(1.5);
_f326._f310.setFillColor("ff5555");
_f326._f310.setVectorShape("c:30");
}
x58=new MVFOI(x59,MVSdoGeometry.createPoint(_f326._f313,_f326._f314,_f326.parent.srid),
_f326._f310,null,12,12);
}
x58.enableInfoTip(false);
x58._f285=true;
x58.enableInfoWindow(false);
x58.objType4RedLineTool=-1;
x58.setEventListener(MVEvent.MOUSE_RIGHT_CLICK,_f326._f339);
x58.setEventListener(MVEvent.MOUSE_OVER,_f326._f344);
x58.setEventListener(MVEvent.MOUSE_OUT,_f326._f345);
x58.setMouseCursorStyle("pointer");
x58.setBringToTopOnMouseOver(false);
x58.setTopFlag(true);
if(_f326._f336)
x58.setZIndex(_f326.pointGenerateOnTopZIndex);
if(_f326._f323&&_f326._f329.length==0)
{
x58.setEventListener(MVEvent.MOUSE_CLICK,function()
{
_f326._f319=0;
if(_f326._f317.length<=2)
return;
_f326._f317.push(_f326._f317[0]);
_f326._f317.push(_f326._f317[1]);
_f326.generate();
if(_f326._f328)
_f326._f328();
MVUtil.runListeners(_f326._f98,MVEvent.FINISH);
return ;
});
}
_f326._f329.push(x58);
_f326._f317.push(_f326._f313);
_f326._f317.push(_f326._f314);
if(_f326._f329.length==0)
_f326.keepTwoPointsInHalfEarth(0,x58,null);
else
 _f326.keepTwoPointsInHalfEarth(_f326._f329.length-1,x58,_f326._f329[_f326._f329.length-2]);
_f326.parent.addFOI(x58);
FOILineLen=(_f326._f317.length);
if(FOILineLen>=4)
{
var x60=new MVFOI(("-RL6RL"+_f326.id+"_"+(++_f326._f309)),
MVSdoGeometry.createLineString(_f326._f317.slice((FOILineLen-4)),
(_f326.parent.srid)),_f326._f311,_f326._f3);
x60.setTopFlag(_f326._f336);
x60.enableInfoTip(false);
x60.enableInfoWindow(false);
x60.objType4RedLineTool=-2;
x60.setEventListener(MVEvent.MOUSE_RIGHT_CLICK,_f326._f339);
x60.setEventListener(MVEvent.MOUSE_OVER,_f326._f344);
x60.setEventListener(MVEvent.MOUSE_OUT,_f326._f345);
x60.setMouseCursorStyle("pointer");
x60.setBringToTopOnMouseOver(false);
if(_f326._f329[_f326._f329.length-2].areacode<_f326._f329[_f326._f329.length-1].areacode)
x60.areacode=_f326._f329[_f326._f329.length-2].areacode;
else
 x60.areacode=_f326._f329[_f326._f329.length-1].areacode;
_f326.parent.addFOI(x60);
_f326._f330.push(x60);
}
if(_f326._f324&&FOILineLen>4)
{
var x61=new Array(FOILineLen+2);
for(var x62=0;x62<FOILineLen;x62++)
x61[x62]=_f326._f317[x62];
x61[FOILineLen]=_f326._f317[0];
x61[FOILineLen+1]=_f326._f317[1];
_f326._f325.updateGeometry(MVSdoGeometry.createPolygon(x61,_f326.parent.srid));
}
if(_f326._f337&&!_f326._f337.isVisible()&&_f326._f338)
_f326._f337.setVisible(true);
if(_f326._f327)
_f326._f327();
MVUtil.runListeners(_f326._f98,MVEvent.NEW_SHAPE_POINT);
}
}
this.mouse_move=function(x63)
{
x63=(x63)?x63:((window.event)?event:null);
if((_f326._f319==2)||(_f326._f319==3))
{
_f11._f348=_f326;
_f326.parent._f349(x63);
var x64=_f326._f331;
var x65=x64.objType4RedLineTool;
switch(x65)
{
case -1:
_f326.moveVertex(x64,(_f326.parent._f350()),(_f326.parent._f351()));
break;
case -2:
var x66=(_f326.parent._f350());
var x67=(_f326.parent._f351());
var x68=x66-(_f326._f313);
var x69=x67-(_f326._f314);
_f326._f313=x66;
_f326._f314=x67;
if(_f11._f70=="IF")
{
_f326.pointdx=x63.clientX+document.body.scrollLeft-_f326.mouseDownX;
_f326.pointdy=x63.clientY+document.body.scrollTop-_f326.mouseDownY;
}
else
 {
_f326.pointdx=x63.pageX-_f326.mouseDownX;
_f326.pointdy=x63.pageY-_f326.mouseDownY;
}
_f326.moveEdge(x64,x68,x69);
break;
}
MVUtil._f173(x63);
if(_f326._f319==2)
{
_f326._f319=3;
_f326.parent._f215.onmousemove=function(x70){};
if(_f326.parent._f215.attached!=true)
{
if(_f11._f70=="IF")
{
_f326.parent._f215.attachEvent("onmousemove",_f326.mouse_move);
_f326.parent._f215.attached=true;
}
else
 {
_f326.parent._f215.addEventListener("mousemove",_f326.mouse_move,false);
_f326.parent._f215.attached=true;
}
}
}
return;
}
if((_f11._f348)!=_f326||
(x63!=null&&x63.button==2))
return;
if(_f326._f318==1)
{
var x68=0;
var x69=0;
if(_f11._f70=="IF")
{
x68=x63.clientX+document.body.scrollLeft-_f326._f315;
x69=x63.clientY+document.body.scrollTop-_f326._f316;
}
else
 {
x68=x63.pageX-_f326._f315;
x69=x63.pageY-_f326._f316;
}
var x71=_f11._f355;
if((x68*x68+x69*x69)>(x71*x71))
_f326._f318=2;
}
}
this.getStatus=function()
{
return this.state;
}
this.init=function(x72)
{
if(!_f326._f337)
{
_f326._f337=new MVMapDecoration("<div id=\"redline_cpan_div_"+this.id+"\" style=\"padding:3px 2px 2px 3px; border:1px solid black;font-size:9pt;background-color:white;filter:alpha(opacity=80);-moz-opacity:.80;opacity:.80;"
+this.textStyle+"\"><a id=\"finishLink_"+this.id+"\" href=\"javascript:void(0);\"> "+MVi18n._f340("finish")+"</a> / <a id=\"clearLink_"
+this.id+"\" href=\"javascript:void(0);\">"+MVi18n._f340("clear")+" </a></div>",0.4,0.9);
_f326._f337.afterDisAction=_f326.redlineInfoLink;
_f326.parent.addMapDecoration(_f326._f337);
_f326._f337.setDraggable(true);
_f326._f337.setDraggable(true);
_f326._f337.setVisible(false);
}
switch(x72)
{
case 1:
this.gtype=x72;
break;
case 2:
this.gtype=x72;
break;
default:
this.gtype=x72=3;
}
if(!this._f310)
this.setRenderingStyle(MVRedlineTool.STYLE_POINT,null);
if(!this._f311)
this.setRenderingStyle(MVRedlineTool.STYLE_LINE,null);
if(!this._f312)
this.setRenderingStyle(MVRedlineTool.STYLE_AREA,null);
this.parent._f175=true;
if(this.state==0)
{
if(_f11._f70=="IF")
{
this.parent._f215.attachEvent("onmousedown",this._f347);
this.parent._f215.attachEvent("onmouseup",this._f352);
this.parent._f215.attachEvent("onmousemove",this.mouse_move);
}
else
 {
this.parent._f215.addEventListener("mousedown",this._f347,false);
this.parent._f215.addEventListener("mouseup",this._f352,false);
this.parent._f215.addEventListener("mousemove",this.mouse_move,false);
}
}
_f11._f348=this;
if(this._f324)
{
this._f325=new MVFOI("-RL10RL"+this.id,
MVSdoGeometry.createPolygon([0,0,0,0,0,0,0,0],this.parent.srid),
this._f312,
this._f3);
this._f325.enableInfoTip(false);
this._f325.enableInfoWindow(false);
this._f325.setClickable(false);
this.parent.addFOI(this._f325);
}
while(this._f329.length>0)
{
var x73=this._f329.pop();
this.parent.removeFOI(x73);
}
while(this._f330.length>0)
{
var x73=this._f330.pop();
this.parent.removeFOI(x73);
}
this._f329=new Array();
this._f330=new Array();
this.parent.removeFOI("-RL2RL"+this.id);
this.parent.removeFOI("-RL10RL"+this.id);
if(this._f333!=null)
this.parent.removeFOI(this._f333);
this._f317=new Array();
this.state=1;
this.parent._f248.attachEventListener("refreshFOI",this.refreshRedline);
MVUtil.runListeners(this._f98,MVEvent.INIT,[MVToolBar.BUILTIN_REDLINE]);
}
this.setTextStyle=function(x74)
{
_f326.textStyle=x74;
}
this.setControlPanelVisible=function(x75)
{
this._f338=x75;
if(this._f337)
this._f337.setVisible(x75);
}
this.redlineInfoLink=function()
{
var x76=document.getElementById("finishLink_"+_f326.id);
var x77=document.getElementById("clearLink_"+_f326.id);
x76.onclick=function()
{
if(_f326.finishLinkClick)
{
_f326.finishLinkClick();
}
_f326.generate();
if(_f326.getStatus()!=2)
_f326.clear();
}
x77.onclick=function()
{
if(_f326.clearLinkClick)
{
_f326.clearLinkClick();
}
_f326.clear();
}
}
this.getInfoDecorationDiv=function()
{
return document.getElementById("redline_cpan_div_"+this.id);
}
this.generate=function()
{
var x78=this.gtype;
if(this.state==1)
{
if(x78==3&&this._f317.length<6)
{
MVi18n._f6("MVRedlineTool.generate","MAPVIEWER-05512",null,this.parent?this.parent._f7:null);
return;
}
else if(x78==2&&this._f317.length<4)
{
MVi18n._f6("MVRedlineTool.generate","MAPVIEWER-05512",null,this.parent?this.parent._f7:null);
return;
}
else if(x78==1&&this._f317.length<2)
{
MVi18n._f6("MVRedlineTool.generate","MAPVIEWER-05512",null,this.parent?this.parent._f7:null);
return;
}
var x79=this;
var x80=function()
{
if(x79._f330.length>0)
{
if(!x79._f330[x79._f330.length-1].node)
{
setTimeout(x80,50);
return ;
}
}
var x81=null;
if(x79._f329&&x79._f329[0])
{
x81=x79._f329[0].areacode;
for(var x82=1;x82<x79._f329.length;x82++)
{
if(x79._f329[x82]&&(x79._f329[x82].areacode<x81))
x81=x79._f329[x82].areacode;
}
}
x79.state=2;
for(var x82=0;x82<x79._f329.length;x82++)
{
var x83=x79._f329[x82];
x79.parent.removeFOI(x83);
}
while(x79._f330.length>0)
{
var x83=x79._f330.pop();
x79.parent.removeFOI(x83);
}
x79._f330=new Array();
if(x79._f333!=null)
x79.parent.removeFOI(x79._f333);
x79.parent.removeFOI("-RL10RL"+x79.id);
var x84=x79._f317.length;
if(x78==3&&(x79._f317[0]!=x79._f317[x84-2]||x79._f317[1]!=x79._f317[x84-1]))
{
x79._f317.push(x79._f317[0]);
x79._f317.push(x79._f317[1]);
}
var x85=null;
switch(x78)
{
case 1:
if(x79._f320)
x85=new MVFOI("-RL2RL"+x79.id,MVSdoGeometry.createPoint(x79._f317[0],x79._f317[1],x79.parent.srid),"",x79._f320,x79._f321,x79._f322);
else
 x85=new MVFOI("-RL2RL"+x79.id,MVSdoGeometry.createPoint(x79._f317[0],x79._f317[1],x79.parent.srid),x79._f310,x79._f3);
break;
case 2:
x85=new MVFOI("-RL2RL"+x79.id,MVSdoGeometry.createLineString(x79._f317,x79.parent.srid),x79._f311,x79._f3);
break;
case 3:
x85=new MVFOI("-RL2RL"+x79.id,MVSdoGeometry.createPolygon(x79._f317,x79.parent.srid),x79._f312,x79._f3);
break;
}
x85.areacode=x81;
x85.enableInfoTip(false);
x85.enableInfoWindow(false);
x85.setClickable(false);
x85.setTopFlag(x79._f336);
x79.parent.addFOI(x85);
x79.parent._f175=false;
if(_f326._f328)
_f326._f328();
MVUtil.runListeners(_f326._f98,MVEvent.FINISH);
}
x80();
}
}
this.generateArea=this.generate;
this.clear=function()
{
if(!this.parent)
return ;
var x86=false;
while(this._f329.length>0)
{
var x87=this._f329.pop();
this.parent.removeFOI(x87);
x86=true;
}
while(this._f330.length>0)
{
var x87=this._f330.pop();
this.parent.removeFOI(x87);
x86=true;
}
if(this.parent.getFOI("-RL2RL"+this.id))
x86=true;
this.parent.removeFOI("-RL2RL"+this.id);
if(this.parent.getFOI("-RL10RL"+this.id))
x86=true;
this.parent.removeFOI("-RL10RL"+this.id);
if(this._f333!=null)
this.parent.removeFOI(this._f333);
if(_f11._f70=="IF")
{
this.parent._f215.detachEvent("onmousedown",this._f347);
this.parent._f215.detachEvent("onmouseup",this._f352);
this.parent._f215.detachEvent("onmousemove",this.mouse_move);
}
else
 {
this.parent._f215.removeEventListener("mousedown",this._f347,false);
this.parent._f215.removeEventListener("mouseup",this._f352,false);
this.parent._f215.removeEventListener("mousemove",this.mouse_move,false);
}
if(_f326._f337)
{
_f326._f337.setVisible(false);
var x88=_f326._f337.getContainerDiv();
for(var x89=0;x89<x88.childNodes.length;x89++)
{
if(x88.childNodes[x89].tagName=="IMG")
{
x88.childNodes[x89].style.visibility="hidden";
}
}
}
if(this.getStatus()==1||x86)
MVUtil.runListeners(_f326._f98,MVEvent.CLEAR);
this.state=0;
this.parent._f175=false;
this._f329=new Array();
this._f330=new Array();
this._f317=new Array();
}
this.setMarkerImage=function(x90,x91,x92)
{
this._f320=x90;
this._f321=x91;
this._f322=x92;
}
this.getPolygon=function()
{
var x93=this._f317.length;
if(x93>=6&&this._f317[0]==this._f317[x93-2]&&this._f317[1]==this._f317[x93-1])
{
var x94=MVSdoGeometry.createPolygon(this._f317,this.parent.srid);
return x94;
}
else
 return null;
}
this.getSdoGeometry=function()
{
var x95=this._f317.length;
var x96=null;
if(x95>=6&&this._f317[0]==this._f317[x95-2]&&this._f317[1]==this._f317[x95-1])
x96=MVSdoGeometry.createPolygon(this._f317,this.parent.srid);
else if(x95>=4)
x96=MVSdoGeometry.createLineString(this._f317,this.parent.srid);
else if(x95==2)
x96=MVSdoGeometry.createPoint(this._f317,this.parent.srid);
return x96;
}
this.getPolygonFOI=function()
{
if(this.gtype==3)
return this.parent.getFOI("-RL2RL"+this.id);
else
 return null;
}
this.getFOI=function()
{
return this.parent.getFOI("-RL2RL"+this.id);
}
this.getOrdinates=function()
{
return this._f317;
}
this.addEventListener=function(x97,x98)
{
this.setEventListener(x97,x98);
}
this.setEventListener=function(x99,x100)
{
if(x99==MVEvent.NEW_SHAPE_POINT||x99==MVEvent.MOUSE_CLICK)
this._f327=x100;
else if(x99=="on_finish"||x99==MVEvent.FINISH)
this._f328=x100;
else if(x99==MVEvent.MODIFY)
this._f335=x100;
}
this.attachEventListener=function(x101,x102)
{
MVUtil.attachEventListener(_f326._f98,x101,x102)
}
this.detachEventListener=function(x103,x104)
{
MVUtil.detachEventListener(_f326._f98,x103,x104);
}
}
MVRedlineTool.prototype.setAutoClose=function(x0)
{
this._f323=x0;
}
MVRedlineTool.prototype.setEditingMode=function(x1)
{
if(!x1)
this.editingMode={deletePoint:false,dragPoint:false,deleteLine:false,dragLine:false};
else
 this.editingMode=x1;
}
MVRedlineTool.prototype.getEditingMode=function()
{
return this.editingMode;
}
MVRedlineTool.prototype.setGeneratePolygonTop=function(x2)
{
this._f336=x2;
}
MVRedlineTool.prototype.setFillArea=function(x3)
{
this._f324=x3;
}
MVRedlineTool.prototype.getPointNumber=function()
{
return this._f329.length;
}
MVRedlineTool.prototype.getPointFOIs=function()
{
return this._f329;
}
MVRedlineTool.prototype.getLineFOIs=function()
{
return this._f330;
}
MVRedlineTool.prototype.setRenderingStyle=function(x4,x5)
{
if(x5&&!(typeof(x5)=="string"||(typeof(x5)=="object"
&&x5.getXMLString!=null)))
{
MVi18n._f6("MVRedlineTool.setRenderingStyle","MAPVIEWER-05519","objStyle",
this.parent?this.parent._f7:null);
}
else
 {
switch(x4)
{
case MVRedlineTool.STYLE_POINT:
if(!x5)
{
var x6=new MVStyleMarker(_f11._f354,"vector");
x6.setStrokeColor("000000");
x6.setStrokeWidth(1.5);
x6.setFillColor("ff5555");
x6.setVectorShape("c:30");
this._f310=x6;
}
else
 this._f310=x5;
break;
case MVRedlineTool.STYLE_LINE:
if(!x5)
{
var x7='<svg width="1in" height="1in"><desc/><g class="line" style="stroke-width:1.0">'+
'<line class="base" style="fill:#000066;stroke-width:1.0" dash="5.0,3.0"/></g></svg>';
var x8=new MVXMLStyle(_f11._f357,x7);
this._f311=x8;}
else
 this._f311=x5;
break;
case MVRedlineTool.STYLE_AREA:
if(!x5)
{
var x8=new MVStyleColor(_f11._f358,"FF0000","FF0000");
x8.setFillOpacity(100);
this._f312=x8;
}
else
 this._f312=x5;
break;
}
}
}
MVRedlineTool.prototype._f359=function(x9)
{
var x10=this._f329.length;
var x11=null;
var x12=null;
if(x9!=null)
{
if(isNaN(x9))
{
var x13=0;
for(;x13<x10;++x13)
{
if(this._f329[x13]==x9)
{
x11=x9;
x12=x13;
break;
}
}
}
else if(x9<x10&&x9>=0)
{
x11=this._f329[x9];
x12=x9;
}
else if(x9==x10)
{
x12=x9;
}
}
return [x11,x12];
}
MVRedlineTool.prototype._f346=function(x14)
{
var x15=this._f330.length;
var x16=null;
var x17=-1;
if(x14!=null)
{
if(isNaN(x14))
{
var x18=0;
for(;x18<x15;++x18)
{
if(this._f330[x18]==x14)
{
x16=x14;
x17=x18;
break;
}
}
}
else if(x14<x15&&x14>=0)
{
x16=this._f329[x14];
x17=x14;
}
}
return [x16,x17];
}
MVRedlineTool.prototype.removeVertex=function(x19)
{
var x20=this._f359(x19);
if(!x20||!x20[0])
return;
var x21=x20[0];
var x22=x20[1];
var x23=this._f329.length;
this._f329.splice(x22,1);
var x24=(x22*2);
this._f317.splice(x24,2);
this.parent.removeFOI(x21);
var x25=x22>0?true:false;
var x26=(x22<(x23-1))?true:false;
var x27=null;
if(x25&&x26)
{
x27=this._f330[x22-1];
if(!this.parent.wraparound)
{
x27.updateGeometry(MVSdoGeometry.createLineString(this._f317.slice((x24-2),(x24+2)),
(this.parent.srid)));
}
x27=this._f330[x22];
this.parent.removeFOI(x27);
this._f330.splice(x22,1);
}
else if(x25)
{
x27=this._f330[x22-1];
this.parent.removeFOI(x27);
this._f330.splice((x22-1),1);
}
else if(x26)
{
x27=this._f330[x22];
this.parent.removeFOI(x27);
this._f330.splice(x22,1);
}
if(this.parent.wraparound)
{
this.refreshRedline(true);
}
if(this._f335)
this._f335();
MVUtil.runListeners(this._f98,MVEvent.MODIFY);
}
MVRedlineTool.prototype.removeEdge=function(x28,x29)
{
var x30=this._f346(x28);
if(!x30||!x30[0])
return;
var x31=x30[0];
var x32=x30[1];
if(x29=="left")
this.removeVertex(x32);
else
 this.removeVertex(x32+1);
}
MVRedlineTool.prototype.moveVertex=function(x33,x34,x35)
{
var x36=this._f359(x33);
if(!x36||!x36[0])
return;
var x37=x36[0];
var x38=x36[1];
if(x35>this.parent.msi._f274)
x35=this.parent.msi._f274;
else if(x35<this.parent.msi._f275)
x35=this.parent.msi._f275;
x37.areacode=MVUtil.calAreaCodeFromCenter(this.parent,x34);
x37.updateGeometry(MVSdoGeometry.createPoint(x34,x35,(this.parent.srid)));
var x39=(x38*2);
this._f317[x39]=x34;
this._f317[x39+1]=x35;
var x40=this._f329.length;
var x41=x38>0?true:false;
var x42=(x38<(x40-1))?true:false;
var x43=null;
var x44=this;
this._f334=function()
{
x44.moveToNewEdge(x41,x42,x38,x39,x37,x34,x35);
if(x44._f335)
x44._f335();
MVUtil.runListeners(x44._f98,MVEvent.MODIFY);
};
}
MVRedlineTool.prototype.moveToNewEdge=function(x45,x46,x47,x48,x49,x50,x51)
{
if(this.parent.wraparound)
{
this.refreshRedline(true);
}
else
 {
if(x45)
{
var x52=this._f330[x47-1];
x52.updateGeometry(MVSdoGeometry.createLineString(this._f317.slice((x48-2),(x48+2)),
(this.parent.srid)));
}
if(x46)
{
var x52=this._f330[x47];
x52.updateGeometry(MVSdoGeometry.createLineString(this._f317.slice(x48,(x48+4)),
(this.parent.srid)));
}
}
}
MVRedlineTool.prototype.moveEdge=function(x53,x54,x55)
{
var x56=this._f346(x53);
if(!x56||!x56[0])
return;
var x57=x56[0];
var x58=x56[1];
var x59=(x58*2);
this._f317[x59]=this._f317[x59]+x54;
this._f317[x59+1]=this._f317[x59+1]+x55;
if(this._f317[x59+1]>this.parent.msi._f274)
this._f317[x59+1]=this.parent.msi._f274;
else if(this._f317[x59+1]<this.parent.msi._f275)
this._f317[x59+1]=this.parent.msi._f275;
this._f317[x59+2]=this._f317[x59+2]+x54;
this._f317[x59+3]=this._f317[x59+3]+x55;
if(this._f317[x59+3]>this.parent.msi._f274)
this._f317[x59+3]=this.parent.msi._f274;
else if(this._f317[x59+3]<this.parent.msi._f275)
this._f317[x59+3]=this.parent.msi._f275;
var x60=x57.node;
if(_f11._f360==2)
{
if(!(x53.mouseDownX))
{
x53.mouseDownX=x60.style.pixelLeft;
x53.mouseDownY=x60.style.pixelTop;
}
x60.style.pixelLeft=x53.mouseDownX+this.pointdx;
x60.style.pixelTop=x53.mouseDownY+this.pointdy;
}
else
 {
if(!(x53.mouseDownX))
{
x53.mouseDownX=MVUtil._f84(x60);
x53.mouseDownY=MVUtil._f85(x60);
}
x60.style.left=(x53.mouseDownX+this.pointdx)+'px';
x60.style.top=(x53.mouseDownY+this.pointdy)+'px';
}
var x61=this._f329.length;
var x62=x58>0?true:false;
var x63=(x58<(x61-2))?true:false;
var x64=null;
var x65=this;
this._f334=function()
{
x65.moveToNewEdge2(x62,x63,x58,x59,x57);
if(x65._f335)
x65._f335();
MVUtil.runListeners(x65._f98,MVEvent.MODIFY);
};
}
MVRedlineTool.prototype.moveToNewEdge2=function(x66,x67,x68,x69,x70){
this._f329[x68].areacode=MVUtil.calAreaCodeFromCenter(this.parent,this._f317[x69]);
this._f329[x68+1].areacode=MVUtil.calAreaCodeFromCenter(this.parent,this._f317[x69+2]);
this._f329[x68].updateGeometry(MVSdoGeometry.createPoint(this._f317[x69],this._f317[x69+1],
(this.parent.srid)));
this._f329[x68+1].updateGeometry(MVSdoGeometry.createPoint(this._f317[x69+2],this._f317[x69+3],
(this.parent.srid)));
if(this.parent.wraparound)
{
this.refreshRedline(true);
}
else
 {
x70.updateGeometry(MVSdoGeometry.createLineString(this._f317.slice(x69,(x69+4)),
(this.parent.srid)));
if(x66)
{
var x71=this._f330[x68-1];
x71.updateGeometry(MVSdoGeometry.createLineString(this._f317.slice((x69-2),(x69+2)),
(this.parent.srid)));
}
if(x67)
{
var x71=this._f330[x68+1];
x71.updateGeometry(MVSdoGeometry.createLineString(this._f317.slice(x69+2,(x69+6)),
(this.parent.srid)));
}
}
x70.mouseDownX=null;
x70.mouseDownY=null;
}
MVRedlineTool.prototype.addVertex=function(x72,x73,x74)
{
var x75=this._f359(x72);
if(!x75||!x75[1]&&x72!=this._f329.length)
return;
var x76=x75[0];
var x77=x75[1];
var x78=this._f329.length;
var x79=null;
var x80="-RL8RL"+this.id+"_"+(++this._f308);
if(this._f320)
x79=new MVFOI(x80,MVSdoGeometry.createPoint(x73,x74,this.parent.srid),
"",this._f320,this._f321,this._f322);
else
 x79=new MVFOI(x80,MVSdoGeometry.createPoint(x73,x74,this.parent.srid),
this._f310,null,12,12);
if(this._f336)
x79.setZIndex(this.pointGenerateOnTopZIndex);
x79.enableInfoTip(false);
x79._f285=true;
x79.enableInfoWindow(false);
x79.objType4RedLineTool=-1;
x79.setEventListener(MVEvent.MOUSE_RIGHT_CLICK,this._f339);
x79.setEventListener(MVEvent.MOUSE_OVER,this._f344);
x79.setEventListener(MVEvent.MOUSE_OUT,this._f345);
x79.setMouseCursorStyle("pointer");
x79.setBringToTopOnMouseOver(false);
x79.setTopFlag(true);
x79.areacode=MVUtil.calAreaCodeFromCenter(this.parent,x73);
this._f329.splice(x77,0,x79);
var x81=x77*2;
this._f317.splice(x81,0,x73,x74);
this.parent.addFOI(x79);
if(this._f337&&!this._f337.isVisible()&&this._f338)
this._f337.setVisible(true);
if(this._f329.length==1)
return ;
var x82=x77<x78?true:false;
var x83=null;
if(x82)
{
x83=this._f330[x77-1];
x83.updateGeometry(MVSdoGeometry.createLineString(this._f317.slice((x81-2),(x81+2)),
(this.parent.srid)));
x83=new MVFOI(("-RL6RL"+this.id+"_"+(++this._f309)),
MVSdoGeometry.createLineString(this._f317.slice(x81,(x81+4)),
(this.parent.srid)),this._f311,this._f3);
x83.setTopFlag(this._f336);
x83.enableInfoTip(false);
x83.enableInfoWindow(false);
x83.objType4RedLineTool=-2;
x83.setEventListener(MVEvent.MOUSE_RIGHT_CLICK,this._f339);
x83.setEventListener(MVEvent.MOUSE_OVER,this._f344);
x83.setEventListener(MVEvent.MOUSE_OUT,this._f345);
x83.setMouseCursorStyle("pointer");
x83.setBringToTopOnMouseOver(false);
x83.areacode=this._f329[x77].areacode;
this.parent.addFOI(x83);
this._f330.splice(x77,0,x83);
}
else
 {
x83=new MVFOI(("-RL6RL"+this.id+"_"+(++this._f309)),
MVSdoGeometry.createLineString(this._f317.slice(x81-2,(x81+2)),
(this.parent.srid)),this._f311,this._f3);
x83.setTopFlag(this._f336);
x83.enableInfoTip(false);
x83.enableInfoWindow(false);
x83.objType4RedLineTool=-2;
x83.setEventListener(MVEvent.MOUSE_RIGHT_CLICK,this._f339);
x83.setEventListener(MVEvent.MOUSE_OVER,this._f344);
x83.setEventListener(MVEvent.MOUSE_OUT,this._f345);
x83.setMouseCursorStyle("pointer");
x83.setBringToTopOnMouseOver(false);
x83.areacode=this._f329[x77].areacode;
this.parent.addFOI(x83);
this._f330.push(x83);
}
}
MVRedlineTool.STYLE_POINT="point";
MVRedlineTool.STYLE_LINE="line";
MVRedlineTool.STYLE_AREA="area";
function MVRectangleTool(rectStyle,_f470)
{
this.parent=null;
this._f626=document.createElement("div");
this.innerRectangleDiv=null;
this._f626.style.position="absolute";
this._f626.style.zIndex=5000;
this._f626.style.border="#FF0000 2px dashed";
this._f626.style.visibility="hidden";
this._f473=2;
this._f470=_f470;
this._f312=rectStyle;
this._f474=0;
this._f475=0;
this._f476=0;
this._f477=0;
this._f478=0;
this._f479=0;
this._f98=new Array();
this._f480=null;
this._f328=null;
this._f481=null;
this.mouseupHandler=null;
this._f356=null;
var _f557=eval(_f11._f557);
++_f557;
_f11._f557=_f557;
this.id=_f557;
this._f485=0;
this._f70="NS";
var rt=this;
this._f486=function(x0)
{
x0=(x0)?x0:((window.event)?event:null);
if(rt.parent._f487)
MVUtil._f173(x0);
};
this._f488=function()
{
if(this.netscape==null)
{
if(_f11._f489("netscape"))
this.netscape=true;
else
 this.netscape=false;
}
if(this.netscape)
{
window.addEventListener("keypress",this._f486,false);
}
}
this._f490=function()
{
if(this.netscape==null)
{
if(_f11._f489("netscape"))
this.netscape=true;
else
 this.netscape=false;
}
if(this.netscape)
window.removeEventListener("keypress",this._f486,false);
}
this.refreshRetangle=function()
{
var x1=rt.parent;
if(x1.wraparound)
{
if(rt.getStatus()==3)
{
var x2=rt.getRectangleFOI();
if(x2)
{
x2.areacode=rt.parent._f248.centerArea;
}
}
}
}
}
MVRectangleTool.prototype.setDivBorderStyle=function(x0,x1,x2)
{
var x3="#FF0000";
if(x0&&x0!="")
{
x3=x0;
}
var x4="1px";
if(x1)
{
x4=x1+"px";
this._f473=x1;
}
var x5="dashed";
if(x2!="")
x5=x2;
this._f626.style.border=x3+" "+x4+" "+x5;
}
MVRectangleTool.prototype.setDivFillColor=function(x6,x7)
{
if(!this.innerRectangleDiv)
{
this.innerRectangleDiv=document.createElement("div");
this.innerRectangleDiv.style.width="100%";
this.innerRectangleDiv.style.height="100%";
this._f626.appendChild(this.innerRectangleDiv);
}
if(x6&&x6!="")
{
if(this.innerRectangleDiv.style.setAttribute)
this.innerRectangleDiv.style.cssText+=";background-color:"+x6;
else
 this.innerRectangleDiv.style.backgroundColor=x6;
}
if(isNaN(x7)||x7<0||x7>1)
{
MVi18n._f6("MVRectangleTool.setDivFillColor","MAPVIEWER-05519","opacity",
this.parent?this.parent._f7:null);
return ;
}
else
 {
if(window.ActiveXObject)
{
this.innerRectangleDiv.style.filter="alpha(opacity="+(x7*100)+")";
}
else
 {
this.innerRectangleDiv.style.opacity=x7;
}
}
}
MVRectangleTool.prototype.setAreaStyle=function(x8)
{
this._f312=x8;
}
MVRectangleTool.prototype._f491=function()
{
this._f474=this.parent._f350();
this._f475=this.parent._f351();
}
MVRectangleTool.prototype._f492=function()
{
this._f476=this.parent._f350();
this._f477=this.parent._f351();
var x9=Math.abs(this._f476-this._f474);
var x10=this.parent.msi._f158-this.parent.msi._f159;
if(x9>x10)
{
if(this._f474<this._f476)
this._f476=this._f474+x10;
else
 this._f476=this._f474-x10;
}
}
MVRectangleTool.prototype._f627=function()
{
this._f626.style.width="0px";
this._f626.style.height="0px";
this._f626.style.clip="rect(0px, 0px, 0px, 0px)";
this._f626.style.visibility="hidden";
}
MVRectangleTool.prototype._f628=function()
{
if(this._f485!=3)
{
return;
}
var x11=(this._f474-this.parent._f222())*this.parent._f76+
(this.parent._f109()/2);
var x12=(this._f475-this.parent._f223())*(-this.parent._f78)+
(this.parent._f110()/2);
var x13=(this._f476-this.parent._f222())*this.parent._f76+
(this.parent._f109()/2);
var x14=(this._f477-this.parent._f223())*(-this.parent._f78)+
(this.parent._f110()/2);
var x15=Math.abs(x11-x13);
var x16=Math.abs(x12-x14);
x15=Math.round(x15);
x16=Math.round(x16);
var x17=(x11<x13?x11:x13);
var x18=(x12<x14?x12:x14);
x17=Math.round(x17);
x18=Math.round(x18);
this._f626.style.left=x17-1-MVUtil._f84(this.parent.div)+'px';
this._f626.style.top=x18-1-MVUtil._f85(this.parent.div)+'px';
if(x15>0)
x15++;
if(x16>0)
x16++;
if(this._f70=="NS")
{
if(x15==0)
x15=(2*this._f473);
if(x16==0)
x16=(2*this._f473);
this._f626.style.width=Math.abs(x15-(2*this._f473))+'px';
this._f626.style.height=Math.abs(x16-(2*this._f473))+'px';
this._f626.style.clip="rect(0px, "+(x15)+"px, "+
(x16)+"px, 0px)";
}
else
 {
this._f626.style.width=x15+'px';
this._f626.style.height=x16+'px';
this._f626.style.clip="rect(0px, "+(x15+(2*this._f473))+"px, "+
(x16+(2*this._f473))+"px, 0px)";
}
this._f626.style.visibility="visible";
}
MVRectangleTool.prototype.generateArea=function()
{
if(this._f485!=3)
return;
this.parent.removeFOI("MVRectangleTool"+this.id);
if(!this._f312)
return;
this._f627();
if(this._f474!=this._f476&&this._f475!=this._f477)
{
this.getRectangle();
var x19=new MVFOI("MVRectangleTool"+this.id,this._f356,this._f312,this._f470);
x19.areacode=MVUtil.calAreaCodeFromCenter(this.parent,this._f474<this._f476?this._f474:this._f476);
x19.setClickable(false);
x19.enableInfoTip(false);
this.parent.addFOI(x19);
}
else
 this._f356=null;
}
MVRectangleTool.prototype.init=function()
{
this._f627();
this.parent.removeFOI("MVRectangleTool"+this.id);
this._f485=0;
this.parent._f353();
this._f485=1;
MVUtil._f629(this._f626);
var x20=this.parent;
var x21=x20._f215;
var x22=this;
this._f474=this._f475=this._f476=this._f477=0;
var x23;
MVUtil.runListeners(x22._f98,MVEvent.INIT,[MVToolBar.BUILTIN_RECTANGLE]);
this._f70=this.parent.getBrowserType();
var x24=function()
{
x20._f487=true;
var x25=x20._f222();
var x26=x20._f223();
if((x22._f478)!=x25||(x22._f479)!=x26)
{
if(x22._f485==2)
{
x22._f485=3;
}
x22._f476+=(x25-x22._f478);
x22._f477+=(x26-x22._f479);
x22._f478=x25;
x22._f479=x26;
x22._f628();
}
}
x21.onmousedown=MVUtil._f106(x21,function(x27)
{
x20.removeFOI("MVRectangleTool"+x22.id);
x22._f485=2;
x27=(x27)?x27:((window.event)?event:null);
var x28=x20._f220(x27);
x20.mouseDownLoc=x28;
x20._f496(x27);
x22._f491();
x22._f492();
x22._f626.style.visibility="hidden";
if(x22._f480!=null)
(x22._f480)();
MVUtil.runListeners(x22._f98,MVEvent.START);
x22._f478=x20._f222();
x22._f479=x20._f223();
var x29=5;
x23=setInterval(x24,x29);
x22._f488();
return false;
});
x21.onmousemove=MVUtil._f106(x21,function(x30)
{
if(x22._f485==2)
x22._f485=3;
if(x22._f485==3)
{
x30=(x30)?x30:((window.event)?event:null);
x20._f496(x30);
x22._f492();
x22._f628();
if(x22._f481)
x22._f481();
MVUtil.runListeners(x22._f98,MVEvent.DRAG);
}
return false;
});
var x31=MVUtil._f106(x21,function(x32)
{
if(x22._f485==0||x22._f485==1)
return false;
x32=(x32)?x32:((window.event)?event:null);
var x33=x20._f220(x32);
x20.mouseUpLoc=x33;
x20._f496(x32);
x22._f492();
x22.generateArea();
clearInterval(x23);
if(_f11._f70=="IF")
{
x21.detachEvent("onmouseup",x31);
}
else
 {
x21.removeEventListener("mouseup",x31,true);
}
x20._f353();
x22._f490();
if(x22._f328){
(x22._f328)();
}
MVUtil.runListeners(x22._f98,MVEvent.FINISH);
return false;
});
if(_f11._f70=="IF")
{
if(this.mouseupHandler)
x21.detachEvent("onmouseup",this.mouseupHandler);
x21.attachEvent("onmouseup",x31);
}
else
 {
if(this.mouseupHandler)
x21.removeEventListener("mouseup",this.mouseupHandler,true);
x21.addEventListener("mouseup",x31,true);
}
this.mouseupHandler=x31;
x21.onmouseout=MVUtil._f106(x21,function(x34)
{
if(x22._f485!=3)
return false;
if(x22._f70=="NS"||x22._f70=="OS")
{
x34=(x34)?x34:((window.event)?event:null);
var x35=x34.clientX;
var x36=x34.clientY;
var x37=x20._f109();
var x38=x20._f110();
var x39=MVUtil._f497(this);
var x40=x34.pageX-x39.x;
var x41=x34.pageY-x39.y;
if(x40<=0||x40>=x37||
x41<=0||x41>=x38||
x35<=0||x35>=document.body.clientWidth||
x36<=0||x36>=document.body.clientHeight)
{
return this.onmouseup(x34);
}
}
});
this.parent._f248.attachEventListener("refreshFOI",this.refreshRetangle);
MVUtil.runListeners(x22._f98,MVEvent.START,[MVToolBar.BUILTIN_DISTANCE]);
}
MVRectangleTool.prototype.clear=function()
{
this._f627();
this.parent.removeFOI("MVRectangleTool"+this.id);
this._f485=0;
this.parent._f353();
MVUtil.runListeners(this._f98,MVEvent.CLEAR);
}
MVRectangleTool.prototype.getRectangle=function()
{
if(this._f474!=this._f476&&this._f475!=this._f477)
{
var x42=this.parent.msi._f158-this.parent.msi._f159;
if((this._f476-this._f474)<x42/2)
{
this._f356=MVSdoGeometry.createRectangle(
this._f474,this._f475,this._f476,this._f477,this.parent.srid);
return this._f356;
}
else
 {
var x43=this._f474;
var x44=this._f475;
var x45=this._f476;
var x46=this._f477;
var x47=(x45-x43)/3;
var x48=(x45-x43)/3*2;
var x49=[x43,x44,x43,x46,x43+x47,x46,x43+x48,x46,x45,x46,x45,x44,x43+x48,x44,x43+x47,x44];
this._f356=MVSdoGeometry.createPolygon(x49,this.parent.srid);
return this._f356;
}
}
else
 return null;
}
MVRectangleTool.prototype.getRectangleFOI=function()
{
return this.parent.getFOI("MVRectangleTool"+this.id);
}
MVRectangleTool.prototype.getRectangleDIV=function()
{
return this._f626;
}
MVRectangleTool.prototype.addEventListener=function(x50,x51)
{
this.setEventListener(x50,x51);
}
MVRectangleTool.prototype.setEventListener=function(x52,x53)
{
if(x52=="on_start"||x52==MVEvent.START)
this._f480=x53;
else if(x52=="on_finish"||x52==MVEvent.FINISH)
this._f328=x53;
else if(x52=="on_drag"||x52==MVEvent.DRAG)
this._f481=x53;
}
MVRectangleTool.prototype.attachEventListener=function(x54,x55)
{
MVUtil.attachEventListener(this._f98,x54,x55)
}
MVRectangleTool.prototype.detachEventListener=function(x56,x57)
{
MVUtil.detachEventListener(this._f98,x56,x57);
}
MVRectangleTool.prototype.getStatus=function()
{
return this._f485;
}
MVRectangleTool.prototype.getWidth=function(x58)
{
if(!x58)
{
return Math.abs(this._f474-this._f476);
}
else
 {
return MVSdoGeometry.getDistance(this._f474,this._f475,
MVUtil.transLongitudeOnWarpAroungMap(this.parent,this._f474,this._f476),
this._f475,this.parent.srid,x58);
}
}
MVRectangleTool.prototype.getHeight=function(x59)
{
var x60=this._f475;
var x61=this._f477
if(x60>this.parent.msi._f274)
x60=this.parent.msi._f274;
else if(x60<this.parent.msi._f275)
x60=this.parent.msi._f275;
if(x61>this.parent.msi._f274)
x61=this.parent.msi._f274
else if(x61<this.parent.msi._f275)
x61=this.parent.msi._f275
if(!x59)
return Math.abs(x60-x61);
else
 return MVSdoGeometry.getDistance(this._f474,x60,this._f474,x61,this.parent.srid,x59);
}
MVRectangleTool.prototype.setDivStyle=function(x62)
{
var x63="#FF0000";
var x64=2;
var x65="dashed";
var x66="#555555";
var x67=0.3;
if(x62.borderColor!=undefined)
x63=x62.borderColor;
if(x62.borderWidth!=undefined)
x64=x62.borderWidth;
if(x62.borderDashed!=undefined)
{
if(!x62.borderDashed)
x65="solid";
}
if(x62.fillColor!=undefined)
x66=x62.fillColor;
if(x62.fillOpacity!=undefined)
x67=x62.fillOpacity;
this.setDivFillColor(x66,x67);
this.setDivBorderStyle(x63,x64,x65);
}
function MVCircleTool(circleStyle,_f470)
{
this.parent=null;
this._f471=document.createElement("div");
this._f471.style.position="absolute";
this._f471.style.visibility="hidden";
this._f471.style.zIndex=5000;
this._f472=new Object();
this._f472._f473=2;
this._f472.divHeight=2;
this._f472.zIndex=5001;
this._f472.backgroundColor="#FF0000";
this._f472.opacity=null;
this._f470=_f470;
this._f312=circleStyle;
this._f474=0;
this._f475=0;
this._f476=0;
this._f477=0;
this.stepPx=6;
this._f478=0;
this._f479=0;
this._f98=new Array();
this._f480=null;
this._f328=null;
this._f481=null;
this._f482=null;
this._f356=null;
var _f483=eval(_f11._f483);
_f11._f483=(++_f483);
this.id=_f483;
this._f484="MVCircleTool";
this._f485=0;
this.refresh=false;
var ct=this;
this._f486=function(x0)
{
x0=(x0)?x0:((window.event)?event:null);
if(ct.parent._f487)
MVUtil._f173(x0);
};
this._f488=function()
{
if(this.netscape==null)
{
if(_f11._f489("netscape"))
this.netscape=true;
else
 this.netscape=false;
}
if(this.netscape)
{
window.addEventListener("keypress",this._f486,false);
}
}
this._f490=function()
{
if(this.netscape==null)
{
if(_f11._f489("netscape"))
this.netscape=true;
else
 this.netscape=false;
}
if(this.netscape)
window.removeEventListener("keypress",this._f486,false);
}
this.refreshCircle=function()
{
var x1=ct.parent;
if(x1.wraparound)
{
if(ct.getStatus()==3)
{
var x2=ct.getCircleFOI();
if(x2)
{
x2.areacode=ct.parent._f248.centerArea;
}
}
}
}
}
MVCircleTool.prototype.setPlotDivSize=function(x0,x1)
{
if(x0!=null)
{
if(isNaN(x0))
{
MVi18n._f6("MVCircleTool.setPlotDivSize","MAPVIEWER-05519","width",
this.parent?this.parent._f7:null);
}
else
 {
x0=Math.round(x0);
this._f472._f473=x0;
}
}
if(x1!=null)
{
if(isNaN(x1))
{
MVi18n._f6("MVCircleTool.setPlotDivSize","MAPVIEWER-05519","height",
this.parent?this.parent._f7:null);
}
else
 {
x1=Math.round(x1);
this._f472.divHeight=x1;
}
}
}
MVCircleTool.prototype.setPlotDivFillColor=function(x2,x3)
{
if(x2!=null&&x2!="")
this._f472.backgroundColor=x2;
if(isNaN(x3)||x3<0||x3>1)
{
MVi18n._f6("MVCircleTool.setPlotDivFillColor","MAPVIEWER-05519","opacity",
this.parent?this.parent._f7:null);
}
else
 {
this._f472.opacity=x3;
}
}
MVCircleTool.prototype.setPlotDivZindex=function(x4)
{
if(x4!=null)
{
if(isNaN(x4))
{
MVi18n._f6("MVCircleTool.setPlotDivZindex","MAPVIEWER-05519","zindex",
this.parent?this.parent._f7:null);
}
else
 {
this._f472.zIndex=x4;
this._f471.style.zIndex=(x4-1);
}
}
}
MVCircleTool.prototype._f491=function()
{
this._f474=this.parent._f350();
this._f475=this.parent._f351();
}
MVCircleTool.prototype._f492=function()
{
this._f476=this.parent._f350();
this._f477=this.parent._f351();
}
MVCircleTool.prototype._f493=function()
{
if(this._f485!=3)
{
return;
}
var x5=this.parent._f76;
var x6=this.parent._f78;
var x7=Math.round((this._f474-this.parent._f222())*x5+(this.parent._f109()/2));
var x8=Math.round((this._f475-this.parent._f223())*(-x6)+(this.parent._f110()/2));
var x9=(this._f476-this._f474)*x5;
var x10=(this._f477-this._f475)*(-x6);
var x11=Math.round(Math.sqrt(x9*x9+x10*x10));
var x12=Math.floor(x5*(this.parent.msi._f158-this.parent.msi._f159)/2);
if(x11>x12)
{
x11=x12;
this._f482=Math.floor((this.parent.msi._f158-this.parent.msi._f159)/2);
}
else
 this._f482=x11/x5;
var x13=2*(x11+this._f472._f473);
var x14=2*(x11+this._f472.divHeight);
var x15=x7-x11-Math.round((this._f472._f473)/2);
var x16=x8-x11-Math.round((this._f472.divHeight)/2);
this._f471.style.left=x15+'px';
this._f471.style.top=x16+'px';
this._f471.style.width=x13+'px';
this._f471.style.height=x14+'px';
this._f471.style.clip="rect(0px, "+x14+"px, "+x13+"px, 0px)";
this._f471.style.visibility="visible";
var x17=(this._f484+this.id+"PlotsDiv");
var x18=document.getElementById(x17);
if(x18!=null)
this._f471.removeChild(x18);
x18=document.createElement("div");
x18.id=x17;
x18.style.position="absolute";
x18.style.left=0+'px';
x18.style.top=0+'px';
this._f471.appendChild(x18);
var x19=(this.stepPx)/x11;
x19=x19>1?1:x19;
var x20=Math.acos(x19);
var x21=Math.asin(x19);
var x22=x20<x21?x20:x21;
var x23=50;
if(x22==0)
x22=Math.PI/(2*x23);
var x24=Math.ceil(Math.PI/(2*x22));
for(var x25=0;x25<x24;++x25)
{
var x26=x25*x22;
x9=Math.round(x11*Math.cos(x26));
x10=Math.round(x11*Math.sin(x26));
this._f494((x11+x9),(x11+x10));
this._f494((x11+x9),(x11-x10));
this._f494((x11-x9),(x11+x10));
this._f494((x11-x9),(x11-x10));
}
}
MVCircleTool.prototype._f494=function(x27,x28)
{
var x29=(this._f484+this.id+"PlotsDiv");
var x30=document.getElementById(x29);
if(x30==null)
{
MVi18n._f6("MVCircleTool.plotPos","MAPVIEWER-05500","null plodsDiv found",
this.parent?this.parent._f7:null);
return;
}
var x31=document.createElement("DIV");
x31.style.position="absolute";
x31.style.zIndex=this._f472.zIndex;
if(this._f472.backgroundColor!=null)
x31.style.backgroundColor=this._f472.backgroundColor;
if((this._f472.opacity)!=null)
{
if(window.ActiveXObject)
{
x31.style.filter="alpha(opacity="+((this._f472.opacity)*100)+")";
}
else
 {
x31.style.opacity=this._f472.opacity;
}
}
x31.style.left=x27+"px";
x31.style.top=x28+"px";
x31.style.width=(this._f472._f473)+"px";
x31.style.height=(this._f472.divHeight)+"px";
x31.style.clip="rect(0px, "+(this._f472._f473)+"px, "+
(this._f472.divHeight)+"px, 0px)";
x30.appendChild(x31);
}
MVCircleTool.prototype.generateArea=function()
{
if(this._f485!=3)
{
return;
}
var x32=this._f484+this.id;
this.parent.removeFOI(x32);
var x33=this._f482;
this._f356=MVSdoGeometry.createCirclePolygon(this._f474,this._f475,x33,this.parent.srid);
if(!(this._f312))
return;
var x34=new MVFOI(x32,this._f356,this._f312,this._f470);
var x35=this._f474-x33;
x34.areacode=MVUtil.calAreaCodeFromCenter(this.parent,x35);
x34.setClickable(false);
x34.enableInfoTip(false);
this.parent.addFOI(x34);
}
MVCircleTool.prototype.init=function()
{
this.parent.removeFOI(this._f484+this.id);
this._f485=0;
this.parent._f353();
this._f485=1;
var x36=this.parent;
var x37=x36._f215;
var x38=this;
var x39;
MVUtil.runListeners(x38._f98,MVEvent.INIT,[MVToolBar.BUILTIN_CIRCLE]);
var x40=function()
{
x36._f487=true;
var x41=x36._f222();
var x42=x36._f223();
if((x38._f478)!=x41||(x38._f479)!=x42||(x38.refresh))
{
x38.refresh=false;
if(x38._f485==2)
{
x38._f485=3;
}
x38._f476+=(x41-x38._f478);
x38._f477+=(x42-x38._f479);
x38._f478=x41;
x38._f479=x42;
x38._f493();
}
}
x37.onmousedown=MVUtil._f106(x37,function(x43)
{
x36.removeFOI(x38._f484+x38.id);
x38._f485=2;
x43=(x43)?x43:((window.event)?event:null);
x36._f496(x43);
x38._f491();
x38._f492();
x38._f471.style.visibility="hidden";
if(x38._f480!=null)
(x38._f480)();
MVUtil.runListeners(x38._f98,MVEvent.START);
x38._f478=x36._f222();
x38._f479=x36._f223();
var x44=5;
x39=setInterval(x40,x44);
x38._f488();
return false;
});
x37.onmousemove=MVUtil._f106(x37,function(x45)
{
if(x38._f485==2)
{
x38._f485=3;
}
if(x38._f485==3)
{
x45=(x45)?x45:((window.event)?event:null);
x36._f496(x45);
x38._f492();
x38.refresh=true;
if(x38._f481)
x38._f481();
MVUtil.runListeners(x38._f98,MVEvent.DRAG);
}
return false;
});
x37.onmouseup=MVUtil._f106(x37,function(x46)
{
x46=(x46)?x46:((window.event)?event:null);
x36._f496(x46);
x38._f492();
x38.generateArea();
var x47=document.getElementById(x38._f484+x38.id+"PlotsDiv");
if(x47!=null)
x38._f471.removeChild(x47);
x38._f471.style.width="0px";
x38._f471.style.height="0px";
x38._f471.style.clip="rect(0px, 0px, 0px, 0px)";
x38._f471.style.visibility="hidden";
clearInterval(x39);
x36._f353();
x38._f490();
if(x38._f328!=null)
(x38._f328)();
MVUtil.runListeners(x38._f98,MVEvent.FINISH);
return false;
});
x37.onmouseout=MVUtil._f106(x37,function(x48)
{
if(x38._f485!=3)
return false;
if(!(window.ActiveXObject))
{
x48=(x48)?x48:((window.event)?event:null);
var x49=x48.clientX;
var x50=x48.clientY;
var x51=x36._f109();
var x52=x36._f110();
var x53=MVUtil._f497(this);
var x54=x48.pageX-x53.x;
var x55=x48.pageY-x53.y;
if(x54<=0||x54>=x51||
x55<=0||x55>=x52||
x49<=0||x49>=document.body.clientWidth||
x50<=0||x50>=document.body.clientHeight)
{
return this.onmouseup(x48);
}
}
});
this.parent._f248.attachEventListener("refreshFOI",this.refreshCircle);
}
MVCircleTool.prototype.clear=function()
{
this.parent.removeFOI(this._f484+this.id);
this._f485=0;
this.parent._f353();
MVUtil.runListeners(this._f98,MVEvent.CLEAR);
}
MVCircleTool.prototype.getCircle=function()
{
if(this._f485==3&&this._f482)
return MVSdoGeometry.createCircle(this._f474,this._f475,this._f482,this.parent.srid);
else if(this._f356!=null)
return (this._f356);
else
 return null;
}
MVCircleTool.prototype.getCirclePolygon=function()
{
if(this._f485==3&&this._f482)
return MVSdoGeometry.createCirclePolygon(this._f474,this._f475,this._f482,this.parent.srid);
else if(this._f356!=null)
return (this._f356);
else
 return null;
}
MVCircleTool.prototype.getRadius=function(x56)
{
if(!x56)
return this._f482;
else
 return MVSdoGeometry.getDistance(this._f474,this._f475,this._f474,this._f475+this._f482,this.parent.srid,x56);
}
MVCircleTool.prototype.getCenter=function()
{
return MVSdoGeometry.createPoint(this._f474,this._f475,this.parent.srid);
}
MVCircleTool.prototype.getCircleFOI=function()
{
return this.parent.getFOI(this._f484+this.id);
}
MVCircleTool.prototype.addEventListener=function(x57,x58)
{
this.setEventListener(x57,x58);
}
MVCircleTool.prototype.setEventListener=function(x59,x60)
{
if(x59=="on_start"||x59==MVEvent.START)
this._f480=x60;
else if(x59=="on_finish"||x59==MVEvent.FINISH)
this._f328=x60;
else if(x59=="on_drag"||x59==MVEvent.DRAG)
this._f481=x60;
}
MVCircleTool.prototype.attachEventListener=function(x61,x62)
{
MVUtil.attachEventListener(this._f98,x61,x62)
}
MVCircleTool.prototype.detachEventListener=function(x63,x64)
{
MVUtil.detachEventListener(this._f98,x63,x64);
}
MVCircleTool.prototype.getStatus=function()
{
return this._f485;
}
MVCircleTool.prototype.setAreaStyle=function(x65)
{
this._f312=x65;
}
function MVi18n()
{
}
MVi18n._f498=null;
MVi18n._f499=null;
MVi18n._f500=null;
MVi18n.URL=null;
MVi18n.language=null;
MVi18n.country=null;
MVi18n.fetched=false;
MVi18n.timeoutCount=0;
MVi18n.isFetched=function()
{
if(MVi18n.fetched)
return true;
MVi18n.timeoutCount++;
if(MVi18n.timeoutCount>10)
MVi18n.fetched=true;
return MVi18n.fetched;
}
MVi18n._f6=function(x0,x1,x2,x3)
{
if(x2)
x2=" ("+x2+")";
else
 x2="";
if(x3)
x3("["+x0+"] "+MVi18n._f453(x1)+x2);
else
 MVi18n.alert("["+x0+"] "+MVi18n._f453(x1)+x2);
}
MVi18n._f453=function(x4)
{
if(MVMessages[x4])
return x4+": "+MVMessages[x4];
else
 return x4;
}
MVi18n._f340=function(x5)
{
if(MVMessages[x5])
return MVMessages[x5];
else
 return '';
}
MVi18n._f501=function()
{
if(MVi18n.URL==null)
return;
var localDomain=(MVUtil._f9(MVi18n.URL)==MVUtil._f9(_f11._f119()));
var xmlHttp=localDomain||MVMapView._f8;
if(MVMapView._f8&&!localDomain)
MVi18n.URL=_f11._f12()+"?rtarget="+MVi18n.URL;
var url=null;
url=encodeURI(MVi18n.URL+"/mcserver");
try
{
var _f278=MVUtil.getXMLHttpRequest(xmlHttp);
_f278.open("POST",url,true);
_f278.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
_f278.onreadystatechange=MVUtil._f106(_f278,function()
{
try
{
if(this.readyState!=4)
return;
if(this.status==200)
text=this.responseText;
MVUtil.gc();
eval(text);
MVi18n.fetched=true;
}
catch(e)
{
MVi18n.fetched=true;
return ;
}
});
var params="request=getinfomsgs";
if(MVi18n.language)
params=params+"&language="+MVi18n.language;
if(MVi18n.country)
params=params+"&country="+MVi18n.country;
_f278.send(encodeURI(params));
}
catch(ex)
{
MVi18n._f6("MVi18n.fetchMessages","MAPVIEWER-05511",ex);
}
}
MVi18n.addBehavior=function(x6,x7,x8)
{
if(x6==null||(x6!="func"&&x6!="eval"))
{
MVi18n.alert("MethodType Error");
return;
}
if(!x7)
{
MVi18n.alert("MethodName Error");
return;
}
if(x8==null||((x8.toLowerCase())!="url"&&(x8.toLowerCase())!="text"))
{
MVi18n.alert("ArgType Error");
return;
}
MVi18n._f498=x6;
MVi18n._f499=x7;
MVi18n._f500=x8;
}
MVi18n.setURL=function(x9)
{
if(x9==null||x9=="")
{
MVi18n.alert("Parameter should not be null or empty. Error occurs in MVi18n.setURL(...)");
return;
}
MVi18n.URL=x9;
}
MVi18n.alert=function(x10)
{
if(MVi18n._f499)
{
var x11=MVi18n._f499;
x11(x10);
}
else
 alert(x10);
}
function MVMapView(x0,x1)
{
if(!x0)
{
MVi18n._f6("MVMapView.constructor","MAPVIEWER-05519","mapDiv");
return ;
}
MVUtil.objArray.push(this);
var x2=this;
if(!MVi18n._f499)
MVi18n.addBehavior("func",alert,"text");
this._f145=MVUtil._f653++;
this.center=null;
this.originalCenter=null;
this.reCenterTag=false;
this._f95=0;
this._f96=0;
this._f76=0;
this._f78=0;
this.tileWidth=0;
this.tileHeight=0;
this._f675=200;
this._f676=200;
this._f677=0;
this._f678=0;
this._f679=1;
this._f680=0;
this._f681=0;
this._f21=0;
this._f682=false;
this._f265=false;
this._f647=0;
this._f648=0;
this._f683=null;
this._f684=null;
this._f685=0.0;
this._f686=0.0;
this._f687=0.0;
this._f688=0.0;
this._f689=0.0;
this._f690=0.0;
this._f75=0.0;
this._f293=0.0;
this._f287=0.0;
this._f77=0.0;
this._f691=false;
this.msi=null;
this._f692=false;
this._f693=true;
this._f177=null;
this._f694=new Array();
this._f695=new Array();
this._f696=null;
this._f135=new Array();
this.mapLayerNumber=0;
this._f697=new Array();
this._f80=null;
this._f248=null;
this._f634=null;
this._f698=2;
this._f699=4;
this._f700=4;
this._f701=null;
this._f297=false;
this._f702=0;
this._f93=false;
this._f703=new Array();
this._f211=null;
this._f212=false;
this._f216=true;
this._f704=null;
this.srid=null;
this._f705=null;
this._f706=0;
this._f707=true;
this._f708="one_time";
this._f709=false;
this._f710=null;
this._f711=false;
this._f712=false;
this._f713=false;
this._f714=false;
this._f715=null;
this._f716=1;
this.centerMarkUrl=null;
this.centerMarkW=0;
this.centerMarkH=0;
this._f98=new Array();
var x3=String(document.location);
var x4=x3.indexOf("//");
this._f10=_f11._f119();
this._f717=this._f10;
this._f4=this._f10;
this._f209="default";
this._f208="move";
this._f718="default";
if(x1)
{
this._f717=x1;
this._f4=x1;
if(MVUtil._f5(x1,'/'))
{
this._f717=x1.substring(0,x1.length-1);
this._f4=x1.substring(0,x1.length-1);
}
if(MVUtil._f9(this._f10)!=MVUtil._f9(this._f717))
{
this._f717=_f11._f12()+"?rtarget="+this._f717;
}
}
_f11._f558=this._f4+"/foi";
_f11._f142=this._f4+_f11._f553;
MVi18n.setURL(this._f4);
if(x0==null)
return null;
MVUtil._f191(x0);
this.real_base_div=x0;
if(this.real_base_div.style.position!="absolute")
{
this.real_base_div.style.position="relative";
}
this._f215=document.createElement("div");
this.real_base_div.appendChild(this._f215);
this._f215.style.position="absolute";
this._f215.style.left=MVUtil._f32(0);
this._f215.style.top=MVUtil._f32(0);
this._f215.style.width="100%";
this._f215.style.height="100%";
MVUtil._f146(this._f215);
MVUtil._f146(this.real_base_div);
this._f215.style.overflow="hidden";
this._f428=document.createElement("img");
this._f428.src=_f11._f142+"transparent."+(_f11._f70=="IF"?"gif":"png");
this._f428.src+="?refresh="+(new Date()).getTime();
this._f428.style.position="absolute";
this._f428.style.left=MVUtil._f32(0);
this._f428.style.top=MVUtil._f32(0);
this._f428.onload=MVUtil._f106(this,function(){_f11._f563=true;});
this._f215.appendChild(this._f428);
this._f290=document.createElement("div");
this._f290.style.position="absolute";
this._f290.style.left=MVUtil._f32(0);
this._f290.style.top=MVUtil._f32(0);
this.real_base_div.appendChild(this._f290);
this.div=null;
var x5=this.getBrowserType();
this.toolBarContainer=null;
this._f719();
MVUtil._f146(this.div);
this._f720="";
this._f299=0;
this._f300=0;
this._f721();
this._f633=null;
this._f82=document.createElement("div");
MVUtil._f146(this._f82);
this._f82.style.position="absolute";
this._f82.style.zIndex=125;
this.div.appendChild(this._f82);
this._f722=null;
this._f723=null;
this._f570=null;
this._f724=null;
this._f725=null;
this._f726=null;
this._f727=new _f728();
this._f729=true;
this.draggingEnabled=true;
this._f730="zoomin";
this._f394=null;
this._f731=true;
this._f732=null;
this._f733=false;
this._f734=null;
this._f735=null;
this._f736=0;
this._f737=0;
this._f738=false;
this._f739=0;
this._f740=null;
this._f741=5;
this._f175=false;
this._f94=new Array();
this.infoTipDiv=null;
this._f171=0;
this._f169=0;
this._f742=null;
this._f743=null;
this._f744=null;
this._f745=null;
this._f746=null;
this._f747=null;
this._f748=null;
this._f749=null;
this._f487=false;
this._f534=null;
this._f68="PNG24";
this.foiBgColor=null;
this.zoomAnimationEnabled=true;
this._f750=false;
this.dragCoords=null;
this.wraparound=false;
this.maptype=null;
this._f751=true;
this._f7=null;
this._f752=0;
this._f753=0;
this._f754=0;
this._f755=0;
this._f756=0;
this._f757=-1;
this._f758=-1;
if(!MVi18n.isFetched())
MVi18n._f501();
return this;
}
MVMapView.prototype._f719=function()
{
if(_f11._f70=="IF"&&document.compatMode&&document.compatMode!="BackCompat")
{
document.body.style.height="100%";
}
if(this.real_base_div.style.backgroundColor=="")
this.real_base_div.style.backgroundColor="lightgrey";
this.div=document.createElement("div");
this.div.style.position="absolute";
if(_f11._f70=="IF"&&document.compatMode&&document.compatMode!="BackCompat")
{
this.div.style.width=MVUtil._f32(this._f109());
this.div.style.height=MVUtil._f32(this._f110());
}
else
 {
this.div.style.width="100%";
this.div.style.height="100%";
}
this.div.style.zIndex=80;
this.div.style.left=MVUtil._f32(0);
this.div.style.top=MVUtil._f32(0);
this._f290.style.left=MVUtil._f32(0);
this._f290.style.top=MVUtil._f32(0);
this._f140=0;
this._f141=0;
this._f415=this._f140;
this._f416=this._f141;
this._f759=0;
this._f760=0;
this._f215.appendChild(this.div);
var x0=document.createElement("table");
x0.style.zIndex=9999;
x0.style.height=MVUtil._f32(15);
x0.style.position="absolute";
x0.style.right=MVUtil._f32(0);
x0.style.bottom=MVUtil._f32(0);
x0.style.backgroundColor="white";
this._f215.appendChild(x0);
var x1=document.createElement("tbody");
x0.appendChild(x1);
this.toolBarContainer=document.createElement("tr");
x1.appendChild(this.toolBarContainer);
}
MVMapView.prototype._f761=function(x2,x3)
{
this._f759=x2-this._f140;
this._f760=x3-this._f141;
}
MVMapView.prototype._f504=function(x4,x5)
{
this.reCenterTag=true;
this._f140=x4-this._f759;
this._f141=x5-this._f760;
this._f415=MVUtil._f84(this.div);
this._f416=MVUtil._f85(this.div);
var x6=this._f762(this._f415,this._f140,this._f416,this._f141);
this._f140=x6.left;
this._f141=x6.top;
var x7=this._f95+(this._f415-this._f140)/this._f76;
var x8=this._f96-(this._f416-this._f141)/this._f78;
this._f763(this._f140-this._f415,this._f141-this._f416,
x7,x8);
var x9=this._f95;
var x10=this._f96;
this._f95=x7;
this._f96=x8;
if(x9!=this._f95||x10!=this._f96)
{
this._f691=true;
}
MVUtil._f74(this.div,this._f140,this._f141);
MVUtil._f74(this._f290,this._f140,this._f141);
}
MVMapView.prototype.addEventListener=function(x11,x12)
{
this.setEventListener(x11,x12);
}
MVMapView.prototype.setEventListener=function(x13,x14)
{
if(x13==MVEvent.RECENTER)
{
this._f724=x14;
}
else if(x13==MVEvent.MOUSE_CLICK)
{
this._f570=x14;
}
else if(x13==MVEvent.MOUSE_RIGHT_CLICK)
{
this._f725=x14;
}
else if(x13==MVEvent.ZOOM_LEVEL_CHANGE)
{
this._f722=x14;
}
else if(x13==MVEvent.BEFORE_ZOOM_LEVEL_CHANGE)
{
this._f723=x14;
}
else if(x13==MVEvent.MOUSE_DOUBLE_CLICK)
{
this._f394=x14;
}
else if(x13==MVEvent.INITIALIZE)
{
this.initializeListener=x14;
}
}
MVMapView.prototype.attachEventListener=function(x15,x16)
{
MVUtil.attachEventListener(this._f98,x15,x16)
}
MVMapView.prototype.detachEventListener=function(x17,x18)
{
MVUtil.detachEventListener(this._f98,x17,x18);
}
MVMapView.prototype.init=function()
{
MVUtil._f74(this.div,0,0);
MVUtil._f74(this._f290,0,0);
if(this._f80._f81.length>0)
this._f80.deleteInfoWindow(this._f80._f81[0]);
this._f140=0;
this._f141=0;
this._f415=this._f140;
this._f416=this._f141;
this._f759=0;
this._f760=0;
this.setZoomLevel(this._f21);
this._f764();
var x19=MVSdoGeometry.createPoint(this._f95,this._f96,this.srid);
x19=this._f765(x19);
this._f95=x19.getPointX();
this._f96=x19.getPointY();
this._f736=this._f109();
this._f737=this._f110();
if(this._f740)
clearTimeout(this._f740);
this.setLoadingIconVisible(true);
this._f647=this._f680+(this._f677/2*this.tileWidth);
this._f648=this._f681+(this._f678/2*this.tileHeight);
this._f766();
if(!this._f93)
{
if(this._f767())
return ;
}
if(this._f634&&!this._f634._f566&&!this._f634._f123)
this._f768(this._f634);
if(this.navigationPanel&&!this.navigationPanel._f123)
this.navigationPanel.init(this._f215,this);
for(var x20=0;x20<this._f135.length;x20++)
{
if(this._f135[x20].layerControl)
this._f135[x20].layerControl._f432(this._f109(),this._f110(),
this._f95,this._f96);
else if(this._f135[x20].isExtAPITileLayer())
{
this._f508(this._f135[x20],this._f95,this._f96,this._f21);
}
}
if(!this._f80)
this._f80=new _f288(this);
this._f126();
this._f171=this._f75;
this._f169=this._f77;
this.refreshThemeBasedFOIs(true);
this._f280();
this._f80.refresh(this._f75,this._f293,
this._f287,this._f77,
this._f109(),this._f110());
this._f265=true;
this._f682=true;
if(this._f715!=null)
for(var x20=0;x20<this._f715.length;x20++)
if(this._f715[x20]!=null&&!this._f715[x20]._f93)
{
var id="_md_"+this._f716
this._f715[x20].init(id,this,this._f215);
this._f716++;
this._f715[x20]._f93=true;
if(this._f715[x20].afterDisAction)
{
this._f715[x20].afterDisAction();
}
}
if(this._f211!=null&&this._f211._f200==null)
this._f211.init(this._f211._f198,this);
this._f83();
this._f740=this.setTimeout("this.checkSize()",400);
if(!this._f93)
{
this._f93=true;
if(this.initializeListener)
this.initializeListener();
MVUtil.runListeners(this._f98,MVEvent.INITIALIZE);
}
window.status=' ';
}
MVMapView.prototype.destroy=function()
{
while(this._f135&&this._f135.length>0)
{
var x21=this._f135.pop();
x21.destroy();
}
this._f135=null;
if(!this._f80)
{
this._f80.destroy();
this._f80=null;
}
while(this._f697&&this._f697.length>0)
{
var x22=this._f697.pop();
x22.destroy();
}
this._f697=null;
if(this._f248)
this._f248.destroy();
this._f248=null;
if(this._f715)
while(this._f715.length>0)
this._f715.pop();
this._f715=null;
if(this._f211)
{
this._f211.destroy();
this._f211=null;
}
MVUtil._f191(this._f215,this._f7);
this._f428=null;
this._f215=null;
this._f290=null;
this._f82=null;
this.div=null;
}
MVMapView.prototype._f764=function()
{
this.tileWidth=this._f694[this._f21];
this.tileHeight=this._f695[this._f21];
this._f769();
}
MVMapView.prototype._f769=function()
{
this._f680=-this.tileWidth*this._f679;
this._f681=-this.tileHeight*this._f679;
var x23=this._f109()/this.tileWidth;
this._f677=parseInt(Math.ceil(x23/2)+1)*2+1;
x23=this._f110()/this.tileHeight;
this._f678=parseInt(Math.ceil(x23/2)+1)*2+1;
this._f647=this._f680+(this._f677/2*this.tileWidth);this._f648=this._f681+(this._f678/2*this.tileHeight);var x24=this.msi.mapConfig.zoomLevels[this._f21];
this._f76=x24.tileImageWidth/x24.tileWidth;this._f78=x24.tileImageHeight/x24.tileHeight;}
MVMapView.prototype._f766=function()
{
this._f684=new _f368(this.msi.mapConfig,this._f95,this._f96,this._f21);this._f683=new _f368(this.msi.mapConfig,this._f95,this._f96,this._f21);this._f683._f374(-(Math.ceil(this._f677/2)-1),+Math.ceil(this._f678/2)-1);
}
MVMapView.prototype._f126=function()
{
this._f687=this._f683.minX;
this._f688=(this._f683.minY+this._f683._f370);
this._f689=(this._f683.minX+(this._f683._f369*this._f677));
this._f690=(this._f683.minY-(this._f683._f370*(this._f678-1)));
this._f75=this._f770(this._f95);
this._f293=this._f771(this._f96);
this._f287=this._f772(this._f95);
this._f77=this._f773(this._f96);
}
MVMapView.prototype.display=function()
{
var x25=this;
var x26=function()
{
var x27=0;
if(!x25._f215)
return ;
if(x25._f215.offsetWidth)
x27=parseInt(x25._f215.offsetWidth+"");
else if(typeof(x25._f215.offsetWidth)=="undefined")
x27=x25._f109();
var x28=0;
if(x25._f215.offsetHeight)
x28=parseInt(x25._f215.offsetHeight+"");
else if(typeof(x25._f215.offsetHeight)=="undefined")
x28=x25._f110();
if(!MVi18n.isFetched()||x27<=0||x28<=0||!x25.tileLayersConfigLoaded()||
!_f11._f563||!x25.msi)
{
setTimeout(x26,200);
return ;
}
var x29=function()
{
x25.init();
if(!x25._f714)
{
x25._f353();
x25._f714=true;
}
x25._f93=true;
while(x25._f94.length>0)
{
var x30=x25._f94.shift();
if(x30.func=="refreshFOIs")
x30.obj._f79(x30.params[0],x30.params[1],x30.params[2],x30.params[3],x30.params[4],x30.params[5],x30.params[6],x30.params[7],true,x30.params[8]);
else if(x30.func=="zoomToRectangle")
x30.obj.zoomToRectangle(x30.params[0],x30.params[1]);
else if(x30.func=="refresh")
x30.obj.refresh(x30.params[0]);
}
}
x25._f774(x29);
}
x26();
}
MVMapView.prototype.setCenter=function(x31,x32)
{
this.reCenterTag=true;
this.center=x31;
if(this._f93)
this._f774(null,x32);
}
MVMapView.prototype._f774=function(x33,x34)
{
if(!this.center)
{
if(x33)
x33();
return ;
}
if(!this.msi)
{
MVi18n._f6("MVMapView.setCenter","MAPVIEWER-05518",null,this._f7);
return ;
}
this.srid=this.msi.getSrid();
var x35=this.center;
if(!this.reCenterTag&&this.originalCenter!=null)
x35=this.originalCenter;
if(x35)
{
if(!x35.srid)
x35.srid=this.srid;
this.center=null;
if(!(x35.sdo_point))
{
MVi18n._f6("MVMapView.setCenter","MAPVIEWER-05505",null,this._f7);
return ;
}
var x36=this;
var x37=function(x38)
{
if(x38)
x35=x38;
if(x36._f93)
x35=x36._f765(x35);
if(!x36._f93||(x36.mapLayerNumber<x36._f135.length))
{
x36.mapLayerNumber++;
x36._f95=x35.getPointX();
x36._f96=x35.getPointY();
x36._f775(x35.getPointX(),x35.getPointY());
}
else
 {
x36._f776=(x36._f95-x35.getPointX())*x36._f76;
x36._f777=(x36._f96-x35.getPointY())*x36._f78*(-1);
x36._f778=x36._f109();
x36._f779=x36._f110();
if(Math.abs(x36._f776)<=(x36._f778+x36._f675)&&Math.abs(x36._f777)<=(x36._f779+x36._f676))
{
x36._f263=Math.sqrt(x36._f776*x36._f776+x36._f777*x36._f777);
x36._f780=20;
x36._f781=x36._f263>((x36._f778+x36._f779)/4)?300:200;
x36._f782=0;
x36._f783=0;
x36._f784=0;
x36.smoothPan(x34);
}
else
 {
x36._f95=x35.getPointX();
x36._f96=x35.getPointY();
x36.display();
if(x36._f724)
{
x36._f724();
}
MVUtil.runListeners(x36._f98,MVEvent.RECENTER);
}
if(x36._f634)
{
x36._f634._f642(x36);
}
}
if(x33)
x33();
}
if(this.srid!=x35.srid)
{
this.reCenterTag=true;
x35=this.transformGeom(x35,this.srid,null,x37);
}
else
 x37();
}
else
 {
if(x33)
x33();
}
}
MVMapView.prototype.setCenterLon=function(x39)
{
this._f95=x39;
}
MVMapView.prototype.setCenterLat=function(x40)
{
this._f96=x40;
}
MVMapView.prototype.setCenterMark=function(x41,x42,x43)
{
if(this._f785!=undefined)
{
this._f215.removeChild(this._f785);
delete this._f785;
}
if(x41==null)
return ;
this.centerMarkUrl=x41;
this.centerMarkW=x42;
this.centerMarkH=x43;
var x44=document.createElement("img");x44.src=x41;
x44.style.zIndex=2000;
x44.style.position="absolute";
x44.style.width=MVUtil._f32(x42);
x44.style.height=MVUtil._f32(x43);
x44.style.left=MVUtil._f32((this._f109()-x42)/2);
x44.style.top=MVUtil._f32((this._f110()-x43)/2);
this._f215.appendChild(x44);
this._f785=x44;
}
MVMapView.prototype.setZoomLevel=function(x45)
{
if(isNaN(x45))
{
MVi18n._f6("MVMapView.setZoomLevel","MAPVIEWER-05506",null,this._f7);
return;
}
if((!this._f93)&&this.msi==null)
{
this._f21=x45;
return ;
}
if(x45<0)
x45=0;
else if(x45>this._f702)
x45=this._f702;
if(this._f93)
this.zoomTo(x45);
else
 {
this._f21=x45;
this._f764();
}
}
MVMapView.prototype.setHomeMap=function(x46,x47)
{
this._f705=x46;
if(x47)
this._f706=x47;
}
MVMapView.prototype.setMapBuffer=function(x48,x49)
{
this._f675=x48;
this._f676=x49;
}
MVMapView.prototype._f786=function(x50,x51)
{
var x52=false;
var x53=this;
var x54=function(x55)
{
if(x55)
{
x53._f95=x55.getPointX();
x53._f96=x55.getPointY();
x53.center=x55;
}
x53.msi=x50.msi;
x53.srid=x53.msi.getSrid();
x53.maptype=x53.msi.mapConfig.coordSys.type;
if(x53._f21>(x53.msi.zoomCount-1))
{
x53._f21=x53.msi.zoomCount-1;
}
for(var x56=0;x56<x53.msi.getMaxZoomLevel();x56++)
{
x53._f694[x56]=x53.msi._f524(x53._f21);
x53._f695[x56]=x53.msi._f525(x53._f21);
}
x53._f764();
x53._f702=x53.msi.getMaxZoomLevel()-1;
x53._f766();
x51(x52);
}
if(!x50.msi)
x50.msi=new _f512(x50._f108);
if(this.msi)
{
if(!x50.isVisible()||this.msi.getSrid()==x50.msi.getSrid())
{
x51(false);
return;
}
else
 {
x52=true;
var x57=MVSdoGeometry.createPoint(this._f95,this._f96,this.msi.getSrid());
if(!this.reCenterTag&&this.originalCenter!=null)
x57=this.originalCenter;
this.transformGeom(x57,x50.msi.getSrid(),null,x54);
}
}
else
 x54();
}
MVMapView.prototype.getMapBaseURL=function()
{
return this._f4;
}
MVMapView.prototype._f109=function()
{
var x58=0;
if(this._f215.style&&this._f215.style.width)
{
var x59=this._f215.style.width+"";
if(x59.indexOf("%")<0)
{
if(x59.indexOf("px")>0)
x58=parseInt(x59.substring(0,x59.indexOf("px")));
else
 x58=parseInt(x59);
if(x58)
return x58;
}
}
if(this._f215.offsetWidth)
x58=this._f215.offsetWidth;
else if(this._f215.width)
x58=parseInt(this._f215.width+"");
return x58;
}
MVMapView.prototype._f110=function()
{
var x60=0;
if(this._f215.style&&this._f215.style.height)
{
var x61=this._f215.style.height+"";
if(x61.indexOf("%")<0)
{
if(x61.indexOf("px")>0)
x60=parseInt(x61.substring(0,x61.indexOf("px")));
else
 x60=parseInt(x61);
if(x60)
return x60;
}
}
if(this._f215.offsetHeight)
x60=this._f215.offsetHeight;
else if(this._f215.width)
x60=parseInt(this._f215.width+"");
return x60;
}
MVMapView.prototype._f770=function(x62)
{
var x63=this._f109()/2;
return (0-x63)/this._f76+x62;
}
MVMapView.prototype._f771=function(x64)
{
var x65=this._f110()/2;
return -1.0*(x65-1)/this._f78+x64;
}
MVMapView.prototype._f772=function(x66)
{
var x67=this._f109()/2;
return (x67-1)/this._f76+x66;
}
MVMapView.prototype._f773=function(x68)
{
var x69=this._f110()/2;
return -1.0*(0-x69)/this._f78+x68;
}
MVMapView.prototype.getCenter=function()
{
var x70=MVSdoGeometry.createPoint(this._f95,this._f96,this.srid);
return x70;
}
MVMapView.prototype._f222=function()
{
return this._f95;
}
MVMapView.prototype._f223=function()
{
return this._f96;
}
MVMapView.prototype.getMapWindowBBox=function()
{
this._f126();
var x71=MVSdoGeometry.createRectangle(this._f75,this._f293,
this._f287,this._f77,this.srid);
return x71;
}
MVMapView.prototype.getMouseLocation=function()
{
var x72=MVSdoGeometry.createPoint(this._f685,this._f686,this.srid);
return x72;
}
MVMapView.prototype._f350=function()
{
return this._f685;
}
MVMapView.prototype._f351=function()
{
return this._f686;
}
MVMapView.prototype.getZoomLevel=function()
{
return this._f21;
}
MVMapView.prototype.getMaxZoomLevel=function()
{
if(!this.msi)
{
MVi18n._f6("MVMapView.getMaxZoomLevel","MAPVIEWER-05526",null,this._f7);
return -1;
}
else
 return this.msi.getMaxZoomLevel();
}
MVMapView.prototype.enableMapBoundConstraint=function(x73)
{
return this._f751=x73;
}
MVMapView.prototype.getBaseDiv=function()
{
return this._f215;
}
MVMapView.prototype._f787=function()
{
return this.div;
}
MVMapView.prototype._f788=function()
{
return this._f684._f370;
}
MVMapView.prototype._f789=function()
{
return this._f684._f369;
}
MVMapView.prototype._f790=function()
{
return this.msi;
}
MVMapView.prototype._f791=function()
{
return this._f687;
}
MVMapView.prototype._f792=function()
{
return this._f688;
}
MVMapView.prototype._f793=function()
{
return this._f689;
}
MVMapView.prototype._f794=function()
{
return this._f690;
}
MVMapView.prototype.getScreenMinLon=function()
{
return this._f75;
}
MVMapView.prototype._f795=function()
{
return this._f293;
}
MVMapView.prototype._f796=function()
{
return this._f287;
}
MVMapView.prototype._f797=function()
{
return this._f77;
}
MVMapView.prototype._f798=function()
{
return this._f76;
}
MVMapView.prototype._f799=function()
{
return this._f78;
}
MVMapView.prototype.zoomIn=function(x74)
{
this.zoomTo(this._f21+1,x74);
}
MVMapView.prototype.zoomOut=function(x75)
{
this.zoomTo(this._f21-1,x75);
}
MVMapView.prototype.setZoomAnimationEnabled=function(x76)
{
this.zoomAnimationEnabled=x76;
}
MVMapView.prototype.zoomTo=function(x77,x78)
{
var x79=this;
if(x79._f757<0)
x79._f757=x79._f21;
var x80=function()
{
x79._f800=0;
if(x77>=x79._f702)
{
x77=x79._f702;
}
else if(x77<0)
{
x77=0;
}
if(x79._f758==x79._f21&&x77==x79._f21&&!x78)
{
if(x79.navigationPanel)
x79.navigationPanel.updateSliderBar();
return false;
}
x79._f594();
x79._f21=x77;
if(x79.navigationPanel)
x79.navigationPanel.updateSliderBar();
x79._f21=x77;
x79._f764();
var x81=false;
var x82=MVSdoGeometry.createPoint(x79._f95,x79._f96,x79.srid);
var x83=function(x84)
{
if(x84)
x78=x84;
if(x78)
{
if(x78.getPointX()!=x79._f95||x78.getPointY()!=x79._f96)
{
x81=true;
}
x79._f95=x78.getPointX();
x79._f96=x78.getPointY();
}
if(x79._f723&&x79._f757!=x79._f21)
x79._f723(x79._f757,x79._f21);
if(x79._f757!=x79._f21)
MVUtil.runListeners(x79._f98,MVEvent.BEFORE_ZOOM_LEVEL_CHANGE,[x79._f757,x79._f21]);
var x85=function()
{
if(Math.abs(x79._f757-x79._f21)==1&&x79._f755<=1&&x79.zoomAnimationEnabled)
{
for(var x86=0;x86<x79._f135.length;x86++)
{
if(x79._f135[x86].layerControl)
x79._f135[x86].layerControl.zoomControl.showTiles(x79._f757,x79._f21,x82,x78);
}
}
x79.display();
x79._f755=0;
x79._f756=null;
x79._f758=x79._f21;
if(x81)
{
if(x79._f724)
x79._f724();
MVUtil.runListeners(x79._f98,MVEvent.RECENTER);
}
if(x79._f211)
{
x79._f211._f226(x79._f757,x79._f21);
x79._f211._f225();
}
if(x79._f757!=x79._f21)
{
var x87=x79._f757;
x79._f757=-1;
if(x79._f722)
x79._f722(x87,x79._f21);
MVUtil.runListeners(x79._f98,MVEvent.ZOOM_LEVEL_CHANGE,[x87,x79._f21]);
}
}
if(!x79._f755)
x79._f755=0;
x79._f755++;
if(x79._f756)
clearTimeout(x79._f756);
x79._f756=setTimeout(x85,200);
if(x79._f634)
{
x79._f634._f642(x79);
}
}
if(x78)
{
x79.reCenterTag=true;
x79.srid=x79.msi.getSrid();
if(!x78.srid)
x78.srid=x79.srid;
if(!(x78.sdo_point))
{
MVi18n._f6("MVMapView.zoomTo","MAPVIEWER-05505",null,x79._f7);
return ;
}
if(x78.srid&&(x79.srid!=x78.srid))
x78=x79.transformGeom(x78,x79.srid,null,x83);
else
 x83();
}
else
 x83();
return true;
}
if(!this._f800)
this._f800=0;
this._f800++;
if(x79._f756)
{
clearTimeout(x79._f756);
x79._f756=null;
}
if(this._f801)
clearTimeout(this._f801);
if(this._f800<2)
this._f801=setTimeout(x80,200);
else
 {
x80();
return ;
}
}
MVMapView.prototype.zoomToAtLocation=function(x88,x89)
{
this.srid=this.msi.getSrid();
var x90=this;
var x91=function(x92)
{
if(x92)
x88=x92;
var x93=(x90._f95-x88.getPointX())*x90._f76;
var x94=(x90._f96-x88.getPointY())*x90._f78;
var x95=x90._f694[x89]/x90.msi._f522(x89);
var x96=x90._f695[x89]/x90.msi._f523(x89);
var x97=x93/x95+x88.getPointX();
var x98=x94/x96+x88.getPointY();
var x99=MVSdoGeometry.createPoint(x97,x98,x90.srid);
x90.zoomTo(x89,x99);
}
if(x88.srid&&(this.srid!=x88.srid))
x88=this.transformGeom(x88,this.srid,null,x91);
else
 x91();
}
MVMapView.prototype.setCenterAndZoomLevel=function(x100,x101)
{
if(!this._f93)
{
this.center=x100;
this._f21=x101;
return ;
}
this.srid=this.msi.getSrid();
if(!x100.srid)
x100.srid=this.srid;
var x102=this;
var x103=function(x104)
{
if(x104)
x100=x104;
if(x101==null)
{
x101=x102._f21;
}
if(!x102._f93)
{
x102.setCenter(x100);
x102.setZoomLevel(x101);
return ;
}
if(x101==x102._f21)
{
if(!x102._f682)
{
x102.zoomTo(x101,x100);
return;
}
else {
x102._f776=(x102._f95-x100.getPointX())*x102._f76
x102._f777=(x102._f96-x100.getPointY())*x102._f78*(-1);
x102._f778=x102._f109();
x102._f779=x102._f110();
if(Math.abs(x102._f776)<=(x102._f778+x102._f675)&&Math.abs(x102._f777)<=(x102._f779+x102._f676))
{
x102._f263=Math.sqrt(x102._f776*x102._f776+x102._f777*x102._f777);
x102._f780=20;
x102._f781=x102._f263>((x102._f778+x102._f779)/4)?300:200;
x102._f782=0;
x102._f783=0;
x102._f784=0;
x102.smoothPan();
return;
}
}
}
x102.zoomTo(x101,x100);
}
if(x100.srid&&(this.srid!=x100.srid))
x100=this.transformGeom(x100,this.srid,null,x103);
else
 x103();
}
MVMapView.prototype.addMapTileLayer=function(x105,x106)
{
if(x105.getType&&x105.getType()=="MVExternalAPIMapTileLayer")
this._f802(x105);
else
 {
if(x105._f108==undefined||
x105._f108==null||
MVUtil._f227(x105._f108)=="")
{
MVi18n._f6("MVMapView.addMapTileLayer","MAPVIEWER-05507",null,this._f7);
return ;
}
if(!this._f534&&this.compareBaseURL(x105._f409))
{
var x107=x105._f108.split(".");
if(x107.length>=2)
{
_f11._f536(x107[0]);
this._f536(x107[0]);
}
}
x105.parent=this;
if(!x105._f409)
{
if(this._f4)
{
if(MVUtil._f5(this._f4,'/'))
x105._f409=this._f4+'mcserver';
else
 x105._f409=this._f4+'/mcserver';
}
else
 {
MVi18n._f6("MVMapView.addMapTileLayer","MAPVIEWER-05504",null,this._f7);
return ;
}
}
if(MVMapView._f8&&MVUtil._f9(x105._f409)!=MVUtil._f9(this._f10))
x105._f505=_f11._f12()+"?rtarget="+x105._f409;
else
 x105._f505=x105._f409;
if(x105==null||!x105.getType)
{
MVi18n._f6("MVMapView.addMapTileLayer","MAPVIEWER-05519","mapTileLayer",this._f7);
return;
}
this._f135.push(x105);
}
var x108=this;
var x109=function()
{
if(!x105.zIndex)
x105.zIndex=x108._f135.length+1;
x108._f786(x105,AfterSetProvider);
function AfterSetProvider(x0)
{
x105.config=_f11._f514[x105._f108];
if(!x105.isExtAPITileLayer())
{
var x1=new _f406(x108,x105);
x105.layerControl=x1;
x108.combineTileLayers();
}
if(x108._f93)
{
if(!x0)
{
if(x105.isExtAPITileLayer())
{
var x2=function(x3)
{
x105.setCenterAndZoomLevel(x3.getPointX(),x3.getPointY(),x108._f21);
}
var x4=MVSdoGeometry.createPoint(x108._f95,x108._f96,x108.srid);
x108.transformGeom(x4,x105.srid,null,x2);
}
else
 {
if(x105.layerControl)
x105.layerControl._f432(x108._f109(),x108._f110(),x108._f95,x108._f96);
}
}
else
 x108.display();
if(x108._f634)
{
x108._f634._f642(x108);
}
if(x108.navigationPanel&&x108.navigationPanel.getMaxZoomLevel()!=x108._f702)
{
x108.navigationPanel.updateSlider();
}
}
if(x106)
x106();
if(x108._f211&&x108._f211._f202&&x108._f211._f202._f135.length==0)
x108._f211.addOverviewMapTileLayer();
if(x108._f211&&x108._f211._f202)
x108._f211._f218();
}
}
_f11._f541(x105,x109,this._f7);
}
MVMapView.prototype.addBaseMapLayer=MVMapView.prototype.addMapTileLayer;
MVMapView.prototype.getMapTileLayerDefinition=function(x110,x111,x112)
{
if(!x112)
x112=null;
var x113=new MVMapTileLayer(x111,x112);
if(!x113._f409)
{
if(this._f4)
{
if(MVUtil._f5(this._f4,'/'))
x113._f409=this._f4+'mcserver';
else
 x113._f409=this._f4+'/mcserver';
}
else
 {
MVi18n._f6("MVMapView.getMapTileLayerDefinition","MAPVIEWER-05504",null,this._f7);
return ;
}
}
if(MVMapView._f8&&MVUtil._f9(x113._f409)!=MVUtil._f9(this._f10))
x113._f505=_f11._f12()+"?rtarget="+x113._f409;
else
 x113._f505=x113._f409;
_f11._f541(x113,x110,this._f7);
}
MVMapView.prototype.removeMapTileLayer=function(x114)
{
var x115=0;
var x116=x114._f108;
var x117=x114._f409;
if(x114._f506){
var x118=x114._f506
for(var x119=0;x119<x118._f439.length;x119++)
{
if(x118._f439[x119]==x114)
{
if(x118._f439.length>1)
{
x118._f439[x119]=x118._f439[x118._f439.length-1];
x118._f439.pop();
var x120=x118.getTransparent();
if(x118.layerControl)
x118.layerControl._f412=x120;
x118.refresh(true);
if(this._f211)
this.removeOverviewMapTileLayer(x116,x117);
return;
}
else
 {
x118._f439[x119]=null;
x118._f439.pop();
x114=x118;
}
}
}
}
for(;x115<this._f135.length;x115++)
{
if(this._f135[x115]==x114)
{
var x121=this._f135[x115];
this._f135[x115]=this._f135[this._f135.length-1];
x121.destroy();
this._f135.pop();
this.mapLayerNumber--;
if(this._f211)
this.removeOverviewMapTileLayer(x116,x117);
break;
}
}
if(this.msi._f513==x116&&this._f135.length>0)
{
var x122=this;
var x123=this._f135[this._f135.length-1];
function AfterSetProvider(x0)
{
if(x122._f93)
{
if(!x0)
{
if(x123.isExtAPITileLayer())
{
var x1=function(x2)
{
x123.setCenterAndZoomLevel(x2.getPointX(),x2.getPointY(),x122._f21);
}
var x3=MVSdoGeometry.createPoint(x122._f95,x122._f96,x122.srid);
x122.transformGeom(x3,x123.srid,null,x1);
}
else
 {
if(x123.layerControl)
x123.layerControl._f432(x122._f109(),x122._f110(),x122._f95,x122._f96);
}
}
else
 x122.display()
if(x122._f634)
{
x122._f634._f642(x122);
}
if(x122.navigationPanel&&x122.navigationPanel.getMaxZoomLevel()!=x122._f702)
{
x122.navigationPanel.updateSlider();
}
}
}
this._f786(x123,AfterSetProvider);
}
}
MVMapView.prototype.removeOverviewMapTileLayer=function(x124,x125)
{
if(this._f211._f202._f135.length>0)
{
var x126=this._f211._f202._f135[0];
if(x126._f108==x124&&x126._f409==x125)
{
this._f211._f202.removeMapTileLayer(x126);
this._f211._f218();
}
}
}
MVMapView.prototype._f721=function()
{
if(!this._f80)
this._f80=new _f288(this);
this._f248=new _f259(this);
this._f248.setSize(Math.ceil(2*(this._f109())),
Math.ceil(2*(this._f110())));
MVUtil._f74(this._f248._f61(),
(this._f109()-this._f248._f88())/2,
(this._f110()-this._f248._f89())/2);
}
MVMapView.prototype.addGroupFOI=function(x127)
{
this._f152=0;
this._f803=x127;
this.startAddGroupFOI();
}
MVMapView.prototype.startAddGroupFOI=function()
{
for(var x128=0;x128<11000;x128++)
{
if(this._f152<this._f803.length)
{
this.addFOI(this._f803[this._f152]);
this._f152++;
}
else
 {
this._f803=null;
this._f804=null;
return;
}
}
this.setTimeout("this.startAddGroupFOI()",5);
}
MVMapView.prototype.addFOI=function(x129)
{
if(!this._f534&&this.compareBaseURL(x129._f3)&&x129.style&&x129.style.indexOf!=undefined)
{
var x130=x129.style.split(".");
if(x130.length>=2)
{
_f11._f536(x130[0]);
this._f536(x130[0]);
}
}
if(!x129||!x129.id)
return false;
if(x129.gtype%10==1)
x129._f234=true;
x129.parent=this;
if(x129.id!="")
{
var x131=x129.id.substr(0,3);
if(x131!="-RL")
{
for(var x132=0;x132<this._f248._f260.length;x132++)
{
if(this._f248._f260[x132].id==x129.id)
{
MVi18n._f6("MVMapView.addFOI","MAPVIEWER-05508","foi.id:"+x129.id,this._f7);
return false;
}
}
}
}
if(x129.gtype%10==1&&!x129.style)
{
x129._f131=x129._f3;
}
if(!x129._f3&&!x129.isHTMLFOI)
x129._f3=_f11._f454();
this._f248._f257(x129);
return true;
}
MVMapView.prototype.getFOI=function(id)
{
return this._f248._f262(id);
}
MVMapView.prototype.removeFOI=function(x133)
{
if(!x133)
return ;
if(x133.id)
this._f248._f256(x133.id);
else
 this._f248._f256(x133);
}
MVMapView.prototype.getAllFOIs=function()
{
return this._f248._f279();
}
MVMapView.prototype._f280=function()
{
this._f126();
if(this._f248!=null)
{
var x134=(this._f248._f88()-
this._f109())*0.5/this._f76;
var x135=(this._f248._f89()-
this._f110())*0.5/this._f78;
MVUtil._f74(this._f248._f61(),
(this._f109()-this._f248._f88())/2,
(this._f110()-this._f248._f89())/2);
MVUtil._f74(this._f248.getTopContainer(),
(this._f109()-this._f248._f88())/2,
(this._f110()-this._f248._f89())/2);
this._f248._f280(this._f75-x134,
this._f293-x135,this._f287+x134,
this._f77+x135);
}
}
MVMapView.prototype.removeAllFOIs=function()
{
this._f248._f90();
}
MVMapView.prototype.addRedLineTool=function(x136)
{
if(x136==null)
{
MVi18n._f6("MVMapView.addRedLineTool","MAPVIEWER-05519","redlineTool",this._f7);
return;
}
x136.parent=this;
if(!x136._f3)
x136._f3=_f11._f454();
}
MVMapView.prototype.addCircleTool=function(x137)
{
if(x137==null)
{
MVi18n._f6("MVMapView.addCircleTool","MAPVIEWER-05519","circleTool",this._f7);
return;
}
this._f215.appendChild(x137._f471);
x137.parent=this;
if(!x137._f470)
x137._f470=_f11._f454();
}
MVMapView.prototype.addRectangleTool=function(x138)
{
if(x138==null)
{
MVi18n._f6("MVMapView.addRectangleTool","MAPVIEWER-05519","rectangleTool",this._f7);
return;
}
this.div.appendChild(x138._f626);
x138.parent=this;
if(!x138._f470)
x138._f470=_f11._f454();
}
MVMapView.prototype.addDistanceTool=function(x139)
{
if(x139==null)
{
MVi18n._f6("MVMapView.addDistanceTool","MAPVIEWER-05519","distanceTool",this._f7);
return;
}
x139.parent=this;
if(!x139._f3)
x139._f3=_f11._f454();
}
MVMapView.prototype.addToolBar=function(x140)
{
if(x140==null)
{
MVi18n._f6("MVMapView.addToolBar","MAPVIEWER-05519","toolBar",this._f7);
return;
}
x140.mapviewer=this;
this.addMapDecoration(x140.toolBarDecoration);
x140._f623();
if(this._f93)
x140._f622();
else
 x140.toolBarDecoration.afterDisAction=x140._f622;
}
MVMapView.prototype.stopMarqueeZoom=function()
{
this._f709=false;
if(this._f710)
this._f710.clear();
MVUtil.runListeners(this._f98,MVEvent.MARQUEEZOOM_FINISH);
}
MVMapView.prototype.startMarqueeZoom=function(x141,x142,x143)
{
if(!x141)
x141="one_time";
if(x143&&x143>0)
this._f741=x143;
this._f709=true;
this._f708=x141;
this.marqueeZoom(x142);
}
MVMapView.prototype.marqueeZoom=function(x144)
{
if(!this._f709)
return ;
this._f711=false;
this._f713=false;
var x145=this._f710;
if(!x145)
{
x145=new MVRectangleTool(null,null);
x145.setDivFillColor("#555555",0.3);
this.addRectangleTool(x145);
this._f710=x145;
}
if(x144)
x145.setDivStyle(x144);
x145.init();
var x146=MVUtil._f106(x145,function()
{
this.clear();
var x147=null;
if(this.parent.marqueeRectGeom)
x147=this.parent.marqueeRectGeom.getMBR();
if(x147&&
(this._f708=="prompt"||
Math.abs(x147[2]-x147[0])*this.parent._f76>=this.parent._f741&&
Math.abs(x147[3]-x147[1])*this.parent._f78>=this.parent._f741))
{
var x148=document.getElementById("mv_mrqzm_msg");
if(x148)
x148.parentNode.removeChild(x148);
this.parent.zoomToRectangle(this.parent.marqueeRectGeom);
}
if(this.parent._f708!="one_time")
{
this.parent.marqueeZoom();
}
});
var x149=MVUtil._f106(x145,function()
{
this.parent._f711=true;
this.parent.marqueeRectGeom=x145.getRectangle();
x146();
});
var x150=MVUtil._f106(x145,function()
{
this.parent.marqueeRectGeom=x145.getRectangle();
x146();
this._f709=false;
});
var x151=MVUtil._f106(x145,function(x152)
{
this.parent._f712=true;
x152=(x152)?x152:((window.event)?event:null);
MVUtil._f173(x152);
});
var x153=MVUtil._f106(x145,function(x154)
{
x154=(x154)?x154:((window.event)?event:null);
if(this.parent._f712)
{
MVUtil._f173(x154);
x146();
}
});
var x155=MVUtil._f106(x145,function()
{
this.parent._f711=true;
this.parent.marqueeRectGeom=x145.getRectangle();
if(!this.parent.marqueeRectGeom)
{
this.clear();
this.parent.marqueeZoom();
return ;
}
var x156=this.parent;
var x157=x145.getRectangleDIV();
MVUtil._f164(x157,"crosshair");
if(MVi18n._f340("marqueeZoomHint"))
{
x157.onmouseover=function(x158)
{
var x159=document.getElementById("mv_mrqzm_msg");
if(x159)
x159.parentNode.removeChild(x159);
x159=MVUtil._f674(MVi18n._f340("marqueeZoomHint"));
x159.id="mv_mrqzm_msg";
x159.style.border="1px solid #000000";
x159.style.backgroundColor="#FFFFCC";
x159.style.position="absolute";
var x160=MVUtil._f655(x156,x158);
x159.style.left=x160.x+"px";
x159.style.top=x160.y+"px";
x159.style.zIndex=9999;
x156._f215.appendChild(x159);
};
x157.onmouseout=function()
{
var x161=document.getElementById("mv_mrqzm_msg");
if(x161)
x161.parentNode.removeChild(x161);
};
}
this.parent._f712=false;
x157.onmousedown=x151;
x157.onmouseup=x153;
x157.onclick=function(x162)
{
MVUtil._f173(x162);
};
});
this._f712=false;
if(this._f708=="prompt")
x145.addEventListener("on_finish",x155);
else if(this._f708=="continuous")
x145.addEventListener("on_finish",x149);
else
 x145.addEventListener("on_finish",x150);
}
MVMapView.prototype.zoomToRectangle=function(x163)
{
if(!this._f93)
{
this._f805(this,"zoomToRectangle",x163);
return ;
}
var x164=this;
var x165=function(x166)
{
var x167=x166.getMBR();
var x168=Math.abs(x167[0]-x167[2]);
var x169=Math.abs(x167[1]-x167[3]);
var x170=x168*(x164._f76);
var x171=x169*(x164._f78);
var x172=x164._f109();
var x173=x164._f110();
var x174=x164._f21;
var x175=x174;
var x176=x164._f702;
if(x172<x170||x173<x171)
{
for(var x177=(x174-1);x177>=0;--x177)
{
x175=x177;
var x178=(x164._f694[x177]/x164.msi._f522(x177))*x168;
var x179=(x164._f695[x177]/x164.msi._f523(x177))*x169;
if(x178<=x172&&x179<=x173)
break;
}
}
else if(x172>x170&&x173>x171)
{
for(var x177=(x174+1);x177<=x176;++x177)
{
var x178=(x164._f694[x177]/x164.msi._f522(x177))*x168;
var x179=(x164._f695[x177]/x164.msi._f523(x177))*x169;
if(x178<=x172&&x179<=x173)
x175=x177;
else
 break;
}
}
var x180=(x167[0]+x167[2])/2;
var x181=(x167[1]+x167[3])/2;
var x182=MVSdoGeometry.createPoint(x180,x181,x164.srid);
x164.zoomTo(x175,x182);
}
if(!x163.srid)
x163.srid=this.srid;
if(x163.srid&&this.srid!=x163.srid)
this.transformGeom(x163,this.srid,null,x165);
else
 x165(x163);
}
MVMapView.prototype._f806=function(x183)
{
var x184=(x183._f88()-this._f109())*0.5/this._f76;
var x185=(x183._f89()-this._f110())*0.5/this._f78;
var x186=new Array();
if(x183._f1._f51==1&&x183._f1._f52==1)
{
x186[0]=this._f75;
x186[1]=this._f293;
x186[2]=this._f287;
x186[3]=this._f77;
}
else
 {
var x187=this._f807(this._f75-x184,this._f293-x185);
var x188=this._f808(this._f287+x184,this._f77+x185);
x186[0]=x187.x;
x186[1]=x187.y;
x186[2]=x188.x;
x186[3]=x188.y;
}
var x189=Math.floor((x186[2]-x186[0])*this._f76+0.5);
var x190=Math.floor((x186[3]-x186[1])*this._f78+0.5);
x183._f50(x189,x190);
return x186;
}
MVMapView.prototype._f767=function()
{
this._f126();
var x191=0;
for(x191=0;x191<this._f697.length;x191++)
{
var x192=this._f697[x191];
if(x192._f1._f809)
{
x192.clearTransImageLayer();
var x193=this._f806(x192);
x192._f79(this,x193[0],x193[1],x193[2],x193[3],true,true,null,true,x192._f1._f810);
return true;
}
}
return false;
}
MVMapView.prototype._f768=function(x194,x195)
{
var x196=this;
var x197=function(x198)
{
x198.style.position="absolute";
if(x196._f698==1||x196._f698==3)
x198.style.right=MVUtil._f32(x196._f699);
else
 x198.style.left=MVUtil._f32(x196._f699);
if(x196._f698==2||x196._f698==3)
x198.style.bottom=MVUtil._f32(x196._f700);
else
 x198.style.top=MVUtil._f32(x196._f700);
}
x194._f632(this,x195,x197);
}
MVMapView.prototype.addThemeBasedFOI=function(x199)
{
if(!this._f534&&this.compareBaseURL(x199._f3)&&
x199._f43.indexOf("<jdbc_query")<0)
{
var x200=x199._f43.split(".");
if(x200.length>=2)
{
_f11._f536(x200[0]);
this._f536(x200[0]);
}
}
if(this._f733)
{
this._f811(x199);
return ;
}
if(!x199)
{
MVi18n._f6("MVMapView.addThemeBasedFOI","MAPVIEWER-05519","themeBasedFOI",this._f7);
return ;
}
if(!this._f80)
this._f80=new _f288(this);
for(var x201=0;x201<this._f697.length;x201++)
{
if(MVUtil._f227(this._f697[x201]._f1.name)==MVUtil._f227(x199.name))
{
MVi18n._f6("MVMapView.addThemeBasedFOI","MAPVIEWER-05509",MVUtil._f227(x199.name),this._f7);
return;
}
}
x199.parent=this;
var x202=new _f0(this,x199);
if(this._f697.length<100)
{
x202.div.style.zIndex=this._f697.length*2+130;
x202._f33=this._f697.length;
}
else
 {
x202.div.style.zIndex=100*2+130;
x202._f33=100;
}
x202.setSize(parseInt(x199._f51*(this._f109())),
parseInt(x199._f52*(this._f110())));
this._f697[this._f697.length]=x202;
if(this._f93)
{
var x203=this._f806(x202);
MVUtil._f74(x202._f61(),Math.ceil((x203[0]-this._f75)*this._f76),
-Math.ceil((x203[3]-this._f77)*this._f78));
if(x199._f809)
x202._f79(this,x203[0],x203[1],
x203[2],x203[3],false,true,false,false,x199._f810);
else
 x202._f79(this,x203[0],x203[1],
x203[2],x203[3],false,false);
}
}
MVMapView.prototype.getThemeIndex=function(x204)
{
for(var x205=0;x205<this._f697.length;x205++)
{
if(MVUtil._f227(this._f697[x205]._f1.name)==MVUtil._f227(x204.name))
{
return x205+1;
}
}
MVi18n._f6("MVMapView.getThemeIndex","MAPVIEWER-05510",MVUtil._f227(x204.name),this._f7);
}
MVMapView.prototype.getOrigThemeIndex=function(x206)
{
for(var x207=0;x207<this._f697.length;x207++)
{
if(MVUtil._f227(this._f697[x207]._f1.name)==MVUtil._f227(x206.name))
{
return this._f697[x207]._f33;
}
}
MVi18n._f6("MVMapView.getOrigThemeIndex","MAPVIEWER-05510",MVUtil._f227(x206.name),this._f7);
}
MVMapView.prototype.setThemeIndex=function(x208,x209)
{
if(x209>100)
x209=100;
if(x209<1)
x209=1;
for(var x210=0;x210<this._f697.length;x210++)
{
if(MVUtil._f227(this._f697[x210]._f1.name)==MVUtil._f227(x208.name))
{
while(x210+1<this._f697.length&&x210+1<x209)
{
var x211=this._f697[x210];
this._f697[x210]=this._f697[x210+1];
this._f697[x210+1]=x211;
x210++;
}
while(x210+1>x209)
{
var x211=this._f697[x210];
this._f697[x210]=this._f697[x210-1];
this._f697[x210-1]=x211;
x210--;
}
break;
}
}
for(var x212=0;x212<this._f697.length;x212++)
{
this._f697[x212].div.style.zIndex=x212*2+130;
if(this._f697[x212]._f34)
this._f697[x212]._f34.style.zIndex=x212*2+130+1;
if(this._f697[x212]._f34&&this._f697[x212]._f31!=1&&
this._f697[x212]._f1._f104<=this._f21)
this._f82.style.zIndex=x212*2+130+1;
}
}
MVMapView.prototype.setToOrigThemeIndex=function(x213)
{
for(var x214=0;x214<this._f697.length;x214++)
{
if(MVUtil._f227(this._f697[x214]._f1.name)==MVUtil._f227(x213.name))
{
this._f697[x214].div.style.zIndex=this._f697[x214]._f33*2+130;
if(this._f697[x214]._f31==2&&_f11._f70!="IF"&&
this._f697[x214]._f34)
{
this._f697[x214]._f34.style.zIndex=this._f697[x214]._f33*2+130+1;
}
}
}
}
MVMapView.prototype.getThemeBasedFOI=function(x215)
{
for(var x216=0;x216<this._f697.length;x216++)
{
if(this._f697[x216].getThemeBasedFOI().name==x215)
{
return this._f697[x216].getThemeBasedFOI();
}
}
return null;
}
MVMapView.prototype.enableInfoWindowEventPropagation=function(x217)
{
if(!this._f80)
this._f80=new _f288(this);
this._f80.enableEventPropagation(x217);
}
MVMapView.prototype.refreshThemeBasedFOIs=function(x218)
{
if(this._f80)
this._f80._f291();
this._f126();
var x219=0;
for(x219=0;x219<this._f697.length;x219++)
{
var x220=this._f697[x219];
if(x220._f31!=1)
{
x220.clearTransImageLayer();
}
var x221=this._f806(x220);
var x222=x220._f1.getFOIData();
if(x222!=null)
{
if(!x220._f2&&
!x220._f1._f812&&x222[0].gtype%10==1&&
x220._f1._f71<this._f21){
x220._f91();
x220.div.style.display='none';
x220._f1.minX=x220.minX=x221[0];
x220._f1.minY=x220.minY=x221[1];
x220._f1.maxX=x220.maxX=x221[2];
x220._f1.maxY=x220.maxY=x221[3];
x220._f21=this._f21;
var x223=Math.ceil((x220.minX-this._f75)*this._f76);
var x224=-Math.ceil((x220.maxY-this._f77)*this._f78);
MVUtil._f74(x220._f61(),x223,x224);
x220._f28=this._f95;
x220._f29=this._f96;
for(var x225=0;x225<x222.length;x225++)
{
x220._f133(x222[x225]);
}
x220.makeFOIsVisiable();
x220._f2=false;
x220._f30=false;
continue;
}
}
x220._f79(this,x221[0],x221[1],x221[2],x221[3],x218,false,null,true);
}
}
MVMapView.prototype._f807=function(x226,x227)
{
var x228={"x":0,"y":0};
var x229=Math.floor((x226-this.msi._f55())/this.msi._f522(this._f21));
var x230=Math.floor((x227-this.msi._f56())/this.msi._f523(this._f21));
x228.x=this.msi._f55()+x229*this.msi._f522(this._f21);
x228.y=this.msi._f56()+x230*this.msi._f523(this._f21);
return x228;
}
MVMapView.prototype._f808=function(x231,x232)
{
var x233={"x":0,"y":0};
var x234=Math.floor((x231-this.msi._f55())/this.msi._f522(this._f21));
var x235=Math.floor((x232-this.msi._f56())/this.msi._f523(this._f21));
x233.x=this.msi._f55()+(x234+1)*this.msi._f522(this._f21);
x233.y=this.msi._f56()+(x235+1)*this.msi._f523(this._f21);
return x233;
}
MVMapView.prototype._f813=function()
{
if(this._f93)
{
for(var x236=0;x236<this._f697.length;x236++)
this._f697[x236].setVisible(false,false);
this.refreshThemeBasedFOIs(false);
}
while(this._f697.length>0)
{
this._f697[this._f697.length-1].destroy();
this._f697[this._f697.length-1]=null;
this._f697.pop();
}
while(this._f82.childNodes.length>0)
{
this._f82.removeChild(this._f82.childNodes[0]);
}
}
MVMapView.prototype.removeThemeBasedFOI=function(x237)
{
if(!x237)
return ;
for(var x238=0;x238<this._f697.length;x238++)
{
if(x237==this._f697[x238].getThemeBasedFOI())
{
this._f697[x238].destroy();
this._f697[x238]=this._f697[this._f697.length-1];
this._f697[this._f697.length-1]=null;
this._f697.pop();
break;
}
}
for(var x239=0;x239<this._f82.childNodes.length;x239++)
{
if(this._f82.childNodes[x239].id==x237.name)
{
this._f82.removeChild(this._f82.childNodes[x239]);
x239--;
}
}
for(var x240=0;x240<this._f697.length;x240++)
{
this._f697[x240].div.style.zIndex=x240*2+130;
if(this._f697[x240]._f34&&this._f697[x240]._f31!=1&&
this._f697[x240]._f1._f104<=this._f21)
{
this._f697[x240]._f34.style.zIndex=x240*2+130+1;
this._f82.style.zIndex=x240*2+130+1;
}
}
}
MVMapView.prototype._f814=function()
{
if(this._f80)
this._f80._f291();
this._f126();
var x241=0;
var x242=false;
for(x241=0;x241<this._f697.length;x241++)
{
var x243=this._f697[x241];
var x244=this._f806(x243);
if((
this._f75<x243._f55()||
this._f293<x243._f56()||
this._f287>x243._f57()||
this._f77>x243._f58())&&x243._f1._f812)
{
if(x243._f31!=1)
{
x243.clearTransImageLayer();
}
x243._f79(this,x244[0],x244[1],x244[2],x244[3],true,false);
}
else if(x243._f2)
{
x243._f79(this,x244[0],x244[1],x244[2],x244[3],true,false);
}
}
if(this._f75<this._f248.minX||
this._f293<this._f248.minY||
this._f287>this._f248.maxX||
this._f77>this._f248.maxY)
{
var x245=(this._f248._f88()-this._f109())*0.5/this._f76;
var x246=(this._f248._f89()-this._f110())*0.5/this._f78;
this._f248._f280(this._f75-x245,
this._f293-x246,
this._f287+x245,
this._f77+x246);
}
else if(this.wraparound){
var x245=(this._f248._f88()-this._f109())*0.5/this._f76;
var x247=x245-(this._f75-this._f248.minX);
var x248=x245-(this._f248.maxX-this._f287);
var x249=x247>0?x247:x248;
var x250=(this.msi._f158-this.msi._f159)/4;
if(x249>x250)
{
var x246=(this._f248._f89()-this._f110())*0.5/this._f78;
this._f248._f280(this._f75-x245,
this._f293-x246,
this._f287+x245,
this._f77+x246);
}
}
if(!x242)
{
this._f80.refresh(this._f75,this._f293,this._f287,
this._f77,this._f109(),this._f110());
x242=true;
}
}
MVMapView.prototype.getAllThemeBasedFOIs=function()
{
return this._f697;
}
MVMapView.prototype.setDefaultInfoWindowStyle=function(x251,x252)
{
_f11._f295=x251;
if(x251=="MVInfoWindowStyle1")
MVInfoWindowStyle1._f815=x252;
}
MVMapView.prototype.displayInfoWindow=function(x253,x254,x255,x256,x257,x258,x259)
{
if(this.infoWindowTimeout)
{
clearTimeout(this.infoWindowTimeout);
this.infoWindowTimeout=null;
}
var x260=this;
var x261=function(x262)
{
if(x262)
x253=x262;
x260._f80._f305(x254,x253.sdo_point.x,x253.sdo_point.y,x255,x256,x257,x258,x259);
}
var x263=function()
{
if(x260._f93)
{
if(!x253.srid)
x253.srid=x260.srid;
if(x253.srid&&(x260.srid!=x253.srid))
center=x260.transformGeom(x253,x260.srid,null,x261);
else
 x261();
}
else
 x260.infoWindowTimeout=setTimeout(x263,200);
}
x263();
}
MVMapView.prototype.displayTabbedInfoWindow=function(x264,x265,x266,x267,x268,x269)
{
if(this.infoWindowTimeout)
{
clearTimeout(this.infoWindowTimeout);
this.infoWindowTimeout=null;
}
var x270=this;
var x271=function(x272)
{
if(x272)
x264=x272;
x270._f80.showTabbedHtmlWindow(null,x264.sdo_point.x,x264.sdo_point.y,x267,x268,x269,x265,x266);
}
var x273=function()
{
if(x270._f93)
{
if(!x264.srid)
x264.srid=x270.srid;
if(x264.srid&&(x270.srid!=x264.srid))
center=x270.transformGeom(x264,x270.srid,null,x271);
else
 x271();
}
else
 x270.infoWindowTimeout=setTimeout(x273,200);
}
x273();
}
MVMapView.prototype.setInfoWindow=function(x274,x275,x276)
{
this._f720=x274;
this._f299=x275;
this._f300=x276;
}
MVMapView.prototype._f816=function(x277,x278)
{
_f11._f299=x277;
_f11._f300=x278;
}
MVMapView.prototype.removeInfoWindow=function()
{
if(this._f80._f81.length>0)
this._f80.deleteInfoWindow(this._f80._f81[0]);
}
MVMapView.prototype.addCSTransformFunction=function(x279,x280,x281)
{
_f11._f405(x280,x281,x279);
}
MVMapView.prototype.transformGeom=function(geometry,_f402,_f409,callBack,dataSource)
{
if(!geometry.srid)
{
if(!this.srid)
{
MVi18n._f6("MVMapView.transformGeom","MAPVIEWER-05520",null,this._f7);
return ;
}
geometry.srid=this.srid;
}
if(geometry.srid==_f402)
{
if(callBack)
{
callBack(geometry);
return ;
}
else
 return geometry;
}
var clientTrans=_f11._f458(geometry.srid,_f402);
if(clientTrans)
{
var _f228=null;
if(geometry.getGType()==1)
{
var result=clientTrans(geometry.getPointX(),geometry.getPointY(),this._f7);
_f228=MVSdoGeometry.createPoint(result.x,result.y,_f402);
}
else if((geometry.gtype%10==2||geometry.gtype%10==3)&&
geometry.sdo_elem_info.length==3&&geometry.sdo_elem_info[0]==1&&
(geometry.sdo_elem_info[1]==2||geometry.sdo_elem_info[1]==1003||geometry.sdo_elem_info[1]==2003)&&
geometry.sdo_elem_info[2]==1)
{
var ordinates=MVMapView._f460(clientTrans,geometry.sdo_ordinates,this._f7);
_f228=new MVSdoGeometry(geometry.gtype,_f402,geometry.sdo_elem_info,ordinates);
}
if(_f228)
{
if(callBack)
{
callBack(_f228);
return ;
}
else
 return _f228;
}
}
if(_f409)
{}
else if(this._f4)
{
if(MVUtil._f5(this._f4,'/'))
{
_f409=this._f4+'mcserver';
}
else
 {
_f409=this._f4+'/mcserver';
}
}
else
 {
MVi18n._f6("MVMapView.transformGeom","MAPVIEWER-05504",null,this._f7);
return ;
}
var localDomain=(MVUtil._f9(_f409)==MVUtil._f9(this._f10));
var xmlHttp=localDomain||MVMapView._f8;
if(!xmlHttp&&!callBack)
{
MVi18n._f6("MVMapView.transformGeom","MAPVIEWER-05521",null,this._f7);
return null;
}
var request=null;
var _f461;
var _f462;
var _f817;
if(dataSource)
_f817=dataSource;
else
 _f817=this._f534;
_f462=MVUtil._f49(geometry.toString(),"null","");
if(geometry.getGType()==1)
{
_f462=MVUtil._f49(_f462,"SdoPointType","");
}
else
 {
}
_f462=MVUtil._f49(_f462," ","");
var targetURL=_f409;
if(MVMapView._f8&&!localDomain)
_f409=_f11._f12()+"?rtarget="+targetURL;
else
 _f409=_f409;
var _f818=_f409;
var params="request=cstransform&dstsrid="+
_f402+"&geometry="+_f462;
if(_f817)
params+="&datasource="+_f817;
var request=null;
var mv=this;
var transformed=MVUtil._f106(this,function()
{
if(request.readyState==4)
{
if(request.status==200)
{
try
{
var resp=request.responseText;
eval("var result="+resp);
_f461=result;
if(result.length==0)
{
result=null;
return;
}
var _f228=null;
if(_f461.SDO_GTYPE==1)
{
_f228=MVSdoGeometry.createPoint(_f461.SDO_POINT.X,_f461.SDO_POINT.Y,_f461.SDO_SRID);
}
else
 {
_f228=new MVSdoGeometry(2000+_f461.SDO_GTYPE,_f461.SDO_SRID,_f461.SDO_ELEM_INFO,_f461.SDO_ORDINATES,null)
}
result=null;
}
catch(e)
{
MVi18n._f6("MVMapView.transformGeom","MAPVIEWER-05523",request.responseText,mv._f7);
return ;
}
if(callBack)
callBack(_f228);
if(xmlHttp)
return _f228;
}
}
});
try
{
request=MVUtil.getXMLHttpRequest(xmlHttp);
if(callBack||!xmlHttp)
request.onreadystatechange=transformed;
request.open("POST",_f818,callBack||!xmlHttp);
request.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
request.send(params);
}catch(e)
{
MVi18n._f6("MVMapView.transformGeom","MAPVIEWER-05511",e,this._f7);
return ;
}
if(!callBack&&xmlHttp)
{
if(request.status==200)
{
return transformed();
}
}
}
MVMapView.prototype.getSrid=function()
{
return this.srid;
}
MVMapView.prototype._f819=function()
{
MVUtil._f164(document.body,"wait");
MVUtil._f164(this._f215,"wait");
}
MVMapView.prototype._f820=function()
{
MVUtil._f164(document.body,"");
MVUtil._f164(this._f215,this._f209);
}
MVMapView.prototype.getMapTileLayer=function(x282)
{
var x283=x282.split(".");
if(x283.length<2){
return "";
}
else
 return x283[1];
}
MVMapView.prototype.getMapImageURL=function(x284,x285,x286,x287)
{
var x288=this.getMapAsXML(x285,x286,x287);
var x289=(this._f4==this._f717);
var x290=x289||MVMapView._f8;
var x291="";
if(MVMapView._f8&&!x289)
x291=this._f717;
else
 x291=this._f4;
if(MVUtil._f5(x291,'/'))
x291=x291+'omserver';
else
 x291=x291+'/omserver';
var x292=MVUtil.getXMLHttpRequest(x290);
x292.open("POST",x291,true);
x292.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
x292.send("xml_request="+encodeURIComponent(x288));
var x293=this;
x292.onreadystatechange=function()
{
if(x292.readyState==4)
{
if(x292.status==200)
{
var x294=x292.responseText;
var x295=x294.indexOf("url=\"");
if(x295<0)
{
MVi18n._f6("MVMapView.getMapImageURL","MAPVIEWER-05519",x294,x293._f7);
return ;
}
var x296=x294.substring(x295+5,x294.indexOf("\"",x295+5));
x284(x296);
}
}
}
}
MVMapView.prototype.getMapAsXML=function(x297,x298,x299)
{
if(!x298)
x298=this._f109();
if(!x299)
x299=this._f110();
var x300="<?xml version=\"1.0\" standalone=\"yes\"?>"
x300=x300+"<map_request datasource=\""+this._f534+"\" format=\""+x297+"\" width=\""+
x298+"\" height=\""+x299+"\" antialiase=\"true\" "+
(this.getSrid()?"srid=\""+this.getSrid()+"\"":"")+">";
var x301=parseInt(this._f110())/this._f78;
var x302=this.getCenter();
x300=x300+"<center size=\""+x301+"\"><geoFeature><geometricProperty typeName=\"center\"><Point><coordinates>"+x302.sdo_point.x+","+x302.sdo_point.y+"</coordinates></Point></geometricProperty></geoFeature></center>";
x300=x300+"<themes>";
for(i=0;i<this._f135.length;i++)
{
if(this._f135[i].isVisible()&&this._f135[i].layerControl)
x300=x300+"<theme name=\"cached_basemap"+i+"\">"+this._f135[i].layerControl.getXMLForPrint()+"</theme>";
}
var x303="";
var x304="";
var x305="";
for(var x306=0;x306<this._f697.length;x306++)
{
if(this._f697[x306].isVisible())
{
var x307=null;
if(this._f697[x306]._f1._f821)
{
x307=this._f697[x306]._f1._f43+"_markerstyle"
var x308=new MVStyleMarker(x307,"image");
var x309=this._f697[x306]._f1._f821;
x308.setImageUrl(x309._f131)
x308.setSize(x309.width,x309.height);
x304+=x308.getXMLString();
}
x303+=this._f697[x306]._f40(x307);
if(this._f697[x306]._f1._f105!=null)
x305+=this._f697[x306]._f1._f105.getXML(this._f697[x306]._f1);
var x310=this._f697[x306]._f1._f112;
if(x310.length>0)
{
for(var x311=0;x311<x310.length;x311++)
{
x304+=x310[x311].getXMLString();
}
}
}
}
for(var x306=0;x306<this._f248._f260.length;x306++)
{
if(this._f248._f260[x306].style&&
this._f248._f260[x306].style.getXMLString&&
this._f248._f260[x306].visible)
x304+=this._f248._f260[x306].style.getXMLString();
if(this._f248._f260[x306].createMarker)
{
var x307="indfoi_"+this._f248._f260[x306].id+"_style";
var x308=new MVStyleMarker(x307,"image");
x308.setImageUrl(this._f248._f260[x306]._f131);
x308.setSize(this._f248._f260[x306].width,this._f248._f260[x306].height);
x304+=x308.getXMLString();
}
}
x300=x300+x303+"</themes>";
if(x304!="")
x300+=" <styles>"+x304+"</styles>";
if(x305!="")
x300+=x305;
x300=x300+this._f248._f40();
x300=x300+"</map_request>";
return x300;
}
MVMapView.prototype._f40=function()
{
return this.getMapAsXML("GIF_URL");
}
MVMapView.prototype._f594=function()
{
this.real_base_div.className=null;
this._f822=null;
if(this._f633)
while(this._f633.childNodes.length>0)
{
var x312=this._f633.childNodes[0];
if(x312.src)
{
}
x312.onload=null;
this._f633.removeChild(x312);
MVUtil._f92(x312);
}
}
MVMapView.prototype._f823=function()
{
this.real_base_div.className=("noprint");
}
MVMapView.prototype.print=function(x313)
{
var x314=this;
if(x313.style.position!="absolute")
x313.style.position="relative";
x313.style.width=MVUtil._f32(this._f109());
x313.style.height=MVUtil._f32(this._f110());
if(!this._f824)
{
this._f819();
this._f594();
var x315=this._f40();
var x316=(this._f4==this._f717);
var x317=x316||MVMapView._f8;
var x318="";
if(MVMapView._f8&&!x316)
x318=this._f717;
else
 x318=this._f4;
if(MVUtil._f5(x318,'/'))
x318=x318+'omserver';
else
 x318=x318+'/omserver';
_f278=MVUtil.getXMLHttpRequest(x317);
_f278.open("POST",x318,true);
_f278.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
_f278.send("xml_request="+encodeURIComponent(x315));
_f278.onreadystatechange=function()
{
if(_f278.readyState==4)
{
if(_f278.status==200)
{
x314._f633=x313;
var x319=document.createElement("img");
var x320=_f278.responseText;
var x321=x320.indexOf("url=\"");
if(x321<0)
{
MVi18n._f6("MVMapView.print","MAPVIEWER-05519",x320,x314._f7);
return ;
}
x319.src=x320.substring(x321+5,x320.indexOf("\"",x321+5));x319.style.position="absolute";
x319.style.left=MVUtil._f32(0);
x319.style.top=MVUtil._f32(0);
x314._f633.appendChild(x319);
x319.onload=MVUtil._f106(x319,function()
{
x314._f823();
if(x314._f715!=null)
for(var x322=0;x322<x314._f715.length;x322++)
{
if(x314._f715[x322]._f569&&x314._f715[x322].visible&&!x314._f715[x322].collapsed)
x314._f633.appendChild(x314._f715[x322]._f601());
}
x314._f825(true);
if(x314._f634)
{
x314._f701=new MVScaleBar(x314._f634._f396);
x314._f768(x314._f701,x314._f633)
}
x314._f820();
window.print();
x314._f824=false;
})
x314._f822=x319;
}
}
}
}
}
MVMapView.prototype._f825=function(x323)
{
var x324=document.createElement("span");
x324.className="copyright";
x324.style.zIndex=1999;
if(typeof x323=="string")
{
x324.innerHTML=" "+x323+" ";
this._f633.appendChild(x324);
}
else if(this._f826!=undefined)
{
x324.innerHTML=" "+this._f826.innerHTML+" ";
this._f633.appendChild(x324);
}
}
MVMapView.prototype.setMouseWheelZoomEnabled=function(x325)
{
this._f707=x325;
if(this._f93)
this._f353();
}
MVMapView.prototype._f353=function()
{
var x326=this;
var x327=this.getBrowserType();
this.real_base_div.tabIndex=0;
this.real_base_div.onfocus=MVUtil._f106(this._f215,function()
{
x326._f487=true;
});
this.real_base_div.onblur=MVUtil._f106(this._f215,function()
{
x326._f487=false;
});
this._f215.onmouseover=MVUtil._f106(this._f215,function()
{
x326._f487=true;
});
this._f215.onmouseout=MVUtil._f106(this._f215,function(x328)
{
x328=(x328)?x328:((window.event)?event:null);
var x329=x328.clientX;
var x330=x328.clientY;
var x331=x326._f109();
var x332=x326._f110();
var x333=x326._f220(x328);
if(x333.left<=0||x333.left>=x331||
x333.top<=0||x333.top>=x332||
x329<=1||x329>=document.body.clientWidth||
x330<=1||x330>=document.body.clientHeight)
{
x326._f827(x328);
x326._f487=false;
if(x326._f727.contains(37))x326._f727.remove(37);
if(x326._f727.contains(38))x326._f727.remove(38);
if(x326._f727.contains(39))x326._f727.remove(39);
if(x326._f727.contains(40))x326._f727.remove(40);
}
});
this._f215.onclick=MVUtil._f106(this._f215,function(x334)
{
if((x326.mouseDownLoc&&x326.mouseUpLoc&&
(Math.abs(x326.mouseDownLoc.left-x326.mouseUpLoc.left)>1||
Math.abs(x326.mouseUpLoc.top-x326.mouseUpLoc.top)>1))||
(Math.abs(x326.mouseDownScreenX-x326.mouseUpScreenX)>1||
Math.abs(x326.mouseDownScreenY-x326.mouseUpScreenY)>1))
return ;
if(x326._f570)
{
x326._f570(MVUtil.getEvent(x334));
}
MVUtil.runListeners(x326._f98,MVEvent.MOUSE_CLICK,[MVUtil.getEvent(x334)]);
});
if(!this._f745)
MVUtil.detachEvent(document.body,"mousedown",this._f745);
this._f745=MVUtil._f106(document.body,function()
{
x326._f487=false;
});
MVUtil.attachEvent(document.body,"mousedown",this._f745);
if(x327=="IF")
{
this._f215.onselectstart=MVUtil._f106(this._f215,function()
{
if(x326._f297)
return true;
else
 return false;
});
this._f215.oncontextmenu=MVUtil._f106(this._f215,function(x335)
{
x326._f496(window.event);
if(x326._f725)
{
x326._f725(MVUtil.getEvent(x335));
}
MVUtil.runListeners(x326._f98,MVEvent.MOUSE_RIGHT_CLICK,[MVUtil.getEvent(x335)]);
if(x326._f297)
return true;
else
 return false;
});
if(this._f707)
this._f215.onmousewheel=MVUtil._f106(this._f215,function()
{
return x326._f828(window.event);
});
else
 this._f215.onmousewheel=null;
this._f215.onmousedown=MVUtil._f106(this._f215,function()
{
MVUtil._f173(window.event);
return x326._f349(window.event);
});
this._f215.onmousemove=MVUtil._f106(this._f215,function()
{
return x326._f829(window.event);
});
this._f215.onmouseup=MVUtil._f106(this._f215,function()
{
x326._f487=true;
return x326._f827(window.event);
});
this._f215.ondblclick=MVUtil._f106(this._f215,function()
{
return x326._f830(window.event);
});
document.body.attachEvent('onmouseup',MVUtil._f106(document.body,function()
{
return x326._f827(window.event);
}));
if(!this._f212)
{
if(!this._f748)
MVUtil.detachEvent(document.body,"keydown",this._f748);
this._f748=MVUtil._f106(document.body,function()
{
return x326._f831(window.event);
});
MVUtil.attachEvent(document.body,"keydown",this._f748);
if(!this._f749)
MVUtil.detachEvent(document.body,"keyup",this._f749);
this._f749=MVUtil._f106(document.body,function()
{
return x326._f832(window.event);
});
MVUtil.attachEvent(document.body,"keyup",this._f749);
var x336=function()
{
x326._f833(window.event);
return false;
}
var x337=function()
{
if(x326._f753==document.body.offsetWidth)
{
x326._f752=0;
x336();
}
else
 {
x326._f754=window.setTimeout(x337,100);
}
}
if(!this._f742)
MVUtil.detachEvent(window,"resize",this._f742);
this._f742=MVUtil._f106(window,function(x338)
{
if(Math.abs(x326._f736-x326._f109())<=3||
Math.abs(x326._f737-x326._f110())<=3)
{
return ;
}
this._f752++;
this._f753=document.body.offsetWidth;
if(this._f752>1)
{
if(this._f754)
window.clearTimeout(this._f754);
MVUtil._f754=window.setTimeout(x337,100);
}
});
MVUtil.attachEvent(window,"resize",this._f742);
}
}
else if(x327=="NS")
{
this._f215.oncontextmenu=function(x339)
{
x326._f496(x339);
if(x326._f725)
{
x326._f725(MVUtil.getEvent(x339));
}
MVUtil.runListeners(x326._f98,MVEvent.MOUSE_RIGHT_CLICK,[MVUtil.getEvent(x339)]);
if(x326._f297)
return true;
else
 return false;
};
this._f215.onmousedown=function(x340)
{
x340.stopPropagation();
return x326._f349(x340);
};
this._f215.onmousemove=function(x341)
{
return x326._f829(x341);
};
this._f215.onmouseup=function(x342)
{
x326._f487=true;
return x326._f827(x342);
};
var x343=function(x344)
{
MVUtil._f173(x344);
if(!x326.lastWheelTime)
x326.lastWheelTime=new Date();
else
 {
var x345=new Date();
if(x345-x326.lastWheelTime<10)
return ;
}
x326.lastWheelTime=new Date();
x344.preventDefault();
if(x326._f707)
{
if(x344.detail<0)x326.zoomIn();
else x326.zoomOut();
}
}
if(this._f707)
{
this._f215.addEventListener("DOMMouseScroll",x343,false);
}
else
 this._f215.removeEventListener("DOMMouseScroll",x343,false);
this._f215.ondblclick=function(x346)
{
return x326._f830(x346);
};
if(!this._f743)
MVUtil.detachEvent(window.document,"mouseup",this._f743);
this._f743=function(x347)
{
return x326._f827(x347);
};
MVUtil.attachEvent(window.document,"mouseup",this._f743);
if(!this._f212)
{
if(!this._f746)
MVUtil.detachEvent(window.document,"keydown",this._f746);
this._f746=function(x348)
{
return x326._f831(x348);
};
MVUtil.attachEvent(window.document,"keydown",this._f746);
if(!this._f747)
MVUtil.detachEvent(window.document,"keyup",this._f747);
this._f747=function(x349)
{
return x326._f832(x349);
};
MVUtil.attachEvent(window.document,"keyup",this._f747);
if(!this._f742)
MVUtil.detachEvent(window,"resize",this._f742);
this._f742=function(x350)
{
if(Math.abs(x326._f736-x326._f109())<=3||
Math.abs(x326._f737-x326._f110())<=3)
{
return ;
}
x326._f833(x350);
return false;
};
MVUtil.attachEvent(window,"resize",this._f742);
}
}
else if(x327=="SF")
{
this._f215.oncontextmenu=function(x351)
{
x326._f496(x351);
if(x326._f725)
{
x326._f725(MVUtil.getEvent(x351));
}
MVUtil.runListeners(x326._f98,MVEvent.MOUSE_RIGHT_CLICK,[MVUtil.getEvent(x351)]);
if(x326._f297)
return true;
else
 return false;
};
this._f215.onmousedown=function(x352)
{
x352.stopPropagation();
return x326._f349(x352);
};
this._f215.onmousemove=function(x353)
{
return x326._f829(x353);
};
this._f215.onmouseup=function(x354)
{
x326._f487=true;
return x326._f827(x354);
};
var x355=function(x356)
{
x356.preventDefault();
if(x356.detail>0||x356.wheelDelta<0)
x326.zoomOut();
else
 x326.zoomIn();
}
if(this._f707)
this._f215.onmousewheel=x355;
else
 this._f215.onmousewheel=null;
this._f215.ondblclick=function(x357)
{
return x326._f830(x357);
};
if(!this._f743)
MVUtil.detachEvent(window.document,"mouseup",this._f743);
this._f743=function(x358)
{
return x326._f827(x358);
};
MVUtil.attachEvent(window.document,"mouseup",this._f743);
if(!this._f212)
{
if(!this._f746)
MVUtil.detachEvent(window.document,"keydown",this._f746);
this._f746=function(x359)
{
return x326._f831(x359);
};
MVUtil.attachEvent(window.document,"keydown",this._f746);
if(!this._f747)
MVUtil.detachEvent(window.document,"keyup",this._f747);
this._f747=function(x360)
{
return x326._f832(x360);
};
MVUtil.attachEvent(window.document,"keyup",this._f747);
if(!this._f742)
MVUtil.detachEvent(window,"resize",this._f742);
this._f742=function(x361)
{
if(Math.abs(x326._f736-x326._f109())<=3||
Math.abs(x326._f737-x326._f110())<=3)
{
return ;
}
x326._f833(x361);
return false;
};
MVUtil.attachEvent(window,"resize",this._f742);
}
}
if(x327=="OS")
{
this._f215.onmousedown=function()
{
window.event.stopPropagation();
return x326._f349(window.event);
};
this._f215.onmousemove=function()
{
return x326._f829(window.event);
};
this._f215.onmouseup=function()
{
x326._f487=true;
return x326._f827(window.event);
};
this._f215.ondblclick=function()
{
return x326._f830(window.event);
};
if(!this._f743)
MVUtil.detachEvent(window.document,"mouseup",this._f743);
this._f743=function(x362)
{
return x326._f827(window.event);
};
MVUtil.attachEvent(window.document,"mouseup",this._f743);
if(!this._f212)
{
if(!this._f742)
MVUtil.detachEvent(window,"resize",this._f742);
this._f742=function(x363)
{
if(Math.abs(x326._f736-x326._f109())<=3||
Math.abs(x326._f737-x326._f110())<=3)
{
return ;
}
x326._f833(window.event);
return false;
};
MVUtil.attachEvent(window,"resize",this._f742);
}
this._f215.onkeypress=function()
{
return x326._f831(window.event);
};
this._f215.onkeyup=function()
{
return x326._f832(window.event);
};
}
this._f290.oncontextmenu=this._f215.oncontextmenu;
this._f290.onmousedown=this._f215.onmousedown;
this._f290.onmousemove=this._f215.onmousemove;
this._f290.onmouseup=this._f215.onmouseup;
this._f290.ondblclick=this._f215.ondblclick;
}
MVMapView.prototype._f828=function(x364)
{
if(x364.wheelDelta>0)this.zoomIn();
else this.zoomOut();
return false;
}
MVMapView.prototype._f833=function(x365)
{
this._f738=false;
this._f21=this.getZoomLevel();
if(this._f21>this._f702)
{
this._f21=this._f702;
return ;
}
MVUtil._f74(this.div,0,0);
MVUtil._f74(this._f290,0,0);
this._f140=0;
this._f141=0;
this._f415=this._f140;
this._f416=this._f141;
this._f759=0;
this._f760=0;
this.setZoomLevel(this._f21);
if(this._f785)
{
if(this.centerMarkUrl)
this.setCenterMark(this.centerMarkUrl,this.centerMarkW,this.centerMarkH);
else
 this.setCenterMark(_f11._f142+"center.gif",20,20);
}
if(this.navigationPanel)
this.navigationPanel.init(null,this);
for(lc=0;lc<this._f697.length;lc++)
{
this._f697[lc].setSize(
parseInt(this._f697[lc]._f1._f51*(this._f109())),
parseInt(this._f697[lc]._f1._f52*(this._f110())));
}
this._f248.setSize(Math.ceil(2*(this._f109())),
Math.ceil(2*(this._f110())));
this.display();
if(this._f634)
{
this._f634._f642(this);
}
if(this._f211)
{
this._f211._f202._f833();
this._f211._f218();
}
if(this._f715!=null)
for(var x366=0;x366<this._f715.length;x366++)
{
this._f715[x366]._f600();
}
return false;
}
MVMapView.prototype.getBrowserType=function()
{
return _f11._f70;
}
MVMapView.prototype.enableKeyboardPanning=function(x367)
{
this._f729=x367;
}
MVMapView.prototype._f831=function(x368)
{
if(!this._f487||!this._f729)
return true;
if(this._f834==undefined||this.scroll_up==undefined||this._f835==undefined||this._f836==undefined)
{
this._f834=0;
this.scroll_up=0;
this._f835=0;
this._f836=0;
this._f837=0;
}
var x369=(this.getBrowserType()!="OS")?96:48;
if(!x368.shiftKey)
{
switch(x368.keyCode)
{
case 37:this._f834=1;this._f727.add(x368.keyCode);break;
case 38:this.scroll_up=1;this._f727.add(x368.keyCode);break;
case 39:this._f835=1;this._f727.add(x368.keyCode);break;
case 40:this._f836=1;this._f727.add(x368.keyCode);break;
case x369+1:this._f834=1;this._f836=1;
break;
case x369+2:this._f836=1;this._f727.add(x368.keyCode);
break;
case x369+3:this._f835=1;this._f836=1;
break;
case x369+4:this._f834=1;this._f727.add(x368.keyCode);
break;
case x369+5:break;
case x369+6:this._f835=1;this._f727.add(x368.keyCode);
break;
case x369+7:this._f834=1;this.scroll_up=1;
break;
case x369+8:this.scroll_up=1;this._f727.add(x368.keyCode);
break;
case x369+9:this.scroll_up=1;this._f835=1;
break;
default:return true;
}
this._f838();
return false;
}
else
 return true;
}
MVMapView.prototype._f832=function(x370)
{
if(!this._f487)return;
var x371=(this.getBrowserType()!="OS")?96:48;
if(x370.shiftKey)
{
switch(x370.keyCode)
{
case 37:this._f727.remove(x370.keyCode);
break;
case 38:this._f727.remove(x370.keyCode);break;
case 39:this._f727.remove(x370.keyCode);
break;
case 40:this._f727.remove(x370.keyCode);break;
default:return true;
}
}
else
 {
switch(x370.keyCode)
{
case 33:this.smoothScroll(0,-this._f110()/2);break;
case 34:this.smoothScroll(0,this._f110()/2);break;
case 35:this.smoothScroll(this._f109()/2,0);break;
case 36:this.smoothScroll(-this._f109()/2,0);break;
case 37:this._f727.remove(x370.keyCode);break;
case 38:this._f727.remove(x370.keyCode);
break;
case 39:this._f727.remove(x370.keyCode);
break;
case 40:this._f727.remove(x370.keyCode);break;
case x371+1:this._f834=0;
this._f836=0;
break;
case x371+2:this._f836=0;
this._f727.remove(x370.keyCode);
break;
case x371+3:this._f835=0;
this._f836=0;
break;
case x371+4:this._f834=0;
this._f727.remove(x370.keyCode);
break;
case x371+5:this._f834=0;
this.scroll_up=0;
this._f835=0;
this._f836=0;
break;
case x371+6:this._f835=0;
this._f727.remove(x370.keyCode);
break;
case x371+7:this._f834=0;
this.scroll_up=0;
break;
case x371+8:this.scroll_up=0;
this._f727.remove(x370.keyCode);
break;
case x371+9:this.scroll_up=0;
this._f727.remove(x370.keyCode);
this._f835=0;
break;
case 107:this.zoomIn();break;
case 187:this.zoomIn();break;
case 109:this.zoomOut();break;
case 189:this.zoomOut();break;
case 61:this.zoomIn();
break;
case 43:this.zoomIn();
break;
case 45:this.zoomOut();
break;
case 95:this.zoomOut();
break;
default:return true;
}
}return false;
}
MVMapView.prototype._f839=function(x372)
{
x372=(x372)?x372:((window.event)?event:null);
var x373=x372.clientX+document.body.scrollLeft-this._f215.offsetLeft;
return x373;
}
MVMapView.prototype._f840=function(x374)
{
x374=(x374)?x374:((window.event)?event:null);
var x375=x374.clientY+document.body.scrollTop-this._f215.offsetTop;
return x375;
}
MVMapView.prototype._f220=function(x376)
{
var x377={left:0,top:0};
absolutePosition=MVUtil._f497(this.real_base_div);
var x378=MVUtil._f174(x376);
x377.left=x378.x-absolutePosition.x;
x377.top=x378.y-absolutePosition.y;
return x377;
}
MVMapView.prototype._f349=function(x379)
{
x379=(x379)?x379:((window.event)?event:null);
var x380=this._f220(x379);
this.mouseDownScreenX=x379.screenX;
this.mouseDownScreenY=x379.screenY;
this.mouseDownLoc=x380;
if(this._f709&&this._f711)
{
this._f713=true;
}
if(this._f693==false)return;
if(!MVUtil.mouseLeftKeyPressed(x379))
{
return;
}
if((_f11._f70!="SF")&&(_f11._f70.indexOf("OS")!=0)&&
((_f11._f70=="IF"&&x379.button==4)||(_f11._f70!="IF"
&&x379.button==1)))
{
MVUtil._f173(x379);
return false;
}
for(var x381=0;x381<this._f697.length;x381++)
{
if(this._f697[x381]._f53())
{
MVUtil._f164(this._f215,"wait");
}
}
if((_f11._f70.indexOf("OS")==0)&&x379.button==2)
{
_f841();
}
if(this._f696!=null)
{
clearInterval(this._f696);
this._f696=null;
}
this._f177=this;
this._f761(x380.left,x380.top);
this.dragCoords=this._f220(x379);
this._f496(x379);
if(_f11._f360==2)x379.returnValue=false;
if(this._f692)this._f177=null;
this._f842();
this.divLeftOrig=MVUtil._f84(this.div);
this.mapWindowMinXOrig=this._f75;
this.mapWindowMaxYOrig=this._f287;
this.mapCenterXOrig=this._f95;
return false;
}
MVMapView.prototype._f842=function()
{
if(this.fetchingMapId)
{
clearTimeout(this.fetchingMapId);
}
this.fetchingMapId=this.setTimeout("this.fetching()",400);
}
MVMapView.prototype.fetching=function()
{
this._f126();
for(var x382=0;x382<this._f135.length;x382++)
this._f135[x382]._f435();
this._f842();
}
function _f841(x0)
{
x0=(x0)?x0:((window.event)?event:null);
MVUtil._f173(x0);
return false;
}
MVMapView.prototype._f176=function()
{
if(this._f597)
{
this._f597._f176();
}
if(this._f709&&this._f713)
{
this._f710.clear();
this.marqueeZoom();
}
this._f177=null;
MVUtil._f164(this._f215,this._f209);
this._f750=false;
if(this._f82.parentNode==null||(_f11._f70=="IF"&&this._f82.parentNode.tagName!="DIV"))
this.div.appendChild(this._f82);
}
MVMapView.prototype._f829=function(x383)
{
this._f496(MVUtil.getEvent(x383));
if(this._f597)
{
MVUtil.runListeners(this._f98,MVEvent.MOUSE_MOVE,[x383]);
var x384=MVUtil._f174(x383);
this._f597._f74(
this._f597.dragStartLeft+(x384.x-this._f597.dragStartMouseLoc.x),
this._f597.dragStartTop+(x384.y-this._f597.dragStartMouseLoc.y));
if(this._f597._f576)
this._f597._f576(this._f597.getPosition());
MVUtil.runListeners(this._f597._f98,MVEvent.DRAG,[this._f597.getPosition()]);
return ;
}
if(this._f693==false||
!this._f177||this._f692||!this.draggingEnabled)
{
MVUtil.runListeners(this._f98,MVEvent.MOUSE_MOVE,[x383]);
return;
}
if(!this._f750)
{
this._f750=true;
this.div.removeChild(this._f82);
}
else if(_f11._f70=="IF"&&x383.button!=1)
{
MVUtil._f164(this._f215,this._f209);
this._f827(x383);
return ;
}
this._f594();
var x385=this._f220(x383);
if(this._f718!=this._f208)
{
MVUtil._f164(this._f215,this._f208);
this._f718=this._f208;
}
if(this._f709&&this._f711&&this._f713)
this._f713=false;
this._f504(x385.left,x385.top);
this.dragCoords=x385;
if(this._f211)
{
this._f211._f224(this._f415-this._f140,this._f416-this._f141)
}
if(this._f726)
{
this._f726(this._f415-this._f140,this._f416-this._f141);
}
return false;
}
MVMapView.prototype._f827=function(x386)
{
x386=(x386)?x386:((window.event)?event:null);
this.mouseUpLoc=this.dragCoords;
this.mouseUpScreenX=x386.screenX;
this.mouseUpScreenY=x386.screenY;
if(this._f693==false)return;
MVUtil._f164(this._f215,this._f209);
this._f718=this._f209;
if(!this._f177&&!this._f692)return;
this._f176();
if(this.dragCoords)
{
this._f496(this.dragCoords);
this.dragCoords=null;
}
else
 {
this._f496(x386);
}
this._f126();
if(this._f691==true)
{
for(var x387=0;x387<this._f135.length;x387++)
this._f135[x387]._f435();
this._f814();
this._f691=false;
if(this._f211)
{
this._f211._f225();
}
if(this._f724)
{
this._f724();
}
MVUtil.runListeners(this._f98,MVEvent.RECENTER);
if(this._f634)
{
this._f634._f642(this);
}
}
if(this.fetchingMapId!=null)
{
clearTimeout(this.fetchingMapId);
}
this._f775(this._f95,this._f96,this._f21,true);
this._f594();
}
MVMapView.prototype.setDoubleClickAction=function(x388)
{
if(x388==null||(x388!='recenter'&&x388!='zoomin'))
{
MVi18n._f6("MVMapView.setDoubleClickAction","MAPVIEWER-05519","action",this._f7);
return ;
}
this._f730=x388;
}
MVMapView.prototype._f830=function(x389)
{
x389=(x389)?x389:((window.event)?event:null);
if((this._f98[MVEvent.MOUSE_DOUBLE_CLICK]!=null&&this._f98[MVEvent.MOUSE_DOUBLE_CLICK].length!=0)||this._f394)
{
if(this._f394){
this._f394(MVUtil.getEvent(x389));
}
MVUtil.runListeners(this._f98,MVEvent.MOUSE_DOUBLE_CLICK,[MVUtil.getEvent(x389)]);
return true;
}
if(!this._f212&&this._f730=="zoomin")
{
this.zoomIn(this.getMouseLocation());
return true;
}
if(this._f692)
{
this._f177=null;
this._f215.style.cursor=this._f209;
return true;
}
this._f215.style.cursor=this._f209;
if(this._f696!=null)
{
clearInterval(this._f696);
this._f696=null;
}
var x390=this._f220(x389);
var x391=x390.left;
var x392=x390.top;
this._f776=this._f109()/2-x391;
this._f777=this._f110()/2-x392;
this._f263=Math.sqrt(this._f776*this._f776+this._f777*this._f777);
this._f780=20;
this._f778=this._f109();
this._f779=this._f110();
this._f781=this._f263>((this._f778+this._f779)/4)?300:200;
this._f782=0;
this._f783=0;
this._f784=0;
this.smoothPan();
}
MVMapView.prototype.pan=function(x393,x394)
{
this.smoothScroll(x393,x394);
}
MVMapView.prototype.smoothScroll=function(x395,x396)
{
if(x395==0&&x396==0)
{
return;
}
if(this._f692)
{
this._f177=null;
this._f215.style.cursor=this._f209;
return true;
}
this._f215.style.cursor=this._f209;
if(this._f696!=null)
{
clearTimeout(this._f696);
this._f696=null;
}
this._f776=x395*(-1);
this._f777=x396*(-1);
this._f263=Math.sqrt(x395*x395+x396*x396);
this._f780=10;
this._f778=this._f109();
this._f779=this._f110();
this._f781=this._f263>((this._f778+this._f779)/4)?300:200;
this._f782=0;
this._f783=0;
this._f784=0;
this.smoothPan();
}
MVMapView.prototype.smoothPan=function(x397)
{
if((!this.reCenterTag)&&this._f776==0&&this._f777==0)
return;
this._f782++;
var x398=x397?1:this._f782*this._f780/this._f781;
this.targetX=this._f776*(2*x398-x398*x398);
this.targetY=this._f777*(2*x398-x398*x398);
var x399=this._f778/2;
var x400=this._f779/2;
this._f761(x399,x400);
x399=Math.round(this.targetX-this._f783)+this._f778/2;
x400=Math.round(this.targetY-this._f784)+this._f779/2;
this._f504(x399,x400);
if(this._f211)
{
this._f211._f224(this._f415-this._f140,this._f416-this._f141)
}
if(this._f726)
{
this._f726(this._f415-this._f140,this._f416-this._f141);
}
this._f783=this.targetX;
this._f784=this.targetY;
this._f126();
for(i=0;i<this._f135.length;i++)
this._f135[i]._f435();
if(x398<1.0)
{
this._f696=this.setTimeout("this.smoothPan()",this._f780);
this._f733=true;
}
else
 {
clearTimeout(this._f696);
this._f696=null;
this._f814();
this._f691=false;
if(this._f211)
{
this._f211._f225();
}
if(this._f724)
{
this._f724();
}
MVUtil.runListeners(this._f98,MVEvent.RECENTER);
if(this._f634)
{
this._f634._f642(this);
}
this._f594();
this._f733=false;
return false;
}
}
MVMapView.prototype.smoothMove=function(x401,x402)
{
this.targetX=x401*(-1);
this.targetY=x402*(-1);
var x403=this._f109()/2;
var x404=this._f110()/2;
this._f761(x403,x404);
x403=Math.round(this.targetX+this._f109()/2);
x404=Math.round(this.targetY+this._f110()/2);
this._f504(x403,x404);
if(this._f726)
{
this._f726(this._f415-this._f140,this._f416-this._f141);
}
for(i=0;i<this._f135.length;i++)
this._f135[i]._f435();
}
var _f264=0;
MVMapView.prototype.setTimeout=function(_f149,_f150)
{
var Ie="tempVar"+_f264;
_f264++;
eval(Ie+" = this;");
var oi=_f149.replace(/\\/g,"\\\\");
oi=oi.replace(/\"/g,'\\"');
return window.setTimeout(Ie+'._setTimeoutDispatcher("'+oi+'");'+Ie+" = null;",_f150);
}
MVMapView.prototype._setTimeoutDispatcher=function(func)
{
eval(func);
}
MVMapView.prototype._f838=function(x405,x406)
{
if(!this._f696)
{
this._f696=this.setTimeout("this.arrowScroll()",5)
}
}
MVMapView.prototype.arrowScroll=function()
{
if(this._f692)
{
this._f177=null;
this._f215.style.cursor=this._f209;
return true;
}
this._f215.style.cursor=this._f209;
if(this._f696!=null)
{
clearTimeout(this._f696);
this._f696=null;
}
var x407=(this.getBrowserType()!="OS")?96:48;
if(this._f727.length>0)
{
var x408=((this._f727.contains(37)||this._f727.contains(x407+4))?1:0)+
((this._f727.contains(39)||this._f727.contains(x407+6))?-1:0);
var x409=((this._f727.contains(38)||this._f727.contains(x407+8))?1:0)+
((this._f727.contains(40)||this._f727.contains(x407+2))?-1:0);
this.moveOffsetX=(x408==1?15:0)+(x408==-1?-15:0);
this.moveOffsetY=(x409==1?15:0)+(x409==-1?-15:0);
this._f761(0,0);
this._f504(this.moveOffsetX,this.moveOffsetY);
if(this._f211)
{
this._f211._f224(this._f415-this._f140,this._f416-this._f141)
}
if(this._f726)
{
this._f726(this._f415-this._f140,this._f416-this._f141);
}
for(i=0;i<this._f135.length;i++)
this._f135[i]._f435();
this._f696=this.setTimeout("this.arrowScroll()",5)
}
else
 {
this._f594();
this._f696=null;
this._f126();
this._f814();
this._f691=false;
if(this._f211)
{
this._f211._f225();
}
if(this._f724)
{
this._f724();
}
MVUtil.runListeners(this._f98,MVEvent.RECENTER);
if(this._f634)
{
this._f634._f642(this);
}
}
}
MVMapView.prototype._f843=function(x410){
var x411=this._f220(x410);
var x412=x411.left;
var x413=x411.top;
if(((_f11._f70=="NS"))&&((x412<=0)||(x412>=
this._f109())||(x413<=0)||(x413>=
this._f110()))&&(this._f177))
{
this._f827();
}
}
MVMapView.prototype._f496=function(x414)
{
var x415=MVUtil.getMouseLocation(this,x414);
var x416=x415.sdo_point.x;
var x417=x415.sdo_point.y;
this._f685=x416;
this._f686=x417;
}
MVMapView.prototype._f844=function(x418)
{
this._f692=x418;
}
MVMapView.prototype.enableDragging=function(x419)
{
this.draggingEnabled=x419;
}
function _f728(x0)
{
this.length=0;
if(x0)
{
for(var x1=x0.length-1;x1>=0;--x1)
{
this.add(x0[x1]);
this.length++;
}
}
}
_f728.prototype.add=function(x420)
{
if(!this.contains(x420))
{
this["key"+x420]=1;
this.length++;
}
}
_f728.prototype.remove=function(x421)
{
if(this.exist(x421))
{
delete this["key"+x421];
this.length--;
}
}
_f728.prototype.contains=function(x422)
{
return this["key"+x422]==1;
}
_f728.prototype.exist=function(x423)
{
return this["key"+x423]==1||this["key"+x423]==2;
}
_f728.prototype.sleep=function()
{
for(prop in this)
{
if(typeof this[prop]!="function")
{
this[prop]=2;
}
}
}
_f728.prototype.wakeup=function()
{
for(prop in this)
{
if(typeof this[prop]!="function")
{
this[prop]=1;
}
}
}
MVMapView.prototype.addMapDecoration=function(x424)
{
if(this._f715==null)
this._f715=new Array();
this._f715.push(x424);
if(this._f93)
{
var id="_md_"+this._f716
x424.init(id,this,this._f215);
this._f716++;
x424._f93=true;
if(x424.afterDisAction)
{
x424.afterDisAction();
}
}
}
MVMapView.prototype.removeMapDecoration=function(x425)
{
if(x425==null||x425.id==null||this._f715==null)
{
return;
}
for(var x426=0;x426<this._f715.length;x426++)
if(this._f715[x426].id==x425.id)
{
if(this._f715[x426]._f569)
this._f594();
this._f715[x426]=this._f715[this._f715.length-1]
this._f715.pop();
x425._f219();
var x427=null;
if(x425._f568)
x427=x425._f592;
else
 x427=x425._f567;
x427.onclick=null;
x427.onmouseover=null;
x427.onmouseout=null;
this._f215.removeChild(x427);
MVUtil._f92(x427);
break;
}
}
MVMapView.prototype.addCopyRightNote=function(x428)
{
if(this._f826!=undefined)
{
this._f215.removeChild(this._f826);
delete this._f826;
}
var x429=document.createElement("SPAN");
x429.innerHTML=" "+x428+" ";
x429.style.zIndex=2000;
x429.style.position="absolute";
x429.style.right=MVUtil._f32(-1);
x429.style.bottom=MVUtil._f32(-1);
x429.style.fontSize=12;
x429.style.color="black";
x429.style.backgroundColor="";
this._f215.appendChild(x429);
this._f826=x429;
}
MVMapView.prototype.addOverviewMap=function(x430)
{
if(this._f211==null)
this._f211=x430;
else
 return;
if(this._f93)
this._f211.init(this._f211._f198,this);
}
function _f845(x0,x1,x2,x3,x4,x5)
{
this.div=document.createElement("img");
this.div.src=x1;
this.div.style.position="absolute";
this.div.style.zIndex=x4;
if(x5)
this.div.title=x5;
this.div.style.width=MVUtil._f32(x2);
this.div.style.height=MVUtil._f32(x3);
MVUtil._f146(this.div);
x0.appendChild(this.div);
return this.div;
}
MVMapView.prototype.addNavigationPanel=function(x431,x432,x433,x434,x435)
{
this.navigationPanel=new MVNavigationPanel(x432,x433,x434,x435);
if(!x431)
x431="WEST";
this.navigationPanel._f846=x431;
if(this._f93)
this.navigationPanel.init(this._f215,this);
}
MVMapView.prototype.addScaleBar=function(x436,x437,x438,x439)
{
if(this._f634)
return ;
this._f634=new MVScaleBar(x439);
this._f698=x436||this._f698;
this._f699=x437||this._f699;
this._f700=x438||this._f700;
if(this._f93)
this._f768(this._f634);
}
MVMapView.prototype.setMouseCursorStyle=function(x440,x441)
{
if(x441=="dragging")
this._f208=x440;
else if(x441=="default")
{
this._f209=x440;
MVUtil._f164(this._f215,x440);
}
}
MVMapView.prototype.enableLoadingIcon=function(x442)
{
this._f731=x442;
if(!x442)
this.setLoadingIconVisible(false);
}
MVMapView.prototype.setLoadingIconVisible=function(x443)
{
if(!this._f731)
x443=false;
if(!x443)
{
if(this._f732)
this._f732.style.visibility="hidden";
}
else
 {
if(!this._f732)
{
var x444=document.createElement("img");x444.src=_f11._f142+"loading.gif";
x444.style.zIndex=2001;
x444.style.position="absolute";
var x445=0,x446=0;
if(x444.width)
x445=x444.width;
if(x444.height)
x446=x444.height;
x444.style.left=MVUtil._f32((this._f109()-x445)/2);
x444.style.top=MVUtil._f32((this._f110()-x446)/2);
var x447=this;
x444.onload=function()
{
x444.style.left=MVUtil._f32((x447._f109()-x444.width)/2);
x444.style.top=MVUtil._f32((x447._f110()-x444.height)/2);
x444.onload=null;
};
this._f215.appendChild(x444);
this._f732=x444;
}
else
 {
this._f732.style.left=MVUtil._f32((this._f109()-this._f732.width)/2);
this._f732.style.top=MVUtil._f32((this._f110()-this._f732.height)/2);
this._f732.style.visibility="visible";
}
}
}
MVMapView.prototype.getMapCoordinates=function(x448)
{
return MVSdoGeometry.createPoint(x448.x/this._f76+this._f75,
this._f77-x448.y/this._f78,this.srid);
}
MVMapView.prototype.setDefaultFOIImageFormat=function(x449)
{
if(x449)
this._f68=x449.toUpperCase();
}
MVMapView.prototype.setErrorHandler=function(x450)
{
this._f7=x450;
}
MVMapView.prototype._f83=function()
{
var x451=true;
for(var x452=0;x452<this._f697.length;x452++)
{
var x453=this._f697[x452];
if(x453.div&&x453.isVisible()&&x453._f30)
{
x451=false;
break;
}
}
if(x451)
this.setLoadingIconVisible(false);
}
MVMapView.prototype._f811=function(x454)
{
if(!this._f734)
this._f734=new Array();
this._f734.push(x454);
var x455=this;
var x456=function()
{
if(x455._f733)
return ;
clearInterval(x455._f735);
x455._f735=null;
while(x455._f734.length>0)
{
x455.addThemeBasedFOI(x455._f734.shift());
}
}
if(!this._f735)
this._f735=setInterval(x456,50);
}
MVMapView.prototype._f536=function(x457)
{
if(!this._f534)
this._f534=x457;
}
MVMapView.prototype.compareBaseURL=function(x458)
{
if(x458&&x458!=""&&x458.indexOf(this._f4)<0)
return false;
else
 return true;
}
MVMapView.prototype.checkSize=function()
{
clearTimeout(this._f740);
if(!this._f215)
return ;
if(Math.abs(this._f736-this._f109())>3||
Math.abs(this._f737-this._f110())>3)
{
this._f738=true;
this._f739=0.4;
this._f736=this._f109();
this._f737=this._f110();
}
else
 this._f739+=0.4;
if(this._f739>0.4&&this._f738)
{
this._f833();
this._f738=false;
this._f739=0;
}
this._f740=this.setTimeout("this.checkSize()",300);
}
MVMapView.prototype._f157=function(x459,x460)
{
return {x:(x459-this._f95)*this._f76+this._f109()/2,
y:(this._f96-x460)*this._f78+this._f110()/2};
}
MVMapView.prototype.showInfoTip=function(x461,x462)
{
if(this.infoTipDiv)
this.infoTipDiv.parentNode.removeChild(this.infoTipDiv);
this.infoTipDiv=MVUtil._f674(x461);
this.infoTipDiv.id="mv_mrqzm_msg";
this.infoTipDiv.style.border="1px solid #000000";
this.infoTipDiv.style.backgroundColor="#FFFFCC";
this.infoTipDiv.style.position="absolute";
var x463=MVUtil._f655(this,x462);
this.infoTipDiv.style.left=x463.x+"px";
this.infoTipDiv.style.top=x463.y+"px";
this.infoTipDiv.style.zIndex=9999;
this._f215.appendChild(this.infoTipDiv);
};
MVMapView.prototype._f188=function()
{
if(this.infoTipDiv)
{
this.infoTipDiv.parentNode.removeChild(this.infoTipDiv);
this.infoTipDiv=null;
}
};
MVMapView.prototype._f805=function()
{
if(arguments.length<2)
return ;
var x464=new Array();
for(var x465=2;x465<arguments.length;x465++)
x464[x465-2]=arguments[x465];
var x466={obj:arguments[0],func:arguments[1],params:x464};
this._f94.push(x466);
}
MVMapView.addMapTileLayerDefinition=function(x467)
{
var x468=x467.dataSource?x467.dataSource+"."+x467.mapTileLayer:x467.mapTileLayer;
if(x467)
_f11._f539(x468,x467)
}
MVMapView._f8=false;
MVMapView.enableXMLHTTP=function(x469)
{
MVMapView._f8=x469;
}
MVMapView.setProxyPath=function(x470)
{
_f11._f559=x470;
}
MVMapView.setErrorHandler=function(x471)
{
MVi18n.addBehavior("func",x471,"text");
}
MVMapView._f121=true;
MVMapView.enableCodingHints=function(x472)
{
MVMapView._f121=x472;
}
MVMapView._f120=false;
MVMapView.enableTiming=function(x473)
{
MVMapView._f120=x473;
}
MVMapView.debug=false;
MVMapView.enableDebugMode=function(x474)
{
MVMapView.debug=x474;
}
MVMapView._f460=function(x475,x476,x477)
{
var x478=new Array(x476.length);
for(var x479=0;x479<x476.length;x479+=2)
{
var x480=x475(x476[x479],x476[x479+1],x477);
x478[x479]=x480.x;
x478[x479+1]=x480.y;
}
return x478;
}
MVMapView.prototype.getPixelsPerXUnit=function()
{
var x481=this.msi.mapConfig.zoomLevels[this._f21].tileImageWidth;
var x482=this.msi.mapConfig.zoomLevels[this._f21].tileWidth;
return x481/x482;
}
MVMapView.prototype.getPixelsPerYUnit=function()
{
var x483=this.msi.mapConfig.zoomLevels[this._f21].tileImageHeight;
var x484=this.msi.mapConfig.zoomLevels[this._f21].tileHeight;
return x483/x484;
}
MVMapView.prototype.wrapAroundLayer=function(x485)
{
if(!x485)
return false;
if(this.wraparound)
{
this._f126();
var x486=(x485-
this._f109())*0.5/this._f76;
var x487=this._f75-x486;
var x488=this._f287+x486;
var x489=this.msi._f159;
var x490=this.msi._f158;
var x491=x490-x489;
var x492=0;
if(x487<x489)
{
x492=Math.ceil((x489-x487)/x491);
x487+=x491*x492;
x488+=x491*x492;
x492=0-x492;
}
else if(x487>x490)
{
x492=Math.ceil((x487-x490)/x491);
x487-=x491*x492;
x488-=x491*x492;
}
if(x488>x490)
return true;
}
return false
}
MVMapView.prototype.enableMapWrapAround=function(x493)
{
this.wraparound=x493;
}
MVMapView._f847=0;
MVMapView.prototype._f802=function(x494,x495)
{
if(!x494)
return ;
this._f135.push(x494);
var x496=document.createElement("div");
this._f215.appendChild(x496);
if(!x494.visible)
x496.style.visibility="hidden";
x496.style.zIndex=this._f135.length;
x496.style.position="absolute";
x496.style.zIndex=1;
x496.style.left=MVUtil._f32(0);
x496.style.top=MVUtil._f32(0);
x496.style.width="100%";
x496.style.height="100%";
var x497=document.createElement("img");
x497.src=_f11._f142+"transparent."+(_f11._f70=="IF"?"gif":"png");
x497.style.position="absolute";
x497.style.zIndex=9999;
x497.style.left=MVUtil._f32(0);
x497.style.top=MVUtil._f32(0);
x497.style.width="100%";
x497.style.height="100%";
x496.appendChild(x497);
var x498=this;
x496.ondblclick=function(x499)
{
return x498._f830(x499);
};
var x500=document.createElement("div");
x494.layerDiv=x500;
x500.id="mvexttl_"+MVMapView._f847++;
x500.style.position="absolute";
x500.style.left=MVUtil._f32(0);
x500.style.top=MVUtil._f32(0);
x500.style.width="100%";
x500.style.height="100%";
x496.appendChild(x500);
if(this._f93)
{
x494.setCenterAndZoomLevel(this._f95,this._f96,this._f21);
x494.init(x500);
}
x494.parent=this;
MVMapView.addMapTileLayerDefinition(x494._f503);
}
MVMapView.prototype._f763=function(x501,x502,x503,x504)
{
for(var x505=0;x505<this._f135.length;x505++)
{
var x506=this._f135[x505];
if(x506.isExtAPITileLayer()&&x506.isVisible())
{
if(x506._f504)
x506._f504(x501,x502);
else
 {
var x507=function(x508)
{
x506.setCenter(x508.getPointX(),x508.getPointY());
}
var x509=MVSdoGeometry.createPoint(x503,x504,this.srid);
this.transformGeom(x509,x506.srid,null,x507);
}
}
}
}
MVMapView.prototype._f508=function(x510,x511,x512,x513,x514)
{
if(!x510.isVisible()&&!x514)
return ;
var x515=function(x516)
{
if(x514)
{
x510._f28=x516.getPointX();
x510._f29=x516.getPointY();
if(x513!=undefined&&x513!=null)
x510._f21=x513;
}
else
 {
if(x510.map)
{
if(x513!=undefined&&x513!=null)
{
x510.resize();
x510.setCenterAndZoomLevel(x516.getPointX(),x516.getPointY(),x513);
}
else
 {
x510.setCenter(x516.getPointX(),x516.getPointY());
}
}
else
 {
x510._f28=x516.getPointX();
x510._f29=x516.getPointY();
if(x513!=undefined&&x513!=null)
x510._f21=x513;
x510.init(x510.layerDiv);
}
}
}
var x517=MVSdoGeometry.createPoint(x511,x512,this.srid);
this.transformGeom(x517,x510.srid,null,x515);
}
MVMapView.prototype._f775=function(x518,x519,x520,x521)
{
for(var x522=0;x522<this._f135.length;x522++)
{
var x523=this._f135[x522];
if(x523.isExtAPITileLayer())
this._f508(x523,x518,x519,x520,x521);
}
}
MVMapView.prototype._f213=function()
{
if(this._f135.length>0)
return this._f135[0];
else
 return null;
}
MVMapView.prototype._f762=function(x524,x525,x526,x527)
{
var x528,x529;
if(this.wraparound||this._f212||!this._f751)
return {left:x525,top:x527};
if(x524==x525)
x528=x525;
else
 {
var x530=(x524-x525)/this._f76;
var x531=(x524<x525);
var x532=this.msi.mapConfig.coordSys.minX;
var x533=this.msi.mapConfig.coordSys.maxX;
var x534=(this.msi.mapConfig.coordSys.minX+this.msi.mapConfig.coordSys.maxX)/2;
var x535=this.msi.mapConfig.coordSys.maxX-this.msi.mapConfig.coordSys.minX;
var x536=this._f287-this._f75;
var x537=this._f95+x530;
var x538=x537-x536/2;
var x539=x537+x536/2;
if(x531)
{
if(x538<x532)
{
if(x536>x535)
{
if(x537<x534)
x537=x534;
}
else
 {
if(this._f75<x532)
x537=this._f95;
else
 x537=x537-(x538-x532);
}
}
}
else
 {
if(x539>x533)
{
if(x536>x535)
{
if(x537>x534)
x537=x534;
}
else
 {
if(this._f287>x533)
x537=this._f95;
else
 x537=x537-(x539-x533);
}
}
}
x530=x537-this._f95;
x528=x524-x530*this._f76;
}
if(x526==x527)
x529=x527;
else
 {
var x540=(x526-x527)/this._f78;
var x541=(x526<x527);
var x542=this.msi.mapConfig.coordSys.minY;
var x543=this.msi.mapConfig.coordSys.maxY;
var x544=(this.msi.mapConfig.coordSys.minY+this.msi.mapConfig.coordSys.maxY)/2;
var x545=this.msi.mapConfig.coordSys.maxY-this.msi.mapConfig.coordSys.minY;
var x546=this._f77-this._f293;
var x547=this._f96-x540;
var x548=x547-x546/2;
var x549=x547+x546/2;
if(!x541)
{
if(x548<x542)
{
if(x546>x545)
{
if(x547<x544)
x547=x544;
}
else
 {
if(this._f293<x542)
x547=this._f96;
else
 x547=x547-(x548-x542);
}
}
}
else
 {
if(x549>x543)
{
if(x546>x545)
{
if(x547>x544)
x547=x544;
}
else
 {
if(this._f77>x543)
x547=this._f96;
else
 x547=x547-(x549-x543);
}
}
}
x540=x547-this._f96;
x529=x526+x540*this._f78;
}
return {left:Math.round(x528),top:Math.round(x529)};
}
MVMapView.prototype._f765=function(x550)
{
if(this.wraparound||this._f212||!this._f751)
return x550;
if(this.originalCenter==null)
this.originalCenter=x550;
if(this.reCenterTag)
this.originalCenter=x550;
else
 x550=this.originalCenter;
this.reCenterTag=false;
var x551=this.msi.mapConfig.coordSys.minX;
var x552=this.msi.mapConfig.coordSys.maxX;
var x553=(this.msi.mapConfig.coordSys.minX+this.msi.mapConfig.coordSys.maxX)/2;
var x554=this.msi.mapConfig.coordSys.maxX-this.msi.mapConfig.coordSys.minX;
var x555=this._f109()/this._f76;
var x556=x550.getPointX();
var x557=x556-x555/2;
var x558=x556+x555/2;
if(x555>x554)
x556=x553;
else
 {
if(x557<x551)
x556=x551+x555/2;
else if(x558>x552)
x556=x552-x555/2;
}
var x559=this.msi.mapConfig.coordSys.minY;
var x560=this.msi.mapConfig.coordSys.maxY;
var x561=(this.msi.mapConfig.coordSys.minY+this.msi.mapConfig.coordSys.maxY)/2;
var x562=this.msi.mapConfig.coordSys.maxY-this.msi.mapConfig.coordSys.minY;
var x563=this._f110()/this._f78;
var x564=x550.getPointY();
var x565=x564-x563/2;
var x566=x564+x563/2;
if(x563>x562)
x564=x561;
else
 {
if(x565<x559)
x564=x559+x563/2;
else if(x566>x560)
x564=x560-x563/2;
}
return MVSdoGeometry.createPoint(x556,x564,this.srid);
}
MVMapView.prototype._f107=function()
{
for(var x567=0;x567<this._f135.length;x567++)
{
if(this._f135[x567].layerControl)
return this._f135[x567].layerControl;
}
return null;
}
MVMapView.prototype.combineTileLayers=function()
{
for(var x568=0;x568<this._f135.length;x568++)
{
if(this._f135[x568].layerControl)
{
for(var x569=x568+1;x569<this._f135.length;x569++)
{
if(MVUtil.canBeCombined(this._f135[x568],this._f135[x569]))
{
if(!this._f135[x568]._f439){
var x570=this._f135[x568].clone();
var x571=new _f406(this,x570);
x570.parent=this;
x570.layerControl=x571;
x570._f439=new Array();
x570._f439.push(this._f135[x568]);
this._f135[x568].destroy();
this._f135[x568]._f506=x570;
this._f135[x568]=x570;
}
this._f135[x568]._f439.push(this._f135[x569]);
this._f135[x568]._f412=this._f135[x568]._f412&&this._f135[x569]._f412;
this._f135[x569].destroy();
this._f135[x569]._f506=this._f135[x568];
this._f135.splice(x569,1);
if(this._f93)
this._f135[x568].refresh(true);
}
else
 break;
}
}
}
}
MVMapView.prototype.tileLayersConfigLoaded=function()
{
for(var x572=0;x572<this._f135.length;x572++)
{
if(!this._f135[x572].config)
return false;
}
return true;
}
MVMapView.prototype.enableMouseWheelZooming=function(x573)
{
this.setMouseWheelZoomEnabled(x573);
}
MVMapView.prototype.getMapTileLayers=function()
{
return this._f135;
}
function _f298(_f440,x,y,tabs,index,_width,_height,style,_f296,mouseOver,parent,screenLoc,parameters)
{
if(_width==null||_width<0)_width=0;
if(_height==null||_height<0)_height=0;
this._f296=_f296;
if(style!="MVInfoWindowStyle1")
{
if(!_width)
_width=_f11._f299;
if(!_height)
_height=_f11._f300;
eval(style+".init()");
this._f441=eval(style+".createWindow("+x+","+y+","+_width+","+_height+","+parent.parent._f145+")");
_f440.appendChild(this._f441);
}
var close=function(){
if(MVInfoWindowStyle1._f294)
{
clearTimeout(MVInfoWindowStyle1._f294);
MVInfoWindowStyle1._f294=null;
}
MVInfoWindowStyle1.clear();
_f296._f80.deleteInfoWindow(_f440);
}
if(style=="MVInfoWindowStyle1")
{
var _f441=this._f441;
var drawInfoWindow=function()
{
if(MVInfoWindowStyle1.sizeChanged(parent.parent._f145))
{
MVUtil._f129(_f440);
MVInfoWindowStyle1.init(parameters);
_f441=MVInfoWindowStyle1.createWindow(x,y,_width,_height,tabs.length,parent.parent._f145);
MVInfoWindowStyle1.setTitleStyle(tabs,index);
_f440.appendChild(_f441);
MVInfoWindowStyle1.addContent(_f440,tabs);
var x0=document.getElementById("infowindow3table_"+parent.parent._f145);
MVInfoWindowStyle1.getWindowSize(_width,_height,parent.parent._f145);
MVInfoWindowStyle1.removeInActiveTabs(index,parent.parent._f145);
if(x0)
{
if(!isNaN(screenLoc.x))
{
var x1=x0.offsetWidth;var x2=x0.offsetHeight;
var x3=parent.parent._f140;var x4=parent.parent._f141;
var x5=parent.width;var x6=parent.height;
var x7=screenLoc.x;var x8=screenLoc.y;
var x9=true;var x10=true;
if(x3+x7>x5/2)
x9=false;
if(x4+x8<x6/2)
x10=false;
var x11=false;
if(x10)
{
if(x4+x8-MVInfoWindowStyle1._f442-x2>=0)
x8=x8-MVInfoWindowStyle1._f442-x2;
else
 {
x8=-x4+MVInfoWindowStyle1._f442;
x11=true;
}
}
else
 {
if(x4+x8+MVInfoWindowStyle1._f442+x2<=x6)
x8=x8+MVInfoWindowStyle1._f442;
else
 {
x8=x6-x4-MVInfoWindowStyle1._f442-x2;
x11=true;
}
}
if(x9)
{
if(x3+x7+MVInfoWindowStyle1._f442+x1<=x5||x11)
x7=x7+MVInfoWindowStyle1._f442;
else
 x7=x5-x3-MVInfoWindowStyle1._f442-x1;
}
else
 {
if(x3+x7-MVInfoWindowStyle1._f442-x1>=0||x11)
x7=x7-MVInfoWindowStyle1._f442-x1;
else
 x7=-x3+MVInfoWindowStyle1._f442;
}
x0.style.left=MVUtil._f32(x7-screenLoc.x);
x0.style.top=MVUtil._f32(x8-screenLoc.y);
if(!mouseOver)
{
MVInfoWindowStyle1.addCloseButton(close);
MVInfoWindowStyle1._f443(_f440,parent.parent._f145);
}
}
else
 {
if(!mouseOver)
{
MVInfoWindowStyle1.addCloseButton(close);
MVInfoWindowStyle1._f443(_f440,parent.parent._f145);
}
}
}
}
MVInfoWindowStyle1._f294=setTimeout(drawInfoWindow,500);
}
drawInfoWindow();
this._f441=_f441;
}
else
 {
div=this._f444(tabs[0].getContent(),_width);
eval(style+".addContent(div)");
if(!mouseOver)
{
eval(style+".addCloseButton(close)");
}
}
}
_f298.prototype._f444=function(x0,x1)
{
var x2=window.document.createElement("DIV");
x2.className="infowindow";
x2.style.position="absolute";
x2.style.zIndex=3;
x2.innerHTML="<table width="+x1+"px><tr><td > "+x0+"</td></tr></table>";
MVUtil._f164(x2,"auto");
return x2;
}
_f298.prototype._f445=function(){
_f296._f80.deleteInfoWindow(_f179);
}
_f298.prototype._f446=function()
{
if(this._f441){
this._f441.style.display="none";
}
}
_f298.prototype._f447=function()
{
this._f441.style.display="";
this._f441.style.visibility="visible";
}
_f298.prototype.isVisible=function()
{
return this._f441&&this._f441.style.display!="none";
}
function MVSdoGeometry(x0,x1,x2,x3,x4)
{
if(!x0)
MVi18n._f6("MVSdoGeometry.constructor","MAPVIEWER-05519","gtype");
if(x2!=undefined&&x2!=null&&!MVUtil.isNumberArray(x2))
MVi18n._f6("MVSdoGeometry.constructor","MAPVIEWER-05527","sdo_elem_info");
if(x3!=undefined&&x3!=null&&!MVUtil.isNumberArray(x3))
MVi18n._f6("MVSdoGeometry.constructor","MAPVIEWER-05527","sdo_ordinates");
if(x4!=undefined&&x4!=null&&!(x4 instanceof MVSdoPointType))
MVi18n._f6("MVSdoGeometry.constructor","MAPVIEWER-05527","sdo_point");
this.gtype=x0;
this.srid=x1;
if(x4)
{
this.sdo_point=x4;
}
else
 this.sdo_point=null;
this.sdo_elem_info=x2;
this.sdo_ordinates=x3;
this.mbr=null;
}
MVSdoGeometry.prototype.clone=function()
{
return new MVSdoGeometry(this.gtype,this.srid,this.sdo_elem_info,this.sdo_ordinates,this.sdo_point);
}
MVSdoGeometry.prototype.equals=function(x0)
{
if(x0==null)return false;
if(this.gtype!=x0.gtype)return false;
if(this.srid!=x0.srid)return false;
if(!this.array_equals(this.getPoint(),x0.getPoint()))return false;
if(!this.array_equals(this.sdo_elem_info,x0.sdo_elem_info))return false;
if(!this.array_equals(this.sdo_ordinates,x0.sdo_ordinates))return false;
return true;
}
MVSdoGeometry.prototype.getDimensions=function()
{
return parseInt(this.gtype/1000);
}
MVSdoGeometry.prototype.getFirstPoint=function()
{
if(!this.isPoint())return null;
var x1=[];
if(this.sdo_point!=null)
{
x1[0]=this.sdo_point.x;
x1[1]=this.sdo_point.y;
if(this.getDimensions()>2)x1[2]=this.sdo_point.z;
}
else
 {
for(var x2=0;x2<this.getDimensions();x2++)
x1[x2]=this.sdo_ordinates[x2];
}
return x1;
}
MVSdoGeometry.prototype.getLabelPoint=function()
{
if(this.sdo_point==null)return null;
return [this.sdo_point.x,this.sdo_point.y];
}
MVSdoGeometry.prototype.getLastPoint=function()
{
if(!this.isPoint())return null;
var x3=[];
if(this.sdo_point!=null)
{
x3[0]=this.sdo_point.x;
x3[1]=this.sdo_point.y;
if(this.getDimensions()>2)x3[2]=this.sdo_point.z;
}
else
 {
var x4=this.sdo_ordinates.length-this.getDimensions();
for(var x5=0;x5<this.getDimensions();x5++)
x3[x5]=this.sdo_ordinates[x4+x5];
}
return x3;
}
MVSdoGeometry.prototype.getMBR=function()
{
if(!this.mbr)
{
if(this.isPoint())
this.mbr=new Array(this.getPointX(),this.getPointY(),this.getPointX(),this.getPointY());
else
 {
if(this.sdo_ordinates)
{
var x6;var x7;var x8;var x9;
for(var x10=0;x10<this.sdo_ordinates.length;x10+=2)
{
if(!x6)
{
x6=this.sdo_ordinates[x10];
x8=x6;
x7=this.sdo_ordinates[x10+1];
x9=x7;
}
else
 {
if(this.sdo_ordinates[x10]<x6)
x6=this.sdo_ordinates[x10];
else if(this.sdo_ordinates[x10]>x8)
x8=this.sdo_ordinates[x10];
if(this.sdo_ordinates[x10+1]<x7)
x7=this.sdo_ordinates[x10+1];
else if(this.sdo_ordinates[x10+1]>x9)
x9=this.sdo_ordinates[x10+1];
}
}
this.mbr=new Array(x6,x7,x8,x9);
}
}
}
return this.mbr;
}
MVSdoGeometry.prototype.getNumPoints=function()
{
if(this.isPoint())return 1;
else return this.sdo_ordinates.length/this.getDimensions();
}
MVSdoGeometry.prototype.getOrdinates=function()
{
if(this.getGType()==1&&this.sdo_point)
return [this.sdo_point.x,this.sdo_point.y];
else
 return this.sdo_ordinates;
}
MVSdoGeometry.prototype.getOrdinatesOfElements=function()
{
var x11=[];
var x12=[];
if(this.sdo_elem_info.length==3)
{
x11[0]=this.sdo_ordinates;
}
else
 {
for(var x13=0;x13<(this.sdo_elem_info.length-3)/3;x13++)
{
var x14=this.sdo_elem_info[3*x13]-1;
var x15=this.sdo_elem_info[3*(x13+1)]-this.sdo_elem_info[3*x13];
for(var x16=0;x16<x15;x16++)
{
x12[x16]=this.sdo_ordinates[x14+x16];
}
x11[x13]=x12;
x12=[];
}
for(var x16=this.sdo_elem_info[this.sdo_elem_info.length-3]-1;x16<this.sdo_ordinates.length;x16++)
{
x12[x16-this.sdo_elem_info[this.sdo_elem_info.length-3]+1]=this.sdo_ordinates[x16];
}
x11[x13]=x12;
}
return x11;
}
MVSdoGeometry.prototype.getPoint=function()
{
if(!this.isPoint())return null;
return this.getFirstPoint();
}
MVSdoGeometry.prototype.getGType=function()
{
return parseInt(this.gtype%10);
}
MVSdoGeometry.prototype.setSrid=function(x17)
{
this.srid=x17;
}
MVSdoGeometry.prototype.isPoint=function()
{
return this.getGType()==1;
}
MVSdoGeometry.prototype.isCircle=function()
{
return this.sdo_elem_info&&this.sdo_elem_info.length==3&&
this.sdo_elem_info[0]==1&&this.sdo_elem_info[1]==1003&&this.sdo_elem_info[2]==4;
}
MVSdoGeometry.prototype.getPointX=function()
{
if(!this.isPoint())return null;
return this.getFirstPoint()[0];
}
MVSdoGeometry.prototype.getPointY=function()
{
if(!this.isPoint())return null;
return this.getFirstPoint()[1];
}
MVSdoGeometry.prototype.toString=function()
{
var x18="SdoGeometry(";
x18+=this.gtype+",";
x18+=this.srid+",";
x18+=this.sdo_point+",";
if(!this.isPoint()&&this.sdo_elem_info!=null)
x18+="("+this.sdo_elem_info+"),";
else
 x18+=",";
if(!this.isPoint()&&this.sdo_ordinates!=null)
x18+="("+this.sdo_ordinates+"))";
else
 x18+=")";
return x18;
}
MVSdoGeometry.prototype.array_equals=function(x19,x20)
{
if(x19==null&&x20==null)return true;
if(x19==null||x20==null)return false;
if(x19.length!=x20.length)return false;
for(var x21=0;x21<x19.length;x21++)
{
if(x19[x21]!=x20[x21])return false;
}
return true;
}
MVSdoGeometry.createPoint=function(x22,x23,x24)
{
if(!MVUtil.isNumber(x22))
{
MVi18n._f6("MVSdoGeometry.createPoint","MAPVIEWER-05527","x");
return null;
}
if(!MVUtil.isNumber(x23))
{
MVi18n._f6("MVSdoGeometry.createPoint","MAPVIEWER-05527","y");
return null;
}
return new MVSdoGeometry(2001,x24,null,null,new MVSdoPointType(x22,x23,null));
}
MVSdoGeometry.createLineString=function(x25,x26)
{
if(!MVUtil.isNumberArray(x25))
{
MVi18n._f6("MVSdoGeometry.createLineString","MAPVIEWER-05527","ordinatesArray");
return null;
}
return new MVSdoGeometry(2002,x26,[1,2,1],x25,null);
}
MVSdoGeometry.createPolygon=function(x27,x28)
{
if(!MVUtil.isNumberArray(x27))
{
MVi18n._f6("MVSdoGeometry.createPolygon","MAPVIEWER-05527","ordinatesArray");
return null;
}
if(x27.length<=2)
return null;
if(x27[0]!=x27[x27.length-2]&&
x27[1]!=x27[x27.length-1])
{
x27.push(x27[0]);
x27.push(x27[1]);
}
return new MVSdoGeometry(2003,x28,[1,1003,1],x27,null);
}
MVSdoGeometry.createRectangle=function(x29,x30,x31,x32,x33)
{
if(!MVUtil.isNumber(x29))
{
MVi18n._f6("MVSdoGeometry.createRectangle","MAPVIEWER-05527","minX");
return null;
}
if(!MVUtil.isNumber(x31))
{
MVi18n._f6("MVSdoGeometry.createRectangle","MAPVIEWER-05527","maxX");
return null;
}
if(!MVUtil.isNumber(x30))
{
MVi18n._f6("MVSdoGeometry.createRectangle","MAPVIEWER-05527","minY");
return null;
}
if(!MVUtil.isNumber(x32))
{
MVi18n._f6("MVSdoGeometry.createRectangle","MAPVIEWER-05527","maxY");
return null;
}
var x34;
if(x29>x31)
{
x34=x31;
x31=x29;
x29=x34;
}
if(x30>x32)
{
x34=x32;
x32=x30;
x30=x34;
}
return new MVSdoGeometry(2003,x33,[1,1003,1],[x29,x30,x29,x32,x31,x32,x31,x30,x29,x30],null);
}
MVSdoGeometry.createRectangleByGCD=function(x35,x36,x37,x38,x39)
{
if(!MVUtil.isNumber(x35))
{
MVi18n._f6("MVSdoGeometry.createRectangleByGCD","MAPVIEWER-05527","minLon");
return null;
}
if(!MVUtil.isNumber(x36))
{
MVi18n._f6("MVSdoGeometry.createRectangleByGCD","MAPVIEWER-05527","minLat");
return null;
}
if(!MVUtil.isNumber(x37))
{
MVi18n._f6("MVSdoGeometry.createRectangleByGCD","MAPVIEWER-05527","width");
return null;
}
if(!MVUtil.isNumber(x38))
{
MVi18n._f6("MVSdoGeometry.createRectangleByGCD","MAPVIEWER-05527","height");
return null;
}
if(!x39)
x39=8307;
var x40=_f361._f363(new MVSdoPointType(x35,x36,0),x38,0);
var x41=_f361._f363(new MVSdoPointType(x35,x36,0),x37,Math.PI/2);
var x42=x35;var x43=x36;var x44=x41.x;var x45=x40.y;
return new MVSdoGeometry(2003,x39,[1,1003,1],[x42,x43,x42,x45,x44,x45,x44,x43,x42,x43],null);
}
MVSdoGeometry.createCircle=function(x46,x47,x48,x49)
{
if(!MVUtil.isNumber(x46))
{
MVi18n._f6("MVSdoGeometry.createCircle","MAPVIEWER-05527","cx");
return null;
}
if(!MVUtil.isNumber(x47))
{
MVi18n._f6("MVSdoGeometry.createCircle","MAPVIEWER-05527","cy");
return null;
}
if(!MVUtil.isNumber(x48))
{
MVi18n._f6("MVSdoGeometry.createCircle","MAPVIEWER-05527","radius");
return null;
}
return new MVSdoGeometry(2003,x49,[1,1003,4],[x46+x48,x47,x46,x47+x48,x46-x48,x47],null);
}
MVSdoGeometry.createCirclePolygon=function(x50,x51,x52,x53)
{
if(!MVUtil.isNumber(x50))
{
MVi18n._f6("MVSdoGeometry.createCirclePolygon","MAPVIEWER-05527","cx");
return null;
}
if(!MVUtil.isNumber(x51))
{
MVi18n._f6("MVSdoGeometry.createCirclePolygon","MAPVIEWER-05527","cy");
return null;
}
if(!MVUtil.isNumber(x52))
{
MVi18n._f6("MVSdoGeometry.createCirclePolygon","MAPVIEWER-05527","radius");
return null;
}
if(!x53)
x53=8307;
var x54=new Array();
for(i=0;i<36;i++)
{
var x55=i*Math.PI/18;
x54[i*2]=x50+x52*Math.cos(x55);
x54[i*2+1]=x51+x52*Math.sin(x55);
}
x54[72]=x54[0];
x54[73]=x54[1];
return new MVSdoGeometry(2003,x53,[1,1003,1],x54,null);
}
MVSdoGeometry.createGeodeticCirclePolygon=function(x56,x57,x58,x59)
{
if(!MVUtil.isNumber(x56))
{
MVi18n._f6("MVSdoGeometry.createGeodeticCirclePolygon","MAPVIEWER-05527","longitude");
return null;
}
if(!MVUtil.isNumber(x57))
{
MVi18n._f6("MVSdoGeometry.createGeodeticCirclePolygon","MAPVIEWER-05527","latitude");
return null;
}
if(!MVUtil.isNumber(x58))
{
MVi18n._f6("MVSdoGeometry.createGeodeticCirclePolygon","MAPVIEWER-05527","radius");
return null;
}
if(!x59)
x59=8307;
var x60=new Array();
for(i=0;i<36;i++)
{
var x61=_f361._f363(new MVSdoPointType(x56,x57,0),x58,i*Math.PI/18);
x60[i*2]=x61.x;
x60[i*2+1]=x61.y;
}
x60[72]=x60[0];
x60[73]=x60[1];
return new MVSdoGeometry(2003,x59,[1,1003,1],x60,null);
}
MVSdoGeometry.createPointAtBearing=function(x62,x63,x64,x65,x66)
{
if(!MVUtil.isNumber(x62))
{
MVi18n._f6("MVSdoGeometry.createPointAtBearing","MAPVIEWER-05527","longitude");
return null;
}
if(!MVUtil.isNumber(x63))
{
MVi18n._f6("MVSdoGeometry.createPointAtBearing","MAPVIEWER-05527","latitude");
return null;
}
if(!MVUtil.isNumber(x64))
{
MVi18n._f6("MVSdoGeometry.createPointAtBearing","MAPVIEWER-05527","bearing");
return null;
}
if(!MVUtil.isNumber(x65))
{
MVi18n._f6("MVSdoGeometry.createPointAtBearing","MAPVIEWER-05527","distance");
return null;
}
if(!x66)
x66=8307;
var x67=_f361._f363(new MVSdoPointType(x62,x63,0),x65,x64);
return MVSdoGeometry.createPoint(x67.x,x67.y,x66);
}
MVSdoGeometry.prototype.getRequestURL=function(x68)
{
var x69=String(document.location);
if(!x68)
x68=_f11._f454();
var x70="";
if(MVMapView._f8&&MVUtil._f9(x69)!=MVUtil._f9(x68))
x70=_f11._f12()+"?rtarget="+x68+"&";
else
 x70=x68+"?";
return x70;
}
MVSdoGeometry.prototype.getLength=function(unit,_f396,foiURL,callBack,dataSource)
{
if(!dataSource)
dataSource=_f11._f456();
if(!this.srid)
{
var len=MVSdoGeometry.calcLength(this.sdo_ordinates);
if(callBack)
callBack(len);
return len;
}
var srs=_f11._f457(this.srid);
var clientTransFunc=null;
if(_f396)
clientTransFunc=_f11._f458(this.srid,8307);
if(!_f396||this.srid==8307||this.srid==8265||
_f396&&clientTransFunc)
{
if((srs||this.srid==8307||this.srid==8265||_f396&&clientTransFunc)&&
(this.gtype%10==2||this.gtype%10==3)&&
this.sdo_elem_info.length==3&&this.sdo_elem_info[0]==1&&
(this.sdo_elem_info[1]==2||this.sdo_elem_info[1]==1003||this.sdo_elem_info[1]==2003)&&
this.sdo_elem_info[2]==1)
{
var convFactor=0;
if(!unit||unit=="")
convFactor=1;
else
 {
convFactor=_f11._f459(unit);
if(srs&&srs.distConvFactor&&!_f396)
convFactor=convFactor/srs.distConvFactor;
}
var ordinates=this.sdo_ordinates;
if(clientTransFunc)
ordinates=MVMapView._f460(clientTransFunc,ordinates);
if(convFactor)
{
var len=0;
if(srs&&srs.type!='GEODETIC'&&!_f396)
len=MVSdoGeometry.calcLength(ordinates)/convFactor;
else
 len=_f361._f367(ordinates)/convFactor;
if(callBack)
callBack(len);
return len;
}
}
}
var request=null;
var _f461;
var _f462=MVUtil._f49(this.toString(),"null","");
if(this.gtype%10!=2&&this.gtype%10!=6&&this.gtype%10!=3&&this.gtype%10!=7)
{
MVi18n._f6("MVSdoGeometry.getLength","MAPVIEWER-05513");
return 0;
}
else
 {
_f462=MVUtil._f49(_f462," ","");
}
var _f455=this.getRequestURL(foiURL);
var localDomain=(_f455.indexOf(_f11._f12())<0&&
MVUtil._f9(_f455)==MVUtil._f9(_f11._f119()));
var xmlHttp=localDomain||MVMapView._f8;
if(!xmlHttp&&!callBack)
{
MVi18n._f6("MVSdoGeometry.getLength","MAPVIEWER-05521");
return -1;
}
_f455+="request=getlength&version=1.1&figord="+_f462+"";
if(unit)
{
_f455=_f455+"&unit="+unit;
}
if(_f396)
{
var trans=(_f396==true)?"yes":"no";
_f455=_f455+"&togeodetic="+trans;
}
if(dataSource)
_f455=_f455+"&datasource="+dataSource;
var request=null;
var respLoaded=function()
{
if(request.readyState==4)
{
if(request.status==200)
{
var resp=xmlHttp?request.responseText:this.responseText;
try
{
eval("var result="+resp);
_f461=result;
if(result.length==0)
{
result=null;
return;
}
result=null;
}
catch(e)
{
MVi18n._f6("MVSdoGeometry.getLength","MAPVIEWER-05523",resp+"\n"+e);
return ;
}
if(callBack)
callBack(_f461);
if(xmlHttp)
return _f461;
}
}
};
try
{
request=MVUtil.getXMLHttpRequest(xmlHttp);
if(callBack||!xmlHttp)
request.onreadystatechange=respLoaded;
request.open("POST",encodeURI(_f455),callBack||!xmlHttp);
request.send("");
}catch(e)
{
MVi18n._f6("MVSdoGeometry.getLength","MAPVIEWER-05511",e);
}
if(!callBack&&xmlHttp)
{
if(request.status==200)
{
return respLoaded();
}
}
}
MVSdoGeometry.prototype.getArea=function(unit,_f396,foiURL,callBack,dataSource)
{
if(!dataSource)
dataSource=_f11._f456();
var request=null;
var _f461;
var _f462=MVUtil._f49(this.toString(),"null","");
if(this.gtype%10!=3&&this.gtype%10!=7)
{
MVi18n._f6("MVSdoGeometry.getArea","MAPVIEWER-05513");
return 0;
}
else
 {
_f462=MVUtil._f49(_f462," ","");
}
var _f455=this.getRequestURL(foiURL);
var localDomain=(_f455.indexOf(_f11._f12())<0&&
MVUtil._f9(_f455)==MVUtil._f9(_f11._f119()));
var xmlHttp=localDomain||MVMapView._f8;
if(!xmlHttp&&!callBack)
{
MVi18n._f6("MVSdoGeometry.getArea","MAPVIEWER-05521")
return -1;
}
_f455+="request=getarea&version=1.1&figord="+_f462+"";
if(unit)
{
_f455=_f455+"&unit="+unit;
}
if(_f396)
{
var trans=(_f396==true)?"yes":"no";
_f455=_f455+"&togeodetic="+trans;
}
if(dataSource)
_f455=_f455+"&datasource="+dataSource;
var request=null;
var respLoaded=function()
{
if(request.readyState==4)
{
if(request.status==200)
{
var resp=xmlHttp?request.responseText:this.responseText;
try
{
eval("var result="+resp);
_f461=result;
if(result.length==0)
{
result=null;
return;
}
result=null;
}
catch(e)
{
MVi18n._f6("MVSdoGeometry.getArea","MAPVIEWER-05523",resp+"\n"+e);
}
if(callBack)
callBack(_f461);
if(xmlHttp)
return _f461;
}
}
};
try
{
request=MVUtil.getXMLHttpRequest(xmlHttp);
if(callBack||!xmlHttp)
request.onreadystatechange=respLoaded;
request.open("POST",encodeURI(_f455),callBack||!xmlHttp);
request.send("");
}catch(e)
{
MVi18n._f6("MVSdoGeometry.getArea","MAPVIEWER-05511",e);
}
if(!callBack&&xmlHttp)
{
if(request.status==200)
{
return respLoaded();
}
}
}
MVSdoGeometry.prototype.densify=function()
{
var x71=this.getGType();
if(x71<=1||x71==5)
return this;
var x72=_f11._f457(this.srid);
if(this.srid!=8307&&this.srid!=8265&&(!x72||x72.type!='GEODETIC'&&x72.type!='PROJECTED'))
return this;
if(!this.sdo_elem_info||this.sdo_elem_info.length<3||!this.sdo_ordinates)
return this;
var x73=this.sdo_point?this.sdo_point.clone():null;
var x74=this.sdo_elem_info.slice();
var x75=this.sdo_ordinates;
var x76=this.getComponents(x74,x75);
if(x76==null||x76.length<=0)
return null;
var x77=null;
var x78=1;
if(x72&&x72.type=='PROJECTED')
x78=100000*x72.distConvFactor;
for(var x79=0;x79<x76.length;x79++)
{
var x80=this.densifyComponent(x76[x79].elem_info,x76[x79].ordinates,x78);
if(x79==0)
x77=x80.ordinates;
else
 {
x74[x79*3]=x77.length+1;
x77=x77.concat(x80.ordinates);
}
}
return new MVSdoGeometry(this.gtype,this.srid,x74,x77,x73);
}
MVSdoGeometry.prototype.getComponents=function(x81,x82)
{
if(!x81||x81.length<3||!x82)
return {elem_info:x81,sdo_ordinates:x82};
var x83=new Array();
var x84=0;
for(var x85=0;x85<x81.length/3;x85++)
{
var x86=x82.length;
if(x85+1<x81.length/3)
x86=x81[(x85+1)*3]-1;
var x87=null;
if(x81[x85*3+1]%10==3&&x81[x85*3+2]==3)
{
x81[x85*3+2]=1;
var x87=new Array();
x87.push(x82[x84+0]);
x87.push(x82[x84+1]);
x87.push(x82[x84+0]);
x87.push(x82[x84+3]);
x87.push(x82[x84+2]);
x87.push(x82[x84+3]);
x87.push(x82[x84+2]);
x87.push(x82[x84+1]);
x87.push(x82[x84+0]);
x87.push(x82[x84+1]);
}
else
 x87=x82.slice(x84,x86);
var x88={elem_info:x81.slice(x85*3,x85*3+3),
ordinates:x87};
x83.push(x88);
x84=x86;
}
return x83;
}
MVSdoGeometry.prototype.densifyComponent=function(x89,x90,x91)
{
var x92=x89[1]%10;
var x93={elem_info:x89,ordinates:x90};
switch(x92)
{
case 0:
case 1:
break;
case 2:case 3:if(x89[2]==1)x93={elem_info:x89,ordinates:this.densifyOrdinates(x90,x91)};
break;
default:
break;
}
return x93;
}
MVSdoGeometry.prototype.densifyOrdinates=function(x94,x95)
{
if(!x94||x94.length<4)
return x94;
var x96=new Array();
for(var x97=0;x97<x94.length-2;x97+=2)
{
var x98=x94[x97];
var x99=x94[x97+1];
var x100=x94[x97+2];
var x101=x94[x97+3];
var x102=x100-x98;
var x103=x101-x99;
x96.push(x98);
x96.push(x99);
var x104=Math.ceil(Math.abs(x102)/x95);
if(x104>0)
{
x102=x102*1.0/x104;
x103=x103*1.0/x104;
for(var x105=0;x105<x104-1;x105++)
{
x98+=x102;
x99+=x103;
x96.push(x98);
x96.push(x99);
}
}
}
x96.push(x94[x94.length-2]);
x96.push(x94[x94.length-1]);
return x96;
}
MVSdoGeometry.prototype.toGML=function()
{
var x106=this.getGType();
var x107=""
switch(x106%10)
{
case 1:
x107=this._f463(this.getOrdinates());
break;
case 2:
x107=this._f464(this.getOrdinates());
break;
case 3:
if(this.sdo_elem_info[2]==4)
x107=this._f465(this.getOrdinates());
else
 x107=this._f466(this.getOrdinates());
break;
case 4:
break;
case 5:
x107=this._f463(this.getOrdinates());
break;
case 6:
x107=this._f467();
break;
case 7:
x107=this._f468();
break;
}
return x107;
}
MVSdoGeometry.prototype._f463=function(x108)
{
var x109=MVSdoGeometry._f469(x108);
var x110="<Point srsName=\"SDO:"+this.srid+"\" ><coordinates>";
x110=x110+x109+"</coordinates></Point>";
return x110;
}
MVSdoGeometry.prototype._f464=function(x111)
{
var x112=MVSdoGeometry._f469(x111);
var x113="<LineString srsName=\"SDO:"+this.srid+"\" ><coordinates>";
x113=x113+x112+"</coordinates></LineString>"
return x113;
}
MVSdoGeometry.prototype._f466=function(x114)
{
var x115=MVSdoGeometry._f469(x114);
var x116="<Polygon srsName=\"SDO:"+this.srid+"\" ><outerBoundaryIs><LinearRing><coordinates>";
x116=x116+x115+"</coordinates></LinearRing></outerBoundaryIs></Polygon>"
return x116;
}
MVSdoGeometry.prototype._f468=function()
{
var x117=this.getOrdinatesOfElements();
var x118="<MULTIPOLYGON srsName=\"SDO:"+this.srid+"\" >";
for(var x119=0;x119<x117.length;x119++)
{
x118=x118+"<polygonMember>";
x118=x118+this._f466(x117[x119]);
x118=x118+"</polygonMember>";
}
x118=x118+"</MULTIPOLYGON>";
return x118;
}
MVSdoGeometry.prototype._f467=function()
{
var x120=this.getOrdinatesOfElements();
var x121="<MultiLineString srsName=\"SDO:"+this.srid+"\" >";
for(var x122=0;x122<x120.length;x122++)
{
x121=x121+"<lineStringMember>";
x121=x121+this._f464(x120[x122]);
x121=x121+"</lineStringMember>";
}
x121=x121+"</MultiLineString>";
return x121;
}
MVSdoGeometry.prototype._f465=function(x123)
{
var x124=parseFloat(x123[2]);
var x125=parseFloat(x123[1]);
var x126=parseFloat(x123[0])-parseFloat(x123[2]);
var x127="";
var x128=2*Math.PI;
var x129=x124+x126*Math.cos((x128*0)/120);
for(var x130=1;x130<120;x130++)
{
if(x130%2==0)
{
x127=x124+x126*Math.cos((x128*x130)/120);
x129+=" "+x127;
}
else
 {
x127=x125+x126*Math.sin((x128*x130)/120);
x129+=","+x127;
}
}
var x131="<Polygon srsName=\"SDO:"+this.srid+"\" ><outerBoundaryIs><LinearRing><coordinates>";
x131+=x129+"</coordinates></LinearRing></outerBoundaryIs></Polygon>";
return x131;
}
MVSdoGeometry._f469=function(x132)
{
var x133=0;
var x134="";
for(;x133<x132.length;x133+=2)
{
if(x133>0)
x134+=" ";
x134+=x132[x133+0]+","+x132[x133+1];
}
return x134;
}
MVSdoGeometry.getDistance=function(x135,x136,x137,x138,x139,x140,x141,x142,x143)
{
if(!MVUtil.isNumber(x135))
{
MVi18n._f6("MVSdoGeometry.getDistance","MAPVIEWER-05527","x1");
return null;
}
if(!MVUtil.isNumber(x136))
{
MVi18n._f6("MVSdoGeometry.getDistance","MAPVIEWER-05527","y1");
return null;
}
if(!MVUtil.isNumber(x137))
{
MVi18n._f6("MVSdoGeometry.getDistance","MAPVIEWER-05527","x2");
return null;
}
if(!MVUtil.isNumber(x138))
{
MVi18n._f6("MVSdoGeometry.getDistance","MAPVIEWER-05527","y2");
return null;
}
var x144=new Array(x135,x136,x137,x138);
var x145=MVSdoGeometry.createLineString(x144,x139);
return x145.getLength(x140,x141,x142,x143);
}
MVSdoGeometry.calcLength=function(x146)
{
var x147=0,x148=2;
if(x146&&x146.length>3)
{
for(;x148<x146.length;x148+=2)
{
var x149=x146[x148]-x146[x148-2];
var x150=x146[x148+1]-x146[x148-1];
x147+=Math.sqrt(x149*x149+x150*x150);
}
return x147;
}
else
 return 0;
}
MVSdoGeometry.calcPolygonArea=function(x151,x152)
{
if(x151&&x151.length>=4)
{
var x153=0;
var x154=x151.length/2;
var x155;
for(x155=1;x155+1<x154;x155++)
{
var x156=x151[x155*2]-x151[0];
var x157=x151[x155*2+1]-x151[1];
var x158=x151[x155*2+2]-x151[0];
var x159=x151[x155*2+3]-x151[1];
var x160=x156*x159-x158*x157;
x153+=x160;
}
return Math.abs(x160/2.0);
}
else
 return 0;
}
function MVSdoPointType(x0,x1,x2)
{
if(!MVUtil.isNumber(x0))
MVi18n._f6("MVSdoPointType.constructor","MAPVIEWER-05527","x");
if(!MVUtil.isNumber(x1))
MVi18n._f6("MVSdoPointType.constructor","MAPVIEWER-05527","y");
this.x=x0;
this.y=x1;
this.z=x2;
}
MVSdoPointType.prototype.toString=function()
{
var x161="SdoPointType( ";
x161+=this.x+", ";
x161+=this.y+", ";
x161+=this.z+" )";
return x161;
}
MVSdoPointType.prototype.clone=function()
{
var x162=new MVSdoPointType(this.x,this.y,this.z);
}
function MVOverviewMap(x0,x1,x2)
{
if(x0&&!x0.style)
{
if(x1)
x2=x1;
x1=x0;
}
this.type="MVOverviewMap";
this._f199=true;
this._f198=x0?x0:null;
this._f200=null;
this._f201=x1;
this._f202=null;
this.overviewMapTileLayer=x2;
this._f203=false;
this._f204=0;
this._f205=0;
this.coords=null;
this._f206=false;
this._f207=false;
this.borderStyle="2px solid red";
this.backgroundColor="red";
this._f208=null;
this._f209=null;
this._f210=null;
}
MVOverviewMap.prototype.init=function(x0,x1)
{
x1._f211=this;
if(x0)
this._f198=x0;
this._f198._f212=true;
this._f200=x1;
var x2=this._f200.getZoomLevel()-this._f201;
if(x2<0)
x2=0;
this._f202=new MVMapView(this._f198,this._f200._f4);
if(x1.wraparound)
this._f202.wraparound=true;
this._f202.enableLoadingIcon(false);
if(this._f209)
this._f202.setMouseCursorStyle(this._f209,"default");
if(this._f208)
this._f202.setMouseCursorStyle(this._f208,"dragging");
if(this.overviewMapTileLayer==null)
{
this.overviewMapTileLayer=this._f200._f213().clone();
}
this._f214=document.createElement("div");
this._f202._f215.appendChild(this._f214);
this._f214.style.zIndex=10000;
var x3=this;
var x4=function(x5,x6)
{
if(!x3._f207)
{
x3._f214.style.left=(parseInt(x3._f214.style.left)-x5)+"px";
x3._f214.style.top=(parseInt(x3._f214.style.top)-x6)+"px";
}
}
var x7=function(x8,x9)
{
var x10=x8-x9;
if(x9>x8&&x3._f200._f216)
{
x3._f202._f216=false;
x3._f200.setZoomLevel(MVOverviewMap._f217(x3._f200.getZoomLevel()-x10));
x3._f218();
}
else if(x9<x8&&x3._f200._f216)
{
x3._f202._f216=false;
if(x3._f200.getZoomLevel()-x8==x3._f201){
x3._f200.setZoomLevel(MVOverviewMap._f217(x3._f200.getZoomLevel()-x10));
}
else{
x3._f200.setZoomLevel(MVOverviewMap._f217(x8-x10+x3._f201));
}
x3._f218();
}
x3._f200._f216=true;
}
var x11=function()
{
if(!x3._f207)
{
x3._f206=true;
x3._f200.setCenter(x3._f202.getCenter());
x3._f218();
}
x3._f207=false;
}
this._f202.setEventListener(MVEvent.RECENTER,x11);
this._f202.addEventListener(MVEvent.ZOOM_LEVEL_CHANGE,x7);
var x12=function()
{
var x13=x3._f200.getCenter();
x3._f202.setCenter(x13);
x3._f202.setZoomLevel(MVOverviewMap._f217(x2));
x3._f202._f212=true;
function setRectangle()
{
x3._f218();
}
x3._f202.setEventListener(MVEvent.INITIALIZE,setRectangle);
x3._f202.display();
}
var x14=this.overviewMapTileLayer;
if(x14.getType&&x14.getType()=='MVExternalAPIMapTileLayer')
{
if(x14.hideCopyRights)
x14.initializeListener=function(){x14.hideCopyRights();};
}
this._f202.addMapTileLayer(this.overviewMapTileLayer,x12);
}
MVOverviewMap.prototype.addOverviewMapTileLayer=function()
{
this.overviewMapTileLayer=this._f200._f213().clone();
this._f202.addMapTileLayer(this.overviewMapTileLayer);
}
MVOverviewMap.prototype._f219=function()
{
this.destroy();
}
MVOverviewMap.prototype.destroy=function()
{
if(this._f200)
this._f200._f211=null;
if(this._f202)
this._f202.destroy();
MVUtil._f191(this._f198,this._f200?this._f200._f7:null);
this._f202=null;
}
MVOverviewMap.prototype._f218=function()
{
var x15=this._f200.getMapWindowBBox();
var x16=this._f202.getMapWindowBBox();
var x17=this;
var x18=function(x19)
{
var x20=x19.getMBR();
var x21=x16.getMBR();
x17.xRatio=(x20[2]-x20[0])/(x21[2]-x21[0]);
x17.yRatio=(x20[3]-x20[1])/(x21[3]-x21[1]);
var x22=x17.xRatio*x17._f202._f109();
var x23=x17.yRatio*x17._f202._f110();
x17._f214.style.position="absolute";
x17._f214.style.overflow="hidden";
x17._f214.style.zIndex=10000
x17._f214.style.left=((x17._f202._f109()-x22)/2)+"px";
x17._f214.style.top=((x17._f202._f110()-x23)/2)+"px";
x17._f214.style.width=x22+"px";
x17._f214.style.height=x23+"px";
x17._f214.style.border=x17.borderStyle;
if(_f11._f70=='NS')
x17._f214.innerHTML="<div  style='width: "+x22+"px; height: "+x23
+"px;opacity: 0.3;background-color:"+x17.backgroundColor+";'></div>";
else if(_f11._f70=='SF')
x17._f214.innerHTML="<div  style='width: "+x22+"px; height: "+x23
+"px;opacity: 0.3;background-color:"+x17.backgroundColor+";'></div>";
else if(_f11._f70=='IF')
x17._f214.innerHTML="<div  style='width: "+x22+"px; height: "+x23
+"px;filter:alpha(opacity=30);background-color:"+x17.backgroundColor+";'></div>";
if(x22>x17._f202._f109()||x23>x17._f202._f110())
x17._f214.style.visibility="hidden";
else
 x17._f214.style.visibility=x17._f202._f215.style.visibility;
x17._f214.onmousedown=MVUtil._f106(x17._f214,function(x24){
var x25=x17._f200.getCenter();
x24=(x24)?x24:((window.event)?event:null);
if(x24.button==2)
{
return;
}
x17.coords=x17._f202._f220(x24)
x17._f203=true;
MVUtil._f173(x24);
x17._f204=0;
x17._f205=0;
})
x17._f214.onmouseup=MVUtil._f106(x17._f214,function(x26)
{
if(x17._f203)
{
x26=(x26)?x26:((window.event)?event:null);
x17._f203=false;
x26.cancelBubble=true;
x17._f214._f221();
}
})
x17._f214._f221=MVUtil._f106(x17._f214,function()
{
x17._f203=false;
var x27=parseInt(this.style.left)+parseInt(this.style.width)/2;
var x28=parseInt(this.style.top)+parseInt(this.style.height)/2;
var x29=x17._f202;
var x30=x27-x29._f109()/2;
x30=x30/x29._f76;
var x31=x28-x29._f110()/2;
x31=x31/x29._f78;
var x32=x17._f202._f222()+x30;
var x33=x17._f202._f223()-x31;
var x34=MVSdoGeometry.createPoint(x32,x33,x17._f202.srid);
x17._f202.setCenter(x34);
})
if(!x17._f210)
MVUtil.detachEvent(window.document,"mouseup",x17._f210);
x17._f210=MVUtil._f106(window.document,function(x35)
{
if(x17._f203){
x17._f203=false;
x17._f214._f221();
}
});
MVUtil.attachEvent(window.document,"mouseup",x17._f210);
x17._f214.onmousemove=MVUtil._f106(x17._f214,function(x36)
{
x36=(x36)?x36:((window.event)?event:null);
if(x17._f203)
{
var x37=x17._f202._f220(x36);
x=x37.left-x17.coords.left;
y=x37.top-x17.coords.top;
x17.coords=x37;
this.style.left=(parseInt(this.style.left)+x)+"px";
this.style.top=(parseInt(this.style.top)+y)+"px";
x36.cancelBubble=true;
}
})
}
if(x15.srid!=x16.srid)
{
this._f200.transformGeom(x15,x16.srid,null,x18);
}
else
 x18(x15);
}
MVOverviewMap.prototype._f224=function(x38,x39)
{
if(this.xRatio==undefined)
return ;
var x40=Math.round(this.xRatio*x38);
var x41=Math.round(this.yRatio*x39);
if(x40>=0)
x40=Math.floor(x40);
else
 x40=Math.ceil(x40);
if(x41>=0)
x41=Math.floor(x41);
else
 x41=Math.ceil(x41);
if(!this._f206)
{
this._f207=true;
this._f214.style.left=(parseInt(this._f214.style.left)+x40)+"px";
this._f214.style.top=(parseInt(this._f214.style.top)+x41)+"px";
}
}
MVOverviewMap.prototype._f225=function()
{
if(!this._f206)
{
this._f207=true;
this._f202.setCenter(this._f200.getCenter());
this._f218();
}
this._f206=false;
}
MVOverviewMap.prototype._f226=function(x42,x43,x44)
{
var x45=x42-x43;
if(x43>=x42&&this._f202._f216)
{
this._f200._f216=false;
if(x42-this._f202.getZoomLevel()==this._f201){
if(x44)
this._f202.setCenterAndZoomLevel(x44,MVOverviewMap._f217(this._f202.getZoomLevel()-x45));
else
 this._f202.setZoomLevel(MVOverviewMap._f217(this._f202.getZoomLevel()-x45));
}
else {
if(x44)
this._f202.setCenterAndZoomLevel(x44,MVOverviewMap._f217(x42-x45-this._f201));
else
 this._f202.setZoomLevel(MVOverviewMap._f217(x42-x45-this._f201));
}
this._f218();
}
else if(x43<x42&&this._f202._f216)
{
this._f200._f216=false;
if(x44)
this._f202.setCenterAndZoomLevel(x44,MVOverviewMap._f217(this._f202.getZoomLevel()-x45));
else
 this._f202.setZoomLevel(MVOverviewMap._f217(this._f202.getZoomLevel()-x45));
this._f218();
}
this._f202._f216=true;
}
MVOverviewMap.prototype.setRectangleStyle=function(x46,x47)
{
this.borderStyle=x46;
this.backgroundColor=x47;
}
MVOverviewMap.prototype.setMouseCursorStyle=function(x48,x49)
{
if(x49=="dragging")
this._f208=x48;
else if(x49=="default")
{
this._f209=x48;
MVUtil._f164(this._f215,x48);
}
}
MVOverviewMap._f217=function(x50)
{
if(!x50||x50<0)
return 0;
else
 return x50;
}
function MVInfoWindowStyle1()
{
}
MVInfoWindowStyle1._f815=null;
MVInfoWindowStyle1.closeButtonImgURL=null;
MVInfoWindowStyle1._f865=function(x0)
{
if(x0&&x0.hideCloseButton)
this.hideCloseButton=true;
else
 this.hideCloseButton=false;
if(x0&&x0.closeButtonImageURL)
{
if(!this._f866||
this._f866!=x0.closeButtonImageURL)
{
this.closeButtonWidth=null;
this.closeButtonHeight=null;
}
this._f866=x0.closeButtonImageURL;
if(x0.closeButtonWidth)
this.closeButtonWidth=x0.closeButtonWidth;
if(x0.closeButtonHeight)
this.closeButtonHeight=x0.closeButtonHeight;
}
else
 {
if(!this._f866||
this._f866!=MVInfoWindowStyle1.closeButtonImgURL)
{
this.closeButtonWidth=null;
this.closeButtonHeight=null;
}
this._f866=MVInfoWindowStyle1.closeButtonImgURL;
this.closeButtonWidth=14;
this.closeButtonHeight=14;
}
if(x0&&x0.bodyStyle)
this._f867=x0.bodyStyle;
else
 this._f867="border:1px;border-color:#8794A3;background-color:#FFFFFF";
if(x0&&x0.titleBarStyle)
this._f868=x0.titleBarStyle;
else
 this._f868="background-color:#CFDCEB;font-weight:bold";
if(x0&&x0.coneStyle)
{
this._f869=x0.coneStyle;
var x1=x0.coneStyle.split(/;|:/);
if(x1)
{
for(i=0;i<x1.length;i++)
if(x1[i].indexOf("opacity")>=0&&i<x1.length)
{
this._f870=parseInt(x1[i+1]);
break;
}
}
}
else
 {
this._f869="background-color:#99CC33;opacity:30";
this._f870=30;
}
if(x0&&x0.offset)
MVInfoWindowStyle1._f442=x0.offset;
else
 MVInfoWindowStyle1._f442=20;
if(x0&&x0.activeTab)
this.style_activeTab=x0.activeTab;
else
 this.style_activeTab="background-color:#FFFFFF;font-weight:bold";
if(x0&&x0.inactiveTab)
this.style_inactiveTab=x0.inactiveTab;
else
 this.style_inactiveTab="background-color:#CFDCEB;font-weight:bold";
if(x0&&x0.tabBorder)
this.style_tabBorder=x0.tabBorder;
else
 this.style_tabBorder="1px solid #8794A3";
if(x0&&x0.contentStyle)
this.style_contentStyle=x0.contentStyle;
else
 this.style_contentStyle="padding:5px";
}
MVInfoWindowStyle1.init=function(x2)
{
if(!MVInfoWindowStyle1.closeButtonImgURL)
MVInfoWindowStyle1.closeButtonImgURL=_f11._f142+"infoicons/closeDialog_n.png";
if(x2)
MVInfoWindowStyle1._f865(x2);
else
 MVInfoWindowStyle1._f865(MVInfoWindowStyle1._f815);
MVInfoWindowStyle1._f299=null;
MVInfoWindowStyle1._f300=null;
MVInfoWindowStyle1._f294=null;
this._f871=null;
this._f872=null;
this.x=null;
this.y=null;
this._f873="";
this._f874=null;
this._f875=null;
}
MVInfoWindowStyle1.createWindow=function(x3,x4,x5,x6,x7,x8)
{
this.x=x3;
this.y=x4;
var x9=document.createElement("div");
var x10=document.createElement("table");
x10.id="infowindow3table_"+x8;
x10.style.cssText=this._f867;
x10.style.borderStyle="solid";
x10.style.cellSpacing="0px";
x10.style.borderSpacing="0px";
if(_f11._f70=='IF')
x10.style.borderCollapse="collapse";
x10.style.padding="0px";
if(!x10.style.width||x10.style.width==""||
x10.style.width=="0"||x10.style.width=="0px")
{
if(x5)
x10.style.width=MVUtil._f32(x5);
else
 x10.style.width=MVUtil._f32(_f11._f299);
}
if(!x10.style.height||x10.style.height==""||
x10.style.height=="0"||x10.style.height=="0px")
{
if(x6)
x10.style.height=MVUtil._f32(x6);
else
 x10.style.height=MVUtil._f32(_f11._f300);
}
var x11=document.createElement("tbody");
x10.appendChild(x11);
var x12=document.createElement("tr");
this.titleRow=x12;
x12.style.border="0px";
x12.style.padding="0px";
x12.style.height=MVUtil._f32(10);
x11.appendChild(x12);
this._f875=new Array();
var x13=this;
var x14=function(){
MVInfoWindowStyle1.pauseRedraw=true;
var x15=this.number;
var x16=x13._f875.length;
if(x15==x13.active)
{
MVInfoWindowStyle1.pauseRedraw=false;
return;
}
var x17=document.getElementById("infowindow3table_"+x8)
var x18=x17.childNodes[0];
if(x18.childNodes.length>1)
x18.removeChild(x18.childNodes[1]);
x18.appendChild(x13._f872[x15]);
x13._f875[x13.active].style.cssText=x13.style_inactiveTab;
x13._f875[x13.active].style.height=MVUtil._f32(10);x13._f875[x13.active].style.width=((100-10)/x16)+"%";
x13._f875[x13.active].style.borderBottom=x13.style_tabBorder;
x13._f875[x13.active].style.cursor="pointer";
if(x13.active+1==x13._f875.length)
x13._f875[x13.active].style.borderRight="none";
else
 x13._f875[x13.active].style.borderRight=x13.style_tabBorder;
x13._f875[x15].style.cssText=x13.style_activeTab;
x13._f875[x15].style.height=MVUtil._f32(10);x13._f875[x15].style.width=((100-10)/x16)+"%";
x13._f875[x15].style.borderBottom="none";
x13._f875[x15].style.borderRight=x13.style_tabBorder;
x13._f875[x15].style.cursor="default";
if(x15+1==x13._f875.length){
x13._f875[x15].style.borderRight=x13.style_tabBorder;
}
x13.active=x15;
MVInfoWindowStyle1.pauseRedraw=false;
MVInfoWindowStyle1._f299=x17.offsetWidth;MVInfoWindowStyle1._f300=x17.offsetHeight;x17.style.height=x17.offsetHeight}
for(var x19=0;x19<x7;x19++){
var x20=document.createElement("TD");
x20.align="center";
x20.number=x19;
this._f875.push(x20);
x12.appendChild(x20);
x20.onclick=MVUtil._f106(x20,x14);
}
var x21=document.createElement("TD");
x21.style.width="10%";
x21.align="right";
x21.vAlign="top";
x21.style.padding="2px";
x12.appendChild(x21);
this._f874=x21;
this._f872=new Array();
for(var x19=0;x19<x7;x19++){var x22=document.createElement("tr");
x12.style.border="0px";
x12.style.padding="0px";
x11.appendChild(x22);
var x23=document.createElement("td");
x23.colSpan=x7+1;
x23.style.cssText=this.style_contentStyle;
x22.appendChild(x23);
this._f872.push(x22);
}
x10.style.left=MVUtil._f32(x3-MVInfoWindowStyle1._f442);
x10.style.top=MVUtil._f32(x4+MVInfoWindowStyle1._f442);
x10.style.position='absolute';
x10.style.zIndex=200;
return x10;
}
MVInfoWindowStyle1.addCloseButton=function(x24)
{
if(this.hideCloseButton)
return ;
var x25=document.createElement("img");
x25.src=this._f866;
x25.style.cursor='pointer';
if(this.closeButtonWidth)
{
x25.width=this.closeButtonWidth;
x25.style.width=this.closeButtonWidth;
}
if(this.closeButtonHeight)
{
x25.height=this.closeButtonHeight;
x25.style.height=this.closeButtonHeight;
}
if(!this.closeButtonWidth&&!this.closeButtonHeight)
x25.onload=MVInfoWindowStyle1.setCloseButtonSize;
x25.onclick=x24;
this._f874.appendChild(x25);
}
MVInfoWindowStyle1.addContent=function(x26,x27)
{
var x28=true;
for(var x29=0;x29<x27.length;x29++){
this._f872[x29].childNodes[0].innerHTML=x27[x29].getContent();
var x30=x27[x29].getTitle();
if(!x30)
x30="&nbsp;";
else
 x28=false;
this._f875[x29].innerHTML=x30;
}
if(x28&&this.hideCloseButton)
{
this.titleRow.parentNode.removeChild(this.titleRow);
MVUtil._f191(this.titleRow);
this.titleRow=null;
}
}
MVInfoWindowStyle1.setTitleStyle=function(x31,x32)
{
var x33=this._f875[0].parentNode;
var x34=this._f875.length;
if(x34>1){
x33.style.cssText=this._f868;
x33.style.border="0px none";
x33.style.padding="0px";
x33.style.height=MVUtil._f32(10);
for(var x35=0;x35<x34;x35++){
if(x35==x32){
this._f875[x35].style.cssText=this.style_activeTab;
}
else{
this._f875[x35].style.cssText=this.style_inactiveTab;
this._f875[x35].style.borderBottom=this.style_tabBorder;
this._f875[x35].style.cursor="pointer";
}
this._f875[x35].style.height=MVUtil._f32(10);this._f875[x35].style.width=((100-10)/x34)+"%";
if(x35+1!=x34||x35==x32){
this._f875[x35].style.borderRight=this.style_tabBorder;
}
}
this._f874.style.borderBottom=this.style_tabBorder;
this._f874.style.borderTop="none";
}
else if(x31[0].getTitle()){
x33.style.cssText=this._f868;
x33.style.border="0px none";
x33.style.padding="0px";
x33.style.height=MVUtil._f32(10);
}
}
MVInfoWindowStyle1._f443=function(x36,x37)
{
MVInfoWindowStyle1._f876(x36,this.x,this.y,x37);
}
MVInfoWindowStyle1._f876=function(x38,x39,x40,x41)
{
if(this._f870==0)
return ;
var x42=document.getElementById("infowindow3table_"+x41);
var x43=x42.style.zIndex-1;
var x44=x42.offsetLeft;
var x45=x42.offsetTop;
x44-=x39;
x45-=x40;
var x46=1;
var x47=1;
var x48=x42.offsetWidth;
var x49=x42.offsetHeight;
MVInfoWindowStyle1._f299=x48;
MVInfoWindowStyle1._f300=x49;
var x50=0;
var x51=50;
var x52=260;
var x53=340;
var x54=50;if(this._f871)
{
MVUtil._f191(this._f871);
this._f871.parentNode.removeChild(this._f871);
}
var x55=document.createElement("DIV");
x55.id=x42.id+'cones';
x55.style.position='absolute';
x55.style.left=x39+'px';
x55.style.top=x40+'px';
x55.style.border='0px solid #000000';
x55.style.zIndex=x43;
this._f871=x55;
x38.appendChild(x55);
zcounter=0;
var x56=x54;this._f873=Math.round((this._f870/x54)*7);
var x57=Math.abs(x44/x54);
var x58=Math.abs(x45/x54);
var x59=Math.abs(x57/x58);
var x60=0;
for(var x61=0;x61<x54;x61++)
{
var x62=((x44*1.0/x54)*x61);var x63=((x45*1.0/x54)*x61);x52=x46+Math.abs(((x48-x46)/x54*x61));x53=x47+Math.abs(((x49-x47)/x54*x61));
var x64=document.createElement("DIV");
x64.id=x42.id+'D'+x61;
x64.style.cssText=this._f869;
x64.style.position='absolute';
MVUtil._f638(x64,x62,x63,x52,x53);
x64.style.border='0px solid black';
x64.style.fontSize="1px";
x64.style.lineHeight=0;
x55.appendChild(x64);
MVInfoWindowStyle1._f877(x64);
x64.style.zIndex=zcounter;
zcounter++;
x60++;
x56-=x61;
}
coneDone=true;
}
MVInfoWindowStyle1._f877=function(x65)
{
if(_f11._f70=="IF"){
x65.style.opacity=0.05;
x65.style.filter='alpha(opacity = '+this._f873+')';
}else if(_f11._f70=="SF"){
var x66=this._f873/100;
x65.style.opacity=x66;
}else{
var x66=this._f873/100;
x65.style.MozOpacity=x66;
}
}
MVInfoWindowStyle1.sizeChanged=function(x67)
{
var x68=document.getElementById("infowindow3table_"+x67);
if(MVInfoWindowStyle1.pauseRedraw||(x68&&
MVInfoWindowStyle1._f299&&
Math.abs(MVInfoWindowStyle1._f299-x68.offsetWidth)<5&&
MVInfoWindowStyle1._f300&&
Math.abs(MVInfoWindowStyle1._f300-x68.offsetHeight)<5))
return false;
else
 return true;
}
MVInfoWindowStyle1.getWindowSize=function(x69,x70,x71)
{
var x72=document.getElementById("infowindow3table_"+x71);
if(x72)
{
var x73=x72.offsetWidth;
if(x73>(x69?x69:_f11._f299))
x72.style.width=MVUtil._f32(x73);
var x74=0;
var x75=this._f872[0].offsetHeight;
for(var x76=1;x76<this._f872.length;x76++){
if(this._f872[x74].offsetHeight<this._f872[x76].offsetHeight)
x74=x76;
x75+=this._f872[x76].offsetHeight;
}
var x77=x72.offsetHeight-x75+this._f872[x74].offsetHeight;
if(x77>(x70?x70:_f11._f300))
x72.style.height=MVUtil._f32(x77);
}
}
MVInfoWindowStyle1.setCloseButtonSize=function()
{
if(this.closeButtonWidth||!this._f874||!this._f874.firstChild)
return ;
this.closeButtonWidth=this._f874.firstChild.offsetWidth;
this.closeButtonHeight=this._f874.firstChild.offsetHeight;
}
MVInfoWindowStyle1.clear=function()
{
if(this._f871)
MVUtil._f191(this._f871);
this._f871=null;
if(this._f872)
MVUtil._f191(this._f872);
this._f872=null;
if(this._f874)
MVUtil._f191(this._f874);
this._f874=null;
if(this.titleRow)
MVUtil._f191(this.titleRow);
this.titleRow=null;
if(this._f875)
{
for(var x78=0;x78<this._f875.length;x78++)
this._f875[x78]=null;
}
this._f875=null;
}
MVInfoWindowStyle1.removeInActiveTabs=function(x79,x80)
{
var x81=document.getElementById("infowindow3table_"+x80);
var x82=this._f872.length;
if(x82>1){
for(var x83=0;x83<x82;x83++)
if(x83!=x79)
this._f872[x83].parentNode.removeChild(this._f872[x83]);
}
this.active=x79;
}
function MVInfoWindowStyle2()
{
}
MVInfoWindowStyle2.init=function()
{
this.window=null;
this._f375=null;
this.iw=_f11._f142+"/infoicons/";
this.iw_arrow=this.iw+"arrow_circle.gif";
this.iw_h=this.iw+"h.gif";
this.iw_v=this.iw+"v.gif";
this.iw_c=this.iw+"iw_c.gif";
this.iw_c1=this.iw+"iw_n.gif";
this.iw_c2=this.iw+"iw_s.gif";
this.iw_nw=this.iw+"tl.gif";
this.iw_sw=this.iw+"bl.gif";
this.iw_ne=this.iw+"tr.gif";
this.iw_se=this.iw+"br.gif";
this.closeImg=this.iw+"close_circle.gif";
this.cornerwidth=25;
this.cornerheight=25;
this.arrowwidth=41;
this.arrowheight=40;
this.arrowendheight=20;
}
MVInfoWindowStyle2.createWindow=function(x0,x1,x2,x3)
{
this.window=new Object();
this.window.arrow=_f284(this.iw_arrow,this.arrowwidth+2,this.arrowheight,1,7,0);
this.window.e=_f284(this.iw_v,1,x3+1-this.cornerheight*2,x2+this.arrowwidth,this.cornerheight,0);
this.window.w=_f284(this.iw_v,1,x3-this.cornerheight*2-20,this.arrowwidth,this.arrowendheight+this.cornerheight,0);
this.window.wu=_f284(this.iw_nw,this.cornerwidth,this.cornerheight,this.arrowwidth,0,0);
this.window.wd=_f284(this.iw_sw,this.cornerwidth,25,this.arrowwidth,x3+1-this.cornerheight,0);
this.window.eu=_f284(this.iw_ne,this.cornerwidth,this.cornerheight,x2+this.arrowwidth+1-this.cornerwidth,0,0);
this.window.ed=_f284(this.iw_se,this.cornerwidth,25,x2+this.arrowwidth+1-this.cornerwidth,x3+1-this.cornerheight,0);
this.window.c=_f284(this.iw_c,x2-1,x3+1-this.cornerheight*2,this.arrowwidth+1,this.cornerheight,0);
this.window.cu=_f284(this.iw_c1,x2+1-this.cornerwidth*2,this.cornerheight,this.arrowwidth+this.cornerwidth,0,0);
this.window.cd=_f284(this.iw_c2,x2+1-this.cornerwidth*2,25,this.arrowwidth+this.cornerwidth,x3-this.cornerheight+1,0);
this._f375=window.document.createElement("div");
this._f375.style.position="relative";
this._f375.style.left=MVUtil._f32(x0-1);
this._f375.style.top=MVUtil._f32(x1+2);
this._f375.appendChild(this.window.e);
this._f375.appendChild(this.window.wu);
this._f375.appendChild(this.window.w);
this._f375.appendChild(this.window.cu);
this._f375.appendChild(this.window.c);
this._f375.appendChild(this.window.cd);
this._f375.appendChild(this.window.wd);
this._f375.appendChild(this.window.ed);
this._f375.appendChild(this.window.eu);
this._f375.appendChild(this.window.arrow);
this.width=x2;
return this._f375;
}
MVInfoWindowStyle2.addCloseButton=function(x4)
{
this.closeButton=_f284(this.closeImg,16,16,this.width+10,10,4);
this.closeButton.style.position="absolute";
this.closeButton.onclick=MVUtil._f106(this.closeButton,
function(){x4();});
MVUtil._f164(this.closeButton,"pointer");
this._f375.appendChild(this.closeButton);
}
MVInfoWindowStyle2.addContent=function(x5)
{
x5.style.left=MVUtil._f32(55);
x5.style.top=MVUtil._f32(17);
this._f375.appendChild(x5);
}
MVInfoWindowStyle2.getWindow=function()
{
return this._f375;
}
function MVInfoWindowStyle3()
{
}
MVInfoWindowStyle3.init=function()
{
this.window=null;
this._f375=null;
this.iw=_f11._f142+"/infoicons/";
this.iw_arrow=this.iw+"arrow.gif";
this.iw_h=this.iw+"hline.gif";
this.iw_v=this.iw+"vline.gif";
this.iw_c=this.iw+"iw_c.gif";
this.closeImg=this.iw+"close.gif";
this._f376=600;
this._f377=600;
this._f378=30;
this._f379=16;
this._f380=600;
this._f381=1;
this._f382=1;
this._f383=600;
this._f384=1;
this._f385=10;
}
MVInfoWindowStyle3.createWindow=function(x0,x1,x2,x3)
{
this.window=new Object();
this.window.arrow=_f284(this.iw_arrow,this._f378,this._f379,1,this._f385,0);
this.window.n=_f284(this.iw_h,x2+1,1,this._f378+1,0,0);
this.window.s=_f284(this.iw_h,x2+1,1,this._f378+1,x3+1,0);
this.window.e=_f284(this.iw_v,1,x3+1,x2+this._f378+1,1,0);
this.window.wu=_f284(this.iw_v,1,this._f385,this._f378,0,0);
this.window.wd=_f284(this.iw_v,1,x3-this._f379-this._f385+2,this._f378+1,this._f379+this._f385,0);
this.window.c=_f284(this.iw_c,x2,x3,this._f378+1,1,0);
this._f375=window.document.createElement("div");
this._f375=window.document.createElement("div");
this._f375.style.position="relative";
this._f375.style.left=MVUtil._f32(x0);
this._f375.style.top=MVUtil._f32(x1);
this._f375.appendChild(this.window.n);
this._f375.appendChild(this.window.s);
this._f375.appendChild(this.window.e);
this._f375.appendChild(this.window.wu);
this._f375.appendChild(this.window.c);
this._f375.appendChild(this.window.wd);
this._f375.appendChild(this.window.arrow);
this.width=x2;
return this._f375;
}
MVInfoWindowStyle3.addCloseButton=function(x4)
{
this.closeButton=_f284(this.closeImg,15,15,this.width-1,10,4);
this.closeButton.style.position="absolute";
MVUtil._f164(this.closeButton,"pointer");
this._f375.appendChild(this.closeButton);
this.closeButton.onclick=MVUtil._f106(this.closeButton,
function(){x4();});
}
MVInfoWindowStyle3.addContent=function(x5)
{
x5.style.left=MVUtil._f32(50);
x5.style.top=MVUtil._f32(25);
this._f375.appendChild(x5);
}
function MVNSDP(x0)
{
this.nsdp_id=x0;
this.nsdp_theme=null;
this.nsdp_keycol=null;
this.nsdp_rendersty=null;
this.nsdp_labelsty=null;
this.nsdp_params=null;
this.nsdp_timeout=20;
this.nsdp_smfo=true;
}
MVNSDP.prototype.setTheme=function(x0)
{
this.nsdp_theme=x0;
}
MVNSDP.prototype.setKeyColumn=function(x1)
{
this.nsdp_keycol=x1;
}
MVNSDP.prototype.setRenderStyle=function(x2)
{
this.nsdp_rendersty=x2;
}
MVNSDP.prototype.setLabelStyle=function(x3)
{
this.nsdp_labelsty=x3;
}
MVNSDP.prototype.setShowMatchedFeatureOnly=function(x4)
{
this.nsdp_smfo=x4;
}
MVNSDP.prototype.setParameters=function(x5)
{
this.nsdp_params=x5;
}
MVNSDP.prototype.setTimeout=function(x6)
{
this.nsdp_timeout=x6;
}
MVNSDP.prototype.getFlatString=function()
{
var x7="{";
x7+="\"nsdp_id\":"+"\""+this.nsdp_id+"\",";
x7+="\"nsdp_theme\":"+"\""+this.nsdp_theme+"\",";
x7+="\"nsdp_keycol\":"+"\""+this.nsdp_keycol+"\",";
if(this.nsdp_smfo!=null&&this.nsdp_smfo==false)
x7+="\"nsdp_smfo\":"+"\""+this.nsdp_smfo+"\",";
if(this.nsdp_rendersty!=null)
x7+="\"nsdp_rendersty\":"+"\""+this.nsdp_rendersty+"\",";
if(this.nsdp_labelsty!=null)
x7+="\"nsdp_labelsty\":"+"\""+this.nsdp_labelsty+"\",";
x7+="\"nsdp_timeout\":"+"\""+this.nsdp_timeout+"\"";
if(this.nsdp_params==null)
{
return x7+="}";
}
x7+=",\"nsdp_params\":[";
var x8=0;
for(p in this.nsdp_params)
{
var x9=this.nsdp_params[p];
var x10=MVUtil._f49(x9,"\"","\\\"");
x7+="{"+"\""+p+"\":"+"\""+x10+"\"},";
x8++;
}
if(x8>0)
x7=x7.substr(0,x7.length-1);
x7+="]}";
return x7;
}
MVNSDP.prototype.getXML=function(x11)
{
var x12="<ns_data_provider provider_id=\""+this.nsdp_id+"\" ";
x12+="time_out=\""+this.nsdp_timeout+"\">";
x12+="<for_theme name=\""+this.nsdp_theme+"\"/>";
var x13=null;
if(this.nsdp_rendersty!=null)
x13=this.nsdp_rendersty;
else if(x11&&x11._f111!=null)
x13=x11._f111;
if(x13)
x12+="<custom_rendering_style name=\""+x13+"\"/>";
x12+="<join spatial_key_column=\""+this.nsdp_keycol+"\"/>";
if(this.nsdp_smfo!=null&&this.nsdp_smfo==false)
x12+="<display_unmatched_features>true</display_unmatched_features>";
if(this.nsdp_params!=null)
{
x12+="<parameters>";
for(p in this.nsdp_params)
{
var x14=null;
if(p=="xml")
x14=this.nsdp_params[p].replace(/</g,"&lt;");
else
 x14=this.nsdp_params[p];
x12+="<parameter name=\""+p+"\" value=\""+x14+"\"/>";
}
x12+="</parameters>";
}
x12+="</ns_data_provider>";
return x12;
}
function MVXMLStyle(x0,x1)
{
this.name=x0;
this.xmlDef=x1;
}
MVXMLStyle.prototype.getXMLString=function()
{
var x0='<style name="'+this.name+'">'+
this.xmlDef+
'</style>';
return x0;
}
function MVStyleColor(x0,x1,x2)
{
this.name=x0;
this.type="color";
this.description=null;
if(x1==null)
this.fill=null;
else if(x1.charAt(0)=='#')
this.fill=x1.substring(1,x1.length);
else
 this.fill=x1;
this.fill_opacity=255;
if(x2==null)
this.stroke=null;
else if(x2.charAt(0)=='#')
this.stroke=x2.substring(1,x2.length);
else
 this.stroke=x2;
this.stroke_opacity=255;
this.stroke_width=1;
}
MVStyleColor.prototype.setStroke=function(x0)
{
if(x0==null)
this.stroke=null;
else if(x0.charAt(0)=='#')
this.stroke=x0.substring(1,x0.length);
else
 this.stroke=x0;
}
MVStyleColor.prototype.setStrokeOpacity=function(x1)
{
this.stroke_opacity=x1;
}
MVStyleColor.prototype.setFill=function(x2)
{
if(x2==null)
this.fill=null;
else if(x2.charAt(0)=='#')
this.fill=x2.substring(1,x2.length);
else
 this.fill=x2;
}
MVStyleColor.prototype.setFillOpacity=function(x3)
{
this.fill_opacity=x3;
}
MVStyleColor.prototype.setDescription=function(x4)
{
this.description=x4;
}
MVStyleColor.prototype.setStrokeWidth=function(x5)
{
this.stroke_width=x5;
}
MVStyleColor.prototype.getXMLString=function()
{
var x6=(this.stroke==null)?"":"stroke:0x"+this.stroke+";";
var x7=(this.fill==null)?"":"fill:0x"+this.fill+";";
var x8=this.stroke_opacity<255?"stroke-opacity:"+this.stroke_opacity+";":"";
var x9=this.fill_opacity<255?"fill-opacity:"+this.fill_opacity+";":"";
var x10=this.stroke_width!=1?"stroke-width:"+this.stroke_width:"";
var x11='<style name="'+this.name+'">'+
'<svg width="1in" height="1in">'+
'<g class="color" style="'+x6+x8+x7+x9+x10+'">'+
'</g></svg></style>';
return x11;
}
function MVStyleMarker(x0,x1)
{
this.name=x0;
if(x1==null)
this.type="image";
else
 this.type=x1.toLowerCase();
this.description=null;
this.fill=null;
this.fill_opacity=255;
this.stroke=null;
this.stroke_opacity=255;
this.stroke_width=1;
this.imageUrl=null;
this.vector=null;
this.width=16;
this.height=16;
}
MVStyleMarker.prototype.setName=function(x0)
{
if(x0!=null)
this.name=x0;
}
MVStyleMarker.prototype.setSize=function(x1,x2)
{
if(x1!=null)
this.width=x1;
if(x2!=null)
this.height=x2;
}
MVStyleMarker.prototype.setImageUrl=function(x3)
{
if(x3==null)
this.imageUrl=null;
else
 {
if(x3.indexOf("://")<0)
{
if(x3.indexOf("/")==0)
x3=document.location.protocol+"//"+document.location.host+x3;
else
 {
var x4=document.location+"";
var x5=x4.lastIndexOf("/");
if(x5>0)
x4=x4.substring(0,x5+1);
x3=x4+x3;
}
}
this.imageUrl=x3;
}
}
MVStyleMarker.prototype.setStrokeColor=function(x6,x7)
{
if(x6==null)
this.stroke=null;
else if(x6.charAt(0)=='#')
this.stroke=x6.substring(1,x6.length);
else
 this.stroke=x6;
this.stroke_opacity=x7;
}
MVStyleMarker.prototype.setFillColor=function(x8,x9)
{
if(x8==null)
this.fill=null;
else if(x8.charAt(0)=='#')
this.fill=x8.substring(1,x8.length);
else
 this.fill=x8;
if(x9!=null)
this.fill_opacity=x9;
}
MVStyleMarker.prototype.setDescription=function(x10)
{
this.description=x10;
}
MVStyleMarker.prototype.setStrokeWidth=function(x11)
{
this.stroke_width=x11;
}
MVStyleMarker.prototype.setVectorShape=function(x12)
{
if(x12==null)
this.vector=null;
else
 this.vector=x12;
}
MVStyleMarker.prototype.getXMLString=function()
{
var x13="width:"+this.width+";height:"+this.height+";";
var x14=(this.stroke==null)?"":"stroke:0x"+this.stroke+";";
var x15=(this.fill==null)?"":"fill:0x"+this.fill+";";
var x16=this.stroke_opacity<255?"stroke-opacity:"+this.stroke_opacity+";":"";
var x17=this.fill_opacity<255?"fill-opacity:"+this.fill_opacity+";":"";
var x18=this.stroke_width!=1?"stroke-width:"+this.stroke_width:"";
var x19="";
if(this.type=="image")
{
x19='  <image_marker url="'+this.imageUrl+'" />';
}
else if(this.type=="vector")
{
if(this.vector.charAt(0)=='c'||this.vector.charAt(0)=='C')
{
x19='  <circle cx="0" cy="0" r="1"/>';
}
else
 {
x19='  <polyline points="'+this.vector+'"/>';
}
}
var x20='<style name="'+this.name+'">'+
'<svg width="1in" height="1in">'+
'<g class="marker" style="'+x13+x14+x16+x15+x17+x18+'">'+
x19+
'</g></svg></style>';
return x20;
}
function MVBucketSeries(x0)
{
this._f510=null;
this._f511=new Array();
this._f509=x0;
if(x0!="SCHEME_CUSTOM"&&x0!="SCHEME_EQUAL_INTERVAL")
this._f509="SCHEME_CUSTOM";
}
MVBucketSeries.prototype.setBuckets=function(x0)
{
this._f511=x0;
}
MVBucketSeries.prototype.getBuckets=function()
{
return this._f511;
}
MVBucketSeries.prototype.getScheme=function()
{
return _f509;
}
MVBucketSeries.prototype.setDefaultRenderingStyle=function(x1)
{
this._f510=x1;
}
MVBucketSeries.prototype.getXMLString=function()
{
var x2=(this._f510==null)?"":' default_style="'+this._f510+'" ';
var x3="<Buckets"+x2+">";
for(var x4=0;x4<this._f511.length;x4++)
{
var x5=this._f511[x4];
if(x5!=null)
x3+=x5.getXMLString();
}
x3+="</Buckets>";
return x3;
}
function MVBucketStyle(x0,x1)
{
this.name=x0;
this._f511=x1;
}
MVBucketStyle.prototype.getXMLString=function()
{
var x0='<style name="'+this.name+'"><AdvancedStyle><BucketStyle>';
x0+=this._f511.getXMLString()+"</BucketStyle></AdvancedStyle></style>";
return x0;
}
function MVNumericRangedBucket(x0,x1,x2,x3)
{
this.low=x0;
this.high=x1;
this.renderingStyle=x2;
this.label=x3;
}
MVNumericRangedBucket.prototype.getXMLString=function()
{
var x0=(this.label==null)?"":'label="'+this.label+'" ';
var x1="<RangedBucket "+x0;
var x2=(this.low==null)?"":'low="'+this.low+'" ';
var x3=(this.high==null)?"":'high="'+this.high+'" ';
var x4=(this.renderingStyle==null)?"":'style="'+this.renderingStyle+'" ';
x1+=x2+x3+x4;
x1+="/>";
return x1;
}
function MVMapDecoration(x0,x1,x2,x3,x4,x5,x6)
{
this.mapControl=null;
this.html=null;
if(x0)
{
if(x0._f199)
{
x0._f566=true;
this.mapControl=x0;
}
else if(x0.substr)
this.html=x0;
}
if(typeof(x1)=="number")
this.left=x1;
else
 {
this.left=1;
if(!x5&&x3)
x5=-x3;
}
if(typeof(x2)=="number")
this.top=x2;
else
 {
this.top=1;
if(!x6&&x4)
x6=-x4;
}
this.width=x3;
this.height=x4;
this._f567=document.createElement("div");
this._f567.id=null;
this._f567.isMapDecoration=true;
this.parent=null;
this._f568=false;
this._f569=false
this._f98=new Array();
this._f570=null;
this._f571=null;
this._f572=null;
this._f573=null;
this.afterCollapseListener=null;
this._f574=null;
this.afterRestoreListener=null;
this._f575=null;
this._f576=null;
this._f577=null;
this.id=null;
this.visible=true;
this._f138=0;
this._f139=0;
this._f578=(_f11._f70=="IF")?18:16;
this._f579=0;
this._f580=0;
this._f581=40;
this._f582=0;
this._f583=null;
this.icon=null;
this._f584=null;
this._f172=false;
this._f585=false;
this._f586=null;
this._f587=null;
this._f588=null;
this.buffer=(_f11._f70=="IF")?5:7;
this.collapsed=false;
this._f416=0;
this._f415=0;
this.setOffset(x5,x6);
this._f589=null;
this._f590=false;
this.cursorStyle=null;
this.style={borderStyle:"1px solid #636661",
titleBgImg:"title_bg.png",titleBgImgMO:"title_bg_over.png",titleBgImgMD:"title_bg_over.png",
minBtnImg:"button_minimize.gif",minBtnImgMO:"button_minimize.gif",minBtnImgMD:"button_minimize.gif",
resBtnImg:"button_restore.gif",resBtnImgMO:"button_restore.gif",resBtnImgMD:"button_restore.gif",
dragIconImg:"pan.png"};
}
MVMapDecoration.prototype.setOffset=function(x0,x1)
{
if(!x0)
x0=0;
if(!x1)
x1=0;
this._f138=x0;
this._f139=x1;
}
MVMapDecoration.prototype.setPosition=function(x2,x3,x4,x5)
{
if(!x4)
x4=0;
if(!x5)
x5=0;
this._f138=x4;
this._f139=x5;
if(typeof(x2)=="number")
this.left=x2;
else
 {
this.left=1;
if(!x4&&this.width)
x4=-this.width;
}
if(typeof(x3)=="number")
this.top=x3;
else
 {
this.top=1;
if(!x5&&this.height)
x5=-this.height;
}
if(!this.parent)
return ;
if(this._f568)
{
if(!this.collapsed)
this._f591(this._f592);
}
else
 this._f591(this._f567);
}
MVMapDecoration.prototype.setPrintable=function(x6)
{
this._f569=x6;
}
MVMapDecoration.prototype.setCollapsible=function(x7,x8,x9)
{
this._f568=x7;
this._f585=x8;
if(x9!=undefined||x9)
{
if(x9.borderStyle)
this.style.borderStyle=x9.borderStyle;
if(x9.titleBgImg)
this.style.titleBgImg=x9.titleBgImg;
if(x9.titleBgImgMO)
this.style.titleBgImgMO=x9.titleBgImgMO;
else if(x9.titleBgImg)
this.style.titleBgImgMO=x9.titleBgImg;
if(x9.titleBgImgMD)
this.style.titleBgImgMD=x9.titleBgImgMD;
else
 this.style.titleBgImgMD=this.style.titleBgImgMO;
if(x9.minBtnImg)
this.style.minBtnImg=x9.minBtnImg;
if(x9.minBtnImgMO)
this.style.minBtnImgMO=x9.minBtnImgMO;
else if(x9.minBtnImg)
this.style.minBtnImgMO=x9.minBtnImg;
if(x9.minBtnImgMD)
this.style.minBtnImgMD=x9.minBtnImgMD;
else
 this.style.minBtnImgMD=this.style.minBtnImgMO;
if(x9.resBtnImg)
this.style.resBtnImg=x9.resBtnImg;
if(x9.resBtnImgMO)
this.style.resBtnImgMO=x9.resBtnImgMO;
else if(x9.resBtnImg)
this.style.resBtnImgMO=this.style.resBtnImg;
if(x9.resBtnImgMD)
this.style.resBtnImgMD=x9.resBtnImgMD;
else
 this.style.resBtnImgMD=this.style.resBtnImgMO;
}
}
MVMapDecoration.prototype.setTitleBar=function(x10,x11,x12)
{
if(x10)
this._f586=x10;
if(x11)
if(x11)
{
this._f587=x11;
this.style.resBtnImg=x11;
this.style.minBtnImg=x11;
}
if(!x12&&x10)
x12=x10;
this._f588=x12;
}
MVMapDecoration.prototype.setVisible=function(x13)
{
this.visible=x13;
if(this.parent!=null&&this.parent._f93)
this._f593();
if(this.parent!=null&&this._f569)
this.parent._f594();
}
MVMapDecoration.prototype.isVisible=function()
{
return this.visible;
}
MVMapDecoration.prototype.addEventListener=function(x14,x15)
{
this.setEventListener(x14,x15);
}
MVMapDecoration.prototype.setEventListener=function(x16,x17)
{
if(x16==MVEvent.MOUSE_CLICK)
this._f570=x17;
else if(x16==MVEvent.MOUSE_OVER)
this._f571=x17;
else if(x16==MVEvent.MOUSE_OUT)
this._f572=x17;
else if(x16==MVEvent.COLLAPSE)
this._f573=x17;
else if(x16==MVEvent.AFTER_COLLAPSE)
this.afterCollapseListener=x17;
else if(x16==MVEvent.RESTORE)
this._f574=x17;
else if(x16==MVEvent.AFTER_RESTORE)
this.afterRestoreListener=x17;
else if(x16==MVEvent.DRAG_START)
this._f575=x17;
else if(x16==MVEvent.DRAG)
this._f576=x17;
else if(x16==MVEvent.DRAG_END)
this._f577=x17;
}
MVMapDecoration.prototype.attachEventListener=function(x18,x19)
{
MVUtil.attachEventListener(this._f98,x18,x19)
}
MVMapDecoration.prototype.detachEventListener=function(x20,x21)
{
MVUtil.detachEventListener(this._f98,x20,x21);
}
MVMapDecoration.prototype.getContainerDiv=function()
{
return this._f567;
}
MVMapDecoration.prototype.enableEventPropagation=function(x22)
{
this._f172=x22;
}
MVMapDecoration.prototype.isCollapsed=function()
{
return this.collapsed;
}
MVMapDecoration.prototype.collapse=function()
{
if(this.collapsed)
return ;
this._f567.style.visibility="hidden";
MVUtil._f74(this._f592,
this.parent._f109()-this._f578-this.buffer,
this.parent._f110()-this._f578-this.buffer);
this._f595=false;
if(this._f584)
clearTimeout(this._f584);
this._f584=null;
this.collapsed=true;
this._f592.style.visibility="hidden";
if(this.visible)
this._f596();
if(this.afterCollapseListener)
this.afterCollapseListener();
MVUtil.runListeners(this._f98,MVEvent.COLLAPSE);
}
MVMapDecoration.prototype.setMouseCursorStyle=function(x23)
{
this.cursorStyle=x23;
if(this.contentDiv)
MVUtil._f164(this.contentDiv,x23);
else if(this._f567)
MVUtil._f164(this._f567,x23);
}
MVMapDecoration.prototype.isDraggable=function()
{
return this._f590;
}
MVMapDecoration.prototype.setDraggable=function(x24)
{
this._f590=x24;
if(this.parent&&this==this.parent._f597)
this._f176();
if(this.parent)
{
var x25=this._f567;
if(this._f568)
x25=this._f583;
if(this._f590)
MVUtil._f164(x25,"move");
else
 MVUtil._f164(x25,"default");
}
}
MVMapDecoration.prototype.getPosition=function()
{
var x26=this._f592;
if(!this._f568)
x26=this._f567;
var x27=this.collapsed?this._f415:MVUtil._f84(x26);
var x28=this.collapsed?this._f416:MVUtil._f85(x26);
return {x:x27,y:x28};
}
MVMapDecoration.prototype._f591=function(x29,x30)
{
var x31=(this.top==1)?this._f138-this.buffer/2:this._f138;
var x32=(this.top==1)?this._f139-this.buffer/2:this._f139;
this._f416=this.parent._f110()*this.top+x32;
this._f415=this.parent._f109()*this.left+x31;
if(!x30)
{
x29.style.top=MVUtil._f32(this._f416);
x29.style.left=MVUtil._f32(this._f415);
}
this._f582=this._f581*(this.parent._f110()-this._f416)/(this.parent._f109()-this._f415);
this._f579=Math.ceil(this._f581*this.width/(this.parent._f109()-this._f415));
this._f580=Math.ceil(this._f581*this.height/(this.parent._f109()-this._f415));
}
MVMapDecoration.prototype.init=function(id,x33,x34)
{
this.id=id;
this.parent=x33;
this._f567.id=this.id;
this._f567.style.zIndex=2000;
this._f567.style.position="absolute";
if(this._f568)
{
this._f592=document.createElement("div");
this._f592.style.position="absolute";
this._f592.style.zIndex=2000;
this._f592.style.width=(this.width+this.buffer)+"px";
this._f592.style.height=(this.height+this.buffer)+"px";
this._f592.style.backgroundColor="white";
x34.appendChild(this._f592);
this.realContdiv=document.createElement("div");
this.realContdiv.style.position="absolute";
this.realContdiv.style.zIndex=2000;
this.realContdiv.style.left="3px";
this.realContdiv.style.top="3px";
this.realContdiv.style.width=this.width+"px";
this.realContdiv.style.height=this.height+"px";
this.realContdiv.style.border=this.style.borderStyle;
this.realContdiv.style.backgroundColor="white";
this._f592.appendChild(this.realContdiv);
var x35=document.createElement("div");
x35.style.position="absolute";
x35.style.left=MVUtil._f32(0);
x35.style.top=MVUtil._f32(0);
x35.style.width="100%";
x35.style.height=this._f578+2+"px";
x35.style.overflow="hidden";
this._f583=document.createElement("div");
this._f583.style.position="absolute";
this._f583.style.left=MVUtil._f32(-1);
this._f583.style.top=MVUtil._f32(-1);
this._f583.style.width="110%";
this._f583.style.height=this._f578+"px";
this._f583.style.border=this.style.borderStyle;
if(this._f588)
this._f583.title=this._f588;
x35.appendChild(this._f583);
this.realContdiv.appendChild(x35);
this.titleBg=document.createElement("img");
this.titleBg.style.width="100%";
this.titleBg.style.height="100%";
this.titleBg.src=MVUtil.getImageURL(this.style.titleBgImg);
this._f583.appendChild(this.titleBg);
var x36=this;
var x37=document.createElement("img");
this.icon=x37;
x37.style.position="absolute";
x37.style.left=MVUtil._f32(2);
x37.style.top=MVUtil._f32(1);
x37.style.height=MVUtil._f32(this._f578-3);
x37.style.width=MVUtil._f32(this._f578-3);
this._f583.appendChild(x37);
this._f583.onmousedown=function(x38)
{
if(x36._f590)
{
x36.parent._f597=x36;
x36.dragStartMouseLoc=MVUtil._f174(x38);
x36.dragStartLeft=MVUtil._f84(x36._f592);
x36.dragStartTop=MVUtil._f85(x36._f592);
MVUtil._f164(x36._f583,"move");
if(x36._f575)
x36._f575(x36.getPosition());
MVUtil.runListeners(x36._f98,MVEvent.AFTER_RESTORE,[x36.getPosition()]);
}
MVUtil._f173(x38);
}
if(this._f590)
MVUtil._f164(this._f583,"move");
MVUtil._f164(x37,"pointer");
this.setIconImageListeners(false);
if(this._f586)
{
var x39=document.createElement("div");
x39.style.position="absolute";
x39.style.left=MVUtil._f32(this._f578);
x39.style.top=MVUtil._f32(-1);
x39.style.width=MVUtil._f32(this.width-2*this._f578);
x39.style.height=MVUtil._f32(this._f578);
x39.style.fontSize=MVUtil._f32(this._f578-(_f11._f70=="IF"?3:2));
this._f583.appendChild(x39);
x39.innerHTML="<center>"+this._f586+"</center>";
}
this._f567.style.left=MVUtil._f32(0);
this._f567.style.top=this._f578+((_f11._f70=="IF")?-1:1)+"px";
this._f567.style.border=MVUtil._f32(0);
if(_f11._f70=="NS"||_f11._f70=="IF")
{
this._f567.style.width=this.width-2+"px"
this._f567.style.height=(this.height-this._f578-1)+"px";
}
else
 {
this._f567.style.width=this.width-2+"px"
this._f567.style.height=(this.height-this._f578-2)+"px";
}
if(this.html)
this._f567.innerHTML=this.html;
this.realContdiv.appendChild(this._f567);
this._f595=true;
this.n=(this.height-this._f578)/this._f580+1;
this._f162(this.realContdiv);
this._f591(this._f592);
if(this._f585)
this.collapse();
}
else
 {
x34.appendChild(this._f567);
if(this.html)
this._f567.innerHTML=this.html;
this._f591(this._f567);
if(this.width)
this._f567.style.width=MVUtil._f32(this.width);
if(this.height)
this._f567.style.height=MVUtil._f32(this.height);
this._f162(this._f567);
var x36=this;
var x40=document.createElement("img");
x40.src=MVUtil.getImageURL(this.style.dragIconImg);
MVUtil._f598(x40);
x40.style.zIndex=9999;
x40.style.left=MVUtil._f32(0);
x40.style.top=MVUtil._f32(-20);
x40.style.visibility="hidden";
if(_f11._f70=="NS")
{
x40.onmousedown=function(x41)
{
if(x36._f590)
{
x36.parent._f597=x36;
x36.dragStartMouseLoc=MVUtil._f174(x41);
x36.dragStartLeft=MVUtil._f84(x36._f567);
x36.dragStartTop=MVUtil._f85(x36._f567);
if(x36._f575)
x36._f575(x36.getPosition());
MVUtil.runListeners(x36._f98,MVEvent.AFTER_RESTORE,[x36.getPosition()]);
MVUtil._f173(x41);
}
}
}
this._f567.appendChild(x40);
var x42=this._f567.onmouseover;
this._f567.onmouseover=function(x43)
{
if(x42)
x42(x43);
if(x36._f590)
x40.style.visibility="visible";
}
var x44=this._f567.onmouseout;
this._f567.onmouseout=function(x45)
{
if(x44)
x44(x45);
if(x36.parent._f597!=x36)
x40.style.visibility="hidden";
}
if(this._f590)
MVUtil._f164(this._f567,"move");
else
 MVUtil._f164(this._f567,"default");
this._f567.onmousedown=function(x46)
{
if(x36._f590)
{
x36.parent._f597=x36;
x36.dragStartMouseLoc=MVUtil._f174(x46);
x36.dragStartLeft=MVUtil._f84(x36._f567);
x36.dragStartTop=MVUtil._f85(x36._f567);
if(x36._f575)
x36._f575(x36.getPosition());
MVUtil.runListeners(x36._f98,MVEvent.AFTER_RESTORE,[x36.getPosition()]);
MVUtil.trapEvent(x46);
}
else if(!x36._f172)
MVUtil.trapEvent(x46);
}
}
if(this.mapControl)
{
if(this.mapControl.setMouseCursorStyle&&this.cursorStyle)
this.mapControl.setMouseCursorStyle(this.cursorStyle,"default");
this.mapControl.init(this._f567,x33);
this.mapControl._f566=true;
}
if(!this._f585)
this._f593();
}
MVMapDecoration.prototype._f162=function(x47)
{
var x48=this;
var x49=function(x50)
{
if(!x48._f172)
MVUtil.trapEvent(x50);
}
x47.onclick=function(x51)
{
x49(x51);
if(x48._f570)
{
x48.parent._f176();
x48._f570();
}
if(x48._f98[MVEvent.MOUSE_CLICK]!=null&&x48._f98[MVEvent.MOUSE_CLICK].length!=0)
{
x48.parent._f176();
MVUtil.runListeners(x48._f98,MVEvent.MOUSE_CLICK);
}
}
x47.onmouseover=function(x52)
{
x49(x52);
if(x48._f571)
x48._f571(x52);
MVUtil.runListeners(x48._f98,MVEvent.MOUSE_OVER,[x52]);
};
x47.onmouseout=function(x53)
{
x49(x53);
if(x48._f572)
x48._f572(x53);
MVUtil.runListeners(x48._f98,MVEvent.MOUSE_OUT,[x53]);
}
x47.ondblclick=x49;
x47.onmousedown=x49;
x47.onmouseup=function(x54)
{
x49(x54);
if(x48._f590)
x48._f176();
}
x47.onkeyup=x49
if(_f11._f70=="OS")
x47.onkeypress=x49;
else
 x47.onkeydown=x49;
if(_f11._f70=="IF")
{
x47.oncontextmenu=x49;
x47.onselectstart=x49;
}
}
MVMapDecoration.prototype.setIconImageListeners=function(x55)
{
var x56;
if(this._f587)
x56=this._f587;
else if(x55)
x56=MVUtil.getImageURL(this.style.resBtnImg);
else
 x56=MVUtil.getImageURL(this.style.minBtnImg);
var x57=null;
if(x55)
x57=this._f589;
else
 x57=this.icon;
x57.src=x56;
var x58=this;
if(x55)
{
x57.style.height=MVUtil._f32(this._f578-3);
x57.style.width=MVUtil._f32(this._f578-3);
x57.parentNode.onmouseover=MVUtil._f106(x57.parentNode,function()
{
x57.parentNode.style.backgroundImage="url("+MVUtil.getImageURL(x58.style.titleBgImgMO)+")";
x57.src=MVUtil.getImageURL(x58.style.resBtnImgMO);
});
x57.parentNode.onmouseout=MVUtil._f106(x57.parentNode,function()
{
x57.parentNode.style.backgroundImage="url("+MVUtil.getImageURL(x58.style.titleBgImg)+")";
x57.src=MVUtil.getImageURL(x58.style.resBtnImg);
});
x57.onmousedown=MVUtil._f106(x57.parentNode,function()
{
x57.parentNode.style.backgroundImage="url("+MVUtil.getImageURL(x58.style.titleBgImgMD)+")";
x57.src=MVUtil.getImageURL(x58.style.resBtnImgMD);
});
var x59=this;
x57.parentNode.onclick=MVUtil._f106(x57.parentNode,function(x60)
{
MVUtil._f173(x60);
x57.parentNode.parentNode.removeChild(x57.parentNode);
MVUtil._f191(x57.parentNode,x59.parent._f7);
x59._f589=null;
x59._f592.style.visibility="visible";
x59._f599();
});
}
else
 {
var x61=this.titleBg;
x57.parentNode.onmouseover=MVUtil._f106(x57.parentNode,function()
{
x61.src=MVUtil.getImageURL(x58.style.titleBgImgMO);
if(x55)
x57.src=MVUtil.getImageURL(x58.style.resBtnImgMO);
else
 x57.src=MVUtil.getImageURL(x58.style.minBtnImgMO);
});
x57.parentNode.onmouseout=MVUtil._f106(x57.parentNode,function()
{
x61.src=MVUtil.getImageURL(x58.style.titleBgImg);
x57.src=MVUtil.getImageURL(x58.style.minBtnImg);
});
x57.onmousedown=MVUtil._f106(x57.parentNode,function()
{
x61.src=MVUtil.getImageURL(x58.style.titleBgImgMD);
x57.src=MVUtil.getImageURL(x58.style.minBtnImgMD);
});
var x59=this;
x57.onclick=MVUtil._f106(x57,function()
{
x59._f599();
});
}
}
MVMapDecoration.prototype._f219=function()
{
if(this.mapControl)
this.mapControl._f219();
if(this._f589)
{
this._f589.parentNode.parentNode.removeChild(this._f589.parentNode);
MVUtil._f191(this._f589.parentNode,this.parent._f7);
this._f589=null;
}
this.parent=null;
}
MVMapDecoration.prototype._f600=function()
{
this.setPosition(this.left,this.top,this._f138,this._f139);
}
MVMapDecoration.prototype._f601=function()
{
var x62=document.createElement("div");
x62.innerHTML=this.html;
x62.style.zIndex=2000;
x62.style.position="absolute";
if(this.collasible)
{
this._f602.style.right=MVUtil._f32(0);
this._f602.style.bottom=MVUtil._f32(0);
this._f602.style.width=MVUtil._f32(width);
this._f602.style.height=MVUtil._f32(height-this._f578);
this._f602.style.border="1px solid black";
this._f602.style.backgroundColor="white";
}
else{
this._f591(x62);
if(this.width)
x62.style.width=this.width;
if(this.height)
x62.style.height=this.height;
return x62;
}
}
MVMapDecoration.prototype._setTimeoutDispatcher=function(func)
{
eval(func);
}
MVMapDecoration.prototype._f593=function()
{
if(this.visible)
this._f567.style.visibility="visible";
else
 this._f567.style.visibility="hidden";
if(this._f568)
{
if(this.visible&&!this.collapsed)
this._f592.style.visibility="visible";
else
 this._f592.style.visibility="hidden";
if(!this.collapsed&&this.parent._f211&&this.parent._f211._f200&&
this._f567._f212&&this.parent)
{
this.parent._f211._f218();
}
if(this._f589&&!this.visible)
{
this._f589.parentNode.parentNode.removeChild(this._f589.parentNode);
MVUtil._f191(this._f589.parentNode,this.parent._f7);
this._f589=null;
}
else if(this.visible&&this.collapsed)
this._f596();
}
}
MVMapDecoration.prototype._f599=function()
{
if(this._f584!=null&&this._f584!="")
return;
var x63=15;
if(this._f595)
{
if(this._f573)
this._f573();
MVUtil.runListeners(this._f98,MVEvent.COLLAPSE);
this._f584=this.setTimeout("this.scrollElement(-1)",10);
}
else
 {
if(this._f574)
this._f574();
MVUtil.runListeners(this._f98,MVEvent.RESTORE);
this._f591(this._f592,true);
this._f584=this.setTimeout("this.scrollElement(1)",10);
}
}
MVMapDecoration.prototype._f596=function()
{
this._f589=document.createElement("img");
var x64=document.createElement("td");
x64.style.border=this.style.borderStyle;
x64.style.backgroundImage="url("+MVUtil.getImageURL(this.style.titleBgImg)+")";
x64.appendChild(this._f589);
if(this._f588)
x64.title=this._f588;
MVUtil._f164(x64,"pointer");
this.parent.toolBarContainer.appendChild(x64);
this.setIconImageListeners(true);
}
MVMapDecoration.prototype.scrollElement=function(x65)
{
var x66=parseInt(this._f592.style.height);
var x67=parseInt(this._f567.style.height);
var x68=parseInt(this._f592.style.width);
var x69=parseInt(this._f567.style.width);
var x70=MVUtil._f85(this._f592);
var x71=MVUtil._f84(this._f592);
if(x65<0)
{
if(x67>1)
{
if(x67>this._f580)
{
if(x70<this.parent._f110())
{
MVUtil._f74(this._f592,x71+this._f581,x70+this._f582);
this._f584=this.setTimeout("this.scrollElement(-1)",20);
}
else
 this.collapse();
}
else
 this.collapse();
}
else
 this.collapse();
}
if(x65>0)
{
x67+=this._f580;
x69+=this._f579;
x66+=this._f580;
x68+=this._f579;
x70-=this._f582;
x71-=this._f581;
var x72=false;
if(this._f416<x70)
MVUtil._f74(this._f592,x71,x70);
else
 x72=true;
if(x72)
{
this._f595=true;
clearTimeout(this._f584);
this._f584=null;
this.setIconImageListeners(false);
MVUtil._f74(this._f592,this._f415,this._f416);
this.collapsed=false;
this._f567.style.visibility="visible";
if(this._f567._f212&&this.parent)
{
var x73=this.parent.getCenter();
this.parent._f211._f207=true;
this.parent._f211._f202.setCenter(x73,true);
this.parent._f211._f202.display();
this.parent._f211._f218();
}
if(this.afterRestoreListener)
this.afterRestoreListener();
MVUtil.runListeners(this._f98,MVEvent.AFTER_RESTORE);
}
else
 {
this._f584=this.setTimeout("this.scrollElement(1)",20);
}
}
}
MVMapDecoration.prototype.setTimeout=function(_f149,_f150)
{
var Ie="tempVar"+_f264;
_f264++;
eval(Ie+" = this;");
var oi=_f149.replace(/\\/g,"\\\\");
oi=oi.replace(/\"/g,'\\"');
return window.setTimeout(Ie+'._setTimeoutDispatcher("'+oi+'");'+Ie+" = null;",_f150);
}
MVMapDecoration.prototype._f176=function()
{
var x74=this._f592;
if(!this._f568)
x74=this._f567;
this.left=(MVUtil._f84(x74)-this._f138)/this.parent._f109();
this.top=(MVUtil._f85(x74)-this._f139)/this.parent._f110();
this.parent._f597=null;
if(this._f577)
this._f577(this.getPosition());
MVUtil.runListeners(this._f98,MVEvent.DRAG,[this.getPosition()]);
}
MVMapDecoration.prototype._f74=function(x75,x76)
{
var x77=this._f592;
if(!this._f568)
x77=this._f567;
MVUtil._f74(x77,x75,x76);
}
function MVBarInfo(x0,x1)
{
this.name=x0;
this.color=null;
if(x1)
{
if(x1.charAt(0)=='#')
this.color=x1.substring(1,x1.length);
else
 this.color=x1;
}
this.color_opacity=255;
}
function MVBarChartStyle(x0,x1,x2,x3)
{
this.name=x0;
this.width=x1;
this.height=x2;
this.bars=x3;
this.minValue=null;
this.maxValue=null;
this.shareScale=true;
this.showXAxis=false;
}
MVBarChartStyle.prototype.setMinValue=function(x0)
{
this.minValue=x0;
}
MVBarChartStyle.prototype.setMaxValue=function(x1)
{
this.maxValue=x1;
}
MVBarChartStyle.prototype.setShareScale=function(x2)
{
this.shareScale=x2;
}
MVBarChartStyle.prototype.setShowXAxis=function(x3)
{
this.showXAxis=x3;
}
MVBarChartStyle.prototype.getXMLString=function()
{
var x4=
'<style name="'+this.name+'">'+
'<AdvancedStyle><BarChartStyle'+
' width="'+this.width+'" height="'+this.height+'"';
if(this.shareScale==true)
{
x4=x4+' share_scale="true"';
}
if(this.maxValue&&this.minValue)
{
x4=x4+' min_value="'+this.minValue+'" max_value="'+this.maxValue+'"';
}
if(this.showXAxis)
{
x4=x4+' show_x_axis="true"';
}
x4=x4+'>';
if(this.bars!=null)
{
for(var x5=0;x5<this.bars.length;x5++)
{
var x6=this.bars[x5];
x4=x4+' <Bar name="'+x6.name+'"';
if(x6.color!=null)
x4=x4+' color="#'+x6.color+'"';
x4=x4+' />';
}
}
x4+=' </BarChartStyle></AdvancedStyle></style>';
return x4;
}
function MVPieSlice(x0,x1)
{
this.name=x0;
this.color=null;
if(x1)
{
if(x1.charAt(0)=='#')
this.color=x1.substring(1,x1.length);
else
 this.color=x1;
}
this.color_opacity=255;
}
MVPieSlice.prototype.setColorOpacity=function(x0)
{
this.color_opacity=x0;
}
function MVPieChartStyle(x0,x1,x2)
{
this.name=x0;
this._f192=x1;
this.slices=x2;
}
MVPieChartStyle.prototype.getXMLString=function()
{
var x1='<style name="'+this.name+'">'+
'<AdvancedStyle><PieChartStyle '+
'pieradius="'+this._f192+'">';
if(this.slices!=null)
{
for(var x2=0;x2<this.slices.length;x2++)
{
var x3=this.slices[x2];
if(x3!=null)
{
x1=x1+' <PieSlice name="'+x3.name+'"';
if(x3.color!=null)
x1=x1+' color="#'+x3.color+'" />';
}
}
}
x1=x1+' </PieChartStyle></AdvancedStyle></style>';
return x1;
}
function MVCollectionBucket(x0,x1,x2,x3)
{
if(x2==null)
this.type="string";
else
 this.type=x2;
if(x3==null)
this.delimiter=",";
else
 this.delimiter=x3;
this.renderingStyle=x0;
this.label=x1;
this.items=null;
}
MVCollectionBucket.prototype.setItems=function(x0)
{
if(x0==null)
this.items=null;
this.items=x0;
}
MVCollectionBucket.prototype.getXMLString=function()
{
var x1=(this.label==null)?"":'label="'+this.label+'" ';
var x2="<CollectionBucket "+x1;
var x3=(this.type==null)?"":'type="'+this.type+'" ';
var x4=(this.delimiter==null)?"":'delimiter="'+this.delimiter+'" ';
var x5=(this.renderingStyle==null)?"":'style="'+this.renderingStyle+'" ';
x2+=x3+x4+x5+">"+this.items+"</CollectionBucket>";
return x2;
}
function MVScaleBar(x0,x1)
{
this.type="MVScaleBar";
this._f199=true;
this._f630=x1?x1:125;
this._f396=x0;
this._f566=false;
this.textSize=11;
this._f631=null;
this._f123=false;
this.style=
{barStyle:"border-top:1px solid #AAAAAA;border-bottom:1px solid #555555;border-left:1px solid #AAAAAA;border-right:1px solid #555555;background-color:#D0E0F0",
barHeight:3,textStyle:"font-family: Tahoma;font-size:11px;font-weight:bold"};
this.barSpacing=7;
}
MVScaleBar.prototype.setStyle=function(x0)
{
if(x0)
{
if(x0.barStyle)
this.style.barStyle=x0.barStyle;
if(x0.barHeight)
this.style.barHeight=x0.barHeight;
if(x0.textStyle)
this.style.textStyle=x0.textStyle;
}
}
MVScaleBar.prototype.init=function(x1,x2)
{
this.div=x1;
this._f632(x2);
}
MVScaleBar.prototype._f632=function(x3,x4,x5)
{
if(!x4)
x3._f634=this;
this._f631=x3;
if(!this.div)
{
this.div=document.createElement("div");
this.div.style.zIndex=2000;
if(x4)
x4.appendChild(this.div);
else
 x3._f215.appendChild(this.div);
}
this._f635=document.createElement("div");
this._f636=document.createElement("div");
MVUtil._f598(this.div);
MVUtil._f598(this._f635);
MVUtil._f598(this._f636);
this.div.style.height=MVUtil._f32(40);
this.div.style.width=MVUtil._f32(125);
this.div.style.fontSize=MVUtil._f32(5);
var x6=this;
var x7=function(x8)
{
var x9=x8.fpsLength;
var x10=x8.metricLength;
var x11=Math.max(x9,x10);
var x12=3;
x6._f637(x6._f635,x8.fpsText);
x6._f635.style.cssText=x6.style.textStyle;
x6._f635.style.position="absolute";
MVUtil._f638(x6._f635,4,0,x11,x12,10);
x6.textSize=MVUtil.getNumber(x6._f635.style.fontSize);
var x13=x12+x6.textSize;
x6.topBar=x6._f639(x9,x6.style.barHeight,3,x13,x6.style.barStyle);
x6.bottomBar=x6._f639(x10,x6.style.barHeight,3,x13+x6.style.barHeight+x6.barSpacing,x6.style.barStyle);
x6._f637(x6._f636,x8.metricText);
x6._f636.style.cssText=x6.style.textStyle;
x6._f636.style.position="absolute";
MVUtil._f638(x6._f636,4,x13+2*x6.style.barHeight+x6.barSpacing+2,x11,0,10);
x6.div.appendChild(x6._f635);
x6.div.appendChild(x6._f636);
x6.div.title=MVi18n._f340("scaleBarInfoTip");
x6._f640();
x6._f123=true;
if(x5)
x5(x6.div);
}
this._f641(x3,this._f630,x7);
}
MVScaleBar.prototype._f639=function(x14,x15,x16,x17,x18)
{
var x19=document.createElement("div");
x19.style.cssText=x18;
x19.style.position="absolute";
MVUtil._f74(x19,x16,x17);
x19.style.fontSize="1px";
x19.style.width=MVUtil._f32(x14);
x19.style.height=MVUtil._f32(x15);
this.div.appendChild(x19);
return x19;
}
MVScaleBar.prototype._f640=function()
{
if(!this.div)
return ;
var x20="black";
this._f635.style.color=x20;
this._f636.style.color=x20;
}
MVScaleBar.prototype._f642=function(x21)
{
if(!this.div||!this._f123)
return ;
var x22=this;
var x23=function(x24)
{
var x25=Math.max(x24.fpsLength,x24.metricLength);
x22._f637(x22._f635,x24.fpsText);
x22._f637(x22._f636,x24.metricText);
x22.width=x25+4;
x22.topBar.style.width=MVUtil._f32(x24.fpsLength);
x22.bottomBar.style.width=MVUtil._f32(x24.metricLength);
x22._f636.style.width=MVUtil._f32(x24.metricLength+2);
x22._f635.style.width=MVUtil._f32(x24.fpsLength+2);
}
this._f641(x21,this._f630,x23);
}
MVScaleBar.prototype._f641=function(x26,x27,x28)
{
var x29;
var x30;
var x31;
var x32;
var x33;
var x34;
var x35=x26.msi._f521();
var x36=x26.msi._f520();
var x37=this;
var x38=function()
{
x32=x37._f643(x31);
if(x32>=1000)
{
var x39=x37._f643(x31/1000);
var x40=x31/1000;
x34=Math.round(x37._f630*x39/x40);
x33=MVUtil.formatNum(x39)+" "+MVi18n._f340("kilometers");
}
else
 {
x34=Math.round(x37._f630*x32/x31);
x33=MVUtil.formatNum(x32)+" "+MVi18n._f340("meters");
}
var x41=x31/1609.344;
var x42=x31*3.28084;
var x43;
var x44;
if(x41>=1)
{
var x45=x37._f643(x41);
x43=Math.round(x37._f630*x45/x41);
x44=MVUtil.formatNum(x45)+" "+MVi18n._f340("miles");
}
else
 {
var x46=x37._f643(x42);
x43=Math.round(x37._f630*x46/x42);
x44=MVUtil.formatNum(x46)+" "+MVi18n._f340("feet");
}
return new _f644(x43,x34,x44,x33);
}
if(x36=='GEODETIC')
{
var x47=function(x48)
{
x30=x48;
var x49=6378137.0;
var x50=0.9966471893352525;
var x51=0.0033528106647474805;
x31=MVScaleBar._f645(x29,x30,x49,x50,x51);
x28(x38());
}
var x52=function(x53)
{
x29=x53;
MVScaleBar._f646(x26._f647+x27/2,x26._f648,x26,x47);
}
MVScaleBar._f646(x26._f647-x27/2,x26._f648,x26,x52);
return ;
}
else if(x36=='PROJECTED')
{
if(this._f396==true)
{
var x54=x27/2;
var x55=x26._f95-x54/x26._f76;
var x56=x54/x26._f76+x26._f95;
var x57=x26._f96;
var x58=MVSdoGeometry.createLineString([x55,x57,x56,x57],x26.srid);
var x59=function(x60)
{
x31=x60;
x28(x38());
}
x31=x58.getLength("meter",true,x26._f4+'/foi',x59);
return ;
}
else
 {
x31=x35*x27/x26._f76;
}
}
else if(x36=='LOCAL'||(x36=='UNDEFINED'&&x35!=0))
{
x31=x35*x27/x26._f76;
}
else if(x36=='UNDEFINED'&&x35==0)
{
x31=x27/x26._f76;
_f11._f649='';
_f11._f650='';
_f11._f651='';
_f11._f652='';
}
x28(x38());
}
MVScaleBar.prototype._f643=function(x61)
{
var x62=x61;
if(x62>1)
{
var x63=0;
while(x62>=10){x62=x62/10;x63=x63+1;}
if(x62>=5){x62=5;}
else if(x62>=2){x62=2;}
else {x62=1;}
while(x63>0){x62=x62*10;x63=x63-1;}
}
return x62;
}
MVScaleBar.prototype._f637=function(x64,x65)
{
if(x64.innerHTML!=x65)
{
x64.innerHTML=x65;
}
}
MVScaleBar.prototype._f219=function()
{
this._f631._f634=null;
}
MVScaleBar._f646=function(x66,x67,x68,x69)
{
var x70=MVSdoGeometry.createPoint(0,0,x68.srid);
x70.sdo_point.x=(x66-x68._f647)/x68._f76+x68._f95;
x70.sdo_point.y=x68._f96-(x67-x68._f648)/x68._f78;
var x71=function(x72)
{
if(x72)
x70=x72;
while(x70.sdo_point.x<-180)x70.sdo_point.x+=360;
while(x70.sdo_point.x>180)x70.sdo_point.x-=360;
x70.sdo_point.x=(x70.sdo_point.x)*Math.PI/180;
x70.sdo_point.y=(x70.sdo_point.y)*Math.PI/180;
x69(x70.sdo_point);
}
if(x70.srid&&x70.srid!=8307)
x70=x68.transformGeom(x70,8307,null,x71);
else
 x71();
}
MVScaleBar._f645=function(x73,x74,x75,x76,x77)
{
var x78;var x79;var x80;var x81;var x82;var x83;var x84;var x85;var x86;var x87;
var x88;var x89;var x90;var x91;var x92;
var x93;var x94;var x95;var x96;var x97;var x98;
var x99=Math.PI/2.0;
if(x99-Math.abs(x73.y)<1.e-10)
x91=x73.y;
else
 x91=Math.atan(x76*Math.tan(x73.y));
if(x99-Math.abs(x74.y)<1.e-10)
x92=x74.y;
else
 x92=Math.atan(x76*Math.tan(x74.y));
x79=(x91+x92)/2.0;
x80=(x91-x92)/2.0;
x78=Math.abs(x73.x-x74.x);
if(x78>Math.PI)
x78=2.0*Math.PI-x78;
x78/=2.0;
if(x78<5.e-8&&Math.abs(x80)<5.e-8)
return 0.0;
else if((x99-x78<5.e-8&&Math.abs(x79)<5.e-8)||
x99-Math.abs(x80)<5.e-8)
return -1.0;
x93=Math.sin(x79);
x94=Math.cos(x79);
x95=Math.sin(x80);
x96=Math.cos(x80);
x97=Math.sin(x78);
x81=x95*x95+x97*x97*(x96*x96-x93*x93);
x82=Math.acos(1.0-2.0*x81);
x98=Math.sin(x82);
x83=2.0*(1.0-2.0*x81);
x84=2.0*x93*x93*x96*x96/(1.0-x81);
x85=2.0*x95*x95*x94*x94/x81;
x86=x84+x85;
x87=x84-x85;
x88=x82/x98;
x89=4.0*x88*x88;
x90=x89*x83;
var x100=(x90+(x88-(x90-x83)/2.0)*x86);
return x75*x98*(x88-x77*(x88*x86-x87)/4.0+x77*x77*
(x86*x100-x87*(2.0*x89+x83*x87)+x89*x86*x87)/64.0);
}
function _f644(x0,x1,x2,x3)
{
this.fpsLength=x0;
this.metricLength=x1;
this.fpsText=x2;
this.metricText=x3;
}
function MVNavigationPanel(x0,x1,x2,x3)
{
this.type="MVNavigationPanel";
this._f199=true;
if(x0==undefined||
x0!=0&&x0!=4&&x0!=8)
x0=8;
this._f855=x0;
this._f856=x1;
this._f857=x2;
this.zoomLevelBars=x3;
this._f858=null;
this._f846=null;
this._f631=null;
this.containerDiv=null;
this._f123=false;
this.infoTipDivs=new Object();
this.infoTipTexts=null;
this.infoTipTextStyle=null;
var x4=this;
this.hideInfoTimeOut;
this.enableZoomLevelInfoTip=true;
this.showInfoTip=function()
{
if(!x4.enableZoomLevelInfoTip)
return;
clearTimeout(x4.hideInfoTimeOut);
for(div in x4.infoTipDivs){
x4.infoTipDivs[div].style.visibility="";
}
}
this._f188=function()
{
x4.hideInfoTimeOut=setTimeout(x4.doHideInfoTip,500);
}
this.doHideInfoTip=function()
{
for(div in x4.infoTipDivs)
{
x4.infoTipDivs[div].style.visibility="hidden";
}
}
}
MVNavigationPanel.prototype.setZoomLevelInfoTips=function(x0,x1)
{
this.infoTipTexts=x0;
this.infoTipTextStyle=x1;
if(this._f859){
MVUtil._f191(this._f859.div,this._f631._f7);
this._f859=new MVZoomBarAndPin(this);
}
}
MVNavigationPanel.prototype.enableZoomLevelInfoTips=function(x2)
{
this.enableZoomLevelInfoTip=x2;
}
MVNavigationPanel.prototype.setNavPosition=function(x3)
{
this._f846=x3;
}
MVNavigationPanel.prototype.init=function(x4,x5)
{
if(x4)
this.containerDiv=x4;
if(this._f858)
{
if(this._f858.parentNode)
this._f858.parentNode.removeChild(this._f858);
MVUtil._f191(this._f858,x5._f7);
}
this._f858=document.createElement('div');
this._f858.style.position='absolute';
this._f858.className="noprint";
this._f858.ondblclick=MVUtil._f106(this._f858,function(x6)
{
MVUtil._f173(MVUtil.getEvent(x6));
});
this._f858.onclick=MVUtil._f106(this._f858,function(x7)
{
MVUtil._f173(MVUtil.getEvent(x7));
});
this.containerDiv.appendChild(this._f858);
if(x5)
{
this._f631=x5;
x5.navigationPanel=this;
}
if(this._f846)
{
if(this._f846.toLowerCase()=="east")
{
if(this._f855==8)
this._f858.style.left=MVUtil._f32(this._f631._f109()-80);
else if(this._f855==4)
this._f858.style.left=MVUtil._f32(this._f631._f109()-80);
else if(this._f855==0)
this._f858.style.left=MVUtil._f32(this._f631._f109()-50);
}
else
 {
if(this._f855==8)
this._f858.style.left=MVUtil._f32(10);
else if(this._f855==8)
this._f858.style.left=MVUtil._f32(10);
else if(this._f855==0)
this._f858.style.left=MVUtil._f32(30);
}
this._f858.style.top=MVUtil._f32(10);
}
else
 {
this._f858.style.left=MVUtil._f32(0);
this._f858.style.top=MVUtil._f32(0);
}
this._f858.style.zIndex=2000;
MVUtil._f164(this._f858,"pointer");
this._f547=0;
this._f548=0;
var x5=this._f631;
var x8=function(x9)
{
if(x5._f709&&x5._f713)
{
x5._f710.clear();
x5.marqueeZoom();
}
x9=(x9)?x9:((window.event)?event:null);
MVUtil._f173(x9);
}
if(this._f855!=false)
{
this._f860();
}
this._f543=0;
this._f544=0;
if(this._f856!=false)
{
this._f543=_f11._f543;
this._f544=_f11._f544;
}
this._f551=0;
this._f552=0;
if(this._f857!=false)
{
this._f551=_f11._f551;
this._f552=_f11._f552;
var x10=new _f845(this._f858,src=_f11._f142+"navicons/zoomin.gif",
this._f551,this._f552,2000,MVi18n._f340("zoomInInfoTip"));
var x11=this._f855>0?(this._f547*3-this._f551)/2+1:0;
var x12=this._f855>0?this._f548*3+3:0;
MVUtil._f74(x10,x11,x12);
x10.onmousedown=MVUtil._f106(x10,function(x13)
{
x13=(x13)?x13:((window.event)?event:null);
MVUtil._f173(x13);
if(x5._f709&&x5._f711)
x5._f713=true;
x5.zoomIn();
});
x10.onmouseup=MVUtil._f106(x10,function(x14)
{
x8(x14);
});
x10.onmouseover=this.showInfoTip;
x10.onmouseout=this._f188;
var x15=new _f845(this._f858,_f11._f142+"navicons/zoomout.gif",
this._f551,this._f552,2000,MVi18n._f340("zoomOutInfoTip"));
MVUtil._f74(x15,x11,x12+this._f552+this._f544+1);
x15.onmousedown=MVUtil._f106(x15,function(x16)
{
x16=(x16)?x16:((window.event)?event:null);
MVUtil._f173(x16);
if(x5._f709&&x5._f711)
x5._f713=true;
x5.zoomOut();
});
x15.onmouseup=MVUtil._f106(x15,function(x17)
{
x8(x17);
});
x15.onmouseover=this.showInfoTip;
x15.onmouseout=this._f188;
}
if(this._f856!=false)
{
this._f859=new MVZoomBarAndPin(this);
}
this._f123=true;
}
MVNavigationPanel.prototype._f860=function()
{
this._f547=_f11._f547;
this._f548=_f11._f548;
var x18=this._f631;
var x19=function(x20)
{
if(x18._f709&&x18._f713)
{
x18._f710.clear();
x18.marqueeZoom();
}
x20=(x20)?x20:((window.event)?event:null);
MVUtil._f173(x20);
}
var x21=new _f845(this._f858,_f11._f142+"navicons/north.gif",
this._f547,this._f548,2000,MVi18n._f340("panNInfoTip"));
MVUtil._f74(x21,this._f547+1,0);
x21.onmousedown=MVUtil._f106(x21,function(x22)
{
if(x18._f709&&x18._f711)
x18._f713=true;
x18.smoothScroll(0,(x18._f109()+x18._f110())*(-0.25));
x22=(x22)?x22:((window.event)?event:null);
MVUtil._f173(x22);
});
x21.onmouseup=MVUtil._f106(x21,function(x23)
{
x19(x23);
});
var x24=new _f845(this._f858,_f11._f142+"navicons/west.gif",
this._f547,this._f548,2000,MVi18n._f340("panWInfoTip"));
MVUtil._f74(x24,0,this._f548+1);
x24.onmousedown=MVUtil._f106(x24,function(x25)
{
if(x18._f709&&x18._f711)
x18._f713=true;
x18.smoothScroll((x18._f109()+x18._f110())*(-0.25),0);
x25=(x25)?x25:((window.event)?event:null);
MVUtil._f173(x25);
});
x24.onmouseup=MVUtil._f106(x24,function(x26)
{
x19(x26);
});
var x27=new _f845(this._f858,_f11._f142+"navicons/east.gif",
this._f547,this._f548,2000,MVi18n._f340("panEInfoTip"));
MVUtil._f74(x27,this._f547*2+2,this._f548+1);
x27.onmousedown=MVUtil._f106(x27,function(x28)
{
if(x18._f709&&x18._f711)
x18._f713=true;
x18.smoothScroll((x18._f109()+x18._f110())*(0.25),0);
x28=(x28)?x28:((window.event)?event:null);
MVUtil._f173(x28);
});
x27.onmouseup=MVUtil._f106(x27,function(x29)
{
x19(x29);
});
var x30=new _f845(this._f858,_f11._f142+"navicons/south.gif",
this._f547,this._f548,2000,MVi18n._f340("panSInfoTip"));
MVUtil._f74(x30,this._f547+1,this._f548*2+2);
x30.onmousedown=MVUtil._f106(x30,function(x31)
{
if(x18._f709&&x18._f711)
x18._f713=true;
x18.smoothScroll(0,(x18._f109()+x18._f110())*(0.25));
x31=(x31)?x31:((window.event)?event:null);
MVUtil._f173(x31);
});
x30.onmouseup=MVUtil._f106(x30,function(x32)
{
x19(x32);
});
if(this._f855==4)
return ;
var x33=new _f845(this._f858,_f11._f142+"navicons/nw.gif",
this._f547,this._f548,200,MVi18n._f340("panNWInfoTip"));
MVUtil._f74(x33,0,0);
x33.onmousedown=MVUtil._f106(x33,function(x34)
{
if(x18._f709&&x18._f711)
x18._f713=true;
x18.smoothScroll((x18._f109()+x18._f110())*(-0.18),(x18._f109()+x18._f110())*(-0.18));
x34=(x34)?x34:((window.event)?event:null);
MVUtil._f173(x34);
});
x33.onmouseup=MVUtil._f106(x33,function(x35)
{
x19(x35);
});
var x36=new _f845(this._f858,_f11._f142+"navicons/ne.gif",
this._f547,this._f548,2000,MVi18n._f340("panNEInfoTip"));
MVUtil._f74(x36,this._f547*2+2,0);
x36.onmousedown=MVUtil._f106(x36,function(x37)
{
if(x18._f709&&x18._f711)
x18._f713=true;
x18.smoothScroll((x18._f109()+x18._f110())*(0.18),(x18._f109()+x18._f110())*(-0.18));
x37=(x37)?x37:((window.event)?event:null);
MVUtil._f173(x37);
});
x36.onmouseup=MVUtil._f106(x36,function(x38)
{
x19(x38);
});
var x39=new _f845(this._f858,_f11._f142+"navicons/sw.gif",
this._f547,this._f548,2000,MVi18n._f340("panSWInfoTip"));
MVUtil._f74(x39,0,this._f548*2+2);
x39.onmousedown=MVUtil._f106(x39,function(x40)
{
if(x18._f709&&x18._f711)
x18._f713=true;
x18.smoothScroll((x18._f109()+x18._f110())*(-0.18),(x18._f109()+x18._f110())*(0.18));
x40=(x40)?x40:((window.event)?event:null);
MVUtil._f173(x40);
});
x39.onmouseup=MVUtil._f106(x39,function(x41)
{
x19(x41);
});
var x42=new _f845(this._f858,_f11._f142+"navicons/se.gif",
this._f547,this._f548,2000,MVi18n._f340("panSEInfoTip"));
MVUtil._f74(x42,this._f547*2+2,this._f548*2+2);
x42.onmousedown=MVUtil._f106(x42,function(x43)
{
if(x18._f709&&x18._f711)
x18._f713=true;
x18.smoothScroll((x18._f109()+x18._f110())*(0.18),(x18._f109()+x18._f110())*(0.18));
x43=(x43)?x43:((window.event)?event:null);
MVUtil._f173(x43);
});
x42.onmouseup=MVUtil._f106(x42,function(x44)
{
x19(x44);
});
if(x18._f705){
var x45=new _f845(this._f858,_f11._f142+"navicons/center.gif",
this._f547,this._f548,2000,MVi18n._f340("panCenterInfoTip"));
MVUtil._f74(x45,this._f547+1,this._f548+1);
x45.onmousedown=MVUtil._f106(x45,function(x46)
{
if(x18._f709&&x18._f711)
x18._f713=true;
x18.setCenterAndZoomLevel(x18._f705,x18._f706);
x46=(x46)?x46:((window.event)?event:null);
MVUtil._f173(x46);
});
x45.onmouseup=MVUtil._f106(x45,function(x47)
{
x19(x47);
});
}
}
MVNavigationPanel.prototype.updateSliderBar=function()
{
if(!this._f123)
return ;
if(this._f859&&this._f859._f861)
this._f859._f861.style.top=
MVUtil._f32(this._f548*3+3+this._f552+
(this._f544-_f11._f546)*
(this._f631._f702-this._f631._f21)/this._f631._f702);
}
MVNavigationPanel.prototype._f219=function()
{
this._f631.navigationPanel=null;
}
MVNavigationPanel.prototype.getMaxZoomLevel=function()
{
if(this._f859.maxZoomlevel)
return this._f859.maxZoomleve;
else
 return this.parent._f702;
}
MVNavigationPanel.prototype.updateSlider=function()
{
if(this._f859){
MVUtil._f191(this._f859.div,this._f631._f7);
this._f859=new MVZoomBarAndPin(this);
}
}
function MVZoomBarAndPin(x0)
{
var x1=x0;
this.div=document.createElement("div");
var x2=x1._f855>0?(x1._f547*3-x1._f543)/2+1:0;
var x3=x1._f855>0?x1._f548*3+3+x1._f552:x1._f552;
this._f862=new _f845(this.div,_f11._f142+"navicons/zoombar.gif",
x1._f543,x1._f544,2000,MVi18n._f340("sliderBarInfoTip"));
this._f862.onmouseover=x0.showInfoTip;
this._f862.onmouseout=x0._f188;
MVUtil._f74(this._f862,x2,x3);
this.maxZoomlevel=x1._f631._f702;
for(var x4=0;x4<=x1._f631._f702;x4++)
{
var x5=x1._f543-4;
if(x0.zoomLevelBars!=false)
{
var x6=new _f845(this.div,_f11._f142+"navicons/zoomBarHoriz.gif",x5,10,1999);
MVUtil._f74(x6,x2+2,x3+2+x4*(x1._f544-14)/x1._f631._f702);
}
for(tipLevel in x0.infoTipTexts)
{
if(parseInt(tipLevel)==(x1._f631._f702-x4))
{
this.infoDiv=document.createElement("div");
this.infoDiv.style.height=16;
this.infoDiv.style.position="absolute";
this.infoDiv.style.opacity=0.6;
this.infoDiv.style.filter="alpha(opacity = 60)";
this.infoDivTable=document.createElement("Table");
this.infoDivTable.cellSpacing=0;
this.infoDivTable.cellPadding=0;
this.infoDivTable.style.border=0;
this.infoDivRow=this.infoDivTable.insertRow(0);
this.infoDivLeft=this.infoDivRow.insertCell(0);
this.infoDivCenter=this.infoDivRow.insertCell(1);
this.infoDivCenter.style.background="url("+_f11._f142+"navicons/tip_center.gif"+") repeat-x";
this.infoDivRight=this.infoDivRow.insertCell(2);
this.infoDivCenter.innerHTML=x0.infoTipTexts[tipLevel];
this.infoDivCenter.style.fontSize="12px";
this.infoDivCenter.style.fontWeight="bold";
this.infoDivCenter.style.fontFamily="Verdana,Tahoma";
this.infoDivCenter.style.cssText=this.infoDivCenter.style.cssText+"; "+x0.infoTipTextStyle;
this.infoDiv.appendChild(this.infoDivTable);
if(x1._f846&&x1._f846.toLowerCase()=="east")
{
this.infoDivLeft.innerHTML='<img border="0" src="'+_f11._f142+'navicons/tip_east_end.gif"/>';
this.infoDivRight.innerHTML='<img border="0" src="'+_f11._f142+'navicons/tip_east_start.gif"/>';
this.infoDivCenter.style.textAlign="left";
this.infoDivCenter.style.align="left";
this.infoDivCenter.style.direction="RTL"
if(_f11._f360==2)
{
this.infoDiv.style.pixelRight=x2-22;
this.infoDiv.style.pixelTop=x3+x4*(x1._f544-16)/x1._f631._f702;
}
else
 {
this.infoDiv.style.right=MVUtil._f32(x2-22);
this.infoDiv.style.top=MVUtil._f32(x3+x4*(x1._f544-16)/x1._f631._f702);
}
}
else
 {
this.infoDivLeft.innerHTML='<img border="0" src="'+_f11._f142+'navicons/tip_start.gif"/>';
this.infoDivRight.innerHTML='<img border="0" src="'+_f11._f142+'navicons/tip_end.gif"/>';
this.infoDivCenter.style.textAlign="right";
this.infoDivCenter.style.align="right";
MVUtil._f74(this.infoDiv,x2+16,x3+x4*(x1._f544-16)/x1._f631._f702);
}
this.infoDiv.style.visibility="hidden";
this.infoDiv._f21=x1._f631._f702-x4;
this.div.appendChild(this.infoDiv);
x0.infoTipDivs["div"+x4]=this.infoDiv;
var x7=function(x8)
{
clearTimeout(x0.hideInfoTimeOut);
this.style.opacity=0.9;
this.style.filter="alpha(opacity = 90)";
}
var x9=function(x10)
{
this.style.opacity=0.6;
this.style.filter="alpha(opacity = 60)";
x0.hideInfoTimeOut=setTimeout(x0.doHideInfoTip,500);
}
var x11=function(x12)
{
x0._f631.zoomTo(this._f21)
x12=(x12)?x12:((window.event)?event:null);
MVUtil._f173(x12);
}
this.infoDiv.onmouseover=MVUtil._f106(this.infoDiv,x7);
this.infoDiv.onmouseout=MVUtil._f106(this.infoDiv,x9);
this.infoDiv.onmousedown=MVUtil._f106(this.infoDiv,x11);
}
}
}
this._f861=new _f845(this.div,_f11._f142+"navicons/slider.gif",_f11._f545,
_f11._f546,2002);
this._f861.onmouseover=x0.showInfoTip;
this._f861.onmouseout=x0._f188;
MVUtil._f74(this._f861,x2,x3+
(x1._f544-_f11._f546)*
(x1._f631._f702-x1._f631._f21)/x1._f631._f702);
x1._f858.appendChild(this.div);
this._f863=Math.floor(_f11._f546/2);
var x13=0;
var x14=0;
var x15=function(x16)
{
x1._f631._f693=false;
x16=(x16)?x16:((window.event)?event:null);
var x17=x1._f631._f220(x16);
var x18=x17.top-x1._f859._f863;var x19=x1._f548*3+3+x1._f552;
if(x18<=x19)
x18=x19;
else if(x18>=x19+x1._f544-_f11._f546)
x18=x19+x1._f544-_f11._f546;
var x20=(x1._f544-_f11._f546);
x20=x20/x1._f631._f702;
var x21=Math.floor((x18-x19)/x20);
x18=x21*x20+x19;
var x22=x1._f631._f702-x21;
x1._f631.zoomTo(x22);
MVUtil._f173(x16);
if(x1._f631._f709&&x1._f631._f711)
{
x1._f631._f713=true;
x1._f631._f710.clear();
}
x1._f859._f861.style.top=MVUtil._f32(x18);
}
var x23=function(x24)
{
x24=(x24)?x24:((window.event)?event:null);
x1._f631._f693=false;
if(!x1._f859._f864)return;
var x25=x1._f631._f220(x24);
var x26=x25.top-x13+x14;
if(x26<=x1._f548*3+3+x1._f552)
x26=x1._f548*3+3+x1._f552;
else if(x26>=x1._f548*3+3+x1._f552+
x1._f544-_f11._f546)
x26=x1._f548*3+3+x1._f552+
x1._f544-_f11._f546;
x1._f859._f861.style.top=MVUtil._f32(x26);
MVUtil._f173(x24);
}
var x27=function(x28)
{
if(!x1._f859._f864)
return ;
x28=(x28)?x28:((window.event)?event:null);
x1._f631._f693=true;
x1._f859._f864=false;
var x29=x1._f631._f220(x28);
var x30=MVUtil._f85(x1._f859._f861);
var x31=x1._f548*3+3+x1._f552;
var x32=(x1._f544-_f11._f546);
x32=x32/x1._f631._f702;
var x33=Math.round((x30-x31)/x32);
x30=x33*x32+x31;
var x34=x1._f631._f702-x33;
x1._f631.zoomTo(x34);
x1._f859._f861.style.top=MVUtil._f32(x30);
x1._f631.zoomTo(x34);
MVUtil._f173(x28);
if(x1._f631._f709&&x1._f631._f713)
x1._f631.marqueeZoom();
}
var x35=function(x36)
{
x36=(x36)?x36:((window.event)?event:null);
x1._f631._f693=true;
if(x1._f859._f864)
{
x27(x36);
}
return false;
}
var x37=function(x38)
{
x38=(x38)?x38:((window.event)?event:null);
var x39=x1._f631._f220(x38);
x13=x39.top;
x14=MVUtil._f85(x1._f859._f861);
x1._f859._f864=true;
MVUtil._f173(x38);
}
this.div.onmousedown=MVUtil._f106(this.div,x15);
this.div.onmouseup=MVUtil._f106(this.div,x27);
this.div.onmousemove=MVUtil._f106(this.div,x23);
this.div.onmouseout=MVUtil._f106(this.div,x35);
this._f861.onmousedown=MVUtil._f106(this._f861,x37);
this._f864=false;
}
function _f398()
{
}
_f398._f399=[
{srid:54004,params:[0,0,1,0,0]},
{srid:8307,params:[6378137,298.257223563]}
];
_f398._f400=function(x0,x1,x2,x3,x4)
{
var x5=_f398._f403(x3);
var x6=_f398._f403(x2);
if(!x5||!x6)
{
MVi18n._f6("MVCSTransformMercator.geodeticToMercator","MAPVIEWER-05522",x2+"->"+x3,x4);
return null;
}
var x7=_f361._f364({x:x0,y:x1});
x0=x7.x;
x1=x7.y;
var x8=x5[3];
var x9=x5[4];
var x10=x5[2];
var x11=x5[1];
var x12=x6[0];
var x13=1/x6[1];
var x14=Math.sqrt(2*x13-x13*x13);
var x15=x8+x12*x10*(x0-x11);
var x16=Math.sin(x1);
var x17=Math.tan(Math.PI/4+x1/2);
var x18=(1-x14*x16)/(1+x14*x16);
var x19=x14/2;
var x20=Math.pow(x18,x19);
var x21=x9+x12*x10*Math.log(x17*x20);
return {x:x15,y:x21};
}
_f398._f404=function(x22,x23,x24,x25,x26)
{
var x27=_f398._f403(x24);
var x28=_f398._f403(x25);
if(!x27||!x28)
{
MVi18n._f6("MVCSTransformMercator.mercatorToGeodetic","MAPVIEWER-05522",x24+"->"+x25,x26);
return null;
}
var x29=x27[3];
var x30=x27[4];
var x31=x27[2];
var x32=x27[1];
var x33=x28[0];
var x34=1/x28[1];
var x35=Math.sqrt(2*x34-x34*x34);
var x36=x23;
var x37=x22;
var x38=Math.E;
var x39=Math.pow(x38,((x30-x36)/(x33*x31)));
var x40=Math.PI/2-2*Math.atan2(x39,1.0);
var x41=Math.pow(x35,4.0);
var x42=Math.pow(x35,6.0);
var x43=Math.pow(x35,8.0);
var x44=x35*x35/2;
var x45=5*x41/24;
var x46=x42/12;
var x47=13*x43/360;
var x48=Math.sin(2*x40);
var x49=7*x41/48;
var x50=29*x42/240;
var x51=811*x43/11520;
var x52=Math.sin(2*x40);
var x53=7*x42/120;
var x54=81*x43/1120;
var x55=Math.sin(6*x40);
var x56=4279*x43/161280;
var x57=Math.sin(8*x40);
var x58=x40+(x44+x45+x46+x47)*x48+(x49+x50+x51)*x52+(x53+x54)*x55+x56*x57;
var x59=((x37-x29)/(x33*x31))+x32;
return _f361._f365({x:x59,y:x58});
}
_f398._f403=function(x60)
{
for(var x61=0;x61<_f398._f399.length;x61++)
{
if(_f398._f399[x61].srid==x60)
return _f398._f399[x61].params;
}
return null;
}
_f11._f405(54004,8307,function(x62,x63,x64){
return _f398._f404(x62,x63,54004,8307,x64);
});
_f11._f405(8307,54004,function(x65,x66,x67){
return _f398._f400(x65,x66,8307,54004,x67);
});
function MVZoomControl(x0,x1)
{
this.mapview=x0;
this.ldiff=0;
this.tdiff=0;
this.movedLeft=0;
this.movedTop=0;
this._f426=x1;
this._f420=null;
this.tileLayerDiv=null;
this.zoomStep=0.1
this.interval=40;
this.zooming=false;
this.oldTilesLoaded=false;
this.timeouts=new Array();
}
MVZoomControl.prototype.computeTilesOffset=function()
{
var x0=this._f426._f420[0][0];
this.ldiff=-x0.parentNode.offsetLeft
this.tdiff=-x0.parentNode.offsetTop;
this.movedLeft=-x0.parentNode.parentNode.offsetLeft;
this.movedTop=-x0.parentNode.parentNode.offsetTop;
}
MVZoomControl.prototype.getScreenCor=function(x1,x2,x3)
{
var x4=this.getPPXByZoomLevel(x3);
var x5=this.getPPYByZoomLevel(x3);
var x6=x1-this.mapview._f109()/2;
var x7=x2-this.mapview._f110()/2;
return {x:x6/x4+this.mapview._f95,
y:this.mapview._f96-x7/x5};
}
MVZoomControl.prototype._f157=function(x8,x9,x10)
{
var x11=this.getPPXByZoomLevel(x10);
var x12=this.getPPYByZoomLevel(x10);
return {x:(x8-this.mapview._f95)*x11+this.mapview._f109()/2,
y:(this.mapview._f96-x9)*x12+this.mapview._f110()/2};
}
MVZoomControl.prototype.getPPXByZoomLevel=function(x13)
{
var x14=this.mapview.msi.mapConfig.zoomLevels[x13].tileImageWidth;
var x15=this.mapview.msi.mapConfig.zoomLevels[x13].tileWidth;
var x16=x14/x15;
return x16;
}
MVZoomControl.prototype.getPPYByZoomLevel=function(x17){
var x18=this.mapview.msi.mapConfig.zoomLevels[x17].tileImageHeight;
var x19=this.mapview.msi.mapConfig.zoomLevels[x17].tileHeight;
var x20=x18/x19;
return x20;
}
MVZoomControl.prototype.initZoomInfo=function(x21,x22,x23)
{
var x24=this.getPPXByZoomLevel(x21);
var x25=this.getPPYByZoomLevel(x21);
var x26=this.getPPXByZoomLevel(x22);
var x27=this.getPPYByZoomLevel(x22);
var x28=this.mapview.msi.mapConfig.zoomLevels[x21].tileImageWidth;
var x29=this.mapview.msi.mapConfig.zoomLevels[x21].tileImageWidth;
var x30=this.mapview.msi.mapConfig.zoomLevels[x21].tileImageWidth*x26/x24;
var x31=this.mapview.msi.mapConfig.zoomLevels[x21].tileImageHeight*x27/x25;
var x32=MVUtil._f84(x23)-this.movedLeft;
var x33=MVUtil._f85(x23)-this.movedTop;
var x34=this.getScreenCor(x32-this.ldiff+this.movedLeft,x33-this.tdiff+this.movedTop,x21);
var x35=this._f157(x34.x,x34.y,x22);
var x36=x35.x+this.ldiff-this.movedLeft*x26/x24;
var x37=x35.y+this.tdiff-this.movedTop*x26/x24;
x23.oldZoomInfo=new Object();
x23.oldZoomInfo.lstart=x32;
x23.oldZoomInfo.tstart=x33;
x23.oldZoomInfo.lend=x36;
x23.oldZoomInfo.tend=x37;
x23.oldZoomInfo.fw=x28;
x23.oldZoomInfo.fh=x29;
x23.oldZoomInfo.tiw=x30;
x23.oldZoomInfo.tih=x31;
}
MVZoomControl.prototype.zoomToFactor=function(x38,x39)
{
if(x39>1)
{
return;
}
var x40=x38.oldZoomInfo;
var x41=(x40.lend-x40.lstart)*x39+x40.lstart;
var x42=(x40.tend-x40.tstart)*x39+x40.tstart;
var x43=(x40.tiw-x40.fw)*x39+x40.fw;
var x44=(x40.tih-x40.fh)*x39+x40.fh;
MVUtil._f74(x38,x41,x42);
x38.style.width=(x43+1)+'px';
x38.style.height=(x44+1)+'px';
}
MVZoomControl.prototype.copyTiles=function()
{
if(this.tileLayerDiv&&this.tileLayerDiv.parentNode)
{
this.tileLayerDiv.parentNode.removeChild(this.tileLayerDiv);
MVUtil._f92(this.tileLayerDiv);
}
this.tileLayerDiv=this._f426._f426;
this.tileLayerDiv.zooming=true;
this._f420=this._f426._f420;
this.tileLayerDiv.style.zIndex=200+this._f426.zIndex;
return;
}
MVZoomControl.prototype.zoomToNewCenter=function(x45,x46,x47)
{
if((x45)&&(x46)&&(x45.getPointX()!=x46.getPointX()||x45.getPointY()!=x46.getPointY()))
{
var x48=this._f157(x45.getPointX(),x45.getPointY(),x47);
var x49=this._f157(x46.getPointX(),x46.getPointY(),x47);
var x50=x49.x-x48.x;
var x51=x49.y-x48.y;
var x52=this.mapview.div;
if(_f11._f360==2)
{
x52.style.pixelLeft=x52.style.pixelLeft-x50;
x52.style.pixelTop=x52.style.pixelTop-x51;
}
else
 {
x52.style.left=(MVUtil._f84(x52)-x50)+'px';
x52.style.top=(MVUtil._f85(x52)-x51)+'px';
}
}
}
MVZoomControl.prototype.clearLastZoom=function(){
for(var x53=0;x53<this._f426._f420.length;x53++)
{
for(var x54=0;x54<this._f426._f420[x53].length;x54++)
{
if(!this.hasTileLoaded(this._f426._f420[x53][x54]))
{
var x55=_f11._f142+"transparent."+(_f11._f70=="IF"?"gif":"png");
this._f426._f420[x53][x54].src=x55;
this._f426._f420[x53][x54].imgLoadFinished=true;
}
}
}
for(var x53=0;x53<this.timeouts.length;x53++)
{
clearTimeout(this.timeouts[x53]);
}
this.timeouts=new Array();
}
MVZoomControl.prototype.showTiles=function(x56,x57,x58,x59)
{
if(!this._f426.isVisible()||this._f426._f420==''||Math.abs(x56-x57)>1||this._f426._f412)
{
this.reset();
return;
}
this._f426.setVisible(false);
this._f426._f426.style.visibility="visible";
this.zooming=true;
this.zoomToNewCenter(x58,x59,x56);
this.computeTilesOffset()
this.copyTiles();
this.clearLastZoom();
for(var x60=0;x60<this._f420.length;x60++)
{
for(var x61=0;x61<this._f420[x60].length;x61++)
{
this.initZoomInfo(x56,x57,this._f420[x60][x61]);
this.runZoom(this,x60,x61);
}
}
}
MVZoomControl.prototype.reset=function()
{
this.ldiff=0;
this.tdiff=0;
this.movedLeft=0;
this.movedTop=0;
if(this.tileLayerDiv&&this.tileLayerDiv.parentNode)
{
this.tileLayerDiv.parentNode.removeChild(this.tileLayerDiv);
MVUtil._f92(this.tileLayerDiv);
}
this._f420=null;
this.tileLayerDiv=null;
this.zoomStep=0.2
this.interval=100;
this.zooming=false;
this.oldTilesLoaded=false;
for(var x62=0;x62<this.timeouts.length;x62++)
{
clearTimeout(this.timeouts[x62]);
}
this.timeouts=new Array();
if(this._f426.basemap.isVisible())
this._f426.setVisible(true);
}
MVZoomControl.prototype.runZoom=function(x63,x64,x65,x66)
{
x66=(x66)?x66:x63.zoomStep;
var x67=x63._f420[x64][x65];
x63.zoomToFactor(x67,x66);
if(x66>=1)
{
x67.oldZoomInfo=null;
x67.zoomFinished=true;
if(this.hasZoomFinished())
{
this.startClear();
}
return;
}
else
 {
var x68=setTimeout(function(){x63.runZoom.call(x63,x63,x64,x65,x66+x63.zoomStep);},x63.interval);
this.timeouts.push(x68);
}
}
MVZoomControl.prototype.hasZoomFinished=function()
{
if(!(this._f420))
{
return true;
}
for(var x69=0;x69<this._f420.length;x69++)
{
for(var x70=0;x70<this._f420[x69].length;x70++)
{
if(!this._f420[x69][x70].zoomFinished)
{
return false;
}
}
}
return true;
}
MVZoomControl.prototype.hasTilesLoadFinished=function()
{
for(var x71=0;x71<this._f426._f420.length;x71++)
{
for(var x72=0;x72<this._f426._f420[x71].length;x72++)
{
if(!this.hasTileLoaded(this._f426._f420[x71][x72]))
{
return false;
}
}
}
return true;
}
MVZoomControl.prototype.hasTileLoaded=function(x73)
{
if(x73.src==this.mapview._f428.src)
return true;
if(_f11._f360==2){
return x73.imgLoadFinished;
}
else {
return (x73.offsetWidth==this._f426.tileWidth);
}
}
MVZoomControl.prototype.startClear=function()
{
this._f426.setVisible(true);
for(var x74=0;x74<this.mapview._f248._f260.length;x74++)
{
var x75=this.mapview._f248._f260[x74].node;
if(x75 instanceof Array)
{
for(var x76=0;x76<x75.length;x76++)
x75[x76].style.display='';
}
else if(x75!=null)
x75.style.display='';
}
if(_f11._f360==2){
for(var x74=0;x74<this._f426._f420.length;x74++)
{
for(var x76=0;x76<this._f426._f420[x74].length;x76++)
{
this._f426._f420[x74][x76].style.display='';
}
}
}
this.tileLayerDiv.style.zIndex=this._f426.zIndex;;
this.clearCloneTiles();
}
MVZoomControl.prototype.clearCloneTiles=function()
{
if(this.hasTilesLoadFinished())
{
for(var x77=0;x77<this._f426._f420.length;x77++)
{
for(var x78=0;x78<this._f426._f420[x77].length;x78++)
{
this._f426._f420[x77][x78].style.display='';
}
}
this.removeCloneTiles();
}
else
 {
var x79=this;
var x80=setTimeout(function(){x79.clearCloneTiles()},70);
this.timeouts.push(x80);
}
}
MVZoomControl.prototype.removeCloneTiles=function()
{
if(this.tileLayerDiv&&this.tileLayerDiv.parentNode)
{
this.tileLayerDiv.parentNode.removeChild(this.tileLayerDiv);
MVUtil._f92(this.tileLayerDiv);
this.tileLayerDiv=null;
}
this.zooming=false;
}
function _f848()
{
}
_f848._f400=function(x0,x1,x2)
{
var x3=_f361._f364({x:x0,y:x1});
x0=x3.x;
x1=x3.y;
var x4=Math.sin(x1);
var x5=Math.log((1+x4)/(1-x4));
var x6=x2*x0;
var x7=x5*x2/2;
return {x:x6,y:x7};
}
_f848._f404=function(x8,x9,x10)
{
var x11=x8/x10;
var x12=x9*2/x10;
var x13=Math.pow(Math.E,x12);
var x14=(x13-1)/(x13+1);
var x15=Math.asin(x14);
return _f361._f365({x:x11,y:x15});
}
_f11._f405(3785,8307,function(x16,x17){
return _f848._f404(x16,x17,6378137);
});
_f11._f405(8307,3785,function(x18,x19){
return _f848._f400(x18,x19,6378137);
});
_f11._f405(53004,8307,function(x20,x21){
return _f848._f404(x20,x21,6371000);
});
_f11._f405(8307,53004,function(x22,x23){
return _f848._f400(x22,x23,6371000);
});
_f11._f405(53004,54004,function(x24,x25){
var x26=_f848._f404(x24,x25,6371000);
return _f398._f400(x26.x,x26.y,8307,54004);
});
_f11._f405(54004,53004,function(x27,x28){
var x29=_f398._f404(x27,x28,54004,8307);
return _f848._f400(x29.x,x29.y,6371000);
});
_f11._f405(3785,54004,function(x30,x31){
var x32=_f848._f404(x30,x31,6378137);
return _f398._f400(x32.x,x32.y,8307,54004);
});
_f11._f405(54004,3785,function(x33,x34){
var x35=_f398._f404(x33,x34,54004,8307);
return _f848._f400(x35.x,x35.y,6378137);
});
function MVButtonGroup()
{
this._f193=new Object();
this.toolBar=null;
var x0=this;
this.add=function(x1)
{
x0._f193[x1._f194]=x1;
x1.group=x0;
x1._f195=x0._f196;
if(this.toolBar)
this.toolBar.addButton(x1);
}
this.addSeparator=function(x2)
{
var x3=new MVToolButton(x2,MVToolButton.SEPARATOR);
this.add(x3);
}
this.reset=function()
{
this._f196();
}
this._f196=function(x4)
{
if(x0._f193)
{
for(var x5 in x0._f193)
{
if(x4==undefined)
x0._f193[x5].reset();
else
 x0._f193[x5]._f197(x4);
}
}
}
}
function MVDistanceTool(x0,x1)
{
var x2=this;
this._f387=true;
this._f388=true;
this._f386=x0;
this._f3=x1;
this._f328=null;
this._f389=null;
this._f335=null;
this._f98=new Array();
this.srid=null;
this._f390=null;
this._f391=new MVRedlineTool(null,null,x1);
this._f391.setGeneratePolygonTop(false);
this.init=function()
{
x2.parent.addRedLineTool(x2._f391);
x2.srid=x2.parent.srid;
if(x2._f391.getStatus()!=0)
{
x2.clear();
}
x2._f391.finishLinkClick=x2.finish;
x2._f391.clearLinkClick=x2.clear;
x2._f391.init(2);
x2._f391._f319=-1;
x2._f391.setEventListener(MVEvent.NEW_SHAPE_POINT,x2._f392);
x2._f391.setEventListener(MVEvent.MODIFY,x2._f393);
if(x2._f388)
{
x2._f390=x2.parent._f394;
x2.parent.setEventListener(MVEvent.MOUSE_DOUBLE_CLICK,x2.finish);
}
MVUtil.runListeners(this._f98,MVEvent.INIT,[MVToolBar.BUILTIN_DISTANCE]);
}
this.setEventListener=function(x3,x4)
{
if(x3==MVEvent.NEW_SHAPE_POINT)
x2._f389=x4;
else if(x3==MVEvent.FINISH)
x2._f328=x4;
else if(x3==MVEvent.MODIFY)
x2._f335=x4;
}
this.attachEventListener=function(x5,x6)
{
MVUtil.attachEventListener(x2._f98,x5,x6)
}
this.detachEventListener=function(x7,x8)
{
MVUtil.detachEventListener(x2._f98,x7,x8);
}
this.setRenderingStyle=function(x9,x10)
{
this._f391.setRenderingStyle(x9,x10);
}
this.setTextStyle=function(x11)
{
x2._f391.setTextStyle(x11);
}
this.getOrdinates=function()
{
return x2._f391.getOrdinates();
}
this.getStatus=function()
{
return x2._f391.getStatus();
}
this.finish=function(x12)
{
if(x2._f391!=null)
{
if(x2._f391.getOrdinates().length<2)
{
MVi18n._f6("MVDistanceTool.finish","MAPVIEWER-05512",null,this.parent?this.parent._f7:null);
return;
}
else
 {
x2._f391.generateArea();
if(x2._f328)
x2._f328();
MVUtil.runListeners(x2._f98,MVEvent.FINISH);
}
}
x2.parent._f394=x2._f390;
}
this.clear=function()
{
if(x2._f391!=null)
{
if(x2._f391.getOrdinates().length>=2&&
x2._f391.getStatus()==1)
{
if(x2._f328)
x2._f328();
MVUtil.runListeners(x2._f98,MVEvent.FINISH);
}
x2._f391.clear();
var x13=document.getElementById("distance_info_div_"+x2._f391.id);
if(x13)
x13.innerHTML="";
MVUtil.runListeners(x2._f98,MVEvent.CLEAR);
}
}
this._f392=function()
{
if(x2._f387)
{
if(x2._f391==null)
return;
var x14=x2._f391.getOrdinates();
if(x14==null||x14.length<4)
{
if(x2._f389)
x2._f389();
MVUtil.runListeners(x2._f98,MVEvent.NEW_SHAPE_POINT);
return;
}
var x15=MVSdoGeometry.createLineString(x14,x2.srid);
var x16;
if(x2._f386==MVDistanceTool.METRIC)
x16=parseInt(x15.getLength("meter",true,x2._f3));
else
 x16=parseInt(x15.getLength("Yard",true,x2._f3));
var x17=MVi18n._f340("totalDistance")+x2._f395(x16);
var x18=document.getElementById("distance_info_div_"+x2._f391.id);
if(!x18)
{
x18=document.createElement("div");
x18.id="distance_info_div_"+x2._f391.id;
x2._f391.getInfoDecorationDiv().appendChild(x18);
}
x18.innerHTML=x17;
}
if(x2._f389)
x2._f389();
MVUtil.runListeners(x2._f98,MVEvent.NEW_SHAPE_POINT);
}
this.getDistance=function(x19,x20,x21)
{
if(x2._f391==null)
return 0;
var x22=x2._f391.getOrdinates();
if(x22==null||x22.length<4)
return 0;
var x23=x22.length;
var x24=new Array(4);
for(var x25=0;x25<4;x25++)
x24[x25]=x22[x23+x25-4];
var x26=MVSdoGeometry.createLineString(x24,x2.srid);
return parseInt(x26.getLength(x19,x20,x21));
}
this._f397=function(x27,x28,x29)
{
if(x2._f391==null)
return 0;
var x30=x2._f391.getOrdinates();
if(x30==null||x30.length<4)
return 0;
var x31=MVSdoGeometry.createLineString(x30,x2.srid);
return parseInt(x31.getLength(x27,x28,x29));
}
this._f395=function(x32)
{
if(x2._f386==MVDistanceTool.METRIC)
{
if(x32>1000)
{
var x33=Math.round(x32/10);
return MVUtil.formatNum(x33/100)+MVi18n._f340("km");
}
else if(x32>1)
return MVUtil.formatNum(x32)+MVi18n._f340("m");
else
 return MVUtil.formatNum(x32*10)+MVi18n._f340("cm");
}
else
 {
if(x32>1760)
{
var x33=Math.round((x32/1760)*100);
return MVUtil.formatNum(x33/100)+MVi18n._f340("mi");
}
else if(x32>1)
return MVUtil.formatNum(x32)+MVi18n._f340("yd");
else
 return MVUtil.formatNum(x32*3)+MVi18n._f340("ft");
}
}
this._f393=function()
{
if(x2._f387)
x2._f392();
else
 {
if(x2._f335)
x2._f335();
MVUtil.runListeners(x2._f98,MVEvent.MODIFY);
}
}
}
MVDistanceTool.prototype.setUnitStandard=function(x0)
{
this._f386=x0;
}
MVDistanceTool.METRIC=0;
MVDistanceTool.IMPERIAL=1;
function MVInfoWindowTab(x0,x1)
{
this.title=x0;
this.content=x1;
}
MVInfoWindowTab.prototype.getTitle=function()
{
return this.title;
}
MVInfoWindowTab.prototype.getContent=function()
{
return this.content;
}
function MVMenu(x0,x1,x2)
{
this.mapview=x0;
this.menuTableId="mvmenu_table_0";
this.menuTableBodyId="mvmenu_table_body_0";
this.menuFOIId="mvmenu_0";
x0.removeFOI(this.menuFOIId);
var x3="<table id='"+this.menuTableId+"' cellpadding=1 cellspacing=0 style='width:1px'><tr>"+
"<td style='border-width:1px;border-style:solid;border-color:black;background-color:white'>"+
"<table><tbody id='"+this.menuTableBodyId+"'></tbody></table>"+
"</td>"+
"</tr></table>"
var x4=MVFOI.createHTMLFOI(this.menuFOIId,x1,x3,0,0);
if(x2)
x4.areacode=x2;
x4.setInfoTip(null);
x4.setInfoWindow(null,0,0);
x4.setTopFlag(true);
x0.addFOI(x4);
var x5=this.menuFOIId;
var x6=function()
{
x0.removeFOI(x5);
}
x0.addEventListener(MVEvent.MOUSE_CLICK,x6);
}
MVMenu.count=0;
MVMenu.prototype.addMenuItem=function(x0,x1)
{
var x2=document.getElementById(this.menuTableBodyId);
var x3=document.getElementById(this.menuTableId).style.width;var x4=parseInt(x3.substr(0,x3.length-2));
if(x4<(x0.length*12))
document.getElementById(this.menuTableId).style.width=(x0.length*12)+"px";
var x5=document.createElement('tr');
x2.appendChild(x5);
x5.style.fontFamily="Arial, Helvetica, Geneva, sans-serif";
x5.style.fontSize="12px";
x5.style.cursor="pointer";
x5.align='left';
var x6=document.createElement("td");
x6.innerHTML=x0;
x5.appendChild(x6);
x5.onmouseover=function()
{
this.style.backgroundColor="#336699";
this.style.color="#ffffff";
}
x5.onmouseout=function()
{
this.style.backgroundColor="transparent";
this.style.color="#000000";
}
if(x1)
{
var x2=this;
x5.onmousedown=function(x7)
{
MVUtil._f173(x7);
x2.removeRightClickMenu();
x1();
}
}
}
MVMenu.prototype.removeRightClickMenu=function()
{
this.mapview.removeFOI(this.menuFOIId);
}
function MVToolBar(x0,x1,x2)
{
if(x2==MVToolBar.VERTICAL)
this.Direction=x2;
else
 this.Direction=MVToolBar.HORIZONTAL;
this._f603=x0;
this._f604=x1;
this.mapviewer=null;
this.toolBarDecoration=null;
this._f605;
this._f606=new Object();
var x3=this;
var x4=null;
this.visible=true;
this.clearButtonAdded=false;
this._f607=new MVToolButton(x0+"_bt_clear",MVToolButton.COMMAND,_f11._f142+"tbicons/clear.gif",_f11._f142+"tbicons/p_clear.gif");
this._f608=new MVToolButton(x0+"_bt_redline",MVToolButton.TOGGLE,_f11._f142+"tbicons/redline.gif",_f11._f142+"tbicons/p_redline.gif");
this._f609=new MVToolButton(x0+"_bt_distance",MVToolButton.TOGGLE,_f11._f142+"tbicons/distance.gif",_f11._f142+"tbicons/p_distance.gif");
this._f610=new MVToolButton(x0+"_bt_circle",MVToolButton.TOGGLE,_f11._f142+"tbicons/circle.gif",_f11._f142+"tbicons/p_circle.gif");
this._f611=new MVToolButton(x0+"_bt_rectangle",MVToolButton.TOGGLE,_f11._f142+"tbicons/rectangle.gif",_f11._f142+"tbicons/p_rectangle.gif");
this._f612=new MVToolButton(x0+"_bt_zoom",MVToolButton.TOGGLE,_f11._f142+"tbicons/zoom.gif",_f11._f142+"tbicons/p_zoom.gif");
this._f613=new MVButtonGroup();
this._f614=new Array();
this.setPosition=function(x5,x6,x7,x8)
{
if(!x7)
x7=0;
if(!x8)
x8=0;
x3.toolBarDecoration.setPosition(x5,x6,x7,x8);
}
this.setVisible=function(x9)
{
if(x3.visible!=x9)
{
x3.toolBarDecoration.setVisible(x9);
x3.visible=x9;
}
}
this.isVisible=function()
{
return this.visible;
}
this.getBuiltInTool=function(x10)
{
if(x3._f604[MVToolBar.BUILTIN_ALL]==true||x3._f604[x10]==true)
{
var x11=null;
switch(x10)
{
case MVToolBar.BUILTIN_CIRCLE:
x11=x3._f610;
break;
case MVToolBar.BUILTIN_RECTANGLE:
x11=x3._f611;
break;
case MVToolBar.BUILTIN_REDLINE:
x11=x3._f608;
break;
case MVToolBar.BUILTIN_DISTANCE:
x11=x3._f609;
break;
}
if(x11)
return x11.tool;
else
 return null;
}
else
 return null;
}
this.addButton=function(x12)
{
var x13=null;
var x14=null;
if(x12._f615==MVToolButton.SEPARATOR)
{
if(x3.Direction==MVToolBar.HORIZONTAL)
x12._f616.setAttribute("src",_f11._f142+"tbicons/bk_v.png");
else
 x12._f616.setAttribute("src",_f11._f142+"tbicons/bk_h.png");
}
if(x3.Direction==MVToolBar.HORIZONTAL)
{
if(x4.childNodes.length==0)
{
x13=x4.insertRow(0);
}
else
 {
x13=x4.firstChild.firstChild;
}
x14=x13.insertCell(x13.childNodes.length);
x14.id="_td_"+x12._f194
}
else
 {
if(x4.childNodes.length==0)
x13=x4.insertRow(0);
else
 x13=x4.insertRow(x4.firstChild.childNodes.length);
x13.id="_tr_"+x12._f194;
x14=x13.insertCell(0);
}
x12._f617=x3;
x14.appendChild(x12._f618);
x3._f606[x12._f194]=x12;
x12._f617=x3;
}
this.addSeparator=function(x15)
{
var x16=new MVToolButton(x15,MVToolButton.SEPARATOR);
x3.addButton(x16);
}
this.getButton=function(x17)
{
return x3._f606[x17];
}
this.getBuiltInButton=function(x18)
{
if(x18==MVToolBar.BUILTIN_CLEAR&&this.clearButtonAdded)
return this._f607;
if(x3._f604[MVToolBar.BUILTIN_ALL]==true||x3._f604[x18]==true)
{
var x19=null;
switch(x18)
{
case MVToolBar.BUILTIN_CIRCLE:
x19=x3._f610;
break;
case MVToolBar.BUILTIN_RECTANGLE:
x19=x3._f611;
break;
case MVToolBar.BUILTIN_REDLINE:
x19=x3._f608;
break;
case MVToolBar.BUILTIN_DISTANCE:
x19=x3._f609;
break;
case MVToolBar.BUILTIN_MARQUEE_ZOOM:
x19=x3._f612;
break;
}
if(x19)
return x19;
else
 return null;
}
else
 return null;
}
this.removeButton=function(x20)
{
if(!x20)
return ;
delete x3._f606[x20._f194];
if(x20.group)
{
delete x20.group._f193[x20._f194];
x20.group=null;
}
if(x3.Direction==MVToolBar.HORIZONTAL)
{
if(x4.childNodes.length==0)
{
return;
}
else
 {
oTR=x4.firstChild.firstChild;
for(var x21=0;x21<oTR.childNodes.length;x21++)
{
if(oTR.childNodes[x21].id=="_td_"+x20._f194)
{
oTR.deleteCell(x21);
}
}
}
}
else
 {
if(x4.childNodes.length==0)
{
return;
}
else
 {
for(var x21=0;x21<x4.firstChild.childNodes.length;x21++)
{
if(x4.firstChild.childNodes[x21].id=="_tr_"+x20._f194)
{
x4.firstChild.deleteRow(x21);
}
}
}
}
}
this.getBuiltInButtonGroup=function()
{
return this._f613;
}
this.addButtonGroup=function(x22)
{
if(x22)
{
x22.toolBar=this;
this._f614.push(x22);
for(var x23 in x22._f193)
{
this.addButton(x22._f193[x23]);
}
}
}
this.removeButtonGroup=function(x24)
{
if(x24)
{
for(var x25=0;x25<this._f614.length;x25++)
{
if(this._f614[x25]==x24)
{
this._f614.splice(x25,1);
for(var x26 in x24._f193)
{
this.removeButton(x24._f193[x26]);
}
return ;
}
}
}
}
this.init=function()
{
x3.toolBarDecoration=new MVMapDecoration("<div id=\"div_"+x3._f603+"\" style=\"background: white;\"></div>",0.35,0.05);
x3.toolBarDecoration.setVisible(x3.visible);
x3.toolBarDecoration.setDraggable(true);
x4=document.createElement("Table");
x4.id="tb_"+x3._f603;
x4.style.backgroundColor="rgb(242, 237, 242)";
x4.cellSpacing=1;
x4.cellPadding=1;
x4.style.border="1px solid #7F9DB9";
x3._f619();
}
this._f619=function()
{
x3._f607.setToolTip(MVi18n._f340("clearButton"));
x3._f608.setToolTip(MVi18n._f340("redlineTool"));
x3._f609.setToolTip(MVi18n._f340("distanceTool"));
x3._f610.setToolTip(MVi18n._f340("circleTool"));
x3._f611.setToolTip(MVi18n._f340("retangleTool"));
x3._f612.setToolTip(MVi18n._f340("marqueeZoomTool"));
x3._f608.btName=MVToolBar.BUILTIN_REDLINE;
x3._f609.btName=MVToolBar.BUILTIN_DISTANCE;
x3._f610.btName=MVToolBar.BUILTIN_CIRCLE;
x3._f611.btName=MVToolBar.BUILTIN_RECTANGLE;
x3._f612.btName=MVToolBar.BUILTIN_MARQUEE_ZOOM;
x3._f607.attachEventListener(MVEvent.BUTTON_DOWN,x3._f620);
x3._f608.attachEventListener(MVEvent.BUTTON_DOWN,x3._f620);
x3._f609.attachEventListener(MVEvent.BUTTON_DOWN,x3._f620);
x3._f610.attachEventListener(MVEvent.BUTTON_DOWN,x3._f620);
x3._f611.attachEventListener(MVEvent.BUTTON_DOWN,x3._f620);
x3._f612.attachEventListener(MVEvent.BUTTON_DOWN,x3._f620);
x3._f608.attachEventListener(MVEvent.BUTTON_UP,x3._f621);
x3._f609.attachEventListener(MVEvent.BUTTON_UP,x3._f621);
x3._f610.attachEventListener(MVEvent.BUTTON_UP,x3._f621);
x3._f611.attachEventListener(MVEvent.BUTTON_UP,x3._f621);
x3._f612.attachEventListener(MVEvent.BUTTON_UP,x3._f621);
x3._f613.add(x3._f607);
x3._f613.add(x3._f608);
x3._f613.add(x3._f609);
x3._f613.add(x3._f610);
x3._f613.add(x3._f611);
x3._f613.add(x3._f612);
var x27=new MVCircleTool();
var x28=new MVStyleColor("_bt_circle_area_color",null,"FF0000");
x27.setAreaStyle(x28);
x3._f610.tool=x27;
var x29=new MVRectangleTool();
var x28=new MVStyleColor("_bt_rectangle_area_color",null,"FF0000");
x29.setAreaStyle(x28);
x3._f611.tool=x29;
var x30=new MVRedlineTool();
x3._f608.tool=x30;
var x31=new MVDistanceTool(MVDistanceTool.IMPERIAL);
var x32=new MVStyleMarker("_bt_distance_point","vector");
x32.setStrokeColor("ff0000");
x32.setFillColor("00FF00");
x32.setVectorShape("c:30");
x31.setRenderingStyle(MVRedlineTool.STYLE_POINT,x32);
var x33='<svg width="1in" height="1in"><desc/><g class="line" style="stroke-width:1.0">'+
'<line class="base" style="fill:#ff0000;stroke-width:1.0" /></g></svg>';
var x34=new MVXMLStyle("_bt_distance_line",x33);
x31.setRenderingStyle(MVRedlineTool.STYLE_LINE,x34);
x3._f609.tool=x31;
}
this._f622=function()
{
x3._f605=document.getElementById("div_"+x3._f603);
x3._f605.appendChild(x4);
}
this._f623=function(x35)
{
var x36=[false,false,false,false,false,false];
if(x35==null&&x3._f604)
{
for(var x37=0;x37<x3._f604.length;x37++)
{
x36[x3._f604[x37]]=true;
}
if(x36[MVToolBar.BUILTIN_ALL]==true)
{
x36=[true,true,true,true,true,true];
}
x3._f604=x36;
for(var x37=0;x37<x36.length;x37++)
{
if(x36[x37])
{
x3.addButton(x3._f607);
x3.clearButtonAdded=true;
break;
}
}
}
else if(x35!=null)
{
if(x3._f604==null)
{
x3._f604=[false,false,false,false,false,false];
x3._f604[x35.btName]=true;
x36=x3._f604;
for(var x37=0;x37<x36.length;x37++)
{
if(x36[x37])
{
x3.addButton(x3._f607);
x3.clearButtonAdded=true;
break;
}
}
}
else if(x3._f604[x35.btName]==true)
return;
else if(x3._f604[x35.btName]==false)
{
x3._f604[x35.btName]=true;
x36=[false,false,false,false,false,false];
x36[x35.btName]=true;
}
}
var x38=function(x39)
{
for(var x40 in x3._f606)
{
if(x3._f606[x40].tool)
{
var x41=x3._f606[x40]._f194;
if(x41.indexOf(x39)!=-1)
{
x3._f606[x40]._f197();
}
}
}
}
var x42=function()
{
x38("redline");
}
var x43=function()
{
x38("distance");
}
var x44=function()
{
x38("circle");
}
var x45=function()
{
x38("rectangle");
}
var x46=function()
{
if(x3._f608.tool.getPointFOIs().length>0)
x3._f608.tool.clear();
x38("redline");
}
var x47=function()
{
if(x3._f609.tool._f391.getPointFOIs().length>0)
x3._f609.tool.clear();
x38("distance");
}
var x48=function()
{
x38("circle");
if(x3._f610.tool.getStatus()!=0)
x3._f610.tool.clear();
}
var x49=function()
{
x38("rectangle");
if(x3._f611.tool.getStatus()!=0)
x3._f611.tool.clear();
}
var x50=function()
{
x38("zoom");
}
var x51=function(x52)
{
var x53=x3.getBuiltInButton(x52);
if(x53._f624==MVToolButton.UP)
{
x53.group.reset();
x53._f616.setAttribute("src",x53._f625);
x53._f624=MVToolButton.DOWN;
if(x53._f195)
x53._f195(x53._f194);
}
}
for(var x37=1;x37<x36.length;x37++)
{
if(x36[x37]==true)
{
x3.addSeparator("sp_builtIn_"+x37);
if(x37==MVToolBar.BUILTIN_CIRCLE)
{
x3.addButton(x3._f610);
x3.mapviewer.addCircleTool(x3._f610.tool);
x3._f610.tool.attachEventListener(MVEvent.FINISH,x44);
x3._f610.tool.attachEventListener(MVEvent.CLEAR,x48);
x3._f610.tool.attachEventListener(MVEvent.INIT,x51);
}
else if(x37==MVToolBar.BUILTIN_RECTANGLE)
{
x3.addButton(x3._f611);
x3.mapviewer.addRectangleTool(x3._f611.tool);
x3._f611.tool.attachEventListener(MVEvent.FINISH,x45);
x3._f611.tool.attachEventListener(MVEvent.CLEAR,x49);
x3._f611.tool.attachEventListener(MVEvent.INIT,x51);
}
else if(x37==MVToolBar.BUILTIN_REDLINE)
{
x3.addButton(x3._f608);
x3.mapviewer.addRedLineTool(x3._f608.tool);
x3._f608.tool.attachEventListener(MVEvent.FINISH,x42);
x3._f608.tool.attachEventListener(MVEvent.CLEAR,x46);
x3._f608.tool.attachEventListener(MVEvent.INIT,x51);
}
else if(x37==MVToolBar.BUILTIN_DISTANCE)
{
x3.addButton(x3._f609);
x3.mapviewer.addDistanceTool(x3._f609.tool);
x3._f609.tool.attachEventListener(MVEvent.FINISH,x43);
x3._f609.tool.attachEventListener(MVEvent.CLEAR,x47);
x3._f609.tool.attachEventListener(MVEvent.INIT,x51);
}
else if(x37==MVToolBar.BUILTIN_MARQUEE_ZOOM)
{
x3.addButton(x3._f612);
}
}
}
}
this._f620=function(x54)
{
if(x54==x0+"_bt_clear")
{
for(var x55 in x3._f606)
{
if(x3._f606[x55].tool)
{
var x56=x3._f606[x55]._f194;
if((x56==x0+"_bt_redline"||x56==x0+"_bt_distance"||
x56==x0+"_bt_circle"||x56==x0+"_bt_rectangle")&&
x3._f606[x55].tool.parent!=null)
x3._f606[x55].tool.clear();
}
}
}
else if(x54==x0+"_bt_redline")
{
if(x3.getButton(x54).tool)
{
var x57=x3.getButton(x54).tool;
x57.init();
}
}
else if(x54==x0+"_bt_distance")
{
var x58;
if(x3.getButton(x54).tool)
{
x58=x3.getButton(x54).tool;
x58.init();
}
}
else if(x54==x0+"_bt_circle")
{
var x59;
if(x3.getButton(x54).tool)
{
x59=x3.getButton(x54).tool;
x59.init();
}
}
else if(x54==x0+"_bt_rectangle")
{
var x60;
if(x3.getButton(x54).tool)
{
x60=x3.getButton(x54).tool;
x60.init();
}
}
else if(x54==x0+"_bt_zoom")
{
var x61=new Object();
x61.borderColor="#0000FF";
x61.borderWidth=2;
x61.borderDashed=false;
x61.fillColor="#FF0000";
x61.fillOpacity=0.5;
x3.mapviewer.startMarqueeZoom("continuous",x61);
var x62=function()
{
x3._f606[x54]._f197();
}
x3.mapviewer.attachEventListener(MVEvent.MARQUEEZOOM_FINISH,x62);
}
}
this._f621=function(x63)
{
if(x63==x0+"_bt_redline")
{
if(x3.getButton(x63).tool)
{
if(x3.getButton(x63).tool.getPointFOIs().length>=3)
x3.getButton(x63).tool.generateArea();
else
 x3.getButton(x63).tool.clear();
}
}
if(x63==x0+"_bt_distance")
{
if(x3.getButton(x63).tool)
{
if(x3.getButton(x63).tool._f391.getPointFOIs().length>=3)
x3.getButton(x63).tool.finish();
else
 x3.getButton(x63).tool.clear();
}
}
else if(x63==x0+"_bt_zoom")
{
x3.mapviewer.stopMarqueeZoom();
}
}
x3.init();
}
MVToolBar.VERTICAL=0;
MVToolBar.HORIZONTAL=1;
MVToolBar.BUILTIN_ALL=0;
MVToolBar.BUILTIN_CIRCLE=1;
MVToolBar.BUILTIN_RECTANGLE=2;
MVToolBar.BUILTIN_REDLINE=3;
MVToolBar.BUILTIN_DISTANCE=4;
MVToolBar.BUILTIN_MARQUEE_ZOOM=5;
MVToolBar.BUILTIN_CLEAR=6;
function MVToolButton(x0,x1,x2,x3)
{
this._f194=x0;
this._f615=x1;
this._f849=x2;
this._f625=x3;
this._f624=MVToolButton.UP;
this._f617=null;
this._f98=new Array();
this._f850=null;
this._f851=null;
this._f195=null;
this._f618=document.createElement("div");
this._f616=document.createElement("img");
this._f852=document.createElement("div");
this.btName=null;
var x4=this;
this.attachEventListener=function(x5,x6)
{
MVUtil.attachEventListener(x4._f98,x5,x6)
}
this.detachEventListener=function(x7,x8)
{
MVUtil.detachEventListener(x4._f98,x7,x8);
}
this.setToolTip=function(x9)
{
x4._f852.innerHTML=x9;
}
this.reset=function()
{
this._f197();
}
this._f853=function()
{
x4._f618.appendChild(x4._f616);
x4._f618.appendChild(x4._f852);
x4._f852.style.position="absolute";
x4._f852.style.display="none";
x4._f852.style.borderColor="black";
x4._f852.style.backgroundColor="#ffffe1";
x4._f852.style.borderWidth="1px";
x4._f852.style.borderStyle="solid";
x4._f852.style.fontSize="14px";
x4._f852.style.whiteSpace="nowrap";
x4._f852.style.padding="2px";
if(x4._f615!=MVToolButton.SEPARATOR)
{
x4._f616.setAttribute("src",x4._f849);
}
}
this._f616.onmouseover=function()
{
if(x4._f615!=MVToolButton.SEPARATOR)
{
MVUtil._f164(x4._f617._f605,"pointer");
if(x4._f852.innerHTML)
{
x4._f852.style.display="";
MVUtil._f74(x4._f852,x4._f618.parentNode.offsetLeft+5,x4._f618.parentNode.offsetTop+30);
}
}
else
 {
MVUtil._f164(x4._f617._f605,"default");
}
}
this._f616.onmouseout=function(x10)
{
if(x4._f624==MVToolButton.DOWN)
x4._f616.onmouseup(x10);
x4._f852.style.display="none";
}
this._f616.onmousedown=function(x11)
{
if(x4._f195)
x4._f195(x4._f194);
if(x4._f615==MVToolButton.COMMAND)
{
x4._f616.setAttribute("src",x4._f625);
x4._f624=MVToolButton.DOWN;
x11=(x11)?x11:((window.event)?event:null);
MVUtil._f173(x11);
if(x4._f850)
x4._f850(x4._f194);
MVUtil.runListeners(x4._f98,MVEvent.BUTTON_DOWN,[x4._f194]);
}
else if(x4._f615==MVToolButton.TOGGLE)
{
if(x4._f624==MVToolButton.UP)
{
x4._f616.setAttribute("src",x4._f625);
x4._f624=MVToolButton.DOWN;
x11=(x11)?x11:((window.event)?event:null);
MVUtil._f173(x11);
if(x4._f850)
x4._f850(x4._f194);
MVUtil.runListeners(x4._f98,MVEvent.BUTTON_DOWN,[x4._f194]);
}
else
 {
x4._f616.setAttribute("src",x4._f849);
x4._f624=MVToolButton.UP;
x11=(x11)?x11:((window.event)?event:null);
MVUtil._f173(x11);
if(x4._f851)
x4._f851(x4._f194);
MVUtil.runListeners(x4._f98,MVEvent.BUTTON_UP,[x4._f194]);
}
}
}
this._f616.onmouseup=function(x12)
{
if(x4._f615==MVToolButton.COMMAND&&x4._f624==MVToolButton.DOWN)
{
x4._f616.setAttribute("src",x4._f849);
if(x4._f851)
x4._f851(x4._f194);
MVUtil.runListeners(x4._f98,MVEvent.BUTTON_UP,[x4._f194]);
x4._f624=MVToolButton.UP;
}
}
this._f197=function(x13)
{
if(x4._f615==MVToolButton.TOGGLE&&(x13==undefined||x4._f194!=x13))
{
if(x4._f624==MVToolButton.DOWN)
{
x4._f616.setAttribute("src",x4._f849);
x4._f624=MVToolButton.UP;
MVUtil.runListeners(x4._f98,MVEvent.BUTTON_UP,[x4._f194]);
}
}
}
x4._f853();
}
MVToolButton.COMMAND=0;
MVToolButton.TOGGLE=1;
MVToolButton.SEPARATOR=2;
MVToolButton.UP=0;
MVToolButton.DOWN=1;




function MVGoogleTileLayer()
{

this._f503=
{
"mapTileLayer":"MV3785_1_19",
"format":"PNG",
"coordSys":
{
"srid":3785,
"type":"PROJECTED",
"distConvFactor":1.0,
"minX":-2.0037508E7,"minY":-2.0037508E7,
"maxX":2.0037508E7,"maxY":2.0037508E7
},
"zoomLevels":
[
{"zoomLevel":1,"name":"","tileWidth":2.0037508E7,"tileHeight":2.0037508E7,"tileImageWidth":256,"tileImageHeight":256},
{"zoomLevel":2,"name":"","tileWidth":1.0018754E7,"tileHeight":1.0018754E7,"tileImageWidth":256,"tileImageHeight":256},
{"zoomLevel":3,"name":"","tileWidth":5009377.0,"tileHeight":5009377.0,"tileImageWidth":256,"tileImageHeight":256},
{"zoomLevel":4,"name":"","tileWidth":2504688.5,"tileHeight":2504688.5,"tileImageWidth":256,"tileImageHeight":256},
{"zoomLevel":5,"name":"","tileWidth":1252344.25,"tileHeight":1252344.25,"tileImageWidth":256,"tileImageHeight":256},
{"zoomLevel":6,"name":"","tileWidth":626172.125,"tileHeight":626172.125,"tileImageWidth":256,"tileImageHeight":256},
{"zoomLevel":7,"name":"","tileWidth":313086.0625,"tileHeight":313086.0625,"tileImageWidth":256,"tileImageHeight":256},
{"zoomLevel":8,"name":"","tileWidth":156543.03125,"tileHeight":156543.03125,"tileImageWidth":256,"tileImageHeight":256},
{"zoomLevel":9,"name":"","tileWidth":78271.515625,"tileHeight":78271.515625,"tileImageWidth":256,"tileImageHeight":256},
{"zoomLevel":10,"name":"","tileWidth":39135.7578125,"tileHeight":39135.7578125,"tileImageWidth":256,"tileImageHeight":256},
{"zoomLevel":11,"name":"","tileWidth":19567.87890625,"tileHeight":19567.87890625,"tileImageWidth":256,"tileImageHeight":256},
{"zoomLevel":12,"name":"","tileWidth":9783.939453125,"tileHeight":9783.939453125,"tileImageWidth":256,"tileImageHeight":256},
{"zoomLevel":13,"name":"","tileWidth":4891.9697265625,"tileHeight":4891.9697265625,"tileImageWidth":256,"tileImageHeight":256},
{"zoomLevel":14,"name":"","tileWidth":2445.98486328125,"tileHeight":2445.98486328125,"tileImageWidth":256,"tileImageHeight":256},
{"zoomLevel":15,"name":"","tileWidth":1222.992431640625,"tileHeight":1222.992431640625,"tileImageWidth":256,"tileImageHeight":256},
{"zoomLevel":16,"name":"","tileWidth":611.4962158203125,"tileHeight":611.4962158203125,"tileImageWidth":256,"tileImageHeight":256},
{"zoomLevel":17,"name":"","tileWidth":305.74810791015625,"tileHeight":305.74810791015625,"tileImageWidth":256,"tileImageHeight":256},
{"zoomLevel":18,"name":"","tileWidth":152.87405395507812,"tileHeight":152.87405395507812,"tileImageWidth":256,"tileImageHeight":256},
{"zoomLevel":19,"name":"","tileWidth":76.43702697753906,"tileHeight":76.43702697753906,"tileImageWidth":256,"tileImageHeight":256}
]
};

this.srid=8307;

this._f502=MVMapTileLayer;

this._f502(this._f503.mapTileLayer);

this.mapType=null;

this._f28=null;

this._f29=null;

this._f21=null;

this.initializeListener=null;

this.map=null;

this._f564=null;

this._f565=null;
}

MVGoogleTileLayer.prototype=new MVMapTileLayer;

MVGoogleTileLayer.prototype.setMapType=function(x0)
{
this.mapType=x0;
if(this.map)
this.map.setMapType(x0);
}

MVGoogleTileLayer.prototype.getMapType=function()
{
return this.mapType;
}

MVGoogleTileLayer.prototype.getType=function()
{
return "MVExternalAPIMapTileLayer";
}

MVGoogleTileLayer.prototype.init=function(x1)
{
this.map=new GMap2(x1);
if(this.mapType)
this.map.setMapType(this.mapType);
if(this._f28!=null&&this._f29!=null&&this._f21!=null)
{
var x2=new GLatLng(this._f29,this._f28);
this.map.setCenter(x2,this._f21+1);
}
if(this.initializeListener)
this.initializeListener();
this._f564=x1.lastChild;
this._f564.id="google_term_of_use";
x1.removeChild(this._f564);
this._f564.style.zIndex="100";
x1.parentNode.parentNode.appendChild(this._f564);
this._f565=x1.lastChild;
this._f565.id="google_logo";
x1.removeChild(this._f565);
this._f565.style.zIndex="100";
x1.parentNode.parentNode.appendChild(this._f565);
}

MVGoogleTileLayer.prototype.setCenterAndZoomLevel=function(x3,x4,x5)
{
this._f28=x3;
this._f29=x4;
this._f21=x5;
if(this.map)
{
this.map.setCenter(new GLatLng(x4,x3),x5+1);
}
}

MVGoogleTileLayer.prototype._f504=function(x6,x7)
{
this.map.getDragObject().moveBy(new GSize(x6,x7));
}

MVGoogleTileLayer.prototype.getCenter=function()
{
var x8=this.map.getCenter();
if(x8)
return MVSdoGeometry.createPoint(x8.lng(),x8._f314(),this.srid);
else
 return null;
}

MVGoogleTileLayer.prototype.setCenter=function(x9,x10)
{
this._f28=x9;
this._f29=x10;
if(this.map)
this.map.setCenter(new GLatLng(x10,x9));
}

MVGoogleTileLayer.prototype.resize=function()
{
if(this.map)
this.map.checkResize();
}

MVGoogleTileLayer.prototype.clone=function()
{
var x11=new MVGoogleTileLayer();
return x11;
}

MVGoogleTileLayer.prototype.setVisible=function(x12)
{
MVMapTileLayer.prototype.setVisible.call(this,x12);
if(this._f564)
this._f564.style.display=x12?"":"none";
if(this._f565)
this._f565.style.display=x12?"":"none";
}

MVGoogleTileLayer.prototype.destroy=function()
{
MVMapTileLayer.prototype.destroy.call(this);
if(this._f564)
{
this._f564.parentNode.removeChild(this._f564);
this._f564=null;
}
if(this._f565)
{
this._f565.parentNode.removeChild(this._f565);
this._f565=null;
}
GUnload();
}




function MVBingTileLayer()
{

this._f503=
{
"mapTileLayer":"MV3785_1_19",
"format":"PNG",
"coordSys":
{
"srid":3785,
"type":"PROJECTED",
"distConvFactor":1.0,
"minX":-2.0037508E7,"minY":-2.0037508E7,
"maxX":2.0037508E7,"maxY":2.0037508E7
},
"zoomLevels":
[
{"zoomLevel":0,"name":"","tileWidth":2.0037508E7,"tileHeight":2.0037508E7,"tileImageWidth":256,"tileImageHeight":256},
{"zoomLevel":1,"name":"","tileWidth":1.0018754E7,"tileHeight":1.0018754E7,"tileImageWidth":256,"tileImageHeight":256},
{"zoomLevel":2,"name":"","tileWidth":5009377.0,"tileHeight":5009377.0,"tileImageWidth":256,"tileImageHeight":256},
{"zoomLevel":3,"name":"","tileWidth":2504688.5,"tileHeight":2504688.5,"tileImageWidth":256,"tileImageHeight":256},
{"zoomLevel":4,"name":"","tileWidth":1252344.25,"tileHeight":1252344.25,"tileImageWidth":256,"tileImageHeight":256},
{"zoomLevel":5,"name":"","tileWidth":626172.125,"tileHeight":626172.125,"tileImageWidth":256,"tileImageHeight":256},
{"zoomLevel":6,"name":"","tileWidth":313086.0625,"tileHeight":313086.0625,"tileImageWidth":256,"tileImageHeight":256},
{"zoomLevel":7,"name":"","tileWidth":156543.03125,"tileHeight":156543.03125,"tileImageWidth":256,"tileImageHeight":256},
{"zoomLevel":8,"name":"","tileWidth":78271.515625,"tileHeight":78271.515625,"tileImageWidth":256,"tileImageHeight":256},
{"zoomLevel":9,"name":"","tileWidth":39135.7578125,"tileHeight":39135.7578125,"tileImageWidth":256,"tileImageHeight":256},
{"zoomLevel":10,"name":"","tileWidth":19567.87890625,"tileHeight":19567.87890625,"tileImageWidth":256,"tileImageHeight":256},
{"zoomLevel":11,"name":"","tileWidth":9783.939453125,"tileHeight":9783.939453125,"tileImageWidth":256,"tileImageHeight":256},
{"zoomLevel":12,"name":"","tileWidth":4891.9697265625,"tileHeight":4891.9697265625,"tileImageWidth":256,"tileImageHeight":256},
{"zoomLevel":13,"name":"","tileWidth":2445.98486328125,"tileHeight":2445.98486328125,"tileImageWidth":256,"tileImageHeight":256},
{"zoomLevel":14,"name":"","tileWidth":1222.992431640625,"tileHeight":1222.992431640625,"tileImageWidth":256,"tileImageHeight":256},
{"zoomLevel":15,"name":"","tileWidth":611.4962158203125,"tileHeight":611.4962158203125,"tileImageWidth":256,"tileImageHeight":256},
{"zoomLevel":16,"name":"","tileWidth":305.74810791015625,"tileHeight":305.74810791015625,"tileImageWidth":256,"tileImageHeight":256},
{"zoomLevel":17,"name":"","tileWidth":152.87405395507812,"tileHeight":152.87405395507812,"tileImageWidth":256,"tileImageHeight":256},
{"zoomLevel":18,"name":"","tileWidth":76.43702697753906,"tileHeight":76.43702697753906,"tileImageWidth":256,"tileImageHeight":256}
]
};

this.srid=8307;

this._f502=MVMapTileLayer;

this._f502(this._f503.mapTileLayer);

this.mapType=VEMapStyle.Road;

this._f28=null;

this._f29=null;

this._f21=null;

this.initializeListener=null;

this.map=null;

this.key=null;
}
MVBingTileLayer.prototype=new MVMapTileLayer;

MVBingTileLayer.prototype.setMapType=function(x0)
{
this.mapType=x0;
if(this.map)
this.map.SetMapStyle(x0);
}

MVBingTileLayer.prototype.getMapType=function()
{
return this.mapType;
}

MVBingTileLayer.prototype.setKey=function(x1)
{
if(x1)
this.key=x1;
}

MVBingTileLayer.prototype.getType=function()
{
return "MVExternalAPIMapTileLayer";
}

MVBingTileLayer.prototype.init=function(x2)
{
this.map=new VEMap(x2.id);
this.validateKey();
var x3=null;
if(this._f28!=null&&this._f29!=null)
x3=new VELatLong(this._f29,this._f28);
this.map.LoadMap(x3,this._f21+1,this.mapType,true);
if(this.initializeListener)
this.initializeListener();
}

MVBingTileLayer.prototype.setCenterAndZoomLevel=function(x4,x5,x6)
{
this._f28=x4;
this._f29=x5;
this._f21=x6;
if(this.map)
this.map.SetCenterAndZoom(new VELatLong(x5,x4),x6+1);
}

MVBingTileLayer.prototype._f504=function(x7,x8)
{
this.map.vemapcontrol.PanMap(-x7,-x8);
}

MVBingTileLayer.prototype.getCenter=function()
{
var x9=this.map.GetCenter();
return MVSdoGeometry.createPoint(x9.Longitude,x9.Latitude,this.srid);
}

MVBingTileLayer.prototype.setCenter=function(x10,x11)
{
this._f28=x10;
this._f29=x11;
if(this.map)
this.map.SetCenter(new VELatLong(x11,x10));
}

MVBingTileLayer.prototype.resize=function()
{
if(this.map&&this.layerDiv.parentNode.offsetWidth>0)
this.map.Resize(this.layerDiv.parentNode.offsetWidth,this.layerDiv.parentNode.offsetHeight);
}



MVBingTileLayer.prototype.clone=function()
{
var x12=new MVBingTileLayer();
return x12;
}

MVBingTileLayer.prototype.validateKey=function()
{
var x13="You have entered an invalid Bing Maps Key! Please replace it with a valid key.";
if(!this.map)
return ;
this.map.AttachEvent("oncredentialserror",function(){alert(x13)});
this.map.SetCredentials(this.key);
}
MVVirtualEarthTileLayer=MVBingTileLayer;



function MVYahooTileLayer()
{

this._f503=
{
"mapTileLayer":"MV3785_1_17",
"format":"PNG",
"coordSys":
{
"srid":3785,
"type":"PROJECTED",
"distConvFactor":1.0,
"minX":-2.0037508E7,"minY":-2.0037508E7,
"maxX":2.0037508E7,"maxY":2.0037508E7
},
"zoomLevels":
[
{"zoomLevel":0,"name":"","tileWidth":2.0037508E7,"tileHeight":2.0037508E7,"tileImageWidth":256,"tileImageHeight":256},
{"zoomLevel":1,"name":"","tileWidth":1.0018754E7,"tileHeight":1.0018754E7,"tileImageWidth":256,"tileImageHeight":256},
{"zoomLevel":2,"name":"","tileWidth":5009377.0,"tileHeight":5009377.0,"tileImageWidth":256,"tileImageHeight":256},
{"zoomLevel":3,"name":"","tileWidth":2504688.5,"tileHeight":2504688.5,"tileImageWidth":256,"tileImageHeight":256},
{"zoomLevel":4,"name":"","tileWidth":1252344.25,"tileHeight":1252344.25,"tileImageWidth":256,"tileImageHeight":256},
{"zoomLevel":5,"name":"","tileWidth":626172.125,"tileHeight":626172.125,"tileImageWidth":256,"tileImageHeight":256},
{"zoomLevel":6,"name":"","tileWidth":313086.0625,"tileHeight":313086.0625,"tileImageWidth":256,"tileImageHeight":256},
{"zoomLevel":7,"name":"","tileWidth":156543.03125,"tileHeight":156543.03125,"tileImageWidth":256,"tileImageHeight":256},
{"zoomLevel":8,"name":"","tileWidth":78271.515625,"tileHeight":78271.515625,"tileImageWidth":256,"tileImageHeight":256},
{"zoomLevel":9,"name":"","tileWidth":39135.7578125,"tileHeight":39135.7578125,"tileImageWidth":256,"tileImageHeight":256},
{"zoomLevel":10,"name":"","tileWidth":19567.87890625,"tileHeight":19567.87890625,"tileImageWidth":256,"tileImageHeight":256},
{"zoomLevel":11,"name":"","tileWidth":9783.939453125,"tileHeight":9783.939453125,"tileImageWidth":256,"tileImageHeight":256},
{"zoomLevel":12,"name":"","tileWidth":4891.9697265625,"tileHeight":4891.9697265625,"tileImageWidth":256,"tileImageHeight":256},
{"zoomLevel":13,"name":"","tileWidth":2445.98486328125,"tileHeight":2445.98486328125,"tileImageWidth":256,"tileImageHeight":256},
{"zoomLevel":14,"name":"","tileWidth":1222.992431640625,"tileHeight":1222.992431640625,"tileImageWidth":256,"tileImageHeight":256},
{"zoomLevel":15,"name":"","tileWidth":611.4962158203125,"tileHeight":611.4962158203125,"tileImageWidth":256,"tileImageHeight":256},
{"zoomLevel":16,"name":"","tileWidth":305.74810791015625,"tileHeight":305.74810791015625,"tileImageWidth":256,"tileImageHeight":256}
]
};

this.srid=8307;

this._f502=MVMapTileLayer;

this._f502(this._f503.mapTileLayer);

this.mapType=YAHOO_MAP_REG;

this._f28=null;

this._f29=null;

this._f21=null;

this.initializeListener=null;

this.map=null;
}
MVYahooTileLayer.prototype=new MVMapTileLayer;

MVYahooTileLayer.prototype.setMapType=function(x0)
{
this.mapType=x0;
if(this.map)
this.map.setMapType(x0);
}

MVYahooTileLayer.prototype.getMapType=function()
{
return this.mapType;
}

MVYahooTileLayer.prototype.getType=function()
{
return "MVExternalAPIMapTileLayer";
}

MVYahooTileLayer.prototype.init=function(x1)
{
this.map=new YMap(x1);
var x2=null;
if(this._f28!=null&&this._f29!=null)
{
x2=new YGeoPoint(this._f29,this._f28);
this.map.drawZoomAndCenter(x2,17-this._f21);
}
if(this.initializeListener)
this.initializeListener();
}

MVYahooTileLayer.prototype.setCenterAndZoomLevel=function(x3,x4,x5)
{
this._f28=x3;
this._f29=x4;
this._f21=x5;
if(this.map)
this.map.panToLatLon(new YGeoPoint(x4,x3),17-this._f21);
}

MVYahooTileLayer.prototype.getCenter=function()
{
var x6=this.map.getCenterLatLon();
return MVSdoGeometry.createPoint(x6.Lon,x6.Lat,this.srid);
}

MVYahooTileLayer.prototype._f504=function(x7,x8)
{
this.map.moveByXY(new YCoordPoint(x7,x8));
}

MVYahooTileLayer.prototype.setCenter=function(x9,x10)
{
this._f28=x9;
this._f29=x10;
if(this.map)
this.map.panToLatLon(new YGeoPoint(x10,x9));
}

MVYahooTileLayer.prototype.resize=function()
{
var x11=new YGeoPoint(this._f29,this._f28);
this.map.drawZoomAndCenter(x11,17-this._f21);

}

MVYahooTileLayer.prototype.clone=function()
{
var x12=new MVYahooTileLayer();
return x12;
}
MVMapView.version = "Ver11_B091229";
