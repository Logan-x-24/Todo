package com.reactive.TODO.Handler;

import com.reactive.TODO.Model.Task;
import com.reactive.TODO.Service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class TaskHandler {

    private final TaskService taskService;

    public Mono<ServerResponse> saveTask(ServerRequest request){
        Mono<Task> task = request.bodyToMono(Task.class);
        return task
                .flatMap(taskService::saveTask)
                .flatMap(savedTask -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(savedTask)
                        .onErrorResume(s -> ServerResponse.badRequest().bodyValue("Error saving course:" + s.getMessage())));
    }

    public Mono<ServerResponse> getAllTasks(ServerRequest request){
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(taskService.getAllTasks(), Task.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }


    public Mono<ServerResponse> getTaskById(ServerRequest request){
       Integer id = Integer.valueOf(request.pathVariable("id"));
       return taskService.getTaskById(id)
               .flatMap(task -> ServerResponse
                       .ok()
                       .contentType(MediaType.APPLICATION_JSON)
                       .bodyValue(task))
               .switchIfEmpty(ServerResponse.notFound().build());

    }

    public Mono<ServerResponse> deleteTask(ServerRequest request){
         Integer id = Integer.valueOf(request.pathVariable("id"));

         return taskService.deleteTask(id)
                 .flatMap(task -> ServerResponse
                         .ok()
                         .bodyValue("Deleted Successfully"));
    }

    public Mono<ServerResponse> updateTask(ServerRequest request){
        Mono<Task> taskMono = request.bodyToMono(Task.class);
        Integer id = Integer.valueOf(request.pathVariable("id"));

        return taskMono
                .flatMap(task -> taskService.updateTask(id,task))
                .flatMap(updatedTask -> ServerResponse.ok().bodyValue(updatedTask))
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
