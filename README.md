# SE3_NachhilfeApp

## Deletion:
To improve consistency in the DB , deleting entries in one table should also delete linked entries from different tables.
Entries will not be physically deleted but property deleted will be set to true.

**Impacts of delete calls:**

Assignment -> Task -> none

Workload -> Submission -> Solution -> none

Contract: none

Member: -> Offer (Must have no active contracts) 

Subject: none **TBD IF SUBJECTS SHOULD BE DELETABLE**

**Important: Assignments & Tasks can currently still be delted, even if they are in an active workload //TODO** 

## REST:
  + Assignment:
    * GET
      - //ALL      
      http://localhost:8080/api/v1/assignments
      - //BY ID      
      http://localhost:8080/api/v1/assignments/byId/{assignmentID}
      - //BY OWNER       
      http://localhost:8080/api/v1/assignments/byOwner/{ownerID}
    * POST
      - //CREATE NEW ASSIGNMENT      
      http://localhost:8080/api/v1/assignments/add       
      JSON: {"owner": "\<ownerID\>", "subject": "\<subjectID\>", "name": "\<name\>", "description": "\<description\>"}
    * PUT
      - //UPDATE BY ID   
      http://localhost:8080/api/v1/assignments/update/{assignmentID}?name={name}&description={description}&subjectId={subjectId}      
      Parameter name, description and subjectId must not be present at all time only if subject to change
    * DELETE
      - //DELETE BY ID      
      http://localhost:8080/api/v1/assignments/delete/{assignmentID}
  + User:
    * GET
      - //ALL      
      http://localhost:8080/api/v1/user
      - //BY ID      
      http://localhost:8080/api/v1/user/byId/{userID}
    * POST
      - //CREATE NEW USER      
      http://localhost:8080/api/v1/user/add       
      JSON: {"name": "\<name\>", "needsHelp": "\<needsHelp\>", "offersHelp": "\<offersHelp\>"}
    * PUT
      - //UPDATE BY ID   
      http://localhost:8080/api/v1/user/update/{userID}?name={name}&needsHelp={needsHelp}&offersHelp={offersHelp}      
      Parameter name, needsHelp and offersHelp must not be present at all time only if subject to change
    * DELETE
      - //DELETE BY ID      
      http://localhost:8080/api/v1/user/delete/{userID}
  + Solution:
    * GET
        - //ALL      
          http://localhost:8080/api/v1/solution
        - //BY ID      
          http://localhost:8080/api/v1/solution/byId/{solutionID}
    * POST
        - //CREATE NEW SOLUTION      
          http://localhost:8080/api/v1/solution/add       
          JSON: {"taskID": "\<taskID\>", "submissionID": "\<submissionID\>", "solutionText": "\<solutionText\>"}
    * PUT
        - //UPDATE BY ID   
          http://localhost:8080/api/v1/solution/update/{solutionID}?solutionText={solutionText}
    * DELETE
        - //DELETE BY ID      
          http://localhost:8080/api/v1/solution/delete/{solutionID}