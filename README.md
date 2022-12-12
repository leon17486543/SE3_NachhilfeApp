# SE3_NachhilfeApp

## REST:
  + Assignment:
    * GET
      - //ALL      
      http://localhost:8080/api/v1/assignments
      - //BY ID      
      http://localhost:8080/api/v1/assignments/byId/<assignmentID\>
      - //BY OWNER       
      http://localhost:8080/api/v1/assignments/byOwner/<ownerID\>
    * POST
      - //CREATE NEW ASSIGNMENT      
      http://localhost:8080/api/v1/assignments/add       
      JSON: {"owner": "\<ownerID\>", "subject": "\<subjectID\>", "name": "\<name\>", "description": "\<description\>"}
    * PUT
      - //UPDATE BY ID   
      http://localhost:8080/api/v1/assignments/update/<assignmentID\>?name=\<name\>&description=\<description\>&subjectId=\<subjectId\>      
      Parameter name, description and subjectId must not be present at all time only if subject to change
    * DELTE
      - //DELETE BY ID      
      http://localhost:8080/api/v1/assignments/delete/<assignmentID\>
      
