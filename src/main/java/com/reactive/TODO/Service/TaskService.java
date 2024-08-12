package com.reactive.TODO.Service;

import com.reactive.TODO.Model.Task;
import com.reactive.TODO.Repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public Mono<Task> saveTask(Task task){
        return taskRepository.save(task);
    }

    public Flux<Task> getAllTasks(){
        return taskRepository.findAll();
    }
    public Mono<Task> getTaskById(Integer id){
        return taskRepository.findById(id);
    }

    public Mono<Task> updateTask(Integer id, Task task){
        return taskRepository.findById(id)
                .flatMap(existingTask -> {
                    existingTask.setTitle(task.getTitle());
                    existingTask.setStatus(task.getStatus());
                    existingTask.setTask(task.getTask());
                    existingTask.setDate(task.getDate());
                    return taskRepository.save(existingTask);
                });
    }

    public Mono<Void> deleteTask(Integer id){
     return taskRepository.deleteById(id);
    }
}
