package com.SE3_NachhilfeApp.Workload;


import com.SE3_NachhilfeApp.Task.Task;
import com.SE3_NachhilfeApp.Task.TaskRepository;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class WorkloadService {

    private final WorkloadRepository workloadRepository;

    @Autowired
    public WorkloadService(WorkloadRepository workloadRepository) {
        this.workloadRepository = workloadRepository;
    }

    //GET ALL
    public List<Workload> getWorkload(){
        return workloadRepository.findAll();
    }

    //GET Workload BY ID
    public Workload getWorkloadById(UUID workloadID){
        return workloadRepository.findById(workloadID).orElseThrow(() -> new IllegalStateException("Workload does not exist"));

    }

    //ADD NEW Tasks
    public void addNewWorkload(Workload workload) {
        workloadRepository.save(workload);
    }

    //DELETE Workload BY ID
    public void deleteWorkload(UUID workloadID) {
        workloadRepository.findById(workloadID);
        boolean exists = workloadRepository.existsById(workloadID);

        if(!exists){
            throw new IllegalStateException("Workload does not exist");
        }

        workloadRepository.deleteById(workloadID);
    }

    //UPDATE Workload BY ID
    @Transactional
    public void updateWorkload(UUID workloadID, LocalDate dueDate) {
        Workload workload = workloadRepository.findById(workloadID).orElseThrow(() -> new IllegalStateException("Workload does not exist"));

        if(dueDate != null && dueDate.isAfter(LocalDate.now())){
            workload.setDueDate(dueDate);
        }

    }
}
