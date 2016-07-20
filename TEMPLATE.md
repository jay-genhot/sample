The template schema
===================


Overview
--------

Custom types.

|  Type | JSON type | Description |
|---|---|---|
| date-time  | _string_  |  Date and time in format: `2016-05-11 13:38`  |
| html  | _string_  |  HTML text |



Root element
------------



|  Name | Type | Description |
|---|---|---|
| pages  | _array_  |   |
| functions  | _array_  |   |
| constitutionalDocuments  |  _array_ |   |
| contracts  | _array_  |   |
| documents  |  _array_ |   |
| guidelines  |  _array_ |   |
| partners  | _array_  |   |
| riskAssessments  | _array_  |   |
| qualityObjectives  |  _array_ |   |
| references  | _array_  |   |



pages item
----------

Page item contains a recursive pages attribute, max two levels.

|  Name | Type | Description |
|---|---|---|
| pageId  | _number_  |   |
| date  | _date-time_  |   |
| name  | _string_  |   |
| helpContent  | _html_  |   |
| pages  | _array_  |   |


functions item
--------------

|  Name | Type | Description |
|---|---|---|
| pageId  | _number_  |   |
| date  | _date-time_  |   |
| name  | _string_  |   |
| helpContent  | _html_  |   |
| description  | _html_  |   |
| type  | _string_  | Valid values: ROTATED, COMMON, SINGLE, SIMULTANEOUS  |
| tasks  | _array_  |   |
| pages  | _array_  |   |
| condition  | _object_  |   |

condition
---------

|  Name | Type | Description |
|---|---|---|
| context  | _string_  |  Valid values: organization, employees, partners |
| expression  | _string_  |   |


RELATED.employees means «this» , i.e. the current context


tasks item
----------

|  Name | Type | Description |
|---|---|---|
| pageId  | _number_  |   |
| date  | _date-time_  |   |
| name  | _string_  |   |
| helpContent  | _html_  |   |
| procedures  | _html_  |   |
| situation  | _string_  |   |
| sequence  | _string_  |   |
| rrule  | _string_  |   |
| pages  | _array_  |   |
| condition  | _object_  |   |



constitutionalDocuments item
----------------------------

|  Name | Type | Description |
|---|---|---|
| pageId  | _number_  |   |
| date  | _date-time_  |   |
| name  | _string_  |   |
| helpContent  | _html_  |   |
| content  | _html_  |   |
| condition  | _object_  |   |


Deprecated properties will be removed: conditional, conditionalOnEntityType

contracts item
--------------

|  Name | Type | Description |
|---|---|---|
| pageId  | _number_  |   |
| date  | _date-time_  |   |
| name  | _string_  |   |
| helpContent  | _html_  |   |
| content  | _html_  |   |
| condition  | _object_  |   |
| pages  | _array_  |   |

Deprecated properties will be removed: template

documents item
--------------

|  Name | Type | Description |
|---|---|---|
| pageId  | _number_  |   |
| date  | _date-time_  |   |
| name  | _string_  |   |
| helpContent  | _html_  |   |
| content  | _html_  |   |
| condition  | _object_  |   |
| pages  | _array_  |   |



guidelines item
---------------

|  Name | Type | Description |
|---|---|---|
| pageId  | _number_  |   |
| date  | _date-time_  |   |
| name  | _string_  |   |
| helpContent  | _html_  |   |
| content  | _html_  |   |
| condition  | _object_  |   |
| pages  | _array_  |   |



partners item
-------------

|  Name | Type | Description |
|---|---|---|
| pageId  | _number_  |   |
| date  | _date-time_  |   |
| name  | _string_  |   |
| helpContent  | _html_  |   |
| pages  | _array_  |   |
| condition  | _object_  |   |



riskAssessments item
--------------------

|  Name | Type | Description |
|---|---|---|
| pageId  | _number_  |   |
| date  | _date-time_  |   |
| name  | _string_  |   |
| helpContent  | _html_  |   |
| description  | _html_  |   |
| condition  | _object_  |   |

qualityObjectives item
----------------------

|  Name | Type | Description |
|---|---|---|
| pageId  | _number_  |   |
| date  | _date-time_  |   |
| name  | _string_  |   |
| helpContent  | _html_  |   |
| description  | _html_  |   |
| condition  | _object_  |   |
| qualitySubObjectives  | _array_  |   |

qualitySubObjectives item
-------------------------

|  Name | Type | Description |
|---|---|---|
| pageId  | _number_  |   |
| date  | _date-time_  |   |
| name  | _string_  |   |
| helpContent  | _html_  |   |
| condition  | _object_  |   |
| qualityIndicators  | _array_  |   |

qualityIndicators item
----------------------

|  Name | Type | Description |
|---|---|---|
| pageId  | _number_  |   |
| date  | _date-time_  |   |
| name  | _string_  |   |
| helpContent  | _html_  |   |
| condition  | _object_  |   |


references item 
---------------

|  Name | Type | Description |
|---|---|---|
| groups  | _array_  |   |


references groups item 
----------------------

|  Name | Type | Description |
|---|---|---|
| name  | _string_  |   |
| links  | _array_  |   |

references groups links item 
----------------------------

|  Name | Type | Description |
|---|---|---|
| pageId  | _number_  |   |
| date  | _date-time_  |   |
| name  | _string_  |   |
| link  | _string_  |   |
| condition  | _object_  |   |
