package com.SE3_NachhilfeApp.Workload;


import com.SE3_NachhilfeApp.Submission.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class WorkloadService {

    private final WorkloadRepository workloadRepository;
    private final SubmissionService submissionService;

    private final String doesNotExistMsg = "Workload does not exist";

    @Autowired
    public WorkloadService(WorkloadRepository workloadRepository, SubmissionService submissionService) {
        this.workloadRepository = workloadRepository;
        this.submissionService = submissionService;
    }

    //GET ALL
    public List<Workload> getAll(){
        return workloadRepository.findAll();
    }

    //GET Workload BY ID
    public Workload getById(UUID workloadID){
        return workloadRepository.findById(workloadID).orElseThrow(() -> new IllegalStateException(doesNotExistMsg));
    }

    //ADD NEW Workload
    public void createNew(Workload workload) {
        workloadRepository.save(workload);
    }

    //DELETE Workload BY ID
    @Transactional
    public void deleteById(UUID workloadID) {
        Workload workload = getById(workloadID);

        workload.setDeleted(true);

        submissionService.deleteById(workload.getSubmissionID());
    }

    //UPDATE Workload BY ID
    @Transactional
    public void updateById(UUID workloadID, LocalDate dueDate) {
        Workload workload = getById(workloadID);

        if(dueDate != null && dueDate.isAfter(LocalDate.now())){
            workload.setDueDate(dueDate);
        }
    }
}
