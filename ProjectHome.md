# Goal #
The goal of this project is to build a webapp which reads entities saved in XLS/CSV/XML files and import them into database. The functionality is similar with the entity importation in Webtools component.

# Install #
Check out the source and put them under ./hot-deploy/.
```
svn checkout https://ofbiz-entity-import.googlecode.com/svn/trunk/ ofbiz-entity-import
```

Import the entities which define permissions of this webapp in this file
`entity-import/data/entity-importSecurityData.xml`

# Usage #
Here is the default link : https://localhost:8443/entity-import/control/main
There is a form which accepts a file in XML or XLS format.
You can download samples here :[sample files in XLS and XML](https://ofbiz-entity-import.googlecode.com/svn/trunk/entity-import/data/data-file-template/)

# TODO #
~~Plan to add support to process CSV files.~~ implemented on 08.19.2012