package com.reactive.TODO.Router;

import com.reactive.TODO.Handler.TaskHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class TaskRouter {

    @Bean
    public RouterFunction<ServerResponse> route(TaskHandler taskHandler){
        return RouterFunctions
                .route(POST("/todo/save"),taskHandler::saveTask)
                .andRoute(GET("/todo/getall"),taskHandler::getAllTasks)
                .andRoute(GET("/todo/get/{id}"),taskHandler::getTaskById)
                .andRoute(DELETE("/todo/delete/{id}"),taskHandler::deleteTask)
                .andRoute(PUT("/todo/update/{id}"),taskHandler::updateTask);
    }

}
