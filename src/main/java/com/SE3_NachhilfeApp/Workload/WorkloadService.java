package com.SE3_NachhilfeApp.Workload;


import com.SE3_NachhilfeApp.Assignment.Assignment;
import com.SE3_NachhilfeApp.Submission.SubmissionRepository;
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
    private final SubmissionRepository submissionRepository;

    private final String doesNotExistMsg = "Workload does not exist";
    private final String doesNotExistMsgByTutor = "Tutor has no Workloads";
    private final String doesNotExistMsgBySchooler = "Schooler has no Workloads";

    @Autowired
    public WorkloadService(WorkloadRepository workloadRepository, SubmissionService submissionService, SubmissionRepository submissionRepository) {
        this.workloadRepository = workloadRepository;
        this.submissionService = submissionService;
        this.submissionRepository = submissionRepository;
    }

    //GET ALL
    public List<Workload> getAll(){
        return workloadRepository.findAll();
    }

    //GET Workload BY ID
    public Workload getById(UUID workloadID){
        return workloadRepository.findById(workloadID).orElseThrow(() -> new IllegalStateException(doesNotExistMsg));
    }

    //GET Workload BY Schooler
    public List<Workload> getBySchooler(UUID id){
        return workloadRepository.findWorkloadBySchooler(id).orElseThrow(() -> new IllegalStateException(doesNotExistMsgBySchooler));
    }

    //GET Workload BY Tutor
    public List<Workload> getByTutor(UUID id){
        return workloadRepository.findWorkloadByTutor(id).orElseThrow(() -> new IllegalStateException(doesNotExistMsgByTutor));
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

        if(submissionRepository.existsById(workload.getSubmissionID())){
            submissionService.deleteById(workload.getSubmissionID());
        }
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
