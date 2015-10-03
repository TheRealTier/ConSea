ConSea
======
ConSea lets you search and find public constants that are defined in ABAP classes and interfaces and use them in your coding. ConSea features:
* regular expression to search for constants
* use of shared objects to speed up the inital loading of constants
* search view plug-in for Eclipse-based ABAP Development Tools
 
###Hints
The ABAP part is distributed as a SAPLink nugget called "NUGG_ZCONSEA.nugg" which intstalls three packages and a bunch of development objects. If you want to go with the classical SAPGUI way, just install the objects from this nugget. To start ConSea, use Transaction ZCONSEA and have a try.

The Eclipse plugin part is currently only available as source code in the folder "trunk" in this repository, so you will need to build it on your own. We are working on just deployable solution for the plugin.

###Authors and Contributors
ConSea is maintained by tijoer, Sebastian and therealtier.
