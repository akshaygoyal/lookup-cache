## Objective:
The objective of this to design and implement a component which encapsulates a data structure that will used to lookup reference data in a latency-sensitive data processing pipeline.

## Data Format:
The data is organized as follows:

* Outer Array of Organization Objects keyed by the orgKey and contain an array of ParameterNameObjects
    * ParameterNameObjects are keyed by parameterName and contain an array of 1...n SegmentConfig objects.
        * SegmentConfig objects are keyed by parameterValue which apply to that segment.  These can be an empty string, a single value, or a newline-delimited string of values. Multiple segment objects in a ParameterNameObject can share the same key.  Internally, Segment Objects have a segmentId field.

## Functional Requirements:
Functionally, the following must be supported:
The first function will return an array of SegmentConfig objects given an orgKey and a parameterName.  This call should return all SegmentConfigs associated
with that org and parameter, and with an empty parameterValue

The second function will return an array of SegmentConfig objects given an orgKey, a parameterName, and a parameterValue.  This call should return all SegmentConfigs associated
with that org, parameter, and parameter value.


### Example:
Given the following JSON Structure:
```json
[
    {
        "org1": [
            {
                "paramName1": [
                    {
                        "paramVal1": {
                            "segmentId": "seg_1234"
                        }
                    },
                    {
                        "paramVal2\nparamVal3\nparamVal4\nparamVal5": {
                            "segmentId": "intr.edu"
                        }
                    },
                    {
                        "paramVal6": {
                            "segmentId": "dem.infg.m"
                        }
                    },
                    {
                         "paramVal6": {
                             "segmentId": "intr.heal"
                         }
                    },
                    {
                          "paramVal6": {
                             "segmentId": "dem.infg.f"
                          }
                    }
                ]
            },
            {
                "testedu": [
                    {
                        "": {
                            "segmentId": "n277"
                        }
                    }
                ]
            },
            {
                "sid": [
                    {
                        "": {
                            "segmentId": "dem.life.expat"
                        }
                    }
                ]
            },
            {
                "gen": [
                    {
                        "Female": {
                            "segmentId": "dem.g.f"
                        }
                    },
                    {
                        "Male": {
                            "segmentId": "dem.g.m"
                        }
                    }
                ]
            }
        ]
    }
]
```
* The query getSegmentFor("org1", "paramName1")  will return an empty SegmentConfig array.
* The query getSegmentFor("org1", "paramName1", "paramVal1")  will return a 1-element SegmentConfigArray containing a SegmentConfig object for seg_1234
* The query getSegmentFor("org1", "paramName1", << "paramVal2" OR  "paramVal3" OR  "paramVal4" OR  "paramVal5" >> )  will return a 1-element SegmentConfigArray containing a SegmentConfig object with id: "intr.edu". (Note that in the data file paramVals may be in a "\n" delimited string.)
* The query getSegmentFor("org1", "paramName1", "paramVal6" )  will return a 3-element SegmentConfigArray containing SegmentConfig objects with ids: dem.infg.m, intr.heal, dem.infg.f
* The query getSegmentFor("org1", "testedu")  will return a 1-element SegmentConfigArray containing a SegmentConfig object with id "n277"
* The query getSegmentFor("org1", "testedu", "")  will return a 1-element SegmentConfigArray containing a SegmentConfig object with id "n277"
* The query getSegmentFor("org1", "testedu", << any value other than an empty string >>) will return an empty SegmentConfig array.
* The query getSegmentFor("org1", "gen", "Female") will return a 1-element SegmentConfigArray containing a SegmentConfig object with id "dem.g.f"
* The query getSegmentFor("org1", "gen", "Male") will return a 1-element SegmentConfigArray containing a SegmentConfig object with id "dem.g.m"
* The query getSegmentFor("org1", "gen", << any value other than "Male" or "Female" >>) will return an empty SegmentConfig array.

## Nonfunctional requirements:
* The data structure should be as small as possible in memory
* **HIGH IMPORTANCE**: The design should be optimized for speed of lookup (i.e., the methods defined in the interface).
* **HIGH IMPORTANCE**: The design should be optimized to be as memory stable as possible for lookups; meaning high rates of lookup calls should not
 generate memory churn and significant gc activity.
