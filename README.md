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
