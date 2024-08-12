package com.reactive.TODO.Repository;

import com.reactive.TODO.Model.Task;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface TaskRepository extends R2dbcRepository<Task,Integer> {
}
