# SE3_NachhilfeApp

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
    * DELTE
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
    * DELTE
      - //DELETE BY ID      
      http://localhost:8080/api/v1/user/delete/{userID}
