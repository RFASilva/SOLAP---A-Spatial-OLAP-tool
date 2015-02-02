# SOLAP---A-Spatial-OLAP-tool
A Spatial OLAP Tool

The SOLAP+ is a generic system that relies on a three tier architecture composed by Oracle as the Database Management System (gives support to spatial data), a SOLAP server, and a client coupling the OLAP features with maps. The server was implemented from scratch, in Java, and it is responsible for listening to client requests, processing them and retrieving the appropriate results. The client handles all user interaction, data presentation and request generation. It was implemented in Java Server Faces (JSF) and the communication with the server is performed based on the XML protocol. Also, it uses Oracle Maps JavaScript API (to enhance the functionality of the Oracle MapViewer) in order to support map visualization and interaction.
